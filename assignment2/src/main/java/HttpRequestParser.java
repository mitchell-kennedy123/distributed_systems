import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

  private String method;
  private String path;
  private String httpVersion;
  private Map<String, String> headers;
  private String body;

  public HttpRequestParser(BufferedReader reader) throws IOException {
    headers = new HashMap<>();
    parseRequest(reader);
  }

  private void parseRequest(BufferedReader reader) throws IOException {
    // Parse the request line (e.g., "GET /index.html HTTP/1.1")
    String requestLine = reader.readLine();
    if (requestLine != null && !requestLine.isEmpty()) {
      String[] parts = requestLine.split(" ");
      if (parts.length == 3) {
        method = parts[0];
        path = parts[1];
        httpVersion = parts[2];
      }
    }

    // Parse the headers
    String headerLine;
    while (!(headerLine = reader.readLine()).isEmpty()) {
      String[] headerParts = headerLine.split(": ", 2);
      if (headerParts.length == 2) {
        headers.put(headerParts[0], headerParts[1]);
      }
    }

    // Parse the body (if any)
    if (headers.containsKey("Content-Length")) {
      int contentLength = Integer.parseInt(headers.get("Content-Length"));
      char[] bodyChars = new char[contentLength];
      reader.read(bodyChars, 0, contentLength);
      body = new String(bodyChars);
    } else {
      body = "";
    }
  }

  // Getters for accessing the parsed data

  public String getMethod() {
    return method;
  }

  public String getPath() {
    return path;
  }

  public String getHttpVersion() {
    return httpVersion;
  }

  public String getHeader(String headerName) {
    return headers.get(headerName);
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append(method).append(" ").append(path).append(" ").append(httpVersion).append("\n");
    for (Map.Entry<String, String> header : headers.entrySet()) {
      result.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
    }
    result.append("\n").append(body);
    return result.toString();
  }
}