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
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcSaveCapGetAcc.js" language="JavaScript"> </SCRIPT>
		<script language="javascript">
		//获得利率
        function getrate()
        { //alert(GetRMBSaveRatio(4,document.all.SaveTerm.options(document.all.SaveTerm.selectedIndex).value),window.xmlRMBSaveRate.XMLDocument);
          	
          	 if(document.CompPartSumCalc.SaveTerm.options(document.CompPartSumCalc.SaveTerm.selectedIndex).value==12)
          	 {
          	 	document.CompPartSumCalc.edFullRate.value= 1.71;
          	 	
          	 }else if(document.CompPartSumCalc.SaveTerm.options(document.CompPartSumCalc.SaveTerm.selectedIndex).value==36)
          	 {
          	 	document.CompPartSumCalc.edFullRate.value= 1.98;
          	 }else if(document.CompPartSumCalc.SaveTerm.options(document.CompPartSumCalc.SaveTerm.selectedIndex).value==60)
          	 {
          	 	document.CompPartSumCalc.edFullRate.value= 2.250;
          	 }

          //   document.CompPartSumCalc.edFullRate.value=GetRMBSaveRatio(4,document.CompPartSumCalc.SaveTerm.options(document.CompPartSumCalc.SaveTerm.selectedIndex).value,window.xmlRMBSaveRate.XMLDocument);
        }
		</script>
	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
 var cImage; 
 cImage = new Image; 
 cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
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
		<form name="CompPartSumCalc" id="CompPartSumCalc">
		<!--  	<div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="240"><img src="../Images/bg5.gif" width="475" height="240"><img src="../Images/bg6.gif" width="25" height="240"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>
			-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="width:300;color:black">【存本取息计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:480">通过本计算器可以对每次支取利息金额和初始存入金额进行计算。其中到期本息金额为本金与最后一次支取利息金额之和（已扣除利息税）。
			</div>
			<div id="FDiv3" class="FDiv3" style="width:450" align="center">
				<TABLE id="Table1" style="WIDTH: 450px; HEIGHT: 302px" cellSpacing="1" cellPadding="1" width="410" border="0">
					<TR>
						<TD width="146" style="WIDTH: 127px">计算项目</TD>
						<TD colspan="2">
							<input type="radio" id="CalcType_0" name="CalcType" checked onclick="calctypechange();">
							每次支取利息金额 <input type="radio" id="CalcType_1" name="CalcType" onclick="calctypechange();">
							初始存入金额 
							<!--
						<select name="CalcType" id="CalcType" tabindex="1000" style="width:110px;">
									<option value="1">每次支取利息金额</option>
									<option value="2">初始存入金额</option>
								</select>
								-->
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 127px">储蓄存期</TD>
						<TD width="209" style="WIDTH: 202px"><FONT face="宋体">
								<select name="SaveTerm" id="SaveTerm" tabindex="1001" style="width:112px;" onchange="getrate()" language="javascript">
								<option value=12>一年</option>
								<option value=36>三年</option>
								<option value=60>五年</option>
								</select>
							</FONT>
						</TD>
						<td width="85"></td>
					</TR>
					<TR>
						<TD style="WIDTH: 127px; HEIGHT: 23px">初始存入日期</TD>
						<TD style="WIDTH: 202px; HEIGHT: 23px"><input name="beginDate" class="txt" type="text" value="2001-1-1" id="beginDateID" style="width:87" onblur='ChkCZDate(beginDateID);'>
							<IMG SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(beginDateID)'>
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
						</TD>
						<TD style="HEIGHT: 23px"><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 127px" id="lbCSCR">初始存入金额(元)</TD>
						<TD style="WIDTH: 202px" id="CSCR"><input name="edInitSaveSum" type="text" id="edInitSaveSum" tabindex="1003" class="txtnum" style="width:112px;" /></TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 127px">年利率(%)</TD>
						<TD style="WIDTH: 202px"><input name="edFullRate" type="text" id="edFullRate" tabindex="1005" class="txtnum" style="width:112px;" />
							&nbsp;</TD>
						<TD><input name="button" type="button" class="btn" id="btnCalc" tabindex="1006" value=" 计算 "></TD>
					</TR>
					<TR>
						<TD colSpan="3">
							<HR width="100%" SIZE="1">
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 127px" id="lbMCZQ">每次支取利息金额(元)</TD>
						<TD style="WIDTH: 202px" id="MCZQ"><input name="edCapitalSum" disabled="false" type="text" value="计算得出" id="edCapitalSum" tabindex="7" class="txtd" style="width:112px;" />
							&nbsp;</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 127px">到期支取本息金额(元)</TD>
						<TD style="WIDTH: 202px"><input name="edFullSum" disabled="false" type="text" value="计算得出" id="edFullSum" tabindex="7" class="txtd" style="width:112px;" /></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 127px">扣除利息税金额(元)</TD>
						<TD style="WIDTH: 202px"><input name="edTaxSum" disabled="false" type="text" value="计算得出" id="edTaxSum" tabindex="7" class="txtd" style="width:112px;" /></TD>
						<TD></TD>
					</TR>
				</TABLE>
			</div>
			<script language="javascript">
<!--
	var startdate =  new Array('2002-2-21','2002-2-21','2002-2-21');
	var minterm =  new Array(12,36,60);
	var rate =  new Array(0.0171,0.0189,0.0198);
		// -->
			</script>
		</form>
		<script language="javascript" event="onclick" for="btnCalc">
				
				if (!CheckEmpty(document.all.beginDateID,"无效的初始存入日期"))
					return false;
				if ( Cal_strtodate(document.all.beginDateID.value)< Cal_strtodate("1999-11-1"))
					{
						DispMessage(document.all.beginDateID,"初始存入日期不得小于1999年11月1日");
						return false;
					}											
				if (document.CompPartSumCalc.CalcType_0.checked==true)
					if (!CheckFN3(edInitSaveSum,"初始存入金额请输入正数",false))
						return false;	
				if (!CheckFN3(edFullRate,"年利率请输入正数",false))				 
						return false;					
				if (document.CompPartSumCalc.CalcType_1.checked==true)
					if (!CheckFN3(edCapitalSum,"每次支取利息金额请输入正数",false))
						return false;		
				if (document.CompPartSumCalc.CalcType_0.checked==true)
				compute(0);
				else
				compute(1);
		</script>
		<script language="javascript">
		    function changeobject(objectid)
			{
			  	    var ss1=document.all.CSCR.innerHTML;
				    var ss2=document.all.MCZQ.innerHTML;
				    
				    if (ss1.indexOf(objectid)>0)
				    {
				         ls=document.all.lbCSCR.innerHTML;
				        document.all.lbCSCR.innerHTML=document.all.lbMCZQ.innerHTML;
    				    document.all.lbMCZQ.innerHTML=ls;
    				    
    				    document.all.CSCR.innerHTML=document.all.MCZQ.innerHTML;
    				    document.all.MCZQ.innerHTML=ss1;
    			    }   
    				
			
			}
			
			function calctypechange()
			{
					document.all.edCapitalSum.value="";
					document.all.edCapitalSum.className="txtnum";
					document.all.edCapitalSum.disabled=false;
					document.all.edInitSaveSum.value="";
					document.all.edInitSaveSum.className="txtnum";
					document.all.edInitSaveSum.disabled=false;
				if (document.all.CalcType_0.checked==true)
				{
				    changeobject("edCapitalSum");
					document.all.edCapitalSum.value="计算得出";
					document.all.edCapitalSum.className="txtd";
					document.all.edCapitalSum.disabled=true;
				}
				else if (document.all.CalcType_1.checked==true)
				{
				    changeobject("edInitSaveSum");
					document.all.edInitSaveSum.value="计算得出";
					document.all.edInitSaveSum.className="txtd";
					document.all.edInitSaveSum.disabled=true;
				}
				
			}
			
			function loadinit()
			{
				calctypechange();		
				
				document.all.edFullSum.disabled=true;
				document.all.edTaxSum.disabled=true;
			} 
			
			function locaterate(sdate,term)
			{		
			 var result=null;	 
			 for (var i=0; i<startdate.length;i++)
			  if ( (sdate>=Cal_strtodate(startdate[i]))&&(term==minterm[i]) )
			  {
			    result=rate[i];
			  }  
				return result;
			}
			
						
			function compute(type)
			{
				var initsavesum,term,capitalsum,taxsum;
				term=parseInt(document.all.SaveTerm.value)/12; //年为单位
				//var calc = new ActiveXObject("FinanceCalculator.ICalcSaveCapGetAcc");
				if (type==0) //计算每次支取利息金额
				{
				    //calc.Calc(term,document.all.beginDateID.value,document.all.edInitSaveSum.value,document.all.edFullRate.value,1);
				    //document.all.edCapitalSum.value=calc.EverySum;
				    //initsavesum=document.all.edInitSaveSum.value;
					//capitalsum=CalcGetAcc(document);
					CalcGetAcc(document);
					//document.all.edCapitalSum.value=Round(capitalsum);
				}
				else if (type==1) //计算初始存入金额
				 {
				    //calc.Calc(term,document.all.beginDateID.value,document.all.edCapitalSum.value,document.all.edFullRate.value,2);
				    //document.all.edInitSaveSum.value=calc.InitSum;
				   // initsavesum=CalcSaveCap(document);
				   CalcSaveCap(document);
					//capitalsum=document.all.edCapitalSum.value;
					//document.all.edInitSaveSum.value=Round(initsavesum);
				 }
				//taxsum=CalcTaxofRate(document,initsavesum,term);
				//document.all.edTaxSum.value=Round(taxsum);
				//document.all.edFullSum.value=Round(parseFloat(initsavesum)+parseFloat(capitalsum)-taxsum);
				    
			}
		
		</script>
	<!--  	<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml" ondatasetcomplete="addterm()"></xml>
	-->
	</body>
</HTML>
<script language="javascript">
document.all.SaveTerm.options(0).selected=true;
document.CompPartSumCalc.edFullRate.value= 1.71;
//获得存期
function addterm()
{
  document.all.SaveTerm.options.length = 0;
  ComSaveTime(document.all.SaveTerm,5,window.xmlRMBSaveRate.XMLDocument);
  if (document.all.E_SaveTerm!=null)
	 document.all.E_SaveTerm.value = document.all.SaveTerm.options(0).text;
  document.all.SaveTerm.selectedIndex = 0;
  getrate();
}

</script>
<script language="javascript">
  //addterm();
  document.all.beginDateID.value=new Date().toString();
  
     //GetCalc_Close();

</script>
