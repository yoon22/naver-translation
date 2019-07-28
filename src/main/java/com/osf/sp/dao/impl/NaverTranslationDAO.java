package com.osf.sp.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class NaverTranslationDAO {
	
	@Resource
	private SqlSession ss;
	
	public List<Map<String,Object>> selectTranslationHisList(){
		return ss.selectList("com.osf.sp.mapper.NaverTranslationMapper.selectList");
	}
	
	public Map<String, Object> selectTranslationHisOne(Map<String,Object> param){
		return ss.selectOne("com.osf.sp.mapper.NaverTranslationMapper.selectListOne",param);
	}
	
	public Integer insertOne(Map<String,Object> param) {
		return ss.insert("com.osf.sp.mapper.NaverTranslationMapper.insert",param);
	}
	
	public Integer updateOne(Map<String,Object> param) {
		return ss.update("com.osf.sp.mapper.NaverTranslationMapper.update",param);
	}
	//검색순위
	public List<Map<String,Object>> selectOrderByCount(){
		return ss.selectList("com.osf.sp.mapper.NaverTranslationMapper.selectOrderByCount");
	}
	
	
}
