<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/colligate/planWay.js"></script>
<script type="text/javascript">	
	Ext.onReady(function() {
		//页面布局
		var view = new Ext.Viewport( {
			layout : "fit",
			title:"策略选择",
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
	                 items:[wayForm]
	             }]
	     },{
	       	 columnWidth:1,
	         border:false,
	         autoHeight:true,
	         items:[{
	             collapsible:true,
	             layout:'fit',
	             title:'现金流分析',
	             style:'padding:0px 0px 0px 0px',
	             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src= \"../investPlan/chart/b.html\"  " scrolling="no"> </iframe>'
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