package com.keduit.bpro52.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keduit.bpro52.entity.Member;

/** JpaRepository => entity인 member와 id타입인 string*/
public interface MemberRepository extends JpaRepository<Member, String>{

}
