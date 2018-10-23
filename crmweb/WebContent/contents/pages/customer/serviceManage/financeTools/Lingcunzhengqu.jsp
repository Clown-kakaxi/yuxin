<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>ECC IDE Jsp file</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<!-- ECC IDE required comment, please don't delete it! -->
<!-- toBeLayoutContent="true" mvcFile="${mvcfile}" -->    

		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WinControl.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calendar.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calculator.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WBselect.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/GetRate.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Components.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcZCLQ.js" language="JavaScript"> </SCRIPT>
		
								
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">

			<script language="javascript">
		<!--
			function getRate(month)
			{
				for(var i=0;i<rate.length;i++)
				{
					if(month==parseInt(minterm[i]))
						return rate[i];
				}
				return 0;
			}
			function getTerm(month)
			{
				for(var i=0;i<rate.length;i++)
				{
					if(Math.abs(month-parseInt(minterm[i]))<1)
						return i;
				}
				return -1;			}
			function setRate()
			{
				this.document.all.tbRate.value = Round(100*getRate(parseInt(this.document.all.cbMonth.value)),4);
			}
		//-->
			</script>
			<script language="javascript" event="onclick" for="rblItem">
		
			if(this.document.all.rblItem_0.checked)
			{
				/*tbEverySum.className="txtd";
				tbFirstSum.className="txtnum";
				tbEverySum.value="计算得出";
				if(tbFirstSum.value=="计算得出")
					tbFirstSum.value="";
				if(cbMonth.value=="计算得出")
					cbMonth.value=cbMonth.options[0].value;
				tbFirstSum.disabled=false;
				tbEverySum.disabled=true;
				cbMonth.disabled=false;*/
				tbEverySum.disabled=true;
				tbEverySum.className="txtd";
			    
				tbEverySum.value="计算得出";
				lblFirstSum.innerText= "初始存入金额";
				lblEverySum.innerText="每次支取金额";
				hr1.style.top=179;
			}
			else
			{
				if(this.document.all.rblItem_1.checked)
				{
					/*tbEverySum.className="txtnum";
					tbFirstSum.className="txtd";
					tbFirstSum.value="计算得出";
					if(tbEverySum.value=="计算得出")
						tbEverySum.value="";
					if(cbMonth.value=="计算得出")
						cbMonth.value=cbMonth.options[0].text;
					tbFirstSum.disabled=true;
					tbEverySum.disabled=false;
					cbMonth.disabled=false;*/
					tbEverySum.disabled=true;
					tbEverySum.value="计算得出";
					tbEverySum.className="txtd";
					lblFirstSum.innerText= "每次支取金额";
				    lblEverySum.innerText="初始存入金额";
				    hr1.style.top=179;
				}
				else
				{
					/*tbEverySum.className="txtnum";
					tbFirstSum.className="txtnum";
					cbMonth.text="计算得出";
					if(tbEverySum.value=="计算得出")
						tbEverySum.value="";
					if(tbFirstSum.value=="计算得出")
						tbFirstSum.value="";
					tbFirstSum.disabled=false;
					tbEverySum.disabled=false;
					cbMonth.disabled=true;*/
					tbEverySum.disabled=false;
					tbEverySum.value="";
					tbEverySum.className="txtnum";
					lblFirstSum.innerText= "初始存入金额";
				    lblEverySum.innerText="每次支取金额";
				    hr1.style.top=218;
				}
			}
		//-->
			</script>
			<script language="javascript" event="onclick" for="btnCalc">
		<!--
		    if(this.document.all.rblItem_0.checked)
		    if(!CheckFN3(this.document.all.tbFirstSum,"请在[初始存入金额]中输入正数金额！",false))
					return false;
			if(this.document.all.rblItem_1.checked)
		    if(!CheckFN3(this.document.all.tbFirstSum,"请在[每次存入金额]中输入正数金额！",false))
					return false;
			if(this.document.all.rblItem_2.checked)
			{
		    if(!CheckFN3(this.document.all.tbFirstSum,"请在[初始存入金额]中输入正数金额！",false))
					return false;	
			if(!CheckFN3(this.document.all.tbEverySum,"请在[每次支取金额]中输入正数金额！",false))
					return false;			
			}
		    
			if(!CheckEmpty(this.document.all.beginDateID,"请输入初始存入日期！"))
				return false;
			if(Cal_strtodate("1999-11-1")>Cal_strtodate(this.document.all.beginDateID.value))
			{
				DispMessage(this.document.all.beginDateID, "初始存入日期不得小于1999年11月1日！");
				return false;
			}
			if(!CheckFN3(this.document.all.tbRate,"请在[年利率]中输入正数！",false,null,4))
				return false;
			
			Calc(document);
			return false;
		//-->
			</script>
			<script id="clientEventHandlersJS" language="javascript">
<!--

function btnCalc_onclick() {

}

//-->
			</script>
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#E9F3F4" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
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
<noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
<!-- END WRating v1.0 -->
		<form name="EduCalc" id="EduCalc" onsubmit="return false;">
			<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="200"><img src="../Images/bg5.gif" width="475" height="200"><img src="../Images/bg6.gif" width="25" height="200"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div> -->
			<div id="FDiv1" class="FDiv1" Movable="1" style="width:300;color:black">【整存零取计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:480">整存零取存款指客户须一次存入，然后按期定额支取的储蓄品种。本计算器可依据一定要求计算出整存零取存款的每次支取金额和所得利息金额（已扣除利息税），并可反向计算整存零取的初始存入金额和储蓄存期。
			</div>
			<div id="FDiv3" class="FDiv3" style="width:480" align="center">
				<table cellSpacing="2" cellPadding="0" width="96%" border="0">
					<tr>
						<td align="middle">
							<span id="lblDate" style="Z-INDEX: 100; LEFT: 24px; POSITION: absolute; TOP: 70px">初始存入日期</span>
							<span id="lblFirstSum" style="Z-INDEX: 112; LEFT: 24px; POSITION: absolute; TOP: 39px">初始存入金额(元)</span>
							<span id="lblMonth" style="Z-INDEX: 101; LEFT: 24px; POSITION: absolute; TOP: 101px">储蓄存期</span>
							<span id="lblRate" style="Z-INDEX: 102; LEFT: 24px; POSITION: absolute; TOP: 129px">年利率(%)</span>
							<span id="lblFreq" style="Z-INDEX: 104; LEFT: 24px; POSITION: absolute; TOP: 159px">支取频度</span>
							<hr id="hr1" size="1" style="Z-INDEX: 104; LEFT: 24px; width:400;POSITION: absolute; TOP: 183px">
							<span id="lblTaxSum" style="Z-INDEX: 105; LEFT: 24px; POSITION: absolute; TOP: 263px">扣除利息税金额(元)</span>
							<select tabindex="4" name="cbMonth" id="cbMonth" tabindex="4" onchange="setRate()" style="width:126px;Z-INDEX: 106; LEFT: 152px; POSITION: absolute; TOP: 96px">
								<option value="12">一年</option>
								<option value="36">三年</option>
								<option value="60">五年</option>
							</select>
							<input tabindex="5" name="tbRate" type="text" id="tbRate" tabindex="5" class="txtnum" style="width:126px;Z-INDEX: 107; LEFT: 152px; POSITION: absolute; TOP: 127px" />
							<input type="submit" name="btnCalc" value=" 计算 " id="btnCalc" onclick="return btnCalc_onclick()" tabindex="10" class="btn" style="Z-INDEX: 108; LEFT: 357px; POSITION: absolute; TOP: 35px" />
							<input tabindex="3" name="beginDate" type="text" value="2002-2-21" id="beginDateID" class="txt" size="13" onblur='ChkCZDate(beginDateID);' style="width:100;Z-INDEX: 100; LEFT: 152px; POSITION: absolute; TOP: 67px">
							<IMG SRC="./images/Calendar1.gif" align="absMiddle" onclick='javascript:Cal_dropdown(beginDateID)' style="cursor:hand; left:255;TOP: 67px;position:absolute" height="17" width="22">
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}

 }</script>
							<input tabindex="9" name="tbTaxSum" type="text" value="计算得出" disabled="true" id="tbTaxSum" tabindex="9" class="txtd" style="width:126px;Z-INDEX: 111; LEFT: 153px; POSITION: absolute; TOP: 257px" />
							<input tabindex="2" name="tbFirstSum" type="text" id="tbFirstSum" tabindex="2" class="txtnum" style="width:125px;Z-INDEX: 113; LEFT: 152px; POSITION: absolute; TOP: 37px" />
							<table id="rblFreq" border="0" style="Z-INDEX: 114; LEFT: 144px; POSITION: absolute; TOP: 153px">
								<tr>
									<td><input tabindex="6" id="rblFreq_0" type="radio" name="rblFreq" value="1" checked="checked" tabindex="6" />
										<label for="rblFreq_0">每月</label></td>
									<td><input tabindex="6" id="rblFreq_1" type="radio" name="rblFreq" value="3" tabindex="6" />
										<label for="rblFreq_1">每季</label></td>
									<td><input tabindex="6" id="rblFreq_2" type="radio" name="rblFreq" value="6" tabindex="6" />
										<label for="rblFreq_2">每半年</label></td>
								</tr>
							</table>
							<span id="lblEverySum" style="Z-INDEX: 115; LEFT: 24px; POSITION: absolute; TOP: 204px">每次支取金额(元)</span>
							<input tabindex="7" name="tbEverySum" type="text" value="计算得出" disabled="true" id="tbEverySum" tabindex="7" class="txtd" style="width:126px;Z-INDEX: 116; LEFT: 152px; POSITION: absolute; TOP: 197px" />
							<input tabindex="8" name="tbInSum" type="text" value="计算得出" disabled="true" id="tbInSum" tabindex="8" class="txtd" style="width:126px;Z-INDEX: 117; LEFT: 152px; POSITION: absolute; TOP: 227px" />
							<span id="lbInSum" style="Z-INDEX: 118; LEFT: 24px; POSITION: absolute; TOP: 234px">所得利息金额(元)</span>
							<table id="rblItem" border="0" style="width:310px;Z-INDEX: 119; LEFT: 143px; POSITION: absolute; TOP: 0px">
								<tr>
									<td><input tabindex="1" id="rblItem_0" type="radio" name="rblItem" value="1" checked="checked" tabindex="1" />
										<label for="rblItem_0">每次支取金额</label></td>
									<td><input tabindex="1" id="rblItem_1" type="radio" name="rblItem" value="2" tabindex="1" />
										<label for="rblItem_1">初始存入金额</label></td>
									<td><input tabindex="1" id="rblItem_2" type="radio" name="rblItem" value="3" tabindex="1" />
										<label for="rblItem_2">储蓄存期</label></td>
								</tr>
							</table>
							<span id="lblItem" style="Z-INDEX: 120; LEFT: 24px; POSITION: absolute; TOP: 8px">计算项目</span>
						</td>
					</tr>
				</table>
			</div>
			<script language="javascript">
<!--
	var minterm;
	var rate1;
	var rate2;
	var rate3;
	var rate;
	function init()
	{
		minterm =  new Array(12,36,60,-1);
		rate1 = GetRMBSaveRatio(5,12,window.xmlRMBSaveRate.XMLDocument)/100;
		rate2 = GetRMBSaveRatio(5,36,window.xmlRMBSaveRate.XMLDocument)/100;
		rate3 = GetRMBSaveRatio(5,60,window.xmlRMBSaveRate.XMLDocument)/100;
		rate =  new Array(rate1,rate2,rate3,-1);
		setRate();
	}
		// -->
			</script>
		</form>
		<xml id="xmlRMBSaveRate" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/RMBSaveRate.xml" ondatasetcomplete="init()"></xml>
	</body>
</HTML>
<script language="javascript">
document.EduCalc.beginDateID.value = datetostring(new Date());
//GetCalc_Close();

</script>
