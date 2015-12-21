package com.yinghua.translation.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "cc_translation_record")
public class TranslationRecord implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name = "member_number")
	private String memberNumber; // member_number 会员编号
	@Column(name = "translation_type")
	private String translationType; // translation_type 翻译类型
	@Column(name = "start_time")
	private Date startTime; // start_time 开始时间
	@Column(name = "end_time")
	private Date endTime; // end_time 结束时间
	@Column(name = "translation_date")
	private Date transDate; // translation_date 翻译日期
	@Column(name = "translation_duration")
	private Integer transTimes; // translation_duration 翻译时长
	@Column(name = "language")
	private String language; // language 翻译语种
	@Column(name = "emergency_call_id")
	private String emergencyCallId; // emergency_call_id 漫游地
	@Column(name = "country_abbreviation_zh")
	private String countryAbbreviationZh; // country_abbreviation_zh 漫游国家名称
	@Column(name = "translation_costs")
	private BigDecimal fee; // translation_costs 翻译费用
	@Column(name = "translation_surcharge")
	private BigDecimal transSurcharge; // translation_surcharge 附加费
	@Column(name = "work_id")
	private String workId;// 译员工号
	@Column(name = "caller_phone")
	private String callerPhone;// 主要号码
	@Column(name = "remark")
	private String remark; // remark 备注说明
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间

	public TranslationRecord()
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

	public String getTranslationType()
	{
		return translationType;
	}

	public void setTranslationType(String translationType)
	{
		this.translationType = translationType;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public Date getTransDate()
	{
		return transDate;
	}

	public void setTransDate(Date transDate)
	{
		this.transDate = transDate;
	}

	public Integer getTransTimes()
	{
		return transTimes;
	}

	public void setTransTimes(Integer transTimes)
	{
		this.transTimes = transTimes;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getEmergencyCallId()
	{
		return emergencyCallId;
	}

	public void setEmergencyCallId(String emergencyCallId)
	{
		this.emergencyCallId = emergencyCallId;
	}

	public String getCountryAbbreviationZh()
	{
		return countryAbbreviationZh;
	}

	public void setCountryAbbreviationZh(String countryAbbreviationZh)
	{
		this.countryAbbreviationZh = countryAbbreviationZh;
	}

	public BigDecimal getFee()
	{
		return fee;
	}

	public void setFee(BigDecimal fee)
	{
		this.fee = fee;
	}

	public BigDecimal getTransSurcharge()
	{
		return transSurcharge;
	}

	public void setTransSurcharge(BigDecimal transSurcharge)
	{
		this.transSurcharge = transSurcharge;
	}

	public String getWorkId()
	{
		return workId;
	}

	public void setWorkId(String workId)
	{
		this.workId = workId;
	}

	public String getCallerPhone()
	{
		return callerPhone;
	}

	public void setCallerPhone(String callerPhone)
	{
		this.callerPhone = callerPhone;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
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
	
}
