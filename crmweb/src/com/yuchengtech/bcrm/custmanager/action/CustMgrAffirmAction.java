package com.yuchengtech.bcrm.custmanager.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bcrm.custmanager.service.CustMgrAffirmService;

@Action("/custMgrAffirm")
@Results({@org.apache.struts2.convention.annotation.Result(name="success", type="redirectAction", params={"actionName", "custMgrAffirm"})})
public class CustMgrAffirmAction extends ValidationAwareSupport
  implements ModelDriven<Object>, Validateable
{
  private OcrmFCmCustMgrInfo wi = new OcrmFCmCustMgrInfo();
  private Collection<OcrmFCmCustMgrInfo> wic;
  private Long id;
  private HttpServletRequest request;

  @Autowired
  private CustMgrAffirmService wis;

  public HttpHeaders show()
  {
    this.wi = (OcrmFCmCustMgrInfo) this.wis.find(this.id);
    return new DefaultHttpHeaders("show");
  }

  public HttpHeaders index()
  {
    this.wic = this.wis.findAll();
    return new DefaultHttpHeaders("index").disableCaching
      ();
  }

  public String edit()
  {
    return "edit";
  }

  public String editNew()
  {
    return "editNew";
  }

  public String deleteConfirm()
  {
    return "deleteConfirm";
  }

  public String destroy()
  {
    ActionContext ctx = ActionContext.getContext();
    this.request = 
      ((HttpServletRequest)ctx.get
      ("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
    String idStr = this.request.getParameter("idStr");

    this.wis.remove(idStr);
    return "success";
  }

  public String create()
    throws Exception
  {
    ActionContext ctx = ActionContext.getContext();
    this.request = ((HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
    String s3 = this.request.getParameter("models");
    String s4 = this.request.getParameter("models2");
    JSONArray jarray = JSONArray.fromObject(s3);
    JSONArray jarray2 = JSONArray.fromObject(s4);
    
    

    this.wis.save(jarray);
    this.wis.remove(jarray2);
    return "success";
  }

  public String update()
  {
    this.wis.save(this.wi);
    return "success";
  }

  public void validate()
  {
  }

  public Object getModel()
  {
    return ((this.wic != null) ? this.wic : this.wi);
  }
}