<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>  
<head>  
    <title></title> 
    <script type="text/javascript" src="/crmweb/contents/commonjs/jquery-1.5.2.min.js"></script>
    <script  type="text/javascript" src="./fileUploadField.js"></script>  
    <style type=text/css>      
 		.upload-icon {  
            background: url('images/image_add.png') no-repeat 0 0 !important;  
        }  
    .x-form-file-wrap {  
        position: relative;  
        height: 22px;  
    }  
    .x-form-file-wrap .x-form-file {  
        position: absolute;  
        right: 0;  
        -moz-opacity: 0;  
        filter:alpha(opacity: 0);  
        opacity: 0;  
        z-index: 2;  
        height: 22px;  
    }  
    .x-form-file-wrap .x-form-file-btn {  
        position: absolute;  
        right: 0;  
        z-index: 1;  
    }  
    .x-form-file-wrap .x-form-file-text {  
        position: absolute;  
        left: 0;  
        z-index: 3;  
        color: #777;  
    }  
    </style>
  
    <script type="text/javascript">
    		/**
				 * 获取唯一的表单对象，用于通过常规表单提交的方式提交参数。
				 * @param {Object} [params] 参数对象。
				 * @param {Boolean} [isUpload] 是否使用multipart/form-data编码，该编码方式通常用于上传文件。默认为false。
				 * @return {HTMLForm} 表单dom对象。
				 */
				function getForm(params, isUpload) {
				  var el, form = window.defaultForm;
				  if (form) {
					while (form.childNodes.length !== 0){
					  form.removeChild(form.childNodes[0]);
					}
				  } else {
				    form = document.createElement('FORM');
				    window.defaultForm = form;
				    document.body.appendChild(form);
				  }
				  if (params) {
				  	for(var key in params){
				  		var value = params[key];
				  		el = document.createElement('input');
						//el.setAttribute('id', key);
						el.setAttribute('name', key);
						el.setAttribute('type', 'hidden');
						if (Ext.isArray(value) || Ext.isObject(value))
						  value = Ext.encode(value);
						else if (Ext.isDate(value))
						  value = Ext.dateToStr(value);
						el.setAttribute('value', Ext.isEmpty(value) ? '' : value);
						form.appendChild(el);
				  	}
				  }
				  if (isUpload)
				    form.encoding = "multipart/form-data";
				  else
				    form.encoding = "application/x-www-form-urlencoded";
				  return form;
				}
				/**
				 * 表单提交
				 * @param {Object} o 表单提交的配置信息
				 * @param {Ext.FormPanel} [o.formpanel] 表单面板
				 * @param {String} o.url 提交的URL地址。
				 * @param {Object} [o.params] 参数对象。
				 * @param {String} [o.target] 指定在何处打开url，可以为预设值或框架。默认为'_blank'。
				 * @param {String} [o.method] 提交使用的方法。默认为'POST'。
				 * @param {Boolean} [o.isUpload] 是否使用multipart/form-data编码，该编码方式通常用于上传文件。默认为false。
				 */
				function submit(o){
					if(!o||!o.url){
						return;
					}
					var formPanel = o.formpanel
					if(formPanel instanceof Ext.FormPanel){
						Ext.applyIf(o,{
							waitTitle : '请稍候',
							waitMsg : '正在保存......',
							method 	: 'POST',
							scope 	: formPanel
						});
						if(formPanel.form.isValid()){
							formPanel.form.submit(o);
						}
					}else{
						var form = getForm(o.params, o.isUpload);
					    form.action = o.url;
					    form.method = o.method || 'POST';
					    if(o.target){
					    	form.target = o.target || '_blank';
					    }
					    form.submit();
					}
				}
        Ext.onReady(function() {  
            Ext.QuickTips.init();  
  
            var msg = function(title, msg) {  
                Ext.Msg.show({  
                    title: title,  
                    msg: msg,  
                    minWidth: 200,  
                    modal: true,  
                    icon: Ext.Msg.INFO,  
                    buttons: Ext.Msg.OK  
                });  
            };  
              
            var fp = new Ext.FormPanel({  
                renderTo: 'fi-form',  
                fileUpload: true,  
                width: 700,
                inputType :'file',
                frame: true,  
                title: '<font style="font-size:16px;font-family:blod;">开户手册上传下载操作</font>',  
                autoHeight: true,  
                bodyStyle: 'padding: 50px;margin-bottom:30px;border:1px solid #a6b1be; ',  
                labelWidth: 80,  
                defaults: {  
                    anchor: '95%',  
                    allowBlank: false,  
                    msgTarget: 'side'  
                },  
                items: [{  
                    xtype: 'textfield',  
                    fieldLabel: '文件名称',
                    id:"txt-name",  
                    name:"myname"  ,
                    hidden:true
                }, {  
                    xtype: 'fileuploadfield',  
                    id: 'file-path',  
                    emptyText: '选择文件',  
                    fieldLabel: '<font style="font-size:16px;">文件路径</font>',  
                    name: 'mypath',   	
                    buttonText: '<font>&nbsp;&nbsp;选择&nbsp;&nbsp;</font>',
                    listeners	: {
                    	fileselected : function(comp,path){
                    		path = path.substring(path.lastIndexOf("\\")+1);
                    		Ext.getCmp("txt-name").setValue(path);
                    	}
                    } 
                    //buttonCfg: {  
                    //    iconCls: 'upload-icon'  
                    //}  
				}],  
                    buttons: [{  
                        text: '<font>&nbsp;&nbsp;下载开户手册&nbsp;&nbsp;</font>',  
                        handler: function() {
                        	submit({
                        			url : basepath + '/oneKeyAccountAction!downloadFile.json'
                        	});
                        }  
                    },{  
                        text: '<font>&nbsp;&nbsp;上传开户手册&nbsp;&nbsp;</font>',  
                        handler: function() {  
                            if (fp.getForm().isValid()) {  
                                fp.getForm().submit({  
                                    url: basepath + '/oneKeyAccountAction!uploadFile.json',
                                    waitMsg: '正在上传，请稍候。。。',  
                                    success: function(form, action) {
                                    	Ext.Msg.alert("提示",action.result.msg);  
                                    },
                                    failure	: function(form, action){
                                    	Ext.Msg.alert("提示",action.result.msg);
                                    }  
                                });  
                            }  
                        }  
                    }, {  
                        text: '<font>&nbsp;&nbsp;重置&nbsp;&nbsp;</font>',  
                        handler: function() {  
                            fp.getForm().reset();  
                        }  
					}]  
                    });  
                });  
      
</script>  
</head>  
<body style = 'background-color:#e9edf6;'>  
<div id="fi-form" align='center' 
	style = 'padding:50px 0px;margin:20px 50px; '></div>  
  
</body>  
</html> 