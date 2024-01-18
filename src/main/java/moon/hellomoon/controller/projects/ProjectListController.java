package moon.hellomoon.controller.projects;

import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.member.MemberService;
import moon.hellomoon.service.project.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProjectListController {
    private final ProjectService projectService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    public ProjectListController(ProjectService projectService, MemberRepository memberRepository, MemberService memberService) {
        this.projectService = projectService;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @GetMapping("/project-list")
    public String viewProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "community/projectList";
    }



    @GetMapping("/project-list/user")
    public String viewUserProjects(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            List<Project> projects = projectService.getProjectsByUser(member.getId());
            model.addAttribute("projects", projects);

            return "community/projectUserList";
        } else {
            return ResponseEntity.ok().toString();
        }
    }




}
