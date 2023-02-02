package com.keduit.bpro52.repository.search;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.keduit.bpro52.entity.Board;
import com.keduit.bpro52.entity.Pr_table;
import com.keduit.bpro52.entity.QBoard;
import com.keduit.bpro52.entity.QMember;
import com.keduit.bpro52.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

	public SearchBoardRepositoryImpl() {
		super(Pr_table.class);
	}

	@Override
	public Pr_table search1() {
		log.info("search1-----");
		
		return null;
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		log.info("-----------");
		
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		QMember member = QMember.member;
		
		JPQLQuery<Board> jpqlQuery = from(board);
		jpqlQuery.leftJoin(member).on(board.writer.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
		
//		jpqlQuery.select(board, member.email, reply.count())
//				 .groupBy(board);
//		jpqlQuery.select(board).where(board.bno.eq(2L));
//		JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
		JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = board.bno.gt(0L); // board의 bno가 0L보다 클 때
		
		booleanBuilder.and(expression);
		
		if(type != null) {
			String[] typeArr = type.split("");
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			for(String t : typeArr) {
				switch(t) {
				case "t" : 
					conditionBuilder.or(board.title.contains(keyword));
					break;
				case "c" : 
					conditionBuilder.or(board.content.contains(keyword));
					break;
				case "w" : 
					conditionBuilder.or(member.email.contains(keyword));
					break;
				}
			}
			
			booleanBuilder.and(conditionBuilder);
		}
		tuple.where(booleanBuilder);
		
		// sort 추가
//		tuple.orderBy(board.bno.desc());
		Sort sort = pageable.getSort();
		
		sort.stream().forEach(order -> {
			Order derection = order.isAscending() ? Order.ASC : Order.DESC; // 정렬 기준 조건 넣어주기
			String prop = order.getProperty();
			
			PathBuilder orderByExpression = new PathBuilder<>(Board.class, "board");
			
			tuple.orderBy(new OrderSpecifier<>(derection, orderByExpression.get(prop)));
		});
		
		tuple.groupBy(board);
		
		// 페이지 처리
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());
		
		log.info("--------------------");
		log.info("tuple : " + tuple);
		log.info("--------------------");
		
//		List<Board> result = jpqlQuery.fetch();
		List<Tuple> result = tuple.fetch();
		
		log.info("result : " + result);
		
		long count = tuple.fetchCount();
		log.info("count : " + count);
		
		return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
				pageable, count);
	}

}
