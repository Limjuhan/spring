package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.*;
import com.fastcampus.ch4.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import javax.servlet.http.*;
import java.time.*;
import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);//insert

            //수정실패시 게시판이아닌 쓰던내용으로 돌아가게하기
            if(rowCnt!=1)
                throw new Exception("Modify failed");

            rattr.addAttribute("page",page);
            rattr.addAttribute("pageSize",pageSize);

            //모델에담아 메시지전달시 지속적으로남아 새로고침시 메시지 계속 발생하므로 1회성을 위해
            //RedirectAttributes객체의 addFlashAttribute사용
            rattr.addFlashAttribute("msg","MDF_OK");

            return "redirect:/board/list";// 컨트롤러내에서 list메서드로 가던가 파라미터 담아서 맞는 url로 보내야하는데..

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("msg", "MDF_ERR");
            return "board";//수정하려던내용 보여줘야함
        }
    }


    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto);//insert

            //입력실패시 게시판이아닌 쓰던내용으로 돌아가게하기
            if(rowCnt!=1)
                throw new Exception("Write failed");

            //모델에담아 메시지전달시 지속적으로남아 새로고침시 메시지 계속 발생하므로 1회성을 위해
            //RedirectAttributes객체의 addFlashAttribute사용
            rattr.addFlashAttribute("msg","WRT_OK");

            return "redirect:/board/list";//redirect는 항상 post다. get방식은 없다.
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    @GetMapping("/write")
    public String write(Model m) {
        m.addAttribute("mode","new");

        return "board"; //쓰기.읽기 구분위해 사용. 쓰기는 mode=new
    }

    @PostMapping("/remove")
    //RedirectAttributes객체이용하여 데이터보낼시 1회사용후 소멸. 게시판목록 새로고침시 DEL_OK나DEL_ERR msg가 남아있어 경고창 계속 뜨는거 해소
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        try {
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);

            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt!=1)
                throw new Exception("board remove error");

                rattr.addFlashAttribute("msg", "DEL_OK");
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }

        //model에 page,pageSize담으면 return시 주소뒤에 ?page=&pageSize= 추가해준다.
        //addFlashAttribute사용하여 값전달시 redirect후 값이 소멸
        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno,Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
            //m.addAttribute("boardDto",boardDto); //아래문장과 동일
            m.addAttribute(boardDto);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    @GetMapping("/list")
    public String list(@ModelAttribute SearchCondition sc, Model m,HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        try {
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt",totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph",pageHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}