package com.bornsoft.ycl.client.compoment;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil {
	
	private static Logger logger = LoggerFactory.getLogger(NumberUtil.class);
	
	public static long parseLong(String strLong) {
		return parseLong(strLong, 0);
	}

	public static long parseLong(String strLong, long defaultValue) {
		if (StringUtils.isEmpty(strLong))
			return defaultValue;
		try {
			return Long.parseLong(strLong.trim());
		} catch (Exception e) {
			logger.error(strLong + " parseLong error,use defalt value " + defaultValue, e);
		}
		return defaultValue;
	}
}
