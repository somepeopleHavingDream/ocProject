package com.online.college.common.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.college.common.orm.dao.JdbcDao;
import com.online.college.common.util.BeanUtil;

@Service
public class BaseDao {
	private static Logger log = Logger.getLogger(BaseDao.class); // 日志
	
	@Autowired
	private JdbcDao jdbcDao;
	
	/**
     * 单体查询
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public <T> T getById(Class<T> entityClass, Serializable id, String... fieldNames){
    	String sql = MyBatisJdbcHelper.getByIdSql(entityClass, fieldNames);
    	Map<String,Object> resultMap = jdbcDao.getById(sql,id);
    	return BeanUtil.mapToBean(entityClass, resultMap);
    }
	
    /**
     * 获得所有
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> List<T> queryAll(Class<T> entityClass, String... fieldNames){
    	return this.query(entityClass, null, fieldNames);
    }
    
    /**
     * 求列表，只支持简单的条件查询；条件个数 <=3 个
     * @param entityClass
     * @param filter
     * @param fieldNames
     * @param <T>
     * @return
     */
    public <T> List<T> query(Class<T> entityClass, QueryFilter filter, String... fieldNames){
    	String sql = MyBatisJdbcHelper.querySql(entityClass, filter, fieldNames);
    	List<Map<String,Object>> mapList = jdbcDao.query(sql);
    	return BeanUtil.mapListToBeanList(entityClass, mapList);
    }
    
    
    /**
     * 查询一个实体
     * @param entityClass
     * @param filter
     * @param fieldNames
     * @param <T>
     * @return
     */
    public <T> T queryEntity(Class<T> entityClass, QueryFilter filter, String... fieldNames){
    	List<T> list = this.query(entityClass, filter, fieldNames);
    	if(list != null && list.size() == 1){
    		return list.get(0);
    	}else{
    		log.error("查询到多个实体！");
    		return null;
    	}
    }
    
    /**
     * 根据id查询
     * @param <T>
     * @param <E>
     * @param entityClass
     * @param ids
     * @param fieldNames
     */
    public <T, E extends Serializable> List<T> queryByIds(Class<T> entityClass, E[] ids, String... fieldNames){
    	if(ids != null && ids.length < 100){
    		String sql = MyBatisJdbcHelper.queryByIdsSql(entityClass, ids, fieldNames);
        	List<Map<String,Object>> mapList = jdbcDao.query(sql);
        	return BeanUtil.mapListToBeanList(entityClass, mapList);
		}else{
			log.error("ids的个数最多为100个");
    		return null;
		}
    }
    
    /**
     * 获取数量
     * @param <T>
     * @param entityClass
     */
    public <T> int count(Class<T> entityClass, QueryFilter filter){
    	String sql = MyBatisJdbcHelper.countSql(entityClass, filter);
    	return jdbcDao.count(sql).intValue();
    }
    
	/**
	 * 保存实体
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T> T create(T entity){
		String sql = MyBatisJdbcHelper.createSql(entity.getClass());
    	jdbcDao.create(sql ,entity);
    	return entity;
	}
	
	/**
     * 批量插入
     * @param entities
     * @param <T>
     * @return
     */
    public <T> int createAll(Collection<T> entities){
    	Iterator<T> it = entities.iterator();
    	Map<String,String> map = MyBatisJdbcHelper.createAllSqlMap(it.next().getClass());
    	return jdbcDao.createAll(map, entities);
    }
    
    /**
     * 更新
     * @param <T>
     * @param entity
     */
    public <T> int updateEntity(T entity, String... fieldNames){
    	String sql = MyBatisJdbcHelper.updateSql(entity.getClass(),true,null,fieldNames);
    	return jdbcDao.update(sql ,entity);
    }
    
    /**
     * 更新
     * @param <T>
     * @param entity
     */
    public <T> int update(T entity, QueryFilter filter ,String... fieldNames){
    	String sql = MyBatisJdbcHelper.updateSql(entity.getClass(),false,filter,fieldNames);
    	return jdbcDao.update(sql ,entity);
    }
    
    /**
     * 删除一个实体
     * @param entity
     * @param <T>
     * @return
     */
    public <T> boolean delete(T entity){
    	String sql = MyBatisJdbcHelper.deleteSql(entity.getClass(),true,null);
    	Integer rst = jdbcDao.delete(sql ,entity);
    	return rst > 0;
    }
    
    /**
     * 根据id删除一个实体
     * @param entityClass
     * @param id
     */
    public <T> boolean deleteById(Class<T> entityClass, Serializable id){
    	String sql = MyBatisJdbcHelper.deleteByIdSql(entityClass);
    	Integer rst = jdbcDao.deleteById(sql ,id);
    	return rst > 0;
    }
    
    /**
     * 根据id删除一组实体
     * 不会使用JPA事件回调
     * @param entityClass
     * @param ids
     * @param <T>
     * @return
     */
    public <T> int deleteByIds(Class<T> entityClass, Serializable[] ids){
    	String sql = MyBatisJdbcHelper.deleteByIdSqls(entityClass);
    	return jdbcDao.deleteByIds(sql ,ids);
    }
    
    /**
     * 删除
     * @param entity
     * @param <T>
     * @return
     */
    public <T> boolean deleteByFilter(Class<T> entityClass,QueryFilter filter){
    	String sql = MyBatisJdbcHelper.deleteByFilterSql(entityClass,filter);
    	Integer rst = jdbcDao.deleteByFilter(sql);
    	return rst > 0;
    }
	
}

