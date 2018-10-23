<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var mainform;
	$(function(){
		var groupicon = "${ctx}/images/classics/icons/communication.gif";
	
		mainform = $("#mainform").ligerForm({
			align : "centre",
			fields : [{ 
				name:"logicSysId",
				type:"hidden"
			},{ 
				name:"isBuiltin",
				type:"hidden"
			},{
				display : "逻辑系统缩写<font color=\"red\">*</font>",
				name : "logicSysNo",
				newline : true,
				type : "text",
				group : "逻辑系统",
				groupicon : groupicon,
				validate:{
	        		required:true,
	        		maxlength:32,
	        		remote :  "${ctx}/bione/admin/logicSys/checkLogicSysNo",
					messages : {
						remote : "该逻辑系统已存在"
					}
	        	}
			},{
				display : "逻辑系统名称<font color=\"red\">*</font>",
				name : "logicSysName",
				newline : false,
				type : "text",
				validate : {
					required : true
				}
			}, {
				display : "认证方式<font color=\"red\">*</font>",
				name : "authTypeName",
				newline : true,
				type : "select",
				options : {
					valueFieldID : "authTypeNo",
					url : "${ctx}/bione/admin/authInfo/findForCombo.json",
					ajaxType:"get"
				},
				validate : {
					required : true
				}
			},/* {
				display : "图标选择 ",
				name : "logicSysIcon",
				newline : false,
				type : "select",
				comboboxName : "IconBoxID",
		        options : {
					valueFieldID : "logicSysIcon",
					url : "${ctx}/bione/logicSys/logic-sys!findImgForCombo.json",
					ajaxType:"get"
				},
 				validate : {
 					required : false
 				}
			}, */{
				display : "基线机构",
				name : "basicOrgStsName",
				newline : false,
				type : "select",
				comboboxName : "basicOrgStsID",
				options:{
            		valueFieldID:'basicOrgSts',
            		data:[{
            			text:'是',
            			id:'1'
            		},{
            			text:'否',
            			id:'0'
            		}]
            	},
				validate : {
					required : true
				}
			},{
				display : "基线部门",
				name : "basicDeptStsName",
				newline : true,
				type : "select",
				comboboxName : "basicDeptStsID",
				options:{
            		valueFieldID:'basicDeptSts',
            		data:[{
            			text:"是",
            			id:'1'
            		},{
            			text:'否',
            			id:'0'
            		}]
            	},
				validate : {
					required : true
				}
			},{
				display : "是否启用",
				name : "logicSysStsName",
				newline : false,
				type : "select",
				comboboxName : "logicSysStsID",
				options:{
            		valueFieldID:'logicSysSts',
            		data:[{
            			text:'启用',
            			id:'1'
            		},{
            			text:'停用',
            			id:'0'
            		}]
            	},
				validate : {
					required : true
				}
			},{
				display : "备注",
				name : "remark",
				newline : true,
				type : "textarea",
				width : 503,
				attr : {
					style : "resize: none;"
				}
			},{
				name:"orderNo",
				type:"hidden"
			}, {
				display : "系统版本<font color=\"red\">*</font>", name : "systemVersion", newline : true, type : "text", group : "版本信息", groupicon : groupicon, width : 503,
				validate : {
					required : true
				}
			}, {
				display : "中文版权<font color=\"red\">*</font>", name : "cnCopyright", newline : true, type : "text", width : 503,
				validate : {
					required : true
				}
			}, {
				display : "英文版权<font color=\"red\">*</font>", name : "enCopyright", newline : true, type : "text", width : 503,
				validate : {
					required : true
				}
			} ]
		});
		
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick:f_close
		});
		buttons.push({
			text : '保存',
			onclick : f_save
		});
		BIONE.addFormButtons(buttons);
		
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#mainform");
		
	/* 	//客户 在弹出grid中选择
		$.ligerui.get("IconBoxID").openSelect({
			url : '${ctx}/bione/logicSys/logic-sys!selectImages.xhtml',
			dialogname:'iconselector',
			title:'选择图标',
			comboxName:'IconBoxID',
			dialogType:'icon'
		});  */
		
		// id 存在为修改, id 不存在为新增
		if ("${id}") {
			$("#mainform [name='logicSysNo']").attr("readonly", "true");
			$("#mainform input[name=logicSysNo]").removeAttr("validate");
			BIONE.loadForm(mainform, {
				url : "${ctx}/bione/admin/logicSys/${id}"
			});
		} else {

			$("#logicSysName").change(function() {
				$("#systemVersion").val($(this).val());
			});
			$.ligerui.get("basicOrgStsID").selectValue(1);
			$.ligerui.get("basicDeptStsID").selectValue(1);
			$.ligerui.get("logicSysStsID").selectValue(1);
		}
	});
	
	function f_save() {
		$("#mainform input[name=logicSysIcon]").val($("#mainform input[name=IconBoxID]").val());
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("logicSysWin","maingrid","保存成功");
		}, function() {
			BIONE.closeDialog("logicSysWin","保存失败");
		});
	}
	
	function f_close(){
		BIONE.closeDialog("logicSysWin");
	}
	</script>
</head>
<body>
	<div id="template.center">
		<form action="${ctx}/bione/admin/logicSys" method="post"
			id="mainform"></form>
	</div>
</body>
</html>