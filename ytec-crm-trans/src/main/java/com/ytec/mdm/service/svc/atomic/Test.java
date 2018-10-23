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
			
			String s="��";
			System.out.println("����"+s.getBytes().length);
			
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
	 * ���ַ����ָ�
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
				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// ����һ���Ƿ��Ǻ���
					sb.append(" " + charArray[i]); // �Ǻ��ֽ����һ���ո�
				} else {
					sb.append(charArray[i]); // ���Ǻ���ֱ��ƴ��
				}
			} else {
				if ((sb.toString().getBytes().length) >= countType || i == (charArray.length-1)) {//�жϳ����Ƿ񳬹��涨���Ȼ����Ƿ������һ���ַ�
					
					if (sb.toString().getBytes().length == 0 || i==(charArray.length-1)) {
						sb.append(nowStr);
						char bs = charArray[i-1];//ǰ��һ����
						if((bs >= 0x4e00) && (bs <= 0x9fbb)){//���ǰ���Ǹ����Ǻ���
							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���ֱ��ƴ��
								sb.append(charArray[i]+" ");
							}else{
								if(returnSpecial(charArray[i])){
									sb.append(charArray[i]);
								}else{
								   sb.append(" "+charArray[i]);
								}
							}
						}else{//ǰ���Ǹ����Ǻ���
							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���
								if(returnSpecial(bs)){
									sb.append(charArray[i]);
								}else{
								    sb.append(" "+charArray[i]);
								}
							}else{//��ǰ���Ǻ���
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
						char bs = charArray[i - 1];//ǰ��һ����
						if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// ������һ���Ƿ��Ǻ���
							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// ������һ���Ǻ��ӣ��ж�ǰ��һ���Ƿ��Ǻ����Ǻ������ӿո�
								if ((sb.toString().getBytes().length) >= countType) {
									sb.append(" "); // �Ǻ��ֽ����һ���ո�
								} else {
									sb.append(charArray[i] + " ");
								}
							} else {//���һ�����Ǻ���
								if ((sb.toString().getBytes().length) <= countType && i == charArray.length) {
									if(returnSpecial(bs)){
										sb.append(charArray[i]);
									}else{
										sb.append(" "+charArray[i]);
									}
									
								}
								
							}
							nowStr = " " + charArray[i];
							
						} else {// ��ǰ���Ǻ��֣��ж�ǰ��һ���Ƿ��Ǻ��֣����ǰ���Ǻ��Ӳ����ӣ����ǰ�治�Ǻ��־�����
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
					char bs = charArray[i-1];//ǰ��һ����
					if((bs >= 0x4e00) && (bs <= 0x9fbb)){//���ǰ���Ǹ����Ǻ���
						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���ֱ��ƴ��
							sb.append(charArray[i]);
						}else{
							if(returnSpecial(charArray[i])){
							   sb.append(charArray[i]);
							}else{
							   sb.append(" "+charArray[i]);
							}
						}
					}else{
						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���
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
					if ((chr >= 0x4e00) && (chr <= 0x9fbb)) {// ��ǰ������Ǻ���
						if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// ǰ���Ǹ��Ƿ��Ǻ���
							sb.append(chr);
						} else {
							if(returnSpecial(bs)){
								sb.append(chr);
							}else{
							    sb.append(" "+chr);
							}
						}

					} else {// ��ǰ�õ��Ĳ��Ǻ���
						if (((bs >= 0x4e00) && (bs <= 0x9fbb))) {// �����ǰ����Ǻ���
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
		char[] chrs = {'��','��','��','��','��','��','��','��','��','��','��','��','��','��'};
		for(int i =0;i<chrs.length;i++){
			if(chr==chrs[i]){
				flag=true;
			}
		}
		return flag;
	}
}
