package com.yuchengtech.bcrm.customer.phoneMkt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.phoneMkt.model.OcrmFMmTelMain;
import com.yuchengtech.bcrm.customer.phoneMkt.service.OcrmFMmTelMainService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 通话信息处理  luyy
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFMmTelMain")
public class OcrmFMmTelMainAction  extends CommonAction{
    @Autowired
    private  OcrmFMmTelMainService  ocrmFMmTelMainService;
    @Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
    
    @Autowired
	public void init(){
	  	model = new OcrmFMmTelMain(); 
		setCommonService(ocrmFMmTelMainService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}

    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
    
    public void prepare(){
    	 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String custId = request.getParameter("custId");
//    	 String frId = (String)auth.getUnitInfo().get("FR_ID");
    	 StringBuilder sb=new StringBuilder();
    		 sb.append("select s.*,to_char(s.STAET_TIME,'yyyy-mm-dd hh24:mi:ss') as TIME_S,to_char(s.end_time,'yyyy-mm-dd hh24:mi:ss') as TIME_E," +
    		 		"f.F_VALUE as BIS_TYPE_name,a.user_name,fi.physical_address  from OCRM_F_MM_TEL_MAIN s " +
    		 		"left join OCRM_SYS_LOOKUP_ITEM f on s.BIS_TYPE = f.F_CODE and f.F_LOOKUP_ID like 'BIS_TYPE_%' " +
    		 		"left join OCRM_F_WP_ANNEXE fi on fi.relation_info = to_char(s.id),admin_auth_account a" +
    		 		" where a.account_name = s.user_id  ");
    	if(custId!=null&&!"".equals(custId))
    		 sb.append(" and s.cust_Id='"+custId+"'");
    	else
    		sb.append(" and 1=2 ");
//    	//增加法人权限控制
//    	if(frId != null && !"".equals(frId)){
//  			sb.append(" and b.FR_ID = '"+frId+"'");
//  		}
//    	
    	setPrimaryKey("s.id desc");
    	addOracleLookup("TEL_TYPE", "TEL_TYPE");
    	addOracleLookup("FOLLOW_DO", "FOLLOW_DO");
		SQL = sb.toString();
		datasource = ds;
	}
	
    
    public DefaultHttpHeaders saveData(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String operate = request.getParameter("operate");
    	if("outadd".equals(operate)||"inadd".equals(operate)){//表示呼出新增
    		
    		((OcrmFMmTelMain) model).setUserId(auth.getUserId());
    		((OcrmFMmTelMain) model).setStaetTime(new Timestamp(System.currentTimeMillis()));
//    		((OcrmFMmTelMain) model).setFrId(auth.getUnitInfo().get("FR_ID").toString());
        	ocrmFMmTelMainService.save(model);
        	
        	JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
    		auth.setPid(metadataUtil.getId(model).toString());//获取新增操作产生的最新记录的ID
    	}else if("update".equals(operate)){//通话内容处理
    		String phoneId = request.getParameter("phoneId");//记录id
    		OcrmFMmTelMain info = (OcrmFMmTelMain)ocrmFMmTelMainService.find(Long.parseLong(phoneId));
    		info.setTelType(((OcrmFMmTelMain) model).getTelType());
    		info.setBisType(((OcrmFMmTelMain) model).getBisType());
    		info.setFollowDo(((OcrmFMmTelMain) model).getFollowDo());
    		info.setContent(((OcrmFMmTelMain) model).getContent());
    		
    		ocrmFMmTelMainService.save(info);
    		
    	}else if("timeUpdate".equals(operate)){//时间处理
    		String phoneId = request.getParameter("phoneId");//记录id
    		OcrmFMmTelMain info = (OcrmFMmTelMain)ocrmFMmTelMainService.find(Long.parseLong(phoneId));
			
    		String sTime = request.getParameter("sTime");//开始时间（如果没有重播，这位空）
    		String eTime = request.getParameter("eTime");//结束时间
    		
    		if(!"".equals(sTime)){
    			Timestamp st = Timestamp.valueOf(sTime);
    			info.setStaetTime(st);
    		}
    		Timestamp et = Timestamp.valueOf(eTime);
    		info.setEndTime(et);
    		info.setTimeLong(((OcrmFMmTelMain)model).getTimeLong());//通话时间
    		
    		ocrmFMmTelMainService.save(info);
    		
    	}else if("updateCust".equals(operate)){//切换为对公客户
    		String phoneId = request.getParameter("phoneId");//记录id
    		OcrmFMmTelMain info = (OcrmFMmTelMain)ocrmFMmTelMainService.find(Long.parseLong(phoneId));
    		String custId = request.getParameter("custId");
    		info.setCustId(custId);
    		
    		ocrmFMmTelMainService.save(info);
    	}
    	
    	return new DefaultHttpHeaders("success").setLocationId(((OcrmFMmTelMain) model).getId());
    }
    
//    //文件对象
//    private static InputStream inputStream = null;
//    //服务器端播放文件
//	public void play(){ 
//    	ActionContext ctx = ActionContext.getContext();
//    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//    	String file = request.getParameter("file");
//    	String path = FileTypeConstance.getUploadPath();
//    	file = "PhoneOcx.wav";
//    	try{
//    		inputStream = new FileInputStream(new File(path,file)); 
//        	AudioPlayer.player.start(inputStream);
//    	}catch(Exception ee){
//    		ee.printStackTrace();
//    	}
//    	} 
//	
//	//服务器端停止播放
//	public void stop(){
//		if(inputStream!=null)
//			AudioPlayer.player.stop(inputStream); 
//	}
    
   //将音频文件复制到/contents/pages/customer/customerManager/mktPhone下，以供页面播放
    public HttpHeaders copyFile() throws IOException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String exit = "no";//文件是否存在
    	String name = request.getParameter("file");//文件名
    	
    	
    	String path = FileTypeConstance.getUploadPath();//上传文件路径
    	File file= new File(path,name);
    	if(file.exists()){
    		exit = "yes";
    		//复制文件
    		FileInputStream  inStream = null;
    		FileOutputStream fs = null;
    		try{
	    		String realPath = request.getSession().getServletContext().getRealPath("");
	    		realPath = realPath + "\\contents\\pages\\customer\\customerManager\\mktPhone";
	    		inStream = new FileInputStream(file); //读入原文件                
	    		fs = new FileOutputStream(realPath+"\\"+name); 
	    		 byte[] b = new byte[1024 * 5]; 
	    		 int len; 
	    		 while ( (len = inStream.read(b)) != -1) {
	    			 fs.write(b, 0, len);                    
	    		}  
	    		 fs.flush();
	    		 fs.close(); 
	    		 inStream.close(); 
    		}catch(Exception ee){
    			ee.printStackTrace();
    		}finally{
    			if(fs!=null)
    				fs.close(); 
    			if(inStream!=null)
    				inStream.close(); 
    		}
    		
    	}
    	
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		response.getWriter().write(exit);
		response.getWriter().flush();
    	return new DefaultHttpHeaders("success").disableCaching();
    }
    //删除复制到/contents/pages/customer/customerManager/mktPhone的音频文件
    public HttpHeaders deleteFile() throws IOException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String name = request.getParameter("file");//文件名
    	
    	String realPath = request.getSession().getServletContext().getRealPath("");
		realPath = realPath + "\\contents\\pages\\customer\\customerManager\\mktPhone";
		File file= new File(realPath,name);
		if(file.exists()){
			file.delete();//删除文件
		}
    	
    	return new DefaultHttpHeaders("success").disableCaching();
    }
	/**
	 * 根据号码匹配客户，返回格式  是否我行客户#是否对私客户#对私客户id#对私客户name#是否对公联系人#对公客户id#对公客户name#是否对公客户#对公客户id#对公客户name
	 * @param num:电话号码
	 * @return
	 * @throws IOException 
	 */
    public HttpHeaders checkNum() throws IOException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String num = request.getParameter("num");
    	boolean ifCust = false;
    	boolean ifIndiv = false;
    	boolean ifContact = false;
    	boolean ifCom = false;
    	String custType = "";
    	String custIndiv = "custIndiv";
    	String custIndivName = "custIndivName";
    	String custContact = "custContact";
    	String custContactName = "custContactName";
    	String custCom = "custCom";
    	String custComName = "custComName";
    	List<Object[]> list1 = ocrmFMmTelMainService.getBaseDAO().findByNativeSQLWithIndexParam(" select cust_id,cust_zh_name,cust_typ from  OCRM_F_CI_CUST_DESC" +
    			" where TELEPHONE_NUM='"+num+"' or OFFICE_PHONE='"+num+"' or LINK_PHONE = '"+num+"'");
    	
    	if (list1 != null && list1.size() > 0) {
			Object[] o = list1.get(0);
			custType = o[2].toString();
			if("2".equals(custType)){//对私
				ifIndiv = true;
				custIndiv = o[0].toString();
				custIndivName = o[1].toString();
				//判断是否为对公联系人
				List<Object[]> list2 = ocrmFMmTelMainService.getBaseDAO().findByNativeSQLWithIndexParam(" select cust_id,cust_zh_name from OCRM_F_CI_CUST_DESC " +
						" where  cust_typ=2 and link_user='"+custIndivName+"'");
				if (list2 != null && list2.size() > 0) {
					Object[] oo = list2.get(0);
					ifContact = true;
					custContact = oo[0].toString();
			    	custContactName = oo[1].toString();
				}
				
			}else if("1".equals(custType)){//对公客户
				ifCom = true;
				custCom = o[0].toString();
				custComName = o[1].toString();
			}
    	}
    	
    	ifCust = ifIndiv||ifContact||ifCom;
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(ifCust+"#"
				+ifIndiv+"#"+custIndiv+"#"+custIndivName+"#"
				+ifContact+"#"+custContact+"#"+custContactName+"#"
				+ifCom+"#"+custCom+"#"+custComName);
		response.getWriter().flush();
    	return new DefaultHttpHeaders("success").disableCaching();
    }
    
    public void changeNum(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String num = request.getParameter("num");
    	String custId = request.getParameter("custId");
    	
    	String jql="update OcrmFCiCustDesc C set c.linkPhone=:linkPhone where c.custId='"+custId+"'";
		Map<String,Object> values=new HashMap<String,Object>();
		values.put("linkPhone", num);
		ocrmFMmTelMainService.batchUpdateByName(jql, values);
    	
    }
    /**
     * 更新联系方式
     */
    public void updateNum(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String nums = request.getParameter("num");
    	String custId = request.getParameter("custId");
    	String num[] = nums.split("#");
    	String jql="";
    	
    	if(!"empty".equals(num[0])){
    		jql  ="update OcrmFCiCustDesc C set  c.telephoneNum=:telephoneNum  where c.custId='"+custId+"'";
    		Map<String,Object> values=new HashMap<String,Object>();
    		values.put("telephoneNum", num[0]);
    		ocrmFMmTelMainService.batchUpdateByName(jql, values);
    	}
    	if(!"empty".equals(num[1])){
    		jql  ="update OcrmFCiCustDesc C set  c.officePhone=:officePhone  where c.custId='"+custId+"'";
    		Map<String,Object> values=new HashMap<String,Object>();
    		values.put("officePhone", num[1]);
    		ocrmFMmTelMainService.batchUpdateByName(jql, values);
    	}
    	if(!"empty".equals(num[2])){
    		jql  ="update OcrmFCiCustDesc C set  c.linkPhone=:linkPhone  where c.custId='"+custId+"'";
    		Map<String,Object> values=new HashMap<String,Object>();
    		values.put("linkPhone", num[2]);
    		ocrmFMmTelMainService.batchUpdateByName(jql, values);
    	}
    	
    }
}