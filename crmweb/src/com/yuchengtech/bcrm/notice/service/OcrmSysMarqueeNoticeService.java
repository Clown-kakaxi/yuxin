package com.yuchengtech.bcrm.notice.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.notice.model.OcrmSysMarqueeNotice;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion 跑马灯信息维护--业务逻辑层
 * @author liuyx
 * @date: 2017年10月18日 下午5:03:54 
 */
@Service
public class OcrmSysMarqueeNoticeService extends CommonService {
	public OcrmSysMarqueeNoticeService(){
		JPABaseDAO<OcrmSysMarqueeNotice,Long> baseDAO = new JPABaseDAO<OcrmSysMarqueeNotice,Long>(OcrmSysMarqueeNotice.class);
		super.setBaseDAO(baseDAO);
	}
	/**
	 * 跑马灯新增、修改
	 */
	@SuppressWarnings("unchecked")
    @Override
	public Object save(Object obj) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmSysMarqueeNotice notice = (OcrmSysMarqueeNotice)obj;
		Calendar calendar=Calendar.getInstance();
		if(notice.getId()==null){//新增
			Timestamp ts = new Timestamp(calendar.getTimeInMillis());
			notice.setCreateDt(ts);
			notice.setCreateUser(auth.getUserId());
		}else{//修改
			OcrmSysMarqueeNotice not = (OcrmSysMarqueeNotice) baseDAO.get(notice.getId());
			notice.setCreateDt(not.getCreateDt());
			notice.setCreateUser(not.getCreateUser());
			baseDAO.getEntityManager().detach(not);
			not = null;
		}
		//有效时间
		String[] dtArr = notice.getValidDtTime().split(":");//ExtJS里使用的timefield，格式类似"12:15"
		calendar.setTime(notice.getValidDtDate());
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(dtArr[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(dtArr[1]));
		Timestamp validTs = new Timestamp(calendar.getTimeInMillis());
		notice.setValidDt(validTs);
	    return super.save(notice);
	}
	/**
	 * 批量删除跑马灯
	 */
	@Override
	public void batchRemove(String id) {
	    Map<String,Object> params = new HashMap<String,Object>();
	    params.put("id", Long.parseLong(id));
	    this.batchUpdateByName("delete from OcrmSysMarqueeNotice where id=:id", params);
	}
}
