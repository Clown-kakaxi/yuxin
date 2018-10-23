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
			<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcCompPartSum.js" language="JavaScript"> </SCRIPT>
	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
			<script language="javascript">
          function gettoday()
          {
            return new Date();
          }
	  function getrate(id,perior)
	  {
	    document.CompPartSumCalc.tbFullRate.value = GetRMBSaveRatio(2,perior,window.xmlRMBSaveRate.XMLDocument);
	  }
	  
	  function checkinput()
	  {
	            if (!CheckFN3(CompPartSumCalc.tbInitSaveSum,"请在初始存入金额输入正数",false))
					 return false;			
					 	
				if (!CheckFN3(CompPartSumCalc.tbFullRate,"请在整存整取年利率输入非负数",false))				 
						return false;											
				if (!CheckFN3(CompPartSumCalc.tbDepositRate,"请在活期存款年利率输入非负数",false))
						return false;	
				if (!CheckFN(CompPartSumCalc.tbAdvDrawSum,"请在部分提前支取金额输入正数",false))
					 return false;										 
				if (new Number(CompPartSumCalc.tbAdvDrawSum.value) >= new Number(CompPartSumCalc.tbInitSaveSum.value))
				{
					DispMessage(CompPartSumCalc.tbAdvDrawSum, "部分提前支取金额要小于初始存入金额");
					return false;
				}
				if (!CheckEmpty(CompPartSumCalc.beginDateID,"无效的初始存入日期"))
					return false;	
				if (!CheckEmpty(CompPartSumCalc.periorDateID,"无效的提前支取日期"))
					return false;	
				var SaveDate=new Date();
				SaveDate.setTime(Cal_strtodate(CompPartSumCalc.beginDateID.value));				
				var AdvDrawDate=new Date();
				AdvDrawDate.setTime(Cal_strtodate(CompPartSumCalc.periorDateID.value));	
				var Date1999 = new Date();
				Date1999.setTime(Cal_strtodate("1999-11-01"));
				if (AdvDrawDate<=Math.max(SaveDate,Date1999))
					{
						DispMessage(CompPartSumCalc.periorDateID,"提前支取日期要大于初始存入日期和1999年11月1日的较大值");
						return false;
					}								
				maxyear=SaveDate.getFullYear();
				maxmonth=SaveDate.getMonth();
				
				if (CompPartSumCalc.tbSaveTime.value >=1) //表示是整年
					maxyear=maxyear+parseInt(CompPartSumCalc.tbSaveTime.value);
				else
				{
					maxmonth=maxmonth+parseInt(CompPartSumCalc.tbSaveTime.value);
					if (maxmonth>12)
					 {
						maxyear=maxyear+1;
						maxmonth=maxmonth-12;
					 }
				}			
				
				SaveDate.setYear(maxyear);
				SaveDate.setMonth(maxmonth);
				if (AdvDrawDate>=SaveDate)
					{	
						DispMessage(CompPartSumCalc.periorDateID,"提前支取日期要小于初始存入日期+储蓄存期");
						return false;						
					}
				return true;
	  }
	  function calcu()
	  {
	    if (checkinput()==false) return false;

	    //var cal = new ActiveXObject("FinanceCalculator.ICalcComparePartSumDraw");
	    CalCompPartSum(document)
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
<!-- <img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
 -->
</NOSCRIPT>

<!-- START WRating v1.0 -->
<script type="text/javascript" src="http://msn.wrating.com/a1.js">
</script>
<!-- <script type="text/javascript">
var vjAcc="860010-0218010200";
var wrUrl="http://msn.wrating.com/";
vjTrack("");
 -->
</script>
<!--  <noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
-->
<!-- END WRating v1.0 -->
		<form name="CompPartSumCalc" ID="Form1">
		 
	<!--  		<div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="190"><img src="../Images/bg5.gif" width="475" height="190"><img src="../Images/bg6.gif" width="25" height="190"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>
			-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【部分提支与全额提支比较计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:450">如果客户拥有整存整取存单，当其急需资金时，可以全额提前支取存款，也可以部分提前支取存款。本计算器可以根据不同的条件，对这两种方式的结果进行比较。</div>
		
		
			<DIV id="FDiv3" class="FDiv3" style="WIDTH: 450px; " align="center">
				<TABLE id="Table4" cellSpacing="1" cellPadding="1" width="380" border="0" align="center">
					<TR>
						<TD width="121" height="28">初始存入金额</TD>
						<TD width="189"><input name="tbInitSaveSum" type="text" value="0" style="width:120px" id="tbInitSaveSum" class="txtnum">
							&nbsp;元</TD>
						<TD width="60"><FONT face="宋体"> <input type="button" name="btnExecute" value=" 计算 " id="btnExecute" tabindex="9" class="btn" onclick="return calcu()">
							</FONT>
						</TD>
					</TR>
					<TR>
						<TD width="121" height="28">初始存入日期</TD>
						<TD width="189"><input name="beginDate" class="txt" type="text" value="2001-1-1" id="beginDateID" style="width:92px" onblur='ChkCZDate(beginDateID);'>
							<IMG SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(beginDateID)'>
						</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD width="121" height="28">储蓄存期</TD>
						<TD width="189" height="28"><select name="tbSaveTime" id="tbSaveTime" style="width:120px" onchange="getrate(2,tbSaveTime.value)">
								<option value="3">三个月</option>
								<OPTION VALUE="6">半年</OPTION>
								<OPTION SELECTED="SELECTED" VALUE="12">一年</OPTION>
								<OPTION VALUE="24">二年</OPTION>
								<OPTION VALUE="36">三年</OPTION>
								<OPTION VALUE="60">五年</OPTION>
							</select></TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD width="121" height="28">整存整取年利率</TD>
						<TD width="189"><input name="tbFullRate" type="text" value="1.98" style="width:120px" id="tbFullRate" class="txtnum">
							&nbsp;%</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD width="121" height="28">活期存款年利率</TD>
						<TD width="189"><input name="tbDepositRate" type="text" value="0.72" style="width:120px" id="tbDepositRate" class="txtnum">
							&nbsp;%</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD width="121" height="28">部分提前支取金额</TD>
						<TD width="189"><input name="tbAdvDrawSum" type="text" value="0" id="tbAdvDrawSum" style="width:120px" class="txtnum">
							&nbsp;元</TD>
						<TD></TD>
					</TR>
					<TR>
						<TD width="121" height="28">提前支取日期</TD>
						<TD width="189"><input name='periorDate' class="txt" type="text" id='periorDateID' value='2002-12-27' tabIndex="6" style="width:92px" onblur='ChkCZDate(periorDateID);'>
							<IMG SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(periorDateID)'>
						</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
				</TABLE>
				<table border="0" width="94%" align="center">
					<TR>
						<TD height="35">
							<HR width="100%" SIZE="1">
						</TD>
					</TR>
					<TR>
						<TD>部分提前要比全额提前支取减少利息损失 <input name="tbLoseSum" type="text" value="计算得出" disabled="true" id="tbLoseSum" tabindex="7" class="txtd">
							&nbsp;元
						</TD>
					</TR>
				</table>
			</DIV>
		</form>
		<xml id="xmlRMBSaveRate" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/RMBSaveRate.xml" ondatasetcomplete="LoadInit()"></xml>
	</body>
</HTML>
<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }
</script>
<script language="javascript">
function LoadInit()
{
  getrate(2,CompPartSumCalc.tbSaveTime.options[CompPartSumCalc.tbSaveTime.selectedIndex].value);
  document.CompPartSumCalc.beginDateID.value=datetostring(new Date());
  document.CompPartSumCalc.periorDateID.value=datetostring(new Date());
}
  //GetCalc_Close();
</script>
