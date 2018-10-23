<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template32.jsp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">

	$(init);
	
	function init() {		
		
		//初始化右侧tab
		initTab();
	}
	
	//构造右侧tab
	function initTab(node){
	
		$('#tab').ligerTab({
			height : 800
		});
		
		templateTab = $("#tab").ligerGetTabManager();
	}
</script>
</head>
<body>

	<div id="template.right">
		<div id="tab" style="overflow:hidden;height:92%">
		  	  <div tabid="t1" title="查看交易日志" lselected="true" style="overflow:hidden;height:100%">
		  	  	<iframe frameborder="10" id="tab1" style="height:600" src='${ctx}/ecif/transaction/txlog' ></iframe>
		  	  </div>
		  	  <div tabid="t2" title="查看异常日志" style="overflow:hidden;">
		  	  	<iframe frameborder="10" id="tab2" style="height:90%" src='${ctx}/ecif/transaction/txerr' ></iframe>
		  	  </div>

		 </div>

	</div>
	
	<div id="template.bottom">
			<div class="form-bar">
				<div class="form-bar-inner" style="padding-top: 5px"></div>
			</div>
		</div>
	</div>
	

  	
</body>
</html>