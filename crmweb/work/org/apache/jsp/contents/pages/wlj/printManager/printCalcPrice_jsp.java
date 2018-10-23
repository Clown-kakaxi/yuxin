package org.apache.jsp.contents.pages.wlj.printManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.springframework.security.core.context.SecurityContextHolder;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.version.VersionInformation.Version;

public final class printCalcPrice_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/contents/pages/wlj/printManager/common/printInclude.jsp");
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
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar a=\"");
      out.print(request.getContextPath());
      out.write("\";\r\n");
      out.write("\tvar basepath = \"/\" + a.substring(1, a.length);\t\r\n");
      out.write("\t");

	if((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
		//Session过期，重新登录
		out.print("top.location.href = basepath;");
	}else{
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();
		out.print("var __userId = '"+userId+"';");
		out.print("var __userName = '"+auth.getUsername()+"';");
		out.print("var __userCname = '"+auth.getUsername()+"';");
		//公共JS变量
		out.print("var __units = '"+auth.getUnitId()+"';");
		out.print("var __unitname = '"+auth.getUnitName()+"';");
		out.print("var __unitlevel = '"+auth.getUnitlevel()+"';");
	}
	
      out.write("\r\n");
      out.write("</script>\r\n");
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
      out.write("<!-- 控制点权限判断组件 -->\r\n");
      if (_jspx_meth_version_005fframeScript_005f6(_jspx_page_context))
        return;
      out.write("  \r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("//Ajax请求超时时间\r\n");
      out.write("Ext.Ajax.timeout = 90000;\r\n");
      out.write("</script>");
      out.write('\r');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_version_005fframeLink_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_version_005fframeScript_005f7(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\tvar LOAN_PRICE = '");
      out.print(request.getParameter("LOAN_PRICE"));
      out.write("';\r\n");
      out.write("\t\tvar PLAN_APPLY = '");
      out.print(request.getParameter("PLAN_APPLY"));
      out.write("';\r\n");
      out.write("\t\tvar LOAN = '");
      out.print(request.getParameter("LOAN"));
      out.write("';\r\n");
      out.write("\t\tvar LOAN_PERCENT = '");
      out.print(request.getParameter("LOAN_PERCENT"));
      out.write("';\r\n");
      out.write("\t\tvar PRICE = '");
      out.print(request.getParameter("PRICE"));
      out.write("';\r\n");
      out.write("\t\tvar LOANABLE_PERCENT = '");
      out.print(request.getParameter("LOANABLE_PERCENT"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT1 = '");
      out.print(request.getParameter("LIMIT1"));
      out.write("';\r\n");
      out.write("\t\tvar REPAY1 = '");
      out.print(request.getParameter("REPAY1"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT1_TIME = '");
      out.print(request.getParameter("LIMIT1_TIME"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT1_RATE = '");
      out.print(request.getParameter("LIMIT1_RATE"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT1_REPAY = '");
      out.print(request.getParameter("LIMIT1_REPAY"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT2 = '");
      out.print(request.getParameter("LIMIT2"));
      out.write("';\r\n");
      out.write("\t\tvar REPAY2 = '");
      out.print(request.getParameter("REPAY2"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT2_TIME = '");
      out.print(request.getParameter("LIMIT2_TIME"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT2_RATE = '");
      out.print(request.getParameter("LIMIT2_RATE"));
      out.write("';\r\n");
      out.write("\t\tvar LIMIT2_REPAY = '");
      out.print(request.getParameter("LIMIT2_REPAY"));
      out.write("';\r\n");
      out.write("\t\tvar AMOUNT_REPAY = '");
      out.print(request.getParameter("AMOUNT_REPAY"));
      out.write("';\r\n");
      out.write("\t\tvar AMOUNT_RATE = '");
      out.print(request.getParameter("AMOUNT_RATE"));
      out.write("';\r\n");
      out.write("\t\tvar PRICE_LEVEL = '");
      out.print(request.getParameter("PRICE_LEVEL"));
      out.write("';\r\n");
      out.write("\t</script>\r\n");
      out.write("\t");
      if (_jspx_meth_version_005fframeScript_005f8(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
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

  private boolean _jspx_meth_version_005fframeScript_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameScript
    com.yuchengtech.crm.version.VersionControlTagFrameScript _jspx_th_version_005fframeScript_005f0 = new com.yuchengtech.crm.version.VersionControlTagFrameScript();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f0);
    _jspx_th_version_005fframeScript_005f0.setJspContext(_jspx_page_context);
    // /contents/pages/wlj/printManager/common/printInclude.jsp(27,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f0.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(27,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /contents/pages/wlj/printManager/common/printInclude.jsp(28,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f1.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(28,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /contents/pages/wlj/printManager/common/printInclude.jsp(29,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f2.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(29,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /contents/pages/wlj/printManager/common/printInclude.jsp(30,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f3.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(30,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /contents/pages/wlj/printManager/common/printInclude.jsp(31,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f4.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(31,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /contents/pages/wlj/printManager/common/printInclude.jsp(32,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f5.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(32,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /contents/pages/wlj/printManager/common/printInclude.jsp(34,0) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f6.setType("text/javascript");
    // /contents/pages/wlj/printManager/common/printInclude.jsp(34,0) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f6.setSrc("/contents/pages/common/ViewContext.js");
    _jspx_th_version_005fframeScript_005f6.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f6);
    return false;
  }

  private boolean _jspx_meth_version_005fframeLink_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  version:frameLink
    com.yuchengtech.crm.version.VersionControlTagFrameLink _jspx_th_version_005fframeLink_005f0 = new com.yuchengtech.crm.version.VersionControlTagFrameLink();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f0);
    _jspx_th_version_005fframeLink_005f0.setJspContext(_jspx_page_context);
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(11,1) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f0.setType("text/css");
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(11,1) name = rel type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f0.setRel("stylesheet");
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(11,1) name = href type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeLink_005f0.setHref("/contents/pages/wlj/printManager/common/printCss.css");
    _jspx_th_version_005fframeLink_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeLink_005f0);
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
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(12,1) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f7.setType("text/javascript");
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(12,1) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f7.setSrc("/contents/pages/wlj/printManager/common/printParam.js");
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
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(34,1) name = type type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f8.setType("text/javascript");
    // /contents/pages/wlj/printManager/printCalcPrice.jsp(34,1) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_version_005fframeScript_005f8.setSrc("/contents/pages/wlj/printManager/printCalcPrice.js");
    _jspx_th_version_005fframeScript_005f8.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_version_005fframeScript_005f8);
    return false;
  }
}
