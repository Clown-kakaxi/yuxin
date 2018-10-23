<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>金融计算器_理财频道_MSN中国</title>
		<script>
  //window.resizeTo(460,474);
		</script>
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
		<!--  <div id="bg1"><img src="../Images/bg1.gif"><img src="../Images/bg2.gif" width="175" height="126"><img src="../Images/bg3.gif"></div>
		<div id="bg2"><img src="../Images/bg4.gif" width="25" height="200"><img src="../Images/bg5.gif" width="400" height="200"><img src="../Images/bg6.gif" width="25" height="200"></div>
		<div id="bg3"><img src="../Images/bg7.gif"><img src="../Images/bg8.gif" height="119" width="400"><img src="../Images/bg9.gif"></div>-->
		<div id="FDiv1" class="FDiv1" Movable="1" style="color:black">【人民币存款利率表】</div>
		<div id="FDiv2" class="FDiv2" Movable="1" style="width:380">可查询各种类型、期限的人民币存款利率。</div>
		<script language="javascript">
		function SetContent(oTable, xmlDoc)
		{
			var oRow;
			var oCell;
			var xmlroot = xmlDoc.documentElement;
			var tmpCell;
			for(i=0; i<xmlroot.childNodes.length;  i++)
			{
			  var beginterm = xmlroot.childNodes.item(i).childNodes.item(2).text;
			  var endterm = xmlroot.childNodes.item(i).childNodes.item(3).text;
			  var strterm = "";
			  
			  if (beginterm == 0 && endterm == 0)
			  {
			    strterm = "";
			  }
			  
			  //非通知存款
			  if (beginterm == 0 && endterm > 0 && xmlroot.childNodes.item(i).childNodes.item(4).text!="8")
			  {
			    strterm = GetHowLong(xmlroot.childNodes.item(i).childNodes.item(3).text) +"期";
			  }
			  if (beginterm == 0 && endterm > 0 && xmlroot.childNodes.item(i).childNodes.item(4).text=="8")
			  {
			    
			    strterm =  GetChinaNumber(xmlroot.childNodes.item(i).childNodes.item(3).text) +"天期";
			  }
			  if (beginterm >0 && endterm==0)
			  { 
			    strterm = GetHowLong(xmlroot.childNodes.item(i).childNodes.item(2).text) +"以上";
			  }
			  if (beginterm >0 && endterm>0)
			  {
			    strterm = GetHowLong(xmlroot.childNodes.item(i).childNodes.item(2).text) +"至"+GetHowLong(xmlroot.childNodes.item(i).childNodes.item(3).text);
			  }
			  oRow = oTable.insertRow();			  
			  oCell = oRow.insertCell();
			  oCell.innerText = xmlroot.childNodes.item(i).childNodes.item(5).text +strterm;
			  tmpCell = oCell.innerText;
			  oCell = oRow.insertCell();
			  if(tmpCell == '定活两便')
				oCell.innerHTML = '按一年以内定期整存整取<br>同档次利率打6折';
			  else
				oCell.innerText = xmlroot.childNodes.item(i).childNodes.item(7).text;
			  if (i%2 ==0)
			    oRow.style.backgroundColor="#DCEEF2";
			  else
			    oRow.style.backgroundColor="#E6E8DB";
			}
		}
		</script>
		<form name="fmSaveRate" id="fmSaveRate">
			<div id="FDiv3" class="FDiv3" style="WIDTH: 400;height:280" align="center">
				<div class="scdiv" style="height:260">
					<table cellspacing="0" rules="all" bordercolor="#638B97" border="1" id="DataGrid1" style="border-color:#638B97;border-collapse:collapse;">
						<tr align="Center" style="color:White;background-color:#9CAEBA;">
							<td style="width:170px;">项目</td>
							<td style="width:130px;">年利率(%)</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<script>
     //GetCalc_Close();
		</script>
		<xml id="xmlRMBSaveRate" src="http://localhost:8080/CRM/scripts/financeTools/RMBSaveRate.xml" ondatasetcomplete="SetContent(window.DataGrid1, window.xmlRMBSaveRate.XMLDocument);"></xml>
	</body>
</html>
