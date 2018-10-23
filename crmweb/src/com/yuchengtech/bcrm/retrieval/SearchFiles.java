package com.yuchengtech.bcrm.retrieval;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describtion: 全文检索-检索类
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2013-8-6 上午9:33:37
 */
public class SearchFiles {
	private static Logger log = Logger.getLogger(SearchFiles.class);
	private static String INDEX_PATH =  FileTypeConstance.getSystemProperty("indexPath")==null?("index"+File.separator):(FileTypeConstance.getSystemProperty("indexPath")+File.separator);
	
	private static SearchFiles instance;
	
	private SearchFiles(){
	}
	
	public static synchronized SearchFiles getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new SearchFiles();
        }
        return instance;
    }
	
	/**
	 * 查询出来的结果集filename
	 * @param field  待查询的字段 filename/contents
	 * @param queryString 待查询的字串
	 * @return
	 * @throws Exception
	 */
	public synchronized List<String> serach(String field,String queryString){
		List<String> resultList = null;
		IndexReader reader = null;
		try {
			resultList = new ArrayList<String>();
			int hitsPerPage = 1000;//查询文档每页数目
			reader = DirectoryReader.open(FSDirectory.open(new File(INDEX_PATH)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
			QueryParser parser = new QueryParser(Version.LUCENE_44, field, analyzer);
			
			Query query = parser.parse(queryString);
			log.info("Searching for: " + query.toString(field));
			
			TopDocs results = searcher.search(query, hitsPerPage);
			
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;
			log.info(numTotalHits + " total matching documents");
			
			for (int i = 0; i < hits.length; i++) {
				Document doc = searcher.doc(hits[i].doc);
				String filename = doc.get("filename");
				if (filename != null) {
					resultList.add(filename);
				} else {
					log.info(i + 1 + ". "+ "No filename for this document");
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new BizException(1, 2, "1002", e.getMessage());
//			e.printStackTrace();
		} catch (ParseException e) {
			throw new BizException(1, 2, "1002", e.getMessage());
//			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
	public static void main(String[] args) throws Exception {
		String field = "contents";
		String queryString = "永丰";
		List<?> list = SearchFiles.getInstance().serach(field,queryString);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
}
