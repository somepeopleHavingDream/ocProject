package com.online.college.core.consts.service;

import java.util.List;
import com.online.college.common.page.TailPage;
import com.online.college.core.consts.domain.ConstsDictionary;


public interface IConstsDictionaryService {

	/**
	*根据id获取
	**/
	public ConstsDictionary getById(Long id);

	/**
	*获取所有
	**/
	public List<ConstsDictionary> queryAll(ConstsDictionary queryEntity);

	/**
	*分页获取
	**/
	public TailPage<ConstsDictionary> queryPage(ConstsDictionary queryEntity ,TailPage<ConstsDictionary> page);

	/**
	*创建
	**/
	public void create(ConstsDictionary entity);

	/**
	*根据id更新
	**/
	public void update(ConstsDictionary entity);

	/**
	*根据id 进行可选性更新
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

