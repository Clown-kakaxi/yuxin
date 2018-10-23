package com.yuchengtech.emp.ecif.alarm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.util.PropertiesUtils;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmConfVO;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmEmailVO;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmInfoUserVO;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmInfoVO;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;

@Service
public class TxAlarmInfoBS extends BaseBS<T> {
	protected static Logger log = LoggerFactory.getLogger(TxAlarmInfoBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	/**
	 * 获取待发送的报警对象
	 * @return
	 */
	@Transactional(readOnly = false)	
	public TxAlarmInfoVO[] queryTxAlarmInfoList(){
			
		//找出待发送的报警信息
		StringBuffer sql = new StringBuffer("");
//		sql.append(" SELECT ALARM_INFO_ID,ALARM_SYS,ALARM_MODULE,ERROR_CODE,ALARM_LEVEL,ALARM_INFO,OCCUR_DATE,OCCUR_TIME,SRC_SYS_CD,ALARM_STAT FROM　TX_ALARM_INFO　WHERE ALARM_STAT ='0' FOR UPDATE ");
		sql.append(" SELECT ALARM_INFO_ID,ALARM_SYS,ALARM_MODULE,ERROR_CODE,ALARM_LEVEL,ALARM_INFO,OCCUR_DATE,OCCUR_TIME,SRC_SYS_CD,ALARM_STAT FROM　TX_ALARM_INFO　WHERE ALARM_STAT ='0'  ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		
		TxAlarmInfoVO[] txAlarmInfoVOs = null;
		try {
			txAlarmInfoVOs = this.resultUtil.listObjectsToEntityBeans(list, TxAlarmInfoVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		if(txAlarmInfoVOs==null)
			return null;
		
		for(TxAlarmInfoVO info: txAlarmInfoVOs){
			//对每个报警系统、报警模块、报警级别，根据报警配置找出报警对象(用户)
			processAlarmInfo(info);
		}
		
		return txAlarmInfoVOs;
	}
	
	
	public void processAlarmInfo(TxAlarmInfoVO info){
		
				
		//将用户和组的邮件信息选择出来
		List<TxAlarmInfoUserVO> infoUserList = new ArrayList<TxAlarmInfoUserVO>();
		
		StringBuffer sqlSelect = new StringBuffer("");
		
		sqlSelect.append(" select u.USER_ID,u.user_Name,u.user_Title,u.login_Name,cont.CONTMETH_TYPE,cont.CONTMETH_INFO,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
		sqlSelect.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont "); 
		sqlSelect.append(" where u.USER_ID=conf.ALARM_OBJECT_ID AND u.USER_ID=cont.USER_ID and conf.ALARM_METHOD=cont.CONTMETH_TYPE and conf.ALARM_OBJECT_TYPE='1' " );		//人员
		sqlSelect.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
		sqlSelect.append(" and info.ALARM_SYS ='"+ info.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ info.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ info.getAlarmLevel()+"'");
		sqlSelect.append(" and info.ALARM_STAT='0'");
		
		List<Object[]> usermaillist = this.baseDAO.findByNativeSQLWithIndexParam(sqlSelect.toString());
		
		TxAlarmInfoUserVO[] txAlarmInfoUserVOs = null;
		try {
			txAlarmInfoUserVOs = this.resultUtil.listObjectsToEntityBeans(usermaillist, TxAlarmInfoUserVO.class, sqlSelect.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(txAlarmInfoUserVOs!=null){
			for(TxAlarmInfoUserVO vo:txAlarmInfoUserVOs){
				infoUserList.add(vo);
			}
		}
		
		sqlSelect = new StringBuffer("");
		sqlSelect.append(" select u.USER_ID,u.user_Name,u.user_Title,cont.CONTMETH_TYPE,cont.CONTMETH_INFO,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
		sqlSelect.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont "); 
		sqlSelect.append(" where u.USER_ID=conf.ALARM_OBJECT_ID AND u.USER_ID=cont.USER_ID and conf.ALARM_METHOD=cont.CONTMETH_TYPE and conf.ALARM_OBJECT_TYPE='0' " );		//分组
		sqlSelect.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
		sqlSelect.append(" and info.ALARM_SYS ='"+ info.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ info.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ info.getAlarmLevel()+"'");
		sqlSelect.append(" and info.ALARM_STAT='0'");
		
		usermaillist = this.baseDAO.findByNativeSQLWithIndexParam(sqlSelect.toString());
		
		txAlarmInfoUserVOs = null;
		try {
			txAlarmInfoUserVOs = this.resultUtil.listObjectsToEntityBeans(usermaillist, TxAlarmInfoUserVO.class, sqlSelect.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(txAlarmInfoUserVOs!=null){
			for(TxAlarmInfoUserVO vo:txAlarmInfoUserVOs){
				infoUserList.add(vo);
			}
		}
		
		//处理
		if(infoUserList==null)
			return;
		for(TxAlarmInfoUserVO vo:infoUserList){
			
			if(vo.getContmethType().equals("0")){			//网页
				StringBuffer sqlInsert = new StringBuffer("");
				sqlInsert.append(" insert into TX_ALARM_WEB(ALARM_ID,USER_ID,USER_NAME,USER_TITLE,LOGIN_NAME,ALARM_SYS,ALARM_MODULE,ERROR_CODE,ALARM_LEVEL,ALARM_INFO,OCCUR_DATE,OCCUR_TIME,SRC_SYS_CD,ALARM_STAT,SEND_DATE,SEND_TIME,SEND_NUM) ");
				sqlInsert.append(" values (SEQ_TX_ALARM_WEB.nextval,?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,'1',sysdate,sysdate,1)");
				
				Object[] objs = new Object[]{vo.getUserId(),vo.getUserName(),vo.getUserTitle(),vo.getLoginName(),vo.getAlarmSys(),vo.getAlarmModule(),vo.getErrorCode(),vo.getAlarmLevel(),vo.getAlarmInfo(),vo.getOccurDate(),vo.getOccurTime(),vo.getSrcSysCd()};
				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), objs).executeUpdate();
				
			}else if(vo.getContmethType().equals("1")){    //邮件
				StringBuffer sqlInsert = new StringBuffer("");
				sqlInsert.append(" insert into TX_ALARM_EMAIL(ALARM_ID,USER_ID,USER_NAME,USER_TITLE,EMAIL_ADDR,ALARM_SYS,ALARM_MODULE,ERROR_CODE,ALARM_LEVEL,ALARM_INFO,OCCUR_DATE,OCCUR_TIME,SRC_SYS_CD,ALARM_STAT,SEND_DATE,SEND_TIME,SEND_NUM) ");
				sqlInsert.append(" values (SEQ_TX_ALARM_EMAIL.nextval,?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,'1',sysdate,sysdate,1)");
				
				Object[] objs = new Object[]{vo.getUserId(),vo.getUserName(),vo.getUserTitle(),vo.getContmethInfo(),vo.getAlarmSys(),vo.getAlarmModule(),vo.getErrorCode(),vo.getAlarmLevel(),vo.getAlarmInfo(),vo.getOccurDate(),vo.getOccurTime(),vo.getSrcSysCd()};
				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), objs).executeUpdate();
				
				//发送邮件
				//MailUtils.sendTextEmail(vo.getContmethInfo(), "ecif@fubonchina.com", "ECIF系统报警", vo.getAlarmInfo());
				sendEmail(vo.getContmethInfo(), "ecif@fubonchina.com", "ECIF系统报警", vo.getAlarmInfo());

			}else if(vo.getContmethType().equals("2")){	  //短信
				StringBuffer sqlInsert = new StringBuffer("");
				sqlInsert.append(" insert into TX_ALARM_SMS(ALARM_ID,USER_ID,USER_NAME,USER_TITLE,SMS_ADDR,ALARM_SYS,ALARM_MODULE,ERROR_CODE,ALARM_LEVEL,ALARM_INFO,OCCUR_DATE,OCCUR_TIME,SRC_SYS_CD,ALARM_STAT,SEND_DATE,SEND_TIME,SEND_NUM) ");
				sqlInsert.append(" values (SEQ_TX_ALARM_SMS.nextval,?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,'1',sysdate,sysdate,1)");
				
				Object[] objs = new Object[]{vo.getUserId(),vo.getUserName(),vo.getUserTitle(),vo.getContmethInfo(),vo.getAlarmSys(),vo.getAlarmModule(),vo.getErrorCode(),vo.getAlarmLevel(),vo.getAlarmInfo(),vo.getOccurDate(),vo.getOccurTime(),vo.getSrcSysCd()};
				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), objs).executeUpdate();
			}
		}
	
		//修改报警信息状态
		StringBuffer sqlUpdate = new StringBuffer("");
		sqlUpdate.append(" update TX_ALARM_INFO set ALARM_STAT = '1' where ALARM_INFO_ID = "+ info.getAlarmInfoId());
		this.baseDAO.createNativeQueryWithIndexParam(sqlUpdate.toString(), null).executeUpdate();
		
	}
	
	
	
	public TxAlarmConfVO[] queryTxAlarmUserList(TxAlarmInfoVO vo){
		
		//对每个报警系统、报警模块、报警级别，根据报警配置找出报警对象(用户)
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT ALARM_CONF_ID,ALARM_OBJECT_TYPE,ALARM_OBJECT_ID,ALARM_SYS,ALARM_MODULE,ALARM_LEVEL,ALARM_METHOD,CONF_STAT,START_DATE,END_DATE,CREATE_TM,CREATE_USER,UPDATE_TM,UPDATE_USER" +
				" FROM　TX_ALARM_CONF　WHERE ALARM_SYS ='"+ vo.getAlarmSys()+ "' AND　ALARM_MODULE='"+ vo.getAlarmModule()+"' AND ALARM_LEVEL='"+ vo.getAlarmLevel()+"'");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		
		TxAlarmConfVO[] txAlarmConfVOs = null;
		try {
			txAlarmConfVOs = this.resultUtil.listObjectsToEntityBeans(list, TxAlarmConfVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		//处理每个报警对象
		for(TxAlarmConfVO txAlarmConfVO: txAlarmConfVOs){
			
			StringBuffer sqlInsert = new StringBuffer("");
			String alarmMethod =  txAlarmConfVO.getAlarmMethod();
			if(alarmMethod.endsWith("0")){              	   //网页
				
				//新增网页报警信息
				
				//插入用户的报警信息
				sqlInsert.append(" insert into TX_ALARM_WEB ");
				sqlInsert.append(" select SEQ_TX_ALARM_WEB.nextval, u.USER_ID,u.user_Name,u.user_Title,u.LOGIN_NAME,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
				sqlInsert.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont "); 
				sqlInsert.append(" where u.USER_ID=conf.ALARM_OBJECT_ID  AND u.USER_ID=cont.USER_ID  and conf.ALARM_OBJECT_TYPE='1' " );		//人员
				sqlInsert.append(" and cont.CONTMETH_TYPE='"+ alarmMethod +"'");
				sqlInsert.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
				sqlInsert.append(" and info.ALARM_SYS ='"+ txAlarmConfVO.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ txAlarmConfVO.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ txAlarmConfVO.getAlarmLevel()+"'");

				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), null).executeUpdate();

				//插入组的报警信息
				sqlInsert = new StringBuffer("");
				sqlInsert.append(" insert into TX_ALARM_WEB ");
				sqlInsert.append(" select SEQ_TX_ALARM_WEB.nextval,u.USER_ID,u.user_Name,u.user_Title,u.LOGIN_NAME,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
				sqlInsert.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont ,TX_ALARM_GROUP g,TX_ALARM_USER_GROUP_REL rel"); 
				sqlInsert.append(" where g.GROUP_ID=conf.ALARM_OBJECT_ID  AND u.USER_ID=cont.USER_ID and conf.ALARM_OBJECT_TYPE='0' " );	    //分组
				sqlInsert.append(" and cont.CONTMETH_TYPE='"+ alarmMethod +"'");
				sqlInsert.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
				sqlInsert.append(" and g.GROUP_ID=rel.GROUP_ID and rel.USER_ID=u.USER_ID " );
				sqlInsert.append(" and info.ALARM_SYS ='"+ txAlarmConfVO.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ txAlarmConfVO.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ txAlarmConfVO.getAlarmLevel()+"'");

				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), null).executeUpdate();

			}else if(alarmMethod.endsWith("1")){               //邮件
				
				//将用户和组的邮件信息选择出来
				List<TxAlarmEmailVO> infoUserList = new ArrayList<TxAlarmEmailVO>();
				
				StringBuffer sqlSelect = new StringBuffer("");
				
				sqlSelect.append(" select u.USER_ID,u.user_Name,u.user_Title,cont.CONTMETH_INFO,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
				sqlSelect.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont "); 
				sqlSelect.append(" where u.USER_ID=conf.ALARM_OBJECT_ID AND u.USER_ID=cont.USER_ID and conf.ALARM_OBJECT_TYPE='1' " );		//人员
				sqlSelect.append(" and cont.CONTMETH_TYPE='"+ alarmMethod +"'");
				sqlSelect.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
				sqlSelect.append(" and info.ALARM_SYS ='"+ txAlarmConfVO.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ txAlarmConfVO.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ txAlarmConfVO.getAlarmLevel()+"'");
				
				List<Object[]> usermaillist = this.baseDAO.findByNativeSQLWithIndexParam(sqlSelect.toString());
				
				TxAlarmEmailVO[] txAlarmEmailVOs = null;
				try {
					txAlarmEmailVOs = this.resultUtil.listObjectsToEntityBeans(usermaillist, TxAlarmEmailVO.class, sqlSelect.toString());
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				if(txAlarmEmailVOs!=null){
					for(TxAlarmEmailVO usermailvo:txAlarmEmailVOs){
						infoUserList.add(usermailvo);
					}
				}
				
				sqlSelect = new StringBuffer("");
				sqlSelect.append(" select u.USER_ID,u.user_Name,u.user_Title,cont.CONTMETH_INFO as EMAIL_ADDR,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
				sqlSelect.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont "); 
				sqlSelect.append(" where u.USER_ID=conf.ALARM_OBJECT_ID AND u.USER_ID=cont.USER_ID and conf.ALARM_OBJECT_TYPE='1' " );		//人员
				sqlSelect.append(" and cont.CONTMETH_TYPE='"+ alarmMethod +"'");
				sqlSelect.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
				sqlSelect.append(" and info.ALARM_SYS ='"+ txAlarmConfVO.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ txAlarmConfVO.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ txAlarmConfVO.getAlarmLevel()+"'");
				
				usermaillist = this.baseDAO.findByNativeSQLWithIndexParam(sqlSelect.toString());
				
				txAlarmEmailVOs = null;
				try {
					txAlarmEmailVOs = this.resultUtil.listObjectsToEntityBeans(usermaillist, TxAlarmEmailVO.class, sqlSelect.toString());
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				if(txAlarmEmailVOs!=null){
					for(TxAlarmEmailVO usermailvo:txAlarmEmailVOs){
						infoUserList.add(usermailvo);
					}
				}
				
				//新增邮件报警信息
				//插入用户的报警信息
				sqlInsert.append(" insert into TX_ALARM_EMAIL ");
				sqlInsert.append(" select SEQ_TX_ALARM_EMAIL.nextval, u.USER_ID,u.user_Name,u.user_Title,cont.CONTMETH_INFO as EMAIL_ADDR,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
				sqlInsert.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont "); 
				sqlInsert.append(" where u.USER_ID=conf.ALARM_OBJECT_ID AND u.USER_ID=cont.USER_ID and conf.ALARM_OBJECT_TYPE='1' " );		//人员
				sqlInsert.append(" and cont.CONTMETH_TYPE='"+ alarmMethod +"'");
				sqlInsert.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
				sqlInsert.append(" and info.ALARM_SYS ='"+ txAlarmConfVO.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ txAlarmConfVO.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ txAlarmConfVO.getAlarmLevel()+"'");

				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), null).executeUpdate();

				//插入组的报警信息
				sqlInsert = new StringBuffer("");
				sqlInsert.append(" insert into TX_ALARM_EMAIL ");
				sqlInsert.append(" select SEQ_TX_ALARM_EMAIL.nextval,u.USER_ID,u.user_Name,u.user_Title,cont.CONTMETH_INFO,info.ALARM_SYS, info.ALARM_MODULE,info.ERROR_CODE,info.ALARM_LEVEL,info.ALARM_INFO,info.OCCUR_DATE,info.OCCUR_TIME,info.SRC_SYS_CD,'1',sysdate,sysdate,1 ");
				sqlInsert.append(" from TX_ALARM_USER u,TX_ALARM_INFO info,TX_ALARM_CONF conf,TX_ALARM_USER_CONT cont ,TX_ALARM_GROUP g,TX_ALARM_USER_GROUP_REL rel"); 
				sqlInsert.append(" where g.GROUP_ID=conf.ALARM_OBJECT_ID AND u.USER_ID=cont.USER_ID  and conf.ALARM_OBJECT_TYPE='0' " );	    //分组
				sqlInsert.append(" and cont.CONTMETH_TYPE='"+ alarmMethod +"'");
				sqlInsert.append(" and conf.ALARM_SYS =info.ALARM_SYS AND　info.ALARM_MODULE=conf.ALARM_MODULE AND info.ALARM_LEVEL>=conf.ALARM_LEVEL ");
				sqlInsert.append(" and g.GROUP_ID=rel.GROUP_ID and rel.USER_ID=u.USER_ID " );
				sqlInsert.append(" and info.ALARM_SYS ='"+ txAlarmConfVO.getAlarmSys()+ "' AND　info.ALARM_MODULE='"+ txAlarmConfVO.getAlarmModule()+"' AND info.ALARM_LEVEL>='"+ txAlarmConfVO.getAlarmLevel()+"'");

				this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), null).executeUpdate();
				
				//发送邮件
				for(TxAlarmEmailVO usermailvo:infoUserList){
					//MailUtils.sendTextEmail(usermailvo.getEmailAddr(), null, "ECIF系统报警", usermailvo.getAlarmInfo());
					sendEmail(usermailvo.getEmailAddr(), "ecif@fubonchina.com", "ECIF系统报警", usermailvo.getAlarmInfo());
				}
				
				
			}else if(alarmMethod.endsWith("2")){               //短信
			
			}			
			
			
		}
		
		//修改报警信息状态
		StringBuffer sqlUpdate = new StringBuffer("");
		sqlUpdate.append(" update TX_ALARM_INFO set ALARM_STAT = '1' where ALARM_INFO_ID = "+ vo.getAlarmInfoId());
		this.baseDAO.createNativeQueryWithIndexParam(sqlUpdate.toString(), null).executeUpdate();
		
		return txAlarmConfVOs;		
	}
	
	public static void sendEmail(String sendto, String sendfrom, String subject, String text) throws MailException {

		PropertiesUtils tool = new PropertiesUtils("biappframe/email/email.properties");
		String host =  tool.getProperty("mail.host").trim();
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", host);
	//	props.put("mail.smtp.starttls.enable", "true"); 
	//	props.put("mail.smtp.ssl.trust", "192.168.2.97");
	//	props.put("javax.net.ssl.trustStore", "c:\\jssecacerts");
		
		Session session = Session.getInstance(props);
//		session.setDebug(true);
	
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(sendfrom));
			message.setSubject(subject);
			message.setText(text);
			Transport transport = session.getTransport();
			transport.connect(host, 25, null, null);
			transport.sendMessage(message, new Address[]{new InternetAddress(sendto)});
			transport.close();		
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
