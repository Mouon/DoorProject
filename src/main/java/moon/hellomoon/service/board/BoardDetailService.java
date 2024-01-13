package moon.hellomoon.service.board;

import moon.hellomoon.domain.Board;
import moon.hellomoon.dto.BoardForm;
import moon.hellomoon.repository.repositoryInterface.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardDetailService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardDetailService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardForm detail(Long id) throws Exception{
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception("게시글을 찾을 수 없습니다."));
        BoardForm boardForm = new BoardForm(board.getId(), board.getMemberId(), board.getTitle(), board.getContent());
        return boardForm;
    }
}
