package com.yuchengtech.emp.ecif.transaction.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrCt;

@Service
@Transactional(readOnly = false)
public class TxMsgNodeAttrCtBS extends BaseBS<TxMsgNodeAttrCt> {
	@SuppressWarnings("unchecked")
	public List<TxMsgNodeAttrCt> getCtList(Long attrId) {
		
		if(attrId==null){
			return null;
		}
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNodeAttrCt from TxMsgNodeAttrCt TxMsgNodeAttrCt where 1=1");
		if (attrId != null||"".equals(attrId)){
			jql.append(" and " + "TxMsgNodeAttrCt.attrId=" + attrId );
		}

		List<TxMsgNodeAttrCt> TxMsgNodeAttrCt = this.baseDAO.findWithNameParm(
				jql.toString(), null);
		return TxMsgNodeAttrCt;
	}
	
}
