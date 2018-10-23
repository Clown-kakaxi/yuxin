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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcFundInvestLost.js" language="JavaScript"> </SCRIPT>
	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
 //var cImage; 
// cImage = new Image; 
 //cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<!--  <img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
-->
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
		<form name="EduCalc" id="EduCalc" onsubmit="return false;">
			<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="230" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="140"><img src="../Images/bg5.gif" width="455" height="140"><img src="../Images/bg6.gif" width="25" height="140"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="455"><img src="../Images/bg9.gif"></div> -->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【封闭式基金投资损益计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:450">投资损益是客户进行投资关注的焦点。本计算器将根据基金的买入、卖出价格和交易费率等已知条件，计算出封闭式基金投资损益。</div>
			<DIV id="FDiv3" class="FDiv3" style="WIDTH: 250px; " align="center">
				<table cellSpacing="0" borderColorDark="#e9f3f4" cellPadding="0" width="90%" borderColorLight="#336666" border="0" id="Table1">
					<tr>
						<td align="middle">
							<HR style="Z-INDEX: 104; LEFT: 22px; WIDTH: 400; POSITION: absolute; TOP: 170px; HEIGHT: 1px" width="91.31%" SIZE="1">
							<input name="edinprice" type="text" id="edinprice" tabindex="1010" class="txtnum" style="width:108px;Z-INDEX: 101; LEFT: 201px; POSITION: absolute; TOP: 18px" />
							<input name="edoutprice" type="text" id="edoutprice" tabindex="1020" class="txtnum" style="width:108px;Z-INDEX: 102; LEFT: 200px; POSITION: absolute; TOP: 72px" />
							<DIV style="Z-INDEX: 105; LEFT: 35px; WIDTH: 120px; POSITION: absolute; TOP: 190px; HEIGHT: 19px" align="left" ms_positioning="FlowLayout">基金的投资损益(元)</DIV>
							<INPUT class="btn" id="btnCalc" style="Z-INDEX: 106; LEFT: 354px; WIDTH: 53px; POSITION: absolute; TOP: 17px; HEIGHT: 20px" tabIndex="1050" type="button" value=" 计算 " name="btnCalc">
							<DIV style="Z-INDEX: 107; LEFT: 35px; POSITION: absolute; TOP: 73px" ms_positioning="text2D">基金的卖出价格（元）</DIV>
							<input name="edoutnum" type="text" id="edoutnum" tabindex="1030" class="txtnum" style="width:108px;Z-INDEX: 108; LEFT: 200px; POSITION: absolute; TOP: 99px" />
							<DIV style="Z-INDEX: 109; LEFT: 35px; POSITION: absolute; TOP: 128px" ms_positioning="text2D">基金的交易费率（%）</DIV>
							<input name="txttotal" type="text" value="计算得出" id="txttotal" class="txtd" style="width:109px;Z-INDEX: 110; LEFT: 202px; POSITION: absolute; TOP: 189px" />
							<DIV style="Z-INDEX: 111; LEFT: 35px; POSITION: absolute; TOP: 102px" ms_positioning="text2D">基金的卖出单位（份）</DIV>
							<input name="edtraderate" type="text" value="0.25" id="edtraderate" tabindex="1040" class="txtnum" style="width:108px;Z-INDEX: 112; LEFT: 200px; POSITION: absolute; TOP: 126px" /><STRONG>
							</STRONG><STRONG></STRONG>
							<DIV style="Z-INDEX: 113; LEFT: 35px; POSITION: absolute; TOP: 20px" ms_positioning="text2D">基金的买入价格（元）</DIV>
							<DIV style="Z-INDEX: 114; LEFT: 35px; POSITION: absolute; TOP: 48px" ms_positioning="text2D">基金持有期间的分红金额（元）</DIV>
							<input name="edcake" type="text" id="edcake" tabindex="1010" class="txtnum" style="width:108px;Z-INDEX: 115; LEFT: 200px; POSITION: absolute; TOP: 45px" />
						</td>
					</tr>
				</table>
			</DIV>
	</form>
		<script language="javascript" src="../Script/CheckDataFunction.js"></script>
		<script language="javascript" event="onclick" for="btnCalc">
			if (!CheckFN3(edinprice,"请在基金的买入价格中输入正数",false))
	   		return false;	   					
	   		if (!CheckFN3(edcake,"请在基金持有期间的分红金额中输入非负数",true))
	   		return false;	   	   		
	   		if (!CheckFN3(edoutprice,"请在基金的卖出价格中输入正数",false))
	   		return false;	   
	   		if (!CheckPN(edoutnum,"请在基金的卖出单位中输入正整数",false))
	   		return false;	   	   		
	   		if (!CheckFN3(edtraderate,"请在基金的交易费率中输入正数",false))
	   		return false;
			calc(document);
		</script>
		<script language="javascript">
		function loadinit() { this.document.all.txttotal.disabled=true;
		 }
		window.attachEvent("onload",loadinit); 
		
     //GetCalc_Close();
		
		</script>
	</body>
</HTML>
