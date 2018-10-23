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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcEqualSumRet.js" language="JavaScript"> </SCRIPT>

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
		<form name="CompPartSumCalc" method="post" action="EqualSumRetCalc.aspx" id="CompPartSumCalc">
			<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" height="126" width="245"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="300"><img src="../Images/bg5.gif" width="470" height="300"><img src="../Images/bg6.gif" width="25" height="300"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="470"><img src="../Images/bg9.gif"></div>-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【等额累进还款计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="WIDTH:480px">贷款等额累进还款法是国际上十分通行的一种消费信贷还款方式，适合于预计未来收入将有较大变动的客户。本计算器可以依据一定的条件，计算出运用此还款法的还款额度变动情况，并可反向计算贷款金额。</div>
			<div id="FDiv3" class="FDiv3" style="WIDTH:80px;HEIGHT:90px">
				<TABLE id="Table1" width="450" border="0">
					<TR>
						<TD width="155" height="25">
							计算项目</TD>
						<TD width="186" height="25">
							<input type="radio" checked="true" id="rd1" NAME="RadioGroup">首次还款金额 <input type="radio" id="rd2" NAME="RadioGroup">贷款金额
						</TD>
						<TD height="25"><INPUT class="btn" style="width:70px" id="btnCalc" tabIndex="1008" type="button" value=" 计算 "></TD>
					</TR>
					<TR>
						<TD width="155" height="25">
							还款方法</TD>
						<TD width="186" height="25">
							<select style="width=116px" name="RetType" id="RetType" tabindex="1000">
								<option value="1" selected>等额递增还款法</option>
								<option value="2">等额递减还款法</option>
							</select></TD>
						<TD height="25"><INPUT class="btn" style="width:70px" id="btnReturn" tabIndex="1008" type="button" value="计算还款表" name="Button1"></TD>
					</TR>
					<TR>
						<TD width="155" height="25">
							贷款金额(元)</TD>
						<TD width="186"><input style="width=116px" name="edLoanSum" type="text" id="edLoanSum" tabindex="1003" class="txtnum"></TD>
						<TD><FONT face="宋体"></FONT></TD>
					</TR>
					<TR>
						<TD width="155" height="24">
							贷款期限(年)</TD>
						<TD width="186" height="24">
							<input style="width=116px" name="edLoanYears" type="text" id="edLoanYears" tabindex="1003" class="txtnum"></TD>
						<TD height="24"></TD>
					</TR>
					<TR>
						<TD width="155" height="25">
							贷款年利率(％)</TD>
						<TD width="186" height="25">
							<input style="width=116px" name="edRate" type="text" id="edRate" tabindex="1003" class="txtnum"></TD>
						<TD height="25"></TD>
					</TR>
					<TR style="display:none">
						<TD width="155" height="25">
							开始还款日期</TD>
						<TD width="186" height="25"><input name='edSRetDate' type="text" id='edSRetDate' value='2003-1-3' tabIndex="1004" class='txt' onblur='ChkCZDate(edSRetDate);' size="12">
							<IMG style="cursor:hand" SRC="../Images/Calendar1.gif" align="absMiddle" onclick='javascript:Cal_dropdown(edSRetDate)'>
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
						</TD>
						<TD height="25"><FONT face="宋体"></FONT></TD>
					</TR>
					<TR>
						<TD width="155" height="25">
							开始变动的期次(期)</TD>
						<TD width="186"><input style="width=116px" name="edStartTerm" type="text" id="edStartTerm" tabindex="1005" class="txtnum"></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD width="155" height="25">
							每次变动间隔期数(期)</TD>
						<TD width="186">
							<input style="width=116px" name="edSpaceTerm" type="text" id="edSpaceTerm" tabindex="1005" class="txtnum"></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD width="155" height="25"><FONT face="宋体">
								<span id="Lab1">递增金额(元)</span></FONT></TD>
						<TD width="186">
							<input style="width=116px" name="edIncSum" type="text" id="edIncSum" tabindex="1005" class="txtnum"></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD colSpan="3" height="18">
							<HR width="100.13%" SIZE="1">
						</TD>
					</TR>
					<TR>
						<TD width="155" height="25">
							首次还款金额(元)</TD>
						<TD width="186"><input name="edFirstSum" type="text" value="计算得出" id="edFirstSum" tabindex="7" class="txtd"></TD>
						<TD></TD>
					</TR>
					<tr>
						<td colspan="4">
							<div align="center" style="OVERFLOW: auto;HEIGHT: 100px" class="scdiv">
								<table width="90%" border="0" id="Table5">
									<thead>
										<tr class="head">
											<th>
												期次
											</th>
											<th style="display:none">
												还款日期
											</th>
											<th>
												偿还本息
											</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
						</td>
					</tr>
				</TABLE>
			</div>
		</form>
		<script language="javascript" event="onclick" for="btnCalc"> //计算
			 return ComputeData();
		</script>
		<script language="javascript" event="onclick" for="btnReturn"> //还款表
			CaclEqualSum();
			SetTableColor(document.all.Table5);
		</script>
		<script language="javascript" event="onclick" for="rd1">
			calctypechange();			
		</script>
		<script language="javascript" event="onclick" for="rd2">
			calctypechange();			
		</script>
		<script language="javascript" event="onchange" for="RetType">
		  document.all.Lab1.innerText=document.all.RetType.selectedIndex==0 ? "递增金额(元)" : "递减金额(元)";
		</script>
		<script language="javascript">			
	  function ComputeData()
	  {
		var FirstSum = new Number(0);
		var Rate=parseFloat(document.all.edRate.value)/12/100;
		var TotalTerm=parseInt(document.all.edLoanYears.value)*12;
		var SpaceTerm=parseInt(document.all.edSpaceTerm.value);
		var StartTerm=parseInt(document.all.edStartTerm.value);
		var IncSum=document.all.RetType.selectedIndex==0 ? parseInt(document.all.edIncSum.value):-parseInt(document.all.edIncSum.value);
		
		if (!CheckData()) return false;
		
		if (document.all.rd2.checked)	//计算贷款金额
		  {
			  FirstSum=parseFloat(document.all.edFirstSum.value);		
              LoanSum = CalcM(FirstSum, Rate, TotalTerm, StartTerm - 1, SpaceTerm, IncSum, true);
 			  document.all.edLoanSum.value=Math.round(LoanSum);
		  }	
		else //计算首次还款金额  										
		{
			  LoanSum=parseFloat(document.all.edLoanSum.value);
			  FirstSum = CaclA(0, LoanSum * Math.pow(1 + Rate, TotalTerm), LoanSum, Rate, TotalTerm, StartTerm - 1, SpaceTerm, IncSum);
			  document.all.edFirstSum.innerText=NBround(FirstSum,2);
		}
		return true;
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
	  function calctypechange()
	  {							
		if (document.all.rd1.checked)
		{
			document.all.edFirstSum.value="计算得出";
			document.all.edFirstSum.className="txtd";
			document.all.edFirstSum.disabled=true;
			document.all.edLoanSum.value="";
			document.all.edLoanSum.className="txtnum";
			document.all.edLoanSum.disabled=false;					
		}
		else
		{
			document.all.edLoanSum.value="计算得出";
			document.all.edLoanSum.className="txtd";
			document.all.edLoanSum.disabled=true;						
			document.all.edFirstSum.value="";
			document.all.edFirstSum.className="txtnum";
			document.all.edFirstSum.disabled=false;				
		}
					
		SetTableColor(Table5);
	  }
				  
	  window.attachEvent("onload",calctypechange);
	  document.all.edSRetDate.value = datetostring(new Date());
      //GetCalc_Close();		
		</script>
	</body>
</HTML>
