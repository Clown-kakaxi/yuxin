<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
        <link href="../assets/prettify/prettify.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript" src="FusionCharts.js"></script>

        <style type="text/css">
            body { background:none !important }
        </style>

    </head>
    <body>
        <!-- heading -->

<p>&nbsp;</p>
        <div id="chartdiv" align="center">
            FusionCharts.
        </div>

        <script type="text/javascript">
            var chart = new FusionCharts(basepath+"/contents/pages/customer/groupClientManager/organizationChart/DragNode.swf", "ChartId", "1024", "768", "0", "0");
           /*  chart.setDataURL(basepath+'/query-view-and-grid?customerId='+parent.parent.location.href.split("customerId=")[1]);
            chart.render("chartdiv"); */
        	Ext.Ajax.request({
				url : basepath+'/query-view-and-grid?customerId='+parent.parent.location.href.split("customerId=")[1],
				method : 'GET',
				success : function(response) {
					var dataXml = Ext.util.JSON.decode(response.responseText).dataXml;
					
					chart.setDataXML(dataXml);
            		chart.render("chartdiv");
				},
				failure : function(response) {
					
				}
			});
        </script>
<p>&nbsp;</p>
    

        <!-- data viewer -->
        <div class="show-code-block">
            
            
            
            <pre class="prettyprint"></pre>
           
        </div>

    </body>
</html>