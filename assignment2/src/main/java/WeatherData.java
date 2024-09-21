import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherData {
  private String id;
  private String name;
  private String state;
  private String timeZone;
  private double lat;
  private double lon;
  private String localDateTime;
  private String localDateTimeFull;
  private double airTemp;
  private double apparentTemp;
  private String cloud;
  private double dewpt;
  private double press;
  private int relativeHumidity;
  private String windDirection;
  private int windSpeedKmh;
  private int windSpeedKt;

  // Default constructor
  public WeatherData() {
  }

  // Getters and Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public String getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(String localDateTime) {
    this.localDateTime = localDateTime;
  }

  public String getLocalDateTimeFull() {
    return localDateTimeFull;
  }

  public void setLocalDateTimeFull(String localDateTimeFull) {
    this.localDateTimeFull = localDateTimeFull;
  }

  public double getAirTemp() {
    return airTemp;
  }

  public void setAirTemp(double airTemp) {
    this.airTemp = airTemp;
  }

  public double getApparentTemp() {
    return apparentTemp;
  }

  public void setApparentTemp(double apparentTemp) {
    this.apparentTemp = apparentTemp;
  }

  public String getCloud() {
    return cloud;
  }

  public void setCloud(String cloud) {
    this.cloud = cloud;
  }

  public double getDewpt() {
    return dewpt;
  }

  public void setDewpt(double dewpt) {
    this.dewpt = dewpt;
  }

  public double getPress() {
    return press;
  }

  public void setPress(double press) {
    this.press = press;
  }

  public int getRelativeHumidity() {
    return relativeHumidity;
  }

  public void setRelativeHumidity(int relativeHumidity) {
    this.relativeHumidity = relativeHumidity;
  }

  public String getWindDirection() {
    return windDirection;
  }

  public void setWindDirection(String windDirection) {
    this.windDirection = windDirection;
  }

  public int getWindSpeedKmh() {
    return windSpeedKmh;
  }

  public void setWindSpeedKmh(int windSpeedKmh) {
    this.windSpeedKmh = windSpeedKmh;
  }

  public int getWindSpeedKt() {
    return windSpeedKt;
  }

  public void setWindSpeedKt(int windSpeedKt) {
    this.windSpeedKt = windSpeedKt;
  }

  // Method to serialize the WeatherData object to JSON
  public String generateJsonBody() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  // Method to populate the WeatherData object from a JSON string
  public static WeatherData parseJson(String jsonString) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(jsonString, WeatherData.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String toString() {
    return "WeatherData{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", state='" + state + '\'' +
        ", timeZone='" + timeZone + '\'' +
        ", lat=" + lat +
        ", lon=" + lon +
        ", localDateTime='" + localDateTime + '\'' +
        ", localDateTimeFull='" + localDateTimeFull + '\'' +
        ", airTemp=" + airTemp +
        ", apparentTemp=" + apparentTemp +
        ", cloud='" + cloud + '\'' +
        ", dewpt=" + dewpt +
        ", press=" + press +
        ", relativeHumidity=" + relativeHumidity +
        ", windDirection='" + windDirection + '\'' +
        ", windSpeedKmh=" + windSpeedKmh +
        ", windSpeedKt=" + windSpeedKt +
        '}';
  }
}