package board.springjpacrud.entity;

import board.springjpacrud.dto.BoardDto;
import lombok.*;

import javax.persistence.*;

// DB와 연결되는 클래스, service와 repository에서만 사용하자.
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    // 20자 이내로, not null 옵션
    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    //bulider 패턴 연습
    @Builder
    public BoardEntity(BoardDto boardDto){
        this.boardWriter = boardDto.getBoardWriter();
        this.boardPass = boardDto.getBoardPass();
        this.boardTitle = boardDto.getBoardTitle();
        this.boardContents = boardDto.getBoardContents();
        this.boardHits = boardDto.getBoardHits();
    }

    public static BoardEntity toSaveEntity(BoardDto boardDto){
        return BoardEntity.builder()
                .boardWriter(boardDto.getBoardWriter())
                .boardPass(boardDto.getBoardPass())
                .boardTitle(boardDto.getBoardTitle())
                .boardContents(boardDto.getBoardContents())
                .boardHits(boardDto.getBoardHits())
                .build();
    }

    public static BoardEntity toUpdateEntity(BoardDto boardDto) {
        return BoardEntity.builder()
                .id(boardDto.getId())
                .boardWriter(boardDto.getBoardWriter())
                .boardPass(boardDto.getBoardPass())
                .boardTitle(boardDto.getBoardTitle())
                .boardContents(boardDto.getBoardContents())
                .boardHits(boardDto.getBoardHits())
                .build();
    }

    public void boardUpdate(BoardDto boardDto){
        this.boardTitle = boardDto.getBoardTitle();
        this.boardContents = boardDto.getBoardContents();
    }

    // Dto를 entity로 바꾸는 메소드
    /*public static BoardEntity toSaveEntity(BoardDto boradDto){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boradDto.getBoardWriter());
        boardEntity.setBoardPass(boradDto.getBoardPass());
        boardEntity.setBoardTitle(boradDto.getBoardTitle());
        boardEntity.setBoardContents(boradDto.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }*/
}
