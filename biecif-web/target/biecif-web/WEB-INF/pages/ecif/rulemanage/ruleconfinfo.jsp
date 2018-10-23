<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
var ruleGroupId ;
var grid;
 $(init);
 function init(){
	 initGrid();
	 searchForm();
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
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}
	//初始化Grid
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : [  {
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
				render : RDTRender
			}, {
				display : '规则业务分类',
				name : 'ruleBizType',
				align : 'center',
				width : 120,
				render : RBTRender
			}, {
				display : '规则状态',
				name : 'ruleStat',
				align : 'center',
				width : 120,
				render : RSRender
			}, {
				display : '规则描述',
				name : 'ruleDesc',
				align : 'center',
				width : 120
			},  {
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
			} ],
			checkbox : false,
			usePager : true,
			isScroll : true,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : "${ctx}/ecif/rulemanage/txbizruleconf/list.json",
			onDblClickRow : function(rowdata, rowindex, rowDomElement) {//双击选择
			    addToParent(rowdata.ruleId, rowdata.ruleName);},
			sortName : 'ruleNo', //第一次默认排序的字段
			sortOrder : 'asc'
		});
	}
	
	function addToParent(ruleId, ruleName) {
	    var main = parent || window;
	    var selectBox = main.$("[name='parentRuleName']");
	    selectBox.val(ruleName);
	    var hiddenBox = main.$("[name='parentRuleId']");
	    hiddenBox.val(ruleId);
	    BIONE.closeDialog("RuleInfoBox");
	}
	
	function RDTRender(rowdata) {
		if (rowdata.ruleDefType == 'C') {
			return "校验";
		}
		if (rowdata.ruleDefType == 'T') {
			return "转换";
		} else {
			return rowdata.ruleDefType;
		}
	}
	function RBTRender(rowdata) {
		if (rowdata.ruleBizType == 'P') {
			return "个人客户";
		}
		if (rowdata.ruleBizType == 'B') {
			return "对公客户";
		} else {
			return rowdata.ruleBizType;
		}
	}
	function RSRender(rowdata) {
		if (rowdata.ruleStat == '1') {
			return "有效";
		}
		if (rowdata.ruleStat == '0') {
			return "无效";
		} else {
			return rowdata.ruleStat;
		}
	}

</script>
</head>
<body>
</body>
</html>


