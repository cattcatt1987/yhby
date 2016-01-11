package com.yinghua.translation.handler;

import java.util.List;

import com.yinghua.translation.model.BaseProduct;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.MemberOrderUse;
import com.yinghua.translation.service.BaseProductBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberOrderUseBean;
public class ServiceNoHandlerByDay extends ServiceNoHandler {

//	@EJB
//	private BaseProductBean baseProductBean;
//	
//	@EJB
//	private MemberOrderBean memberOrderBean;
//	
//	@EJB
//	private MemberOrderUseBean memberOrderUseBean;
	
	private final String PACKAGE_BY_DAY = "1";
	
	@Override
	public String processServiceNo(String uno,String serviceNo,BaseProductBean baseProductBean,MemberOrderBean memberOrderBean,MemberOrderUseBean memberOrderUseBean) {
		
		String result = "1011";//服务号不存在
		BaseProduct bp = baseProductBean.findByServiceNo(serviceNo);
		if(bp!=null){
			List<MemberOrder> orders = memberOrderBean.findUsingOrderByUno(uno,PACKAGE_BY_DAY);
			if(orders!=null&&orders.size()>0){
				a:for (MemberOrder memberOrder : orders) {
					List<MemberOrderUse> mou = memberOrderUseBean.findByOrderNo(memberOrder.getOrderNo());
					for (MemberOrderUse memberOrderUse : mou) {
						if(memberOrderUse.getProductNo().equals(bp.getProductNo())){
							result = "000000";
							break a;
						}
					}
				}
			}
			if(!"000000".equals(result)){
				result = successor.processServiceNo(uno, serviceNo, baseProductBean, memberOrderBean, memberOrderUseBean);
			}
		}
		return result;
	}

	@Override
	public void processFee(String uno, String serviceNo,
			BaseProductBean baseProductBean, MemberOrderBean memberOrderBean,
			MemberOrderUseBean memberOrderUseBean) {
		String result = "1011";
		BaseProduct bp = baseProductBean.findByServiceNo(serviceNo);
		if(bp!=null){
			List<MemberOrder> orders = memberOrderBean.findUsingOrderByUno(uno,PACKAGE_BY_DAY);
			if(orders!=null&&orders.size()>0){
				a:for (MemberOrder memberOrder : orders) {
					List<MemberOrderUse> mou = memberOrderUseBean.findByOrderNo(memberOrder.getOrderNo());
					for (MemberOrderUse memberOrderUse : mou) {
						if(memberOrderUse.getProductNo().equals(bp.getProductNo())){
							result = "000000";
							break a;
						}
					}
				}
			}
			if(!"000000".equals(result)){
				successor.processFee(uno, serviceNo, baseProductBean, memberOrderBean, memberOrderUseBean);
			}
		}
	}

}
