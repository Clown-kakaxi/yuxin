package com.yuchengtech.trans.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yuchengtech.crm.exception.BizException;



public class Dom4jReadXml {

	 public List getParseXmlList(String xml) throws Exception{
			List dataList = new ArrayList();
			Map map =new HashMap();

		  try{

			xml=xml.substring(4);
		  	boolean flag=false;
			Document doc = DocumentHelper.parseText(xml);
			Element root =doc.getRootElement();
			String TxStatCode = root.element("Data").element("Res").element("errorCode").getTextTrim();
			if(TxStatCode!=null && !TxStatCode.trim().equals("") && (TxStatCode.trim().equals("000000"))){
				flag = true;
			}
		
			
			
			 if(flag){
				 //得到可用余额的值
				 List< Map<String,String>> crdlmtbalance = new ArrayList< Map<String,String>>();
				String balance= root.element("Data").element("Res").element("crd_lmt_balance").getTextTrim();
				if(balance!=""){
					Map<String,String> aMap = new HashMap<String, String>();
					aMap.put("crd_lmt_balance", balance);
					crdlmtbalance.add(aMap);
				}
				 
				 
				 
				 //解析第一个list 对应的表OCRM_F_CI_GROUP_GENERALBUSI
				 List< Map<String,String>> LmtAppDetailsList = new ArrayList< Map<String,String>>();
				 if(root.element("Data").element("Res").element("LmtAppDetailsList").selectSingleNode("LmtAppDetails")!=null){
				 List<Element> list =root.element("Data").element("Res").element("LmtAppDetailsList").elements("LmtAppDetails");
				
				 for(int i=0;i<list.size();i++){
					Element e = list.get(i);
					Map<String,String> aMap = new HashMap<String, String>();
					String cus_name = e.element("cus_name").getTextTrim();
					aMap.put("cus_name", cus_name);
					String org_name= e.element("org_name").getTextTrim();
					aMap.put("org_name", org_name);
					String crd_lmt= e.element("crd_lmt").getTextTrim();
					aMap.put("crd_lmt", crd_lmt);
					String start_using_amt= e.element("start_using_amt").getTextTrim();
					aMap.put("start_using_amt", start_using_amt);
					String sum_balance= e.element("sum_balance").getTextTrim();
					aMap.put("sum_balance", sum_balance);
					String end_date= e.element("end_date").getTextTrim();
					aMap.put("end_date", end_date);
					String borrow_cus= e.element("borrow_cus").getTextTrim();
					aMap.put("borrow_cus", borrow_cus);
					LmtAppDetailsList.add(aMap);
				 }
				 }
				 //解析第二个list 对应的表OCRM_F_CI_GROUP_PLEDGEBUSI
				 List< Map<String,String>> PledgeMssJournalAccList = new ArrayList< Map<String,String>>();
				 if(root.element("Data").element("Res").element("PledgeMssJournalAccList").selectSingleNode("PledgeMssJournalAcc")!=null){
					 List<Element> list2 =root.element("Data").element("Res").element("PledgeMssJournalAccList").elements("PledgeMssJournalAcc");
				

					 for(int i=0;i<list2.size();i++){
						Element e = list2.get(i);
						Map<String,String> bMap = new HashMap<String, String>();
						String cus_name = e.element("pledge_id").getTextTrim();
						bMap.put("pledge_id", cus_name);
						String org_name= e.element("borrow_cus_id").getTextTrim();
						bMap.put("borrow_cus_id", org_name);
						String crd_lmt= e.element("borrow_cus_name").getTextTrim();
						bMap.put("borrow_cus_name", crd_lmt);
						String start_using_amt= e.element("currency").getTextTrim();
						bMap.put("currency", start_using_amt);
						String sum_balance= e.element("occur_amt").getTextTrim();
						bMap.put("occur_amt", sum_balance);
						String end_date= e.element("occur_date").getTextTrim();
						bMap.put("occur_date", end_date);
						PledgeMssJournalAccList.add(bMap);
					 }
				 }
				 
			

				 //解析第三个list 对应的表OCRM_F_CI_GROUP_DISCOUNTBUSI
				 List< Map<String,String>> DisAppDetailsList = new ArrayList< Map<String,String>>();
				 if(root.element("Data").element("Res").element("DisAppDetailsList").selectSingleNode("DisAppDetails")!=null){
				 List<Element> list3 =root.element("Data").element("Res").element("DisAppDetailsList").elements("DisAppDetails");
	

				 for(int i=0;i<list3.size();i++){
					Element e = list3.get(i);
					Map<String,String> cMap = new HashMap<String, String>();
					String cus_name_dis = e.element("cus_name_dis").getTextTrim();
					cMap.put("cus_name_dis", cus_name_dis);
					String org_name_dis= e.element("org_name_dis").getTextTrim();
					cMap.put("org_name_dis", org_name_dis);
					String crd_lmt_dis= e.element("crd_lmt_dis").getTextTrim();
					cMap.put("crd_lmt_dis", crd_lmt_dis);
					String start_amt= e.element("start_amt").getTextTrim();
					cMap.put("start_amt", start_amt);
					String sum_balance_dis= e.element("sum_balance_dis").getTextTrim();
					cMap.put("sum_balance_dis", sum_balance_dis);
					String end_date_dis= e.element("end_date_dis").getTextTrim();
					cMap.put("end_date_dis", end_date_dis);
					String brdis_cus_name= e.element("brdis_cus_name").getTextTrim();
					cMap.put("brdis_cus_name", brdis_cus_name);
					DisAppDetailsList.add(cMap);
				 }
				 }
				 map.put("LmtAppDetails", LmtAppDetailsList);
				 map.put("PledgeMssJournalAcc", PledgeMssJournalAccList);
				 map.put("DisAppDetails", DisAppDetailsList);
			     map.put("balance",crdlmtbalance);
			 dataList.add(map);
				 return dataList;
			 }
			}catch(Exception e){
				e.printStackTrace();
				throw new BizException(1,0,"0000","Warning-169：数据信息同步失败，请及时联系IT部门！");
			}

		return null;

	  }  

	
	 }

