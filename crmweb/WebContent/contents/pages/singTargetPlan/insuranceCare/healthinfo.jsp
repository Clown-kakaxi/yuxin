<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/healthinfo.js"></script>
	<script type="text/javascript">	
	Ext.onReady(function() {
		//页面布局
		var view = new Ext.Viewport( {
			layout : "fit",
			frame : true,
			 items:[{
            	 columnWidth:1,
	                border:false,
	                autoHeight:true,
	                items:[{
	                	title:'介绍',
	                    collapsible:true,
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;医疗保险，是为保障家庭医疗需求，提供医疗金的保险类产品。 XXX医疗保险为您量身定做最合适的医疗保险计划，为您的家庭幸福生活提供最坚实的财务保障......</p>'
	    }]},{
            	 columnWidth:1,
	                border:false,
	                autoHeight:true,
	                items:[{
	                    collapsible:true,
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    items:[healthform]
	    }]}]
		});
	});
	</script>
</head>
<body>
</body>
</html>