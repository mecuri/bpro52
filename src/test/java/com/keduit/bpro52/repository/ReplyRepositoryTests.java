package com.keduit.bpro52.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keduit.bpro52.entity.Board;
import com.keduit.bpro52.entity.Pr_table;
import com.keduit.bpro52.entity.Reply;

@SpringBootTest
public class ReplyRepositoryTests {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Test
	public void insertReply() {
		
		IntStream.rangeClosed(1, 300).forEach(i -> {
			
			Long bno = (long)(Math.random()*100 )+1;
			Board board = Board.builder().bno(bno).build();
			
			Reply reply = Reply.builder().text(i+"번째 댓글 내용")
										 .board(board)
										 .replyer("guest " + i)
										 .build();
			replyRepository.save(reply);
		});
		
	}
	@Test
	@Transactional
	public void testReadReply() {
		Optional<Reply> result = replyRepository.findById(1L);
		
		Reply reply = result.get();
		
		System.out.println(reply);
		System.out.println(reply.getBoard());
		
	}
	
	@Test
	public void testListByBoard() {
		List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
					Board.builder().bno(97L).build()
				);
		
		replyList.forEach(reply -> System.out.println(reply));
	}
	
}
