package com.yuchengtech.bcrm.action;

import com.fsb.account.oti.webservice.LdapVaildStub;
import com.yuchengtech.bob.common.CommonAction;

public class SingleSignonAction extends CommonAction {

	public boolean onCheck(String userid, String hashtk) {
		boolean flag = false;
//		 String userid = request.getParameter("userid");
//		 String hashtk = request.getParameter("hashtk");
		try {

			LdapVaildStub lvs = new LdapVaildStub();

			LdapVaildStub.GetHashAuth lvsgha = new LdapVaildStub.GetHashAuth();

			lvsgha.setLoginid(userid);

			lvsgha.setHashcod(hashtk);

			LdapVaildStub.GetHashAuthResponse gap = lvs.getHashAuth(lvsgha);

			String[] arg = new String[2];

			arg = gap.get_return();

			String strRtn = arg[0];

			if (strRtn.equals("true")) {
				flag = true;
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return flag;
	}
}
