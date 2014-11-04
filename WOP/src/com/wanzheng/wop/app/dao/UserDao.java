/*
 * 文 件 名:  UserDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-10-14
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wanzheng.wop.app.dao;

import java.sql.SQLException;

import com.wanzheng.wop.app.contants.ErrorCode;
import com.wanzheng.wop.app.vo.UserInfoVO;
import com.wanzheng.wop.intf.dao.IUserDao;
import com.wanzheng.wop.kernel.exc.WOPException;
import com.wanzheng.wop.pub.util.IDGenerator;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  brother80
 * @version  [版本号, 2014-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserDao extends BaseDao implements IUserDao
{
    
    /**
     * @param yhbh
     * @return
     * @throws WOPException
     */
    
    public UserInfoVO queryUserInfo(String yhbh) throws WOPException
    {
        try
        {
            return (UserInfoVO) this.getSqlMapClient()
                    .queryForObject("userinfo.queryUserInfo", yhbh);
        }
        catch (SQLException e)
        {
            throw new WOPException(ErrorCode.QUERY_EXCEPTION, "db query fail",
                    e);
        }
    }
    
    public void regUser(UserInfoVO newUser) throws WOPException
    {
        UserInfoVO someOne = getUserInfoByName(newUser.getUserName());
        if (null != someOne)
        {
            throw new WOPException(ErrorCode.USER_HAS_EXIST);
        }
        newUser.setRegister_time(System.currentTimeMillis() / 1000);
        newUser.setUserId(IDGenerator.generateUserID());
        try
        {
            this.getSqlMapClient().insert("userinfo.regUser", newUser);
        }
        catch (SQLException e)
        {
            throw new WOPException(ErrorCode.INSERT_EXCEPTION,
                    "add new user fail", e);
        }
    }
    
    public UserInfoVO getUserInfoByName(String userName) throws WOPException
    {
        try
        {
            return (UserInfoVO) this.getSqlMapClient()
                    .queryForObject("userinfo.getUserInfoByName", userName);
        }
        catch (SQLException e)
        {
            throw new WOPException(ErrorCode.QUERY_EXCEPTION, "db query fail",
                    e);
        }
    }
    
}
