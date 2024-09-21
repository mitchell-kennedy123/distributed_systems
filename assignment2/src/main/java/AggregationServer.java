
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class AggregationServer implements NetworkNode {

  private String serverAddress;
  private int port;
  private LamportClock lamportClock;
  private boolean isRunning;

  private ConcurrentHashMap<Integer, WeatherData> dataStore;

  public AggregationServer(String serverAddress, int port, LamportClock lamportClock) {
    this.serverAddress = serverAddress;
    this.port = port; 
    this.lamportClock = lamportClock;
    this.isRunning = false;
    this.dataStore = new ConcurrentHashMap<>();
  }
  
  @Override
  public boolean startup() {
    // TODO: Reads JSON file to load weather data
    return true;
  }

  @Override
  public boolean isRunning() {
    return this.isRunning;
  }

  @Override
  public String getServerAddress() {
    return this.serverAddress;
  }

  @Override
  public int getPort() {
    return this.port;
  }

  @Override
  public LamportClock getLamportClock() {
    return this.lamportClock;
  }

  /**
   * Removes expired weather data. This is data drom content
   * servers that have make a PUT request for more than 30 seconds.
   */
  public void cleanJson() {

  }

  public static void main(String[] args) {
    int port = 4567; // Default port
    if (args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.err.println("Invalid port number provided. Using default port " + port);
      }
    }

    LamportClock lamportClock = new LamportClockImpl();
    AggregationServer server = new AggregationServer("localhost", port, lamportClock);
    server.startup();
    server.listen();
  }

  public void listen() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        try (Socket clientSocket = serverSocket.accept()) {
          handleClient(clientSocket);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleClient(Socket clientSocket) {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream os = clientSocket.getOutputStream();
        PrintWriter out = new PrintWriter(os, true))
      {

      HttpRequestParser request = new HttpRequestParser(in);
      // Process the request using the parsed data
      System.out.println("Request Method: " + request.getMethod());
      System.out.println("Request Path: " + request.getPath());
      System.out.println("Request HTTP Version: " + request.getHttpVersion());
      System.out.println("Request Headers: " + request.getHeaders());
      System.out.println("Request Body: " + request.getBody());


      // Store the data and update Lamport Clock
      int status = handlePutRequest(request);
      String statusText = (status == StatusCodes.CREATED) ? "Created" : "OK";
      sendResponse(out, status, statusText, "Data stored successfully");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void sendResponse(PrintWriter out, int statusCode, String statusText, String message) {
    out.println("HTTP/1.1 " + statusCode + " " + statusText);
    out.println("Content-Type: text/plain");
    out.println("Content-Length: " + message.length());
    out.println();
    out.println(message);
  }

  public int handlePutRequest(HttpRequestParser request) {
    int requestLamportTimestamp = Integer.parseInt(request.getHeader("Lamport-Timestamp"));
    WeatherData newData = WeatherData.parseJson(request.getBody());
    lamportClock.processEvent(requestLamportTimestamp);
    boolean isNewEntry = (dataStore.put(requestLamportTimestamp, newData) == null);
    return isNewEntry ? StatusCodes.CREATED : StatusCodes.OK;
  }
}
