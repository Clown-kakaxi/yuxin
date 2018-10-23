package com.yuchengtech.emp.biappframe.message.web;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.message.MessageConstants;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAttachInfo;
import com.yuchengtech.emp.biappframe.message.service.BioneMsgAttachmentBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.bione.util.DownloadUtils;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title: 消息模块-附件控制器
 * Description: 消息模块-附件控制器
 * </pre>
 * 
 * @author liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/message/attach")
public class MessageAttachController extends BaseController {

	
	@Autowired
	private BioneMsgAttachmentBS msgAttachBS ;
	
	/** 上传路径 */
	private final static String UPLOAD_ATTACH_DIR = "/message/attach";
	

	/**
	 * 获取某一条公告的附件列表
	 * @param msgId 公告消息的ID
	 * @return
	 */
	@RequestMapping(value = "/{id}/listAttach.*")
	@ResponseBody
	public Map<String, Object> listFile(@PathVariable("id") String msgId) {
		List<BioneMsgAttachInfo> result = this.msgAttachBS.getAttachList(msgId);
		Map<String, Object> taskMap = Maps.newHashMap();
		taskMap.put("Rows", result);
		return taskMap;
	}
	
	

	/**
	 * 附件批量删除
	 * （这个可以放在附件的Controller里面）
	 * @param id
	 */
	@RequestMapping(value = "/delAttach/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void batchDeleteAttach(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		this.msgAttachBS.deleteBatch(ids);
	}
	
	
	/**
	 * 上传附件
	 * 
	 * @param uploader
	 * @param msgId  消息ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/{id}/startUpload")
	@ResponseBody
	public void startUpload(Uploader uploader, @PathVariable("id") String msgId, HttpServletResponse response) throws Exception {
		File file = null;
		try {
			file = this.uploadFile(uploader, UPLOAD_ATTACH_DIR, false);
		} catch (Exception e) {
			logger.info("文件上传出现异常", e);
		}
		if (file !=  null) {
			logger.info("文件[" + file.getName() + "]上传完成");
			// access db
			BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			String userNo = userObj.getUserName();
			//String logicSysNo = userObj.getCurrentLogicSysNo();
			String fileName = file.getName();
			String filePath = file.getCanonicalPath();
			if (filePath != null && filePath.length() > 0) {
				filePath = filePath.replace("\\", "/");
			}
			long   fileSize = file.length(); // bytes
			String fileExtName = "";
			if (fileName != null && fileName.indexOf(".") > 0) {
				fileExtName = fileName.substring(fileName.indexOf(".") + ".".length());
			}
			String relativePath = filePath;
			if (relativePath.indexOf(UPLOAD_ATTACH_DIR) > 0) {
				relativePath = relativePath.substring(relativePath.indexOf(UPLOAD_ATTACH_DIR));
			}
			//
			BioneMsgAttachInfo attach = new BioneMsgAttachInfo(); 
			attach.setAttachId(RandomUtils.uuid2());
			attach.setAttachName(fileName);
			attach.setAttachSize(fileSize);
			attach.setAttachSrc(relativePath);
			attach.setAttachSts(MessageConstants.COMMON_STATUS_VALID);
			attach.setAttachTypeNo(fileExtName);
			attach.setCreateTime(now);
			attach.setCreateUser(userNo);
			attach.setLastUpdateTime(now);
			attach.setLastUpdateUser(userNo);
			attach.setMsgId(msgId);
			attach.setRemark(null);
			// do save
			this.msgAttachBS.saveEntity(attach);
		}
	}
	
	/**
	 * 下载附件 
	 * @param response
	 * @param logicSysNo
	 * @throws Exception
	 */
	@RequestMapping("/startDownload")
	public void exportsys(HttpServletResponse response, String attachId) throws Exception {
		BioneMsgAttachInfo attach = this.msgAttachBS.getEntityById(attachId);
		if (attach!=null && attach.getAttachSrc()!=null && !attach.getAttachSrc().equals("")) {
			String path = this.getRealPath() + "/" + attach.getAttachSrc();
//			String path = attach.getAttachSrc();
			File src = new File(path);	
			DownloadUtils.download(response, src);
		}
	}
	
}
