package moon.hellomoon.service.bookmark;

import moon.hellomoon.domain.Bookmarks;
import moon.hellomoon.domain.Diary;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.BookmarkForm;
import moon.hellomoon.dto.DiaryForm;
import moon.hellomoon.repository.repositoryInterface.BookmarkRepository;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;


    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository,MemberRepository memberRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.memberRepository=memberRepository;
    }

    @Transactional
    public void insertBookmark(BookmarkForm bookmarkForm) {
        Bookmarks bookmarks = new Bookmarks();
        Member member = memberRepository.findById(bookmarkForm.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        bookmarks.setMember(member);
        bookmarks.setName(bookmarkForm.getName());
        bookmarks.setBookmarkUrl(bookmarkForm.getBookmarkUrl());
        bookmarks.setImageUrl(bookmarkForm.getImageUrl());

        bookmarkRepository.save(bookmarks);

    }

}
