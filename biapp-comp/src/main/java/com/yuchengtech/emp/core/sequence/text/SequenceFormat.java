package com.yuchengtech.emp.core.sequence.text;

import com.yuchengtech.emp.core.sequence.Sequence;

/**
 * Sequence Format
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class SequenceFormat {

	
	private int length = -1;
	
	private char fillchar = '0';
	
	
	public SequenceFormat(Sequence sequence) {
		this.length = String.valueOf(sequence.getMaxVal()).length();
	}
	
	public SequenceFormat(int length, char fillchar) {
		this.length = length;
		this.fillchar = fillchar;
	}
	
	public SequenceFormat(Sequence sequence, char fillchar) {
		this(sequence);
		this.fillchar = fillchar;
	}
	
	public String format(long value) {
		String dest = String.valueOf(value);
		while(dest.length()<this.length) {
			dest = this.fillchar + dest ;
		}
		return dest;
	}
	
	public long parse(String seqstr) {
		if(seqstr==null || seqstr.trim().length()<=0) {
			throw new NullPointerException("Sequence-str to parse should not be null !");
		}
		seqstr = seqstr.substring(seqstr.lastIndexOf(this.fillchar) + String.valueOf(this.fillchar).length());
		return Long.parseLong(seqstr);
	}
	
	
}
