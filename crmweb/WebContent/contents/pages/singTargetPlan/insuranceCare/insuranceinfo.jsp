<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/insuranceinfo.js"></script>
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
		                	title:'已有保障',
		                    collapsible:true,
		                    layout:'fit',
		                    height:80,
		                    style:'padding:0px 0px 0px 0px',
		                    items:[insuranceform]
		    }]},{
           	 columnWidth:.6,
             border:false,
             autoHeight:true,
             items:[{
                 collapsible:true,
                 layout:'fit',
                 height:250,
                 style:'padding:0px 0px 0px 0px',
                 items:[insuranceGrid]
 }]},{
 	 columnWidth:.4,
         border:false,
         autoHeight:true,
         items:[{
             collapsible:true,
             layout:'fit',
             style:'padding:0px 0px 0px 0px',
             html:'<iframe id="contentFrame" name="content" height="200" frameborder="no" width="100%" src=\"chart/b.html\" " scrolling="no"> </iframe>'
}]},{
        	 columnWidth:1,
                border:false,
                autoHeight:true,
                items:[{
                	title:'说明',
                    collapsible:true,
                    layout:'fit',
                    style:'padding:0px 0px 0px 0px',
                    html:' <p style="line-height:30px">&nbsp;&nbsp;&nbsp;&nbsp;欠缺 - 您还没有考虑该项责任</p>&nbsp;&nbsp;&nbsp;&nbsp;不足 - 您的实际保额小于建议保额</p>&nbsp;&nbsp;&nbsp;&nbsp;合理 - 您的实际保额与建议保额相符</p>&nbsp;&nbsp;&nbsp;&nbsp;充足 - 您的实际保额大于建议保额</p>'
    }]}]}]
		});
	});
	</script>
</head>
<body>
</body>
</html>