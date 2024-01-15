package moon.hellomoon.dto.project;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ProjectInsertRequest {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private List<String> tags = new ArrayList<>();
    private String githubAddress;
}
