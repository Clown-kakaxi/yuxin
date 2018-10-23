/**
 * 
 */
package com.yuchengtech.emp.ecif.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author guanyb guanyb@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CardUtil {
	/*********************************** 身份证验证开始 ****************************************/
	/**
	 * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
	 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
	 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
	 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
	 * X 9 8 7 6 5 4 3 2
	 */

    /**
     * 检查身份证日期是否合法
     * 
     * @param str
     * @return 0 合法，1 月份不在规定范围内，2 日期不在规定范围内
     */
    public static int checkPersonNoDate(String str){ 
           String year = str.substring(0, 4);
           String month = str.substring(4, 6);
           String day = str.substring(6, 8);

           int maxDays = 31;

           int y = Integer.parseInt(year);
           int m = Integer.parseInt(month);
           int d = Integer.parseInt(day);

           if(m > 12 || m < 1){
        	   //log.info("您输入的月份不在规定范围内[" + m + "]");
               return 1;
           } else if(m == 4 || m == 6 || m == 9 || m == 11){
               maxDays = 30;
           } else if (m == 2) {
               if (y % 4 > 0) {
            	   maxDays = 28;
               } else if (y % 100 == 0 && y % 400 > 0) {
            	   maxDays = 28;
               } else {
            	   maxDays = 29;
               }
            }
           
           if(d <1 || d > maxDays){
        	   //log.info("您输入的日期不在规定范围内[" + d + "]");
        	   return 2;
           }
        return 0;
    }
	
	public static boolean IDCardValidate2(String str) throws ParseException {
    	
    	if(!Pattern.matches("^[0-9]{17}[0-9X]{1}$", str)){
    		return false;
    	}
    	
    	//判断身份证是否在地区编码范围
//    	JPABaseDAO baseDAO=(JPABaseDAO) SpringContextUtils.getBean("baseDAO");
//    	
//    	List<String> list=(List<String>) baseDAO.findByNativeSQLWithIndexParam("SELECT t.STD_CODE FROM STD_CODE  t where t.STD_CATE = ? AND t.STD_CODE=? ", EcifConstant.PROVINCE_CATE, str.substring(0, 2));
//    	
    	
//    	if(list==null || list.size()==0){
//    		return false;
//    	}
    	
    	Date birDate = ConvertUtils.getDateStrToData(str.substring(6, 14));
    	if((null == birDate) || (ConvertUtils.getDateStrToData("19000101").after(birDate) || (new Date()).before(birDate))){
    		return false;
    	}
    	
//    	if(!StringUtil.isEmpty(sex)){
//	    	int num=str.charAt(16);
//	    	if(EcifConstant.FEMAN.equals(sex)){
//	    		if(num%2==1){
//	    			return false;
//	    		}
//	    		
//	    	}
//	    	if(EcifConstant.MAN.equals(sex)){
//	    		if(num%2==0){
//	    			return false;
//	    		}
//	    	}
//    	}
    	
    	int checkDateRst = checkPersonNoDate(str.substring(6, 14));
    	if (0 != checkDateRst) {
    		return false;
		}
    	return true;
    }
	
	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static String IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(
						strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "身份证生日不在有效范围。";
			return errorInfo;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings("unchecked")
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 其他证件号码的验证
	 * @param IDStr
	 * @return
	 */
	public static String otherCardValidate(String IDStr){
		String errorInfo = "";// 记录错误信息
		if (IDStr.length() < 6) {
			errorInfo = "号码长度必须大于等于6位。";
			return errorInfo;
		}
		if(IDStr.indexOf(" ") != -1){
			errorInfo = "号码里不能有空格。";
			return errorInfo;
		}		
		return errorInfo;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		//String IDCardNum = "210102820826411";
		//String IDCardNum = "210102198208264114";
		String IDCardNum = "230102199708080002";
		System.out.println(CardUtil.IDCardValidate(IDCardNum));
		//System.out.println(CardUtil.isDate("1996-02-29"));
	}
	/*********************************** 身份证验证结束 ****************************************/
}
