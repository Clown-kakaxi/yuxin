package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustRelate;
import com.yuchengtech.bcrm.customer.model.AcrmFCiPerFamilys;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
* @ClassName: RelationshipManageService 
* @Description: 客户视图：客户关联关系管理 
* @author wangmk1 
* @date 2014-7-31  
*
 */
@Service
public class RelationshipManageService extends CommonService {
	public RelationshipManageService(){
		JPABaseDAO<AcrmFCiCustRelate, String> baseDao = new JPABaseDAO<AcrmFCiCustRelate, String>(AcrmFCiCustRelate.class);
		super.setBaseDAO(baseDao);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 新增 或 修改 
	 */
	@SuppressWarnings("unchecked")
	public Object save(Object obj) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiCustRelate relation =(AcrmFCiCustRelate) obj ;
		AcrmFCiPerFamilys family =new AcrmFCiPerFamilys();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId =request.getParameter("custId");
		
		//家庭家庭成员表  MEMBERNAME 成员名称    FAMILYRELA 家庭成员关系     TEL 电话    MOBILE 手机号码   关联客户号和核心客户号
		String tel = relation.getTel();//电话
		String mobile = relation.getMobile();//手机号码
		String membername = relation.getCustNameR();//成员名称
		String familyrela = relation.getRelationship();//家庭成员关系
		String cust_id  = relation.getCustId();//关联客户号
		String core_id  = relation.getCoreNo();//核心客户号
		Long f_id= relation.getFId();
		
		List<AcrmFCiCustomer> list = this.em.createQuery("select t from AcrmFCiCustomer t where t.custId='"+custId+"'").getResultList();
		AcrmFCiCustomer customer = list.get(0);
		Long idString = relation.getId();
		if(null==relation.getId()){
			relation.setCustName(customer.getCustName());
			relation.setCreatDate(new Date());
			relation.setCreatOrg(auth.getUnitName());
			relation.setCreatPerson(auth.getUsername());
			//将新增的信息保存到家庭信息表中
			family.setMxtid(request.getParameter("custNoR"));//关联客户编号
			family.setCustId(custId);
			family.setTel(tel);
			family.setMobile(mobile);
			family.setMembername(membername);
			family.setFamilyrela(familyrela);
			family.setCoreNo(core_id);//核心客户号
			/*if (familyrela.equals("104")) {
				family.setMxtid(request.getParameter("custNoR"));//关联客户编号
				family.setCustId(custId);
				family.setTel(tel);
				family.setMobile(mobile);
				family.setMembername(membername);
				family.setFamilyrela(familyrela);
				family.setCoreNo(core_id);//核心客户号
			}*/
		}
		else{
			relation.setUpdateTime(new Date());
			relation.setUpdatePerson(auth.getUsername());
			//将关联客户变更的信息保存到家庭信息表中    关联客户姓名  配偶联系电话  配偶移动电话  配偶对应客户号  配偶对应核心客户号(不能修改)
            family.setId(f_id);
			family.setMxtid(request.getParameter("custNoR"));//关联客户编号
			family.setTel(tel);
			family.setMobile(mobile);
			family.setMembername(membername);
			family.setFamilyrela(familyrela);
			family.setCustId(custId);
			//family.setCoreNo(core_id);//核心客户号
		
		/*	if (familyrela.equals("104")) {
				family.setMxtid(request.getParameter("custNoR"));//关联客户编号
				family.setTel(tel);
				family.setMobile(mobile);
				family.setMembername(membername);
				family.setFamilyrela(familyrela);
				//family.setCoreNo(core_id);//核心客户号
			}*/
		}
		/*if (familyrela.equals("104")) {
			super.save(family);
		}*/
		super.save(family);
		return super.save(relation);
	}
	
	//新增当关联关系为配偶时的删除方法
//	public void delInfo(String ids,String relations){
//		String jql = "delete from AcrmFCiPerFamilys  where custId in(:ids) and familyrela=(:relations)";
//		Query query = em.createQuery(jql);
//		query.setParameter("ids", ids);
//		query.setParameter("relations", relations);
//		query.executeUpdate();
//	}

}
