package com.yuchengtech.emp.biappframe.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelTypeInfo;
/**
 * <pre>
 * Title:标签类型
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class LabelTypeBS extends BaseBS<BioneLabelTypeInfo> {
}
