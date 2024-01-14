package moon.hellomoon.controller.bookmark;

import lombok.extern.slf4j.Slf4j;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.bookmark.BookmarkAddRequest;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.bookmark.BookmarkInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class BookmarksManageController {
    @Autowired

    private final BookmarkInsertService bookmarkInsertService;

    private final MemberRepository memberRepository;


    public BookmarksManageController(MemberRepository memberRepository, BookmarkInsertService bookmarkInsertService){
        this.memberRepository=memberRepository;
        this.bookmarkInsertService = bookmarkInsertService;
    }

    @PostMapping("/addBookmark")
    public ResponseEntity<?> addBookmarks(@RequestParam("name") String name,
                                          @RequestParam("bookmarkUrl") String bookmarkUrl,
                                          @RequestParam("imageFile") MultipartFile imageFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            BookmarkAddRequest request = new BookmarkAddRequest();
            request.setMemberId(member.getId());
            request.setName(name);
            request.setBookmarkUrl(bookmarkUrl);
            bookmarkInsertService.insertBookmark(request,imageFile);
            return ResponseEntity.ok().build();
        }else{
        return ResponseEntity.ok().build();
        }
    }

}
