package com.online.college.core.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.dao.CourseSectionDao;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;


@Service
public class CourseSectionServiceImpl implements ICourseSectionService{

	@Autowired
	private CourseSectionDao entityDao;

	public CourseSection getById(Long id){
		return entityDao.getById(id);
	}

	public List<CourseSection> queryAll(CourseSection queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public TailPage<CourseSection> queryPage(CourseSection queryEntity ,TailPage<CourseSection> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<CourseSection> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(CourseSection entity){
		entityDao.create(entity);
	}

	public void update(CourseSection entity){
		entityDao.update(entity);
	}

	public void updateSelectivity(CourseSection entity){
		entityDao.updateSelectivity(entity);
	}

	public void delete(CourseSection entity){
		entityDao.delete(entity);
	}

	public void deleteLogic(CourseSection entity){
		entityDao.deleteLogic(entity);
	}



}

