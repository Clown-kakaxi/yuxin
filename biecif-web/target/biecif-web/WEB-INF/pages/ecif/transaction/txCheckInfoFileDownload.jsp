<%@ page contentType="application/zip; charset=GBK"%>

<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.yuchengtech.emp.ecif.transaction.service.TxDefBS"%>

<html>


<head>

</head>
<body>
<%

InputStream in = null;
try{
	
    String txId = (String)request.getParameter("txId");
    String txName = (String)request.getAttribute("txName");
	WebApplicationContext wac = (WebApplicationContext)config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	
	TxDefBS txDefBS = (TxDefBS) wac.getBean("txDefBS");
	
	
			StringBuffer contentDisposition = new StringBuffer();
			contentDisposition.append("attachment;");
			contentDisposition.append("filename=\"");
			contentDisposition.append("tx"+ System.currentTimeMillis() + ".zip");
			contentDisposition.append("\"");
			response.setHeader(
					"Content-Disposition",
					new String(contentDisposition.toString().getBytes(
							System.getProperty("file.encoding")), "iso8859_1"));
	
	Boolean result = txDefBS.downloadTxMsgCheckInfo(txId,txName,response);

	ServletOutputStream servletOut = response.getOutputStream();
	servletOut.flush();
	servletOut.close();
	servletOut=null;
	response.flushBuffer();
	out.clear();
	out = pageContext.pushBody();

	


}catch(Exception e){
	e.printStackTrace();
}finally{
	
	if(in != null)
		in.close();
}

%>
</body>
</html>
