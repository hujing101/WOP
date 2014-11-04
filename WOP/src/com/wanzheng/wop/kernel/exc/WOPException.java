package com.wanzheng.wop.kernel.exc;

/**
 * 异常类
 */
public class WOPException extends Exception
{
    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    // 错误描述
    private String desc = null;

    /**
     * 默认构造函数
     * 
     * @param code
     *            错误码
     */
    public WOPException(String code)
    {
        super(code);

        this.code = code;

    }

    /**
     * 默认构造函数
     * 
     * @param code
     *            错误码
     * 
     * @param msg
     *            错误描述信息
     * @param sysException
     *            是否是系统异常，true表示是，false表示不是系统异常
     * @param e
     *            异常对象
     */
    public WOPException(String code, String msg, Throwable e)
    {
        super(code + ", " + msg, e);

        this.code = code;

        this.msg = msg;

    }

    /**
     * 错误码
     * 
     * @return 返回 code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * 描述信息
     * 
     * @return 返回 msg
     */
    public String getMsg()
    {
        return msg;
    }

    /**
     * 返回desc
     * 
     * @return 返回desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * 设置desc
     * 
     * @param desc
     *            要设置的desc
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}