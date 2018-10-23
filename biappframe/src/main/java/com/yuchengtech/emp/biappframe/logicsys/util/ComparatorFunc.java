package com.yuchengtech.emp.biappframe.logicsys.util;

import java.math.BigDecimal;
import java.util.Comparator;

import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;

/**
 * 
 * <pre>
 * Title: 功能节点排序工具类
 * Description: 
 * </pre>
 * @author yunlei yunlei@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ComparatorFunc implements Comparator<BioneFuncInfo>{

	public int compare(BioneFuncInfo func1, BioneFuncInfo func2) {
		
		if(func1.getOrderNo()==null||"".equals(func1.getOrderNo())){
			func1.setOrderNo(new BigDecimal(0));
		}
		if(func2.getOrderNo()==null||"".equals(func2.getOrderNo())){
			func2.setOrderNo(new BigDecimal(0));
		}
		return  func1.getOrderNo().intValue()-func2.getOrderNo().intValue();
	}

}
