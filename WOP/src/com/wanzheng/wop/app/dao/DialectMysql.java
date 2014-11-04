package com.wanzheng.wop.app.dao;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @version [版本号, 2007-12-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DialectMysql implements Dialect
{
    protected static final String SQL_END_DELIMITER = ";";

    /**
     * {@inheritDoc}
     */
    public String getLimitString(String sql, int offset, int limit)
    {
        int m = offset;
        int n = m + limit;
        return "select * from ( select row_.*, rownum rownum_ from ( " + sql
                + " ) row_ where rownum <= " + n + ") where rownum_ > " + m;

    }

    /**
     * {@inheritDoc}
     */
    public boolean supportsLimit()
    {
        return true;
    }
}