package newspringboot.finalapi.Controller;

import newspringboot.finalapi.Entity.Weather;
import newspringboot.finalapi.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller

public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String getWeather(Model model) {
//        Weather weather = new Weather("Clear", BigDecimal.ONE,BigDecimal.ZERO, BigDecimal.TEN);
//        model.addAttribute("weather",weather);

        model.addAttribute("currentWeather", weatherService.getCurrentWeather("US", "New York"));
        model.addAttribute("currentWeather", weatherService.getCurrentWeather("US", "Chciago"));

        return "index";
    }
}

