package moon.hellomoon.repository.repositoryInterface;

import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Bookmarks;
import moon.hellomoon.domain.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository {
    Bookmarks save(Bookmarks bookmarks);
    Optional<Bookmarks> findById(long id);
    Optional<Bookmarks> findByName(String name);
    List<Bookmarks> findByMemberId(long id);
    List<Bookmarks> findAll();
}
