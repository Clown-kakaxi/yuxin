package com.yuchengtech.emp.biappframe.message.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAuthRel;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAuthRelPK;
import com.yuchengtech.emp.biappframe.message.service.BioneMsgAuthRelBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title: 消息模块-消息权限控制器
 * Description: 消息模块-消息权限控制器
 * </pre>
 * 
 * @author liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/message/auth")
public class MessageAuthController extends BaseController {

	
	@Autowired
	private BioneMsgAuthRelBS msgAuthRelBS;
	
	
	@Autowired
	private AuthBS authBS;
	
	/** @TODO 设置权限 */
	
	
	
	/**
	 * 查询权限的对照关系
	 * 获取指定授权对象树
	 * 
	 * @param objDefNo
	 * @return
	 */
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
							(pageShowTree.get(i)).setIcon(this.getRequest().getContextPath() + "/" + (pageShowTree.get(i)).getIcon());
						}
					}
				}
			}
		}
		return pageShowTree;
	}
	
	
	/* 获取用户与授权对象关系 */
	@RequestMapping("/getMsgAuthRel.*")
	@ResponseBody
	public List<BioneMsgAuthRel> getMsgAuthRel(String msgId, String msgTypeNo) {
		if (msgId != null && !"".equals(msgId)) {
			BiOneUser userInfo = BiOneSecurityUtils.getCurrentUserInfo();
			return this.msgAuthRelBS.getMsgAuthRelByMsgId(userInfo.getCurrentLogicSysNo(), msgId, msgTypeNo);
		}
		return null;
	}
		
	
	/**
	 * 保存权限的对照关系
	 * 
	 * @param msgAuths
	 */
	@RequestMapping("/saveMsgAuth")
	public void saveObjUserRel(String msgAuths) {
		if (msgAuths != null && !"".equals(msgAuths)) {
			// 当前用户信息
			BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
			JSONObject authObj = JSONObject.fromObject(msgAuths);
			String msgId = authObj.getString("msgId");
			JSONArray objs = authObj.getJSONArray("objs");
			// 新关系集合
			List<BioneMsgAuthRel> newRels = new ArrayList<BioneMsgAuthRel>();
			// 获取旧关系
			String msgTypeNo = "announcement"; // 注意这里的硬编码：应该跟消息类型的定义关联起来！！
			// 
			List<BioneMsgAuthRel> oldRels = this.msgAuthRelBS.getMsgAuthRelByMsgId(userObj.getCurrentLogicSysNo(), msgId, msgTypeNo);
			for (Iterator it = objs.iterator(); it.hasNext();) {
				JSONObject oi = (JSONObject) it.next();
				String objDefNo = oi.getString("objDefNo");
				String objIds = oi.getString("objIds");
				if (objIds != "" && !"".equals(objIds)) {
					String[] objIdsArray = objIds.split(",");
					for (int i = 0; i < objIdsArray.length; i++) {
						BioneMsgAuthRel newRel = new BioneMsgAuthRel();
						BioneMsgAuthRelPK pk = new BioneMsgAuthRelPK();
						pk.setLogicSysNo(userObj.getCurrentLogicSysNo());
						pk.setObjDefNo(objDefNo);
						pk.setMsgId(msgId);
						pk.setMsgTypeNo(msgTypeNo);
						pk.setObjId(objIdsArray[i]);
						newRel.setId(pk);
						
						newRels.add(newRel);
					}
				}
			}
			this.msgAuthRelBS.updateRelBatch(oldRels, newRels);
		}
	}
	
}
