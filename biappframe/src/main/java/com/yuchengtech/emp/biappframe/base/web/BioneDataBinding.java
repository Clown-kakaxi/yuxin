/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * <pre>
 * Title:自定义springmvc日期、时间绑定
 * Description: 自定义springmvc日期、时间绑定
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class BioneDataBinding implements WebBindingInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.bind.support.WebBindingInitializer#initBinder
	 * (org.springframework.web.bind.WebDataBinder,
	 * org.springframework.web.context.request.WebRequest)
	 */
	public void initBinder(WebDataBinder binder, WebRequest request) {
		
		List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>(); 
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
		dateFormats.add(new SimpleDateFormat("yyyyMMdd"));
		dateFormats.add(new SimpleDateFormat("yyyy.MM.dd"));

		List<SimpleDateFormat> datetimeFormats = new ArrayList<SimpleDateFormat>();
		datetimeFormats.add(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"));
		datetimeFormats.add(new SimpleDateFormat(
				"yyyyMMdd HH:mm:ss"));
		datetimeFormats.add(new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss"));
		datetimeFormats.add(new SimpleDateFormat(
				"yyyy-MM-dd"));
		datetimeFormats.add(new SimpleDateFormat(
				"yyyyMMdd"));
		datetimeFormats.add(new SimpleDateFormat(
				"yyyy.MM.dd"));

		binder.registerCustomEditor(java.util.Date.class, new BioneDateEditor(
				dateFormats, true));
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new BioneTimestampEditor(datetimeFormats, true));

	}

}
