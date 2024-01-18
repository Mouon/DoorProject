package moon.hellomoon.service.project;

import moon.hellomoon.repository.jpaRepository.JpaProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStaticService {
    @Autowired
    JpaProjectRepository projectRepository;

    public List<Object[]> ProjectStaticByTag(String tagName){
        return projectRepository.CountTag(tagName);
    }

    public List<Object[]> ProjectAllStaticByTag(){
        return projectRepository.countAllTags();
    }

    public List<Object[]> ProjectUserStaticByTag(Long userId){
        return projectRepository.CountUserTag(userId);
    }
}
