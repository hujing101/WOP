/*
 * 文 件 名:  IUserDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-10-14
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wanzheng.wop.intf.dao;

import com.wanzheng.wop.app.vo.UserInfoVO;
import com.wanzheng.wop.kernel.exc.WOPException;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  brother80
 * @version  [版本号, 2014-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IUserDao
{
    /**
     * 根据用户编号查询用户信息
     * @param yhbh
     * @return
     * @throws WOPException [参数说明]
     * 
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public UserInfoVO queryUserInfo(String yhbh) throws WOPException;
    
    /**
     * 开户接口
     * @param newUser 新注册用户信息
     * @throws WOPException
     * 
     * @exception throws [违例类型] [违例说明]
     */
    public void regUser(UserInfoVO newUser) throws WOPException;
    
    /**
     * 用户登陆
     * @param userName
     * @throws WOPException [参数说明]
     * 
     * @return UserInfoVO [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public UserInfoVO getUserInfoByName(String userName) throws WOPException;
}
