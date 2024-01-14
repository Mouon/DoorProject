package moon.hellomoon.service.board;

import moon.hellomoon.domain.Board;
import moon.hellomoon.dto.Board.BoardDetailResponse;
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

    public BoardDetailResponse detail(Long id) throws Exception{
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception("게시글을 찾을 수 없습니다."));
        BoardDetailResponse boardDetailResponse = new BoardDetailResponse(board.getId(), board.getMemberId(), board.getTitle(), board.getContent());
        return boardDetailResponse;
    }
}
