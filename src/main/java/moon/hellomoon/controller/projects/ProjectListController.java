package moon.hellomoon.controller.projects;

import moon.hellomoon.domain.Project;
import moon.hellomoon.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ProjectListController {
    private final ProjectService projectService;

    public ProjectListController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/project-list")
    public String viewProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();

        model.addAttribute("projects", projects);

        return "community/projectList";
    }


}
