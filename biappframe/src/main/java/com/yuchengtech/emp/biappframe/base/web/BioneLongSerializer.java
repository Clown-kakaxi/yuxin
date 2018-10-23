/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.web;

/**
 * <pre>
 * Title:自定义SpringMVC JSON转换器(Jackson)中对Long型处理
 * Description: 自定义SpringMVC JSON转换器(Jackson)中对Long型处理
 * 				using: entity中对应属性get方法上加上注解@JsonSerialize(using=BioneLongSerializer.class)
 * 				其中JsonSerialize暂用org.codehaus.jackson.map.annotate.JsonSerialize
 * </pre>
 * @author caiqy  caiqy@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 自定义返回JSON 数据格中整型格式化处理
 *
 */
public class BioneLongSerializer extends JsonSerializer<Long> {
	@Override
	public void serialize(Long value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeString(value.toString());
	}

}