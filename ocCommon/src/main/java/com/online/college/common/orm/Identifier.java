package com.online.college.common.orm;

import java.io.Serializable;

/**
 * @author yx
 * @createtime 2019/04/21 12:58
 */
public interface Identifier<KEY extends Serializable> {
	KEY getId();
}
