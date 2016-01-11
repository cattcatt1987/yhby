package com.yinghua.translation.handler;

import java.util.List;

import com.yinghua.translation.model.BaseProduct;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.MemberOrderUse;
import com.yinghua.translation.service.BaseProductBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberOrderUseBean;
public class ServiceNoHandlerByTimes extends ServiceNoHandler {

//	@EJB
//	private BaseProductBean baseProductBean;
//	
//	@EJB
//	private MemberOrderBean memberOrderBean;
//	
//	@EJB
//	private MemberOrderUseBean memberOrderUseBean;
	
	private final String PACKAGE_BY_TIMES = "2";
	
	@Override
	public String processServiceNo(String uno,String serviceNo,BaseProductBean baseProductBean,MemberOrderBean memberOrderBean,MemberOrderUseBean memberOrderUseBean) {
		String result = "1012";//未订购套餐
		BaseProduct bp = baseProductBean.findByServiceNo(serviceNo);
		if(bp!=null){
			List<MemberOrder> orders = memberOrderBean.findUsingOrderByUno(uno,PACKAGE_BY_TIMES);
			if(orders!=null&&orders.size()>0){
				a:for (MemberOrder memberOrder : orders) {
					List<MemberOrderUse> mou = memberOrderUseBean.findByOrderNo(memberOrder.getOrderNo());
					for (MemberOrderUse memberOrderUse : mou) {
						if(memberOrderUse.getProductNo().equals(bp.getProductNo())&&memberOrderUse.getTimes()>0){
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
		String result = "1012";//未订购套餐
		BaseProduct bp = baseProductBean.findByServiceNo(serviceNo);
		if(bp!=null){
			List<MemberOrder> orders = memberOrderBean.findUsingOrderByUno(uno,PACKAGE_BY_TIMES);
			if(orders!=null&&orders.size()>0){
				a:for (MemberOrder memberOrder : orders) {
					List<MemberOrderUse> mou = memberOrderUseBean.findByOrderNo(memberOrder.getOrderNo());
					for (MemberOrderUse memberOrderUse : mou) {
						if(memberOrderUse.getProductNo().equals(bp.getProductNo())&&memberOrderUse.getTimes()>0){
							memberOrderUse.setTimes(memberOrderUse.getTimes()-1);
							memberOrderUseBean.updateMemberOrderUse(memberOrderUse);
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
