/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����FtpOperator.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:11:16
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�FtpOperator
 * @��������FTP������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:11:23   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:11:23
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FtpOperator {
	
	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(FtpOperator.class);
	
	/**
	 * The ip.
	 * 
	 * @��������:ip��ַ
	 */
	private String ip;
	
	/**
	 * The port.
	 * 
	 * @��������:�˿ں�
	 */
	private int port;
	
	/**
	 * The username.
	 * 
	 * @��������:�û���
	 */
	private String username;
	
	/**
	 * The password.
	 * 
	 * @��������:����
	 */
	private String password;
	
	/**
	 * The remote path.
	 * 
	 * @��������:Ŀ¼
	 */
	private String remotePath;
	
	/**
	 * The local path.
	 * 
	 * @��������:
	 */
	private String localPath;

	
	
	/**
	 *@���캯�� 
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
	 * @��������:downloadFile
	 * @��������:�����ļ�
	 * @�����뷵��˵��:
	 * 		@param filename
	 * 		@return
	 * @�㷨����:
	 */
	public boolean downloadFile(String filename) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
            int reply;
            ftp.connect(ip, port);
            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
            ftp.login(username, password);//��¼
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼
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
	 * @��������:uploadFile
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@param filename
	 * 		@return
	 * @�㷨����:
	 */
	public boolean uploadFile(String filename) {
		boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
        	FileInputStream in=new FileInputStream(new File(localPath + "/" + filename));
            int reply;
            ftp.connect(ip, port);//����FTP������
            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
            ftp.login(username, password);//��¼
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
	 * @��������:downloadFile
	 * @��������:�����ļ�
	 * @�����뷵��˵��:
	 * 		@param filename
	 * 		@return
	 * @�㷨����:
	 */
//	public boolean downloadFileViaSftp(String filename) {
//		boolean success = false;
//		
//		ChannelSftp sftp = null;
//		try {
//            int reply;
//            ftp.connect(ip, port);
//            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
//            ftp.login(username, password);//��¼
//            reply = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                return success;
//            }
//            ftp.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼
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
