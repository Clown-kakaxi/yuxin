package com.yuchengtech.bcrm.common.action;

import com.yuchengtech.bcrm.common.service.EchainCommonService;
import com.yuchengtech.bob.common.CommonService;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=======>");
		CommonService commonService=new CommonService();
		commonService.initWorkflowByWfidAndInstanceid("95", "���̽�ɻ��°��ݷø���", null, "QSJVISIT_12345");
		
	}

}
