<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
var mainform;
var buttons = [];
$(function(){
	initForm();
	initBtns();
});
function initForm(){
	mainform = $("#mainform").ligerForm({
		align:'center',
		inputWidth : 180,
		labelWidth : 100,
		space : 30,
		fields : [{
			name : "typeId",
			type:"hidden"
		},{
			name : "labelObjId",
			type:"hidden",
			attr: {
				value: "${realId}"
			}
		}, {
			display : "类型名称",
			name : "typeName",
			newline : true,
			type : "text",
			group : "标签分类 ",
			groupicon : "${ctx}/images/classics/icons/tag_green.png",
			validate : {
				required : true,
				maxlength : 32,
				remote : {
					url : "${ctx}/bione/label/labelConfig/checkTypeName",
					type : 'get',
					async : true,
					data: {
						id: "${id}",
						realId: "${realId}"
					}
				},
				messages : {
					remote : "名称已存在！"
				}
			}
		}, {
			display:'备注',
			name:'remark',
			newline:true,
			width:'500',
			validate:{
				maxlength:1000
			},
			type:'textarea',
			attr : {
				style : "resize: none;"
			}
		}]
	});
	jQuery.metadata.setType("attr", "validate");
	BIONE.validate("#mainform");
	if (true != "${isNew}") {
		BIONE.loadForm($("mainform"), {
			url : "${ctx}/bione/label/labelConfig/info.json",
			data : {
				id : "${id}",
				type: "type"
			}
		});
	}
}
function initBtns() {
	buttons.push({
		text : '取消',
		onclick : f_close
	});
	buttons.push({
		text : '保存',
		onclick : f_save
	});
	BIONE.addFormButtons(buttons);
}

function f_close(){
	BIONE.closeDialog("dialog");
}
function f_save(){
	BIONE.submitForm($("#mainform"), function() {
		var win = parent || window;
		window.parent.BIONE.tip("保存成功");
		win.f_beforeClose();
		BIONE.closeDialog("dialog");
	}, function() {
		window.parent.BIONE.tip("保存失败");
	});
}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/label/labelConfig/type" method="post"></form>
	</div>
</body>
</html>