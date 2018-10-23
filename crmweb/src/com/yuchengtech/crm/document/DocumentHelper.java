package com.yuchengtech.crm.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class DocumentHelper {

	private Configuration configuration = null;

	public DocumentHelper() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	public void createDoc(String dir, String fileName, String savePath,
			String[][] sDate) {
		// 要填入模本的数据文件
		Map<String, Object> dataMap = new HashMap<String, Object>();
		getData(dataMap, sDate);
		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		Template t = null;
		try {
			// 从什么地方加载freemarker模板文件
			configuration.setDirectoryForTemplateLoading(new File(dir));

			// 设置对象包装器
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			// 设置异常处理器
			configuration
					.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			// 定义Template对象
			t = configuration.getTemplate(fileName);

		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		File outFile = new File(savePath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getData(Map<String, Object> dataMap, String[][] sDate) {
		for (int i = 0; i < sDate.length; i++) {
			dataMap.put(sDate[i][0], sDate[i][1]);
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		DocumentHelper dh = new DocumentHelper();
		String[][] sDate = new String[24][2];
		sDate[0][0] = "custName";
		sDate[0][1] = "王二";
		sDate[1][0] = "telNum";
		sDate[1][1] = "13987654321";
		sDate[2][0] = "cerType";
		sDate[2][1] = "身份证";
		sDate[3][0] = "cerNum";
		sDate[3][1] = "210123199909091234";
		dh.createDoc("E:\\documentDir", "需求部分摘要-关于此问题描述.xml", "E:/documentDir/yuchengDocument.doc", sDate);
		//dh.createDoc("E:\\documentDir", "yuchengDocument.ftl", "E:/documentDir/yuchengDocument.rtf", sDate);
		long end = System.currentTimeMillis();
		System.out.println("导出消耗时间为：" + (end - start) + "毫秒");
	}

}
