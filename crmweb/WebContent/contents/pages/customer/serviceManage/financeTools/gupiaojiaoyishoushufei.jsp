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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcStockTradeFee.js" language="JavaScript"> </SCRIPT>

	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
  </HEAD>
  <body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
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
<img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
<!-- END WRating v1.0 -->
	<form name="EduCalc" id="EduCalc">
	 <!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
	  <div id="bg2"><img src="../Images/bg4.gif" width="25" height="200"><img src="../Images/bg5.gif" width="475" height="200"><img src="../Images/bg6.gif" width="25" height="200"></div>
	  <div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div> -->
	  <div id="FDiv1" class="FDiv1" Movable="1" style="width:300;color:black">【股票交易手续费计算器】</div>
	  <div id="FDiv2" class="FDiv2" Movable="1" style="width:480">在进行股票交易时，各种费用也应记入成本。本计算器能迅速计算出一定成交量下投资者投资上海A、B股，深圳A、B股需要缴纳的佣金、印花税、过户费及其他费用
	  </div>
	  <div id="FDiv3" class="FDiv3" style="width:450" align="center">
		<table cellSpacing="0" borderColorDark="#e9f3f4" cellPadding="0" width="90%" borderColorLight="#336666" border="0">
		  <tr>
			<td align="middle">
			  <DIV style="WIDTH: 446px; POSITION: relative; HEIGHT: 257px" ms_positioning="GridLayout">
				<HR style="Z-INDEX: 104; LEFT: 19px; WIDTH: 91.31%; POSITION: absolute; TOP: 145px; HEIGHT: 1px" width="91.31%" SIZE="1">
				<input name="edtradeprice" type="text" id="edtradeprice" tabindex="1010" class="txtnum" style="width:110px;Z-INDEX: 101; LEFT: 180px; POSITION: absolute; TOP: 47px" />
				<input name="edtradenum" type="text" id="edtradenum" tabindex="1020" class="txtnum" style="width:110px;Z-INDEX: 102; LEFT: 180px; POSITION: absolute; TOP: 78px" />
				<DIV style="Z-INDEX: 105; LEFT: 54px; POSITION: absolute; TOP: 20px" ms_positioning="text2D"><FONT face="宋体">交易对象</FONT></DIV>
				<DIV style="Z-INDEX: 106; LEFT: 54px; POSITION: absolute; TOP: 49px" ms_positioning="text2D">股票成交价格（元）</DIV>
				<DIV style="Z-INDEX: 107; LEFT: 30px; WIDTH: 186px; POSITION: absolute; TOP: 158px; HEIGHT: 19px" align="left" ms_positioning="FlowLayout">须交纳的股票交易手续费共计(元)</DIV>
				<INPUT class="btn" id="btnCalc" style="Z-INDEX: 108; LEFT: 344px; WIDTH: 53px; POSITION: absolute; TOP: 15px; HEIGHT: 20px" tabIndex="1040" type="button" value=" 计算 ">
				<DIV style="Z-INDEX: 109; LEFT: 54px; POSITION: absolute; TOP: 79px" ms_positioning="text2D">股票成交量（股）</DIV>
				<input name="edtraderate" type="text" id="edtraderate" tabindex="1030" class="txtnum" style="width:111px;Z-INDEX: 110; LEFT: 180px; POSITION: absolute; TOP: 109px" />
				<DIV style="Z-INDEX: 111; LEFT: 54px; POSITION: absolute; TOP: 109px" ms_positioning="text2D">佣金比率（%）</DIV>
				<select name="TradeType" id="TradeType" tabindex="1000" style="width:110px;Z-INDEX: 112; LEFT: 180px; POSITION: absolute; TOP: 13px">
				  <option value="1">上海A股</option>
				  <option value="2">上海B股</option>
				  <option value="3">深圳A股</option>
				  <option value="4">深圳B股</option>
				</select>
				<input name="txttotal" type="text" value="计算得出" id="txttotal" class="txtd" style="width:97px;Z-INDEX: 113; LEFT: 233px; POSITION: absolute; TOP: 157px" />
				<input name="txt1" type="text" value="计算得出" readonly="readonly" id="txt1" class="txtd" style="width:98px;Z-INDEX: 114; LEFT: 111px; POSITION: absolute; TOP: 205px" />
				<span id="Lab1" style="height:19px;width:67px;Z-INDEX: 115; LEFT: 26px; POSITION: absolute; TOP: 208px">印花税(元)</span><span id="Lab2" style="height:19px;width:67px;Z-INDEX: 116; LEFT: 22px; POSITION: absolute; TOP: 240px">佣金(元)</span>
				<input name="txt2" type="text" value="计算得出" readonly="readonly" id="txt2" class="txtd" style="width:98px;Z-INDEX: 117; LEFT: 112px; POSITION: absolute; TOP: 240px" />
				<span id="Lab3" style="height:19px;width:67px;Z-INDEX: 118; LEFT: 235px; POSITION: absolute; TOP: 208px">过户费(元)</span>
				<input name="txt3" type="text" value="计算得出" readonly="readonly" id="txt3" class="txtd" style="width:98px;Z-INDEX: 119; LEFT: 315px; POSITION: absolute; TOP: 205px" />
				<DIV style="Z-INDEX: 120; LEFT: 30px; POSITION: absolute; TOP: 180px" ms_positioning="text2D">包括：</DIV>
				<TABLE id="Table1" style="Z-INDEX: 121; LEFT: 236px; VISIBILITY: hidden; WIDTH: 197px; POSITION: absolute; TOP: 243px; HEIGHT: 28px" cellSpacing="1" cellPadding="1" width="197" border="0">
				  <TR>
					<TD style="WIDTH: 74px"><span id="Lab4">Lab4</span></TD>
					<TD><FONT face="宋体"> <input name="txt4" type="text" value="计算得出" readonly="readonly" id="txt4" class="txtd" style="width:98px;" />
					  </FONT>
					</TD>
				  </TR>
				</TABLE>
			  </DIV>
			</td>
		  </tr>
		</table>
	  </div>
	</form>
	<script language="javascript" src="../Script/CheckDataFunction.js"></script>
	<script language="javascript" event="onclick" for="btnCalc">
			if (!CheckFN3(edtradeprice,"请在股票成交价格中输入正数",false,null,4))
	   		return false;
			if (!CheckPN(edtradenum,"请在成交量中输入正整数",false))
	   		return false;
	   		if (!CheckFN3(edtraderate,"请在佣金比例中输入非负数",true))
	   		return false;	   
	   		var obj=ComputeTradeFee(document,parseInt(TradeType.value));
	   		txt1.value=Round(obj.r1);
	   		txt2.value=Round(obj.r2);
	   		txt3.value=Round(obj.r3);
	   		txttotal.value=Round(obj.r1+obj.r2+obj.r3);
	   		if ( Table1.style.visibility=="visible" )
	   		{
	   			txt4.value=Round(obj.r4);
	   			txttotal.value=Round(parseFloat(txttotal.value)+obj.r4);
	   		}	
	</script>
	<script language="javascript" event="onchange" for="TradeType">		
			switch (document.all.TradeType.value ) 
			 {
				case "1": //上A
				{				
					Lab3.innerText="过户费(元)";			  			  
					Table1.style.visibility="hidden";
					break;
				}
				case "3": //深A
				{				
					Lab3.innerText="监督管理和经手费(元)";			  						
					Table1.style.visibility="hidden";
					break;
				}
				case "2": //上B
				{				
					Lab3.innerText="结算费(元)";			  			  
					Table1.style.visibility="visible";
					this.document.all.Lab4.innerText="过户费(元)"; 
					break;
				}
				case "4": //深B
				{					
					Lab3.innerText="交易规费(元)";			  			  
					Table1.style.visibility="visible";
					document.all.Lab4.innerText="结算费(元)"; 
					break;
				}
			 }			 
			  txt1.value="计算得出";
			  txt2.value="计算得出";
			  txt3.value="计算得出";
			  txt4.value="计算得出";
			  txttotal.value="计算得出";
	</script>
	<script language="javascript">
		/*
		 function ComputeTradeFee(Type)
		 {
		    var cal = new ActiveXObject("FinanceCalculator.ICalcStockFee");
		    cal.Execute(Type,document.all.edtradeprice.value,document.all.edtradenum.value,document.all.edtraderate.value/100);
		    
			//var tradenum=parseInt(document.all.edtradenum.value);
			//var tradeprice=parseFloat(document.all.edtradeprice.value);
			//var traderate=parseFloat(document.all.edtraderate.value) /100;
			//var tradesum=tradeprice * tradenum;
			
			var obj=new Object();
			
			obj.r1=cal.Tax; //印花税					
			obj.r2 =cal.Comm; //佣金
			switch (Type)
			{				
				case 1: //上A
					{					
					obj.r3=cal.Transfer; //过户费
					break;
					}
				case 3: //深A
					{					
					obj.r3=cal.Manage; //监督管理费和经手费
					break;
					}
				case 2: //上B
					{					
					obj.r3=cal.Balance; //结算费
					obj.r4=cal.Transfer;  //交易过户费
					break;
					}	
				case 4: //深B
					{					
					obj.r3=cal.Deal; //交易规费
					obj.r4=cal.Balance;  //结算费
					
					break;
					}			
			} 
			return obj;
		 }
		 */
		 function loadinit()
		 {
		  this.document.all.txt1.disabled=true;
		  this.document.all.txt2.disabled=true;
		  this.document.all.txt3.disabled=true;
		  this.document.all.txt4.disabled=true;
		  this.document.all.txttotal.disabled=true;
		 }
		 window.attachEvent("onload",loadinit);
		 
     //GetCalc_Close();
		
	</script>
  </body>
</HTML>
