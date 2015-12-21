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
@Table(name = "cc_timed_task")
public class TimedTask implements Serializable
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
	@Column(name = "call_id")
	private String callId; // 会话标识
	@Column(name = "tariff")
	private BigDecimal tariff; // subject 商品名称
	@Enumerated(EnumType.STRING)
	@Column(name = "languages")
	private Language languages; // languages 语言
	@Column(name = "access_type")
	private String accessType; // price 价格
	@Column(name="access_line")
	private String accessLine;
	@Column(name="calling_number")
	private String caller; //calling_number	主叫号码
	@Column(name="called_number")
	private String callee; //called_number	被号号码
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	

	public TimedTask()
	{}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public BigDecimal getTariff() {
		return tariff;
	}

	public void setTariff(BigDecimal tariff) {
		this.tariff = tariff;
	}

	public Language getLanguages() {
		return languages;
	}

	public void setLanguages(Language languages) {
		this.languages = languages;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getAccessLine() {
		return accessLine;
	}

	public void setAccessLine(String accessLine) {
		this.accessLine = accessLine;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}
	
	

}
