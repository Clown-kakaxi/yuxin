package com.yuchengtech.emp.biappframe.base.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.bione.util.FormatUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:基础Controller类型
 * Description: 实现部分公用方法，如文件上传、下拉图标选择等
 * </pre>
 * 
 * @author songxf
 * @version 1.00.00
 * 
 *          <pre>

 */
public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获得当前工程的url
	 */
	public String getProjectUrl() {
		//		String contextPath = ServletActionContext.getServletContext().getContextPath();
		String contextPath = GlobalConstants.APP_CONTEXT_PATH;
		String imgUrl = contextPath + "/";
		return imgUrl;
	}

	/**
	 * 获取Spring管理的Bean
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getSpringBean(String beanName) {
		return SpringContextHolder.getBean(beanName);
	}

	/**
	 * 将上传文件的公共方法
	 * 注意，由于显示上传百分比的原因，文件有可能被分为多块上传，如果返回值为null说明文件没有上传完成
	 * @param dstPath   
	 * 				上传的目标路径
	 * @param timeflag   
	 * 				时间戳标识，是否在文件后面追加时间戳
	 * @return File
	 *              file分块全部 上传完成    null分块传输未结束  
	 */
	public File uploadFile(Uploader uploader, String dstPath, boolean timeflag) throws Exception {
		String realName = uploader.getUpload().getOriginalFilename();
		String dstAllPath = this.getRealPath() + File.separatorChar + dstPath + File.separatorChar
				+ FormatUtils.format(System.currentTimeMillis(), "yyyyMMdd") + File.separatorChar;
		File dstFilePath = new File(dstAllPath);
		if (dstFilePath.exists() == false) {
			dstFilePath.mkdirs();
		}
		File dstFile = new File(dstAllPath + uploader.getName());
		// 文件已存在（上传了同名的文件）
		if (uploader.getChunk() == 0 && dstFile.exists()) {
			dstFile.delete();
			dstFile = new File(dstAllPath + uploader.getName());
		}
		copyFile(uploader.getUpload().getInputStream(), dstFile);
		logger.info("上传文件:" + realName + "文件类型:" + uploader.getUpload().getContentType() + " "
				+ (uploader.getChunk() + 1) + " " + uploader.getChunk());
		if (uploader.getChunk() == uploader.getChunks() - 1) {
			File file = null;
			if (timeflag)
				file = new File(dstAllPath + realName + "."
						+ FormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS"));
			else
				file = new File(dstAllPath + realName);
			if (file.exists())
				file.delete();
			dstFile.renameTo(file);
			if (dstFile.exists())
				dstFile.delete();
			return file;
		}
		return null;
	}

	/**
	 * 将分块上传的文件整合到一个文件中
	 * 
	 */
	private void copyFile(InputStream src, File dst) {
		int BUFFER_SIZE = 1024;
		InputStream in = null;
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true), BUFFER_SIZE);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			}
			in = new BufferedInputStream(src, BUFFER_SIZE);

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**  
	 * 绕过Template,直接输出内容的简便函数.   
	 */
	protected String render(String text, String contentType, HttpServletResponse response) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**  
	 * 直接输出字符串.  
	 */
	protected String renderText(String text, HttpServletResponse response) {
		return render(text, "text/plain;charset=UTF-8", response);
	}

	/**  
	 * 直接输出HTML.  
	 */
	protected String renderHtml(String html, HttpServletResponse response) {
		return render(html, "text/html;charset=UTF-8", response);
	}

	/**  
	 * 直接输出XML.  
	 */
	protected String renderXML(String xml, HttpServletResponse response) {
		return render(xml, "text/xml;charset=UTF-8", response);
	}

	/**
	 * 关闭窗口
	 */
	protected void closeWindow(HttpServletResponse response) {
		StringBuffer html = new StringBuffer();
		html.append("<script type=\"text/javascript\">");
		html.append("window.opener=null;");
		html.append("window.open('', '_self', '');");
		html.append("window.close();");
		html.append("</script>");
		this.renderHtml(html.toString(), response);
	}

	/**
	 * 获取HttpServletRequest对象
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {

		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	/**
	 * 加载图片选择combox数据,并缓存结果
	 * 
	 * @param directory
	 *            图标存放的目录
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, String>> buildIconCombox(String projectPath, String directory) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();// 返回普通结果集合

		StringBuffer path = new StringBuffer();
		path.append("images");
		path.append("/");
		if (projectPath != null && !"".equals(projectPath))
			path.append(projectPath);
		else
			path.append(GlobalConstants.THEME);
		path.append("/");
		path.append(directory);
		path.append("/");
		if (EhcacheUtils.get(path.toString(), "iconsCombox") == null) {
			String realPath = this.getRealPath();
			Collection<File> filelist = new ArrayList<File>();
			filelist = FileUtils.listFiles(new File(realPath + "/" + path), null, false);
			for (Iterator<File> iter = filelist.iterator(); iter.hasNext();) {
				File file = iter.next();
				String filename = file.getName();
				String imgPath = path + filename;
				Map<String, String> map = new HashMap<String, String>();
				map.put("text", imgPath);
				map.put("id", imgPath);
				list.add(map);
			}
			EhcacheUtils.put(path.toString(), "iconsCombox", list);
		} else {
			return (List) EhcacheUtils.get(path.toString(), "iconsCombox");
		}
		return list;

	}

	/**
	 * 加载图片选择combox数据,并缓存结果
	 * 
	 * @param directory
	 *            图标存放的目录
	 */
	public List<Map<String, String>> buildIconCombox(String directory) {

		return buildIconCombox(null, directory);

	}

	/**
	 * 生成图标选择页面,并缓存结果
	 * 
	 * @param directory
	 *            图标存放的目录
	 */
	public String buildIconSelectHTML(String projectPath, String directory) {
		String path = new String();
		if (projectPath != null && !"".equals(projectPath)) {
			path = "images" + "/" + projectPath + "/" + directory + "/";
		} else {
			path = "images" + "/" + GlobalConstants.THEME + "/" + directory + "/";
		}
		StringBuffer html = new StringBuffer();
		if (EhcacheUtils.get(path, "iconsHTML") == null) {
			// 获取服务器路径下的图标列表
			String realPath = this.getRealPath();
			Collection<File> filelist;
			try {
				filelist = FileUtils.listFiles(new File(realPath + "/" + path), null, false);
				html.append("<div id=\"winicons\">");
				html.append("<ul class=\"iconlist\">");
				for (Iterator<File> iter = filelist.iterator(); iter.hasNext();) {
					File file = iter.next();
					String filename = file.getName();
					String imgPath = path + filename;
					//					String contextPath = ServletActionContext.getServletContext().getContextPath();
					String contextPath = GlobalConstants.APP_CONTEXT_PATH;
					String imgUrl = contextPath + "/" + imgPath;
					html.append("<li><img src='" + imgUrl + "' alt='" + imgPath + "' /></li>");
				}
				html.append("</ul>");
				html.append("</div>");
			} catch (Exception e) {
				e.printStackTrace();
			}
			EhcacheUtils.put(path, "iconsHTML", html.toString());
			return html.toString();
		} else {
			return (String) EhcacheUtils.get(path, "iconsHTML");
		}
	}

	/**
	 * 生成图标选择页面,并缓存结果
	 * 
	 * @param directory
	 *            图标存放的目录
	 */
	public String buildIconSelectHTML(String directory) {
		return buildIconSelectHTML(null, directory);
	}

	/**
	 * 工程当前真实的物理地址，weblogic war发布模式时不能获取路径
	 */
	public String getRealPath() {
		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession().getServletContext().getRealPath("/");
		return path;
	}

	/**
	 * 获得ContextPath
	 */
	public String getContextPath() {
//		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
//		return path;
		
		/* @Revision 20130403105200-liuch */
		return GlobalConstants.APP_CONTEXT_PATH;
		/* @Revision 20130403105200-liuch END */
	}
}
