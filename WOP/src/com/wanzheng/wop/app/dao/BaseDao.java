package com.wanzheng.wop.app.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.wanzheng.wop.kernel.exc.WOPException;
import com.wanzheng.wop.pub.util.ReflectUtil;


/**
 * 所有DAO类的父类，通过注入SqlExecutor实现捕获sql语句记录功能
 * 
 * @author w48952
 */
public abstract class BaseDao   extends SqlMapClientDaoSupport
{
    private SqlExecutor sqlExecutor;

    public SqlExecutor getSqlExecutor()
    {
        return sqlExecutor;
    }

    public void setSqlExecutor(SqlExecutor sqlExecutor)
    {
        this.sqlExecutor = sqlExecutor;
    }

    /**
     * 设置值 <功能详细描述>
     * 
     * @param enableLimit
     *            参数
     * @see [类、类#方法、类#成员]
     */
    public void setEnableLimit(boolean enableLimit)
    {
        if (sqlExecutor instanceof LimitSqlExecutor)
        {
            ((LimitSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);
        }
    }

    /**
     * 初始化 <功能详细描述>
     * 
     * @throws WOPException
     * 
     *             异常
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("deprecation")
    public void initialize() throws WOPException
    {
        if (sqlExecutor != null)
        {
            SqlMapClient sqlMapClient = this.getSqlMapClientTemplate()
                    .getSqlMapClient();
            if (sqlMapClient instanceof com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient)
            {
                ReflectUtil
                        .setFieldValue(
                                ((com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient) sqlMapClient)
                                        .getDelegate(), "sqlExecutor",
                                SqlExecutor.class, sqlExecutor);
            }
        }

    }


}
