/**
 * 
 */
package com.yuchengtech.emp.biappframe.server.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yuchengtech.emp.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.yuchengtech.emp.biappframe.server.entity.BioneServerInfo;
import com.yuchengtech.emp.biappframe.server.service.ServerManagerBS;
import com.yuchengtech.emp.bione.util.JsonUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:提供rest服务相关方法
 * Description: 提供rest服务相关方法
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class RestServerUtils {

	private static Logger logger = LoggerFactory
			.getLogger(RestServerUtils.class);

	private static RestTemplate restTemplate;

	private static JsonUtils jsonUtils;

	private static ServerManagerBS serverBS;

	static {
		restTemplate = new RestTemplate();
		serverBS = SpringContextHolder.getBean("serverManagerBS");
	}

	/**
	 * 调用远程rest服务
	 * 
	 * @param serverNo
	 *            服务标识
	 * @param methodName
	 *            所调用服务的具体方法名称
	 * @param paramMap
	 *            所调用服务的具体方法参数
	 * @param repBeanType
	 *            期望的服务方法返回类型
	 * @param repCollectionType
	 *            期望的服务方法返回bean所装载的集合类型(ArrayList.class)，
	 *            若为null，服务方法返回简单bean；否则转换为指定集合
	 * @param methodType
	 *            期望调用远程rest的方式，暂只支持get和post协议，不区分大小写
	 * 
	 * @return JSON
	 */
	public static <T> Object callRestServer(String serverNo, String methodName,
			Map<String, Object> paramMap, Class<T> repBeanType,
			Class<?> repCollectionType, String methodType) {
		jsonUtils = JsonUtils.nonDefaultMapper();
		String returnJson = "";
		if (serverNo != null && !"".equals(serverNo) && methodName != null
				&& !"".equals(methodName)) {
			// 服务标识和所调用的远程方法名不能为空
			List<BioneServerInfo> serverInfoList = serverBS
					.getEntityListByProperty(BioneServerInfo.class, "serverNo",
							serverNo);
			BioneServerInfo serverInfo = null;
			if (serverInfoList.size() == 1) {
				serverInfo = serverInfoList.get(0);
			}
			// http://ip:port/path
			if (serverInfo != null && serverInfo.getServerIp() != null
					&& !"".equals(serverInfo.getServerIp())
					&& serverInfo.getServerPort() != null
					&& !"".equals(serverInfo.getServerPort())
					&& serverInfo.getServerPath() != null
					&& !"".equals(serverInfo.getServerPath())) {
				// 定制的服务满足要求
				StringBuffer url = new StringBuffer("http://")
						.append(serverInfo.getServerIp()).append(":")
						.append(serverInfo.getServerPort());
				if (serverInfo.getServerPath().indexOf("/") != 0) {
					// 若第一个字符不是"/"
					url.append("/");
				}
				url.append(serverInfo.getServerPath()).append("/")
						.append(methodName).append(".json");
				if (paramMap != null) {
					// 拼接参数
					Iterator<String> it = paramMap.keySet().iterator();
					boolean isFirst = true;
					while (it.hasNext()) {
						String keyTmp = it.next();
						if (isFirst) {
							url.append("?");
							isFirst = false;
						} else {
							url.append("&");
						}
						url.append(keyTmp).append("={").append(keyTmp)
								.append("}");
					}
				}
				// 统一以JSON形式处理服务返回结果
				if (methodType == null || "".equals(methodType)) {
					// 默认用post协议
					methodType = "post";
				}
				if ("post".equals(StringUtils.toLowerEnglish(methodType))) {
					returnJson = restTemplate.postForObject(url.toString(),
							null, String.class, paramMap);
				} else if ("get".equals(StringUtils.toLowerEnglish(methodType))) {
					returnJson = restTemplate.getForObject(url.toString(),
							String.class, paramMap);
				}
				if (returnJson == null || "".equals(returnJson)) {
					return null;
				}
				if (repBeanType == null) {
					// 若类型为null，直接以json形式返回
					return returnJson;
				}
				if (repCollectionType != null) {
					// 若是返回collection
					return jsonUtils.fromJson(returnJson, jsonUtils
							.createCollectionType(repCollectionType,
									repBeanType));
				} else {
					// 若是返回简单类型
					return jsonUtils.fromJson(returnJson, repBeanType);
				}
			} else {
				logger.error("\n==============服务配置有误,请核查==============");
			}
		}
		return null;
	}

}
