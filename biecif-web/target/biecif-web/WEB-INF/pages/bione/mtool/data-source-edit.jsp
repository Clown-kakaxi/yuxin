<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var flag = false;
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
			fields : [ {
				name : "dsId",
				type : "hidden"
			}, {
				display : "数据源名称 <font color='red'>*</font>",
				name : "dsName",
				newline : true,
				type : "text",
				group : "数据源信息",
				groupicon : groupicon,
				validate : {
					required : true,
					maxlength : 100,
				 	remote: {
						url:"${ctx}/bione/mtool/datasource/dsNameValid?dsId=${id}"
					},
					messages:{
						remote : "数据源名称已存在"
					}
				}
			}, {
				display : "连接驱动<font color='red'>*</font>",
				name : "driverSelect",
				newline : false,
				type : "select",
				options : {
					resize:false,
					valueFieldID : 'driverId',
					textField : 'driverName',
					valueField : 'driverId',
					onSelected : function(value) {
						//alert(flag);
						if("${id}"){
							if(flag){
								getUrl(value);
							}
							flag=true;
						}else{
							getUrl(value);
						}
						
					} 
				},
				validate : {
					required : true
				}
			}, {
				display : "连接用户 <font color='red'>*</font>",
				name : "connUser",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 100
				}
			}, {
				display : "连接密码  <font color='red'>*</font>",
				name : "connPwd",
				newline : false,
				type : "password",
				validate : {
					required : true,
					maxlength : 100	
				}
			}, {
				display : "连接URL<font color='red'>*</font>",
				name : "connUrl",
				newline : true,
				type : "text",
				width : 501
			},{
				display : "描述",
				name : "remark",
				newline : true,
				type : "textarea",
				width : 503,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 250
				}
			} ]
		});
		
		//填充驱动下拉框
		getDriverSelectData();
		
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : save_module
		});
		buttons.push({
			text :"测试",
			onclick : test
		});
		BIONE.addFormButtons(buttons);
			
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#mainform");
		
		if("${id}") {
			//flag=false;
			BIONE.loadForm($("#mainform"), {url : "${ctx}/bione/mtool/datasource/${id}"});
		}
	});
	//---函数---
	//测试按钮函数 
	function test(){
		if($("form:first").valid()){
			var connUser = $("#mainform input[name='connUser']").val();
			var connPwd = $("#mainform [name='connPwd']").val();
			var driver = $("#mainform [name='driverSelect']").val();
			var connUrl = $("#mainform [name='connUrl']").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/bione/mtool/datasource/getTest",
				data:{
					connUser:connUser,
					connPwd:connPwd,
					driver:driver,
					connUrl:connUrl
					},
				success : function(r) {
					if(r){
						BIONE.tip("测试成功 ");
					}
					if(!r){
						BIONE.tip("测试失败 ");
					} 
				}
			});
		 }else{
			BIONE.showInvalid();
		} 
		
	}
	//保存按钮函数 
	function save_module() {
		if($("form:first").valid()){
		var connUser = $("#mainform input[name='connUser']").val();
		var connPwd = $("#mainform [name='connPwd']").val();
		var driver = $("#mainform [name='driverSelect']").val();
		var connUrl = $("#mainform [name='connUrl']").val();
		$.ajax({
			type : "POST",
			url : "${ctx}/bione/mtool/datasource/getTest",
			data:{
				connUser:connUser,
				connPwd:connPwd,
				driver:driver,
				connUrl:connUrl
				},
			success : function(r) {
				if(r){
					BIONE.submitForm($("#mainform"), function() {
						BIONE.closeDialogAndReloadParent("addDsWin", "maingrid", "保存成功");
					}, function() {
						BIONE.tip("保存失败");
					});
				}
				if(!r){
					BIONE.tip("测试未通过,保存失败 ");
				} 
			}
		});
		}else{
			BIONE.showInvalid();
		}
	}
	//取消按钮函数 
	function cancleCallBack() {
		BIONE.closeDialog("addDsWin");
	}
	//填充驱动 
	function getDriverSelectData(){
		$.ajax({
			type : "POST",
			url : "${ctx}/bione/mtool/datasource/getDriverData.json",
			success : function(ajaxData) {
				var selectData = ajaxData.data;
				var g = $.ligerui.get('driverSelect');
				g.setData(selectData);
				if (selectData.length > 0 && selectData[0].driverId != null
						&& selectData[0].driverId != "") {
					g.selectValue(selectData[0].driverId);
					flag=false;
				}
			}
		});
	}
	//填充 URl 
	function getUrl(driverId){
		$.ajax({
			type : "POST",
			url : "${ctx}/bione/mtool/datasource/getUrlData.json?driverId="+driverId,
			dataType : "json",
			success : function(ajaxData) {
				var selectData = ajaxData.data;
				$("#mainform input[name='connUrl']").val(selectData.connUrl);
			}
		});
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/mtool/datasource" method="post"></form>
	</div>
</body>
</html>