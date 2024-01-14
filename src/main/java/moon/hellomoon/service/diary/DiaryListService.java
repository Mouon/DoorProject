package moon.hellomoon.service.diary;

import moon.hellomoon.domain.Diary;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import org.springframework.stereotype.Service;

@Service
public class DiaryListService {
    private final DiaryRepository diaryRepository;

    public DiaryListService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));
    }
}
