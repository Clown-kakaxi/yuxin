<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">

	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	 $(function() {
		$("#mainform").ligerForm({
			fields : [  {
				name : "custRelId",
				type : "hidden"
			},{
				display : "源客户号",
				name : "srcCustNo",
				newline : true,
				labelWidth : 130,
				options :{ 
					disabled : true
				},
				type : "text"
			}
			,{
				display : "目标客户号",
				name : "destCustNo",
				labelWidth : 130,
				newline : false,
				options :{ 
					disabled : true
		},
				type : "text"
			},{
				display : "关系开始日期",
				name : "custRelStart",
				labelWidth : 130,
				options :{
					disabled : true
				},
				newline : true,
				type : "date"
			},{
				display : "关系结束日期",
				name : "custRelEnd",
				labelWidth : 130,
				options :{
					disabled : true
				},
				newline : false,
				type : "date"
			}
			, {
				display : "关系种类",
				name : "relationClass",
				newline : true,
				type : "select",
				labelWidth : 130,
				cssClass : "field",
				options : {
					disabled : true,
					valueFieldID : 'relationClassTemp',
					onSelected : function(val) {
					}
				}
			}
			, {
				display : "关系类型",
				name : "custRel",
				newline : false,
				type : "select",
				labelWidth : 130,
				cssClass : "field",
				options : {
					disabled : true,
					valueFieldID : 'custRelType',
					onSelected : function(val) {
						//var url='${ctx}/ecif/customerrelationlook/getCodeComBoBox.json?paramValue='+val;
						var url='${ctx}/ecif/customerrelationlook/getCodeMapRelType.json?paramValue='+val;
	        			$.get(url, {}, function(ret, status) {
	        				$("#custRel").val(ret);
		        			if( val > 1001001000 && val < 2001001000 ){
		        				$("#relationClass").val("机构类客户与机构类客户");
		        			}else if( val > 2001001000 && val < 3000000000 ){
		        				$("#relationClass").val("机构类客户与个人类客户");
		        			}else if( val > 3000000000 && val < 5000000000 ){
		        				$("#relationClass").val("个人类客户与个人类客户");
		        			}
	        			});
					}
				}
			}/* , {
				display : "客户间关系状态<font color='red'>*</font>",
				name : "custRelStatBox",
				newline : true,
				labelWidth : 130,
				disabled : true,
				type : "select",
				options :{
					disabled : true,
					valueFieldID : "custRelStat",
					url : "${ctx}/ecif/customerrelationlook/getRenderRelStatBox.json",
					ajaxType : "get"
				},
			} */
			,{
				display : "客户间关系类型描述",
				name : "custRelDesc",
				newline : true,
				labelWidth : 130,
				width : 475,
				type : "textarea",
				validate : {
					//required : true,
					maxlength : 200
				}
			}
			
			]
		});
		
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/customerrelationlook/${id}.json"});
			$("#mainform input[name=custRelId]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
		
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
		var buttons = [];

		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : f_save
		});
		BIONE.addFormButtons(buttons);
	});
	 
	function f_save() {
		BIONE.submitForm($("#mainform"), function() {
			//diagname
			BIONE.closeDialogAndReloadParent("customerrelationlookAdd", "maingrid", "保存提交待审批");
		}, function() {
			BIONE.closeDialog("customerrelationlookAdd", "保存失败");
		});
	}
	
	function cancleCallBack() {
		BIONE.closeDialog("customerrelationlookAdd");
	}
	
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/customerrelationlook" method="post"></form>
	</div>
</body>
</html>