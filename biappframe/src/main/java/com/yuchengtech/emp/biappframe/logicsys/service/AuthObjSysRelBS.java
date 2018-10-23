package com.yuchengtech.emp.biappframe.logicsys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAuthObjSysRel;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAuthObjSysRelPK;

/**
 * <pre>
 * Title:授权对象与逻辑系统关系维护
 * Description: 
 * </pre>
 * 
 * @author songxf songxf@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class AuthObjSysRelBS extends BaseBS<BioneAuthObjSysRel>{
	/**
	 * 保存授权资源
	 * @param logicSysNo
	 * @param authObjIds
	 */
	@Transactional(readOnly = false)
	public void saveAuthObj(String logicSysNo, String[] authObjIds) {

		String jql = "Delete from BioneAuthObjSysRel obj where obj.id.logicSysNo =?0";
		this.baseDAO.batchExecuteWithIndexParam(jql, logicSysNo);

		for (String authObjId : authObjIds) {
			if (!"".equals(authObjId)) {
				BioneAuthObjSysRel authObj = new BioneAuthObjSysRel();
				BioneAuthObjSysRelPK authObjSysRelPK = new BioneAuthObjSysRelPK();
				authObjSysRelPK.setLogicSysNo(logicSysNo);
				authObjSysRelPK.setObjDefNo(authObjId);
				authObj.setId(authObjSysRelPK);
				this.saveOrUpdateEntity(authObj);
			}
		}
	}
}
