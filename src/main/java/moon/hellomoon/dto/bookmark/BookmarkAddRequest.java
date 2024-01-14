package moon.hellomoon.dto.bookmark;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookmarkAddRequest {
    private Long id;
    private Long memberId;
    private String name;
    private String bookmarkUrl;
    private String imageUrl;
}
