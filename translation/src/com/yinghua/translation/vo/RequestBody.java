package com.yinghua.translation.vo;

public class RequestBody implements java.io.Serializable
{
 
	private String mobile;
	private String pwd;
	private String country;
	private String verify_code;
	private String type;
	private Long uid;
	private String code;
	private String purchased;
	
	public RequestBody()
	{
		// TODO Auto-generated constructor stub
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getVerify_code()
	{
		return verify_code;
	}

	public void setVerify_code(String verify_code)
	{
		this.verify_code = verify_code;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Long getUid()
	{
		return uid;
	}

	public void setUid(Long uid)
	{
		this.uid = uid;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getPurchased()
	{
		return purchased;
	}

	public void setPurchased(String purchased)
	{
		this.purchased = purchased;
	}

}
