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
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcDingHuo.js" language="JavaScript"> </SCRIPT>

	<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
			<script language="javascript">

			function GetRate()
			{
				//获得年利率
				var valstartdate=Cal_strtodate(this.document.all.beginDateID.value);
				var valenddate=Cal_strtodate(this.document.all.endDateID.value);			
				var dayMi=24*60*60*1000;
				var months=(valenddate-valstartdate)/dayMi/30;
				var month=parseInt(months);
				if (month<0)
				  return 0;
				if (month<3)
				  return GetRMBSaveRatio(1,"0",window.xmlRMBSaveRate.XMLDocument)/100;
				else  
				  return GetRMBSaveRatio(7,month,window.xmlRMBSaveRate.XMLDocument)/100;
				
			}
			
			function changeobject(objectid)
			{
			  	    var ss1=document.all.CSCRJE.innerHTML;
				    var ss2=document.all.DQBX.innerHTML;
				    var ss3=document.all.TQRQ.innerHTML;
				    
				    if (ss1.indexOf(objectid)>0)
				    {
				         ls=document.all.lbCSCRJE.innerHTML;
				        document.all.lbCSCRJE.innerHTML=document.all.lbDQBX.innerHTML;
    				    document.all.lbDQBX.innerHTML=ls;
    				    
    				    document.all.CSCRJE.innerHTML=document.all.DQBX.innerHTML;
    				    document.all.DQBX.innerHTML=ss1;
    			    }   
    				if (ss3.indexOf(objectid)>0)    
    				{
				        document.all.TQRQ.innerHTML=document.all.DQBX.innerHTML;
    				    document.all.DQBX.innerHTML=ss3;
				        
				        ls=document.all.lbTQRQ.innerHTML;
    				    document.all.lbTQRQ.innerHTML=document.all.lbDQBX.innerHTML;
    				    document.all.lbDQBX.innerHTML=ls;
				    }
			
			}
			
			
		    function funcontrol()
		    {
		    	if (this.document.all.rdselect_0.checked)
				{
				    changeobject("edend");
					this.document.all.edend.value="计算得出";
					this.document.all.edend.disabled=true;
					
					this.document.all.edend.className="txtd";
					this.document.all.endDateID.className="txt";
					this.document.all.edstart.className="txtnum";
					this.document.all.imgdd.disabled=false;
					this.document.all.edtax.value="计算得出";
					this.document.all.edtax.disabled=true;
					
					this.document.all.edstart.value="";
					this.document.all.edstart.disabled=false;
					this.document.all.endDateID.disabled=false;
					var d=new Date();
					this.document.all.endDateID.value=datetostring(d);
					//this.document.all.edRate.value="0.72";
					dateout();

				}
				else if (this.document.all.rdselect_1.checked)
				{
				   changeobject("edstart");
				    this.document.all.edend.className="txtnum";
					this.document.all.endDateID.className="txt";
					this.document.all.edstart.className="txtd";
					this.document.all.imgdd.disabled=false;
					this.document.all.edstart.value="计算得出";
					this.document.all.edstart.disabled=true;
					this.document.all.edtax.value="计算得出";				
					this.document.all.edtax.disabled=true;
					this.document.all.edend.value="";
					this.document.all.edend.disabled=false;
					this.document.all.endDateID.disabled=false;
					var d=new Date();
					this.document.all.endDateID.value=datetostring(d);
					//this.document.all.edRate.value="0.72";
					dateout();
				}
				else if (this.document.all.rdselect_2.checked)
				{
				    changeobject("endDateID");
				    this.document.all.edend.className="txtnum";
					this.document.all.endDateID.className="txtd";
					this.document.all.edstart.className="txtnum";
					this.document.all.imgdd.disabled=true;
					this.document.all.edstart.value="";
					this.document.all.edstart.disabled=false;
					this.document.all.edtax.value="计算得出";			
					this.document.all.edtax.disabled=true;
					this.document.all.edend.value="";
					this.document.all.edend.disabled=false;
					this.document.all.endDateID.value="计算得出";
					this.document.all.endDateID.disabled=true;
					
					dateout();
				
				}
		    
		    }
		    function CheckData()
		    {
				return true;
		    }
		    function CheckData1()
		    {
				if (!CheckFN3(this.document.all.edstart,"请在[初始存入金额]中输入正数",false))
				return false;
				if (!CheckFN3(this.document.all.edRate,"请在[利率]中输入正数",false))
				return false;
				if (!CheckEmpty(this.document.all.beginDateID,"请在[初始存入日期]中输入日期格式"))
				return false;
				if (!CheckEmpty(this.document.all.endDateID,"请在[提取日期]中输入日期格式"))
				return false;
				if(!CheckDiffDate(this.document.all.beginDateID,this.document.all.endDateID,"初始存入日期应该小于等于提取日期！"))
					return false;
				
				return true;	
		    }
		    function CheckData2()
		    {
				if (!CheckFN(this.document.all.edend,"请在[到期本息总额]中输入正数",null,2))
				return false;
				if (!CheckFN3(this.document.all.edRate,"请在[利率]中输入正数",false))
				return false;
				if (!CheckEmpty(this.document.all.beginDateID,"请在[初始存入日期]中输入日期格式"))
				return false;
				if (!CheckEmpty(this.document.all.endDateID,"请在[提取日期]中输入日期格式"))
				return false;
				if(!CheckDiffDate(this.document.all.beginDateID,this.document.all.endDateID,"初始存入日期应该小于等于提取日期！"))
				return false;
				
				return true;
		    }
		    function CheckData3()
		    {
				if (!CheckFN3(this.document.all.edstart,"请在[初始存入金额]中输入正数",false))
				return false;

				if (!CheckFN3(this.document.all.edend,"请在[到期本息总额]中输入正数",false))
				return false;
				if (!CheckFN3(this.document.all.edRate,"请在[利率]中输入正数",false))
				return false;
				if (!CheckEmpty(this.document.all.beginDateID,"请在[初始存入日期]中输入日期格式"))
				return false;
				if (parseFloat(document.all.edstart.value) > parseFloat(document.all.edend.value))
				{
					DispMessage(document.all.edstart, "初始存入金额应小于等于到期本息总额");
					return false;
				}
				return true;
		    }
		    
			function calc()
			{


				if (!CheckData()) return false;
				if (this.document.all.rdselect_0.checked)
				{
				    
					if (!CheckData1()) return false;
					calc1(document);
				}
				else if (this.document.all.rdselect_1.checked)
				{
					if (!CheckData2()) return false;
					calc2(document);
				}
				else if (this.document.all.rdselect_2.checked)
				{
				   if (!CheckData3()) return false;	
				   calc3(document);
				}
				
				
				
			return true;
			}
			
			function dateout()
			{
				this.document.all.edRate.value=GetRate()*100;
			}
			</script>
			<script language="javascript" event="onclick" for="rdselect_0">
			funcontrol();
		
			</script>
			<script language="javascript" event="onclick" for="rdselect_1">
			funcontrol();
		
			</script>
			<script language="javascript" event="onclick" for="rdselect_2">
			funcontrol();
		
			</script>
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
		<form name="WebForm4" id="WebForm4" onsubmit="return false;">
			<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="300" height="126"><img src="../Images/bg3.gif"></div>
			<div id="bg2"><img src="../Images/bg4.gif" width="25" height="220"><img src="../Images/bg5.gif" width="525" height="220"><img src="../Images/bg6.gif" width="25" height="220"></div>
			<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="525"><img src="../Images/bg9.gif"></div> -->
			<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【定活两便计算器】</div>
			<div id="FDiv2" class="FDiv2" Movable="1" style="width:520">定活两便存款的优点在于兼顾了资金运用的收益性和灵活性。本计算器可方便的计算出定活两便存款的到期本息总额（已扣除利息税）、初始存入金额和储蓄存期。</div>
			<DIV id="FDiv3" class="FDiv3" style="WIDTH: 500px; " align="center">
				<TABLE id="Table1" style="WIDTH: 430px; HEIGHT: 307px" cellSpacing="1" cellPadding="1" width="430" border="0">
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 34px">计算项目</TD>
						<TD style="HEIGHT: 34px" colspan="2"><table id="rdselect" border="0" style="width:300px;">
								<tr>
									<td><input id="rdselect_0" type="radio" name="rdselect" value="1" checked="checked" />
										<label for="rdselect_0">到期本息总额</label></td>
									<td><input id="rdselect_1" type="radio" name="rdselect" value="2" />
										<label for="rdselect_1">初始存入金额</label></td>
									<td><input id="rdselect_2" type="radio" name="rdselect" value="3" />
										<label for="rdselect_2">提取日期</label></td>
								</tr>
							</table>
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 33px" id="lbCSCRJE">初始存入金额</TD>
						<TD style="HEIGHT: 33px" id="CSCRJE"><INPUT class="txtnum" id="edstart" type="text" name="edstart1"></TD>
						<td><INPUT tabindex="20" class="btn" id="btnenter" onclick="javascript:calc();" type="button" value=" 计算 "></td>
					</TR>
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 36px">初始存入日期</TD>
						<TD style="HEIGHT: 36px"><input name="beginDate" class="txt" type="text" value="2001-1-1" id="beginDateID" size="15" onblur='ChkCZDate(beginDateID);dateout(beginDateID)'>
							<IMG SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(beginDateID)'>
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 36px" id="lbTQRQ">提取日期</TD>
						<TD style="HEIGHT: 36px" colspan="2" id="TQRQ"><input name="endDate" class="txt" type="text" value="2001-1-1" id="endDateID" size="15" onblur='ChkCZDate(endDateID);dateout(endDateID);'>
							<IMG id="imgdd" SRC="images/Calendar1.gif" style='cursor:hand' align="AbsMiddle" onclick='javascript:Cal_dropdown(endDateID)'>
						</TD>
					</TR>
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 36px">利率(%)</TD>
						<TD style="HEIGHT: 36px" colspan="2"><input name="edRate" type="text" value="0.72" id="edRate" class="txtnum" /></TD>
					</TR>
					<tr>
						<td colspan="3">
							<hr>
						</td>
					</tr>
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 35px" id="lbDQBX">到期本息总额（元）</TD>
						<TD style="HEIGHT: 35px" colspan="2" id="DQBX"><INPUT class="txtnum" id="edend" type="text"></TD>
					</TR>
					<TR>
						<TD style="WIDTH: 116px; HEIGHT: 37px">扣除利息税金额</TD>
						<TD style="HEIGHT: 37px" colspan="2"><INPUT class="txt" id="edtax" type="text" style="TEXT-ALIGN: right"></TD>
					</TR>
					<TR>
						<TD colspan="3" align="middle" height="30"></TD>
					</TR>
				</TABLE>
				<script language='javascript'> 
var strcount=4;
				</script>
			</DIV>
		</form>
		<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml" ondatasetcomplete="funcontrol();"></xml>
	</body>
</HTML>
<script language='javascript'>
function ChkCZDate(edit)
{edit.value=Trim(edit.value);if(edit.value=='') return true;if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
{alert('日期格式不正确,日期有效范围为1910年到3000年');
edit.focus();}
 }</script>
<script language="javascript">
document.WebForm4.beginDateID.value=datetostring(new Date());
document.WebForm4.endDateID.value=datetostring(new Date());
//GetCalc_Close();
</script>
