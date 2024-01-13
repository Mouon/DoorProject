package moon.hellomoon.dto.diary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class DiaryInquiryRequest {
    private LocalDate eventDate;

}
