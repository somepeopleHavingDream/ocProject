package com.online.college.core.consts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.dao.ConstsDictionaryDao;
import com.online.college.core.consts.domain.ConstsDictionary;
import com.online.college.core.consts.service.IConstsDictionaryService;


@Service
public class ConstsDictionaryServiceImpl implements IConstsDictionaryService{

	@Autowired
	private ConstsDictionaryDao entityDao;

	public ConstsDictionary getById(Long id){
		return entityDao.getById(id);
	}

	public List<ConstsDictionary> queryAll(ConstsDictionary queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public TailPage<ConstsDictionary> queryPage(ConstsDictionary queryEntity ,TailPage<ConstsDictionary> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<ConstsDictionary> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(ConstsDictionary entity){
		entityDao.create(entity);
	}

	public void update(ConstsDictionary entity){
		entityDao.update(entity);
	}

	public void updateSelectivity(ConstsDictionary entity){
		entityDao.updateSelectivity(entity);
	}

	public void delete(ConstsDictionary entity){
		entityDao.delete(entity);
	}

	public void deleteLogic(ConstsDictionary entity){
		entityDao.deleteLogic(entity);
	}



}

