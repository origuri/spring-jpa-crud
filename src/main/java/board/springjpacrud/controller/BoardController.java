package board.springjpacrud.controller;

import board.springjpacrud.dto.BoardDto;
import board.springjpacrud.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String findById(@PathVariable("id") Long boardId, Model model,
                           @PageableDefault(page = 1) Pageable pageable){
        boardService.updateHits(boardId);
        BoardDto boardDto = boardService.findById(boardId);
        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    /*
        Pageable 인터페이스는 paging할 때 사용할 수 있는 인터페이스
        Spring data 꺼로 임포트 해야됨.
        @PageableDefault(page = 1)는 /board/paging?page=1 에서 page를 정할 수 있음.
     */
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        //Page는 spring에서 제공하는 인터페이스, service 클래스에서 사용
        Page<BoardDto> boardList = boardService.paging(pageable);

        int blockLimit = 3; // 한 번에 보여줄 페이지 갯수, ex-> 1 2 3 // 5 6 7 // 11 12 13 등
        // 1 4 7 10 ~~
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        // 3 6 9 ~ 식인데 총 페이지가 8이면 8페이지가 endPage가 되게 삼항 연산자를 사용함.
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();


        // page 갯수가 20개
        // 현재 사용자 페이지가 3페이지면 표현이 좀 다름, 링크가 안되어있다던가. 색이 다르다던가

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "paging";
    }

}
