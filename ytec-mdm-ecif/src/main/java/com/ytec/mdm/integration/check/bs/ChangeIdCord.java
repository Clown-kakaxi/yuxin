/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����ChangeIdCord.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:41:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.bs;

import java.text.SimpleDateFormat;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ChangeIdCord
 * @�����������֤У��ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:41:19   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:41:19
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ChangeIdCord implements IMsgNodeFilter {
	private static SimpleDateFormat idDate = new SimpleDateFormat("yyyyMMdd");
	private final static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	public String execute(Object expression, String idCord, String... params) {
		if (!idCord.matches("\\d{18}|\\d{17}X|\\d{17}x")){
			return null;
		}
		int s = 0;
		String check = "";
		int retId = 0;
		idCord=idCord.replace("x", "X");
		
    	String birs=idCord.substring(6, 14);
		try{
			idDate.setLenient(false);
			idDate.parse(birs);
		}catch(Exception e){
			return null;
		}
		if(Integer.parseInt(birs)<19000101){
    		return null;
    	}
		for (int i = 0; i <= 16; i++) {
			s = Integer.parseInt(idCord.substring(i, i + 1));
			retId += wi[i] * s;
		}
		
		retId = retId % 11;
		retId = 12 - retId;
		if (retId == 10){
			check = "X";
		}else if (retId == 11) {
			check = "0";
		}else if (retId == 12){
			check = "1";
		}else{
			check = String.valueOf(retId);
		}
		if (idCord.length() == 18) {
			if (!(check.equals(idCord.substring(17,18)))){
				return null;
			}
		}
		return idCord.substring(0, 17) + check;
	}

}
