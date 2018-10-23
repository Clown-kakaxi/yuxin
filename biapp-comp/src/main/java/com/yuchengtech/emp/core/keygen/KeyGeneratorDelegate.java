package com.yuchengtech.emp.core.keygen;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuchengtech.emp.core.keygen.sequence.SequenceKeyGenerator;
import com.yuchengtech.emp.core.keygen.timeformat.TimeFormatKeyGenerator;
import com.yuchengtech.emp.core.keygen.uuid.UUIDKeyGenerator;
import com.yuchengtech.emp.core.sequence.Sequence;

/**
 * 生成器的实现类
 * 
 * @version 1.0
 * @since 1.0.1
 */
@Repository
public class KeyGeneratorDelegate implements KeyGenerator {

	
	@Autowired
	private UUIDKeyGenerator uuidKeyGen ;
	
	@Autowired
	private TimeFormatKeyGenerator timeKeyGen ;
	
	@Autowired
	private SequenceKeyGenerator seqKeyGen ;
	
	
//	@Override
	public String getUuidKey() {
		return uuidKeyGen.getUuidKey();
	}
	
//	@Override
	public String getUuidKey(String str) {
		return uuidKeyGen.getUuidKey(str);
	}

//	@Override
//	public String getTimeFormatKey() {
//		return timeKeyGen.getTimeFormatKey();
//	}

//	@Override
	public String getTimeFormatKey(Sequence sequence) {
		return timeKeyGen.getTimeFormatKey(sequence);
	}

//	@Override
	public String getTimeFormatKey(Date date, Sequence sequence) {
		return timeKeyGen.getTimeFormatKey(date, sequence);
	}

//	@Override
//	public String getTimeFormatKey(String timeFormat) {
//		return timeKeyGen.getTimeFormatKey(timeFormat);
//	}

//	@Override
	public String getTimeFormatKey(String timeFormat, Sequence sequence) {
		return timeKeyGen.getTimeFormatKey(timeFormat, sequence);
	}

//	@Override
	public String getTimeFormatKey(String timeFormat, Date date, Sequence sequence) {
		return timeKeyGen.getTimeFormatKey(timeFormat, date, sequence);
	}

//	@Override
	public long getNumberKey(String seqKey) {
		return seqKeyGen.getNumberKey(seqKey);
	}

//	@Override
	public long getNumberKey(String seqKey, String roolingKey) {
		return seqKeyGen.getNumberKey(seqKey, roolingKey);
	}

//	@Override
	public long getNumberKey(Sequence sequence) {
		return seqKeyGen.getNumberKey(sequence);
	}

//	@Override
	public String getNumberFormatKey(String seqKey) {
		return seqKeyGen.getNumberFormatKey(seqKey);
	}

//	@Override
	public String getNumberFormatKey(String seqKey, String roolingKey) {
		return seqKeyGen.getNumberFormatKey(seqKey, roolingKey);
	}

//	@Override
	public String getNumberFormatKey(Sequence sequence) {
		return seqKeyGen.getNumberFormatKey(sequence);
	}
	
	
}
