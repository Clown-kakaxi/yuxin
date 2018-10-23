package com.yuchengtech.emp.ecif.transaction.service;

import static com.yuchengtech.emp.ecif.base.common.GlobalConstants.EXCEL_TEMPLATE_TRADINGCONFIG_PATH;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.base.util.BeanRefUtil;
import com.yuchengtech.emp.ecif.base.util.FileFinder;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsg;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgCheckinfo;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttr;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrCt;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeFilter;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMap;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabsRel;

@Service
public class TxXmlReportBS extends BaseBS<Object>  {

	private Logger log = LoggerFactory.getLogger(TxExcelReportBS.class);
	private Map<String, Map<String, Method>> classGetMethods;
	private Map<String, Map<String, Method>> classSetMethods;
	private Map<String, Class> classMap;
	private byte[] template = null;
	
	public TxXmlReportBS() {
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
	
	public void newVersion(Long txId,String txCode,String path){
		
		String xml = getTxXml(txId);
		
		String filename = path + "/txversion/" + txCode + "~" + getFileVerison(path+"/txversion",txCode) +".xml";

		writeFile(filename, xml);
	}
	
	public static void writeFile(String filename, String str) {
		try {
			
			File file = new File(filename);
			if (!file.getParentFile().exists()) {  
				file.getParentFile().mkdirs();  
			}  

			Writer  pw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			pw.write(str);
			pw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}		
	
	public String getTxXml(Long txId) {
		
		Document document = DocumentHelper.createDocument();
		Element element = new DefaultElement("TxDef");
		document.add(element);
		Element e = fillTxDef(element, txId);
		String xml = document.asXML();
		
		return xml;
	}
	
	public List<String> getAllReport() {
		String jql = "select t.txId from TxDef t order by t.txId asc";
		List<Long> txIdLst = baseDAO.findWithIndexParam(jql);
		return getReports(txIdLst);
	}
	
	
	public List<String> getReports(List<Long> txIds) {
		List<String> xmllst = Lists.newArrayList();
		for (int i = 0; i < txIds.size(); i++) {
			xmllst.add(getTxXml(txIds.get(i)));
		}
		return xmllst;
	}	
	// 以下为填充各表的方法
	private Element fillTxDef(Element element, Long txId) {
		TxDef entity = (TxDef) baseDAO.getObjectById(TxDef.class, txId);
		Map<String, Method> mm = classGetMethods.get("TxDef");
		List<String> cl = getColumnEname(TxDef.class);
		fillRow(entity, mm, cl, element);
		fillTxMsg(element, txId);
		return element;
	}

	private Element fillTxMsg(Element element, Long txId) {
		
		
		String jql = "select t from TxMsg t where t.txId=?0 order by t.msgId asc";
		List<TxMsg> list = baseDAO.findWithIndexParam(jql, txId);
		Map<String, Method> mm = classGetMethods.get("TxMsg");
		List<String> cl = getColumnEname(TxMsg.class);
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			TxMsg entity = list.get(i);
			if (list.get(i) != null) {
				ids.add(entity.getMsgId());
			}
			Element e = new DefaultElement("TxMsg");
			fillRow(entity, mm, cl, e);
			element.add(e);
			
			fillTxMsgNode(e,  entity.getMsgId(),new Long(-1));
			//fillTxMsgCheckinfo(element, ids);
		}

		return element;
	}

	//递归求出整个节点树
	private Element fillTxMsgNode(Element element,
			Long msgId,Long upNodeId) {
		String jql = "select t from TxMsgNode t where t.msgId in ?0 and t.upNodeId = ?1 order by t.nodeId asc";
		List<TxMsgNode> list = baseDAO.findWithIndexParam(jql, msgId,upNodeId);
		Map<String, Method> mm = classGetMethods.get("TxMsgNode");
		List<String> cl = getColumnEname(TxMsgNode.class);
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			TxMsgNode entity = list.get(i);
			if (list.get(i) != null) {
				ids.add(entity.getNodeId());
			}
			Element e = new DefaultElement("TxMsgNode");
			fillRow(entity, mm, cl, e);
			element.add(e);
			
			fillTxMsgNodeTabMap(e, entity.getNodeId());
			fillTxMsgNodeTabsRel(e, entity.getNodeId());
			fillTxMsgNodeAttr(e, entity.getNodeId());
			fillTxMsgNodeFilter(e, entity.getNodeId());
			
			fillTxMsgNode(e,msgId,entity.getNodeId());

		}

		return element;
	}

	private Element fillTxMsgNodeTabMap(Element element,
			Long nodeId) {
		String jql = "select t from TxMsgNodeTabMap t where t.nodeId in ?0 order by t.nodeTabMapId asc";
		List<TxMsgNodeTabMap> list = baseDAO.findWithIndexParam(jql, nodeId);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeTabMap");
		List<String> cl = getColumnEname(TxMsgNodeTabMap.class);
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeTabMap entity = list.get(i);
			Element e = new DefaultElement("TxMsgNodeTabMap");
			fillRow(entity, mm, cl, e);
			element.add(e);
		}
		return element;
	}

	private Element fillTxMsgNodeTabsRel(Element element,
			Long nodeId) {
		String jql = "select t from TxMsgNodeTabsRel t where t.nodeId in ?0 order by t.nodeTabsRelId asc";
		List<TxMsgNodeTabsRel> list = baseDAO.findWithIndexParam(jql, nodeId);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeTabsRel");
		List<String> cl = getColumnEname(TxMsgNodeTabsRel.class);
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeTabsRel entity = list.get(i);
			Element e = new DefaultElement("TxMsgNodeTabsRel");
			fillRow(entity, mm, cl, e);
			element.add(e);
		}
		return element;
	}

	private Element fillTxMsgNodeAttr(Element element,
			Long nodeId) {
		String jql = "select t from TxMsgNodeAttr t where t.nodeId in ?0 order by t.attrId asc";
		List<TxMsgNodeAttr> list = baseDAO.findWithIndexParam(jql, nodeId);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeAttr");
		List<String> cl = getColumnEname(TxMsgNodeAttr.class);
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeAttr entity = list.get(i);
			if (list.get(i) != null) {
				ids.add(list.get(i).getAttrId());
			}
			Element e = new DefaultElement("TxMsgNodeAttr");
			fillRow(entity, mm, cl, e);
			element.add(e);
			
			fillTxMsgNodeAttrCt(element, entity.getAttrId() );
		}

		return element;
	}

	private Element fillTxMsgNodeAttrCt(Element element,
			Long attrId) {
		String jql = "select t from TxMsgNodeAttrCt t where t.attrId in ?0 order by t.ctId asc";
		List<TxMsgNodeAttrCt> list = baseDAO.findWithIndexParam(jql, attrId);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeAttrCt");
		List<String> cl = getColumnEname(TxMsgNodeAttrCt.class);
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeAttrCt entity = list.get(i);
			Element e = new DefaultElement("TxMsgNodeAttrCt");
			fillRow(entity, mm, cl, e);
			element.add(e);
		}
		return element;
	}

	private Element fillTxMsgNodeFilter(Element element,
			Long nodeId) {
		String jql = "select t from TxMsgNodeFilter t where t.nodeId in ?0 order by t.filterId asc";
		List<TxMsgNodeFilter> list = baseDAO.findWithIndexParam(jql, nodeId);
		Map<String, Method> mm = classGetMethods.get("TxMsgNodeFilter");
		List<String> cl = getColumnEname(TxMsgNodeFilter.class);
		for (int i = 0; i < list.size(); i++) {
			TxMsgNodeFilter entity = list.get(i);
			Element e = new DefaultElement("TxMsgNodeFilter");
			fillRow(entity, mm, cl, e);
			element.add(e);

		}
		return element;
	}

	private Element fillTxMsgCheckinfo(Element element,
			List<Long> msgIds) {
		String jql = "select t from TxMsgCheckinfo t where t.msgId in ?0 order by t.checkId asc";
		List<TxMsgCheckinfo> list = baseDAO.findWithIndexParam(jql, msgIds);
		Map<String, Method> mm = classGetMethods.get("TxMsgCheckinfo");
		List<String> cl = getColumnEname(TxMsgCheckinfo.class);
		for (int i = 0; i < list.size(); i++) {
			TxMsgCheckinfo entity = list.get(i);
			fillRow(entity, mm, cl, element);
		}
		return element;
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
	
	
	/**
	 * 获取sheet中标注的列名
	 * 
	 * @param sheetName
	 * @return
	 */
//	private List<String> getColumnEname(String sheetName) {
//		Sheet tSheet = getTemplate().getSheet(sheetName);
//		if (tSheet != null) {
//			Row row = tSheet.getRow(2);
//			if (row != null) {
//				List<String> cl = Lists.newArrayList();
//				for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
//					Cell cell = row.getCell(i);
//					cl.add(cell.getStringCellValue());
//				}
//				return cl;
//			}
//		}
//		return null;
//	}	
	
	
	private List<String> getColumnEname(Class clazz) {

		List<String> fieldArray = new ArrayList();
		Field[] fields = clazz.getDeclaredFields();
		for(int i = 0; i <fields.length;i++ ){
			fieldArray.add(fields[i].getName().substring(0,1).toUpperCase().concat(fields[i].getName().substring(1))) ;
		}
		return fieldArray;
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
	 * 向Xml中写入数据
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
	private boolean fillRow(Object entity, Map<String, Method> mm,List<String> cl,Element element) {
		
		if (entity == null || mm == null || cl == null ) {
			return false;
		}
		for (int i = 0; i < cl.size(); i++) {
			Method getM = mm.get(cl.get(i));
			String value = "";
			try {
				if (getM != null) {
					Object o = getM.invoke(entity);
					if (o != null) {
						value = o.toString();
					}
					
					Element e1 = new DefaultElement(cl.get(i));
					e1.setText(value);
					
					element.add(e1);
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
	
	private int getFileVerison(String path, String txCode){
		
		File directory = new File(path);  
		
		int maxversion = 0;
		
		if (directory.isDirectory()) {  
			boolean flag = false;
			File[] fileArr = directory.listFiles();  
			for (int i = 0; i < fileArr.length; i++) {  
				String[] files = fileArr[i].getName().replaceAll("[.][^.]+$", "").split("~");
					
					//如果有现有交易已有版本
					if(files.length>1 && files[0].trim().equalsIgnoreCase(txCode)){
						flag = true;
						
						String veriosn = files[1];
						
						int veriosntemp = new Integer(veriosn).intValue();
						if(veriosntemp > maxversion){
							maxversion = veriosntemp;
						}
				}
				
			}
			
			if(flag){
				return new Integer(maxversion + 1).intValue();
			}else{
				return 1;
			}
		}
		return 1;

	}
	
	public List getTxDefRecoverList(String path, String txCode){
		
		String filepath = path + "/txversion/";  
			
		List resultList = FileFinder.findFiles(filepath, txCode+"~*.xml", Integer.MAX_VALUE);
		
		File[] fs = null;
		
		if(resultList.size()>0){

			fs = (File[])resultList.toArray(new File[resultList.size()]);
			
			Arrays.sort(fs,new Comparator<File>(){
				public int compare(File f1, File f2) {
					long diff = f1.lastModified() - f2.lastModified();
					if (diff < 0)
						return 1;
					else if (diff == 0)
						return 0;
					else
						return -1;
				}
				public boolean equals(Object obj) {
					return true;
				}
				
			});
		}else{
			return new ArrayList();
		}
		
		return Arrays.asList(fs) ;
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
}
