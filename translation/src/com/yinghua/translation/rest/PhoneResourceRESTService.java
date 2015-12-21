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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.yinghua.translation.Constant;
import com.yinghua.translation.model.Account;
import com.yinghua.translation.model.CallRecord;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.PackageProduct;
import com.yinghua.translation.model.Product;
import com.yinghua.translation.model.TranslationRecord;
import com.yinghua.translation.model.enumeration.OrderStatus;
import com.yinghua.translation.model.enumeration.OrderUseStatus;
import com.yinghua.translation.service.AccountBean;
import com.yinghua.translation.service.CallHistoryBean;
import com.yinghua.translation.service.MemberBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.OrderBean;
import com.yinghua.translation.service.PackageProductBean;
import com.yinghua.translation.service.PaymentBean;
import com.yinghua.translation.service.ProductBean;
import com.yinghua.translation.util.ClassLoaderUtil;
import com.yinghua.translation.util.OrderNoUtil;

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
	private PackageProductBean packageProductBean;
	
	@EJB
	private AccountBean accountBean;
	
	@EJB
	private CallHistoryBean callHistoryBean;

	@EJB
	private OrderBean orderBean;

	@EJB
	private MemberOrderBean memberOrderBean;
	
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
		
		String uno = obj.getString("uno");
		String prod_no = Objects.toString(obj.getString("packageNo"), "0");
		Float orderPrice = obj.getFloat("price");
		int price = (int)(orderPrice*100);
		String pay_way = obj.getString("pay_way");
		Date service_time = obj.getDate("service_time");
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
			order.setServiceTime(service_time);
			order.setSurplusCallDuration(packageProduct.getSurplusCallDuration()*use_date);
			order.setUseDate(use_date);
			order.setUseState(OrderUseStatus.PERPARE);
			order.setState(OrderStatus.CREATED);
			order.setOrderPrice(String.valueOf(orderPrice));
			order.setPayWay(pay_way);
			
			Long id = memberOrderBean.createOrder(order);
			
			Map<String, Object> chargeMap = new HashMap<String, Object>();
			Charge charge;
			if (id > 0)
			{
				//生成订单对象返回给app
				chargeMap.put("amount", price);
				chargeMap.put("currency", "cny");
				chargeMap.put("subject", order.getPackageName());
				chargeMap.put("body", "Your Body");
				chargeMap.put("order_no", order.getOrderNo());
				
				switch (pay_way)
				{
					case "0":
						req.put("order_no", order.getOrderNo());
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
							req.put("order_no", order.getOrderNo());
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
						chargeMap.put("order_no",  order.getOrderNo());
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
							req.put("order_no", order.getOrderNo());
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
						req.put("order_no", order.getOrderNo());
						req.put("amount", order.getOrderPrice());
						req.put("pay_way", order.getPayWay());
//						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
					case "4":
						req.put("order_no", order.getOrderNo());
						req.put("amount", order.getOrderPrice());
						req.put("pay_way", order.getPayWay());
//						req.put("credential", "");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						break;
				}
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
		String callee = Objects.toString(obj.getString("callee"),"null");
		Map<String, Object> req = new HashMap<>();
		// 此处更新三方通话号码
		try
		{
			Constant.call3Map.put(caller, callee);
			System.out.println("caller:"+caller+",callee:"+Constant.call3Map.get(caller));
			System.out.println("三方通话开始！收到第三方号码参数");
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
	 * 发送第三方通话号码
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
				 MemberOrder order = memberOrderBean.findByOrderNo(orderNo);
				 if(order!=null){
					 order.setState(OrderStatus.FINISHED);
					 memberOrderBean.updateOrder(order);
					 System.out.println("orderNo:"+order+OrderStatus.FINISHED);
					 
				 }else{
					 //记录日志
				 }
			 }
	        }
		 req.put("result", "ok");
		return req;
	}
	
	
}
