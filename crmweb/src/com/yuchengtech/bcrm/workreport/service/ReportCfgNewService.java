package com.yuchengtech.bcrm.workreport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workreport.model.OcrmFSysReport;
import com.yuchengtech.bcrm.workreport.model.OcrmFSysReportCfg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.crm.exception.BizException;

/****
 * 
 * 报表配置New
 * @author ChengMeng
 */

@Service
public class ReportCfgNewService  extends CommonService{
	
	public ReportCfgNewService(){
		   JPABaseDAO<OcrmFSysReport, Long>  baseDAO = new JPABaseDAO<OcrmFSysReport, Long>(OcrmFSysReport.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 更新报表状态
	 * @param id 报表ID
	 * @param reportStatus 报表状态
	 **/
	@SuppressWarnings("unchecked")
	public void updateReportStatus(Long id, Long reportStatus) {
		OcrmFSysReport ofsr = (OcrmFSysReport) this.find(id);
		ofsr.setReportStatus(reportStatus);
		baseDAO.save(ofsr);
	}
	/**
	 * 保存报表配置
	 * @param reportPanel 报表主表
	 * @param reportList 报表子表list
	 **/
	public void saveReport(Map<String, Object> formPanel, List<Map<String, Object>> reportList) {
		OcrmFSysReport ofsr = new OcrmFSysReport();
		//新增修改判断
		if (null != formPanel.get("ID") && !"".equals(formPanel.get("ID"))) {
			ofsr = (OcrmFSysReport) this.find((Long.parseLong((String) formPanel.get("ID"))));
			ofsr.setReportStatus(ofsr.getReportStatus());
		} else {
			ofsr.setReportStatus(new Long(0));
		}
		ofsr.setReportName((String) formPanel.get("REPORT_NAME"));
		ofsr.setReportCode((String) formPanel.get("REPORT_CODE"));
		validatorReport(ofsr);
		ofsr.setReportUrl((String) formPanel.get("REPORT_URL"));
		ofsr.setReportGroup((String) formPanel.get("REPORT_GROUP"));
		ofsr.setAppId(SystemConstance.LOGIC_SYSTEM_APP_ID);
		ofsr.setCreator(getUserSession().getUserId());
		if (null != formPanel.get("REPORT_DESC") && !"".equals(formPanel.get("REPORT_DESC"))) {
			ofsr.setReportDesc((String) formPanel.get("REPORT_DESC"));
		}
		if (null != formPanel.get("REPORT_TYPE") && !"".equals(formPanel.get("REPORT_TYPE"))) {
			ofsr.setReportType((String) formPanel.get("REPORT_TYPE"));
		}
		if (null != formPanel.get("REPORT_SORT") && !"".equals((String)formPanel.get("REPORT_SORT"))) {
			ofsr.setReportSort(Long.parseLong((String) formPanel.get("REPORT_SORT")));
		}		
		if (null != formPanel.get("REPORT_SERVER_TYPE") && !"".equals((String)formPanel.get("REPORT_SERVER_TYPE"))) {
			ofsr.setReportServerType((String) formPanel.get("REPORT_SERVER_TYPE"));
		}		
		baseDAO.save(ofsr);
		JPABaseDAO<OcrmFSysReportCfg, Long>  cfgbaseDAO = new JPABaseDAO<OcrmFSysReportCfg, Long>(OcrmFSysReportCfg.class); 
		cfgbaseDAO.setEntityManager(em);
		//更新子表数据
		if (null != reportList) {
			Map<String, Object> params = new HashMap<String, Object>();
			StringBuilder jql = new StringBuilder("delete from OcrmFSysReportCfg o where o.reportCode='"+ofsr.getReportCode()+"'");
			cfgbaseDAO.batchExecuteWithNameParam(jql.toString(), params);
			for (Map<String, Object> ofsrc : reportList) {
				OcrmFSysReportCfg cfg = new OcrmFSysReportCfg();
				cfg.setAppId(SystemConstance.LOGIC_SYSTEM_APP_ID);
				cfg.setConditionField((String) ofsrc.get("conditionField"));
				cfg.setConditionName((String) ofsrc.get("conditionName"));
				cfg.setConditionType((String) ofsrc.get("conditionType"));
				cfg.setConditionDefault((String) ofsrc.get("conditionDefault"));
				cfg.setIsAllowBlank((String) ofsrc.get("isAllowBlank"));
				cfg.setIsHidden((String) ofsrc.get("isHidden"));
				cfg.setReportCode(ofsr.getReportCode());
				cfgbaseDAO.save(cfg);
			}
		}
	}

	/**
	 * 检验报表信息
	 * @param OcrmFSysReport 报表信息
	 */
	private void validatorReport(OcrmFSysReport ofsr) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> values = new HashMap<String, Object>();
		sb.append("select o from OcrmFSysReport o where 1=1 ");
		List relist = new ArrayList();
		if (null != ofsr.getId()) {
			 sb.append(" and o.id<>"+ofsr.getId());
			 relist = this.baseDAO.findWithNameParm(sb.toString() + " and o.reportCode='"+ ofsr.getReportCode()+"'", values);
			 if (relist.size() > 0) {
				 throw new BizException(1,2,"1002","报表编码重复");
			 }
			 relist = this.baseDAO.findWithNameParm(sb.toString() + " and o.reportName='"+ ofsr.getReportName()+"'", values);
			 if (relist.size() > 0) {
				 throw new BizException(1,2,"1002","报表名称重复");
			 }
		} else {
			 relist = this.baseDAO.findWithNameParm(sb.toString() + " and o.reportCode='"+ ofsr.getReportCode()+"'", values);
			 if (relist.size() > 0) {
				 throw new BizException(1,2,"1002","报表编码重复");
			 }
			 relist = this.baseDAO.findWithNameParm(sb.toString() + " and o.reportName='"+ ofsr.getReportName()+"'", values);
			 if (relist.size() > 0) {
				 throw new BizException(1,2,"1002","报表名称重复");
			 }
		}
		
	}


	 
}
