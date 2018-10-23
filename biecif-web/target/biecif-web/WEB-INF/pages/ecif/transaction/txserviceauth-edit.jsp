<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var clientAuthId = '${clientAuthId}';
	var txNameGloable = '';
	//alert(clientAuthId);
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ 
			{
				display : "服务端授权标识",
				name : "serviceAuthId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "客户端授权标识",
				name : "clientAuthId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "选择交易<font color='red'>*</font>",
				name : "txCodeff",
				newline : true,
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
				
				validate : {
					required : true,
					maxlength : 50,
	        		remote :  "${ctx}/ecif/transaction/txserviceauth/checkserviceId?clientAuthId="+clientAuthId,
					messages : {
						required :"交易不能为空",
						remote:"交易已存在"
					}					
				}
			}, {
				display : "交易名称<font color='red'>*</font>",
				name : "txName",
				newline : true,
				type : "hidden",
				validate : {
					required : true,
					maxlength : 40
				}
			}, {
				display : "授权类型<font color='red'>*</font>",
				name : "authTypeBox",
				newline : true,
				type : "select",
				options : {
					valueFieldID : "authType",
					url : "${ctx}/bione/common/getComboxData.json?paramTypeNo=AUTH_TYPE",
					ajaxType : "get"
				},
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "有效性<font color='red'>*</font>",
				name : "flagbox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'flag',
					data : [{id : '1', text : '有效'}, {id : '0', text : '无效'}]
				},
				validate : {
					required : true,
					maxlength : 50
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/transaction/txserviceauth/${id}"});
			$("#mainform input[name=resDefNo]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
		
		$("#mainform input[name=clientAuthId]").val(clientAuthId);
		//$("#mainform input[name=txName]").val(txNameGloable);
		
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
		<form id="mainform" action="${ctx}/ecif/transaction/txserviceauth" method="post"></form>
	</div>
</body>
</html>