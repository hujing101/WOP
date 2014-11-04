/*
 * 文件名：IUserService.java
 * 描述： IUserService.java
 * 修改人：zhangshaojun
 * 修改时间：2011-8-6
 * 修改内容：新增
 */
package com.wanzheng.wop.intf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wanzheng.wop.app.vo.UserInfoVO;
import com.wanzheng.wop.kernel.exc.WOPException;

/**
 * 添加描述
 * <p>
 * 
 * @author zhangshaojun
 */
public interface IUserService
{
    public Log userLog = LogFactory.getLog(IUserService.class);
    
    public UserInfoVO queryUserInfo(String yhbh) throws WOPException;
    
    /**
     * 开户接口
     * @param newUser 新注册用户信息
     * @param manager 管理员信息，普通用户注册可为空
     * @throws WOPException
     * 
     * @exception throws [违例类型] [违例说明]
     */
    public void regUser(UserInfoVO newUser, UserInfoVO manager)
            throws WOPException;
    
    /**
     * 用户登陆
     * @param userName
     * @param passWord
     * @param imei
     * @throws WOPException [参数说明]
     * 
     * @return UserInfoVO [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public UserInfoVO login(String userName, String passWord, String imei)
            throws WOPException;
}
