package moon.hellomoon.service.project;

import moon.hellomoon.domain.Project;
import moon.hellomoon.dto.project.ProjectDetailResponse;
import moon.hellomoon.repository.jpaRepository.JpaProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectDetailService {
    @Autowired
    JpaProjectRepository projectRepository;


    public ProjectDetailResponse detail(Long id) throws Exception{
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("게시글을 찾을 수 없습니다."));
        ProjectDetailResponse projectDetailResponse = new ProjectDetailResponse(project.getTitle(),project.getContent(),project.getTags(),project.getGithubAddress());
        return projectDetailResponse;
    }

}
