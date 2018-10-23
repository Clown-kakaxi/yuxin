<!DOCTYPE html PUBLIC "-//W3C// DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
	<head>
		<script type="text/javascript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
		
	</head>
	
	<body>
	<div id="chartdiv" align="center"></div>
	<script>
			var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
			myChart.setJSONUrl(basepath+'/sysStatusMonitoring.json');
			myChart.render("chartdiv");
		</script>
	</body>
</html>