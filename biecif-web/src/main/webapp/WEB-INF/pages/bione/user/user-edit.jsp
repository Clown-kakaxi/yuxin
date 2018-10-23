<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var orgNo, deptNo;
	//扩展字段map
	var extFields;
	//非扩展字段map
	var unextFields;
	//创建表单结构 
	var mainform;
	var userId = "${id}";
	$(function() {
		initFormField();
	});
	
	function initFormField(){
		$.ajax({
			cache : false,
			async : true,
			type : 'post',
			url : "${ctx}/bione/admin/user/generateUserForm.json",
			dataType : 'json',
			data:{
				"userId":userId
			},
			success : function(result) {
				if(result == null || !result.fields
						|| !result.extFields
						|| !result.unextFields){
					BIONE.tip('表单初始化异常');
					return ;
				}
				extFields = result.extFields;
				unextFields = result.unextFields;
				//初始化表单
				mainform = $("#mainform").ligerForm({
					fields:result.fields
				}); 
				//初始化表单中特殊组件动作
				jQuery.metadata.setType("attr", "validate");
				BIONE.validate("#mainform");
				//当是修改操作时，初始化数据
				if(userId){
					setWhenUpdate();
				}else{
					/* @Revision 2013-6-5  liucheng2@yuchengtech.com 
					 * 新建用户时不用手工填写用户密码，保存时使用系统默认密码。 */
					$("ul > li input#userPwd").parent().parent().parent("ul").hide().find("input").attr("disabled", "disabled");
					/* @Revision 2013-6-5 END */
					initForm();
				}
				//初始化按钮
				var buttons = [];
				buttons.push({
					text : '取消',
					onclick : cancleCallBack
				});
				buttons.push({
					text : '保存',
					onclick : save_user
				});
				BIONE.addFormButtons(buttons);
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	function initForm() {
		//没办法，里面很多东西都只能写死来进行初始化 - , -
		//-- 初始化机构部门联动
		if($.ligerui.get("orgNo_select") && $.ligerui.get("deptNo_select")){
			if(userId){
				$.ligerui.get("orgNo_select").openSelect({
					url : "${ctx}/bione/admin/orgtree",
					dialogname : 'orgComBoBox',
					title : '选择机构',
					comboxName : 'OrgBoxNo',
					height : '410',
					width : '450'
				});
				var deptcombox = $.ligerui.get("deptNo_select");
				deptcombox.openSelect({
					url : "${ctx}/bione/admin/depttree?id=" + orgNo,
					dialogname : 'deptComBoBox',
					title : '选择部门',
					comboxName : 'deptNo_select',
					height : '450',
					width : '450'
				});
			}else{				
				$.ligerui.get("orgNo_select").openSelect({
					url : "${ctx}/bione/admin/orgtree",
					dialogname : 'orgComBoBox',
					title : '选择机构',
					comboxName : 'OrgBoxNo',
					height : '410',
					width : '450'
				});
				$.ligerui.get("deptNo_select").bind('beforeOpen', function() {
					firstopen();
					return false;
				});
			}
		}
		//-- 初始化各种校验
		//-- -- 用户帐号重复性验证
		$("#userNo").rules("add",{remote:{
			url:"${ctx}/bione/admin/user/userNoValid",
			type:"post",
			async:false
		},messages:{remote:"用户标识重复"}});
		//-- -- 确认密码一致性验证
		$("#userPwdTo").rules("add",{equalTo:"#userPwd"});
		//-- -- 日期验证
		$("#birthday").rules("add",{date:true});
		//-- -- 邮箱验证
		$("#email").rules("add",{email:true});
		//-- -- 手机号码验证
		$("#mobile").rules("add",{mobile:true});
		//-- -- 电话号码验证
		$("#tel").rules("add",{phone:true});
		//-- -- 邮编验证
		$("#postcode").rules("add",{number:true});
		
		function firstopen(){
			BIONE.tip('请先选择机构');
		}
	}
	
	// 修改动作初始化数据
	function setWhenUpdate(){
		if(userId){
			$.ajax({
				cache : false,
				async : true,
				type : 'post',
				url : "${ctx}/bione/admin/user/setWhenUpdate.json",
				dataType : 'json',
				data:{
					"userId":userId
				},
				success : function(result) {
					if(result){
						var userInfo = result.userInfo;
						var deptName;
						var orgName;
						if(userInfo){
							//初始化基本信息
							f_loadformOwn(userInfo);
							orgNo = userInfo.orgNo;
							deptNo = userInfo.deptNo;
							if(result.orgName){
								orgName = result.orgName;
							}
							if(result.deptName){
								deptName = result.deptName;
							}
						}
						var attrVals = result.attrVals;
						if(attrVals && attrVals.length){
							var objTmp = {};
							for(var i = 0 ; i < attrVals.length ; i++){
								var valTmp = attrVals[i];
								objTmp[valTmp.fieldName] = valTmp.attrValue;
							}
							//初始化扩展属性
							f_loadformOwn(objTmp);
						}
						$("#userNo").attr("disabled", "disabled");
						$("ul > li input#userPwd").parent().parent().parent("ul").hide().find("input").attr("disabled", "disabled");
						if(orgName){
							$("[name='orgNo_select']").val(orgName);
						}
						if(deptName){
							$("[name='deptNo_select']").val(deptName);
						}
					}
					initForm();
				},
				error : function(result, b) {
					BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
				}
			});
		}
	}
	
	//
	function f_loadformOwn(data) {
		// 根据返回的属性名，找到相应ID的表单元素，并赋值
		for ( var p in data) {
			var ele = $("[name=" + p + "]");
			// 针对复选框和单选框 处理
			if (ele.is(":checkbox,:radio")) {
				ele[0].checked = data[p] ? true : false;
			} else if (ele.is(":text") && ele.attr("ltype") == "date") {
				if (data[p]) {
					var date=null;
					if(data[p].time){
						date = new Date(data[p].time);
					}else{
						//edit by caiqy
						var tdate;
						if(typeof data[p] == "string" 
								&&data[p].indexOf('-') != -1 && data[p].length >= 10){
				    		// if format as 'yyyy-MM-dd' or 'yyyy-mm-dd hh:mm:ss'
				    		tdate = new Date(new Number(data[p].substr(0,4)),new Number(data[p].substr(5,2))-1,new Number(data[p].substr(8,2)));
				    	}else{
			   		 		tdate = new Date(data[p]);
				    	}
						date = new Date(tdate);
					}
					var yy = date.getFullYear();
					var Mm = ((date.getMonth() + 1) < 10) ? ('0' + (date
							.getMonth() + 1)) : (date.getMonth() + 1);
					var dd = (date.getDate() < 10) ? ('0' + date.getDate())
							: date.getDate();
					ele.val(yy + '-' + Mm + '-' + dd);
				}
			} else {
				ele.val(data[p]);
			}
		}
		// 下面是更新表单的样式
		var managers = $.ligerui.find($.ligerui.controls.Input);
		for ( var i = 0, l = managers.length; i < l; i++) {
			// 改变了表单的值，需要调用这个方法来更新ligerui样式
			var o = managers[i];
			o.updateStyle();
			if (managers[i] instanceof $.ligerui.controls.TextBox)
				o.checkValue();
		}
	}
	
	// 保存提交方法
	function save_user() {
		if(!$("#mainform").valid()){
			BIONE.showInvalid(BIONE.validator);
			return ;
		}
		var formArray = $("#mainform").formToArray();
		if(formArray != null && extFields && unextFields){
			var submitObj = {
				extArray:[],
				unextObj:{}
			};
			var extArrayTmp = [];
			var unextObjTmp = {};
			f1:for(var i = 0 ,l = formArray.length ; i < l ; i++){
				var fieldTmp = formArray[i];
				f2:for(var j = 0 , l2 = extFields.length ; j < l2 ; j++){
					if(extFields[j].fieldName == fieldTmp.name){
						//若该属性是扩展属性，进行扩展属性封装
						var extObjTmp = {
							userId:"",
							attrId:extFields[j].attrId,
							attrValue:fieldTmp.value
						};
						extArrayTmp.push(extObjTmp);
						continue f1;
					}
				}
				f3:for(var k = 0 , l3 = unextFields.length ; k < l3 ; k++){
					if(unextFields[k].fieldName == fieldTmp.name){
						//若该属性是非扩展属性，进行非扩展属性封装
						unextObjTmp[fieldTmp.name] = fieldTmp.value;
						continue f1;
					}
				}
			}
			submitObj.extArray = extArrayTmp;
			submitObj.unextObj = unextObjTmp;
			$.ajax({
				cache : false,
				async : true,
				url : "${ctx}/bione/admin/user",
				dataType : 'json',
				type : "post",
				data : {
					"userId":userId?userId:"",
					"submitObj" : JSON2.stringify(submitObj)
				},
				success : function(){
					BIONE.closeDialogAndReloadParent("userManage", "maingrid", "保存成功");
				},
				error : function(){
					BIONE.closeDialog("userManage", "保存失败");
				}
			});
		}
	}
	function cancleCallBack() {
		BIONE.closeDialog("userManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/admin/user" method="post"></form>
	</div>
</body>
</html>