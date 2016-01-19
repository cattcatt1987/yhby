package com.yinghua.translation.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name ="cc_partner_preferential")
public class Preferential  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键 bigint
	@Column(name = "sale_strategy_no")
	private String salestrategyno; //优惠策略标识
	@Column(name = "preferential")
	private BigDecimal preferential; // 优惠力度
	@Column(name = "describe")
	private String describe; // 优惠描述
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPreferential() {
		return preferential;
	}
	public void setPreferential(BigDecimal preferential) {
		this.preferential = preferential;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSalestrategyno() {
		return salestrategyno;
	}
	public void setSalestrategyno(String salestrategyno) {
		this.salestrategyno = salestrategyno;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
