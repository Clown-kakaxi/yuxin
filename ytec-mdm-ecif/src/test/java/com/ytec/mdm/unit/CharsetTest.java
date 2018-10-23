/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.unit
 * @�ļ�����CharsetTest.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-12-����5:37:52
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.unit;

import java.io.IOException;

import com.ytec.mdm.base.util.charsetDetector.CharsetEasyDetector;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsGB18030Verifier;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsUTF8Verifier;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsVerifier;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CharsetTest
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-12 ����5:37:52   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-12 ����5:37:52
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CharsetTest {
	
	public static void main(String[] args)throws IOException{
		nsVerifier[] mVerifier = new nsVerifier[] { new nsUTF8Verifier(),new nsGB18030Verifier()};
//		
		CharsetEasyDetector charDect = new CharsetEasyDetector();
       // File file = new File("E:/ECIF2.0/ytec-mdm-ecif/conf/src/serverConfig.xml");
		mVerifier=null;
        charDect.setmVerifier(mVerifier);
        charDect.init();
        if(charDect.detectCharset("E:/ECIF2.0/ytec-mdm-ecif/conf/src/serverConfig.xml")){
        	System.out.println("true,"+charDect.getResult());
        }else{
        	System.out.println("guess,"+charDect.guess());
        }
    }
}
