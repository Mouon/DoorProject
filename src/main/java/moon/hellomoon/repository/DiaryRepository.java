package moon.hellomoon.repository;

import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Diary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface DiaryRepository {
    Diary save(Diary diary);
    Optional<Diary> findById(long id);
    List<Diary> findByMemberId(long id,LocalDate eventDate);
    List<Diary> findByMemberIdAndDate(long memberId, LocalDate eventDate);
    List<Diary> findByDate(LocalDate eventDate);
    List<Diary> findAll();
}
