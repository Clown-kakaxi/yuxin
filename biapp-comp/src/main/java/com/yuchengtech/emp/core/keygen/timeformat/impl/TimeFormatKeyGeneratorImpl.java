package com.yuchengtech.emp.core.keygen.timeformat.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.yuchengtech.emp.core.keygen.timeformat.TimeFormatKeyGenerator;
import com.yuchengtech.emp.core.sequence.Sequence;
import com.yuchengtech.emp.core.sequence.text.SequenceFormat;

/*
 * 事件格式的Key生成器实现类
 */
@Repository
public class TimeFormatKeyGeneratorImpl implements TimeFormatKeyGenerator {

	/* formater */
	private DateFormat formater = null;
	
	
//	@Override
//	public String getTimeFormatKey() {
//		return this.getTimeFormatKey("yyyyMMddHHmmssSSS");
//	}

//	@Override
	public String getTimeFormatKey(Sequence sequence) {
		return this.getTimeFormatKey("yyyyMMddHHmmss", sequence);
	}

//	@Override
	public String getTimeFormatKey(Date date, Sequence sequence) {
		return this.getTimeFormatKey("yyyyMMddHHmmss", date, sequence);
	}
	
//	@Override
//	public String getTimeFormatKey(String timeFormat) {
//		this.formater = new SimpleDateFormat(timeFormat);
//		return this.formater.format(new java.util.Date());
//	}

//	@Override
	public String getTimeFormatKey(String timeFormat, Sequence sequence) {
		return this.getTimeFormatKey(timeFormat, new java.util.Date(), sequence);
	}
	
//	@Override
	public String getTimeFormatKey(String timeFormat, java.util.Date date, Sequence sequence) {
		this.formater = new SimpleDateFormat(timeFormat);
		String datestr = this.formater.format(date);
		
		long val = sequence.nextval();
		return datestr + new SequenceFormat(sequence).format(val);
		
//		return datestr + new SequenceFormat(sequence).format(sequence.nextval());
	}
	
	
}
