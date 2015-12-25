package com.yinghua.translation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cc_pay_easy_log")
public class PayEasyLog implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "oid")
	private String oid;//订单编号
	@Column(name = "pmode")
	private String pmode;//支付方式
	@Column(name = "pstatus")
	private String pstatus;//支付状态
	@Column(name = "pstring")
	private String pstring;//支付
	@Column(name = "amount")
	private String amount;//支付金额
	@Column(name = "moneytype")
	private String moneytype;//支付币种
	@Column(name = "mac")
	private String mac;//数字签名
	@Column(name = "md5money")
	private String md5money;//商城数据签名
	@Column(name = "sign")
	private String sign;//商城数据签名
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getPmode() {
		return pmode;
	}
	public void setPmode(String pmode) {
		this.pmode = pmode;
	}
	public String getPstatus() {
		return pstatus;
	}
	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}
	public String getPstring() {
		return pstring;
	}
	public void setPstring(String pstring) {
		this.pstring = pstring;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMoneytype() {
		return moneytype;
	}
	public void setMoneytype(String moneytype) {
		this.moneytype = moneytype;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getMd5money() {
		return md5money;
	}
	public void setMd5money(String md5money) {
		this.md5money = md5money;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
