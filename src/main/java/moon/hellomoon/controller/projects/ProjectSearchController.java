package moon.hellomoon.controller.projects;

import moon.hellomoon.domain.Project;
import moon.hellomoon.service.project.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectSearchController {
    private final ProjectService projectService;

    public ProjectSearchController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("project/search-tag")
    public ResponseEntity<List<Project>> viewProjectsByTag(@RequestParam String tagName){
        List<Project> projects = projectService.getProjectsByTag(tagName);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("project/all")
    public ResponseEntity<List<Project>> viewAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
}
