package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	// 게시물 목록
	public List<Board> listBoard(){
		List<Board> list = mapper.selectAll();
		return list;
	}

	// 게시글 눌렀을때 페이지 뜨게
	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	// 수정이 잘되었으면 1 
	public boolean modify(Board board) {
		int cnt = mapper.update(board);
		
		return cnt ==1;
		
	}

	// 게시글 삭제
	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt==1;
	}

	// 게시글 추가
	public boolean addBoard(Board board) {
		int cnt = mapper.insert(board);
		return cnt==1;
	}
	
	
}
