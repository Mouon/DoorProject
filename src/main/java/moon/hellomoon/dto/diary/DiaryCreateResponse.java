package moon.hellomoon.dto.diary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class DiaryCreateResponse {
    private Long id;
    private LocalDate eventDate;
    private String eventDescription;
    private String createDate;
    private String modifyDate;
}
