/**
 * 
 */
package com.yuchengtech.emp.biappframe.authobj.web.vo;

import java.io.Serializable;

/**
 * 
 * 
 * <pre>
 * Title:
 * Description:
 * </pre>
 * 
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class BioneLogInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String logNo;

	private String logEvent;

	private String logicSysNo;

	private String occurTime;

	private String operUser;

	private String loginIP;

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getLogEvent() {
		return logEvent;
	}

	public void setLogEvent(String logEvent) {
		this.logEvent = logEvent;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

}
