package com.ichaoj.sxq.client.compoment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 获取指定时间的后num天时间
     *
     * @param date
     * @return
     */
    public static Date getAfterDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        return date;
    }
}
