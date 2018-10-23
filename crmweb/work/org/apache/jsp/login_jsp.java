package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.springframework.security.web.WebAttributes;
import com.yuchengtech.bob.core.CrmLisenceManager;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

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
      out.write("\r\n");
      out.write("\r\n");

	String currentTime = ""+System.currentTimeMillis();	
	String versionInfo = "Copyright &copy; "
						+ "Fubon Bank（China）Co., Ltd"/* CrmLisenceManager.getInstance().getCustomerName() */;
						//+ " Version："
						//+ CrmLisenceManager.getInstance().getVersion();

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("\t<title>客户关系管理系统</title>\r\n");
      out.write("\t<meta name=\"keywords\" content=\"客户关系管理系统,CRM\" />\r\n");
      out.write("\t<meta name=\"description\" content=\"客户关系管理系统,CRM\" />\r\n");
      out.write("\t<meta name=\"Author\" content=\"YuchengTech\" />\r\n");
      out.write("\t<link rel=\"shortcut icon\" href=\"favicon.ico\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"contents/wljFrontFrame/styles/search/searchthemes/blue/login.css\" />\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/contents/commonjs/controlCookie.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/contents/commonjs/jquery-1.5.2.min.js\"></script>\r\n");
      out.write("\t<!--[if lt IE 9]>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/contents/commonjs/PIE_IE678.js\"></script>\r\n");
      out.write("\t<![endif]-->\t\t\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\twindow.document.onkeydown = function(e){\r\n");
      out.write("\t\t\te = !e ? window.event : e;\r\n");
      out.write("\t\t\tvar key = window.event ? e.keyCode:e.which;\r\n");
      out.write("\t\t\tif(13==key){//监视是否按下'Enter'键\r\n");
      out.write("\t\t\t\t  setFromValue();\r\n");
      out.write("\t\t\t  }\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t \r\n");
      out.write("\t\tvar currentTime = ");
      out.print(currentTime);
      out.write(";\r\n");
      out.write("\t\tfunction setTarget(){\r\n");
      out.write("\t\t\tvar target = 'chat");
      out.print(currentTime);
      out.write("';\r\n");
      out.write("\t\t\tdocument.getElementById('formLogin').target = target;\r\n");
      out.write("\t\t\t//closeWindow();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\t//\r\n");
      out.write("\t\tfunction check() {\r\n");
      out.write("\t\t\tif (document.getElementById(\"username\").value.length == 0) {\r\n");
      out.write("\t\t\t\talert(\"用户名为空，请输入\");\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"username\").focus();\r\n");
      out.write("\t\t\t\tdocument.getElementById('submitBtn').disabled = false;\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\t\tif (document.getElementById(\"password\").value.length == 0) {\r\n");
      out.write("\t\t\t\talert(\"密码为空，请输入\");\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"password\").focus();\r\n");
      out.write("\t\t\t\tdocument.getElementById('submitBtn').disabled = false;\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\treturn true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\tfunction setFromValue() {\r\n");
      out.write("\t\t\tdocument.getElementById('submitBtn').disabled = true;\r\n");
      out.write("\t\t\tdocument.getElementById('j_username').value = document\r\n");
      out.write("\t\t\t\t\t.getElementById(\"username\").value;\r\n");
      out.write("\t\t\tdocument.getElementById('j_password').value = document\r\n");
      out.write("\t\t\t\t\t.getElementById(\"password\").value;\r\n");
      out.write("\t\t\tvar tempValue = document.getElementById(\"username\").value;\r\n");
      out.write("\t\t\tsetCookie(\"CRM_USER_ID1\", \"\", 30);\r\n");
      out.write("\t\t\tsetCookie(\"CRM_USER_ID1\", tempValue, 30);\r\n");
      out.write("\t\t\tdocument.getElementById('submit').click();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t//从Cookie获取用户登录ID\r\n");
      out.write("\t\tfunction getUserId() {\r\n");
      out.write("\t\t\tvar userid = getCookie(\"CRM_USER_ID1\");\r\n");
      out.write("\t\t\tif (userid != null && userid != \"\") {\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"username\").value = userid;\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"password\").focus();\r\n");
      out.write("\t\t\t} else\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"username\").focus();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\tfunction showErrorMsg() {\r\n");
      out.write("\t\t\tdocument.getElementById('submitBtn').disabled = false;\r\n");
      out.write("\t\t\tvar msg = document.getElementById(\"errorMsg\").value;\r\n");
      out.write("\t\t\t//closeWindow();\r\n");
      out.write("\t\t\tif (msg != '' && msg != 'undefined') {\r\n");
      out.write("\t\t\t\talert(msg);\r\n");
      out.write("\t\t\t\tdocument.getElementById('errorMsg').value = '';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"password\").focus();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\tfunction closeWindow() {\r\n");
      out.write("\t\t\tif (window.opener != null) {\r\n");
      out.write("\t\t\t\twindow.focus();\r\n");
      out.write("\t\t\t\twindow.opener.windowClose();\r\n");
      out.write("\t\t\t\twindow.opener = null;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\r\n");
      out.write("\t\t$(function() {\r\n");
      out.write("\t\t\tsetLoginUi();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\tfunction setLoginUi(){\r\n");
      out.write("\r\n");
      out.write("\t\t\tif (window.PIE) {\r\n");
      out.write("\t\t        $(\"#loginContainer\").each(function() {\r\n");
      out.write("\t\t            PIE.attach(this);\r\n");
      out.write("\t\t        });\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t\t\t$(\"#username,#password\").each(function() {\r\n");
      out.write("\t\t\t\t$(this).hover(function() {\r\n");
      out.write("\t\t\t\t\t$(this).parent(\"li\").addClass(\"fulCheck\");\r\n");
      out.write("\t\t\t\t}, function() {\r\n");
      out.write("\t\t\t\t\tif (!$(this).is(\":focus\")) {\r\n");
      out.write("\t\t\t\t\t\t$(this).parent(\"li\").removeClass(\"fulCheck\");\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t$(this).focus(function() {\r\n");
      out.write("\t\t\t\t\t$(this).parent(\"li\").addClass(\"fulCheck\");\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t$(this).blur(function() {\r\n");
      out.write("\t\t\t\t\t$(this).parent(\"li\").removeClass(\"fulCheck\");\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t$(\"#loginContainer,#versionInfo\").fadeIn(\r\n");
      out.write("\t\t\t\t1000,\r\n");
      out.write("\t\t\t\tfunction(){\r\n");
      out.write("\t\t\t\t\t $(\"\").fadeIn();\r\n");
      out.write("\t\t\t\t\t }\r\n");
      out.write("\t\t\t);\r\n");
      out.write("\t\t};\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"getUserId();showErrorMsg();\">\r\n");
      out.write("\t\t<div id=\"loginContainer\">\r\n");
      out.write("\t\t\t<div id=\"logo\"></div>\r\n");
      out.write("\t\t\t<div id=\"loginIco\"></div>\r\n");
      out.write("\t\t\t<div id=\"formContainer\">\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t<li><input type=\"text\" id=\"username\" /></li>\r\n");
      out.write("\t\t\t\t\t<li><input type=\"password\" id=\"password\" /></li>\r\n");
      out.write("\t\t\t\t\t<li><a id=\"submitBtn\" href=\"#\" onclick=\"setFromValue();\" />登 录</a>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id=\"versionInfo\">");
      out.print(versionInfo);
      out.write("</div>\r\n");
      out.write("\t\t<form id=\"formLogin\"\r\n");
      out.write("\t\t\taction=\"");
      out.print(request.getContextPath());
      out.write("/j_spring_security_check\"\r\n");
      out.write("\t\t\tmethod=\"post\">\r\n");
      out.write("\t\t\t<input type=\"hidden\" value=\"\" id=\"j_username\" name=\"j_username\" />\r\n");
      out.write("\t\t\t<input type=\"hidden\" value=\"\" id=\"j_password\" name=\"j_password\" />\r\n");
      out.write("\t\t\t<input type=\"submit\" class=\"button\" id=\"submit\" value=\"登录\"\r\n");
      out.write("\t\t\t\tstyle=\"visibility: hidden;\" onclick=\"if(!check()) return false; \" />\r\n");
      out.write("\t\t\t");

				if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
			
      out.write("\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"errorMsg\" name=\"errorMsg\"\r\n");
      out.write("\t\t\t\tvalue='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("' />\r\n");
      out.write("\t\t\t");

				} else {
			
      out.write("\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"errorMsg\" name=\"errorMsg\" value='' />\r\n");
      out.write("\t\t\t");

				}
			
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t\t</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
