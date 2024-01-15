package moon.hellomoon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "weather_stations")
public class WeatherStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stnId;
    private String stnName;
    private String managementOffice;

}
