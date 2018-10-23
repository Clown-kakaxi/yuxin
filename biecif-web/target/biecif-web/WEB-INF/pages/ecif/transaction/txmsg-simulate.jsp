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
		 	labelWidth : 100,
			inputWidth : 750,
			space : 30, 
			fields : [   
		    {
				display : "服务器类型<font color='red'>*</font>",
				name : "serverType",
				type:'select',
				width :400,
				options:{
					valueFieldID:'serverType',
					data : [{id : '1', text : 'SOCKET'}],
				    onSelected : function(val) {
						setCss();
					}
				}
			},{
				display : "IP地址",
				name : "ip",
				type : "text",
				width :150
			},{
				display : "端口",
				name : "port",
				type : "text",
				newline : false,
				width :100
			},{
				display : "参数",
				name : "params",
				type : "textarea",
				attr : {
					style : "height: 100px;"
				},
				newline : true
			},{
				display : "请求报文",
				name : "reqMsg",
				type : "textarea",
				attr : {
					style : "height: 200px;"
				},
				validate : {
					required : true
				}
				
			}, {
				display : "响应报文",
				newline : true,
				name : "resMsg",
				type : "textarea",
				attr : {
					style : "height: 600px;"
				}
			} ]
		});

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
		var buttons = [];
		buttons.push({
			text : '发送',
			onclick : save_resDef
		});

		BIONE.addFormButtons(buttons);
		$("#mainform input[name=ip]")[0].parentNode.parentNode.parentNode.style.display="";
		$("#mainform textarea[name=params]")[0].parentNode.parentNode.style.display="none";

	});

    function setCss() {
    	var val = $("#mainform input[name=serverType]").val();
    	//prompt('',$("#mainform input[name=params]"));
    	if (val == "HTTP"||val == "WebService"||val == "SOCKET") {
    		
    		
    	    //$("#mainform input[name=ip]").parent().parent().parent().parent("ul").hide().find("input").removeAttr("disabled").css("color", "black");
    	    
    	    $("#mainform input[name=ip]")[0].parentNode.parentNode.parentNode.style.display="";
    	    $("#mainform textarea[name=params]")[0].parentNode.parentNode.style.display="none";    	    
    	}else{
    	    $("#mainform input[name=ip]")[0].parentNode.parentNode.parentNode.style.display="none";
    	    $("#mainform textarea[name=params]")[0].parentNode.parentNode.style.display="";    
    	    
    	    if (val == "ActiveMQ"){
    	    	$("#mainform textarea[name=params]")[0].value = 
    	    		"URL=tcp://192.168.1.99:61616\n"+
    	    		"activeMqRcvQueueName=queue-1\n"+
    	    		"activeMqSndQueueName=queue-2";
    	    }
    	    if (val == "YCESB"){
    	    	$("#mainform textarea[name=params]")[0].value = 
    	    		"ycEsbHostname=192.168.1.99\n"+
    	    		"ycEsbQmName=QM_GW_IN\n"+
    	    		"ycEsbQmPort=33300\n"+
    	    		"ycEsbQmCcsid=1208\n"+
    	    		"ycEsbChannel=SVRCONN_GWIN\n"+
    	    		"ycEsbAppCd=ECIF";
    	    }
    	    if (val == "YCESB2"){
    	    	$("#mainform textarea[name=params]")[0].value = 
    	    		"ycEsbHostname_main=192.168.1.99\n"+ 
    	    		"ycEsbQmName_main=QM_GW_IN\n"+
    	    		"ycEsbQmPort_main=33300\n"+
    	    		"ycEsbQmCcsid_main=1208\n"+
    	    		"ycEsbChannel_main=SVRCONN_GW\n"+
    	    		"ycEsbSndQueueName_main=REQ\n"+
    	    		"ycEsbRcvQueueName_main=REP.CMS\n"+
    	    		"ycEsbCnnPoolMax_main=2\n"+
    	    		"ycEsbHostname_sub=192.168.1.99\n"+
    	    		"ycEsbQmName_sub=QM_GW_OUT\n"+
    	    		"ycEsbQmPort_sub=33399\n"+
    	    		"ycEsbQmCcsid_sub=1208\n"+
    	    		"ycEsbChannel_sub=SVRCONN_GW\n"+
    	    		"ycEsbRcvQueueName_sub=REP.CMS\n"+
    	    		"ycEsbCnnPoolMax_sub=2\n"+
    	    		"charset=UTF-8\n"+
    	    		"timeout=60\n";
    	    }
    		
    	}
    }
	
	function save_resDef() {
		BIONE.submitForm($("#mainform"), function(data) {
			
			//alert(data.resMsg);
			//if(data!='') {
				//prompt('',data.resMsg);
				$("#mainform textarea[name='resMsg']").val(data.resMsg);
			//}
			//BIONE.closeDialogAndReloadParent("resDefManage","maingrid" , "发送成功");
		}, function() {
			BIONE.closeDialog("resDefManage", "发送失败");
		});
	}


</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/transaction/txsimulate" method="post"></form>
	</div>
</body>
</html>