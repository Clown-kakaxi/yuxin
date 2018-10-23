<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    var triggerId = "${triggerId}";
    var triggerName = "${triggerName}";
    var TASK_STS_NORMAL = "01";//正常
    var TASK_STS_STOP = "02";//挂起
    $(function() {
	$("#mainform").ligerForm({
	    fields : [ {
		name : 'testTriggerId',
		type : 'hidden'
	    }, {
		name : 'taskType',
		type : 'hidden'
	    }, {
		name : 'taskSts',
		type : 'hidden'
	    }, {
		name : 'taskId',
		type : 'hidden'
	    }, {
		group : "任务",
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
		display : '触发器',
		name : 'trigger',
		comboboxName : "triggerCombBox",
		newline : true,
		type : 'select',
		validate : {
		    maxlength : 32
		},
		width : '500',
		options : {
		    initText : triggerName,
		    initValue : triggerId,
		    textField : 'triggerName',
		    valueFieldID : 'triggerId',
		    valueField : 'triggerId',
		    hideOnLoseFocus : true,
		    slide : false,
		    selectBoxHeight : 238,
		    selectBoxWidth : 545,
		    resize : false,
		    switchPageSizeApplyComboBox : false,
		    grid : {
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/bione/schedule/trigger/list.json",
			columns : [ {
			    name : 'triggerId',
			    hide : 1,
			    width : '0'
			}, {
			    display : '触发器名称',
			    name : 'triggerName',
			    width : '242'
			}, {
			    display : '备注',
			    name : 'remark',
			    width : '300'
			} ],
			pageSize : 10,
			checkbox : false,
			switchPageSizeApplyComboBox : false
		    }
		}
	    } ]

	});
	BIONE.loadForm(mainform, {
	    url : "${ctx}/bione/schedule/task/showInfo.json?id=${id}"
	});

	$("#mainform input:hidden[name = testTriggerId]").val(triggerId);
	$("#mainform input[name=taskId]").attr("readonly", "readonly");

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));

	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("taskModifyWin", null);
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
	var taskId = $("#mainform input[name=taskId]").val();
	var beanName = $("#mainform input[name=beanName]").val();
	var triggerId = $.ligerui.get("triggerCombBox").getValue();

	$.ajax({
	    cache : false,
	    async : true,
	    url : "${ctx}/bione/schedule/task/checkIsRunning",
	    dataType : 'json',
	    type : "post",
	    data : {
		"taskId" : taskId,
		"beanName" : beanName,
		"triggerId" : triggerId
	    },
	    success : function(result) {
		if (result) {
		    if (result && result == "running") {
			BIONE.tip("该任务正在运行中，请稍候再试");
		    } else {
			BIONE.submitForm($("#mainform"), function(result) {
			    BIONE.closeDialogAndReloadParent("taskModifyWin",
				    "maingrid", "修改成功");
			}, function() {
			    BIONE.closeDialog("taskModifyWin", "修改失败");
			});
		    }
		}
	    }
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