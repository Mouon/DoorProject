package moon.hellomoon.service.project;

import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.dto.project.ProjectInsertRequest;
import moon.hellomoon.repository.jpaRepository.JpaMemberRepository;
import moon.hellomoon.repository.jpaRepository.JpaProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    JpaProjectRepository projectRepository;
    @Autowired
    JpaMemberRepository memberRepository;

    private Project mapToProjectDomain(ProjectInsertRequest request){

        Project project =new Project();
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        project.setMember(member);
        project.setTags(request.getTags());
        project.setTitle(request.getTitle());
        project.setContent(request.getContent());
        project.setGithubAddress(request.getGithubAddress());
        return project;
    }
    @Transactional
    public void addProject(ProjectInsertRequest request){
        Project project = mapToProjectDomain(request);
        projectRepository.save(project);
    }

    public List<Project> getProjectsByTag(String tagName){
        return projectRepository.findByTag(tagName);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByUser(Long userId){
        return projectRepository.findByUser(userId);
    }

}
