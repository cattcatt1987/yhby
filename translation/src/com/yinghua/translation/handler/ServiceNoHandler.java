package com.yinghua.translation.handler;

import com.yinghua.translation.service.BaseProductBean;
import com.yinghua.translation.service.MemberOrderBean;
import com.yinghua.translation.service.MemberOrderUseBean;

public abstract class ServiceNoHandler {
	
	protected ServiceNoHandler successor;

	public void setSuccessor(ServiceNoHandler successor) {
		this.successor = successor;
	}
	
	public abstract String processServiceNo(String uno,String serviceNo,BaseProductBean baseProductBean,MemberOrderBean memberOrderBean,MemberOrderUseBean memberOrderUseBean);

	public abstract void processFee(String memberNumber, String callee,
			BaseProductBean baseProductBean, MemberOrderBean memberOrderBean,
			MemberOrderUseBean memberOrderUseBean) ;
	
}
