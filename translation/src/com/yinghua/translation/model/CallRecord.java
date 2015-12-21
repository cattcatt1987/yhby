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
@Table(name="cc_call_record")
public class CallRecord implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; //id	主键
	@Column(name="member_number")
	private String userNumber; //member_number	会员编号
	@Column(name="calling_number")
	private String caller; //calling_number	主叫号码
	@Column(name="call_id")
	private String callId; //会话标识
	@Column(name="work_id")
	private String workId;
	@Column(name="called_number")
	private String callee; //called_number	被号号码
	@Column(name="type")
	private String type; //type	呼叫类型
	@Column(name="line")
	private String line; //line	线路类型
	@Column(name="start_time")
	private Date startTime; //start_time	开始时间
	@Column(name="end_time")
	private Date endTime; //end_time	结束时间
	@Column(name="call_date")
	private Date callDate; //call_date	通话日期
	@Column(name="call_duration")
	private Integer callDuration; //call_duration	通话时长/翻译时长
	@Column(name="emergency_call_id")
	private String emergencyCallId; //emergency_call_id	漫游地
	@Column(name="country_abbreviation_zh")
	private String countryAbbreviationZh; //country_abbreviation_zh	漫游国家名称
	@Enumerated(EnumType.STRING)
	@Column(name = "languages")
	private Language languages; // language 语言
	@Column(name="call_costs")
	private BigDecimal callCosts; //call_costs	通话费用
	@Column(name="call_surcharge")
	private BigDecimal callSurcharge; //call_surcharge	附加费
	@Column(name="remark")
	private String remark; //remark	备注说明
	@Column(name="create_person")
	private String createPerson; //create_person	创建人
	@Column(name="create_time")
	private Date createTime; //create_time	创建时间
	@Column(name="modified_person")
	private String modifiedPerson; //modified_person	修改人
	@Column(name="modified_time")
	private Date modifiedTime; //modified_time	修改时间
	
	public CallRecord()
	{
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public Integer getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(Integer callDuration) {
		this.callDuration = callDuration;
	}

	public String getEmergencyCallId() {
		return emergencyCallId;
	}

	public void setEmergencyCallId(String emergencyCallId) {
		this.emergencyCallId = emergencyCallId;
	}

	public String getCountryAbbreviationZh() {
		return countryAbbreviationZh;
	}

	public void setCountryAbbreviationZh(String countryAbbreviationZh) {
		this.countryAbbreviationZh = countryAbbreviationZh;
	}

	public Language getLanguages() {
		return languages;
	}

	public void setLanguages(Language languages) {
		this.languages = languages;
	}

	public BigDecimal getCallCosts() {
		return callCosts;
	}

	public void setCallCosts(BigDecimal callCosts) {
		this.callCosts = callCosts;
	}

	public BigDecimal getCallSurcharge() {
		return callSurcharge;
	}

	public void setCallSurcharge(BigDecimal callSurcharge) {
		this.callSurcharge = callSurcharge;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getModifiedPerson() {
		return modifiedPerson;
	}

	public void setModifiedPerson(String modifiedPerson) {
		this.modifiedPerson = modifiedPerson;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}
