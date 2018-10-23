package com.yuchengtech.emp.biappframe.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.base.common.DeptInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.OrgInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.ParamInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.ParamTypeInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.SysVarInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.TriggerInfoHolder;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;

/**
 * <pre>
 * Title:公共Action类
 * Description: 实现通用的ajax数据请求功能，如参数转中文、机构号转中文、生成参数下拉框等
 * 
 * </pre>
 * 
 * @author songxf 
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/common")
public class CommonController {

	/**
	 * 通过系统参数表,生成下拉框数据
	 * 
	 * @param paramTypeNo 参数类型标识
	 * @return 
	 */
	@RequestMapping("/getComboxData.*")
	@ResponseBody
	public List<Map<String, String>> getComboxData(String paramTypeNo) {
		Map<String, String> paramInfoMap = ParamInfoHolder.getParamInfo(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo(), paramTypeNo);
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		if (!MapUtils.isEmpty(paramInfoMap)) {
			for (Iterator<String> iter = paramInfoMap.keySet().iterator(); iter.hasNext();) {
				Map<String, String> comboxMap = new HashMap<String, String>();
				String paramValue = (String) iter.next();
				String paramName = (String) paramInfoMap.get(paramValue);
				comboxMap.put("text", paramName);
				comboxMap.put("id", paramValue);
				results.add(comboxMap);
			}
		}
		return results;
	}

	/**
	 * 通过系统参数表,将参数代码转换为参数名称
	 * 
	 * @param paramTypeNo 参数类型标识
	 * @return 
	 */
	@RequestMapping("/getParamName")
	@ResponseBody
	public String getParamName(String paramTypeNo, String paramValue) {
		String result = ParamInfoHolder.getParamName(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(),
				paramTypeNo, paramValue);

		if (result == null) {
			return paramValue;
		} else {
			return result;
		}
	}

	/**
	 * 通过系统参数表,将机构参数代码转换为机构名称
	 */
	@RequestMapping(value = "/getOrgName", method = RequestMethod.POST)
	@ResponseBody
	public String getOrgName(String paramValue) {
		String result = OrgInfoHolder.getOrgName(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(),
				paramValue);
		if (result == null) {
			return paramValue;
		} else {
			return result;
		}
	}

	/**
	 * 通过系统参数表,将部门参数代码转换为部门名称
	 */
	@RequestMapping("/getDeptName")
	@ResponseBody
	public String getDeptName(String paramValue) {
		String result = DeptInfoHolder.getDeptName(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(),
				paramValue);
		if (result == null) {
			return paramValue;
		} else {
			return result;
		}
	}

	/**
	 * 通过系统变量表, 将系统变量标识转换为变量值
	 */
	@RequestMapping("/getSysVarValue")
	@ResponseBody
	public String getSysVarValue(String paramValue) {
		String result = SysVarInfoHolder.getSysVarValue(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(),
				paramValue);
		if (result == null) {
			return paramValue;
		} else {
			return result;
		}
	}

	/**
	 * 通过触发器表，将触发器id转换为触发器名称
	 */
	@RequestMapping("/getTriggerName")
	@ResponseBody
	public String getTriggerName(String paramValue) {
		String result = TriggerInfoHolder.getTriggerName(paramValue);
		if (result == null) {
			return paramValue;
		} else {
			return result;
		}
	}

	/**
	 * 通过系统参数类型表，将参数类型标识转化为类型名称
	 */
	@RequestMapping("/getParamTypeName")
	@ResponseBody
	public String getParamTypeName(String paramValue) {
		String result = ParamTypeInfoHolder.getParamTypeName(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo(), paramValue);
		if (result == null) {
			return paramValue;
		} else {
			return result;
		}
	}

}
