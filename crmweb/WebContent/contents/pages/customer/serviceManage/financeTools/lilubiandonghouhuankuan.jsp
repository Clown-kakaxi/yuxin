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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcLoanChg.js" language="JavaScript"> </SCRIPT>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
		</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
//<img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
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
		<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" height="126" width="255"><img src="../Images/bg3.gif"></div>
		<div id="bg2"><img src="../Images/bg4.gif" width="25" height="280"><img src="../Images/bg5.gif" width="480" height="280"><img src="../Images/bg6.gif" width="25" height="280"></div>
		<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="480"><img src="../Images/bg9.gif"></div>-->
		<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【利率变动后还款计算器】</div>
		<div id="FDiv2" class="FDiv2" Movable="1" style="width:480px">本计算器是计算当长期贷款（等额本息还款）的利率发生一次或多次变动后，在其它条件不变的情况下，每期还款额和最终总偿付利息额的变动情况。</div>
		<form id="CalcLoanChg" name="CalcLoanChg" onsubmit="return false;">
			<div id="FDiv3" class="FDiv3" align="center" style="width:500px">
				<table id="Table2" border="0" width="90%">
					<tr>
						<td style="display:NONE">贷款类型</td>
						<td style="display:NONE"><select id="edType" name="edType" onchange="TypeChange()">
								<option value="0" selected>商业贷款</option>
								<option value="1">公积金贷款</option>
							</select></td>
						</SELECT>
						<td></td>
					</tr>
					<tr>
						<td>贷款总额(元)</td>
						<td><input name="edLoanSum" style="width:100px" type="text" class="txtnum" id="edLoanSum" tabIndex="1" value="0"></td>
						<td><input class="btn" id="btnCalc" tabIndex="11" type="button" value=" 计算 " name="btnCalc" onclick="javascript:if(CheckData()) Execute(document);"></td>
					</tr>
					<tr>
						<td>贷款期限(年)</td>
						<td><input class="txtnum" style="width:100px" id="edLoanYears" onblur="TypeChange()" tabIndex="2" type="text" value="10" name="edLoanYears"></td>
						<td></td>
					</tr>
					<tr>
						<td>初始还款时间</td>
						<td><input class="txt" style="width:100px" id="edDate1" onblur="ChkCZDate(document.all.edDate1);" tabIndex="3" type="text" value="2003-1-3" name="edDate1">
							<IMG style="cursor:hand" onclick="javascript:Cal_dropdown(document.all.edDate1)" src="images/Calendar1.gif" align="absMiddle">
						</td>
						<td></td>
					</tr>
					<tr>
						<td>原贷款年利率(%)</td>
						<td><input class="txtnum" style="width:100px" id="edRate1" tabindex="4" type="text" value="4.77" name="edRate1"></td>
						<td></td>
					</tr>
					<tr>
						<td colSpan="4">利率变动情况</td>
					</tr>
					<tr>
						<td>新利率执行日期</td>
						<td><input class="txt" id="edDate2" style="width:100px" onBlur="ChkCZDate(document.all.edDate2);" tabindex="5" type="text" value="2003-1-3" name="edDate2">
							<IMG style="cursor:hand" onclick="javascript:Cal_dropdown(document.all.edDate2)" src="images/Calendar1.gif" align="absMiddle">
						</td>
						<td width="67">利率(%)</td>
						<td><input class="txtnum" id="edRate2" tabIndex="6" style="width:100px" type="text" name="edRate2"></td>
					</tr>
					<tr>
						<td>新利率执行日期</td>
						<td><input class="txt" id="edDate3" style="width:100px" onBlur="ChkCZDate(document.all.edDate3);" tabindex="7" type="text" value="2003-1-3" name="edDate3">
							<IMG style="cursor:hand" onclick="javascript:Cal_dropdown(document.all.edDate3)" src="images/Calendar1.gif" align="absMiddle">
						</td>
						<td width="67">利率(%)</td>
						<td><input class="txtnum" id="edRate3" tabIndex="8" type="text" style="width:100px" name="edRate3"></td>
					</tr>
					<tr>
						<td>新利率执行日期</td>
						<td><input class="txt" id="edDate4" style="width:100px" onblur="ChkCZDate(document.all.edDate4);" tabIndex="9" type="text" value="2003-1-3" name="edDate4">
							<IMG style="cursor:hand" onclick="javascript:Cal_dropdown(document.all.edDate4)" src="images/Calendar1.gif" align="absMiddle">
						</td>
						<td width="67">利率(%)</td>
						<td><input class="txtnum" style="width:100px" id="edRate4" tabIndex="10" type="text" name="edRate4"></td>
					</tr>
					<tr>
						<td colSpan="4" height="5">
							<hr width="400" SIZE="1">
						</td>
					</tr>
					<tr>
						<td colSpan="4" align="middle">
							<div id="divReturnList">
								<xml id="xmlresult" async="false">
									<Root>
										<Items>
											<Memo />
											<MonthRet />
											<Interest />
										</Items>
									</Root>
								</xml>
								<table id="Table3" dataSrc="#xmlresult" width="430" border="0">
									<thead>
										<tr class="head">
											<th align="middle">
												说明</th>
											<th align="middle">
												月还款额(元)</th>
											<th align="middle">
												利息总额(元)</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td align="left"><span dataFld="Memo"></span></td>
											<td align="right"><span dataFld="MonthRet"></span></td>
											<td align="right"><span dataFld="Interest"></span></td>
										</tr>
									</tbody></table>
							</div>
							<xml id="xmlchg" async="false">
								<Root>
									<Items>
										<chgdate />
										<chgrate />
									</Items>
								</Root>
							</xml>
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
						</td>
					</tr>
				</table>
			</div>
		</form>
		<xml id="xmlLoanRate" src="http://localhost:8080/CRM/scripts/financeTools/LoanAndOtherLoan.xml"></xml>
		<script language="javascript">
		function ChkCZDate(edit)
		{
			edit.value=Trim(edit.value);
			if(edit.value=="")
				return true;
			if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
			{
				alert('日期格式不正确,日期有效范围为1910年到3000年');
				edit.focus();
			}
		}

		function CheckData()
		{
			if (!CheckPN(this.document.all.edLoanSum,"请在贷款总额输入正整数",false))
				return false;
			if (!CheckPN(this.document.all.edLoanYears,"请在贷款期限输入正整数",false))
				return false;
			if (!CheckFN3(this.document.all.edRate1,"请在原贷款年利率输入正数",false,null,4))
				return false;
			if (!CheckEmpty(this.document.all.edDate1,"无效的初始还款时间"))
				return false;
			if(Trim(document.all.edRate2.value)!="")
			{
			if (!CheckFN3(this.document.all.edRate2,"请在利率输入正数",false,null,4))
				return false;
			else
				Rate2=parseFloat(document.all.edRate2.value)/100;		
			}
			else
				Rate2=-1;    // Rate2=0;  // modified by huhao, 2003/4/30: 用-1表示无值
			if(Trim(document.all.edRate3.value)!="")
			{
				if (!CheckFN3(this.document.all.edRate3,"请在利率输入正数",false,null,4))
					return false;
				else
					Rate3=parseFloat(document.all.edRate3.value)/100;		
			}
			else
				Rate3=-1;    // Rate3=0; // modified by huhao, 2003/4/30: 用-1表示无值
			if(Trim(document.all.edRate4.value)!="")
			{
			if (!CheckFN3(this.document.all.edRate4,"请在利率输入正数",false,null,4))
				return false;
			else
				Rate4=parseFloat(document.all.edRate4.value)/100;
			}
			else
			Rate4=-1;    // Rate4=0; // modified by huhao, 2003/4/30: 用-1表示无值
			if (Rate2 < 0 && Rate3 >= 0)
			{
				DispMessage(document.all.edRate2, "请在利率输入正数");
				return false;
			}
			if (Rate3 < 0 && Rate4 >= 0)
			{
				DispMessage(document.all.edRate3, "请在利率输入正数");
				return false;
			}
			if(document.all.edRate2.value!='' && new Number(document.all.edRate2.value) > 0)
			{
				if (!CheckEmpty(this.document.all.edDate2,"无效的利率变动日期"))
					return false;
			}
			if(document.all.edRate3.value!='' && new Number(document.all.edRate3.value) > 0)
			{
				if (!CheckEmpty(this.document.all.edDate3,"无效的利率变动日期"))
					return false;
			}
			if(document.all.edRate4.value!='' && new Number(document.all.edRate4.value) > 0)
			{
				if (!CheckEmpty(this.document.all.edDate4,"无效的利率变动日期"))
					return false;
			}
			
			var Rate1 = parseFloat(document.all.edRate1.value) / 100;
			var dtLater, dtEarlier;
			if (Rate2 > 0)
			{
				dtEarlier = Cal_strtodate(document.all.edDate1.value);
				dtLater = Cal_strtodate(document.all.edDate2.value);
				if (dtLater <= dtEarlier)
				{
					DispMessage(document.all.edDate2, "新利率执行日期必须晚于初始还款时间");
					return false;
				}
				else
				{
					var limitDate = new Date(dtEarlier);
					var tempLoanYears = parseInt(document.all.edLoanYears.value);
					//alert(tempLoanYears);
					limitDate = new Date(limitDate.getFullYear() + tempLoanYears,limitDate.getMonth(),limitDate.getDate());
					if(dtLater > limitDate)
					{
						//alert("日期过了！"+"\nlimitDate="+limitDate.toLocaleString()+"\ndtLater="+dtLater);
						DispMessage(document.all.edDate2, "新利率执行日期必须小于初始还款时间+贷款期限");
						return false;
					}
				}
			}
			if (Rate3 > 0)
			{
				dtEarlier = Cal_strtodate(document.all.edDate2.value);
				dtLater = Cal_strtodate(document.all.edDate3.value);
				if (dtLater <= dtEarlier)
				{
					DispMessage(document.all.edDate3, "第二次利率变动日期必须晚于第一次利率变动日期");
					return false;
				}	
				else
				{
					var limitDate = new Date(Cal_strtodate(document.all.edDate1.value));
					var tempLoanYears = parseInt(document.all.edLoanYears.value);
					limitDate = new Date(limitDate.getFullYear() + tempLoanYears,limitDate.getMonth(),limitDate.getDate());
					if(dtLater > limitDate)
					{
						DispMessage(document.all.edDate3, "第二次利率执行日期必须小于初始还款时间+贷款期限");
						return false;
					}
				}	
			}
			if (Rate4 > 0)
			{
				dtEarlier = Cal_strtodate(document.all.edDate3.value);
				dtLater = Cal_strtodate(document.all.edDate4.value);
				if (dtLater <= dtEarlier)
				{
					DispMessage(document.all.edDate4, "第三次利率变动日期必须晚于第二次利率变动日期");
					return false;
				}		
				else
				{
					var limitDate = new Date(Cal_strtodate(document.all.edDate1.value));
					var tempLoanYears = parseInt(document.all.edLoanYears.value);
					limitDate = new Date(limitDate.getFullYear() + tempLoanYears,limitDate.getMonth(),limitDate.getDate());
					if(dtLater > limitDate)
					{
						DispMessage(document.all.edDate4, "第三次利率执行日期必须小于初始还款时间+贷款期限");
						return false;
					}
				}	
			}
			return true;
		}

		function SetTableColor(table)
		{
			for(i=0;i<=table.length-1;i++)
			{
				if(i==0)
					table.rows(i).className="head";
				else
				{
					if (i%2)
						table.rows(i).className="odd";
					else
						table.rows(i).className="even";
				}
			}
		}		

		function TypeChange()
		{
			var IDType = "1";
			if (this.document.all.edType.selectedIndex == 1) IDType = "2";
			var period = (this.document.all.edLoanYears.value) * 12;
			var ret = GetLoanRatio(IDType,period,xmlLoanRate);
			if(ret == undefined)
			{
				document.all.edLoanYears.select();
				document.all.edLoanYears.focus();
			}
			else
			{
				this.document.all.edRate1.value = ret;//GetLoanRatio(IDType, period, xmlLoanRate);
			}
		}

		function init()
		{
			//TypeChange();
		}

		this.document.all.edDate1.value=datetostring(new Date());
		this.document.all.edDate2.value=datetostring(new Date());
		this.document.all.edDate3.value=datetostring(new Date());
		this.document.all.edDate4.value=datetostring(new Date());
		window.attachEvent("onload",init);				
	//	GetCalc_Close();
		</script>
	</body>
</HTML>
