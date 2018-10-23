package com.yuchengtech.emp.biappframe.auth.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.auth.service.AuthInfoBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;

/**
 * <pre>
 * Title:逻辑系统认证方式管理
 * Description: 逻辑系统认证方式管理
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

@Controller
@RequestMapping("/bione/admin/authInfo")
public class AuthInfoController extends BaseController {
	@Autowired
	private AuthInfoBS authInfoBS;

	// GET /logicSys
//	public HttpHeaders index() {
//		return new DefaultHttpHeaders("index").disableCaching();
//	}

	//跳转logic-manage.jsp
	public String editNew() {
		return "editNew";
	}

	// 保存新
	public void create() {
	}

	public String edit() {
		return "edit";
	}

	/**
	 * 显示列表
	 */
	public void list() {

	}

	/**
	 * 显示列表
	 */
	@RequestMapping("/findForCombo.*")
	@ResponseBody
	public List<Map<String,String>> findForCombo() {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			list = authInfoBS.findForCombo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
