<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<link href="${ctx}/css/classics/passwd/css/passwd.strength.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/passwd/jquery.passwordStrength.js" type="text/javascript"></script>
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    //创建表单结构 
    var mainform;
    //
    var userPwdSecurity, pwdCpx, maxLength, mixLength, pwdCpxObj ;
    //
    $(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		    fields : [ 
		    {
				display : "请输入旧密码<font color='red'>*</font>",
				name : "userPwd_old",
				newline : true,
				type : "password",
				group : "修改密码",
				groupicon : groupicon,
				validate : {
				    required : true,
				    maxlength : 100,
				    remote : {
					url : "${ctx}/bione/admin/user/userPwdValid?d=" + new Date().getTime()
				    },
				    messages : {
					remote : "原始密码错误"
				    }
				}
			}, {
				display : "请输入新密码<font color='red'>*</font>",
				name : "userPwd_1",
				newline : true,
				type : "password",
				validate : {
				    required : true,
				    maxlength : 100,
				    remote : {
						url : "${ctx}/bione/admin/user/userPwdHisValid?d=" + new Date().getTime()
				    },
				    messages : {
						remote : "密码不允许与历史密码重复"
				    }
				}
			}, {
				display : "确认密码<font color='red'>*</font>",
				name : "userPwd_2",
				newline : true,
				type : "password",
				validate : {
				    required : true,
				    maxlength : 100,
				    equalTo : "#userPwd_1"
				}
		    } ]
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
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
	});
	// 保存提交方法
    function save_user() {
    	//alert(alertWarn(pwdCpxObj, maxLength, mixLength));
		/* if(!pwdCpxObj && maxLength && mixLength && 
			alertWarn(pwdCpxObj, maxLength, mixLength)) {
		} */
		if(userPwdSecurity=="1") {
			if((!pwdCpxObj || !maxLength || !mixLength || 
					!alertWarn(pwdCpxObj, maxLength, mixLength))) {
				return false;	
			}
		}
		BIONE.submitForm($("#mainform"), function() {
		    var dialog = parent.$.ligerui.get("userManage");
		    parent.BIONE.tip("密码修改成功");
		    dialog.close();
		}, function() {
		    parent.BIONE.tip("密码修改失败");
		});
    }
    function cancleCallBack() {
		BIONE.closeDialog("userManage");
    }
    /*
     * 显示密码强度的图片
     */
	$(function() {
    	validPwdStrength(); // 验证密码强度
    	loadPwdCpx(); // load-pwd-cpx
	});
    function validPwdStrength() {
		function showImg() {
	    	var sbuffer = [];
	    	sbuffer.push("<ul>");
	    	sbuffer.push("	<li style='width:100px;text-align:left;'>&nbsp;</li>");
	    	sbuffer.push("	<li style='width:180px;text-align:left;'>");
	    	sbuffer.push("		<div id='passwordStrengthDiv' class='is0'></div>");
	    	sbuffer.push("	</li>");
	    	sbuffer.push("</ul>");
	    	mainform.append(sbuffer.join(''));
	    }
		function bindChange() {
	    	$("input[name=userPwd_1]").passwordStrength();
	    }
		showImg();
	    bindChange();
    }
    function loadPwdCpx() {
    	$.ajax({
       		cache : false,
       		async : true,
       		url : '${ctx}/bione/admin/passwd/getPwdComplex.json',
       		dataType : 'json',
       		type : 'get',
       		success : function(result, textStatus, jqXHR) {
       		    if (!result){ 
       		    	return; 
       		    }
       		    if (result.success && result.success=="true") {
       		    	userPwdSecurity = result.userPwdSecurity;
       		    	pwdCpx = result.pwdCpx;
       		    	maxLength = result.maxLength;
       		    	mixLength = result.mixLength;
       		    	pwdCpxObj = $.parseJSON(pwdCpx);
       		    } else {
       		    	BIONE.tip(result.msg);
       		    }
       		},
       		error : function(result, textStatus, errorThrown) {
       			BIONE.tip("获取用户密码已使用过的时间发生异常！")
       		}
   		});
    }
    function alertWarn(pwdCpxObj, maxLength, mixLength) {	
    	var pwdvalue = $("input[name=userPwd_1]").val();
    	if(pwdvalue.length < mixLength) {
    		alert("密码长度不能小于"+mixLength+"字符");
    		return false;
    	}
    	else if(pwdvalue.length > maxLength) {
    		alert("密码长度不能大于"+maxLength+"字符");
    		return false;
    	}
    	else if(pwdCpxObj.uppercase_letters == "forbid" && 
    			(pwdvalue.length-pwdvalue.replace(/[A-Z]/g, "").length)>0 ) {
    		alert("密码中不允许包含大写字符！");
    		return false;
    	}
    	else if(pwdCpxObj.uppercase_letters == "required" && 
    			(pwdvalue.length-pwdvalue.replace(/[A-Z]/g, "").length)<=0 ) {
    		alert("密码中必须包含大写字符！");
    		return false;
    	}
    	else if(pwdCpxObj.lowercase_letters == "forbid" && 
    			(pwdvalue.length-pwdvalue.replace(/[a-z]/g, "").length)>0 ) {
    		alert("密码中不允许包含小写字符！");
    		return false;
    	}
    	else if(pwdCpxObj.lowercase_letters == "required" && 
    			(pwdvalue.length-pwdvalue.replace(/[a-z]/g, "").length)<=0 ) {
    		alert("密码中必须包含小写字符！");
    		return false;
    	}
    	else if(pwdCpxObj.numeric == "forbid" && 
    			(pwdvalue.length-pwdvalue.replace(/[0-9]/g, "").length)>0 ) {
    		alert("密码中不允许包含数字！");
    		return false;
    	}
    	else if(pwdCpxObj.numeric == "required" && 
    			(pwdvalue.length-pwdvalue.replace(/[0-9]/g, "").length)<=0 ) {
    		alert("密码中必须包含数字！");
    		return false;
    	}
    	else if(pwdCpxObj.special_symbols == "forbid" && 
    			(pwdvalue.length-pwdvalue.replace(/\W/g, "").length)>0 ) {
    		alert("密码中不允许包含特殊字符！");
    		return false;
    	}
    	else if(pwdCpxObj.special_symbols == "required" && 
    			(pwdvalue.length-pwdvalue.replace(/\W/g, "").length)<=0 ) {
    		alert("密码中必须包含特殊字符！");
    		return false;
    	}
    	return true;
    }
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform"
			action="${ctx}/bione/admin/user/updatePwd?userId=${id}" method="post"></form>
	</div>
</body>
</html>