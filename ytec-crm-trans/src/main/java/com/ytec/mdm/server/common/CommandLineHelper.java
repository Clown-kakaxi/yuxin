/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����CommandLineHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-28-����2:20:55
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CommandLineHelper
 * @����������������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-28 ����2:20:55   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-28 ����2:20:55
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CommandLineHelper {
	private Options opt;
	private CommandLine cl;
	
	public CommandLineHelper() {
		super();
		opt = new Options();
	}
	
	/**
	 * @��������:optionsParser
	 * @��������:��������
	 * @�����뷵��˵��:
	 * 		@param args
	 * 		@return
	 * @�㷨����:
	 */
	public boolean optionsParser(String args[]){
		opt.addOption("s", "sync", false, " run synchro server");
		String formatstr = "EcifServerLauncher [-s/--sync] arg";
		CommandLineParser parser = new PosixParser();
		try {
			// ����Options�Ͳ���
			cl = parser.parse(opt, args);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(formatstr);
			return false;
		}
		return true;
	}

	public Options getOpt() {
		return opt;
	}

	public CommandLine getCl() {
		return cl;
	}
	
}
