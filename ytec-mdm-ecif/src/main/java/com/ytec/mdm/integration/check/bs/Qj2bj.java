/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：Qj2bj.java
 * @版本信息：1.0.0
 * @日期：2014-2-21-上午11:36:29
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;

import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：Qj2bj
 * @类描述：全角字符->半角字符转换 
 * @功能描述:只处理全角的空格，全角！到全角～之间的字符，忽略其他。
 * "为额地方考虑12321１２３４５６７８９０～！＠＃￥％……＆×（）－＝——＋		　，。、《》？；：‘“【】｛｝＼｜"
 * 转换成"为额地方考虑123211234567890~!@#￥%……&×()-=——+		 ,。、《》?;:‘“【】{}\|"
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-21 上午11:36:29   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-21 上午11:36:29
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class Qj2bj implements IMsgNodeFilter {
	/**
	 * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
	 */
	private static final char SBC_CHAR_START = 65281; // 全角！

	/**
	 * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
	 */
	private static final char SBC_CHAR_END = 65374; // 全角～

	/**
	 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
	 */
	private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

	/**
	 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
	 */
	private static final char SBC_SPACE = 12288; // 全角空格 12288

	/**
	 * 半角空格的值，在ASCII中为32(Decimal)
	 */
	private static final char DBC_SPACE = ' '; // 半角空格
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	@Override
	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		if (value == null) {
			return value;
		}
		StringBuilder buf = new StringBuilder(value.length());
		char[] ca = value.toCharArray();
		for (int i = 0; i < value.length(); i++) {
			if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
				buf.append((char) (ca[i] - CONVERT_STEP));
			} else if (ca[i] == SBC_SPACE) { // 如果是全角空格
				buf.append(DBC_SPACE);
			} else { // 不处理全角空格，全角！到全角～区间外的字符
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}

}
