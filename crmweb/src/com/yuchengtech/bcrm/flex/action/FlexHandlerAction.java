package com.yuchengtech.bcrm.flex.action;

//import it.sauronsoftware.jave.AudioAttributes;
//import it.sauronsoftware.jave.Encoder;
//import it.sauronsoftware.jave.EncoderException;
//import it.sauronsoftware.jave.EncodingAttributes;
//import it.sauronsoftware.jave.InputFormatException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionContext;

/**
 * @describtion: flex后台调用action，专门处理关于flex后台调用的处理
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-1-24 下午5:46:44
 */
@Action("/flexHandlerAction")
public class FlexHandlerAction{

	private static final long serialVersionUID = 1L;
//	@Autowired
//	@Qualifier("dsOracle")
//	private DataSource ds;
//	@Autowired
//	private FlexHandlerService flexHandlerService;
//    
//    @Autowired
//	public void init(){
//	  	model = new AdminAuthOrg(); 
//		setCommonService(flexHandlerService);
//	}
    
    /**
     * 获取要播放的文件并输出到客户端
     */
    public void playRecorder(){
    	boolean isTrans = false;
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
    	String filepath = request.getParameter("filepath");
    	//注前台flex转码播放30M文件只花6s左右，后台要花14s左右，故选择前台
//    	if(filepath.endsWith(".wav") || filepath.endsWith(".WAV")){
//    		String destFilePath = wavToMp3(filepath);
//    		if(destFilePath != null){
//    			filepath = destFilePath;
//    			isTrans = true;
//    		}
//    	}
		File file =new File(filepath);
        String fileName=file.getName();
        try {
			fileName= new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/plain;charset=ISO8859-1");  //设置 response 的编码方式
		response.setContentLength((int)file.length());  //写明要下载的文件的大小
        response.setHeader("Content-disposition","attachment;filename="+fileName);  //设定输出文件头 
		FileInputStream fis = null; //读出文件到 i/o 流
		BufferedInputStream buff = null;
		OutputStream myout = null;
		try {
			fis = new FileInputStream(file);
			buff = new BufferedInputStream(fis);
			byte [] b=new byte[1024];             //相当于我们的缓存
			long k=0;                             //该值用于计算当前实际下载了多少字节
			myout = response.getOutputStream();   //从 response 对象中得到输出流,准备下载
			while( k < file.length()){   //开始循环下载
				int j=buff.read(b,0,1024);
				k+=j;
				myout.write(b,0,j);     //将 b 中的数据写到客户端的内存
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				myout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				buff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//如果是从wav转换为mp3的，则删除转换后的文件
		if(isTrans){
			file.delete();
		}
    }
    
    /**
     * 注前台flex转码播放30M文件只花6s左右，后台要花14s左右，请慎重选择:但是前台是先下载完之后再转码
     * wav文件转换为mp3文件
     * @param srcFilePath absolutePath/srcFile.wav
     * @return destFilePath 转换后的文件路径
     */
//    private static String wavToMp3(String srcFilePath){
//    	long s = System.currentTimeMillis();
//    	if(!srcFilePath.endsWith(".wav") && !srcFilePath.endsWith(".WAV")){
//    		return null;
//    	}
//    	String destFilePath = srcFilePath.substring(0, srcFilePath.length() - 4) + ".mp3";
//    	File srcFile = new File(srcFilePath);
//    	File destFile = new File(destFilePath);
//    	try {
//			AudioAttributes audio = new AudioAttributes();
//			audio.setCodec("libmp3lame");
//			audio.setBitRate(new Integer(128000));
//			audio.setChannels(new Integer(2));
//			audio.setSamplingRate(new Integer(44100));
//			EncodingAttributes attrs = new EncodingAttributes();
//			attrs.setFormat("mp3");
//			attrs.setAudioAttributes(audio);
//			Encoder encoder = new Encoder();
//			encoder.encode(srcFile, destFile, attrs);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InputFormatException e) {
//			e.printStackTrace();
//		} catch (EncoderException e) {
//			e.printStackTrace();
//		}
//    	long e = System.currentTimeMillis();
//    	System.out.println("=====================: "+(e-s) + "ms");
//		return destFilePath;
//    }
}
