package com.keduit.bpro52.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keduit.bpro52.entity.Pr_table;

public interface SearchBoardRepository {
	
	Pr_table search1();
	
	Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
