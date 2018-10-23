/**
 * 
 */
package com.yuchengtech.emp.biappframe.label.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuchengtech.emp.biappframe.base.web.BaseController;

/**
 * <pre>
 * Title:标签测试
 * Description: 标签测试 
 * </pre>
 * @author caiqy  caiqy@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/bione/label/test")
public class LabelTestController extends BaseController{
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/label/label-test";
	}
	
}
