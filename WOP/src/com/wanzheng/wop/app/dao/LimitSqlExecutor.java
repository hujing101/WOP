/*
 * 修改时间：2009-07-09
 */
package com.wanzheng.wop.app.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.dao.DataAccessException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

/**
 * iBatis sql捕获
 * 
 */
public class LimitSqlExecutor extends SqlExecutor
{

    /** oracle */
    private static final String ORACLECONSTANT = "oracle";

    // private static final LogUtil logger = LogUtil.getInstance();
    // modify by wuwenyu for PMD
    /**
     * LOGGER
     */
    private static final Logger LOGGER = Logger
            .getLogger(LimitSqlExecutor.class);

    /** sql方言 */
    private Dialect dialect;

    /** 允许捕获 */
    private boolean enableLimit = true;

    /**
     * 属性
     */
    private ProxoolDataSource proxoolDataSource;

    private DialectMysql dialectOracle;

    public boolean isEnableLimit()
    {
        return enableLimit;
    }

    public void setEnableLimit(boolean enableLimit)
    {
        this.enableLimit = enableLimit;
    }

    /**
     * 执行更新
     * 
     * @param request
     *            request
     * @param conn
     *            连接
     * @param sql
     *            sql语句
     * @param parameters
     *            参数
     * @return 执行结果
     * @throws SQLException
     *             异常
     */
    public int executeUpdate(StatementScope request, Connection conn,
            String sql, Object[] parameters) throws SQLException
    {
        // logger.log(Priority.DEBUG, " *** SQL *** --> " + sql
        // + getParamsStr(parameters));
        LOGGER.debug("*** SQL *** -->" + sql + getParamsStr(parameters));
        int result = -1;
        try
        {
            result = super.executeUpdate(request, conn, sql, parameters);

        }
        catch (SQLException e)
        {
            LOGGER.debug("*** SQL Error*** -->" + sql
                    + getParamsStr(parameters));
            // logger.log(Priority.ERRORR, " *** SQL Error*** --> " + sql
            // + getParamsStr(parameters));
            throw e;
        }
        return result;
    }

    /**
     * 执行查询
     * 
     * @param request
     *            request
     * @param conn
     *            连接
     * @param sql
     *            sql语句
     * @param parameters
     *            参数
     * @param skipResults
     *            是否跳过结果
     * @param maxResults
     *            最大结果数
     * @param callback
     *            回滚
     * @throws SQLException
     *             异常
     */
    public void executeQuery(StatementScope request, Connection conn,
            String sql, Object[] parameters, int skipResults, int maxResults,
            RowHandlerCallback callback) throws SQLException
    {
        if (null == dialect)
        {
            String dataBaseName = null;

            if (proxoolDataSource != null)
            {
                dataBaseName = proxoolDataSource.getDriverUrl();
            }
            if (dataBaseName == null || "".equals(dataBaseName))
            {
                dataBaseName = "Unknown";
            }

            if (dataBaseName.indexOf(ORACLECONSTANT) >= 0)
            {
                // oracle
                dialect = new DialectMysql();
            }

            else
            {
                LOGGER.debug("reason unknown dataSource ");
                // logger.log(Priority.ERRORR, " unknown dataSource ");
            }

        }

        if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
                && supportsLimit())
        {
            sql = dialect.getLimitString(sql, skipResults, maxResults);
            skipResults = NO_SKIPPED_RESULTS;
            maxResults = NO_MAXIMUM_RESULTS;
        }
        LOGGER.debug(" *** SQL *** --> " + sql + getParamsStr(parameters));
        // logger.log(Priority.DEBUG, " *** SQL *** --> " + sql
        // + getParamsStr(parameters));
        try
        {
            super.executeQuery(request, conn, sql, parameters, skipResults,
                    maxResults, callback);
            // logger.log(Priority.DEBUG, " *** SQL Finish*** --> " + sql
            // + getParamsStr(parameters));
            LOGGER.debug(" *** SQL Finish*** --> " + sql
                    + getParamsStr(parameters));
        }
        catch (DataAccessException e)
        {
            // logger.log(Priority.ERRORR, " *** SQL Error*** --> " + sql
            // + getParamsStr(parameters));
            LOGGER.debug(" *** SQL Error*** --> " + sql
                    + getParamsStr(parameters));
        }
    }

    /**
     * 
     * <获取参数字符串>
     * 
     * @param parameters
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getParamsStr(Object[] parameters)
    {
        String result = null;
        if (parameters != null)
        {
            for (int index = 0; index < parameters.length; index++)
            {
                Object parameter = parameters[index];
                if (parameter != null)
                {
                    if (result == null)
                    {
                        result = " *** params: *** -->";
                    }
                    else
                    {
                        result += ", ";
                    }
                    result += parameters[index].toString();
                }
            }
        }
        if (result == null)
        {
            result = "";
        }
        return result;
    }


    public DialectMysql getDialectOracle()
    {
        return dialectOracle;
    }

    public void setDialectOracle(DialectMysql dialectOracle)
    {
        this.dialectOracle = dialectOracle;
    }

    public Dialect getDialect()
    {
        return dialect;
    }

    /**
     * 参数 <功能详细描述>
     * 
     * @return 返回值
     * @see [类、类#方法、类#成员]
     */
    public boolean supportsLimit()
    {
        if (enableLimit && dialect != null)
        {
            return dialect.supportsLimit();
        }
        return false;
    }

    public ProxoolDataSource getProxoolDataSource()
    {
        return proxoolDataSource;
    }

    public void setProxoolDataSource(ProxoolDataSource proxoolDataSource)
    {
        this.proxoolDataSource = proxoolDataSource;
    }

}
