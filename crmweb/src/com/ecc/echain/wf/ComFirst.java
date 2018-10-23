package com.ecc.echain.wf;

import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describtion: 对公客户一次提交一次复核处理类
 * @author : sunjing5
 * @date : 2017-06-23
 */
public class ComFirst extends EChainCallbackCommon{
	private static Logger log = Logger.getLogger(OrgFsx.class);
	/**
	 * 审批否决或撤办 处理方法
	 * 进行否决处理时，只能部分否决,如若点击流程办理同意了，但是由于代码未可见异常造成流程走不下去，必须撤销办理或否决时
	 * 只能否决掉同意时未因为异常未处理的信息！
	 * @param vo
	 */
	public void endN(EVO vo){
		try {
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String instanceid = vo.getInstanceID();
			String custId = instanceid.split("_")[1];
			String updateFlag = instanceid.split("_")[2];
			SQL = "update OCRM_F_CI_CUSTINFO_UPHIS V set v.appr_flag = '2',v.appr_user = '"+auth.getUserId()+"',v.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') WHERE V.CUST_ID='" + custId + "' AND V.UPDATE_FLAG like '" + updateFlag + "|%' AND (v.appr_flag is null OR v.appr_flag <> '1')";
			execteSQL(vo);
		} catch (Exception e) {
			log.error("comFirst--endN--ERROR: "+e.getMessage());
			throw new BizException(1,0,"0000","Warning-169：数据信息同步失败，请及时联系IT部门！");
		}
	}
	/**
	 * 审批同意
	 * 如若抛出异常，则可能部分成功,成功的顺序为第一屏，第二屏（地址、联系人、联系信息），第三屏
	 * @param vo
	 */
	public void endY(EVO vo){
		try {
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String instanceid = vo.getInstanceID();
			String custId = instanceid.split("_")[1];//客户号
			String preUpdateFlag = instanceid.split("_")[2];//修改标识前缀
			
			SQL = "SELECT DISTINCT T.UPDATE_FLAG,SUBSTR(T.UPDATE_FLAG,15,1) PAGE_ITEM,SUBSTR(T.UPDATE_FLAG,16,1) MODIFY_FLAG,T.APPR_FLAG FROM OCRM_F_CI_CUSTINFO_UPHIS T WHERE T.CUST_ID = '"+custId+"' AND T.UPDATE_FLAG LIKE '"+preUpdateFlag+"|%' ORDER BY T.UPDATE_FLAG ASC";
			Result result = querySQL(vo);
			for (SortedMap<?, ?> item : result.getRows()) {
				//关联修改记录
				String updateFlag = item.get("UPDATE_FLAG") != null ?(String) item.get("UPDATE_FLAG"):"";
				//0第一页,1第二页，2第三页地址,3第三页联系人,4第三页联系信息,5第三页证件信息，6第四页
				int pageItem = item.get("PAGE_ITEM") != null ?Integer.valueOf((String) item.get("PAGE_ITEM")):-1;
				//1新增,0修改,仅限于pageItem为2,3,4时生效
				String modifyFlag = item.get("MODIFY_FLAG") != null ?(String) item.get("MODIFY_FLAG"):"0";
				//1已经复核,2否决,是否在上一次流程提交过程中,已经复核过了,如果已复核过了,则不再调交易复核
				String apprFlag = item.get("APPR_FLAG") != null ?(String) item.get("APPR_FLAG"):"";
				if("1".equals(apprFlag)){
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户信息已复核[WARNNING]："+updateFlag);
					continue;
				}
				
				switch(pageItem){
					case 0:
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第一页调用开始--"+updateFlag);
						new ComFisrt2().endY(vo,custId,updateFlag,auth);
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第一页调用结束--"+updateFlag);
						break;
					case 1:
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第二页调用开始--"+updateFlag);
						new ComSecond().endY(vo,custId,updateFlag,auth);
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第二页调用结束--"+updateFlag);
						break;
					case 2:
					case 3:	
					case 4:
					case 5:
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第三页调用开始--"+updateFlag);
						new ComThird().endY(vo,custId,updateFlag,auth,pageItem,modifyFlag);
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第三页调用结束--"+updateFlag);
						break;
					case 6:
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第四页调用开始--"+updateFlag);
						new ComFourth().endY(vo,custId,updateFlag,auth);
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第四页调用结束--"+updateFlag);
						break;
					case 7:
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第二页（处罚发生日期）调用开始--"+updateFlag);
						new ComSecond2().endY(vo,custId,updateFlag,auth);
						log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对公客户第二页（处罚发生日期）调用结束--"+updateFlag);
						break;
					default:
						break;
				}
			}
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"0000","Warning-169：数据信息同步失败，请及时联系IT部门！");
		}
	}
}
