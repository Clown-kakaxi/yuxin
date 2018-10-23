<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var buttons = [];
	var basic, styles, verify;
	var attrId = "${id}";
	//扩展下拉框数据源验证
	jQuery.validator.addMethod("combDsValidate", function(value, element){
		if(value == null || value == ""){
			return true;
		}else if($.isArray(eval(value))){
			return true;
		}
		return false;
	}, "请输入正确的数据源数组");
	$(function(){
		initTab();
		initForms();
		initBtns();
	});
	function initBtns(){
		buttons.push({
			text: '取消',
			onclick: function(){
				BIONE.closeDialog("userAttrWin");
			}
		});
		buttons.push({
			text: '保存',
			onclick: f_save
		});
		BIONE.addFormButtons(buttons);
	}
	function initTab(){
		$('#tab').ligerTab({
			onBeforeSelectTabItem : f_selectTab
		});
			
	}
	function initForms(){
		var initData = [ {
			field : 'attrSts',
			value : '1'
		}, {
			field : 'elementType',
			value : '01'
		}, {
			field : 'labelAlign',
			value : 'left'
		}, {
			field : 'elementAlign',
			value : 'left'
		}, {
			field : 'isNewline',
			value : '0'
		}, {
			field : 'isAllowNull',
			value : '1'
		}, {
			field : 'isReadonly',
			value : '0'
		} ];
		basic = $('#form_basic').ligerForm({
			fields: [{
				name:'attrId',
				type:'hidden'
			},{
				name:'grpId',
				type:'hidden'
			},{
				name : 'isAllowUpdate',
				type:'hidden'
			}, {
				display:'字段名称',
				name: 'fieldName',
				newline:true, 
				group : "基本属性",
				groupicon : groupicon,
				type: 'text',
				validate:{
					required:true,
					maxlength:100,
					remote : {
						url : "${ctx}/bione/admin/userattrgrp/checkAttrName?d="
								+ new Date(),
						type : 'post',
						async : false,
						data : {
							attrId : attrId
						}
					},
					messages : {
						remote : "字段名称已存在"
					}
				}
			}, {
				display: '属性状态',
				name: 'attr',
				newline: false,
				type: 'select',
				options:{
					valueFieldID:'attrSts',
					data:[{
						text:'启用',
						id:'1'
					},{
						text:'停用',
						id:'0'
					}]
				}
			},{
				display:'标签名称',
				name:'labelName',
				newline:true, 
				type:'text',
				validate:{
					required:false,
					maxlength:100
				}
			},{
				display:'组件类型',
				name:'element',
				newline:false,
				type:'select',
				options:{
					valueFieldID:'elementType',
					data:[{
						text:'文本框',
						id:'01'
					},{
						text:'下拉框',
						id:'02'
					},{
						text:'日期选择框',
						id:'03'
					},{
						text:'隐藏域',
						id:'04'
					},{
						text:"密码域",
						id:'05'
					},{
						text:"多行文本输入域",
						id:'06'
					}],
					onSelected:function(value,text){
						if("02" == value) {
							ul.css('display','block');
							ul2.css('display','block');
						} else {
							ul.css('display','none');
							ul2.css('display','none');
						}
					}
				},
				validate:{
					required:true,
					maxlength:10
				}
			},{
				display:'初始化值',
				name:'initValue',
				newline:true, 
				type:'text',
				validate:{
					required:false,
					maxlength:500
				}
			},{
				display:'下拉框数据源',
				name:'combDs',
				newline:true,
				width:'503',
				validate:{
					combDsValidate:true,
					maxlength:500
				},
				type:'textarea',
				attr : {
					style : "resize: none;"
				}
			},{
				display:'备注',
				name:'remark',
				newline:true,
				width:'503',
				validate:{
					maxlength:1000
				},
				type:'textarea',
				attr : {
					style : "resize: none;"
				}
			}]
		});
		styles = $('#form_styles').ligerForm({
			fields: [{
				display: '标签宽度',
				name: 'labelWidth',
				newLine: true,
				group : "外观样式",
				groupicon : groupicon,
				type: 'number',
				validate : {
					digits : true,
					maxlength : 12
				}
			}, {
				display:'标签对齐',
				name:'labelAlignValue',
				newline:false,
				type:'select',
				options:{
					valueFieldID:'labelAlign',
					data:[{
					text:'左对齐',
					id:'left'
					},{
					text:'右对齐',
					id:'right'
					},{
					text:'居中对齐',
					id:'center'
					}]
				},
				validate:{
					required:true
				}
			},{
				display: '组件宽度',
				name: 'elementWidth',
				newLine: true,
				type: 'number',
				validate : {
					digits : true,
					maxlength : 12
				}
			}, {
				display:'组件对齐',
				name:'elementAlignValue',
				newline:false,
				type:'select',
				options:{
					valueFieldID:'elementAlign',
					data:[{
					text:'左对齐',
					id:'left'
					},{
					text:'右对齐',
					id:'right'
					},{
					text:'居中对齐',
					id:'center'
					}]
				},
				validate:{
					required:true
				}
			},{
				display:'是否只读',
				name:'readonly',
				newline: true,
				type:'select',
				options:{
					valueFieldID:'isReadonly',
					data:[{
					text:'是',
					id:'1'
					},{
					text:'否',
					id:'0'
					}]
				},
				validate:{
					required:true
				}
			}, {
				display:'是否换行',
				name:'ifNewlineValue',
				newline: false,
				type:'select',
	        	options:{
	        		valueFieldID:'isNewline',
	        		data:[{
	        			text:'是',
	        			id:'1'
	        		},{
	        			text:'否',
	        			id:'0'
	        		}]
	        	},
				validate:{
					required:true
				}
			}, {
				display : '顺序编号',
				name : 'orderNo',
				type : 'number',
				newline: true,
				validate : {
					digits : true,
					maxlength : 5
				}
			}]
		});
		verify = $('#form_verify').ligerForm({
			fields: [{
				display: '字段长度',
				name: 'fieldLength',
				newLine: true,
				group : "校验信息",
				groupicon : groupicon,
				type: 'number',
				validate : {
					digits : true,
					maxlength : 10
				}
			},{
				display:'是否允许为空',
				name:'isAllowNullValue',
				newline:true,
				type:'select',
	        	options:{
	        		valueFieldID:'isAllowNull',
	        		data:[{
	        			text:'是',
	        			id:'1'
	        		},{
	        			text:'否',
	        			id:'0'
	        		}]
	        	},
				validate:{
					required:true
				}
			}]
		});
		$("#grpId").val("${grpId}");
		// 组件类型控制
		var ul = $('#combDs').parents('ul:first');
		ul.css('display','none');
		var ul2 = $('#initValue').parents('ul:first');
		ul2.css('display','none');
		//$('#element').change(function(){
		//	var value = this.value;
		//	if("下拉框" == value) {
		//		ul.css('display','block');
		//	} else {
		//		ul.css('display','none');
		//	}
		//});
		
		// update
		if("not" == "${update}") {
			doNotUpdate();
		}
		// init
		var l = initData.length;
		for(var i = 0; i < l; i++){
			var data = initData[i];
			var ele = $("[name=" + data.field + "]");
			ele.val(data.value);
		}
		var managers = $.ligerui.find($.ligerui.controls.Input);
		for ( var i = 0, l = managers.length; i < l; i++) {
			// 改变了表单的值，需要调用这个方法来更新ligerui样式
			var o = managers[i];
			o.updateStyle();
			if (managers[i] instanceof $.ligerui.controls.TextBox)
				o.checkValue();
		}
		
		// 表单验证
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#form_basic");
		BIONE.validate("#form_styles");
		BIONE.validate("#form_verify");
		BIONE.loadForm(null, {
			url : "${ctx}/bione/admin/userattr/info.json",
			data : {
				id : "${id}"
			}
		});
	}

	function f_save() {
		var strBasic = $('#form_basic').serialize();
		var strStyles = $('#form_styles').serialize();
		var strVerify = $('#form_verify').serialize();
		var str = strBasic + "&" + strStyles + "&" + strVerify;
		if ($('#form_basic').valid() && $('#form_styles').valid() && $('#form_verify').valid()) {
			$.ajax({
				cache : false,
				url: '${ctx}/bione/admin/userattr',
				type: 'post',
				data: str,
				dataType : 'json',
				success: function(){
					BIONE.tip('保存成功');
					var userAttr = window.parent.userAttr;
					BIONE.closeDialog("userAttrWin");
					if(userAttr){
						userAttr.changeGrp($('#grpId').val());
						userAttr = null;
					}
				},
				error : function(){
					BIONE.tip('保存失败,请联系管理员');
				}
			});
		}
	}
	function doNotUpdate() {
		var t = ["fieldName", "element"];
		var l = t.length;
		for(var i = 0; i< l; i++) {
			var fe = $.ligerui.get(t[i]);
			fe.setDisabled();
		}
	}
	function f_selectTab(TabId){
		var st = $.ligerui.find($.ligerui.controls.ComboBox);
		$.each(st, function(i, n) {
			if (n.selectBox.is(":visible")) {
				n._toggleSelectBox(true);
			}
		});
		if($('#form_basic').valid() && $('#form_styles').valid() && $('#form_verify').valid()) {
			return true;
		} else {
			return false;
		}
		
	}
</script>
</head>
<body>
  <div id="template.center">
  	<div id="tab" style="overflow:hidden;">
  	  <div tabid="basic" title="基本属性" lselected="true" style="overflow:hidden;">
  	  	<form id="form_basic" method="post" action=""></form>
  	  </div>
  	  <div tabid="styles" title="外观样式" style="overflow:hidden;">
  	  	<form id="form_styles" method="post" action=""></form>
  	  </div>
  	  <div tabid="verify" title="校验信息" style="overflow:hidden;">
  	  	<form id="form_verify" method="post" action=""></form>
  	  </div>
  	</div>
  </div>
</body>
</html>