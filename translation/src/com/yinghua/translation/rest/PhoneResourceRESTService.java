/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yinghua.translation.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.yinghua.translation.Constant;
import com.yinghua.translation.handler.ServiceNoHandlerFactory;
import com.yinghua.translation.model.Account;
import com.yinghua.translation.model.CallRecord;
import com.yinghua.translation.model.CommonData;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.MemberOrderUse;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.PackageProduct;
import com.yinghua.translation.model.PackageProductContent;
import com.yinghua.translation.model.PayEasyLog;
import com.yinghua.translation.model.PayWeixinLog;
import com.yinghua.translation.model.Product;
import com.yinghua.translation.model.enumeration.MemberStatus;
import com.yinghua.translation.model.enumeration.MemberType;
import com.yinghua.translation.model.enumeration.OrderStatus;
import com.yinghua.translation.model.enumeration.OrderUseStatus;
import com.yinghua.translation.rongcloud.io.rong.ApiHttpClient;
import com.yinghua.translation.rongcloud.io.rong.models.FormatType;
import com.yinghua.translation.rongcloud.io.rong.models.SdkHttpResult;
import com.yinghua.translation.service.AccountBean;
import com.yinghua.translation.service.BaseProductBean;
import com.yinghua.translation.service.CallHistoryBean;
import com.yinghua.translation.service.CommonDataBean;
import com.yinghua.translation.service.MemberBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberOrderUseBean;
import com.yinghua.translation.service.OrderBean;
import com.yinghua.translation.service.PackageProductBean;
import com.yinghua.translation.service.PackageProductContentBean;
import com.yinghua.translation.service.PayEasyLogBean;
import com.yinghua.translation.service.PayWeixinLogBean;
import com.yinghua.translation.service.PaymentBean;
import com.yinghua.translation.service.ProductBean;
import com.yinghua.translation.util.ClassLoaderUtil;
import com.yinghua.translation.util.OrderNoUtil;
import com.yinghua.translation.util.UUIDUtil;

@Path("/phoneService")
@RequestScoped
public class PhoneResourceRESTService
{
	@Inject
	private Logger log;

	@EJB
	private MemberBean repository;

	@EJB
	private PaymentBean paymentBean;

	@EJB
	private ProductBean productBean;

	@EJB
	private BaseProductBean baseProductBean;
	
	@EJB
	private PackageProductBean packageProductBean;
	
	@EJB
	private PackageProductContentBean packageProductContentBean;
	
	@EJB
	private AccountBean accountBean;
	
	@EJB
	private CallHistoryBean callHistoryBean;

	@EJB
	private OrderBean orderBean;
	
	@EJB
	private PayEasyLogBean payEasyLogBean;
	
	@EJB
	private PayWeixinLogBean payWeixinLogBean;
	
	@EJB
	private MemberOrderBean memberOrderBean;
	
	@EJB
	private MemberOrderUseBean memberOrderUseBean;
	
	@EJB
	private MemberBean memberBean;
	
	@EJB
	private CommonDataBean commonDataBean;
	
	private Properties keyPro = ClassLoaderUtil.getProperties("key.properties");
	/**
	 * 查询服务记录
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getCallHistory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getCallHistory(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		Map<String, Object> req = new HashMap<>();
		//String uid = Objects.toString(obj.get("uid"), "0");
		String uno = Objects.toString(obj.get("uno"), "0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
//		try {
//			start = sdf.parse(Objects.toString(obj.get("start"), "0"));
//			end = sdf.parse(Objects.toString(obj.get("end"), "0"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// 此处更新用户信息
		List<CallRecord> list = callHistoryBean.findByUid(uno);
		
//		Integer total = callHistoryBean.findByUid(uno).size();
		//req.put("count", Objects.toString(list.size(), "0"));
		req.put("total", Objects.toString(list.size(), "0"));
		req.put("callList", list);
		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");
		return req;
	}

	
	/**
	 * 用户生成订单
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/preOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> preOrder(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		
		//用户信息、套餐信息
		String businessNo = "9056";
		
		String uno = obj.getString("uno");
		String prod_no = Objects.toString(obj.getString("packageNo"), "0");
		Float orderPrice = obj.getFloat("price");
		int price = (int)(orderPrice*100); 
		String pay_way = obj.getString("pay_way");
		String service_time = obj.getString("service_time");
		int use_date = obj.getIntValue("use_date");
		String service_end_time = obj.getString("service_end_time");
		String remark = obj.getString("remark");
		String orderNo = obj.getString("orderNo");
		
		PackageProduct packageProduct = packageProductBean.findByPackageNo(prod_no);
		
		if(packageProduct!=null){
			MemberOrder order = null;
			Long id = 0L;
			//插入用户订单表
			if(orderNo!=null){
				order = memberOrderBean.findByOrderNo(orderNo);
				if(order!=null){
					if("3".equals(pay_way)||"4".equals(pay_way)){
						String str = new SimpleDateFormat("yyyyMMdd").format(new Date(System
								.currentTimeMillis()));
						order.setPayNo(str+"-"+businessNo+"-"+OrderNoUtil.getOrderNo("OR"));
					}else{
						order.setPayNo(OrderNoUtil.getOrderNo("OR"));
					}
					order.setPayWay(pay_way);
					memberOrderBean.updateOrder(order);
					id = order.getId();
				}
			}else{
				order = new MemberOrder();
				order.setMemberNumber(uno);
				order.setOrderTime(new Date());
				order.setPackageNo(prod_no);
				order.setPackageName(packageProduct.getSubject());
				order.setPackageType(packageProduct.getType());
				order.setPackageDesc(packageProduct.getDesc());
				if("3".equals(pay_way)||"4".equals(pay_way)){
					String str = new SimpleDateFormat("yyyyMMdd").format(new Date(System
							.currentTimeMillis()));
					order.setOrderNo(str+"-"+businessNo+"-"+OrderNoUtil.getOrderNo("OR"));
				}else{
					order.setOrderNo(OrderNoUtil.getOrderNo("OR"));
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				try {
					order.setServiceTime(sdf.parse(service_time));
				} catch (ParseException e1) {
					e1.printStackTrace();
					order.setServiceTime(new Date());
				}
				
				try {
					if(service_end_time!=null){
						order.setServiceEndTime(sdf.parse(service_end_time));
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
					order.setServiceTime(new Date(System.currentTimeMillis()+30*24*60*60*1000));
				}
				
				order.setSurplusCallDuration(packageProduct.getSurplusCallDuration()*use_date);
				order.setUseDate(use_date);
				order.setUseState(OrderUseStatus.PERPARE);
				order.setState(OrderStatus.CREATED);
				order.setOrderPrice(String.valueOf(orderPrice));
				order.setPayWay(pay_way);
				order.setRemark(remark);
				order.setPayNo(order.getOrderNo());
				
				id = memberOrderBean.createOrder(order);
			}
			
			Map<String, Object> chargeMap = new HashMap<String, Object>();
			Charge charge;
			if (id > 0)
			{
				//生成订单对象返回给app
				chargeMap.put("amount", price);
				chargeMap.put("currency", "cny");
				chargeMap.put("subject", order.getPackageName());
				chargeMap.put("body", "Your Body");
				chargeMap.put("order_no", order.getPayNo());
				
				switch (pay_way)
				{
					case "0":
						req.put("order_no", order.getPayNo());
						req.put("amount", price);
						req.put("pay_way", order.getPayWay());
						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
					case "1":
						chargeMap.put("channel", "alipay");
						chargeMap.put("client_ip", "127.0.0.1");
						try
						{
							charge = paymentBean.charge(chargeMap);
//							order.setCredential(JSONObject.toJSONString(charge)
//									.toString());
//							orderBean.updateOrder(order);
							req.put("charge", JSONObject.toJSONString(charge)
									.toString());
							req.put("order_no", order.getPayNo());
							req.put("amount", price);
							req.put("pay_way", order.getPayWay());
//							req.put("credential", order.getCredential());
							req.put("error_code", "000000");
							req.put("error_msg", "");
						}
						catch (Exception e)
						{
							log.info(e.getMessage());
							order.setState(OrderStatus.CANCELLED);
							memberOrderBean.updateOrder(order);
							req.put("result", "fail");
							req.put("error_code", "5001");
							req.put("error_msg", "支付失败");
						}
						break;
					case "2":
						chargeMap.put("channel", "wx");
						chargeMap.put("client_ip", "127.0.0.1");
						chargeMap.put("order_no",  order.getPayNo());
						//Integer am = order.getAmount().multiply(new BigDecimal(100)).;
						chargeMap.put("amount", price);
						try
						{
							charge = paymentBean.charge(chargeMap);
//							order.setCredential(JSONObject.toJSONString(charge)
//									.toString());
//							orderBean.updateOrder(order);
							req.put("charge", JSONObject.toJSONString(charge)
									.toString());
							req.put("charge_id", charge.getId());
							req.put("order_no", order.getPayNo());
							req.put("amount", order.getOrderPrice());
							req.put("pay_way", order.getPayWay());
//							req.put("credential", order.getCredential());
							req.put("error_code", "000000");
							req.put("error_msg", "");
						}
						catch (Exception e)
						{
							order.setState(OrderStatus.CANCELLED);
							memberOrderBean.updateOrder(order);
							log.info(e.getMessage());
							req.put("result", "fail");
							req.put("error_code", "5001");
							req.put("error_msg", "支付失败");
						}
						break;
					case "3":
						req.put("order_no", order.getPayNo());
						req.put("amount", order.getOrderPrice());
						req.put("pay_way", order.getPayWay());
						req.put("business_no", businessNo);
						req.put("key", "test");
//						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
					case "4":
						req.put("order_no", order.getPayNo());
						req.put("amount", order.getOrderPrice());
						req.put("pay_way", order.getPayWay());
						req.put("businessNo", businessNo);
						req.put("key", "test");
//						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
				}
			}else{
				req.put("result", "fail");
				req.put("error_code", "5005");
				req.put("error_msg", "订单不存在");
			}
			
		}else{
			req.put("result", "fail");
			req.put("error_code", "5005");
			req.put("error_msg", "商品不存在");
		}
		return req;
	}
	
	
	/**
	 * 用户生成订单
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/preBankOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> preBankOrder(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		
		//用户信息、套餐信息
		
		String uno = obj.getString("uno");
		String prod_no = Objects.toString(obj.getString("packageNo"), "0");
		Float orderPrice = obj.getFloat("price");
		String pay_way = obj.getString("pay_way");
		String service_time = obj.getString("service_time");
		int use_date = obj.getIntValue("use_date");
		
		PackageProduct packageProduct = packageProductBean.findByPackageNo(prod_no);
		
		if(packageProduct!=null){
			//插入用户订单表
			MemberOrder order = new MemberOrder();
			order.setMemberNumber(uno);
			order.setOrderTime(new Date());
			order.setPackageNo(prod_no);
			order.setPackageName(packageProduct.getSubject());
			order.setPackageType(packageProduct.getType());
			order.setPackageDesc(packageProduct.getDesc());
			order.setOrderNo(OrderNoUtil.getOrderNo("OR"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				order.setServiceTime(sdf.parse(service_time));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			order.setSurplusCallDuration(packageProduct.getSurplusCallDuration()*use_date);
			order.setUseDate(use_date);
			order.setUseState(OrderUseStatus.PERPARE);
			order.setState(OrderStatus.CREATED);
			order.setOrderPrice(String.valueOf(orderPrice));
			order.setPayWay(pay_way);
			
			Long id = memberOrderBean.createOrder(order);
			
			if (id > 0)
			{
				//生成订单对象返回给app
				req.put("result", "success");
				req.put("error_code", "000000");
				req.put("error_msg", "");
			}
			
		}else{
			req.put("result", "fail");
			req.put("error_code", "5005");
			req.put("error_msg", "商品不存在");
		}
		return req;
	}
	
	
	/**
	 * 用户支付
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/makeOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> makeOrder(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = obj.getString("uid");
		String uno = obj.getString("uno");
		String prod_no = Objects.toString(obj.getString("productNo"), "0");
		String price = obj.getString("price");
		String pay_way = obj.getString("pay_way");

		Product product = productBean.findByProductNo(prod_no);
		Account account = accountBean.findByMemberNo(uno);
		if (product != null)
		{
			Map<String, Object> chargeMap = new HashMap<String, Object>();

			Order order = new Order();
			order.setAmount(product.getPrice());
			order.setCreateTime(new Date(System.currentTimeMillis()));
			order.setLanguages(product.getLanguages());
			order.setProdType(product.getType());
			order.setProdId(product.getProductNo());
			order.setProdName(product.getSubject());
			order.setPayWay(pay_way);
			order.setState(OrderStatus.CREATED);
			order.setOrderNo(OrderNoUtil.getOrderNo("OR"));
			order.setMemberNumber(uno);

			Long id = orderBean.createOrder(order);
			Charge charge;
			if (id > 0)
			{
				chargeMap.put("amount", order.getPrice());
				chargeMap.put("currency", "cny");
				chargeMap.put("subject", order.getProdName());
				chargeMap.put("body", "Your Body");
				chargeMap.put("order_no", order.getOrderNo());
				
				switch (pay_way)
				{
					case "0":
						req.put("order_no", order.getOrderNo());
						req.put("amount", order.getAmount().toString());
						req.put("pay_way", order.getPayWay());
						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
					case "1":
						chargeMap.put("channel", "alipay");
						chargeMap.put("client_ip", "127.0.0.1");
						try
						{
							charge = paymentBean.charge(chargeMap);
							order.setCredential(JSONObject.toJSONString(charge)
									.toString());
							orderBean.updateOrder(order);
							req.put("charge", JSONObject.toJSONString(charge)
									.toString());
							req.put("order_no", order.getOrderNo());
							req.put("amount", order.getPrice());
							req.put("pay_way", order.getPayWay());
							req.put("credential", order.getCredential());
							req.put("error_code", "000000");
							req.put("error_msg", "");
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							log.info(e.getMessage());
							req.put("result", "fail");
							req.put("error_code", "5001");
							req.put("error_msg", "支付失败");
						}
						break;
					case "2":
						chargeMap.put("channel", "wx");
						chargeMap.put("client_ip", "127.0.0.1");
						chargeMap.put("order_no",  order.getOrderNo());
						//Integer am = order.getAmount().multiply(new BigDecimal(100)).;
						chargeMap.put("amount", order.getAmount().multiply(new BigDecimal(100)).intValue());
						try
						{
							charge = paymentBean.charge(chargeMap);
							order.setCredential(JSONObject.toJSONString(charge)
									.toString());
							orderBean.updateOrder(order);
							req.put("charge", JSONObject.toJSONString(charge)
									.toString());
							req.put("charge_id", charge.getId());
							req.put("order_no", order.getOrderNo());
							req.put("amount", order.getAmount());
							req.put("pay_way", order.getPayWay());
							req.put("credential", order.getCredential());
							req.put("error_code", "000000");
							req.put("error_msg", "");
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							log.info(e.getMessage());
							req.put("result", "fail");
							req.put("error_code", "5001");
							req.put("error_msg", "支付失败");
						}
						break;
					case "3":
						req.put("order_no", order.getOrderNo());
						req.put("amount", order.getPrice());
						req.put("pay_way", order.getPayWay());
						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
					case "4":
						req.put("order_no", order.getOrderNo());
						req.put("amount", order.getPrice());
						req.put("pay_way", order.getPayWay());
						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
				}
			}
		}
		else
		{
			req.put("result", "fail");
			req.put("error_code", "5005");
			req.put("error_msg", "商品不存在");
		}
		return req;
	}

	/**
	 * 更改用户支付凭证
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/uploadPurchase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> uploadPurchase(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uid"), "0");
		Member member = repository.findById(Long.parseLong(uid));
		if (member == null)
		{
			// 用户不存在
			req.put("result", "fail");
			req.put("error_code", "6001");
			req.put("error_msg", "支付失败");
		}
		else
		{
			// member.setPurchased("true");
			repository.updateMember(member);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "已支付");
		}
		return req;
	}

	/**
	 * 保存用户通迅录
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/uploadContacts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> uploadContacts(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String contacts = obj.getString("contacts");
		String uno = Objects.toString(obj.get("uno"), "0");
		Map<String, Object> req = new HashMap<>();
		//Member member = repository.findById(Long.parseLong(uid));
		Member member = repository.findByMemberNo(uno);
		// 此处更新用户信息
		try
		{
			member.setContacts(contacts);
			repository.updateMember(member);

			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");

		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "7001");
			req.put("error_msg", "");
		}

		return req;
	}
	
	/**
	 * 获取常见问题及消息
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getCommonData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getCommonData(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String commonType = Objects.toString(obj.get("commonType"), "");
		
		Map<String, Object> req = new HashMap<>();
		//Member member = repository.findById(Long.parseLong(uid));
		List<CommonData> list = commonDataBean.findByCommonType(commonType);
		try
		{
			if(list!=null){
				req.put("count",list.size());
				req.put("commonDatas", list);
			}
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");

		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "7001");
			req.put("error_msg", "系统内部错误");
		}

		return req;
	}
	
	/**
	 * 发送第三方通话号码
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/sendCallee3")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> sendCallee3(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String caller = Objects.toString(obj.get("caller"), "null");
		String callee = Objects.toString(obj.getString("callee"),"");
		Map<String, Object> req = new HashMap<>();
		// 此处更新三方通话号码
		boolean isOk = false;
		String errorCode = "1011";
		String errorMsg = "未订购套餐";
		try
		{
			Constant.call3Map.put(caller, callee);
			System.out.println("caller:"+caller+",callee:"+Constant.call3Map.get(caller));
			System.out.println("收到第三方号码参数");
			if(callee!=null&&callee.length()>0){
				if(callee.startsWith("000000000")){
					//套餐内
					Member member = memberBean.findMember(caller, "");
					if(member!=null){
						 
						errorCode = ServiceNoHandlerFactory.createServiceNoHandler().processServiceNo(member.getMemberNumber(), callee, baseProductBean, memberOrderBean, memberOrderUseBean);
						if("000000".equals(errorCode))isOk = true;
						
//						System.out.println("用户号码："+member.getMemberNumber());
//						List<MemberOrder> orders = memberOrderBean.findUsingOrderByUno(member.getMemberNumber());
//						if(orders!=null&&orders.size()>0){
//							for (MemberOrder memberOrder : orders) {
//								if("1003".equals(memberOrder.getPackageNo())||Constant.packageNoMap.get(callee).equals(memberOrder.getPackageNo())){
//									isOk = true;
//									break;
//								}
//							}
//						}
//				Account account = accountBean.findByMemberNo(member.getMemberNumber());
//				if(account!=null&&account.getSurplusCallDuration()>0){
//					req.put("result", "success");
//					req.put("error_code", "000000");
//					req.put("error_msg", "");
//				}else{
//					req.put("result", "fail");
//					req.put("error_code", "1011");
//					req.put("error_msg", "剩余分钟数不足");
//				}
					}else{
						errorCode="1006";
						errorMsg="用户不存在";
					}
					if(isOk){
						req.put("result", "success");
						req.put("error_code", "000000");
						req.put("error_msg", "");
					}else{
						req.put("result", "fail");
						req.put("error_code", errorCode);
						req.put("error_msg", errorMsg);
					}
				}else{//网络电话
					req.put("result", "success");
					req.put("error_code", "000000");
					req.put("error_msg", "");
				}
			}else{
				req.put("result", "fail");
				req.put("error_code", "1011");
				req.put("error_msg", "拨打号码为空");
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "7001");
			req.put("error_msg", "系统内部错误");
		}

		return req;
	}
	
	/**
	 * 接通客席通话状态接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/callerStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> callerStatus(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String caller = Objects.toString(obj.get("caller"), "null");
		Map<String, Object> req = new HashMap<>();
		try
		{
			String status = "error";
			String call_id = Constant.callerMap.get(caller);
			if(call_id!=null&&call_id.length()>0){
				status = "ok";
				Constant.callerMap.remove(caller);
			}
			System.out.println("客户端查看坐席接通状态");
			System.out.println("caller:"+caller+",call_id:"+call_id);
			req.put("result", "success");
			req.put("status", status);
			req.put("error_code", "000000");
			req.put("error_msg", "");

		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "7001");
			req.put("error_msg", "");
		}

		return req;
	}
	
	/**
	 * 接通三方通话状态接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/caller3Status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> caller3Status(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String caller = Objects.toString(obj.get("caller"), "null");
		Map<String, Object> req = new HashMap<>();
		// 此处更新三方通话号码
		try
		{
			String status = "error";
			String result = Constant.calleeMap.get(caller);
			if("1".equals(result)){
				status = "ok";
				Constant.calleeMap.remove(caller);
			}else if("2".equals(result)){
				status = "end";
				Constant.calleeMap.remove(caller);
			}
			System.out.println("APP查看第三方接通状态");
			System.out.println("caller:"+caller+",result:"+status);
			req.put("result", "success");
			req.put("status", status);
			req.put("error_code", "000000");
			req.put("error_msg", "");

		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "7001");
			req.put("error_msg", "");
		}

		return req;
	}

	
	
	/**
	 * 接收ping++异步支付结果通知
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/webhooks")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> webhooks(String params)
	{
		Event event = Webhooks.eventParse(params);
		Map<String, Object> req = new HashMap<>();
		 if ("charge.succeeded".equals(event.getType())) {
			@SuppressWarnings("unchecked")
			Map<String,Object> object = (Map<String, Object>) event.getData().get("object");
			 if(object!=null&&object.size()>0){
				 String orderNo = Objects.toString(object.get("order_no"),"0");
				 MemberOrder order = memberOrderBean.findByPayNo(orderNo);
				 if(order!=null){
					 if(order.getState()!=OrderStatus.FINISHED){
						 order.setState(OrderStatus.FINISHED);
						 List<PackageProductContent> ppcs = packageProductContentBean.findByPackageNo(order.getPackageNo());
						 switch (order.getPackageType()) {
						 case "1":
						 //按天
							for (PackageProductContent packageProductContent : ppcs) {
								MemberOrderUse mou = new MemberOrderUse();
								mou.setOrderNo(order.getOrderNo());
								mou.setProductNo(packageProductContent.getProductNo());
								mou.setTimes(packageProductContent.getTimes());
								memberOrderUseBean.createMemberOrderUse(mou);
							}
							break;
						 case "2":
						 //按次
								for (PackageProductContent packageProductContent : ppcs) {
									MemberOrderUse mou = new MemberOrderUse();
									mou.setOrderNo(order.getOrderNo());
									mou.setProductNo(packageProductContent.getProductNo());
									mou.setTimes(packageProductContent.getTimes());
									memberOrderUseBean.createMemberOrderUse(mou);
								}
								List<MemberOrder> moList = memberOrderBean.findUsingOrderByUno(order.getMemberNumber(),"2");
								for (MemberOrder memberOrder : moList) {
									if(memberOrder.getServiceEndTime().getTime()<order.getServiceEndTime().getTime()){
										memberOrder.setServiceEndTime(order.getServiceEndTime());
										memberOrderBean.updateOrder(memberOrder);
									}
								}
								
							break;
						 case "3":
						 //按单项
							JSONObject obj = JSONObject.parseObject(order.getRemark());
							for (String key : obj.keySet()) {
								MemberOrderUse mou = new MemberOrderUse();
								mou.setOrderNo(order.getOrderNo());
								mou.setProductNo(key);
								mou.setTimes(obj.getIntValue(key));
								memberOrderUseBean.createMemberOrderUse(mou);
							}
							List<MemberOrder> moList2 = memberOrderBean.findUsingOrderByUno(order.getMemberNumber(),"3");
							for (MemberOrder memberOrder : moList2) {
								if(memberOrder.getServiceEndTime().getTime()<order.getServiceEndTime().getTime()){
									memberOrder.setServiceEndTime(order.getServiceEndTime());
									memberOrderBean.updateOrder(memberOrder);
								}
							}
							break;
						}
						 if(System.currentTimeMillis()>=order.getServiceTime().getTime()){
//							 Account account = accountBean.findByMemberNo(order.getMemberNumber());
//							 int addCall = order.getSurplusCallDuration()+account.getSurplusCallDuration();
//							 account.setSurplusCallDuration(addCall);
//							 accountBean.updateAccount(account);
							 order.setUseState(OrderUseStatus.USING);
						 }
						 memberOrderBean.updateOrder(order);
						 System.out.println("orderNo:"+order.getOrderNo()+"|orderState:"+order.getState()+"|orderUseState:"+order.getUseState());
					 }
				 }else{
					 //记录日志
				 }
			 }
	        }
		 req.put("result", "ok");
		return req;
	}
	
	
	/**
	 * 接收首信易支付异步支付结果通知
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@GET
	@Path("/billNotify")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Encoded
	public String billNotify(@QueryParam("v_count") int count,@QueryParam("v_oid") String oids,@QueryParam("v_pmode") String pmodes,@QueryParam("v_pstatus") String pstatus,@QueryParam("v_pstring") String pstrings,
			@QueryParam("v_amount") String amounts,@QueryParam("v_moneytype") String moneytype,@QueryParam("v_mac") String mac,@QueryParam("v_md5money") String md5money,@QueryParam("v_sign") String sign)
	{
		String result = "error";
			if(count>0){
				String regex = "\\|_\\|";
				if(oids!=null&&pstatus!=null&&pmodes!=null&&pstrings!=null&&amounts!=null&&moneytype!=null){
					String[] oid = oids.split(regex);
					String[] status = pstatus.split(regex);
					String[] pmode = pmodes.split(regex);
					String[] pstring = pstrings.split(regex);
					String[] amount = amounts.split(regex);
					String[] mtype = moneytype.split(regex);
					for (int i = 0; i < count; i++) {
						if(oid[i]!=null){
							try{
								String orderNo = Objects.toString(oid[i]);
								MemberOrder order = memberOrderBean.findByPayNo(orderNo);
								if(order!=null&&"1".equals(status[i])){
									if(order.getState()!=OrderStatus.FINISHED){//重复订单不计费
										 order.setState(OrderStatus.FINISHED);
										 List<PackageProductContent> ppcs = packageProductContentBean.findByPackageNo(order.getPackageNo());
										 switch (order.getPackageType()) {
										 case "1":
										 //按天
											for (PackageProductContent packageProductContent : ppcs) {
												MemberOrderUse mou = new MemberOrderUse();
												mou.setOrderNo(order.getOrderNo());
												mou.setProductNo(packageProductContent.getProductNo());
												mou.setTimes(packageProductContent.getTimes());
												memberOrderUseBean.createMemberOrderUse(mou);
											}
											break;
										 case "2":
										 //按次
												for (PackageProductContent packageProductContent : ppcs) {
													MemberOrderUse mou = new MemberOrderUse();
													mou.setOrderNo(order.getOrderNo());
													mou.setProductNo(packageProductContent.getProductNo());
													mou.setTimes(packageProductContent.getTimes());
													memberOrderUseBean.createMemberOrderUse(mou);
												}
											break;
										 case "3":
										 //按单项
											JSONObject obj = JSONObject.parseObject(order.getRemark());
											for (String key : obj.keySet()) {
												MemberOrderUse mou = new MemberOrderUse();
												mou.setOrderNo(order.getOrderNo());
												mou.setProductNo(key);
												mou.setTimes(obj.getIntValue(key));
												memberOrderUseBean.createMemberOrderUse(mou);
											}
											break;
										}
										 if(System.currentTimeMillis()>=order.getServiceTime().getTime()){
//											 Account account = accountBean.findByMemberNo(order.getMemberNumber());
//											 int addCall = order.getSurplusCallDuration()+account.getSurplusCallDuration();
//											 account.setSurplusCallDuration(addCall);
//											 accountBean.updateAccount(account);
											 order.setUseState(OrderUseStatus.USING);
										 }
										 memberOrderBean.updateOrder(order);
										 System.out.println("orderNo:"+order.getOrderNo()+"|orderState:"+order.getState()+"|orderUseState:"+order.getUseState());
									 }
								}else{
									System.out.println("订单不存在");
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							
							try {
								PayEasyLog payel = new PayEasyLog();
								payel.setOid(oid[i]);
								payel.setPstatus(status[i]);
								payel.setPmode(URLDecoder.decode(pmode[i], "gbk"));
								payel.setPstring(URLDecoder.decode(pstring[i], "gbk"));
								payel.setAmount(amount[i]);
								payel.setMoneytype(mtype[i]);
								payel.setMac(mac);
								payel.setMd5money(md5money);
								payel.setSign(sign);
								payEasyLogBean.create(payel);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
					}
					result="send";
				}
			}
		return result;
	}
	
	/**
	 * 接收微信异步支付结果通知
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/weixinNotify")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public String weixinNotify(String params)
	{
		String req = null;
		Document reqDoc = DocumentHelper.createDocument();
		Element reqRoot = reqDoc.addElement("xml");
		Element reqCode = reqRoot.addElement("return_code");
		try {
			Document doc = DocumentHelper.parseText(params);
			Element root = doc.getRootElement();
			Element returnCode = root.element("return_code");
			if( "SUCCESS".equals(returnCode.getText())){
				String orderNo = root.elementText("out_trade_no");
				MemberOrder order = memberOrderBean.findByOrderNo(orderNo);
				if(order!=null&&"SUCCESS".equals(root.elementText("result_code"))){
					if(order.getState()!=OrderStatus.FINISHED){//重复订单不计费
						 order.setState(OrderStatus.FINISHED);
						 if(System.currentTimeMillis()>=order.getServiceTime().getTime()){
							 Account account = accountBean.findByMemberNo(order.getMemberNumber());
							 int addCall = order.getSurplusCallDuration()+account.getSurplusCallDuration();
							 account.setSurplusCallDuration(addCall);
							 accountBean.updateAccount(account);
							 order.setUseState(OrderUseStatus.USING);
						 }
						 memberOrderBean.updateOrder(order);
						 System.out.println("orderNo:"+order.getOrderNo()+"|orderState:"+order.getState()+"|orderUseState:"+order.getUseState());
					 }
				}else{
					System.out.println("订单不存在");
				}
				
				PayWeixinLog pwl = new PayWeixinLog();
				pwl.setAppid(root.elementText("appid"));
				pwl.setMchId(root.elementText("mch_id"));
				if(root.element("device_info")!=null)
				pwl.setDeviceInfo(root.elementText("device_info"));
				pwl.setNonceStr(root.elementText("nonce_str"));
				pwl.setSign(root.elementText("sign"));
				pwl.setResultCode(root.elementText("result_code"));
				if(root.element("err_code")!=null)
				pwl.setErrCode(root.elementText("err_code"));
				if(root.element("err_code_des")!=null)
				pwl.setErrCodeDes(root.elementText("err_code_des"));
				pwl.setOpenid(root.elementText("openid"));
				if(root.element("is_subscribe")!=null)
				pwl.setIsSubscribe(root.elementText("is_subscribe"));
				pwl.setTradeType(root.elementText("trade_type"));
				pwl.setBankType(root.elementText("bank_type"));
				pwl.setTotalFee(Integer.parseInt(root.elementText("total_fee")));
				if(root.element("fee_type")!=null)
				pwl.setFeeType(root.elementText("fee_type"));
				if(root.element("cash_fee")!=null)
				pwl.setCashFee(Integer.parseInt(root.elementText("cash_fee")));
				if(root.element("cash_fee_type")!=null)
				pwl.setCashFeeType(root.elementText("cash_fee_type"));
				pwl.setTransationId(root.elementText("transation_id"));
				pwl.setOutTradeNo(root.elementText("out_trade_no"));
				if(root.element("attach")!=null)
				pwl.setAttach(root.elementText("attach"));
				pwl.setTimeEnd(root.elementText("time_end"));
				payWeixinLogBean.createPayWeixinLog(pwl);
				
				reqCode.setText("SUCCESS");
			}else{
				reqCode.setText("FAIL");
				Element returnMsg = root.element("return_msg");
				returnMsg.setText(root.elementText("retrun_msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			reqCode.setText("FAIL");
			Element reqMsg = reqRoot.addElement("return_msg");
			reqMsg.setText("校验失败");
		}
		req = reqDoc.asXML();
		return req;
	}
	
	/**
	 * vip用户
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/vip")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> vip(String params) {
		Map<String, Object> req = new HashMap<>();
		try {
			JSONObject obj = JSONObject.parseObject(params);
			String key = "yhbyvip";
			
			if (key.equals(obj.getString("vipkey"))) {
				Member member = repository
						.findByMobile(obj.getString("mobile"));
				if (member == null) {
					// 用户不存在，注册新用户
					SdkHttpResult result = ApiHttpClient.getToken(
							keyPro.getProperty("appKey"),
							keyPro.getProperty("appSecret"),
							obj.getString("mobile"), "", "", FormatType.json);
					JSONObject objj = (JSONObject) JSONObject.parse(result
							.getResult());
					member = new Member();
					member.setMobilePhone(obj.getString("mobile"));
					member.setPassword(obj.getString("pwd"));
					member.setImToken(objj.getString("token"));
					member.setIsCompleted(0);
					member.setStatus(MemberStatus.NORMAL);
					member.setMemberType(MemberType.ORDINARY);
					member.setMemberNumber(UUIDUtil.genUUIDString());
					member.setVoip(Constant.UUCALL_GROUP
							+ obj.getString("mobile"));
					
					Long id = repository.register(member);
					
					
					Account account = new Account();
					account.setAccountBalance(BigDecimal.ZERO);
					account.setAccountNumber(member.getMobilePhone());
					account.setChargingStandard(BigDecimal.ZERO);
					account.setMemberNumber(member.getMemberNumber());
					account.setCreateTime(new Date(System.currentTimeMillis()));
					account.setRechargeTimes(0);
					account.setSurplusCallDuration(360000);
					account.setStatus(MemberStatus.NORMAL);
					accountBean.register(account);

					
				} 
				
				MemberOrder mo = new MemberOrder();
				 mo.setMemberNumber(member.getMemberNumber());
				 mo.setOrderNo(OrderNoUtil.getOrderNo("OR"));
				 mo.setOrderPrice("0");
				 mo.setOrderTime(new Date());
				 mo.setPackageDesc("尊享VIP畅享所有服务");
				 mo.setPackageName("尊享VIP套餐");
				 mo.setPackageNo("9999");
				 mo.setPayWay("0");
				 mo.setServiceTime(new Date());
				 mo.setState(OrderStatus.FINISHED);
				 mo.setUseState(OrderUseStatus.USING);
				 mo.setUseDate(30);
				 mo.setSurplusCallDuration(12000*30);
				 mo.setPackageType("1");
				 mo.setServiceEndTime(new Date(126,2,30));
				 memberOrderBean.createOrder(mo);

				 List<PackageProductContent> ppcs = packageProductContentBean.findByPackageNo(mo.getPackageNo());
				 for (PackageProductContent packageProductContent : ppcs) {
						MemberOrderUse mou = new MemberOrderUse();
						mou.setOrderNo(mo.getOrderNo());
						mou.setProductNo(packageProductContent.getProductNo());
						mou.setTimes(packageProductContent.getTimes());
						memberOrderUseBean.createMemberOrderUse(mou);
					}
				req.put("result", "success");
			} else {
				req.put("result", "fail");
			}

		} catch (Exception e) {
			// Handle generic exceptions
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "3002");
			req.put("error_msg", e.getMessage());
		}
		return req;
	}
	
}
