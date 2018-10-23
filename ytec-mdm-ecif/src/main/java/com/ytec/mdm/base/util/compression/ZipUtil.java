/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util.compression
 * @文件名：ZipUtil.java
 * @版本信息：1.0.0
 * @日期：2014-4-11-下午2:09:57
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：ZipUtil
 * @类描述：zip压缩解压
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-11 下午2:09:57
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-11 下午2:09:57
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ZipUtil {
	
	/**
	 * @函数名称:zip
	 * @函数描述:压缩文件file成zip文件zipFile
	 * @参数与返回说明:
	 * 		@param file
	 * 		@param zipFile
	 * 		@param charSet
	 * 		@throws Exception
	 * @算法描述:
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
			// 关闭流
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
	 * 压缩文件为zip格式
	 * 
	 * @param output
	 *            ZipOutputStream对象
	 * @param file
	 *            要压缩的文件或文件夹
	 * @param basePath
	 *            条目根目录
	 * @throws IOException
	 */
	private static void zipFile(ZipOutputStream output, File file,
			String basePath) throws IOException {
		FileInputStream input = null;
		try {
			// 文件为目录
			if (file.isDirectory()) {
				// 得到当前目录里面的文件列表
				File list[] = file.listFiles();
				basePath = basePath + (basePath.length() == 0 ? "" : "/")
						+ file.getName();
				// 循环递归压缩每个文件
				for (File f : list)
					zipFile(output, f, basePath);
			} else {
				// 压缩文件
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
			// 关闭流
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {

				}
			}
		}
	}

	/**
	 * 解压zip文件
	 * 
	 * @param zipFilePath
	 *            zip文件绝对路径
	 * @param unzipDirectory
	 *            解压到的目录
	 * @param charSet
	 * 			  字符集
	 * @throws Exception
	 */
	public static void unzip(String zipFilePath, String unzipDirectory,String charSet)
			throws Exception {
		// 定义输入输出流对象
		InputStream input = null;
		OutputStream output = null;
		try {
			// 创建文件对象
			File file = new File(zipFilePath);
			// 创建zip文件对象
			if(charSet==null||"".equals(charSet)){
				charSet="GB18030";
			}
			ZipFile zipFile = new ZipFile(file,charSet);
			// 创建本zip文件解压目录
			String name = file.getName().substring(0,
					file.getName().lastIndexOf("."));
			File unzipFile = new File(unzipDirectory + "/" + name);
			if (unzipFile.exists())
				unzipFile.delete();
			unzipFile.mkdir();
			// 得到zip文件条目枚举对象
			Enumeration zipEnum = zipFile.getEntries();
			// 定义对象
			ZipEntry entry = null;
			String entryName = null, path = null;
			String names[] = null;
			int length;
			// 循环读取条目
			while (zipEnum.hasMoreElements()) {
				// 得到当前条目
				entry = (ZipEntry) zipEnum.nextElement();
				entryName = new String(entry.getName());
				// 用/分隔条目名称
				names = entryName.split("\\/");
				length = names.length;
				path = unzipFile.getAbsolutePath();
				for (int v = 0; v < length; v++) {
					if (v < length - 1) // 最后一个目录之前的目录
						FileUtil.createFolder(path += "/" + names[v] + "/");
					else { // 最后一个
						if (entryName.endsWith("/")) // 为目录,则创建文件夹
							FileUtil.createFolder(unzipFile.getAbsolutePath()
									+ "/" + entryName);
						else { // 为文件,则输出到文件
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
			// 关闭流
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
