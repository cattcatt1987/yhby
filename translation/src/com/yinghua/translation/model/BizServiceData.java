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
@Table(name = "cc_biz_service_data")
public class BizServiceData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name="phone")
	private String phone;//主叫号
	@Column(name="order_num")
	private String orderNum;//订单号
	@Column(name="language")
	private String language;//语种
	@Column(name="driver_phone")
	private String driverPhone;//司机号码
	@Column(name="from_address")
	private String starting;//出发地址
	@Column(name="destination")
	private String destination;//目的地址
	@Column(name="location")
	private String location;//当前地址
	@Column(name="create_time")
	private Date createTime;//创建时间
	@Column(name="order_time")
	private Date orderTime;//订单时间
	@Column(name="call_sign")
	private String callSign;//通话唯一标识
	@Column(name="lng")
	private double lng;
	@Column(name="lat")
	private double lat;
	
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
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getStarting() {
		return starting;
	}
	public void setStarting(String starting) {
		this.starting = starting;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}

}
