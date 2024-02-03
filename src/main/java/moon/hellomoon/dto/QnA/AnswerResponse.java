package moon.hellomoon.dto.QnA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponse {
    private Long memberId;
    private String content;
    private int likeCount;

}