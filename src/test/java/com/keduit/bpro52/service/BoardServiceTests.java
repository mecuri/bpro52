package com.keduit.bpro52.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keduit.bpro52.dto.BoardDTO;
import com.keduit.bpro52.dto.PageRequestDTO;
import com.keduit.bpro52.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

	@Autowired
	public BoardService boardService;

	@Test
	public void testRegister() {
		BoardDTO dto = BoardDTO.builder().title("제목...")
										 .content("내용....")
										 .writerEmail("user13@abc.com").build();
		Long bno = boardService.register(dto);
		
		System.out.println(bno + " : " + dto);
	}
	
	@Test
	public void testList() {
		PageRequestDTO pageRequestDTO = new PageRequestDTO();
		
		PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
		
		for(BoardDTO boardDTO : result.getDtoList()) {
			System.out.println(boardDTO);
		}
	}
	@Test
	public void testGet() {
		Long bno = 1L;
		BoardDTO boardDTO = boardService.get(bno);
		
		System.out.println(boardDTO);
	}
	@Test
	public void testRemove() {
		Long bno = 1L;
		
		boardService.removeWithReplies(bno);
	}
	@Test
	public void testModify() {
		Long bno = 2L;
		BoardDTO boardDTO = BoardDTO.builder().bno(bno)
											  .title("제목 변경하기")
											  .content("내용 변경하기")
											  .build();
		boardService.modify(boardDTO);
	}

}
