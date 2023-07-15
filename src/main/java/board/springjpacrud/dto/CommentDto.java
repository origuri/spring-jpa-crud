package board.springjpacrud.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;   // 어떤 게시물인지 구분하기 우해

    private LocalDateTime commentCreateTime;

}
