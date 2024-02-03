package moon.hellomoon.dto.QnA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    private Long memberId;
    private String title;
    private String content;
}
