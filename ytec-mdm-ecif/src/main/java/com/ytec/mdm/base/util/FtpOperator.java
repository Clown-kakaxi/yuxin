/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：FtpOperator.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:11:16
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：FtpOperator
 * @类描述：FTP工具类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:11:23   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:11:23
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class FtpOperator {
	
	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	private static Logger log = LoggerFactory.getLogger(FtpOperator.class);
	
	/**
	 * The ip.
	 * 
	 * @属性描述:ip地址
	 */
	private String ip;
	
	/**
	 * The port.
	 * 
	 * @属性描述:端口号
	 */
	private int port;
	
	/**
	 * The username.
	 * 
	 * @属性描述:用户名
	 */
	private String username;
	
	/**
	 * The password.
	 * 
	 * @属性描述:密码
	 */
	private String password;
	
	/**
	 * The remote path.
	 * 
	 * @属性描述:目录
	 */
	private String remotePath;
	
	/**
	 * The local path.
	 * 
	 * @属性描述:
	 */
	private String localPath;

	
	
	/**
	 *@构造函数 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param localPath
	 */
	public FtpOperator(String ip, int port, String username, String password, String remotePath, String localPath) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.remotePath = remotePath;
		this.localPath = localPath;
	}
	
	
	/**
	 * @函数名称:downloadFile
	 * @函数描述:下载文件
	 * @参数与返回说明:
	 * 		@param filename
	 * 		@return
	 * @算法描述:
	 */
	public boolean downloadFile(String filename) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
            int reply;
            ftp.connect(ip, port);
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
            File localFile = new File(localPath + "/" + filename);
            OutputStream is = new FileOutputStream(localFile);
            success = ftp.retrieveFile(filename, is);
            is.close();
            ftp.logout();
            if( success ){
            	log.info("FtpOperator.downloadFile(" + remotePath + "/" + filename + ") OK.");
            }else{
            	log.error("FtpOperator.downloadFile(" + remotePath + "/" + filename + ") retrieveFile ERROR.");
            }
        
		} catch (IOException e) {
            log.error("FtpOperator.downloadFile(" + remotePath + "/" + filename + ") IOException.");
            log.error("FtpOperator:",e);
            
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
	}
	
	
	/**
	 * @函数名称:uploadFile
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@param filename
	 * 		@return
	 * @算法描述:
	 */
	public boolean uploadFile(String filename) {
		boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
        	FileInputStream in=new FileInputStream(new File(localPath + "/" + filename));
            int reply;
            ftp.connect(ip, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);
            success = ftp.storeFile(filename, in);
            in.close();
            ftp.logout();
            if( success ){
            	log.info("FtpOperator.uploadFile(" + remotePath + "/" + filename + ") OK.");
            }else{
            	log.info("FtpOperator.uploadFile(" + remotePath + "/" + filename + ") storeFile ERROR.");
            }
        }
        catch (IOException e) {
        	 log.error("FtpOperator.uploadFile(" + remotePath + "/" + filename + ") IOException.");
        	 log.error("FtpOperator:",e);
		} catch (Exception e) {
			e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
	}
	
	/**
	 * Gets the ip.
	 * 
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the remote path.
	 * 
	 * @return the remote path
	 */
	public String getRemotePath() {
		return remotePath;
	}

	/**
	 * Gets the local path.
	 * 
	 * @return the local path
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	
	/**
	 * @函数名称:downloadFile
	 * @函数描述:下载文件
	 * @参数与返回说明:
	 * 		@param filename
	 * 		@return
	 * @算法描述:
	 */
//	public boolean downloadFileViaSftp(String filename) {
//		boolean success = false;
//		
//		ChannelSftp sftp = null;
//		try {
//            int reply;
//            ftp.connect(ip, port);
//            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
//            ftp.login(username, password);//登录
//            reply = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                return success;
//            }
//            ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
//            File localFile = new File(localPath + "/" + filename);
//            OutputStream is = new FileOutputStream(localFile);
//            success = ftp.retrieveFile(filename, is);
//            is.close();
//            ftp.logout();
//            if( success ){
//            	log.info("FtpOperator.downloadFile(" + remotePath + "/" + filename + ") OK.");
//            }else{
//            	log.error("FtpOperator.downloadFile(" + remotePath + "/" + filename + ") retrieveFile ERROR.");
//            }
//        
//		} catch (IOException e) {
//            log.error("FtpOperator.downloadFile(" + remotePath + "/" + filename + ") IOException.");
//            log.error("FtpOperator:",e);
//            
//        } finally {
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        return success;
//	}
	
	
	
	public static void main(String[] args) {
		FtpOperator fp = new FtpOperator("10.20.34.108",21,"ecif","ecif","/pub/ecif","/home/ecif/transaction/ytec_mdm_core/batch");
//		fp.downloadFile("Test.java");
		fp.downloadFile("nohup.out");
//		fp.uploadFile("bione.log");
//		fp.uploadFile("nohup.out");
	}

}
