/**
 * 
 */
package com.yuchengtech.emp.biappframe.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjUserRel;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author caiqy  caiqy@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class AuthUsrRelBS extends BaseBS<BioneAuthObjUserRel> {

	/**
	 * 根据用户和逻辑系统，查询有关系的授权对象
	 * 
	 * @param logicSysNo
	 * @param userId
	 * @return
	 */
	public List<BioneAuthObjUserRel> getObjUserRelByUserId(String logicSysNo,String userId){
		String jql = "select rel from BioneAuthObjUserRel rel where rel.id.logicSysNo=?0 and rel.id.userId=?1";
		
		return this.baseDAO.findWithIndexParam(jql, logicSysNo , userId);
	}
	
	/**
	 * 更新用户与授权对象关系
	 * 
	 * @param oldRels
	 * 			  旧关系
	 * @param newRels
	 *           新关系
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateRelBatch(List<BioneAuthObjUserRel> oldRels,List<BioneAuthObjUserRel> newRels){
		if(oldRels == null || newRels == null ){
			return ;
		}
		//先删除旧关系
		for(int i = 0 ; i < oldRels.size() ; i++){
			this.removeEntity(oldRels.get(i));
			if(i % 20 == 0){
				this.baseDAO.flush();
			}
		}
		//维护新关系
		for(int j = 0 ; j < newRels.size() ; j++){
			this.updateEntity(newRels.get(j));
			if(j % 20 == 0){
				this.baseDAO.flush();
			}
		}
	}
}
