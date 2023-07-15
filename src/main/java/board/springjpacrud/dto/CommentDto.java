package board.springjpacrud.dto;

import board.springjpacrud.entity.CommentEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class CommentDto {

    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;   // 어떤 게시물인지 구분하기 우해

    private LocalDateTime commentCreateTime;

    public static CommentDto toCommentDto(CommentEntity commentEntity) {
        return CommentDto.builder()
                .id(commentEntity.getId())
                .commentWriter(commentEntity.getCommentWriter())
                .commentContents(commentEntity.getCommentContents())
                .boardId(commentEntity.getBoardEntity().getId())
                .commentCreateTime(commentEntity.getCreatedTime())
                .build();
    }
}
