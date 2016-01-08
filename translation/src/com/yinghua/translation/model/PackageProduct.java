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

import com.yinghua.translation.model.enumeration.PackageStatus;

@Entity
@Table(name = "cc_package_product")
public class PackageProduct implements Serializable
{
	private static final long serialVersionUID = -5011159453156990393L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name = "package_no")
	private String packageNo; // product_no 套餐编号
	@Column(name = "subject")
	private String subject; // subject 套餐名称
	@Column(name = "desc")
	private String desc; // packageDesc 套餐描述
	@Column(name = "price")
	private BigDecimal price; // price 套餐价格
	@Column(name="discount_price")
	private BigDecimal discountPrice;//优惠价格
	@Column(name = "surplus_call_duration")
	private Integer surplusCallDuration; // surplus_call_duration 通话时长
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private PackageStatus status; // status 状态
	@Column(name = "type")
	private String type; // type 套餐类型 1按次 2按天
	@Column(name = "is_app_buy")
	private String isAppBuy; // is_app_buy 是否可在App购买
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 截止时间
	@Column(name = "remark")
	private String remark;// remark 备注
	
	public PackageProduct()
	{}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}



	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}


	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public Integer getSurplusCallDuration()
	{
		return surplusCallDuration;
	}

	public void setSurplusCallDuration(Integer surplusCallDuration)
	{
		this.surplusCallDuration = surplusCallDuration;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PackageStatus getStatus() {
		return status;
	}

	public void setStatus(PackageStatus status) {
		this.status = status;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getIsAppBuy()
	{
		return isAppBuy;
	}

	public void setIsAppBuy(String isAppBuy)
	{
		this.isAppBuy = isAppBuy;
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
