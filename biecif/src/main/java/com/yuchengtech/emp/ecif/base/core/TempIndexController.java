/**
 * 
 */
package com.yuchengtech.emp.ecif.base.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/customer/tempindex")
public class TempIndexController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(TempIndexController.class);
	
	@Autowired
	private CodeUtil codeUtil;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/customer/temp-index";
	}
	
}
