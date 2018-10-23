<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template2.jsp">
<script type="text/javascript">
var leftTreeObj ;
var currentNode ;
var ruleGroupId ;
var grid;
var url;
var url2;
 $(init);
 function init(){
	 initTree();
	 addTreeToolBar();
	 initGrid();
	 initButtons();
	 //searchForm();
 }
	function searchForm() {
		$("#search").ligerForm({
			fields : [{
				display : "规则编号",
				name : "ruleNo",
				newline : true,
				labelWidth : 100,
				width : 220,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "tbrc.ruleNo"
				}
			}, {
				display : "规则名称",
				name : "ruleName",
				newline : false,
				labelWidth : 100,
				width : 220,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					vt : "string",
					field : "tbrc.ruleName"
				}
			} ]
		});


		$('#searchbtn').empty();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");

		
	}
	
	//搜索表单应用ligerui样式
	function searchForm2() {
		//$("#search").empty();
		$("#search").ligerForm({
		 	labelWidth : 110,
			inputWidth : 110,
			space : 10, 
			fields : [ {
				display : "源表模式名",
				name : "schsrc",
				align : 'left',
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "schsrc"
				}
			}, {
				display : "目标表模式名",
				name : "schdst",
				align : 'left',
				newline : false,
				type : "text",
				cssClass : "field",
			 	labelWidth : 200,
				width : 250,
				attr : {
					op : "=",
					field : "schdst"
				}
			}]
		});

		$('#searchbtn').empty();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}
	
	//初始化Grid
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			height : '100%',
			width : '100%',
			columns : [ {
				display : '规则编号',
				name : 'ruleNo',
				align : 'center',
				width : 150
			}, {
				display : '规则版本',
				name : 'ruleVer',
				align : 'center',
				width : 60
			}, {
				display : '规则名称',
				name : 'ruleName',
				align : 'center',
				width : 200
			} , {
				display : '规则定义分类',
				name : 'ruleDefType',
				align : 'center',
				width : 120,
				render : RuleDefTypeRender
			}, {
				display : '规则业务分类',
				name : 'ruleBizType',
				align : 'center',
				width : 120,
				render : RuleBizTypeRender
			}, {
				display : '规则状态',
				name : 'ruleStat',
				align : 'center',
				width : 120,
				render : RuleStatRender
			}, {
				display : '规则描述',
				name : 'ruleDesc',
				align : 'center',
				width : 200
			}, {
				display : '创建人',
				name : 'createOper',
				align : 'center',
				width : 120
			}, {
				display : '创建时间',
				name : 'createTime',
				type:'date',
				align : 'center',
				width : 120
			}, {
				display : '修改人',
				name : 'updateOper',
				align : 'center',
				width : 120
			}, {
				display : '修改时间',
				name : 'updateTime',
				type:'date',
				align : 'center',
				width : 120
			}, {
				display : '审批人',
				name : 'approvalOper',
				align : 'center',
				width : 120
			}, {
				display : '审批时间',
				name : 'approvalTime',
				type:'date',
				align : 'center',
				width : 120
			}, {
				display : '生效时间',
				name : 'effectiveTime',
				type:'date',
				align : 'center',
				width : 120
			} , {
				display : '失效时间',
				name : 'expiredTime',
				type:'date',
				align : 'center',
				width : 120
			} , {
				display : '规则处理方法分类',
				name : 'ruleDealType',
				align : 'center',
				width : 120,
				render : RuleDealTypeRender
			} , {
				display : '规则处理接口类型',
				name : 'ruleIntfType',
				align : 'center',
				width : 120
			} , {
				display : '规则处理包路径',
				name : 'rulePkgPath',
				align : 'center',
				width : 120
			} , {
				display : '规则处理类',
				name : 'ruleDealClass',
				align : 'center',
				width : 120
			} , {
				display : '规则处理表达式',
				name : 'ruleExpr',
				align : 'center',
				width : 120
			}  , {
				display : '规则处理表达式描述',
				name : 'ruleExprDesc',
				align : 'center',
				width : 120
			},{
				display : '父规则名称',
				name : 'parentRuleName',
				align : 'center',
				width : 120
			}],
			checkbox : true,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			delayLoad : true,
			url : url,
			sortName : 'ruleNo', //第一次默认排序的字段
			sortOrder : 'asc',
			toolbar : {}
		});
	}
	
	

	var btns;

	function initButtons() {
		
		//删除已定义的按钮
/**
		var c1 = $("#maingrid");
		var c2 =  c1[0].getElementsByTagName("DIV")[2]; 
		for(var i=0;i<c2.childNodes.length;i++){
			x = c2.childNodes[i];
			x.parentNode.removeChild(x);
		}
**/				
		if(btns!=null) return;
		
		btns = [ {
			text : '增加',
			click : addRuleConf,
			icon : 'add'
		}, {
			text : '修改',
			click : editRuleConf,
			icon : 'modify'
		}, {
			text : '删除',
			click : deleteRuleConf,
			icon : 'delete'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});
	}
	
	
 function initTree(){
		var setting = {
				data : {
					key : {
						name : "text"
					}
				},
				view : {
					selectedMulti : true,
					showLine : true
				},
				check : {
					enable : false,
					chkboxType : {
						"Y" : "null",
						"N" : "null"
					}, 
					radioType : "all"
				},
				callback : {
						onClick : function(event, treeId, treeNode) {
							if(treeNode.id=="ROOT"){
								return;
							}
							ruleGroupId = treeNode.id ;
							ruleGroupName = treeNode.text;
							
							if(ruleGroupName =='客户信息覆盖规则'){													//客户信息覆盖规则
								/**
								url2="${ctx}/ecif/rulemanage/dqincrtabconf/list.json";
								initGrid2();
								initButtons2();
							    searchForm2();
							    **/
							    
							    //暂时隐藏此功能
							    $('#maingrid').hide();
							    dialog = BIONE.commonOpenLargeDialog("通用覆盖规则配置", "authResWin","${ctx}/ecif/rulemanage/dqincrtabconf");
							}else{
								//alert(ruleGroupName);
								url="${ctx}/ecif/rulemanage/txbizruleconf/list.json?ruleGroupId="+ruleGroupId;  //其他规则
								initGrid();
								initButtons();
							    searchForm();		
								 $('#maingrid').show(); 

							}
							
						}
				}
			};
			leftTreeObj = $.fn.zTree.init($("#tree"), setting);
			loadTree(leftTreeObj);
}
 
 //打开通用覆盖程序配置
 function open_cvrconfig() {
	    dialog = BIONE.commonOpenLargeDialog("通用覆盖规则配置", "authResWin","${ctx}/ecif/rulemanage/dqincrtabconf/list.json");
 }	 
 
 function  addTreeToolBar(){
		$("#treeToolbar").ligerTreeToolBar({
			items : [ {
				icon : 'add',
				text : '添加',
				click : addRuleGroup
			}, {
				line : true
			}, {
				icon : 'modify',
				text : '修改',
				click : editRuleGroup
			}, {
				line : true
			}, {
				icon : 'delete',
				text : '删除',
				click : deleteRuleGroup
			} ],
			treemenu : false
		});
 }
	function loadTree() {
		$.ajax({
			cache : false,
			async : false,
			url : "${ctx}/ecif/rulemanage/txbizrulegroup/getNodeTree.json?d=",
			type : "post",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success : function(result) {
				if(result){
					for(var i in result){
						if(result[i].params.open){
							result[i].open = true;
						}else{
							result[i].open = false;
						}
					}
					leftTreeObj.addNodes(null, result, false);
					leftTreeObj.expandAll(true);

				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	function addRuleGroup(){
		BIONE.commonOpenLargeDialog('添加规则大类', 'RuleGroupAdd',
		'${ctx}/ecif/rulemanage/txbizrulegroup/new');
	}
 
	function editRuleGroup(){
		if (ruleGroupId == null||ruleGroupId=="ROOT") {
			BIONE.tip('请选择规则组！');
			return;
		}else {
			var id = ruleGroupId;
			BIONE.commonOpenLargeDialog("修改规则大类", "RuleGroupAdd",
					"${ctx}/ecif/rulemanage/txbizrulegroup/" + id + "/edit");
		}
	}
	
	function deleteRuleGroup(){
		if (ruleGroupId == null||ruleGroupId=="ROOT") {
			BIONE.tip('请选择规则组！');
			return;
		}else {
			$.ligerDialog
			.confirm('您确定删除此规则组吗？',
					function(yes) {
						if (yes) {
							BIONE.ajax({
										url : '${ctx}/ecif/rulemanage/txbizrulegroup/deleteRuleGroup?id='
														+ ruleGroupId
											},
											function(flag) {
												if (flag == 1) {
													BIONE.tip('删除成功');
													initTree();
												} else {
													BIONE.tip('该规则组下存在规则，不能删除！');
												}
											});
						} else {
							BIONE.tip('取消删除');
						}
					});
		}
	}
	
	function addRuleConf(){
		if(!ruleGroupId){return;}
		BIONE.commonOpenLargeDialog('添加规则', 'RuleConfAdd',
		'${ctx}/ecif/rulemanage/txbizruleconf/'+ruleGroupId+'/new');
	}
	
	function editRuleConf(){
		if(!ruleGroupId){return;}
		var rows = grid.getSelectedRows();
		if (rows.length < 1) {
			BIONE.tip('请选择记录');
		} else if (rows.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			var id = rows[0].ruleId;
			BIONE.commonOpenLargeDialog("修改规则", "RuleConfAdd",
					"${ctx}/ecif/rulemanage/txbizruleconf/" +id+"/edit");
		}
	}
	
	function  deleteRuleConf(){
		if(!ruleGroupId){return;}
		var ids = achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm(
							'您确定删除这' + ids.length + "条记录吗？",
							function(yes) {
								if (yes) {
									BIONE.ajax({
										url : '${ctx}/ecif/rulemanage/txbizruleconf/deleteRuleConf?ids='
																+ ids
													},
													function(flag) {
														if (flag == 1) {
															BIONE.tip('删除成功');
															grid.loadData();
														} else {
															BIONE.tip('规则存在子规则，不能删除！');
														}
													});
								} else {
									BIONE.tip('取消删除');
								}
							});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	
	function dqincrtabconf_add() {
		BIONE.commonOpenLargeDialog('新增', 'dqincrtabconfManage',
				'${ctx}/ecif/rulemanage/dqincrtabconf/new');
	}
	function dqincrtabconf_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenDialog('修改', 'dqincrtabconfManage',800,400,
					'${ctx}/ecif/rulemanage/dqincrtabconf/' + ids[0] + '/edit');
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录!');
		} else {
			BIONE.tip('请选择记录!');
		}
	}
	function dqincrtabconf_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/rulemanage/dqincrtabconf/" + ids.join(','),
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
	
    //配置字段
    function dqincrtabconf_config() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
		    var id = rows[0].id;
		    var dstTab = rows[0].dstTab;
		    dialog = BIONE.commonOpenDialog(dstTab + "表配置字段", "dqincrcolconfWin", 950, 400, 
			    "${ctx}/ecif/rulemanage/dqincrtabconf/dqincrcolconf?id=" + id + "&dstTab=" + dstTab
				    );
		} else {
		    BIONE.tip("请选择需要配置的表");
		}
    }		
	function achieveIds() {
		var ids = [];
		var rows = grid.getSelectedRows();
		for ( var i in rows) {
			ids.push(rows[i].ruleId);
		}
		return ids;
	}
	
	function RuleDefTypeRender(rowdata) {
		if (rowdata.ruleDefType == 'C') {
			return "校验";
		}
		if (rowdata.ruleDefType == 'T') {
			return "转换";
		} else {
			return rowdata.ruleDefType;
		}
	}
	function RuleBizTypeRender(rowdata) {
		if (rowdata.ruleBizType == 'P') {
			return "个人客户";
		}
		if (rowdata.ruleBizType == 'B') {
			return "对公客户";
		} else {
			return rowdata.ruleBizType;
		}
	}
	function RuleStatRender(rowdata) {
		if (rowdata.ruleStat == '1') {
			return "有效";
		}
		if (rowdata.ruleStat == '0') {
			return "无效";
		} else {
			return rowdata.ruleStat;
		}
	}
	
	function RuleDealTypeRender(rowdata) {
		if (rowdata.ruleDealType == '1') {
			return "正则表达式";
		}
		if (rowdata.ruleDealType == '9') {
			return "自定义";
		} else {
			return rowdata.ruleDealType;
		}
	}



</script>
</head>
<body>
</body>
</html>


