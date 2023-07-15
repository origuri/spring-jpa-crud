package board.springjpacrud.repository;



import board.springjpacrud.entity.BoardEntity;
import board.springjpacrud.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id = ? order by createdTime desc;
    // CommentEntity에서 BoardEntity 필드를 createdTime Desc 순으로 전부 찾으라는 메소드
    List<CommentEntity> findAllByBoardEntityOrderByCreatedTimeDesc(BoardEntity boardEntity);
}
