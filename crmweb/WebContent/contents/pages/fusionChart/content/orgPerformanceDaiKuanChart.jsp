<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <style type="text/css">
            body { background:none !important }
        </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
        
<!--         <script type="text/javascript" src="../tool/gallery_helper.js"></script> -->

		<script type="text/javascript">
        	var temp = 1;
        	function load() {
        		var chart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", temp++, "100%", "100%", "0", "0");
        		
				Ext.Ajax.request({
					url : basepath + '/orgPerformanceDaiKuan.json',
					
					method : 'GET',
					success : function(response) {
						var dataXml = Ext.util.JSON.decode(response.responseText).dataXml;
             //         alert(dataXml);
						chart.setDataXML(dataXml);
	            		chart.render("chartdiv");
	            		
					},
					failure : function(response) {
						
					}
				});
        	}
        	
        	
        </script>
</head>
<body onload="load()">
<div id="chartdiv" width="100%"></div>
</body>
</html>