package moon.hellomoon.controller.weather;

import lombok.extern.slf4j.Slf4j;
import moon.hellomoon.service.weather.WeatherInquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class WeatherInquiryController {
    private final WeatherInquiryService weatherInquiryService;

    public WeatherInquiryController(WeatherInquiryService weatherInquiryService) {
        this.weatherInquiryService = weatherInquiryService;
    }

    @GetMapping("/get-weather")
    @ResponseBody
    public ResponseEntity<String> getWeather(@RequestParam String stationName){
        String weatherData = weatherInquiryService.TodayWeatherDataByName(stationName);
        return ResponseEntity.ok(weatherData);
    }

    @GetMapping("/get-station-names")
    public ResponseEntity<List<String>> getStationNames(){
        List<String> stationNames = weatherInquiryService.getAllStationNames();
        return ResponseEntity.ok(stationNames);
    }


}
