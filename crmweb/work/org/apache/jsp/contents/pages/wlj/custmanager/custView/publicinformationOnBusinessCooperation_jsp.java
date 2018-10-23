package org.apache.jsp.contents.pages.wlj.custmanager.custView;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.springframework.security.core.context.SecurityContextHolder;
import com.yuchengtech.bob.vo.AuthUser;
import java.util.List;
import com.yuchengtech.bob.common.LogService;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.bob.core.LookupManager;
import com.yuchengtech.crm.constance.OperateTypeConstant;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.yuchengtech.crm.version.VersionInformation;
import java.util.Map;
import com.yuchengtech.crm.version.VersionInformation.Version;

public final class publicinformationOnBusinessCooperation_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/contents/pages/common/includes.jsp");
    _jspx_dependants.add("/WEB-INF/versionControlTag.tld");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("\t<title>客户关系管理系统</title>\r\n");
      out.write("\t<meta name=\"keywords\" content=\"客户关系管理系统,CRM\" />\r\n");
      out.write("\t<meta name=\"description\" content=\"客户关系管理系统,CRM\" />\r\n");
      out.write("\t<meta name=\"Author\" content=\"YuchengTech\" />\r\n");
      out.write("\t<link rel=\"shortcut icon\" href=\"favicon.ico\" />\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar a=\"");
      out.print(request.getContextPath());
      out.write("\";\r\n");
      out.write("\tvar basepath = \"/\" + a.substring(1, a.length);\t\r\n");
      out.write("\t");

	String frontVersion = VersionInformation.getInstance().getVersionInfo(VersionInformation.Version.SUB);
	String frameVersion = VersionInformation.getInstance().getVersionInfo(VersionInformation.Version.FRAME);
	out.print("var __frontVersion = '"+frontVersion+"';");
	out.print("var __frameVersion = '"+frameVersion+"';");
	if((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
		//Session过期，重新登录
		out.print("top.location.href = basepath;");
	}else{
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();
		out.print("var __userId = '"+userId+"';");
		out.print("var __userName = '"+auth.getUsername()+"';");
		out.print("var __userCname = '"+auth.getUsername()+"';");
		out.print("var __userIcon = '"+(auth.getTemp1() != null?auth.getTemp1():"")+"';");
		out.print("var __updatePwdStat = '"+(auth.getTemp2() != null?auth.getTemp2():"")+"';");
		
		out.print("var __themeColorId = '"+auth.getAttribute("THEME_COLOR_ID")+"';");
		
		out.print("var __background='"+auth.getAttribute("crm.front.BG")+"';");
		out.print("var __theme='"+auth.getAttribute("crm.front.TH")+"';");
		out.print("var __wordsize='"+auth.getAttribute("crm.front.WS")+"';");
		out.print("var __busiLine= '"+auth.getBelongBusiLine()+"';");
		
		//当前用户角色ID串
		String role = "";
		//当前用户角色Code串
		String roleCode ="";
		String roleName ="";
		//当前用户角色类型：1：对私  2：对公
		String roleType = "1";
		for(int i=0;i<auth.getAuthorities().size();i++)
			role += auth.getAuthorities().get(i).getAuthority()+"$";
		
		for(int i=0;i<auth.getRolesInfo().size();i++){
			Map roleMap=(Map)auth.getRolesInfo().get(i);
			roleCode+=roleMap.get("ROLE_CODE")+"$";
			roleName+="," + roleMap.get("ROLE_NAME");
		}
		roleName = roleName.length() > 0?roleName.substring(1):roleName;
		if(roleCode.toLowerCase().startsWith("c_")){
			roleType = "2";
			//customerManagerCode = "1014";
		}
		//当前用户机构ID
		String orgId = auth.getUnitId();
		//公共JS变量
		out.print("var __roles = '"+role+"';");
		out.print("var __roleCodes = '"+roleCode+"';");
		out.print("var __roleNames = '"+roleName+"';");
		out.print("var __roleType = '"+ roleType +"';");
		out.print("var __units = '"+auth.getUnitId()+"';");
		out.print("var __grants = [];");
		String resId = request.getParameter("resId");
		out.print("var __resId = '"+resId+"';");
		out.print("var __unitname = '"+auth.getUnitName()+"';");
		out.print("var __unitlevel = '"+auth.getUnitlevel()+"';");
		out.print("var __appId = '"+SystemConstance.LOGIC_SYSTEM_APP_ID+"';");
		//登录类型（单角色或多角色）
		out.print("var __loginType = '"+auth.getLoginType()+"';");
		//security变量
		out.print("var __secMsgType = '';");
		out.print("var __secMsg = '';");
		if (auth.getCredentialInfo() != null) {
			out.print("__secMsgType = '"+auth.getCredentialInfo().getInfoType()+"';");
			out.print("__secMsg = '"+auth.getCredentialInfo().getMessage()+"';");
		}
		//判断如果是菜单URL请求，则做两件事  
		//1、将菜单下的控制点写入公共变量
		//2、记录菜单访问日志
		if(resId!=null && !"-1".equals(resId) && !"".equals(resId)){
			List<String> grants = auth.findGrantByRes(resId);
			if(grants!=null){
				for(int i=0;i<grants.size();i++){
					out.print("__grants.push('"+grants.get(i)+"');");
				}
			}
			//增加菜单日志访问记录
			//LogUtils lu=new LogUtils();
			LogService.loginfo.setLoginIp(request.getRemoteAddr());
			LogService.loginfo.setLogTypeId(Long.valueOf(OperateTypeConstant.VISIT_MENU+""));
			LogService.loginfo.setAfterValue(request.getServletPath());
			LogService.loginfo.setContent(OperateTypeConstant.getOperateText(OperateTypeConstant.VISIT_MENU)+":"+LookupManager.getInstance().getMenuName(resId));
			LogService.addLog();
		} 
		//获取异常描述信息
		ApplicationContext app =WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletConfig().getServletContext()) ;
		Map<String, String> errMsgMap = (Map<String, String>) app.getBean("getErrMsgMap");
		Map<String, String> errPageMap = (Map<String, String>) app.getBean("getErrPageMap");
		String defaultErrMsg = (String) app.getBean("getDefaultErrMsg");
		String defaultErrPage = (String) app.getBean("getDefaultErrPage");
		out.print("var __errMsgMap = [];");
		for(String key : errMsgMap.keySet()){
			out.print("__errMsgMap.push({code:'"+key+"',content:'"+errMsgMap.get(key)+"'});");
		}
	}
	
      out.write("\r\n");
      out.write("</script>\r\n");
      if (_jspx_meth_version_005fframeLink_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
 
	if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\""+request.getContextPath()+"/contents/wljFrontFrame/styles/search/searchthemes/"+auth.getAttribute("crm.front.TH")+"/main.css\" />");
	/*	if(auth.getAttribute("crm.front.WS").equals("ra_normal")){
			out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\""+request.getContextPath()+"/contents/wljFrontFrame/styles/search/searchcss/font_normal.css\" />");
		}else{
			out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\""+request.getContextPath()+"/contents/wljFrontFrame/styles/search/searchcss/font_big.css\" />");
		}*/
	}

      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeLink_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<!-- 补丁样式文件，对于Ext中由于css样式引起的公共性质的BUG，修复代码均添加在此文件中 -->\r\n");
      if (_jspx_meth_version_005fframeLink_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_version_005fframeScript_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeScript_005f1(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeScript_005f2(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeScript_005f3(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeScript_005f4(_jspx_page_context))
        return;
      out.write("  \r\n");
      if (_jspx_meth_version_005fframeScript_005f5(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<!-- 校验以及数据格式化组件 -->\r\n");
      if (_jspx_meth_version_005fframeScript_005f6(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeScript_005f7(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<!-- 控制点权限判断组件 -->\r\n");
      if (_jspx_meth_version_005fframeScript_005f8(_jspx_page_context))
        return;
      out.write("  \r\n");
      out.write("<!-- 导入导出组件 -->\r\n");
      if (_jspx_meth_version_005fframeScript_005f9(_jspx_page_context))
        return;
      out.write(' ');
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_version_005fframeScript_005f10(_jspx_page_context))
        return;
      out.write(" \r\n");
      out.write("<!-- 流程发起选择办理人窗口 -->\r\n");
      if (_jspx_meth_version_005fframeScript_005f11(_jspx_page_context))
        return;
      out.write(" \r\n");
      out.write("\r\n");
      out.write("<!--");
      if (_jspx_meth_version_005fframeScript_005f12(_jspx_page_context))
        return;
      out.write("  -->\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("/*特殊按键屏蔽\r\n");
      out.write(" * \r\n");
      out.write(" \r\n");
      out.write("document.oncontextmenu=function(){\r\n");
      out.write("\treturn false;\r\n");
      out.write("};\r\n");
      out.write("document.onkeydown = function(){\r\n");
      out.write("\tif((window.event.ctrlKey)&&((window.event.keyCode==67))){\r\n");
      out.write("\t\tevent.returnValue=false;\r\n");
      out.write("\t}\r\n");
      out.write("};*/\r\n");
      out.write("JsContext.initContext();\r\n");
      out.write("//Ajax请求超时时间\r\n");
      out.write("Ext.Ajax.timeout = 90000;\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t");

		String custId = request.getParameter("custId");
		String busiId = request.getParameter("busiId");
		out.print("var _custId = '"+custId+"';");	
		out.print("var _busiId = '"+busiId+"';");
	
      out.write("\r\n");
      out.write("\t//注：此tabs变量必须按此格式定义,且此文件只能更改tabs变量里面的内容\r\n");
      out.write("\tvar tabs =[{\r\n");
      out.write("\t\ttitle:'机构存款账号信息',\r\n");
      out.write("\t\turl:'/contents/pages/wlj/custmanager/custView/publicinformationOnBusinessCooperation.js'\r\n");
      out.write("\t},{\r\n");
      out.write("\t\ttitle:'机构贷款账号信息',\r\n");
      out.write("\t\turl:'/contents/pages/wlj/custmanager/custView/publicinformationOnBusinessCooperation1.js'\r\n");
      out.write("\t}];\r\n");
      out.write("\t</script>\r\n");
      out.write("\t");
      if (_jspx_meth_version_005fframeScript_005f13(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_version_005fframeLink_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameLink
    com.yuchengtech.crm.version.VersionControlTagFrameLink _jspx_th_version_005fframeLink_005f0 = new com.yuchengtech.crm.version.VersionControlTagFrameLink();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f0);
    _jspx_th_version_005fframeLink_005f0.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(117,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f0.setType("text/css");
    // /contents/pages/common/includes.jsp(117,0) name = rel type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f0.setRel("stylesheet");
    // /contents/pages/common/includes.jsp(117,0) name = href type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f0.setHref("/contents/resource/ext3/resources/css/ext-all-notheme.css");
    _jspx_th_version_005fframeLink_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f0);
    return false;
  }

  private boolean _jspx_meth_version_005fframeLink_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameLink
    com.yuchengtech.crm.version.VersionControlTagFrameLink _jspx_th_version_005fframeLink_005f1 = new com.yuchengtech.crm.version.VersionControlTagFrameLink();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f1);
    _jspx_th_version_005fframeLink_005f1.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(129,0) name = rel type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f1.setRel("stylesheet");
    // /contents/pages/common/includes.jsp(129,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f1.setType("text/css");
    // /contents/pages/common/includes.jsp(129,0) name = href type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f1.setHref("/contents/resource/ext3/ux/css/toolbars.css");
    _jspx_th_version_005fframeLink_005f1.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f1);
    return false;
  }

  private boolean _jspx_meth_version_005fframeLink_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameLink
    com.yuchengtech.crm.version.VersionControlTagFrameLink _jspx_th_version_005fframeLink_005f2 = new com.yuchengtech.crm.version.VersionControlTagFrameLink();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f2);
    _jspx_th_version_005fframeLink_005f2.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(131,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f2.setType("text/css");
    // /contents/pages/common/includes.jsp(131,0) name = rel type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f2.setRel("stylesheet");
    // /contents/pages/common/includes.jsp(131,0) name = href type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f2.setHref("/contents/pages/common/Crm-Ext-Patch-Css-1.000-v1.0.css");
    _jspx_th_version_005fframeLink_005f2.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f2);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f0 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f0);
    _jspx_th_version_005fframeScript_005f0.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(133,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f0.setType("text/javascript");
    // /contents/pages/common/includes.jsp(133,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f0.setSrc("/contents/resource/ext3/adapter/ext/ext-base.js");
    _jspx_th_version_005fframeScript_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f0);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f1 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f1);
    _jspx_th_version_005fframeScript_005f1.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(134,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f1.setType("text/javascript");
    // /contents/pages/common/includes.jsp(134,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f1.setSrc("/contents/resource/ext3/ext-all.js");
    _jspx_th_version_005fframeScript_005f1.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f1);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f2 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f2);
    _jspx_th_version_005fframeScript_005f2.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(135,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f2.setType("text/javascript");
    // /contents/pages/common/includes.jsp(135,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f2.setSrc("/contents/resource/ext3/ux/ux-all.js");
    _jspx_th_version_005fframeScript_005f2.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f2);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f3 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f3);
    _jspx_th_version_005fframeScript_005f3.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(136,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f3.setType("text/javascript");
    // /contents/pages/common/includes.jsp(136,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f3.setSrc("/contents/pages/common/Crm-Ext-Patch-1.000-v1.0.js");
    _jspx_th_version_005fframeScript_005f3.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f3);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f4 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f4);
    _jspx_th_version_005fframeScript_005f4.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(137,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f4.setType("text/javascript");
    // /contents/pages/common/includes.jsp(137,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f4.setSrc("/contents/pages/common/Crm-Ext-Extends-1.000-v1.0.js");
    _jspx_th_version_005fframeScript_005f4.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f4);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f5 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f5);
    _jspx_th_version_005fframeScript_005f5.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(138,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f5.setType("text/javascript");
    // /contents/pages/common/includes.jsp(138,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f5.setSrc("/contents/resource/ext3/locale/ext-lang-zh_CN.js");
    _jspx_th_version_005fframeScript_005f5.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f5);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f6 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f6);
    _jspx_th_version_005fframeScript_005f6.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(140,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f6.setType("text/javascript");
    // /contents/pages/common/includes.jsp(140,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f6.setSrc("/contents/commonjs/DataFormat.js");
    _jspx_th_version_005fframeScript_005f6.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f6);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f7 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f7);
    _jspx_th_version_005fframeScript_005f7.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(141,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f7.setType("text/javascript");
    // /contents/pages/common/includes.jsp(141,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f7.setSrc("/contents/commonjs/Validator.js");
    _jspx_th_version_005fframeScript_005f7.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f7);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f8 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f8);
    _jspx_th_version_005fframeScript_005f8.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(143,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f8.setType("text/javascript");
    // /contents/pages/common/includes.jsp(143,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f8.setSrc("/contents/pages/common/ViewContext.js");
    _jspx_th_version_005fframeScript_005f8.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f8);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f9 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f9);
    _jspx_th_version_005fframeScript_005f9.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(145,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f9.setType("text/javascript");
    // /contents/pages/common/includes.jsp(145,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f9.setSrc("/contents/pages/common/Com.yucheng.crm.common.ImpExp.js");
    _jspx_th_version_005fframeScript_005f9.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f9);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f10 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f10);
    _jspx_th_version_005fframeScript_005f10.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(146,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f10.setType("text/javascript");
    // /contents/pages/common/includes.jsp(146,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f10.setSrc("/contents/pages/common/Com.yucheng.crm.common.Upload.js");
    _jspx_th_version_005fframeScript_005f10.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f10);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f11 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f11);
    _jspx_th_version_005fframeScript_005f11.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(148,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f11.setType("text/javascript");
    // /contents/pages/common/includes.jsp(148,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f11.setSrc("/contents/pages/common/EchainSelectNextNodeUserPanel.js");
    _jspx_th_version_005fframeScript_005f11.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f11);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f12(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f12 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f12);
    _jspx_th_version_005fframeScript_005f12.setJspContext(_jspx_page_context);
    // /contents/pages/common/includes.jsp(150,4) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f12.setType("text/javascript");
    // /contents/pages/common/includes.jsp(150,4) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f12.setSrc("/contents/pages/common/SystemBooter.js");
    _jspx_th_version_005fframeScript_005f12.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f12);
    return false;
  }

  private boolean _jspx_meth_version_005fframeScript_005f13(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f13 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f13);
    _jspx_th_version_005fframeScript_005f13.setJspContext(_jspx_page_context);
    // /contents/pages/wlj/custmanager/custView/publicinformationOnBusinessCooperation.jsp(27,1) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f13.setType("text/javascript");
    // /contents/pages/wlj/custmanager/custView/publicinformationOnBusinessCooperation.jsp(27,1) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f13.setSrc("/contents/pages/common/Com.yucheng.bcrm.common.tabpanel.js");
    _jspx_th_version_005fframeScript_005f13.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f13);
    return false;
  }
}
