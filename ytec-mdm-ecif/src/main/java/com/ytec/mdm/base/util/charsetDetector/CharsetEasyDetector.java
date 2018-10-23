/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util.charsetDetector
 * @文件名：CharsetEasyDetector.java
 * @版本信息：1.0.0
 * @日期：2014-5-12-下午1:35:00
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util.charsetDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsDetector;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsEUCStatistics;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsICharsetDetectionObserver;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsPSMDetector;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsVerifier;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：CharsetEasyDetector
 * @类描述：简单字符集检测
 * @功能描述:设置字符集范围，此字符集之间顺序和编码不冲突，例如中文中GB18030范围最大，包含GBK
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-12 下午1:35:00
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-12 下午1:35:00
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CharsetEasyDetector implements nsICharsetDetectionObserver{
	/**
	 * @属性名称:MAXLENGTH
	 * @属性描述:读取最大字节数，用来判定字符集
	 * @since 1.0.0
	 */
	private static int MAXLENGTH=1024;
	/**
	 * @属性名称:lang
	 * @属性描述:判定字符集范围
	 * @since 1.0.0
	 */
	private int lang;
	
	/**
	 * @属性名称:mVerifier
	 * @属性描述:自定义字符集判定范围
	 * @since 1.0.0
	 */
	private nsVerifier[] mVerifier;
	
	/**
	 * @属性名称:mStatisticsData
	 * @属性描述:对应判定静态数据
	 * @since 1.0.0
	 */
	private nsEUCStatistics[] mStatisticsData;
	
	/**
	 * @属性名称:det
	 * @属性描述:判定器
	 * @since 1.0.0
	 */
	private nsDetector det;
	
	
	private boolean found;
	private String result;
	/**
	 * @属性名称:prob
	 * @属性描述:结果字符集
	 * @since 1.0.0
	 */
	private String[] charsets;
	
	private int amountOfVerifiers=0;

	public CharsetEasyDetector() {
		
	}
	
	public void init(){
		if(this.lang>0){
			this.det = new nsDetector(lang);
		}else if(mVerifier!=null){
			this.det = new nsDetector(mVerifier,mStatisticsData);
		}else{
			lang = nsPSMDetector.CHINESE;
			this.det = new nsDetector(lang);
		}
		this.amountOfVerifiers = det.getProbableCharsets().length;
		det.Init(this);
	}

	/**
	 * @函数名称:detectCharset
	 * @函数描述:字符集判定
	 * @参数与返回说明:
	 * 		@param in
	 * 		@return
	 * 		@throws IOException
	 * @算法描述:
	 */
	public boolean detectCharset(InputStream in) throws IOException {
		byte[] buf = new byte[MAXLENGTH];
		int len;
		boolean isAscii=true;
		BufferedInputStream imp=null;
		try{
			imp = new BufferedInputStream(in);
			while ((len = imp.read(buf, 0, buf.length)) != -1) {
				if (!det.isAscii(buf, len)) {
					isAscii=false;
					if (det.DoIt(buf, len, false)){
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(imp!=null){
				imp.close();
			}
			if(in!=null){
				in.close();
			}
		}
		det.DataEnd();
		if (isAscii) {
			found = true;
			charsets = new String[] { "US-ASCII" };
		} else if (found) {
			charsets = new String[] { result };
			found=true;
		}else{
			charsets=det.getProbableCharsets();
		}
		return found;
	}
	
	public boolean detectCharset(final String fileName) throws IOException {
		 File file = new File(fileName);
		 if(file.exists()){
			 URL url= file.toURI().toURL();
			 BufferedInputStream in = new BufferedInputStream(url.openStream());
			 return this.detectCharset(in);
		 }else{
			 System.out.println(fileName +"not exists");
			 return false;
		 }
	}
	
	public boolean detectCharset(final URL url) throws IOException {
		BufferedInputStream in = new BufferedInputStream(url.openStream());
		return this.detectCharset(in);
	}
	
	public int getLang() {
		return lang;
	}

	public void setLang(int lang) {
		this.lang = lang;
	}

	public nsVerifier[] getmVerifier() {
		return mVerifier;
	}

	public void setmVerifier(nsVerifier[] mVerifier) {
		this.mVerifier = mVerifier;
	}

	public nsEUCStatistics[] getmStatisticsData() {
		return mStatisticsData;
	}

	public void setmStatisticsData(nsEUCStatistics[] mStatisticsData) {
		this.mStatisticsData = mStatisticsData;
	}
	public String[] getCharsets() {
		return charsets;
	}
	
	
	
	public String getResult() {
		return result;
	}

	public Charset guess() {
	    Charset ret = null;
	    String[] possibilities = det.getProbableCharsets();
	    /*
	     * Detect US-ASCII by the fact, that no exclusion of any Charset was
	     * possible.
	     */
	    if (possibilities.length == this.amountOfVerifiers) {
	      ret = Charset.forName("US-ASCII");
	    } else if(possibilities.length>0) {
	      // He should better return an Array of length zero!
	      String check = possibilities[0];
	      if (check.equalsIgnoreCase("nomatch")) {
	        ret = null;
	      } else {
	        for (int i = 0; ret == null && i < possibilities.length; i++) {
	          try {
	            ret = Charset.forName(possibilities[i]);
	          } catch (UnsupportedCharsetException uce) {
	            ret = null;
	          }
	        }
	      }
	    }
	    return ret;
	  }
	/* (non-Javadoc)
	 * @see com.ytec.mdm.base.util.charsetDetector.jchardet.nsICharsetDetectionObserver#Notify(java.lang.String)
	 */
	@Override
	public void Notify(String charset) {
		// TODO Auto-generated method stub
		found = true;
		result = charset;
	}
}
