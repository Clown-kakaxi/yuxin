<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template14.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var mainform;
	var isStart = eval('(${isStart})');
	var id="${id}";
	var upNo='${orgNo}';
	var upName='${upName}';
    var field = [ {
		name : "orgId",
		type : "hidden"
	},{
    	name:'upNo',
    	type:'hidden'
    },{
		display : "机构编号",
		name : "orgNo",
		newline : true,
		type : "text",
		validate : {
		    required : true,
		    maxlength : 32/* ,
		    remote : "${ctx}/bione/admin/org!testOrgNo.xhtml",
		    messages : {
				remote : "机构编号已存在"
		    } */
		},
		group : "机构信息",
		groupicon : groupicon
	}, {
		display : "机构名称",
		name : "orgName",
		newline : false,
		type : "text",
		validate : {
		    required : true,
		    maxlength : 100
		}
	}, {
		display : "上级机构 ",
		name : "upOrg",
		newline : true,
		type : "text"
	}, {
		display : '机构状态',
		id : 'orgStsValue',
		name : 'orgStsValue',
		newline : false,
		type : 'select',
		options:{
    		valueFieldID:'orgSts',
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
    }, {
		display : "备注 ",
		name : "remark",
		newline : true,
		type : "textarea",
		width : 475,
		validate : {
		    maxlength : 500
		}
    } ];
   
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 170,
		    labelWidth : 90,
		    space : 40,
		    fields : field
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		$("#mainform [name='upNo']").val(upNo);
		if(upName==null||upName=="null"){
			upName="";
		}
		$("#mainform [name='upOrg']").val(upName);
		$("#mainform [name='upOrg']").attr("disabled", "disabled");
		$("#mainform [name='orgNo']").attr("readOnly", "readOnly");
		
		if (!isStart) {
			$("#mainform [name='orgName']").attr("disabled", "disabled");
			$("#mainform [name='orgStsValue']").attr("readOnly", "readOnly");
			$("#mainform [name='remark']").attr("disabled", "disabled");
		}
		
		BIONE.loadForm($("#mainform"), {
		    url : "${ctx}/bione/admin/org/show.json?id=" + id
		}, function(){
		});
	});
	
	function f_save() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.tip("保存成功");
			window.parent.saveSuccess();
			window.parent.currentNode=null;
		}, function() {
			BIONE.tip("保存失败");
		});
	}
</script>

<title>机构管理</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/bione/admin/org">
	</form>
</div>
</body>
</html>