/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server.common
 * @文件名：CommandLineHelper.java
 * @版本信息：1.0.0
 * @日期：2014-5-28-下午2:20:55
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.server.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CommandLineHelper
 * @类描述：参数解析帮助类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-28 下午2:20:55   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-28 下午2:20:55
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
	 * @函数名称:optionsParser
	 * @函数描述:参数解析
	 * @参数与返回说明:
	 * 		@param args
	 * 		@return
	 * @算法描述:
	 */
	public boolean optionsParser(String args[]){
		opt.addOption("s", "sync", false, " run synchro server");
		String formatstr = "EcifServerLauncher [-s/--sync] arg";
		CommandLineParser parser = new PosixParser();
		try {
			// 处理Options和参数
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
