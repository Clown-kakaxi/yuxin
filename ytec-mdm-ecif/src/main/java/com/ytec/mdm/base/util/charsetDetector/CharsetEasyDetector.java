/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util.charsetDetector
 * @�ļ�����CharsetEasyDetector.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-12-����1:35:00
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�CharsetEasyDetector
 * @�����������ַ������
 * @��������:�����ַ�����Χ�����ַ���֮��˳��ͱ��벻��ͻ������������GB18030��Χ��󣬰���GBK
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-12 ����1:35:00
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-12 ����1:35:00
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CharsetEasyDetector implements nsICharsetDetectionObserver{
	/**
	 * @��������:MAXLENGTH
	 * @��������:��ȡ����ֽ����������ж��ַ���
	 * @since 1.0.0
	 */
	private static int MAXLENGTH=1024;
	/**
	 * @��������:lang
	 * @��������:�ж��ַ�����Χ
	 * @since 1.0.0
	 */
	private int lang;
	
	/**
	 * @��������:mVerifier
	 * @��������:�Զ����ַ����ж���Χ
	 * @since 1.0.0
	 */
	private nsVerifier[] mVerifier;
	
	/**
	 * @��������:mStatisticsData
	 * @��������:��Ӧ�ж���̬����
	 * @since 1.0.0
	 */
	private nsEUCStatistics[] mStatisticsData;
	
	/**
	 * @��������:det
	 * @��������:�ж���
	 * @since 1.0.0
	 */
	private nsDetector det;
	
	
	private boolean found;
	private String result;
	/**
	 * @��������:prob
	 * @��������:����ַ���
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
	 * @��������:detectCharset
	 * @��������:�ַ����ж�
	 * @�����뷵��˵��:
	 * 		@param in
	 * 		@return
	 * 		@throws IOException
	 * @�㷨����:
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
