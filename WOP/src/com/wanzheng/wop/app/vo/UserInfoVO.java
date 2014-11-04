/*
 * 文 件 名:  UserInfoVO.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-10-14
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wanzheng.wop.app.vo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  brother80
 * @version  [版本号, 2014-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserInfoVO
{
    /**
     * 用户唯一标识
     */
    private String userId;
    
    /**
     * 登陆用户名
     */
    private String userName;
    
    /**
     * 登陆密码
     */
    private String passWord;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 用户角色  默认为普通用户
     */
    private int role;
    
    /**
     * 用户手机imei
     */
    private String imei;
    
    /**
     * 用户对应的水厂编号
     */
    private String wwId;
    
    /**
     * 注册时间
     */
    private long register_time;
    
    /**
     * 用户手机号
     */
    private String mobile;
    
    /**
     * 用户联系地址
     */
    private String address;
    
    /**
     * 用户性别 默认为男
     */
    private int sex;
    
    /**
     * 用户联系电话
     */
    private String tel;
    
    /**
     * 用户电子邮件地址
     */
    private String mail;
    
    /**
     * 用户QQ
     */
    private String qq;
    
    /**
     * 用户状态
     */
    private int state;
    
    /**
     * @return 返回 userId
     */
    public String getUserId()
    {
        return userId;
    }
    
    /**
     * @param 对userId进行赋值
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    /**
     * @return 返回 userName
     */
    public String getUserName()
    {
        return userName;
    }
    
    /**
     * @param 对userName进行赋值
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    /**
     * @return 返回 passWord
     */
    public String getPassWord()
    {
        return passWord;
    }
    
    /**
     * @param 对passWord进行赋值
     */
    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }
    
    /**
     * @return 返回 nickName
     */
    public String getNickName()
    {
        return nickName;
    }
    
    /**
     * @param 对nickName进行赋值
     */
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
    /**
     * @return 返回 role
     */
    public int getRole()
    {
        return role;
    }
    
    /**
     * @param 对role进行赋值
     */
    public void setRole(int role)
    {
        this.role = role;
    }
    
    /**
     * @return 返回 imei
     */
    public String getImei()
    {
        return imei;
    }
    
    /**
     * @param 对imei进行赋值
     */
    public void setImei(String imei)
    {
        this.imei = imei;
    }
    
    /**
     * @return 返回 wwId
     */
    public String getWwId()
    {
        return wwId;
    }
    
    /**
     * @param 对wwId进行赋值
     */
    public void setWwId(String wwId)
    {
        this.wwId = wwId;
    }
    
    /**
     * @return 返回 mobile
     */
    public String getMobile()
    {
        return mobile;
    }
    
    /**
     * @param 对mobile进行赋值
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    /**
     * @return 返回 address
     */
    public String getAddress()
    {
        return address;
    }
    
    /**
     * @param 对address进行赋值
     */
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    /**
     * @return 返回 sex
     */
    public int getSex()
    {
        return sex;
    }
    
    /**
     * @param 对sex进行赋值
     */
    public void setSex(int sex)
    {
        this.sex = sex;
    }
    
    /**
     * @return 返回 tel
     */
    public String getTel()
    {
        return tel;
    }
    
    /**
     * @param 对tel进行赋值
     */
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    /**
     * @return 返回 mail
     */
    public String getMail()
    {
        return mail;
    }
    
    /**
     * @param 对mail进行赋值
     */
    public void setMail(String mail)
    {
        this.mail = mail;
    }
    
    /**
     * @return 返回 qq
     */
    public String getQq()
    {
        return qq;
    }
    
    /**
     * @param 对qq进行赋值
     */
    public void setQq(String qq)
    {
        this.qq = qq;
    }
    
    /**
     * @return 返回 register_time
     */
    public long getRegister_time()
    {
        return register_time;
    }
    
    /**
     * @param 对register_time进行赋值
     */
    public void setRegister_time(long register_time)
    {
        this.register_time = register_time;
    }
    
    /**
     * @return 返回 state
     */
    public int getState()
    {
        return state;
    }
    
    /**
     * @param 对state进行赋值
     */
    public void setState(int state)
    {
        this.state = state;
    }
    
}
