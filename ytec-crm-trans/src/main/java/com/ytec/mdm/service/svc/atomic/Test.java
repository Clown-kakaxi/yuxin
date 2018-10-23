package com.ytec.mdm.service.svc.atomic;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.ytec.mdm.base.util.StringUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			Map<String,String> mapEnName = splitString("NICHINGCO.,LTD.", "enName");
			System.out.println(mapEnName);
			System.out.println(mapEnName.get("0").getBytes().length);
			
			String s="　";
			System.out.println("长度"+s.getBytes().length);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}

	
	public static Map<String, String> splitString(String str, String type) throws Exception {
		if(str!=null && !"".equals(str.trim())){
			int custNamecount = 45;
			int addrcount = 31;
//			str=str.trim();
//			str = str.replace(" ", "");
			if (type.toString().equals("custName")) {
				return splitraddress(str, custNamecount);
			} else if (type.toString().equals("addr")) { 
				return splitraddress(str, addrcount); 
			}else if (type.toString().equals("enName")){
				return splitraddress(str, 31);
			}
		}
		return null;
	}
	/**
	 * 将字符串分割
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> splitraddress(String str, int countType) throws Exception {
		char[] charArray = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		String nowStr = "";
		for (int i = 0; i <charArray.length; i++) {
			if (i == 0) {
				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// 检测第一个是否是汉子
					sb.append(" " + charArray[i]); // 是汉字接添加一个空格
				} else {
					sb.append(charArray[i]); // 不是汉子直接拼接
				}
			} else {
				if ((sb.toString().getBytes().length) >= countType || i == (charArray.length-1)) {//判断长度是否超过规定长度或者是否是最后一个字符
					
					if (sb.toString().getBytes().length == 0 || i==(charArray.length-1)) {
						sb.append(nowStr);
						char bs = charArray[i-1];//前面一个数
						if((bs >= 0x4e00) && (bs <= 0x9fbb)){//如果前面那个数是汉子
							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子直接拼接
								sb.append(charArray[i]+" ");
							}else{
								if(returnSpecial(charArray[i])){
									sb.append(charArray[i]);
								}else{
								   sb.append(" "+charArray[i]);
								}
							}
						}else{//前面那个不是汉子
							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子
								if(returnSpecial(bs)){
									sb.append(charArray[i]);
								}else{
								    sb.append(" "+charArray[i]);
								}
							}else{//当前不是汉子
								if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
								    sb.append(" "+charArray[i]);
								}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
									 sb.append(" "+charArray[i]);
								}else{
								     sb.append(charArray[i]);
								}
							}
						}
						map.put("" + count, sb.toString());
					}else{
						char bs = charArray[i - 1];//前面一个数
						if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// 检测最后一个是否是汉子
							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// 如果最后一个是汉子，判断前面一个是否是汉字是汉子增加空格
								if ((sb.toString().getBytes().length) >= countType) {
									sb.append(" "); // 是汉字接添加一个空格
								} else {
									sb.append(charArray[i] + " ");
								}
							} else {//最后一个不是汉子
								if ((sb.toString().getBytes().length) <= countType && i == charArray.length) {
									if(returnSpecial(bs)){
										sb.append(charArray[i]);
									}else{
										sb.append(" "+charArray[i]);
									}
									
								}
								
							}
							nowStr = " " + charArray[i];
							
						} else {// 当前不是汉字，判断前面一个是否是汉字，如果前面是汉子不增加，如果前面不是汉字就增加
							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {
								if(returnSpecial(charArray[i])){
									sb.append(charArray[i]);
								}else{
								    sb.append(" "+charArray[i]);
								}
							} else {
								if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
								    sb.append(" "+charArray[i]);
								}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
									 sb.append(" "+charArray[i]);
								}else{
								     sb.append(charArray[i]);
								}
							}
							nowStr = "";
						}
						map.put("" + count, sb.toString());
						count++;
						sb = new StringBuffer();
						continue;
					}
					
					
				}
				if (sb.toString().getBytes().length == 0) {
					sb.append(nowStr);
					nowStr="";
					char bs = charArray[i-1];//前面一个数
					if((bs >= 0x4e00) && (bs <= 0x9fbb)){//如果前面那个数是汉子
						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子直接拼接
							sb.append(charArray[i]);
						}else{
							if(returnSpecial(charArray[i])){
							   sb.append(charArray[i]);
							}else{
							   sb.append(" "+charArray[i]);
							}
						}
					}else{
						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子
							if(returnSpecial(bs)){
								sb.append(charArray[i]);
							}else{
							    sb.append(" "+charArray[i]);
							}
						}else{
							if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
							    sb.append(" "+charArray[i]);
							}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
								 sb.append(" "+charArray[i]);
							}else{
								 sb.append(charArray[i]);
							}
							
						}
					}
					
				} else {
					char bs = charArray[i - 1];
					char chr = charArray[i];
					if ((chr >= 0x4e00) && (chr <= 0x9fbb)) {// 当前这个字是汉字
						if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// 前面那个是否是汉字
							sb.append(chr);
						} else {
							if(returnSpecial(bs)){
								sb.append(chr);
							}else{
							    sb.append(" "+chr);
							}
						}

					} else {// 当前拿到的不是汉子
						if (((bs >= 0x4e00) && (bs <= 0x9fbb))) {// 如果他前面的是汉子
							if(returnSpecial(chr)){
								sb.append(chr);
							}else{
							    sb.append(" "+chr);
							}
						} else {
							if(returnSpecial(bs) && !returnSpecial(chr)){
								sb.append(" "+chr);
							}else if(!returnSpecial(bs) && returnSpecial(chr)){
								sb.append(""+chr);
							}else{
							    sb.append(chr);
							}
						}
					}
				}
			}
		}	
		return map;
	}

	
	public static boolean returnSpecial(char chr){
		
		boolean flag = false;
		char[] chrs = {'（','）','【','】','，','。','？','；','、','《','》','：','￥','　'};
		for(int i =0;i<chrs.length;i++){
			if(chr==chrs[i]){
				flag=true;
			}
		}
		return flag;
	}
}
