package com.online.college.common.util;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * 该类的作用是将对象转换成Json格式的字符串
 * @author yx
 */
public class JSONUtil {
	
    private static ObjectMapper mapper;
    static{
        mapper=new ObjectMapper();
    }

    /**
     * 将对象转换成Json格式的字符串
     */
    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }
}
