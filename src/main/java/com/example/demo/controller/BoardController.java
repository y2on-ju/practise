package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Board;
import com.example.demo.service.BoardService;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@Controller
@RequestMapping("/")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	
	//게시물 목록 
	@GetMapping({"/", "list"})
	public String list(Model model) {
		// 1. request param 수집/가공
		// 2. business logic 처리(서비스에 일시킴)
		List<Board> list = service.listBoard();
		//3. add attribute
		model.addAttribute("boardList", list);
		// 4. forward/redirect
		return "list";
	}
	
	// 게시글 눌렀을때 페이지 뜨게
	@GetMapping("/id/{id}")
	public String board(@PathVariable("id") Integer id, Model model) {
		Board board = service.getBoard(id);
		model.addAttribute("board", board);
		return "get";
		
	}
	
	// 게시글 수정하기 눌렀을때 수정폼 보여주기
	@GetMapping("/modify/{id}")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getBoard(id));
		return "modify";
		
	}
	
	// 수정폼에서 수정 눌렀을때 수정되게
	@PostMapping("/modify/{id}")
	public String modifyProcess(Board board, RedirectAttributes rttr) {
		boolean ok = service.modify(board);
		
		if(ok) {
			// 수정 잘 되었으면 해당 게시물 보기로 리디렉션
			rttr.addAttribute("success", "success");
			return "redirect:/id/" + board.getId();
		} else {
			// 수정 잘 안되었으면 수정폼으로 리디렉션
			rttr.addAttribute("fail", "fail");
			return "redirect:/modify/" + board.getId();
		}
	}
	// 게시물 삭제
	@PostMapping("remove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if(ok){
			rttr.addAttribute("success", "remove");
			return "redirect:/list";
		} else {
			return "redirect:/id/" +id;
		}
	}
	
	@GetMapping("add")
	public void addForm() {
		//게시물 작성  form(view)로 포워드
	}
	
	@PostMapping("add")
	public String addProcess(Board board, RedirectAttributes rttr) {
		// 새 게시물 db에 추가
		
		boolean ok = service.addBoard(board);
		if (ok) {
			return "redirect:/list";
			
		} else {
			rttr.addFlashAttribute("board",board);
			return "redirect:/add";
		}
	}
	

}
