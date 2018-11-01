package com.online.college.common.web.t2b;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.online.college.common.util.BeanField;
import com.online.college.common.util.BeanUtil;
import com.online.college.common.util.CommonUtil;

/**
 * 针对 SpringMVC、MyBatis
 */

public class TableConvertClass {
	
	
	public static String getJdbcType(String key ,Field field){
		String jdbcType = "VARCHAR";
		Class<?> classType = field.getType();
		if(Integer.class.equals(classType) || Long.class.equals(classType)){
			if("del".equals(key)){
				jdbcType="TINYINT";
			}else{
				jdbcType="INTEGER";
			}
		}else if(String.class.equals(classType)){
			jdbcType="VARCHAR";
		}else if(java.util.Date.class.equals(classType)){
			if("updateTime".equals(key)){
				jdbcType="TIMESTAMP";
			}else{
				jdbcType="DATE";
			}
		}else if(java.math.BigDecimal.class.equals(classType)){
			jdbcType="DECIMAL";
		}
		return jdbcType;
	}
	
	public static String converResult(String packageAndClass ,Class<?> clazz) throws NoSuchFieldException, SecurityException{
		Map<String, BeanField> map = BeanUtil.getAllFields(clazz);
        StringBuilder sb = new StringBuilder("	<resultMap id=\"BeanResultMap\" type=\""+packageAndClass+"\" >\n");
        sb.append("		<id column=\"id\" property=\"id\" jdbcType=\"INTEGER\"/>\n");
        for(String key : map.keySet()){
        	if("id".equals(key)){
        	}else{
        		sb.append("		<result column=\""+map.get(key).getColumnName()+"\" property=\""+key+"\" ");
        		
        		Field field=map.get(key).getField();
        		String jdbcType = TableConvertClass.getJdbcType(key,field);
        		sb.append(" jdbcType=\""+jdbcType+"\" />\n");
        	}
        }
        sb.append("	</resultMap> \n");
        return sb.toString();
	}
	
	
	public static String converBaseColumnList(Class<?> clazz){
		Map<String, String> map = BeanUtil.getAllFieldColumns(clazz);
        StringBuilder sb = new StringBuilder("	<sql id=\"All_Columns\">\n");
        Object[] colNames = map.values().toArray();
        sb.append("		" + join(colNames) + " \n");
        sb.append("	</sql>");
        return sb.toString();
	} 

    public static String insertSql(String tableName, Class<?> clazz) {
        Map<String, String> map = BeanUtil.getAllFieldColumns(clazz);
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + "\n");
        Object[] filedNames = map.keySet().toArray();
        Object[] colNames = map.values().toArray();
        sb.append("		( " + join(colNames).toUpperCase() + " ) \n");
        sb.append("		VALUES \n");
        sb.append("		( " + join2(filedNames,clazz) + " ) ");
        return sb.toString();
    }
    
    public static String insertSelectivitySql(String tableName, Class<?> clazz) throws NoSuchFieldException, SecurityException {
        Map<String, BeanField> map = BeanUtil.getAllFields(clazz);
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + "\n");
        sb.append("		<trim prefix=\"(\" suffix=\")\"  suffixOverrides=\",\" >\n");
        for(String key : map.keySet()){
        	if("id".equals(key)){
        	}else{
        		sb.append("			<if test=\""+key+" != null \">\n");
				sb.append("			"+map.get(key).getColumnName().toUpperCase()+",\n");
				sb.append("			</if>\n");
        	}
        }
        sb.append("		</trim>\n");
        
        sb.append("		VALUES\n");
        sb.append("		<trim prefix=\"(\" suffix=\")\"  suffixOverrides=\",\" >\n");
        for(String key : map.keySet()){
        	if("id".equals(key)){
        	}else{
        		Field field=map.get(key).getField();
        		String jdbcType = TableConvertClass.getJdbcType(key,field);
        		
        		sb.append("			<if test=\""+key+" != null \">\n");
				sb.append("			#{"+key+", jdbcType="+jdbcType+"},\n");
				sb.append("			</if>\n");
        	}
        }
        sb.append("		</trim>");
        return sb.toString();
    }
    
    public static String updateSelectivity(String tableName, Class<?> clazz) throws NoSuchFieldException, SecurityException {
        Map<String, BeanField> map = BeanUtil.getAllFields(clazz);
        StringBuilder sb = new StringBuilder("UPDATE " + tableName + "\n");
        sb.append("		<trim prefix=\"SET\" suffixOverrides=\",\" >\n");
        for(String key : map.keySet()){
        	if("id".equals(key) || "createTime".equalsIgnoreCase(key) || "createUser".equalsIgnoreCase(key)){
        		
        	}else{
        		Field field=map.get(key).getField();
        		String jdbcType = TableConvertClass.getJdbcType(key,field);
        		
        		sb.append("			<if test=\""+key+" != null \">\n");
				sb.append("			"+map.get(key).getColumnName().toUpperCase()+" = #{"+key+", jdbcType="+jdbcType+"},\n");
				sb.append("			</if>\n");
        	}
        }
        sb.append("		</trim>\n");
        sb.append("		WHERE ID = #{id, jdbcType = INTEGER}\n");
        return sb.toString();
    }

    public static String selectSql(String tableName, Class<?> clazz) {
        Map<String, String> map = BeanUtil.getAllFieldColumns(clazz);
        StringBuilder sb = new StringBuilder("SELECT ");
        Object[] colNames = map.values().toArray();
        sb.append(join(colNames).toUpperCase() + "\n		FROM " + tableName);
        return sb.toString();
    }
    
    public static String updateSql(String tableName, Class<?> clazz) {
        Map<String, BeanField> map = BeanUtil.getAllFields(clazz);
        StringBuilder sb = new StringBuilder("UPDATE " + tableName + " SET ");
        for (String s : map.keySet()) {
            if (!"id".equals(s) && !"createUser".equalsIgnoreCase(s) && !"createTime".equalsIgnoreCase(s) && !"del".equalsIgnoreCase(s)) {
            	Field field=map.get(s).getField();
        		String jdbcType = TableConvertClass.getJdbcType(s,field);
            	sb.append("\n		"+map.get(s).getColumnName().toString().toUpperCase() + " = #{" + s + ", jdbcType="+jdbcType+"},");
            }
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1) + "\n		WHERE ID = #{id, jdbcType=INTEGER} ";
    }
    
    public static String join(Object[] arr) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Object s : arr) {
            sb.append(s + ", ");
            count ++;
            if(count%6 == 0){
            	sb.append("\n");
            	sb.append("		");
            }
        }
        String str = sb.toString().trim();
        return str.substring(0, str.length() - 1);
    }

    public static String join2(Object[] arr,Class<?> clazz) {
    	Map<String, BeanField> map = BeanUtil.getAllFields(clazz);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Object s : arr) {
        	Field field=map.get(s).getField();
    		String jdbcType = TableConvertClass.getJdbcType(s.toString(),field);
    		
            sb.append("#{" + s + ", jdbcType="+jdbcType+"}, ");
            count ++;
            if(count%6 == 0){
            	sb.append("\n");
            	sb.append("		");
            }
        }
        String str = sb.toString().trim();
        return str.substring(0, str.length() - 1);
    }

    public static void sqls(String tableName, Class<?> clazz) {
        String insertSql = TableConvertClass.insertSql(tableName, clazz);
        System.out.println("##insertSql = \n " + insertSql);

        String selectSql = TableConvertClass.selectSql(tableName, clazz);
        System.out.println("##selectSql = \n " + selectSql);

        String updateSql = TableConvertClass.updateSql(tableName, clazz);
        System.out.println("##updateSql = \n " + updateSql);

    }

    public static void getJavaClassProperty(String filepath) {
        StringBuilder sb = new StringBuilder("");

        File file = new File(filepath);
        try {
            BufferedReader bw = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bw.readLine()) != null) {
                sb.append("	private String " + getColumnFieldProperty(line).trim() + ";\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sb);
    }

    public static String getColumnFieldProperty(String line) {
        line = line.toLowerCase();
        Integer endIndex = 0;
        if (line.contains("nvarchar")) {
            endIndex = line.indexOf("nvarchar");
            return BeanUtil.columnToField(line.substring(0, endIndex));
        } else if (line.contains("varchar")) {
            endIndex = line.indexOf("varchar");
            return BeanUtil.columnToField(line.substring(0, endIndex));
        } else if (line.contains("date")) {
            endIndex = line.indexOf("date");
            return BeanUtil.columnToField(line.substring(0, endIndex));
        } else if (line.contains("char")) {
            endIndex = line.indexOf("char");
            return BeanUtil.columnToField(line.substring(0, endIndex));
        } else if (line.contains("decimal")) {
            endIndex = line.indexOf("decimal");
            return BeanUtil.columnToField(line.substring(0, endIndex));
        }
        return "";
    }

    /**
     * 针对maven src/main/java|resources 如果后台报异常（由于class.forname引起），请刷新项目，再跑一次；
     * @param projectDir 项目的绝对路径 如 : D:/workspace/amp/amp2/amp.web
     * @param packageName 项目的package 名称 如： com.yum.amp2.web
     * @param html 路径
     * @param tableName 表名，
     * @param cols 表对应的列
     * @param myClassName 自己的className，domain 和业务表对应，其他的如果 myClassName=null，类名和业务对应；否则，是myClassName 如 JC_010
     * @param onlydomain 是否只生成 domain class ，如果是：api，service，controller，dao，mapper 都不生成
     */
    public static void convertTablesToClass(String projectDir, String packageName, String htmlDir, String tableName, List<T2BVO> cols, String myClassName,
            boolean onlydomain,boolean withModule) {
    	
    	String tmpTableName = "";
        String moduleName = "";
        String tableClassName = "";
    	System.out.println("### tableName = " + tableName);
    	if(tableName.toUpperCase().indexOf("T_") > -1){
    		tmpTableName = tableName.substring(2);
    		if(tmpTableName.indexOf("_") > -1){
    			moduleName = tmpTableName.substring(0, tmpTableName.indexOf("_")).toLowerCase();
                tableClassName = tmpTableName.substring(tmpTableName.indexOf("_") + 1).toLowerCase();
    		}else{
    			moduleName = tmpTableName.toLowerCase();
                tableClassName = tmpTableName.toLowerCase();
    		}
            tableClassName = BeanUtil.columnToField2(tableClassName);
    	}else{
    		tmpTableName = tableName.substring(tableName.indexOf("_")+1);
            moduleName = tableName.substring(0, tableName.indexOf("_")).toLowerCase();
            tableClassName = BeanUtil.columnToField2(tmpTableName);
    	}
        
        String className = myClassName;
        if (StringUtils.isEmpty(myClassName)) {
            className = tableClassName;
        }
        String packagePath = packageName.replace(".", "/");
        
        String packageFilePath = projectDir + "/" + packagePath + "/" + moduleName + "/";
        String daoFilePath = projectDir + "/" + packagePath + "/" + moduleName + "/";

        File packageFile = new File(packageFilePath);
        File daoFile = new File(daoFilePath);
        try {
            if (!packageFile.exists()) {// 创建class文件夹
                packageFile.mkdirs();
            }
            if (!daoFile.exists()) {// 创建dao文件夹
                daoFile.mkdirs();
            }

            // 创建api、controller、dao、domain、service 文件夹
            String[] folders = {"controller", "dao", "domain", "service" };
            for (String f : folders) {
                File tmpFile = new File(packageFilePath + f);
                if (!tmpFile.exists()) {
                    tmpFile.mkdir();
                }

                if ("service".equals(f) && !TableConvertClass.isMiddleTable(cols)) {
                    if (!onlydomain) {
                        String tmpClassName =className + "Service.java";
                        String tmpTableClassName = tableClassName;
                        if(withModule){
                        	tmpClassName = BeanUtil.upperCaseFirst(moduleName) + tmpClassName;
                        	tmpTableClassName = BeanUtil.upperCaseFirst(moduleName) + tmpTableClassName;
                        }
                        File classFile = new File(packageFilePath + f + "/" + "I"+tmpClassName);
                        if (!classFile.exists()) {// 如果存在，则不重建
                            createApiClass(classFile, packageName, moduleName, className, tmpTableClassName,withModule);
                        }
                    }
                    if (!onlydomain) {
                        String tmpClassName = className + "ServiceImpl.java";
                        String tmpTableClassName = tableClassName;
                        if(withModule){
                        	tmpClassName = BeanUtil.upperCaseFirst(moduleName) + tmpClassName;
                        	tmpTableClassName = BeanUtil.upperCaseFirst(moduleName) + tmpTableClassName;
                        }
                        File classFile = new File(packageFilePath + f + "/impl/" + tmpClassName);
                        if (!classFile.exists()) {// 如果存在，则不重建
                        	tmpFile = new File(packageFilePath + f + "/impl/");
                        	if (!tmpFile.exists()) {
                                tmpFile.mkdir();
                            }
                            createServiceImplClass(classFile, packageName, moduleName, className, tmpTableClassName,withModule);
                        }
                    }
                } else if ("controller".equals(f) && !TableConvertClass.isMiddleTable(cols)) {
                    if (!onlydomain) {
                        String tmpClassName = className + "Controller.java";
                        String tmpTableClassName = tableClassName;
                        if(withModule){
                        	tmpClassName = BeanUtil.upperCaseFirst(moduleName) + tmpClassName;
                        	tmpTableClassName = BeanUtil.upperCaseFirst(moduleName) + tmpTableClassName;
                        }
                        File classFile = new File(packageFilePath + "/" + f + "/" + tmpClassName);
                        if (!classFile.exists()) {// 如果存在，则不重建
                            createControllerClass(classFile, packageName, moduleName, className, tmpTableClassName,withModule);
                        }
                    }
                } else if ("dao".equals(f)) {
                    if (!onlydomain) {
                        String tmpClassName = className + "Dao.java";
                        String tmpTableClassName = tableClassName;
                        if(withModule){
                        	tmpClassName = BeanUtil.upperCaseFirst(moduleName) + tmpClassName;
                        	tmpTableClassName = BeanUtil.upperCaseFirst(moduleName) + tmpTableClassName;
                        }
                        File classFile = new File(packageFilePath + "/" + f + "/" + tmpClassName);
                        if (!classFile.exists()) {// 如果存在，则不重建
                        	if(!TableConvertClass.isMiddleTable(cols)){
                        		createDaoClass(classFile, packageName, moduleName, className, tmpTableClassName,withModule);
                        	}else{
                        		createDaoClass2(classFile, packageName, moduleName, className, tmpTableClassName,withModule);
                        	}
                        }
                    }
                } else if("domain".equals(f)){
                    String tmpPackageName = packageName + "." + moduleName + ".domain;";
                    String tmpClassName = tableClassName + ".java";
                    String tmpTableClassName = tableClassName;
                    if(withModule){
                    	tmpClassName = BeanUtil.upperCaseFirst(moduleName) + tmpClassName;
                    	tmpTableClassName = BeanUtil.upperCaseFirst(moduleName) + tmpTableClassName;
                    }
                    File classFile = new File(packageFilePath + "/" + f + "/" + tmpClassName);
                    if (!classFile.exists()) {// 如果存在，则不重建
                    	if(!TableConvertClass.isMiddleTable(cols)){
                    		createDomainClass(classFile, tmpPackageName, tableName,tmpTableClassName, cols,withModule,moduleName);
                    	}else{
                    		createDomainClass2(classFile, tmpPackageName, tmpTableClassName, cols,withModule,moduleName);
                    	}
                    }
                }
            }

            if (!onlydomain) {
                // 创建dao文件夹
                File daoTempFile = new File(daoFilePath + "dao");
                if (!daoTempFile.exists()) {
                    daoTempFile.mkdir();
                }
                if(withModule){
                	className = BeanUtil.upperCaseFirst(moduleName) + className;
                }
                File daoXmlFile = new File(daoFilePath + "dao" + "/" + className + "_Mapper.xml");
                if (!daoXmlFile.exists()) {// 如果存在，则不重建
                    String packageAndClass = packageName + "." + moduleName + ".domain." + className;
                    String packageAndDaoClass = packageName + "." + moduleName + ".dao." + className + "Dao";
                    if(!TableConvertClass.isMiddleTable(cols)){
                    	createMapperXml(daoXmlFile, packageAndClass, packageAndDaoClass, tableName);
                    }else{
                    	createMapperXml2(daoXmlFile, packageAndClass, packageAndDaoClass, tableName);
                    }
                }
            }
            
            //创建html文件
            if(!StringUtils.isEmpty(htmlDir)){
            	 File htmlDirFile = new File(htmlDir + "/" + moduleName + "/");
            	 if (!htmlDirFile.exists()) {
            		 htmlDirFile.mkdir();
                 }
            	 if(!TableConvertClass.isMiddleTable(cols)){
            		 createHtmlPage(htmlDirFile, moduleName, className, cols);
            	 }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }

    public static void createApiClass(File classFile, String packageName, String moduleName, String className, String domainName, boolean withModule) {
        try {

            String tmpPackageName = packageName + "." + moduleName + ".service;";
            if(withModule){
            	className = BeanUtil.upperCaseFirst(moduleName) + className;
            }
            String tmpClassName = "I" + className + "Service";

            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + tmpPackageName + "\n\n");

            sb.append("import java.util.List;\n");
            sb.append("import com.online.college.common.page.TailPage;\n");
            sb.append("import " + packageName + "." + moduleName + ".domain." + domainName + ";\n");

            sb.append("\n\n");
            sb.append("public interface " + tmpClassName + " {\n\n");
            
            sb.append("	/**\n");
            sb.append("	*根据id获取\n");
            sb.append("	**/\n");
            sb.append("	public " + domainName + " getById(Long id);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*获取所有\n");
            sb.append("	**/\n");
            sb.append("	public List<" + domainName + "> queryAll(" + domainName + " queryEntity);\n\n");

            sb.append("	/**\n");
            sb.append("	*分页获取\n");
            sb.append("	**/\n");
            sb.append("	public TailPage<" + domainName + "> queryPage(" + domainName + " queryEntity ,TailPage<" + domainName + "> page);\n\n");

            sb.append("	/**\n");
            sb.append("	*创建\n");
            sb.append("	**/\n");
            sb.append("	public void create(" + domainName + " entity);\n\n");

            sb.append("	/**\n");
            sb.append("	*根据id更新\n");
            sb.append("	**/\n");
            sb.append("	public void update(" + domainName + " entity);\n\n");

            sb.append("	/**\n");
            sb.append("	*根据id 进行可选性更新\n");
            sb.append("	**/\n");
            sb.append("	public void updateSelectivity(" + domainName + " entity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*物理删除\n");
            sb.append("	**/\n");
            sb.append("	public void delete(" + domainName + " entity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*逻辑删除\n");
            sb.append("	**/\n");
            sb.append("	public void deleteLogic(" + domainName + " entity);\n\n");

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createControllerClass(File classFile, String packageName, String moduleName, String className, String domainName, boolean withModule) {
        try {

            String tmpPackageName = packageName + "." + moduleName + ".controller;";
            if(withModule){
            	className = BeanUtil.upperCaseFirst(moduleName) + className;
            }
            String tmpClassName = className + "Controller";

            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + tmpPackageName + "\n\n");

            sb.append("import java.util.List;\n");
            sb.append("import com.online.college.common.page.TailPage;\n");
            sb.append("import org.springframework.stereotype.Controller;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
            sb.append("import org.springframework.web.servlet.ModelAndView;\n");

            sb.append("import " + packageName + "." + moduleName + ".domain." + domainName + ";\n");
            sb.append("import " + packageName + "." + moduleName + ".service.I" + className + "Service;\n");

            sb.append("\n\n");
            sb.append("@Controller\n@RequestMapping(\"/" + moduleName + "/" + CommonUtil.stringCap(className) + "\")\npublic class " + tmpClassName + "{\n\n");
            sb.append("	@Autowired\n	private I" + className + "Service entityService;\n\n");

            sb.append("	@RequestMapping(value = \"/getById\")\n	public ModelAndView getById(Long id){"
            		+ "\n		ModelAndView mv = new ModelAndView(\"" + moduleName + "/" + CommonUtil.stringCap(className) + "\");" 
                    + "\n		mv.addObject(\"entity\",entityService.getById(id));" 
            		+ "\n		return mv;" 
                    + "\n	}\n\n");

            sb.append("	@RequestMapping(value = \"/queryAll\")\n	public  ModelAndView queryAll(" + domainName + " queryEntity){" 
                    + "\n		ModelAndView mv = new ModelAndView(\"" + moduleName + "/" + CommonUtil.stringCap(className) + "List\");" 
            		+ "\n		List<"+domainName+"> list = entityService.queryAll(queryEntity);" 
            		+ "\n		mv.addObject(\"list\",list);" 
            		+ "\n		return mv;"
                    + "\n	}\n\n");
            
            sb.append("	@RequestMapping(value = \"/queryPage\")\n	public  ModelAndView queryPage(" + domainName + " queryEntity , TailPage<" + domainName + "> page){" 
                    + "\n		ModelAndView mv = new ModelAndView(\"" + moduleName + "/" + CommonUtil.stringCap(className) + "Page\");" 
            		+ "\n		page = entityService.queryPage(queryEntity,page);" 
            		+ "\n		mv.addObject(\"page\",page);" 
            		+ "\n		mv.addObject(\"queryEntity\",queryEntity);" 
                    + "\n		return mv;"
                    + "\n	}\n\n");

            sb.append("	@RequestMapping(value = \"/toMerge\")\n	public ModelAndView toMerge(" + domainName + " entity){" 
            		+ "\n		ModelAndView mv = new ModelAndView(\"" + moduleName + "/" + CommonUtil.stringCap(className) + "Merge\");"
            		+ "\n		if(entity.getId() != null){"
            		+ "\n			entity = entityService.getById(entity.getId());" 
            		+ "\n		}"
            		+ "\n		mv.addObject(\"entity\",entity);" 
            		+ "\n		return mv;" 
            		+ "\n	}\n\n");

            sb.append("	@RequestMapping(value = \"/doMerge\")\n	public ModelAndView doMerge(" + domainName + " entity){"
            		+ "\n		if(entity.getId() == null){" 
            		+ "\n			entityService.create(entity);" 
            		+ "\n		}else{" 
            		+ "\n			entityService.update(entity);" 
            		+ "\n		}" 
            		+ "\n		return new ModelAndView(\"redirect:/" + moduleName + "/" + CommonUtil.stringCap(className) + "/queryPage.html\");" 
            		+ "\n	}\n\n");

            sb.append("	@RequestMapping(value = \"/delete\")\n	public ModelAndView delete(" + domainName + " entity){"
                    + "\n		entityService.delete(entity);" 
                    + "\n		return new ModelAndView(\"redirect:/" + moduleName + "/" + CommonUtil.stringCap(className) + "/queryPage.html\");" 
                    + "\n	}\n\n");
            
            sb.append("	@RequestMapping(value = \"/deleteLogic\")\n	public ModelAndView deleteLogic(" + domainName + " entity){"
                    + "\n		entityService.deleteLogic(entity);" 
                    + "\n		return new ModelAndView(\"redirect:/" + moduleName + "/" + CommonUtil.stringCap(className) + "/queryPage.html\");" 
                    + "\n	}\n\n");

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDaoClass(File classFile, String packageName, String moduleName, String className, String domainName, boolean withModule) {
        try {

            String tmpPackageName = packageName + "." + moduleName + ".dao;";
            if(withModule){
            	className = BeanUtil.upperCaseFirst(moduleName) + className;
            }
            String tmpClassName = className + "Dao";

            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + tmpPackageName + "\n\n");

            sb.append("import java.util.List;\n");
            sb.append("import com.online.college.common.page.TailPage;\n");
            sb.append("import " + packageName + "." + moduleName + ".domain." + domainName + ";\n");

            sb.append("\n\n");
            sb.append("public interface " + tmpClassName + " {\n\n");
            
            sb.append("	/**\n");
            sb.append("	*根据id获取\n");
            sb.append("	**/\n");
            sb.append("	public " + domainName + " getById(Long id);\n\n");

            sb.append("	/**\n");
            sb.append("	*获取所有\n");
            sb.append("	**/\n");
            sb.append("	public List<" + domainName + "> queryAll(" + domainName + " queryEntity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*获取总数量\n");
            sb.append("	**/\n");
            sb.append("	public Integer getTotalItemsCount(" + domainName + " queryEntity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*分页获取\n");
            sb.append("	**/\n");
            sb.append("	public List<" + domainName + "> queryPage(" + domainName + " queryEntity , TailPage<" + domainName + "> page);\n\n");

            sb.append("	/**\n");
            sb.append("	*创建新记录\n");
            sb.append("	**/\n");
            sb.append("	public void create(" + domainName + " entity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*创建新记录\n");
            sb.append("	**/\n");
            sb.append("	public void createSelectivity(" + domainName + " entity);\n\n");

            sb.append("	/**\n");
            sb.append("	*根据id更新\n");
            sb.append("	**/\n");
            sb.append("	public void update(" + domainName + " entity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*根据id选择性更新自动\n");
            sb.append("	**/\n");
            sb.append("	public void updateSelectivity(" + domainName + " entity);\n\n");

            sb.append("	/**\n");
            sb.append("	*物理删除\n");
            sb.append("	**/\n");
            sb.append("	public void delete(" + domainName + " entity);\n\n");
            
            sb.append("	/**\n");
            sb.append("	*逻辑删除\n");
            sb.append("	**/\n");
            sb.append("	public void deleteLogic(" + domainName + " entity);\n\n");

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void createDaoClass2(File classFile, String packageName, String moduleName, String className, String domainName,boolean withModule) {
        try {

            String tmpPackageName = packageName + "." + moduleName + ".dao;";
            if(withModule){
            	className = BeanUtil.upperCaseFirst(moduleName) + className;
            }
            String tmpClassName = className + "Dao";

            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + tmpPackageName + "\n\n");

            sb.append("import " + packageName + "." + moduleName + ".domain." + domainName + ";\n");

            sb.append("\n\n");
            sb.append("public interface " + tmpClassName + " {\n\n");

            sb.append("	public void create(" + domainName + " entity);\n\n");

            sb.append("	public void update(" + domainName + " entity);\n\n");
            
            sb.append("	public void delete(" + domainName + " entity);\n\n");

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDomainClass(File classFile, String packageName, String tableName,String className, List<T2BVO> cols,boolean withModule,String moduleName) {
        try {
            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + packageName + "\n\n");
            sb.append("import java.util.Date;\n");
            sb.append("import java.math.BigDecimal;\n");
            sb.append("import javax.persistence.Table;\n");
            sb.append("import com.online.college.common.orm.BaseEntity;\n");

            sb.append("\n\n");
//            sb.append("@Table(name=\"" + tableName.toUpperCase() + "\")\n");
            sb.append("public class " + className + " extends BaseEntity{\n");

            for (T2BVO vo : cols) {
            	String col = vo.getColName();
                if ("CREATETIME".equalsIgnoreCase(col) || "CREATE_USER".equalsIgnoreCase(col) || "CREATE_TIME".equalsIgnoreCase(col) || "UPDATE_USER".equalsIgnoreCase(col)
                        || "UPDATE_TIME".equalsIgnoreCase(col) || "DEL".equalsIgnoreCase(col) || "ID".equalsIgnoreCase(col)) {
                	//pass
                } else {
                	String type = vo.getColType();
                	String javaType = " String ";
                	if(type.toLowerCase().contains("smallint") || type.toLowerCase().contains("tinyint")){
                		javaType = " Integer ";
                	}else if(type.toLowerCase().contains("date")){
                		javaType = " Date ";
                	}else if(type.toLowerCase().contains("int")){
                		javaType = " Long ";
                	}else if(type.toLowerCase().contains("decimal")){
                		javaType = " BigDecimal ";
                	}
                	sb.append("\n\n	/**\n	*" + new String(vo.getColComment()) + "\n	**/\n");
                    sb.append("	private" + javaType + BeanUtil.columnToField(col.toLowerCase()) + ";");
                   
                }
            }
            
            sb.append("\n\n");
            
            //setter & getter
            for (T2BVO vo : cols) {
            	String col = vo.getColName();
                if ("CREATETIME".equalsIgnoreCase(col) || "CREATE_USER".equalsIgnoreCase(col) || "CREATE_TIME".equalsIgnoreCase(col) || "UPDATE_USER".equalsIgnoreCase(col)
                        || "UPDATE_TIME".equalsIgnoreCase(col) || "DEL".equalsIgnoreCase(col) || "ID".equalsIgnoreCase(col)) {
                	//pass
                } else {
                	String type = vo.getColType();
                	String javaType = " String ";
                	if(type.toLowerCase().contains("smallint") || type.toLowerCase().contains("tinyint")){
                		javaType = " Integer ";
                	}else if(type.toLowerCase().contains("date")){
                		javaType = " Date ";
                	}else if(type.toLowerCase().contains("int")){
                		javaType = " Long ";
                	}else if(type.toLowerCase().contains("decimal")){
                		javaType = " BigDecimal ";
                	}
                	String property = BeanUtil.columnToField(col.toLowerCase());
                    sb.append("	public" + javaType + "get" + BeanUtil.upperCaseFirst(property) + "(){\n");
                    sb.append("		return " + property +";\n");
                    sb.append("	}\n");
                    
                    sb.append("	public void set" + BeanUtil.upperCaseFirst(property) + "(" + javaType.trim() + " " + property + "){\n");
                    sb.append("		this." +property+ " = " + property +";\n");
                    sb.append("	}\n\n");
                }
            }

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void createDomainClass2(File classFile, String packageName, String className, List<T2BVO> cols,boolean withModule,String moduleName) {
        try {
            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + packageName + "\n\n");
            sb.append("import java.util.Date;\n");
            sb.append("import com.online.college.common.orm.LongModel;\n");

            sb.append("\n\n");
            if(withModule){
            	className = BeanUtil.upperCaseFirst(moduleName) + className;
            }
            sb.append("public class " + className + " extends LongModel{\n\n");

            for (T2BVO vo : cols) {
            	String col = vo.getColName();
                if ("CREATETIME".equalsIgnoreCase(col) || "CREATE_USER".equalsIgnoreCase(col) || "CREATE_TIME".equalsIgnoreCase(col) || "UPDATE_USER".equalsIgnoreCase(col)
                        || "UPDATE_TIME".equalsIgnoreCase(col) || "DEL".equalsIgnoreCase(col) || "ID".equalsIgnoreCase(col)) {
                	//pass
                } else {
                	String type = vo.getColType();
                	String javaType = " String ";
                	if(type.toLowerCase().contains("smallint") || type.toLowerCase().contains("tinyint")){
                		javaType = " Integer ";
                	}else if(type.toLowerCase().contains("date")){
                		javaType = " Date ";
                	}else if(type.toLowerCase().contains("int")){
                		javaType = " Long ";
                	}else if(type.toLowerCase().contains("decimal")){
                		javaType = " Double ";
                	}
                	sb.append("\n\n	/**\n	*" + new String(vo.getColComment()) + "\n	**/\n");
                    sb.append("	private" + javaType + BeanUtil.columnToField(col.toLowerCase()) + ";");
                    
                }
            	
            }
            
            sb.append("\n\n");
            
            //setter & getter
            for (T2BVO vo : cols) {
            	String col = vo.getColName();
                if ("CREATETIME".equalsIgnoreCase(col) || "CREATE_USER".equalsIgnoreCase(col) || "CREATE_TIME".equalsIgnoreCase(col) || "UPDATE_USER".equalsIgnoreCase(col)
                        || "UPDATE_TIME".equalsIgnoreCase(col) || "DEL".equalsIgnoreCase(col) || "ID".equalsIgnoreCase(col)) {
                	//pass
                } else {
                	String type = vo.getColType();
                	String javaType = " String ";
                	if(type.toLowerCase().contains("smallint") || type.toLowerCase().contains("tinyint")){
                		javaType = " Integer ";
                	}else if(type.toLowerCase().contains("date")){
                		javaType = " Date ";
                	}else if(type.toLowerCase().contains("int")){
                		javaType = " Long ";
                	}else if(type.toLowerCase().contains("decimal")){
                		javaType = " Double ";
                	}
                	String property = BeanUtil.columnToField(col.toLowerCase());
                    sb.append("	public" + javaType + "get" + BeanUtil.upperCaseFirst(property) + "(){\n");
                    sb.append("		return " + property +";\n");
                    sb.append("	}\n");
                    
                    sb.append("	public void set" + BeanUtil.upperCaseFirst(property) + "(" + javaType.trim() + " " + property + "){\n");
                    sb.append("		this." +property+ " = " + property +";\n");
                    sb.append("	}\n\n");
                }
            }

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createServiceImplClass(File classFile, String packageName, String moduleName, String className, String domainName,boolean withModule) {
        try {

            String tmpPackageName = packageName + "." + moduleName + ".service.impl;";
            if(withModule){
            	className = BeanUtil.upperCaseFirst(moduleName) + className;
            }
            String tmpClassName = className + "ServiceImpl";

            classFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("package " + tmpPackageName + "\n\n");

            sb.append("import java.util.List;\n");
//            sb.append("import com.online.college.common.orm.BaseDao;\n");
            sb.append("import com.online.college.common.page.TailPage;\n");
            sb.append("import org.springframework.stereotype.Service;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");

            sb.append("import " + packageName + "." + moduleName + ".domain." + domainName + ";\n");
            sb.append("import " + packageName + "." + moduleName + ".service.I" + className + "Service;\n");
            sb.append("import " + packageName + "." + moduleName + ".dao." + className + "Dao;\n");

            sb.append("\n\n");
            
            sb.append("@Service\npublic class " + tmpClassName + " implements I" + className + "Service{\n\n");

//            sb.append("	//开发者可以使用公共的Dao，也可以使用每个Entity的entityDao\n");
//            sb.append("	@Autowired\n	private BaseDao  baseDao;\n\n");
            sb.append("	@Autowired\n	private " + className + "Dao entityDao;\n\n");

            sb.append("	@Override\n");
            sb.append("	public " + domainName + " getById(Long id){" + "\n		return entityDao.getById(id);\n" + "	}\n\n");

            sb.append("	@Override\n");
            sb.append("	public List<" + domainName + "> queryAll(" + domainName + " queryEntity){"
                    + "\n		return entityDao.queryAll(queryEntity);\n" + "	}\n\n");
            
            sb.append("	@Override\n");
            sb.append("	public TailPage<" + domainName + "> queryPage(" + domainName + " queryEntity ,TailPage<" + domainName + "> page){"
                    + "\n		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);" 
                    + "\n		List<" + domainName + "> items = entityDao.queryPage(queryEntity,page);" 
                    + "\n		page.setItemsTotalCount(itemsTotalCount);" 
                    + "\n		page.setItems(items);" 
                    + "\n		return page;" 
            		+ "\n	}\n\n");
            
            sb.append("	@Override\n");
            sb.append("	public void create(" + domainName + " entity){" + "\n		entityDao.create(entity);\n" + "	}\n\n");

            sb.append("	@Override\n");
            sb.append("	public void update(" + domainName + " entity){" + "\n		entityDao.update(entity);\n" + "	}\n\n");
            
            sb.append("	@Override\n");
            sb.append("	public void updateSelectivity(" + domainName + " entity){" + "\n		entityDao.updateSelectivity(entity);\n" + "	}\n\n");

            sb.append("	@Override\n");
            sb.append("	public void delete(" + domainName + " entity){" + "\n		entityDao.delete(entity);\n" + "	}\n\n");
            
            sb.append("	@Override\n");
            sb.append("	public void deleteLogic(" + domainName + " entity){" + "\n		entityDao.deleteLogic(entity);\n" + "	}\n\n");

            sb.append("\n\n}\n\n");
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(classFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createMapperXml(File daoXmlFile, String packageAndClass, String packageAndDaoClass, String tableName) {
        try {
            Class<?> clazz = Class.forName(packageAndClass);
            
            tableName = StringUtils.upperCase(tableName);
            daoXmlFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://10.20.91.130/dtd/mybatis-3-mapper.dtd\" >");

            sb.append("\n\n<mapper namespace=\"" + packageAndDaoClass + "\">\n");
            
            sb.append("\n"+TableConvertClass.converResult(packageAndClass, clazz)+"\n");
            sb.append("\n"+TableConvertClass.converBaseColumnList(clazz)+"\n");

            sb.append("\n	<select id=\"queryAll\" parameterType=\"" + packageAndClass + "\" resultType=\"" + packageAndClass + "\">");
            sb.append("\n		SELECT ");
            sb.append("\n		<include refid=\"All_Columns\" />");
            sb.append("\n		FROM " + tableName + " ORDER BY ID");
            sb.append("\n	</select>");
            
            sb.append("\n\n	<select id=\"getTotalItemsCount\" parameterType=\"" + packageAndClass + "\" resultType=\"java.lang.Integer\">");
            sb.append("\n		SELECT COUNT(*) FROM " + tableName );
            sb.append("\n		WHERE DEL = 0 " );
            sb.append("\n	</select>");
            
            sb.append("\n\n	<select id=\"queryPage\" resultType=\"" + packageAndClass + "\">");
            sb.append("\n		SELECT ");
            sb.append("\n		<include refid=\"All_Columns\" />");
            sb.append("\n		FROM " + tableName);
            sb.append("\n		WHERE DEL = 0 " );
            sb.append("\n		ORDER BY ID");
            sb.append("\n		LIMIT #{param2.startIndex, jdbcType=INTEGER} , #{param2.pageSize, jdbcType=INTEGER} ");
            sb.append("\n	</select>");
            
            sb.append("\n\n	<select id=\"getById\" parameterType=\"java.lang.Long\" resultType=\"" + packageAndClass + "\">");
            sb.append("\n		SELECT ");
            sb.append("\n		<include refid=\"All_Columns\"  />");
            sb.append("\n		FROM " + tableName);
            sb.append("\n		WHERE ID = #{id, jdbcType=INTEGER}");
            sb.append("\n	</select>");

            sb.append("\n\n	<insert id=\"create\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\"  useGeneratedKeys=\"true\" keyProperty=\"id\"  >");
            sb.append("\n		" + TableConvertClass.insertSql(tableName, clazz));
            sb.append("\n	</insert>");
            
            sb.append("\n\n	<insert id=\"createSelectivity\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\"  useGeneratedKeys=\"true\" keyProperty=\"id\"  >");
            sb.append("\n		" + TableConvertClass.insertSelectivitySql(tableName, clazz));
            sb.append("\n	</insert>");
            
            sb.append("\n\n	<update id=\"update\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\">");
            sb.append("\n		" + TableConvertClass.updateSql(tableName, clazz));
            sb.append("\n	</update>");
            
            sb.append("\n\n	<update id=\"updateSelectivity\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\">");
            sb.append("\n		" + TableConvertClass.updateSelectivity(tableName, clazz));
            sb.append("\n	</update>");

            sb.append("\n\n	<delete id=\"delete\" parameterType=\"" + packageAndClass + "\" >");
            sb.append("\n		DELETE FROM " + tableName + "\n		WHERE ID = #{id, jdbcType=INTEGER}");
            sb.append("\n	</delete>");
            
            sb.append("\n\n	<update id=\"deleteLogic\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\">");
            sb.append("\n		UPDATE " + tableName );
            sb.append("\n		SET DEL = 1");
            sb.append("\n		WHERE ID = #{id, jdbcType=INTEGER}");
            sb.append("\n	</update>");

            sb.append("\n\n</mapper>\n");

            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(daoXmlFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("如果后台报异常（由于class.forname引起），请刷新项目目录，再跑一次；");
        }

    }
    
    public static void createMapperXml2(File daoXmlFile, String packageAndClass, String packageAndDaoClass, String tableName) {
        try {
            Class<?> clazz = Class.forName(packageAndClass);
            
            tableName = StringUtils.upperCase(tableName);
            daoXmlFile.createNewFile();

            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://10.20.91.130/dtd/mybatis-3-mapper.dtd\" >");

            sb.append("\n\n<mapper namespace=\"" + packageAndDaoClass + "\">\n");

            sb.append("\n\n	<insert id=\"create\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\"  useGeneratedKeys=\"true\" keyProperty=\"id\"  >");
            sb.append("\n		" + TableConvertClass.insertSql(tableName, clazz));
            sb.append("\n	</insert>");

            sb.append("\n\n	<update id=\"update\" parameterType=\"" + packageAndClass + "\" flushCache=\"true\">");
            sb.append("\n		" + TableConvertClass.updateSql(tableName, clazz));
            sb.append("\n	</update>");

            sb.append("\n\n	<delete id=\"delete\" parameterType=\"" + packageAndClass + "\" >");
            sb.append("\n		DELETE FROM " + tableName + "\n		WHERE ID = #{id, jdbcType=INTEGER}");
            sb.append("\n	</delete>");
            
            sb.append("\n\n</mapper>\n");

            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(daoXmlFile),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            writer.write(sb.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("如果后台报异常（由于class.forname引起），请刷新项目目录，再跑一次；");
        }

    }
    
    private static boolean passProperty(String col){
    	return "CREATETIME".equalsIgnoreCase(col) || "CREATE_USER".equalsIgnoreCase(col) 
    			|| "CREATE_TIME".equalsIgnoreCase(col) || "UPDATE_USER".equalsIgnoreCase(col)
                || "UPDATE_TIME".equalsIgnoreCase(col) || "DEL_FLAG".equalsIgnoreCase(col) 
                || "ID".equalsIgnoreCase(col);
    }
    
    private static boolean isMiddleTable(List<T2BVO> cols){
    	boolean flag = true;
    	for(T2BVO vo : cols){
    		if(vo.getColName().toUpperCase().equalsIgnoreCase("CREATE_TIME")){
    			flag = false;
    			break;
    		}
    	}
    	return flag;
    }
    
    //创建页面文件
    public static void createHtmlPage(File htmlFile, String moduleName, String className ,List<T2BVO> cols){
		File pageFile = new File(htmlFile + "/" + BeanUtil.lowerCaseFirst(className) + "Page.html");
		File mergeFile = new File(htmlFile + "/" + BeanUtil.lowerCaseFirst(className) + "Merge.html");
		
		try {
			if(!pageFile.exists()){
				pageFile.createNewFile();
				
				StringBuilder sb = getPageHtml(moduleName,className,cols);
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8");
	            BufferedWriter writer=new BufferedWriter(write);  
	            writer.write(sb.toString());
	            writer.close();
			}
			
			if(!mergeFile.exists()){
				mergeFile.createNewFile();
				
				StringBuilder sb = getMergeHtml(moduleName,className,cols);
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8");
	            BufferedWriter writer=new BufferedWriter(write);  
	            writer.write(sb.toString());
	            writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
    
    private static StringBuilder getPageHtml(String moduleName, String className,List<T2BVO> cols){
    	StringBuilder sb = new StringBuilder(getHtmlHeader());
    	sb.append("		<div class=\"content\">\n");
    	sb.append("			<div class=\"block-nav\">\n");
    	sb.append("				<#include \"../common/nav.html\">\n");
    	sb.append("			</div>\n");
    	
    	sb.append("			<div class=\"block-content\">\n\n");
    	sb.append("				<div class=\"block-content-content\">\n");
    	sb.append("					<form id=\"id_fm-form\" method=\"post\" action=\"${s.base}/" + moduleName + "/" + CommonUtil.stringCap(className) + "/queryPage.html\">\n");
    	sb.append("						<table class=\"searchform-table\">\n");
    	
    	for(int i = 0; i < cols.size() ;i++){
    		if(i == 0){
    			sb.append("							<tr>\n");
    		}
    		String col = cols.get(i).getColName();
    		if (!passProperty(col)) {
    			
    			sb.append("								<td>\n");
            	sb.append("									<label>" + BeanUtil.columnToField(col.toLowerCase()) + "</label>\n");
            	sb.append("								</td>\n");
    			
    			sb.append("								<td>\n");
            	sb.append("									<input type=\"text\" name=\""+BeanUtil.columnToField(col.toLowerCase())+"\" value=\"${queryEntity."+BeanUtil.columnToField(col.toLowerCase())+"!}\"></input>\n");
            	sb.append("								</td>\n");
    		}
    		if(i == cols.size() - 1){
    			sb.append("								<td>\n");
            	sb.append("									<input class=\"btn\" type=\"button\" value=\"搜索\"></input>\n");
            	sb.append("								</td>\n");
            	sb.append("							</tr>\n");
    		}
        	if(i%3 == 0 && i != 0){
        		sb.append("							</tr>\n");
        		sb.append("							<tr>\n");
        	}
    	}
    	sb.append("						</table>\n\n\n");
    	
    	//分页table
    	sb.append("						<table class=\"page-table\">\n");
    	
    	sb.append("							<thead>\n");
    	sb.append("								<tr>\n");
    	for(int i = 0; i < cols.size() ;i++){
    		String col = cols.get(i).getColName();
    		sb.append("									<td style=\"width:100px;\">"+BeanUtil.columnToField(col.toLowerCase())+"</td>\n");
    	}
    	sb.append("									<td style=\"width:100px;\"><input class=\"btn\" type=\"button\" onClick=\"window.location.href='${s.base}/" + moduleName + "/" + CommonUtil.stringCap(className) + "/toMerge.html'\" value=\"添加\"/></td>\n");
    	sb.append("								</tr>\n");
    	sb.append("							</thead>\n");
    	
    	sb.append("							<tbody>\n");
    	sb.append("								<#list page.items as item>\n");
    	sb.append("								<#if item_index %2 == 0>\n");
    	sb.append("								<tr>\n");
    	sb.append("								<#else>\n");
    	sb.append("								<tr class=\"even-tr\">\n");
    	sb.append("								</#if>\n");
    	for(int i = 0; i < cols.size() ;i++){
    		String col = cols.get(i).getColName();
    		if("createtime".equals(BeanUtil.columnToField(col.toLowerCase()))){
    			sb.append("									<td style=\"width:100px;\">${item."+BeanUtil.columnToField(col.toLowerCase())+"!?string(\"yyyy-MM-dd HH:mm:ss\")}</td>\n");
    		}else{
    			sb.append("									<td style=\"width:100px;\">${item."+BeanUtil.columnToField(col.toLowerCase())+"!}</td>\n");
    		}
    	}
    	sb.append("									<td style=\"width:100px;\">\n");
    	sb.append("										<a href=\"javascript:void(0)\" onClick=\"window.location.href='${s.base}/" + moduleName + "/" + CommonUtil.stringCap(className) + "/toMerge.html?id=${item.id!}'\" >修改</a>\n");
    	sb.append("										<a href=\"javascript:void(0)\" onClick=\"window.location.href='${s.base}/" + moduleName + "/" + CommonUtil.stringCap(className) + "/delete.html?id=${item.id!}'\" >删除</a>\n");
    	sb.append("									</td>\n");
    	sb.append("								</tr>\n");
    	sb.append("								</#list>\n");
    	sb.append("							</tbody>\n");
    	sb.append("						</table>\n\n\n");
    	
    	sb.append("						<#include \"../common/tailPage.html\">\n");
    	sb.append("					</form>\n");
    	sb.append("				</div>\n");
    	sb.append("			</div>\n");
    	sb.append("			<div class=\"clearfloat\"></div>\n");
    	sb.append("		</div>\n\n");
    	sb.append("		<#include \"../common/footer.html\">\n\n");
    	sb.append(getHtmlFooter());
    	return sb;
    }
    
    private static StringBuilder getMergeHtml(String moduleName, String className, List<T2BVO> cols){
    	StringBuilder sb = new StringBuilder(getHtmlHeader());
    	sb.append("		<div class=\"content\">\n");
    	sb.append("			<div class=\"block-nav\">\n");
    	sb.append("				<#include \"../common/nav.html\">\n");
    	sb.append("			</div>\n");
    	
    	sb.append("			<div class=\"block-content\">\n\n");
    	sb.append("				<div class=\"block-content-content\">\n");
    	sb.append("					<form class=\"fm-form\" method=\"post\" enctype=\"multipart/form-data\" onsubmit=\"return doSubmit();\" action=\"${s.base}/" + moduleName + "/" + CommonUtil.stringCap(className) + "/doMerge.html\">\n");
    	sb.append("						<input type=\"hidden\" name=\"id\" value=\"${entity.id!}\" />\n");
    	sb.append("						<table class=\"fm-form-table\">\n");
    	
    	for(int i = 0; i < cols.size() ;i++){
    		if(i == 0){
    			sb.append("							<tr>\n");
    		}
    		String col = cols.get(i).getColName();
    		if (!passProperty(col)) {
    			sb.append("								<td>\n");
            	sb.append("									<label>" + BeanUtil.columnToField(col.toLowerCase()) + " </label>\n");
            	sb.append("								</td>\n");
    			sb.append("								<td>\n");
            	sb.append("									<input type=\"text\" name=\""+BeanUtil.columnToField(col.toLowerCase())+"\" value=\"${entity."+BeanUtil.columnToField(col.toLowerCase())+"!}\"></input>\n");
            	sb.append("								</td>\n");
    		}
    		if(i%3 == 0 && i != 0){
        		sb.append("							</tr>\n");
        		sb.append("							<tr>\n");
        	}
    		if(i == cols.size() - 1){
            	sb.append("							</tr>\n");
    		}
    	}
    	
    	sb.append("							<tr>\n");
    	sb.append("								<td style=\"text-align:center;\" colspan=\"6\">\n");
    	sb.append("									<input class=\"btn\" type=\"submit\" value=\"提交\"></input>\n");
    	sb.append("								</td>\n");
    	sb.append("							</tr>\n");
    	
    	sb.append("						</table>\n");
    	sb.append("					</form>\n\n");
    	sb.append("				</div>\n");
    	sb.append("			</div>\n");
    	sb.append("			<div class=\"clearfloat\"></div>\n");
    	sb.append("		</div>\n\n");
    	sb.append("		<#include \"../common/footer.html\">\n\n");
    	sb.append(getHtmlFooter());
    	return sb;
    }
    
    
    private static String getHtmlHeader(){
    	StringBuilder sb = new StringBuilder("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">\n");
    	sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
    	sb.append("	<head>\n");
    	sb.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
    	sb.append("		<#include \"../common/res.html\">\n");
    	sb.append("	</head>\n\n");
    	sb.append("	<body class=\"bg\">\n");
    	sb.append("		<#include \"../common/top.html\">\n");
    	return sb.toString();
    }
    
    private static String getHtmlFooter(){
    	StringBuilder sb = new StringBuilder("	</body>\n");
    	sb.append("</html>\n");
    	return sb.toString();
    }
    
}
