<%@ page contentType="text/html;charset=GBK" %>
<%	String appmap = request.getContextPath();
	String printImage = "<img src='" + appmap + "/images/print.gif' border=no >";
%>

<table id=titleTable width=100% cellspacing=0 cellpadding=0 border=0 ><tr>
	<td height="22" width=100% valign="middle"  style="font-size:13px" background="../images/ta51top.jpg">
		<table width="100%">
			<tr >
				<td width=53% align="left"  style="font-size:13px" >&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td width="47%" align="center" valign="middle"   style="font-size:12px" >
				
		<!--  	¹²<span id="t_page_span"></span>Ò³/µÚ<span id="c_page_span"></span>Ò³   -->	
				
				&nbsp;&nbsp;
				<a href="#" onClick="report1_print();return false;"><%=printImage%></a>
			  </td>
			</tr>
	  </table>
	</td>
</table>