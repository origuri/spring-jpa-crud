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
