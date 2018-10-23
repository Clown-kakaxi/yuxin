package com.yuchengtech.bcrm.workplat.action;

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
 * 获取提醒规则类型action
 * @author hujun
 * 2014-07-7
 */

@ParentPackage("json-default")
@Action(value="/RemindRuleTypeGetAction", results={
    @Result(name="success", type="json", params = {"actionName", "VipAddValueNameAction" }),
})
public class RemindRuleTypeNameAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性	
    private List<HashMap<String, String>> JSON;//声明返回值
    
    /**
     * 构造方法 ，
     */
    public RemindRuleTypeNameAction() {
    	JSON = new ArrayList<HashMap<String, String>>();
    }

    public String index() {
        return "success";
    }
    public List<HashMap<String, String>> getJSON() throws SQLException {
      	StringBuilder sb = new StringBuilder("");
  		sb.append("select f_value,f_code,f_id,(case f_code  when 't*' then  '0' else ('t'||SUBSTR(f_code,0,1)) end) as type from OCRM_SYS_LOOKUP_ITEM where f_lookup_id='REMIND_TYPE' ");
  		List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) new QueryHelper(sb.toString(), ds.getConnection()).getJSON().get("data");
		if(tempRowsList != null && tempRowsList.size()>0){
			for(int i=0;i<tempRowsList.size();i++){
				Map<?, ?> map = tempRowsList.get(i);
				HashMap<String,String> eventMap = new HashMap<String,String>();
				eventMap.put("F_CODE",(String) map.get("F_CODE"));
				eventMap.put("F_VALUE",(String) map.get("F_VALUE"));
				eventMap.put("TYPE",(String) map.get("TYPE"));
				JSON.add(eventMap);
			}
		}
		HashMap<String,String> eventMap0 = new HashMap<String,String>();
  		HashMap<String,String> eventMap1 = new HashMap<String,String>();
  		HashMap<String,String> eventMap2 = new HashMap<String,String>();
  		HashMap<String,String> eventMap3 = new HashMap<String,String>();
  		HashMap<String,String> eventMap4 = new HashMap<String,String>();
  		HashMap<String,String> eventMap5 = new HashMap<String,String>();
  		HashMap<String,String> eventMap6 = new HashMap<String,String>();
  		HashMap<String,String> eventMap7 = new HashMap<String,String>();
		HashMap<String,String> eventMap8 = new HashMap<String,String>();
		HashMap<String,String> eventMap9 = new HashMap<String,String>();
		HashMap<String,String> eventMap10 = new HashMap<String,String>();
		HashMap<String,String> eventMap11 = new HashMap<String,String>();
  		eventMap0.put("F_CODE","t0");
  		eventMap0.put("F_VALUE","反洗钱指标提醒");
  		eventMap0.put("TYPE","0");
  		JSON.add(eventMap0);
  		eventMap1.put("F_CODE","t1");
  		eventMap1.put("F_VALUE","账户变动情况");
  		eventMap1.put("TYPE","0");
  		JSON.add(eventMap1);
  		eventMap2.put("F_CODE","t2");
  		eventMap2.put("F_VALUE","产品发售、到期和购买信息提醒");
  		eventMap2.put("TYPE","0");
  		JSON.add(eventMap2);
  		eventMap3.put("F_CODE","t3");
  		eventMap3.put("F_VALUE","生日、节假日和客户重大事件提醒");
  		eventMap3.put("TYPE","0");
  		JSON.add(eventMap3);
  		eventMap4.put("F_CODE","t4");
  		eventMap4.put("F_VALUE","客户状态变动情况提醒");
  		eventMap4.put("TYPE","0");
  		JSON.add(eventMap4);
  		eventMap5.put("F_CODE","t5");
  		eventMap5.put("F_VALUE","客户联系和维护提醒");
  		eventMap5.put("TYPE","0");
  		JSON.add(eventMap5);
  		eventMap6.put("F_CODE","t6");
  		eventMap6.put("F_VALUE","授信客户提醒");
  		eventMap6.put("TYPE","0");
  		JSON.add(eventMap6);
  		eventMap7.put("F_CODE","t7");
  		eventMap7.put("F_VALUE","客户管理提醒");
  		eventMap7.put("TYPE","0");
  		JSON.add(eventMap7);
  		eventMap8.put("F_CODE","t8");
  		eventMap8.put("F_VALUE","营销类提醒");
  		eventMap8.put("TYPE","0");
  		JSON.add(eventMap8);
  		eventMap9.put("F_CODE","t9");
  		eventMap9.put("F_VALUE","电访拜访提醒");
  		eventMap9.put("TYPE","0");
  		JSON.add(eventMap9);
  		eventMap10.put("F_CODE","ta");
  		eventMap10.put("F_VALUE","卡片相关提醒");
  		eventMap10.put("TYPE","0");
  		JSON.add(eventMap10);
  		eventMap10.put("F_CODE","tb");
  		eventMap10.put("F_VALUE","CallReport提醒");
  		eventMap10.put("TYPE","0");
  		JSON.add(eventMap11);
          return JSON;
      }

}
