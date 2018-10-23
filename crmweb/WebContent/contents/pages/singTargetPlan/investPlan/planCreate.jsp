<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/planCreate.js"></script>
<script type="text/javascript">	
	Ext.onReady(function() {
		//页面布局
		var view = new Ext.Viewport( {
			layout : "fit",
			title:"规划创建",
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
	                 items:[infoForm]
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