<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/oldinfo.js"></script>

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
	                autoHeight:true,
	                items:[{
	                	title:'介绍',
	                    collapsible:true,
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;养老保险，是为保障老年生活需求，提供养老金的退休养老保险类产品。 XXX养老险您量身定做最合适的养老保险计划，为您的晚年幸福生活提供最坚实的财务保障......</p>'
	    }]},{
            	 columnWidth:1,
	                border:false,
	                autoHeight:true,
	                items:[{
	                    collapsible:true,
	                    layout:'fit',
	                    height:300,
	                    style:'padding:0px 0px 0px 0px',
	                    items:[oldform]
	    }]}]
		}]
	});
});
</script>
</head>
<body>
</body>
</html>