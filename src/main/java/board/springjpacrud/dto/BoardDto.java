package board.springjpacrud.dto;

import board.springjpacrud.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자를 만들어주는 어노테이션
@AllArgsConstructor // 모든 필드를 아규먼트로 사용하는 생성자.
@Builder
public class BoardDto {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    // 파일 업로드 관련 필드 추가
    private MultipartFile boardFile;    // 파일을 담을 수 있는 인터페이스
    private String originalFileName;    // 원본 파일 이름
    private String storedFileName;      // 서버 저장용 파일 이름.
    private int fileAttached;           // 파일 첨부 여부 (첨부 : 1, 미첨부 0) boolean 타입을 쓰면 엔티티에서 복잡해짐.

    public BoardDto(Long id, String boardWriter, String boardTitle, String boardContents, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDto toSaveDto(BoardEntity boardEntity){
        return BoardDto.builder()
                .id(boardEntity.getId())
                .boardWriter(boardEntity.getBoardWriter())
                .boardPass(boardEntity.getBoardPass())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContents(boardEntity.getBoardContents())
                .boardHits(boardEntity.getBoardHits())
                .boardCreatedTime(boardEntity.getCreatedTime())
                .boardUpdatedTime(boardEntity.getUpdatedTime())
                .build();
    }

}
