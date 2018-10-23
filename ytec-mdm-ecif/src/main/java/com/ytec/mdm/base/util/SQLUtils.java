package com.ytec.mdm.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <pre>
 * Title:SQL������
 * Description: SQL��صĲ���������
 * 
 * </pre>
 * 
 * @author mengzx mengzx@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
 * </pre>
 */
public class SQLUtils {

	
	  /**
	   * ���ݲ�ѯ�б�SQL����Զ������ѯ��¼������SQL���
	   * @param strSQL String
	   * @return String
	   */
	  public static String buildCountSQL(Object strSQL) {
	    
		  StringBuffer countBuff = new StringBuffer();
	    if (strSQL != null) {
	      String sql = null;
	      if (strSQL instanceof String) {
	        sql = (String) strSQL;
	      }
	      else if (strSQL instanceof StringBuffer) {
	        sql = ( (StringBuffer) strSQL).toString();
	      }
	      if (containsDistinctKeywords(sql)) {
	    	  
	    	//��ѯ�ֶ�  
	    	String queryField = sql.substring(findStrPosition(sql, "distinct") + 8,
                    findStrPosition(sql, "from")).trim();  
	    	
	        countBuff.append("select count(distinct ")
	            .append(queryField).append(") ");
	      }else {
	        countBuff.append("select count(*) ");
	      }

	      countBuff.append(removeOrderBy(trimFrom(sql)));
	    }
	    return countBuff.toString();
	  }
	  
	  /**
	   * ȡsql����"from"֮����ַ���
	   * @param sql String
	   * @return String
	   */
	  public static String trimFrom(String sql) {
	    String patternString = "[Ff][Rr][Oo][Mm]";
	    Pattern pattern = Pattern.compile(patternString);
	    Matcher matcher = pattern.matcher(sql);

	    return matcher.find() ? sql.substring(matcher.start()) : ""; //������ַ�
	  }
	  
	  /**
	   * �ж�SQL������Ƿ����distinct�ؼ���
	   * @param sql
	   * @return
	   */
	  public static boolean containsDistinctKeywords(String sql) {
		  
		    StringBuffer patternString = new StringBuffer();
		    patternString.append("\\s*").append(buildRegexStr("select"))
		        .append("\\s*").append(buildRegexStr("distinct"));

		    Pattern pattern = Pattern.compile(patternString.toString());
		    Matcher matcher = pattern.matcher(sql);

		    return matcher.find() && matcher.start() == 0 ? true : false;
		  }
	  
	  
	  /**
	   * �����ַ�������������ʽ
	   * ����where����[Ww][Hh][Ee][Rr]��������ʽ
	   * @param str String
	   * @return String
	   */
	  public static String buildRegexStr(String str) {
		
		//ת����д  
	    String upperCaseStr = str.toUpperCase();
	    
	    char[] strArr = upperCaseStr.toCharArray();
	    char[] regexArr = new char[str.length() * 4];
	    for (int i = 0; i < strArr.length; i++) {
	      regexArr[4 * i] = '[';
	      regexArr[4 * i + 1] = strArr[i];
	      regexArr[4 * i + 2] = (char) (strArr[i] + 32); // to lower case
	      regexArr[4 * i + 3] = ']';
	    }

	    return String.copyValueOf(regexArr);
	  }
	  
	  /**
	   *�滻�������order by �Ӿ�Ϊ" ORDER BY "
	   * @param sql String
	   * @return String
	   */
	  public static String replaceOrderBy(String sql) {
	    String patternString = "\\s*[Oo][Rr][Dd][Ee][Rr]\\s+[Bb][Yy]\\s*";
	    Pattern pattern = Pattern.compile(patternString);
	    Matcher matcher = pattern.matcher(sql);
	    String wantStr = matcher.replaceAll(" ORDER BY "); //������ַ�
	    return wantStr;
	  }
	  
	  /**
	   * ���� sql����е�order by �Ӿ�
	   * @param sql
	   * @return String
	   */
	  public static String removeOrderBy(String sql) {
	    String patternString = "\\sORDER\\sBY\\s[a-zA-Z0-9\\.\\_\\,\\s]+";
	    Pattern pattern = Pattern.compile(patternString);
	    Matcher matcher = pattern.matcher(replaceOrderBy(sql));
	    String resultStr = matcher.replaceAll("");
	    return resultStr;
	  }
	  
	  /**
	   * ����ƥ���ַ�����λ��
	   * @param sql
	   * @param targetStr
	   * @return
	   */
	  public static int findStrPosition(String sql, String targetStr) {
		    String patternString = buildRegexStr(targetStr);
		    Pattern pattern = Pattern.compile(patternString);
		    Matcher matcher = pattern.matcher(sql);

		    return matcher.find() ? matcher.start() : -1;
		  }
}
