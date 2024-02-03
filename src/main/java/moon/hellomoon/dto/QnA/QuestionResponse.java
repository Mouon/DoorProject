package moon.hellomoon.dto.QnA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponse {
    private Long memberId;
    private Long id;
    private String title;
    private String content;
    private int likeCount;
}
