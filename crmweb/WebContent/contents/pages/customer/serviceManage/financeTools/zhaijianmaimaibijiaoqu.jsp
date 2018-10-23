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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js" language="JavaScript"> </SCRIPT>
	<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcBondDealCompare.js" language="JavaScript"> </SCRIPT>
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
			<xml id="xmlSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml"></xml>
			<script language="javascript">
		<!--
			function GetRate(month)
			{
				if (month == 0) month = 0.1;
				if (month > 60) month = 60;	// 整存整取最大期限
				return GetRMBSaveRatio("2", month, xmlSaveRate) / 100;  // 整存整取typeID = 2
			}
			function Hidden(ctrl)
			{
				ctrl.disabled=true;
			}
			function Show(ctrl)
			{
				ctrl.disabled=false;
			}
			function setvisible1()
			{
				if(this.document.all.rblID_0.checked)
				{
					Hidden(this.document.all.lblPrice);
					Hidden(this.document.all.tbPrice);
					Hidden(this.document.all.lblBuyDate);
					Hidden(this.document.all.dpBuyDate);
					Hidden(this.document.all.lblSellDate);
					Hidden(this.document.all.dpSellDate);
					Show(this.document.all.lblWillBuyDate);
					Show(this.document.all.dpWillBuyDate);
					
					Hidden(this.document.all.dpBuyDateImg);
					
					Hidden(this.document.all.dpSellDateImg);
					
					Show(this.document.all.dpWillBuyDateImg);

					Show(this.document.all.lblLimitDate);
					Show(this.document.all.dpLimitDate); 
					Show(this.document.all.dpLimitDateImg);
				}
				else
				{
					Show(this.document.all.lblPrice);
					Show(this.document.all.tbPrice);
					Show(this.document.all.lblBuyDate);
					Show(this.document.all.dpBuyDate);
					Show(this.document.all.lblSellDate);
					Show(this.document.all.dpSellDate);
					Hidden(this.document.all.lblWillBuyDate);
					Hidden(this.document.all.dpWillBuyDate);
					
					Show(this.document.all.dpBuyDateImg);
					Show(this.document.all.dpSellDateImg);
					
					Hidden(this.document.all.dpWillBuyDateImg);

					Hidden(this.document.all.lblLimitDate);
					Hidden(this.document.all.dpLimitDate); 
					Hidden(this.document.all.dpLimitDateImg);
				}
			}
			function setvisible2()
			{
				v=this.document.all.ddlType.value;
				if(v=="3")
				{
					Show(this.document.all.lblYearTime);
					Show(this.document.all.tbYearTime);
				}
				else
				{
					Hidden(this.document.all.lblYearTime);
					Hidden(this.document.all.tbYearTime);
				}
				if(v=="1")
				{
					Hidden(this.document.all.lblRate);
					Hidden(this.document.all.tbRate);
				}
				else
				{
					Show(this.document.all.lblRate);
					Show(this.document.all.tbRate);
				}
				if(v=="2" && this.document.all.rblID_0.checked)
				{
					Show(this.document.all.lblRetTerm);
					Show(this.document.all.tbRetTerm);
				}
				else
				{
					Hidden(this.document.all.lblRetTerm);
					Hidden(this.document.all.tbRetTerm);
				}
			}
			
			function window.onload()
			{
				setvisible1();
				setvisible2();
			}
			function IsEnabled(ctrl)
			{
				return (ctrl.disabled!=true);  // ctrl.style.disabled==false : modified by huhao, 2003/3/28
			}
		//-->
			</script>
			<script language="javascript" event="onclick" for="rblID">
		<!--
			setvisible1();
			setvisible2();
		//-->
			</script>
			<script language="javascript" event="onchange" for="ddlType">
		<!--
			setvisible2();
		//-->
			</script>
			<script language="javascript" event="onclick" for="btnCalc">
		<!--
			if(!CheckFN3(this.document.all.tbCost,"请在[债券票面面值]中输入正数！",false))
				return false;
			if(IsEnabled(this.document.all.tbYearTime))
				if(!CheckPN(this.document.all.tbYearTime,"请在[年付息次数]中输入正整数！",false))
					return false;
			if(IsEnabled(this.document.all.tbRetTerm))
				if(!CheckPN(this.document.all.tbRetTerm,"请在[债券偿还期限]中输入正整数！",false))
					return false;
			if(IsEnabled(this.document.all.tbRate))
				if(!CheckFN3(this.document.all.tbRate,"请在[债券票面年利率]中输入正数！",false))
					return false;
			if(!CheckEmpty(this.document.all.dpLimitDate,"请输入债券到期兑换日期！"))
				return false;
			if(IsEnabled(this.document.all.tbPrice))
				if(!CheckFN3(this.document.all.tbPrice,"请在[债券成本价格]中输入正数！",false))
					return false;
			if(IsEnabled(this.document.all.dpBuyDate))
				if(!CheckEmpty(this.document.all.dpBuyDate,"请输入债券购入日期！"))
					return false;
			if(IsEnabled(this.document.all.dpSellDate))
			{
				if(!CheckEmpty(this.document.all.dpSellDate,"请输入预计卖出日期！"))
					return false;
				if(!CheckDiffDate(this.document.all.dpBuyDate,this.document.all.dpSellDate,"债券购入日期不能晚于预计卖出日期！"))
					return false;
			}
			if(IsEnabled(this.document.all.dpWillBuyDate))
			{
				if(!CheckEmpty(this.document.all.dpWillBuyDate,"请输入预计购入日期！"))
					return false;
				if(!CheckDiffDate(this.document.all.dpWillBuyDate,this.document.all.dpLimitDate,"预计购入日期不能晚于债券到期兑换日期！"))
					return false;
			}
			Calc(document);
			return false;
		//-->
			</script>
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
 //cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
</NOSCRIPT>

<!-- START WRating v1.0 -->
<script type="text/javascript" src="http://msn.wrating.com/a1.js">
</script>
<!-- 
<script type="text/javascript">
var vjAcc="860010-0218010200";
var wrUrl="http://msn.wrating.com/";
vjTrack("");
 -->
</script>
<noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
<!-- END WRating v1.0 -->
		<form name="EduCalc" id="EduCalc">
			<!-- <div id="bg1"><img src="../Images/bg1.gif" class="moveimg"><img src="../Images/bg2.gif" width="250" height="126" class="moveimg"><img src="../Images/bg3.gif" class="moveimg"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="210"><img src="../Images/bg5.gif" width="475" height="210"><img src="../Images/bg6.gif" width="25" height="210"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div> -->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【债券买卖比较器】</div>
			<DIV id="FDiv2" class="FDiv2" Movable="1" style="WIDTH: 450px;">本计算器通过将债券投资收益与当前银行存款利率（扣除利息税影响）的比较，帮助确定非上市债券交易的合理价格。</DIV>
			<DIV id="FDiv3" class="FDiv3" style="WIDTH: 411px;" align="center">
				<table id="Table1" cellSpacing="0" borderColorDark="#e9f3f4" cellPadding="0" width="90%" borderColorLight="#336666" border="0">
					<tr>
						<td align="middle">
							<span id="lblCost" style="Z-INDEX: 101; LEFT: 46px; POSITION: absolute; TOP: 58px">债券票面面值(元)</span>
							<span id="lblType" style="Z-INDEX: 110; LEFT: 46px; POSITION: absolute; TOP: 33px">债券类型</span>
							<span id="lblYearTime" style="Z-INDEX: 102; LEFT: 46px; POSITION: absolute; TOP: 84px">年付息次数</span>
							<span id="lblRetTerm" style="Z-INDEX: 102; LEFT: 256px; POSITION: absolute; TOP: 84px">债券偿还期限(月)</span>
							<span id="lblRate" style="Z-INDEX: 103; LEFT: 46px; POSITION: absolute; TOP: 107px">债券票面年利率(%)</span>
							<span id="lblLimitDate" style="Z-INDEX: 104; LEFT: 46px; POSITION: absolute; TOP: 134px">债券到期兑换日期</span>
							<span id="lblSellDate" style="Z-INDEX: 105; LEFT: 46px; POSITION: absolute; TOP: 216px">预计卖出日期</span>
							<input tabindex="6" name="tbRate" type="text" id="tbRate" class="txtnum" style="width:126px;Z-INDEX: 106; LEFT: 162px; POSITION: absolute; TOP: 105px" />
							<input type="button" name="btnCalc" value=" 计算 " id="btnCalc" class="btn" style="Z-INDEX: 107; LEFT: 365px; POSITION: absolute; TOP: 27px" />
							<span id="lblPrice" style="Z-INDEX: 109; LEFT: 46px; POSITION: absolute; TOP: 159px">
											债券成本价格(元)</span><input tabindex="8" name="tbPrice" type="text" id="tbPrice" class="txtnum" style="width:126px;Z-INDEX: 111; LEFT: 162px; POSITION: absolute; TOP: 158px" />
							<span id="lblBuyDate" style="Z-INDEX: 112; LEFT: 46px; POSITION: absolute; TOP: 188px">债券购入日期</span>
							<table id="rblID" border="0" style="Z-INDEX: 113; LEFT: 161px; POSITION: absolute; TOP: 3px">
								<tr>
									<td><input tabindex="1" id="rblID_0" type="radio" name="rblID" value="1" /><label for="rblID_0">买方</label></td>
									<td><input tabindex="1" id="rblID_1" type="radio" name="rblID" value="2" checked="checked" /><label for="rblID_1">卖方</label></td>
								</tr>
							</table>
							<span id="lblID" style="Z-INDEX: 114; LEFT: 46px; POSITION: absolute; TOP: 11px">请选择交易身份</span>
							<span id="lblWillBuyDate" style="Z-INDEX: 115; LEFT: 46px; POSITION: absolute; TOP: 245px">预计购入日期</span>
							<input tabindex="3" name="tbCost" type="text" id="tbCost" class="txtnum" style="width:127px;Z-INDEX: 117; LEFT: 162px; POSITION: absolute; TOP: 55px" />
							<input tabindex="4" name="tbYearTime" type="text" id="tbYearTime" value="1" class="txtnum" style="width:42px;Z-INDEX: 118; LEFT: 162px; POSITION: absolute; TOP: 80px" />
							<input tabindex="5" name="tbRetTerm" type="text" id="tbRetTerm" value="12" class="txtnum" style="width:42px;Z-INDEX: 118; LEFT: 374px; POSITION: absolute; TOP: 80px" />
							<input tabindex="7" name="dpLimitDate" class="txt" type="text" value="2001-1-1" id="dpLimitDate" size="13" onblur='ChkCZDate(dpLimitDate)' style="width:100px;Z-INDEX: 120; LEFT: 162px; POSITION: absolute; TOP: 130px">
							<IMG id="dpLimitDateImg" SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(dpLimitDate)' style="Z-INDEX: 120; LEFT: 265px; POSITION: absolute; TOP: 130px">
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
							<input tabindex="9" name="dpBuyDate" class="txt" type="text" value="2001-1-1" id="dpBuyDate" size="13" onblur='ChkCZDate(dpBuyDate)' style="width:100px;Z-INDEX: 121; LEFT: 162px; POSITION: absolute; TOP: 182px">
							<IMG id="dpBuyDateImg" SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(dpBuyDate)' style="Z-INDEX: 121; LEFT: 265px; POSITION: absolute; TOP: 182px">
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
							<input tabindex="10" name="dpSellDate" class="txt" type="text" value="2001-1-1" id="dpSellDate" size="13" onblur='ChkCZDate(dpSellDate)' style="width:100px;Z-INDEX: 122; LEFT: 162px; POSITION: absolute; TOP: 211px">
							<IMG id="dpSellDateImg" SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(dpSellDate)' style="Z-INDEX: 122; LEFT: 265px; POSITION: absolute; TOP: 211px">
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
							<input tabindex="11" name="dpWillBuyDate" class="txt" type="text" value="2001-1-1" id="dpWillBuyDate" size="13" onblur='ChkCZDate(dpWillBuyDate)' style="width:100px;Z-INDEX: 123; LEFT: 162px; POSITION: absolute; TOP: 241px">
							<IMG id="dpWillBuyDateImg" SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(dpWillBuyDate)' style="Z-INDEX: 123; LEFT: 265px; POSITION: absolute; TOP: 241px">
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
							<!--<DIV style="Z-INDEX: 119; LEFT: 26px; POSITION: absolute; TOP: 299px" ms_positioning="text2D">计算得出：</DIV>-->
							<DIV style="DISPLAY: inline; Z-INDEX: 120; LEFT: 26px; WIDTH: 371px; POSITION: absolute; TOP: 265px; HEIGHT: 38px" ms_positioning="FlowLayout">
								<table style="WIDTH: 359px; HEIGHT: 13px" height="13" cellSpacing="1" cellPadding="1" width="359" border="0" ID="Table2">
									<tr height="100%">
										<td align="left"><span id="lblInfo"><FONT face="宋体"></FONT></span></td>
									</tr>
								</table>
							</DIV>
							<select tabindex="2" name="ddlType" id="ddlType" style="width:175px;Z-INDEX: 121; LEFT: 162px; POSITION: absolute; TOP: 28px">
								<option selected="selected" value="1">贴现债券</option>
								<option value="2">到期一次还本付息债券</option>
								<option value="3">固定利率债券和浮动利率债券</option>
							</select>
						</td>
					</tr>
				</table>
			</DIV>
		</form>
	</body>
</HTML>
<script language="javascript">
  document.EduCalc.dpBuyDate.value = datetostring(new Date());
  document.EduCalc.dpLimitDate.value = datetostring(new Date());
  document.EduCalc.dpSellDate.value = datetostring(new Date());
  document.EduCalc.dpWillBuyDate.value = datetostring(new Date());
</script>
