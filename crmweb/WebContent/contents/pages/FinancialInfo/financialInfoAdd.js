Ext.onReady(function(){
	var finDirStore = new Ext.data.JsonStore( {
		id : 'finDirStore',
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/fin-dir.json'
		}),
		fields : [ 'dirId', 'dirName' ],
		reader : new Ext.data.JsonReader( {
			totalProperty : 'list'
		}, [ {
			name : 'dirId'
		}, {
			name : 'dirName'
		} ])
	});
	var formPanel=new Ext.form.FormPanel({
		labelAlign:'right',
		frame : true,
		region : 'center',
		fileUpload:true,
		autoScroll : true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:0.5,
				layout:'form',
				items:[{
					xtype : 'textfield',
					labelStyle : {
						width : '120px'
					},
					Width : '100',
					id : 'title',
					name : 'title',
					allowBlank:false,
					fieldLabel : '标题',
					anchor : '90%'
				},{
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					Width:'100',
					id:'finInfoIssuing',
					name:'finInfoIssuing',
					fieldLabel:"期刊号",
					allowBlank:false,
					anchor:'90%'
				}]
			},{
				columnWidth:0.5,
				layout:'form',
				items:[{
					xtype : 'textfield',
					inputType:'file',
					labelStyle : {
						width : '120px'
					},
					Width : '100',
					id : 'titleImg',
					name : 'titleImg',
					fieldLabel : '标题图片',
					allowBlank:false,
					anchor : '90%'
//				},{
//					xtype : 'textfield',
//					labelStyle : {
//						width : '120px'
//					},
//					Width : '100',
//					id : 'belongDirId',
//					name : 'belongDirId',
//					fieldLabel : '所属目录',
//					anchor : '90%'
				},{
					store : finDirStore,
					xtype : 'combo',
					resizable : true,
					fieldLabel : '所属目录',
					allowBlank:false,
					name : 'belongDirId',
					hiddenName : 'belongDirId',
					valueField : 'dirId',
					displayField : 'dirName',
					mode : 'local',
					typeAhead : true,
					editable:false,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					width : '100',
					anchor : '90%'
			}]
			}]
		},{
			xtype : 'textarea',
			labelStyle : {
				width : '0px'
			},
			id : 'uploadContent',
			fieldLabel:'财经资讯内容',
			allowBlank:false,
			name : 'uploadContent',
			anchor : '90%'
		}],
		buttonAlign:'center',
		buttons:[{
			text:'发布',
			
			handler:function(){
				 if(formPanel.getForm().isValid()){
					 formPanel.getForm().submit({
		                    url: basepath+'/uploadFinInfoTitleImg?type=title',
		                    success:function(formPanel,action){
		                    	Ext.Msg.alert("发布成功","发布成功",function(){
		                    		window.location='financialInfo.jsp';		                    		
		                    	});
		                    }
		                });
				 }
			}
		},{
			text:'取消',
			handler:function(){
				window.location='financialInfo.jsp';
			}
		}]
	});
	new Ext.Viewport( {
		layout : 'fit',
		items : [formPanel]
	});	
	CKEDITOR.replace('uploadContent',{filebrowserUploadUrl : basepath+'/uploadFinInfoTitleImg?type=content'}); 
});