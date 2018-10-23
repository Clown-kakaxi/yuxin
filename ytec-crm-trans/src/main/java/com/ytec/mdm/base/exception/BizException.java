/**
 * 
 */
package com.ytec.mdm.base.exception;

import java.io.Serializable;

/**
 * <pre>
 * Title:ҵ���쳣��
 * Description: ���쳣Ϊ���� ����쳣�������ںͿͻ��˽����ĵط�ͳһ�����쳣��BS����DAO���в����쳣���д���ֱ���׳�
 * </pre>
 * @author mengzx  mengzx@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
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
