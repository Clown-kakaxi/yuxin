package com.yuchengtech.emp.biappframe.logicsys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAuthResSysRel;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAuthResSysRelPK;

@Service
public class AuthResSysRelBS extends BaseBS<BioneAuthResSysRel> {
	/**
	 * 保存授权资源与逻辑系统关系
	 * @param logicSysNo
	 * @param authResIds
	 */
	@Transactional(readOnly = false)
	public void saveAuthRes(String logicSysNo, String[] authResIds) {

		String jql = "Delete from BioneAuthResSysRel obj where obj.id.logicSysNo =?0";
		this.baseDAO.batchExecuteWithIndexParam(jql, logicSysNo);

		for (String authResId : authResIds) {
			if (!"".equals(authResId)) {
				BioneAuthResSysRel authRes = new BioneAuthResSysRel();
				BioneAuthResSysRelPK authResSysRelPK = new BioneAuthResSysRelPK();
				authResSysRelPK.setLogicSysNo(logicSysNo);
				authResSysRelPK.setResDefNo(authResId);
				authRes.setId(authResSysRelPK);
				this.updateEntity(authRes);
				this.baseDAO.flush();
			}
		}
	}

}
