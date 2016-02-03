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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alibaba.fastjson.JSONObject;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.yinghua.translation.model.Account;
import com.yinghua.translation.model.BaseProduct;
import com.yinghua.translation.model.LanuageTariff;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.MemberOrderUse;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.PackageProduct;
import com.yinghua.translation.model.PackageProductContent;
import com.yinghua.translation.model.PartnerCode;
import com.yinghua.translation.model.Preferential;
import com.yinghua.translation.model.Product;
import com.yinghua.translation.model.enumeration.OrderStatus;
import com.yinghua.translation.service.AccountBean;
import com.yinghua.translation.service.BaseProductBean;
import com.yinghua.translation.service.LanuageTariffBean;
import com.yinghua.translation.service.MemberBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberOrderUseBean;
import com.yinghua.translation.service.MemberPackageBean;
import com.yinghua.translation.service.OrderBean;
import com.yinghua.translation.service.PackageProductBean;
import com.yinghua.translation.service.PackageProductContentBean;
import com.yinghua.translation.service.PartnerCodeBean;
import com.yinghua.translation.service.PaymentBean;
import com.yinghua.translation.service.PreferentialBean;
import com.yinghua.translation.service.ProductBean;
import com.yinghua.translation.util.ClassLoaderUtil;

@Path("/consume")
@RequestScoped
public class MemberPackageRESTService {

	@Inject
	private Logger log;

	@EJB
	private MemberPackageBean memberPackageBean;

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
	private OrderBean orderBean;

	@EJB
	private MemberOrderBean memberOrderBean;

	@EJB
	private MemberOrderUseBean memberOrderUseBean;

	@EJB
	private LanuageTariffBean lanuageTariffBean;
	@EJB
	private PartnerCodeBean partnercodebean;
	@EJB
	private MemberBean memberbean;
	@EJB
	private PreferentialBean preferentialbean;

	// private static final String key = "p5tvi9dst3wi4";
	// private static final String secret = "9jcy7Nbq3OU0YB";
	private Properties keyPro = ClassLoaderUtil.getProperties("key.properties");

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public MemberPackage lookupMemberById(@PathParam("id") long id) {
		MemberPackage member = memberPackageBean.findById(id);
		if (member == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return member;
	}

	/**
	 * 查询用户资费列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/standardFee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> standardFee(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uid"), "0");

		// List<MemberPackage> pack = memberPackageBean.finByMemberId(Objects
		// .toString(obj.getString("uno"), "0"));
		// List<MemberPackage> list = new ArrayList<>();
		List<LanuageTariff> lan = lanuageTariffBean.findAll();
		if (lan != null) {
			req.put("fee_list", lan);
			req.put("voip", keyPro.getProperty("voip"));
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 获取安卓当前版本号
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getVersion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getVersion(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String version = Objects.toString(obj.getString("version"), "0");

		if (!version.equals(keyPro.getProperty("version"))) {
			req.put("version", keyPro.getProperty("version"));
			req.put("desc", "请更新最新版本");
			req.put("downloadUrl", keyPro.getProperty("downloadUrl"));
			req.put("result", "fail");
			req.put("error_code", "8000");
			req.put("error_msg", "需要更新版本");
		} else {
			req.put("version", version);
			req.put("desc", "已是最新版本");
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		}
		return req;
	}

	/**
	 * 获取安卓当前版本号
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getIosVersion")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getIosVersion(String params) {
		Map<String, Object> req = new HashMap<>();
//		JSONObject obj = JSONObject.parseObject(params);
//		String version = Objects.toString(obj.getString("version"), "0");
		if (keyPro.getProperty("iosVersion")!=null&&keyPro.getProperty("iosVersion").length()>0) {
			req.put("version", keyPro.getProperty("iosVersion"));
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "请求成功");
		} else {
			req.put("result", "fail");
			req.put("error_code", "2001");
			req.put("error_msg", "获取版本失败");
		}
		return req;
	}
	
	/**
	 * 查询用户订购列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/userOrders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> userOrders(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uno"), "0");

		List<MemberOrder> list = memberOrderBean.findByUid(uid);
		if (list != null && list.size() > 0) {
			req.put("orders", list);
			req.put("count", list.size());
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 查询用户待支付订单
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/userNoPayOrders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> userNoPayOrders(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uno"), "0");

		List<MemberOrder> list = memberOrderBean.findNoPayByUid(uid);
		if (list != null && list.size() > 0) {
			req.put("orders", list);
			req.put("count", list.size());
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 查询用户全部服务
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/userAllService")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> userAllService(String params) {

		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uno"), "0");
		List<MemberOrder> list3 = memberOrderBean.findUsingOrderByUno(uid, "1");
		List<MemberOrder> list = memberOrderBean.findUsingOrderByUno(uid, "3");
		List<MemberOrder> list2 = memberOrderBean.findUsingOrderByUno(uid, "2");
		if(list!=null){
			if(list2!=null&&list2.size()>0){
				list.addAll(list2);
			}
		}else{
			if(list2!=null&&list2.size()>0){
				list=list2;
			}
		}
		
		if(list!=null||list3!=null){
			List<Map<String, Object>> life = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> hurry = new LinkedList<Map<String, Object>>();
			Map<String, Integer> resultMap = new HashMap<String, Integer>();
			Date serviceEndTime = null;
			List<BaseProduct> bps = baseProductBean.findAll();
			Map<String, BaseProduct> bpMap = new HashMap<String, BaseProduct>();
			for (BaseProduct baseProduct : bps) {
				bpMap.put(baseProduct.getProductNo(), baseProduct);
			}
			if(list3!=null&&list3.size()>0){
				serviceEndTime = list3.get(0).getServiceEndTime();
				for (MemberOrder memberOrder : list3) {
					List<MemberOrderUse> useList = memberOrderUseBean
							.findByOrderNo(memberOrder.getOrderNo());
					for (MemberOrderUse memberOrderUse : useList) {
						resultMap.put(memberOrderUse.getProductNo(), 999);
					}
				}
			}
			
			if(list!=null&&list.size()>0){
				serviceEndTime = list.get(0).getServiceEndTime();
				for (MemberOrder memberOrder : list) {
					List<MemberOrderUse> useList = memberOrderUseBean
							.findByOrderNo(memberOrder.getOrderNo());
					for (MemberOrderUse memberOrderUse : useList) {
						String pNo = memberOrderUse.getProductNo();
						int times = memberOrderUse.getTimes();
						if (resultMap.containsKey(pNo)) {
							if(resultMap.get(memberOrderUse.getProductNo())!=999){
								times = resultMap.get(memberOrderUse.getProductNo())
										+ times;
							}else{
								times = resultMap.get(memberOrderUse.getProductNo());
							}
						}
						resultMap.put(pNo, times);
					}
				}
			}
			
			for (String pNo : resultMap.keySet()) {
				BaseProduct bp = bpMap.get(pNo);
				Map<String, Object> pkgMap = new HashMap<String, Object>();
				pkgMap.put("productNo", pNo);
				pkgMap.put("productName", bp.getProductName());
				pkgMap.put("serviceType", bp.getServiceType());
				pkgMap.put("times", resultMap.get(pNo));
				if ("生活类".equals(bp.getServiceType())) {
					life.add(pkgMap);
				} else if ("应急类".equals(bp.getServiceType())) {
					hurry.add(pkgMap);
				}
			}

			req.put("life", life);
			req.put("lifeCount", life.size());
			req.put("hurry", hurry);
			req.put("hurryCount", hurry.size());
			req.put("seviceEndTime", serviceEndTime);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "查无信息");
		}
		
		return req;
	}

	/**
	 * 取消用户待支付订单
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/cancelUserOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> cancelUserOrder(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String orderNo = Objects.toString(obj.getString("orderNo"), "0");
		MemberOrder order = memberOrderBean.findByOrderNo(orderNo);
		if (order != null) {
			memberOrderBean.remove(order);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "无此订单信息");
		}
		return req;
	}

	/**
	 * 查询产品列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/products")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> products(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uid"), "0");

		List<Product> pack = productBean.findAll();
		if (pack != null) {
			req.put("count", Objects.toString(pack.size(), "0"));
			req.put("products", pack);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20002");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 查询产品列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/baseProducts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> baseProducts(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("uid"), "0");

		List<BaseProduct> pack = baseProductBean.findAll();
		if (pack != null) {
			req.put("count", Objects.toString(pack.size(), "0"));
			req.put("baseProducts", pack);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20002");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 查询套餐详情列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/packageDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> packageDetail(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String packageNo = Objects.toString(obj.getString("packageNo"), "0");

		List<PackageProductContent> pack = packageProductContentBean
				.findByPackageNo(packageNo);
		if (pack != null) {
			List<Map<String, Object>> packageProduct = new LinkedList<Map<String, Object>>();
			for (PackageProductContent packageProductContent : pack) {
				BaseProduct bp = baseProductBean
						.findByProductNo(packageProductContent.getProductNo());
				if (bp != null) {
					Map<String, Object> ppMap = new HashMap<String, Object>();
					ppMap.put("type", packageProductContent.getType());
					ppMap.put("times", packageProductContent.getTimes());
					ppMap.put("productNo", bp.getProductNo());
					ppMap.put("productName", bp.getProductName());
					ppMap.put("bidPrice", bp.getBidPrice());
					ppMap.put("price", bp.getPrice());
					ppMap.put("desc", bp.getDesc());
					ppMap.put("serviceType", bp.getServiceType());
					packageProduct.add(ppMap);
				}
			}

			req.put("count", Objects.toString(pack.size(), "0"));
			req.put("baseProducts", packageProduct);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20002");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 查询套餐列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/packages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> packages(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String type = Objects.toString(obj.getString("package_type"), "0");

		// List<PackageProduct> pack =
		// packageProductBean.findByPackageType(type);
		List<PackageProduct> pack = packageProductBean.findAll();
		if (pack != null && pack.size() > 0) {
			req.put("count", Objects.toString(pack.size(), "0"));
			req.put("packages", pack);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20002");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 查询套餐列表
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/packageProducts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> packageProducts(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);

		String type = Objects.toString(obj.getString("package_type"), "0");

		// List<PackageProduct> pack =
		// packageProductBean.findByPackageType(type);
		List<PackageProduct> pack = packageProductBean.findAll();
		if (pack != null && pack.size() > 0) {
			req.put("count", Objects.toString(pack.size(), "0"));
			req.put("packages", pack);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20002");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 用户余额支付
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/payWithAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> payWithAccount(String params) {
		JSONObject obj = JSONObject.parseObject(params);
		// String contacts = obj.getString("contacts");
		String uid = Objects.toString(obj.get("uid"), "0");
		String uno = Objects.toString(obj.get("uno"), "0");
		String order_no = Objects.toString(obj.get("order_no"), "0");
		String amount = Objects.toString(obj.get("amount"), "0");
		String trade_pwd = Objects.toString(obj.get("trade_pwd"), "0");
		Map<String, Object> req = new HashMap<>();

		Account acc = accountBean.findByMemberNo(uno);
		Order ord = orderBean.findByOrderNo(order_no);
		if (acc != null) {
			if (ord != null) {
				if (trade_pwd.equals(acc.getPaymentPassword())) {
					acc.setAccountBalance(acc.getAccountBalance().subtract(
							new BigDecimal(amount)));
					accountBean.updateAccount(acc);

					ord.setState(OrderStatus.PAY_SUCCESS);
					orderBean.updateOrder(ord);

					Product product = productBean.findByProductNo(ord
							.getProdId());
					MemberPackage mp = memberPackageBean.findMemberPackage(uno,
							ord.getLanguages().toString());
					if (mp != null) {
						mp.setImGiftCount(product.getMultimediaMessage());
						mp.setImPreferEnd(product.getEndTime());
						mp.setImPreferFee(product.getPrice());
						// mp.setImPreferStart(product.getStartTime());

						mp.setTelGiftAmount(mp.getTelGiftAmount()
								+ product.getSurplusCallDuration());
						// mp.setTelPreferEnd(telPreferEnd);
						// mp.setTelPreferStart(telPreferStart);
						mp.setTelPreferFee(product.getPrice());
						// mp.setTelTreeStart(product.getStartTime());
						mp.setTelFreeEnd(product.getEndTime());

						memberPackageBean.updateMemberPackage(mp);
					} else {
						MemberPackage mm = new MemberPackage();
						mm.setImGiftCount(product.getMultimediaMessage());
						mm.setImPreferEnd(product.getEndTime());
						mm.setImPreferFee(product.getPrice());
						mm.setMemberNumber(uno);
						// mp.setImPreferStart(product.getStartTime());

						mm.setTelGiftAmount(product.getSurplusCallDuration());
						// mp.setTelPreferEnd(telPreferEnd);
						// mp.setTelPreferStart(telPreferStart);
						mm.setTelPreferFee(product.getPrice());
						mm.setLanguages(product.getLanguages());
						// mp.setTelTreeStart(product.getStartTime());
						mm.setTelFreeStart(new Date(System.currentTimeMillis()));
						mm.setTelFreeEnd(product.getEndTime());
						mm.setCreateTime(new Date(System.currentTimeMillis()));
						memberPackageBean.create(mm);
					}

					req.put("result", "success");
					req.put("error_code", "000000");
					req.put("error_msg", "支付成功");
				} else {
					req.put("result", "fail");
					req.put("error_code", "3001");
					req.put("error_msg", "支付密码错误");
				}
			} else {
				req.put("result", "fail");
				req.put("error_code", "3002");
				req.put("error_msg", "查无订单信息");
			}
		} else {
			req.put("result", "fail");
			req.put("error_code", "3003");
			req.put("error_msg", "查无用户信息");
		}
		return req;
	}

	/**
	 * 验证支付结果
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/verifyPayment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> verifyPayment(String params) {
		JSONObject obj = JSONObject.parseObject(params);
		String pay_way = obj.getString("pay_way");
		String uid = Objects.toString(obj.get("uid"), "0");
		String uno = Objects.toString(obj.get("uno"), "0");
		String order_no = Objects.toString(obj.get("order_no"), "0");
		String amount = Objects.toString(obj.get("amount"), "0");
		String trans_no = Objects.toString(obj.get("trans_no"), "0");
		Map<String, Object> req = new HashMap<>();

		Order ord = orderBean.findByOrderNo(order_no);
		Account account = accountBean.findByMemberNo(uno);
		switch (pay_way) {
		case "0":
			req.put("state", ord.getState());
			break;
		case "1":
			break;
		case "2":
			try {
				boolean result = paymentBean.retrieve(trans_no);
				if (result) {
					ord.setState(OrderStatus.PAY_SUCCESS);
					orderBean.updateOrder(ord);
					req.put("state", ord.getState());

					Product product = productBean.findByProductNo(ord
							.getProdId());
					if ("1".equals(product.getType())) {
						account.setAccountBalance(account.getAccountBalance()
								.add(product.getDenomination()));
						accountBean.updateAccount(account);
						req.put("result", "true");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						return req;
					}
					MemberPackage mp = memberPackageBean.findMemberPackage(uno,
							ord.getLanguages().toString());
					if (mp != null) {
						mp.setImGiftCount(product.getMultimediaMessage());
						mp.setImPreferEnd(product.getEndTime());
						mp.setImPreferFee(product.getPrice());
						// mp.setImPreferStart(product.getStartTime());

						mp.setTelGiftAmount(mp.getTelGiftAmount()
								+ product.getSurplusCallDuration());
						// mp.setTelPreferEnd(telPreferEnd);
						// mp.setTelPreferStart(telPreferStart);
						mp.setTelPreferFee(product.getPrice());
						// mp.setTelTreeStart(product.getStartTime());
						mp.setTelFreeEnd(product.getEndTime());

						memberPackageBean.updateMemberPackage(mp);
						req.put("result", "true");
						req.put("error_code", "000000");
						req.put("error_msg", "");
					} else {
						MemberPackage mm = new MemberPackage();
						mm.setImGiftCount(product.getMultimediaMessage());
						mm.setImPreferEnd(product.getEndTime());
						mm.setImPreferFee(product.getPrice());
						mm.setMemberNumber(uno);
						mm.setLanguages(product.getLanguages());
						// mp.setImPreferStart(product.getStartTime());

						mm.setTelGiftAmount(product.getSurplusCallDuration());
						// mp.setTelPreferEnd(telPreferEnd);
						// mp.setTelPreferStart(telPreferStart);
						mm.setTelPreferFee(product.getPrice());
						// mp.setTelTreeStart(product.getStartTime());
						mm.setTelFreeStart(new Date(System.currentTimeMillis()));
						mm.setTelFreeEnd(product.getEndTime());
						mm.setCreateTime(new Date(System.currentTimeMillis()));
						memberPackageBean.create(mm);
						req.put("result", "true");
						req.put("error_code", "000000");
						req.put("error_msg", "");
					}
				} else {
					req.put("result", "fail");
					req.put("error_code", "3013");
					req.put("error_msg", "支付未成功");
				}
			} catch (Exception e) {
				req.put("result", "fail");
				req.put("error_code", "3012");
				req.put("error_msg", "");
			}
			break;
		case "3":
			break;
		case "4":
			OAuthTokenCredential tokenCredential = new OAuthTokenCredential(
					"AUPmsszy8ViseOYu4UHVarnMnDAkXYQqNHhpR68KBSG3RQR8_ZCYNoQrlmzE1OOf5Y9VQt9z1efPwgVW",
					"EKv6f6JGf_Ldh70tRIhgFf0b5oZxFQHiX0jT3qF-fAdW0UutY-U1Ay4hA3CI3EeVvIbEfhtmqgSk0YKM");
			String accessToken;
			Payment payment = new Payment();
			try {
				accessToken = tokenCredential.getAccessToken();
				payment = Payment.get(accessToken, order_no);
				if ("approved".equals(payment.getState())) {
					ord.setState(OrderStatus.PAY_SUCCESS);
					orderBean.updateOrder(ord);
					req.put("state", ord.getState());

					Product product = productBean.findByProductNo(ord
							.getProdId());
					if ("1".equals(product.getType())) {
						account.setAccountBalance(account.getAccountBalance()
								.add(product.getDenomination()));
						accountBean.updateAccount(account);
						req.put("result", "true");
						req.put("error_code", "000000");
						req.put("error_msg", "");
						return req;
					}
					MemberPackage mp = memberPackageBean.findMemberPackage(uno,
							ord.getLanguages().toString());
					if (mp != null) {
						mp.setImGiftCount(product.getMultimediaMessage());
						mp.setImPreferEnd(product.getEndTime());
						mp.setImPreferFee(product.getPrice());
						// mp.setImPreferStart(product.getStartTime());

						mp.setTelGiftAmount(mp.getTelGiftAmount()
								+ product.getSurplusCallDuration());
						// mp.setTelPreferEnd(telPreferEnd);
						// mp.setTelPreferStart(telPreferStart);
						mp.setTelPreferFee(product.getPrice());
						// mp.setTelTreeStart(product.getStartTime());
						mp.setTelFreeEnd(product.getEndTime());

						memberPackageBean.updateMemberPackage(mp);
						req.put("result", "true");
						req.put("error_code", "000000");
						req.put("error_msg", "");
					} else {
						MemberPackage mm = new MemberPackage();
						mm.setImGiftCount(product.getMultimediaMessage());
						mm.setImPreferEnd(product.getEndTime());
						mm.setImPreferFee(product.getPrice());
						mm.setMemberNumber(uno);
						mm.setLanguages(product.getLanguages());
						// mp.setImPreferStart(product.getStartTime());

						mm.setTelGiftAmount(product.getSurplusCallDuration());
						// mp.setTelPreferEnd(telPreferEnd);
						// mp.setTelPreferStart(telPreferStart);
						mm.setTelPreferFee(product.getPrice());
						// mp.setTelTreeStart(product.getStartTime());
						mm.setTelFreeStart(new Date(System.currentTimeMillis()));
						mm.setTelFreeEnd(product.getEndTime());
						mm.setCreateTime(new Date(System.currentTimeMillis()));
						memberPackageBean.create(mm);
						req.put("result", "true");
						req.put("error_code", "000000");
						req.put("error_msg", "");
					}
				} else {
					req.put("result", "fail");
					req.put("error_code", "3013");
					req.put("error_msg", "支付未成功");
				}

			} catch (PayPalRESTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				req.put("result", "fail");
				req.put("error_code", "3012");
				req.put("error_msg", "");
			}
			break;
		}

		return req;
	}

	/**
	 * 查询用户订单
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/orderList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> orderList(String params) {
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.get("uid"), "0");
		String uno = Objects.toString(obj.get("uno"), "0");
		Map<String, Object> req = new HashMap<>();

		List<Order> ord = orderBean.findByUid(uno);
		if (ord != null) {
			req.put("count", Objects.toString(ord.size(), "0"));
			req.put("orders", ord);
		} else {
			req.put("count", "0");
			req.put("orders", ord);
		}

		return req;
	}

	/**
	 * 查询用户订单
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/orderDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> orderDetail(String params) {
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.get("uid"), "0");
		String order_no = Objects.toString(obj.get("order_no"), "0");
		Map<String, Object> req = new HashMap<>();

		Order ord = orderBean.findByOrderNo(order_no);
		if (ord != null) {
			req.put("order", ord);
		} else {
			req.put("order", ord);
		}

		return req;
	}

	public static void main(String[] args) {
		OAuthTokenCredential tokenCredential = new OAuthTokenCredential(
				"AUPmsszy8ViseOYu4UHVarnMnDAkXYQqNHhpR68KBSG3RQR8_ZCYNoQrlmzE1OOf5Y9VQt9z1efPwgVW",
				"EKv6f6JGf_Ldh70tRIhgFf0b5oZxFQHiX0jT3qF-fAdW0UutY-U1Ay4hA3CI3EeVvIbEfhtmqgSk0YKM");
		String accessToken;
		Payment payment = new Payment();
		try {
			accessToken = tokenCredential.getAccessToken();
			System.out.println("----------------------------------------------"
					+ accessToken + "------------------------------------");
			// payment = Payment.get(accessToken,
			// "PAY-5YK922393D847794YKER7MUI");
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 邀请码优惠查询(单条数据)
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/invitationCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> invitationCode(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("code"), "0");
		List<PartnerCode> list = partnercodebean.findByUid(uid);
		if (list != null && list.size() > 0) {
			req.put("orders", list.get(0));
			req.put("count", list.size());
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "查无信息");
		}
		return req;
	}

	/**
	 * 邀请码优惠查询(全部)
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/invitationCodeAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> invitationCodeAll(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String uid = Objects.toString(obj.getString("code"), "0");
		List<PartnerCode> list = partnercodebean.findAll();
		if (list != null && list.size() > 0) {
			req.put("orders", list);
			req.put("count", list.size());
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		} else {
			req.put("result", "fail");
			req.put("error_code", "20001");
			req.put("error_msg", "查无信息");
		}
		return req;

	}

	/**
	 * 优惠率接口
	 */
	@POST
	@Path("/preferentia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> preferentia(String params) {
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String mobile = Objects.toString(obj.getString("uno"), "0");// 传用户名
		// 先查询member表 查出来邀请码再说下一步
		Member findByMemberNo = memberbean.findByMemberNo(mobile);
		if (findByMemberNo != null) {
			String code = findByMemberNo.getCode();
			// 根据邀请码过期时间判断 是否有效
			if (code != null && !code.equals("")) {
				 PartnerCode findByPartnerCodeNo = partnercodebean.findByPartnerCodeNo(code);
				if (findByPartnerCodeNo != null) {
					Date serviceStartTime = findByPartnerCodeNo
							.getServiceStartTime();// 开始时间
					Date serviceEndTime = findByPartnerCodeNo
							.getServiceEndTime();// 结束时间
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
					String time = format.format(date);
					Date d = null;
					try {
						d = format.parse(time);

					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (serviceStartTime.after(d) != true) {// 邀请码 开始时间
						if (serviceEndTime.before(d) != true) { // 邀请码结束时间

							String saleStrategyNo = findByPartnerCodeNo
									.getSaleStrategyNo();// 得到邀请码表数据（优惠策略标识）
							Preferential findByPreferentialNo = preferentialbean
									.findByPreferentialNo(saleStrategyNo);// 查询优惠表
							BigDecimal preferential = findByPreferentialNo
									.getPreferential();// 优惠参数
							String describe = findByPreferentialNo
									.getDescribe();//优惠描述
							req.put("preferential", preferential);
							req.put("describe", describe);//优惠描述
							req.put("result", "success");
							req.put("error_code", "000000");
							req.put("error_msg", "");
						} else {
							req.put("result", "success");
							req.put("preferential", 1);
							req.put("error_code", "000000");
							req.put("error_msg", "");
							// req.put("error_code", "20001");
							// req.put("error_msg", "邀请码已过期");
						}
					} else {
						req.put("result", "success");
						req.put("preferential", 1);
						req.put("error_code", "000000");
						req.put("error_msg", "");
						// req.put("error_code", "20001");
						// req.put("error_msg", "邀请码未到激活时间");
					}
				}else {
					req.put("result", "success");
					req.put("preferential", 1);
					req.put("error_code", "000000");
					req.put("error_msg", "");
					// req.put("error_code", "20001");
					// req.put("error_msg", "查无邀请码");
				}
			} else {
				req.put("result", "success");
				req.put("preferential", 1);
				req.put("error_code", "000000");
				req.put("error_msg", "");
				// req.put("error_code", "20001");
				// req.put("error_msg", "查无邀请码");
			}
		} else {
			req.put("result", "success");
			req.put("preferential", 1);
			req.put("error_code", "000000");
			req.put("error_msg", "");
			// req.put("error_code", "20001");
			// req.put("error_msg", "");
		}

		return req;
	}

}
