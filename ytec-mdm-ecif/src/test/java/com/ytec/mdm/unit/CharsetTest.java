/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.unit
 * @文件名：CharsetTest.java
 * @版本信息：1.0.0
 * @日期：2014-5-12-下午5:37:52
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.unit;

import java.io.IOException;

import com.ytec.mdm.base.util.charsetDetector.CharsetEasyDetector;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsGB18030Verifier;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsUTF8Verifier;
import com.ytec.mdm.base.util.charsetDetector.jchardet.nsVerifier;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CharsetTest
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-12 下午5:37:52   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-12 下午5:37:52
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
