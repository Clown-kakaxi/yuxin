package com.yuchengtech.bcrm.workplat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.retrieval.IndexFiles;
import com.yuchengtech.bcrm.workplat.action.WorkingplatformInfoAction;
import com.yuchengtech.bcrm.workplat.model.WorkingplatformAnnexeTemp;
import com.yuchengtech.bob.common.EntityToVoTrans;
import com.yuchengtech.bob.model.WorkingplatformAnnexe;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.bob.vo.WorkingplatformAnnexeVo;

/**
 * @describe 附件服务
 * @author WillJoe
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class WorkingplatformAnnexeService {

	private EntityManager em;
		
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * 保存：包括新增和修改
	 * @param wa
	 */
	public void save(WorkingplatformAnnexeVo wav){
		if(wav.getAnnexeId()==null){
		    WorkingplatformAnnexe wa = new WorkingplatformAnnexe();
		    wa.setAnnexeName(wav.getAnnexeName());
		    wa.setRelationInfo(wav.getRelationInfo());
		    wa.setRelationMod(wav.getRelationMod());
		    wa.setPhysicalAddress(wav.getPhysicalAddress());
		    wa.setAnnexeSize(wav.getAnnexeSize());
		    wa.setLoadCount(0l);
		    wa.setCreateTime(new Date());
			em.persist(wa);
			if("infomation".equals(wav.getRelationMod())){//创建索引
				WorkingplatformInfoAction wfAction = new WorkingplatformInfoAction();
				wfAction.createIndexFiles(wav.getPhysicalAddress());
			}
		}else {
		    WorkingplatformAnnexe wa = (WorkingplatformAnnexe)em.find(WorkingplatformAnnexe.class, wav.getAnnexeId());
		    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
	        //String userId = auth.getUserId(); 
	        String userName = auth.getUsername();
	        wa.setLastLoader(userName);
	        wa.setLastLoadTime(new Date());
	        wa.setLoadCount(wa.getLoadCount()+1);
		    em.merge(wa);
		} 
	}
	/**
	 * 上传创建公告附件临时表
	 */
	public void createNoticeTemp(WorkingplatformAnnexeVo wav){
		WorkingplatformAnnexeTemp  temp = new WorkingplatformAnnexeTemp();
		temp.setAnnexeName(wav.getAnnexeName());
		temp.setRelationInfo(wav.getRelationInfo());
		temp.setRelationMod(wav.getRelationMod());
		temp.setPhysicalAddress(wav.getPhysicalAddress());
		temp.setAnnexeSize(wav.getAnnexeSize());
		temp.setLoadCount(0l);
		temp.setCreateTime(new Date());
	    em.persist(temp);
	}
	/**
	 * 移除记录
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void remove(Long id){
		WorkingplatformAnnexe wp = (WorkingplatformAnnexe)em.find(WorkingplatformAnnexe.class, id);
		if(null!=wp){
			//删除时先删除文件索引 限于知识库发布
			if("infomation".equals(wp.getRelationMod())){
				IndexFiles.getInstance().delete(wp.getPhysicalAddress());
			}
			//删除临时表文件(临时表用于公告审核)
			if("notice".equals(wp.getRelationMod())){
				String jql = "select t from WorkingplatformAnnexeTemp t where t.relationInfo = '"+wp.getRelationInfo()+"' " +
						" and t.annexeName = '"+wp.getAnnexeName()+"'";
				Query query = em.createQuery(jql);
				List<WorkingplatformAnnexeTemp> temps = (List<WorkingplatformAnnexeTemp>)query.getResultList();
				for(WorkingplatformAnnexeTemp temp:temps){
					em.remove(temp);
				}
			}
			em.remove(wp);
		}
	}
	
	/**
	 * 查看记录
	 * @param id
	 * @return
	 */
	public WorkingplatformAnnexeVo find(Long id){
	    WorkingplatformAnnexe wa =  em.find(WorkingplatformAnnexe.class, id);
	    EntityToVoTrans etvt = new EntityToVoTrans();
	    return (WorkingplatformAnnexeVo) etvt.trans(wa);
	}
	
	/**
	 * 查询所有记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkingplatformAnnexeVo> findAll(){
		String waFindall = "select wa from WorkingplatformAnnexe wa";
		Query waQuery = em.createQuery(waFindall);
		List<WorkingplatformAnnexe> wal =  waQuery.getResultList();
		List<WorkingplatformAnnexeVo> wavl = new ArrayList<WorkingplatformAnnexeVo>();
		EntityToVoTrans etvt = new EntityToVoTrans();
		for(WorkingplatformAnnexe wa:wal){
		    WorkingplatformAnnexeVo wav = (WorkingplatformAnnexeVo) etvt.trans(wa);
		    wavl.add(wav);
		}
		return wavl;
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkingplatformAnnexeVo> findByRe(String relateId){
	    
	    String waFindRe = "select wa from WorkingplatformAnnexe wa where wa.relationInfo = ?1";
	    Query  q = em.createQuery(waFindRe);
	    q.setParameter(1, relateId);
        List<WorkingplatformAnnexe> wal = (List<WorkingplatformAnnexe>)q.getResultList();
	    EntityToVoTrans etvt = new EntityToVoTrans();
	    List<WorkingplatformAnnexeVo> wavl = new ArrayList<WorkingplatformAnnexeVo>();
	    for(WorkingplatformAnnexe wa:wal){
	        WorkingplatformAnnexeVo wav = (WorkingplatformAnnexeVo)etvt.trans(wa);
	        wavl.add(wav);
	    }
	    return wavl;
	}
}
