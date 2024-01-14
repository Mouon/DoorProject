package moon.hellomoon.service.board;

import moon.hellomoon.dto.Board.BoardWriteRequest;
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

    public void insertBoard(BoardWriteRequest boardWriteRequest) throws Exception {
        Board board = new Board();
        board.setMemberId(boardWriteRequest.getMemberId());
        board.setTitle(boardWriteRequest.getTitle());
        board.setContent(boardWriteRequest.getContent());
        boardRepository.save(board);
    }
}
