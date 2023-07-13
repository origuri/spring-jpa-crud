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
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public void save(BoardDto boardDto) {
        // 생성자 파라미터로 dto를 사용해서 entity로 변경한다.
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
        System.out.println(boardDto);
        // 변경한 entity로 insert 작업을 실행한다.
        boardRepository.save(boardEntity);
    }
    @Transactional(readOnly = true)
    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        /*for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(new BoardDto(boardEntity));
        }*/
        boardEntityList.stream().forEach(b -> boardDtoList.add(BoardDto.toSaveDto(b)));
        return boardDtoList;

    }


    public void updateHits(Long boardId) {
        boardRepository.updateHits(boardId);
    }

    @Transactional(readOnly = true)
    public BoardDto findById(Long boardId) {
        // null이 있을 수 있으면 optional로 받음
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        // 만약 값이 들어있다면
        if(optionalBoardEntity.isPresent()){
            // 여기에 넣는 거죠.
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toSaveDto(boardEntity);
            return boardDto;
        } else{
            return null;
        }

    }

    public BoardDto update(BoardDto boardDto) {
        // id 값으로 영속 상태로 만듬
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDto.getId());
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            // boardUpdate 메소드는 내용과 제목만 바꿀 수 있음.
            // 쿼리는 필드 전체를 update하는 쿼리가 나가지만 실제로 바뀐건 내용과 제목만 바뀜.
            // update 메소드에서 변경감지가 된건 제목과 내용이고 나머지는 원래 값으로 update
            boardEntity.boardUpdate(boardDto);
            boardDto = BoardDto.toSaveDto(boardEntity);
            return boardDto;
        }else {
            return null;
        }
    }
}
