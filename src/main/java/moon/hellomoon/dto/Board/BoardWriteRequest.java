package moon.hellomoon.dto.Board;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardWriteRequest {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String date;
}
