package com.online.college.opt.business.impl;

import com.online.college.core.consts.CourseEnum;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;
import com.online.college.opt.business.ICourseBusiness;
import com.online.college.opt.vo.CourseSectionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程业务层
 */
@Service
public class CourseBusinessImpl implements ICourseBusiness {

    private final ICourseSectionService courseSectionService;

    @Autowired
    public CourseBusinessImpl(ICourseSectionService courseSectionService) {
        this.courseSectionService = courseSectionService;
    }

    /**
     * 获取课程章节
     */
    public List<CourseSectionVO> queryCourseSection(Long courseId) {
        CourseSection queryEntity = new CourseSection();
        queryEntity.setCourseId(courseId);
        queryEntity.setOnsale(CourseEnum.ONSALE.value());//上架

        Map<Long, CourseSectionVO> tmpMap = new LinkedHashMap<>();
        for (CourseSection item : courseSectionService.queryAll(queryEntity)) {
            if (Long.valueOf(0).equals(item.getParentId())) {//章
                CourseSectionVO vo = new CourseSectionVO();
                BeanUtils.copyProperties(item, vo);
                tmpMap.put(vo.getId(), vo);
            } else {
                tmpMap.get(item.getParentId()).getSections().add(item);//小节添加到大章中
            }
        }
        return new ArrayList<>(tmpMap.values());
    }

}
