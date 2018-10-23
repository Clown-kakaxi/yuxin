package com.yuchengtech.emp.biappframe.passwd;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdHisInfo;
import com.yuchengtech.emp.biappframe.passwd.service.PwdHisBS;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserBS;


/**
 * <pre>
 * Title: 保存历史密码的默认策略
 * Description: 保存历史密码的默认策略
 * </pre>
 * 
 * @author liucheng liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
public class DeftPwdSavaHisStrategy implements IPwdSavaHisStrategy {

	@Autowired
	private UserBS userBS;
	
	@Autowired
	private PwdHisBS pwdHisBS;
	
	public String saveHis(String userId, String pwdCrypt) {
		
		BioneUserInfo userInfo = this.userBS.getEntityById(userId);
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		String pwd_old = userInfo.getUserPwd();
		BionePwdHisInfo pwdHis = new BionePwdHisInfo();
		pwdHis.setPwdHisId(java.util.UUID.randomUUID().toString().replace("-", ""));
		pwdHis.setUserPwd(pwd_old);
		pwdHis.setUserId(userId);
		pwdHis.setBackupDesc("user_curpwd");
		pwdHis.setCreateTime(now);
		pwdHisBS.saveEntity(pwdHis);
		
		return STATUS_NORMAL;
	}
	
	public boolean isPwdValid(String userId, String pwdCrypt) {
		// 暂无校验规则
		return true;
	}

}
