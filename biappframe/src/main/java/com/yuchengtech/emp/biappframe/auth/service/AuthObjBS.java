/**
 * 
 */
package com.yuchengtech.emp.biappframe.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRel;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;

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
@Service
@Transactional(readOnly = true)
public class AuthObjBS extends BaseBS<BioneAuthObjResRel> {

	/**
	 * 查询某个授权对象所有授权资源许可关系
	 * 
	 * @param logicSysNo
	 * @param objDefNo
	 *            授权对象标识
	 * @param objId
	 *            授权对象Id
	 * @return
	 */
	public List<BioneAuthObjResRel> findAuthObjRelByObj(String logicSysNo,
			String objDefNo, String objId) {
		String jql = "select rel from BioneAuthObjResRel rel where rel.id.logicSysNo=?0 and rel.id.objDefNo.id.objDefNo=?1 and rel.id.objId=?2";

		return this.baseDAO
				.findWithIndexParam(jql, logicSysNo, objDefNo, objId);
	}

	/**
	 * 获取传入集合中已配置了菜单权限
	 * 
	 * @param rels
	 * @return list
	 */
	public List<BioneResOperInfo> findHasOperRess(List<BioneAuthObjResRel> rels) {
		if (rels != null) {
			List<String> defNos = new ArrayList<String>();
			List<String> nos = new ArrayList<String>();
			for (int i = 0; i < rels.size(); i++) {
				BioneAuthObjResRel rel = rels.get(i);
				if (RES_PERMISSION_TYPE_OPER.equals(rel.getId()
						.getPermissionType())
						&& PERMISSION_ALL.equals(rel.getId().getPermissionId())) {
					// 若是操作许可类型,且从页面获取的授权许可为空
					if (!defNos.contains(rel.getId().getResDefNo())) {
						defNos.add(rel.getId().getResDefNo());
					}
					if (!nos.contains(rel.getId().getResId())) {
						nos.add(rel.getId().getResId());
					}
				}
			}
			if (defNos.size() == 0 || nos.size() == 0) {
				return null;
			}
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			paramMap.put("defNo", defNos);
			paramMap.put("no", nos);
			String jql = "select rel from BioneResOperInfo rel where rel.resDefNo in :defNo and rel.resNo in :no";

			return this.baseDAO.findWithNameParm(jql, paramMap);
		}
		return null;
	}

	/**
	 * 更新授权对象与资源关系
	 * 
	 * @param oldRels
	 *            旧关系
	 * @param newRels
	 *            新关系
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateRelBatch(List<BioneAuthObjResRel> oldRels,
			List<BioneAuthObjResRel> newRels) {
		if (oldRels == null || newRels == null) {
			return;
		}
		// 先删除旧关系
		for (int i = 0; i < oldRels.size(); i++) {
			this.removeEntity(oldRels.get(i));
			if (i % 20 == 0) {
				this.baseDAO.flush();
			}
		}
		// 维护新关系
		for (int j = 0; j < newRels.size(); j++) {
			this.updateEntity(newRels.get(j));
			if (j % 20 == 0) {
				this.baseDAO.flush();
			}
		}
	}

}
