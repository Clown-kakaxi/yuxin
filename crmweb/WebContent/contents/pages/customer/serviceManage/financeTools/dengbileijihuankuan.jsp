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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcEqualRateRet.js" language="JavaScript"> </SCRIPT>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<!--  <img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">-->
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

<!-- 
 <img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/>
-->
</noscript>
<!-- END WRating v1.0 -->
		<form name="CompPartSumCalc" method="post" action="EqualRateRetCalc.aspx" id="CompPartSumCalc">
			<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" height="126" width="245"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="300"><img src="../Images/bg5.gif" width="470" height="300"><img src="../Images/bg6.gif" width="25" height="300"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="470"><img src="../Images/bg9.gif"></div> -->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【等比累进还款计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="WIDTH:480px">等比累进还款法是在一定还款期次内等额还款，之后每隔一定的时间即按比例增加或减少还款金额的还款方法。本计算器可对还款期内的还款情况进行计算。</div>
			<div id="FDiv3" class="FDiv3" style="WIDTH:80px;HEIGHT:90px">
				<TABLE id="Table1" cellSpacing="1" cellPadding="1" width="459" border="0">
					<TR>
						<TD width="209" height="25">
							贷款金额(元)</TD>
						<TD width="147"><input name="edLoanSum" type="text" id="edLoanSum" tabindex="1003" class="txtnum"></TD>
						<TD><INPUT class="btn" id="btnCalc" style="width:70px" tabIndex="1008" type="button" value=" 计算 " NAME="btnCalc"></TD>
					</TR>
					<TR>
						<TD width="209" height="24">
							贷款期限(年)</TD>
						<TD width="147" height="24">
							<input name="edLoanYears" type="text" id="edLoanYears" tabindex="1003" class="txtnum"></TD>
						<TD height="24">
							<INPUT class="btn" style="width:70px" id="btnReturn" tabIndex="1008" type="button" value="计算还款表" name="Button1"></TD>
					</TR>
					<TR>
						<TD width="209" height="25">
							贷款年利率(％)</TD>
						<TD width="147" height="25">
							<input name="edRate" type="text" id="edRate" tabindex="1003" class="txtnum"></TD>
						<TD height="25"></TD>
					</TR>
					<TR>
						<TD width="209" height="25">
							调整还款金额开始期次(期)</TD>
						<TD width="147"><input name="edStartTerm" type="text" id="edStartTerm" tabindex="1005" class="txtnum"></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD width="209" height="25">
							调整还款金额间隔期次(期)</TD>
						<TD width="147">
							<input name="edSpaceTerm" type="text" id="edSpaceTerm" tabindex="1005" class="txtnum"></TD>
						<TD><FONT face="宋体"></FONT></TD>
					</TR>
					<TR>
						<TD width="209" height="25"><FONT face="宋体"> 初次调整还款金额后与调整前比率</FONT></TD>
						<TD width="147">
							<input name="edAdjRate" type="text" id="edAdjRate" tabindex="1005" class="txtnum"></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD colSpan="3" height="18">
							<HR width="100.13%" SIZE="1">
						</TD>
					</TR>
					<TR>
						<TD width="209" height="25">改变还款金额前每月还款额(元)</TD>
						<TD width="147"><input name="edPerSum" type="text" value="计算得出" id="edPerSum" tabindex="7" class="txtd"></TD>
						<TD></TD>
					</TR>
					<tr>
						<td colspan="4">
							<div align="center" style="OVERFLOW: auto;HEIGHT: 180px" class="scdiv">
								<table id="ReturnList" datasrc="#xmldso" width="90%" border="0">
									<thead>
										<tr class="head">
											<th>
												期次
											</th>
											<th>
												偿还本息
											</th>
										</tr>
									</thead>
									<tr>
										<td align="left"><span datafld="Times"></span></td>
										<td align="right"><span datafld="CorpusRate"></span></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</TABLE>
			</div>
			</td></tr></table>
			<xml id="xmldso" async="false">
				<Root>
					<Items>
						<Times />
						<CorpusRate />
					</Items>
				</Root>
			</xml>
		</form>
		<script language="javascript" src="../Script/CheckDataFunction.js"></script>
		<script language="javascript" event="onclick" for="btnCalc"> //计算
		ComputeData();
		</script>
		<script language="javascript" event="onclick" for="btnReturn"> //还款表
		if (ComputeData())
		{
			var LoanSum, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate, RetPerSum;
			LoanSum=parseInt(document.all.edLoanSum.value);
			Rate=parseFloat(document.all.edRate.value)/12/100;								
			TotalTerm=parseInt(document.all.edLoanYears.value)*12;
			SpaceTerm=parseInt(document.all.edSpaceTerm.value);
			StartTerm=parseInt(document.all.edStartTerm.value);
			AdjRate=parseFloat(document.all.edAdjRate.value);
			RetPerSum=parseFloat(document.all.edPerSum.value);
			var obj = document.all("xmldso");
			var FirstDate=new Date();
			ERateLoanReturnList(obj, LoanSum, RetPerSum, Rate, TotalTerm, SpaceTerm, StartTerm, FirstDate, AdjRate);
		}			 
		</script>
		<script language="javascript">			
			function SetTableColor(table)
			{
				for(i=0;i<=table.rows.length-1;i++)
				{
					if(i==0) continue;
					if (i%2)
						table.rows(i).className="odd";
					else
						table.rows(i).className="even";
				}
			}		 
			
		    function ComputeData()
		     {
				if (!CheckPN(document.all.edLoanSum,"贷款金额请输入正整数",false))				 
					return false;
				if (!CheckPN(document.all.edLoanYears,"贷款期限请输入正整数",false))				 
					return false;
				if (!CheckFN3(document.all.edRate,"贷款年利率请输入正数",false,null,4))
					return false;																			
				if (!CheckPN(document.all.edStartTerm,"调整还款金额开始期次请输入大于1的正整数",false))
					return false;																							
				if (!CheckPN(document.all.edSpaceTerm,"调整还款金额间隔期次请输入正整数",false))				 
					return false;										
				if (!CheckFN3(document.all.edAdjRate,"初次调整还款金额后与调整前比率请输入正数",false))				 
					return false;		
								
				var LoanSum=parseInt(document.all.edLoanSum.value);
				var Rate=parseFloat(document.all.edRate.value)/12/100;							
				var TotalTerm=parseInt(document.all.edLoanYears.value)*12;
				var SpaceTerm=parseInt(document.all.edSpaceTerm.value);
				var StartTerm=parseInt(document.all.edStartTerm.value);
				var AdjRate=parseFloat(document.all.edAdjRate.value);
				
				if ((StartTerm>TotalTerm)|| (StartTerm<1))
				{
				  DispMessage(document.all.edStartTerm,"开始变动的期次在2-"+(TotalTerm)+"之间");
				  return false;
				}
				 
				if  (SpaceTerm>TotalTerm-StartTerm+1)
				{
				  DispMessage(document.all.edSpaceTerm,"调整还款金额间隔期次不能大于"+(TotalTerm-StartTerm+1));
				  return false;
				}
				var RetPerSum = ERateLoanPersum(LoanSum, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate);//aiai modify
				
				if(RetPerSum == -1)
				{
					alert("您输入了无效的等比累进还款方式，请调整！");
					return false;
				 }
				 else
				 {
					document.all.edPerSum.innerText = NBround(RetPerSum, 2);
   					return true;				
   			    }
		     }			
		     
			function SetTableColor(table)
			{
				for(i=0;i<=table.rows.length-1;i++)
				{
					if(i==0)
					{
						table.rows(i).className="head";
						continue;
					}
					if (i%2)
						table.rows(i).className="odd";
					else
						table.rows(i).className="even";
				}
			}			
			function loadinit()
			{
			 document.all.edPerSum.disabled=true;
			 SetTableColor(ReturnList);
			}			
			window.attachEvent("onload",loadinit);
		</script>
		<script>
     //GetCalc_Close();
		</script>
	</body>
</HTML>
