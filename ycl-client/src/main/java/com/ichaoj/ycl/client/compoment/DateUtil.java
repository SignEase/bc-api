package com.ichaoj.ycl.client.compoment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String dtShort = "yyyyMMdd";
    
    public static final String dtSimpleYmdhms = "yyyy-MM-dd HH:mm:ss";
	
	public static final String shortDate(Date Date) {
        if (Date == null) {
            return null;
        }
        return getFormat(dtShort).format(Date);
    }
	
	public static final String dtSimpleYmdhmsDate(Date Date) {
        if (Date == null) {
            return null;
        }
        return getFormat(dtSimpleYmdhms).format(Date);
    }
	/**
     * 获取格式
     *
     * @param format
     * @return
     */
    public static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }
}
