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

import com.yinghua.translation.model.enumeration.Language;

@Entity
@Table(name = "cc_member_package")
public class MemberPackage implements Serializable
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
	@Column(name = "tel_free_start")
	private Date telFreeStart; //tel_free_start
	@Column(name = "tel_free_end")
	private Date telFreeEnd; //tel_free_end
	@Column(name = "tel_gift_amount")
	private Integer telGiftAmount; //tel_gift_amount
	@Column(name = "tel_prefer_fee")
	private BigDecimal telPreferFee; //tel_prefer_fee
	@Column(name = "tel_prefer_start")
	private Date telPreferStart; //tel_prefer_start
	@Column(name = "tel_prefer_end")
	private Date telPreferEnd; //tel_prefer_end
	@Column(name = "im_gift_count")
	private Integer imGiftCount; //im_gift_count
	@Column(name = "im_prefer_fee")
	private BigDecimal imPreferFee; //im_prefer_fee
	@Column(name = "im_prefer_start")
	private Date imPreferStart; //im_prefer_start
	@Column(name = "im_prefer_end")
	private Date imPreferEnd; //im_prefer_end
	@Enumerated(EnumType.STRING)
	@Column(name = "languages")
	private Language languages; // language 语言
//	@Column(name = "product_no")
//	private String productNo; // product_no 商品编号
//	@Column(name = "subject")
//	private String subject; // subject 商品名称
//	@Column(name = "desc")
//	private String desc; // desc 描述
//	@Enumerated(EnumType.STRING)
//	@Column(name = "languages")
//	private Language languages; // languages 语言
//	@Column(name = "surplus_call_duration")
//	private Integer surplusCallDuration; // surplus_call_duration 剩余时长
//	@Column(name = "multimedia_message")
//	private Integer multimediaMessage; // multimedia_message 剩余多媒体消息
//	@Enumerated(EnumType.STRING)
//	@Column(name = "status")
//	private PackageStatus status; // status 状态
//	@Column(name = "type")
//	private String type; // type 套餐类型
//	@Column(name = "start_time")
//	private Date startTime; // start_time 有效期
//	@Column(name = "end_time")
//	private Date endTime; // end_time 有效期
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间

	public MemberPackage()
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
	
	public Date getTelFreeStart()
	{
		return telFreeStart;
	}

	public void setTelFreeStart(Date telFreeStart)
	{
		this.telFreeStart = telFreeStart;
	}

	public Date getTelFreeEnd()
	{
		return telFreeEnd;
	}

	public void setTelFreeEnd(Date telFreeEnd)
	{
		this.telFreeEnd = telFreeEnd;
	}

	public Integer getTelGiftAmount()
	{
		return telGiftAmount;
	}

	public void setTelGiftAmount(Integer telGiftAmount)
	{
		this.telGiftAmount = telGiftAmount;
	}

	public BigDecimal getTelPreferFee()
	{
		return telPreferFee;
	}

	public void setTelPreferFee(BigDecimal telPreferFee)
	{
		this.telPreferFee = telPreferFee;
	}

	public Date getTelPreferStart()
	{
		return telPreferStart;
	}

	public void setTelPreferStart(Date telPreferStart)
	{
		this.telPreferStart = telPreferStart;
	}

	public Date getTelPreferEnd()
	{
		return telPreferEnd;
	}

	public void setTelPreferEnd(Date telPreferEnd)
	{
		this.telPreferEnd = telPreferEnd;
	}

	public Integer getImGiftCount()
	{
		return imGiftCount;
	}

	public void setImGiftCount(Integer imGiftCount)
	{
		this.imGiftCount = imGiftCount;
	}

	public BigDecimal getImPreferFee()
	{
		return imPreferFee;
	}

	public void setImPreferFee(BigDecimal imPreferFee)
	{
		this.imPreferFee = imPreferFee;
	}

	public Date getImPreferStart()
	{
		return imPreferStart;
	}

	public void setImPreferStart(Date imPreferStart)
	{
		this.imPreferStart = imPreferStart;
	}

	public Date getImPreferEnd()
	{
		return imPreferEnd;
	}

	public void setImPreferEnd(Date imPreferEnd)
	{
		this.imPreferEnd = imPreferEnd;
	}

	public Language getLanguages()
	{
		return languages;
	}

	public void setLanguages(Language languages)
	{
		this.languages = languages;
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