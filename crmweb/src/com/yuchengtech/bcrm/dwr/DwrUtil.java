package com.yuchengtech.bcrm.dwr;

import java.util.Collection;
import java.util.List;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO
 * @Package: com.yuchengtech.bcrm
 * @author: liuyx
 * @date: 2017年11月9日 上午10:31:32
 */
public class DwrUtil {
	Logger log = LoggerFactory.getLogger(DwrUtil.class);
	/**
	 * 调用页面javascript函数
	 * @param functionName
	 * @param args
	 */
	public void invokeJavascriptFunction(String _funcName, List _args) {
		final String funcName = _funcName;
		final List args = _args;
		Browser.withAllSessions(new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				// 拼接javascript
				script = script.appendScript(funcName + "(");
				for (int i = 0; i < args.size(); i++) {
					if (i != 0) {
						script = script.appendScript(",");
					}
					script = script.appendData(args.get(i));
				}
				script.appendScript(")");
				log.debug(script.toString());
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
