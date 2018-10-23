<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	jQuery.extend(jQuery.validator.messages, {
		greaterThan : "结束日期应大于开始日期"
	});
	
	var custRelTypeFlag = "";
	var isNoTypeFlag = "";

	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	 $(function() {
		$("#mainform").ligerForm({
			fields : [  {
				name : "custRelId",
				type : "hidden"
			},{
				display : "源客户号<font color='red'>*</font>",
				name : "srcCustNo",
				newline : true,
				labelWidth : 130,
				options :{ 
					onChangeValue : function(value){
							custNo = value;
							var url='${ctx}/ecif/customerrelationlook/getCustomer.json?custNo='+custNo;
		        			$.get(url, {}, function(ret, status) {
		        				if('true' == ret){
		        					alert("此用户不存在，请重新添加");
		        					$("#srcCustNo").val("");
		        				}
		        			});
						}
				},
				type : "text",
				validate : {
					required : true
				}
			}
			,{
				display : "目标客户号<font color='red'>*</font>",
				name : "destCustNo",
				labelWidth : 130,
				newline : false,
				options :{ onChangeValue : function(value){
							custNo = value;
							var url='${ctx}/ecif/customerrelationlook/getCustomer.json?custNo='+custNo;
        					$.get(url, {}, function(ret, status) {
        						if('true' == ret){
        							alert("此用户不存在，请重新添加");
        							$('#destCustNo').val("");
        						}
        						var srcNo = $('#srcCustNo').val();
        						if(srcNo == custNo){
        							alert("不能为相同用户，请重新添加");
        							$('#destCustNo').val("");
        						}
        				});
					}
				},
				type : "text",
				validate : {
					required : true
				}
			},{
				display : "关系开始日期<font color='red'>*</font>",
				name : "custRelStart",
				labelWidth : 130,
				newline : true,
				type : "date",
				validate : {
					required : true,
					maxlength : 200
				}
			},{
				display : "关系结束日期<font color='red'>*</font>",
				name : "custRelEnd",
				labelWidth : 130,
				newline : false,
				type : "date",
				validate : {
					required : true,
					maxlength : 200,
					greaterThan : 'custRelStart'
				}
			}
			, {
				display : "关系种类",
				name : "relationClass",
				newline : true,
				type : "select",
				labelWidth : 130,
				cssClass : "field",
				options : {
					data : [ 
					{
						id : '1',
						text : '机构类客户与机构类客户'
					}, {
						id : '2',
						text : '机构类客户与个人类客户'
					}, {
						id : '3',
						text : '个人类客户与个人类客户'
					} ],
					onSelected : function(val) {
						    $("#custRel").ligerGetComboBoxManager().setData();
// 		        			alert(val);
		        			adapid = val;
		        			var url='${ctx}/ecif/customerrelationlook/getModeVer.json?adapterId='+adapid;
		        			$.get(url, {}, function(ret, status) {
		        				
		        				$("#custRel").ligerGetComboBoxManager().setData(ret);
		        				
		        			});
					}
				},
				attr : {
					op : "="//,
				}
			}, {
				display : "关系类型<font color='red'>*</font>",
				name : "custRel",
				newline : false,
				type : "select",
				labelWidth : 130,
				cssClass : "field",
				options : {
					valueFieldID : 'custRelType',
					onSelected : function(val) {
						var srcCustNo = $("#srcCustNo").val();
						var destCustNo = $("#destCustNo").val();
						if(val != "" && srcCustNo != "" && destCustNo != ""){
		        			var url='${ctx}/ecif/customerrelationlook/isAllType.json?'+new Date().getTime()+'&relType='+val+'&srcCustNo='+srcCustNo+'&destCustNo='+destCustNo;
							$.ajax({
								url : url,
								async : false,
								type : 'post',
								success : function(data) {
		 	        				custRelTypeFlag = data;
		// 	        				alert(custRelTypeFlag);
		 	        				if("false" == custRelTypeFlag){
		 	        					alert("您选则的类型不允许新增,请重新填写");
		 	        					$('#custRelType').valid("");
		 	        					$('#custRelType').val("");
		 	        				}
								}
							});
						}
					}
				},
				validate : {
					required : true,
					maxlength : 200
				}
			
			}/* , {
				display : "客户间关系状态<font color='red'>*</font>",
				name : "custRelStatBox",
				newline : true,
				labelWidth : 130,
				type : "select",
				validate : {
					required : true
				},
				options : {
					valueFieldID : 'custRelStat',
					data : [ {id : '1',text : '有效'}, {id : '2',text : '无效'}]
				}
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
			}]
		});
		
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}//ecif/customerrelationlook/${id}.json"});
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
			onclick : brefore_save
		});
		BIONE.addFormButtons(buttons);
	});
	 
	function brefore_save(){
		var srcCustNoComm = $("#srcCustNo").val();
		var destCustNoComm = $("#destCustNo").val();
		var custrelTypeComm = $("#custRelType").val();
		if(custrelTypeComm == ""){
			alert("请重新选择关系类型");
			return;
		}
		custRelTypeFlag = "";
		isNoTypeFlag = "";
		var url2='${ctx}/ecif/customerrelationlook/isAllType.json?'+new Date().getTime()+'&relType='+custrelTypeComm+'&srcCustNo='+srcCustNoComm+'&destCustNo='+destCustNoComm;
		/* $.get(url2, {}, function(ret, status) {
			custRelTypeFlag = ret;
			alert("custRelTypeFlag"+custRelTypeFlag);
		}); */
		$.ajax({
			url : url2,
			async : false,
			type : 'post',
			success : function(data) {
 				custRelTypeFlag = data;
 				if("false" == custRelTypeFlag){
 					//alert("您选则的类型不允许新增,请重新填写");
 					$('#custRelType').valid("");
 					$('#custRelType').val("");
 				}
			}
		});
		if(custRelTypeFlag == "false"){
			alert("您选择的关系类型不对，请您重新选择");
		}
		var url='${ctx}/ecif/customerrelationlook/isNoTypeRight.json?'+new Date().getTime()+'&srcCustNo='+srcCustNoComm+'&destCustNo='+destCustNoComm+'&custrelType='+custrelTypeComm;
		/* $.get(url, {}, function(ret, status) {
			//alert(ret);
			isNoTypeFlag = ret;
 			alert("isNoTypeFlag"+isNoTypeFlag);
		}); */
		$.ajax({
			url : url,
			async : false,
			type : 'post',
			success : function(data) {
				isNoTypeFlag = data;
			}
		});
		if(isNoTypeFlag == "false"){
			alert("您新增的关系已存在或正在审批中，请重新添加");
		}
		//alert(custRelTypeFlag + "-" +isNoTypeFlag);
		if((custRelTypeFlag == "true") && (isNoTypeFlag == "true")){
			f_save();
		}
	}

	function f_save() {
		BIONE.submitForm($("#mainform"), function() {
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