<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
        <style type="text/css">
            body { background:none !important }
        </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.FusionChartPanel.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
		<script type="text/javascript">

			Ext.onReady(function(){
				var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
					layout : 'fit',
					swfFile : basepath+"/FusionCharts/MSLine.swf",
					dataUrl : basepath + '/orgPerformanceCunKuan.json'
				});
				var viewPort = new Ext.Viewport({
					layout:'fit',
					items:[contentPanel]
				});
			});
        	
        </script>
</head>
<body>
	
</body>
</html>