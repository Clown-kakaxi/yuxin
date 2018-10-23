package com.yuchengtech.emp.biappframe.authres.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.biappframe.authres.service.ResOperBS;
import com.yuchengtech.emp.biappframe.authres.web.vo.BioneResOperInfoVO;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

/**
 * 
 * <pre>
 * Title:CRUD操作演示
 * Description: 对资源操作信息表的CURD
 * </pre>
 * 
 * @author xingyw xingyw@gmail.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/resOper")
public class ResOperController extends BaseController {

	@Autowired
	private ResOperBS resOperBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/resOper/res-oper-index";
	}

	/**
	 * 右侧表单树
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/getResOperInfo.*")
	@ResponseBody
	public Map<String, Object> getResOperInfo(Pager pager, String resDefNo, String resNo)
			throws IllegalAccessException, InvocationTargetException {

		List<BioneResOperInfoVO> resOperVOList = resOperBS
				.findResOperInfoByResNoAndResDefNo(resDefNo, resNo,
						pager.getSearchCondition());
		Map<String, Object> resOperMap = Maps.newHashMap();
		resOperMap.put("Rows", resOperVOList);
		resOperMap.put("Total", resOperVOList.size());
		return resOperMap;
	}

	/**
	 * 填充下拉框
	 */
	@RequestMapping("/getResOperTree")
	@ResponseBody
	public List<CommonTreeNode> getResOperTree(String resDefNo, String resNo, String operId) {
		List<CommonTreeNode> pageShowTree = this.resOperBS.findResOperTree(resDefNo, resNo, operId);
		return pageShowTree;
	}

	/**
	 * 修改时通过upNo得到父节的的operNo
	 */
	@RequestMapping("/getUpOperNo")
	@ResponseBody
	public Map<String, Object> getUpOperNo(String resDefNo, String resNo, String upNo) {
		String upOperNo = this.resOperBS.findUpOperNo(resDefNo, resNo, upNo);
		Map<String, Object> resOperMap = Maps.newHashMap();
		resOperMap.put("upOperNo", upOperNo);
		return resOperMap;
	}

	/**
	 * 验证
	 */
	@RequestMapping("/checkedOperNo")
	@ResponseBody
	public boolean checkedOperNo(String urlType, String operNo, String currentOperNo) {
		boolean flag = this.resOperBS.checkedOperNo(operNo, currentOperNo, urlType);
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加/修改
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneResOperInfo model) {
		if(model.getOperId()==null||model.getOperId().equals("")){
			model.setOperId(RandomUtils.uuid2());
		}
		if ("根目录".equals(model.getUpNo())) {
			model.setUpNo("0");
		}
		resOperBS.updateEntity(model);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") String id) {
		return "/bione/resOper/res-oper-edit";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String upid, String upno) {
		ModelMap mm = new ModelMap();
		mm.put("upid", upid);
		mm.put("upno", upno);
		return new ModelAndView("/bione/resOper/res-oper-edit", mm);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteResOper")
	@ResponseBody
	public Map<String, Object> deleteResOper(String resDefNo, String resNo, String operId) {
		/*String sid[] = operId.split(",");
		String operNoStrB = null;
		String operNoStr = null;
		for (int i = 0; i < sid.length; i++) {
			operNoStr = resOperBS.delResOperByParams(resDefNo, resNo, sid[i]);
			if (operNoStr != null && !"".equals(operNoStr)) {
				if (operNoStrB == null) {
					operNoStrB = operNoStr;
				} else {
					operNoStrB += "," + operNoStr;
				}
			}
		}多行删除*/
		String operNoStrB = null;
		operNoStrB = resOperBS.delResOperByParams(resDefNo, resNo, operId);
		Map<String, Object> resOperMap = Maps.newHashMap();
		resOperMap.put("operNoStrB", operNoStrB);
		return resOperMap;
	}
}
