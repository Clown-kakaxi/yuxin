package com.ytec.fubonecif.integration.sync.customization.util;

import com.ytec.mdm.base.util.MdmConstants;

public class Sync4CB {
	public static void main(String[] args) {
		
		String data2cb = "�Ϻ��ֶ������л�����½����ֵ����ʹ��1168�Ŷ������ڹ㳡A��19F��һ����1902��";
		int counter = 0;
		int MAX_LEN = 135;
		char SPACE = MdmConstants.SPACE;
		
		StringBuffer result = new StringBuffer();
		boolean preIsAssic = false;
		for (char ch : data2cb.toCharArray()) {
			if (Character.getType(ch) == Character.OTHER_LETTER) {
				//��ǰΪ���֣�����֮ǰΪASSIC�ַ�
				if (preIsAssic) {
					result.append(SPACE);
					result.append(ch);
					preIsAssic = false;
				}
				//��ǰΪ���֣�����֮ǰΪ��ASSIC�ַ������Ǹմӵ�һ���ֿ�ʼ��ȡ��
				else {
					result.append(ch);
				}
				counter+=2;
			}
			else {
				//��ǰΪASSIC������֮ǰ����ASSIC�ַ�
				if (preIsAssic) {
					result.append(ch);
				}
				//��ǰΪASSIC����֮ǰΪ����
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
