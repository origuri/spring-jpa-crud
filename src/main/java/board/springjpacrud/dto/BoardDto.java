package board.springjpacrud.dto;

import board.springjpacrud.entity.BoardEntity;
import lombok.*;

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
