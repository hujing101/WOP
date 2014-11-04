/*
 * 文 件 名:  User.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-11-2
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wanzheng.wop.app.client.service;

import com.wanzheng.wop.app.client.http.HttpHandler;
import com.wanzheng.wop.app.vo.UserInfoVO;
import com.wanzheng.wop.kernel.exc.WOPException;

public class User
{
    /**
     * 开户接口
     * @param newUser 新注册用户信息
     * @param manager 管理员信息，普通用户注册可为空
     * @throws WOPException
     * 
     * @exception throws [违例类型] [违例说明]
     */
    public void regUser(UserInfoVO newUser, UserInfoVO manager)
            throws WOPException
    {
        HttpHandler.invoke("regUser", "IUser", newUser, manager);
    }
    
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
            throws WOPException
    {
        return (UserInfoVO) HttpHandler.invoke("login",
                "IUser",
                userName,
                passWord,
                imei);
    }
    
    public static void main(String[] args)
    {
        HttpHandler.init(8080, "127.0.0.1");
        try
        {
            UserInfoVO newUser = new UserInfoVO();
            newUser.setUserName("wanglong");
            newUser.setPassWord("123456");
            newUser.setWwId("001");
            new User().regUser(newUser, null);
        }
        catch (WOPException e)
        {
            e.printStackTrace();
        }
    }
}
