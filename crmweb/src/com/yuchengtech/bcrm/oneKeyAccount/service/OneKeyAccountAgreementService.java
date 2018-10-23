package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.oneKeyAccount.utils.ConvertWordToHtmlUtil;
import com.yuchengtech.bob.upload.FileTypeConstance;

/**
 * 获取到一键开户协议并转换成html
 * @author wx
 *
 */
@Service
public class OneKeyAccountAgreementService {
	//private ConvertWordToHtmlUtil wThUtil = new ConvertWordToHtmlUtil();

	public Map<String, Object> getAgreementWordHtml(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String htmlContent = "";
		try {
			String accountFielPath = FileTypeConstance.getBipProperty("crm.accountFielPath");
			if(StringUtils.isEmpty(accountFielPath)){
				retMap.put("error", "获取开户手册路径失败");
				retMap.put("msg", "获取开户手册路径失败");
				return retMap;
			}
			htmlContent = ConvertWordToHtmlUtil.getWordContentHtml(accountFielPath);
		} catch (TransformerException e) {
			retMap.put("error", "文件转换失败，请联系管理员");
			retMap.put("msg", e.getStackTrace());
			return retMap;
		} catch (IOException e) {
			retMap.put("error", "文件读取失败，请联系管理员");
			retMap.put("msg", e.getStackTrace());
			return retMap;
		} catch (ParserConfigurationException e) {
			retMap.put("error", "文件解析失败，请联系管理员");
			retMap.put("msg", e.getStackTrace());
			return retMap;
		}//
		if(htmlContent != null && !htmlContent.equals("")){
			retMap.put("success", "success");
			retMap.put("msg", htmlContent);
		}
		return retMap;
	}
}
