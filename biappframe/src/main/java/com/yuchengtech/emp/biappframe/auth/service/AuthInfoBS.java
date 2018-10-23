package com.yuchengtech.emp.biappframe.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;

/**
 * 
 * 
 * <pre>
 * Title:认证方式操作类
 * Description: 认证方式操作类
 * </pre>
 * 
 * @author yunlei yunlei@yuchengtech.com
 * @version 1.00.00
 * 
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class AuthInfoBS extends BaseBS<BioneAuthInfo> {
	protected static Logger log = LoggerFactory.getLogger(AuthInfoBS.class);
	
	public List<Map<String,String>> findForCombo(){
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		List<BioneAuthInfo> list =  this.getAllEntityList();
		for (BioneAuthInfo bioneAuthInfo : list) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("text", bioneAuthInfo.getAuthTypeName());
			map.put("id", bioneAuthInfo.getAuthTypeNo());
			results.add(map);
		}
		return results;
	}
	
}
