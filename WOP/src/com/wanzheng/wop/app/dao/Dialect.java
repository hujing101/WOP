package com.wanzheng.wop.app.dao;

/**
 * <一句话功能简述>
 * 
 * @version [版本号, 2007-12-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface Dialect
{
    /**
     * 方法 <功能详细描述>
     * 
     * @return 结果
     * @see [类、类#方法、类#成员]
     */
    public boolean supportsLimit();

    /**
     * 方法 <功能详细描述>
     * 
     * @param sql
     *            sql语句
     * @param offset
     *            参数
     * @param limit
     *            参数
     * @return 结果
     * @see [类、类#方法、类#成员]
     */
    public String getLimitString(String sql, int offset, int limit);
}