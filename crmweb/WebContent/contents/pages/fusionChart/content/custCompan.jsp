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
<!--         <script type="text/javascript" src="../tool/gallery_helper.js"></script> -->
		<script type="text/javascript" src="custCompan.js"></script>
		<script type="text/javascript">
		    function load() {
	            var chart = new FusionCharts(basepath+"/contents/pages/custRelationPicture/demo1/custCompan.swf", "ChartId", "100%", "100%", "0", "0");					
					Ext.Ajax.request({
						url : basepath+'/custcompanchart.json?customerId=' + parent.oCustInfo.cust_id,
						method : 'GET',
						success : function(response) {
							var dataXml = Ext.util.JSON.decode(response.responseText).dataXml;
							Ext.Msg.alert('',response.responseText);
							chart.setDataXML(dataXml);
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