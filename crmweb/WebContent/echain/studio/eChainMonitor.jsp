<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="java.awt.*" %>
<html>
<%
//String servleturl=request.getScheme()+"://"+request.getRemoteAddr()+":"+request.getServerPort()+request.getContextPath()+"/monitorservlet";
String servleturl=request.getScheme()+"://"+request.getLocalAddr()+":"+request.getServerPort()+request.getContextPath()+"/monitorservlet";
//Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
%>
  <head>
    <title>eChainMonitor图形化流程跟踪</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">      
  </head>
  
  <body text=#ffffff bgColor=#ffffff leftMargin=0 topMargin=0 onload="" marginheight="0" marginwidth="0" scroll=yes status=no>
    <applet archive = "eChainMonitor.jar"
			codebase="." 
            code="com.ecc.echain.workflow.monitor.WfMonitor.class" 
            name="WfMonitor" 
            width="100%" 
            height="100%">
      
<param name = "instanceid" value="<%=request.getParameter("instanceid")%>">
<param name = "ejbfactory" value = "servlet">
<param name = "ejburl" value = "<%=servleturl%>">
<param name = "colorset" value = "A0B7D9;E7EAD7;D2D6BC;D0DEF8;FDCD1C;F0F2E3;CED1BD">
    </applet>
  </body>
</html>
