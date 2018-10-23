<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	//
	var pwdComplex ;
	//
	var glabelWidth = 125;
	var gfieldWidth = 180;
	var gspaceWidth = 40;
	//
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
			/*labelWidth : 110,
			inputWidth : 140,*/
			/* space : 30,*/
			labelWidth : glabelWidth,
			fields : [ 
			{ name: "pwdSecurityId", type: "hidden" },
			{ name: "pwdComplex", type: "hidden" },
			{
				display : "是否启用安全策略<font color='red'>*</font>",
				name : "usePwdSecurityName",
				newline : true,
				type : "select",
				comboboxName : "usePwdSecurityID",
				options:{
					data:[{
		    			text:'启用',	id:'1'
		    		},{
		    			text:'停用', id:'0'
		    		}] ,
		    		valueFieldID : 'usePwdSecurity'
		    	},
		    	group : "密码安全策略",
				groupicon : groupicon,
				validate : {
					required : true
				}
			}, 
			{
				display : "是否保存历史密码<font color='red'>*</font>",
				name : "isSavePwdHisName",
				newline : false,
				type : "select",
				comboboxName : "isSavePwdHisID",
				options:{
					data:[{
		    			text:'启用',	id:"1"
		    		},{
		    			text:'停用', id:"0"
		    		}],
			    	valueFieldID : 'isSavePwdHis'
		    	},
				validate : {
					required : true
				}
			}, 
			{
				display : "密码失效时间（月）<font color='red'>*</font>",
				name : "pwdUseTimeName",
				newline : true,
				type : "select",
				comboboxName : "pwdUseTimeID",
				options:{
					data:[
						{ text:'1个月', id:"1" },
						{ text:'2个月', id:"2" },
						{ text:'3个月', id:"3" },
						{ text:'6个月', id:"6" },
						{ text:'9个月', id:"9" },
						{ text:'12个月', id:"12" }
					],
					valueFieldID : 'pwdUseTime'
		    	},
		    	group : "密码属性设置",
				groupicon : groupicon,
				validate : {
					required : true
				}
			},
			{
				display : "密码最小长度<font color='red'>*</font>",
				name : "pwdMixLength",
				newline : true,
				type : "number",
				validate : {
					required : true
				}
			},
			{
				display : "密码最大长度<font color='red'>*</font>",
				name : "pwdMaxLength",
				newline : false,
				type : "number",
				validate : {
					required : true
				}
			},
			{
				display : "允许错误次数<font color='red'>*</font>",
				name : "allowErrorTimes",
				newline : true,
				type : "number",
				validate : {
					required : true
				}
			},
			{
				display : "锁定方式<font color='red'>*</font>",
				name : "lockTypeName",
				newline : true,
				type : "select",
				comboboxName : "lockTypeID",
				options:{
					data:[
						{ text:'按小时计算', id:"hour" }
					],
					valueFieldID : 'lockType'
		    	},
				validate : {
					required : true
				}
			},
			{
				display : "锁定时间<font color='red'>*</font>",
				name : "lockTime",
				newline : false,
				type : "number"
			},
			{
				display : "备注",
				name : "remark",
				newline : true,
				type : "textarea",
				width : 533,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 250
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/bione/admin/passwd/${id}"});
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
			onclick : savePwdSecurity
		});
		BIONE.addFormButtons(buttons);
		//
		showCpxItems();
	});
	function savePwdSecurity() {
		getCpx();
		BIONE.submitForm($("#mainform"), function(data) {
			BIONE.tip("保存成功");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("pwdSecurityManage");
	}
	/* 
	 * PWD_COMPLEX
	 */
	function toObject(json) {
		if (!json) {
			json = '{"uppercase_letters":"allow","lowercase_letters":"allow","special_symbols":"allow","numeric":"allow"}';
		}
		return $.parseJSON(json);
	}
	function setCpx(json) {
		var pwdComplexObj = new Object();
		if (!json) {
			// query again
			$.ajax({
				cache : false,
				async : true,
				url : "${ctx}/bione/admin/passwd/getPwdComplex.json?d="
						+ new Date().getTime(),
				dataType : 'json',
				type : "get",
				complete : function() {
					pwdComplexObj = toObject(pwdComplex);
					setCpxValue(pwdComplexObj);
				},
				success : function(result) {
					if (!result) {
						return;
					}
					if (result.success && result.success == "true") {
						pwdComplex = result.pwdCpx;
					} else {
						BIONE.tip(result.msg);
					}
				},
				error : function(result, b) {
					BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
				}
			});
		} 
		else {
			pwdComplex = json;
		}
	}
	function setCpxValue(pwdComplexObj) {
		$.ligerui.get("useSuppercaseID").selectValue(
				pwdComplexObj.uppercase_letters);
		$.ligerui.get("useLowercaseID").selectValue(
				pwdComplexObj.lowercase_letters);
		$.ligerui.get("useSpecialsymbolsID").selectValue(
				pwdComplexObj.special_symbols);
		$.ligerui.get("useNumericID").selectValue(pwdComplexObj.numeric);
	}
	function getCpx() {
		var sbuffer = [];
		sbuffer.push('{');
		sbuffer.push('"uppercase_letters":"'
				+ $.ligerui.get("useSuppercaseID").getValue() + '",');
		sbuffer.push('"lowercase_letters":"'
				+ $.ligerui.get("useLowercaseID").getValue() + '",');
		sbuffer.push('"special_symbols":"'
				+ $.ligerui.get("useSpecialsymbolsID").getValue() + '",');
		sbuffer.push('"numeric":"' + $.ligerui.get("useNumericID").getValue()
				+ '"');
		sbuffer.push("}");
		$("#mainform [name=pwdComplex]").val(sbuffer.join(''));
	}
	//
	var cpxform;
	function showCpxItems() {
		cpxform = $("#cpxform");
		cpxform.ligerForm({
			labelWidth : glabelWidth,
			fields : [ {
				display : "密码包含大写字母<font color='red'>*</font>",
				name : "useSuppercaseName",
				newline : true,
				type : "select",
				comboboxName : "useSuppercaseID",
				options : {
					data : [ {
						text : '必须包含',
						id : 'required'
					}, {
						text : '允许包含',
						id : 'allow'
					}, {
						text : '禁止包含',
						id : 'forbid'
					} ],
					valueFieldID : 'useSuppercase'
				},
				group : "密码安全策略",
				groupicon : groupicon,
				validate : {
					required : true
				}
			}, {
				display : "密码包含小写字母<font color='red'>*</font>",
				name : "useLowercaseName",
				newline : false,
				type : "select",
				comboboxName : "useLowercaseID",
				options : {
					data : [ {
						text : '必须包含',
						id : 'required'
					}, {
						text : '允许包含',
						id : 'allow'
					}, {
						text : '禁止包含',
						id : 'forbid'
					} ],
					valueFieldID : 'useLowercase'
				},
				validate : {
					required : true
				}
			}, {
				display : "密码包含特殊符号<font color='red'>*</font>",
				name : "useSpecialsymbolsName",
				newline : true,
				type : "select",
				comboboxName : "useSpecialsymbolsID",
				options : {
					data : [ {
						text : '必须包含',
						id : 'required'
					}, {
						text : '允许包含',
						id : 'allow'
					}, {
						text : '禁止包含',
						id : 'forbid'
					} ],
					valueFieldID : 'useSpecialsymbols'
				},
				validate : {
					required : true
				}
			}, {
				display : "密码包含数字<font color='red'>*</font>",
				name : "useNumericName",
				newline : false,
				type : "select",
				comboboxName : "useNumericID",
				options : {
					data : [ {
						text : '必须包含',
						id : 'required'
					}, {
						text : '允许包含',
						id : 'allow'
					}, {
						text : '禁止包含',
						id : 'forbid'
					} ],
					valueFieldID : 'useNumeric'
				},
				validate : {
					required : true
				}
			} ]
		})
		pwdComplex = $("#mainform [name=pwdComplex]").val();
		setCpx(pwdComplex);
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/admin/passwd" method="post"></form>
		
		<form id="cpxform" class="l-form" novalidate="novalidate"></form>
		
		<!-- <form id="cpxform" class="l-form" novalidate="novalidate">
			<div class="l-group l-group-hasicon">
				<div class="l-icon-div">
					<img src="/biapp/images/classics/icons/communication.gif">
				</div>
				<div>
					<span>密码复杂度</span>
				</div>
				
			</div>
		</form> -->
	</div>
</body>
</html>