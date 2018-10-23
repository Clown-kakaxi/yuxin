/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.unit
 * @文件名：CheckTxModel.java
 * @版本信息：1.0.0
 * @日期：2014-4-29-下午3:02:25
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.unit;

import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.bs.TxConfigBS;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CheckTxModel
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-29 下午3:02:25   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-29 下午3:02:25
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CheckTxModel {

	private TxConfigBS txConfigBS;
	/**
	 *@构造函数 
	 */
	public CheckTxModel() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		txConfigBS= (TxConfigBS)SpringContextUtils.getBean("txConfigBS");
	}
	
	public boolean validationTxModel(String txCode){
		try{
			txConfigBS.getTxModel(txCode);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @函数名称:main
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@param args
	 * @算法描述:
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContextUtils.setApplicationContext();
		CheckTxModel check=new CheckTxModel();
		check.init();
		String[] tcCodes={"QECIF2_007-copy1"};
		if(args.length>0){
			tcCodes=args;
		}
		if(tcCodes.length>0){
			if(tcCodes!=null&&tcCodes.length>0){
				boolean r;
				for(String arg:tcCodes){
					r=check.validationTxModel(arg);
					if(!r){
						System.out.println("交易["+arg+"]配置错误");
					}else{
						System.out.println("交易["+arg+"]配置 OK");
					}
				}
			}
		}else{
			System.out.println("请输入校验的交易码");
		}
	}

}
