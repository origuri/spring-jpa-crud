package board.springjpacrud.controller;

import board.springjpacrud.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    @ResponseBody
    @PostMapping("/save")
    public CommentDto save(@RequestBody CommentDto commentDto){
        System.out.println("잘넘어왓나? =? "+ commentDto);
        return commentDto;
    }
}
