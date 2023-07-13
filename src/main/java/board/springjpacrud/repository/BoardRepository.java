package board.springjpacrud.repository;

import board.springjpacrud.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 조회수 올려주는 쿼리
    // update board_table set board_hits = board_hits + 1 where id = ?

    @Modifying // delete 기능을 사용할 때에는 필수로 이 어노테이션을 정의해야 함.
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);
}
