package com.yuchengtech.bcrm.customer.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCreatetemp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
@Service
public class LatentPotentialCusTwoService extends CommonService {
	public LatentPotentialCusTwoService(){
		JPABaseDAO<AcrmFCiPotCusCreatetemp,String> baseDao = new JPABaseDAO<AcrmFCiPotCusCreatetemp,String>(AcrmFCiPotCusCreatetemp.class);
		super.setBaseDAO(baseDao);
	}
    
	
	   public String saveLatent(String tempCustId,String custZhName,String custTyp,String linkUser,String zipcode,String sourceChannel,String mktActivitie,String cusNationality,String refereesId,String certType,
				String linkPhone,String contmethInfo,String addr,String custStat,String certNum,String jobType,String industType){	
				  System.out.println("data>"+tempCustId+ custZhName+ custTyp+ linkUser+ zipcode+ sourceChannel+mktActivitie+cusNationality+refereesId+certType+ linkPhone+ contmethInfo+ addr+ custStat+ certNum+ jobType+ industType);		
				  String sign=null;	
				  AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				  	Date date1 = new Date();
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = format1.format(date1);
					date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
					String callNo = linkPhone;				
					if(industType==null){
						industType="";
					}
					String sql="";
					//生成CUST_ID
					Random random1 = new Random();
					int sp=Math.abs(random1.nextInt());
					String result="crm"+sp;
				     sql = 
									   "insert into ACRM_F_CI_POT_CUS_COM" +
									   "  (cus_id," + 
									   "   cus_name," + 
									   "   CUST_TYPE," + 
									   "   ATTEN_NAME," + 
									   "   ZIPCODE," + 
									   "   SOURCE_CHANNEL," + 
									   "   MKT_ACTIVITIE," + 
									   "   CUS_NATIONALITY," + 
									   "   REFEREES_ID," + 
									   "   CERT_TYPE," + 
									   "   CERT_CODE," + 
									   "   CALL_NO," + 
									   "   CONTMETH_INFO," + 
									   "   JOB_TYPE," + 
									   "   CUS_ADDR," + 
									   "   INDUST_TYPE,"+
									   "   CONCLUSION," +
									   "   CUST_MGR," +
									   "   MAIN_BR_ID," +
									   "   INPUT_ID,"+
									   "   INPUT_BR_ID,"+
									   "   INPUT_DATE"+
									   "   )" + 
									   "values" + 
									   "  (" + 
									   "  '"+result+"'," + 
									   "  '"+custZhName+"'," + 
									   "  '2'," + 
									   "  '"+linkUser+"'," + 
									   "  '"+zipcode+"'," + 
									   "  '"+sourceChannel+"'," + 
									   "  '"+mktActivitie+"'," + 
									   "  '"+cusNationality+"'," + 
									   "  '"+refereesId+"'," + 
									   "  '"+certType+"'," + 
									   "  '"+certNum+"'," + 
									   "  '"+callNo+"'," + 
									   "  '"+contmethInfo+"'," + 
									   "  '"+jobType+"'," + 
									   "  '"+addr+"'," + 
									   "  '"+industType+"',"+
									   " '2'," + 
									   "  '"+auth.getUserId()+"'," + 
									   "  '"+auth.getUnitId()+"'," + 
									   "  '"+auth.getUserId()+"'," + 
									   "  '"+auth.getUnitId()+"'," + 
									   "  to_date('" + format1.format(new Date()) + "','yyyy-MM-dd')" + 
									   "   )";
							     System.out.println("转入临时客户:"+sql);                   
				                 try {
									this.em.createNativeQuery(sql).executeUpdate();
								} catch (Exception e) {
									e.printStackTrace();
								}
					
			           return sign;
				}
}
