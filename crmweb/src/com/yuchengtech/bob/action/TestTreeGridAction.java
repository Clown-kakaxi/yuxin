/**
 * 
 */
package com.yuchengtech.bob.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 * @author yaoliang
 *
 */
@ParentPackage("json-default")

@Action(value="/test-tree-grid", results={
    @Result(name="success", type="json")
})
public class TestTreeGridAction extends BaseAction {
	
	public void index(){
		
		String  treeGridStr = "[{xzqhdm:'110000000000',xzqhmc:'北京市',xzqhjc:'北京市 ',xzqhjb:'1',xzqhjbmc:'',leaf:false},{xzqhdm:'120000000000',xzqhmc:'天津市 ',xzqhjc:'天津市',xzqhjb:'1',xzqhjbmc:'',leaf:false}, {xzqhdm:'130000000000',xzqhmc:'河北省',xzqhjc:'河北省 ',xzqhjb:'1',xzqhjbmc:'',leaf:false},{xzqhdm:'140000000000',xzqhmc:'山西省 ',xzqhjc:'山西省',xzqhjb:'1',xzqhjbmc:'',leaf:false}, {xzqhdm:'150000000000',xzqhmc:'内蒙古自治区',xzqhjc:'内蒙古自治区 ',xzqhjb:'1',xzqhjbmc:'',leaf:false}]";
		
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			response.getWriter().write(treeGridStr);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
}
