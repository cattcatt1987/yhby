package com.yinghua.translation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.util.ClassLoaderUtil;

@Stateless
public class PaymentBean extends AbstractFacade<Member>
{
	private static final Logger logger = Logger.getLogger(MemberBean.class
			.getCanonicalName());
	private Properties prop = ClassLoaderUtil.getProperties("key.properties");

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public PaymentBean()
	{
		// TODO Auto-generated constructor stub
		super(Member.class);
	}
	
	/**
	 * 创建 Charge
	 * 
	 * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create(); map
	 * 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
	 * 
	 * @return
	 */
	public Charge charge(Map<String, Object> chargeMap) throws Exception
	{
		Pingpp.apiKey = prop.getProperty("apiKey");
		Charge charge = null;
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", prop.getProperty("appId"));
		chargeMap.put("app", app);
		// 发起交易请求
		charge = Charge.create(chargeMap);
		System.out.println(charge);
		return charge;
	}

	/**
	 * 查询 Charge
	 * 
	 * 该接口根据 charge Id 查询对应的 charge 。
	 * 参考文档：https://pingxx.com/document/api#api-c-inquiry
	 * 
	 * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。 参考文档：
	 * https://pingxx.com/document/api#api-expanding
	 * 
	 * @param id
	 */
	public boolean retrieve(String id) throws Exception
	{
		Pingpp.apiKey = prop.getProperty("apiKey");
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> expande = new ArrayList<String>();
		expande.add("app");
		param.put("expand", expande);
		// Charge charge = Charge.retrieve(id);
		// Expand app
		Charge charge = Charge.retrieve(id, param);
//		if (charge.getApp() instanceof App)
//		{
//			// App app = (App) charge.getApp();
//			// System.out.println("App Object ,appId = " + app.getId());
//		}
//		else
//		{
//			// System.out.println("String ,appId = " + charge.getApp());
//		}

		System.out.println(charge);
		return charge.getPaid();

	}

	/**
	 * 分页查询Charge
	 * 
	 * 该接口为批量查询接口，默认一次查询10条。 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
	 * 
	 * 该接口同样可以使用 expand 参数。
	 * 
	 * @return
	 */
	public ChargeCollection all() throws Exception
	{
		Pingpp.apiKey = prop.getProperty("apiKey");
		ChargeCollection chargeCollection = null;
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("limit", 3);

		// 增加此处设施，刻意获取app expande
		// List<String> expande = new ArrayList<String>();
		// expande.add("app");
		// chargeParams.put("expand", expande);

		chargeCollection = Charge.all(chargeParams);
		System.out.println(chargeCollection);
		return chargeCollection;
	}

}
