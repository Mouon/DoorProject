package moon.hellomoon.service.board;

import moon.hellomoon.dto.BoardForm;
import moon.hellomoon.repository.repositoryInterface.BoardRepository;
import moon.hellomoon.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardWriteService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardWriteService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void insertBoard(BoardForm boardForm) throws Exception {
        Board board = new Board();
        board.setMemberId(boardForm.getMemberId());
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        boardRepository.save(board);
    }
}
