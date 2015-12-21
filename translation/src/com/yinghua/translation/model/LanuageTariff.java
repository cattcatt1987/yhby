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
@Table(name = "cc_lanuage_tariff")
public class LanuageTariff implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name = "tariff")
	private BigDecimal tariff; // 通话资费
	@Column(name = "msg_tariff")
	private BigDecimal msgTariff; // subject 商品名称
	@Enumerated(EnumType.STRING)
	@Column(name = "languages")
	private Language languages; // languages 语言
	@Column(name = "rate")
	private Float rate;	//费率
	

	public LanuageTariff()
	{}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getTariff()
	{
		return tariff;
	}


	public void setTariff(BigDecimal tariff)
	{
		this.tariff = tariff;
	}


	public BigDecimal getMsgTariff()
	{
		return msgTariff;
	}


	public void setMsgTariff(BigDecimal msgTariff)
	{
		this.msgTariff = msgTariff;
	}


	public Float getRate() {
		return rate;
	}


	public void setRate(Float rate) {
		this.rate = rate;
	}


	public Language getLanguages()
	{
		return languages;
	}


	public void setLanguages(Language languages)
	{
		this.languages = languages;
	}

}
