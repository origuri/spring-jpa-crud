package board.springjpacrud.service;

import board.springjpacrud.dto.BoardDto;

import board.springjpacrud.entity.BoardEntity;
import board.springjpacrud.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// DTO -> Entity로 변환 (Entity class에서 변환 메소드 작성)
// Entity -> Dto로 변환하는 과정이 일어나는 클래스 (DTO class에서 변환 메소드 작성 예정)

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void save(BoardDto boardDto) {
        // 생성자 파라미터로 dto를 사용해서 entity로 변경한다.
        BoardEntity boardEntity = new BoardEntity(boardDto);
        // 변경한 entity로 insert 작업을 실행한다.
        boardRepository.save(boardEntity);
    }

    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        /*for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(new BoardDto(boardEntity));
        }*/
        boardEntityList.stream().forEach(b -> boardDtoList.add(new BoardDto(b)));
        return boardDtoList;

    }

    @Transactional
    public void updateHits(Long boardId) {
        boardRepository.updateHits(boardId);
    }

    public BoardDto findById(Long boardId) {
        // null이 있을 수 있으면 optional로 받음
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        // 만약 값이 들어있다면
        if(optionalBoardEntity.isPresent()){
            // 여기에 넣는 거죠.
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDto boardDto = new BoardDto(boardEntity);
            return boardDto;
        } else{
            return null;
        }

    }
}
