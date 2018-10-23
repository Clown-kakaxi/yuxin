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
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcBondBuy.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>
				
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
			<script language="javascript">
	  function calcu()
	  {
	    if (!CheckFN3(document.all.edCost,"请在债券面额输入正数",false))
	   	return false;	       		    
	if (!CheckFN3(document.all.edPrice,"请在认购价格输入正数",false))
	   	return false;
	if (!CheckPN(document.all.edYear,"请在债券期限输入正整数",false))
	   	return false;	   			
	if (document.all.cbType_1.checked)   		
		if (!CheckFN(document.all.edRate,"请在票面利率输入非负数"))
	   		return false;	       		    
	if (document.all.cbType_2.checked)  
		{ 		
		if (!CheckFN(document.all.edRate,"请在票面利率输入非负数"))
	   		return false;	       		    
		if(!CheckEmpty(document.all.edDate,"认购日期格式不正确！"))
			return false;
		if (!CheckPN(document.all.edFreq,"请在利息支付频率输入正整数",false))
	   		return false;	       		    	
	  }
	  
	    return true;
}	  
function Visibleselect()
{
	  //贴现债券
if (document.all.cbType_0.checked==true)
	{
		document.all.edCost.style.display="block";
		document.all.edRate.style.display="none";
		document.all.edPrice.style.display="block";
		document.all.edYear.style.display="block";
		document.all.edDate.style.display="none";					
		document.all.edFreq.style.display="none";
		document.all.Label1.style.display="block";
		document.all.Label2.style.display="block";
		document.all.Label3.style.display="block";
		document.all.Label4.style.display="none";
		document.all.Label5.style.display="none";
		document.all.Label6.style.display="none";
	//	document.all.img1.style.display="none";
	 } 
//到期一次还本付息债券
if (document.all.cbType_1.checked==true)
	 {
		document.all.edCost.style.display="block"
		document.all.edRate.style.display="block";
		document.all.edPrice.style.display="block";
		document.all.edYear.style.display="block";
		document.all.edDate.style.display="none";					
		document.all.edFreq.style.display="none";
		document.all.Label1.style.display="block";
		document.all.Label2.style.display="block";
		document.all.Label3.style.display="block";
		document.all.Label4.style.display="block";
		document.all.Label5.style.display="none";
		document.all.Label6.style.display="none";
	//	document.all.img1.style.display="none";
    }
//固定利率和浮动利率
if (document.all.cbType_2.checked==true)
	{
		document.all.edCost.style.display="block";
		document.all.edRate.style.display="block";
		document.all.edPrice.style.display="block";
		document.all.edYear.style.display="block";
		document.all.edDate.style.display="block";					
		document.all.edFreq.style.display="block";
		document.all.Label1.style.display="block";
		document.all.Label2.style.display="block";
		document.all.Label3.style.display="block";
		document.all.Label4.style.display="block";
		document.all.Label5.style.display="block";
		document.all.Label6.style.display="block";
	//	document.all.img1.style.display="block";
    }
}
			</script>
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
 //var cImage; 
 //cImage = new Image; 
 //cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<!--  <img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
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
<!--  
<img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/>
-->
</noscript>
<!-- END WRating v1.0 -->
	<!--  	<div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
		<div id="bg2"><img src="../Images/bg4.gif" width="25" height="200"><img src="../Images/bg5.gif" width="475" height="200"><img src="../Images/bg6.gif" width="25" height="200"></div>
		<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>
	--> 
		<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【债券认购收益率计算器】</div>
		<div id="FDiv2" class="FDiv2" Movable="1" style="width:450">&nbsp;从债券新发行就买进，持有到偿还期到期还本付息，这期间的收益率就为认购收益率。</div>
	
		<div id="FDiv3" class="FDiv3" style="WIDTH: 400" align="center">
	
			<form name="BondBuyCalc" id="BondBuyCalc" onsubmit="return false;">
				<table width="493" border="0" cellspacing="0" cellpadding="0" bordercolordark="#e9f3f4" bordercolorlight="#336666" ID="Table2">
					<tr>
						<td align="middle">
							<table id="cbType" class="txt" border="0" style="height:62px;width:278px;Z-INDEX: 101; LEFT: 26px; POSITION: absolute; TOP: 3px">
								<tr>
									<td><input id="cbType_0" type="radio" name="cbType" value="0" onClick="Visibleselect()" /><label for="cbType_0">贴现债券（认购价格大于债券面额则无收益）</label></td>
								</tr>
								<tr>
									<td><input id="cbType_1" type="radio" name="cbType" value="1" onClick="Visibleselect()" /><label for="cbType_1">到期一次还本付息债券</label></td>
								</tr>
								<tr>
									<td><input id="cbType_2" type="radio" name="cbType" value="2" checked="checked" onClick="Visibleselect()" /><label for="cbType_2">固定利率附息债券和浮动利率债券</label></td>
								</tr>
							</table>
							<span id="Label1" style="Z-INDEX: 116; LEFT: 37px; POSITION: absolute; TOP: 84px ">债券面额(元)</span>
							<span id="Label2" style="Z-INDEX: 104; LEFT: 37px; POSITION: absolute; TOP: 112px ">认购价格(元)</span>
							<span id="Label3" style="Z-INDEX: 112; LEFT: 37px; POSITION: absolute; TOP: 139px ">债券期限(年)</span>
							<span id="Label4" style="Z-INDEX: 106; LEFT: 37px; POSITION: absolute; TOP: 167px ">票面利率(%)</span>
							<span id="Label5" style="Z-INDEX: 110; LEFT: 37px; POSITION: absolute; TOP: 194px ">认购日期</span>
							<span id="Label6" style="Z-INDEX: 108; LEFT: 37px; POSITION: absolute; TOP: 223px ">利息支付频率(次/年)</span>
							<input name="edCost" type="text" id="edCost" tabindex="1" class="txtnum" style="width:124px;Z-INDEX: 111; LEFT: 178px; POSITION: absolute; TOP: 81px " />
							<input name="edPrice" type="text" id="edPrice" tabindex="2" class="txtnum" style="width:124px;Z-INDEX: 102; LEFT: 178px; POSITION: absolute; TOP: 108px " />
							<input name="edYear" type="text" id="edYear" tabindex="3" class="txtnum" style="width:124px;Z-INDEX: 103; LEFT: 178px; POSITION: absolute; TOP: 136px " />
							<input name="edRate" type="text" id="edRate" tabindex="4" class="txtnum" style="width:124px;Z-INDEX: 105; LEFT: 178px; POSITION: absolute; TOP: 165px " />
							<input name="edFreq" type="text" id="edFreq" tabindex="6" class="txtnum" style="width:124px;Z-INDEX: 107; LEFT: 178px; POSITION: absolute; TOP: 220px " />
							<input type="submit" name="btnCalc" value=" 计算 " onclick="javascript:if(calcu()) CalcBondBuy(document);" id="btnCalc" tabindex="8" class="btn" style="Z-INDEX: 109; LEFT: 349px; POSITION: absolute; TOP: 14px" />
							<span id="Label7" style="Z-INDEX: 113; LEFT: 37px; POSITION: absolute; TOP: 266px">该债券的认购收益率为(%)</span>
							<input tabindex="5" name="edDate" class="txt" type="text" value="2001-1-1" id="edDate" size="13" onblur='ChkCZDate(edDate)' style="width:100px;Z-INDEX: 115; LEFT: 178px; POSITION: absolute; TOP: 193px ">
				<!-- 			<IMG id="img1" SRC="../Images/Calendar1.gif" align="AbsMiddle" onclick='javascript:Cal_dropdown(edDate)' style="cursor:hand; Z-INDEX: 115; LEFT: 278px; POSITION: absolute; TOP: 193px ">
						 -->
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
							<HR style="Z-INDEX: 118; LEFT: 10px; WIDTH: 95.62%; POSITION: absolute; TOP: 251px; HEIGHT: 1px" width="95.62%" SIZE="1">
							<input name="lbResult" type="text" disabled="false" value="计算得出" id="lbResult" tabindex="7" class="txtd" style="width:122px;Z-INDEX: 119; LEFT: 179px; POSITION: absolute; TOP: 262px" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
	<script language="javascript">
	//document.all.edDate.value=datetostring(new Date());
    ////GetCalc_Close();
	</script>
</HTML>
