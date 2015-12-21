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
import com.yinghua.translation.model.enumeration.ProductStatus;

@Entity
@Table(name = "cc_product")
public class Product implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name = "product_no")
	private String productNo; // product_no 商品编号
	@Column(name = "subject")
	private String subject; // subject 商品名称
	@Column(name = "product_desc")
	private String desc; // desc 描述
	@Enumerated(EnumType.STRING)
	@Column(name = "languages")
	private Language languages; // languages 语言
	@Column(name = "price")
	private BigDecimal price; // price 价格
	@Column(name="discount_price")
	private BigDecimal discountPrice;//优惠价格
	@Column(name="denomination")
	private BigDecimal denomination; //面额
	@Column(name = "surplus_call_duration")
	private Integer surplusCallDuration; // surplus_call_duration 通话时长
	@Column(name = "multimedia_message")
	private Integer multimediaMessage; // multimedia_message 多媒体消息
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ProductStatus status; // status 状态
	@Column(name = "type")
	private String type; // type 套餐类型
	@Column(name = "start_time")
	private Date startTime; // start_time 有效期
	@Column(name = "end_time")
	private Date endTime; // end_time 有效期
	@Column(name = "is_app_buy")
	private String isAppBuy; // is_app_buy 是否可在App购买
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间

	public Product()
	{}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public BigDecimal getDenomination() {
		return denomination;
	}

	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}

	public String getProductNo()
	{
		return productNo;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setProductNo(String productNo)
	{
		this.productNo = productNo;
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

	public Language getLanguages()
	{
		return languages;
	}

	public void setLanguages(Language languages)
	{
		this.languages = languages;
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

	public Integer getMultimediaMessage()
	{
		return multimediaMessage;
	}

	public void setMultimediaMessage(Integer multimediaMessage)
	{
		this.multimediaMessage = multimediaMessage;
	}

	public ProductStatus getStatus()
	{
		return status;
	}

	public void setStatus(ProductStatus status)
	{
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

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
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
