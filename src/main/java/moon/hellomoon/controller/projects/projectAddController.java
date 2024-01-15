package moon.hellomoon.controller.projects;

import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.project.ProjectInsertRequest;
import moon.hellomoon.repository.jpaRepository.JpaProjectRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class projectAddController {

    private final MemberRepository memberRepository;
    private final ProjectService projectService;

    public projectAddController(MemberRepository memberRepository, ProjectService projectService) {
        this.memberRepository = memberRepository;
        this.projectService = projectService;
    }


    @PostMapping("/projects")
    public ResponseEntity<?> addProject(@RequestBody ProjectInsertRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            request.setMemberId(member.getId());
            projectService.addProject(request);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
