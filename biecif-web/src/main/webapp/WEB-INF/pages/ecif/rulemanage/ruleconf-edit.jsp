<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
 jQuery.validator.addMethod("dateVID",
		function(value, element) {
			var start = $("#effectiveTime").val(), end =  $("#expiredTime").val();
			return !start|| !end
					|| ((+(end.split("-").join(""))) >= (+(start
							.split("-").join(""))));
		}, "生效日期应大于失效日期"); 
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
			labelWidth : 140,
			fields : [{
				name:'ruleGroupId',
				type:'hidden'
			},
	            {	name : 'ruleId',
					type : 'hidden'
			    },
			    {
					display : "规则编号<font color='red'>*</font>",
					name : "ruleNo",
					newline : true,
					group : "规则大类",
					groupicon : groupicon,
					type : "text",
					validate : {
						required : true,
						maxlength : 32,
						remote: {
							url:"${ctx}/ecif/rulemanage/txbizruleconf/ruleConfValid?d="+new Date()
						},
						messages:{
							remote : "规则编号重复"
						}
					}
				}, {
				display : "规则版本<font color='red'>*</font>",
				name : "ruleVer",
				newline : false,
				groupicon : groupicon,
				type : "text",
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "规则名称<font color='red'>*</font>",
				name : "ruleName",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 255,
					remote: {
						url:"${ctx}/ecif/rulemanage/txbizruleconf/ruleConfValid?ruleId="+"${id}"+"&d="+new Date()
					},
					messages:{
						remote : "规则名称重复"
					}
				}
			} , 
				{
				display : "父规则名称",
				name : "parentRuleName",
				groupicon : groupicon,
				newline : false,
				type : "select",
				options : {
					valueFieldID :'parentRuleId',
					onBeforeOpen : function() {
						BIONE.commonOpenDialog(
										"请选择父规则(双击规则选定)",
										"RuleInfoBox",
										"800", "350",
										"${ctx}/ecif/rulemanage/txbizruleconf/ruleConfInfo");
						return false;
					}
				}
				
				
			} ,{
				display : "规则定义分类",
				name : "ruleDefTypeBox",
				newline : true,
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID :'ruleDefType',
					data : [ {
						text : '校验',
						id : 'C'
					}, {
						text : '转换',
						id : 'T'
					} ]
				}
			}, {
				display : "规则业务分类",
				name : "ruleBizTypeBox",
				newline : false,
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID :'ruleBizType',
					data : [ {
						text : '个人客户',
						id : 'P'
					}, {
						text : '对公客户',
						id : 'B'
					} ]
				}
			},  {
				display : "规则状态<font color='red'>*</font>",
				name : "ruleStatBox",
				newline : true,
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID :'ruleStat',
					data : [ {
						text : '有效',
						id : '1'
					}, {
						text : '无效',
						id : '0'
					} ]
				},
				validate : {
					required : true
				}
			},  {
				display : "规则描述",
				name : "ruleDesc",
				newline : true,
				groupicon : groupicon,
				type : "textarea",
				width : 545,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 255
				}
			} , {
				display : "生效时间",
				name : "effectiveTime",
				newline : true,
				groupicon : groupicon,
				type : "date",
				validate:{
					dateVID : true
				} 
			}, {
				display : "失效时间",
				name : "expiredTime",
				newline : false,
				groupicon : groupicon,
				type : "date",
				validate:{
					dateVID : true
				} 
			} , {
				display : "规则处理方法分类",
				name : "ruleDealTypeBox",
				newline : true,
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID :'ruleDealType',
					data : [ {
						text : '正则表达式',
						id : '1'
					}, {
						text : '自定义',
						id : '9'
					} ]
				}
			}, {
				display : "规则处理接口类型",
				name : "ruleIntfType",
				newline : false,
				groupicon : groupicon,
				type : "text"
			}, {
				display : "规则处理包路径",
				name : "rulePkgPath",
				newline : true,
				groupicon : groupicon,
				type : "text",
				validate : {
					maxlength : 255
				}
			}, {
				display : "规则处理类",
				name : "ruleDealClass",
				newline : false,
				groupicon : groupicon,
				type : "text",
				validate : {
					maxlength : 128
				}
			}, {
				display : "规则处理表达式",
				name : "ruleExpr",
				newline : true,
				groupicon : groupicon,
				type : "textarea",
				width : 545,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 512
				}
			}, {
				display : "规则处理表达式描述",
				name : "ruleExprDesc",
				newline : true,
				groupicon : groupicon,
				type : "textarea",
				width : 545,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 255
				}
			}]
		});
		if("${ruleGroupId}") {
		$("#mainform input[name=ruleGroupId]").val("${ruleGroupId}");
		}  
		//修改判定
	 	 if("${id}") {
 			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/rulemanage/txbizruleconf/${id}"}); 
			$.ajax({
				type : "GET",
				url : "${ctx}/ecif/rulemanage/txbizruleconf/${id}",
				async : false,
				success : function(vo) {
				    var  ruleName=vo.parentRuleName;
					 $("#parentRuleName").val(ruleName);
					 
				}
			});

			$("#mainform input[name=ruleNo]").attr("readonly", "readonly").removeAttr("validate");
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
			onclick : save
		});
		BIONE.addFormButtons(buttons);

	});
	function save() {
		BIONE.submitForm($("#mainform"), function() {
			window.parent.grid.loadData();
			BIONE.closeDialog("RuleConfAdd","保存成功");
		}, function() {
			BIONE.closeDialog("RuleConfAdd", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("RuleConfAdd");					
		}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/rulemanage/txbizruleconf" method="post"></form>
	</div>
</body>
</html>