import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataReader {

  public static void main(String[] args) {
    String filePath = "path_to_your_file.txt"; // Change this to your file path
    List<WeatherData> weatherDataList = readWeatherDataFromFile(filePath);

    // Print the weather data to verify
    for (WeatherData weatherData : weatherDataList) {
      System.out.println(weatherData);
    }
  }

  public static List<WeatherData> readWeatherDataFromFile(String filePath) {
    List<WeatherData> weatherDataList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      WeatherData currentData = new WeatherData();
      while ((line = br.readLine()) != null) {
        if (line.trim().isEmpty()) {
          // End of a weather data entry, add it to the list
          weatherDataList.add(currentData);
          currentData = new WeatherData(); // Reset for the next entry
        } else {
          // Process the line and populate the currentData object
          String[] parts = line.split(":", 2);
          if (parts.length == 2) {
            String key = parts[0].trim();
            String value = parts[1].trim();
            populateWeatherData(currentData, key, value);
          }
        }
      }
      // Add the last entry if the file doesn't end with a blank line
      if (!currentData.getId().isEmpty()) {
        weatherDataList.add(currentData);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return weatherDataList;
  }

  private static void populateWeatherData(WeatherData weatherData, String key, String value) {
    switch (key) {
      case "id":
        weatherData.setId(value);
        break;
      case "name":
        weatherData.setName(value);
        break;
      case "state":
        weatherData.setState(value);
        break;
      case "time_zone":
        weatherData.setTimeZone(value);
        break;
      case "lat":
        weatherData.setLat(Double.parseDouble(value));
        break;
      case "lon":
        weatherData.setLon(Double.parseDouble(value));
        break;
      case "local_date_time":
        weatherData.setLocalDateTime(value);
        break;
      case "local_date_time_full":
        weatherData.setLocalDateTimeFull(value);
        break;
      case "air_temp":
        weatherData.setAirTemp(Double.parseDouble(value));
        break;
      case "apparent_t":
        weatherData.setApparentTemp(Double.parseDouble(value));
        break;
      case "cloud":
        weatherData.setCloud(value);
        break;
      case "dewpt":
        weatherData.setDewpt(Double.parseDouble(value));
        break;
      case "press":
        weatherData.setPress(Double.parseDouble(value));
        break;
      case "rel_hum":
        weatherData.setRelativeHumidity(Integer.parseInt(value));
        break;
      case "wind_dir":
        weatherData.setWindDirection(value);
        break;
      case "wind_spd_kmh":
        weatherData.setWindSpeedKmh(Integer.parseInt(value));
        break;
      case "wind_spd_kt":
        weatherData.setWindSpeedKt(Integer.parseInt(value));
        break;
    }
  }
}