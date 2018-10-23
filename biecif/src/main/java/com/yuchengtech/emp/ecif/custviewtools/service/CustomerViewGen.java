package com.yuchengtech.emp.ecif.custviewtools.service;

import org.dom4j.Document;

public class CustomerViewGen {

	//------------------指定生成目录-------------------
	//客户视图主目录  
	//com.yuchengtech.emp.ecif.customer.entity
	//com.yuchengtech.emp.ecif.customer.simplegroup。service
	//com.yuchengtech.emp.ecif.customer.simplegroup。web
	//客户视图树
	//使用XML文件
	//树一级菜单：BS类、WEB类
	//树二级菜单；ecif\customer\simplegroup\一级菜单\二级菜单.jsp
	
	public static void makeOneEntityFiles(String xmlStr,String entityname){

		//-------------------生成文件---------------------
		Document doc = MakeUtils.stringToXml(xmlStr);
		
		//生成实体类
		MakeEntityFile makeEntityFile = new MakeEntityFile();
		String str = makeEntityFile.make(doc, entityname);
		String filename = MakeUtils.getEntityFilePath(doc, entityname);
		MakeUtils.writeFile(filename, str);
		
		//生成BS类
		MakeBSFile makeBSFile = new MakeBSFile();
		str = makeBSFile.make(doc, entityname);
		filename = MakeUtils.getBSFilePath(doc, entityname);
		MakeUtils.writeFile(filename, str);
		
		//生成WEB类
		MakeControllerFile makeControllerFile = new MakeControllerFile();
		str = makeControllerFile.make(doc, entityname);
		filename = MakeUtils.getControllFilePath(doc, entityname);
		MakeUtils.writeFile(filename, str);
		
		//生成jsp文件
		String type = MakeUtils.geJspShowType(doc,entityname);
		if(type.equals("list")){
			MakeJspListFile makeJspListFile = new MakeJspListFile();
			str = makeJspListFile.make(doc, entityname);
		}else if(type.equals("form")){
			MakeJspFormFile makeJspformFile = new MakeJspFormFile();
			str = makeJspformFile.make(doc,entityname);
		}
		filename = MakeUtils.getJspFilePath(doc, entityname);
		MakeUtils.writeFile(filename, str);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xml = MakeUtils.readFile("C:\\Users\\shangjf\\Desktop\\customerpermenu.xml");
		makeOneEntityFiles(xml,"Personbusiinfo");
	}

	
}
