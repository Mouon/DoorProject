package moon.hellomoon.service.diary;

import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaryManageService {
    private final DiaryRepository diaryRepository;

    public DiaryManageService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public void deleteEvent(Long diaryId) {
        diaryRepository.findById(diaryId).ifPresent(diary -> {
            diaryRepository.deleteDiaryById(diaryId);
        });
    }
    /**
     * 아래랑 같은 기능인데 위에 스타일을 습관화하자
     * Diary diary = diaryRepository.findById(diaryId);
     * if (diary != null) {
     *     diaryRepository.deleteDiaryById(diaryId);
     * }
     *
     * */

    @Transactional
    public void modifyEvent(Long diaryId,String eventDescription) {
        diaryRepository.modifyDiaryById(diaryId,eventDescription);
    }
}
