package com.yuchengtech.emp.biappframe.user.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.biappframe.base.common.CommonFormField;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.common.PasswdInfoHolder;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.passwd.IPwdSavaHisStrategy;
import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdSecurityInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttr;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrVal;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrValPK;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserAttrValBS;
import com.yuchengtech.emp.biappframe.user.service.UserBS;
import com.yuchengtech.emp.biappframe.user.web.vo.BioneUserAttrGrpVO;
import com.yuchengtech.emp.biappframe.user.web.vo.BioneUserAttrValVO;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成用户信息表的CRUD操作
 * </pre>
 * 
 * @author xuguangyuan xuguangyuansh@gmail.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：许广源		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/user")
public class UserController extends BaseController {
	@Autowired
	private UserBS userBS;
	@Autowired
	private UserAttrValBS userAttrValBS;
	
	@Autowired
	private IPwdSavaHisStrategy pwdSaveHis ;
	
	public void setPwdSaveHis(IPwdSavaHisStrategy saveHis) {
		this.pwdSaveHis = saveHis;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/user/user-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<BioneUserInfo> searchResult = userBS.getUserList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		userMap.put("Rows", searchResult.getResult());
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public boolean updatePwd(String userId, String userPwd_1, String userPwd_2) {
		if (userId.equals(BiOneSecurityUtils.getCurrentUserId())
				|| BiOneSecurityUtils.getCurrentUserInfo().isSuperUser()) {
			BioneUserInfo userInfo = this.userBS.getEntityById(userId);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			// 加密后的密码
			String pwdCrypt = BiOneSecurityUtils.getHashedPasswordBase64(userPwd_1);
			/* @Revision 20130415171800-liuch 将密码同时备份到历史表 */
			BionePwdSecurityInfo pwdSecCacheInfo = (BionePwdSecurityInfo)EhcacheUtils.get(PasswdInfoHolder.PWD_SECURITY_CACHE_NAME, PasswdInfoHolder.PWD_SECURITY_KEY);
			boolean needSave = true;
			if(pwdSecCacheInfo!=null && pwdSecCacheInfo.getIsSavePwdHis().equals(GlobalConstants.COMMON_NO)) {
				needSave = false;
			}
			if(needSave) {
				/* @Revision 20130530152500-liuch 把这里改造成接口的方式，子工程中可以进行自有扩展
				 * 注意这里的配置是写在[biappframe/applicationContext-base.xml]中的  
				 * pwdSaveHis的实现类以具体的配置文件为准！！*/
				String result = this.pwdSaveHis.saveHis(userId, pwdCrypt);
				if (!result.equals(IPwdSavaHisStrategy.STATUS_NORMAL)) {
					return false;
				}
				/* @Revision 20130530152500-liuch END */
			}
			/* @Revision 20130415171800-liuch END */
			userInfo.setUserPwd(BiOneSecurityUtils
					.getHashedPasswordBase64(userPwd_1));
			userInfo.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
			userInfo.setLastPwdUpdateTime(now);
			userInfo.setLastUpdateTime(now);
			userBS.updateEntity(userInfo);
			return true;
		} else {
			return false;

		}
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneUserInfo show(@PathVariable("id") String id) {
		return this.userBS.getEntityById(id);
	}

	// public void getUserEditEvents() {
	// this.responseWrite(this.userBS.getUserEditEvents());
	// }

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/user/user-edit", "id", id);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/user/user-edit";
	}

	/*
	 * 修改密码
	 * @Revision 2013-6-5
	 * liucheng2@yuchengtech.com
	 * 这个功能去掉了。
	 * 改成密码重置功能。@see resetPwd
	 */
	/*@RequestMapping(value = "/updatePwd")
	public ModelAndView updatePwd(String id) {
		return new ModelAndView("/bione/user/user-pwd", "id", id);
	}*/

	
	/**
	 * 重置密码
	 */
	@RequestMapping(value = "/{id}/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPwd(@PathVariable("id") String id) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (!BiOneSecurityUtils.getCurrentUserInfo().isSuperUser()) {
			retMap.put("msg", "非超级管理员不能使用重置密码功能");
			return retMap;
		}
		try {
			BioneUserInfo user = this.userBS.getEntityById(id);
			user.setUserPwd(BiOneSecurityUtils.getHashedPasswordBase64(GlobalConstants.DEFAULT_PASSWD));
			this.userBS.updateEntity(user);
			retMap.put("msg", "S");
		} catch (Exception ex) {
			retMap.put("msg", "F");
			ex.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 用户自行修改密码
	 */
	@RequestMapping("/updateCurPwd")
	public ModelAndView updateCurPwd() {
		String id = BiOneSecurityUtils.getCurrentUserId();
		return new ModelAndView("/bione/user/user-curpwd", "id", id);
	}

	/**
	 * 验证原始密码是否有效
	 */
	@RequestMapping("/userPwdValid")
	@ResponseBody
	public boolean userPwdValid(String userPwd_old) {
		BioneUserInfo model = this.userBS.validUserPwd(
				BiOneSecurityUtils.getCurrentUserId(),
				BiOneSecurityUtils.getHashedPasswordBase64(userPwd_old));
		if (model != null && !StringUtils.isEmpty(model.getUserId())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证新密码是否与历史密码重复有效
	 */
	@RequestMapping("/userPwdHisValid")
	@ResponseBody
	public boolean userPwdHisValid(String userPwd_1) {
		// 加密后的新密码
		String pwdCrypt = BiOneSecurityUtils.getHashedPasswordBase64(userPwd_1);
		return this.pwdSaveHis.isPwdValid(BiOneSecurityUtils.getCurrentUserId(), pwdCrypt);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.userBS.removeEntityByProperty("userId", id);
		} else {
			this.userBS.removeEntityById(id);
		}
		return "true";

	}

	/**
	 * 表单验证中的后台验证，验证用户标识是否已存在
	 */
	@RequestMapping("/userNoValid")
	@ResponseBody
	public boolean userNoValid(String userNo) {
		BioneUserInfo model = userBS.findUniqueEntityByProperty("userNo",
				userNo);
		if (model != null)
			return false;
		else
			return true;
	}

	/**
	 * 获取机构下拉框数据
	 */
	@RequestMapping("/getOrgComboxData.*")
	@ResponseBody
	public List<Map<String, String>> getOrgComboxData(String orgNo) {
		return this.userBS.getOrgComboxData(orgNo);
	}

	/**
	 * 获取部门下拉框数据
	 */
	@RequestMapping("/getDeptComboxData.*")
	@ResponseBody
	public List<Map<String, String>> getDeptComboxData(String orgNo,
			String deptNo) {
		return this.userBS.getDeptComboxData(orgNo, deptNo);
	}

	/**
	 * 获取头像列表
	 */
	@RequestMapping("/buildHeadIconList")
	public ModelAndView buildHeadIconList() {
		String iconsHTML = this.buildIconSelectHTML("usericons");
		return new ModelAndView("/bione/user/user-icons", "iconsHTML",
				iconsHTML);
	}

	/**
	 * 修改头像
	 */
	@RequestMapping("/updateHeadIcon")
	public void updateHeadIcon(String userIcon) {
		BioneUserInfo user = this.userBS.getEntityById(BiOneSecurityUtils
				.getCurrentUserId());
		user.setUserIcon(userIcon);
		this.userBS.updateEntity(user);
	}

	/**
	 * 保存动态用户属性方法
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(String submitObj) {
		if (submitObj != null && !"".equals(submitObj)) {
			String uId = this.getRequest().getParameter("userId");
			// 解析JSON
			JSONObject sObj = JSONObject.fromObject(submitObj);
			JSONArray extArray = sObj.getJSONArray("extArray");
			JSONObject unextObj = sObj.getJSONObject("unextObj");
			String userIdTmp = "";
			if (unextObj != null) {
				// 新增用户
				BioneUserInfo userTmp = (BioneUserInfo) JSONObject.toBean(
						unextObj, BioneUserInfo.class);
				if (uId != null && !"".equals(uId)) {
					// 若是修改操作
					BioneUserInfo userInfo = this.userBS.getEntityById(
							BioneUserInfo.class, uId);
					userTmp.setUserNo(userInfo.getUserNo());
					userTmp.setUserIcon(userInfo.getUserIcon());
					userTmp.setUserPwd(userInfo.getUserPwd());
					userTmp.setLastPwdUpdateTime(userInfo
							.getLastPwdUpdateTime());
				} else {
					userTmp.setUserIcon("images/classics/usericons/userhead.png");
					String tmpPwd = userTmp.getUserPwd();
					if (tmpPwd == null || tmpPwd.length() <= 0) {
						tmpPwd = GlobalConstants.DEFAULT_PASSWD;
					}
					userTmp.setUserPwd(BiOneSecurityUtils.getHashedPasswordBase64(tmpPwd));
					userTmp.setLastPwdUpdateTime(new Timestamp(new Date().getTime()));
				}
				userTmp.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
				userTmp.setLastUpdateTime(new Timestamp(new Date().getTime()));
				userIdTmp = userBS.saveUserInfo(userTmp);
			}
			if (extArray != null && userIdTmp != null) {
				// 保存用户属性扩展 , 先清空扩展属性
				this.userBS.deleteUserAttrsByUId(userIdTmp);
				for (@SuppressWarnings("unchecked")
				Iterator<JSONObject> it = extArray.iterator(); it.hasNext();) {
					JSONObject objTmp = it.next();
					String attrId = (String) objTmp.get("attrId");
					String attrValue = (String) objTmp.get("attrValue");
					BioneUserAttrVal valTmp = new BioneUserAttrVal();
					BioneUserAttrValPK valPK = new BioneUserAttrValPK();
					valPK.setAttrId(attrId);
					valPK.setUserId(userIdTmp);
					valTmp.setId(valPK);
					valTmp.setAttrValue(attrValue);
					this.userAttrValBS.saveOrUpdateEntity(valTmp);
				}
			}
		}
	}

	/**
	 * 修改动态用户时的属性初始化方法
	 */
	@RequestMapping("/setWhenUpdate.*")
	@ResponseBody
	public Map<String, Object> setWhenUpdate(String userId) {
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		String userIdTmp = userId;
		if (userIdTmp != null && !"".equals(userIdTmp)) {
			// 获取用户基本信息
			BioneUserInfo userInfo = this.userBS.getEntityById(
					BioneUserInfo.class, userIdTmp);
			if (userInfo != null) {
				fieldsMap.put("userInfo", userInfo);
				// 获取扩展属性
				List<BioneUserAttrValVO> vals = this.userBS
						.getAttrValsByUser(userIdTmp);
				if (vals != null) {
					fieldsMap.put("attrVals", vals);
				}
				// 获取机构中文名
				if (userInfo.getOrgNo() != null
						&& !"".equals(userInfo.getOrgNo())) {
					BioneOrgInfo org = this.userBS.getEntityByProperty(
							BioneOrgInfo.class, "orgNo", userInfo.getOrgNo());
					if (org != null) {
						fieldsMap.put("orgName", org.getOrgName());
					}
				}
				// 获取部门中文名
				if (userInfo.getDeptNo() != null
						&& !"".equals(userInfo.getDeptNo())) {
					BioneDeptInfo dept = this.userBS
							.getEntityByProperty(BioneDeptInfo.class, "deptNo",
									userInfo.getDeptNo());
					if (dept != null) {
						fieldsMap.put("deptName", dept.getDeptName());
					}
				}
			}
		}
		return fieldsMap;
	}

	/**
	 * 动态获取用户属性表单
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/generateUserForm.*")
	@ResponseBody
	public Map<String, Object> generateUserForm(String userId) {
		String defaultGroupicon = "images/classics/icons/communication.gif";
		String userIdTmp = userId;
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		List<CommonFormField> fields = new ArrayList<CommonFormField>();
		// 扩展属性list
		List<BioneUserAttr> extFields = new ArrayList<BioneUserAttr>();
		// 非扩展属性list
		List<BioneUserAttr> unextFields = new ArrayList<BioneUserAttr>();
		// 获取所有有效属性
		List<BioneUserAttrGrpVO> grpList = this.userBS.getAttrsAndGrps();
		if (grpList != null) {
			for (int i = 0; i < grpList.size(); i++) {
				BioneUserAttrGrpVO grpTmp = grpList.get(i);
				List<BioneUserAttr> attrsTmp = grpTmp.getAttrs();
				if (attrsTmp == null || attrsTmp.size() <= 0) {
					continue;
				}
				// 构建表单
				// 记录非隐藏组件数量
				int visibleCount = 0;
				for (int j = 0; j < attrsTmp.size(); j++) {
					BioneUserAttr aTmp = attrsTmp.get(j);
					// 将属性放入map
					if (GlobalConstants.COMMON_STATUS_INVALID.equals(aTmp
							.getIsExt())) {
						// 如是非扩展属性
						if (!unextFields.contains(aTmp.getFieldName())) {
							unextFields.add(aTmp);
						}
					} else {
						// 扩展属性
						if (!extFields.contains(aTmp.getFieldName())) {
							extFields.add(aTmp);
						}
					}
					CommonFormField fTmp = new CommonFormField();
					if (visibleCount == 0
							&& !GlobalConstants.BIONE_ATTR_ELEMENT_TYPE_HIDDEN
									.equals(aTmp.getElementType())) {
						// 若是一个分组的第一个非隐藏组件，加上分组相关属性
						fTmp.setGroup(grpTmp.getGrpName());
						String iconTmp = "";
						if (grpTmp.getGrpIcon() != null
								&& !"".equals(grpTmp.getGrpIcon())) {
							iconTmp = getContextPath() + "/"
									+ grpTmp.getGrpIcon();
						} else {
							iconTmp = getContextPath() + "/" + defaultGroupicon;
						}
						fTmp.setGroupicon(iconTmp);
					}
					// 隐藏域、下拉框和多行文本输入域得特殊处理
					if (GlobalConstants.BIONE_ATTR_ELEMENT_TYPE_SELECT
							.equals(aTmp.getElementType())) {
						visibleCount++;
						// 若是下拉框
						fTmp.setDisplay(aTmp.getLabelName());
						fTmp.setName(aTmp.getFieldName() + "_select");
						fTmp.setNewline(GlobalConstants.COMMON_STATUS_INVALID
								.equals(aTmp.getIsNewline()) ? false : true);
						fTmp.setType("select");
						// 构造下拉框options
						Map optionsTmp = new HashMap();
						optionsTmp.put("valueFieldID", aTmp.getFieldName());
						String combData = aTmp.getCombDs();
						JSONArray arrayTmp = null;
						if (combData != null) {
							try {
								arrayTmp = JSONArray.fromObject(combData);
							} catch (Exception e) {
								logger.warn("【用户管理】动态表单中，存在不合法的下拉框内容定义");
							}
							optionsTmp.put("data", arrayTmp == null ? ""
									: arrayTmp);
							if (aTmp.getInitValue() != ""
									&& !"".equals(aTmp.getInitValue())) {
								if (userIdTmp != null && !"".equals(userIdTmp)) {
									// 若是修改操作,查看该控件是否有设置值，若有，则不初始化initValue属性
									if (GlobalConstants.COMMON_STATUS_INVALID
											.equals(aTmp.getIsExt())) {
										String fieldValue = this.userBS
												.getFieldValueByUserId(
														userIdTmp,
														aTmp.getFieldName());
										if (fieldValue == null
												|| "".equals(fieldValue)) {
											optionsTmp.put("initValue",
													aTmp.getInitValue());
										}
									} else {
										// 是扩展属性
										BioneUserAttrVal valTmp = this.userBS
												.getValByUserAndAttr(userIdTmp,
														aTmp.getAttrId());
										if (valTmp == null
												|| valTmp.getAttrValue() == null
												|| "".equals(valTmp
														.getAttrValue())) {
											optionsTmp.put("initValue",
													aTmp.getInitValue());
										}
									}
								} else {
									// 新增操作，直接初始化
									optionsTmp.put("initValue",
											aTmp.getInitValue());
								}
							}
						}

						fTmp.setOptions(optionsTmp);
					} else if (GlobalConstants.BIONE_ATTR_ELEMENT_TYPE_HIDDEN
							.equals(aTmp.getElementType())) {
						// 若是隐藏域
						fTmp.setType("hidden");
						fTmp.setName(aTmp.getFieldName());
					} else if (GlobalConstants.BIONE_ATTR_ELEMENT_TYPE_TEXTAREA
							.equals(aTmp.getElementType())) {
						visibleCount++;
						// 若是多行文本输入框
						fTmp.setDisplay(aTmp.getLabelName());
						fTmp.setName(aTmp.getFieldName());
						fTmp.setNewline(GlobalConstants.COMMON_STATUS_INVALID
								.equals(aTmp.getIsNewline()) ? false : true);
						fTmp.setType("textarea");
						if (aTmp.getElementWidth() != null
								&& aTmp.getElementWidth() != BigDecimal.ZERO) {
							fTmp.setWidth(String.valueOf(aTmp.getElementWidth()));
						}
						Map attrMap = new HashMap();
						attrMap.put("style", "resize: none;");
						fTmp.setAttr(attrMap);
					} else if (GlobalConstants.BIONE_ATTR_ELEMENT_TYPE_DATE
							.equals(aTmp.getElementType())) {
						visibleCount++;
						// 若是日期输入框
						fTmp.setDisplay(aTmp.getLabelName());
						fTmp.setName(aTmp.getFieldName());
						fTmp.setNewline(GlobalConstants.COMMON_STATUS_INVALID
								.equals(aTmp.getIsNewline()) ? false : true);
						fTmp.setType("date");
					} else if (GlobalConstants.BIONE_ATTR_ELEMENT_TYPE_PASSWORD
							.equals(aTmp.getElementType())) {
						visibleCount++;
						// 若是密码输入框
						fTmp.setDisplay(aTmp.getLabelName());
						fTmp.setName(aTmp.getFieldName());
						fTmp.setNewline(GlobalConstants.COMMON_STATUS_INVALID
								.equals(aTmp.getIsNewline()) ? false : true);
						fTmp.setType("password");
					} else {
						visibleCount++;
						// 其他情况，default为文本域
						fTmp.setDisplay(aTmp.getLabelName());
						fTmp.setName(aTmp.getFieldName());
						fTmp.setNewline(GlobalConstants.COMMON_STATUS_INVALID
								.equals(aTmp.getIsNewline()) ? false : true);
						fTmp.setType("text");
					}
					// 统一处理校验相关属性
					Map validateMap = new HashMap();
					if (aTmp.getIsAllowNull() != null) {
						if (GlobalConstants.COMMON_STATUS_INVALID.equals(aTmp
								.getIsAllowNull())) {
							validateMap.put("required", true);
						}
					}
					if (aTmp.getFieldLength() != null
							&& !"".equals(aTmp.getFieldLength())
							&& !"0".equals(aTmp.getFieldLength())) {
						validateMap.put("maxlength", aTmp.getFieldLength());
					}
					fTmp.setValidate(validateMap);

					fields.add(fTmp);
				}
			}
		}
		fieldsMap.put("fields", fields);
		fieldsMap.put("extFields", extFields);
		fieldsMap.put("unextFields", unextFields);
		return fieldsMap;
	}
}
