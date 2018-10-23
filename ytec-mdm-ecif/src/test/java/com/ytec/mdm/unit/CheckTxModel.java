/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.unit
 * @�ļ�����CheckTxModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-29-����3:02:25
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.unit;

import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.bs.TxConfigBS;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CheckTxModel
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-29 ����3:02:25   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-29 ����3:02:25
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CheckTxModel {

	private TxConfigBS txConfigBS;
	/**
	 *@���캯�� 
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
	 * @��������:main
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@param args
	 * @�㷨����:
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
						System.out.println("����["+arg+"]���ô���");
					}else{
						System.out.println("����["+arg+"]���� OK");
					}
				}
			}
		}else{
			System.out.println("������У��Ľ�����");
		}
	}

}
