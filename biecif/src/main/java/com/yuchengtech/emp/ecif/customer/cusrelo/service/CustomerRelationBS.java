package com.yuchengtech.emp.ecif.customer.cusrelo.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.customer.cusrelo.web.vo.CusRelationLookVO;
import com.yuchengtech.emp.ecif.customer.customerinfo.service.CustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Custrel;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgidentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgidentinfoBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PersonIdentifierBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author wuhp wuhp@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("customerRelationLookBS")
@Transactional(readOnly = true)
public class CustomerRelationBS extends BaseBS<Custrel> {
	
	@Autowired
	private CustomerInfoBS customerInfoBS;
	
	@Autowired
	private OrgidentinfoBS orgidentinfoBS;
	
	@Autowired
	private PersonIdentifierBS personIdentifierBS;
	
	@Autowired
	private CodeUtil codeUtil;

	protected static Logger log = LoggerFactory
			.getLogger(CustomerRelationBS.class);

	/**
	 * @param firstResult
	 *            分页的开始游标
	 * @param pageSize
	 *            游标大小
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序方式（升序/降序）
	 * @param conditionMap
	 *            参数列表（其他的参数，键值对形式）
	 * @return 任务实例集合
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<CusRelationLookVO> getTaskInstanceList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String approvalStat) {

		SearchResult<CusRelationLookVO> taskVOResult = new SearchResult<CusRelationLookVO>();
		List<CusRelationLookVO> temps = new ArrayList<CusRelationLookVO>();
		Object count = 0;
		
//		Map<String, ?> fieldValues = (Map<String, ?>) conditionMap.get("fieldValues");
//		if(fieldValues.get("custrel.CUST_REL_TYPE") == null){
//			taskVOResult.setResult(temps);
//			taskVOResult.setTotalCount(count);
//			return taskVOResult;
//		}
		Date tempDate = null;
		StringBuffer sql = new StringBuffer("");
		sql.append(" select ");
		sql.append(" custrel.CUST_REL_ID,");
		sql.append(" customer1.CUST_NO srcCustNo,");
		sql.append(" custrel.SRC_CUST_ID,");
		sql.append(" n1.CUST_NAME srcName, ");
		sql.append(" customer2.CUST_NO destCustNo,");
		sql.append(" custrel.DEST_CUST_ID ,");
		sql.append(" n2.CUST_NAME destName,");
		sql.append(" custrel.CUST_REL_TYPE,");
		sql.append(" custrel.REL_START_DATE,");
		sql.append(" custrel.REL_END_DATE,");
		sql.append(" custrel.CUST_REL_STAT,");
		sql.append(" custrel.LAST_UPDATE_SYS,");
		sql.append(" custrel.APPROVAL_FLAG, ");
		sql.append(" custrel.CUST_REL_DESC ");
//		sql.append(" , custrel.LAST_UPDATE_TM ");	
		sql.append(" from  M_CI_CUSTREL custrel ");
		sql.append(" left join M_CI_NAMETITLE n1 on n1.CUST_ID = custrel.SRC_CUST_ID ");
		sql.append(" left join M_CI_NAMETITLE n2 on n2.CUST_ID = custrel.DEST_CUST_ID ");
		sql.append(" left join M_CI_CUSTOMER customer1 on customer1.CUST_ID = custrel.SRC_CUST_ID");
		sql.append(" left join M_CI_CUSTOMER customer2 on customer2.CUST_ID = custrel.DEST_CUST_ID");
		// 没有审批状态，审批状态不为待审批
		sql.append(" where 1=1 ");
//		sql.append(" where 1=1 and (custrel.APPROVAL_FLAG is null or custrel.APPROVAL_FLAG <> '");
//		sql.append(approvalStat);
//		sql.append("')");
		if (!conditionMap.get("jql").equals("")) {
			sql.append(" and " + conditionMap.get("jql"));
		}

		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<Object[]> temp = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize,
						sql.toString(), values);

		List<Object[]> objList = temp.getResult();
		for (Object[] obj : objList) {
			CusRelationLookVO custrelInstance = new CusRelationLookVO();
			//客户间关系id
			custrelInstance.setCustRelId(obj[0] != null ? obj[0].toString(): "");
			//源码客户号
			custrelInstance.setSrcCustNo(obj[1] != null ? obj[1].toString(): "");
			//源客户标识
			custrelInstance.setSrcId(obj[2] != null ? obj[2].toString() : "");
			//源客户名
			custrelInstance.setSrcName(obj[3] != null ? obj[3].toString() : "");
			//目标客户号
			custrelInstance.setDestCustNo(obj[4] != null ? obj[4].toString() : "");
			//目标客户标识
			custrelInstance.setDestId(obj[5] != null ? obj[5].toString() : "");
			//目标客户名称
			custrelInstance.setDestName(obj[6] != null ? obj[6].toString() : "");
			//客户关系类型
			custrelInstance.setCustRelType(obj[7] != null ? obj[7].toString(): "");
			//关系开始日期
//			custrelInstance.setCustRelStart(obj[7] != null ? obj[7].toString() : "");
			try {
				tempDate = obj[8]!=null?ConvertUtils.getDateStrToData(obj[8].toString()):null;
			} catch (ParseException e) {
				tempDate = null;
			}finally{
				custrelInstance.setCustRelStart(tempDate);
			}
			tempDate = null;
			//关系结束日期
//			custrelInstance.setCustRelEnd(obj[8] != null ? obj[8].toString() : "");
			try {
				tempDate = obj[9]!=null?ConvertUtils.getDateStrToData(obj[9].toString()):null;
			} catch (ParseException e) {
				tempDate = null;
			}finally{
				custrelInstance.setCustRelEnd(tempDate);
			}
			//关系状态
			custrelInstance.setCustRelStat(obj[10] != null ? obj[10].toString(): "");
			//更新系统
			custrelInstance.setLastUpdateSys(obj[11] != null ? obj[11].toString(): "");
			//审批状态
			custrelInstance.setApprovalFlag(obj[12] != null ? obj[12].toString(): "");
			custrelInstance.setCustRelDesc(obj[13] != null ? obj[13].toString(): "");
			//最后更新时间
//			try {
//				custrelInstance.setLastUpdateTm(obj[12]!=null?ConvertUtils.getStrToTimestamp2(obj[12].toString()):null);
//			} catch (ParseException e) {
//				custrelInstance.setLastUpdateTm(null);
//			}
			temps.add(custrelInstance);
		}
		taskVOResult.setResult(temps);
		taskVOResult.setTotalCount(temp.getTotalCount());
		return taskVOResult;
	}

	/**
	 * 获取级联表结构
	 * 
	 * @return
	 */
	public List<Map<String, String>> getModeVer(String adapterId) {

		StringBuffer jql = new StringBuffer(
				"select cd.STD_CODE, cd.STD_CODE_DESC from TX_STD_CODE cd  where cd.STATE = '0' AND cd.STD_CATE='"+GlobalConstants.CODE_STR_CUSTREL_TYPE+"' ");

		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql
				.toString());
		// 存储返回对象
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		// 存储所有的关系类型
		Map<String, String> harvMap;

		if ("1".equals(adapterId)) {
			for (Object[] obj : objList) {
				if (obj[0].toString().length() == 11) {
					long id = Long.parseLong(obj[0].toString());
					if (1001001000L < id && id < 2001001000L) {
						harvMap = Maps.newHashMap();
						harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
						harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
						harvComboList.add(harvMap);
					}
				}
			}
		}
		if ("2".equals(adapterId)) {
			for (Object[] obj : objList) {
				if (obj[0].toString().length() == 11) {
					long id = Long.parseLong(obj[0].toString());

					if (2001001000L < id && id < 3000000000L) {
						harvMap = Maps.newHashMap();
						harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
						harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
						harvComboList.add(harvMap);
					}

				}
			}
		}
		if ("3".equals(adapterId)) {
			for (Object[] obj : objList) {
				if (obj[0].toString().length() == 11) {
					long id = Long.parseLong(obj[0].toString());
					if (3000000000L < id && id < 5000000000L) {
						harvMap = Maps.newHashMap();
						harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
						harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
						harvComboList.add(harvMap);
					}
				}
			}
		}
		return harvComboList;
	}
	/**
	 * 获取关系状态
	 * 
	 * @return
	 */
	public List<Map<String, String>> getRenderRelStat() {

		StringBuffer sql = new StringBuffer(
				"select * from TX_STD_CODE where STATE = '0' AND STD_CATE = '"+GlobalConstants.CODE_STR_VALID_TYPE+"'  ");
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[2] != null ? obj[2].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
	/*
	 * 判断关系表中是否存在关系
	 */
	public boolean isExistCustRel(Long srcCustId,Long destCustId,String custRelType){
		boolean flag = false;
		String sql = "SELECT CUST_REL_ID,SRC_CUST_ID FROM M_CI_CUSTREL WHERE SRC_CUST_ID=?0 and DEST_CUST_ID=?1 and CUST_REL_TYPE=?2";
		List objList = this.baseDAO.findByNativeSQLWithIndexParam(sql, srcCustId,destCustId,custRelType);
		if(objList != null && objList.size() > 0 ){
			flag = true;
		}
		return flag;
	}
	/**
	 * 保存客户标识,并且向证件表里插入数据
	 * 
	 */
	public void saveId(String identType, String identNo, String identCustName,String flag,Custrel custrel,String srcOrDest){
		
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		identType = perMap.get(identType);
		if(	identType == null || "".equals(identType)){
			identType = orgMap.get(identType);
		}
		if("per".equals(flag)){
			//是个人客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			PersonIdentifier personidentifier = customerInfoBS.getPersonIdentifierByCustId(identType,identNo,identCustName);
			if(personidentifier == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
				String sql = "select SEQ_CUST_ID.NEXTVAL FROM SYSIBM.SYSDUMMY1";
				
				List objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
				//保存证件信息
				personidentifier = new PersonIdentifier();
				personidentifier.setIdentId(null);
                
				BigInteger b = (BigInteger)(objList.get(0));
				personidentifier.setCustId(Long.parseLong(b.toString()));
				personidentifier.setIdentType(identType);
				personidentifier.setIdentNo(identNo);
				personidentifier.setIdentCustName(identCustName);
				//引入一个个人证件的bs
//				this.baseDAO.save(personidentifier);
				personIdentifierBS.updateEntity(personidentifier);
				
				//设置客户关系标识,判断是源客户还是目标客户
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(Long.parseLong(b.toString()));
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(Long.parseLong(b.toString()));
				}
				
			}else{
				//设置客户关系标识,判断是源客户还是目标客户
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(personidentifier.getCustId());
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(personidentifier.getCustId());
				}
			}
		}
		
		if("org".equals(flag)){
			//是机构客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			Orgidentinfo orgidentinfo = customerInfoBS.getOrgidentinfoByCustId(identType,identNo,identCustName);
			if(orgidentinfo == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
				String sql = "SELECT SEQ_CUST_ID.NEXTVAL  FROM SYSIBM.SYSDUMMY1";
				//看一下返回值是啥
				List objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
				//保存证件信息
				orgidentinfo = new Orgidentinfo();
				orgidentinfo.setIdentId(null);
				BigInteger b = (BigInteger)(objList.get(0));
				orgidentinfo.setCustId(Long.parseLong(b.toString()));
				orgidentinfo.setIdentType(identType);
				orgidentinfo.setIdentNo(identNo);
				orgidentinfo.setIdentCustName(identCustName);
				//引入一个机构证件的bs
//				this.baseDAO.save(orgidentinfo);
				orgidentinfoBS.updateEntity(orgidentinfo);
				//设置客户关系标识
				
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(Long.parseLong(b.toString()));
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(Long.parseLong(b.toString()));
				}
			}else{
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(orgidentinfo.getCustId());
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(orgidentinfo.getCustId());
				}
			}
		}
	}
	
	/**
	 * 直接更新给定的黑名单审批标识
	 * @param specialListId
	 */
	public void updateApprovalFlag(Long relId, String approvalFlag){
		String sql = "UPDATE M_CI_CUSTREL SET APPROVAL_FLAG=?0 WHERE CUST_REL_ID=?1";
		this.baseDAO.createNativeQueryWithIndexParam(
				sql,
				approvalFlag,
				relId).executeUpdate();
		this.baseDAO.flush();
	}
	
	public Long getIdentSeq(){
		Long seq = 0L;
		String sql = "select SEQ_CUST_ID.NEXTVAL FROM SYSIBM.SYSDUMMY1";
		List objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		BigInteger b = (BigInteger)(objList.get(0));
		seq = Long.parseLong(b.toString());
		return seq;
	}
}
