package org.apache.jsp.reportJsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.*;
import com.runqian.report4.usermodel.Context;

public final class showReport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/runqianReport4.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005freport_005fhtml_0026_005fwidth_005freportFileName_005fparams_005fneedPageMark_005fname_005fgenerateParamForm_005ffuncBarLocation_005fexceptionPage_005fappletJarName_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005freport_005fhtml_0026_005fwidth_005freportFileName_005fparams_005fneedPageMark_005fname_005fgenerateParamForm_005ffuncBarLocation_005fexceptionPage_005fappletJarName_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005freport_005fhtml_0026_005fwidth_005freportFileName_005fparams_005fneedPageMark_005fname_005fgenerateParamForm_005ffuncBarLocation_005fexceptionPage_005fappletJarName_005fnobody.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<link type=\"text/css\" href=\"css/style.css\" rel=\"stylesheet\"/>\r\n");
      out.write("<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>\r\n");

	request.setCharacterEncoding( "GBK" );
	String report = request.getParameter( "raq" );
	String reportFileHome=Context.getInitCtx().getMainDir();
	StringBuffer param=new StringBuffer();
	
	//保证报表名称的完整性
	int iTmp = 0;
	if( (iTmp = report.lastIndexOf(".raq")) <= 0 ){
		report = report + ".raq";
		iTmp = 0;
	}
	
	Enumeration paramNames = request.getParameterNames();
	if(paramNames!=null){
		while(paramNames.hasMoreElements()){
			String paramName = (String) paramNames.nextElement();
			String paramValue=request.getParameter(paramName);
			if(paramValue!=null){
				//把参数拼成name=value;name2=value2;.....的形式
				param.append(paramName).append("=").append(paramValue).append(";");
			}
		}
	}

	//以下代码是检测这个报表是否有相应的参数模板
	String paramFile = report.substring(0,iTmp)+"_arg.raq";
	File f=new File(application.getRealPath(reportFileHome+ File.separator +paramFile));


      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "toolbar.jsp", out, false);
      out.write("\r\n");
      out.write("<table id=\"rpt\" align=\"center\"><tr><td>\r\n");
	//如果参数模板存在，则显示参数模板
	if( f.exists() ) {
	
      out.write("\r\n");
      out.write("\t<table id=\"param_tbl\" width=\"100%\" height=\"100%\"><tr><td>\r\n");
      out.write("\t\t");
      //  report:param
      com.runqian.report4.tag.ParamTag _jspx_th_report_005fparam_005f0 = (com.runqian.report4.tag.ParamTag) _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.get(com.runqian.report4.tag.ParamTag.class);
      _jspx_th_report_005fparam_005f0.setPageContext(_jspx_page_context);
      _jspx_th_report_005fparam_005f0.setParent(null);
      // /reportJsp/showReport.jsp(46,2) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fparam_005f0.setName("form1");
      // /reportJsp/showReport.jsp(46,2) name = paramFileName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fparam_005f0.setParamFileName(paramFile);
      // /reportJsp/showReport.jsp(46,2) name = needSubmit type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fparam_005f0.setNeedSubmit("no");
      // /reportJsp/showReport.jsp(46,2) name = params type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fparam_005f0.setParams(param.toString());
      int _jspx_eval_report_005fparam_005f0 = _jspx_th_report_005fparam_005f0.doStartTag();
      if (_jspx_th_report_005fparam_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.reuse(_jspx_th_report_005fparam_005f0);
        return;
      }
      _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.reuse(_jspx_th_report_005fparam_005f0);
      out.write("\r\n");
      out.write("\t</td>\r\n");
      out.write("\t<td><a href=\"javascript:_submit( form1 )\"><img src=\"../images/query.jpg\" border=no style=\"vertical-align:middle\"></a></td>\r\n");
      out.write("\t</tr>\t");
 }
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<table align=\"center\" width=\"100%\" height=\"100%\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>");
      //  report:html
      com.runqian.report4.tag.HtmlTag _jspx_th_report_005fhtml_005f0 = (com.runqian.report4.tag.HtmlTag) _005fjspx_005ftagPool_005freport_005fhtml_0026_005fwidth_005freportFileName_005fparams_005fneedPageMark_005fname_005fgenerateParamForm_005ffuncBarLocation_005fexceptionPage_005fappletJarName_005fnobody.get(com.runqian.report4.tag.HtmlTag.class);
      _jspx_th_report_005fhtml_005f0.setPageContext(_jspx_page_context);
      _jspx_th_report_005fhtml_005f0.setParent(null);
      // /reportJsp/showReport.jsp(58,8) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setName("report1");
      // /reportJsp/showReport.jsp(58,8) name = reportFileName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setReportFileName(report);
      // /reportJsp/showReport.jsp(58,8) name = funcBarLocation type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setFuncBarLocation("top");
      // /reportJsp/showReport.jsp(58,8) name = needPageMark type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setNeedPageMark("yes");
      // /reportJsp/showReport.jsp(58,8) name = generateParamForm type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setGenerateParamForm("no");
      // /reportJsp/showReport.jsp(58,8) name = params type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setParams(param.toString());
      // /reportJsp/showReport.jsp(58,8) name = width type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setWidth("-1");
      // /reportJsp/showReport.jsp(58,8) name = exceptionPage type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setExceptionPage("/reportJsp/myError2.jsp");
      // /reportJsp/showReport.jsp(58,8) name = appletJarName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_report_005fhtml_005f0.setAppletJarName("runqianReport4Applet.jar,dmGraphApplet.jar");
      int _jspx_eval_report_005fhtml_005f0 = _jspx_th_report_005fhtml_005f0.doStartTag();
      if (_jspx_th_report_005fhtml_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005freport_005fhtml_0026_005fwidth_005freportFileName_005fparams_005fneedPageMark_005fname_005fgenerateParamForm_005ffuncBarLocation_005fexceptionPage_005fappletJarName_005fnobody.reuse(_jspx_th_report_005fhtml_005f0);
        return;
      }
      _005fjspx_005ftagPool_005freport_005fhtml_0026_005fwidth_005freportFileName_005fparams_005fneedPageMark_005fname_005fgenerateParamForm_005ffuncBarLocation_005fexceptionPage_005fappletJarName_005fnobody.reuse(_jspx_th_report_005fhtml_005f0);
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\t//设置分页显示值\r\n");
      out.write("\t\r\n");
      out.write("\tdocument.getElementById( \"t_page_span\" ).innerHTML=report1_getTotalPage();\r\n");
      out.write("\tdocument.getElementById( \"c_page_span\" ).innerHTML=report1_getCurrPage();\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
