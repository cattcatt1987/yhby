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
import javax.persistence.UniqueConstraint;

import com.yinghua.translation.model.enumeration.MemberStatus;

@Entity
@Table(name = "cc_account")
public class Account implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id 主键
	@Column(name = "member_number")
	private String memberNumber; // member_number 会员编号
	@Column(name = "account_number")
	private String accountNumber; // account_number 账号编号
	@Column(name = "account_balance")
	private BigDecimal accountBalance; // account_balance 账户余额
	@Column(name = "payment_password")
	private String paymentPassword; // payment_password 账户支付密码
	@Column(name = "total_consumption")
	private BigDecimal totalConsumption; // total_consumption 累计消费 decimal(9,2)
	@Column(name = "total_recharge")
	private BigDecimal totalRecharge; // total_recharge 累计充值 decimal(9,2)
	@Column(name = "recharge_times")
	private Integer rechargeTimes; // recharge_times 累计充值次数 int
	@Column(name = "surplus_call_duration")
	private Integer surplusCallDuration; // surplus_call_duration 剩余通话时长
	@Column(name = "surplus_tran_duration")
	private Integer surplusTranDuration; // surplus_tran_duration 剩余翻译时长
	@Column(name = "surplus_msg_duration")
	private Integer surplusMsgDuration; // surplus_msg_duration 剩余短信条数
	@Column(name = "charging_standard")
	private BigDecimal chargingStandard; // charging_standard 收费标准
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private MemberStatus status; // status 会员账户状态 enum
	@Column(name = "create_person")
	private String createPerson; // create_person 创建人
	@Column(name = "create_time")
	private Date createTime; // create_time 创建时间
	@Column(name = "modified_person")
	private String modifiedPerson; // modified_person 修改人
	@Column(name = "modified_time")
	private Date modifiedTime; // modified_time 修改时间

	public Account()
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

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAccountBalance()
	{
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance)
	{
		this.accountBalance = accountBalance;
	}

	public String getPaymentPassword()
	{
		return paymentPassword;
	}

	public void setPaymentAassword(String paymentPassword)
	{
		this.paymentPassword = paymentPassword;
	}

	public BigDecimal getTotalConsumption()
	{
		return totalConsumption;
	}

	public void setTotalConsumption(BigDecimal totalConsumption)
	{
		this.totalConsumption = totalConsumption;
	}

	public BigDecimal getTotalRecharge()
	{
		return totalRecharge;
	}

	public void setTotalRecharge(BigDecimal totalRecharge)
	{
		this.totalRecharge = totalRecharge;
	}

	public Integer getRechargeTimes()
	{
		return rechargeTimes;
	}

	public void setRechargeTimes(Integer rechargeTimes)
	{
		this.rechargeTimes = rechargeTimes;
	}

	public Integer getSurplusCallDuration()
	{
		return surplusCallDuration;
	}

	public void setSurplusCallDuration(Integer surplusCallDuration)
	{
		this.surplusCallDuration = surplusCallDuration;
	}

	public Integer getSurplusTranDuration()
	{
		return surplusTranDuration;
	}

	public void setSurplusTranDuration(Integer surplusTranDuration)
	{
		this.surplusTranDuration = surplusTranDuration;
	}

	public Integer getSurplusMsgDuration()
	{
		return surplusMsgDuration;
	}

	public void setSurplusMsgDuration(Integer surplusMsgDuration)
	{
		this.surplusMsgDuration = surplusMsgDuration;
	}

	public BigDecimal getChargingStandard()
	{
		return chargingStandard;
	}

	public void setChargingStandard(BigDecimal chargingStandard)
	{
		this.chargingStandard = chargingStandard;
	}

	public MemberStatus getStatus()
	{
		return status;
	}

	public void setStatus(MemberStatus status)
	{
		this.status = status;
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
