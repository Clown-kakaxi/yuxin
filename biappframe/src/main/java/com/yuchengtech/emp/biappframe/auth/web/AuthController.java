package com.yuchengtech.emp.biappframe.auth.web;

import static com.yuchengtech.emp.biappframe.base.common.GlobalConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjDef;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRel;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRelPK;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthResDef;
import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.auth.service.AuthObjBS;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.biappframe.security.IResObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 授权对象授权操作action
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
@RequestMapping("/bione/admin/auth")
public class AuthController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthBS authBS;

	@Autowired
	private AuthObjBS authObjBS;

	// 资源操作树，操作节点nodeType，仅在本功能供判断是否是操作节点使用
	private String resOperNodeType = "RES_OPER";

	@RequestMapping("/manage")
	public String manage() {
		return "/bione/auth/auth-manage";
	}

	// 获取授权对象-下拉菜单
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getAuthObjCombo.*")
	@ResponseBody
	public List<Map> getAuthObjCombo() {
		List<Map> authObjComboList = new ArrayList<Map>();
		// 获取授权对象
		List<BioneAuthObjDef> objs = this.authBS
				.getEntityList(BioneAuthObjDef.class);
		// 获取授权对象id列表
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		List<String> authStrs = this.authBS.getObjDefNoBySys(userObj
				.getCurrentLogicSysNo());
		if (objs != null) {
			for (BioneAuthObjDef authObj : objs) {
				if (authStrs.contains(authObj.getObjDefNo())) {
					Map<String, String> objMap = new HashMap<String, String>();
					objMap.put("id", authObj.getObjDefNo());
					objMap.put("text", authObj.getObjDefName());
					authObjComboList.add(objMap);
				}
			}
		}
		return authObjComboList;
	}

	// 获取授权对象树(暂包含:用户，角色，机构，授权组等)
	@RequestMapping("/getAuthObjDefTree.*")
	@ResponseBody
	public List<CommonTreeNode> getAuthObjDefTree(String objDefNo) {
		List<CommonTreeNode> pageShowTree = null;
		if (objDefNo != null && !"".equals(objDefNo)) {
			// 获取实现类
			List<String> beanNames = this.authBS
					.findAuthObjBeanNameByType(objDefNo);
			if (beanNames != null && beanNames.size() > 0) {
				// 存在至少一个授权对象实现类申明
				String beanName = beanNames.get(0);
				IAuthObject authObj = SpringContextHolder.getBean(beanName);
				if (authObj != null) {
					pageShowTree = authObj.doGetAuthObjectInfo();
					if (pageShowTree != null) {
						for (int i = 0; i < pageShowTree.size(); i++) {
							(pageShowTree.get(i)).setIcon(this.getRequest()
									.getContextPath()
									+ "/"
									+ (pageShowTree.get(i)).getIcon());
						}
					}
				}
			}
		}
		return pageShowTree;
	}

	// 获取授权资源tab

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAuthResDefTabs.*")
	@ResponseBody
	public Map getAuthResDefTabs() {
		Map ress = new HashMap();
		List<BioneAuthResDef> resDefs = this.authBS
				.getEntityList(BioneAuthResDef.class);
		// 获取该逻辑系统授权资源
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		List<String> authRess = this.authBS.getResDefNoBySys(userObj
				.getCurrentLogicSysNo());
		if (resDefs != null && resDefs.size() > 0) {
			List<Map> subList = new ArrayList<Map>();
			for (BioneAuthResDef obj : resDefs) {
				if (authRess.contains(obj.getResDefNo())) {
					Map<String, String> resMap = new HashMap<String, String>();
					resMap.put("resDefNo", obj.getResDefNo());
					resMap.put("resName", obj.getResName());
					resMap.put("resBean", obj.getBeanName());
					if (AUTH_RES_DEF_ID_MENU.equals(obj.getResDefNo())) {
						// 若是菜单资源，放至第一个
						subList.add(0, resMap);
					} else {
						subList.add(resMap);
					}
				}
			}
			ress.put("Data", subList);
		}
		return ress;
	}

	// 获取资源树(暂包含 菜单 等)
	@RequestMapping("/getAuthResDefTree.*")
	@ResponseBody
	public List<CommonTreeNode> getAuthResDefTree(String resDefNo) {
		List<CommonTreeNode> pageShowTree = null;
		if (resDefNo != null && !"".equals(resDefNo)) {
			// 获取实现类
			List<String> beanNames = this.authBS
					.findResObjBeanNameByType(resDefNo);
			if (beanNames != null && beanNames.size() > 0) {
				// 存在至少一个授权对象实现类申明
				String beanName = beanNames.get(0);
				try {
					IResObject resObj = SpringContextHolder.getBean(beanName);
					if (resObj != null) {
						pageShowTree = resObj.doGetResInfo();
					}
				} catch (org.springframework.beans.factory.NoSuchBeanDefinitionException e) {
					e.printStackTrace();
				}
			}
		}
		return pageShowTree;
	}

	// 获取资源操作树
	@RequestMapping("/getMenuOperTree.*")
	@ResponseBody
	public List<CommonTreeNode> getMenuOperTree(String resIds, String resDefNo,
			String permissionIds) {
		List<CommonTreeNode> pageShowTree = new ArrayList<CommonTreeNode>();
		if (resIds != null && !"".equals(resIds) && resDefNo != null
				&& !"".equals(resDefNo)) {
			String[] resIdArray = resIds.split(",");
			List<String> resIdList = new ArrayList<String>();
			for (int i = 0; i < resIdArray.length; i++) {
				resIdList.add(resIdArray[i]);
			}
			List<String> beanNames = this.authBS
					.findResObjBeanNameByType(resDefNo);

			List<BioneResOperInfo> operInfos = null;
			if (beanNames != null && beanNames.size() > 0) {
				// 存在至少一个授权对象实现类申明
				String beanName = beanNames.get(0);
				IResObject resObj = SpringContextHolder.getBean(beanName);
				if (resObj != null) {
					operInfos = resObj.findResOperList(resDefNo, resIdList);
				}
			}

			if (operInfos != null) {
				List<String> pList = new ArrayList<String>();
				if (permissionIds != null && !"".equals(permissionIds)) {
					String[] pIdArray = permissionIds.split(",");
					for (int m = 0; m < pIdArray.length; m++) {
						pList.add(pIdArray[m]);
					}
				}
				for (int j = 0; j < operInfos.size(); j++) {
					BioneResOperInfo oper = operInfos.get(j);
					CommonTreeNode node = new CommonTreeNode();
					node.setId(resOperNodeType + "_" + oper.getOperId());
					node.setText(oper.getOperName());
					if (CommonTreeNode.ROOT_ID.equals(oper.getUpNo())) {
						node.setUpId(resDefNo + "_" + oper.getResNo());
					} else {
						node.setUpId(resOperNodeType + "_" + oper.getUpNo());
					}
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("realId", oper.getOperId());
					paramMap.put("nodeType", resOperNodeType);
					node.setParams(paramMap);
					// 判断授权操作是否是资源所选中
					if (pList.contains(oper.getOperId())) {
						node.setIschecked(true);
					} else {
						node.setIschecked(false);
					}

					pageShowTree.add(node);
				}
			}
		}

		return pageShowTree;
	}

	// 获取数据规则树(暂没实现)
	public void getDataRuleTree() {

	}

	// 获取具体授权对象的资源权限
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getAuthObjResRel.*")
	@ResponseBody
	public Map getAuthObjResRel(String objDefNo, String objDefId) {
		Map ress = new HashMap();
		if (objDefNo != null && !"".equals(objDefNo) && objDefId != null
				&& !"".equals(objDefId)) {
			BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
			List<BioneAuthObjResRel> relList = this.authBS
					.findAuthResListByType(userObj.getCurrentLogicSysNo(),
							objDefNo, objDefId);
			ress = new HashMap();
			ress.put("Data", relList);
		}
		return ress;
	}

	// 保存方法
	@SuppressWarnings("rawtypes")
	@RequestMapping("/saveAuth")
	public void saveAuth(String nodes) {
		if (nodes != null && !"".equals(nodes)) {
			// 当前用户信息
			BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
			JSONObject groupJson = JSONObject.fromObject(nodes);
			String objDefNo = (String) groupJson.get("objDefNo");
			String objId = (String) groupJson.get("objId");
			// 新许可list
			List<BioneAuthObjResRel> rels = new ArrayList<BioneAuthObjResRel>();
			// 获取该授权对象旧配置
			List<BioneAuthObjResRel> relsOld = this.authObjBS
					.findAuthObjRelByObj(userObj.getCurrentLogicSysNo(),
							objDefNo, objId);
			JSONArray rulesJson = groupJson.getJSONArray("resSelNodes");
			for (Iterator it = rulesJson.iterator(); it.hasNext();) {
				JSONObject rule = (JSONObject) it.next();
				String resDefNo = (String) rule.get("resDefNo");
				String ids = (String) rule.get("ids");
				JSONArray permissionArr = rule.getJSONArray("permissionIds");
				for (Iterator it2 = permissionArr.iterator(); it2.hasNext();) {
					JSONObject pObj = (JSONObject) it2.next();
					String pIds = (String) pObj.get("operIds");

					// 这里的授权许可编号还是形如'01,02,03'的形式
					String[] pIdArray = pIds.split(",");
					for (int i = 0; i < pIdArray.length; i++) {
						BioneAuthObjResRel rel = new BioneAuthObjResRel();
						BioneAuthObjResRelPK relPK = new BioneAuthObjResRelPK();
						relPK.setLogicSysNo(userObj.getCurrentLogicSysNo());
						relPK.setObjDefNo(objDefNo);
						relPK.setObjId(objId);
						if ("".equals(pIdArray[i]) || pIdArray[i] == null) {
							// 若没选择操作，则全部操作授权
							relPK.setPermissionId(PERMISSION_ALL);
						} else {
							relPK.setPermissionId(pIdArray[i]);
						}
						relPK.setPermissionType(RES_PERMISSION_TYPE_OPER);
						relPK.setResDefNo(resDefNo);
						relPK.setResId(ids);
						rel.setId(relPK);

						rels.add(rel);
					}
				}

			}
			List<BioneResOperInfo> hasOperRess = this.authObjBS
					.findHasOperRess(rels);
			if (hasOperRess != null) {
				for (int i = 0; i < rels.size(); i++) {
					BioneAuthObjResRel rel = rels.get(i);
					for (int j = 0; j < hasOperRess.size(); j++) {
						BioneResOperInfo oper = hasOperRess.get(j);
						if (rel.getId().getResDefNo()
								.equals(oper.getResDefNo())
								&& rel.getId().getResId()
										.equals(oper.getResNo())) {
							rels.get(i).getId()
									.setPermissionId(PERMISSION_NONE);
						}
					}
				}
			}
			this.authObjBS.updateRelBatch(relsOld, rels);
		}
	}

}
