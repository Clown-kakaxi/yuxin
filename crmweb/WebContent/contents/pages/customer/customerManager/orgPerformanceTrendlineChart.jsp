<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
        <link href="../assets/prettify/prettify.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            body { background:none !important }
        </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerDistribution/Charts/FusionCharts.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/organizationChart/json2.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/organizationChart/prettify.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/orgPerformanceTrendlineChart.js"></script>
        
<!--         <script type="text/javascript" src="../tool/gallery_helper.js"></script> -->

		<script type="text/javascript">
        	var temp = 1;     

        	function load() {
        		var chart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", temp++, "100%", "100%", "0", "0");
        		//alert(document.getElementById("startLoanYear1").value+"************"+document.getElementById("startLoanMonth1").value);
        		Ext.Ajax.request({
					url : basepath+'/orgPerformanceTrendline.json?startLoanYear=' + document.getElementById("startLoanYear1").value + '&startLoanMonth=' + document.getElementById("startLoanMonth1").value+'&condition=' + document.getElementById("condition1").value+'&crmDtEnd=' + document.getElementById("crmDtEnd").value,
					method : 'GET',
					success : function(response) {
						var dataXml = Ext.util.JSON.decode(response.responseText).dataXml;
						chart.setDataXML(dataXml);
	            		chart.render("chartdiv");
					},
					failure : function(response) {
					}
				});
        	}
        	
        </script>
</head>
<body onload="setTimeout('load()', 1000)">
		<input type="hidden" id="startLoanYear1" value="2011" /> 
		<input type="hidden" id="startLoanMonth1" value="12" />
		<input type="hidden" id="condition1" value="1" />
		<input type="hidden" id="crmDtEnd1" value="" />
</body>
</html>