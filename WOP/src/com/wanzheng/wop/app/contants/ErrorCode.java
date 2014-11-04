/*
 * 文件名：ErrorCode.java
 * 描述： ErrorCode.java
 * 修改人：zhangshaojun
 * 修改时间：2011-8-6
 * 修改内容：新增
 */
package com.wanzheng.wop.app.contants;

/**
 * 错误码
 * 
 * @author  brother80
 * @version  [版本号, 2014-10-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ErrorCode
{
    /** 成功(包括任何处理或操作) */
    public static final String SUCCESS = "0";
    
    /**
     * 服务调用失败
     */
    public static final String ERROR_INVOCATE_SERVICE = "1500";
    
    /** 参数为空 */
    public static final String PARAM_NULL_INVALID = "2000";
    
    /** 查询失败 */
    public static final String QUERY_EXCEPTION = "2001";
    
    /** 更新失败 */
    public static final String UPDATE_EXCEPTION = "2002";
    
    /** 删除失败 */
    public static final String DELETE_EXCEPTION = "2003";
    
    /** 插入失败 */
    public static final String INSERT_EXCEPTION = "2004";
    
    /** 用户名为空或者不合法 */
    public static final String USENAME_INVALID = "2005";
    
    /** 用户不存在 **/
    public static final String USER_NOT_EXIST = "2006";
    
    /** 用户密码错误 */
    public static final String USER_HAS_EXIST = "2007";
    
    /** 密码为空或不合法 */
    public static final String PASSWORD_INVALID = "2008";
    
    /** 用户对应的水厂编号为空或者不合法 */
    public static final String WWID_INVALID = "2009";
    
    /**
     * 没有权限进行此类操作
     */
    public static final String PERMISSION_DENIED = "2010";
    
    public static final String IMEI_INVALID = "2011";
    
}
