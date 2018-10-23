/**
 * 
 */
package com.yuchengtech.emp.biappframe.mainpage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.mainpage.entity.BioneMpDesignDetail;
import com.yuchengtech.emp.biappframe.mainpage.entity.BioneMpDesignInfo;
import com.yuchengtech.emp.biappframe.mainpage.entity.BioneMpLayoutInfo;
import com.yuchengtech.emp.biappframe.mainpage.entity.BioneMpModuleInfo;
import com.yuchengtech.emp.biappframe.mainpage.web.vo.MpDetailInfoVO;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title:首页相关BS
 * Description: 首页相关BS
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
public class MainpageBS extends BaseBS<Object> {

	/**
	 * 获取模块信息
	 * 
	 * @param logicSysNo
	 *            逻辑系统标识
	 * @return 模块信息集合
	 */
	public List<BioneMpModuleInfo> getModules(String logicSysNo) {
		List<BioneMpModuleInfo> modules = new ArrayList<BioneMpModuleInfo>();
		if (!StringUtils.isEmpty(logicSysNo)) {
			String jql = "select module from BioneMpModuleInfo module where module.logicSysNo = ?0";
			modules = this.baseDAO.findWithIndexParam(jql, logicSysNo);
		}
		return modules;
	}

	/**
	 * 获取布局信息
	 * 
	 * @param logicSysNo
	 *            逻辑系统标识
	 * @return 布局信息集合
	 */
	public List<BioneMpLayoutInfo> getLayouts(String logicSysNo) {
		List<BioneMpLayoutInfo> layouts = new ArrayList<BioneMpLayoutInfo>();
		if (!StringUtils.isEmpty(logicSysNo)) {
			String jql = "select layout from BioneMpLayoutInfo layout where layout.logicSysNo = ?0";
			layouts = this.baseDAO.findWithIndexParam(jql, logicSysNo);
		}
		return layouts;
	}

	/**
	 * 保存布局信息
	 * 
	 * @param logicSysNo
	 *            逻辑系统标识
	 */
	@Transactional(readOnly = false)
	public void saveLayout(BioneMpDesignInfo designInfo,
			List<BioneMpDesignDetail> details) {
		if (designInfo == null || StringUtils.isEmpty(designInfo.getUserId())
				|| StringUtils.isEmpty(designInfo.getLogicSysNo())
				|| StringUtils.isEmpty(designInfo.getLayoutId())) {
			return ;
		}
		//若是修改操作
		if(!StringUtils.isEmpty(designInfo.getDesignId())){
			//清除所有定制下的详细信息项
			String deleteJql = "delete from BioneMpDesignDetail detail where detail.designId = ?0 ";
			this.baseDAO.batchExecuteWithIndexParam(deleteJql,designInfo.getDesignId());
		}else{
			designInfo.setDesignId(RandomUtils.uuid2());
		}
		//保存基本定制信息
		this.updateEntity(designInfo);
		//保存定制详细信息
		if(details != null){
			for(int i = 0 ; i < details.size() ; i++){
				String detailIdTmp = RandomUtils.uuid2();
				details.get(i).setDetailId(detailIdTmp);
				details.get(i).setDesignId(designInfo.getDesignId());
				this.saveEntity(details.get(i));
			}
		}
	}
	
	/**
	 * 获取用户在指定逻辑系统下的基础布局信息
	 * 
	 * @param userId
	 * 			     用户id
	 * @param logicSysNo
	 *            逻辑系统标识
	 * @return 基础布局信息
	 */
	public BioneMpDesignInfo getUserLayoutInfo(String userId , String logicSysNo){
		BioneMpDesignInfo info = null;
		if(!StringUtils.isEmpty(userId)
				&& !StringUtils.isEmpty(logicSysNo)){
			String jql = "select info from BioneMpDesignInfo info where info.userId = ?0 and info.logicSysNo = ?1 ";
			List<BioneMpDesignInfo> infos = this.baseDAO.findWithIndexParam(jql, userId , logicSysNo);
			if(infos != null && infos.size() > 0){
				info = infos.get(0);
			}
		}
		return info;
	}
	
	/**
	 * 获取指定定制ID下对应的所有明细信息
	 * 
	 * @param designId
	 * 			     定制id
	 * @return 明细布局信息
	 */
	public List<BioneMpDesignDetail> getDetailsByDesignId(String designId){
		List<BioneMpDesignDetail> details = null;
		if(!StringUtils.isEmpty(designId)){
			String jql = "select d from BioneMpDesignDetail d where d.designId = ?0 ";
			List<BioneMpDesignDetail> ds = this.baseDAO.findWithIndexParam(jql, designId);
			if(ds != null && ds.size() > 0){
				details = ds;
			}
		}
		return details;
	}
	
	/**
	 * 获取指定系统、用户下，所对应的基本布局信息
	 * 
	 * @param userId
	 * @param logicSysId
	 * @return 基本布局信息map
	 */
	public Map<String,String> getBasicInfoByUser(String userId , String logicSysNo){
		Map<String,String> returnMap = new HashMap<String,String>();
		if(!StringUtils.isEmpty(userId)
				&& !StringUtils.isEmpty(logicSysNo)){
			String jql = "select d.designId , d.layoutId , l.cssPath from BioneMpDesignInfo d , BioneMpLayoutInfo l where d.layoutId = l.layoutId and d.userId = ?0 and d.logicSysNo = ?1 ";
			List<Object[]> objs = this.baseDAO.findWithIndexParam(jql, userId , logicSysNo);
			if(objs != null && objs.size() > 0){
				//只取一条记录
				Object[] objArray = objs.get(0);
				if(objArray.length >= 3){
					returnMap.put("designId", objArray[0]==null?"":(String)objArray[0]);
					returnMap.put("layoutId", objArray[1]==null?"":(String)objArray[1]);
					returnMap.put("cssPath", objArray[2]==null?"":(String)objArray[2]);
				}
			}
		}
		return returnMap;
	}
	
	/**
	 * 获取指定用户定制下具体布局信息
	 * 
	 * @param designId
	 * @return 具体布局信息map
	 */
	public List<MpDetailInfoVO> getDetailInfoById(String designId){
		List<MpDetailInfoVO> returnList = new ArrayList<MpDetailInfoVO>();
		if(!StringUtils.isEmpty(designId)){
			String jql = "select new com.yuchengtech.emp.biappframe.mainpage.web.vo.MpDetailInfoVO(d.posNo , d.isDisplayLabel , m.moduleName , m.labelPath , m.modulePath) from BioneMpDesignDetail d , BioneMpModuleInfo m where d.moduleId = m.moduleId and d.designId = ?0 ";
			List<MpDetailInfoVO> details = this.baseDAO.findWithIndexParam(jql, designId);
			if(details != null){
				returnList = details;
			}
		}
		return returnList;
	}
}
