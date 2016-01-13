package com.yinghua.translation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cc_partner_code")
public class PartnerCode implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name="code")
	private String code;//邀请码
	@Column(name="partner_name")
	private String partnerName;//公司名称
	@Column(name="partner_no")
	private String partnerNo;//公司标识
	@Column(name="sale_strategy_no")
	private String saleStrategyNo;//优惠策略标识
	@Column(name="create_time")
	private Date createTime;//创建时间
	@Column(name="service_start_time")
	private Date serviceStartTime;//合作开始时间
	@Column(name="service_end_time")
	private Date serviceEndTime;//合作有效截止时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getSaleStrategyNo() {
		return saleStrategyNo;
	}
	public void setSaleStrategyNo(String saleStrategyNo) {
		this.saleStrategyNo = saleStrategyNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(Date serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public Date getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(Date serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	
}
