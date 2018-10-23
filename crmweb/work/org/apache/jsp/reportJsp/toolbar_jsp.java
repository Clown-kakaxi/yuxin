package org.apache.jsp.reportJsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class toolbar_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
	String appmap = request.getContextPath();
	String printImage = "<img src='" + appmap + "/images/print.gif' border=no >";
	String excelImage = "<img src='" + appmap + "/images/excel.gif' border=no >";
	String pdfImage = "<img src='" + appmap + "/images/pdf.gif' border=no >";
    String wordImage = "<img src='" + appmap + "/images/doc.gif' border=no >";
	String firstPageImage = "<img src='" + appmap + "/images/firstpage.gif' border=no >";
	String lastPageImage = "<img src='" + appmap + "/images/lastpage.gif' border=no >";
	String nextPageImage = "<img src='" + appmap + "/images/nextpage.gif' border=no >";
	String prevPageImage = "<img src='" + appmap + "/images/prevpage.gif' border=no >";
	String submitImage = "<img src='" + appmap + "/images/savedata.gif' border=no >";

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"btnBar\">\r\n");
      out.write("  <ul class=\"left\">\r\n");
      out.write("    <!--<li class=\"borderRight submitLi\" onClick=\"_submitTable( report1 );return false;\" href=\"#\"> <a title=\"提交\" href=\"#\" class=\"submit\"></a></li>-->\r\n");
      out.write("    <li class=\"toggleBg borderRight\">\r\n");
      out.write("      <ul class=\"fileOper\">\r\n");
      out.write("        <li><a class=\"ICOhover\" href=\"#\" onClick=\"report1_print();return false;\"><span title=\"打印\" class=\"print\"></span></a></li>\r\n");
      out.write("        <li><a class=\"ICOhover\" href=\"#\" onClick=\"report1_saveAsExcel();return false;\"><span title=\"导出excel\" class=\"excel\"></span></a></li>\r\n");
      out.write("        <li><a class=\"ICOhover\" href=\"#\" onClick=\"report1_saveAsPdf();return false;\"><span title=\"导出pdf\" class=\"pdf\"></span></a></li>\r\n");
      out.write("        <li><a class=\"ICOhover\" href=\"#\" onClick=\"report1_saveAsWord();return false;\"><span title=\"导出word\" class=\"word\"></span></a></li>\r\n");
      out.write("       </ul>\r\n");
      out.write("    </li>\r\n");
      out.write("    <li class=\"floatRight borderLeft\">\r\n");
      out.write("      <ul class=\"fileOper\">\r\n");
      out.write("         <Li><a class=\"ICOhover\" href=\"#\" onClick=\"try{report1_toPage( 1 );}catch(e){}return false;\"><span title=\"首页\" class=\"begin\"></span></a></li>\r\n");
      out.write("        <li><a class=\"ICOhover\" href=\"#\" onClick=\"try{report1_toPage(report1_getCurrPage()-1);}catch(e){}return false;\"><span title=\"上一页\" class=\"pre\"></span></a></li>\r\n");
      out.write("        <Li><a class=\"ICOhover\" href=\"#\" onClick=\"try{report1_toPage(report1_getCurrPage()+1);}catch(e){}return false;\"><span title=\"下一页\" class=\"next\"></span></a></li>\r\n");
      out.write("        <li><a class=\"ICOhover\" href=\"#\" onClick=\"try{report1_toPage(report1_getTotalPage());}catch(e){}return false;\"><span title=\"尾页\" class=\"end\"></span></a></li>    \r\n");
      out.write("      </ul>\r\n");
      out.write("    </li>\r\n");
      out.write("    <li class=\"floatRight\">  <div style=\"display:inline-block; margin:9px 4px 3px 4px; float:left; \">共<span id=\"t_page_span\"></span>页/第<span id=\"c_page_span\"></span>页&nbsp;&nbsp;</div></li>\r\n");
      out.write("  </ul>\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
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
