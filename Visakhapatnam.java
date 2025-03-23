import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import org.json.JSONObject;

public class Visakhapatnam {
    public static void main(String[] args) {
        String apiKey = "e96b37f496c07d5cf1a855ad12651bd1";
        String city = "Visakhapatnam";
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            JSONObject weatherInfo = getWeatherInfo(apiUrl);
            displayWeatherData(weatherInfo);
        } catch (IOException e) {
          System.err.println("Error fetching weather data: " + e.getMessage());
        }
    }
        public static JSONObject getWeatherInfo(String apiUrl) throws IOException {
        URL url;
        try {
            URI uri = new URI(apiUrl);
            url = uri.toURL(); 
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL: " + apiUrl, e);
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String test;
            StringBuilder response = new StringBuilder();
            while ((test = reader.readLine()) != null) {
                response.append(test);
            }
            reader.close();
            return new JSONObject(response.toString());
        } else {
            throw new IOException("Failed to fetch weather data. Response code: " + responseCode);
        }
    }
    public static void displayWeatherData(JSONObject weatherData) {
               if (weatherData != null) {
            System.out.println("Weather in " + weatherData.getString("name") + ":");
            JSONObject main = weatherData.getJSONObject("main");
            System.out.println("Temperature: " + main.getDouble("temp") + "°C");
            System.out.println("Feels like: " + main.getDouble("feels_like") + "°C");
            System.out.println("Humidity: " + main.getInt("humidity") + "%");
            System.out.println("Description: " + weatherData.getJSONArray("weather").getJSONObject(0).getString("description"));
        } else {
            System.out.println("No weather data available.");
        }
    }
}

