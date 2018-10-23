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

	// �������ݿ�
	private static JPABaseDAO baseDAO;

	public void process(EcifData ecifData) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**
		 * ��ȡ����������
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
		String txCode = body.element("txCode").getTextTrim(); // ���ױ���
		String columns = body.element("columns").getTextTrim(); // ��ѯ���Ľ��

		String querytype = body.element("where").getTextTrim(); // ��ѯ������
		String tableName = body.element("table").getTextTrim(); // ����

		Map<String, String> colunmsmap = new LinkedHashMap<String, String>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("select", "select ");

		if (StringUtils.isEmpty(columns) || StringUtils.isEmpty(querytype) || StringUtils.isEmpty(tableName)) {
			String msg = "��Ϣ����������������ڵ���columns, where, table������Ϊ��";
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
				msg = String.format("���ݿ��ѯ�д�%s,%s(��ѯ��¼���������Ϊ %d)", e.getMessage(), ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), MdmConstants.MAX_SELECT_COUNT);
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_MAX.getCode(), msg);
			} else {
				msg = String.format("���ݿ��ѯ�д�%s", e.getMessage());
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
	 * ���õ���JSONOObject�����sqlת���ɾ����sql����
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
	 * ��ȡ���ݹ�����map�������е���Ҫ�����ݵĽ�ȡ����������һ���µ�list����
	 * ���list��һ�������list
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
	 * ����ѯ�����Ķ���ת����JSONObject���س�ȥ
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
	 *         throw new Exception("���ݴ���");
	 *         }
	 *         list.add(jObject);
	 *         }
	 *         return list;
	 *         }//
	 */

	/**
	 * ����ѯ�����Ķ���ת����JSONObject���س�ȥ
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
	 * �����������Բ�ѯ����Ҫ�����ݵķ�������ѯ������Object���ڶ�Object���д���
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
	 * ����ȥ��ѯ�����е�����
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
				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("bizGetObject return: {}", result);
			return result;
		}
		log.debug("bizGetObject return: {}", result);
		return result;
	}

}
