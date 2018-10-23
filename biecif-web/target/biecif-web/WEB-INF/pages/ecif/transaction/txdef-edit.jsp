<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">

<head>
<script type="text/javascript">

	//标准代码名称有效性验证
	jQuery.validator.addMethod("txCnNameReg", function(value, element) {
		
	    var txCnName = /[\u4E00-\u9FA5\uf900-\ufa2d]/;  
	    //alert(txCnName.test(value));
	    return (txCnName.test(value));
	}, "请输入中文!"); 
</script>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		
		mainform = $("#mainform");
		mainform.ligerForm({
		 	labelWidth : 120,
			inputWidth : 160,
			space : 30, 
			fields : [
			        {
						name : 'txId',
						type : 'hidden'
			   		 }, {
							name : 'createUser',
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
							url : "${ctx}/ecif/transaction/txdef/resDefNoValid?id=${id}",
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
					maxlength : 40
				}
			}, {
				display : "交易中文名称<font color='red'>*</font>",
				name : "txCnName",
				
				newline : true,
				type : "text",
				validate : {
					required : true,
					txCnNameReg : true,
					maxlength : 80
				}
			}, {
				display : "交易类型一级分类<font color='red'>*</font>",
				labelWidth : 120,
				name : "txLvl1TpBox",
				newline : false,
				type:'select',
				options:{
					valueFieldID:'txLvl1Tp',
					data : [{id : 'W', text : '写交易'}, {id : 'R', text : '读交易'}],
					onSelected : setTxLvl2TpValue
				},
				validate : {
					required : true
				}
			}, {
				display : "交易类型二级分类",
				labelWidth : 120,
				name : "txLvl2TpBox",
				newline : true,
				type:'select',
				options:{
					valueFieldID:'txLvl2Tp',
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
				validate : {
					required : false,
					maxlength : 255
				}
			}, {
				display : "交易配置类型",
				name : "txCfgTpBox",
				newline : true,
				type:'select',
				options:{
					valueFieldID:'txCfgTp',
					data : [{id : '1', text : '扩展'},  {id : '0', text : '标准'}]
					//onSelected : setDealEngine
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
					data : [{id : '1', text : '启用'}, {id : '0', text : '未启用'}]
				},
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "交易处理引擎名称",
				name : "txDealEngine",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 255
				}
			}, {
				display : "交易客户类型<font color='red'>*</font>",
				name : "txCustTypeBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'txCustType',
					data : [{id : '1', text : '个人'}, {id : '2', text : '公司'}, {id : '3', text : '同业'}, {id : '0', text : '未知'}]
				},
				validate : {
					required : true,
					maxlength : 50
				}
			} , {
				display : "交易客户识别规则",
				name : "txDiscUrlName",
				newline : true,
				type : "select",
				options : {
					valueFieldID : "txDiscUrl",
					url : "${ctx}/ecif/rulemanage/txbizruleconf/getComBoBox.json",
					ajaxType : "get"
				}	
	         },{ 
					display: "是否进行XSD验证", name: "txCheckXsdBox", newline: false, 
					newline : false,
 	            	type: "select", 
 					options : {
 						valueFieldID : 'txCheckXsd',
 						data : [{id : '1', text : '是'},{id : '0', text : '否'} ]
 					},	 	            	
 					validate : {
 						maxlength : 20
 					} 	
	         },{ 
					display: "是否区分新增与更新", name: "txDivInsUpdBox", newline: false, 
					newline : true,
	            	type: "select", 
					options : {
						valueFieldID : 'txDivInsUpd',
						data : [{id : '1', text : '是'},{id : '0', text : '否'}]
					},	 	            	
					validate : {
						maxlength : 20
					} 	
			}, {
				display : "备注",
				name : "txDesc",
				newline : true,
				type : "textarea",
				width : 450,
				validate : {
					required : false,
					maxlength : 255
				}
			} ]
		});
		
		//alert("${id}");
		
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/transaction/txdef/${id}"});
			
			//$("#mainform input[name=resDefNo]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
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
	
	function setDealEngine(value) {

		var g = $.ligerui.get('txDealEngine');
		
		if(value =='1'){
			g.setEnabled();
		}else{
			g.setDisabled();
		}
	}
	
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/transaction/txdef" method="post"></form>
	</div>
</body>
</html>