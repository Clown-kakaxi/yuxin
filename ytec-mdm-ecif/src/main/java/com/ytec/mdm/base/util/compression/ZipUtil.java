/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util.compression
 * @�ļ�����ZipUtil.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-11-����2:09:57
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�ZipUtil
 * @��������zipѹ����ѹ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-11 ����2:09:57
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-11 ����2:09:57
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ZipUtil {
	
	/**
	 * @��������:zip
	 * @��������:ѹ���ļ�file��zip�ļ�zipFile
	 * @�����뷵��˵��:
	 * 		@param file
	 * 		@param zipFile
	 * 		@param charSet
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public static void zip(File file, File zipFile,String charSet) throws Exception {
		ZipOutputStream output = null;
		try {
			
			output = new ZipOutputStream(new FileOutputStream(zipFile));
			if(charSet==null||"".equals(charSet)){
				charSet="GB18030";
			}
			output.setEncoding(charSet);
			zipFile(output, file, "");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// �ر���
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (Exception e) {

				}
			}
		}
	}

	/**
	 * ѹ���ļ�Ϊzip��ʽ
	 * 
	 * @param output
	 *            ZipOutputStream����
	 * @param file
	 *            Ҫѹ�����ļ����ļ���
	 * @param basePath
	 *            ��Ŀ��Ŀ¼
	 * @throws IOException
	 */
	private static void zipFile(ZipOutputStream output, File file,
			String basePath) throws IOException {
		FileInputStream input = null;
		try {
			// �ļ�ΪĿ¼
			if (file.isDirectory()) {
				// �õ���ǰĿ¼������ļ��б�
				File list[] = file.listFiles();
				basePath = basePath + (basePath.length() == 0 ? "" : "/")
						+ file.getName();
				// ѭ���ݹ�ѹ��ÿ���ļ�
				for (File f : list)
					zipFile(output, f, basePath);
			} else {
				// ѹ���ļ�
				basePath = (basePath.length() == 0 ? "" : basePath + "/")
						+ file.getName();
				output.putNextEntry(new ZipEntry(basePath));
				input = new FileInputStream(file);
				int readLen = 0;
				byte[] buffer = new byte[1024 * 8];
				while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
					output.write(buffer, 0, readLen);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// �ر���
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {

				}
			}
		}
	}

	/**
	 * ��ѹzip�ļ�
	 * 
	 * @param zipFilePath
	 *            zip�ļ�����·��
	 * @param unzipDirectory
	 *            ��ѹ����Ŀ¼
	 * @param charSet
	 * 			  �ַ���
	 * @throws Exception
	 */
	public static void unzip(String zipFilePath, String unzipDirectory,String charSet)
			throws Exception {
		// �����������������
		InputStream input = null;
		OutputStream output = null;
		try {
			// �����ļ�����
			File file = new File(zipFilePath);
			// ����zip�ļ�����
			if(charSet==null||"".equals(charSet)){
				charSet="GB18030";
			}
			ZipFile zipFile = new ZipFile(file,charSet);
			// ������zip�ļ���ѹĿ¼
			String name = file.getName().substring(0,
					file.getName().lastIndexOf("."));
			File unzipFile = new File(unzipDirectory + "/" + name);
			if (unzipFile.exists())
				unzipFile.delete();
			unzipFile.mkdir();
			// �õ�zip�ļ���Ŀö�ٶ���
			Enumeration zipEnum = zipFile.getEntries();
			// �������
			ZipEntry entry = null;
			String entryName = null, path = null;
			String names[] = null;
			int length;
			// ѭ����ȡ��Ŀ
			while (zipEnum.hasMoreElements()) {
				// �õ���ǰ��Ŀ
				entry = (ZipEntry) zipEnum.nextElement();
				entryName = new String(entry.getName());
				// ��/�ָ���Ŀ����
				names = entryName.split("\\/");
				length = names.length;
				path = unzipFile.getAbsolutePath();
				for (int v = 0; v < length; v++) {
					if (v < length - 1) // ���һ��Ŀ¼֮ǰ��Ŀ¼
						FileUtil.createFolder(path += "/" + names[v] + "/");
					else { // ���һ��
						if (entryName.endsWith("/")) // ΪĿ¼,�򴴽��ļ���
							FileUtil.createFolder(unzipFile.getAbsolutePath()
									+ "/" + entryName);
						else { // Ϊ�ļ�,��������ļ�
							input = zipFile.getInputStream(entry);
							output = new FileOutputStream(new File(
									unzipFile.getAbsolutePath() + "/"
											+ entryName));
							byte[] buffer = new byte[1024 * 8];
							int readLen = 0;
							while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
								output.write(buffer, 0, readLen);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// �ر���
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {

				}
			}
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (Exception e) {

				}
			}
		}
	}
}
