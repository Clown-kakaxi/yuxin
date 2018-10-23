package com.yuchengtech.bob.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bob.service.RollCustomerListService;

@ParentPackage("json-default")

@Action(value="/roll-member-batch", results={
    @Result(name="success", type="json")
})
public class RollMemberBatchAction {

	private String idStr;
	private String rollId;
	@Autowired
	private RollCustomerListService  rollCustomerListService;
	
	public String index(){
		return "success";
	}
	
    public String batchCreate() throws Exception {
//    	RollCustomerListService rollCustomerListService = new RollCustomerListService();
        rollCustomerListService.batchAdd(idStr,rollId);
        return "success";
    }
    
    public String batchDestroy() {
//    	RollCustomerListService rollCustomerListService = new RollCustomerListService();
        rollCustomerListService.batchRemove(idStr);
        return "success";
    }

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getRollId() {
		return rollId;
	}

	public void setRollId(String rollId) {
		this.rollId = rollId;
	}	
    
    
}
