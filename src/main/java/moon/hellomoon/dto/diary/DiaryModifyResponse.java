package moon.hellomoon.dto.diary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class DiaryModifyResponse {
    private Long id;
    private LocalDate eventDate;
    private String eventDescription;
    private String createDate;
    private String modifyDate;
}
