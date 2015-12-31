package com.yinghua.translation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yinghua.translation.model.enumeration.CommonType;

@Entity
@Table(name = "cc_common_data")
public class CommonData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键 bigint
	@Enumerated(EnumType.STRING)
	@Column(name = "common_type")
	private CommonType commonType;//通用数据类型 1常见问题 2消息
	@Column(name = "belong_type")
	private String belongType;//内容所属类别
	@Column(name = "country")
	private String country;//所属国家
	@Column(name = "location")
	private String location;//所属洲
	@Column(name = "content")
	private String content;//内容
	@Column(name = "level")
	private Integer level;//排序
	@Column(name = "title")
	private String title;//标题
	@Column(name = "create_time")
	private Date createTime;//创建时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CommonType getCommonType() {
		return commonType;
	}
	public void setCommonType(CommonType commonType) {
		this.commonType = commonType;
	}
	public String getBelongType() {
		return belongType;
	}
	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
