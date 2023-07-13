package board.springjpacrud.controller;

import board.springjpacrud.dto.BoardDto;
import board.springjpacrud.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board") // 라우터와 비슷한 개념.
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 글 작성 form으로 가는 컨트롤러
    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto){
        System.out.println("boradDto => "+boardDto);
        boardService.save(boardDto);
        return "index";
    }
}
