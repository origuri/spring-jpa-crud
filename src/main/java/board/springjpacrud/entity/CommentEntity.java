package board.springjpacrud.entity;

import board.springjpacrud.dto.BoardDto;
import board.springjpacrud.dto.CommentDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;
    private String commentContents;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(CommentDto commentDto, BoardEntity boardEntity){
        return CommentEntity.builder()
                .commentWriter(commentDto.getCommentWriter())
                .commentContents(commentDto.getCommentContents())
                .boardEntity(boardEntity)
                .build();
    }
}
