package com.online.college.common.orm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yx
 * @createtime 2019/04/21 12:57
 */
@Getter
@Setter
class LongModel implements Identifier<Long> ,Serializable{
	private static final long serialVersionUID = 7978917143723588623L;
	
	private Long id;
}
