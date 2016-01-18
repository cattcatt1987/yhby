package com.yinghua.translation.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yinghua.translation.model.enumeration.OrderStatus;
import com.yinghua.translation.model.enumeration.OrderUseStatus;

/**
 * 用户订购记录及剩余分钟数
 * @author Administrator
 *
 */
@Entity
@Table(name = "cc_member_order")
public class MemberOrder implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "order_no")
	private String orderNo; // order_no 订单编号
	@Column(name = "member_number")
	private String memberNumber; // member_number 会员编号
	@Column(name = "package_no")
	private String packageNo; // package_no 套餐编号
	@Column(name = "package_type")
	private String packageType; // package_type 套餐类型 1.按天 2.按次 3.自由组合
	@Column(name = "package_name")
	private String packageName; // package_name 套餐名称
	@Column(name = "package_desc")
	private String packageDesc; // package_desc 套餐描述
	@Column(name = "order_time")
	@Temporal(TemporalType.DATE)
	private Date orderTime; // order_time 下单时间
	@Column(name = "surplus_call_duration")
	private Integer surplusCallDuration; // surplus_call_duration 剩余通话时长
	@Column(name = "state")
	private OrderStatus state; // state 支付状态
	@Column(name = "use_state")
	private OrderUseStatus useState; // use_state 使用状态
	@Column(name = "service_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceTime; // service_time 服务有效时间
	@Column(name = "use_date")
	private int useDate; // use_date 使用周期 为按天套餐使用
	@Column(name = "order_price")
	private String orderPrice; //order_price订单总额
	@Column(name = "pay_way")
	private String payWay; // pay_way 支付方式
	@Column(name = "remark")
	private String remark;// remark 为自由组合套餐预留，存放单项及次数关系
	@Column(name = "service_end_time")
	private Date serviceEndTime;//服务有效截止日期，为按次套餐使用
	@Column(name = "pay_no")
	private String payNo;//支付标识
	
	public MemberOrder()
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

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getSurplusCallDuration() {
		return surplusCallDuration;
	}

	public void setSurplusCallDuration(Integer surplusCallDuration) {
		this.surplusCallDuration = surplusCallDuration;
	}

	public OrderStatus getState() {
		return state;
	}

	public void setState(OrderStatus state) {
		this.state = state;
	}

	public Date getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getUseDate() {
		return useDate;
	}

	public void setUseDate(int useDate) {
		this.useDate = useDate;
	}

	public String getPackageDesc() {
		return packageDesc;
	}

	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public OrderUseStatus getUseState() {
		return useState;
	}

	public void setUseState(OrderUseStatus useState) {
		this.useState = useState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(Date serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}


	
}
