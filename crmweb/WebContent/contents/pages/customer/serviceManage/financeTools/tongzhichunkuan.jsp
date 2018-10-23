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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcInformSave.js" language="JavaScript"> </SCRIPT>

		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
 //cImage = new Image; 
 //cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
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
		<form name="CompPartSumCalc" id="CompPartSumCalc">
			<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="220"><img src="../Images/bg5.gif" width="475" height="220"><img src="../Images/bg6.gif" width="25" height="220"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>-->
			<div id="FDiv1" class="FDiv1" Movable="1" style="width:300;color:black">【通知存款计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:480">通知存款兼具定期存款的收益性和活期存款的方便性。依据一定的已知数据，本计算器可方便的计算出通知存款的实得本息总额（已扣除利息税）和提取金额。
			</div>
			<div id="FDiv3" class="FDiv3" style="width:450" align="center">
				<TABLE id="Table1" style="WIDTH: 390px; HEIGHT: 287px" cellSpacing="1" cellPadding="1" width="390" border="0">
					<TR>
						<td style="width:40"></td>
						<TD style="WIDTH: 128px;height:30px">计算项目</TD>
						<TD style="WIDTH: 165px"><FONT face="宋体">
								<select tabindex="1" name="CalcType" id="CalcType" style="width:112px;">
									<option value="1">实得本息总额</option>
									<option value="2">提取金额</option>
									<option value="3">提取日期</option>
								</select>
							</FONT>
						</TD>
						<TD>
							<INPUT class="btn" id="btnCalc" onclick="return compute(1)" type="button" value=" 计算 "></TD>
					</TR>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px;height:30px">储蓄类型</TD>
						<TD style="WIDTH: 165px"><FONT face="宋体">
								<select tabindex="2" name="SaveType" id="SaveType" style="width:112px;">
									<option value="1">一天通知存款</option>
									<option value="7">七天通知存款</option>
								</select>
							</FONT>
						</TD>
					</TR>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px; HEIGHT: 30px">初始存入日期</TD>
						<TD style="WIDTH: 165px; ">
							<input tabindex="3" name="beginDate" class="txt" type="text" value="2002-2-21" id="beginDateID" style="width:87px;" onblur='ChkCZDate(beginDateID);'>
							<IMG SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(beginDateID)'>
							<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
						</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px;height:30px" id="lbdrawsum">提取金额(元)</TD>
						<TD style="WIDTH: 165px" id="tddrawsum">
							<input tabindex="4" name="edInitSaveSum" type="text" id="edInitSaveSum" class="txtnum" style="width:112px;" /></TD>
						<TD></TD>
					</TR>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px;height:30px" id="lbdrawdate">
							提取日期
						</TD>
						<TD style="WIDTH: 165px" id="tddrawdate">
							<input tabindex="5" name="drawDate" class="txt" type="text" value="2002-2-21" id="drawDateID" style="width:87px;" onblur='ChkCZDate(drawDateID);'>
							<IMG id="imgdd" SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(drawDateID)'>
							<script language='javascript'>
												function ChkCZDate(edit) {edit.value=Trim(edit.value);if(edit.value=='') return 
												true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
												{alert('日期格式不正确,日期有效范围为1910年到3000年'); edit.focus();} }</script>
						</TD>
						<TD><FONT face="宋体">&nbsp;</FONT></TD>
					</TR>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px;height:30px">年利率(%)</TD>
						<TD style="WIDTH: 165px">
							<input tabindex="6" name="edFullRate" type="text" id="edFullRate" class="txtnum" style="width:112px;" />
							&nbsp;</TD>
						<TD></TD>
					</TR>
					<tr>
						<td colspan="4">
							<hr>
						</td>
					</tr>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px;height:30px" id="lbfullsum">实得本息总额(元)</TD>
						<TD style="WIDTH: 165px" id="tdfullsum">
							<input tabindex="7" name="edFullSum" type="text" value="计算得出" id="edFullSum" class="txtd" style="width:112px;" />
							&nbsp;</TD>
						<TD></TD>
					</TR>
					<TR>
						<td></td>
						<TD style="WIDTH: 128px;height:30px">扣除利息税金额(元)</TD>
						<TD style="WIDTH: 165px">
							<input tabindex="8" name="edTaxSum" type="text" value="计算得出" id="edTaxSum" class="txtd" style="width:112px;" /></TD>
						<TD></TD>
					</TR>
				</TABLE>
			</div>
			<script language="javascript">
<!--
	var startdate =  new Array('2002-2-21','2002-2-21');
	var minterm =  new Array(1,7);
	//获得1天和7天存款利率
	var rate1;
	
	var rate7; 
	var rate;
		// -->
			</script>
		</form>
		<script language="javascript" src="../script/CheckDataFunction.js"></script>
		<script language="javascript" event="onclick" for="btnCalc">
		
				if (document.all.CalcType.selectedIndex!=1)
					if (!CheckFN3(edInitSaveSum,"提取金额请输入正数",false))
						return false;				
				if (!CheckFN3(edFullRate,"年利率请输入正数",false))				 
						return false;			
				if (document.all.CalcType.selectedIndex!=0)
					if (!CheckFN3(edFullSum,"实得本息总额请输入正数",false))
						return false;														
				if (!CheckEmpty(CompPartSumCalc.beginDateID,"无效的初始存入日期"))
					return false;	
				if (document.all.CalcType.selectedIndex!=2)
				 {
					if (!CheckEmpty(CompPartSumCalc.drawDateID,"无效的提取日期"))
						return false;	
					if ( Cal_strtodate(CompPartSumCalc.drawDateID.value)< Cal_strtodate("1999-11-1"))
					{
						DispMessage(CompPartSumCalc.drawDateID,"提取日期不得小于1999年11月1日");
						return false;
					}	
					var SaveDate=new Date();
					SaveDate.setTime(Cal_strtodate(CompPartSumCalc.beginDateID.value));								
					var AdvDrawDate=new Date();
					AdvDrawDate.setTime(Cal_strtodate(CompPartSumCalc.drawDateID.value));									
					var days=SaveType.selectedIndex==0 ? 1:7;
					addday(SaveDate,days);
					if (AdvDrawDate<SaveDate)
						{ 
							DispMessage(CompPartSumCalc.drawDateID,"提取日期不得小于初始存入日期+"+days+"天");
							return false;
						}								
				 }
				if ( Cal_strtodate(CompPartSumCalc.beginDateID.value)< Cal_strtodate("1998-7-1"))
				{
						DispMessage(CompPartSumCalc.beginDateID,"初始存入日期不得小于1998年7月1日");
						return false;
				}									
				return compute(document.all.CalcType.selectedIndex);
		</script>
		<script language="javascript" event="onchange" for="CalcType">
			calctypechange();
		</script>
		<script language="javascript" event="onchange" for="SaveType">
			savetypechange();
		</script>
		<script language="javascript">
			function changeobject(objectid)
			{
			  	   var ss1=document.all.tddrawsum.innerHTML;
				    var ss2=document.all.tdfullsum.innerHTML;
				    var ss3=document.all.tddrawdate.innerHTML;
				    
				    if (ss1.indexOf(objectid)>0)
				    {
				         ls=document.all.lbdrawsum.innerHTML;
				        document.all.lbdrawsum.innerHTML=document.all.lbfullsum.innerHTML;
    				    document.all.lbfullsum.innerHTML=ls;
    				    
    				    document.all.tddrawsum.innerHTML=document.all.tdfullsum.innerHTML;
    				    document.all.tdfullsum.innerHTML=ss1;
    			    }   
    				if (ss3.indexOf(objectid)>0)    
    				{
				        document.all.tddrawdate.innerHTML=document.all.tdfullsum.innerHTML;
    				    document.all.tdfullsum.innerHTML=ss3;
				        
				        ls=document.all.lbdrawdate.innerHTML;
    				    document.all.lbdrawdate.innerHTML=document.all.lbfullsum.innerHTML;
    				    document.all.lbfullsum.innerHTML=ls;
				    }
			
			}
			function calctypechange()
			{
					document.all.edFullSum.value="";
					document.all.edFullSum.className="txtnum";
					document.all.edFullSum.disabled=false;
					document.all.edInitSaveSum.value="";
					document.all.edInitSaveSum.className="txtnum";
					document.all.edInitSaveSum.disabled=false;
					if (CompPartSumCalc.drawDateID.value=="计算得出") 
					{
						var SaveDate=new Date();
						SaveDate.setTime(Cal_strtodate(CompPartSumCalc.beginDateID.value));													
						addday(SaveDate,7);					
						CompPartSumCalc.drawDateID.value=SaveDate.getFullYear()+"-"+(SaveDate.getMonth()+1)+"-"+SaveDate.getDate();
					 }
					CompPartSumCalc.drawDateID.disabled=false;
					
				if (document.all.CalcType.selectedIndex==0)
				{
				    changeobject("edFullSum");
					document.all.edFullSum.value="计算得出";
					document.all.edFullSum.className="txtd";
					document.all.edFullSum.disabled=true;
					document.all.imgdd.disabled=false;
					
				}
				else if (document.all.CalcType.selectedIndex==1)
				{
				    changeobject("edInitSaveSum");
					document.all.edInitSaveSum.value="计算得出";
					document.all.edInitSaveSum.className="txtd";
					document.all.edInitSaveSum.disabled=true;
					document.all.imgdd.disabled=false;
					//交换位置
				
				    
				    
				}
				else
				{
				    changeobject("drawDateID");
				    document.all.imgdd.disabled=true;
					CompPartSumCalc.drawDateID.value="计算得出";
					CompPartSumCalc.drawDateID.disabled=true;
				}
				
				
			}
			
			function loadinit()
			{
			 	rate1 = GetRMBSaveRatio(8,1,window.xmlRMBSaveRate.XMLDocument);
				rate7 = GetRMBSaveRatio(8,7,window.xmlRMBSaveRate.XMLDocument); 
				rate =  new Array(rate1/100,rate7/100);
				calctypechange();		
				document.all.edTaxSum.disabled=true;
				savetypechange();
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
			
			
			
			function savetypechange()
			{			   
				if (Trim(CompPartSumCalc.beginDateID.value)!="")
				{				    
					var arate=locaterate(Cal_strtodate(CompPartSumCalc.beginDateID.value),parseInt(document.all.SaveType.value));
					//alert(arate);
					if (arate!=null)
					 {					 
						document.all.edFullRate.value=Round(arate*100,2); //回显时，年利率要乘100
					 }
				}
			}
			
			function compute(type)
			{
				//fullsum	实得本息总额;
				//initsum	   提取金额;
				//SaveDate   初始存入日期;
				//AdvDrawDate  提取日期;
				
				if (type!=0)
					fullsum=parseFloat(document.all.edFullSum.value);				
				if (type!=1)
					initsum=parseInt(document.all.edInitSaveSum.value);				
				var SaveDate=new Date();
				SaveDate.setTime(Cal_strtodate(CompPartSumCalc.beginDateID.value));									
				//var calcu = new ActiveXObject("FinanceCalculator.ICalcInformSave");
				var AdvDrawDate = new Date();
				
				if (type!=2)
				{								
					var AdvDrawDate=new Date();
					AdvDrawDate.setTime(Cal_strtodate(CompPartSumCalc.drawDateID.value));														
					var diffday=getDiffDay(AdvDrawDate,SaveDate);
				}
				fullrate=parseFloat(document.all.edFullRate.value)/100;	//年利率要除100
				
				if (type==0)
				{
				 
				 CalcGetSum(document,fullrate,AdvDrawDate,SaveDate);
				 
				 //calcu.CalcGetSum(CompPartSumCalc.beginDateID.value,document.all.edInitSaveSum.value,CompPartSumCalc.drawDateID.value,document.all.edFullRate.value);
				 //document.all.edFullSum.value = calcu.Fullsum;
				 //document.all.edTaxSum.value = calcu.TaxSum;
				 //document.all.edFullSum.value=Round(initsum*(fullrate/360)* diffday * 0.8 + initsum);
				 //document.all.edTaxSum.value=Round(initsum*(fullrate/360)* diffday * 0.2);
				}
				else if (type==1)
					  {
					    CalcSaveSum(document,fullrate,AdvDrawDate,SaveDate);
						/*
						calcu.CalcSaveSum(CompPartSumCalc.beginDateID.value,document.all.edFullSum.value,CompPartSumCalc.drawDateID.value,document.all.edFullRate.value);
						document.all.edInitSaveSum.value=calcu.InitSum;						
						document.all.edTaxSum.value=calcu.TaxSum;*/
					  }
				else if (type==2)
					 {					
					   /* var interestRate = 0.05;  //利息税率    
						temp=Math.ceil((fullsum-initsum)/ ( (1-interestRate)*initsum*(fullrate/360) )); //取上整												
						datevaild=addday(SaveDate,temp);																	
						if ( (parseInt(document.all.SaveType.value) < temp) && (datevaild))
						{
						    /*
						    calcu.CalcGetDate(document.all.edInitSaveSum.value,document.all.edFullSum.value,CompPartSumCalc.beginDateID.value,CompPartSumCalc.SaveType.value,document.all.edFullRate.value);
						    if (calcu.Error!="") 
						    {
						      alert("计算出来的提取日期不符合实际情况");
						      return false;
						    }
							CompPartSumCalc.drawDateID.value=calcu.AdvdrawDate;
							document.all.edTaxSum.value=calcu.TaxSum;*/
						/*	CalcGetDate(document,SaveDate,initsum,fullrate,temp);
						}
						else 
						 {
							DispMessage(document.all.edInitSaveSum,"提取金额应为不大于到期本息和的正数");
							return false;
						 }*/
						 if(initsum>=fullsum)
							{
							  alert("提取金额应为不大于到期本息和的正数");
							}else
							{
							  CalcGetDate(document,fullrate,SaveDate,initsum,fullsum);
							}	
					 }	  
				 return false;
			}
			
			//window.attachEvent("onload",loadinit);
		</script>
		<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml" ondatasetcomplete="loadinit();"></xml>
	</body>
</HTML>
<script language="javascript">
  document.CompPartSumCalc.beginDateID.value=datetostring(new Date());
  document.CompPartSumCalc.drawDateID.value=datetostring(new Date());
   //GetCalc_Close();

</script>
