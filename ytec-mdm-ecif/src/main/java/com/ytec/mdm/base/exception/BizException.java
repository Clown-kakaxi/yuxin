/**
 * 
 */
package com.ytec.mdm.base.exception;

import java.io.Serializable;

/**
 * <pre>
 * Title:业务异常类
 * Description: 此异常为非受 检查异常，建议在和客户端交互的地方统一捕获异常，BS或者DAO层中不对异常进行处理直接抛出
 * </pre>
 * @author mengzx  mengzx@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class BizException  extends RuntimeException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5725873895114541005L;

	protected String msg;

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public BizException(Throwable cause) {
        super(cause);
    }
    
    public String getMsg() {
        return msg;
    }
}
