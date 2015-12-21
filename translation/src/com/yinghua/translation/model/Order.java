package com.yinghua.translation.model;

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
import com.yinghua.translation.model.enumeration.OrderStatus;

@Entity
@Table(name = "cc_order")
public class Order implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "order_no")
	private String orderNo; // order_no
	@Column(name = "member_number")
	private String memberNumber; // member_number 会员编号
	@Column(name = "prod_id")
	private String prodId; // prod_id
	@Column(name = "prod_type")
	private String prodType; // prod_type
	@Column(name = "prod_name")
	private String prodName; // prod_name
	@Column(name = "order_desc")
	private String desc; // desc
	@Column(name = "price")
	private BigDecimal price; // price
	@Enumerated(EnumType.STRING)
	@Column(name = "languages")
	private Language languages; // language 语言
	@Column(name = "detail")
	private String detail; // detail
	@Column(name = "small_url")
	private String smallUrl; // small_url
	@Column(name = "large_url")
	private String largeUrl; // large_url
	@Column(name = "amount")
	private BigDecimal amount; // amount
	@Column(name = "pay_way")
	private String payWay; // pay_way
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private OrderStatus state; // state
	@Column(name="credential")
	private String credential;
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间

	public Order()
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

	public String getProdId()
	{
		return prodId;
	}

	public void setProdId(String prodId)
	{
		this.prodId = prodId;
	}

	public String getProdType()
	{
		return prodType;
	}

	public void setProdType(String prodType)
	{
		this.prodType = prodType;
	}

	public String getProdName()
	{
		return prodName;
	}

	public void setProdName(String prodName)
	{
		this.prodName = prodName;
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

	public Language getLanguages()
	{
		return languages;
	}

	public void setLanguages(Language languages)
	{
		this.languages = languages;
	}

	public String getDetail()
	{
		return detail;
	}

	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	public String getSmallUrl()
	{
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl)
	{
		this.smallUrl = smallUrl;
	}

	public String getLargeUrl()
	{
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl)
	{
		this.largeUrl = largeUrl;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getPayWay()
	{
		return payWay;
	}

	public void setPayWay(String payWay)
	{
		this.payWay = payWay;
	}

	public OrderStatus getState()
	{
		return state;
	}

	public void setState(OrderStatus state)
	{
		this.state = state;
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

	public String getCredential()
	{
		return credential;
	}

	public void setCredential(String credential)
	{
		this.credential = credential;
	}
	
}
