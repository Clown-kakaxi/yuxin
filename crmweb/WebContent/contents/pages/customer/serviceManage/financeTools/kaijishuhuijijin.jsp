<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WinControl.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calendar.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calculator.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WBselect.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/GetRate.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Components.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcOpenFundRet.js" language="JavaScript"> </SCRIPT>
	
	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	  <script id="clientEventHandlersJS" language="javascript" >
<!--

function btnCalc_onclick() {

}

//-->
	  </script>
  </HEAD>
  <body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
	<form name="EduCalc" id="EduCalc">
	<!--   <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
	  <div id="bg2"><img src="../Images/bg4.gif" width="25" height="120"><img src="../Images/bg5.gif" width="475" height="120"><img src="../Images/bg6.gif" width="25" height="120"></div>
	  <div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>
	 -->
	  <div id="FDiv1" class="FDiv1" Movable="1" style="width:300;color:black">【开放式基金赎回金额计算器】</div>
	  <div id="FDiv2" class="FDiv2" Movable="1" style="width:450">输入赎回份数、基金单位净值、赎回费率，本计算器将计算出可能赎回的金额。
	  </div>
	  <div id="FDiv3" class="FDiv3" style="width:450" align="center">
		<table cellSpacing="0" borderColorDark="#e9f3f4" cellPadding="0" width="90%" borderColorLight="#336666" border="0" height="170">
		  <tr>
			<td>赎回上一日基金单位净值(元)</td>
			<td><input name="edprice" type="text" id="edprice" tabindex="1000" class="txtnum" style="width:125px;" />
			</td>
			<td><INPUT class="btn" id="btnCalc" style="WIDTH: 60px;" type="button" value=" 计算 " tabIndex="1003" onclick="return btnCalc_onclick()">
			</td>
		  </tr>
		  <tr>
			<td>赎回份额(份)</td>
			<td><input name="edunit" type="text" id="edunit" tabindex="1001" class="txtnum" style="width:125px" />
			</td>
		  </tr>
		  <tr>
			<td>赎回费率(%)</td>
			<td><input name="edrate" type="text" id="edrate" tabindex="1002" class="txtnum" style="width:125px" />
			</td>
		  </tr>
		  <tr>
			<td colspan="3" align="left"><HR width="90%" SIZE="1">
			</td>
		  </tr>
		  <tr>
			<td>上一日可赎回金额(元)</td>
			<td><input name="edsum" type="text" value="计算得出" disabled readonly id="edsum" tabindex="3" class="txtd" style="width:125px" />
			</td>
		  </tr>
		</table>
	  </div>
	</form>
	<script language="javascript" src="../Script/CheckDataFunction.js"></script>
	<script language="javascript" event="onclick" for="btnCalc">
			if (!CheckFN3(edprice,"请在赎回上一日基金单位净值中输入正数",false,null,4))
	   		return false;
			if (!CheckFN3(edunit,"请在赎回份额中输入正数",false))
	   		return false;
	   		if (!CheckFN(edrate,"请在赎回费率中输入非负数",null))
	   		return false;
	   		
	   //		var cal = new ActiveXObject("FinanceCalculator.ICalcFundRet");
	  // 		cal.Execute(edprice.value,edunit.value,edrate.value);
	   		
		//	document.all.edsum.value=cal.RetSum;
			document.all.edsum.value=CalcRetSum(document);
	</script>
	<script>
     //GetCalc_Close();
	</script>
  </body>
</HTML>
