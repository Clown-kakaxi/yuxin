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
<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcBondERate.js" language="JavaScript"> </SCRIPT>
<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>

	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
 //var cImage; 
 //cImage = new Image; 
 //cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
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
 <!-- <noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
  -->
<!-- END WRating v1.0 -->
		<form name="BondERateCalc" id="BondERateCalc">
		  
	<!-- 		<div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="330" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="270"><img src="../Images/bg5.gif" width="555" height="270"><img src="../Images/bg6.gif" width="25" height="270"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="555"><img src="../Images/bg9.gif"></div>
		 -->
			<div id="FDiv1" class="FDiv1" style="color:black">【债券到期收益率计算器】</div>
			<DIV id="FDiv2" class="FDiv2" style="WIDTH:550px; ">&nbsp;到期收益率是指投资者在二级市场上买入已经发行的债券并持有到期满为止的这个期限内的年平均收益率。</DIV>
		
		
			<DIV id="FDiv3" class="FDiv3" style="WIDTH:556px; " align="center">
				
				
				<TABLE cellSpacing="0" borderColorDark="#e9f3f4" cellPadding="0" width="90%" borderColorLight="#336666" border="0">
					<TR>
						<TD align="middle">
							<DIV id="Layer1" style="Z-INDEX:103;LEFT:23px;WIDTH:500px;POSITION:absolute;TOP:96px;HEIGHT:223px;VISIBILITY:visible;" ms_positioning="GridLayout"><div id="Bond11_Panel1" style="height:259px;width:459px;">
									<span id="Bond11_Label1" style="Z-INDEX: 101; LEFT: 14px; POSITION: absolute; TOP: 20px">
														债券单位成本(元)</span>
									<input name="Bond11:edUnitCost" type="text" id="Bond11_edUnitCost" tabindex="10" class="txtnum" style="width:104px;Z-INDEX: 113; LEFT: 115px; POSITION: absolute; TOP: 17px" />
									<span id="Bond11_Label4" style="Z-INDEX: 104; LEFT: 234px; POSITION: absolute; TOP: 20px">
														债券单位面值(元)</span>
									<input name="Bond11:edUnitPrice" type="text" id="Bond11_edUnitPrice" tabindex="11" class="txtnum" style="width:104px;Z-INDEX: 120; LEFT: 335px; POSITION: absolute; TOP: 17px" />
									<span id="Bond11_Label2" style="Z-INDEX: 102; LEFT: 14px; POSITION: absolute; TOP: 46px">
														债券购买交割日</span>
									<input tabindex="12" name="Bond11_edBuyDate" class="txt" type="text" value="2001-1-1" id="Bond11_edBuyDate" size="13" onblur='ChkCZDate(Bond11_edBuyDate)' style="width:80px;Z-INDEX: 105; LEFT: 115px; POSITION: absolute; TOP: 43px">
							<!-- 		<IMG SRC="../Images/Calendar1.gif" align="AbsMiddle" onclick="javascript:Cal_dropdown(Bond11_edBuyDate)" style="cursor: hand; Z-INDEX: 105; LEFT: 198px; POSITION: absolute; TOP: 43px">
								 -->
									<span id="Bond11_Label3" style="Z-INDEX: 103; LEFT: 234px; POSITION: absolute; TOP: 46px">
														债券到期兑付日</span>
									<input tabindex="13" name="Bond11_edSellDate" class="txt" type="text" value="2001-1-1" id="Bond11_edSellDate" onblur='ChkCZDate(Bond11_edSellDate)' style="width:80px;Z-INDEX: 115; LEFT: 335px; POSITION: absolute; TOP: 43px">
								<!--  -->	<IMG SRC="images/Calendar1.gif" align="AbsMiddle" onclick="javascript:Cal_dropdown(Bond11_edSellDate)" style="cursor: hand; Z-INDEX: 115; LEFT: 418px; POSITION: absolute; TOP: 43px">
									<span id="Bond11_Label5" style="Z-INDEX: 105; LEFT: 14px; POSITION: absolute; TOP: 80px">
														债券利息支付方式</span>
									<span style="Z-INDEX: 106; LEFT: 40px; POSITION: absolute; TOP: 110px">
										<input id="Bond11_rb1" type="radio" name="Bond11:Method" value="rb1" checked="checked" tabindex="14" /><label for="Bond11_rb1">贴现（在单位成本小于单位面值时才有收益）</label>
									</span>
									<span style="Z-INDEX: 107; LEFT: 40px; POSITION: absolute; TOP: 132px">
										<input id="Bond11_rb2" type="radio" name="Bond11:Method" value="rb2" tabindex="14" /><label for="Bond11_rb2">一次还本付息</label>
									</span>
									<span style="Z-INDEX: 108; LEFT: 40px; POSITION: absolute; TOP: 172px">
										<input id="Bond11_rb3" type="radio" name="Bond11:Method" value="rb3" tabindex="14" /><label for="Bond11_rb3">定期支付</label>
									</span>
									<span id="Bond11_Label6" style="Z-INDEX: 109; LEFT: 62px; POSITION: absolute; TOP: 154px">
														债券票面年利率(%)</span>
									<span id="Bond11_Label7" style="Z-INDEX: 110; LEFT: 232px; POSITION: absolute; TOP: 155px">
														债券偿还期限(年)</span>
									<span id="Bond11_Label8" style="Z-INDEX: 111; LEFT: 62px; POSITION: absolute; TOP: 196px">
														债券票面年利率(%)</span>
									<span id="Bond11_Label9" style="Z-INDEX: 112; LEFT: 232px; POSITION: absolute; TOP: 196px">
														债券利息支付频率(次/年)</span>
									<input name="Bond11:edRate1" type="text" id="Bond11_edRate1" tabindex="21" class="txtnum" style="width:54px;Z-INDEX: 116; LEFT: 168px; POSITION: absolute; TOP: 152px" />
									<input name="Bond11:edYear" type="text" id="Bond11_edYear" tabindex="22" class="txtnum" style="width:54px;Z-INDEX: 117; LEFT: 378px; POSITION: absolute; TOP: 152px" />
									<input name="Bond11:edRate2" type="text" id="Bond11_edRate2" tabindex="23" class="txtnum" style="width:54px;Z-INDEX: 118; LEFT: 168px; POSITION: absolute; TOP: 193px" />
									<input name="Bond11:edFreq" type="text" id="Bond11_edFreq" tabindex="24" class="txtnum" style="width:54px;Z-INDEX: 119; LEFT: 378px; POSITION: absolute; TOP: 193px" />
								</div>
								<!--  
								<script language="javascript" src="../Script/CheckDataFunction.js"></script>
								<script language="jscript" src="../Script/Calendar.js"></script>
								
								-->
							</DIV>
							<DIV id="Layer2" style="Z-INDEX:104;LEFT:31px;WIDTH:500px;POSITION:absolute;TOP:103px;HEIGHT:223px;VISIBILITY:hidden;" ms_positioning="GridLayout">
								
							<!-- 	<script language="jscript" src="../Script/Calendar.js"></script>
							 -->
								<div id="Bond21_Panel1" style="height:248px;width:441px;">
									<span id="Bond21_Label1" style="Z-INDEX: 101; LEFT: 17px; POSITION: absolute; TOP: 22px">
														债券单位成本(元)</span>
									<span id="Bond21_Label2" style="Z-INDEX: 102; LEFT: 17px; POSITION: absolute; TOP: 52px">
														债券购买交割日</span>
									<span id="Bond21_Label3" style="Z-INDEX: 103; LEFT: 17px; POSITION: absolute; TOP: 80px">
														债券到期兑付日</span>
									<span id="Bond21_Label4" style="Z-INDEX: 104; LEFT: 17px; POSITION: absolute; TOP: 109px">
														债券单位面值(元)</span>
									<span id="Bond21_Label5" style="Z-INDEX: 105; LEFT: 17px; POSITION: absolute; TOP: 143px">
														债券票面年利率(%)</span>
									<span id="Bond21_Label6" style="Z-INDEX: 106; LEFT: 17px; POSITION: absolute; TOP: 174px">
														债券偿还期限(年)</span>
									<input tabindex="10" name="Bond21:edUnitCost" type="text" id="Bond21_edUnitCost" class="txtnum" style="width:125px;Z-INDEX: 107; LEFT: 139px; POSITION: absolute; TOP: 17px" />
									<input tabindex="11" name="Bond21_edBuyDate" class="txt" type="text" value="2001-1-1" id="Bond21_edBuyDate" size="13" onblur='ChkCZDate(Bond21_edBuyDate)' style="width:102px;Z-INDEX: 115; LEFT: 138px; POSITION: absolute; TOP: 47px">
							<!--  		<IMG SRC="../Images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(Bond21_edBuyDate)' style="Z-INDEX: 115; LEFT: 243px; POSITION: absolute; TOP: 47px">
							-->
									<input tabindex="12" name="Bond21_edSellDate" class="txt" type="text" value="2001-1-1" id="Bond21_edSellDate" size="13" onblur='ChkCZDate(Bond21_edSellDate)' style="width:102px;Z-INDEX: 109; LEFT: 138px; POSITION: absolute; TOP: 77px">
								<!--  	<IMG SRC="../Images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(Bond21_edSellDate)' style="Z-INDEX: 115; LEFT: 243px; POSITION: absolute; TOP: 77px">
								-->
									<input tabindex="13" name="Bond21:edUnitPrice" type="text" id="Bond21_edUnitPrice" tabindex="1014" class="txtnum" style="width:125px;Z-INDEX: 110; LEFT: 139px; POSITION: absolute; TOP: 107px" />
									<input tabindex="14" name="Bond21:edRate" type="text" id="Bond21_edRate" tabindex="1015" class="txtnum" style="width:125px;Z-INDEX: 111; LEFT: 139px; POSITION: absolute; TOP: 137px" />
									<input tabindex="15" name="Bond21:edYear" type="text" id="Bond21_edYear" tabindex="1016" class="txtnum" style="width:125px;Z-INDEX: 112; LEFT: 139px; POSITION: absolute; TOP: 171px" />
								</div>
							</DIV>
							<DIV id="Layer3" style="Z-INDEX:102;LEFT:30px;WIDTH:500px;POSITION:absolute;TOP:107px;HEIGHT:223px;VISIBILITY:hidden;" ms_positioning="GridLayout">
							
							<!-- 	<script language="jscript" src="../Script/Calendar.js"></script>
							 -->
								<div id="Bond31_Panel1" DESIGNTIMEDRAGDROP="2" style="height:242px;width:442px;">
									<span id="Bond31_Label1" style="Z-INDEX: 100; LEFT: 26px; POSITION: absolute; TOP: 22px">
														债券单位成本(元)</span>
									<span id="Bond31_Label2" style="Z-INDEX: 101; LEFT: 26px; POSITION: absolute; TOP: 48px">
														债券购买交割日</span>
									<span id="Bond31_Label3" style="Z-INDEX: 102; LEFT: 26px; POSITION: absolute; TOP: 74px">
														债券到期兑付日</span>
									<span id="Bond31_Label4" style="Z-INDEX: 103; LEFT: 26px; POSITION: absolute; TOP: 100px">
														债券单位面值(元)</span>
									<span id="Bond31_Label5" style="Z-INDEX: 104; LEFT: 26px; POSITION: absolute; TOP: 126px">
														债券票面年利率(%)</span>
									<input tabindex="10" name="Bond31:edUnitCost" type="text" id="Bond31_edUnitCost" class="txtnum" style="width:125px;Z-INDEX: 106; LEFT: 214px; POSITION: absolute; TOP: 19px" />
									<input tabindex="11" name="Bond31_edBuyDate" class="txt" type="text" value="2001-1-1" id="Bond31_edBuyDate" size="13" onblur='ChkCZDate(Bond31_edBuyDate)' style="width:100px;Z-INDEX: 107; LEFT: 215px; POSITION: absolute; TOP: 45px">
							<!--  		<IMG SRC="../Images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(Bond31_edBuyDate)' style="Z-INDEX: 107; LEFT: 318px; POSITION: absolute; TOP: 45px">
								-->
									<input tabindex="12" name="Bond31_edSellDate" class="txt" type="text" value="2001-1-1" id="Bond31_edSellDate" size="13" onblur='ChkCZDate(Bond31_edSellDate)' style="width:100px;Z-INDEX: 108; LEFT: 215px; POSITION: absolute; TOP: 71px">
								<!--  	<IMG SRC="../Images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(Bond31_edSellDate)' style="Z-INDEX: 108; LEFT: 318px; POSITION: absolute; TOP: 71px">
								-->
									<input tabindex="13" name="Bond31:edUnitPrice" type="text" id="Bond31_edUnitPrice" tabindex="1020" class="txtnum" style="width:125px;Z-INDEX: 109; LEFT: 214px; POSITION: absolute; TOP: 97px" />
									<input tabindex="14" name="Bond31:edRate" type="text" id="Bond31_edRate" tabindex="1021" class="txtnum" style="width:125px;Z-INDEX: 110; LEFT: 214px; POSITION: absolute; TOP: 123px" />
									<span id="Bond31_Label7" style="Z-INDEX: 113; LEFT: 26px; POSITION: absolute; TOP: 153px">
														债券利息支付频率(次/年)</span>
									<input tabindex="15" name="Bond31:edFreq" type="text" id="Bond31_edFreq" tabindex="1023" class="txtnum" style="width:125px;Z-INDEX: 114; LEFT: 214px; POSITION: absolute; TOP: 150px" />
								</div>
							</DIV>
							<HR style="Z-INDEX: 108; LEFT: 12px; WIDTH: 96.76%; POSITION: absolute; TOP: 318px; HEIGHT: 1px" width="96.76%" SIZE="1">
							<table id="rbType" class="txt" border="0" style="width:440px;Z-INDEX: 101; LEFT: 14px; POSITION: absolute; TOP: 9px">
								<tr>
									<td><input tabindex="1" id="rbType_0" type="radio" name="rbType" value="0" checked="checked" /><label for="rbType_0">处于最后付息周期的固定利率债券、待偿期在一年及以内的到期一次还本付息债券和零息债券</label></td>
								</tr>
								<tr>
									<td><input tabindex="1" id="rbType_1" type="radio" name="rbType" value="1" /><label for="rbType_1">待偿期在一年以上的到期一次还本付息债券和零息债券</label></td>
								</tr>
								<tr>
									<td><input tabindex="1" id="rbType_2" type="radio" name="rbType" value="2" /><label for="rbType_2">不处于最后付息周期的固定利率债券</label></td>
								</tr>
							</table>
							<div id="Panel1" style="height:27px;width:292px;Z-INDEX: 105; LEFT: 10px; POSITION: absolute; TOP: 331px">
								<span id="Label1" style="width:153px;">该债券的到期收益率为(%)</span>&nbsp;&nbsp; <input name="lbResult" type="text" value="计算得出" disabled="fasle" readonly="readonly" id="lbResult" tabindex="4" class="txtd" style="width:91px;" />
							</div>
							<input type="button" name="btnCalc" value=" 计算 " id="btnCalc" tabindex="4000" class="btn" onclick="return calcu()" style="Z-INDEX: 106; LEFT: 474px; POSITION: absolute; TOP: 16px" />
							<DIV id="LayerBond" style="Z-INDEX:109; LEFT:349px; VISIBILITY:hidden; WIDTH:62px; POSITION:absolute; TOP:369px; HEIGHT:28px" ms_positioning="GridLayout">
								<input tabindex="1000" name="edBond" type="text" value="0" id="edBond" style="height:19px;width:40px;Z-INDEX: 109; LEFT: 3px; POSITION: absolute; TOP: 6px" />
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</DIV>
		</form>
		<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
		<SCRIPT language="javascript" event="onclick" for="rbType">
		if(this.document.all.rbType_0.checked)
		{
			this.document.all.Layer1.style.visibility="visible";
			this.document.all.Layer2.style.visibility="hidden";
			this.document.all.Layer3.style.visibility="hidden";	  	  
			this.document.all.edBond.value = 0;
		}
  		if(this.document.all.rbType_1.checked)
  		{
			this.document.all.Layer1.style.visibility="hidden";
			this.document.all.Layer2.style.visibility="visible";
			this.document.all.Layer3.style.visibility="hidden";	  	  
			this.document.all.edBond.value = 1;
		}
		if(this.document.all.rbType_2.checked)
		{
			this.document.all.Layer1.style.visibility="hidden";
			this.document.all.Layer2.style.visibility="hidden";
			this.document.all.Layer3.style.visibility="visible";	  	  
			this.document.all.edBond.value = 2;
		}
		</SCRIPT>
		</TR></TBODY></TABLE>
	</body>
	<script language="javascript">
	var initdate=datetostring(new Date());;
	document.all.Bond11_edBuyDate.value = initdate;
	document.all.Bond11_edSellDate.value = initdate;
	document.all.Bond21_edBuyDate.value = initdate;
	document.all.Bond21_edSellDate.value = initdate;
	document.all.Bond31_edBuyDate.value = initdate;
	document.all.Bond31_edSellDate.value = initdate;
	
	</script>
</HTML>
<script language="javascript">
function calcu()
{
  if(this.document.all.rbType_0.checked)
 {
	if (!CheckFN3(this.document.all.Bond11_edUnitCost,"请在[债券单位成本]输入正数",false))
	   	return false;	       		    
	if (!CheckFN3(this.document.all.Bond11_edUnitPrice,"请在[债券单位面值]输入正数",false))
	   	return false;
	if(!CheckEmpty(this.document.all.Bond11_edBuyDate,"[债券购买交割日期]不能为空！"))
		return false;
	if(!CheckEmpty(this.document.all.Bond11_edSellDate,"[债券到期兑付日期]不能为空！"))
		return false;		
	var BuyDate=new Date();
	BuyDate.setTime(Cal_strtodate(this.document.all.Bond11_edBuyDate.value));				
	var SellDate=new Date();
	SellDate.setTime(Cal_strtodate(this.document.all.Bond11_edSellDate.value));			
	if (BuyDate>=SellDate)
	{
		DispMessage(this.document.all.Bond11_edSellDate,"[债券到期兑付日]应当晚于[购买交割日]");
		return false;
	}
	if (this.document.all.Bond11_rb2.checked)		
	{
		if ( !CheckFN3(this.document.all.Bond11_edRate1,"请在[债券票面年利率]输入非负数",true))
	   		return false;
		if ( !CheckPN(this.document.all.Bond11_edYear,"请在[债券偿还期限]输入正整数",false))
			return false;
    }      
    if (this.document.all.Bond11_rb3.checked)
    {
	if (!CheckFN3(this.document.all.Bond11_edRate2,"请在[债券票面年利率]输入非负数",true))
	   	return false;    
	if (!CheckPN(this.document.all.Bond11_edFreq,"请在[利息支付频率]输入正整数",false))
	   	return false;	       		    	    
	}   	

}
if(this.document.all.rbType_1.checked)
 {
	if (!CheckFN3(this.document.all.Bond21_edUnitCost,"请在[债券单位成本]输入正数",false))
	   	return false;	       		    
	if(!CheckEmpty(this.document.all.Bond21_edBuyDate,"[债券购买交割日期]不能为空！"))
		return false;
	if(!CheckEmpty(this.document.all.Bond21_edSellDate,"[债券到期兑付日期]不能为空！"))
		return false;		
	var BuyDate=new Date();
	BuyDate.setTime(Cal_strtodate(this.document.all.Bond21_edBuyDate.value));				
	var SellDate=new Date();
	SellDate.setTime(Cal_strtodate(this.document.all.Bond21_edSellDate.value));			
	if (BuyDate>=SellDate)
	{
		DispMessage(this.document.all.Bond21_edSellDate,"[债券到期兑付日]应当晚于[购买交割日]");
		return false;
	}		
	if (!CheckFN3(this.document.all.Bond21_edUnitPrice,"请在[债券单位面值输入]正数",false))
	   	return false;		
	if (!CheckFN3(this.document.all.Bond21_edRate,"请在[债券票面年利率输入]非负数",true))
	   	return false;
    if (!CheckPN(this.document.all.Bond21_edYear,"请在[债券偿还期限]输入正整数",false))
        return false;
        

}					
if(this.document.all.rbType_2.checked)
 {
	if (!CheckFN3(this.document.all.Bond31_edUnitCost,"请在[债券单位成本]输入正数",false))
	   	return false;	       		    
	if(!CheckEmpty(this.document.all.Bond31_edBuyDate,"[债券购买交割日期]不能为空！"))
		return false;
	if(!CheckEmpty(this.document.all.Bond31_edSellDate,"[债券到期兑付日期]不能为空！"))
		return false;		
	var BuyDate=new Date();
	BuyDate.setTime(Cal_strtodate(this.document.all.Bond31_edBuyDate.value));				
	var SellDate=new Date();
	SellDate.setTime(Cal_strtodate(this.document.all.Bond31_edSellDate.value));			
	if (BuyDate>=SellDate)
	{
		DispMessage(this.document.all.Bond31_edSellDate,"[债券到期兑付日]应当晚于[购买交割日]");
		return false;
	}		
	if (!CheckFN3(this.document.all.Bond31_edUnitPrice,"请在[债券单位面值]输入正数",false))
	   	return false;		
	if (!CheckFN3(this.document.all.Bond31_edRate,"请在债券票面年利率输入非负数",true))
	   	return false;
	if (!CheckPN(this.document.all.Bond31_edFreq,"请在利息支付频率输入正整数",false))
	   	return false;	       		    	            

}
  
  if (this.document.all.rbType_0.checked)
  {
   
    if (document.BondERateCalc.Bond11_rb1.checked==true)
    {    	
        //贴现
       // alert(document.all.Bond11_edUnitCost.value);
       // alert(document.BondERateCalc.Bond11_edUnitCost.value);
       //cal.Calc(document.BondERateCalc.Bond11_edUnitCost.value,document.BondERateCalc.Bond11_edBuyDate.value,document.BondERateCalc.Bond11_edSellDate.value,document.BondERateCalc.Bond11_edUnitPrice.value,0,0,1);
	   CalcBond11(0);
    }
    if (document.BondERateCalc.Bond11_rb2.checked==true)
    {
        //一次还本付息
        //cal.Calc(document.BondERateCalc.Bond11_edUnitCost.value,document.BondERateCalc.Bond11_edBuyDate.value,document.BondERateCalc.Bond11_edSellDate.value,document.BondERateCalc.Bond11_edUnitPrice.value,document.BondERateCalc.Bond11_edRate1.value,document.BondERateCalc.Bond11_edYear.value,3);
		CalcBond11(1);
    }
    if (document.BondERateCalc.Bond11_rb3.checked==true)
    {
        //定期支付
        //cal.Calc(document.BondERateCalc.Bond11_edUnitCost.value,document.BondERateCalc.Bond11_edBuyDate.value,document.BondERateCalc.Bond11_edSellDate.value,document.BondERateCalc.Bond11_edUnitPrice.value,document.BondERateCalc.Bond11_edRate2.value,document.BondERateCalc.Bond11_edFreq.value,2);
		CalcBond11(2);
    }
  }
  if (this.document.all.rbType_1.checked)
  {
    //剩余流通期限在一年以上的到期一次还本付息债券
    //cal.Calc(document.BondERateCalc.Bond21_edUnitCost.value,document.BondERateCalc.Bond21_edBuyDate.value,document.BondERateCalc.Bond21_edSellDate.value,document.BondERateCalc.Bond21_edUnitPrice.value,document.BondERateCalc.Bond21_edRate.value,document.BondERateCalc.Bond21_edYear.value,4);
	CalcBond21();
  }
  if (this.document.all.rbType_2.checked)
  {
    //不处于最后付息周期的固定利率附息债券和浮动利率债券
    //cal.Calc(document.BondERateCalc.Bond31_edUnitCost.value,document.BondERateCalc.Bond31_edBuyDate.value,document.BondERateCalc.Bond31_edSellDate.value,document.BondERateCalc.Bond31_edUnitPrice.value,document.BondERateCalc.Bond31_edRate.value,document.BondERateCalc.Bond31_edFreq.value,5);
	CalcBond31();
  }
  //document.BondERateCalc.lbResult.value = cal.Yield;
  //cal=null;
  return false;
}

function Excute(Cost,BondBuyDate,BondEndDate,EndCost,BondRate,YearTimes,Freq,options)
{

	var w,m,tDays=0;	//int
	var l,pv,x,s,e,isetp;	//double
	var CurrDate=new Date(BondBuyDate.getFullYear(),0,1);
	var BondEndRate;
	
	tDays=GetDayLen1(BondEndDate,BondBuyDate)+1;
	
	switch(options)
	{
		case 0:
			BondEndRate=(EndCost-Cost)/Cost*365/tDays;
			break;
		case 1:
			BondEndRate=(EndCost+YearTimes*EndCost*BondRate-Cost)/Cost*365/tDays;
			break;
		case 2:
			BondEndRate=((EndCost+EndCost*BondRate/Freq)-Cost)/Cost*365.0/tDays;
			break;
		case 3:
			BondEndRate=Math.pow((EndCost+EndCost*YearTimes*BondRate)/Cost,365.0/tDays)-1;
			break;
		case 4:
			l=tDays*Freq/365;
			m=parseInt(l + 1);
			w= tDays % (365/Freq) / (365/Freq);
			isetp=0.0001;
			s=0.001;
			e=1;
			pv=0;						
			x=(e-s)/2;
			while ((Math.abs(pv-Cost)>0.001)&&(Math.abs(e-s)>isetp))
			{
				pv=Calc(x,w,m,EndCost,BondRate,Freq);
				if (pv==0) break;
				if (pv<Cost) 
				{
					e=x;
					x=(s+e)/2;
				}
				if (pv>Cost)
				{
					s=x;
					x=(s+e)/2;
				}
			}
			BondEndRate=x;
			break;
	}

	BondERateCalc.lbResult.value = NBround(BondEndRate*100,2);
	
}

//GetCalc_Close();

</script>
