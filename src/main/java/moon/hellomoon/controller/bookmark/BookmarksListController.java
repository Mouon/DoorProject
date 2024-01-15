package moon.hellomoon.controller.bookmark;

import moon.hellomoon.domain.Bookmarks;
import moon.hellomoon.domain.Member;
import moon.hellomoon.repository.repositoryInterface.BookmarkRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookmarksListController {
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;

    public BookmarksListController(BookmarkRepository bookmarkRepository, MemberRepository memberRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/getBookmarks")
    public ResponseEntity<List<Bookmarks>> getBookmarks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            List<Bookmarks> bookmarks = bookmarkRepository.findByMemberId(member.getId());
            return ResponseEntity.ok(bookmarks);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
