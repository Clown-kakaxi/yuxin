package com.ecc.echain.wf;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
/***
 * 反洗钱风险等级调整处理流程
 * @author hujun
 * 2014-07-04
 */
public class AntMoneyRiskLevel extends EChainCallbackCommon{
	
	//通过处理
	public void endY(EVO vo){		
		//保存修改内容
	}
	//拒绝处理
	public void endN(EVO vo){		
		//不做任何操作
		
	}
	



}
