package com.wanzheng.wop.app.vo;

public class ConfigVO extends BaseVO
{
    private static final long serialVersionUID = 1L;
    //key值
    private String aa_key="";
    //值
    private String aa_value="";
    //描述
    private String aa_description = "";
    public String getAa_key()
    {
        return aa_key;
    }
    public void setAa_key(String aaKey)
    {
        aa_key = aaKey;
    }
    public String getAa_value()
    {
        return aa_value;
    }
    public void setAa_value(String aaValue)
    {
        aa_value = aaValue;
    }
    public String getAa_description()
    {
        return aa_description;
    }
    public void setAa_description(String aaDescription)
    {
        aa_description = aaDescription;
    }
    
    
}
