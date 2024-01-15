package moon.hellomoon.dto.project;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class ProjectDetailResponse {
    private String title;
    private String content;
    private List<String> tags = new ArrayList<>();
    private String githubAddress;

    public ProjectDetailResponse(String title, String content,List<String> tags,String githubAddress){
        this.title=title;
        this.content=content;
        this.tags=tags;
        this.githubAddress=githubAddress;

    }
}
