package com.yuchengtech.emp.biappframe.base.web;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.bione.entity.upload.Uploader;


/**
 * <pre>
 * Title:上传文件
 * Description:上传文件
 * </pre>
 * 
 * @author songxf songxf@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/common/upload")
public class UploadAction extends BaseController {

//	public DefaultHttpHeaders index() {
//		return new DefaultHttpHeaders("index").disableCaching();
//	}

	@RequestMapping("/startUpload")
	@ResponseBody
	public String startUpload(Uploader uploader) throws Exception {
		File file = null;
		try {
			file = this.uploadFile(uploader, "dst", false);
		} catch (Exception e) {
			logger.info("文件上传出现异常", e);
		}
		if (file != null) {
			logger.info("文件[" + file.getName() + "]上传完成");
			//unzipFile(file);
		}
		return "true";
	}

}
