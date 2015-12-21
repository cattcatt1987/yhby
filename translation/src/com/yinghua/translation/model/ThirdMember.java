package com.yinghua.translation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yinghua.translation.model.enumeration.LoginType;

@Entity
@Table(name = "cc_third_member")
public class ThirdMember implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主健
	@Column(name = "member_number")
	private String memberNumber; // member_number 会员编号
	@Column(name = "open_id")
	private String openId; // open_id 第三方账号ID
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private LoginType type; // type 类型
	@Column(name="open_token")
	private String openToken; //open_token 第三方授权token
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间

	public ThirdMember()
	{
		// TODO Auto-generated constructor stub
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getMemberNumber()
	{
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber)
	{
		this.memberNumber = memberNumber;
	}

	public String getOpenId()
	{
		return openId;
	}

	public void setOpenId(String openId)
	{
		this.openId = openId;
	}

	public LoginType getType()
	{
		return type;
	}

	public void setType(LoginType type)
	{
		this.type = type;
	}

	public String getCreatePerson()
	{
		return createPerson;
	}

	public void setCreatePerson(String createPerson)
	{
		this.createPerson = createPerson;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getModifiedPerson()
	{
		return modifiedPerson;
	}

	public void setModifiedPerson(String modifiedPerson)
	{
		this.modifiedPerson = modifiedPerson;
	}

	public Date getModifiedTime()
	{
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime)
	{
		this.modifiedTime = modifiedTime;
	}

	public String getOpenToken()
	{
		return openToken;
	}

	public void setOpenToken(String openToken)
	{
		this.openToken = openToken;
	}

}
