<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//debugger;
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ {
				name : 'alarmRuleId',
				type : 'hidden'
			}, {
				display : "报警系统<font color='red'>*</font>",
				name : "alarmSysBox",
				newline : true,
				group : "报警规则",
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID : "alarmSys",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "报警模块<font color='red'>*</font>",
				name : "alarmModuleBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'alarmModule',
					data : [{id : 'SERVICE', text : '服务'},{id : 'TX', text : '交易'}, {id : 'SYN', text : '同步'}, {id : 'ETL', text : 'ETL'}]
				},
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "错误代码",
				name : "errorCode",
				newline : true,
				type : "text",	 	            	
				validate : {
					maxlength : 20
				} 	
	         },{ 
				display: "是否报警<font color='red'>*</font>", 
				name: "isAlarmBox", newline: false, 
				newline : false,
            	type: "select", 
				options : {
					valueFieldID : 'isAlarm',
					data : [{id : '1', text : '是'},{id : '0', text : '否'}]
				},	 	            	
				validate : {
					required : true,
					maxlength : 10
				} 	
			  }, {
					display : "报警级别",
					name : "alarmLevel",
					newline : true,
					type : "text",	 	            	
					validate : {
						maxlength : 20,
						range : [1, 9]

					} 	
	          }
		 ]
		});
		
		//debugger;
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmrule/${id}"});
			$("#mainform input[name=resDefNo]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : save_resDef
		});
		BIONE.addFormButtons(buttons);

	});
	function save_resDef() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("resDefManage","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("resDefManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("resDefManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/alarm/txalarmrule" method="post"></form>
	</div>
</body>
</html>