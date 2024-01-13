package moon.hellomoon.repository.repositoryInterface;

import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Member;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    Optional<Board> findById(long id);
    Optional<Board> findByTitle(String title);
    Optional<Board> findByContent(String content);
    List<Board> findAll();
}
