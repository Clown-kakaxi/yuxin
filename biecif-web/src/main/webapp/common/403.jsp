<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>

<script type="text/javascript" src="${ctx}/js/bione/Common.min.js"></script>
<title>无权限-403</title>

</head>

<body style="background-color: #F1F1F1">

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="middle">
				<table width="100%" border="0" align="center" cellpadding="4"
					align="center" cellspacing="0">
					<tr>
						<td width="80" height="80" align="right"><img
							src="${ctx}/images/information.png" /></td>
						<td align="left"
							style="font-weight: bold; font-size: 16px; color: #334B67;">&nbsp;&nbsp;您没有操作该功能的权限,请联系管理员授权或者<a
							href="${ctx}/admin/login!logout.xhtml" ><font color="red">重新登录</font></a>！
						</td>
					</tr>


				</table>
			</td>
		</tr>
	</table>



</body>
</html>
