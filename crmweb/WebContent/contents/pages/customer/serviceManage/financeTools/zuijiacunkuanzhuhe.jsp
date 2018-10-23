<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WinControl.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calendar.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calculator.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WBselect.js" language="JavaScript"> </SCRIPT>
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/GetRate.js" language="JavaScript"> </SCRIPT>
	
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Components.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>
	<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcOptiCombine.js" language="JavaScript"> </SCRIPT>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bgColor="#ffffff" leftMargin="0" topMargin="0">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
</NOSCRIPT>

<!-- START WRating v1.0 -->
<script type="text/javascript" src="http://msn.wrating.com/a1.js">
</script>
<script type="text/javascript">
//var vjAcc="860010-0218010200";
//var wrUrl="http://msn.wrating.com/";
//vjTrack("");
</script>
<noscript>
<!--  <img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/>
-->
</noscript>
<!-- END WRating v1.0 -->
		<form name="SavingCombCalc" id="SavingCombCalc" onSubmit="return false;">
		<!--  	<div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="100"><img src="../Images/bg5.gif" width="475" height="100"><img src="../Images/bg6.gif" width="25" height="100"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div> 
		-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="width:300;color:black">【最佳存款组合计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:480">当希望的储蓄存期不符合银行的固有存期时，本计算器可以协助您对存款制定一个最佳组合计划，即收益最大的组合计划。</div>
			<div id="FDiv3" class="FDiv3" style="width:450" align="center">
				<table cellSpacing="0" borderColorDark="#e9f3f4" cellPadding="0" height="180" width="80%" borderColorLight="#336666" border="0">
					<tr>
						<td width="120">初始存入金额</td>
						<td width="150"><input name="edSum" type="text" id="edSum" class="txtnum" style="width:121px;" tabindex=1 />元
						</td>
						<td><input type="button" name="btnCalc" value=" 计算 " id="btnCalc" tabindex="3" class="btn" onclick="javascript:if (CheckData()) calc(document);">
						</td>
					</tr>
					<tr>
						<td>希望的储蓄存期</td>
						<td><select name="cbTerm" id="cbTerm" tabindex="2" style="width:121px;">
								<option value="2">2年</option>
								<option value="3">3年</option>
								<option value="4">4年</option>
								<option value="5">5年</option>
								<option value="6">6年</option>
								<option value="7">7年</option>
								<option value="8">8年</option>
								<option value="9">9年</option>
								<option value="10">10年</option>
							</select></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="3"><HR SIZE="1" width="100%">
						</td>
					</tr>
					<tr>
						<td colspan="3"><span id="lbResult" style="height:49px;TEXT-ALIGN: left;width:95%"> 
          计算得出</span></td>
					</tr>
				</table>
			</div>
		</form>
		<script>
     //GetCalc_Close();
		</script>
		<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml"></xml>
	</body>
</HTML>
<script language="javascript">
function CheckData()
{
 if (!CheckFN3(document.all.edSum,"请在初始存入金额输入正数",false))
	 return false;
else
	return true;
}
</script>
