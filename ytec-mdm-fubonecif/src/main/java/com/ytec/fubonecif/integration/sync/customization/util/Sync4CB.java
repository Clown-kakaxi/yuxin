package com.ytec.fubonecif.integration.sync.customization.util;

import com.ytec.mdm.base.util.MdmConstants;

public class Sync4CB {
	public static void main(String[] args) {
		
		String data2cb = "上海浦东新区中环以内陆家嘴街道世纪大道1168号东方金融广场A座19F华一银行1902室";
		int counter = 0;
		int MAX_LEN = 135;
		char SPACE = MdmConstants.SPACE;
		
		StringBuffer result = new StringBuffer();
		boolean preIsAssic = false;
		for (char ch : data2cb.toCharArray()) {
			if (Character.getType(ch) == Character.OTHER_LETTER) {
				//当前为汉字，并且之前为ASSIC字符
				if (preIsAssic) {
					result.append(SPACE);
					result.append(ch);
					preIsAssic = false;
				}
				//当前为汉字，并用之前为非ASSIC字符（或是刚从第一个字开始读取）
				else {
					result.append(ch);
				}
				counter+=2;
			}
			else {
				//当前为ASSIC，并且之前还是ASSIC字符
				if (preIsAssic) {
					result.append(ch);
				}
				//当前为ASSIC，但之前为汉字
				else {
					preIsAssic = true;
					result.append(SPACE);
					result.append(ch);
				}
				counter+=1;
			}
			
			if(counter+1>MAX_LEN){
				result.append(SPACE);
				break;
			}else if(counter+2>MAX_LEN){
				result.append(SPACE);
				break;
			}
		}
		System.out.println(result.toString());
//		System.out.printf("%d\n", ' ');
	}
}
