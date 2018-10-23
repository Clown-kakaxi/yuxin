package com.yuchengtech.emp.ecif.report.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.ecif.report.service.PubBranchInfoBS;


/**
 * 
 * 
 * <pre>
 * Title:公共机构信息Controller端
 * Description: 
 * </pre>
 * 
 * @author pengsenlin pengsl@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/report/branch")
public class PubBranchInfoController extends BaseController {

	@Autowired
	private PubBranchInfoBS pubBranchInfoBS;
	
	protected static Logger log = LoggerFactory.getLogger(PubBranchInfoController.class);
	
	@RequestMapping(value = "/list.*", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> queryPubBranchInfoList() {
		List<Map<String, String>> comboList = this.pubBranchInfoBS.queryPubBranchInfoList();
		return comboList;
	}
}
