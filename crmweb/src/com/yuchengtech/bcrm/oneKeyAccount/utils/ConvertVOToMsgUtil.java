package com.yuchengtech.bcrm.oneKeyAccount.utils;

import java.util.List;

import com.yuchengtech.bcrm.oneKeyAccount.model.CommonMsgFieldVO;

/**
 * 报文字段转换具体报文工具
 * @author wx
 *
 */
public class ConvertVOToMsgUtil {
/*
	public static void main(String[] args) {
		//System.out.println("测试：【"+("这是一段中文".length())+"】");
		ConvertVOToMsgUtil  convertVOToMsgUtil = new ConvertVOToMsgUtil();
//		String ret = convertVOToMsgUtil.spliceFixedLengthField("这是一段中文", 44);
//		System.out.println("ret:["+ret+"]\n ret长度:["+ret.length()+"]");
		List<CommonMsgFieldVO> fields = new ArrayList<CommonMsgFieldVO>();
		for(int i = 0; i< 10; i++){
			CommonMsgFieldVO vo = new CommonMsgFieldVO();
			vo.setName("name"+i);
			vo.setLength(10+i);
			vo.setContent("content["+i+"]");
			fields.add(vo);
		}
		String ret = convertVOToMsgUtil.convertToFixedLengthMsg(fields);
		System.out.println("ret:["+ret+"]");
	}
	*/
	
	
	/**
	 * 将报文字段VO转换成报文字符串
	 * @param fields
	 * @return
	 */
	public static String convertToFixedLengthMsg(List<CommonMsgFieldVO> fields, String charSetName) {
		StringBuffer retStr = new StringBuffer();//返回信息
		if(fields == null || fields.size() < 1){
			return null;//
		}
		try {
			for (CommonMsgFieldVO field : fields) {
				if(field == null){
					continue;
				}
				//String name_field = field.getName();//name（标示）
				int length_field = field.getLength();//长度
				String content_field = field.getContent();//内容
				//需要尽心转码时
				if(charSetName != null && !charSetName.equals("")){
					content_field = new String(content_field.getBytes(), charSetName);
				}
				//校验字段内容是否超长，如果超长则生成报文失败
				if(!checkFieldContentLength(content_field, length_field)){
					return null;
				}
				if(length_field == -1){//如果是变长的字段
					
				}else if(length_field >= 1){
					retStr.append(spliceFixedLengthField(content_field, length_field));//
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr.toString();
	}
	
	/**
	 * 根据字段内容及给定的字段长度计算最后的字段内容
	 * @param fieldContent
	 * @param fieldLength
	 * @return
	 */
	private static String spliceFixedLengthField(String fieldContent, int fieldLength) {
		if(fieldContent != null && fieldLength != 0){
			if(fieldLength == -1){
				int strLength = fieldContent.length();//字符串长度
				String lengthHead = "0" + strLength;
				fieldContent = lengthHead + "" +fieldContent;
			}else{
				int blankCount = fieldLength - fieldContent.length();//需要补充的空格的数量
				char[] charArray = fieldContent.toCharArray();
				for (char c : charArray) {
					if(isChinese(c)){
						blankCount -= 1;
					}
				}
				if(blankCount > 0){
					for(int i = 0; i < blankCount; i++){
						fieldContent += " ";
					}
				}
			}
		}
		return fieldContent;
	}
	
	
	/**
	 * 校验字符是否是中文
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS){
			return true;
		}else{
			return false;
		}

	}
	
	
	/**
	 * 校验字段内容是否超长
	 * @param content		字段内容
	 * @param fieldLength	字段长度
	 * @return
	 */
	private static boolean checkFieldContentLength(String content, int fieldLength){
		boolean res = false;
		if(content == null || fieldLength == 0){
			return false;
		}
		//如果是变长直接返回
		if(fieldLength == -1){
			return true;
		}else{
			int contentLength = content.length();
			if(contentLength <= fieldLength){
				res = true;
			}else{
				res = false;
			}
		}
		
		return res;
	}
}
