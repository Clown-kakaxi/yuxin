<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="com.yuchengtech.emp.ecif.base.core.EcifLisenceManager"%>
<%
	String currentTime = ""+System.currentTimeMillis();
	
	String versionInfo = "版权所有: "
						+ EcifLisenceManager.getInstance().getCustomerName()
						+ " | "
						+ " 版本号:"
						+ EcifLisenceManager.getInstance().getVersion();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_load.jsp"%>
<title>欢迎使用宇信易诚ECIF系统</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/login.css" />
<title>请登录</title>
<script type="text/javascript">
    $(document).ready(function() {
	//判断是否首次进入登录页
	$("#loginForm").validate({
	    rules : {
		username : {
		    required : true,
		    maxlength : 100
		},
		password : {
		    required : true,
		    maxlength : 15
		}
	    },
	    messages : {
		username : {
		    required : "用户名不允许为空!",
		    maxlength : jQuery.format("用户名最多允许输入 {0} 字符!")
		},
		password : {
		    required : "密码不允许为空!",
		    maxlength : jQuery.format("密码最多允许输入 {0} 字符!")
		}
	    }
	});

	//监控回车事件,提交表单
	document.onkeydown = function(e) {
	    var ev = document.all ? window.event : e;
	    if (ev.keyCode == 13) {

		doSubmit();
	    }
	};

	//使登录窗口居中(解决不同版本IE浏览器表格高度计算问题)
	$("#loginFormTd").css("height", $(document).height() - 50);

    });

    //提交单进行登录
    function doSubmit() {
	//验证用户名、密码是否填写完整
	var valid = $("#loginForm").valid();
	if (valid) {
	    document.loginForm.submit();
	}

    }
</script>


<%
	String error = (String) request
			.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	String loginFailInfo = (String) request
			.getAttribute("LOGIN_FAIL_INFO");
	String tip = "";
	String className = "font_tip_error";

	if (error != null) {
		tip = "您尚未登录或者会话超时，请重新登录.";
	} else if (loginFailInfo != null) {
		tip = loginFailInfo;
	} else {
		tip = "请输入用户名和密码";
		className = "font_tip";
	}

	tip = "<span class='" + className + "'>" + tip + "</span>";
%>

</head>
<body>
	<form name="loginForm" id="loginForm" action="${ctx}/login"
		method="post">
		<table border="0" cellspacing="0" cellpadding="0" width="100%"
			height="100%">
			<tr>
				<td id="loginFormTd" width="100%"
					style="position: relative; buttom: 50" align="center"
					valign="middle">
					<table id="loginFormTable" border="0" cellspacing="0"
						class="login_form" cellpadding="0" width="100%" height="100%">
						<tr valign="middle">
							<td width="50%" align="right"><img id="login_logo"
								src="${ctx}/images/classics/login/login_logo1.png" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td width="50%" style="text-align: left">
								<table border="0" width="100%">
									<tr>
										<td height="50" align="right" class="font_lable"><img
											src="${ctx}/images/classics/login/login_form_sign.png" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td id='tipInfo'><%=tip%></td>
									</tr>
									<tr>
										<td height="35" align="right" class="font_lable"><label
											for="username">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</label></td>
										<td><input name="username" type="text" value="ecif" class="input"/></td>
									</tr>
									<tr>
										<td height="35" align="right" class="font_lable"><label
											for="password">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</label></td>
										<td><input name="password" type="password" value="123456"
											class="input"/></td>
									</tr>
									<tr>
										<td height="35" align="right"  class="font_lable"><label
											for="logicsysno">逻辑系统：</label></td>
										<td><select name="logicsysno" id="logicsysno"
											style="width: 162px; height: 26px" ltype="select">
											
											<%--${logicsysno==item.logicSysNo} --%>
											<c:forEach items="${items}" var="item">
													<option value="${item.logicSysNo}"
														<c:if test="${'ecif'==item.logicSysNo}">selected</c:if>>
														<c:out value="${item.logicSysName}" />
													</option>
											</c:forEach>
											<%--
											<c:forEach items="${items}" var="item">
												<c:if test="${'ecif'==item.logicSysNo}">
												<option value="${item.logicSysNo}" >
													<c:out value="${item.logicSysName}" />
												</option>
												</c:if>
											</c:forEach>
											--%>
										</select></td>
									</tr>
									<%--
									<tr>
										<td height="35" align="right" class="font_lable"><label
											for="password">全屏窗口：</label></td>
										<td><input name="isFullScreen" type="checkbox"
											value="<c:out value="${isFullScreen}" />" <c:if test="${isFullScreen==true}">checked="checked"</c:if>/></td>
									<tr>
									--%>
									<tr>
										<td height="50" align="right" class="font_lable">&nbsp;</td>
										<td height="50" valign="bottom"><a
											href="javascript:doSubmit();"  onFocus="this.blur()"><img
												src="${ctx}/images/classics/login/login_submit1.jpg"
												border="0" /></a></td>
									</tr>

								</table>
							</td>

						</tr>

					</table>

				</td>
			</tr>
			<tr align="center">
				<td height="50" width="100%" valign="top" style="color: #296797;">&copy;
					<div id="versionInfo" class="login_verInfo" style="display:inline"><%=versionInfo%></div></td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
	if (parent.frames[0]) {
	    //如果当前页面是在iframe中，则在父页面中打开
	    parent.location = window.location;
	}
    </script>
</body>
</html>