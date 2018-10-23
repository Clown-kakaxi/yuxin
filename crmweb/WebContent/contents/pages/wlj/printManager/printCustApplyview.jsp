<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@page contentType="text/html; charset=utf-8" language="java"%>
<%@page import="org.apache.struts2.ServletActionContext" language="java"%>
<%@page import="javax.servlet.http.HttpSession" language="java"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户关系管理系统</title>
<meta name="keywords" content="客户关系管理系统,CRM" />
<meta name="description" content="客户关系管理系统,CRM" />
<meta name="Author" content="YuchengTech" />
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="favicon.ico" />
<%@ include
	file="/contents/pages/wlj/printManager/common/printInclude.jsp"%>
<version:frameLink type="text/css" rel="stylesheet"
	href="/contents/pages/wlj/printManager/common/printCustApplyCss.css" />
<version:frameScript type="text/javascript"
	src="/contents/pages/wlj/printManager/common/printParam.js" />
<script type="text/javascript">
	
	<%
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest requestPrint = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession sessionPrint = requestPrint.getSession();
		JSONArray keys = (JSONArray) sessionPrint.getAttribute("changedKeys");
		out.print("var arr1 = " + keys); 
	%>
	function printPage() {
		window.print();
	}
</script>
<!-- <version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/printCustApplyview.js"/>  -->

</head>

<body style="background-color: white">

	<div id="pageOne">
		<!-- 左侧面板 -->
		<button onclick="printPage()" class="printButton">打印</button>
		<div id="div1">
			<table class="table1">
				<tr>富邦华一银行个人账户新增/变更/销户申请书
				</tr>
				<tr>
					<td colspan=3 class="td3">户名：${param.CUST_NAME}</td>
					<td colspan=3 class="td3">证件种类：${param.IDENT_TYPE}</td>
					<td colspan=3 class="td3">证件号：${param.IDENT_NO}</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">A 快速关户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />关闭本人所有账户并销户，同时关闭所有已开通服务</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">B 客户信息变更</td>
				</tr>
				<tr>
					<td id="PINYIN_NAME" colspan=3 class="td3">姓名拼音：${param.PINYIN_NAME}</td>
					<td id="GENDER" colspan=3 class="td3">性别：${param.GENDER}</td>
					<td id="IDENT_EXPIRED_DATE" colspan=3 class="td3">证件有效期：${param.IDENT_EXPIRED_DATE}</td>
				</tr>
				<tr>
					<td id="BIRTHDAY" colspan=3 class="td3">出生日期：${param.BIRTHDAY}</td>
					<td id="CITIZENSHIP" colspan=3 class="td3">国籍：${param.CITIZENSHIP}</td>
					<td id="BIRTHLOCALE" colspan=3 class="td3">出生地：${param.BIRTHLOCALE}</td>
				</tr>
				<tr>
					<td id="CONTMETH_INFO_SJ" colspan=3 class="td3">移动电话：${param.CONTMETH_INFO_SJ}</td>
					<td colspan=6 class="td6">(此电话将用于接收短信交易认证码和账务变动通知,仅限大陆和台湾手机)</td>
				</tr>
				<tr>
					<td colspan=1 class="td1">电话1</td>
					<td colspan=3 class="td3">电话类型：${param.CONTMETH_TYPE_1}</td>
					<td id="CONTMETH_INFO_YD" colspan=5 class="td5">电话号码：${param.CONTMETH_INFO_YD}</td>
				</tr>
				<tr>
					<td colspan=1 class="td1">电话2</td>
					<td colspan=3 class="td3">电话类型：${param.CONTMETH_TYPE_2}</td>
					<td id="CONTMETH_INFO_JT" colspan=5 class="td5">电话号码：${param.CONTMETH_INFO_JT}</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">(电话类型包括：住宅电话、办公电话、传真交易核对电话、其他电话)</td>
				</tr>
				<tr>
					<td colspan=6 class="td6">
						<span id="HOME_ADDR">居住地址：<input type="text" style="border:0px;width:200px;height:8px;font-size:8px;" readOnly="true" value=${param.HOME_ADDR}></span>
						<span id="HOME_STYLE"><input type="checkbox" class="checkbox" id="HOME_STYLE_1" onclick="return false"/>租赁
						<input type="checkbox" id="HOME_STYLE_2" class="checkbox" onclick="return false"/>自有</span>
					</td>
					<td id="HOME_ZIPCODE" colspan=3 class="td3">居住地邮编：${param.HOME_ZIPCODE}</td>
				</tr>
				<tr>
					<td id="POST_ADDR" colspan=6 class="td6">邮寄地址：${param.POST_ADDR}</td>
					<td id="POST_ZIPCODE" colspan=3 class="td3">邮件邮编：${param.POST_ZIPCODE}</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">
						<span id="EMAIL">电子邮件E-mail：<input type="text" style="border:0px;width:260px;height:8px;font-size:8px;" readOnly="true" value=${param.EMAIL}></span>
						<span id="IF_UPDATE_MAIL"><input type="checkbox" id="IF_UPDATE_MAIL_ON" class="checkbox" onclick="return false"/>同步更新为电子对账单接收E-mail</span>
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">职业资料</br> <span id="PROFESSION_STYLE">
						<input type="checkbox" id="PROFESSION_STYLE_1" class="checkbox"  onclick="return false" />全员制雇员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PROFESSION_STYLE_2" class="checkbox"  onclick="return false" />自雇&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PROFESSION_STYLE_3" class="checkbox"  onclick="return false" />退休&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PROFESSION_STYLE_4" class="checkbox"  onclick="return false" />其他</span><span id="PROFESSION_STYLE_REMARK">（请具体注明）${param.PROFESSION_STYLE_REMARK}</span></br>
						<span id="UNIT_NAME">单位名称：<input type="text" readOnly=true style="border: 0px; width: 120px; height: 8px; font-size: 8px;" value=${param.UNIT_NAME}></span>
						<span id="DUTY">职位：<input type="text" readOnly=true style="border: 0px; width: 120px; height: 8px; font-size: 8px;" value=${param.DUTY}></span>
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td9"><span id="USA_TAX_FLAG">
						是否为美国纳税人：<input type="checkbox" id="USA_TAX_FLAG_N" class="checkbox" onclick="return false" />否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="USA_TAX_FLAG_Y"  class="checkbox" onclick="return false" />是</span>&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="USA_TAX_IDEN_NO">（US TIN/SSN:${param.USA_TAX_IDEN_NO}）</span>
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td9"> <span id="IS_RELATED_BANK">
					是否在我行有关联人：<input type="checkbox" id="IS_RELATED_BANK_N" class="checkbox" onclick="return false" />否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox" id="IS_RELATED_BANK_Y" class="checkbox" onclick="return false" />是</span>
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">C 账户变更（开通和关闭个别账户）</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">境内客户：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">一般综合账户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">外汇结算户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">外汇资本金户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">外汇境内资本变现账户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">境外客户：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">一般综合账户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">外汇结算户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">外汇资本金户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">港澳居民人民币储蓄账户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">人民币前期费用专用结算账户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=3 class="td3">人民币再投资专用结算账户</td>
					<td colspan=6 class="td6"><input type="checkbox"
						class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class="checkbox" onclick="return false" />关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">D 服务信息变更</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">1.电子银行服务-网络银行</td>
				</tr>
				<tr>
					<td id="UKEY" colspan=6 class="td6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;U-Key认证</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="UKEY_32" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="UKEY_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td id="MESSAGE_CODE" colspan=6 class="td6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;短信认证码（汇款限额RMB50，000元）</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="MESSAGE_CODE_34" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="MESSAGE_CODE_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td id="UKEY_LOST" colspan=6 class="td6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;U-key挂失</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="UKEY_LOST_30" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="UKEY_LOST_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">2.电子银行服务-手机银行</td>
				</tr>
				<tr>
					<td id="MOBILE_BANKING_QUERY" colspan=6 class="td6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="MOBILE_BANKING_QUERY_42" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="MOBILE_BANKING_QUERY_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td id="MOBILE_BANKING_TRADE" colspan=6 class="td6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="MOBILE_BANKING_TRADE_44" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="MOBILE_BANKING_TRADE_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td id="PHONE_BANKING" colspan=6 class="td6">3.电子银行服务-电话银行</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="PHONE_BANKING_38" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PHONE_BANKING_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td id="TRANSACTION_SERVICE" colspan=9 class="td9">4.传真交易服务（*同时默认开通电子对账单服务，请填写电子对账单服务）</td>
				</tr>
				<tr>
					<td id="FAX_NUMBER" colspan=6 class="td6">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传真机号：${FAX_NUMBER}
					</td>
					<td colspan=3 class="td3">
						<input type="checkbox" id="TRANSACTION_SERVICE_60" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="TRANSACTION_SERVICE_0" class="checkbox" onclick="return false" />关闭
					</td>
				</tr>
				<tr>
					<td id="STATEMENTS" colspan=9 class="td9">5.电子对账单</td>
				</tr>
				<tr>
					<td id="MAIL" colspan=6 class="td6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E-mail:(
					<span id="MAIL_ADDRESS"><input type="checkbox" id="MAIL_ADDRESS_ON" class="checkbox" onclick="return false" />同电邮地址</span>） ${MAIL}
					</td>
					<td colspan=3 class="td3">
					<input type="checkbox" id="STATEMENTS_50" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox" id="STATEMENTS_0" class="checkbox" onclick="return false" />关闭</td>
				</tr>
				<tr>
					<td id="CHANGE_NOTICE" colspan=6 class="td6">6.账务变动通知</td>
					<td colspan=3 class="td3">
					<input type="checkbox" id="CHANGE_NOTICE_65" class="checkbox" onclick="return false" />开通&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox" id="CHANGE_NOTICE_0" class="checkbox" onclick="return false" />关闭</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">E 密码重置</td>
				</tr>
				<tr>
					<td colspan=9 class="td9">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PASS_RESET_1" class="checkbox" onclick="return false" />账户密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PASS_RESET_2" class="checkbox" onclick="return false" />网络/手机银行密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="PASS_RESET_3" class="checkbox" onclick="return false" />电话银行密码
					</td>
				</tr>
			</table>
		</div>
		<!-- 右侧面板 -->
		<div id="div2">
			<table class="table1">
				<tr>
					<td colspan=18 class="td9">F 户名变更</td>
				</tr>
				<tr>
					<td colspan=18 class="td9">
						<input type="checkbox" id="CHANGE_NAME_ON" class="checkbox"  onclick="return false" />兹因变更姓名，本人拟将在贵行所开立之户名称予以更改如下
					</td>
				</tr>
				<tr>
					<td id="PERSONAL_OLD_NAME" colspan=6 class="td3">旧户名：${param.PERSONAL_OLD_NAME}</td>
					<td id="PERSONAL_NEW_NAME" colspan=6 class="td3">新户名：${param.PERSONAL_NEW_NAME}</td>
					<td id="PINYIN_NEW_NAME" colspan=6 class="td3">新户名拼音：${param.PINYIN_NEW_NAME}</td>
				</tr>
				<tr>
					<td colspan=18 class="td9">G 印鉴新增变更挂失</td>
				</tr>
				<tr>
					<td colspan=18 class="td9"><input type="checkbox"
						class="checkbox" name="" onclick="return false" />兹因新增加账户账号，本人预留新印鉴与之对应。新印鉴自&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日起启用。
						<br>
					<input type="checkbox" class="checkbox" name=""
						onclick="return false" />兹因变更印鉴，请照下列方格内所留之新印鉴更换。新印鉴自&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日起启用，原旧印
						<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鉴约定同时失效。又本存户以前在贵行使用旧印鉴所订立各种契据及担保等文件仍属有效。
						<br>
					<input type="checkbox" class="checkbox" name=""
						onclick="return false" />兹因本人印鉴不慎遗失，现特向贵行提出印鉴挂失申请，并请照下列方格内所留之新印鉴更换。新印鉴自
						<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日起启用，原旧印鉴约定同时失效。又本存户以前在贵行使用旧印鉴所订立各种契
						<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据及担保等文件仍属有效。</td>
				</tr>
				<tr>
					<td colspan=18 class="td9">
						新增印鉴样式&nbsp;&nbsp;&nbsp;&nbsp;（对应账号：<input type="text" name=""
						style="border: 0px; width: 120px; height: 8px" readOnly="true" />）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式凭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式有效&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td45"></td>
					<td colspan=9 class="td45"></td>
				</tr>
				<tr>
					<td colspan=18 class="td9">
						需变更旧印鉴样式&nbsp;&nbsp;&nbsp;&nbsp;（对应账号：<input type="text" name=""
						style="border: 0px; width: 120px; height: 8px" readOnly="true" />）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td45"></td>
					<td colspan=9 class="td45"></td>
				</tr>
				<tr>
					<td colspan=18 class="td9">
						挂失/变更后新印鉴样式&nbsp;&nbsp;&nbsp;&nbsp;（对应账号：<input type="text"
						name="" style="border: 0px; width: 120px; height: 8px"
						readOnly="true" />）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式凭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式有效&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=9 class="td45"></td>
					<td colspan=9 class="td45"></td>
				</tr>
			</table>
		</div>
	</div>

</body>
<script type="text/javascript">
	window.onload = function() {
		
		var HOME_STYLE = '<%=request.getParameter("HOME_STYLE")%>';
		var IF_UPDATE_MAIL = '<%=request.getParameter("IF_UPDATE_MAIL")%>';
		var PROFESSION_STYLE = '<%=request.getParameter("PROFESSION_STYLE")%>';
		var USA_TAX_FLAG = '<%=request.getParameter("USA_TAX_FLAG")%>';
		var IS_RELATED_BANK = '<%=request.getParameter("IS_RELATED_BANK")%>';
		var UKEY = '<%=request.getParameter("UKEY")%>';
		var MESSAGE_CODE = '<%=request.getParameter("MESSAGE_CODE")%>';
		var UKEY_LOST = '<%=request.getParameter("UKEY_LOST")%>';
		var MOBILE_BANKING_QUERY = '<%=request.getParameter("MOBILE_BANKING_QUERY")%>';
		var MOBILE_BANKING_TRADE = '<%=request.getParameter("MOBILE_BANKING_TRADE")%>';
		var PHONE_BANKING = '<%=request.getParameter("PHONE_BANKING")%>';
		var TRANSACTION_SERVICE = '<%=request.getParameter("TRANSACTION_SERVICE")%>';
		var STATEMENTS = '<%=request.getParameter("STATEMENTS")%>';
		var CHANGE_NOTICE = '<%=request.getParameter("CHANGE_NOTICE")%>';
		var MAIL_ADDRESS = '<%=request.getParameter("MAIL_ADDRESS")%>';
		var PASS_RESET = '<%=request.getParameter("PASS_RESET")%>';
		var CHANGE_NAME = '<%=request.getParameter("CHANGE_NAME")%>';
		if(HOME_STYLE == '1'){
			document.getElementById("HOME_STYLE_1").checked = true;
		}else if(HOME_STYLE == '2'){
			document.getElementById("HOME_STYLE_2").checked = true;
		}
		if(IF_UPDATE_MAIL == '0'){
			document.getElementById("IF_UPDATE_MAIL_ON").checked = true;
		}
		if(PROFESSION_STYLE == '1'){
			document.getElementById("PROFESSION_STYLE_1").checked = true;
		}else if(PROFESSION_STYLE == '2'){
			document.getElementById("PROFESSION_STYLE_2").checked = true;
		}else if(PROFESSION_STYLE == '3'){
			document.getElementById("PROFESSION_STYLE_3").checked = true;
		}else if(PROFESSION_STYLE == '4'){
			document.getElementById("PROFESSION_STYLE_4").checked = true;
		}
		if(USA_TAX_FLAG == 'N'){
			document.getElementById("USA_TAX_FLAG_N").checked = true;
		}else if(USA_TAX_FLAG == 'Y'){
			document.getElementById("USA_TAX_FLAG_Y").checked = true;
		}
		if(IS_RELATED_BANK == 'N'){
			document.getElementById("IS_RELATED_BANK_N").checked = true;
		}else if(IS_RELATED_BANK == 'Y'){
			document.getElementById("IS_RELATED_BANK_Y").checked = true;
		}
		if(UKEY == '32'){
			document.getElementById("UKEY_32").checked = true;
		}else if(UKEY == '-1'){
			document.getElementById("UKEY_0").checked = true;
		}
		if(MESSAGE_CODE == '34'){
			document.getElementById("MESSAGE_CODE_34").checked = true;
		}else if(MESSAGE_CODE == '-1'){
			document.getElementById("MESSAGE_CODE_0").checked = true;
		}
		if(UKEY_LOST == '30'){
			document.getElementById("UKEY_LOST_30").checked = true;
		}else if(UKEY_LOST == '-1'){
			document.getElementById("UKEY_LOST_0").checked = true;
		}
		if(MOBILE_BANKING_QUERY == '42'){
			document.getElementById("MOBILE_BANKING_QUERY_42").checked = true;
		}else if(MOBILE_BANKING_QUERY == '-1'){
			document.getElementById("MOBILE_BANKING_QUERY_0").checked = true;
		}
		if(MOBILE_BANKING_TRADE == '44'){
			document.getElementById("MOBILE_BANKING_TRADE_44").checked = true;
		}else if(MOBILE_BANKING_TRADE == '-1'){
			document.getElementById("MOBILE_BANKING_TRADE_0").checked = true;
		}
		if(PHONE_BANKING == '38'){
			document.getElementById("PHONE_BANKING_38").checked = true;
		}else if(PHONE_BANKING == '-1'){
			document.getElementById("PHONE_BANKING_0").checked = true;
		}
		if(TRANSACTION_SERVICE == '60'){
			document.getElementById("TRANSACTION_SERVICE_60").checked = true;
		}else if(TRANSACTION_SERVICE == '-1'){
			document.getElementById("TRANSACTION_SERVICE_0").checked = true;
		}
		if(MAIL_ADDRESS == 'on'){
			document.getElementById("MAIL_ADDRESS_ON").checked = true;
		}
		if(STATEMENTS == '50'){
			document.getElementById("STATEMENTS_50").checked = true;
		}else if(STATEMENTS == '-1'){
			document.getElementById("STATEMENTS_0").checked = true;
		}
		if(CHANGE_NOTICE == '65'){
			document.getElementById("CHANGE_NOTICE_65").checked = true;
		}else if(CHANGE_NOTICE == '-1'){
			document.getElementById("CHANGE_NOTICE_0").checked = true;
		}
		if(PASS_RESET == '1'){
			document.getElementById("PASS_RESET_1").checked = true;
		}
		if(PASS_RESET == '2'){
			document.getElementById("PASS_RESET_2").checked = true;
		}
		if(PASS_RESET == '3'){
			document.getElementById("PASS_RESET_3").checked = true;
		}
		if(CHANGE_NAME == 'on'){
			document.getElementById("CHANGE_NAME_ON").checked = true;
		}
		
		for ( var i = 0; i < arr1.length; i++) {
			document.getElementById(arr1[i]).style.background = 'gray';
		}
	}
</script>
</html>
