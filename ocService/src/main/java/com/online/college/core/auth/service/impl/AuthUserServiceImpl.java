package com.online.college.core.auth.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.core.auth.dao.AuthUserDao;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;

@Service
public class AuthUserServiceImpl implements IAuthUserService {

	@Autowired
	private AuthUserDao entityDao;

	public AuthUser getById(Long id) {
		return entityDao.getById(id);
	}

	/**
	 * 根据username获取
	 **/
	public AuthUser getByUsername(String username) {
		return entityDao.getByUsername(username);
	}

	/**
	 * 根据username和password获取
	 **/
	public AuthUser getByUsernameAndPassword(AuthUser authUser) {
		return entityDao.getByUsernameAndPassword(authUser);
	}

	/**
	 * 获取首页推荐5个讲师
	 **/
	public List<AuthUser> queryRecomd() {
		List<AuthUser> recomdList = entityDao.queryRecomd();
		if (CollectionUtils.isNotEmpty(recomdList)) {
			for (AuthUser item : recomdList) {
				if (StringUtils.isNotEmpty(item.getHeader())) {
					item.setHeader(QiniuStorage.getUrl(item.getHeader()));
				}
			}
		}
		return recomdList;
	}

	public TailPage<AuthUser> queryPage(AuthUser queryEntity, TailPage<AuthUser> page) {
		// 此处的queryEntity对象变量并未存取有实际意义的数值，只是为了作为一个形参传入，最终得到t_auth_user表的总记录条数
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		// 查询带限定条件的记录
		List<AuthUser> items = entityDao.queryPage(queryEntity, page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(AuthUser entity) {
		entityDao.create(entity);
	}

	public void update(AuthUser entity) {
		entityDao.update(entity);
	}

	public void updateSelectivity(AuthUser entity) {
		entityDao.updateSelectivity(entity);
	}

	public void delete(AuthUser entity) {
		entityDao.delete(entity);
	}

	public void deleteLogic(AuthUser entity) {
		entityDao.deleteLogic(entity);
	}

	@Override
	public void createSelectivity(AuthUser entity) {
		entityDao.createSelectivity(entity);
	}
}
