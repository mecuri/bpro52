package com.keduit.bpro52.repository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.keduit.bpro52.entity.Board;
import com.keduit.bpro52.entity.Member;
import com.keduit.bpro52.entity.QBoard;
import com.keduit.bpro52.entity.QReply;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;



@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void insertMember() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder().email("user" + i + "@abc.com").build();
			
			Board board = Board.builder().title("제목 " + i)
										 .content(i + "번째 내용 입니다.")
										 .writer(member)
										 .build();
			boardRepository.save(board);
		});
		
	}
	
	@Test
	@Transactional											//lazy시 outer join을 안넣는 대신 만약에 필요하다면 그 다음에 추가적으로 다시 검색
	public void testRead() {
		Optional<Board> result = boardRepository.findById(100L);
		
		Board board = result.get();
		
		System.out.println(board);
		System.out.println(board.getWriter());
		
		
	}
	@Test
	public void testReadWithWriter() {
		Object result = boardRepository.getBoardWithWriter(100L);
		Object[] arr = (Object[])result;
		
		System.out.println("------------------------------------");
		System.out.println(Arrays.toString(arr));
	}
	
	@Test
	public void testGetBoardWithReply() {
		List<Object[]> result = boardRepository.getBoardWithReply(100L);
		
		for(Object[] arr : result) {
			System.out.println(Arrays.toString(arr));
		}
	}
	@Test
	public void testWithReplyCount() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		
		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
		
		result.get().forEach(row -> {
			Object[] arr = (Object[]) row;
			
			System.out.println(Arrays.toString(arr));
		});
	}
	
	@Test
	public void testgetBoardByBno() {
				
		Object result = boardRepository.getBoardByBno(47L);
		
		Object[] arr = (Object[]) result;
		
		System.out.println(Arrays.toString(arr));
	}
	
	@Test
	public void testSearch1() {
		boardRepository.search1();
	}
	
	@Test
	public void testSearchPage() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		Page<Object[]> result = boardRepository.searchPage("tcw", "1", pageable); 

	}
	
	@Test
	public void testSearchPageSort() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
		Page<Object[]> result = boardRepository.searchPage("tcw", "1", pageable); 

	}
	
	
}
