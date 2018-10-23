package com.yuchengtech.bcrm.dynamicCrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriConf;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriScore;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

@Service
public class CustomerAttriTreeService extends CommonService {

	private static Logger log = Logger.getLogger(CustomerAttriTreeService.class);

	public CustomerAttriTreeService() {
		JPABaseDAO<CustomerAttriConf, Long> baseDAO = new JPABaseDAO<CustomerAttriConf, Long>(CustomerAttriConf.class);
		super.setBaseDAO(baseDAO);
	}

	/**
	 * 保存
	 * 
	 * @param cac
	 */
	@SuppressWarnings({ "rawtypes" })
	public void saveCustomerAttriConf(CustomerAttriConf cac) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (cac.getAttriId() == null) {
			String jql = "select t from CustomerAttriConf t where t.attriName = '" + cac.getAttriName() + "'";
			List list = this.em.createQuery(jql).getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1, 0, "", "该属性类别或客户属性已存在!");
			}
			if (cac.getUpAttriId() != null && cac.getUpAttriId().equals("客户属性")) {
				cac.setUpAttriId("0");
			}
			em.persist(cac);
			auth.setPid(cac.getAttriId());
			
			//设置ID_SEQ字段
//			if(cac.getAttriLevel() != null && cac.getAttriLevel().equals("1")){
//				cac.setIdSeq(cac.getAttriId());
//			}else{
				//查询父ID_SEQ
//				String jql2 = "select t from CustomerAttriConf t where t.attriId = '" + cac.getUpAttriId() + "'";
//				List list2 = this.em.createQuery(jql2).getResultList();
//				if(list2 != null && list2.size() > 0){
//					CustomerAttriConf faCac = (CustomerAttriConf) list2.get(0);
//					cac.setIdSeq(faCac.getIdSeq() + "," + cac.getAttriId());
//				}
//			}
			//重新保存一遍
//			em.merge(cac);
		}else{
			String jql = "select t from CustomerAttriConf t where t.attriName = '" + cac.getAttriName() + "' and t.attriId <> '" + cac.getAttriId() + "'";
			List list = this.em.createQuery(jql).getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1, 0, "", "该属性类别或客户属性已存在!");
			}
			
			//如果客户属性状态改为停用(1)，则指标也要改为停用
			if(cac.getAttriState() != null && "1".equals(cac.getAttriState())){
				String jql2 = "select t from CustomerAttriScore t where t.attriId = '" + cac.getAttriId() + "' and t.indexState <> '" + "1'";
				List list2 = this.em.createQuery(jql2).getResultList();
				if(list2 != null && list2.size() > 0){
					log.info("更新客户属性" + cac.getAttriName() + "的指标状态为停用");
					for(Object obj : list2){
						CustomerAttriScore cas = (CustomerAttriScore) obj;
						cas.setIndexState("1");
						em.merge(cas);
					}
				}
			}

			if (cac.getUpAttriId() != null && cac.getUpAttriId().equals("客户属性")) {
				cac.setUpAttriId("0");
			}
			//设置ID_SEQ字段
//			if(cac.getAttriLevel() != null && cac.getAttriLevel().equals("1")){
//				cac.setIdSeq(cac.getAttriId());
//			}else{
				//查询父ID_SEQ
//				String jql2 = "select t from CustomerAttriConf t where t.attriId = '" + cac.getUpAttriId() + "'";
//				List list2 = this.em.createQuery(jql2).getResultList();
//				if(list2 != null && list2.size() > 0){
//					CustomerAttriConf faCac = (CustomerAttriConf) list2.get(0);
//					cac.setIdSeq(faCac.getIdSeq() + "," + cac.getAttriId());
//				}
//			}
			em.merge(cac);
		}
	}

	/**
	 * 删除客户属性
	 * 
	 * @param id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void destroy(HttpServletRequest request) {
		String attriId = request.getParameter("attriId");
		String attriName = request.getParameter("attriName");
		String jql = "select t from CustomerAttriScore t where t.attriId = '" + attriId + "'";
		List list = this.em.createQuery(jql).getResultList();

		//删除客户属性的指标配置
		if(list != null && list.size() > 0){
			log.info("删除" + attriName + "属性的指标配置信息。");
			for(Object obj : list){
				baseDAO.remove(obj);
			}
		}
		
		//删除客户属性的指标值配置
		String jql2 = "select t from CustomerAttriItem t where t.attriId = '" + attriId + "'";
		List list2 = this.em.createQuery(jql2).getResultList();
		if(list2 != null && list2.size() > 0){
			log.info("删除" + attriName + "属性的指标配置信息。");
			for(Object obj : list2){
				baseDAO.remove(obj);
			}
		}		
		
		baseDAO.removeById(attriId);
	}
}
