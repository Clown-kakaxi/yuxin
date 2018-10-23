<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	jQuery.extend(jQuery.validator.messages, {
		greaterThan : "结束日期应大于开始日期"
	});

	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var flag = false;
	if("${id}" != ""){
		flag = true;
	}
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
			fields : [{
				name : "specialListId",
				type : "hidden"
			}, {
				display : "客户编号",
				name : "custNo",
				newline : false,
				type : "text",
				group : "黑名单信息",
				groupicon : groupicon,
				options : {
					disabled : flag
				},
				validate : {
					maxlength : 32/*,
				 	remote: {
						url:"${ctx}/bione/admin/module/moduleNoValid"
					},
					messages:{
						remote : "名称重复"
					}*/
				}
			}, {
				display : "黑名单类别 <font color='red'>*</font>",
				name : "specialListKindBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "specialListKind",
					//data : [{id : '1', text : '信贷黑名单'}, {id : '2', text : '客户黑名单'}],
					url : "${ctx}/ecif/customer/speciallist/getComBoBoxSpecialListKind.json",
					ajaxType : "get",
					disabled : flag
				},
				validate : {
					required : true,
					messages : {
						required : "黑名单类别不能为空。"
					}
				}
			},{
				display : "客户类型<font color='red'>*</font>",
				name : "relationClass",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					data : [ 
					{
						id : '1',
						text : '个人类客户'
					}, {
						id : '2',
						text : '机构类客户'
					}/* , {
						id : '3',
						text : '个人类客户与个人类客户'
					} */ ],
					onSelected : function(val) {
					    $("#identTypeBox").ligerGetComboBoxManager().setData();
	        			adapid = val;
	        			var url='${ctx}/ecif/customer/speciallist/getModeVer.json?adapterId='+adapid;
	        			$.get(url, {}, function(ret, status) {
	        				$("#identTypeBox").ligerGetComboBoxManager().setData(ret);
	        			});
					},
					disabled : flag
				}
			}
			, {
				display : "证件类型 <font color='red'>*</font>",
				name : "identTypeBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'identType',
					//onSelected : function(val) {},
					disabled : flag,
					onSelected : function(val) {
						var type = $("#relationClass").val();
						var temp = val.substring(0,2);
						if(type == ""){
							if(temp == "01"){
								$("#relationClass").val("个人类客户");
							}else{
								$("#relationClass").val("机构类客户");
							}
							var url='${ctx}/ecif/customer/speciallist/getCodeMapIdentType.json?paramValue='+val;
		        			$.get(url, {}, function(ret, status) {
		        				$("#identTypeBox").val(ret);
		        			});
						}
					}
				},
				validate : {
					required : true,
					messages : {
						required : "证件类型不能为空。"
					}
				}
			}, /* {
				display : "证件类型 <font color='red'>*</font>",
				name : "identTypeBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "identType",
					url : "${ctx}/ecif/customer/speciallist/getComBoBoxIdentType.json",
					ajaxType : "get",
					disabled : flag
				},
				validate : {
					required : true,
					messages : {
						required : "证件类型不能为空。"
					}
				}
			}, */ {
				display : "证件号码 <font color='red'>*</font>",
				name : "identNo",
				newline : true,
				type : "text",
				options : {
					disabled : flag
				},
				validate : {
					required : true,
					maxlength : 40,
					messages : {
						required : "证件号码不能为空。"
					}
				}
			}, {
				display : "证件名称 <font color='red'>*</font>",
				name : "identCustName",
				newline : false,
				type : "text",
				options : {
					disabled : flag
				},
				validate : {
					required : true,
					maxlength : 70,
					messages : {
						required : "证件名称不能为空。"
					}
				}
			}, /* {
				display : "状态标志 <font color='red'>*</font>",
				name : "statFlagBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'statFlag',
					url : "${ctx}/ecif/customer/speciallist/getComBoBoxValidType.json",
					ajaxType : "get",
					disabled : flag
				},
				validate : {
					required : true,
					messages : {
						required : "状态标志不能为空。"
					}
				}
			}, */ {
				display : "起始日期 <font color='red'>*</font>",
				name : 'startDate',
				newline : true,
				type : "date",
				options :{
					format : "yyyy-MM-dd",
					disabled : flag
				},
				validate : {
					required : true,
					messages : {
						required : "起始日期不能为空。"
					}
				}
			}, {
				display : "到期日期 <font color='red'>*</font>",
				name : 'endDate',
				newline : false,
				type : "date",
				options :{
					format : "yyyy-MM-dd",
					disabled : false
				},
				validate : {
					required : true,
					messages : {
						required : "到期日期不能为空。"
					},
					greaterThan : 'startDate'
				}
			}, {
				display : "列入原因",
				name : "enterReason",
				newline : true,
				type : "textarea",
				width : 475,
				validate : {
					//required : true,
				    maxlength : 200/*,
				    messages : {
						required : "列入原因不能为空。"
					} */
				}
		    }]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/customer/speciallist/${id}.json"});
			$("#mainform input[name=custNo]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			$("#mainform input[name=specialListKind]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			$("#mainform input[name=identType]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			$("#mainform input[name=identNo]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			$("#mainform input[name=identCustName]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			$("#mainform input[name=statFlag]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			$("#mainform input[name=startDate]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
			//$("#mainform input[name=endDate]").attr("disabled", "disabled").css("color", "black").removeAttr("validate");
		}else{
			
		}
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : validateData//save_test
		});
		BIONE.addFormButtons(buttons);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
	});
	function save_test() {		
		BIONE.submitForm($("#mainform"), 
			function() {
				BIONE.closeDialogAndReloadParent("testManage", "maingrid", "保存提交待审批");
			},
			function() {
				BIONE.closeDialog("testManage", "保存失败");
			});
	}
	function cancleCallBack() {
		BIONE.closeDialog("testManage");
	}
	function validateData(){
		if(flag){
			save_test();
		}else{
			var timestamp=new Date().getTime();
			$.ajax({
				url: '${ctx}/ecif/customer/speciallist/validatedata?'+timestamp,
				async : false,
				type: 'get',
				data: {
					custNo: $('#custNo').val(),
					//others: others
					identType: $('#identType').val(),
					identNo: $('#identNo').val(),
					identCustName: $('#identCustName').val(),
					specialListKind: $('#specialListKind').val()
				},
				success: function (data){
					if(data == "成功"){
						save_test();
					}else{
						alert(data);
					}
				}
			});
		}
	}
	// 关闭dialog窗口
	BIONE.closeDialog = function(dialogName, message, flag) {
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get(dialogName);
		if(dialog.beforeClose != null 
         	&& typeof dialog.beforeClose == "function"
         	&& flag!=null && flag==true){
			 dialog.beforeClose();
        }
		dialog.close();
		if (message) {
			main.BIONE.tip(message);
		}
	};
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/customer/speciallist" method="post"></form>
	</div>
</body>
</html>