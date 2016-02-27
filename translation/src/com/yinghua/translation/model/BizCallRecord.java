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
@Table(name = "cc_biz_call_record")
public class BizCallRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name="phone")
	private String phone;
	@Column(name="driver_phone")
	private String driverPhone;
	@Column(name="call_sign")
	private String callSign;
	@Column(name="order_num")
	private String orderNum;
	@Column(name="start_time")
	private Date startTime;
	@Column(name="billing_start_time")
	private Date billingStartTime;
	@Column(name="end_time")
	private Date endTime;
	@Column(name="worker_id")
	private String workerId;
	@Column(name="language")
	private String language;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getBillingStartTime() {
		return billingStartTime;
	}
	public void setBillingStartTime(Date billingStartTime) {
		this.billingStartTime = billingStartTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
}
