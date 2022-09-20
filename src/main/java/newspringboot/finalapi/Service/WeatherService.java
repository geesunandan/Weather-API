package newspringboot.finalapi.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import newspringboot.finalapi.Entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;

@Service
public class WeatherService {

    private static final String weather_URL = "http://api.openweathermap.org/data/2.5/weather?q={country},{city}&appid={key}";

    @Value("${api.key}")
    private String apikey;

    //Instance variable
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    //Constructor
    public WeatherService(RestTemplateBuilder restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate.build();
        this.objectMapper = objectMapper;
    }

    public Weather getCurrentWeather(String country, String city){

        URI uri = null;
        uri = new UriTemplate(weather_URL).expand(country,city,apikey);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);

        return convert(responseEntity);
    }


    public Weather convert(ResponseEntity<String> responseEntity) {

        try {


            JsonNode root = objectMapper.readTree(responseEntity.getBody());
            return new Weather(
                    root.path("weather").get(0).path("main").asText(),
                    BigDecimal.valueOf(root.path("main").path("temp").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("feels_like").asDouble()),
                    BigDecimal.valueOf(root.path("wind").path("speed").asDouble()),
                    root.path("name").asText()

                    );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error" + e);
        }
     }
}
