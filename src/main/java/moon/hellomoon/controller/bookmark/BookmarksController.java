package moon.hellomoon.controller.bookmark;

import lombok.extern.slf4j.Slf4j;
import moon.hellomoon.domain.Bookmarks;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.BookmarkForm;
import moon.hellomoon.repository.repositoryInterface.BookmarkRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.file.FileStorageService;
import moon.hellomoon.service.bookmark.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class BookmarksController {
    @Autowired
    private final BookmarkRepository bookmarkRepository;

    private final BookmarkService bookmarkService;

    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;


    public BookmarksController(BookmarkRepository bookmarkRepository, MemberRepository memberRepository, BookmarkService bookmarkService,FileStorageService fileStorageService){
        this.bookmarkRepository=bookmarkRepository;
        this.memberRepository=memberRepository;
        this.bookmarkService=bookmarkService;
        this.fileStorageService=fileStorageService;
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
            BookmarkForm bookmarkForm = new BookmarkForm();
            bookmarkForm.setMemberId(member.getId());
            bookmarkForm.setName(name);
            bookmarkForm.setBookmarkUrl(bookmarkUrl);
            try {
                String imageUrl = fileStorageService.storeFile(imageFile);
                bookmarkForm.setImageUrl(imageUrl);
            } catch (IOException e) {
                log.error("Error storing image file: " + e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            bookmarkService.insertBookmark(bookmarkForm);
        }


        return ResponseEntity.ok().build();
    }

    @GetMapping("/getBookmarks")
    public ResponseEntity<List<Bookmarks>> getBookmarks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            LocalDate currentDate = LocalDate.now();
            List<Bookmarks> bookmarks = bookmarkRepository.findByMemberId(member.getId());
            return ResponseEntity.ok(bookmarks);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
