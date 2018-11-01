package com.online.college.common.orm.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@SuppressWarnings("rawtypes")
public interface JdbcDao {
	
	public Map<String,Object> getById(@Param("param1")String sql, @Param("param2")Serializable id);
	public List<Map<String,Object>> query(@Param("param1")String sql);
	public Integer count(@Param("param1")String sql);
	public void create(@Param("param1")String sql, @Param("param2")Object obj);
	public Integer createAll(@Param("param1")Map<String,String> map, @Param("param2")Collection entities);
	public Integer update(@Param("param1")String sql, @Param("param2")Object obj);
	public Integer delete(@Param("param1")String sql, @Param("param2")Object obj);
	public Integer deleteById(@Param("param1")String sql, @Param("param2")Serializable id);
	public Integer deleteByIds(@Param("param1")String sql, @Param("param2")Serializable[] ids);
	public Integer deleteByFilter(@Param("param1")String sql);
	
}
