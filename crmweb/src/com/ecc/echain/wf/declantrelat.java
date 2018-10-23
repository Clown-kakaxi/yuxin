package com.ecc.echain.wf;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class declantrelat extends EChainCallbackCommon{
	public void agree(EVO vo) throws ParseException{//把数据由临时表存储到正式表,并删除临时表数据
		try {
			String instanceid = vo.getInstanceID().split("_")[1];
			SQL="select t.* from ACRM_F_CR_RELATE_PRIVY_INFO t where main_id ="+instanceid;
			execteSQL(vo);
			Result result=querySQL(vo);
			if(result.getRows()!=null){
				StringBuffer sb4 =new StringBuffer("insert into ACRM_F_CR_RELATE_PRIVY_INFO_T(CANCEL_STATE,CONTACT_ADDR, DECLARANT_BANK_REL," +
						"EFFECT_DATE,EMAIL,IDENT_NO, IDENT_TYPE,IS_COMMECIAL_BANK,MAIN_ID,PRIVY_ATTRIBUTE,PRIVY_NAME,RELATE_DECLARANT_REL,RELATE_ID,STOCK_RATIO, TEL) values('");
				for (SortedMap item3 : result.getRows()){
					String s1=	 item3.get("CANCEL_STATE")!=null?item3.get("CANCEL_STATE").toString():"";
					String s2=	 item3.get("CONTACT_ADDR")!=null?item3.get("CONTACT_ADDR").toString():"";
					String s3=	item3.get("DECLARANT_BANK_REL").toString();
					String s4=	item3.get("EFFECT_DATE")!=null?item3.get("EFFECT_DATE").toString():"";
					String s5=	item3.get("EMAIL").toString();
					String s6=	item3.get("IDENT_NO")!=null?item3.get("IDENT_NO").toString():"";
					String s7=	item3.get("IDENT_TYPE").toString();
					String s8=	item3.get("IS_COMMECIAL_BANK")!=null?item3.get("IS_COMMECIAL_BANK").toString():"";;
					String s9=	item3.get("MAIN_ID").toString();
					String s10=	item3.get("PRIVY_ATTRIBUTE").toString();
					String s11=	item3.get("PRIVY_NAME").toString();
					String s12=	item3.get("RELATE_DECLARANT_REL")!=null?item3.get("RELATE_DECLARANT_REL").toString():"";;
					String s13=	item3.get("RELATE_ID").toString();
					String s14=	item3.get("STOCK_RATIO")!=null?item3.get("STOCK_RATIO").toString():"";;
					String s15=	item3.get("TEL")!=null?item3.get("TEL").toString():"";
					StringBuffer sb5= new StringBuffer(s1+"','"+s2+"','"+s3+"',to_date('"+s4+"','yyyy-MM-dd'),'"+s5+"','"+s6+"','"+s7+"','"
							+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"','"+s13+"','"+s14+"','"+s15+"')");
					SQL=(sb4.toString()+sb5.toString()).toString();
					execteSQL(vo);
					
					}
			}
			SQL="delete from ACRM_F_CR_RELATE_PRIVY_INFO where main_id ="+instanceid;	//删除正式表对应的关系人信息
			execteSQL(vo);
			SQL="delete from ACRM_F_CR_DECLARANT_INFO where main_id ="+instanceid;//删除正式表对应的关联方信息
			execteSQL(vo);
			SQL = "select t.* from ACRM_F_CR_DECLARANT_INFO_TEMP t WHERE 1=1  and t.main_Id = '"+instanceid+"'";//查询客户经理信息审批申请表
			Result result1=querySQL(vo);
			
			StringBuffer sb =new StringBuffer("insert into ACRM_F_CR_DECLARANT_INFO(CANCEL_STATE,CANCLE_CAUSE,CHANGE_TYPE,CONTACT_ADDR,CREATE_DATE,CREATOR, " +
					"DECLARANT_ATTR,DECLARANT_BANK_REL,DECLARANT_NAME,DECLARE_DATE,DECLARE_STATUS," +
					"EFFECT_DATE,EMAIL,IDENT_NO, IDENT_TYPE,IS_COMMECIAL_BANK, LAST_UPDATE_TM,LAST_UPDATE_USER,MAIN_ID,REMARK,START_DATE,STOCK_RATIO, TEL) values('1','");
			
		
			for (SortedMap item : result1.getRows()){
				String s1=	 item.get("CANCLE_CAUSE")!=null?item.get("CANCLE_CAUSE").toString():"";
				String s2=	 item.get("CHANGE_TYPE")!=null?item.get("CHANGE_TYPE").toString():"";
				String s3=	 item.get("CONTACT_ADDR")!=null?item.get("CONTACT_ADDR").toString():"";
				String s22=	item.get("CREATE_DATE")!=null?item.get("CREATE_DATE").toString():"";
				String s4=	item.get("CREATOR")!=null?item.get("CREATOR").toString():"";
				String s5=	item.get("DECLARANT_ATTR").toString();
				String s6=	item.get("DECLARANT_BANK_REL").toString();
				String s7=	item.get("DECLARANT_NAME").toString();
				String s8=	item.get("DECLARE_DATE")!=null?item.get("DECLARE_DATE").toString():"";
				String s9=	item.get("DECLARE_STATUS")!=null?"2":"2";//设置申报状态
				Date crruntdate =new Date();
				SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
				String s10=f.format(crruntdate).toString();
				String s11=	item.get("EMAIL").toString();
				String s12=	item.get("IDENT_NO").toString();
				String s13=	item.get("IS_COMMECIAL_BANK")!=null?item.get("IS_COMMECIAL_BANK").toString():"";;
				String s14=	item.get("LAST_UPDATE_TM")!=null?item.get("LAST_UPDATE_TM").toString():"";
				String s15=	item.get("LAST_UPDATE_USER")!=null?item.get("LAST_UPDATE_USER").toString():"";
				String s16=	item.get("MAIN_ID").toString();
				String s17=	 item.get("REMARK")!=null?item.get("REMARK").toString():"";
				String s18=	item.get("START_DATE")!=null?item.get("START_DATE").toString():"";
				String s19=	item.get("STOCK_RATIO")!=null?item.get("STOCK_RATIO").toString():"";;
				String s20=	item.get("TEL")!=null?item.get("TEL").toString():"";
				String s21=	item.get("IDENT_TYPE").toString();
				sb.append(s1+"','"+s2+"','"+s3+"',to_date('"+s22+"','yyyy-MM-dd'),'"+s4+"','"+s5+"','"+s6+"','"+s7+"',to_date('"+s8+"','yyyy-MM-dd'),'"
						+s9+"',to_date('"+s10+"','yyyy-MM-dd'),'"+s11+"','"+s12+"','"+s21+"','"+s13+"',to_date('"+s14+"','yyyy-MM-dd'),'"+s15+"','"+s16+"','"+s17+"',to_date('"+ s18+"','yyyy-MM-dd'),'"+s19+"','"+s20+"')" );     
			}
			SQL =  sb.toString();
			execteSQL(vo);//添加到申报人正式表中
			
			
			
			SQL ="select t.* from ACRM_F_CR_RELATE_PRIVY_INFO_T t WHERE 1=1  and t.main_Id = '"+instanceid+"'";
			execteSQL(vo);//查询关系人并添加到正式表中
			
			StringBuffer sb2 =new StringBuffer("insert into ACRM_F_CR_RELATE_PRIVY_INFO(CANCEL_STATE,CONTACT_ADDR, DECLARANT_BANK_REL," +
					"EFFECT_DATE,EMAIL,IDENT_NO, IDENT_TYPE,IS_COMMECIAL_BANK,MAIN_ID,PRIVY_ATTRIBUTE,PRIVY_NAME,RELATE_DECLARANT_REL,RELATE_ID,STOCK_RATIO, TEL) values('");
			
			Result result2=querySQL(vo);
			for (SortedMap item2 : result2.getRows()){
				String s1=	 item2.get("CANCEL_STATE")!=null?item2.get("CANCEL_STATE").toString():"";
				String s2=	 item2.get("CONTACT_ADDR")!=null?item2.get("CONTACT_ADDR").toString():"";
				String s3=	item2.get("DECLARANT_BANK_REL").toString();
				String s4=	item2.get("EFFECT_DATE")!=null?item2.get("EFFECT_DATE").toString():"";
				String s5=	item2.get("EMAIL").toString();
				String s6=	item2.get("IDENT_NO")!=null?item2.get("IDENT_NO").toString():"";
				String s7=	item2.get("IDENT_TYPE").toString();
				String s8=	item2.get("IS_COMMECIAL_BANK")!=null?item2.get("IS_COMMECIAL_BANK").toString():"";;
				String s9=	item2.get("MAIN_ID").toString();
				String s10=	item2.get("PRIVY_ATTRIBUTE").toString();
				String s11=	item2.get("PRIVY_NAME").toString();
				String s12=	item2.get("RELATE_DECLARANT_REL")!=null?item2.get("RELATE_DECLARANT_REL").toString():"";;
				String s13=	item2.get("RELATE_ID").toString();
				String s14=	item2.get("STOCK_RATIO")!=null?item2.get("STOCK_RATIO").toString():"";;
				String s15=	item2.get("TEL")!=null?item2.get("TEL").toString():"";
				StringBuffer sb3= new StringBuffer(s1+"','"+s2+"','"+s3+"',to_date('"+s4+"','yyyy-MM-dd'),'"+s5+"','"+s6+"','"+s7+"','"
						+s8+"','"+s9+"','"+s10+"','"+s11+"','"+s12+"','"+s13+"','"+s14+"','"+s15+"')");
				SQL=(sb2.toString()+sb3.toString()).toString();
				execteSQL(vo);
				
				}
			
			SQL="delete from ACRM_F_CR_RELATE_PRIVY_INFO_T where main_id ="+instanceid;	//删除临时表对应的关系人
			execteSQL(vo);
			SQL="delete from ACRM_F_CR_DECLARANT_INFO_TEMP where main_id ="+instanceid;//删除临时表对应的关联方信息
			execteSQL(vo);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}
	
	public void refuse(EVO vo){
		try{
		String instanceid = vo.getInstanceID().split("_")[1];
		SQL="delete from ACRM_F_CR_RELATE_PRIVY_INFO_T where main_id ="+instanceid;	//删除临时表对应的关系人
		execteSQL(vo);
		SQL="delete from ACRM_F_CR_DECLARANT_INFO_TEMP where main_id ="+instanceid;//删除临时表对应的关联方信息
		execteSQL(vo);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}
}
