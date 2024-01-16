package moon.hellomoon.service.weather;

import moon.hellomoon.repository.jpaRepository.JpaWeatherStationRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherInquiryService {
    @Value("${OPEN_API_KEY}")
    private String serviceKey;

    private final RestTemplate restTemplate;
    private final JpaWeatherStationRepository weatherStationRepository;

    public WeatherInquiryService(RestTemplate restTemplate, JpaWeatherStationRepository weatherStationRepository) {
        this.restTemplate = restTemplate;
        this.weatherStationRepository = weatherStationRepository;
    }

    public String getWeatherData(Integer stnIds, String startDt, String startHh, String endDt, String endHh) {
        String baseUrl = "http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList?ServiceKey="+serviceKey;

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("numOfRows", "10")
                .queryParam("pageNo", "1")
                .queryParam("dataType", "JSON")
                .queryParam("dataCd", "ASOS")
                .queryParam("dateCd", "HR")
                .queryParam("startDt", startDt)
                .queryParam("startHh", startHh)
                .queryParam("endDt", endDt)
                .queryParam("endHh", endHh)
                .queryParam("stnIds", stnIds.toString())
                .encode()
                .build()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }

    public String getTodayWeatherDataByStnIds(Integer stnIds){
        LocalDateTime now = LocalDateTime.now().minusDays(1);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");

        String todayDate = now.format(dateFormatter);
        String currentHour = now.format(hourFormatter);
        return getWeatherData(stnIds, todayDate, currentHour, todayDate, currentHour);

    }

    public String TodayWeatherDataByName(String name) {
        Optional<Integer> stnIdOptional = weatherStationRepository.findStationIdByName(name);
        if (stnIdOptional.isPresent()) {
            return getTodayWeatherDataByStnIds(stnIdOptional.get()); // 'Optional'의 값을 전달
        } else {
            throw new IllegalArgumentException("Station name not found");
        }
    }

    public List<String> getAllStationNames(){
        return weatherStationRepository.findAllStationNames();
    }

}
