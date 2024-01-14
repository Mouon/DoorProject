package moon.hellomoon.service.bookmark;

import moon.hellomoon.domain.Bookmarks;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.bookmark.BookmarkAddRequest;
import moon.hellomoon.repository.repositoryInterface.BookmarkRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.file.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BookmarkInsertService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public BookmarkInsertService(BookmarkRepository bookmarkRepository, MemberRepository memberRepository, FileStorageService fileStorageService) {
        this.bookmarkRepository = bookmarkRepository;
        this.memberRepository=memberRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void insertBookmark(BookmarkAddRequest bookmarkAddRequest, MultipartFile imageFile) {
        String imageUrl;
        try {
            imageUrl = fileStorageService.storeFile(imageFile);
        } catch (IOException e) {
            throw new RuntimeException("Error storing image file", e);
        }

        Member member = memberRepository.findById(bookmarkAddRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Bookmarks bookmarks = new Bookmarks();
        bookmarks.setMember(member);
        bookmarks.setName(bookmarkAddRequest.getName());
        bookmarks.setBookmarkUrl(bookmarkAddRequest.getBookmarkUrl());
        bookmarks.setImageUrl(bookmarkAddRequest.getImageUrl());

        bookmarkRepository.save(bookmarks);

    }




}
