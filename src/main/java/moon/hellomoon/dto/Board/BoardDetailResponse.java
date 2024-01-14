package moon.hellomoon.dto.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDetailResponse {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String date;


    public BoardDetailResponse(Long id, Long memberId, String title, String content) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}
