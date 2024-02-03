package moon.hellomoon.dto.QnA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequest {
    private Long memberId;
    private Long questionId;
    private Long answerId;
    private boolean like;
}
