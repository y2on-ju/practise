package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	// 게시글 목록 
	@Select("""
			SELECT
			id,
			title,
			writer,
			inserted
			FROM Board
			ORDER BY id DESC
			""")
	List<Board> selectAll();

	// 게시글 눌렀을때 페이지 뜨게
	@Select("""
			SELECT * 
			FROM Board
			WHERE id = #{id}
			""")
	Board selectById(Integer id);

	// 게시글 수정
	@Update("""
			UPDATE Board
			SET
				title = #{title},
				body = #{body},
				writer = #{writer}
			WHERE
				id = #{id}
				
			""")
	int update(Board board);

	// 게시글 삭제
	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Insert("""
			INSERT INTO Board (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);
	
	

}
