package com.yuchengtech.emp.ecif.report.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customermanage.Pubbranchinfo;

@Service
@Transactional(readOnly = true)
public class PubBranchInfoBS extends BaseBS<Pubbranchinfo> {
	
	protected static Logger log = LoggerFactory.getLogger(PubBranchInfoBS.class);
	

	public List<Map<String, String>> queryPubBranchInfoList() {
		List<Pubbranchinfo> list = this.baseDAO.getAll(Pubbranchinfo.class);

		List<Map<String, String>> comboList = Lists.newArrayList();
		Map<String, String> map;
		for(Pubbranchinfo pubbranchinfo: list) {
			map = Maps.newHashMap();
			map.put("id", pubbranchinfo.getBrccode());
			map.put("text", pubbranchinfo.getBrccode()+"--"+pubbranchinfo.getBrcname());
			comboList.add(map);
		}
		return comboList;
	}
}