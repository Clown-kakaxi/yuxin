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
	
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "资讯管理",
		stUrl : basepath + '/finInfoQuery.json',
		deUrl:basepath+'/finInfo!batchDelete.json',
		primary : "finInfoId",
		dbclick:false,
		checkbox : true,
		buts:[
			{
				text : '发布新资讯',
				tooltip : '删除所选中的信息',
				handler : function(){
//					window.location='financialInfoAdd.jsp';
					window.location='financialInfoAdd.jsp';
				},
				scope : this
			
		},'-',{
			text:'查看资讯',
			tooltip:'查看资讯内容',
			handler:function(){
				if (listPanel.grid.selModel.hasSelection()) {
					var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen > 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						var id = listPanel.grid.getSelectionModel()
						.getSelected().get(listPanel.primary);
						window.location="financialInfoContent.jsp?finInfoId="+id;		
					}
				}
			},
			scope:this
		}],
		winHeight : 450,
		winWidth : 800,
		gclms : [ {
			name:'finInfoId',
			mapping:'FIN_INFO_ID'
		},{
			name : 'title',
			header:'标题',
			mapping : 'TITLE'
		}, {
			name : 'belongDirName',
			header:'所属目录',
			mapping : 'DIR_NAME'
		}, {
			name : 'titlePicPath',
			header:'标题图片路径',
			mapping : 'TITLE_PIC_PATH'
		}, {
			name : 'uploadDt',
			header:'上传日期',
			mapping : 'UPLOAD_DT',
			type : 'date'
		}, {
			name : 'uploadPerId',
			header:'上传人',
			mapping : 'UPLOAD_PER_ID'
		} , {
			name : 'finInfoIssuing',
			header:'期刊号',
			mapping : 'FIN_INFO_ISSUING'
		}],
		pagesize : 20,
		// 查询字段
		selectItems : {
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [ {
						store : finDirStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '所属目录',
						name : 'DIR_ID',
						hiddenName : 'DIR_ID',
						valueField : 'dirId',
						editable:false,
						displayField : 'dirName',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						width : '100',
						anchor : '90%'
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [{
					name : 'TITLE',
					xtype : 'textfield',
					fieldLabel : '标题',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [ {
					name : 'UPLOAD_PER_ID',
					xtype : 'textfield',
					fieldLabel : '上传人',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [{
					name : 'FIN_INFO_ISSUING',
					xtype : 'textfield',
					fieldLabel : '期刊号',
					width : '100',
					anchor : '90%'
				}]
			}]
		}
	});
	
	new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
});