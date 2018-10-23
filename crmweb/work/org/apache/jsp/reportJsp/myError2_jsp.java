package org.apache.jsp.reportJsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.*;

public final class myError2_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=GBK");
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
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\r\n");
      out.write("<title>错误信息</title>\r\n");
      out.write("<style>\r\n");
      out.write("* { margin:0; padding:0;}\r\n");
      out.write("body { text-align:center; font:75% Verdana, Arial, Helvetica, sans-serif;}\r\n");
      out.write("h1 { font:125% Arial, Helvetica, sans-serif; text-align:left; font-weight:bolder; background:#333;  padding:3px; display:block; color:#99CC00}\r\n");
      out.write(".class1 { width:90%; background:#CCC; position:relative; margin:0 auto; padding:5px;}\r\n");
      out.write("span { position:absolute; right:10px; top:8px; cursor:pointer; color:yellow;}\r\n");
      out.write("p { text-align:left; line-height:20px; background:#333; padding:3px; margin-top:5px; color:#99CC00}\r\n");
      out.write("#class1content { height:400px;overflow:auto}\r\n");
      out.write("</style>\r\n");
      out.write("<script>\r\n");
      out.write("function $(element){\r\n");
      out.write("return element = document.getElementById(element);\r\n");
      out.write("}\r\n");
      out.write("function $D(){\r\n");
      out.write("var d=$('class1content');\r\n");
      out.write("var h=d.offsetHeight;\r\n");
      out.write("var maxh=400;\r\n");
      out.write("function dmove(){\r\n");
      out.write("h+=50; //设置层展开的速度\r\n");
      out.write("if(h>=maxh){\r\n");
      out.write("d.style.height='400px';\r\n");
      out.write("clearInterval(iIntervalId);\r\n");
      out.write("}else{\r\n");
      out.write("d.style.display='block';\r\n");
      out.write("d.style.height=h+'px';\r\n");
      out.write("}\r\n");
      out.write("}\r\n");
      out.write("iIntervalId=setInterval(dmove,2);\r\n");
      out.write("}\r\n");
      out.write("function $D2(){\r\n");
      out.write("var d=$('class1content');\r\n");
      out.write("var h=d.offsetHeight;\r\n");
      out.write("var maxh=400;\r\n");
      out.write("function dmove(){\r\n");
      out.write("h-=50;//设置层收缩的速度\r\n");
      out.write("if(h<=0){\r\n");
      out.write("d.style.display='none';\r\n");
      out.write("clearInterval(iIntervalId);\r\n");
      out.write("}else{\r\n");
      out.write("d.style.height=h+'px';\r\n");
      out.write("}\r\n");
      out.write("}\r\n");
      out.write("iIntervalId=setInterval(dmove,2);\r\n");
      out.write("}\r\n");
      out.write("function $use(){\r\n");
      out.write("var d=$('class1content');\r\n");
      out.write("var sb=$('stateBut');\r\n");
      out.write("if(d.style.display=='none'){\r\n");
      out.write("$D();\r\n");
      out.write("sb.innerHTML='收缩';\r\n");
      out.write("}else{\r\n");
      out.write("$D2();\r\n");
      out.write("sb.innerHTML='查看详细信息';\r\n");
      out.write("}\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body >\r\n");
      out.write("<div class=\"class1\">\r\n");

	Exception e = ( Exception ) request.getAttribute( "exception" );
	out.println( "<h1>信息：</h1><div style='color:red'>" + e.getMessage() + "</div>" );
	

      out.write("\r\n");
      out.write("<span id=\"stateBut\" onclick=\"$use()\">查看详细信息</span>\r\n");
      out.write("<p id=\"class1content\" style=\"display:none\">\r\n");
 //e.printStackTrace(new PrintWriter(out)); 
java.io.StringWriter jsOutput = new StringWriter(); 
PrintWriter jsContentWriter = new PrintWriter(jsOutput); 
e.printStackTrace(jsContentWriter);
java.util.StringTokenizer st = new java.util.StringTokenizer(jsOutput.toString(),"\t");
while (st.hasMoreTokens()) {
	out.println(st.nextToken()+"<br>&nbsp;&nbsp;&nbsp;&nbsp;");
}

      out.write("</p>\r\n");
      out.write("</div>\r\n");
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
}
