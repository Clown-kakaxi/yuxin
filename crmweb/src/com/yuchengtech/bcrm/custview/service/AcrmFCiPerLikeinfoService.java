package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerLikeinfo;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerPreference;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 对私客户视图（个人喜好信息）
 * @author agile
 *
 */
@Service
public class AcrmFCiPerLikeinfoService extends CommonService {
	
	public AcrmFCiPerLikeinfoService(){
		JPABaseDAO<AcrmFCiPerLikeinfo,Long> baseDao = new JPABaseDAO<AcrmFCiPerLikeinfo,Long>(AcrmFCiPerLikeinfo.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiPerLikeinfo likeinfo = (AcrmFCiPerLikeinfo)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//获取页面其他信息
		//
		String loveChannelCkg = request.getParameter("loveChannelCkg");
		if(loveChannelCkg !=null && !loveChannelCkg.isEmpty()){
			likeinfo.setLikeEchanlType(likeinfo.getLikeEchanlType()+"|"+loveChannelCkg);
		}
		//偏好的金融业务类型
		String loveBusiTypeCkg = request.getParameter("loveBusiTypeCkg");
		//希望联系的时间
		String[]  likeContactTimes  = request.getParameterValues("likeContactTime");
		//投资周期偏好
		String[]  likeInvestCycles  = request.getParameterValues("likeInvestCycle");
		String[]  likeBusiTypes  = request.getParameterValues("likeBusiType");
		String likeContactTime = "";
		String likeBusiType = "";
		String likeInvestCycle = "";
		if(likeContactTimes != null ){
			for(String  a :  likeContactTimes){
				likeContactTime += ","+a;
			}
		}
		if(likeBusiTypes != null){
			for(String a :  likeBusiTypes){
				likeBusiType += ","+a;
			}
		}
		if(likeInvestCycles != null){
			for(String a :  likeInvestCycles){
				likeInvestCycle += ","+a;
			}
		}
		String loveConTimeTypeCkg = request.getParameter("loveConTimeTypeCkg");
		if((likeContactTime!=null && !likeContactTime.isEmpty()) || (likeBusiType !=null && !likeBusiType.isEmpty()) || 
				(likeInvestCycle!=null && !likeInvestCycle.isEmpty())){
			List<AcrmFCiPerPreference>   pps  = this.findByJql("select a from AcrmFCiPerPreference a where a.custId = '"+customerId+"'", null);
			if(pps != null && !pps.isEmpty()){
				for(AcrmFCiPerPreference pp : pps){
					pp.setFinanceBusinessPrefer(likeBusiType!=""?likeBusiType.substring(1):"");
					if(loveBusiTypeCkg!=null && !loveBusiTypeCkg.isEmpty()){
						pp.setFinanceBusinessPrefer(pp.getFinanceBusinessPrefer()+"|"+loveBusiTypeCkg);
					}else{
						pp.setFinanceBusinessPrefer(pp.getFinanceBusinessPrefer());
					}
					pp.setContactTimePrefer(likeContactTime!=""?likeContactTime.substring(1):""+"|"+loveConTimeTypeCkg);
					pp.setInvestCycle(likeInvestCycle!=""?likeInvestCycle.substring(1):"");
					pp.setLastUpdateUser(auth.getUsername());
					pp.setLastUpdateTm(new Timestamp(new Date().getTime()));
					super.save(pp);
				}
			}else{
				AcrmFCiPerPreference pp = new AcrmFCiPerPreference();
				pp.setCustId(customerId);
				pp.setInvestCycle(likeInvestCycle!=""?likeInvestCycle.substring(1):"");
				pp.setFinanceBusinessPrefer(likeBusiType!=""?likeBusiType.substring(1):""+"|"+loveBusiTypeCkg);
				pp.setContactTimePrefer(likeContactTime!=""?likeContactTime.substring(1):""+"|"+loveConTimeTypeCkg);
				
				pp.setLastUpdateUser(auth.getUsername());
				pp.setLastUpdateTm(new Timestamp(new Date().getTime()));
				super.save(pp);
			}
		}
		
		//宗教信仰
		String custReligion = request.getParameter("custReligion");
		if(custReligion!=null && !custReligion.isEmpty()){
			List<AcrmFCiPerson>   ps  = this.findByJql("select a from AcrmFCiPerson a where a.custId = '"+customerId+"'", null);
			if(ps!=null && !ps.isEmpty()){
				for(AcrmFCiPerson p : ps){
					p.setReligiousBelief(custReligion);
					
					p.setLastUpdateUser(auth.getUsername());
					p.setLastUpdateTm(new Timestamp(new Date().getTime()));
					super.save(p);
				}
			}else{
				AcrmFCiPerson p = new AcrmFCiPerson();
				p.setCustId(customerId);
				p.setReligiousBelief(custReligion);
				
				p.setLastUpdateUser(auth.getUsername());
				p.setLastUpdateTm(new Timestamp(new Date().getTime()));
				super.save(p);
			}
		}
		String loveLeisureTypeCkg = request.getParameter("loveLeisureTypeCkg");
		if(loveLeisureTypeCkg!=null && !loveLeisureTypeCkg.isEmpty()){
			likeinfo.setLikeLeisureType(likeinfo.getLikeLeisureType()+"|"+loveLeisureTypeCkg);
		}
		
		String loveMediaTypeCkg = request.getParameter("loveMediaTypeCkg");
		if(loveMediaTypeCkg!=null && !loveMediaTypeCkg.isEmpty()){
			likeinfo.setLikeMediaType(likeinfo.getLikeMediaType()+"|"+loveMediaTypeCkg);
		}
		
		String loveSportTypeCkg = request.getParameter("loveSportTypeCkg");
		if(loveSportTypeCkg!=null && !loveSportTypeCkg.isEmpty()){
			likeinfo.setLikeSportType(likeinfo.getLikeSportType()+"|"+loveSportTypeCkg);
		}
		
		String loveMagazineTypeCkg = request.getParameter("loveMagazineTypeCkg");
		if(loveMagazineTypeCkg!=null && !loveMagazineTypeCkg.isEmpty()){
			likeinfo.setLikeMagazineType(likeinfo.getLikeMagazineType()+"|"+loveMagazineTypeCkg);
		}
		
		String loveMovieTypeCkg = request.getParameter("loveMovieTypeCkg");
		if(loveMovieTypeCkg!=null && !loveMovieTypeCkg.isEmpty()){
			likeinfo.setLikeFilmType(likeinfo.getLikeFilmType()+"|"+loveMovieTypeCkg);
		}
		
		String lovePetTypeCkg = request.getParameter("lovePetTypeCkg");
		if(lovePetTypeCkg!=null && !lovePetTypeCkg.isEmpty()){
			likeinfo.setLikePetType(likeinfo.getLikePetType()+"|"+lovePetTypeCkg);
		}
		
		String loveCollectTypeCkg = request.getParameter("loveCollectTypeCkg");
		if(loveCollectTypeCkg!=null && !loveCollectTypeCkg.isEmpty()){
			likeinfo.setLikeCollectionType(likeinfo.getLikeCollectionType()+"|"+loveCollectTypeCkg);
		}
		
		String loveInvestTypeCkg = request.getParameter("loveInvestTypeCkg");
		if(loveInvestTypeCkg!=null && !loveInvestTypeCkg.isEmpty()){
			likeinfo.setLikeInvestType(likeinfo.getLikeInvestType()+"|"+loveInvestTypeCkg);
		}
		
//		String loveRandTypeCkg = request.getParameter("loveRandTypeCkg");
//		if(loveRandTypeCkg!=null && !loveRandTypeCkg.isEmpty()){
//			likeinfo.setLikeBrandType(likeinfo.getLikeBrandType()+"|"+loveRandTypeCkg);
//		}
		
		String loveFinaServTypeCkg = request.getParameter("loveFinaServTypeCkg");
		if(loveFinaServTypeCkg!=null && !loveFinaServTypeCkg.isEmpty()){
			likeinfo.setFinaServ(likeinfo.getFinaServ()+"|"+loveFinaServTypeCkg);
		}
		
		String loveContactTypeCkg = request.getParameter("loveContactTypeCkg");
		if(loveContactTypeCkg!=null && !loveContactTypeCkg.isEmpty()){
			likeinfo.setContactType(likeinfo.getContactType()+"|"+loveContactTypeCkg);
		}
		
		String loveFinaNewsTypeCkg = request.getParameter("loveFinaNewsTypeCkg");
		if(loveFinaNewsTypeCkg!=null && !loveFinaNewsTypeCkg.isEmpty()){
			likeinfo.setFinaNews(likeinfo.getFinaNews()+"|"+loveFinaNewsTypeCkg);
		}
		
		String loveSalonTypeCkg = request.getParameter("loveSalonTypeCkg");
		if(loveSalonTypeCkg!=null && !loveSalonTypeCkg.isEmpty()){
			likeinfo.setSalon(likeinfo.getSalon()+"|"+loveSalonTypeCkg);
		}
		
		String loveInterestsTypeCkg = request.getParameter("loveInterestsTypeCkg");
		if(loveInterestsTypeCkg!=null && !loveInterestsTypeCkg.isEmpty()){
			likeinfo.setInterests(likeinfo.getInterests()+"|"+loveInterestsTypeCkg);
		}
		
		likeinfo.setCustId(customerId);
		likeinfo.setLastUpdateUser(auth.getUsername());
		likeinfo.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return super.save(likeinfo);
	}
}
