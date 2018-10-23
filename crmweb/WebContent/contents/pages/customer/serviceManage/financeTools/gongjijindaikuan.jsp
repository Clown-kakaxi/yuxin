<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>金融计算器_理财频道_MSN中国</title>
		<script>
  //window.resizeTo(535,404);  
		</script>
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
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</HEAD>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
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
<!--  <img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/>
-->
</noscript>

<!-- END WRating v1.0 -->
		<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="250" height="126"><img src="../Images/bg3.gif"></div>
		<div id="bg2"><img src="../Images/bg4.gif" width="25" height="130"><img src="../Images/bg5.gif" width="475" height="130"><img src="../Images/bg6.gif" width="25" height="130"></div>
		<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="475"><img src="../Images/bg9.gif"></div>  -->
		<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【公积金贷款计算器】</div>
		<div id="FDiv2" class="FDiv2" Movable="1" style="width:450">&nbsp;&nbsp;&nbsp;&nbsp;如果您每月缴了住房公积金，并且缴费年限在１年以上，我们建议您根据您的家庭收入和支出情况，利用住房公积金购买适合的房子。住房公积金的贷款年限上限为３０年。</div>
		<div id="FDiv3" class="FDiv3" style="WIDTH: 450" align="center">
			<form id="LoanAccumFund" method="post" runat="server">
				<table cellSpacing="0" cellPadding="0" width="100%" border="0">
					<tr>
						<td>
							<table cellSpacing="0" cellPadding="3" width="100%" border="0">
								<TR>
									<TD><FONT face="宋体">房款总额(元)</FONT></TD>
									<TD>
										<INPUT id="edfkze" name="edfkze" class="txtnum"></TD>
									<TD>贷款年限(年)</TD>
									<TD>
										<INPUT id="eddknx" name="eddknx" class="txtnum"></TD>
								</TR>
							</table>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="middle"><INPUT class="btn" id="btnCalc" tabIndex="10" type="button" value=" 计算 " name="btnCalc">
						</td>
					</tr>
					<tr>
						<td height="24"><hr style=" HEIGHT: 1px" width="95.29%" SIZE="1">
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing="0" cellPadding="3" width="100%" border="0">
								<TR>
									<TD><FONT face="宋体">首付款(元)</FONT></TD>
									<TD><FONT face="宋体"> <INPUT class="txt" readOnly size="16" id="edsfk" name="edsfk"></FONT></TD>
									<TD>利息率(%)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edlxl" name="edlxl"></TD>
								</TR>
								<TR>
									<TD>需要贷款(元)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edxydk" name="edxydk"></TD>
									<TD>保险费(元)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edbxf" name="edbxf"></TD>
								</TR>
								<TR>
									<TD>税费(元)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edsf" name="edsf"></TD>
									<TD>公证费(元)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edgzf" name="edgzf"></TD>
								</TR>
								<TR>
									<TD>一次性共付款(元)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edycgf" name="edycgf"></TD>
									<TD>每月付款(元)</TD>
									<TD>
										<INPUT class="txt" readOnly size="16" id="edmyfk" name="edmyfk"></TD>
								</TR>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
	<script language="javascript" event="onclick" for="btnCalc">
		<!--
		if (!CheckPN(this.document.all.edfkze,"请在房款总额中输入正整数",false))
		    return false;
		if(!CheckIntRange(this.document.all.eddknx,1,30,"贷款年限为1－30年！"))
					return false;
		CalResult();
		//-->
	</script>
	<script language="javascript">
	function CalResult()
	{
		var sfk,lxl,xydk,bxf,sf,gzf,ycgf,myfk;
		var fkze,dknx;
		var result,li;
		fkze=parseInt(this.document.all.edfkze.value);
		dknx=parseInt(this.document.all.eddknx.value);
		sfk=fkze*30/100;
		document.all.edsfk.value=new Number(sfk).toFixed(2);
		xydk=fkze*70/100;
		document.all.edxydk.value=new Number(xydk).toFixed(2);
		bxf=dknx*fkze*1/1000;
		document.all.edbxf.value=new Number(bxf).toFixed(2);
		sf=xydk*0.05/100;
		document.all.edsf.value=new Number(sf).toFixed(2);
		gzf=xydk*3/1000;
		document.all.edgzf.value=new Number(gzf).toFixed(2);
		ycgf=sfk+bxf+sf+gzf;
		document.all.edycgf.value=new Number(ycgf).toFixed(2);
		
		if(dknx<=5)
		{
			li=3.6/(100*12);
			var temp=Math.pow((1+li),(dknx*12));
			lxl=3.6;
			myfk=(xydk*li*temp)/(temp-1);			
		}
		else
		{
			li=4.05/(100*12);
			lxl=4.05;
			var temp=Math.pow((1+li),(dknx*12));
			myfk=(xydk*li*temp)/(temp-1);
		}
		document.all.edlxl.value=new Number(lxl).toFixed(2);
		document.all.edmyfk.value=new Number(myfk).toFixed(2);
	}	
	</script>
	<script language="javascript">
    //GetCalc_Close();
	</script>
</HTML>
