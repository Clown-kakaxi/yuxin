package com.yuchengtech.emp.ecif.transaction.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.util.RandomUtils;

import static com.yuchengtech.emp.ecif.base.common.GlobalConstants.*;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsg;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgCheckinfo;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttr;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrCt;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeFilter;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMap;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabsRel;

/**
 * <pre>
 * Title: 交易配置报表生成
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
@SuppressWarnings("rawtypes")
public class TxExcelReportBS extends BaseBS<Object> {
	private Logger log = LoggerFactory.getLogger(TxExcelReportBS.class);
	private Map<String, Map<String, Method>> classGetMethods;
	private Map<String, Map<String, Method>> classSetMethods;
	private Map<String, Class> classMap;
	private byte[] template = null;

	public TxExcelReportBS() {
		this.classGetMethods = Maps.newHashMap();
		this.classMap = Maps.newHashMap();
		this.classSetMethods = Maps.newHashMap();
		// this.template = getTemplate();
		// 构造9个表的方法数据结构
		Class[] ca = { TxDef.class, TxMsg.class, TxMsgNode.class,
				TxMsgNodeTabMap.class, TxMsgNodeTabsRel.class,
				TxMsgNodeAttr.class, TxMsgNodeAttrCt.class,
				TxMsgNodeFilter.class, TxMsgCheckinfo.class };
		for (int i = 0; i < ca.length; i++) {
			Class clazz = ca[i];
			classMap.put(clazz.getSimpleName(), clazz);
			classGetMethods.put(clazz.getSimpleName(),
					getClassMethodMap(clazz, "get"));
			classSetMethods.put(clazz.getSimpleName(),
					getClassMethodMap(clazz, "set"));
		}
	}

	/**
	 * 获取实体类中的setter方法
	 * 
	 * @param clazz
	 * @return
	 */
	private Map<String, Method> getClassMethodMap(Class clazz, String type) {
		Map<String, Method> mm = Maps.newHashMap();
		Method[] ma = clazz.getDeclaredMethods();
		for (int i = 0; i < ma.length; i++) {
			Method m = ma[i];
			String name = m.getName();
			if (name.startsWith(type)) {
				String key = name.substring(3);
				mm.put(key, m);
			}
		}
		return mm;
	}

	public List<SXSSFWorkbook> getAllReport() {
		String jql = "select t.txId from TxDef t order by t.txId asc";
		List<Long> txIdLst = baseDAO.findWithIndexParam(jql);
		return getReports(txIdLst);
	}

	/**
	 * 批量获取报表
	 * 
	 * @param txIds
	 *            交易标识ID LIST
	 * @return
	 */
	public List<SXSSFWorkbook> getReports(List<Long> txIds) {
		List<SXSSFWorkbook> wklst = Lists.newArrayList();
		for (int i = 0; i < txIds.size(); i++) {
			SXSSFWorkbook workbook = new SXSSFWorkbook(getTemplate(), 500);
			SXSSFWorkbook swk = fillTxDef(workbook, txIds.get(i));
			if (swk != null) {
				wklst.add(swk);
			}
		}
		return wklst;
	}

	public SXSSFWorkbook getReport(Long txId) {
		XSSFWorkbook wk = getTemplate();
		SXSSFWorkbook workbook = new SXSSFWorkbook(wk, 500);
		return fillTxDef(workbook, txId);
	}

	/**
	 * 获取模板
	 * 
	 * @return
	 */
	private XSSFWorkbook getTemplate() {
		try {
			if (this.template == null) {
				String realPath = ((ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes()).getRequest().getSession()
						.getServletContext().getRealPath("/");
				String tempPath = realPath + EXCEL_TEMPLATE_TRADINGCONFIG_PATH;
				InputStream in = new FileInputStream(tempPath);
				this.template = new byte[in.available()];
				in.read(this.template);
				in.close();
			}
			InputStream in = new ByteArrayInputStream(this.template);
			XSSFWorkbook wb = new XSSFWorkbook(in);
			in.close();
			return wb;
		} catch (FileNotFoundException e) {
			log.error("[模板文件未找到]" + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取sheet中标注的列名
	 * 
	 * @param sheetName
	 * @return
	 */
	private List<String> getColumnEname(String sheetName) {
		Sheet tSheet = getTemplate().getSheet(sheetName);
		if (tSheet != null) {
			Row row = tSheet.getRow(2);
			if (row != null) {
				List<String> cl = Lists.newArrayList();
				for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
					Cell cell = row.getCell(i);
					cl.add(cell.getStringCellValue());
				}
				return cl;
			}
		}
		return null;
	}

	// 以下为填充各表的方法
	private SXSSFWorkbook fillTxDef(SXSSFWorkbook workbook, Long txId) {
		TxDef entity = (TxDef) baseDAO.getObjectById(TxDef.class, txId);
		Map<String, Method> mm = classGetMethods.get("TxDef");
		List<String> cl = getColumnEname("TxDef");
		Sheet sheet = workbook.getSheet("TxDef");
		fillRow(entity, mm, cl, sheet, 3);
		fillTxMsg(workbook, txId);
		return workbook;
	}

	private SXSSFWorkbook fillTxMsg(SXSSFWorkbook workbook, Long txId) {
		String jql = "select t from TxMsg t where t.txId=?0 order by t.msgId asc";
		List<TxMsg> list = baseDAO.findWithIndexParam(jql, txId);
		Map<String, Method> mm = classGetMethods.get("TxMsg");
		List<String> cl = getColumnEname("TxMsg");
		Sheet sheet = workbook.getSheet("TxMsg");
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			TxMsg entity = list.get(i);
			if (list.get(i) != null) {
				ids.add(entity.getMsgId());
			}
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		if (ids.size() > 0) {
			fillTxMsgNode(workbook, ids);
			fillTxMsgCheckinfo(workbook, ids);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgNode(SXSSFWorkbook workbook,
			List<Long> msgIds) {
		String jql = "select t from TxMsgNode t where t.msgId in ?0 order by t.nodeId asc";
		List<TxMsgNode> list = baseDAO.findWithIndexParam(jql, msgIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgNode");
		List<String> cl = getColumnEname("TxMsgNode");
		Sheet sheet = workbook.getSheet("TxMsgNode");
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			TxMsgNode entity = list.get(i);
			if (list.get(i) != null) {
				ids.add(entity.getNodeId());
			}
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		if (ids.size() > 0) {
			fillTxMsgNodeTabMap(workbook, ids);
			fillTxMsgNodeTabsRel(workbook, ids);
			fillTxMsgNodeAttr(workbook, ids);
			fillTxMsgNodeFilter(workbook, ids);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgNodeTabMap(SXSSFWorkbook workbook,
			List<Long> nodeIds) {
		String jql = "select t from TxMsgNodeTabMap t where t.nodeId in ?0 order by t.nodeTabMapId asc";
		List<TxMsgNodeTabMap> list = baseDAO.findWithIndexParam(jql, nodeIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeTabMap");
		List<String> cl = getColumnEname("TxMsgNodeTabMap");
		Sheet sheet = workbook.getSheet("TxMsgNodeTabMap");
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeTabMap entity = list.get(i);
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgNodeTabsRel(SXSSFWorkbook workbook,
			List<Long> nodeIds) {
		String jql = "select t from TxMsgNodeTabsRel t where t.nodeId in ?0 order by t.nodeTabsRelId asc";
		List<TxMsgNodeTabsRel> list = baseDAO.findWithIndexParam(jql, nodeIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeTabsRel");
		List<String> cl = getColumnEname("TxMsgNodeTabsRel");
		Sheet sheet = workbook.getSheet("TxMsgNodeTabsRel");
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeTabsRel entity = list.get(i);
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgNodeAttr(SXSSFWorkbook workbook,
			List<Long> nodeIds) {
		String jql = "select t from TxMsgNodeAttr t where t.nodeId in ?0 order by t.attrId asc";
		List<TxMsgNodeAttr> list = baseDAO.findWithIndexParam(jql, nodeIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeAttr");
		List<String> cl = getColumnEname("TxMsgNodeAttr");
		Sheet sheet = workbook.getSheet("TxMsgNodeAttr");
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeAttr entity = list.get(i);
			if (list.get(i) != null) {
				ids.add(list.get(i).getAttrId());
			}
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		if (ids.size() > 0) {
			fillTxMsgNodeAttrCt(workbook, ids);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgNodeAttrCt(SXSSFWorkbook workbook,
			List<Long> attrIds) {
		String jql = "select t from TxMsgNodeAttrCt t where t.attrId in ?0 order by t.ctId asc";
		List<TxMsgNodeAttrCt> list = baseDAO.findWithIndexParam(jql, attrIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeAttrCt");
		List<String> cl = getColumnEname("TxMsgNodeAttrCt");
		Sheet sheet = workbook.getSheet("TxMsgNodeAttrCt");
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeAttrCt entity = list.get(i);
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgNodeFilter(SXSSFWorkbook workbook,
			List<Long> nodeIds) {
		String jql = "select t from TxMsgNodeFilter t where t.nodeId in ?0 order by t.filterId asc";
		List<TxMsgNodeFilter> list = baseDAO.findWithIndexParam(jql, nodeIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeFilter");
		List<String> cl = getColumnEname("TxMsgNodeFilter");
		Sheet sheet = workbook.getSheet("TxMsgNodeFilter");
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeFilter entity = list.get(i);
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		return workbook;
	}

	private SXSSFWorkbook fillTxMsgCheckinfo(SXSSFWorkbook workbook,
			List<Long> msgIds) {
		String jql = "select t from TxMsgCheckinfo t where t.msgId in ?0 order by t.checkId asc";
		List<TxMsgCheckinfo> list = baseDAO.findWithIndexParam(jql, msgIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgCheckinfo");
		List<String> cl = getColumnEname("TxMsgCheckinfo");
		Sheet sheet = workbook.getSheet("TxMsgCheckinfo");
		for (int i = 0; i < list.size(); i++) {
			TxMsgCheckinfo entity = list.get(i);
			fillRow(entity, mm, cl, sheet, 3 + i);
		}
		return workbook;
	}

	/**
	 * 向Excel中写入数据
	 * 
	 * @param entity
	 *            实体
	 * @param mm
	 *            方法Map
	 * @param cl
	 *            列名列表
	 * @param sheet
	 *            Excel sheet对象
	 * @param rowIndex
	 *            行号
	 * @return 是否执行成功
	 */
	private boolean fillRow(Object entity, Map<String, Method> mm,
			List<String> cl, Sheet sheet, int rowIndex) {
		if (entity == null || mm == null || cl == null || sheet == null) {
			return false;
		}
		Row row = sheet.createRow(rowIndex);
		for (int i = 0; i < cl.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			Method getM = mm.get(cl.get(i));
			String value = "";
			try {
				if (getM != null) {
					Object o = getM.invoke(entity);
					if (o != null) {
						value = o.toString();
					}
					cell.setCellValue(value);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void zip(OutputStream outputSteam, List<SXSSFWorkbook> workbooklst,List<TxDef> txDefLst)
			throws IOException {
		if (outputSteam != null) {
			ZipOutputStream zos = new ZipOutputStream(outputSteam);
			zos.setEncoding("GBK");
			if (workbooklst != null && workbooklst.size() > 0) {
				for (int i = 0; i < workbooklst.size(); i++) {
					SXSSFWorkbook workbook = workbooklst.get(i);
					TxDef txDef = (TxDef)txDefLst.get(i);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					workbook.write(baos);
					byte[] buf = baos.toByteArray();
//					ZipEntry zipEntity = new ZipEntry(RandomUtils.uuid2()
//							+ ".xlsx");
					ZipEntry zipEntity = new ZipEntry(txDef.getTxCode()
							+ ".xlsx");					
					baos.flush();
					baos.close();
					zos.putNextEntry(zipEntity);
					zos.write(buf);
					zos.closeEntry();
				}
			}
			zos.flush();
			zos.close();
		}
	}
	

	public void zipXml(OutputStream outputSteam, List<String> xmllist,List<TxDef> txDefLst)
			throws IOException {
		if (outputSteam != null) {
			
			//一个文件不压缩
			if(xmllist != null && xmllist.size()==1){
				String xml = xmllist.get(0);
				outputSteam.write(xml.getBytes("UTF-8"));
				return;
			}
			
			ZipOutputStream zos = new ZipOutputStream(outputSteam);
			zos.setEncoding("GBK");
			if (xmllist != null && xmllist.size() > 0) {
				
				for (int i = 0; i < xmllist.size(); i++) {
					String xml = xmllist.get(i);
					TxDef txDef = (TxDef)txDefLst.get(i);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					baos.write(xml.getBytes(("UTF-8")));
					
					byte[] buf = baos.toByteArray();

					ZipEntry zipEntity = new ZipEntry(txDef.getTxCode()
							+ ".xml");					
					baos.flush();
					baos.close();
					zos.putNextEntry(zipEntity);
					zos.write(buf);
					zos.closeEntry();
				}
			}
			zos.flush();
			zos.close();
		}
	}
	

	// @Transactional(readOnly = false)
	// private void simpleSave(Object entity) {
	// if (entity == null) {
	// return;
	// }
	// String tName = entity.getClass().getSimpleName();
	// if ("TxDef".equals(tName)) {
	// TxDefBS bs = SpringContextHolder.getBean(TxDefBS.class);
	// bs.saveOrUpdateEntity((TxDef) entity);
	// }
	// if ("TxMsg".equals(tName)) {
	// TxMsgBS bs = SpringContextHolder.getBean(TxMsgBS.class);
	// bs.saveOrUpdateEntity((TxMsg) entity);
	// }
	// if ("TxMsgNode".equals(tName)) {
	// TxMsgNodeBS bs = SpringContextHolder.getBean(TxMsgNodeBS.class);
	// bs.saveOrUpdateEntity((TxMsgNode) entity);
	// }
	// if ("TxMsgNodeTabMap".equals(tName)) {
	// TxMsgNodeTabMapBS bs = SpringContextHolder
	// .getBean(TxMsgNodeTabMapBS.class);
	// bs.saveOrUpdateEntity((TxMsgNodeTabMap) entity);
	// }
	// if ("TxMsgNodeTabsRel".equals(tName)) {
	// TxMsgNodeTabsRelBS bs = SpringContextHolder
	// .getBean(TxMsgNodeTabsRelBS.class);
	// bs.saveOrUpdateEntity((TxMsgNodeTabsRel) entity);
	// }
	// if ("TxMsgNodeAttr".equals(tName)) {
	// TxMsgNodeAttrBS bs = SpringContextHolder
	// .getBean(TxMsgNodeAttrBS.class);
	// bs.saveOrUpdateEntity((TxMsgNodeAttr) entity);
	// }
	// if ("TxMsgNodeAttrCt".equals(tName)) {
	// TxMsgNodeAttrCtBS bs = SpringContextHolder
	// .getBean(TxMsgNodeAttrCtBS.class);
	// bs.saveOrUpdateEntity((TxMsgNodeAttrCt) entity);
	// }
	// if ("TxMsgNodeFilter".equals(tName)) {
	// TxMsgNodeFilterBS bs = SpringContextHolder
	// .getBean(TxMsgNodeFilterBS.class);
	// bs.saveOrUpdateEntity((TxMsgNodeFilter) entity);
	// }
	// if ("TxMsgCheckinfo".equals(tName)) {
	// TxMsgCheckinfoBS bs = SpringContextHolder
	// .getBean(TxMsgCheckinfoBS.class);
	// bs.saveOrUpdateEntity((TxMsgCheckinfo) entity);
	// }
	// }

	/**
	 * 保存Excel中数据
	 * 
	 * @param workbook
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Transactional(readOnly = false)
	public void doUploadDatabase(XSSFWorkbook workbook, String saveType)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		if (workbook == null) {
			return;
		}
		if (!"1".equals(saveType)) {
			// 判断数据库中是否有此交易
			if ("2".equals(saveType)) {
				// 记录TxId的cell
				Cell cell = workbook.getSheetAt(0).getRow(3).getCell(0);
				if (cell != null
						&& !StringUtils.isEmpty(cell.getStringCellValue())) {
					String jql = "select count(t) from TxDef t where t.txId=?0";
					Long count = baseDAO.findUniqueWithIndexParam(jql,
							Long.parseLong(cell.getStringCellValue()));
					if (count > 0) {
						return;
					}
				}
				return;
			}
		}
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet != null) {
				String tName = sheet.getSheetName();
				List<String> colNameLst = Lists.newArrayList();
				Row row = sheet.getRow(2);
				if (row != null) {
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
						Cell cell = row.getCell(j);
						colNameLst.add(cell.getStringCellValue());
					}
				}
				int index = 3;
				Row dataRow = sheet.getRow(index);
				Map<String, Method> methods = classSetMethods.get(tName);
				while (dataRow != null) {
					Class clazz = classMap.get(tName);
					Object entity = clazz.newInstance();
					for (int j = 0; j < colNameLst.size(); j++) {
						String colName = colNameLst.get(j);
						Method method = methods.get(colName);
						Cell cell = dataRow.getCell(j);
						if (cell != null
								&& !StringUtils.isEmpty(cell
										.getStringCellValue())) {
							Class[] classes = method.getParameterTypes();
							if (classes != null && classes.length == 1) {
								if ("String".equals(classes[0].getSimpleName())) {
									method.invoke(entity,
											cell.getStringCellValue());
								}

								if ("Long".equals(classes[0].getSimpleName())) {
									method.invoke(entity, Long.parseLong(cell
											.getStringCellValue()));
								}

								if ("Timestamp".equals(classes[0]
										.getSimpleName())) {
									method.invoke(entity, Timestamp
											.valueOf(cell.getStringCellValue()));
								}

								if ("Integer"
										.equals(classes[0].getSimpleName())) {
									method.invoke(entity, Integer.parseInt(cell
											.getStringCellValue()));
								}
							}
						}
					}
					simpleSaveOrUpdata(entity);
					dataRow = sheet.getRow(++index);
				}
			}
		}
	}

	/**
	 * SQL的简单保存功能
	 * 
	 * @param entity
	 */
	private void simpleSaveOrUpdata(Object entity) {
		Class<?> clazz = entity.getClass();
		String tableName = null;
		String idName = null;
		Map<String, Method> methods = classGetMethods
				.get(clazz.getSimpleName());
		Map<String, Object> values = Maps.newHashMap();
		List<String> fieldNameLst = Lists.newArrayList();
		boolean isExist = false;
		// 获取表名
		if (clazz.isAnnotationPresent(Table.class)) {
			Table tbAnn = clazz.getAnnotation(Table.class);
			tableName = tbAnn.name();
		}
		// 获取列名列表
		// 获取主键相关
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			if (field.isAnnotationPresent(Column.class)) {
				Column colAnn = field.getAnnotation(Column.class);
				fieldNameLst.add(colAnn.name());
				// 获取主键
				if (field.isAnnotationPresent(Id.class)) {
					idName = colAnn.name();
					field.setAccessible(true);
					try {
						// 判断数据库是否存在该数据
						Object id = field.get(entity);
						Object obj = getEntityById(clazz, (Long) id);
						isExist = (obj != null);
						// 如果实体相同，则不需要进行更新操作，直接退出
						if (isExist &&isEntityEquals(obj, entity)) {
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String fieldKey = toUpperCaseFirstChar(fieldName);
				Method method = methods.get(fieldKey);
				try {
					if (method != null) {
						Object value = method.invoke(entity);
						values.put(colAnn.name(), value);
					}
				} catch (Exception e) {
					e.printStackTrace();
					values.put(colAnn.name(), null);
					continue;
				}
			}

		}

		if (isExist) {
			simpleUpdata(tableName, idName, fieldNameLst, values);
		} else {
			simpleSave(tableName, fieldNameLst, values);
		}
	}

	/**
	 * SQL保存
	 * 
	 * @param tableName
	 * @param fieldNameLst
	 * @param values
	 */
	private void simpleSave(String tableName, List<String> fieldNameLst,
			Map<String, Object> values) {
		StringBuffer sql = new StringBuffer(500);
		StringBuffer val = new StringBuffer(100);
		Map<String, Object> valMap = Maps.newHashMap();
		sql.append("INSERT INTO ").append(tableName).append("(");
		for (int i = 0; i < fieldNameLst.size(); i++) {
			String name = fieldNameLst.get(i);
			if (!StringUtils.isEmpty(name)) {
				if (i != 0) {
					sql.append(", ");
					val.append(", ");
				}
				if (values.get(name) == null) { 
					val.append("null");
				} else {
					val.append(":parm").append(i);
					valMap.put("parm" + i, values.get(name));
				}
				sql.append(name);
			}
		}
		sql.append(") VALUES(").append(val).append(")");
		Query query = baseDAO.createNativeQueryWithNameParam(sql.toString(),
				valMap);
		query.executeUpdate();
	}

	/**
	 * SQL更新
	 * 
	 * @param tableName
	 * @param fieldNameLst
	 * @param values
	 */
	private void simpleUpdata(String tableName, String idName,
			List<String> fieldNameLst, Map<String, Object> values) {
		StringBuffer sql = new StringBuffer(500);
		sql.append("UPDATE ").append(tableName).append(" SET ");
		Map<String, Object> valMap = Maps.newHashMap();
		for (int i = 0; i < fieldNameLst.size(); i++) {
			String name = fieldNameLst.get(i);
			if (i != 0) {
				sql.append(", ");
			}
			sql.append(name);
			if (values.get(name) == null) {
				sql.append("=null");
			} else {
				sql.append("=:parm").append(i);
				valMap.put("parm" + i, values.get(name));
			}
		}
		sql.append(" WHERE ").append(idName).append("=:id");
		valMap.put("id", values.get(idName));
		Query query = baseDAO.createNativeQueryWithNameParam(sql.toString(),
				valMap);
		query.executeUpdate();
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	private String toUpperCaseFirstChar(String str) {
		return StringUtils.isEmpty(str) ? "" : new StringBuffer()
				.append(Character.toUpperCase(str.charAt(0)))
				.append(str.length() > 1 ? str.substring(1) : "").toString();
	}

	private boolean isEntityEquals(Object obj1, Object obj2)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = obj1.getClass();
		if (obj1.getClass().equals(obj2.getClass())) {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.isAnnotationPresent(Column.class)) {
					field.setAccessible(true);
					Object obj1Val = field.get(obj1);
					Object obj2Val = field.get(obj2);
					if (obj1Val != null && obj2Val != null) {
						if (!obj1Val.equals(obj2Val)) {
							return false;
						}
					} else if (obj1Val == null && obj2Val == null) {
					} else {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
