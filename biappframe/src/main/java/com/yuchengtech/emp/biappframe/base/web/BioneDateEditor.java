/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.web;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * <pre>
 * Title:自定义springmvc日期类型转换
 * Description: 自定义springmvc日期类型转换
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
public class BioneDateEditor extends PropertyEditorSupport {
	private final List<SimpleDateFormat> dateFormats;

	private final boolean allowEmpty;

	private final int exactDateLength;

	public BioneDateEditor(List<SimpleDateFormat> dateFormats, boolean allowEmpty) {
		this.dateFormats = dateFormats;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}

	public BioneDateEditor(List<SimpleDateFormat> dateFormats, boolean allowEmpty,
			int exactDateLength) {
		this.dateFormats = dateFormats;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}

	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if ((this.allowEmpty && !StringUtils.hasText(text))
				|| this.dateFormats == null) {
			// Treat empty String as null value.
			setValue(null);
		} else if (text != null && this.exactDateLength >= 0
				&& text.length() != this.exactDateLength) {
			throw new IllegalArgumentException(
					"Could not parse date: it is not exactly"
							+ this.exactDateLength + "characters long");
		} else {
			Boolean successFlag = false;
			for (int i = 0; i < this.dateFormats.size(); i++) {
				try {
					setValue(this.dateFormats.get(i).parse(text));
					successFlag = true;
					break;
				} catch (ParseException ex) {
					continue;
				}
			}
			if (!successFlag) {
				throw new IllegalArgumentException("Could not parse date: "
						+ text);
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		if (value != null) {
			String valueReturn = "";
			for (int i = 0; i < this.dateFormats.size(); i++) {
				valueReturn = this.dateFormats.get(i).format(value);
				if(!"".equals(valueReturn)){
					break;
				}
			}
			return valueReturn;
		} else {
			return "";
		}
	}
}
