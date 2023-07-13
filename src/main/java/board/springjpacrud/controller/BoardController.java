package board.springjpacrud.controller;

import board.springjpacrud.dto.BoardDto;
import board.springjpacrud.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public String findAll(Model model){
        // view에 보내야하니까 dto로 받아야 함.
        List<BoardDto> boardDtoList = boardService.findAll();
        model.addAttribute("boardList", boardDtoList);
        return "list";
    }

    /*
    *   해당 게시물의 조회수를 하나 올리고
    *   게시글 데이터를 가져와서 detail.html에 출력
    * */
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long boardId, Model model){
        boardService.updateHits(boardId);
        BoardDto boardDto = boardService.findById(boardId);
        model.addAttribute("board", boardDto);
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model){
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
        return "detail";
    }

}
