package com.online.college.core.course.service.impl;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.course.dao.CourseDao;
import com.online.college.core.course.dao.CourseSectionDao;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程服务实现类
 *
 * @author yx
 * @createtime 2019/04/20 18:02
 */
@Service
public class CourseServiceImpl implements ICourseService{
	private final CourseDao entityDao;
	private final CourseSectionDao courseSectionDao;

	@Autowired
	public CourseServiceImpl(CourseDao entityDao, CourseSectionDao courseSectionDao) {
		this.entityDao = entityDao;
		this.courseSectionDao = courseSectionDao;
	}

	/**
	 * 准备课程图片
	 * @param course 课程对象
	 */
	private void prepareCoursePicture(Course course){
		if(null != course && StringUtils.isNotEmpty(course.getPicture())){
			course.setPicture(QiniuStorage.getUrl(course.getPicture()));
		}
	}

	/**
	 * 通过Id得到课程对象
	 */
	@Override
	public Course getById(Long id){
		Course course = entityDao.getById(id);
		prepareCoursePicture(course);
		return course;
	}

	@Override
	public List<Course> queryList(CourseQueryDto queryEntity){
		if(null == queryEntity.getOnsale()){//是否上架
			queryEntity.setOnsale(CourseEnum.ONSALE.value());
		}
		return entityDao.queryList(queryEntity);
	}

	@Override
	public TailPage<Course> queryPage(Course queryEntity ,TailPage<Course> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<Course> items = entityDao.queryPage(queryEntity, page);	// 到这里之前，分页对象TailPage一直用的是它所继承抽象类AbstractPage的代码，没有使用到自己的业务逻辑
		if(CollectionUtils.isNotEmpty(items)){
			for(Course item : items){
				prepareCoursePicture(item);
			}
		}
		page.setItemsTotalCount(itemsTotalCount);	// 这个地方用到了TailPage类的代码
		page.setItems(items);
		return page;
	}

	@Override
	public void createSelectivity(Course entity){
		entityDao.createSelectivity(entity);
	}

	@Override
	public void updateSelectivity(Course entity){
		entityDao.updateSelectivity(entity);
	}

	//物理删除
	@Override
	public void delete(Course entity){
		entityDao.delete(entity);
		
		//删除课程对应的章节
		CourseSection courseSection = new CourseSection();
		courseSection.setCourseId(entity.getId());
		courseSectionDao.deleteByCourseId(courseSection);
	}
	
	//逻辑删除
	@Override
	public void deleteLogic(Course entity){
		entityDao.deleteLogic(entity);
		
		//删除课程对应的章节
		CourseSection courseSection = new CourseSection();
		courseSection.setCourseId(entity.getId());
		courseSectionDao.deleteLogicByCourseId(courseSection);
	}

}


