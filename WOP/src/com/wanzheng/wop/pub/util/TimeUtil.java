package com.wanzheng.wop.pub.util;

/*
 * 文件名：TimeUtil.java
 * 描述： TimeUtil.java
 * 修改人：zhangjianwei
 * 修改时间：2011-7-20
 * 修改内容：新增
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author zhangjianwei
 * @version V100R001 2011-7-20
 */
public class TimeUtil
{
    /**
     * 日期格式yyyyMMddHHmmss
     */
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 获取时间的string类型
     * 
     * @param time
     *            时间
     * @return String
     */
    public static String getTimeString(long time)
    {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                FORMAT_YYYYMMDDHHMMSS);
        return simpleDateFormat.format(date);
    }

    /**
     * 把时间转成long <功能详细描述>
     * 
     * @param time
     *            时间
     * @return long 返回长整型
     * @see [类、类#方法、类#成员]
     */
    public static long getTimeLong(String time)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                FORMAT_YYYYMMDDHHMMSS);
        try
        {
            Date date = simpleDateFormat.parse(time);
            return date.getTime();
        }
        catch (Throwable e)
        {
            return 0L;
        }
    }

    /**
     * 根据格式生成流水号字符串
     * 
     * @param pattern
     *            格式
     * @return String
     */
    public static final String getDateFormatStr(String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 序列ID,随机生成。YYYYMMMDDHHmmss+6位数字
     * 
     * @return String
     */
    public static final String generateRetryMissonID()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(getDateFormatStr(TimeUtil.FORMAT_YYYYMMDDHHMMSS));
        return sb.toString();
    }

    public static String getFormatStr(String time)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                FORMAT_YYYYMMDDHHMMSS);
        try
        {
            Date date = simpleDateFormat.parse(time);
            return simpleDateFormat.format(date);
        }
        catch (Throwable e)
        {
            return "";
        }
    }

    /**
     * 时间
     * 
     * @param time
     *            时间
     * @return 时间
     * @author zsj 返回时间的字符串形式
     */
    public static String getNumericStrTime(String time)
    {
        if (time == null || "".equals(time))
        {
            return "";
        }
        String timeStr = time.replaceAll("[^0-9]", "");
        if (timeStr.length() < 14)
        {
            return timeStr;
        }
        else
        {
            return timeStr.substring(0, 14);
        }
    }

    /**
     * 返回格式如：9月2日10:46
     * 
     * @param time
     * @param format
     * @return
     */
    public static String getActiveTime(String time, String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try
        {
            Date date = simpleDateFormat.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            String result = (c.get(Calendar.MONTH) + 1) + "月"
                    + c.get(Calendar.DAY_OF_MONTH) + "日"
                    + c.get(Calendar.HOUR_OF_DAY) + ":"
                    + c.get(Calendar.MINUTE);
            return result;
        }
        catch (Throwable e)
        {
            return time;
        }
    }

}
