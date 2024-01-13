package moon.hellomoon.repository.repositoryInterface;

import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Diary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface DiaryRepository {
    Diary save(Diary diary);
    public void deleteDiaryById(Long diaryId);
    public void modifyDiaryById(Long diaryId,String newEventDescription);
    Optional<Diary> findById(long id);
    List<Diary> findByMemberId(long id,LocalDate eventDate);
    List<Diary> findByMemberIdAndDate(long memberId, LocalDate eventDate);
    List<Diary> findByDate(LocalDate eventDate);
    List<Diary> findAll();
}
