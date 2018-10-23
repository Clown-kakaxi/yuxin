package com.yuchengtech.emp.ecif.passwd;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.passwd.IPwdSavaHisStrategy;
import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdHisInfo;
import com.yuchengtech.emp.biappframe.passwd.service.PwdHisBS;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserBS;


/**
 * <pre>
 * Title: Hrb Ecif 系统中保存历史密码的策略
 * Description: Hrb Ecif 系统中保存历史密码的策略
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
public class HrbEcifPwdSavaHisStrategy implements IPwdSavaHisStrategy {

	@Autowired
	private UserBS userBS;
	
	@Autowired
	private PwdHisBS pwdHisBS;
	
	
	@Transactional
	public String saveHis(String userId, String pwdCrypt) {
		
		BioneUserInfo userInfo = this.userBS.getEntityById(userId);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String pwd_old = userInfo.getUserPwd();
		// 判断是否与当前使用中的密码相同
		if(pwd_old.equals(pwdCrypt)) {
			return STATUS_SAME_AS_CURRENT;
		}
		// 查询历史记录：
		List<BionePwdHisInfo> hisList = this.pwdHisBS.findEntityListByPropertyAndOrder("userId", userId, "createTime", true);
		if(hisList!=null && !hisList.isEmpty()) {
			int i = 0;
			for(BionePwdHisInfo pwdInfo : hisList) {
				if(i<2 && pwdInfo.getUserPwd().equals(pwdCrypt)) {
					return STATUS_SAME_AS_HIS;
				}
				if(i>=2) {
					pwdHisBS.removeEntity(pwdInfo);
				}
				i ++;
			}
		}
		// 把当前的密码保存到历史信息；
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
		BioneUserInfo userInfo = this.userBS.getEntityById(userId);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String pwd_old = userInfo.getUserPwd();
		// 判断是否与当前使用中的密码相同
		if(pwd_old.equals(pwdCrypt)) {
			return false;
		}
		// 查询历史记录：
		List<BionePwdHisInfo> hisList = this.pwdHisBS.findEntityListByPropertyAndOrder("userId", userId, "createTime", true);
		if(hisList!=null && !hisList.isEmpty()) {
			int i = 0;
			for(BionePwdHisInfo pwdInfo : hisList) {
				if(i<2 && pwdInfo.getUserPwd().equals(pwdCrypt)) {
					return false;
				}
				i ++;
			}
		}
		return true;
	}

}
