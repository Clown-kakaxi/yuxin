package com.yuchengtech.emp.biappframe.authobj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpObjRel;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;

/**
 * <pre>
 * Title:对象组与其他授权对象关系维护
 * Description: 对象组与其他授权对象关系维护BS
 * </pre>
 * 
 * @author fanll fanll@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class AuthObjgrpRelBS extends BaseBS<BioneAuthObjgrpObjRel>{

	/**
	 * 根据授权对象组和逻辑系统查询授权对象关系
	 * @param logicSysNo 逻辑系统标识
	 * @param objgrpId  授权对象组ID
	 * @return
	 */
	public List<BioneAuthObjgrpObjRel> getAuthObjgrpRelByObjgrpId(String logicSysNo,String objgrpId,String objDefNo){
		String jql = "select rel from BioneAuthObjgrpObjRel rel where rel.id.logicSysNo=?0 and rel.id.objgrpId=?1 and rel.id.objDefNo=?2";
		List<BioneAuthObjgrpObjRel> list = this.baseDAO.findWithIndexParam(jql,logicSysNo, objgrpId,objDefNo);
		if(list!=null&&list.size()>0)
			return list;
		else
			return null;
	}
	
	/**
	 * 维护授权对象组关系
	 * @param oldRels 旧关系集合
	 * @param newRels 新关系集合
	 */
	public void updateAuthObjgrpRelBatch(String logicSysNo,String objgrpId,List<BioneAuthObjgrpObjRel> newRels){

		if(newRels==null)
			newRels = new ArrayList<BioneAuthObjgrpObjRel>();
		
		//清空旧关系
		String jql = "delete from BioneAuthObjgrpObjRel rel where rel.id.logicSysNo=?0 and rel.id.objgrpId=?1";
		this.baseDAO.batchExecuteWithIndexParam(jql,logicSysNo, objgrpId);
		
		//添加新关系
		for(int j=0;j<newRels.size();j++){
			this.baseDAO.save(newRels.get(j));
			if(j % 20 == 0){
				this.baseDAO.flush();
			}
		}
		this.baseDAO.flush();
	}
}
