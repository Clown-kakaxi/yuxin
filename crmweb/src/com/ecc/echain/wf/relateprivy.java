package com.ecc.echain.wf;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

public class relateprivy extends EChainCallbackCommon{
	public void agree(EVO vo) throws ParseException{//把数据由临时表存储到正式表,并删除临时表数据
		try {
			String instanceid = vo.getInstanceID().split("_")[1];
			
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
				SQL=(sb2.toString()+sb3.toString());
				execteSQL(vo);
				
				}
			
			SQL="delete from ACRM_F_CR_RELATE_PRIVY_INFO_T where main_id ="+instanceid;	//删除临时表对应的关系人
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
		
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
		
	}
}
