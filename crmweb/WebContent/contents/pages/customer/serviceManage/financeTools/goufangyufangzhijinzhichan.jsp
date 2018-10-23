<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>金融计算器_理财频道_MSN中国</title>
		<script>
  //window.resizeTo(574,454);
		</script>
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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcLoanOrRental.js" language="JavaScript"> </SCRIPT>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<!-- <img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
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
<!-- <img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/>
-->
</noscript>
<!-- END WRating v1.0 -->
		<xml id="xmldso" async="false">
			<Root>
				<Items>
					<Times />
					<Year />
					<RateSum />
					<Corpus />
					<CorpusRate />
					<LeavCorpus />
				</Items>
			</Root>
		</xml>
		<form name="LoanOrRental" id="LoanOrRental" onSubmit="return false;">
			<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" height="126" width="289px"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="180px"><img src="../Images/bg5.gif" width="514px" height="180px"><img src="../Images/bg6.gif" width="25" height="180px"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="514px"><img src="../Images/bg9.gif"></div> -->
			<div id="FDiv1" class="FDiv1" style="color:black">【购房与租房净资产比较器】</div>
			<div id="FDiv2" class="FDiv2" style="width:514px">为了便于比较，本计算器的计算前提是支出相等、且租房费用不变。在此前提下，计算和比较采取购房和租房两种方式各自在一段时间后的净资产值。</div>
			<div id="FDiv3" class="FDiv3" style="width:514px;height:270px;" align="center">
				<table width="100%" border="0" ID="Table1">
					<tr>
						<td width="30%">希望进行比较的时间(月)</td>
						<td width="20%"><input name="edCompareTime" type="text" id="edMonth" class="txtnum" style="width:80px;" tabindex="1" /></td>
						<td width="30%"><input type="button" name="btnCalc" onclick="javascript:if(CheckData()) Excute(document);" value="计算" id="btnCalc" tabindex="21" class="btn" style="width:60px;" /></td>
						<td></td>
					</tr>
					<tr>
						<td>房屋折旧/增值年比率(%)</td>
						<td><input name="edHouseRate" type="text" id="edHouseRate" tabindex="2" class="txtnum" style="width:80px;" /></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>资金投资的年收益率(%)</td>
						<td><input name="edCashRate" type="text" id="edCashRate" tabindex="3" class="txtnum" style="width:80px" /></td>
						<td>每月租房费用（元）</td>
						<td><input name="edRentOfMonth" type="text" id="edRentOfMonth" tabindex="4" class="txtnum" style="width:80px" /></td>
					</tr>
					<tr>
						<td>购房首付金额（元）</td>
						<td><input name="edBuyFirstPay" type="text" id="edBuyFirstPay" tabindex="5" class="txtnum" style="width:80px" /></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>公积金贷款金额（元）</td>
						<td><input name="edFundAmount" type="text" id="edFundAmount" tabindex="6" class="txtnum" style="width:80px" /></td>
						<td>商业贷款金额（元）</td>
						<td><input name="edLoanAmount" id="edLoanAmount" type="text" tabindex="7" class="txtnum" style="width:80px" /></td>
					</tr>
					<tr>
						<td>公积金贷款期限(月)</td>
						<td><input name="edFundTime" type="text" value="120" id="edFundTime" tabindex="8" class="txtnum" style="width:80px" /></td>
						<td>商业贷款期限(月)</td>
						<td><input name="edLoanTime" type="text" value="120" id="edLoanTime" tabindex="9" class="txtnum" style="width:80px" /></td>
					</tr>
					<tr>
						<td>公积金贷款年利率(%)</td>
						<td><input name="edFundRate" type="text" value="4.05" id="edFuntRate" tabindex="10" class="txtnum" style="width:80px" /></td>
						<td>商业贷款年利率(%)</td>
						<td><input name="edLoanRate" type="text" value="5.04" id="edLoanRate" tabindex="11" class="txtnum" style="width:80px" /></td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">计算得出：</td>
					</tr>
					<tr>
						<td colspan="4" style="text-indent:20px"><span id="lbResult"></span></td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
				</table>
			</div>
		</form>
		<script language="javascript" src="../Script/CheckDataFunction.js"></script>
		<script language="javascript">
	function CheckData()
	{
	if (!CheckPN(this.document.all.edCompareTime,"请在希望进行比较的时间输入正整数",false))
		return false;
	if (!CheckFN(this.document.all.edHouseRate,"请在房屋折旧/增值年比率输入非负数"))
		return false;		
	if (!CheckFN(this.document.all.edCashRate,"请在资金投资的年收益率输入非负数"))
		return false;
	if (!CheckFN(this.document.all.edBuyFirstPay,"请在购房首付金额输入正数",false))
		return false;
	if (!CheckPN(this.document.all.edFundAmount,"请在公积金贷款金额输入非负整数",true))
		return false;
	if (!CheckPN(this.document.all.edFundTime,"请在公积金贷款期限输入正整数",false))
		return false;
	if (!CheckFN(this.document.all.edFundRate,"请在公积金贷款年利率输入非负数",null,4))
		return false;
	if (!CheckFN(this.document.all.edRentOfMonth,"请在每月租房费用输入正数",false))
		return false;		
	if (!CheckPN(this.document.all.edLoanAmount,"请在商业贷款金额输入非负整数",true))
		return false;
	if (!CheckPN(this.document.all.edLoanTime,"请在商业贷款期限输入正整数",false))
		return false;
	if (!CheckFN(this.document.all.edLoanRate,"请在商业贷款年利率输入非负数",null,4))
		return false;
	if (this.document.all.edFundAmount.value == 0 && this.document.all.edLoanAmount.value == 0)
	{
		DispMessage(this.document.all.edFundAmount, "公积金贷款金额和商业贷款金额不能都为0");
		return false;
	}
	return true;
	}
		</script>
		<script>
     //GetCalc_Close();
		</script>
	</body>
</HTML>
