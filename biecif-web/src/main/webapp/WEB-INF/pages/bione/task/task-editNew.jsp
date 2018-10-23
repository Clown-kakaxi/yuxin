<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    var TASK_STS_NORMAL = "01";//正常
    var TASK_STS_STOP = "02";//挂起
    $(function() {

	$("#mainform").ligerForm({
	    fields : [ {
		name : 'taskId',
		type : 'hidden'
	    }, {
		group : "任务信息",
		groupicon : groupicon,
		display : '任务名称',
		name : 'taskName',
		newline : true,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100
		}
	    }, {
		display : '实现类名称',
		name : 'beanName',
		newline : false,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100,
		    remote : {
			url : "${ctx}/bione/schedule/task/testIsExists",
			type : "POST"
		    },
		    messages : {
			remote : "实现类不存在"
		    }
		}
	    }, {
		display : '任务状态',
		name : 'taskStsValue',
		newline : true,
		type : 'select',
		options : {
		    valueFieldID : 'taskSts',
		    initValue : TASK_STS_NORMAL,
		    initText : '正常',
		    data : [ {
			text : '正常',
			id : TASK_STS_NORMAL
		    }, {
			text : '挂起',
			id : TASK_STS_STOP
		    } ]
		}
	    }, {
		display : '触发器',
		name : 'trigger',
		newline : true,
		type : 'select',
		comboboxName : "triggerCombBox",
		validate : {
		    maxlength : 32
		},
		width : '500',
		options : {
		    valueFieldID : 'triggerId',
		    slide : false,
		    selectBoxHeight : 238,
		    selectBoxWidth : 545,
		    resize : false,
		    switchPageSizeApplyComboBox : false
		}
	    } ]
	});

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));

	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("taskAddWin", null);
	    }
	});
	buttons.push({
	    text : '保存',
	    onclick : f_save
	});

	BIONE.addFormButtons(buttons);

	//弹出窗口中选择触发器
	$.ligerui.get("triggerCombBox").openSelect({
	    url : "${ctx}/bione/schedule/task/selectTrigger",
	    dialogname : 'triggerBox',
	    title : '选择触发器',
	    comboxName : 'triggerCombBox',
	    height : '380',
	    width : '600'
	});

    });
    function f_save() {
	BIONE.submitForm($("#mainform"), function() {
	    BIONE.closeDialogAndReloadParent("taskAddWin", "maingrid", "添加成功");
	}, function() {
	    BIONE.closeDialog("taskAddWin", "添加失败");
	});
    }
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/schedule/task"></form>
	</div>
</body>
</html>