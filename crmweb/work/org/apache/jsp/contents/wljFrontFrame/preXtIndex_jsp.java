package org.apache.jsp.contents.wljFrontFrame;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class preXtIndex_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body></body>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\tvar a=\"");
      out.print(request.getContextPath());
      out.write("\";\r\n");
      out.write("\t\tvar basepath = \"/\" + a.substring(1, a.length);\t\r\n");
      out.write("\t\t//获取用户配置信息\r\n");
      out.write("\t\t/* Ext.Ajax.request({\r\n");
      out.write("\t\t\turl:basepath+'/switchThemeAction!getUserCfg.json',\r\n");
      out.write("\t\t\tmothed: 'GET',\r\n");
      out.write("\t\t\tsuccess : function(response) {\r\n");
      out.write("\t\t\t\tvar res = Ext.util.JSON.decode(response.responseText);\r\n");
      out.write("\t\t\t\tif (res.themeId == '1') {\r\n");
      out.write("\t\t\t\t\twindow.location.href = basepath + '/contents/wljFrontFrame/xtIndex.jsp';\r\n");
      out.write("\t\t\t\t} else if (res.themeId == '2'){\r\n");
      out.write("\t\t\t\t\twindow.location.href = basepath + '/contents/wljFrontFrame/JSsearch_index.jsp';\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\twindow.location.href = basepath + '/contents/wljFrontFrame/xtIndex.jsp';\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tfailure : function(response) {\r\n");
      out.write("\t\t\t\twindow.location.href = basepath + '/contents/wljFrontFrame/xtIndex.jsp';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}); */\r\n");
      out.write("\t\t//华一银行只保留win8版\r\n");
      out.write("\t\twindow.location.href = basepath + '/contents/wljFrontFrame/JSsearch_index.jsp';\r\n");
      out.write("    </script>\r\n");
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
}
