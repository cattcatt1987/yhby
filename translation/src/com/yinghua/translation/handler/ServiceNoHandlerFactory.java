package com.yinghua.translation.handler;

public class ServiceNoHandlerFactory {
	
	public static ServiceNoHandler createServiceNoHandler(){
		
		ServiceNoHandlerByDay snbd = new ServiceNoHandlerByDay();
		ServiceNoHandlerByTimes snbt = new ServiceNoHandlerByTimes();
		ServiceNoHandlerByBase snbb = new ServiceNoHandlerByBase();
		
		snbd.setSuccessor(snbt);
		snbt.setSuccessor(snbb);
		
		return snbd;
	}
}
