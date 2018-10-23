<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page
	import="org.slf4j.Logger,org.slf4j.LoggerFactory,java.io.StringWriter,java.io.PrintWriter,java.io.IOException"%>


<%
	Throwable ex = exception;

	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request
				.getAttribute("javax.servlet.error.exception");

	if (request.getAttribute("exception") != null)
		ex = (Throwable) request.getAttribute("exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error("", ex);

	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);

	String exceptionStack = "";//异常的详细信息

	if (ex != null)
		try {
			ex.printStackTrace(pw);
			exceptionStack = sw.toString();
		} finally {
			try {
				sw.close();
				pw.close();
			} catch (IOException e) {
				// ignore
			}
		}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_load.jsp"%>
<script type="text/javascript" src="${ctx}/js/bione/Common.min.js"></script>
<title>系统内部错误-500</title>
<script type="text/javascript">
	//显示/隐藏详细错误信息
	var msgShow = false;
	function toggleErrorMsg() {

		if (msgShow) {

			msgShow = false;
			$("#msgInfoContainer").hide("slow");
		} else {

			msgShow = true;
			$("#msgInfoContainer").show("slow");
		}

	}
</script>
<style type="text/css">

#msgInfoContainer{

	border-style:solid;  
	border-width:1px;  
	border-color:#1E3C56;  
 
	height:380px;  
	width:800px;  
	display: none;
	overflow-y:scroll;  
	 
	scrollbar-face-color:#689BC6;  
	scrollbar-hightLight-color:#4B0082;  
	scrollbar-3dLight-color:#4B0082;  
	scrollbar-darkshadow-color:#4B0082;  
	scrollbar-shadow-color:#4B0082;  
	scrollbar-arrow-color:#FFFFFF;  
	scrollbar-track-color:#F1F1F1;  
	scrollbar-base-color:#FFFFFF;  
	
	background-color: #FFFFFF;

}

</style>
</head>

<body style="background-color: #F1F1F1">

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="middle">
				<table width="100%" border="0" align="center" cellpadding="4"
					align="center" cellspacing="0">
					<tr>
						<td width="80" height="80" align="right" ><img
							src="${ctx}/images/classics/others/information.png" /></td>
						<td align="left"
							style="font-weight: bold; font-size: 16px; color: #334B67;">&nbsp;&nbsp;系统运行中出现错误,请重新操作或者联系管理员!<a
							href="#" onclick="toggleErrorMsg();"><font color="red">查看详细信息</font></a>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<div id="msgInfoContainer">
								详细错误信息:<br/>
								<%=exceptionStack%>
							</div>
						</td>

					</tr>

				</table>
			</td>
		</tr>
	</table>



</body>
</html>
