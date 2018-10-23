<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"/></script>
<html>
<head>
</head>	
<body bgcolor="#ffffff" >	
<script type="text/javascript">

Ext.onReady(function(){
	var feeStatisticPanel = new Ext.Panel({

	    html : "<div id='feeStatisticGraphDiv'></div>"

	});

	 

	var feeStatisticGraphWin = new Ext.Window({

	    frame : true,

	    width : 416,

	    height : 333,

	    title : "费用统计图",

	    shadow : true,

	    modal : true,

	    items : [feeStatisticPanel]

	});

	 

	feeStatisticGraphWin.show();

	 

	var chart = new FusionCharts("custCompan.swf?nocache="+Math.random(), "chartId", "400", "300", "0", "1");

	chart.setDataURL(basepath+'/custcompanchart.json?customerId='+parent.oCustInfo.cust_id);

	chart.render("feeStatisticGraphDiv");
});


/**
		Ext.Ajax.request({		
			url:basepath+'/custcompanchart.json?customerId='+parent.oCustInfo.cust_id,
			method:"GET",
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			success : function(response) {
			debugger;
				var  dataXml = response.responseText;
				var dataXmlStr = Ext.util.JSON.decode(dataXml).dataXml;
				var falshObjectStr = "<object id=\"myFlash\" classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\""+
				" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0\""+
				" width=\"970\" height=\"700\"  id=\"crm5\" align=\"middle\"> <param name=\"wmode\" value=\"opaque\">"+
				" <param name=\"allowScriptAccess\" value=\"sameDomain\" />"+
				" <param id=\"movie\" name=\"movie\" value=\"custCompan.swf?nocache="+Math.random()+
				"/> <param name=\"quality\" value=\"high\" />"+
				" <param name=\"bgcolor\" value=\"#ffffff\" />"+
				" <param name=\"FlashVars\" value=\"imgUrl0=stat.png&imgUrl=user.png&dataXml="+dataXmlStr+
				"\" id=\"flashVars\"> <embed src=\"custCompan.swf?nocache="+Math.random()+
				" FlashVars=\"data=\""+dataXmlStr+
				"\" quality=\"high\" bgcolor=\"#ffffff\" name=\"crm5\" align=\"middle\" allowScriptAccess=\"sameDomain\""+
				" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" />";
				document.write(falshObjectStr);
				document.location.reload(); 


				
				

								


				
			},
			failure : function(response) {
				//debugger;
				//alert(response.responseText);
			}
		});		*/
</script>
 </body>
</html>