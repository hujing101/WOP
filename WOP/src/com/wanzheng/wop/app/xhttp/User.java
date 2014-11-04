/*
 * 文件名：User.java
 * 描述： User.java
 * 修改人：zhangshaojun
 * 修改时间：2011-8-6
 * 修改内容：新增
 */
package com.wanzheng.wop.app.xhttp;

import com.wanzheng.wop.app.contants.Constants;
import com.wanzheng.wop.app.contants.ErrorCode;
import com.wanzheng.wop.app.vo.UserInfoVO;
import com.wanzheng.wop.intf.service.IUserService;
import com.wanzheng.wop.intf.xhttp.IUser;
import com.wanzheng.wop.kernel.exc.WOPException;
import com.wanzheng.wop.pub.util.StringUtil;

/**
 * 用户信息对外接口层
 * @author  brother80
 * @version  [版本号, 2014-10-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class User implements IUser
{
    private IUserService userService;
    
    public void setUserService(IUserService userService)
    {
        this.userService = userService;
    }
    
    public void loginExit()
    {
        
    }
    
    public UserInfoVO queryUserInfo(String yhbh) throws WOPException
    {
        return userService.queryUserInfo(yhbh);
    }
    
    public void regUser(UserInfoVO newUser, UserInfoVO manager)
            throws WOPException
    {
        String resultCode = ErrorCode.SUCCESS;
        if (null == newUser)
        {
            resultCode = ErrorCode.PARAM_NULL_INVALID;
        }
        else if (StringUtil.isEmpty(newUser.getUserName()))
        {
            resultCode = ErrorCode.USENAME_INVALID;
        }
        else if (StringUtil.isEmpty(newUser.getPassWord()))
        {
            resultCode = ErrorCode.PASSWORD_INVALID;
        }
        else if (Constants.ROLE_CUSTOMER == newUser.getRole()
                && StringUtil.isEmpty(newUser.getWwId()))
        {
            resultCode = ErrorCode.WWID_INVALID;
        }
        
        if (!ErrorCode.SUCCESS.equals(resultCode))
        {
            throw new WOPException(resultCode);
        }
        userService.regUser(newUser, manager);
    }
    
    public UserInfoVO login(String userName, String passWord, String imei)
            throws WOPException
    {
        if (StringUtil.isEmpty(userName))
        {
            throw new WOPException(ErrorCode.USENAME_INVALID);
        }
        else if (StringUtil.isEmpty(passWord))
        {
            throw new WOPException(ErrorCode.PASSWORD_INVALID);
        }
        return userService.login(userName, passWord, imei);
    }
}
