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
				name : 'alarmConfId',
				type : 'hidden'
			}, {
				display : "报警系统<font color='red'>*</font>",
				name : "alarmSysBox",
				newline : true,
				group : "报警分配",
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
				display : "报警对象类型<font color='red'>*</font>",
				name : "alarmObjectTypeBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'alarmObjectType',
					data : [{id : '0', text : '分组'},{id : '1', text : '人员'}],
					onSelected : function(value) {
						$("#mainform input[name='alarmObjectIdBox']").val('');
					}
				},
				validate : {
					required : true,
					maxlength : 20
				}
	         },{ 
				display: "报警对象标识<font color='red'>*</font>", 
				name: "alarmObjectIdBox", newline: false, 
				newline : false,
            	type: "select", 
				options : {
					valueFieldID : 'alarmObjectId',
					ajaxType : "get",			
					url : "${ctx}/ecif/alarm/txalarmconf/getComBoBoxById.json?id=${id}",
					onBeforeOpen : function(){
						var objtypeval = $("#mainform input[name='alarmObjectTypeBox']").val();
						objtypeval =encodeURI(encodeURI(objtypeval));   
						$.ajax({
							type : "POST",
							url : "${ctx}/ecif/alarm/txalarmconf/getComBoBox.json?alarmObjectType="+ objtypeval,
							dataType : "json",
							success : function(ajaxData) {
								var g = $.ligerui.get('alarmObjectIdBox');
								g.setData(ajaxData);
							}
						});					
					}
				},	 	            	
				validate : {
					required : true,
					maxlength : 10
				} 	
				
			}, {
				display : "报警方式",
				name : "alarmMethodBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'alarmMethod',
					data : [{id : '0', text : '网页'},{id : '1', text : '邮件'},{id : '2', text : '短信'}]
				},
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "配置状态",
				name : "confStatBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'confStat',
					data : [{id : '1', text : '有效'},{id : '0', text : '无效'}]
				},
				validate : {
					required : true,
					maxlength : 20
				}				
			  }, {
					display : "报警级别",
					name : "alarmLevel",
					newline : true,
					type : "text",
					validate : {
						maxlength : 10,
						range : [1, 9]
					}	
	          }
		 ]
		});
		
		//debugger;
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmconf/${id}"});
			
			/**
			var objtypeval = $("#mainform input[name='alarmObjectTypeBox']").val();
			objtypeval =encodeURI(encodeURI(objtypeval));   

			var selectid =null;
			$.ajax({
				type : "GET",
				async : false,
				url : "${ctx}/ecif/alarm/txalarmconf/${id}",
				dataType : "json",
				success : function(ajaxData) {
					selectid = ajaxData["alarmObjectId"];
				}
			});								
			
	
			$.ajax({
				type : "POST",
				async : false,
				url : "${ctx}/ecif/alarm/txalarmconf/getComBoBox.json?alarmObjectType="+ objtypeval,
				dataType : "json",
				success : function(ajaxData) {
					var g = $.ligerui.get('alarmObjectIdBox');
					g.setData(ajaxData);
					alert(selectid);
					g.selectValue(selectid);
				}
			});					
			**/

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
		<form id="mainform" action="${ctx}/ecif/alarm/txalarmconf" method="post"></form>
	</div>
</body>
</html>