package board.springjpacrud.controller;

import board.springjpacrud.dto.CommentDto;
import board.springjpacrud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/save") // json 형식의 데이터를 받기 위해 @RequestBody 사용
    public ResponseEntity save(@RequestBody CommentDto commentDto){
        System.out.println("잘넘어왓나? =? "+ commentDto);
        Long saveResult = commentService.save(commentDto);
        // 저장 결과가 있다면
        if(saveResult != null){
            // 작성 성공하면 댓글 목록을 가져와서 리턴.
            // 댓글 목록 : 해당 게시글의 댓글 전체니까 해당 게시글의 id가 필요함.
           List<CommentDto> commentDtoList = commentService.findAllByBoardId(commentDto.getBoardId());
           return new ResponseEntity(commentDtoList, HttpStatus.OK);
        }else{

            return new ResponseEntity("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
