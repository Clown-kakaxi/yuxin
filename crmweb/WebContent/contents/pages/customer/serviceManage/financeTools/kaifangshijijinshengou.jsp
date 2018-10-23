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
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>
			<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcFundDeclare.js" language="JavaScript"> </SCRIPT>
	 	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	  <LINK href="../Script/StylePrint.css" type="text/css" rel="STYLESHEET" media="print">
		<script language="javascript">
		   function CheckData()
		   {
			  if (!CheckPN(this.document.all.edamount,"请在[申购金额]中输入正整数", null))
				return false;
			  if (!CheckFN3(this.document.all.edone,"请在[基金单位净值]中输入正数",false,null,4))
				return false;
			  if (!CheckFN3(this.document.all.edrate,"请在[申购手续费率]中输入正数",false,null,2))
				return false;
			  
			  return true;
		   }
		   
		</script>
  </HEAD>
  <body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
//<!-- var cImage; 
 //cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
//-->
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
	<form name="CalcBondProfit" id="CalcBondProfit" onsubmit="return false;">
	<!--    <div id="bg1" class="divnoprt"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="200" height="126"><img src="../Images/bg3.gif"></div>
	  <div id="bg2" class="divnoprt"><img src="../Images/bg4.gif" width="25" height="120"><img src="../Images/bg5.gif" width="425" height="120"><img src="../Images/bg6.gif" width="25" height="120"></div>
	  <div id="bg3" class="divnoprt"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="425"><img src="../Images/bg9.gif"></div>
	-->
	  <div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【开放式基金申购计算器】</div>
	  <div id="FDiv2" class="FDiv2" Movable="1" style="width:430">本计算器依据申购时的基金单位净值和申购手续费，可以迅速的计算出一定的申购金额可以申购到的基金单位。</div>
	  <DIV id="FDiv3" class="FDiv3" style="WIDTH: 400px; " align="center">
		<TABLE id="Table1" style="WIDTH: 424px; HEIGHT: 160px" cellSpacing="1" cellPadding="1" border="0">
		  <TR>
			<TD style="WIDTH: 150px; HEIGHT: 25px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 申购金额(元)</TD>
			<TD style="HEIGHT: 25px">
			  <INPUT type="text" class="txtnum" id="edamount" tabindex="1"></TD>
			<td>
			  <INPUT class="btn" tabindex="4" id="btnenter" onclick="javascript:if (CheckData()) calc(document);" type="button" value=" 计算 " name="btnenter"></td>
		  </TR>
		  <TR>
			<TD style="WIDTH: 150px; HEIGHT: 25px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 基金单位净值(元)</TD>
			<TD style="HEIGHT: 25px">
			  <INPUT type="text" class="txtnum" id="edone" tabindex="2"></TD>
			<td style="display:none">
			  <script language="javascript" event="onclick" for="btnPrint">
								<!--if(CheckData())
								{
									print();
								}-->
			  </script>
			  <input class="btn" id="btnPrint" type="button" value=" 打印 " NAME="btnPrint"></td>
		  </TR>
		  <TR>
			<TD style="WIDTH: 150px; HEIGHT: 25px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 申购手续费率(%)</TD>
			<TD style="HEIGHT: 25px">
			  <INPUT type="text" class="txtnum" id="edrate" tabindex="3"></TD>
			<td></td>
		  </TR>
		  <tr>
			<td colspan="3" style="HEIGHT: 20px"><hr size="1" width="400">
			</td>
		  </tr>
		  <TR>
			<TD style="WIDTH: 150px; HEIGHT: 28px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 申购基金单位(份)</TD>
			<TD style="HEIGHT: 28px">
			  <INPUT class="txt" id="ednumber" type="text" value="计算得出" disabled="true" style="TEXT-ALIGN: right"></TD>
			<td></td>
		  </TR>
		</TABLE>
	  </DIV>
	</form>
	<script>
     //GetCalc_Close();
	</script>
  </body>
</HTML>
