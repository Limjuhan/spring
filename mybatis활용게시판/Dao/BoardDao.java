package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDao {
    @Autowired SqlSession session;
    String namespace="com.fastcampus.ch4.dao.BoardMapper.";

    BoardDto select(int bno) throws Exception{
        return session.selectOne(namespace+"select",bno);
    }

    int delete(int bno, String writer) {
        Map map = new HashMap<>();
        map.put("bno",bno);
        map.put("writer",writer);

        return session.delete(namespace+"delte",map);
    }

    int insert (BoardDto boardDto) {
        return session.insert(namespace+"insert",boardDto);
    }

    public List<BoardDto> selectAll () {
        return session.selectList(namespace+"selectAll");
    }

    public int update(BoardDto boardDto) {
        return session.update(namespace+"update",boardDto);
    }

    public int updateCommentCnt(int comment_cnt, int bno) {
        Map map = new HashMap();
        map.put("cnt",comment_cnt);
        map.put("bno",bno);
        return session.update(namespace+"updateCommentCnt",map);
    }


}
