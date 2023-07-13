package board.springjpacrud.dto;

import board.springjpacrud.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자를 만들어주는 어노테이션
// @AllArgsConstructor // 모든 필드를 아규먼트로 사용하는 생성자.
public class BoardDto {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    @Builder
    public BoardDto(BoardEntity boardEntity){
        this.id = boardEntity.getId();
        this.boardWriter = boardEntity.getBoardWriter();
        this.boardPass = boardEntity.getBoardPass();
        this.boardTitle = boardEntity.getBoardTitle();
        this.boardContents = boardEntity.getBoardContents();
        this.boardHits = boardEntity.getBoardHits();
        this.boardCreatedTime = boardEntity.getCreatedTime();
        this.boardUpdatedTime = boardEntity.getUpdatedTime();

    }

}
