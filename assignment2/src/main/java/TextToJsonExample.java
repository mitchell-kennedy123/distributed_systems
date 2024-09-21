import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextToJsonExample {

  public static void main(String[] args) {
    // Define the input text file and output JSON file paths
    String inputFilePath = "data/ContentServerData/weather.txt";
    String outputFilePath = "data/ContentServerData/weather.json";

    try {
      // Read all lines from the text file
      List<String> lines = Files.readAllLines(Paths.get(inputFilePath));

      // Build the JSON string
      StringBuilder jsonBuilder = new StringBuilder();
      jsonBuilder.append("{\n");

      for (String line : lines) {
        String[] keyValue = line.split(":", 2); // Split only on the first ":"
        if (keyValue.length == 2) {
          String key = keyValue[0].trim();
          String value = keyValue[1].trim();

          // Attempt to treat the value as a number if possible
          if (isNumeric(value)) {
            if (isInteger(value)) {
              long number = Long.parseLong(value);
              // Check if the number can fit in an int
              if (number >= Integer.MIN_VALUE && number <= Integer.MAX_VALUE) {
                jsonBuilder.append("    \"").append(key).append("\" : ").append(number).append(",\n");
              } else {
                jsonBuilder.append("    \"").append(key).append("\" : ").append(value).append(",\n");
              }
            } else {
              // It's a double
              double number = Double.parseDouble(value);
              jsonBuilder.append("    \"").append(key).append("\" : ").append(number).append(",\n");
            }
          } else {
            // Treat as a string if it's not numeric
            jsonBuilder.append("    \"").append(key).append("\" : \"").append(value).append("\",\n");
          }
        }
      }

      // Remove the trailing comma and newline, and close the JSON object
      if (jsonBuilder.length() > 2) {
        jsonBuilder.setLength(jsonBuilder.length() - 2); // Remove the last comma and newline
      }
      jsonBuilder.append("\n}");

      // Write the JSON string to the output file
      try (FileWriter fileWriter = new FileWriter(new File(outputFilePath))) {
        fileWriter.write(jsonBuilder.toString());
      }

      System.out.println("Successfully converted text to JSON and saved it to " + outputFilePath);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Check if a string is numeric
  private static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  // Check if a string is an integer
  private static boolean isInteger(String str) {
    try {
      Long.parseLong(str);
      return !str.contains("."); // Return true only if it doesn't contain a dot
    } catch (NumberFormatException e) {
      return false;
    }

  }
}
