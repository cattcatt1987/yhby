package com.yinghua.translation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cc_pay_weixin_log")
public class PayWeixinLog implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="appid")
	private String appid;//微信分配的公众账号ID（企业号corpid即为此appId）
	@Column(name="mch_id")
	private String mchId;//微信支付分配的商户号
	@Column(name="device_info")
	private String deviceInfo;//微信支付分配的终端设备号
	@Column(name="nonce_str")
	private String nonceStr;//随机字符串，不长于32位
	@Column(name="sign")
	private String sign;//签名
	@Column(name="result_code")
	private String resultCode;//业务结果 SUCCESS/FAIL
	@Column(name="err_code")
	private String errCode;//错误代码
	@Column(name="err_code_des")
	private String errCodeDes;//错误代码描述
	@Column(name="openid")
	private String openid;//用户在商户appid下的唯一标识
	@Column(name="is_subscribe")
	private String isSubscribe;//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	@Column(name="trade_type")
	private String tradeType;//交易类型JSAPI、NATIVE、APP
	@Column(name="bank_type")
	private String bankType;//付款银行
	@Column(name="total_fee")
	private Integer totalFee;//总金额
	@Column(name="fee_type")
	private String feeType;//货币种类
	@Column(name="cash_fee")
	private Integer cashFee;//现金支付金额
	@Column(name="cash_fee_type")
	private String cashFeeType;//现金支付货币种类
	@Column(name="transation_id")
	private String transationId;//微信支付订单号
	@Column(name="out_trade_no")
	private String outTradeNo;//商户系统的订单号
	@Column(name="attach")
	private String attach;//商家数据包，原样返回
	@Column(name="time_end")
	private String timeEnd;//支付完成时间，格式为yyyyMMddHHmmss
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public String getCashFeeType() {
		return cashFeeType;
	}
	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}
	public String getTransationId() {
		return transationId;
	}
	public void setTransationId(String transationId) {
		this.transationId = transationId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Integer getCashFee() {
		return cashFee;
	}
	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}
	
}
