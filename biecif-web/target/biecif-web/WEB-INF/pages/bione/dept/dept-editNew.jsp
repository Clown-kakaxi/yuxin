<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	var orgId = '${orgId}';
	var upDeptId = '${deptId}';
	var orgNo='';
	var upNo='';
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	$(function(){
		$.ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/admin/dept/getRealCode.json",
			dataType : 'json',
			type : "get",
			data : {
				"orgId" : orgId,
				"deptId" : upDeptId
			},
			success : function(result) {
				if (!result){					
					return;
				}
				orgNo = result.orgNo;
				upNo = result.deptNo;
				initForm();
			}
		});
	});	
	
	function initForm() {
		$("#mainform").ligerForm({
			fields:[{
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
	        		maxlength:32,
	        		remote :  {
	        			url : "${ctx}/bione/admin/dept/testDeptNo",
						type : "POST",
						data : {
							"orgNo":orgNo
						}
	        		},
					messages : {
						remote:"部门标识已存在"
					}
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
	        		initValue:'1',
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
	            width:503,
	            validate:{
	            	maxlength:500
	            },
	            type:'textarea'
	        }]
		});
		$("#mainform input[name=orgNo]").val(orgNo);
		$("#mainform input[name=upNo]").val(upNo);
		
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
		var buttons = [];
		
		buttons.push({
			text:'取消',
			onclick:function(){
				BIONE.closeDialog("deptAddWin");
			}
		});
		buttons.push({
			text : '保存',
			onclick : f_save
		});
		BIONE.addFormButtons(buttons);
	}
	
	function f_save() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("deptAddWin","maingrid","保存成功");
		}, function() {
			BIONE.closeDialog("deptAddWin","保存失败");
		});
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