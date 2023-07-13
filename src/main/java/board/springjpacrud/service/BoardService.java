package board.springjpacrud.service;

import board.springjpacrud.dto.BoardDto;

import board.springjpacrud.entity.BoardEntity;
import board.springjpacrud.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
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

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<BoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수 => 한 페이지에 3개의 글이 나옴.
        Page<BoardEntity> boardEntities =
                //여기서 page는 0부터 시작하기 때문에 int page에서 1을 빼줘야함./ 정렬 by entity의 필드 id로 내림차순
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // Page 객체를 가져가야 유용한 메소드를 사용할 수 있다. map이라는 메소드로 원하는 정보만 발라서  메핑 가능함.
        // 목록 : id, writer, title, hits, createdTime // board는 entity
        Page<BoardDto> boardDtos = boardEntities.map(boardEntity -> new BoardDto(boardEntity.getId(),boardEntity.getBoardWriter(),boardEntity.getBoardWriter(),
                                                                    boardEntity.getBoardTitle(),boardEntity.getBoardHits(),boardEntity.getCreatedTime()));
        return boardDtos;
    }
}
