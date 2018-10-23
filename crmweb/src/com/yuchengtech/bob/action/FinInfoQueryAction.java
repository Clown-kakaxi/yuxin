package com.yuchengtech.bob.action;

import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value = "/finInfoQuery", results = { @Result(name = "success", type = "json"), })
public class FinInfoQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	@Override
	public void prepare() {
		StringBuffer sb=new StringBuffer("SELECT a.FIN_INFO_ID,a.UPLOAD_DT,a.UPLOAD_PER_ID,a.BELONG_DIR_ID,a.FIN_INFO_ISSUING,a.TITLE,a.TITLE_PIC_PATH,b.dir_name from CRM_F_SYS_FIN_INFO a,CRM_F_SYS_DIR b WHERE a.belong_dir_id=b.dir_id");
		Set<String> set=this.getJson().keySet();
		Iterator<String> it=set.iterator();
		while(it.hasNext()){
			String key=it.next();
			if(null!=this.getJson().get(key)&&!"".equals(this.getJson().get(key))){
				if("DIR_ID".equals(key)){
					sb.append(" and b.dir_id="+this.getJson().get(key));
				}else{
					sb.append(" and a."+key+" like '%"+this.getJson().get(key)+"%'");
				}
			}
		}
		setPrimaryKey("a.FIN_INFO_ID desc");
		SQL = sb.toString();
		datasource = dsOracle;
	}

}
