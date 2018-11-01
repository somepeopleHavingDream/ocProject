package com.online.college.core.consts.dao;

import java.util.List;
import com.online.college.common.page.TailPage;
import com.online.college.core.consts.domain.ConstsDictionary;


public interface ConstsDictionaryDao {

	/**
	*根据id获取
	**/
	public ConstsDictionary getById(Long id);

	/**
	*获取所有
	**/
	public List<ConstsDictionary> queryAll(ConstsDictionary queryEntity);

	/**
	*获取总数量
	**/
	public Integer getTotalItemsCount(ConstsDictionary queryEntity);

	/**
	*分页获取
	**/
	public List<ConstsDictionary> queryPage(ConstsDictionary queryEntity , TailPage<ConstsDictionary> page);

	/**
	*创建新记录
	**/
	public void create(ConstsDictionary entity);

	/**
	*根据id更新
	**/
	public void update(ConstsDictionary entity);

	/**
	*根据id选择性更新自动
	**/
	public void updateSelectivity(ConstsDictionary entity);

	/**
	*物理删除
	**/
	public void delete(ConstsDictionary entity);

	/**
	*逻辑删除
	**/
	public void deleteLogic(ConstsDictionary entity);



}

