package com.keduit.bpro52.service;



import com.keduit.bpro52.dto.BoardDTO;
import com.keduit.bpro52.dto.PageRequestDTO;
import com.keduit.bpro52.dto.PageResultDTO;
import com.keduit.bpro52.entity.Board;
import com.keduit.bpro52.entity.Member;

public interface BoardService {

	Long register(BoardDTO dto);
	
	PageResultDTO<BoardDTO, Object[]>getList(PageRequestDTO requestDTO);
	
	BoardDTO get(Long bno);
	
	void removeWithReplies(Long bno);
	
	void modify(BoardDTO boardDTO);
	
	default Board dtoEntity(BoardDTO dto) {
		
		Member member = Member.builder().email(dto.getWriterEmail()).build();
		Board board = Board.builder().bno(dto.getBno())
									 .title(dto.getTitle())
									 .content(dto.getContent())
									 .writer(member)									//pk 맴버 테이블에 있는 이메일로 fk되어있기 때문
									 .build();
		
		return board;
	}; // dto -> entity
	
	default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
		
		BoardDTO boardDTO = BoardDTO.builder().bno(board.getBno())
											  .title(board.getTitle())
											  .content(board.getContent())
											  .regDate(board.getRegDate())
											  .updateDate(board.getUpdateDate())
											  .writerEmail(member.getEmail())
											  .writerName(member.getName())
											  .replyCount(replyCount.intValue())
											  .build();
		return boardDTO;
		
	};  // entity -> dto
}
