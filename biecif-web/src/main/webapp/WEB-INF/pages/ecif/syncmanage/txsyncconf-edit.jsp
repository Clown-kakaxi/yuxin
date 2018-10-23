<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		var state = parent.state;
		mainform = $("#mainform");
		mainform.ligerForm({
			fields: [{
				type: "hidden",
				name: "syncConfId"
				/**
			}, {
				type: "hidden",
				name: "createOper"
			}, {
				type: "hidden",
				name: "createTime",	
				format : 'yyyy-MM-dd hh:mm:ss'
			}, {
				type: "hidden",
				name: "updateOper"
			}, {
				type: "hidden",
				name: "updateTime"
					**/
			}, {

				type: "hidden",
				name: "approvalOper"
			}, {
				type: "hidden",
				name: "approvalTime"
			}, {
				name: "txCode",
				display: "交易代码<font color='red'>*</font>",
				newline: true,
				groupicon: "${ctx}/images/classics/icons/communication.gif",
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "txCode",

					url : "${ctx}/ecif/transaction/txserviceauth/getComBoBox.json",
					ajaxType : "get",
					onSelected:function(value,text){
						//$("#mainform [name='txCode']").val(value);
						$("#mainform [name='txName']").val(text);
						txNameGloable = value;
					}
				},
				group: "数据同步配置",
				validate: {
					required : true,
					maxlength: 32
				}
			}, {
				name: "srcSysNoName",
				display: "源系统<font color='red'>*</font>",
				newline: false,
				type : "select",
				options : {
					valueFieldID : "srcSysNo",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},				
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				name: "destSysNoName",
				display: "目标系统<font color='red'>*</font>",
				newline: true,
				type : "select",
				options : {
					valueFieldID : "destSysNo",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},				
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				type: "select",
				name: "syncConfStat_select",
				display: "同步配置状态<font color='red'>*</font>",
				newline: false,
				options: {
					valueFieldID:'syncConfStat',
					data: parseSelect(state.syncConfStat)
				},				
				validate: {
					required : true,
					maxlength: 10
				}
			}, {
				type: "text",
				name: "syncConfDesc",
				display: "同步配置描述",
				newline: true,
				validate : {
					required : false,
					maxlength : 255
				}
			}, {
				type: "select",
				name: "syncMode_select",
				display: "数据同步模式<font color='red'>*</font>",
				newline: false,
				options: {
					valueFieldID:'syncMode',
					data: parseSelect(state.syncMode)
				},
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				type: "select",
				name: "syncMethod_select",
				display: "数据同步方式<font color='red'>*</font>",
				newline: true,
				options: {
					valueFieldID:'syncMethod',
					data: parseSelect(state.syncMethod)
				},				
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				type: "select",
				name: "syncDealMethod_select",
				display: "同步处理方式<font color='red'>*</font>",
				newline: false,
				options: {
					valueFieldID:'syncDealMethod',
					data: parseSelect(state.syncDealMethod)
				},				
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				type: "text",
				name: "syncContentDef",
				display: "同步内容定义",
				newline: true,
				validate: {
					maxlength: 255
				}
			}, {
				type: "text",
				name: "syncContentDesc",
				display: "同步内容描述",
				newline: false,
				validate: {
					maxlength: 255
				}
			}, {
				type: "text",
				name: "syncDealClass",
				display: "同步处理类<font color='red'>*</font>",
				newline: true,
				validate: {
					required : true,
					maxlength: 255
				}
			}, {
				type: "select",
				name: "isRetry_select",
				display: "是否重试<font color='red'>*</font>",
				newline: false,
				options: {
					valueFieldID:'isRetry',
					data: parseSelect(state.isRetry)
				},validate: {
					required : true,
					maxlength: 10
				}
			}, {
				type: "number",
				name: "maxRetry",     
				display: "最大重试次数",
				newline: true,
				validate: {
					required : false,
					maxlength: 10
				}
			}, {
				type: "select",
				name: "syncFailStrategy_select",
				display: "失败处理策略<font color='red'>*</font>",
				newline: false,
				options: {
					valueFieldID:'syncFailStrategy',
					data: parseSelect(state.syncFailStrategy)
				},
				validate: {
					required : false,
					maxlength: 20
				}
			}, {
				type: "date",
				name: "effectiveTime",
				display: "生效时间",
				newline: true
			}, {
				type: "date",
				name: "expiredTime",
				display: "失效时间",
				newline: false
			}]
		});
		
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/syncmanage/syncconf/show/${id}"});
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
			onclick : save
		});
		BIONE.addFormButtons(buttons);

	});
	function save() {
		$("#createTime").val(parent.toDate($("#createTime").val()));
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("frame","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("frame", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("frame");
	}
	
	function parseSelect(data) {
		var arr = [];
		$.each(data, function(k, v) {
			arr.push({
				"id": k,
				"text": v
			});
		});
		return arr;
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/syncmanage/syncconf" method="post"></form>
	</div>
</body>
</html>