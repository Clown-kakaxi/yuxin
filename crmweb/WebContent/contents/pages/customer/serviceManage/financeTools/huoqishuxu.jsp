<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>金融计算器_理财频道_MSN中国</title>
		<script>
  //window.resizeTo(535,504);
		</script>
	<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WinControl.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calendar.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calculator.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WBselect.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/GetRate.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Components.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>
		<SCRIPT type="text/JavaScript" src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcActiveSave.js" language="JavaScript"> </SCRIPT>
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
		</HEAD>
	<body leftMargin="0" topMargin="0" rightMargin="0">
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
<!--  
<noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
-->
<!-- END WRating v1.0 -->
	<!--  
		<DIV><IMG src="../Images/bg1.gif"><IMG height="126" src="../Images/bg2.gif" width="250"><IMG src="../Images/bg3.gif"></DIV>
		<div id="bg2"><IMG height="230" src="../Images/bg4.gif" width="25"><IMG height="230" src="../Images/bg5.gif" width="475"><IMG height="230" src="../Images/bg6.gif" width="25"></div>
		<div id="bg3"><IMG src="../Images/bg7.gif"><IMG height="119" src="../Images/bg8.gif" width="475"><IMG src="../Images/bg9.gif"></div>
		-->	
		<div class="FDiv1" id="FDiv1" style="WIDTH: 300px" Movable="1" style="color:black">【活期储蓄计算器】</div>
		<div class="FDiv2" id="FDiv2" style="WIDTH: 450px" Movable="1">活期储蓄是客户家庭持有现金的一种常用方式。通过本计算器，可以计算出活期储蓄的实得本息总额（已扣除利息税）和提取日期。
		</div>

		<div class="FDiv3" id="FDiv3" style="WIDTH: 460px" align="center">
			<form id="CompPartSumCalc" name="CompPartSumCalc">
				<TABLE id="Table1" style="HEIGHT: 164px" cellSpacing="1" cellPadding="1" width="450" border="0" align="center">
					<TR>
						<TD align="left" width="71" height="22">计算项目</TD>
						<TD colSpan="4" height="22"><input type="radio" id="CalcType_1" name="CalcType" checked onclick="typechange()" language="javascript">实得本息总额
							<input type="radio" id="CalcType_2" name="CalcType" onclick="typechange()" language="javascript">提取日期
						</TD>
					</TR>
					<TR>
						<TD align="left" height="22">存入日期</TD>
						<TD height="22" width="193"><input class="txt" id="beginDateID" onblur="ChkCZDate(beginDateID)" type="text" style="WIDTH:85px" value="2001-1-1" name="beginDate"><IMG style="CURSOR: hand" onclick="javascript:Cal_dropdown(beginDateID)" src="images/Calendar1.gif" align="absMiddle">
						</TD>
					</TR>
					<TR>
						<TD align="left" width="89" height="22">存入金额(元)</TD>
						<TD width="84" height="22"><FONT face="宋体"><INPUT class="txtnum" id="edSaveSum" style="WIDTH: 80px" type="text" name="edSaveSum"></FONT></TD>
						<td colspan="2"><input class="btn" id="btnSaveIn" title="可以录入多笔存款" style="WIDTH: 63px; HEIGHT: 20px" type="button" value=" 确定存入 " name="Button1"></td>
					</TR>
					<TR>
						<TD align="middle" colSpan="4" height="96">
							<DIV class="scdiv" style="WIDTH: 300px; HEIGHT: 70px" align="center">
								<TABLE id="TableView" style="TEXT-ALIGN: center" cellSpacing="0" cellPadding="1" width="300" border="0">
									<TR>
										<TD width="148" bgcolor="#0099cc"><FONT face="宋体">存入日期</FONT></TD>
										<TD bgcolor="#0099cc"><FONT face="宋体">存入金额(元)</FONT></TD>
									</TR>
								</TABLE>
							</DIV>
						</TD>
					</TR>
				</TABLE>
				<table width="450" border="0" align="center">
					<tr>
						<TD align="left" height="22"><FONT face="宋体">年利率(%)</FONT></TD>
						<TD align="left" height="22"><INPUT class="txtnum" id="edFullRate" style="WIDTH: 110px" type="text" name="edFullRate"></TD>
					</tr>
					<TR>
						<TD align="left" width="110" height="22" id="lbTQRQ">提取日期</TD>
						<TD height="22" width="160" id="TQRQ"><INPUT class="txt" id="endDateID" onblur="ChkCZDate(endDateID)" type="text" style="WIDTH:85px" value="2001-1-1" name="endDateID"><IMG id="ddimg" style="CURSOR: hand" onclick="javascript:Cal_dropdown(endDateID)" src="images/Calendar1.gif" align="absMiddle">
						</TD>
						<td><input class="btn" id="btnCalc" style="WIDTH: 63px; HEIGHT: 20px" type="button" value=" 计算 " name="btnCalc"></td>
					</TR>
				</table>
				<HR style="WIDTH: 100%; HEIGHT: 1px" SIZE="1">
				<table ID="Table2" width="314" border="0" align="center">
					<TR>
						<TD width="129" height="22" id="lbSDBX">
							实得本息总额(元)</TD>
						<TD width="175" id="SDBX"><input class="txtd" id="edFullSum" style="WIDTH: 112px" type="text" value="计算得出" name="edFullSum"></TD>
					</TR>
					<TR>
						<TD height="22">存入本金总额(元)</TD>
						<TD><input class="txtd" id="edSaveTotal" style="WIDTH: 112px" type="text" value="计算得出" name="edSaveTotal"></TD>
					</TR>
					<TR>
						<TD height="22">扣除利息税金额(元)</TD>
						<TD><FONT face="宋体"> <input class="txtd" id="edTaxSum" style="WIDTH: 112px" type="text" value="计算得出" name="edTaxSum">
							</FONT>
						</TD>
					</TR>
				</table>
			</form>
			<br>
		</div>
		<SCRIPT language="javascript">
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</SCRIPT>
		<script language="javascript">
<!--
	var startdate =  new Array('2002-2-21');
	var activerate;
	
	var savedatearray =  new Array();
	var savesumarray =  new Array();
	function GetTotalSaveSum() // 获取用户点击“确定存入”向表格中添加的总共存入金额
	{
		var i;
		var s = new Number(0);
		for ( i = 0; i < savesumarray.length; i++ )
		{
			s += new Number(savesumarray[i]);
		}
		return s;
	}
		// -->
		</script>
		<script language="javascript" src="../Script/CheckDataFunction.js"></script>
		<script language="javascript" event="onclick" for="btnSaveIn">
			//在存入之前，初始存入日期非空且大于等于1999-11-1，金额也要合法
			if (!CheckEmpty(beginDateID,"无效的初始存入日期"))
					return false;	
			if ( Cal_strtodate(beginDateID.value)< Cal_strtodate("1999-11-1") )  
			{
					DispMessage(beginDateID,"初始存入日期不得小于1999年11月1日");
					return false;
			}									
			if (!CheckFN3(edSaveSum,"存人金额请输入正数",false))				 
					return false;			
			savedatearray.push(beginDateID.value);		
			savesumarray.push(edSaveSum.value);		
			
			InsertRecord(beginDateID.value,edSaveSum.value);			
			edSaveSum.value="";
			beginDateID.select();
			beginDateID.focus();
		</script>
		<script language="javascript" event="onclick" for="btnCalc">						
			if (savedatearray.length==0) //如果数组中存入有数据，则不校验这两项(点存人按钮时校验了)
			{
				if (!CheckEmpty(beginDateID,"无效的初始存入日期"))
					return false;	
				
				if ( Cal_strtodate(beginDateID.value)< Cal_strtodate("1999-11-1")  )
				{
					DispMessage(beginDateID,"初始存入日期不得小于1999年11月1日");
					return false;
				}									
				if (!CheckFN3(edSaveSum,"存人金额请输入正数",false))
						return false;			
			}	
			else
			{
			 if (document.all.CalcType_1.checked==true) //计算选项不是计算日期时，校验
				for (var i=0; i<savedatearray.length;i++)
				{
					if ( Cal_strtodate(endDateID.value)<=Cal_strtodate(savedatearray[i]))
					{
						DispMessage(endDateID,"你存入的初始存入日期大于或等于提取日期,请修改");
						return false;
					}	
				}		
			}			
			
			if (!CheckFN3(edFullRate,"年利率请输入正数",false,null,4))				 
					return false;		
			
			if (document.all.CalcType_1.checked==true)
			{
				if (!CheckEmpty(endDateID,"无效的提取日期"))
					return false;	
				if (savedatearray.length==0)
					if ( Cal_strtodate(beginDateID.value)>=Cal_strtodate(endDateID.value)&& (document.all.CalcType_1.checked==true) )
					{
						DispMessage(endDateID,"初始存入日期不得大于或等于提取日期");
						return false;
					}						
			}					 	
			if (document.all.CalcType_2.checked==true)
			{
				if (!CheckFN3(edFullSum,"实得本息总额请输入正数",false))
					return false;
				if (GetTotalSaveSum() > parseFloat(edFullSum.value))
				{
					DispMessage(edFullSum, "实得本息总额应大于等于存入金额（之和）！");
					return false;
				}
			}
			
			if (document.all.CalcType_1.checked==true)
				computefullsum(document);										
		    else
				computeoutdate(document);
			
			document.all.edTaxSum.value=Round((parseFloat(document.all.edFullSum.value)-parseFloat(document.all.edSaveTotal.value))/4);				  	
			
			DeleteAll();				
		</script>
		<script language="javascript">
		function typechange()
		{
			calctypechange();
			DeleteAll();
		}	
		</script>
		<script language="javascript">			
			function getmaxsavedate()
			{
				var result=savedatearray[0];
				for (var i=0; i<savedatearray.length;i++)
				{
				 if (i<savedatearray.length-1)
					if ( Cal_strtodate(result)<Cal_strtodate(savedatearray[i+1]))
						result=savedatearray[i+1];
				}		
				return result;		
			}

			function InsertRecord(date,sum)
			{
				var tr=document.all.TableView.insertRow();
				var td1=tr.insertCell();
				td1.innerText=date;
				var td2=tr.insertCell();
				td2.innerText=sum;
			}
			
			function DeleteAll()
			{
				savesumarray.splice(0,savesumarray.length);
				savedatearray.splice(0,savedatearray.length);								
				len=document.all.TableView.rows.length;	
				if (len>1)
				for (var i=1; i<len;i++)
				{				
						document.all.TableView.deleteRow(1);
				}
			}
		
			function changeobject(objectid)
			{
			  	    var ss1=document.all.TQRQ.innerHTML;
				    var ss2=document.all.SDBX.innerHTML;
				    
				    if (ss1.indexOf(objectid)>0)
				    {
				         ls=document.all.lbTQRQ.innerHTML;
				        document.all.lbTQRQ.innerHTML=document.all.lbSDBX.innerHTML;
    				    document.all.lbSDBX.innerHTML=ls;
    				    
    				    document.all.TQRQ.innerHTML=document.all.SDBX.innerHTML;
    				    document.all.SDBX.innerHTML=ss1;
    			    }   
			}
		
			function calctypechange()
			{
					document.all.edFullSum.value="";
					document.all.edFullSum.className="txtnum";
					document.all.edFullSum.disabled=false;
					if (document.all.endDateID.value=="计算得出") 
					{
						var SaveDate=new Date();
						document.all.endDateID.value=SaveDate.getFullYear()+"-"+(SaveDate.getMonth()+1)+"-"+SaveDate.getDate();
					 }
					document.all.endDateID.disabled=false;
					
					if (document.all.CalcType_1.checked==true)
					{
					    changeobject("edFullSum");
					    document.all.ddimg.disabled=false;
						document.all.edFullSum.value="计算得出";
						document.all.edFullSum.className="txtd";
						document.all.edFullSum.disabled=true;
					}
					else
					{
					    changeobject("endDateID");
					    document.all.ddimg.disabled=true;
						document.all.endDateID.value="计算得出";
						document.all.endDateID.disabled=true;
						document.all.edFullRate.value=document.all.edFullRate.value=NBround(activerate[activerate.length-2]*100,4); //最新利率,叔祖结束标志-1。
					}
					document.all.edSaveTotal.value="计算得出";
					document.all.edTaxSum.value="计算得出";					
			}
			
			function loadinit()
			{
			//	activerate =  new Array(GetRMBSaveRatio("1","0",window.xmlRMBSaveRate.XMLDocument)/100,-1);			
				activerate =  new Array(0.36/100,-1);
				calctypechange();		
				document.all.edSaveTotal.disabled=true;
				document.all.edTaxSum.disabled=true;
				dpAdvDateBlur();				
			} 
			
			function locaterate(sdate)
			{			 
			 for (var i=0; i<startdate.length;i++)
			  if ( sdate>=Cal_strtodate(startdate[i]))
			  {
			    if ( (i<startdate.length-1)&&(sdate<Cal_strtodate(startdate[i+1])) )
					return activerate[i];
				else if (i==startdate.length-1)	
					 return activerate[i];
			  }  
				return null;
			}
			
			function dpAdvDateBlur()
			{			   
				if (Trim(document.all.endDateID.value)!="")
				{				    
					var arate=locaterate(Cal_strtodate(document.all.endDateID.value));
					if (arate!=null)
					 {					 
						document.all.edFullRate.value=Round(arate*100,4); //回显时，年利率要乘100
					 }
				}
			}
		</script>
		<script language="javascript">
document.CompPartSumCalc.beginDateID.value=datetostring(new Date());
document.CompPartSumCalc.endDateID.value=datetostring(new Date());
loadinit();
var Savelist="";		
		  //GetCalc_Close();
		
		</script>
		<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml" ondatasetcomplete="loadinit()"></xml>
	</body>
</HTML>
