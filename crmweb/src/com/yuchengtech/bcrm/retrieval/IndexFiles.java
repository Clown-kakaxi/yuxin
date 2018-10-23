package com.yuchengtech.bcrm.retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.upload.poi.POIUtils;

/**
 * @describtion: 全文检索-创建索引
 * 仅对word03/07、excel03/07、ppt、txt、pdf类型的文件建立索引,其它文件不做处理
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2013-8-5 上午10:47:41
 */
public class IndexFiles {
	private static Logger log = Logger.getLogger(IndexFiles.class);
	private static String INDEX_PATH = FileTypeConstance.getSystemProperty("indexPath")==null?("index"+File.separator):(FileTypeConstance.getSystemProperty("indexPath")+File.separator);
	private static IndexFiles instance;
	
	private IndexFiles(){
	}
	
	public static synchronized IndexFiles getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new IndexFiles();
        }
        return instance;
    }
	
	/**
	 * 创建索引
	 * @param docDir 要创建索引的目录
	 * @throws Exception
	 */
	public synchronized void createIndex(File docDir){
	    if ((!docDir.exists()) || (!docDir.canRead())) {
	    	log.warn("Document directory '" + docDir.getAbsolutePath() + "' does not exist or is not readable, please check the path");
	    }
		Date start = new Date();
		try {
			log.info("Indexing to directory '" + INDEX_PATH + "'...");

			Directory dir = FSDirectory.open(new File(INDEX_PATH));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,analyzer);


			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs(writer, docDir);

			log.info("numDocs："+writer.numDocs());
			writer.close();
			Date end = new Date();
			log.info(end.getTime() - start.getTime() + " total milliseconds");
		} catch (IOException e) {
			log.warn(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
	}
	
	/**
	 * 传入要删除的filename，此处的filename是唯一的
	 * @param filename
	 */
	public void delete(String filename) {
		IndexWriter writer = null;
		try {
			String aStrings = filename.substring(0, filename.indexOf("."));
			Directory dir = FSDirectory.open(new File(INDEX_PATH));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,analyzer);
			writer = new IndexWriter(dir, iwc);
			// 参数是一个选项，可以是一个query，也可以是一个term term就是一个精确查找的值
			// 此时删除的文档并未完全删除，而是存储在回收站中，可以恢复的
			QueryParser parser = new QueryParser(Version.LUCENE_44, "filename", analyzer);
			writer.deleteDocuments(parser.parse(aStrings));
			writer.forceMergeDeletes();//清空回收站
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除所有索引
	 */
	public void deleteAll() {
		IndexWriter writer = null;
		try {
			Directory dir = FSDirectory.open(new File(INDEX_PATH));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,analyzer);
			writer = new IndexWriter(dir, iwc);
			writer.deleteAll();
			writer.forceMergeDeletes();//清空回收站
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将file加入索引
	 * @param writer
	 * @param file
	 * @throws IOException
	 */
	private void indexDocs(IndexWriter writer, File file)throws IOException {
		if (file.canRead()){
			if (file.isDirectory()) {
				String[] files = file.list();
				if (files != null){
					for (int i = 0; i < files.length; i++){
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				String fileName = file.getName();
				fileName = fileName.toUpperCase();
				if(fileName.endsWith(".TXT")){
					addTxtFile(writer, file);
				}else if(fileName.endsWith(".PDF")){
					addPdfFile(writer, file);
				}else if(fileName.endsWith(".PPT")){
					addPptFile(writer, file);
				}else if(fileName.endsWith(".DOC") || fileName.endsWith(".DOCX")){
					addWordFile(writer, file);
				}else if(fileName.endsWith(".XLS") || fileName.endsWith(".XLSX")){
					addExcelFile(writer, file);
				}else{
					//其它文件不做处理
				}
			}
		}
	}
	
	/**
	 * TXT文件
	 * @param txtFile
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	private void addTxtFile(IndexWriter writer,File txtFile) throws IOException{
		Document doc = null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(txtFile);
		} catch (FileNotFoundException fnfe) {
			return;
		}
		try {
			doc = new Document();
			doc.add(new Field("filename", txtFile.getName(),Field.Store.YES,Field.Index.ANALYZED));
			doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "GBK"))));//编码为GBK，用于查询.txt文档    修改时间：2013-9-17
			
			log.info("adding " + txtFile);
			writer.addDocument(doc);
		} finally {
			fis.close();
		}
	}
	
	
	/**
	 * PDF文件
	 * @param pdFile
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void addPdfFile(IndexWriter writer, File pdfFile) {
		FileInputStream fis = null;
		PDDocument pdDoc = null;
		Document doc = null;
		try {
			fis = new FileInputStream(pdfFile);
			PDFParser pdfParser = new PDFParser(fis);
			pdfParser.parse();
			pdDoc = pdfParser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			
			String result = stripper.getText(pdDoc);
			
			doc = new Document();
			doc.add(new Field("filename", pdfFile.getName(), Field.Store.YES,Field.Index.ANALYZED));
			doc.add(new TextField("contents", result,Field.Store.NO));
			log.info("adding " + pdfFile);
			writer.addDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pdDoc != null){
				try {
					pdDoc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * doc,docx文件
	 * @param wordFile
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void addWordFile(IndexWriter writer, File wordFile){
		FileInputStream fis = null;
		Document doc = null;
		
        try {
        	String fileName = wordFile.getName();
			fileName = fileName.toUpperCase();
        	StringBuffer contents = new StringBuffer();
        	if(fileName.endsWith(".DOC")){
        		fis = new FileInputStream(wordFile);
        		WordExtractor we = new WordExtractor(fis);  
        		contents.append(we.getText());  
        	}else{
        		OPCPackage opcPackage = POIXMLDocument.openPackage(wordFile.getAbsolutePath());  
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);  
                contents.append(extractor.getText());  
        	}
            
            doc = new Document();
    		doc.add(new Field("filename", wordFile.getName(), Field.Store.YES,Field.Index.ANALYZED));
    		doc.add(new TextField("contents", contents.toString().trim(),Field.Store.NO));
    		
    		log.info("adding " + wordFile);
			writer.addDocument(doc);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
	}
	
	/**
	 * ppt文件
	 * @param pptFile
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void addPptFile(IndexWriter writer, File pptFile){
		FileInputStream fis = null;
		Document doc = null;
        try {
        	StringBuffer contents = new StringBuffer();
        	fis = new FileInputStream(pptFile);
        	SlideShow ppt = new SlideShow(new HSLFSlideShow(fis));
        	Slide[] slides = ppt.getSlides();
        	
        	for(int i=0;i <slides.length;i++){
                TextRun[] t = slides[i].getTextRuns();//为了取得幻灯片的文字内容，建立TextRun 
                for(int j=0;j <t.length;j++){ 
                	contents.append(t[j].getText());
                } 
            }
        	
            doc = new Document();
            doc.add(new Field("filename", pptFile.getName(), Field.Store.YES,Field.Index.ANALYZED));
    		doc.add(new TextField("contents", contents.toString().trim(),Field.Store.NO));
    		log.info("adding " + pptFile);
			writer.addDocument(doc);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
	}
	
	/**
	 * xls,xlsx文件
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void addExcelFile(IndexWriter writer, File excelFile){
		FileInputStream fis = null;
		Document doc = null;
		Workbook workbook = null;
        try {
        	
        	StringBuffer contents = new StringBuffer();
        	fis = new FileInputStream(excelFile);

        	workbook = WorkbookFactory.create(fis);
        	int totalSheetNum = workbook.getNumberOfSheets();
        	int sheetIndex = 0;
        	int rowIndex =0;
        	int totalRowNum = 0;
        	while(sheetIndex < totalSheetNum){
        		contents.append("\n");
        		Sheet currentSheet = workbook.getSheetAt(sheetIndex);
        		rowIndex =0;
        		totalRowNum = currentSheet.getLastRowNum();
        		while(rowIndex <= totalRowNum){
        			contents.append("\n");
        			Row row = currentSheet.getRow(rowIndex);
        			if(row == null){
        				rowIndex++;
        				continue;
        			}
                	for(int i = 0; i <= row.getPhysicalNumberOfCells(); i++) {
                		Object data = POIUtils.getCellValue(row.getCell(i));
                		contents.append(data == null? "": data.toString());
                	}
                	rowIndex++;
                }
        		sheetIndex++;
        	}
        	
            doc = new Document();
    		doc.add(new Field("filename", excelFile.getName(), Field.Store.YES,Field.Index.ANALYZED));
    		doc.add(new TextField("contents", contents.toString().trim(),Field.Store.NO));
    		
    		log.info("adding " + excelFile);
			writer.addDocument(doc);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
	}
	
}
