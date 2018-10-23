package com.yuchengtech.emp.ecif.transaction.web;


import static com.yuchengtech.emp.ecif.base.common.GlobalConstants.EXCEL_IMPORT_FOLDER;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;
import com.yuchengtech.emp.ecif.transaction.service.TxDefBS;
import com.yuchengtech.emp.ecif.transaction.service.TxExcelReportBS;
import com.yuchengtech.emp.ecif.transaction.service.TxXmlReportBS;
import com.yuchengtech.emp.ecif.transaction.service.XmlUploadTask;
import com.yuchengtech.emp.ecif.transaction.service.ZipUploadTask;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:Excel报表
 * Description: Excel报表的导入和导出功能
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/transaction/excel")
public class TxExcelReportController extends BaseController {
	private Logger log = LoggerFactory.getLogger(TxExcelReportController.class);
	@Autowired
	private TxExcelReportBS reportBS;
	
	@Autowired
	private TxXmlReportBS xmlReportBS;
	
	@Autowired
	private TxDefBS txDefBS;
	
	// 上传页面
	@RequestMapping("/upload")
	public String upload() {
		return "/ecif/transaction/txexcelreport-upload";
	}

	// 执行上传
	@RequestMapping("/startUpload")
	public void startUpload(Uploader uploader, String saveType, HttpServletResponse response){
		File file = null;
		try {
			file = uploadFile(uploader, EXCEL_IMPORT_FOLDER, false);
		} catch (Exception e) {
			log.error("文件上传失败", e);
			e.printStackTrace();
		}
		if (uploader.getChunk() == uploader.getChunks() - 1) {
			if (file != null) {
				// 开启线程
				ThreadPoolTaskExecutor executor = SpringContextHolder.getBean("taskExecutor");
				if (uploader.getName().toLowerCase().endsWith(".zip")) {
					ZipUploadTask task = SpringContextHolder.getBean(ZipUploadTask.class);
					task.setFile(file);
					task.setSaveType(saveType);
					executor.execute(task);
				}
				
//				if (uploader.getName().toLowerCase().endsWith(".xlsx")) {
//					ExcelUploadTask task = SpringContextHolder.getBean(ExcelUploadTask.class);
//					task.setFile(file);
//					task.setSaveType(saveType);
//					executor.execute(task);
//				}
//				
				if (uploader.getName().toLowerCase().endsWith(".xml")) {
					XmlUploadTask task = SpringContextHolder.getBean(XmlUploadTask.class);
					task.setFile(file);
					task.setSaveType(saveType);
					executor.execute(task);
				}
				
			}	
		}
	}

	@RequestMapping("/download")
	public void downloadAll(HttpServletResponse response) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//reportBS.zip(baos, reportBS.getAllReport(),txDefBS.getTxDefList());
			reportBS.zipXml(baos, xmlReportBS.getAllReport(),txDefBS.getTxDefList());
			byte[] bus = baos.toByteArray();
			doDownload(bus, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/{id}/download")
	public String download(@PathVariable("id") String id, HttpServletResponse response) {
		if (!StringUtils.isEmpty(id)) {
			String[] sa = StringUtils.split(id, ",");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List<Long> txIdLst = Lists.newArrayList();
			List<TxDef> txDefLst = Lists.newArrayList();
			Iterator<String> iter = Arrays.asList(sa).iterator();
			while (iter.hasNext()) {
				String txId = iter.next();
				txIdLst.add(Long.parseLong(txId));
				
				txDefLst.add(txDefBS.getEntityById(Long.parseLong(txId)));
			}
			try {
				//reportBS.zip(baos, reportBS.getReports(txIdLst),txDefLst);
				reportBS.zipXml(baos, xmlReportBS.getReports(txIdLst),txDefLst);
				byte[] bus = baos.toByteArray();
				if(txIdLst.size()==1){
					doDownloadOne(bus, response,((TxDef)txDefLst.get(0)).getTxCode());
				}else{
					doDownload(bus, response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void doDownload(byte bus[], HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition",
				"attachment; filename=report.zip");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Length", String.valueOf(bus.length));
		response.getOutputStream().write(bus);
	}
	
	private void doDownloadOne(byte bus[], HttpServletResponse response,String txCode) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition",
				"attachment; filename="+ txCode +".xml");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Length", String.valueOf(bus.length));
		response.getOutputStream().write( bus );
	}
	
}
