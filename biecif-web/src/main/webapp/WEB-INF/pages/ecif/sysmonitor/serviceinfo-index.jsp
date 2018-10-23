<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	$(function() {
		url = "${ctx}/ecif/serviceinfo/list.json";
		searchForm();
		initGrid();
		initButtons();
		//BIONE.addSearchButtons("#search", grid, "#searchbtn");
		addSearchButtons("#search", grid, "#searchbtn");
		grid.loadData();
		
	});
	
	// 创建表单搜索按钮：搜索、高级搜索
	addSearchButtons = function(form, grid, btnContainer) {
		if (!form)
			return;
		form = $(form);
		if (btnContainer) {
			BIONE.createButton({
				appendTo : btnContainer,
				text : '搜索',
				icon : 'search3',
				width : '50px',
				click : function() {
					var loading=$(".l-grid-loading");
					if(loading&&loading.css("display")=="block"){
						BIONE.tip('数据正在加载中,请稍后进行查询操作!');
					}else{
						var rule = BIONE.bulidFilterGroup(form);
						if (rule.rules.length) {
							grid.set('parms', {
								condition : JSON2.stringify(rule)
							});
							grid.set('newPage', 1);
						} else {
							grid.set('parms', {
								condition : ''
							});
							grid.set('newPage', 1);
						}
						/* @Revision 20130704182000 liucheng2@yuchengtech.com
						 * 防止grid进行ajax取数时session失效而无法正确获取数据； */
						if (!grid.loading || grid.loading!=true) {
							var opurl = grid.options.url;
							var ctx = "/";
							if (opurl) {
								ctx = "/" + opurl.split("/")[1] + "/";
							}
							$.ajax({
								cache : false,
								async : true,
								url : ctx + "/bione/common/getComboxData.json",
								dataType : 'json',
								data : {
									"paramTypeNo" : ""
								},
								type : grid.options.type,
								complete : function(XMLHttpRequest) {
									BIONE.isSessionAlive(XMLHttpRequest);
								},
								success : function() {
									grid.loadData();
								}
							});
						} else {
							BIONE.showError("查询进行中，请勿重复查询！");
						}
						/* @Revision 20130704182000 END */
					}
				}
			});
			BIONE.createButton({
				appendTo : btnContainer,
				text : '重置',
				icon : 'refresh2',
				width : '50px',
				click : function() {
					$(":input", form)
							.not(":submit, :reset,:hidden,:image,:button, [disabled]")
							.each(function() {
								$(this).val("");
							});
					$(":input[ltype=combobox]", form)
					.each(function() {
						$(this).val("");
					});
				}
			});
		}
	};
	
	function initButtons() {
		btns = [ {
			text : '增加',
			click : serviceinfo_add,
			icon : 'add',
			operNo : 'serviceinfo_add'
		}, {
			text : '修改',
			click : serviceinfo_modify,
			icon : 'modify',
			operNo : 'serviceinfo_modify'
		}, {
			text : '删除',
			click : serviceinfo_delete,
			icon : 'delete',
			operNo : 'serviceinfo_delete'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function serviceinfo_add() {
		BIONE.commonOpenDialog('添加', 'serviceinfoManage',700,200,
				'${ctx}/ecif/serviceinfo/new');
	}
	function serviceinfo_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenDialog('修改', 'serviceinfoManage',700,200,
					'${ctx}/ecif/serviceinfo/' + ids[0] + '/edit');
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	function serviceinfo_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/serviceinfo/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('删除成功');
						initGrid();
					} else {
						BIONE.tip('删除失败');
					}
				}
			});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].serviceID);
		}
		
	}

	
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "IP地址",
				name : "ipAddr",
				type : "text",
				newline : false,
				cssClass : "field",
				attr : {
					op : "=",
					field : "txServiceStatus.ipAddr"
				}
			}, {
				display : "服务名称",
				name : "serviceName",
				type : "text",
				newline : false,
				cssClass : "field",
				attr : {
					op : "=",
					field : "txServiceStatus.serviceName"
				}
			}]
		});

	}

	function initGrid() {
		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							

							{
								display : '主机名',
								name : 'hostName',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},{
								display : 'IP地址',
								name : 'ipAddr',
								align : 'center',
								width : '15%',
								minWidth : '5%'
							},
							{
								display : '端口号',
								name : 'servicePort',
								align : 'center',
								width : '10%',
								minWidth : '2%'
							},
							{
								display : '服务名',
								name : 'serviceName',
								align : 'center',
								width : '20%',
								minWidth : '5%'
							}
							],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					delayLoad :true,
					sortName : 'stopTime', //第一次默认排序的字段
					sortOrder : 'desc',
					onDblClickRow :  function(rowdata, rowindex, rowDomElement){//双击选择
					    dialog = BIONE.commonOpenDialog("服务详细信息", "serviceinfoManage",700,200,
							    "${ctx}/ecif/serviceinfo/"+ rowdata.serviceID +"/edit" 
								    );
					},
					toolbar : {}
					
				});
	}

	function RenderFlag3(rowdata){
		if(rowdata.serviceStart == '0'){
			return '启动';
		}else if(rowdata.serviceStart == '1'){
			return  '<font color="red">停止<font>';
		}else{
			return '<font color="red">未知<font>';
		}
	}
    
	function Long2Date1(rowdata){
		if(rowdata.startTime!=null ){
			return Long2Date(rowdata.startTime);
		}	
	}
	function Long2Date2(rowdata){
		if(rowdata.stopTime!=null){
			return Long2Date(rowdata.stopTime);			
		}
	}
	
	function Long2Date(time){
		if(time!=null){
           var d;
           d = new Date(time);
           return d.getYear()+"年"+(d.getMonth()+1)+"月"+d.getDate()+"日 "+d.getHours()+"时"+d.getMinutes()+"分"+d.getSeconds()+"秒"		
		}
	}
    

</script>
</head>
<body>
</body>
</html>