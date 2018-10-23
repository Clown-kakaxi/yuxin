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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcLoan.js" language="JavaScript"> </SCRIPT>
		
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
			<!--  <LINK href="../Script/StylePrint.css" type="text/css" rel="STYLESHEET" media="print"> -->
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

<!--  <script type="text/javascript" src="http://msn.wrating.com/a1.js">

-->
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
		<form id="LoanCalculator" name="LoanCalculator" method="post" onsubmit="return false;">
	<!-- 		<div id="bg1" class="divnoprt"><IMG src="../Images/bg1.gif"><IMG height="126" src="../Images/bg2.gif" width="235"><IMG src="../Images/bg3.gif"></div>
			<div id="bg2" class="divnoprt"><IMG height="280" src="../Images/bg4.gif" width="25"><IMG height="280" src="../Images/bg5.gif" width="460"><IMG height="280" src="../Images/bg6.gif" width="25"></div>
			<div id="bg3" class="divnoprt"><IMG src="../Images/bg7.gif"><IMG height="119" src="../Images/bg8.gif" width="460"><IMG src="../Images/bg9.gif"></div>
		 -->
			<div class="FDiv1" id="FDiv1" style="color:black">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;【贷款计算器】</div>
			<div class="FDiv2" id="FDiv2" style="WIDTH: 450px">&nbsp;贷款的情况较为复杂，本计算器可以依据已知条件，方便的算出贷款的还款情况。请点击“还款表”进行查询。</div>
			<div class="FDiv3" id="FDiv3" style="WIDTH: 80px; HEIGHT: 90px">
				<table id="Table1" width="90%" border="0">
					<tr>
						<td align="middle">
							<TABLE id="Table2" height="180" width="450" border="0">
								<tr>
									<td width="63"></td>
									<td width="99">贷款类型</td>
									<td width="166"><div class="select2"><select id="edType" style="WIDTH: 120px" tabIndex="1" name="edType" onchange="AutoShowRate();">
												<option value="楼宇按揭" selected>楼宇按揭</option>
												<option value="汽车消费贷款">汽车消费贷款</option>
												<option value="个人消费贷款">个人消费贷款</option>
											</select></div>
									</td>
									<td>
										<INPUT class="btn" id="btnReturnList" tabIndex="7" type="button" value="计算还款表" name="btnReturnList" onClick="javascript:if(CheckData()) {GetPlayList(document);SetTableColor(Table3);}"></td>
								</tr>
								<TR>
									<TD width="63"></TD>
									<TD width="99">贷款金额</TD>
									<TD width="166"><input class="txtnum" tabIndex="2" id="edLastSum" style="WIDTH: 117px" type="text" name="edLastSum"><FONT face="宋体">&nbsp;元</FONT></TD>
									<TD>
										<input class="btn" id="btnPrint" type="button" value="打印" style="width:88px" NAME="btnPrint" tabIndex="8"></TD>
								</TR>
								<TR>
									<TD width="63"></TD>
									<TD width="99">贷款期限</TD>
									<TD width="166">
										<input class="txtnum" id="edTimes" style="WIDTH: 117px" tabIndex="3" onblur="AutoShowRate();" type="text" name="edTimes"><FONT face="宋体">&nbsp;月</FONT></TD>
									<TD>
										<script language="javascript" event="onclick" for="btnPrint">
											if(CheckData())
											{
												if(document.all.rbPayFreq_0.checked)document.all.showPayFreq.innerText = "每月";
												if(document.all.rbPayFreq_1.checked)document.all.showPayFreq.innerText = "每季";
												if(document.all.rbPayFreq_2.checked)document.all.showPayFreq.innerText = "一次性";
												print();
											}
										</script>
									</TD>
								</TR>
								<TR>
									<TD width="63"></TD>
									<TD width="99">年利率</TD>
									<TD width="166"><input style="WIDTH: 117px" class="txtnum" id="edRate" type="text" tabIndex="4" name="edRate">&nbsp;%</TD>
									<TD></TD>
								</TR>
								<TR>
									<TD width="63"></TD>
									<TD width="99">还款方式</TD>
									<TD width="166"><div class="select"><select id="ddlPayWay" style="WIDTH: 120px" tabIndex="5" name="ddlPayWay">
												<option value="1" selected>等额本息还款法</option>
												<option value="2">等额本金还款法</option>
												<option value="3">一次性还本付息法</option>
											</select></div>
									</TD>
									<TD></TD>
								</TR>
								<TR>
									<TD width="63"></TD>
									<TD width="99">偿还频率</TD>
									<TD width="166">
										<table id="rbPayFreq" border="0">
											<tr class="trnoprt">
												<td><input id="rbPayFreq_0" type="radio" CHECKED value="1" name="rbPayFreq" tabIndex="6"><label for="rbPayFreq_0">每月</label></td>
												<td><input id="rbPayFreq_1" type="radio" value="3" name="rbPayFreq"><label for="rbPayFreq_1">每季</label></td>
												<td><input id="rbPayFreq_2" type="radio" value="4" name="rbPayFreq"><label for="rbPayFreq_2">一次性</label></td>
											</tr>
											<tr>
												<td id="showPayFreq" class="tdhidden"></td>
											</tr>
										</table>
									</TD>
									<TD></TD>
								</TR>
							</TABLE>
						</td>
					</tr>
					<tr>
						<td align="middle">
							<div id="divReturnList" class="scdiv">
								<table id="Table3" dataSrc="#xmldso" width="430" border="0">
									<thead>
										<tr class="head">
											<th align="middle">
												期次</th>
											<th align="middle">
												还款时间</th>
											<th align="middle">
												偿还利息</th>
											<th align="middle">
												偿还本金</th>
											<th align="middle">
												偿还本息</th>
											<th align="middle">
												剩余本金</th></tr>
									</thead>
									<tbody>
										<tr>
											<td align="left"><span dataFld="Times"></span></td>
											<td align="left"><span dataFld="Year"></span></td>
											<td align="right"><span dataFld="RateSum"></span></td>
											<td align="right"><span dataFld="Corpus"></span></td>
											<td align="right"><span dataFld="CorpusRate"></span></td>
											<td align="right"><span dataFld="LeavCorpus"></span></td>
										</tr>
									</tbody></table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<xml id="xmlLoan" src="http://localhost:8080/CRM/scripts/financeTools/LoanAndOtherLoan.xml"></xml>
		<script language="javascript">
		function CheckData()
		{
			if (!CheckPN(this.document.all.edLastSum,"请在贷款金额输入正整数",false)) 
				return false;																
			if (!CheckPN(this.document.all.edTimes,"请在贷款期限输入正整数",false)) 
				return false;				
			if (!CheckFN(this.document.all.edRate,"请在年利率要求输入非负数",null,4))
					return false;																    												
			if (this.document.all.rbPayFreq_1.checked) //每季度，	贷款期限要是3的倍数
				if ( parseInt(this.document.all.edTimes.value) % 3!=0)
				{
					DispMessage(this.document.all.edTimes,"当偿还频率为每季度时，贷款期限要是3的倍数");
					return false;
				}
			return true;					
		}
			
		function AutoShowRate()
		{
			if(document.all.edTimes.value>0)
			{
				var year;
				year = document.all.edTimes.value/12;
				document.all.edRate.value='';
				switch(document.all.edType.value)
				{
					case "楼宇按揭":
						document.all.edRate.value = GetLoanRatio(1, year*12, xmlLoan);
						//if(year<=5) document.all.edRate.value = "4.77";
						//if(year>5 && year <=30) document.all.edRate.value = "5.04";
						break;
					case "汽车消费贷款":
						if(year<=1) document.all.edRate.value = "4.779";
						if(year>1 && year<=3) document.all.edRate.value = "4.941";
						if(year>3 && year<=5) document.all.edRate.value = "5.022";
						if(year>5 && year<=8) document.all.edRate.value = "5.184";
						break;
					case "个人消费贷款":
						if(year<=1) document.all.edRate.value = "5.841";
						if(year>1 && year<=3) document.all.edRate.value = "6.039";
						if(year>3 && year<=5) document.all.edRate.value = "6.138";
						if(year>5 && year<=20) document.all.edRate.value = "6.336";
						break;
				}
			}
		}
			
		function waychg(flag)
		{
			if(this.document.all.ddlPayWay.selectedIndex==0)	
			{	    
				this.document.all.rbPayFreq_0.disabled = false;
				this.document.all.rbPayFreq_1.disabled = false;
				this.document.all.rbPayFreq_2.disabled = true;
				if(this.document.all.rbPayFreq_2.checked)
					this.document.all.rbPayFreq_0.checked = true;
			}
			if(this.document.all.ddlPayWay.selectedIndex==1)	
			{
				this.document.all.rbPayFreq_0.disabled = false;
				this.document.all.rbPayFreq_1.disabled = false;
				this.document.all.rbPayFreq_2.disabled = true;
				if(this.document.all.rbPayFreq_2.checked)
					this.document.all.rbPayFreq_0.checked = true;
			}
			if(this.document.all.ddlPayWay.selectedIndex==2)	
			{
				this.document.all.rbPayFreq_0.disabled = true;
				this.document.all.rbPayFreq_1.disabled = true;
				this.document.all.rbPayFreq_2.disabled = false;
				this.document.all.rbPayFreq_2.checked = true;
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
		function init()
		{
			waychg(false); 	
			SetTableColor(Table3);		  
		}
		
		window.attachEvent("onload",init);
//     GetCalc_Close();
		</script>
		<script language="javascript" event="onchange" for="ddlPayWay">	
		 waychg(true);  
		</script>
	</body>
</HTML>
