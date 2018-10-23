/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����EcifLisenceManager.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:53:56
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchenglicense.LicenseVerify;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�EcifLisenceManager
 * @��������ECIF lisence������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:53:56   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:53:56
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CrmLisenceManager {
	
	//Ĭ�ϲ��ÿ���ģʽ
	//#lisence���Ƽ���DEV:����ģʽ�������κ�Լ������DEB:DEBUGģʽ������ģʽ������ֻ���棩��DEP:����ģʽ����ʽ����ģʽ������������ʧ�ܣ�
	private String lisenceModel = "DEV";
	
	private String licenseFile="";
	
	boolean verified = false;
	
	private LicenseVerify licenseVerify;
	
	public static String LISENCE_DEV = "DEV";
	public static String LISENCE_DEB = "DEB";
	public static String LISENCE_DEP = "DEP"; 
	
	public static String LISENCE_ERROR = "License��Ч���ѹ��ڣ�����ϵECIF��Ʒ���Ļ�ȡ����License�ļ�";
	
	public static String PRODUCT_DEV_INFO = "�����׳ϿƼ����޹�˾(YC.ECIF�����汾)";
	public static String PRODUCT_UNREG_INFO = "�����׳ϿƼ����޹�˾(YC.ECIFδע���)";
	
	private Logger log = LoggerFactory.getLogger(CrmLisenceManager.class);
	
	private static CrmLisenceManager instance;
	
	public static CrmLisenceManager getInstance(){
		if(null == instance){
			instance = new CrmLisenceManager();
		}
		return instance;
	}
	
	public void initialize() throws Exception{
		if(LISENCE_DEV.equals(lisenceModel)){
			verified = true;
			return;
		}else {
			verified = null!=licenseFile && !licenseFile.equals("") && verifyLicense();
			if(LISENCE_DEB.equals(lisenceModel) && !verified){
				log.warn(LISENCE_ERROR);
				return;
			}else if(LISENCE_DEP.equals(lisenceModel) && !verified){
				log.warn(LISENCE_ERROR);
				throw new Exception(LISENCE_ERROR);
			}
		}
	}
	
	/**
	 * ���lisence�Ϸ�״̬
	 * @return
	 */
	private boolean verifyLicense(){
		try {
			// ��Ҫ��License�ļ����õ����ʵ�·���£�License�ļ�������Ϊ��10λ���-1λLicense���ͱ��-10λ��Ʒ���.lic��
			String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
			licenseFile = path + licenseFile;
			licenseVerify = new LicenseVerify(licenseFile);
			// ͨ���ȶ�License�ļ�У������Ա���ǰϵͳʱ�䣬�ж�License�ļ��Ƿ���Ч
			if (!licenseVerify.licenseValid()) {
				log.info(licenseVerify.getErrorMessage());
				return false;
			}

			// �������÷���
			// �ж�License���֤�Ƿ���ڣ�������ڷ���true
			else if(licenseVerify.licenseExpired()){
				log.info(licenseVerify.getErrorMessage());
				return false;
			}
				
			// �ж�License���֤�Ƿ�δ������ʱ�䣬���δ������ʱ�䷵��true
			else if(licenseVerify.licenseEarlierThanStartDate()){
				log.info(licenseVerify.getErrorMessage());
				return false;
			}else{
				// �������õ�License��Ϣ��������ʾ��Ȩ���������з���ֵ��ΪString����
				// ���֤����ʱ��
				log.info("���֤����ʱ��:"+licenseVerify.getBuildTime());
				// ���֤���
				log.info("���֤���:"+licenseVerify.getLicenseCode());
				// ��Ʒ���
				log.info("��Ʒ���:"+licenseVerify.getProductCode());
				// ��Ʒ��������
				log.info("��Ʒ��������:"+licenseVerify.getProductNameZh());
				// ��ƷӢ����д���ƣ���д��ĸ��
				log.info("��ƷӢ����д����:"+licenseVerify.getProductNameEn());
				// ��Ʒ���汾��
				log.info("��Ʒ���汾��:"+licenseVerify.getVersionMajor());
				// ��Ʒ�Ӱ汾��
				log.info("��Ʒ�Ӱ汾��:"+licenseVerify.getVersionSub());
				// ��Ʒ������
				log.info("��Ʒ������:"+licenseVerify.getVersionPublish());
				// ��Ʒ��������
				log.info("��Ʒ��������:"+licenseVerify.getComplieDate());
				// ��Ʒ���֤����
				log.info("��Ʒ���֤����:"+licenseVerify.getLicenseType());
				// ��Ʒ�ͻ�ʹ������
				log.info("��Ʒ�ͻ�ʹ������:"+licenseVerify.getCustomerName());
				// ��Ʒ���ۺ�ͬ��
				log.info("��Ʒ���ۺ�ͬ��:"+licenseVerify.getContractCode());
				// ��ƷLicense���֤��Ч��ʼ����
				log.info("��ʼ����:"+licenseVerify.getLicenseStartTime());
				// ��ƷLicense���֤��Ч��ֹ����
				log.info("��ֹ����:"+licenseVerify.getLicenseEndTime());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * ��ȡ��Ȩ����
	 * @return
	 */
	public String getCustomerName(){
		
		if(LISENCE_DEV.equals(lisenceModel)){
			return PRODUCT_DEV_INFO;
		}else if(LISENCE_DEB.equals(lisenceModel)){
			if(null != licenseVerify && verified)
				return licenseVerify.getCustomerName();
			else 
				return PRODUCT_UNREG_INFO;
		}else{
			return PRODUCT_UNREG_INFO;
		}
	}
	
	/**
	 * ��ȡ��Ʒ�汾��
	 * @return
	 */
	public String getVersion(){
		if(null != licenseVerify){
			return licenseVerify.getVersionMajor()+"."
			+licenseVerify.getVersionSub()+"."
			+ licenseVerify.getVersionPublish();
		}else return "";
	}
	
	private CrmLisenceManager(){
	}

	public String getLisenceModel() {
		return lisenceModel;
	}

	public void setLisenceModel(String lisenceModel) {
		this.lisenceModel = lisenceModel;
	}

	public String getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	
	
}
