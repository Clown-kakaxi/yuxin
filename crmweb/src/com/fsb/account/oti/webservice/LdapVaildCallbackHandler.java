/**
 * LdapVaildCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.fsb.account.oti.webservice;

/**
 * LdapVaildCallbackHandler Callback class, Users can extend this class and
 * implement their own receiveResult and receiveError methods.
 */
public abstract class LdapVaildCallbackHandler {

	protected Object clientData;

	/**
	 * User can pass in any object that needs to be accessed once the
	 * NonBlocking Web service call is finished and appropriate method of this
	 * CallBack is called.
	 * 
	 * @param clientData
	 *            Object mechanism by which the user can pass in user data that
	 *            will be avilable at the time this callback is called.
	 */
	public LdapVaildCallbackHandler(Object clientData) {
		this.clientData = clientData;
	}

	/**
	 * Please use this constructor if you don't want to set any clientData
	 */
	public LdapVaildCallbackHandler() {
		this.clientData = null;
	}

	/**
	 * Get the client data
	 */

	public Object getClientData() {
		return clientData;
	}

	/**
	 * auto generated Axis2 call back method for getHashTicket method override
	 * this method for handling normal response from getHashTicket operation
	 */
	public void receiveResultgetHashTicket(
			com.fsb.account.oti.webservice.LdapVaildStub.GetHashTicketResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getHashTicket operation
	 */
	public void receiveErrorgetHashTicket(java.lang.Exception e) {
	}

	// No methods generated for meps other than in-out

	// No methods generated for meps other than in-out

	// No methods generated for meps other than in-out

	/**
	 * auto generated Axis2 call back method for simpleAuthLogin method override
	 * this method for handling normal response from simpleAuthLogin operation
	 */
	public void receiveResultsimpleAuthLogin(
			com.fsb.account.oti.webservice.LdapVaildStub.SimpleAuthLoginResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from simpleAuthLogin operation
	 */
	public void receiveErrorsimpleAuthLogin(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getLadpAuth method override
	 * this method for handling normal response from getLadpAuth operation
	 */
	public void receiveResultgetLadpAuth(
			com.fsb.account.oti.webservice.LdapVaildStub.GetLadpAuthResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getLadpAuth operation
	 */
	public void receiveErrorgetLadpAuth(java.lang.Exception e) {
	}

	// No methods generated for meps other than in-out

	/**
	 * auto generated Axis2 call back method for getDesDecryptStrArray method
	 * override this method for handling normal response from
	 * getDesDecryptStrArray operation
	 */
	public void receiveResultgetDesDecryptStrArray(
			com.fsb.account.oti.webservice.LdapVaildStub.GetDesDecryptStrArrayResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getDesDecryptStrArray operation
	 */
	public void receiveErrorgetDesDecryptStrArray(java.lang.Exception e) {
	}

	// No methods generated for meps other than in-out

	/**
	 * auto generated Axis2 call back method for getPwdWrongTimes method
	 * override this method for handling normal response from getPwdWrongTimes
	 * operation
	 */
	public void receiveResultgetPwdWrongTimes(
			com.fsb.account.oti.webservice.LdapVaildStub.GetPwdWrongTimesResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getPwdWrongTimes operation
	 */
	public void receiveErrorgetPwdWrongTimes(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getUserAuthBoolean method
	 * override this method for handling normal response from getUserAuthBoolean
	 * operation
	 */
	public void receiveResultgetUserAuthBoolean(
			com.fsb.account.oti.webservice.LdapVaildStub.GetUserAuthBooleanResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getUserAuthBoolean operation
	 */
	public void receiveErrorgetUserAuthBoolean(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getFSBPassportAuth method
	 * override this method for handling normal response from getFSBPassportAuth
	 * operation
	 */
	public void receiveResultgetFSBPassportAuth(
			com.fsb.account.oti.webservice.LdapVaildStub.GetFSBPassportAuthResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getFSBPassportAuth operation
	 */
	public void receiveErrorgetFSBPassportAuth(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getDesDecryptStr method
	 * override this method for handling normal response from getDesDecryptStr
	 * operation
	 */
	public void receiveResultgetDesDecryptStr(
			com.fsb.account.oti.webservice.LdapVaildStub.GetDesDecryptStrResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getDesDecryptStr operation
	 */
	public void receiveErrorgetDesDecryptStr(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for qryStat method override this
	 * method for handling normal response from qryStat operation
	 */
	public void receiveResultqryStat(
			com.fsb.account.oti.webservice.LdapVaildStub.QryStatResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from qryStat operation
	 */
	public void receiveErrorqryStat(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getPwdFailTimes method override
	 * this method for handling normal response from getPwdFailTimes operation
	 */
	public void receiveResultgetPwdFailTimes(
			com.fsb.account.oti.webservice.LdapVaildStub.GetPwdFailTimesResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getPwdFailTimes operation
	 */
	public void receiveErrorgetPwdFailTimes(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for syncUsrFromOA method override
	 * this method for handling normal response from syncUsrFromOA operation
	 */
	public void receiveResultsyncUsrFromOA(
			com.fsb.account.oti.webservice.LdapVaildStub.SyncUsrFromOAResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from syncUsrFromOA operation
	 */
	public void receiveErrorsyncUsrFromOA(java.lang.Exception e) {
	}

	// No methods generated for meps other than in-out

	// No methods generated for meps other than in-out

	/**
	 * auto generated Axis2 call back method for checkInsrt method override this
	 * method for handling normal response from checkInsrt operation
	 */
	public void receiveResultcheckInsrt(
			com.fsb.account.oti.webservice.LdapVaildStub.CheckInsrtResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from checkInsrt operation
	 */
	public void receiveErrorcheckInsrt(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getDesEncryptStr method
	 * override this method for handling normal response from getDesEncryptStr
	 * operation
	 */
	public void receiveResultgetDesEncryptStr(
			com.fsb.account.oti.webservice.LdapVaildStub.GetDesEncryptStrResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getDesEncryptStr operation
	 */
	public void receiveErrorgetDesEncryptStr(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getUserAuth method override
	 * this method for handling normal response from getUserAuth operation
	 */
	public void receiveResultgetUserAuth(
			com.fsb.account.oti.webservice.LdapVaildStub.GetUserAuthResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getUserAuth operation
	 */
	public void receiveErrorgetUserAuth(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getHashAuth method override
	 * this method for handling normal response from getHashAuth operation
	 */
	public void receiveResultgetHashAuth(
			com.fsb.account.oti.webservice.LdapVaildStub.GetHashAuthResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getHashAuth operation
	 */
	public void receiveErrorgetHashAuth(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getSSOAuth method override this
	 * method for handling normal response from getSSOAuth operation
	 */
	public void receiveResultgetSSOAuth(
			com.fsb.account.oti.webservice.LdapVaildStub.GetSSOAuthResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getSSOAuth operation
	 */
	public void receiveErrorgetSSOAuth(java.lang.Exception e) {
	}

}
