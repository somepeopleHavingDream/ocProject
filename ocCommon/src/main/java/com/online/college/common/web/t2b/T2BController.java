package com.online.college.common.web.t2b;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online.college.common.web.t2b.dao.T2BDao;

@Controller
@RequestMapping("/tools/t2b")
public class T2BController {
	
	@Autowired
	private T2BDao t2bDao;
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response){
		try {
			response.getWriter().write("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/init")
	public void init(HttpServletRequest request, HttpServletResponse response){
	 	String workSpace = "D:/workspaceAction/ocProject/ocService/src/main/java/";//改成自己的项目路径
	 	String htmlDir = "";
        try {
            // 创建beans
            T2BVO vo = new T2BVO();
            vo.setPrefix("t_course");// 如果是：T_BASE_ 则对所有以 T_BASE_ 开头的表生成java代码
            List<String> tables = t2bDao.listTables(vo);
            Map<String, String> tableMap = new HashMap<String, String>();// 这里写一个表名 和 业务名对应的map
            for (String table : tables) {
                vo.setTableName(table);
                List<T2BVO> cols = t2bDao.listTableCols(vo);
                TableConvertClass.convertTablesToClass(workSpace, "com.online.college.core", htmlDir, table, cols, tableMap.get(table), false,true);// tableMap.get(table)
            }
            response.getWriter().write("please check console : if exception ,please refresh project and create again");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
