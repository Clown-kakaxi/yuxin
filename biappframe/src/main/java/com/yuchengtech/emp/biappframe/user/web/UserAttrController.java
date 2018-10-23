package com.yuchengtech.emp.biappframe.user.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttr;
import com.yuchengtech.emp.biappframe.user.service.UserAttrBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title:用户属性定制Controller
 * Description: 用户属性定制Controller
 * </pre>
 * 
 * @author kanglg kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/userattr")
public class UserAttrController extends BaseController {
	@Autowired
	private UserAttrBS userAttrBS;

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean destroy(@PathVariable("id") String id) {
		StringBuffer errEntity = new StringBuffer();
		if (id.endsWith(",")) {
			id = id.substring(0, id.length() - 1);
		}
		String[] idArray = id.split(",");
		for (String attrid : idArray) {
			BioneUserAttr entity = userAttrBS.getEntityById(attrid);
			if (entity != null) {
				if (GlobalConstants.COMMON_STATUS_INVALID.equals(entity
						.getIsExt())) {
					if (errEntity.length() > 0) {
						errEntity.append(",");
					}
					errEntity.append(entity.getFieldName());
					return false;
				}
			}
		}

		this.userAttrBS.removeEntityBatch(id);
		return true;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/user/user-attr-index";
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager rf) {
		SearchResult<BioneUserAttr> searchResult = this.userAttrBS
				.getUserAttrInfoList(rf.getPageFirstIndex(), rf.getPagesize(),
						rf.getSortname(), rf.getSortorder(),
						rf.getSearchCondition());
		Map<String, Object> userAttrMap = Maps.newHashMap();
		userAttrMap.put("Rows", searchResult.getResult());
		userAttrMap.put("Total", searchResult.getTotalCount());
		return userAttrMap;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/user/user-attr-editNew";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/user/user-attr-edit", "id", id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneUserAttr userAttr) {
		if (userAttr.getAttrId() == null || "".equals(userAttr.getAttrId())) {
			//若是新增
			userAttr.setIsAllowUpdate(GlobalConstants.COMMON_STATUS_VALID);
			userAttr.setIsExt(GlobalConstants.COMMON_STATUS_VALID);
			userAttr.setAttrId(RandomUtils.uuid2());
		}
		this.userAttrBS.updateEntity(userAttr);
	}

	@RequestMapping("/info.*")
	@ResponseBody
	public BioneUserAttr info(String id) {
		BioneUserAttr userAttr = userAttrBS.getEntityById(id);
		return userAttr;
	}

}
