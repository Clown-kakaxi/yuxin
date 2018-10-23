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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcBondBuy.js" language="JavaScript"> </SCRIPT>

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
		<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
		<div id="bg2"><img src="../Images/bg4.gif" width="25" height="250"><img src="../Images/bg5.gif" width="475" height="250"><img src="../Images/bg6.gif" width="25" height="250"></div>
		<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>-->
		<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【商业住房贷款和住房公积金贷款比较】</div>
		<div id="FDiv2" class="FDiv2" Movable="1" style="width:450">看看商业住房贷款和住房公积金贷款这两种不同的贷款方式哪种能适合您。</div>
		<div id="FDiv3" class="FDiv3" style="WIDTH: 450" align="center">
			<form id="LoanComp" name="LoanComp" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							购买房子的价格：
						</td>
						<td width="50%" align="left">
							<input id="edSum" name="edSum" class="txtnum" size="15"> 元
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							贷款购房时，首付
						</td>
						<td width="50%" align="left">
							<input id="edFirstSum" name="edFirstSum" class="txtnum" size="15"> ％
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							贷款期限：
						</td>
						<td width="50%" align="left">
							<input id="edTerm" name="edTerm" class="txtnum" size="15"> 年
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							家庭月总收入：
						</td>
						<td width="50%" align="left">
							<input name="edHomeSum" class="txtnum" id="edHomeSum" size="15"> 元
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							月交公积金总额：
						</td>
						<td width="50%" align="left">
							<input id="edPerFSum" name="edPerFSum" class="txtnum" size="15"> 元
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							目前公积金总额：
						</td>
						<td width="50%" align="left">
							<input id="edCurrFSum" name="edCurrFSum" class="txtnum" size="15"> 元
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							申请住房公积金贷款总额：
						</td>
						<td width="50%" align="left">
							<input id="edLoanFSum" name="edLoanFSum" class="txtnum" size="15"> 元
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							住房公积金贷款年利率：
						</td>
						<td width="50%" align="left">
							<input id="edLoanFRate" name="edLoanFRate" class="txtnum" size="15"> ％
						</td>
					</tr>
					<tr align="middle">
						<td width="50%" valign="center" align="right">
							住房商业贷款年利率：
						</td>
						<td width="50%" align="left">
							<input id="edLoanBRate" name="edLoanBRate" class="txtnum" size="15"> ％
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr align="middle">
						<td width="50%" align="right" colspan="2">
							<input type="button" class="btn" onClick="Calc()" value=" 计算 "><!-- &nbsp;&nbsp;
						</td>
						<td width="50%" align="left">
							&nbsp;&nbsp; <input type="reset" name="Reset" value="重 输" class="btn">-->
						</td>
					</tr>
				</table>
				<table width="100%" border="0">
					<tr>
						<td><hr style=" HEIGHT: 1px" width="95.29%" SIZE="1">
						</td>
					</tr>
					<tr>
						<td>&nbsp;<span id="ret4"></span></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
	<script language="javascript">
function Calc()
{
var
  Sum,FirstSum,Term,HomeSum,PerFSum,
  CurrFSum,LoanFSum,LoanFRate,LoanBRate;
var
  temp1,temp2,ret1,ret2;

	if (!CheckPN(document.all.edSum,"请在购买房子的价格输入正整数",false))
		return false;
	if (!CheckFN(document.all.edFirstSum,"请输入正确的首付"))
		return false;		
	if (!CheckPN(document.all.edTerm,"请在贷款期限输入正整数",false))
		return false;
	if (!CheckPN(document.all.edHomeSum,"请在家庭月总收入输入正整数",false))
		return false;				
	if (!CheckPN(document.all.edPerFSum,"请在月交公积金总额输入正整数",false))
		return false;
	if (!CheckPN(document.all.edCurrFSum,"请在目前公积金总额输入正整数",false))
		return false;
		
	if (!CheckPN(document.all.edLoanFSum,"请在申请住房公积金贷款总额输入正整数",false))
		return false;
	if (!CheckFN(document.all.edLoanFRate,"请输入正确的住房公积金贷款年利率"))
		return false;

	if (!CheckFN(document.all.edLoanBRate,"请输入正确的住房商业贷款年利率"))
		return false;
	
Sum=document.all.edSum.value;
FirstSum=document.all.edFirstSum.value;
Term=document.all.edTerm.value;
HomeSum=document.all.edHomeSum.value;
PerFSum=document.all.edPerFSum.value;
CurrFSum=document.all.edCurrFSum.value;
LoanFSum=document.all.edLoanFSum.value;
LoanFRate=document.all.edLoanFRate.value;
LoanBRate=document.all.edLoanBRate.value;

temp1=Math.pow((1+LoanBRate/100/12),(12*Term));
temp2=Math.pow((1+LoanFRate/100/12),(12*Term));
ret1=Sum*(1-FirstSum/100)*(LoanBRate/1200)*temp1/(temp1-1);
ret2=Sum*(1-FirstSum/100)*(LoanFRate/1200)*temp2/(temp2-1);
  
  
ret4.innerText="　　　若您申请商业性住房贷款，每月需偿还贷款额："+new Number(ret1).toFixed(2)+"元，若您申请住房公积金贷款，您每月需支付"+
	new Number(ret2-PerFSum).toFixed(2)+"元(需减去月交公积金总额)，与申请商业贷款相比您每月少支付"+
    new Number(ret1-ret2+PerFSum).toFixed(2)+"元。";
return true;
}
	</script>
	<script language="javascript">
    //GetCalc_Close();
	</script>
</HTML>
