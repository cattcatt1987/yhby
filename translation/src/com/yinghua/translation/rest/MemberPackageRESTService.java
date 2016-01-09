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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
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
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.PackageProduct;
import com.yinghua.translation.model.PackageProductContent;
import com.yinghua.translation.model.Product;
import com.yinghua.translation.model.enumeration.OrderStatus;
import com.yinghua.translation.service.AccountBean;
import com.yinghua.translation.service.BaseProductBean;
import com.yinghua.translation.service.LanuageTariffBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberPackageBean;
import com.yinghua.translation.service.OrderBean;
import com.yinghua.translation.service.PackageProductBean;
import com.yinghua.translation.service.PackageProductContentBean;
import com.yinghua.translation.service.PaymentBean;
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
	private LanuageTariffBean lanuageTariffBean;

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

//		List<MemberPackage> pack = memberPackageBean.finByMemberId(Objects
//				.toString(obj.getString("uno"), "0"));
//		List<MemberPackage> list = new ArrayList<>();
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
	 * 查询套餐列表
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
		if (list != null&&list.size()>0) {
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
		if (list != null&&list.size()>0) {
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
		String orderNo = Objects.toString(obj.getString("orderNo"),"0");
		MemberOrder order = memberOrderBean.findByOrderNo(orderNo);
		if(order!=null){
			memberOrderBean.remove(order);
			req.put("result", "success");
			req.put("error_code", "000000");
			req.put("error_msg", "");
		}else{
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

		List<PackageProductContent> pack = packageProductContentBean.findByPackageNo(packageNo);
		if (pack != null) {
			List<Map<String,Object>> packageProduct = new LinkedList<Map<String,Object>>();
			for (PackageProductContent packageProductContent : pack) {
				BaseProduct bp = baseProductBean.findByProductNo(packageProductContent.getProductNo());
				if(bp!=null){
					Map<String,Object> ppMap = new HashMap<String,Object>();
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

//		List<PackageProduct> pack = packageProductBean.findByPackageType(type);
		List<PackageProduct> pack = packageProductBean.findAll();
		if (pack != null&&pack.size()>0) {
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

//		List<PackageProduct> pack = packageProductBean.findByPackageType(type);
		List<PackageProduct> pack = packageProductBean.findAll();
		if (pack != null&&pack.size()>0) {
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

					Product product = productBean.findByProductNo(ord.getProdId());
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

					Product product = productBean.findByProductNo(ord.getProdId());
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
				if("approved".equals(payment.getState()))
				{
					ord.setState(OrderStatus.PAY_SUCCESS);
					orderBean.updateOrder(ord);
					req.put("state", ord.getState());

					Product product = productBean.findByProductNo(ord.getProdId());
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
				}
				else
				{
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

}
