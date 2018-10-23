package com.yuchengtech.bcrm.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.core.QueryHelper;
/**
 * 获取增值服务名称action
 * @author hujun
 * 2014-06-25
 */

@ParentPackage("json-default")
@Action(value="/VipAddValueNameAction", results={
    @Result(name="success", type="json", params = {"actionName", "VipAddValueNameAction" }),
})
public class VipAddValueNameAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性	
    private List<HashMap<String, String>> JSON;//声明返回值
    
    /**
     * 构造方法 ，创建空的日志事件类型列表
     */
    public VipAddValueNameAction() {
    	JSON = new ArrayList<HashMap<String, String>>();
    }

    public String index() {
        return "success";
    }
    public List<HashMap<String, String>> getJSON() throws SQLException {
      	StringBuilder sb = new StringBuilder("");
  		sb.append("SELECT ADD_SERVICE_NAME AS VALUE,ADD_SERVICE_NAME AS KEY FROM OCRM_F_CI_ALIANCERVICE_RELA ");
  		try {
  			QueryHelper query =  new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				for(int i=0;i<tempRowsList.size();i++){
					Map<?, ?> map = tempRowsList.get(i);
					HashMap<String,String> eventMap = new HashMap<String,String>();
					eventMap.put("key",(String) map.get("KEY"));
					eventMap.put("value",(String) map.get("VALUE"));
					JSON.add(eventMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return JSON;
   }

}
