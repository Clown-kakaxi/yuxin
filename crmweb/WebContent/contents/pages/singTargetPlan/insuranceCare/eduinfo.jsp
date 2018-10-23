<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/eduinfo.js"></script>
	<script type="text/javascript">
	Ext.onReady(function() {
		//页面布局
		var view = new Ext.Viewport( {
			layout : "fit",
			frame : true,
			items:[{
				xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	            	 columnWidth:1,
		                border:false,
		                title:'各国学费介绍',
		                autoHeight:true,
		                items:[{
		                    collapsible:true,
		                    height:150,
		                    layout:'fit',
		                    style:'padding:0px 0px 0px 0px',
		                    items:[eduGrid]
		    }]},{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		                items:[{
		                    collapsible:true,
		                    layout:'fit',
		                    height:300,
		                    style:'padding:0px 0px 0px 0px',
		                    items:[eduInfo]
		    }]}]
			}] 
		});
	});
	</script>
</head>
<body>
</body>
</html>