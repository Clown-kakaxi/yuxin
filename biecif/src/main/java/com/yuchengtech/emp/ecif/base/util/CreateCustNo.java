package com.yuchengtech.emp.ecif.base.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author guanyb guanyb@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CreateCustNo extends BaseBS<Object> {
	
	private static Logger logger = LoggerFactory.getLogger(CreateCustNo.class);

	public static int CUST_TP_PER = 1;// 客户类型对私
	public static int CUST_TP_ORG = 2;// 客户类型对公
	public static int CUST_TP_PRT = 3;// 客户类型同业

	public String getEcifCustNo(int custType) throws NumberFormatException, Exception {
		if (custType != CUST_TP_ORG && custType != CUST_TP_PER
				&& custType != CUST_TP_PRT) {
			logger.info("客户类型不支持");
			return "";
		}
		String sCustNo = null;

		String sql = "select SEQ_CUST_NO.nextval from sysibm.sysdummy1";
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		if (list != null && list.size() > 0) {
			sCustNo = custType + list.get(0).toString();
			// char[] numCh = sCustNo.toCharArray();// sCustNo = "1234",
			// 则numCh[0] = '1', numCh[numCh.length -1 ] = '4';
			// 从后面第一位开始， 到前两位，之间进行处理加到sum中 (示例中不带标志位，本方法sCustNo中带)
			System.out.println(sCustNo);
			if (sCustNo.length() != 9) {

			}
			int[] iNumArr = new int[sCustNo.length()];
			for (int idx = 0; idx < sCustNo.length(); idx++) {
				iNumArr[idx] = Integer.parseInt(""
						+ sCustNo.substring(idx, idx + 1));
			}
			int sum = 0;
			for (int idx = iNumArr.length - 1; idx > 1; idx -= 2) {
				sum += iNumArr[idx] * 2 / 10 + iNumArr[idx] * 2 % 10
						+ Integer.parseInt("" + iNumArr[idx - 1]);
			}
			// int check = iNumArr[iNumArr.length - 2] * 10 - sum;
			int check = (10 - sum % 10) % 10;
			sCustNo = sCustNo.substring(0, sCustNo.length()) + check;
			// System.err.println(sCustNo);
			return sCustNo;
		} else {
			logger.info("找不到可用的客户号，数据库序列返回值错误");
			return "";
		}
	}

}
