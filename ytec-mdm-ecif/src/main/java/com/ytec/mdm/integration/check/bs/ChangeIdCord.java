/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：ChangeIdCord.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:41:12
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;

import java.text.SimpleDateFormat;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ChangeIdCord
 * @类描述：身份证校验转换
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:41:19   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:41:19
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
