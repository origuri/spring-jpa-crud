package board.springjpacrud.service;

import board.springjpacrud.dto.BoardDto;

import board.springjpacrud.entity.BoardEntity;
import board.springjpacrud.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// DTO -> Entity로 변환 (Entity class에서 변환 메소드 작성)
// Entity -> Dto로 변환하는 과정이 일어나는 클래스 (DTO class에서 변환 메소드 작성 예정)

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDto boardDto) {
        // 생성자 파라미터로 dto를 사용해서 entity로 변경한다.
        BoardEntity boardEntity = new BoardEntity(boardDto);
        // 변경한 entity로 insert 작업을 실행한다.
        boardRepository.save(boardEntity);
    }
}
