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
		
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ {
					name : 'txId',
					type : 'hidden'
			    }, {
					display : "交易编号<font color='red'>*</font>",
					name : "txCode",
					newline : true,
					group : "交易详情",
					groupicon : groupicon,
					type : "text",
					validate : {
						required : true,
						maxlength : 32,
					    remote : {
							url : "${ctx}/ecif/transaction/txdef/resDefNoValid",
							type : "POST"
						    },
						messages : {
							remote : "交易编号重复"
						}
				}
			}, {
				display : "交易名称<font color='red'>*</font>",
				name : "txName",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "交易中文名称<font color='red'>*</font>",
				name : "txCnName",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "交易类型一级分类<font color='red'>*</font>",
				name : "txLvl1TpBox",
				newline : false,
				
				type:'select',
				options:{
					valueFieldID:'txLvl1Tp',
					disabled : true,
					data : [{id : 'W', text : '写交易'}, {id : 'R', text : '读交易'}],
					onSelected : setTxLvl2TpValue
				}
				
			}, {
				display : "交易类型二级分类",
				name : "txLvl2TpBox",
				newline : true,
				
				type:'select',
				options:{
					valueFieldID:'txLvl2Tp',
					disabled : true,
					data : [{id : 'K', text : '开户'}, {id : 'U', text : '修改'}, {id : 'D', text : '删除'}, {id : 'A', text : '增加'}, {id : 'Q', text : '查询'}]
				},
				validate : {
					required : false,
					maxlength : 50
				}
			}, {
				display : "交易处理类名称",
				name : "txDealClass",
				newline : false,
				type : "text",
				disabled : true,
				validate : {
					required : false,
					maxlength : 50
				}
			}, {
				display : "交易配置类型",
				name : "txCfgTpBox",
				newline : true,
				type:'select',
				
				options:{
					valueFieldID:'txCfgTp',
					disabled : true,
					data : [{id : '1', text : '扩展'}, {id : '2', text : '定制'}, {id : '0', text : '标准'}]
				},
				validate : {
					required : false,
					maxlength : 50
				}

			}, {
				display : "交易配置状态<font color='red'>*</font>",
				name : "txStateBox",
				newline : false,
				type : "select",
				
				cssClass : "field",
				options : {
					valueFieldID : 'txState',
					disabled : true,
					data : [{id : '1', text : '启用'}, {id : '0', text : '未启用'}]
				},
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "备注",
				name : "txDesc",
				newline : true,
				type : "textarea",
				validate : {
					required : false,
					maxlength : 50
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/transaction/txdef/${id}"});
			$("#mainform [name='txCode']").focus();
			//$("#mainform input[name=txDealClass]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
			
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

		$("#mainform [name='txCode']").focusout();
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("resDefManage","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("resDefManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("resDefManage");
	}
	
	function setTxLvl2TpValue(value) {
		//alert('11');
		//debugger;
		var selectData1 = [{id : 'Q', text : '查询'}];
		var selectData2 = [{id : 'K', text : '开户'}, {id : 'U', text : '修改'}, {id : 'D', text : '删除'}, {id : 'A', text : '增加'}];
		var g = $.ligerui.get('txLvl2TpBox');
		
		if(value =='W'){
			g.setData(selectData2);
		}else if(value =='R'){
			g.setData(selectData1);
		}
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/transaction/txdef/copytx" method="post"></form>
	</div>
</body>
</html>