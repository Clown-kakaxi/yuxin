package com.yuchengtech.emp.biappframe.label.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjInfo;
/**
 * <pre>
 * Title:标签对象
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class LabelObjBS extends BaseBS<BioneLabelObjInfo> {
	
	/**
	 * 根据  标签标识 逻辑系统标识 获取标签  Id
	 * @param labelObjNo  标签标识
	 * @param logicSysNo  逻辑系统标识
	 * @return
	 */
	public Object getLabelObjId(String labelObjNo, String logicSysNo) {
		
		String labelObjId = null;
		String jql = "select label from BioneLabelObjInfo label where label.labelObjNo=?0 and label.logicSysNo=?1";
		List<BioneLabelObjInfo> labelObjList = this.baseDAO.findWithIndexParam(jql, labelObjNo, logicSysNo);
		for(BioneLabelObjInfo label : labelObjList){
			labelObjId = label.getLabelObjId();
		}
		return labelObjId;
	}
}
