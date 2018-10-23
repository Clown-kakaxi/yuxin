/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����PropertyPlaceholderConfigurerExt.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:25:40
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.ytec.mdm.base.util.crypt.EncrypDES3;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�PropertyPlaceholderConfigurerExt
 * @�����������ݿ��û����������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:25:44   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:25:44
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class PropertyPlaceholderConfigurerExt extends
		PropertyPlaceholderConfigurer {
	/**
	 * ��Map����ʽ�洢���еĲ�����Ϣ
	 */
	private static Map<String, String> ctxPropertiesMap; 
	/**
	 * The SAL t_ length.
	 * 
	 * @��������:˽Կ����
	 */
	private static int SALT_LENGTH=6;
	
	/**
	 * The encryption.
	 * 
	 * @��������:�Ƿ���Ҫ����
	 */
	private boolean encryption = true; 

	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
	 */
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		// ����jdbc.password����ֵ������������
		if (encryption) {
			try {
				EncrypDES3 des = new EncrypDES3();
				/**
				 * ��Կ����
				 */
				username = des.Decryptor(username);
				password = des.Decryptor(password);
				
				/**
				 * ˽Կ����
				 */
				String privateKey=null;
				EncrypDES3 privateDes=null;
				privateKey=username.substring(0, SALT_LENGTH);
				privateDes = new EncrypDES3(privateKey);
				username=privateDes.Decryptor(username.substring(SALT_LENGTH));
				
				privateKey=password.substring(0, SALT_LENGTH);
				privateDes = new EncrypDES3(privateKey);
				password=privateDes.Decryptor(password.substring(SALT_LENGTH));

				props.setProperty("jdbc.username", username);
				props.setProperty("jdbc.password", password);
			} catch (Exception e) {
				throw new BeanDefinitionStoreException("���ݿ��û����������ʧ��");
			}
		}
		super.processProperties(beanFactory, props);
		ctxPropertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
	}

	/**
	 * Checks if is encryption.
	 * 
	 * @return true, if checks if is encryption
	 * @��������:boolean isEncryption()
	 * @��������:
	 * @�����뷵��˵��: boolean isEncryption()
	 * @�㷨����:
	 */
	public boolean isEncryption() {
		return encryption;
	}

	/**
	 * Sets the encryption.
	 * 
	 * @param encryption
	 *            the new encryption
	 */
	public void setEncryption(boolean encryption) {
		this.encryption = encryption;
	}
	
	/**
	 * ���ݲ������ƻ�ȡ����ֵ
	 */
    public static String getContextProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }
    
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
			try {
				String desStr=null;
				String keyStr = "0123456789abcdefghijklmnopqrstuvwxyz";
				if (args.length == 1 ){
					desStr=args[0];
					String privateKey=RandomStringUtils.random(SALT_LENGTH, keyStr);// ����6λ��˽Կ
					EncrypDES3 publicDes = new EncrypDES3();
					EncrypDES3 privateDes = new EncrypDES3(privateKey);
					/**
					 * ˽Կ����
					 */
					desStr=privateDes.Encrytor(desStr);
					desStr=privateKey+desStr;
					/**
					 * ��Կ����
					 */
					desStr=publicDes.Encrytor(desStr);
					System.out.println("���ܺ���ַ���[" + desStr+"]");
				}else{
					System.out.println("����������ַ��� ");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}
	
	

}
