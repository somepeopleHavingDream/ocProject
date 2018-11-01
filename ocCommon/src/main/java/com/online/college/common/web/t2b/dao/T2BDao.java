package com.online.college.common.web.t2b.dao;

import java.util.List;

import com.online.college.common.web.t2b.T2BVO;

public interface T2BDao {

	public String getCurrentTimestamp();
	public List<String> listTables(T2BVO vo);
	public List<T2BVO> listTableCols(T2BVO vo);
	
}
