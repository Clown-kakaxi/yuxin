/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.dao
 * @文件名：DayTimeBatchHandlerDao.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:08:00
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：DayTimeBatchHandlerDao
 * @类描述：批量客户信息保存
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:08:00   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:08:00
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
