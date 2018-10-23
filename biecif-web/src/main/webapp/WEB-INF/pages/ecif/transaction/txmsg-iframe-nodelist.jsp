<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="/template/template8.jsp">
<script type="text/javascript">
	var gridTable; 
	$(init);
	
	function init(){
		initGrid_tables();
	}
	
	function initGrid_tables(nodeid){
		//debugger;
		gridTable = $("#maingrid1").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
								{
									name : 'nodeTabMapId',
									hide :1
								},{
									name : 'nodeId',
									hide :1
								},{
									name : 'state',
									hide :1
								},{
									name : 'tabId_text',
									hide :1
								},{
								display : '表中文名称',
								name : 'tabId',
								align : 'center',
								width : '30%',
								editor : {
									type : 'select',
									//data : tabdefData,
									valueColumnName : 'tabId',
									ext : function(rowdata) {
										return {
											textField : 'tabDesc',
											onBeforeOpen : function(){
												
												var sel = this;
												if (tabdefData.length > 0) {
													sel.setData(tabdefData);
													return true;
												} else {
													//debugger;
													$.ajax({
														url : '${ctx}/ecif/core/tabdef/listall.json',
														async : false,
														type : 'get',
														success : function(data) {
															$.each(data, function(i, n){
																tabdefData.push(n);
															});
														}
													});
												}
												//return false;
											},
											render : function(){
												return rowdata.tabDesc;
											}

										};
									}
								},
								render : function(item) {
									//debugger;
									for ( var i = 0; i < tabdefData.length; i++) {
										if (tabdefData[i].tabId == item.tabId) {
											item.tabDesc = tabdefData[i].tabDesc;
											return tabdefData[i].tabDesc;
										}
									}
									return item.tabDesc;
								},	
								minWidth : '30%'
							} ],
					checkbox : true,
					usePager : false,
					isScroll : true,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'get',
					url : '${ctx}/ecif/transaction/txmsgnodetabmap/list.json?nodeId='+ nodeid,
					sortName : 'tabId', //第一次默认排序的字段
					sortOrder : 'asc',
					enabledEdit : true,     //列表进入可编辑模式
					toolbar : {}
		});	

	}
	
</script>
</head>
<body>
</body>
</html>