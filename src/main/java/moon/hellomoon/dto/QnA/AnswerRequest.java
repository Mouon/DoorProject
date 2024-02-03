package moon.hellomoon.dto.QnA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {
    private Long memberId;
    private Long questionId;
    private String content;
}
