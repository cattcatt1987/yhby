package com.yinghua.translation.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.yinghua.translation.model.enumeration.CredentialsType;
import com.yinghua.translation.model.enumeration.Gender;
import com.yinghua.translation.model.enumeration.MemberStatus;
import com.yinghua.translation.model.enumeration.MemberType;

@Entity
@Table(name = "cc_member")
public class Member implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键 bigint
	@Column(name = "member_number")
	private String memberNumber; // member_number 会员编号 varchar(32)
	@Column(name = "nickname")
	private String nickname; // nickname 会员昵称 varchar(32)
	@Column(name = "member_name")
	private String memberName; // member_name 会员姓名 varchar(32)
	@Enumerated(EnumType.STRING)
	@Column(name = "member_gender")
	private Gender memberGender; // member_gender 会员姓别 enum
	@Column(name = "date_of_birth")
	private Date dateOfBirth; // date_of_birth 出生日期 date
	@Enumerated(EnumType.STRING)
	@Column(name = "credentials_type")
	private CredentialsType credentialsType; // credentials_type 证件类型 enum
	@Column(name = "credentials_number")
	private String credentialsNumber; // credentials_number 证件编号 varchar(32)
	@Column(name = "email")
	private String email; // email 电子邮箱 varchar(64)
	@Column(name="voip")
	private String voip;

	@Column(name = "im_token")
	private String imToken;
	@Column(name = "mobile_phone")
	private String mobilePhone; // mobile_phone 手机 varchar(16)
	@Column(name = "password")
	private String password; // password 会员密码 varchar(64)
	@Column(name = "accumulate_points")
	private Integer accumulatePoints; // accumulate_points 累计积分 int
	@Column(name = "member_face")
	private String memberFace; // member_face 个人头像 varchar(64)
	@Column(name = "location")
	private String location; // location 最近一次登录所在国家 varchar(128)
	@Column(name = "longitude")
	private String longitude; // longitude 最近一次登录所在经度坐标 varchar(32)
	@Column(name = "latitude")
	private String latitude; // latitude 最近一次登录所在纬度坐标 varchar(32)
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private MemberStatus status; // status 会员状态 enum
	@Column(name = "is_completed")
	private Integer isCompleted; // is_completed 是否已完善资料
	@Enumerated(EnumType.STRING)
	@Column(name = "member_type")
	private MemberType memberType; // member_type 会员类型 enum
	@Column(name = "last_time")
	private Date lastTime; // last_time 最后一次登录时间 datetime
	@Column(name = "login_times")
	private Integer loginTimes; // login_times 登录次数 int
	@Column(name = "cmpy_id")
	private Long cmpyId; // cmpy_id 企业标识
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人 varchar(32)
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间 datetime
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人 varchar(32)
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间 datetime
	@Column(name = "local_lang")
	private String localLang; // local_lang 所在国家语言
	@Column(name = "contacts")
	private String contacts; // 手机通信录

	public Member()
	{}

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

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getMemberName()
	{
		return memberName;
	}

	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}

	public Gender getMemberGender()
	{
		return memberGender;
	}

	public void setMemberGender(Gender memberGender)
	{
		this.memberGender = memberGender;
	}

	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public CredentialsType getCredentialsType()
	{
		return credentialsType;
	}

	public void setCredentialsType(CredentialsType credentialsType)
	{
		this.credentialsType = credentialsType;
	}

	public String getCredentialsNumber()
	{
		return credentialsNumber;
	}

	public void setCredentialsNumber(String credentialsNumber)
	{
		this.credentialsNumber = credentialsNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getImToken()
	{
		return imToken;
	}

	public void setImToken(String imToken)
	{
		this.imToken = imToken;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getAccumulatePoints()
	{
		return accumulatePoints;
	}

	public void setAccumulatePoints(Integer accumulatePoints)
	{
		this.accumulatePoints = accumulatePoints;
	}

	public String getMemberFace()
	{
		return memberFace;
	}

	public void setMemberFace(String memberFace)
	{
		this.memberFace = memberFace;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}


	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public MemberStatus getStatus()
	{
		return status;
	}

	public void setStatus(MemberStatus status)
	{
		this.status = status;
	}

	public Integer getIsCompleted()
	{
		return isCompleted;
	}

	public void setIsCompleted(Integer isCompleted)
	{
		this.isCompleted = isCompleted;
	}

	public MemberType getMemberType()
	{
		return memberType;
	}

	public void setMemberType(MemberType memberType)
	{
		this.memberType = memberType;
	}

	public Date getLastTime()
	{
		return lastTime;
	}

	public void setLastTime(Date lastTime)
	{
		this.lastTime = lastTime;
	}

	public Integer getLoginTimes()
	{
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes)
	{
		this.loginTimes = loginTimes;
	}

	public Long getCmpyId()
	{
		return cmpyId;
	}

	public void setCmpyId(Long cmpyId)
	{
		this.cmpyId = cmpyId;
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

	public String getLocalLang()
	{
		return localLang;
	}

	public void setLocalLang(String localLang)
	{
		this.localLang = localLang;
	}

	public String getContacts()
	{
		return contacts;
	}

	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}

	public String getVoip() {
		return voip;
	}

	public void setVoip(String voip) {
		this.voip = voip;
	}
	
	
}
