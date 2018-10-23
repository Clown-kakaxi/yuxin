/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util.compression
 * @�ļ�����FileUtil.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-11-����2:18:21
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util.compression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�FileUtil
 * @���������ļ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-11 ����2:18:21
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-11 ����2:18:21
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FileUtil {
	/**
	 * ��ȡ�ı��ļ�����
	 * 
	 * @param filePathAndName
	 *            ������������·�����ļ���
	 * @param encoding
	 *            �ı��ļ��򿪵ı��뷽ʽ
	 * @return �����ı��ļ�������
	 */
	public static String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data).append('\n');
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	/**
	 * �½�Ŀ¼
	 * 
	 * @param folderPath
	 *            Ŀ¼
	 * @return ����Ŀ¼�������·��
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		java.io.File myFilePath = new java.io.File(txt);
		txt = folderPath;
		if (!myFilePath.exists()) {
			myFilePath.mkdirs();
		}
		return txt;
	}

	/**
	 * �༶Ŀ¼����
	 * 
	 * @param folderPath
	 *            ׼��Ҫ�ڱ���Ŀ¼�´�����Ŀ¼��Ŀ¼·�� ���� c:myf
	 * @param paths
	 *            ���޼�Ŀ¼����������Ŀ¼�Ե��������� ���� a|b|c
	 * @return ���ش����ļ����·�� ���� c:myfac
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		String txt;
		txts = folderPath;
		StringTokenizer st = new StringTokenizer(paths, "|");
		while (st.hasMoreTokens()) {
			txt = st.nextToken().trim();
			if (txts.lastIndexOf("/") != -1) {
				txts = createFolder(txts + txt);
			} else {
				txts = createFolder(txts + txt + "/");
			}
		}
		return txts;
	}

	/**
	 * �½��ļ�
	 * 
	 * @param filePathAndName
	 *            �ı��ļ���������·�����ļ���
	 * @param fileContent
	 *            �ı��ļ�����
	 * @return
	 * @throws IOException
	 */
	public static void createFile(String filePathAndName, String fileContent)
			throws IOException {

		String filePath = filePathAndName;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			myFilePath.createNewFile();
		}
		FileWriter resultFile = new FileWriter(myFilePath);
		PrintWriter myFile = new PrintWriter(resultFile);
		String strContent = fileContent;
		myFile.println(strContent);
		myFile.close();
		resultFile.close();
	}

	/**
	 * �б��뷽ʽ���ļ�����
	 * 
	 * @param filePathAndName
	 *            �ı��ļ���������·�����ļ���
	 * @param fileContent
	 *            �ı��ļ�����
	 * @param encoding
	 *            ���뷽ʽ ���� GBK ���� UTF-8
	 * @return
	 * @throws IOException
	 */
	public static void createFile(String filePathAndName, String fileContent,
			String encoding) throws IOException {

		String filePath = filePathAndName;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			myFilePath.createNewFile();
		}
		PrintWriter myFile = new PrintWriter(myFilePath, encoding);
		String strContent = fileContent;
		myFile.println(strContent);
		myFile.close();

	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param filePathAndName
	 *            �ı��ļ���������·�����ļ���
	 * @return Boolean �ɹ�ɾ������true�����쳣����false
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;

		String filePath = filePathAndName;
		File myDelFile = new File(filePath);
		if (myDelFile.exists()) {
			myDelFile.delete();
			bea = true;
		} else {
			bea = false;
			System.out.println(filePathAndName + "ɾ���ļ���������");
		}

		return bea;
	}

	/**
	 * ɾ���ļ���
	 * 
	 * @param folderPath
	 *            �ļ�����������·��
	 * @return
	 */
	public static void delFolder(String folderPath) {

		delAllFile(folderPath); // ɾ����������������
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete(); // ɾ�����ļ���

	}

	/**
	 * ɾ��ָ���ļ����������ļ�
	 * 
	 * @param path
	 *            �ļ�����������·��
	 * @return
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * ���Ƶ����ļ�
	 * 
	 * @param oldPathFile
	 *            ׼�����Ƶ��ļ�Դ
	 * @param newPathFile
	 *            �������¾���·�����ļ���
	 * @return
	 * @throws IOException
	 */
	public static void copyFile(String oldPathFile, String newPathFile)
			throws IOException {

		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPathFile);
		if (oldfile.exists()) { // �ļ�����ʱ
			InputStream inStream = new FileInputStream(oldPathFile); // ����ԭ�ļ�
			FileOutputStream fs = new FileOutputStream(newPathFile);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // �ֽ��� �ļ���С
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}

	}

	/******
	 * 
	 * @param oldPathFile
	 * @param newPathFile
	 * @param filename
	 * @throws IOException
	 */
	public static void copyFile(String oldPathFile, String newPathFile,
			String filename) throws IOException {

		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPathFile);
		newPathFile += filename;
		if (oldfile.exists()) { // �ļ�����ʱ
			InputStream inStream = new FileInputStream(oldPathFile); // ����ԭ�ļ�
			FileOutputStream fs = new FileOutputStream(newPathFile);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // �ֽ��� �ļ���С
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}

	}

	/**
	 * ���������ļ��е�����
	 * 
	 * @param oldPath
	 *            ׼��������Ŀ¼
	 * @param newPath
	 *            ָ������·������Ŀ¼
	 * @return
	 * @throws IOException
	 */
	public static void copyFolder(String oldPath, String newPath)
			throws IOException {

		new File(newPath).mkdirs(); // ����ļ��в����� �������ļ���
		File a = new File(oldPath);
		String[] file = a.list();
		File temp = null;
		for (int i = 0; i < file.length; i++) {
			if (oldPath.endsWith(File.separator)) {
				temp = new File(oldPath + file[i]);
			} else {
				temp = new File(oldPath + File.separator + file[i]);
			}
			if (temp.isFile()) {
				FileInputStream input = new FileInputStream(temp);
				FileOutputStream output = new FileOutputStream(newPath + "/"
						+ (temp.getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (temp.isDirectory()) {// ��������ļ���
				copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
			}
		}

	}

	/**
	 * �ƶ��ļ�
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @throws IOException
	 */
	public static void moveFile(String oldPath, String newPath)
			throws IOException {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * �ƶ�Ŀ¼
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @throws IOException
	 */
	public static void moveFolder(String oldPath, String newPath)
			throws IOException {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * ����id��ȷ��ͼƬ����·��
	 * 
	 * @param infoId
	 *            - ���ֱ��
	 * @return �ָ�Ŀ¼
	 */
	public static String getPathById(int infoId) {
		return getPathById(infoId + "");
	}

	/**
	 * ����id��ȷ��ͼƬ����·��
	 * 
	 * @param infoId
	 *            - �ַ����
	 * @return �ָ�Ŀ¼
	 */
	public static String getPathById(String infoId) {
		StringBuffer path = new StringBuffer();
		while (infoId.length() > 1) {
			path.append(infoId.substring(0, 2)).append("/");
			infoId = infoId.substring(2);
		}
		if (infoId.length() > 0)
			path.append(infoId).append("/");
		return path.toString();
	}

}
