<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<!-- 基础工具，被产品放大镜依赖 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>

<!-- 产品放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/productinfo.js"></script>
	<script type="text/javascript">	
	Ext.onReady(function() {
		debugger;
		//页面布局
		Ext.getCmp("tbar").setVisible(false);
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
		                autoHeight:true,
		                items:[{
		                    collapsible:true,
		                    layout:'fit',
		                    style:'padding:0px 0px 0px 0px',
		                    items:[productGrid]
		    }]}]}]
		});
	});
	</script>
</head>
<body>
</body>
</html>