package com.ytec.mdm.service.svc.atomic;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
public class PushCustInformation implements IEcifBizLogic {

	private static Logger log = LoggerFactory.getLogger(PushCustInformation.class);

	private static String[] ILLEGAL_STRS = { "*", "-", "\\", "\"" };

	// 操作数据库
	private static JPABaseDAO baseDAO;

	public void process(EcifData ecifData) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**
		 * 获取请求报文数据
		 */
		Element body = ecifData.getBodyNode();

		String bodyStr = body.asXML();
		for (String ILLEGAL_STR : ILLEGAL_STRS) {
			if (bodyStr.contains(ILLEGAL_STR)) {
				String msg = String.format("%s(%s)", ErrorCode.ERR_ECIF_INVALID_REQ_PARA.getChDesc(), ILLEGAL_STR);
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_ECIF_INVALID_REQ_PARA.getCode(), msg);
				return;
			}
		}
		String txCode = body.element("txCode").getTextTrim(); // 交易编码
		String columns = body.element("columns").getTextTrim(); // 查询出的结果

		String querytype = body.element("where").getTextTrim(); // 查询的条件
		String tableName = body.element("table").getTextTrim(); // 表名

		Map<String, String> colunmsmap = new LinkedHashMap<String, String>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("select", "select ");

		if (StringUtils.isEmpty(columns) || StringUtils.isEmpty(querytype) || StringUtils.isEmpty(tableName)) {
			String msg = "信息不完整，报文请求节点中columns, where, table不允许为空";
			// com.ytec.mdm.base.bo.Error err = new com.ytec.mdm.base.bo.Error();
			// err.setChDesc(msg);
			// err.setCode(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode());
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}

		String[] str = splitColumns(columns);
		for (int i = 0; i < str.length; i++) {
			String column = str[i];
			if (i == (str.length - 1)) {
				map.put(column, column + " ");
			} else {
				map.put(column, column + ",");
			}
			colunmsmap.put(column.trim(), column);
		}
		map.put("from", "from ");
		map.put(tableName, tableName);
		map.put("where", " where ");
		map.put(querytype, querytype);

		try {
			List<Map<String, String>> list = doTransJSONObject(map, colunmsmap);
			Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
			for (int i = 0; i < list.size(); i++) {
				Element customerEle = responseEle.addElement(tableName);
				Map obj = list.get(i);
				Set<String> set = obj.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					Element hand = customerEle.addElement(key);
					String value = obj.get(key).toString();
					hand.setText(value);
				}
			}
			ecifData.setRepNode(responseEle);
			return;
		} catch (Exception e) {
			String msg;
			//TODO
			if (e instanceof SQLException) {
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
			}
			if (ErrorCode.ERR_ECIF_DATA_MAX.getCode().equals(e.getMessage())) {
				msg = String.format("数据库查询有错：%s,%s(查询记录数最大限制为 %d)", e.getMessage(), ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), MdmConstants.MAX_SELECT_COUNT);
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_MAX.getCode(), msg);
			} else {
				msg = String.format("数据库查询有错：%s", e.getMessage());
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
			}
			ecifData.setSuccess(false);
			e.printStackTrace();

			return;
		}
	}

	public String[] splitColumns(String columns) {

		String[] str = null;
		if (columns != null && !columns.trim().equals("")) {
			if (columns.contains(",")) {
				str = columns.split(",");
			} else {
				str = new String[] { columns };
			}
		}
		return str;
	}

	/**
	 * 将拿到的JSONOObject里面的sql转化成具体的sql出来
	 *
	 * @param object
	 * @return
	 */

	public String generateJsonObjectString(Map<String, String> map) throws Exception {

		log.debug("execute-->>generateJsonObjectString(), para: {}", map);

		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		StringBuffer sb = new StringBuffer();

		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * 截取传递过来的map，将其中的需要的数据的截取出来，放在一个新的list里面
	 * 这个list是一个有序的list
	 *
	 * @param map
	 * @return
	 */
	public List<String> generateJsonObjectMap(Map<String, String> map) throws BizException {
		log.debug("execute: generateJsonObjectMap(), para: {}", map);

		List<String> ObjectString = new LinkedList<String>();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();

		while (it.hasNext()) {
			String key = it.next();
			if (key.equals("select")) {
				continue;
			} else if (key.equals("from")) {
				break;
			} else {
				ObjectString.add(key);
			}
		}

		return ObjectString;
	}

	/**
	 * 将查询出来的对象转化成JSONObject返回出去
	 *
	 * @param obj
	 * @return
	 *
	         private List generateSuccessResponse(List listObject, Map<String, String> map) throws BizException, Exception {
	 *         JSONArray jArray = JSONArray.fromObject(listObject);
	 *         List<JSONObject> list = new ArrayList<JSONObject>();
	 *         List<String> newList = generateJsonObjectMap(map);
	 *         JSONObject jObject = null;
	 *         for (int i = 0; i < jArray.size(); i++) {
	 *         if (jArray.get(i) instanceof JSONArray) {
	 *         JSONArray jArrays = (JSONArray) jArray.get(i);
	 *         jObject = new JSONObject();
	 *         for (int j = 0; j < jArrays.size(); j++) {
	 *         jObject.put(newList.get(j), jArrays.get(j));
	 *         }
	 *         } else if (jArray.get(i) instanceof JSONObject) {
	 *         jObject = new JSONObject();
	 *         jObject = (JSONObject) jArray.get(i);
	 *         } else if (jArray.get(i) instanceof String) {
	 *         jObject = new JSONObject();
	 *         jObject.put(newList.get(i), jArray.get(i).toString());
	 *         } else {
	 *         throw new Exception("数据错误");
	 *         }
	 *         list.add(jObject);
	 *         }
	 *         return list;
	 *         }//
	 */

	/**
	 * 将查询出来的对象转化成JSONObject返回出去
	 *
	 * @param obj
	 * @return
	 */
	private List generateSuccessResponse(List listObject, Map<String, String> map) throws BizException, Exception {
		log.debug("execute: generateSuccessResponse(), para: {},{}", listObject, map);
		List<String> cols = generateJsonObjectMap(map);
		for (Object o : listObject) {
			if (o instanceof Clob) {
				System.err.println("Clog");
				return null;
			}
		}
		JSONArray data = JSONArray.fromObject(listObject);
		List<JSONObject> result = new ArrayList<JSONObject>();

		int colCount = cols.size();
		int datCount = data.size();
		for (int datIdx = 0; datIdx < datCount; datIdx++) {
			JSONObject rowData = new JSONObject();
			for (int colIdx = 0; colIdx < colCount; colIdx++) {
				if (colCount > 1) {
					log.debug("{}:{}", cols.get(colIdx), ((List) data.get(datIdx)).get(colIdx));
					rowData.put(cols.get(colIdx), ((List) data.get(datIdx)).get(colIdx));
				} else {
					rowData.put(cols.get(colIdx), data.get(datIdx));
					log.debug("{}:{}", cols.get(colIdx), data.get(datIdx));
				}
			}
			result.add(rowData);
		}
		log.debug("generateSuccessResponse return: ", result);
		return result;
	}

	/**
	 * 这个方法是针对查询所需要的数据的方法，查询出来是Object，在对Object进行处理
	 *
	 * @param map
	 */
	public List doTransJSONObject(Map<String, String> map, Map<String, String> colunmsmap) throws Exception {
		List<Map<String, String>> obj = null;
		log.debug("execute: doTransJSONObject(), para: {}", map);
		String sql = generateJsonObjectString(map);
		obj = bizGetObject(sql);
		if (obj != null) {
			List<Map<String, String>> listJsonObject = generateSuccessResponse(obj, colunmsmap);
			return listJsonObject;
		}
		return obj;
	}

	/**
	 * 具体去查询数据中的数据
	 *
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List bizGetObject(String jql) throws Exception {
		log.debug("execute: bizGetObject(), para: {}", jql);
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List result = null;
		result = baseDAO.findByNativeSQLWithNameParam(jql.toString(), null);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("bizGetObject return: {}", result);
			return result;
		}
		log.debug("bizGetObject return: {}", result);
		return result;
	}

}
