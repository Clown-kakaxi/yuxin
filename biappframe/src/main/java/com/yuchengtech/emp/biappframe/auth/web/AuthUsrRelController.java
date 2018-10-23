package com.yuchengtech.emp.biappframe.auth.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjUserRel;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjUserRelPK;
import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.auth.service.AuthUsrRelBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.utils.SpringContextHolder;

import static com.yuchengtech.emp.biappframe.base.common.GlobalConstants.*;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/authUsrRel")
public class AuthUsrRelController extends BaseController {

	@Autowired
	private AuthBS authBS;

	@Autowired
	private AuthUsrRelBS relBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/auth/auth-usr-rel-index";
	}

	// 获取用户树
	@RequestMapping("/getUserTree.*")
	@ResponseBody
	public List<CommonTreeNode> getUserTree() {
		List<CommonTreeNode> pageShowTree = new ArrayList<CommonTreeNode>();
		String objDefNo = AUTH_OBJ_DEF_ID_USER;
		if (objDefNo != null && !"".equals(objDefNo)) {
			// 获取实现类
			List<String> beanNames = this.authBS.findAuthObjBeanNameByType(objDefNo);
			if (beanNames != null && beanNames.size() > 0) {
				// 存在至少一个授权对象实现类申明
				String beanName = beanNames.get(0);
				IAuthObject authObj = SpringContextHolder.getBean(beanName);
				if (authObj != null) {
					pageShowTree = authObj.doGetAuthObjectInfo();
					if (pageShowTree != null) {
						for (int i = 0; i < pageShowTree.size(); i++) {
							(pageShowTree.get(i)).setIcon(this.getRequest().getContextPath() + "/"
									+ (pageShowTree.get(i)).getIcon());
						}
					}
				}
			}
		}
		return pageShowTree;
	}

	// 获取指定授权对象树
	@RequestMapping("/getAuthObjTree.*")
	@ResponseBody
	public List<CommonTreeNode> getAuthObjTree(String objDefNo) {
		List<CommonTreeNode> pageShowTree = new ArrayList<CommonTreeNode>();
		if (objDefNo == null || "".equals(objDefNo)) {
			return pageShowTree;
		}
		if (objDefNo != null && !"".equals(objDefNo)) {
			// 获取实现类
			List<String> beanNames = this.authBS.findAuthObjBeanNameByType(objDefNo);
			if (beanNames != null && beanNames.size() > 0) {
				// 存在至少一个授权对象实现类申明
				String beanName = beanNames.get(0);
				IAuthObject authObj = SpringContextHolder.getBean(beanName);
				if (authObj != null) {
					pageShowTree = authObj.doGetAuthObjectInfo();
					if (pageShowTree != null) {
						for (int i = 0; i < pageShowTree.size(); i++) {
							(pageShowTree.get(i)).setIcon(this.getRequest().getContextPath() + "/"
									+ (pageShowTree.get(i)).getIcon());
						}
					}
				}
			}
		}
		return pageShowTree;
	}

	// 获取用户与授权对象关系
	@RequestMapping("/getObjUserRel.*")
	@ResponseBody
	public List<BioneAuthObjUserRel> getObjUserRel(String objId) {
		if (objId != null && !"".equals(objId)) {
			BiOneUser userInfo = BiOneSecurityUtils.getCurrentUserInfo();
			return this.relBS.getObjUserRelByUserId(userInfo.getCurrentLogicSysNo(), objId);
		}
		return null;
	}

	// 保存
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/saveObjUserRel")
	public void saveObjUserRel(String relObjs) {
		if (relObjs != null && !"".equals(relObjs)) {
			// 当前用户信息
			BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
			JSONObject rels = JSONObject.fromObject(relObjs);
			String userId = rels.getString("userId");
			JSONArray objs = rels.getJSONArray("objs");
			// 新关系集合
			List<BioneAuthObjUserRel> newRels = new ArrayList<BioneAuthObjUserRel>();
			// 获取旧关系
			List<BioneAuthObjUserRel> oldRels = this.relBS
					.getObjUserRelByUserId(userObj.getCurrentLogicSysNo(), userId);
			for (Iterator it = objs.iterator(); it.hasNext();) {
				JSONObject oi = (JSONObject) it.next();
				String objDefNo = oi.getString("objDefNo");
				String objIds = oi.getString("objIds");
				if (objIds != "" && !"".equals(objIds)) {
					String[] objIdsArray = objIds.split(",");
					for (int i = 0; i < objIdsArray.length; i++) {
						BioneAuthObjUserRel newRel = new BioneAuthObjUserRel();
						BioneAuthObjUserRelPK pk = new BioneAuthObjUserRelPK();
						pk.setLogicSysNo(userObj.getCurrentLogicSysNo());
						pk.setObjDefNo(objDefNo);
						pk.setUserId(userId);
						pk.setObjId(objIdsArray[i]);
						newRel.setId(pk);

						newRels.add(newRel);
					}
				}
			}
			this.relBS.updateRelBatch(oldRels, newRels);
		}
	}

}
