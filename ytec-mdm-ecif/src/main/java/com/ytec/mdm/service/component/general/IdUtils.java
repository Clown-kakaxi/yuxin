/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.general
 * @文件名：IdUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:59:26
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.facade.IMCIdentifying;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：IdUtils
 * @类描述：主键客户号生成
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:59:26
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:59:26
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
public class IdUtils implements IMCIdentifying {

	@SuppressWarnings("rawtypes")
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(IdUtils.class);

	/**
	 * 生成ECIF客户号EcifCustId, 为技术主键, 对应客户表(CUSTOMER)中客户标识(cust_id)
	 * 本项目中，通过以下方式找出cust_id上的序列： db2
	 * "select SEQSCHEMA,SEQNAME from syscat.SEQUENCES where seqschema='ECIF'"，
	 * 从输出结果中找出所需序列，结果为：SEQ_CUST_ID 如此序列名改变，则需要修改，且得保证序列为生成ECIF客户号技术主键的序列
	 * 
	 * @author: wangtingbang (wangtb@yuchengtech.com)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getEcifCustId(String custType) throws NumberFormatException, ConfigurationException, Exception {
		// String seqCustId = "SEQ_CUST_ID";
		String seqCustId = MdmConstants.SEQ_CUST_ID;
		if (seqCustId == null || "".equals(seqCustId)) { throw new Exception("ECIF客户号生成所依赖的数据库序列配置为空，请在配置文件中配置"); }
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List list = new ArrayList();
		String sCustId = null;
		list = baseDAO.findByNativeSQLWithIndexParam("select " + seqCustId + ".nextval from " + MdmConstants.formSeq,
				null);
		if (list != null && list.size() > 0) {
			sCustId = custType + list.get(0).toString();
		} else {
			sCustId = null;
		}
		log.info("根据客户类型[{}]生成客户ID[{}]", custType, sCustId);
		return sCustId;
	}

	/**
	 * @author wangtingbang (wangtb@yuchengtech.com)
	 * @param int custType, 客户类型:1为对私，2为对公，3为同业(建议所传参数为本类中三个CUST_TP 常量)
	 * @return String custNo (ECIF系统客户号，高位(从左起第一位)为客户号类型标识，低位为客户号校验位)
	 *         生成ECIF客户号EcifCustNo, 业务主键, 对应客户表(CUSTOMER)中客户标识(cust_no)
	 *         本项目中，通过以下方式找出cust_no上的序列： db2
	 *         "select SEQSCHEMA,SEQNAME from syscat.SEQUENCES where seqschema='ECIF'"
	 *         ， 从输出结果中找出所需序列，结果为：SEQ_CUST_NO
	 *         如此序列名改变，则需要修改，且得保证序列为生成ECIF客户号技术业务主键的序列
	 *         核心客户号11位，1+9+1；第一位为1：对私，2：对公，3：同业；9位为顺序号；最后一位校验位。
	 *         生成客户号校验位("隔位乘2加"算法) 1、从右边第一个数字(低序)开始每隔一位*2
	 *         2、将1中获得乘积的各位数字与原号码中未*2的各位数字相加 3、把2中得到的总和从该值的下一个以0结尾的数字中减去 e.g.:
	 *         无校验数字的客户号为 4992739871 4 9 9 2 7 3 9 8 7 1 1-> *2 *2 *2 *2 *2
	 *         _______________________ 18 4 6 16 2
	 *         2-> 4+1+8+9+4+7+6+9+1+6+7+2=64
	 *         3-> 70-64=6 带校验位客户号为∶ 49927398716
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getEcifCustNo(String custType) throws NumberFormatException, ConfigurationException, Exception {
		String seqCustNo = "SEQ_CUST_NO";
		if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
			custType = "1";
		} else if (MdmConstants.TX_CUST_ORG_TYPE.equals(custType)) {
			custType = "2";
		} else if (MdmConstants.TX_CUST_BANK_TYPE.equals(custType)) {
			custType = "3";
		} else {
			throw new Exception("客户类型不支持");
		}
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List list = new ArrayList();
		String sCustNo = null;

		list = baseDAO.findByNativeSQLWithIndexParam("select " + seqCustNo + ".nextval from " + MdmConstants.formSeq,
				null);
		if (list != null && list.size() > 0) {
			sCustNo = custType + String.format("%09d", Long.valueOf(list.get(0).toString()));
			// 从后面第一位开始， 到前两位，之间进行处理加到sum中 (示例中不带标志位，本方法sCustNo中带)
			int[] iNumArr = new int[sCustNo.length()];
			for (int idx = 0; idx < sCustNo.length(); idx++) {
				iNumArr[idx] = Integer.parseInt("" + sCustNo.substring(idx, idx + 1));
			}

			int sum = 0;

			for (int idx = iNumArr.length - 1; idx > 1; idx -= 2) {
				sum += iNumArr[idx] * 2 / 10 + iNumArr[idx] * 2 % 10 + Integer.parseInt("" + iNumArr[idx - 1]);
			}

			int check = (10 - sum % 10) % 10;
			sCustNo = sCustNo.substring(0, sCustNo.length()) + check;
			log.info("根据客户类型[{}]生成客户号[{}]", custType, sCustNo);
			return sCustNo;
		} else {
			throw new Exception("找不到可用的客户号，数据库序列返回值错误");
		}
	}

	/**
	 * 根据所传参数(attrName)选择数据库中对应序列，之后生成attrName在数据库中对应主键的下一个可用值并返回
	 * 生成规则依赖数据库中序列，并且序列命令规则要以SEQ_ + ATTRNAME为规则，其中，ATTRNAME与相应字段名一致
	 * 
	 * @author wangtingbang (wangtb@yuchengtech.com)
	 * @param String
	 *        arrtName, 属性名称, 对应数据表为主键数据字段 (属性),命名规范与数据表字段名同
	 * @return String AttrId
	 * @throws NumberFormatException
	 * @throws ConfigurationException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getPriIdByAttrName(String attrName) throws Exception {
		// TODO Auto-generated method stub
		String seqName = null;
		if (0 != attrName.indexOf("_")) {
			seqName = "SEQ_" + attrName;
		} else {
			throw new Exception("无法识别序列，所传参数不符合程序规范");// TODO, 需细化成ECIF系统异常
		}
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List list = new ArrayList();
		String sAttrId = null;

		list = baseDAO.findByNativeSQLWithIndexParam("select " + seqName + ".nextval from " + MdmConstants.formSeq,
				null);
		if (list != null && list.size() > 0) {
			sAttrId = list.get(0).toString();
			return sAttrId;
		} else {
			throw new Exception("找不到可用的序列号，数据库序列返回值错误");// TODO, 需细化成ECIF系统异常
		}
	}
}
