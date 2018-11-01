package com.online.college.core.course.service.impl;

import java.util.List;
import com.online.college.common.page.TailPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.online.college.core.course.domain.CourseComment;
import com.online.college.core.course.service.ICourseCommentService;
import com.online.college.core.course.dao.CourseCommentDao;


@Service
public class CourseCommentServiceImpl implements ICourseCommentService{

	@Autowired
	private CourseCommentDao entityDao;

	@Override
	public CourseComment getById(Long id){
		return entityDao.getById(id);
	}

	@Override
	public List<CourseComment> queryAll(CourseComment queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	@Override
	public TailPage<CourseComment> queryPage(CourseComment queryEntity ,TailPage<CourseComment> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<CourseComment> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	@Override
	public void create(CourseComment entity){
		entityDao.create(entity);
	}

	@Override
	public void update(CourseComment entity){
		entityDao.update(entity);
	}

	@Override
	public void updateSelectivity(CourseComment entity){
		entityDao.updateSelectivity(entity);
	}

	@Override
	public void delete(CourseComment entity){
		entityDao.delete(entity);
	}

	@Override
	public void deleteLogic(CourseComment entity){
		entityDao.deleteLogic(entity);
	}



}

