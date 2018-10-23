<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WinControl.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calendar.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calculator.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WBselect.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/GetRate.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Components.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcDeposit.js"></script>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
			<script language="javascript">
		  function getrate()
		  {
		    if (document.DepositCalculator.rbDepositWay_0.checked==true)
		    {
		      document.DepositCalculator.tbYearRate.value=GetRMBSaveRatio(2,document.DepositCalculator.tbSaveTime.options(document.DepositCalculator.tbSaveTime.selectedIndex).value,window.xmlRMBSaveRate.XMLDocument);
		      }
		    if (document.DepositCalculator.rbDepositWay_1.checked==true)
		    {
		       document.DepositCalculator.tbYearRate.value=GetRMBSaveRatio(5,document.DepositCalculator.tbSaveTime.options(document.DepositCalculator.tbSaveTime.selectedIndex).value,window.xmlRMBSaveRate.XMLDocument);
		      }
		  }
		  function check()
		  {
		    if (!CheckEmpty(this.document.all.tbYearRate,"初始存入日期格式不正确"))
			  return false;
		    if (!CheckFN3(this.document.all.tbYearRate,"请在年利率输入正数",false,null,4))
	   		 return false;				
		   if (this.document.all.rbCalcOption_0.checked)
		    {
			if (!CheckFN3(this.document.all.tbInitSaveSum,"请在" + document.all.Layer1.innerText
					+ "输入正数",false))  // 初期存入金额
	   			return false;			
	    	}
	     	else		
	   	   {
			if (!CheckFN3(this.document.all.tbInitSaveSum,"请在到期本息总额输入正数",false))
	   			return false;				   		
	   	   }	
	   	    return true;
		  }
		  
		  function calcu()
		  {
		    if (check()==false) return false;
		    CalcDeposit();
					         
		     return false;
		  }
			</script>
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<!--  <img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
-->
</NOSCRIPT>

<!-- START WRating v1.0 -->
<script type="text/javascript" src="http://msn.wrating.com/a1.js">
</script>
<script type="text/javascript">
<!--
//var vjAcc="860010-0218010200";
//var wrUrl="http://msn.wrating.com/";
//vjTrack("")-->
</script>
<noscript>
<!--  <img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/>
-->
</noscript>
<!-- END WRating v1.0 -->
		<form name="DepositCalculator">
			<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="300" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="190"><img src="../Images/bg5.gif" width="525" height="190"><img src="../Images/bg6.gif" width="25" height="190"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="525"><img src="../Images/bg9.gif"></div>-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【存款计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:540">银行存款是使用较多的投资方式，通过本计算器，可以对零存整取与整存整取的初始存入金额或到期本息总额进行计算。其中，到期本息总额为扣除利息税后的净值。</div>
			<DIV id="FDiv3" class="FDiv3" style="WIDTH: 500px; " align="center">
				<TABLE id="Table1" style="WIDTH: 426px; " cellSpacing="1" cellPadding="1" width="376" border="0">
					<TR>
						<TD style="WIDTH: 110px; HEIGHT: 30px">存款方式</TD>
						<TD style="WIDTH: 300px; HEIGHT: 30px"><table id="rbDepositWay" border="0">
								<tr>
									<td><input id="rbDepositWay_0" type="radio" name="rbDepositWay" value="1" checked onclick="changeall()" language="javascript" tabindex="1" />
										<label for="rbDepositWay_0">整存整取</label></td>
									<td><input id="rbDepositWay_1" type="radio" name="rbDepositWay" value="2" onclick="changeall()" language="javascript" />
										<label for="rbDepositWay_1">零存整取</label></td>
								</tr>
							</table>
						</TD>
						<TD style="HEIGHT: 30px"><input type="submit" name="btnExecute" value=" 计算 " id="btnExecute" class="btn" onclick="return calcu()" tabindex="8" /></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 110px; HEIGHT: 30px" vAlign="middle">计算选项</TD>
						<TD style="HEIGHT:30px">
							<table height="28" width="100%" border="0" id="rbCalcOption">
								<tr>
									<td><input id="rbCalcOption_0" type="radio" name="rbCalcOption" value="1" checked tabindex="2" />
										<label for="rbCalcOption_0">计算到期本息总额</label></td>
									<td><input id="rbCalcOption_1" type="radio" name="rbCalcOption" value="2" />
										<label for="rbCalcOption_1">计算初期存入金额</label></td>
								</tr>
							</table>
						</TD>
						<td rowspan="8"></td>
					</TR>
					<TR>
						<TD style="WIDTH: 110px; HEIGHT: 30px">初始存入日期</TD>
						<TD style="HEIGHT: 30px"><input name="beginDate" tabindex="3" class="txt" type="text" value="2001-1-1" id="beginDateID" style="width:90px" onblur='ChkCZDate(beginDateID);'>
							<IMG SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(beginDateID)'></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 110px; HEIGHT: 30px">储蓄存期</TD>
						<TD style="HEIGHT: 30px">
								<select name="tbSaveTime" tabindex="4" id="tbSaveTime" onchange="getrate()" language="javascript" style="width:123px;">
							
							</select>

				
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 110px;HEIGHT: 30px">年利率</TD>
						<TD><input name="tbYearRate" type="text" value="1.98" tabindex="5" id="tbYearRate" class="txtnum" style="width:112px;" />
							&nbsp;%</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 110pxHEIGHT: 30px" id="LabQcMq"><DIV id="Layer1" style="Z-INDEX:102">初期存入金额</DIV>
							<!--<DIV id="Layer2" style="Z-INDEX:101">每期存入金额</DIV>--></TD>
						<TD><input name="tbInitSaveSum" type="text" id="tbInitSaveSum" tabindex="6" class="txtnum" style="width:112px;" />
							&nbsp;元</TD>
					</TR>
					<tr>
						<td colspan="2">
							<hr width="100%" SIZE="1">
						</td>
					</tr>
					<TR>
						<TD style="WIDTH: 110px;HEIGHT: 30px"><div id="layerresult">到期本息总额</div>
						</TD>
						<TD><input name="tbTermEndSum" type="text" value="计算得出" disabled readonly id="tbTermEndSum" class="txtd" style="width:112px;" />
							&nbsp;元</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 110px;HEIGHT: 30px">扣除利息税金额</TD>
						<TD><input name="tbInterestTaxSum" type="text" value="计算得出" disabled readonly id="tbInterestTaxSum" class="txtd" style="width:112px;" />
							&nbsp;元</TD>
					</TR>
				</TABLE>
			</DIV>
		</form>
		<script language="javascript">
		function changeall()
		{		
			Cleartxt();
		    if (document.DepositCalculator.rbDepositWay_0.checked==true)
		    {
				document.DepositCalculator.tbSaveTime.options.length = 0;
				ComSaveTime(document.DepositCalculator.tbSaveTime,2,window.xmlRMBSaveRate.XMLDocument);

				if (document.DepositCalculator.E_tbSaveTime!=null)
					document.DepositCalculator.E_tbSaveTime.value = document.DepositCalculator.tbSaveTime.options(0).text;
				document.DepositCalculator.tbSaveTime.selectedIndex = 0;
				if (this.document.all.rbCalcOption_0.checked)
				{
					this.document.all.Layer1.innerText="初期存入金额";
					this.document.all.layerresult.innerText="到期本息总额";
				}  
				else
				{
	    			this.document.all.Layer1.innerText="到期本息总额";
	    			this.document.all.layerresult.innerText="初期存入金额";
	    		}		    
		    }
			if (document.DepositCalculator.rbDepositWay_1.checked==true)
			{
				document.DepositCalculator.tbSaveTime.options.length = 0;
				ComSaveTime(document.DepositCalculator.tbSaveTime,5,window.xmlRMBSaveRate.XMLDocument);

				if (document.DepositCalculator.E_tbSaveTime!=null)
					document.DepositCalculator.E_tbSaveTime.value = document.DepositCalculator.tbSaveTime.options(0).text;
				document.DepositCalculator.tbSaveTime.selectedIndex = 0;		
				if (this.document.all.rbCalcOption_0.checked)
				{
					this.document.all.Layer1.innerText="每期存入金额";
					this.document.all.layerresult.innerText="到期本息总额";
				}  
				else
				{
	    		this.document.all.Layer1.innerText="到期本息总额";
	    		this.document.all.layerresult.innerText="每期存入金额";
	    		}
			}
			document.DepositCalculator.tbSaveTime.selectedIndex = 0;
			getrate(); 
		}
		</script>
		<script language="javascript" event="onclick" for="rbCalcOption">	
		Cleartxt();
		if (this.document.all.rbCalcOption_0.checked)
     	{	    		
		    document.all.layerresult.innerText = "到期本息总额";
		    if (document.DepositCalculator.rbDepositWay_0.checked==true)
		        this.document.all.Layer1.innerText="初期存入金额";
		    else 
		        this.document.all.Layer1.innerText="每期存入金额";
		}
		else
		{
		    document.all.Layer1.innerText = "到期本息总额";	    
		    if (document.DepositCalculator.rbDepositWay_0.checked==true)
		        document.all.layerresult.innerText = "初期存入金额";
		    else
		        document.all.layerresult.innerText = "每期存入金额"; 
		}		 
		</script>
		<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml" ondatasetcomplete="star()"></xml>
	</body>
</HTML>
<script language="javascript">
function Cleartxt()
{
	document.all.tbInitSaveSum.value = "";
	document.all.tbTermEndSum.value = "计算得出";
	document.all.tbInterestTaxSum.value = "计算得出";
}
function star()
{
	changeall();
	getrate();
	Cleartxt();
}
function ChkCZDate(edit)
{
	edit.value=Trim(edit.value);
	if(edit.value=='') return true;
	if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
	{
		alert('日期格式不正确,日期有效范围为1910年到3000年');
		edit.focus();
	}
 }
document.DepositCalculator.beginDateID.value=datetostring(new Date());
//this.document.all.Layer1.style.display="block";
//this.document.all.Layer2.style.display="none";
this.document.all.rbDepositWay_0.checked = true;
//GetCalc_Close();
</script>
