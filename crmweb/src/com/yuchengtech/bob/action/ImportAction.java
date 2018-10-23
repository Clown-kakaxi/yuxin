package com.yuchengtech.bob.action;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.core.DatabaseHelper;
import com.yuchengtech.bob.core.ExportImportManager;
import com.yuchengtech.bob.core.TaskImportXLS;
import com.yuchengtech.bob.importimpl.ImportInterface;
import com.yuchengtech.bob.upload.ImportTradeBean;
import com.yuchengtech.bob.upload.ImportTradeManage;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * Excel导入操作处理类
 * @author Administrator
 *
 */
@ParentPackage("json-default")
@Action(value="ImportAction", results={
    @Result(name="success", type="json")
})
public class ImportAction {
    
    private Map<String, Object> json = new HashMap<String, Object>(); 
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource datasource;
    /**
     * 创建导入任务，读取已经上传到服务器上的导入临时文件，并导入到临时表
     * @return String 
     * @throws Exception
     */
    public String index() throws Exception {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();     
        TaskImportXLS task = (TaskImportXLS)auth.getAttribute("BACKGROUND_IMPORT_XLS_TASK");
        if(task!=null){
            json.put("taskID", task.getTaskID());
            throw new Exception("请等待当前导入任务完成。");
        }else {
            String filename = (String)json.get("filename");
            String tradeCode= (String)json.get("tradecode");
            ImportTradeBean importTradeBean = ImportTradeManage.getInstance().findTradeBean(tradeCode);
              
            String userId = auth.getUserId();
            String orgId = auth.getUnitId();
            
            ExportImportManager eim = ExportImportManager.getInstance();
            DatabaseHelper dh = null;
            try {
                dh = new DatabaseHelper(datasource);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int taskId = dh.getNextValue("ID_BACKGROUND_TASK");
            task = eim.addImportTask(taskId, datasource, importTradeBean, filename);
            if (task == null) {
                throw new Exception("当前导入人数过多，请稍后重试。");
            }  else {
                json.put("taskID", task.getTaskID());
                task.setCreator(userId);
                task.setCreatororg(orgId);
                task.setaUser(auth);
                auth.putAttribute("BACKGROUND_IMPORT_XLS_TASK", task);      
            }
        }
        return "success";
    }
    /**
     * 读取当前用户下导入任务的进度信息并返回
     * @return String 
     * @throws Exception
     */
    public String refresh() throws Exception {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        TaskImportXLS task = (TaskImportXLS) auth.getAttribute("BACKGROUND_IMPORT_XLS_TASK");
        if (task != null) {
        	/**当前导入记录数*/
        	json.put("curRecordNum", task.getCurRecordNum());
        	/**导入记录总数*/
            json.put("totalNum", task.getTotalNum());
            /**当前状态*/
            json.put("importState", task.getImportState());
            /**当前sheet中记录数*/
            json.put("curSheetRecordNum", task.getCurSheetRecordNum());
            /**当前sheet号*/
            json.put("sheetNum", task.getSheetNum() + 1);
            /**sheet总数*/
            json.put("totalSheetNum", task.getTotalSheetNum());
            /**已导入完成sheet数*/
            json.put("finishSheetNum", task.getFinishSheetNum());
            /**已导入完成记录数*/
            json.put("finishRecordNum", task.getFinishRecordNum());
            /** 返回导入批次号***/
            json.put("pkHead", task.getPKHead());
            if (task.isRunning()) {
                json.put("result", "999");                
            } else {
            	/**当前状态导入成功*/
            	json.put("importState", 3);
                task = null;
                auth.putAttribute("BACKGROUND_IMPORT_XLS_TASK", null); 
                Object o = auth.getAttribute(ImportInterface.BACK_IMPORT_ERROR);
                json.put("result", "200");
                //如果错误信息不为空，则处理错误信息
                if(o!=null){
                	json.put(ImportInterface.BACK_IMPORT_ERROR, o);
                	auth.putAttribute(ImportInterface.BACK_IMPORT_ERROR, null);
                	json.put("BACK_RUN_ERROR", auth.getAttribute("BACK_RUN_ERROR"));
                	auth.putAttribute("BACK_RUN_ERROR", null);
                	json.put("result", "900");
                	
                }
            }
            
        } else {
        	 //System.out.println("task = null");
        }
        return "success";
    }
    
    
    @SuppressWarnings("unchecked")
    public void setCondition(String condition) {
        try {
            this.json = (Map<String, Object>) JSONUtil.deserialize(condition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getJson() {
        return json;
    }
    
}
