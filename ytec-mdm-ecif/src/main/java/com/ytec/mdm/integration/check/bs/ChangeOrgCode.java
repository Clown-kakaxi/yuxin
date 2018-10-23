/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：ChangeOrgCode.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:42:06
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;

import org.apache.commons.lang.StringUtils;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ChangeOrgCode
 * @类描述：组织机构代码验证
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:42:11   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:42:11
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ChangeOrgCode implements IMsgNodeFilter {
	private final static int[] wi = { 3, 7, 9, 10, 5, 8, 4, 2 };
	public String execute(Object expression, String orgCode, String... params) {
		if (StringUtils.isEmpty(orgCode)) {
			return null;
		}
		orgCode = orgCode.replace("x", "X");
		if (orgCode.length() == 9) {
			orgCode = orgCode.substring(0, 8) + "-" + orgCode.charAt(8);
		}
		if (!orgCode.matches("^[0-9A-Z]{8}-[0-9X]$")) {
			return null;
		}
		String[] all = orgCode.split("-");
		char[] values = all[0].toCharArray();
		int parity = 0;
		for (int i = 0; i < values.length; i++) {
			if(values[i]<='9' && values[i]>='0'){
				parity += wi[i] * (values[i]-'0');
			}else{
				parity += wi[i] * (values[i]-55);
			}
		}
		String cheak = (11 - parity % 11) == 10 ? "X" : Integer
				.toString((11 - parity % 11));
		if (cheak.equals("11")) {
			cheak = "0";
		}
		if (cheak.equals(all[1])) {
			return orgCode;
		} else {
			return null;
		}
	}
}
