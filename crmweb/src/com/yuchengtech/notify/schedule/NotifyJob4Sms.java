package com.yuchengtech.notify.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yuchengtech.bcrm.sales.model.OcrmFWpRemind;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.notify.client.SmsClient;

public class NotifyJob4Sms implements Job {
	private SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date d = new Date();
	private String returnstr = DateFormat.format(d);
	private String contmethType4Sms;
	private JPABaseDAO dao;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println(returnstr + "★★★★★★★★★★★");

		/*
		 * select from OcrmFWpRemind
		 */
		OcrmFWpRemind remind = null;

		/*
		 * process OcrmFWpRemind data
		 */
		// if byte length greater, log error

		// select telNo from AcrmFCiContmeth by custId
		String sql = "select t from AcrmFCiContmeth where custId=? and contmethType=?";
		List<String> telNo_s = dao.findByNativeSQLWithIndexParam(sql, remind.getCustId(), contmethType4Sms);

		// if time > current time, set send time as current time or null
		// else, set send time as selected time
		String sendTime = null;
		/*
		 * send message to SMS
		 */
		if (remind != null) {
			String msg = remind.getMsgRemark();
			try {
				// TODO: 需要改造为调用短信平台批量发送短信功能
				for (String telNo : telNo_s) {
					SmsClient.getInstance().sendMsg(telNo, msg, (String)NotifyJob4Sms.getObjEmpty(sendTime));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (e instanceof TimeoutException) {

				}
			}
		}
	}
	public static void main(String[] args) {
	}
	
	public static boolean isObjEmpty(Object obj){
		return (null == obj) || "".equals(obj);
	}
	
	public static Object getObjEmpty(Object obj){
		return (null == obj) || "".equals(obj)?"":obj;
	}
	
}
