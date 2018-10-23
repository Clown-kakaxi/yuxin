<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	jQuery.extend(jQuery.validator.messages, {
		greaterThan : "结束时间应当大于开始日期"
	});
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var unit = "second";
	$(function() {
		$("#mainform").ligerForm({
			fields : [ {
				display : '触发器名称',
				name : 'triggerName',
				newline : true,
				type : 'text',
				group : "触发器信息",
				validate : {
					required : true,
					maxlength : 32
				},
				groupicon : groupicon
			}, {
				display : '定义类型',
				name : 'defTypeValue',
				newline : false,
				type : 'select',
				validate : {
					required : true
				},
				options : {
					valueFieldID : 'defType',
					id : 'defTypeId',
					initValue : '01',
					data : [ {
						text : '配置',
						id : "01"
					}, {
						text : '自定义',
						id : '02'
					} ],
					onSelected : function(val) {
						setCss();
					}
				}
			}, {
				display : '执行频率',
				name : 'execFreqValue',
				newline : true,
				type : 'select',
				options : {
					initValue : '11',
					valueFieldID : 'execFreq',
					data : [ {
						text : '每天',
						id : '11'
					}, {
						text : '旬初',
						id : '31'
					}, {
						text : '月初',
						id : '41'
					}, {
						text : '月末',
						id : '42'
					}, {
						text : '季初',
						id : '51'
					}, {
						text : '季末',
						id : '52'
					}, {
						text : '年初',
						id : '61'
					}, {
						text : '半年末',
						id : '62'
					}, {
						text : '年末',
						id : '63'
					} ],
					onSelected : serFreqValue
				},
				validate : {
					required : true
				}
			}, {
				display : '执行方式',
				name : "everydayFreque",
				newline : false,
				type : "select",
				validate : {
					required : true
				},
				options : {
					initValue : 'execTime',
					valueFieldID : 'execType',
					data : [ {
						text : '执行时间',
						id : 'execTime'
					}, {
						text : '间隔时间',
						id : 'spaceTime'
					} ],
					onSelected : function(val) {
						setCss();
						setRemarkValue();
					}
				}

			}, {
				display : '执行时间',
				name : 'execTime',
				id : 'execTime',
				newline : true,
				type : "text",
				validate : {
					time : true
				}
			}, {
				display : '间隔时间',
				name : 'spaceTime',
				id : 'spaceTime',
				newline : true,
				type : "number",
				validate : {
					range : [ 0, 60 ]
				}
			}, {
				display : '单位',
				name : 'unitValue',
				newline : false,
				type : 'select',
				options : {
					initValue : 'second',
					valueFieldID : 'spaceUnit',
					data : [ {
						text : '秒',
						id : 'second'
					}, {
						text : '分',
						id : 'min'
					}, {
						text : '时',
						id : 'hour'
					} ],
					onSelected : function(val) {
						unit = val;
						setSpaceValue();
						setRemarkValue();
					}
				}
			}, {
				display : '开始时间',
				name : 'startTime',
				newline : true,
				id : 'startTime',
				validate : {
					required : true
				},
				type : 'date'
			}, {
				display : '结束时间',
				name : 'endTime',
				id : 'endTime',
				newline : false,
				type : 'date',
				validate : {
					greaterThan : "startTime"
				}
			}, {
				display : '克隆表达式',
				name : 'cron',
				width : '500',
				newline : true,
				type : 'text',
				validate : {
					cron : true
				}
			}, {
				display : '描述',
				name : 'remark',
				width : '503',
				newline : true,
				type : 'textarea',
				validate : {
					maxlength:500
				},
				attr : {
					style : 'resize: none;'
				}
			}, {
				name : 'freqValue',
				type : 'hidden'
			}, {
				name : 'everydayFreqV',
				type : 'hidden'
			} ]
		});

		$("#mainform [name='freqValue']").val("* * ? *");
		
		var date = new Date();
		var yy = date.getFullYear();
		var Mm = ((date.getMonth() + 1) < 10) ? ('0' + (date.getMonth() + 1))
				: (date.getMonth() + 1);
		var dd = (date.getDate() < 10) ? ('0' + date.getDate()) : date
				.getDate();
		$("#mainform input[name=startTime]").val(yy + '-' + Mm + '-' + dd);

		$("#mainform input[name=spaceTime]").parent().parent().parent("ul")
				.hide().find("input").attr("disabled", "disabled");
		$("#mainform input[name='cron']").attr("readOnly", "readOnly");

		$("#execTime").blur(
				function() {
					var value = $("#mainform [name='execTime']").val();
					var v = "";
					var dt = value.split(":");
					for ( var i = 0; i < dt.length; i++) {
						v += dt[dt.length - i - 1] + " ";
					}
					setRemarkValue();
					$("#mainform [name='everydayFreqV']").val(v);
					$("#mainform [name='cron']").val(
							$("#mainform [name='everydayFreqV']").val()
									+ $("#mainform [name='freqValue']").val());
				});

		$("#spaceTime").blur(setSpaceValue);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : function() {
				BIONE.closeDialog("triggerAddWin");
			}
		});
		buttons.push({
			text : '保存',
			onclick : f_save
		});

		BIONE.addFormButtons(buttons);
	});

	function setSpaceValue() {
		var value = $("#mainform [name='spaceTime']").val();
		var v = "";
		if (unit == "second") {
			v = "0/" + value + " * * ";
		} else if (unit == "min") {
			v = "0 0/" + value + " * ";
		} else {
			v = "0 0 0/" + value + " ";
		}
		setRemarkValue();
		$("#mainform [name='everydayFreqV']").val(v);
		$("#mainform [name='cron']").val(
				$("#mainform [name='everydayFreqV']").val()
						+ $("#mainform [name='freqValue']").val());
	}

	function f_save() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("triggerAddWin", "maingrid",
					"添加成功");
		}, function() {
			BIONE.closeDialog("triggerAddWin", "添加失败");
		});
	}

	function serFreqValue(value) {
		if (value == '11') {
			//每日
			$("#mainform [name='freqValue']").val("* * ? *");
		} else if (value == '31') {
			//旬初
			$("#mainform [name='freqValue']").val("1,11,21 * ? *");
		} else if (value == '41') {
			//月初
			$("#mainform [name='freqValue']").val("1 * ? *");
		} else if (value == '42') {
			//月末
			$("#mainform [name='freqValue']").val("L * ? *");
		} else if (value == '51') {
			//季初
			$("#mainform [name='freqValue']").val("1 1,4,7,10 ? *");
		} else if (value == '52') {
			//季末
			$("#mainform [name='freqValue']").val("L 1,4,7,10 ? *");
		} else if (value == '61') {
			//年初
			$("#mainform [name='freqValue']").val("1 1 ? *");
		} else if (value == '62') {
			//半年末
			$("#mainform [name='freqValue']").val("L 6 ? *");
		} else if (value == '63') {
			//年末
			$("#mainform [name='freqValue']").val("L 12 ? *");
		}
		setRemarkValue();
		$("#mainform [name='cron']").val(
				$("#mainform [name='everydayFreqV']").val()
						+ $("#mainform [name='freqValue']").val());
	}

	function setCss() {
		var val = $("#mainform input[name=defType]").val();
		if (val == "01") {
			$.ligerui.get("execFreqValue").setEnabled();
			$("#mainform input[name=execFreqValue]").parent().parent().parent()
					.parent("ul").show().find("input").removeAttr("disabled")
					.css("color", "black");
			var val_2 = $("#mainform input[name=execType]").val();
			if (val_2 == "execTime") {
				$("#mainform input[name=spaceTime]").parent().parent().parent(
						"ul").hide().find("input").attr("disabled", "disabled");
				$("#mainform input[name=execTime]").parent().parent().parent(
						"ul").show().find("input").removeAttr("disabled").css(
						"color", "black");
			} else {
				$("#mainform input[name=spaceTime]").parent().parent().parent(
						"ul").show().find("input").removeAttr("disabled").css(
						"color", "black");
				$("#mainform input[name=execTime]").parent().parent().parent(
						"ul").hide().find("input").attr("disabled", "disabled");
			}
			$("#mainform input[name=cron]").attr("readOnly", "readOnly");
		} else {
			$.ligerui.get("execFreqValue").setDisabled();
			$("#mainform input[name=execFreqValue]").parent().parent().parent()
					.parent("ul").hide().find("input").attr("disabled",
							"disabled");
			$("#mainform input[name=spaceTime]").parent().parent().parent("ul")
					.hide().find("input").attr("disabled", "disabled");
			$("#mainform input[name=execTime]").parent().parent().parent("ul")
					.hide().find("input").attr("disabled", "disabled");
			$("#mainform input[name=cron]").removeAttr("readOnly");
		}
		$("#mainform input[name=cron]").css("color", "black");
		//$("#mainform").valid();
	}

	function setRemarkValue() {
		var freq = $("#mainform [name='execFreqValue']").val();
		var last = "";
		var ifEveryDay = $("#mainform [name='everydayFreque']").val();
		if (ifEveryDay == '执行时间') {
			last = $("#mainform [name='execTime']").val();
		} else if (ifEveryDay == '间隔时间') {
			last = "0点开始每隔" + $("#mainform [name='spaceTime']").val()
					+ $("#mainform [name='unitValue']").val();
		}
		$("#mainform [name='remark']").val(freq + last + "执行");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/admin/trigger"></form>
	</div>
</body>
</html>