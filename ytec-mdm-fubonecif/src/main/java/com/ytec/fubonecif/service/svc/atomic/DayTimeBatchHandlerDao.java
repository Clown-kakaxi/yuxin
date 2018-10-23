/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.dao
 * @�ļ�����DayTimeBatchHandlerDao.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:08:00
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.facade.IColumnUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�DayTimeBatchHandlerDao
 * @�������������ͻ���Ϣ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:08:00   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:08:00
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class DayTimeBatchHandlerDao {
	private JPABaseDAO baseDAO;
	private IColumnUtils columnUtils;
	
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void saveCustomer(List custEntity,EcifData ecifData) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
		for(Object custObj:custEntity){
			OIdUtils.createId(custObj);
			columnUtils.setCreateGeneralColumns(ecifData, custObj);
			baseDAO.persist(custObj);
		}
		baseDAO.flush();
	}

}
