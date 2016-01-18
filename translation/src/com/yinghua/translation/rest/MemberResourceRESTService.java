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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.alibaba.fastjson.JSONObject;
import com.yinghua.translation.Constant;
import com.yinghua.translation.model.Account;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.PartnerCode;
import com.yinghua.translation.model.ThirdMember;
import com.yinghua.translation.model.enumeration.Gender;
import com.yinghua.translation.model.enumeration.LoginType;
import com.yinghua.translation.model.enumeration.MemberStatus;
import com.yinghua.translation.model.enumeration.MemberType;
import com.yinghua.translation.model.enumeration.OrderStatus;
import com.yinghua.translation.model.enumeration.OrderUseStatus;
import com.yinghua.translation.rongcloud.io.rong.ApiHttpClient;
import com.yinghua.translation.rongcloud.io.rong.models.FormatType;
import com.yinghua.translation.rongcloud.io.rong.models.SdkHttpResult;
import com.yinghua.translation.service.AccountBean;
import com.yinghua.translation.service.MemberBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberPackageBean;
import com.yinghua.translation.service.PartnerCodeBean;
import com.yinghua.translation.service.ThirdMemberBean;
import com.yinghua.translation.util.ClassLoaderUtil;
import com.yinghua.translation.util.HttpRequester;
import com.yinghua.translation.util.HttpRespons;
import com.yinghua.translation.util.OrderNoUtil;
import com.yinghua.translation.util.PropertiesUtil;
import com.yinghua.translation.util.RandomNum;
import com.yinghua.translation.util.UUIDUtil;

@Path("/user")
@RequestScoped
public class MemberResourceRESTService
{

	@Inject
	private Logger log;

	@EJB
	private MemberBean repository;

	@EJB
	private ThirdMemberBean thirdMemberBean;

	@EJB
	private AccountBean accountBean;

	@EJB
	private MemberPackageBean memberPackageBean;

	@EJB
	private MemberOrderBean memberOrderBean;
	
	@Inject
	private HttpRequester httpRequester;
	@EJB
	private PartnerCodeBean partnercodebean;

	// private static final String key = "p5tvi9dst3wi4";
	// private static final String secret = "9jcy7Nbq3OU0YB";

	private Properties prop = ClassLoaderUtil.getProperties("cache.properties");
	private Properties keyPro = ClassLoaderUtil.getProperties("key.properties");

	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> listAllMembers()
	{
		return repository.findAllOrderedByName();
	}

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member lookupMemberById(@PathParam("id") long id)
	{
		Member member = repository.findById(id);
		if (member == null)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return member;
	}

	/**
	 * 短信验证码发送
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getVeriyCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getVeriyCode(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String code = RandomNum.getStringNum(4);
		Map<String, String> param = new HashMap<>();

//		if ("1".equals(Objects.toString(obj.getString("type"), "null")))
//		{
//			Member member = repository.findByMobile(obj.getString("mobile"));
//			if (member != null)
//			{
//				// 返回用户已注册
//				req.put("result", "fail");
//				req.put("error_code", "3001");
//				req.put("error_msg", "手机号已注册");
//				return req;
//			}
//		}
		param.put("account", "novobabel");
		param.put("password", "novobabel1");
		param.put("mobile", obj.getString("mobile"));

		try
		{
			param.put("content", URLEncoder.encode("您的验证码是：" + code
					+ "。如需帮助请联系客服。", "UTF-8"));
			// HttpRespons hr = httpRequester.sendPost(
			// "http://www.106jiekou.com/webservice/sms.asmx/Submit",
			// param);
			HttpRespons hr = httpRequester.sendPost(
					"http://sms.106jiekou.com/utf8/sms.aspx?", param);
			// JSONObject res = JSONObject.parseObject(hr.getContent());
			if (hr.getContent().toString().equals("100"))
			{
				PropertiesUtil.writeProperties(obj.getString("mobile"), code);
				req.put("result", "success");
				req.put("error_code", "000000");
				req.put("error_msg", "");
			}
			else
			{
				// 验证码发送失败
				req.put("result", "fail");
				req.put("error_code", "4002");
				req.put("error_msg", "短信发送失败");
			}
		}
		catch (IOException e)
		{
			log.info(e.getMessage());
			req.put("result", "fail");
			req.put("error_code", "4002");
			req.put("error_msg", "短信发送失败");
		}

		return req;
	}
	
	@POST
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void test(String params){
		System.out.println("------");
		PropertiesUtil.writeProperties("18601270952", "123456");
		System.out.println("---000---");
	}
	
	/**
	 * 用户登录
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> login(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		Member member = repository.findByMobile(obj.getString("mobile"));
		if (member == null)
		{
			// 用户不存在
			req.put("uid", "");
			req.put("im_token", "");
			req.put("purchased", "");
			req.put("error_code", "1001");
			req.put("error_msg", "用户不存在");
		}
		else
		{
			if (Objects.toString(obj.getString("pwd"), "null").equals(
					member.getPassword()))
			{
				if ("null"
						.equals(Objects.toString(member.getImToken(), "null")))
				{
					try
					{
						SdkHttpResult result = ApiHttpClient.getToken(
								keyPro.getProperty("appKey"),
								keyPro.getProperty("appSecret"),
								obj.getString("mobile"), "", "",
								FormatType.json);
						JSONObject objj = (JSONObject) JSONObject.parse(result
								.getResult());
						member.setImToken(objj.getString("token"));

					}
					catch (Exception e)
					{
						req.put("uid", "");
						req.put("im_token", "");
						req.put("purchased", "");
						req.put("error_code", "1002");
						req.put("error_msg", "成生token失败");
					}

				}

				member.setLongitude(obj.getString("longitude"));
				member.setLatitude(obj.getString("latitude"));
				member.setLocation(obj.getString("address"));
				repository.updateMember(member);
				// 登录成功
				req.put("uid", member.getId().toString());
				req.put("uno", member.getMemberNumber());
				req.put("completed", member.getIsCompleted().toString());
				req.put("name", member.getMemberName());
				req.put("gender", member.getMemberGender());
				req.put("mobile", member.getMobilePhone());
				req.put("local_lang", member.getLocalLang());
				req.put("user_type", member.getMemberType());
				req.put("cmpy_id", member.getCmpyId());
				req.put("avator", member.getMemberFace());
				req.put("im_token", member.getImToken());
				// req.put("purchased",
				// Objects.toString(member.getPurchased(), "false"));
				req.put("error_code", "000000");
				req.put("error_msg", "");
				// 保存用户所在国家

			}
			else
			{
				// 密码错误
				req.put("uid", "");
				req.put("im_token", "");
				req.put("purchased", "");
				req.put("error_code", "1001");
				req.put("error_msg", "密码错误");
			}
		}
		return req;
	}

	/**
	 * 用户注册登录
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/signon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> signon(String params)
	{
		Map<String, Object> req = new HashMap<>();
		try
		{
			JSONObject obj = JSONObject.parseObject(params);
			
			String code = this.prop.getProperty(obj.getString("mobile"));
			
			if (Objects.toString(obj.getString("verify_code"), "0").equals(
					code))
			{
				Member member = repository.findByMobile(obj.getString("mobile"));
				if (member == null)
				{
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
					member.setVoip(Constant.UUCALL_GROUP+obj.getString("mobile"));
					//查询邀请码
					String yqmcode = Objects.toString(obj.getString("code"), "2");
					List<PartnerCode> list = partnercodebean.findByUid(yqmcode);
					if (list != null && list.size() > 0) {
						member.setCode(list.get(0).getCode()); //保存邀请码到member表
					} else {
						req.put("result", "fail");
						req.put("error_code", "20001");
						req.put("error_msg", "邀请码查无信息");
					}
					Long id = repository.register(member);
					Account account = new Account();
					account.setAccountBalance(BigDecimal.ZERO);
					account.setAccountNumber(member.getMobilePhone());
					account.setChargingStandard(BigDecimal.ZERO);
					account.setMemberNumber(member.getMemberNumber());
					account.setCreateTime(new Date(System.currentTimeMillis()));
					account.setRechargeTimes(0);
					account.setSurplusCallDuration(36000);
					account.setStatus(MemberStatus.NORMAL);
					accountBean.register(account);
					
					
					MemberOrder mo = new MemberOrder();
					mo.setMemberNumber(member.getMemberNumber());
					mo.setOrderNo(OrderNoUtil.getOrderNo("OR"));
					mo.setOrderPrice("0");
					mo.setOrderTime(new Date());
					mo.setPackageDesc("问路、打车、租车、餐饮、退税、购物、酒店、购票、其他");
					mo.setPackageName("生活服务套餐");
					mo.setPackageNo("1002");
					mo.setPayWay("0");
					mo.setServiceTime(new Date());
					mo.setState(OrderStatus.FINISHED);
					mo.setUseState(OrderUseStatus.USING);
					mo.setUseDate(30);
					mo.setSurplusCallDuration(8000*30);
					mo.setPackageType("1");
					memberOrderBean.createOrder(mo);
	
				}
				else
				{
					// 用户存在，返回信息
//					if (Objects.toString(obj.getString("pwd"), "null").equals(
//							member.getPassword()))
//					{
						if ("null"
								.equals(Objects.toString(member.getImToken(), "null")))
						{
							try
							{
								SdkHttpResult result = ApiHttpClient.getToken(
										keyPro.getProperty("appKey"),
										keyPro.getProperty("appSecret"),
										obj.getString("mobile"), "", "",
										FormatType.json);
								JSONObject objj = (JSONObject) JSONObject.parse(result
										.getResult());
								member.setImToken(objj.getString("token"));
		
							}
							catch (Exception e)
							{
								req.put("uid", "");
								req.put("im_token", "");
								req.put("purchased", "");
								req.put("error_code", "1002");
								req.put("error_msg", "成生token失败");
							}
		
						}
		
						member.setLongitude(obj.getString("longitude"));
						member.setLatitude(obj.getString("latitude"));
//						member.setLocation(obj.getString("address"));
						member.setIsCompleted(1);
						repository.updateMember(member);
						
		
//					}
//					else
//					{
//						// 密码错误
//						req.put("uid", "");
//						req.put("im_token", "");
//						req.put("purchased", "");
//						req.put("error_code", "1001");
//						req.put("error_msg", "密码错误");
//					}
				}
				// 登录成功
				req.put("uid", member.getId().toString());
				req.put("uno", member.getMemberNumber());
				req.put("completed", member.getIsCompleted().toString());
				req.put("name", member.getMemberName());
				req.put("nickname", member.getNickname());
				req.put("email", member.getEmail());
				req.put("gender", member.getMemberGender());
				req.put("mobile", member.getMobilePhone());
				req.put("localation", member.getLocation());
				req.put("local_lang", member.getLocalLang());
				req.put("credentials_number", member.getCredentialsNumber());
				req.put("user_type", member.getMemberType());
				req.put("cmpy_id", member.getCmpyId());
				req.put("avator", member.getMemberFace());
				req.put("im_token", member.getImToken());
				// req.put("purchased",
				// Objects.toString(member.getPurchased(), "false"));
				req.put("error_code", "000000");
				req.put("error_msg", "");
				// 保存用户所在国家
			}
			else
			{
				req.put("uid", "");
				req.put("im_token", "");
				req.put("error_code", "3002");
				req.put("error_msg", "验证码错误");

			}
			
		}
		catch (Exception e)
		{
			// Handle generic exceptions
			log.info(e.getMessage());
			req.put("error_code", "3002");
			req.put("error_msg", e.getMessage());
		}
		return req;
	}
	
	
	/**
	 * 用户登录
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/openLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> openLogin(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		ThirdMember tmember = thirdMemberBean.findByOpenId(obj
				.getString("open_id"));
		Member member = new Member();
		// 如果在第三方用户表中查不到该授权，则保存新授权，并生成相应的用户记录
		if (tmember == null)
		{
			try
			{
				String uuid = UUIDUtil.genUUIDString();
				SdkHttpResult result = ApiHttpClient.getToken(
						keyPro.getProperty("appKey"),
						keyPro.getProperty("appSecret"), uuid, "", "",
						FormatType.json);
				JSONObject objj = (JSONObject) JSONObject.parse(result
						.getResult());
				member.setImToken(objj.getString("token"));
				member.setLongitude(obj.getString("longitude"));
				member.setLatitude(obj.getString("latitude"));
				member.setLocation(obj.getString("address"));
				member.setCreateTime(new Date(System.currentTimeMillis()));
				member.setIsCompleted(0);
				member.setStatus(MemberStatus.NORMAL);
				member.setMemberNumber(uuid);
				Long id = repository.register(member);
				Account account = new Account();
				account.setAccountBalance(BigDecimal.ZERO);
				account.setAccountNumber(member.getMobilePhone());
				account.setChargingStandard(BigDecimal.ZERO);
				account.setMemberNumber(member.getMemberNumber());
				account.setCreateTime(new Date(System.currentTimeMillis()));
				account.setRechargeTimes(0);
				account.setSurplusCallDuration(0);
				account.setStatus(MemberStatus.NORMAL);
				accountBean.register(account);
				ThirdMember tmp = new ThirdMember();
				tmp.setOpenId(obj.getString("open_id"));
				tmp.setOpenToken(obj.getString("open_token"));
				tmp.setType(LoginType.getOrderStatus(Integer.parseInt(Objects
						.toString(obj.getString("open_type"), "0"))));
				tmp.setMemberNumber(member.getMemberNumber());
				tmp.setCreateTime(new Date(System.currentTimeMillis()));
				thirdMemberBean.register(tmp);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				req.put("uid", "");
				req.put("im_token", "");
				req.put("purchased", "");
				req.put("error_code", "1002");
				req.put("error_msg", "成生token失败");
				return req;
			}
		}
		else
		{
			member = repository.findByMemberNo(tmember.getMemberNumber());
			// if (Objects.toString(obj.getString("pwd"), "null").equals(
			// member.getPassword()))
			// {
			if ("null".equals(Objects.toString(member.getImToken(), "null")))
			{
				try
				{
					SdkHttpResult result = ApiHttpClient.getToken(
							keyPro.getProperty("appKey"),
							keyPro.getProperty("appSecret"),
							tmember.getMemberNumber(), "", "", FormatType.json);
					JSONObject objj = (JSONObject) JSONObject.parse(result
							.getResult());
					member.setImToken(objj.getString("token"));

				}
				catch (Exception e)
				{
					req.put("uid", "");
					req.put("im_token", "");
					req.put("purchased", "");
					req.put("error_code", "1002");
					req.put("error_msg", "成生token失败");
					return req;
				}

			}

			member.setLocalLang(obj.getString("longitude"));
			member.setLatitude(obj.getString("latitude"));
			member.setLocation(obj.getString("address"));
			repository.updateMember(member);

			// 保存用户所在国家

			// }
			// else
			// {
			// // 密码错误
			// req.put("uid", "");
			// req.put("im_token", "");
			// req.put("purchased", "");
			// req.put("error_code", "1001");
			// req.put("error_msg", "密码错误");
			// }
		}

		// 登录成功
		req.put("uid", Objects.toString(member.getId(), "null"));
		req.put("uno", member.getMemberNumber());
		req.put("completed", member.getIsCompleted().toString());
		req.put("name", member.getMemberName());
		req.put("gender", member.getMemberGender());
		req.put("mobile", member.getMobilePhone());
		req.put("local_lang", member.getLocalLang());
		req.put("user_type", member.getMemberType());
		req.put("cmpy_id", Objects.toString(member.getCmpyId(), "null"));
		req.put("avator", member.getMemberFace());
		req.put("im_token", member.getImToken());
		// req.put("purchased",
		// Objects.toString(member.getPurchased(), "false"));
		req.put("error_code", "000000");
		req.put("error_msg", "");

		return req;
	}

	/**
	 * 重置密码
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/resetPwd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> resetPwd(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String code = this.prop.getProperty(obj.getString("mobile"));
		if (code.equals(obj.getString("verify_code")))
		{
			if (!"null".equals(Objects.toString(obj.getString("new_pwd"),
					"null")))
			{
				Member member = repository
						.findByMobile(obj.getString("mobile"));
				switch (obj.getString("pwd_type"))
				{
					case "1":
						member.setPassword(obj.getString("new_pwd"));
						// 此处更新用户信息
						repository.updateMember(member);
						break;
					case "2":
						Account account = accountBean.findByMemberNo(member
								.getMemberNumber());
						account.setPaymentAassword(obj.getString("new_pwd"));
						// 此处更新用户信息
						accountBean.updateAccount(account);
						break;
				}

				req.put("result", "success");
				req.put("error_code", "000000");
				req.put("error_msg", "");
			}
			else
			{
				req.put("result", "fail");
				req.put("error_code", "1001");
				req.put("error_msg", "密码错误");
			}

		}
		else
		{
			req.put("result", "fail");
			req.put("error_code", "1002");
			req.put("error_msg", "验证码错误");
		}
		return req;
	}

	/**
	 * 修改密码
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/changePwd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> changePwd(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		// String code = this.prop.getProperty(obj.getString("mobile"));
		String type = obj.getString("pwd_type");
		String uid = Objects.toString(obj.getString("uid"), "0");
		String uno = Objects.toString(obj.getString("uno"), "0");
		String old_pwd = Objects.toString(obj.getString("old_pwd"), "null");
		String new_pwd = Objects.toString(obj.getString("new_pwd"), "null");

		switch (type)
		{
			case "1":
				Member member = repository.findByMemberNo(uno);
				if (member != null)
				{
					if (old_pwd.equals(member.getPassword()))
					{
						if (!"".equals(new_pwd))
						{
							member.setPassword(new_pwd);
							// 此处更新用户信息
							repository.updateMember(member);

							req.put("result", "success");
							req.put("error_code", "000000");
							req.put("error_msg", "");
						}
						else
						{
							req.put("result", "fail");
							req.put("error_code", "1002");
							req.put("error_msg", "新密码不能为空");
						}

					}
					else
					{
						req.put("result", "fail");
						req.put("error_code", "1001");
						req.put("error_msg", "旧密码错误");
					}
				}
				break;
			case "2":
				Account acc = accountBean.findByMemberNo(uno);
				if (acc != null)
				{
					if (old_pwd.equals(acc.getPaymentPassword()))
					{
						if (!"".equals(new_pwd))
						{
							acc.setPaymentAassword(new_pwd);
							accountBean.updateAccount(acc);
							// 此处更新用户信息
							req.put("result", "success");
							req.put("error_code", "000000");
							req.put("error_msg", "");
						}
						else
						{
							req.put("result", "fail");
							req.put("error_code", "1002");
							req.put("error_msg", "新密码不能为空");
						}

					}
					else
					{
						req.put("result", "fail");
						req.put("error_code", "1001");
						req.put("error_msg", "旧密码错误");
					}
				}
				break;

			default:
				req.put("result", "fail");
				req.put("error_code", "1003");
				req.put("error_msg", "修改密码失败");
				break;
		}

		return req;
	}

	/**
	 * 用户注册
	 * 
	 * @param member
	 * @return
	 */
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> signin(String params)
	{
		Map<String, Object> req = new HashMap<>();

		try
		{
			Member member = new Member();
			JSONObject obj = JSONObject.parseObject(params);
			Member mem = repository.findByMobile(obj.getString("mobile"));
			if (mem == null)
			{

				String code = this.prop.getProperty(obj.getString("mobile"));
				if (Objects.toString(obj.getString("verify_code"), "0").equals(
						code))
				{
					SdkHttpResult result = ApiHttpClient.getToken(
							keyPro.getProperty("appKey"),
							keyPro.getProperty("appSecret"),
							obj.getString("mobile"), "", "", FormatType.json);
					JSONObject objj = (JSONObject) JSONObject.parse(result
							.getResult());
					member.setMobilePhone(obj.getString("mobile"));
					member.setPassword(obj.getString("pwd"));
					member.setImToken(objj.getString("token"));
					member.setIsCompleted(0);
					member.setStatus(MemberStatus.NORMAL);
					member.setMemberType(MemberType.ORDINARY);
					member.setMemberNumber(UUIDUtil.genUUIDString());
					Long id = repository.register(member);

					Account account = new Account();
					account.setAccountBalance(BigDecimal.ZERO);
					account.setAccountNumber(member.getMobilePhone());
					account.setChargingStandard(BigDecimal.ZERO);
					account.setMemberNumber(member.getMemberNumber());
					account.setCreateTime(new Date(System.currentTimeMillis()));
					account.setRechargeTimes(0);
					account.setSurplusCallDuration(0);
					account.setStatus(MemberStatus.NORMAL);
					accountBean.register(account);

					req.put("uid", Objects.toString(id, "0"));
					req.put("uno", member.getMemberNumber());
					req.put("im_token", objj.getString("token"));
					req.put("error_code", "000000");
					req.put("error_msg", "");
				}
				else
				{
					req.put("uid", "");
					req.put("im_token", "");
					req.put("error_code", "3002");
					req.put("error_msg", "验证码错误");

				}

			}
			else
			{
				req.put("uid", "");
				req.put("im_token", "");
				req.put("error_code", "3001");
				req.put("error_msg", "手机号已存在");
			}

		}
		catch (Exception e)
		{
			// Handle generic exceptions
			log.info(e.getMessage());
			req.put("error_code", "3002");
			req.put("error_msg", e.getMessage());
		}

		return req;
	}

	/**
	 * 获取用户信息
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getUserInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getUserInfo(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		//String uid = Objects.toString(obj.get("uid"), "0");
		Member mem = repository.findByMemberNo(Objects.toString(obj.get("uno"), "0"));

		if (mem != null)
		{
			List<MemberPackage> list = memberPackageBean.finByMemberId(mem.getMemberNumber());
			Account acc = accountBean.findByMemberNo(mem.getMemberNumber());
			mem.setPassword("");
			mem.setContacts("");
			mem.setCreatePerson("");
			mem.setCreateTime(null);
			mem.setModifiedPerson("");
			mem.setModifiedTime(null);
			mem.setLastTime(null);
			mem.setLoginTimes(0);
			
			req.put("member", mem);
			req.put("balance", acc.getAccountBalance());
			req.put("pages", list);
			req.put("error_code", "000000");
			req.put("error_msg", "");
		}
		else
		{
			req.put("error_code", "4022");
			req.put("error_msg", "查无用户信息");
		}

		return req;
	}

	/**
	 * 获取用户个人头像
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getUserHead")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getUserHead(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		//String uid = Objects.toString(obj.get("uid"), "0");
		Member mem = repository.findByMemberNo(Objects.toString(obj.get("uno"), "0"));

		if (mem != null)
		{
			
		}
		else
		{
			req.put("error_code", "4022");
			req.put("error_msg", "查无用户信息");
		}

		return req;
	}
	
	
	/**
	 * 获取用户信息
	 * @param params
	 * @return
	 */
	@POST
	@Path("/updateHeadUrl")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> updateHeadUrl(MultipartFormDataInput formDataInput)
	{
		Map<String, Object> req = new HashMap<>();
		
		Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();//提交的form表单
		List<InputPart> inputs = uploadForm.get("uno");
		for (InputPart inputPart : inputs) {
			
		}
        List<InputPart> inputParts = uploadForm.get("file");//从form表单中获取name="file"的元素内容
        Map beans = new HashMap();
        List shops = new ArrayList();
        beans.put("shops",shops);
        try {
            for (InputPart inputPart : inputParts){//遍历name="file"的元素
                //获取服务器上的配置文件                 URL configFileUrl = new URL(request.getRequestURL().toString().split("/rest")[0] + "/excel/config/shop.xml");
                //执行excel导入
//                resultData = handler.read(configFileUrl.openStream(),inputPart.getBody(InputStream.class,null),beans);
            }
        } finally {
        }
		
		//String uid = Objects.toString(obj.get("uid"), "0");
//		Member mem = repository.findByMemberNo(Objects.toString(obj.get("uno"), "0"));
//
//		if (mem != null)
//		{
//			List<MemberPackage> list = memberPackageBean.finByMemberId(mem.getMemberNumber());
//			Account acc = accountBean.findByMemberNo(mem.getMemberNumber());
//			mem.setPassword("");
//			mem.setContacts("");
//			mem.setCreatePerson("");
//			mem.setCreateTime(null);
//			mem.setModifiedPerson("");
//			mem.setModifiedTime(null);
//			mem.setLastTime(null);
//			mem.setLoginTimes(0);
//			
//			req.put("member", mem);
//			req.put("balance", acc.getAccountBalance());
//			req.put("pages", list);
//			req.put("error_code", "000000");
//			req.put("error_msg", "");
//		}
//		else
//		{
//			req.put("error_code", "4022");
//			req.put("error_msg", "查无用户信息");
//		}

		return req;
	}
	
	@POST
	@Path("/completeProfile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> completeProfile(String params)
	{ 
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
//		Member member = repository.findById(Long.parseLong(Objects.toString(
//				obj.getString("uid"), "0")));
		Member member = repository.findByMemberNo(Objects.toString(
				obj.getString("uno"), "0"));
		if (member != null)
		{
			if (obj.getString("mobile").equals(member.getMobilePhone()))
			{
				req.put("result", "fail");
				req.put("error_code", "0X1001");
				req.put("error_msg", "手机号已注册");
				return req;
			}
			if (!"null"
					.equals(Objects.toString(obj.getString("mobile"), "null")))
			{
				String code = this.prop.getProperty(obj.getString("mobile"));
				if ("0".equals(Objects.toString(obj.getString("verify_code"),
						"0")))
				{
					Account acc = accountBean.findByMemberNo(member
							.getMemberNumber());
					acc.setPaymentAassword(obj.getString("trade_pwd"));
					accountBean.updateAccount(acc);
					member.setMemberName(obj.getString("name"));
					member.setMemberGender(Gender.getGender(Integer
							.parseInt(Objects.toString(obj.getString("gender"),
									"0"))));
					member.setIsCompleted(1);
					repository.updateMember(member);
					req.put("result", "success");
					req.put("error_code", "000000");
					req.put("error_msg", "");
					return req;
				}
				if (Objects.toString(obj.getString("verify_code"), "0").equals(
						code))
				{
					Account acc = accountBean.findByMemberNo(member
							.getMemberNumber());
					acc.setPaymentAassword(obj.getString("trade_pwd"));
					accountBean.updateAccount(acc);
				
					if (!"null".equals(Objects.toString(
							obj.getString("login_pwd"), "null")))
						member.setPassword(obj.getString("login_pwd"));
					member.setMemberName(obj.getString("name"));
					member.setMobilePhone(obj.getString("mobile"));
					member.setMemberGender(Gender.getGender(Integer
							.parseInt(Objects.toString(obj.getString("gender"),
									"0"))));
					member.setIsCompleted(1);
					repository.updateMember(member);
					req.put("result", "success");
					req.put("error_code", "000000");
					req.put("error_msg", "");
				}
				else
				{
					req.put("result", "fail");
					req.put("error_code", "0X1000");
					req.put("error_msg", "需要输入短信验证码");
				}

			}

		}
		else
		{
			req.put("result", "fail");
			req.put("error_code", "0X1001");
			req.put("error_msg", "查无此用户");
		}

		return req;
	}

	
	/**
	 * 获取用户简略信息
	 * @param params
	 * @return
	 */
	
	@POST
	@Path("/getUserSimpleInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getUserSimpleInfo(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		//String uid = Objects.toString(obj.get("uid"), "0");
		Member mem = repository.findByMemberNo(Objects.toString(obj.get("uno"), "0"));

		if (mem != null)
		{
			List<MemberPackage> list = memberPackageBean.finByMemberId(mem.getMemberNumber());
			Account acc = accountBean.findByMemberNo(mem.getMemberNumber());
			mem.setPassword("");
			mem.setContacts("");
			mem.setCreatePerson("");
			mem.setCreateTime(null);
			mem.setModifiedPerson("");
			mem.setModifiedTime(null);
			mem.setLastTime(null);
			mem.setLoginTimes(0);
			
			req.put("member", mem);
			req.put("balance", acc.getAccountBalance());
			req.put("pages", list);
			req.put("error_code", "000000");
			req.put("error_msg", "");
		}
		else
		{
			req.put("error_code", "4022");
			req.put("error_msg", "查无用户信息");
		}

		return req;
	}
	
	/**
	 * 编辑个人信息
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/editUserInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> editUserInfo(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String uno = Objects.toString(obj.get("uno"), "null");
		
		Map<String, Object> req = new HashMap<>();
		// 修改用户信息
		if(!"null".equals(uno)){
			String nickname = Objects.toString(obj.get("nickname"), "null");
			String realname = Objects.toString(obj.getString("realname"),"null");
			String gender = Objects.toString(obj.get("gender"), "null");
			String location = Objects.toString(obj.getString("location"),"null");
			String credentialsNumber = Objects.toString(obj.getString("credentialsNumber"),"null");
			String email = Objects.toString(obj.getString("email"),"null");
			
			Member member = repository.findByMemberNo(Objects.toString(
					obj.getString("uno"), "0"));
			try
			{
				if(member!=null){
					if(!"null".equals(nickname))member.setNickname(nickname);
					if(!"null".equals(realname))member.setMemberName(realname);
					if(!"null".equals(gender))member.setMemberGender(Gender.getGender(Integer.parseInt(gender)));
					if(!"null".equals(location))member.setLocation(location);
					if(!"null".equals(credentialsNumber))member.setCredentialsNumber(credentialsNumber);
					if(!"null".equals(email))member.setEmail(email);
					
					repository.updateMember(member);
					
					req.put("result", "success");
					req.put("error_code", "000000");
					req.put("error_msg", "");
				}else{
					req.put("result", "fail");
					req.put("error_code", "1008");
					req.put("error_msg", "用户不存在");
				}
				
			}
			catch (Exception e)
			{
				log.info(e.getMessage());
				req.put("result", "fail");
				req.put("error_code", "7001");
				req.put("error_msg", "系统错误");
			}
		}else{
			req.put("result", "fail");
			req.put("error_code", "1010");
			req.put("error_msg", "用户标识参数为空");
		}
		return req;
	}
	
	/**
	 * 获取用户账户信息
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getUserAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getUserAccount(String params)
	{
		JSONObject obj = JSONObject.parseObject(params);
		String uno = Objects.toString(obj.get("uno"), "null");
		
		Map<String, Object> req = new HashMap<>();
		// 修改用户信息
		if(!"null".equals(uno)){
			Account account = accountBean.findByMemberNo(uno);
			try
			{
					req.put("balance", account.getAccountBalance());
					req.put("result", "success");
					req.put("error_code", "000000");
					req.put("error_msg", "");
				
			}
			catch (Exception e)
			{
				log.info(e.getMessage());
				req.put("result", "fail");
				req.put("error_code", "7001");
				req.put("error_msg", "系统错误");
			}
		}else{
			req.put("result", "fail");
			req.put("error_code", "1010");
			req.put("error_msg", "用户标识参数为空");
		}
		return req;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		Map<String, String> param = new HashMap<>();

		param.put("account", "novobabel");
		param.put("password", "novobabel1");
		param.put("mobile", "15901321233");

		try
		{
			param.put("content",
					URLEncoder.encode("您的验证码是：9876。如需帮助请联系客服。", "UTF-8"));
			HttpRequester h = new HttpRequester();
			HttpRespons hr = h.sendPost(
					"http://sms.106jiekou.com/utf8/sms.aspx?", param);
			System.out.println(hr.getContent());
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
		}
		// HttpRespons hr = httpRequester.sendPost(
		// "http://www.106jiekou.com/webservice/sms.asmx/Submit",
		// param);

	}

}
