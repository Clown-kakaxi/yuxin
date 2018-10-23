<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif", mainform;
	$(function(){
		initForm();
		initButton();
	});	
	
	function initForm() {
		mainform = $("#mainform").ligerForm({
			fields:[{
				name:"deptId",
				type:'hidden'
			},{
				name:'upNo',
				type:'hidden'
			},{
				name:'orgNo',
				type:'hidden'
			},{
				display:'部门标识',
	        	name:'deptNo',
	        	newline:true, 
	        	type:'text',
	        	group: "部门信息",
	        	validate:{
	        		required:true,
	        		maxlength:32
	        	},
				groupicon: groupicon 
			},{
				display:'部门名称',
				name:'deptName',
				newline:false,
				type:'text',
				validate:{
					required:true,
					maxlength:100
				}
			},{
				display:'部门状态',
	        	name:'deptStsValue',
	        	newline:true,
	        	type:'select',
	        	options:{
	        		valueFieldID:'deptSts',
	        		data:[{
	        			text:'启用',
	        			id:'1'
	        		},{
	        			text:'停用',
	        			id:'0'
	        		}]
	        	},
				validate:{
					required:true
				}
			},{
	            display:'备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
	            name:'remark',
	            newline:true,
	            width:'503',
	            validate:{
	            	maxlength:500
	            },
	            type:'textarea'
	        }]
		});
		if("${id}") {
			BIONE.loadForm(mainform, { url : "${ctx}/bione/admin/dept/showInfo.json?id=${id}"});
		}
		var managers = $.ligerui.find($.ligerui.controls.Input);
	    for (var i = 0, l = managers.length; i < l; i++)
	    {
	        //改变了表单的值，需要调用这个方法来更新ligerui样式
	        managers[i].updateStyle();
	    }		
	}
	
	function initButton() {
		var buttons = [];
		if("${isBasicDept}") {
			
			$("#mainform input[name]").attr("readonly", "readonly").css("color", "black");
			$("#mainform textarea[name]").attr("readOnly", "readOnly");
			
			buttons = [{
				text : '关闭',
				onclick : closeDialog
			}];
		} else {
			
			$("#mainform input[name=deptNo]").attr("readonly", "readonly");
			jQuery.metadata.setType("attr", "validate");
			BIONE.validate($("#mainform"));
			
// 			buttons = [{
// 				text : '取消',
// 				onclick : closeDialog
// 			}, {
// 				text : '修改',
// 				onclick : dept_save
// 			}];
			
			buttons = [];
			buttons.push({
				text : '取消',
				onclick : closeDialog
			});
			if("" == "${flag}") {
				buttons.push({
					text : '修改',
					onclick : dept_save
				});
			}
		}
		BIONE.addFormButtons(buttons);
	}
	
	function dept_save() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("deptModifyWin","maingrid","修改成功");
		}, function() {
			BIONE.closeDialog("deptModifyWin","修改失败");
		});
	}
	
	function closeDialog(){
		BIONE.closeDialog("deptModifyWin");
	}
</script>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/bione/admin/dept">
	</form>
</div>
</body>
</html>