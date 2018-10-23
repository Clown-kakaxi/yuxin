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
		<LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
	</head>
	<body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
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
		<!-- <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="275" height="126"><img src="../Images/bg3.gif"></div>
		<div id="bg2"><img src="../Images/bg4.gif" width="25" height="200"><img src="../Images/bg5.gif" width="500" height="200"><img src="../Images/bg6.gif" width="25" height="200"></div>
		<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="500"><img src="../Images/bg9.gif"></div> -->
		<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【外币存款利率表】</div>
		<div id="FDiv2" class="FDiv2" Movable="1" style="width:500">可查询各种类型、期限的外汇存款利率。</div>
		<div id="FDiv3" class="FDiv3" style="WIDTH: 400;height:280" align="center">
			<script language="javascript">
		function SetTitle(oTable, xmlDoc)
		{
			var oRow = oTable.insertRow();
			var oCell;
			var xmlroot = xmlDoc.documentElement; 
			var moneyname="";
		  
			for(i=0; i<xmlroot.childNodes.length; i++)
			{
				if (moneyname != xmlroot.childNodes.item(i).childNodes.item(3).text&&i!=0)
					break;
    
				if (i==0)
				{
					oCell = oRow.insertCell();
					oCell.innerText = " ";
					moneyname = xmlroot.childNodes.item(i).childNodes.item(3).text;  
				}
				oCell = oRow.insertCell();
				oCell.innerText = xmlroot.childNodes.item(i).childNodes.item(1).text;
		     
			}
		}
			
		function SetContent(oTable, xmlDoc)
		{
			var xmlroot = xmlDoc.documentElement; 
			var oRow;
			var oCell;
			var moneyname="";

			for(i=0; i<xmlroot.childNodes.length; i++)
			{
				if (moneyname != xmlroot.childNodes.item(i).childNodes.item(3).text)
				{
					oRow = oTable.insertRow();
					oCell = oRow.insertCell();
					oCell.innerText = xmlroot.childNodes.item(i).childNodes.item(3).text;
					moneyname = xmlroot.childNodes.item(i).childNodes.item(3).text;
		 		}
				oCell = oRow.insertCell();
				oCell.innerText = xmlroot.childNodes.item(i).childNodes.item(7).text;
			}
		}
			</script>
			<form name="fmSaveRate" id="fmSaveRate">
				<div class="scdiv" style="height:260">
					<table ID="Table1" cellspacing="0" rules="all" bordercolor="Navy" border="1" style="border-color:Navy;border-width:1px;border-style:Solid;width:460;border-collapse:collapse;">
						<script language="javascript">
						function init()
						{
							SetTitle(window.Table1, window.xmlForeignSaveRate.XMLDocument);
							SetContent(window.Table1, window.xmlForeignSaveRate.XMLDocument);
						}
						</script>
					</table>
				</div>
			</form>
		</div>
		<script>
     //GetCalc_Close();
		</script>
	<!--  	<xml id="xmlForeignSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/ForeignSaveRate.xml" ondatasetcomplete="init();"></xml>
	-->
		<xml id="xmlForeignSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/ForeignSaveRate.xml" ondatasetcomplete="init();"></xml>
	

	
	</body>
</html>
