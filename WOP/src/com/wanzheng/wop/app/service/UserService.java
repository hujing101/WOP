/*
 * 文 件 名:  UserService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-10-16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wanzheng.wop.app.service;

import com.wanzheng.wop.app.contants.Constants;
import com.wanzheng.wop.app.contants.ErrorCode;
import com.wanzheng.wop.app.vo.UserInfoVO;
import com.wanzheng.wop.intf.dao.IUserDao;
import com.wanzheng.wop.intf.service.IUserService;
import com.wanzheng.wop.kernel.exc.WOPException;
import com.wanzheng.wop.pub.util.StringUtil;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  brother80
 * @version  [版本号, 2014-10-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserService implements IUserService
{
    private IUserDao userDao;
    
    /**
     * @return 返回 userDao
     */
    public IUserDao getUserDao()
    {
        return userDao;
    }
    
    /**
     * @param 对userDao进行赋值
     */
    public void setUserDao(IUserDao userDao)
    {
        this.userDao = userDao;
    }
    
    public UserInfoVO queryUserInfo(String yhbh) throws WOPException
    {
        return userDao.queryUserInfo(yhbh);
    }
    
    public void regUser(UserInfoVO newUser, UserInfoVO manager)
            throws WOPException
    {
        String resultCode = ErrorCode.SUCCESS;
        //需要注册的用户是抄表员
        if (Constants.ROLE_READER == newUser.getRole())
        {
            if (null == manager
                    || StringUtil.isEmpty(manager.getUserName(),
                            manager.getPassWord()))
            {
                resultCode = ErrorCode.PERMISSION_DENIED;
            }
            else
            {
                //校验超级管理员权限
                resultCode = checkManager(manager);
            }
            if (!ErrorCode.SUCCESS.equals(resultCode))
            {
                throw new WOPException(resultCode);
            }
        }
        userDao.regUser(newUser);
    }
    
    public UserInfoVO login(String userName, String passWord, String imei)
            throws WOPException
    {
        UserInfoVO userInfoVO = userDao.getUserInfoByName(userName);
        if (null == userInfoVO)
        {
            throw new WOPException(ErrorCode.USER_NOT_EXIST);
        }
        else if (!passWord.equals(userInfoVO.getPassWord()))
        {
            throw new WOPException(ErrorCode.PASSWORD_INVALID);
        }
        else if (Constants.ROLE_READER == userInfoVO.getRole()
                && !imei.equals(userInfoVO.getImei()))
        {
            //如果是抄表员,且IMEI号不正确
            throw new WOPException(ErrorCode.IMEI_INVALID);
        }
        return userInfoVO;
    }
    
    private String checkManager(UserInfoVO manager) throws WOPException
    {
        UserInfoVO userInfoVO = userDao.getUserInfoByName(manager.getUserName());
        if (null == userInfoVO)
        {
            return ErrorCode.USER_NOT_EXIST;
        }
        else if (!manager.getPassWord().equals(userInfoVO.getPassWord()))
        {
            return ErrorCode.USER_NOT_EXIST;
        }
        return ErrorCode.SUCCESS;
    }
}
