package com.yinghua.translation.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
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
import com.yinghua.translation.Constant;
import com.yinghua.translation.model.Account;
import com.yinghua.translation.model.CallRecord;
import com.yinghua.translation.model.LanuageTariff;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.TimedTask;
import com.yinghua.translation.model.enumeration.MemberType;
import com.yinghua.translation.service.AccountBean;
import com.yinghua.translation.service.CallHistoryBean;
import com.yinghua.translation.service.LanuageTariffBean;
import com.yinghua.translation.service.MemberBean;
import com.yinghua.translation.service.MemberPackageBean;
import com.yinghua.translation.service.TimedTaskBean;
import com.yinghua.translation.util.ClassLoaderUtil;
import com.yinghua.translation.util.HttpRequester;
import com.yinghua.translation.util.HttpRespons;
import com.yinghua.translation.util.PropertiesUtil;
import com.yinghua.translation.util.RandomNum;

@Path("/interface")
@RequestScoped
public class TranslateResourceRESTService
{
	@Inject
	private Logger log;
	@Inject
	private HttpRequester httpRequester;

	@EJB
	private AccountBean accountBean;
	@EJB
	private LanuageTariffBean lanuageTariffBean;

	@EJB
	private TimedTaskBean timedTaskBean;
	@EJB
	private CallHistoryBean callHistoryBean;
	@EJB
	private MemberPackageBean memberPackageBean;

	@EJB
	private MemberBean memberBean;
	private Properties keyPro = ClassLoaderUtil.getProperties("key.properties");

	
	
//	/**
//	 * 查询服务记录
//	 *
//	 * @param params
//	 * @return
//	 */
//	@POST
//	@Path("/getCallHistory")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Map<String, Object> getCallHistory(String params)
//	{
//		JSONObject obj = JSONObject.parseObject(params);
//		Map<String, Object> req = new HashMap<>();
//		// String uid = Objects.toString(obj.get("uid"), "0");
//		String uno = Objects.toString(obj.get("userId"), "0");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date start = null;
//		Date end = null;
//		try
//		{
//			start = sdf.parse(Objects.toString(obj.get("start"), "0"));
//			end = sdf.parse(Objects.toString(obj.get("end"), "0"));
//		}
//		catch (ParseException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// 此处更新用户信息
//		List<CallRecord> list = callHistoryBean.findByUid(uno);
//
//		// Integer total = callHistoryBean.findByUid(uno).size();
//		// req.put("count", Objects.toString(list.size(), "0"));
//		// req.put("total", Objects.toString(total, "0"));
//		req.put("callList", list);
//		req.put("result", "success");
//		req.put("error_code", "000000");
//		req.put("error_msg", "");
//		return req;
//	}

	/**
	 * UUCall第三方通话建立结果通知
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/notifyTranslate3CallEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> notifyTranslate3CallEvent(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
//		String call_id = Objects.toString(obj.get("call_id"), "");
		String caller = Objects.toString(obj.get("caller"), "");
		String call3_result = Objects.toString(obj.get("call3_result"), "");
		//APP调用，返回第三方通话状态
		Constant.calleeMap.put(caller, call3_result);
		//清除第三方通话号码记录
		Constant.call3Map.remove(caller);
		System.out.println("caller:"+caller+"|result:"+call3_result);
		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");

		return req;
	}
	
	
	/**
	 * 翻译触发通知接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/notifyTranslateStartEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> notifyTranslateStartEvent(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String call_id = Objects.toString(obj.get("call_id"), "");
		String user_id = Objects.toString(obj.get("user_id"), "null");
		String time = Objects.toString(obj.get("time"), "");
		String caller = Objects.toString(obj.get("caller"), "");
		String callee = Objects.toString(obj.get("callee"), "");
		String work_id = Objects.toString(obj.get("work_id"), "");
		String language = Objects.toString(obj.get("language"), "");
        Member member = memberBean.findMember(user_id,"");
		Account account = accountBean.findByMemberNo(member.getMemberNumber());
		LanuageTariff lan = lanuageTariffBean.findByLanguage(language);
		//App调用，返回通话状态
		Constant.callerMap.put(Objects.toString(obj.get("caller")), call_id);
		
		req.put("balance", Math.round(account.getSurplusCallDuration()/60.0));
		req.put("balance_time",Math.round(account.getSurplusCallDuration()/(lan.getRate()*60)));
		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");
		
		System.out.println("翻译触发通知接口调用成功");
		
		return req;
	}

	/**
	 * 翻译结束通知接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/notifyTranslateEndEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> notifyTranslateEndEvent(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String call_id = Objects.toString(obj.get("call_id"), "");
		String user_id = Objects.toString(obj.get("user_id"), "null");
		String caller = Objects.toString(obj.get("caller"), "");
		String callee = Objects.toString(obj.get("callee"), "");
		String language = Objects.toString(obj.get("language"), "null");
		String workId = Objects.toString(obj.get("workId"), "");

        Member member = memberBean.findMember(user_id,"");

        System.out.println("lan:"+language);
        MemberPackage mp = memberPackageBean.findMemberPackage(member.getMemberNumber(),
				language);
		Account account = accountBean.findByMemberNo(member.getMemberNumber());
		LanuageTariff lan = lanuageTariffBean.findByLanguage(language);
		Date translate_start = new Date(obj.getLongValue("translate_start"));
		Date translate_end = new Date(obj.getLongValue("translate_end"));;

		Long callDuration = (translate_end.getTime()
				- translate_start.getTime()) / (1000);
		//此处填写逻辑，如果用户存在套餐，就扣套餐，不存在就扣标准费用

		if(account!=null){
        	account.setSurplusCallDuration(account.getSurplusCallDuration()-(int)(callDuration*lan.getRate()));
            accountBean.updateAccount(account);
		}


		// 记录翻译记录
		CallRecord callRecord = new CallRecord();
		callRecord.setCallee(callee);
		callRecord.setWorkId(workId);
		callRecord.setUserNumber(member.getMemberNumber());
		callRecord.setCaller(caller);
		callRecord.setCallId(call_id);
		callRecord.setType("1");
		callRecord.setEndTime(translate_start);
		callRecord.setStartTime(translate_end);
		callRecord.setLanguages(lan.getLanguages());
		callRecord.setCallDuration(callDuration.intValue());// 翻译时长
		callRecord.setCreateTime(new Date(System.currentTimeMillis()));
		// 保存翻译记录
		callHistoryBean.create(callRecord);

//		req.put("balance", account.getAccountBalance().toString());
//		req.put("balance_time",
//				account.getAccountBalance().divide(lan.getTariff()).toString());
		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");

		System.out.println("翻译结束通知接口调用成功");
		
		return req;
	}

	/**
	 * VOIP电话线路状态通知接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/notifyVOIPCallEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> notifyVOIPCallEvent(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String call_id = Objects.toString(obj.get("call_id"), "");
		// String user_id = Objects.toString(obj.get("user_id"), "null");
		String caller = Objects.toString(obj.get("caller"), "");
		String callee = Objects.toString(obj.get("callee"), "");
		Member mem = memberBean.findMember("", caller);
		
		if(mem == null)
		{
			req.put("result", "faile");
			req.put("error_code", "0x00060");
			req.put("error_msg", "查无VOIP用户");
			return req;
		}
		Date translate_start = new Date(obj.getLongValue("start_time"));
		Date translate_end = new Date(obj.getLongValue("end_time"));;
		
		Long callDuration = (translate_end.getTime()
				- translate_start.getTime()) / (60 * 1000);
		Account account = accountBean.findByMemberNo(mem.getMemberNumber());
		if (callee.length() <= 32)
		{
			account.setAccountBalance(account.getAccountBalance()
					.subtract(new BigDecimal(keyPro.getProperty("voip"))
							.multiply(BigDecimal.valueOf(callDuration))));
			accountBean.updateAccount(account);
		}

		// 记录翻译记录
		CallRecord callRecord = new CallRecord();
		callRecord.setCallee(callee);
		callRecord.setType("2");
		// callRecord.setWorkId(workId);
		callRecord.setUserNumber(mem.getMemberNumber());
		callRecord.setCaller(caller);
		callRecord.setCallId(call_id);
		callRecord.setEndTime(translate_start);
		callRecord.setStartTime(translate_end);
		// callRecord.setLanguages(lan.getLanguages());
		callRecord.setCallDuration(callDuration.intValue());// 翻译时长
		callRecord.setCreateTime(new Date(System.currentTimeMillis()));
		// 保存翻译记录
		callHistoryBean.create(callRecord);

		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");

		return req;
	}
	
	/**
	 * VOIP3方电话线路状态通知接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/notifyVOIP3CallEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> notifyVOIP3CallEvent(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String caller = Objects.toString(obj.get("caller"), "");
		String call_id1 = Objects.toString(obj.get("call_id1"), "");
		String callee1 = Objects.toString(obj.get("callee1"), "");
		Long call1_start = obj.getLong("call1_start");
		Long call1_end = obj.getLong("call1_end");
		
		String call_id2 = Objects.toString(obj.get("call_id2"), "");
		String callee2 = Objects.toString(obj.get("callee2"), "");
		Long call2_start = obj.getLong("call2_start");
		Long call2_end = obj.getLong("call2_end");
		Member mem = memberBean.findMember("", caller);
		// 记录翻译记录
		CallRecord callRecord = new CallRecord();
		callRecord.setCallee(callee1);
		callRecord.setType("2");
		// callRecord.setWorkId(workId);
		callRecord.setUserNumber(mem.getMemberNumber());
		callRecord.setCaller(caller);
		callRecord.setCallId(call_id1);
		callRecord.setEndTime(new Date(call1_end));
		callRecord.setStartTime(new Date(call1_start));
		// callRecord.setLanguages(lan.getLanguages());
		//callRecord.setCallDuration(callDuration.intValue());// 翻译时长
		callRecord.setCreateTime(new Date(System.currentTimeMillis()));
		// 保存翻译记录
		callHistoryBean.create(callRecord);
		
		// 记录翻译记录
		CallRecord callRecord1 = new CallRecord();
		callRecord1.setCallee(callee2);
		callRecord1.setType("2");
		// callRecord.setWorkId(workId);
		callRecord1.setUserNumber(mem.getMemberNumber());
		callRecord1.setCaller(caller);
		callRecord1.setCallId(call_id2);
		callRecord1.setEndTime(new Date(call2_end));
		callRecord1.setStartTime(new Date(call2_start));
		// callRecord.setLanguages(lan.getLanguages());
		//callRecord.setCallDuration(callDuration.intValue());// 翻译时长
		callRecord1.setCreateTime(new Date(System.currentTimeMillis()));
		callHistoryBean.create(callRecord1);

		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");

		return req;
	}


	/**
	 * IM消息翻译计费接口
	 * 
	 * @param params
	 * @return
	 */
	@POST
	@Path("/notifyIMTranslateEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> notifyIMTranslateEvent(String params)
	{
		Map<String, Object> req = new HashMap<>();
		JSONObject obj = JSONObject.parseObject(params);
		String user_id = Objects.toString(obj.get("user_id"), "");
		String msg_id = Objects.toString(obj.get("msg_id"), "");
		String msg_type = Objects.toString(obj.get("msg_type"), "");
		String msg_content = Objects.toString(obj.get("msg_content"), "");
		String msg_lang = Objects.toString(obj.get("msg_lang"), "");
		String translate_lang = Objects.toString(obj.get("translate_lang"), "");
		String translate_msg_type = Objects
				.toString(obj.get("translate_msg_type"), "");
		String translate_msg_content = Objects
				.toString(obj.get("translate_msg_content"), "");
		String translate_time = Objects.toString(obj.get("translate_time"),
				"0");
		// String translate_start
		// =Objects.toString(obj.get("translate_start"),"");
		// String translate_end =Objects.toString(obj.get("translate_end"),"");
		// String call_start =Objects.toString(obj.get("call_start"),"");
		// String call_end =Objects.toString(obj.get("call_end"),"");
        Member member = memberBean.findMember(user_id,"");

        MemberPackage pack = memberPackageBean.findMemberPackage(member.getMemberNumber(),
				translate_lang);
		pack.setImGiftCount(
				pack.getImGiftCount() - Integer.parseInt(translate_time));
		memberPackageBean.updateMemberPackage(pack);

		req.put("result", "success");
		req.put("error_code", "000000");
		req.put("error_msg", "");

		return req;
	}

	/**
	 * 获取用户信息接口
	 * 
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
		String phone = Objects.toString(obj.get("phone"), "");
		String voip = Objects.toString(obj.get("voip"), "");
		String trade_code = Objects.toString(obj.get("trade_code"), "null");
		String language=Objects.toString(obj.get("language"),"");

		Member mem = memberBean.findMember(phone, voip);
		if (mem != null)
		{
			Account account = accountBean.findByMemberNo(mem.getMemberNumber());
			if (!"null".equals(trade_code))
			{
				if (!trade_code.equals(account.getPaymentPassword()))
				{
					req.put("error_code", "2");
					req.put("error_msg", "交易密码不正确");
					return req;
				}
				else
				{
					req.put("phone", mem.getMobilePhone());
                    req.put("uno", mem.getMobilePhone());
                    req.put("voip", mem.getVoip());
					req.put("name", mem.getMemberName());
					req.put("gender", mem.getMemberGender());
					req.put("poi", mem.getLocation());
					req.put("balance", account.getAccountBalance());
					req.put("user_type", Integer.toString(MemberType
							.getMemberType(mem.getMemberType().toString())));
					req.put("skill_group_id", "");
					req.put("na_language", "EN");
					if(Constant.call3Map.get(mem.getMobilePhone())!=null)
						req.put("callee", Constant.call3Map.get(mem.getMobilePhone()));
					req.put("error_code", "000000");
					req.put("error_msg", "成功");

                    LanuageTariff lan = lanuageTariffBean.findByLanguage(language);

                    req.put("balance", account.getAccountBalance().toString());

                    MemberPackage mp = memberPackageBean.findMemberPackage(mem.getMemberNumber(),language);

                    if(mp!=null)
                    {
                        Date date= mp.getTelPreferEnd();
                        if(date.getTime()>System.currentTimeMillis())
                        {
                            req.put("balance_time",String.valueOf((date.getTime()-System.currentTimeMillis())/60000));
                        }
                        else
                            req.put("balance_time", account.getAccountBalance().divide(lan.getTariff()).toString());
                    }
                    else
                        req.put("balance_time", account.getAccountBalance().divide(lan.getTariff()).toString());


				}
			}
			else
			{
				
				req.put("phone", mem.getMobilePhone());
				req.put("uno", mem.getMobilePhone());
				req.put("voip", mem.getVoip());
				req.put("name", mem.getMemberName());
				req.put("gender", mem.getMemberGender());
				req.put("poi", mem.getLocation());
//				req.put("balance", account.getAccountBalance());
				req.put("user_type", mem.getMemberType());
				req.put("skill_group_id", "");
//				req.put("online_count", "");
				String key = "";
				if(phone.length()>0){
					key = phone;
				}else if(voip.length()>0){
					key = voip;
				}
//				if(Constant.call3Map.get(key)!=null)
				req.put("callee", Constant.call3Map.get(key));
				req.put("na_language", "EN");
				req.put("error_code", "000000");
				req.put("error_msg", "成功");
                LanuageTariff lan = lanuageTariffBean.findByLanguage(language);
                
                req.put("balance", Math.round(account.getSurplusCallDuration()/60.0));
        		req.put("balance_time",Math.round(account.getSurplusCallDuration()/(lan.getRate()*60)));
//                req.put("balance", account.getAccountBalance().toString());
//                req.put("balance_time", account.getAccountBalance().divide(lan.getTariff()).toString());
			}

		}
		else
		{
			req.put("error_code", "1");
			req.put("error_msg", "手机号或voip分机号不存在");
		}
		
		System.out.println("呼叫中心获取用户信息");
		
		return req;
	}

}
