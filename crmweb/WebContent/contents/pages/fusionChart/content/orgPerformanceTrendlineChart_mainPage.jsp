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
        <script type="text/javascript" src="../tool/FusionCharts.js"></script>
        <script type="text/javascript" src="../tool/json2.js"></script>
        <script type="text/javascript" src="../tool/prettify.js"></script>
        <script type="text/javascript" src="orgPerformanceTrendlineChart_mainPage.js"></script>
        
<!--         <script type="text/javascript" src="../tool/gallery_helper.js"></script> -->

		<script type="text/javascript">
        	var temp = 1;
//        	function load() {
//        		var chart = new FusionCharts(basepath+"/contents/pages/fusionChart/tool/MSLine.swf", temp++, "100%", "100%", "0", "0");
//        		var url = basepath+'/orgPerformanceTrendline?customerId=' + parent.location.href.split("customerId=")[1] + '&crmDtStart=' + document.getElementById("crmDtStart").value + '&crmDtEnd=' + document.getElementById("crmDtEnd").value
//	            chart.setDataURL(url);
//	            chart.render("chartdiv");
//        	}

        	function load() {
        		var chart = new FusionCharts(basepath+"/contents/pages/fusionChart/tool/MSLine.swf", temp++, "50%", "50%", "10", "10");
        		
        		Ext.Ajax.request({
					url : basepath+'/orgPerformanceTrendline?customerId=' + parent.location.href.split("customerId=")[1] + '&crmDtStart=' + new Date().getYear() + "-01-01" + '&crmDtEnd=' + new Date().getYear() + "-12-31",
					method : 'GET',
					success : function(response) {
					debugger;
						var dataXml = Ext.util.JSON.decode(response.responseText).dataXml;
						
						chart.setDataXML(dataXml);
						alert(dataXml);
	            		chart.render("chartdiv");
					},
					failure : function(response) {
						
					}
				});
        	}
        	
//				var button = new Ext.Button(
//						                       {
//						                           width: 60, 
//											 	   height: 18, 
//											 	   text: '返  回',
//											 	   renderTo:'buttonDiv',
//											 	   handler : function() {
//											 	       history.back(); 
//											 	   }
//											   }
//						                    );      
        </script>
</head>
<body onload="setTimeout('load()', 1000)">
</body>
</html>