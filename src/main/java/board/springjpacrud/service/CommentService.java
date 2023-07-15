package board.springjpacrud.service;

import board.springjpacrud.dto.BoardDto;
import board.springjpacrud.dto.CommentDto;
import board.springjpacrud.entity.BoardEntity;
import board.springjpacrud.entity.CommentEntity;
import board.springjpacrud.repository.BoardRepository;
import board.springjpacrud.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto){
        // commentEntity에 저장할 board_id 값을 가져오기 위해 boardEntity를 소환
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            // commentDto를 commentEntity로 바꾸는 메소드에 board_id를 넣기 위해 boardEntity도 같이 파라미터로 넘김.
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
            // 저장 후 commentEntity의 아이디 값을 리턴하여 저장 여부를 확인.
            return commentRepository.save(commentEntity).getId();
        }else{
            return null;
        }
    }
    // 해당 게시물의 댓글을 전부 가져옴.
    public List<CommentDto> findAllByBoardId(Long boardId) {
        // select * from comment_table where board_id = > order by createdTime desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByCreatedTimeDesc(boardEntity);
        /*comentEntityList -> dtoList*/
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList){
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;

    }
}
