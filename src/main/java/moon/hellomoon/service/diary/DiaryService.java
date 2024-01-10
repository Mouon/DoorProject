package moon.hellomoon.service.diary;

import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Diary;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.BoardForm;
import moon.hellomoon.dto.DiaryForm;
import moon.hellomoon.repository.BoardRepository;
import moon.hellomoon.repository.DiaryRepository;
import moon.hellomoon.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;


    @Autowired
    public DiaryService(DiaryRepository diaryRepository,MemberRepository memberRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository=memberRepository;
    }

    @Transactional
    public void insertEvent(DiaryForm diaryForm) {
        Diary diary = new Diary();
        Member member = memberRepository.findById(diaryForm.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        diary.setMember(member);
        diary.setEventDate(diaryForm.getEventDate());
        diary.setEventDescription(diaryForm.getEventDescription());
        diary.setCreateDate(diaryForm.getCreateDate());
        diary.setModifyDate(diaryForm.getModifyDate());
        diaryRepository.save(diary);
    }

}
