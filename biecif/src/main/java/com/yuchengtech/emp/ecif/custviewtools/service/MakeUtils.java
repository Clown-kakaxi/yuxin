package com.yuchengtech.emp.ecif.custviewtools.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yuchengtech.emp.ecif.custviewtools.entity.EntityVO;

public class MakeUtils {
	
	public static String XML_ENCODING = "UTF-8";

	
   /**
    * 将String转换成XML文档对象
    * @param xmlStr
    * @return
 * @throws DocumentException 
    * @throws JDOMException
    * @throws IOException
    */
	public static Document stringToXml(String xmlStr) {
		
		try {
			StringReader sr = new StringReader(xmlStr);
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding(XML_ENCODING);
			Document doc=saxReader.read(sr);
			return doc;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getEntityPackage(Document doc,String entityPackage){

		Element root = doc.getRootElement();
		Element mainEntityPackage = root.element("MainEntityPackage");

		return mainEntityPackage.getText().toLowerCase() + "." + entityPackage.toLowerCase();
	}
	
	public static String getServicePackage(Document doc){

		Element root = doc.getRootElement();
		Element mainServicePackage = root.element("MainServicePackage");

		return mainServicePackage.getText().toLowerCase() ;
	}

	public static String getWebPackage(Document doc){

		Element root = doc.getRootElement();
		Element mainWebPackage = root.element("MainWebPackage");

		return mainWebPackage.getText().toLowerCase() ;
	}

	public static String geViewPath(Document doc,String bizName){

		Element root = doc.getRootElement();
		Element minJspPath = root.element("MainJspPath");

		return minJspPath.getText() + "." + bizName.toLowerCase();
	}
	
	public static String getDefautSchema(Document doc){

		Element root = doc.getRootElement();
		Element DefautSchema = root.element("DefautSchema");

		return DefautSchema.getText() ;
	}	
	
	public static String geJspShowType(Document doc,String entityname){

		Element e = (Element)doc.selectSingleNode("/Body/CustMenu1/CustMenu2[@Entityname='"+entityname +"']");

		Object show = e.attributeValue("show") ;
		if(show==null||show.equals("")||show.equals("list"))
			return "list";
		else
			return "form";
	}	
	
	public static Element getElementByEntity(Document doc,String entityname){
		return (Element)doc.selectSingleNode("/Body/CustMenu1/CustMenu2[@Entityname='"+entityname +"']");
	}
	
	public static String getEntityFilePath(Document doc,String entityname){
		
		Element e = (Element)doc.selectSingleNode("/Body/JavaBasePath");
		String basepath = e.getText();
		
		Element e1 = (Element)doc.selectSingleNode("/Body/MainEntityPackage");
		String packagename = e1.getText();
	
		Element e2 = getElementByEntity(doc,entityname);
		String bizName = e2.getParent().attributeValue("BizName").toLowerCase();		//上级节点的bizName
		
		String packagepath = "/" + packagename.replaceAll("\\.", "/") +"/"+ bizName +"/";
		
		String filename = entityname +".java";
		
		return basepath + packagepath + filename;
	}

	public static String getControllFilePath(Document doc,String entityname){
		
		Element e = (Element)doc.selectSingleNode("/Body/JavaBasePath");
		String basepath = e.getText();
		
		Element e1 = (Element)doc.selectSingleNode("/Body/MainWebPackage");
		String packagename = e1.getText();
		String packagepath = "/" + packagename.replaceAll("\\.", "/") +"/";
		
		String filename = entityname +"Controller.java";
		
		return basepath + packagepath + filename;
	}
	
	public static String getBSFilePath(Document doc,String entityname){
		
		Element e = (Element)doc.selectSingleNode("/Body/JavaBasePath");
		String basepath = e.getText();
		
		Element e1 = (Element)doc.selectSingleNode("/Body/MainServicePackage");
		String packagename = e1.getText();
		String packagepath = "/" + packagename.replaceAll("\\.", "/") +"/";
		
		String filename = entityname +"BS.java";
		
		return basepath + packagepath + filename;
	}
		
	public static String getJspFilePath(Document doc,String entityname){
		
		Element e = (Element)doc.selectSingleNode("/Body/JspBasePath");
		String basepath = e.getText();
		
		Element e1 = (Element)doc.selectSingleNode("/Body/MainJspPath");
		String packagename = e1.getText();

		Element e2 = getElementByEntity(doc,entityname);
		String bizName = e2.getParent().attributeValue("BizName");		//上级节点的bizName
		
		String packagepath = packagename +"/"+ bizName.toLowerCase() +"/";
		
		String filename = entityname.toLowerCase() +"-index.jsp";
		
		return basepath + packagepath + filename;
	}
	
	
	public static List getEntityList(String filename){
		
		Document doc = stringToXml(readFile(filename));
		
		List entityList = new ArrayList();
		List<Element> custMenu1List = doc.selectNodes("/Body/CustMenu1");
        for (int i = 0; i < custMenu1List.size(); i++) {
            Element custMenu1 = custMenu1List.get(i);
            List<Element> custMenu2List = custMenu1.selectNodes("CustMenu2");
            for (int j = 0; j < custMenu2List.size(); j++) {
            	 Element custMenu2 = custMenu2List.get(j);
            	 
            	 String custMenu1Name = custMenu1.attributeValue("MenuName");
            	 String custMenu2Name = custMenu2.attributeValue("MenuName");
            	 String entityName = custMenu2.attributeValue("Entityname");
            	 
            	 EntityVO vo = new EntityVO();
            	 vo.setCustMenu1Name(custMenu1Name);
            	 vo.setCustMenu2Name(custMenu2Name);
            	 vo.setEntityName(entityName);
            	 
            	 entityList.add(vo);
            }
            
        }
		return entityList;
	}	
	 public static String readFile(String fileName) {  
		    String output = "";   
		      
		    File file = new File(fileName);  
		         
		    if(file.exists()){  
		        if(file.isFile()){  
		            try{  
		                BufferedReader input = new BufferedReader (new FileReader(file));  
		                StringBuffer buffer = new StringBuffer();  
		                String text;  
		                     
		                while((text = input.readLine()) != null)  
		                    buffer.append(text );  
		                     
		                output = buffer.toString();                      
		            }  
		            catch(IOException ioException){  
		                System.err.println("File Error!");  

		            }  
		        }  
		        else if(file.isDirectory()){  
		            String[] dir = file.list();  
		            output += "Directory contents:/n";  
		              
		            for(int i=0; i<dir.length; i++){  
		                output += dir[i] +"/n";  
		            }  
		        }  
		    }  
		    else{  
		        System.err.println("Does not exist!");  
		    }  
		    return output;  
		 }  
	
	
	public static void writeFile(String filename, String str) {
		try {
			
			File file = new File(filename);
			if (!file.getParentFile().exists()) {  
				file.getParentFile().mkdirs();  
			}  

			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			pw.println(str);
			pw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	public static String convert2JavaFieldName(String colName){
		
		String fieldName = "";
		String strs[] = colName.split("_");
		fieldName = strs[0].toLowerCase();
		for(int i=1;i<strs.length;i++){
			String str = strs[i];
			fieldName += str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
		}
		
		return fieldName;
	}
	
	
	public static void main(String args[]){
		Document doc = stringToXml(readFile("C:\\Users\\shangjf\\Desktop\\customerpermenu.xml"));
		getEntityList(MakeConstants.PER_XMLFILE);
		//System.out.println(getEntityFilePath(doc,"PersonProfile")) ;
	}
	
}
