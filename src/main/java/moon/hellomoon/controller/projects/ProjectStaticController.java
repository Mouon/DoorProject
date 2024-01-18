package moon.hellomoon.controller.projects;

import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.project.ProjectStaticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectStaticController {

    private final ProjectStaticService projectStaticService;
    private final MemberRepository memberRepository;

    public ProjectStaticController(ProjectStaticService projectStaticService, MemberRepository memberRepository) {
        this.projectStaticService = projectStaticService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("project/static/tag")
    public ResponseEntity<List<Object[]>> ProjectStaticByTag(@RequestParam String tagName){
        List<Object[]> statics = projectStaticService.ProjectStaticByTag(tagName);
        return ResponseEntity.ok(statics);
    }

    @GetMapping("project/static")
    public ResponseEntity<List<Object[]>> ProjectStaticAll(){
        List<Object[]> statics = projectStaticService.ProjectAllStaticByTag();
        return ResponseEntity.ok(statics);
    }

    @GetMapping("project/static/user")
    public ResponseEntity<List<Object[]>> ProjectStaticUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            List<Object[]> statics = projectStaticService.ProjectUserStaticByTag(member.getId());
            return ResponseEntity.ok(statics);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }




}
