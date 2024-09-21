import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ContentServer implements NetworkNode {
  private String serverAddress;
  private int port;
  private LamportClock lamportClock;
  private boolean isRunning;


  public ContentServer(String serverAddress, int port, LamportClock lamportClock) {
    this.serverAddress = serverAddress;
    this.port = port; 
    this.lamportClock = lamportClock;
    this.isRunning = false;
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

  public void makePutRequest() {

  }

  public static void main(String[] args) {
    // Create a WeatherData object
    WeatherData weatherData = new WeatherData();
    weatherData.setId("IDS60901");
    weatherData.setName("Adelaide (West Terrace / ngayirdapira)");
    weatherData.setState("SA");
    weatherData.setTimeZone("CST");
    weatherData.setLat(-34.9);
    weatherData.setLon(138.6);
    weatherData.setLocalDateTime("15/04:00pm");
    weatherData.setLocalDateTimeFull("20230715160000");
    weatherData.setAirTemp(13.3);
    weatherData.setApparentTemp(9.5);
    weatherData.setCloud("Partly cloudy");
    weatherData.setDewpt(5.7);
    weatherData.setPress(1023.9);
    weatherData.setRelativeHumidity(60);
    weatherData.setWindDirection("S");
    weatherData.setWindSpeedKmh(15);
    weatherData.setWindSpeedKt(8);

    // Generate JSON body from WeatherData
    String jsonBody = weatherData.generateJsonBody();

    // Create an HTTP request using HttpRequestBuilder
    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
    String httpRequest = requestBuilder
        .setMethod("PUT")
        .setPath("/weather.json")
        .addHeader("Lamport-Timestamp", String.valueOf(1))
        .setBody(jsonBody)
        .build();

    // Print the generated HTTP request
    System.out.println(httpRequest);

    // Specify the server address and port
    String serverAddress = "localhost"; // Replace with actual server address if different
    int serverPort = 4567;              // Replace with the actual server port

    // Send the HTTP request to the aggregation server
    try (Socket socket = new Socket(serverAddress, serverPort);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      // Send the HTTP request
      out.print(httpRequest);
      out.flush(); // Ensure the request is sent immediately

      // Read the server's response
      String responseLine;
      StringBuilder response = new StringBuilder();
      while ((responseLine = in.readLine()) != null) {
          response.append(responseLine).append("\n");
      }

      // Print the server's response
      System.out.println("Server response:");
      System.out.println(response.toString());

    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
