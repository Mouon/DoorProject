package moon.hellomoon.service.diary;

import moon.hellomoon.domain.Diary;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.diary.DiaryCreateRequest;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DiaryInsertService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;


    @Autowired
    public DiaryInsertService(DiaryRepository diaryRepository, MemberRepository memberRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository=memberRepository;
    }
    @Transactional
    public void insertEvent(DiaryCreateRequest request){
        validateRequest(request);
        Diary diary = mapToDiaryDomain(request);
        diaryRepository.save(diary);
    }
    private void validateRequest(DiaryCreateRequest request) {
        Optional.ofNullable(request.getEventDate())
                .orElseThrow(()->new IllegalArgumentException("Member ID is required."));

        Optional.ofNullable(request.getEventDate())
                .orElseThrow(() -> new IllegalArgumentException("Event date is required."));

        Optional.ofNullable(request.getEventDescription())
                .filter(desc -> !desc.trim().isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Event description is required."));
    }

    private Diary mapToDiaryDomain(DiaryCreateRequest request){
        Diary diary = new Diary();
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        diary.setMember(member);
        diary.setEventDate(request.getEventDate());
        diary.setEventDescription(request.getEventDescription());
        diary.setCreateDate(request.getCreateDate());
        diary.setModifyDate(request.getModifyDate());
        return diary;
    }


}
