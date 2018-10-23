/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：PropertyPlaceholderConfigurerExt.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:25:40
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：PropertyPlaceholderConfigurerExt
 * @类描述：数据库用户名密码加密
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:25:44   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:25:44
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class PropertyPlaceholderConfigurerExt extends
		PropertyPlaceholderConfigurer {
	/**
	 * 以Map的形式存储所有的参数信息
	 */
	private static Map<String, String> ctxPropertiesMap; 
	/**
	 * The SAL t_ length.
	 * 
	 * @属性描述:私钥长度
	 */
	private static int SALT_LENGTH=6;
	
	/**
	 * The encryption.
	 * 
	 * @属性描述:是否需要加密
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
		// 解密jdbc.password属性值，并重新设置
		if (encryption) {
			try {
				EncrypDES3 des = new EncrypDES3();
				/**
				 * 公钥解密
				 */
				username = des.Decryptor(username);
				password = des.Decryptor(password);
				
				/**
				 * 私钥解密
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
				throw new BeanDefinitionStoreException("数据库用户名密码解密失败");
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
	 * @函数名称:boolean isEncryption()
	 * @函数描述:
	 * @参数与返回说明: boolean isEncryption()
	 * @算法描述:
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
	 * 根据参数名称获取参数值
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
					String privateKey=RandomStringUtils.random(SALT_LENGTH, keyStr);// 返回6位的私钥
					EncrypDES3 publicDes = new EncrypDES3();
					EncrypDES3 privateDes = new EncrypDES3(privateKey);
					/**
					 * 私钥加密
					 */
					desStr=privateDes.Encrytor(desStr);
					desStr=privateKey+desStr;
					/**
					 * 公钥加密
					 */
					desStr=publicDes.Encrytor(desStr);
					System.out.println("加密后的字符串[" + desStr+"]");
				}else{
					System.out.println("请输入加密字符串 ");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}
	
	

}
