package com.delta.util;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;



/**
 * Timestamp日期格式转换
 * @author Shuyu.Wang
 *
 */
public class TimestampConverter implements Converter<String, Timestamp>{
	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Timestamp convert(String arg0) {
		if(StringUtils.isNotEmpty(arg0.trim())){
			Timestamp timestamp = Timestamp.valueOf(arg0);
			return timestamp;
		}
		return null;

	}

}
