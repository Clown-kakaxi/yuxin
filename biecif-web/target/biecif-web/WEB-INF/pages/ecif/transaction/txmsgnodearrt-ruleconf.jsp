<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template2.jsp">
<script type="text/javascript">
var ids;
var names;
var leftTreeObj ;
var currentNode ;
var ruleGroupId ;
var grid;
var url;
 $(init);
 function init(){
	 initTree();
	 initGrid();
	 initButtons();
	 $("#mainsearch").css("display", "none");
	 $("#treeToolbar").css("display", "none");
	 grid.setHeight($("#right").height()+55);

 }
 

	
	//初始化Grid
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '规则编号',
				name : 'ruleNo',
				align : 'center',
				width : 120
			}, {
				display : '规则版本',
				name : 'ruleVer',
				align : 'center',
				width : 120
			}, {
				display : '规则名称',
				name : 'ruleName',
				align : 'center',
				width : 120
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
				width : 120
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
			isScroll : true,
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
	function initButtons() {
		var btns;
		btns = [ {
			text : '选择',
			click : f_select,
			icon : 'add'
			//operNo : 'addRuleConf'
		},  {
			text : '取消',
			click : f_unselect,
			icon : 'delete'
			//aoperNo : 'var_deleteBatch'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});
	}

	
 function initTree(){
	 $("#treeContainer").height($("#treeContainer").height()+26);
		var setting = {
				data : {
					key : {
						name : "text"
					}
				},
				view : {
					//selectedMulti : true,
					//showLine : true
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
							url="${ctx}/ecif/rulemanage/txbizruleconf/list.json?ruleGroupId="+ruleGroupId;
							initGrid();
						}
				}
			};
			leftTreeObj = $.fn.zTree.init($("#tree"), setting);
			loadTree(leftTreeObj);
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
	
	function f_select(){
		achieve();
		if (ids.length >= 1) {
			var idstr = ids[0];
			var namestr = names[0];
			var rulenostr = rulenos[0];
			for(var i=1;i<ids.length;i++){
				namestr = namestr  + ',' + names[i];
				idstr = idstr  + ',' + ids[i];
				rulenostr = rulenostr  + ',' + rulenos[i];
			}
			var grid = parent.ruleEqual.gridTable4;
			var row = parent.ruleEqualRow;
			row.checkRule = rulenostr; //idstr;
			row.checkRuleDesc = namestr;
			grid.updateCell('checkRule', rulenostr, row);
			var main = parent || window;
			var dialog = main.jQuery.ligerui.get("checkRuleConf");
 			if(dialog.beforeClose != null 
	         		&& typeof dialog.beforeClose == "function"
	         			&&flag!=null&&flag==true){        		
				 dialog.beforeClose();
	         } 
			 dialog.close();
		} else {
			BIONE.tip("请选择属性！");
		}
	}
	
	function f_unselect() {
		
		var idstr ;
		var namestr ;
		
		var grid = parent.ruleEqual.gridTable4;
		var row = parent.ruleEqualRow;

		row.checkRule = idstr;
		row.checkRuleDesc = namestr;

		grid.updateCell('checkRule', idstr, row);
		
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get("checkRuleConf");
 		if(dialog.beforeClose != null 
         		&& typeof dialog.beforeClose == "function"
         			&&flag!=null&&flag==true){        		
			 dialog.beforeClose();
         }
		dialog.close();
		// BIONE.closeDialog("checkRuleConf");
	}
	function achieve() {
		ids = [];
		names = [];
		rulenos = [];
		var rows = grid.getSelectedRows();
		for ( var i in rows) {
			ids.push(rows[i].ruleId);
			names.push(rows[i].ruleName);
			rulenos.push(rows[i].ruleNo);
		}
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
	<div class="content"><div id="maingrid" class="maingrid"></div></div>
</body>
</html>


