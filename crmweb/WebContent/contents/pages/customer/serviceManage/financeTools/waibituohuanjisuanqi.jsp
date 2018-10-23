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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcExRate.js" language="JavaScript"> </SCRIPT>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
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
<noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
<!-- END WRating v1.0 -->
		<form name="ExRateCalculator" id="ExRateCalculator">
			<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="200" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="150"><img src="../Images/bg5.gif" width="425" height="150"><img src="../Images/bg6.gif" width="25" height="150"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="425"><img src="../Images/bg9.gif"></div>-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【外币兑换计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:430">外币兑换计算器可计算各种币种之间的兑换金额。</div>
			<DIV id="FDiv3" class="FDiv3" style="WIDTH: 400px; " align="center">
				<TABLE id="Table1" style="WIDTH: 371px;" cellSpacing="1" cellPadding="3" border="0">
					<TR>
						<TD colspan="3" height="20"></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 128px; HEIGHT: 34px">现持有货币种类</TD>
						<TD style="HEIGHT: 34px"><select name="ddlFromCurrType" tabIndex=1 id="ddlFromCurrType" onchange="getchangerate()" style="width:107px;">
							</select></TD>
						<TD style="HEIGHT: 34px" align="right"><INPUT class="btn" id="btnComp" type="button" value=" 计算 " tabIndex="5"></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 128px; HEIGHT: 31px">现持有货币数量</TD>
						<TD style="HEIGHT: 31px"><input name="tbFromCurrSum" type="text" tabIndex=2 value="0" id="tbFromCurrSum" class="txtnum" style="width:106px;" />&nbsp;元</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 128px; HEIGHT: 33px">欲兑换货币种类</TD>
						<TD style="HEIGHT: 33px"><select name="ddlToCurrType" id="ddlToCurrType" tabIndex=3 onchange="getchangerate()" style="width:107px;">
							</select></TD>
						<TD style="HEIGHT: 33px"></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 128px; HEIGHT: 34px">汇率</TD>
						<TD style="HEIGHT: 34px"><input name="tbQuotePrice" type="text" value="1.00" tabIndex=4 id="tbQuotePrice" class="txtnum" style="width:106px;" />&nbsp;</TD>
						<TD style="HEIGHT: 34px"></TD>
					</TR>
					<TR>
						<TD align="left" colSpan="3" style="HEIGHT: 27px">
							<HR style="WIDTH: 96.38%; HEIGHT: 1px" width="96.38%" SIZE="1">
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 128px">可兑换的货币数量为</TD>
						<TD><input name="tbToCurrSum" type="text" value="计算得出" disabled="true" readonly="readonly" id="tbToCurrSum" class="txtd" style="width:106px;" />&nbsp;元</TD>
						<TD></TD>
					</TR>
				</TABLE>
			</DIV>
		</form>
		<script language="javascript" src="../Script/CheckDataFunction.js"></script>
		<script language="javascript" event="onclick" for="btnComp">
				
		if (!CheckFN3(tbFromCurrSum,"请在现持有货币数量输入正数",false))
		 return false;
		if (!CheckFN(tbQuotePrice,"请在汇率中输入正数",false,6)) 
		 return false;		 		 
		 
		calc(document);
		</script>
		<xml id="xmlExchangeRate" src="http://localhost:8080/CRM/scripts/financeTools/ExchangeRate.xml" ondatasetcomplete="getbz();"></xml>
	</body>
</HTML>
<script language="javascript">
function getbz()
{
   document.ExRateCalculator.ddlFromCurrType.options.length = 0;
   ComAllMoneyType(document.ExRateCalculator.ddlFromCurrType,window.xmlExchangeRate.XMLDocument);
      if (document.ExRateCalculator.E_ddlFromCurrType!=null)
       document.ExRateCalculator.E_ddlFromCurrType.text = document.ExRateCalculator.ddlFromCurrType.options(0).text;
   document.ExRateCalculator.ddlFromCurrType.selectedIndex = 0;
   
   document.ExRateCalculator.ddlToCurrType.options.length = 0;
   ComAllMoneyType(document.ExRateCalculator.ddlToCurrType,window.xmlExchangeRate.XMLDocument);
   if (document.ExRateCalculator.E_ddlToCurrType!=null)
       document.ExRateCalculator.E_ddlToCurrType.text = document.ExRateCalculator.ddlToCurrType.options(0).text;
   document.ExRateCalculator.ddlToCurrType.selectedIndex = 0;
   
}

function getchangerate()
{
  document.ExRateCalculator.tbQuotePrice.value=GetChangeRatio(document.ExRateCalculator.ddlToCurrType.options[document.ExRateCalculator.ddlToCurrType.selectedIndex].value,document.ExRateCalculator.ddlFromCurrType.options[document.ExRateCalculator.ddlFromCurrType.selectedIndex].value,window.xmlExchangeRate.XMLDocument);
  
}

</script>
<script language="javascript">
 //GetCalc_Close();

</script>
