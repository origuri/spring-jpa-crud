package board.springjpacrud.service;

import board.springjpacrud.dto.BoardDto;

import board.springjpacrud.entity.BoardEntity;
import board.springjpacrud.entity.BoardFileEntity;
import board.springjpacrud.repository.BoardFileRepository;
import board.springjpacrud.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
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
    private final BoardFileRepository boardFileRepository;


    public void save(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직을 분리
        if(boardDto.getBoardFile().isEmpty()){
            // 생성자 파라미터로 dto를 사용해서 entity로 변경한다. 파일이 없는 경우
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
            System.out.println(boardDto);
            // 변경한 entity로 insert 작업을 실행한다.
            boardRepository.save(boardEntity);
        }else{
            /*
            *   첨부 파일이 있음
            *   1. DTO에 담긴 파일을 꺼냄
            *   2. 파일 이름을 가져옴
            *   3. 서버 저장용 이름을 만듬
            *   4. 저장 경로 설정
            *   5. 해당 경로에 파일 저장
            *   6. board_title에 해당 데이터 save 처리
            *   7. board_file_table에 해당 데이터 save 처리
            * */
            /*MultipartFile boardFile = boardDto.getBoardFile();          // 1. DTO에 담긴 파일을 꺼냄
            String originalFileName = boardFile.getOriginalFilename();  // 2. 확장자까지 같이 들고 옴
            String storedFileName = System.currentTimeMillis()+"_"+originalFileName; // 3. 서버 저장용 이름을 만듦
            String savePath = "C:/spring-study/spring-jpa-crud/src/main/resources/static/img/" + storedFileName; //4. 저장경로
            boardFile.transferTo(new File(savePath)); // 5. 해당 경로에 파일 저장  여기서 파일 저장은 끝.
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto); // 6-1. dto를 엔티티로 변경
            Long savedBoardEntityId = boardRepository.save(boardEntity).getId(); // 6-2 save하고 id를 받아옴.
            BoardEntity savedBoardEntity = boardRepository.findById(savedBoardEntityId).get(); // 7-1 저장한 board를 다시 가져옴.
            BoardFileEntity file = BoardFileEntity.createFile(savedBoardEntity, originalFileName, storedFileName);// 7-2 boardFileEntity에 값을 넣고
            boardFileRepository.save(file); // 7-3 저장.*/

            // 다중 파일 저장일 경우 먼저 부모 테이블이 저장이 되어있어야 한다.
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
            Long savedBoardEntityId = boardRepository.save(boardEntity).getId();
            BoardEntity savedBoardEntity = boardRepository.findById(savedBoardEntityId).get();

            for(MultipartFile boardFile : boardDto.getBoardFile()){
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+"_"+originalFileName;
                String savePath = "C:/spring-study/spring-jpa-crud/src/main/resources/static/img/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                BoardFileEntity file = BoardFileEntity.createFile(savedBoardEntity, originalFileName, storedFileName);
                boardFileRepository.save(file);
            }


        }
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
