<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/colligate/planTarget.js"></script>
<script type="text/javascript">	
	Ext.onReady(function() {
		//页面布局
		var view = new Ext.Viewport( {
			layout : "fit",
			title:"	客户生活目标",
			frame : true,
			items:[{xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	           	 columnWidth:1,
	             border:false,
	             autoHeight:true,
	             items:[{
	                 collapsible:true,
	                 layout:'fit',
	                 style:'padding:0px 0px 0px 0px',
	                 items:[targetForm]
	             }]
	     }]
			}]
		});
	});
	</script>
</head>
<body>
</body>
</html>