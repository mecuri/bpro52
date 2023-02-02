package com.keduit.bpro52.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.bpro52.dto.BoardDTO;
import com.keduit.bpro52.dto.PageRequestDTO;
import com.keduit.bpro52.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		
		log.info("list......" + pageRequestDTO);
		model.addAttribute("result", boardService.getList(pageRequestDTO));
	}
	
	@GetMapping("/register")
	public void registerGet() {
		log.info("register...... ");
		
	}
	
	@PostMapping("/register")
	public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		log.info("register Post ..... " + boardDTO);
		Long bno = boardService.register(boardDTO);
		
		log.info("Bno = " + bno);
		redirectAttributes.addFlashAttribute("msg", bno);
		redirectAttributes.addFlashAttribute("writer", boardDTO.getWriterEmail());
		
		return "redirect:/board/list";
	}
	
	@GetMapping({"/read", "/modify"})
	public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
						Long bno, Model model) {
		
		log.info("bno : " + bno);
		BoardDTO boardDTO = boardService.get(bno);
		
		log.info(boardDTO);
		model.addAttribute("dto", boardDTO);
	}
	
	@PostMapping("/remove")
	public String remove(long bno, RedirectAttributes redirectAttributes) {
		
		log.info("bno : ", bno);
		boardService.removeWithReplies(bno);
		redirectAttributes.addFlashAttribute("msg", bno);
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String modify(BoardDTO dto, 
			@ModelAttribute("requestDTO") PageRequestDTO requestDTO,
			RedirectAttributes redirectAttributes) {
		
		log.info("----------------------------------");
		log.info("dto : " + dto);
		
		boardService.modify(dto);
		
		redirectAttributes.addAttribute("page", requestDTO.getPage());
		redirectAttributes.addAttribute("type", requestDTO.getType());
		redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
		redirectAttributes.addAttribute("bno", dto.getBno());
		
		return "redirect:/board/read";
	}
	
}
