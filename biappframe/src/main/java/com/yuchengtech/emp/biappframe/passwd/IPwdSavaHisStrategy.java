package com.yuchengtech.emp.biappframe.passwd;

/**
 * 关于密码保存策略的接口
 * 
 * @author charles
 * 
 */
public interface IPwdSavaHisStrategy {

	public static String STATUS_NORMAL = "00";

	public static String STATUS_SAME_AS_CURRENT = "01";

	public static String STATUS_SAME_AS_HIS = "02";

	/**
	 * 保存历史密码
	 * @param userId
	 * @param pwdCrypt
	 * @return
	 */
	public String saveHis(String userId, String pwdCrypt);

	/**
	 * 验证新密码是否符合规则
	 * @param userId
	 * @param pwdCrypt
	 * @return
	 */
	public boolean isPwdValid(String userId, String pwdCrypt);

}
