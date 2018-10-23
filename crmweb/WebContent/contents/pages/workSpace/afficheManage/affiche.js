Ext.onReady(function(){
    Ext.QuickTips.init();    
    
    /***************用于修复ie下datefield显示不完整的bug,ie9测试ok***************/
    Ext.isIE8 = Ext.isIE && navigator.userAgent.indexOf('MSIE 8')!= -1;
    Ext.override(Ext.menu.Menu, {   
        autoWidth : function(){         
            var el = this.el, ul = this.ul;         
            if(!el){         
                return;         
            }         
            var w = this.width;         
            if(w){         
                el.setWidth(w);   
            }else if(Ext.isIE && !Ext.isIE8){   //Ext2.2 支持 Ext.isIE8 属性      
                el.setWidth(this.minWidth);         
                var t = el.dom.offsetWidth;         
                el.setWidth(ul.getWidth()+el.getFrameWidth("lr"));         
            }         
        }         
    }); 
    
    /********************公告是否置顶*********************/
	var noticeIsTopStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo:{
			field:'key',
			direction:'ASC'
		},
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
    
    /********************重要程度STORE(新增修改用)*********************/
    var noticeLevelStore = new Ext.data.Store({
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
            url : basepath+'/lookup.json?name=NOTICE_LEV'
        }),
        reader : new Ext.data.JsonReader({
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
    
    /********************重要程度STORE（查询用）*********************/
    var nlStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['全部',''],['重要', 'lev001'],['一般', 'lev002']]
    });
    
    /********************阅读标识STORE*********************/
    var irStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['全部',''],['已阅', 'red001'],['未阅', 'red002']]
    });
    
    /************************公告查询FORM****************************/
    var centerApply = new Ext.form.FormPanel({
        id : "searchCondition",
        labelWidth : 100,
        frame : true,
        autoScroll : true,
        region : 'north',
        title : '公告查询',
        buttonAlign:"center",
        height:130,
        width: '100%',
        labelAlign:'right',
        items : [{
            layout:'column',
            items : [{
                columnWidth : .25,
                layout : 'form',
                items : [{
                    xtype : 'textfield',
                    fieldLabel : '公告标题',
                    id : 'NOTICE_TITLE',
                    name : 'NOTICE_TITLE',
                    anchor : '90%'
                },{
                    xtype : 'combo',
                    fieldLabel : '阅读标识',
                    mode:'local',
                    emptyText:'请选择',
                    store:irStore,
                    triggerAction:'all',
                    valueField:'value',
                    editable : false,
                    displayField:'key',
                    id : 'isRead',
                    name : 'isRead',
                    anchor : '90%'    
                }]
            },{
                columnWidth : .25,
                layout : 'form',
                items : [{
                    xtype : 'datefield',
                    fieldLabel : '发布日期范围', 
                    name : 'PUBLISH_TIME_START', 
                    format:'Y-m-d', 
                    editable : false,
                    anchor : '90%' 
                },{ 
                    xtype : 'combo',
                    fieldLabel : '重要程度',
                    editable : false,
                    emptyText:'请选择',
                    id : 'NOTICE_LEVEL',
                    name : 'NOTICE_LEVEL',
                    mode : 'local',
                    anchor : '90%',
                    triggerAction:'all',
                    store:nlStore,
                    valueField:'value',
                    displayField:'key'
                }]
            },{
                columnWidth : .25,
                layout : 'form',
                items : [{ 
                    xtype : 'datefield',
                    fieldLabel : '至',
                    name : 'PUBLISH_TIME_END', 
                    format:'Y-m-d', 
                    editable : false,
                    anchor : '90%' 
                }]
            },{
				columnWidth : .25,
				layout : 'form',
				items : [new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '发布人', 
					id:'PUBLISHER1',
					labelStyle: 'text-align:right;',
					name : 'MGR_NAME',
					hiddenName:'PUBLISHER',
					//searchRoleType:('127,47'),  //指定查询角色属性
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:false,
					anchor : '90%'
					})]
			}]
        }],
        buttons:[{
            text : '查询',
            xtype:'button',
            handler : function() {
        		
        		/********************判断开始时间与结束时间是否异常*********************/
                var startData = centerApply.getForm().findField('PUBLISH_TIME_START').getValue();
                var endData = centerApply.getForm().findField('PUBLISH_TIME_END').getValue();
                if(startData!=undefined&&startData!=""&&endData!=undefined&&endData!=""){
                    if(startData>endData){
                        Ext.MessageBox.alert('条件异常', '开始时间应该小于等于结束时间！');
                        return false;
                    }
                }
//                
//                var userId=Ext.getCmp('PUBLISHER1').hiddenField.getValue();
//                if(userId!=null&&userId!=""){
//                	Ext.getCmp('PUBLISHER').setValue(userId);
//                }
                
                var conditionStr =  centerApply.getForm().getFieldValues();
                restfulStore.on('beforeload', function() {
                    this.baseParams = {
                    		"condition":Ext.encode(conditionStr)
                    };
                });
                
                restfulStore.load({
                    params : {
                        start : 0,
                        limit : bbar.pageSize                                                      
                    }
                });
            }
        },{
            text : '重置',
            xtype:'button',
            handler : function() {
	        	Ext.getCmp('PUBLISHER1').setValue('');
	            centerApply.getForm().reset();
            }
        }]
    });
   
    /**
     * Create a standard HttpProxy instance.
     */
    var proxyIndex = new Ext.data.HttpProxy({
        url : basepath+'/noticequery.json?noticeTitle=asas（）'
    });
    /**
     * Data record, used for read records from the JAVA project to store.
     */
    var TopicRecord = Ext.data.Record.create([
        {name: 'noticeId', mapping: 'NOTICE_ID'},
        {name: 'noticeTitle', mapping: 'NOTICE_TITLE'},                                   
        {name: 'topActiveDate', mapping: 'TOP_ACTIVE_DATE'},  
        {name: 'noticeContent', mapping: 'NOTICE_CONTENT'},
        {name: 'noticeLevel', mapping: 'NOTICE_LEVEL_ORA'},
        {name: 'publisher', mapping: 'PUBLISHER'},
        {name: 'publishOrganizer', mapping: 'PUBLISH_ORG'},
        {name: 'isTop', mapping: 'IS_TOP'},
        {name: 'publishTime', mapping: 'PUBLISH_TIME'},
        {name: 'moduleType', mapping: 'MODULE_TYPE'},
        {name: 'published', mapping: 'PUBLISHED'},
        {name: 'activeDate', mapping: 'ACTIVE_DATE'},
        {name: 'noticeType', mapping: 'NOTICE_TYPE'},
        {name: 'isRead', mapping: 'IS_READ_ORA'},
        {name: 'creator', mapping: 'CREATOR'},
        {name: 'publisherName', mapping: 'PUBLISHER_NAME'},
        {name: 'pubOrgName', mapping: 'PUB_ORG_NAME'},
        {name: 'creatorName', mapping: 'CREATOR_NAME'},
        {name: 'annCount', mapping:'ANN_COUNT'}
    ]);
    /**
     * Typical JsonReader.  Notice additional meta-data params for defining the core attributes of your json-response
     */
    var reader = new Ext.data.JsonReader({
        successProperty: 'success',
        idProperty: 'NOTICE_ID',
        messageProperty: 'message',
        totalProperty: 'json.count',
        root : 'json.data'
    },TopicRecord/**data record*/);
    /**
     * store writer, defined for delete records.
     */
    var writer = new Ext.data.JsonWriter({
        encode: false   
    });
    var sm = new Ext.grid.CheckboxSelectionModel();

    var rownum = new Ext.grid.RowNumberer({
        header : 'No.',
        width : 28
    });
    var cm1 = new Ext.grid.ColumnModel([rownum, sm,
        {
            hidden : true,
            header : '公告ID',
            dataIndex : 'noticeId',
            sortable : true,
            width : 120
        },{
        	header : '发布状态',
        	dataIndex : 'published',
        	sortable : true,
        	renderer : function(value){
        		if(value == "pub001"){
        			return "已发布";
        		}else if(value == "pub002"){
        			return "未发布";
        		}
        	},
        	width : 120
        },{
            header : '公告标题', 
            dataIndex : 'noticeTitle', 
            sortable : true,
            width : 120
        },{
            header : '重要程度',
            sortable : true,
            dataIndex : 'noticeLevel',
            width : 120
        },{
            header : '公告内容', 
            width : 120,
            sortable : true,
            hidden :true,
            dataIndex : 'noticeContent'
        },{
            header : '发布机构',
            width : 120,
            sortable : true,
            dataIndex : 'pubOrgName'
        },{
            header : '发布人',
            dataIndex : 'publisherName',
            sortable : true,
            width : 120
        },{
            header : '创建人',
            width : 120,
            sortable : true,
            dataIndex : 'creatorName'
        },{
			header:'创建人ID',
			width : 120,
			dataIndex : 'creator',
			sortable : true,
			hidden:true
        },{
            header : '发布日期',
            width : 120,
            sortable : true,
            dataIndex : 'publishTime',
            renderer:function(value, p, record){
                if(typeof value =='string'){
                    return value.substring(0,10);
                }else{
                    return value.format('Y-m-d');
                }
            }
        },{
            header : '有效日期',
            dataIndex : 'activeDate',
            sortable : true,
            renderer:function(value, p, record){
                if(typeof value =='string'){
                    return value.substring(0,10);
                }else{
                    return value.format('Y-m-d');
                }
            }
        },{
            header : '阅读标记',
            sortable : true,
            dataIndex : 'isRead',
            width : 120
        },{
            header : '附件个数',
            sortable : true,
            dataIndex : 'annCount',
            width: '50'
        }]);
    /**
     * grid store, include proxy、writer、reader
     */
    var restfulStore = new Ext.data.Store({
        id: 'notice',
        restful : true,     
        proxy : proxyIndex,
        reader : reader,
        writer : writer,
        recordType:TopicRecord
    });
   
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
        }),
        valueField : 'value',
        displayField : 'text',
        value: 20,
        editable : false,
        width : 85
    });
    
    var bbar = new Ext.PagingToolbar({
        pageSize : parseInt(pagesize_combo.getValue()),
        store : restfulStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',       
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo]
    });
    pagesize_combo.on("select", function(comboBox) {
        bbar.pageSize = parseInt(pagesize_combo.getValue()),
        restfulStore.reload({
            params : {
                start : 0,
                limit : parseInt(pagesize_combo.getValue())
            }
        });
    });
    /**
     * page size from PagingToolbar combobox.
     * */
    restfulStore.load({
        params : {
            start : 0,
            limit : parseInt(pagesize_combo.getValue())
        }
    });
    /**
     * form panel ,used for create,update,and show the records.
     */
    var addaffiche=new Ext.FormPanel({
        formId:'newNotice',
        frame:true,
        border:false,
        labelAlign:'right',
        standardSubmit:false,
        layout:'form',
        width : 870,
        items : [{
            layout:'column',
            items : [{
                columnWidth : .5,
                layout : 'form',
                items : [{
                    xtype : 'textfield',
                    fieldLabel : '*公告标题',
                    labelStyle:{
                        width:'120px'
                    },        
                    width:'100',
                    name : 'noticeTitle',
                    id : 'noticeTitleBaby',
                    anchor : '90%',
                    allowBlank:false
                },{
                    xtype : 'datefield',
                    fieldLabel : '有效期至',
                    name : 'activeDate', 
                    id:'activeDateBaby',
                    labelStyle: 'text-align:right;',
                    format:'Y-m-d', 
                    editable : false,
                    anchor : '90%' 
                },
				{
					xtype : 'datefield',
					fieldLabel : '置顶时间至',
					name : 'topActiveDate',
					id : 'topActiveDateBaby',
					labelStyle : 'text-align:right;',
					format : 'Y-m-d',
					editable : false,
					anchor : '90%'
				}]
            },{
                columnWidth : .5,
                layout : 'form',
                items : [{
                    xtype : 'combo',
                    fieldLabel : '重要程度',
                    editable : false,
                    triggerAction:'all',
                    mode:'local',
                    emptyText:'请选择',
                    store:noticeLevelStore,
                    valueField:'key',
                    displayField : 'value',
                    name : 'noticeLevel',
                    anchor : '90%',
                    id:'noticeLevelBaby'
                },{
					xtype : 'combo',
					fieldLabel : '是否置顶',
					editable : false,
					hiddenName : 'isTop',
					triggerAction : 'all',
					mode : 'local',
					emptyText : '请选择',
					store : noticeIsTopStore,
					valueField : 'key',
					displayField : 'value',
					name : 'isTop',
					anchor : '90%',
					id : 'isTopBaby',
					listeners : {
						 'select':function(sel){
							 debugger;
							 if(sel.value == '0'){debugger;
								 Ext.getCmp('topActiveDateBaby').setDisabled(true);
								 Ext.getCmp('topActiveDateBaby').setValue('');
							 }else if(sel.value == '1'){
								 Ext.getCmp('topActiveDateBaby').setDisabled(false);
							 }
						 }
					 }
				}]
            }]
        },{
            xtype : 'htmleditor',
            height : 140,
            width : 700,
            fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Times New Roman','Verdana'],
            defaultFont: '宋体',
            defaultLinkValue:"http://www.",
            enableAlignments:true,
            enableColors:true,
            enableFont:true,
            enableFontSize:true,
            enableFormat:true,
            enableLinks:true,
            enableLists:true,
            enableSourceEdit:true,
            id:'noticeContentBaby',
            fieldLabel : '公告内容',
            name : 'noticeContent'
        },{
            hidden:true,
            id : 'noticeIdBaby',
            name : 'noticeId',
            xtype : 'textfield',
            anchor : '90%'
        },{
            hidden:true,
            id : 'publishedBaby',
            name : 'published',            
            xtype : 'textfield',
            anchor : '90%'
        }]
    });  
    /**
     * form panel ,used for show the records.
     */
    var showform=new Ext.FormPanel({
        formId:'showform',
        frame:true,
        border:false,
        labelAlign:'right',
        standardSubmit:false,
        width : 870,
        layout:'form',
        items : [{
            layout:'column',
            items : [{
                columnWidth : .5,
                layout : 'form',
                items : [{
                    xtype : 'textfield',
                    fieldLabel : '*公告标题',
                    labelStyle:{
                        width:'120px'
                    },        
                    width:'100',
                    name : 'noticeTitle',
                    readOnly : true,
                    anchor : '90%',
                    allowBlank:false
                },{
                    xtype : 'datefield',
                    fieldLabel : '有效期至',
                    name : 'activeDate', 
                    labelStyle: 'text-align:right;',
                    format:'Y-m-d', 
                    readOnly : true,
                    anchor : '90%' 
                },
				{
					xtype : 'datefield',
					fieldLabel : '置顶时间至',
					name : 'topActiveDate',
					labelStyle : 'text-align:right;',
					format : 'Y-m-d',
					readOnly : true,
					anchor : '90%'
				}]
            },{
                columnWidth : .5,
                layout : 'form',
                items : [{
                    xtype : 'combo',
                    fieldLabel : '重要程度',
                    editable : false,
                    triggerAction:'all',
                    mode:'local',
                    emptyText:'请选择',
                    store:noticeLevelStore,
                    valueField:'key',
                    displayField : 'value',
                    name : 'noticeLevel',
                    anchor : '90%'
                },{
					xtype : 'combo',
					fieldLabel : '是否置顶',
					editable : false,
					readOnly : true,
					hiddenName : 'isTop',
					triggerAction : 'all',
					mode : 'local',
					emptyText : '请选择',
					store : noticeIsTopStore,
					valueField : 'key',
					displayField : 'value',
					name : 'isTop',
					anchor : '90%'
				}]
            }]
        },{
            xtype : 'htmleditor',
            height : 140,
            width : 700,
            enableAlignments: false,  
            enableColors: false,  
            enableFont: false,  
            enableFontSize: false,  
            enableLinks: false,  
            enableFormat: false,  
            enableLists: false,  
            enableSourceEdit: false, 
            editable : false,
            fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Times New Roman','Verdana'],
            defaultFont: '宋体',
            fieldLabel : '公告内容',
            name : 'noticeContent'
        },{
            hidden:true,
            id : 'noticeIdBaby',
            xtype : 'textfield',
            anchor : '90%'
        },{
            hidden:true,
            name : 'published',            
            xtype : 'textfield',
            anchor : '90%'
        }]
    });  
    /**
     * grid toolbar.
     */
    var tbar = new Ext.Toolbar({
        items : [{
            id:'_addNot',
            //disabled:true,
            /**
             * 判断该新增权限
             * 同理，若需要在无权限时隐藏，则用hidden属性。
             * hidden:JsContext.checkGrant(JsContext.add)
             * 其中，参数代表所需判断的按钮ID。
             */
            disabled:JsContext.checkGrant('_addNot'),
            text : '公告新增',
            iconCls:'addIconCss',
            handler : function() {
                Ext.each(addaffiche.getForm().items.items,function(f){
                    f.originalValue = '';
                });
                var win=new Ext.Window({
//                    layout : 'fit',
                    width : 880,
                    height :350,
                    closable : true,
                    resizable : false,
                    collapsible : false,
                    draggable : true,
                    closeAction : 'hide',
                    title : '新增公告',
                    modal : true, 
                    animCollapse : false,
                    maximizable : true,
                    border : false,
                    closable : true,
                    animateTarget : Ext.getBody(),
                    constrain : true,
                    items : [addaffiche,
                             { 
						id:'filefields',
						xtype:'form',
						height : 80,
					    width : '100%',
					    fileUpload : true, 
					    dataName:'file',
					    frame:true,
					    relaId:'',/**关联数据ID*/
					    modinfo:'notice',/**modinfo: notice:公告;customer:客户;infomation:资讯;*/
					    items: [
					        new Ext.form.TextField({
					        	xtype :'textfield',
					        	name:'annexeName',
					        	inputType:'file',
					        	fieldLabel : '附件名称',
					        	labelStyle: 'text-align:right;',
					        	anchor :'90%'
						})]}],
                    buttonAlign:'center',
                    buttons:[{
                        text:'新增',
                        handler:function(){
                            if(!addaffiche.getForm().isValid()){
                                Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                                return false;
                            }
                            
                            var pars = addaffiche.getForm().getFieldValues();
                            Ext.Ajax.request({
                                url: basepath+'/workplatnotice.json',
                                method: 'POST',
                                params :pars,
                                failure : function(form, action){
                                    Ext.MessageBox.alert('新增操作', '新增失败！');
                                    restfulStore.load({
                                        params : {
                                            start : 0,
                                            limit : parseInt(pagesize_combo.getValue())
                                        }
                                    });
                                },
                                success:function(response){
                                    //附件上传动作
                                    Ext.Ajax.request({
										url : basepath+'/session-info!getPid.json',
										method : 'GET',
										success : function(a,b,v) {
										    var noticeIdStr = Ext.decode(a.responseText).pid;
										    var filefields = Ext.getCmp('filefields');
										    filefields.relaId = noticeIdStr;
										    filefields.modinfo = 'notice';
										    Com.yucheng.bcrm.common.uploadFiles(filefields);//调用annacommit.js中方法
										}
									});
                                    win.hide();
                                    Ext.Msg.alert('新增操作', '新增成功！',function(btn){debugger;
                                    	if(btn == 'ok'){
                                    		restfulStore.load({
                                    			params : {
                                    			start : 0,
                                    			limit : parseInt(pagesize_combo.getValue())
                                    		}
                                    		});
                                    	}
                                    });
                                }
                            });
                        }
                    },{
                        text: '取消',
                        handler:function(){
                            win.hide();
                        }
                    }]  
                });
                win.show();
                addaffiche.getForm().reset();
            }
        }
        , 
        '-',
        {
            id:'__upNot',
            text : '公告修改',
            iconCls:'resetIconCss',
            handler : function() {
                
                var _record = grid.getSelectionModel().getSelected();
                if (!_record) {
                    Ext.MessageBox.alert('修改操作', '请选择要操作的记录！');
                    return false;
                } else {
                    var checkedNodes = grid.getSelectionModel().selections.items;
                    if(checkedNodes.length>1){
                        Ext.MessageBox.alert('修改操作', '您选择的记录过多！');
                        return false;
                    }
                    
                    /* 附件编辑列表 */
						var noticeIdStr = _record.get('noticeId');
						uploadForm.relaId = noticeIdStr;
						uploadForm.modinfo = 'notice';
						var condi = {};
						condi['relationInfo'] = noticeIdStr;
						condi['relationMod'] = 'notice';
						Ext.Ajax.request( {
							url : basepath + '/queryanna.json',
							method : 'GET',
							params : {
								"condition" : Ext.encode(condi)
							},
							failure : function(a, b, c) {
								Ext.MessageBox.alert(
												'查询异常','查询失败！');
							},
							success : function(response) {
								var anaExeArray = Ext.util.JSON.decode(response.responseText);
								appendixStore.loadData(anaExeArray.json.data);
								appendixGridPanel2.getView().refresh();
							}
					    });
                    
                    var winMod=new Ext.Window({
//                        layout : 'fit',
                        width : 900,
                        height :350,
                        closable : true,
                        resizable : false,
                        draggable : true,
                        closeAction : 'hide',
                        title : '修改公告',
                        collapsible : false,
                        modal : true, 
                        animCollapse : false,
                        maximizable : true,
                        border : false,
                        closable : true,
                        animateTarget : Ext.getBody(),
                        constrain : true,
                        autoScroll : true,
                        items : [addaffiche,
                                 {
							   xtype : 'fieldset',
							   title: '附件',
							   autoHeight : true,
							   layout : 'form',
							   collapsed: true,
						       collapsible: true,
					           items : [appendixGridPanel2],
					           listeners:{
									'collapse':function(){
										winMod.setHeight(320);
										winMod.setPosition(120,10);
										winMod.doLayout();
									},
								    'expand':function(){
										winMod.setHeight(400);
										winMod.setPosition(120,10);
										winMod.doLayout();
									}
							   }
					          }],
                        buttonAlign:'center',
                        buttons:[{
                            text:'修改',
                            handler:function(){
                                if(!addaffiche.getForm().isValid()){
                                    Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                                    return false;
                                }
                                
                                var pars = addaffiche.getForm().getFieldValues();
                                Ext.Ajax.request({
                                    url: basepath+'/workplatnotice.json',
                                    method: 'POST',
                                    params:pars,
                                    failure : function(form, action){
                                        Ext.MessageBox.alert('修改操作', '修改失败！');
                                        restfulStore.load({
                                            params : {
                                                start : 0,
                                                limit : parseInt(pagesize_combo.getValue())
                                            }
                                        });
                                    },
                                    success:function(response){
                                        Ext.MessageBox.alert('修改操作', '修改成功！');
                                        restfulStore.load({
                                            params : {
                                                start : 0,
                                                limit : parseInt(pagesize_combo.getValue())
                                            }
                                        });
                                        winMod.hide();
                                    }
                                });
                            }
                        },{
                            text: '取消',
                            handler:function(){

                        	debugger;
                        	winMod.hide();
                            }
                        }],
                        listeners : {
                    		'beforeshow' : function(){
                    			Ext.getCmp('_downId').setDisabled(true);
                    			Ext.getCmp('_upload').setDisabled(false);
                    			Ext.getCmp('_delload').setDisabled(false);
                    		}
                    	}     
                    });
                    var record = grid.getSelectionModel().getSelected();
                    addaffiche.getForm().loadRecord(record);
                    winMod.show();}               
            }
        },'-',{
        	id:'infoNot',
            text:'公告详细',
            iconCls : 'detailIconCss',
            handler:function()
            {
                var win=new Ext.Window({
//                    layout : 'fit',
                    width : 900,
                    height :350,
                    closable : true,
                    resizable : false,
                    draggable : true,
                    closeAction : 'hide',
                    title : '查看公告',
                    collapsible : false,
                    modal : true,
                    animCollapse : false,
                    maximizable : true,
                    border : false,
                    closable : true,
                    animateTarget : Ext.getBody(),
                    constrain : true,
                    autoScroll : true,
                    items : [showform,
                             {
     				   xtype : 'fieldset',
     				   title: '附件',
     				   id : 'detailApp',
     				   autoHeight : true,
     				   layout : 'form',
     				   collapsed: true,
     			       collapsible: true,
     		           items : [appendixGridPanel2],
     		           listeners:{
     						'collapse':function(){
     							win.setHeight(320);
     							win.setPosition(120,10);
     							win.doLayout();
     						},
     					    'expand':function(){
     							win.setHeight(400);
     							win.setPosition(120,10);
     							win.doLayout();
     						}
     				   }
     		          }],
                    buttonAlign:'center',
                    buttons:[{
                        text: '返回',
                        handler:function(){
                            win.hide();
                        }
                    }],
                    listeners : {
                		'beforeshow' : function(){
                			Ext.getCmp('_downId').setDisabled(false);
                			Ext.getCmp('_upload').setDisabled(true);
                			Ext.getCmp('_delload').setDisabled(true);
                		}
                	}
                });
                var record = grid.getSelectionModel().getSelected();
                /* 附件编辑列表  */	
				var noticeIdStr = record.get('noticeId');
				uploadForm.relaId = noticeIdStr;
				uploadForm.modinfo = 'notice';
				var condi = {};
				condi['relationInfo'] = noticeIdStr;
				condi['relationMod'] = 'notice';
				Ext.Ajax.request( {
					url : basepath + '/queryanna.json',
					method : 'GET',
					params : {
						"condition" : Ext.encode(condi)
					},
					failure : function(a, b, c) {
						Ext.MessageBox.alert('查询异常', '查询失败！');
					},
					success : function(response) {
						var anaExeArray = Ext.util.JSON.decode(response.responseText);
						appendixStore.loadData(anaExeArray.json.data);
						appendixGridPanel2.getView().refresh();
					}
				});
                showform.getForm().loadRecord(record);
                win.show();
            }
        } , '-', {
            id:'delNot',
            text : '公告删除',
            iconCls:'deleteIconCss',
            handler : function() {
                var checkedNodes = grid.getSelectionModel().selections.items;
                if(checkedNodes.length==0)
                {
                    Ext.Msg.alert('提示', '未选择任何记录');
                    return ;
                }else{
                	if(confirm("确定删除吗?")){
                        var batString = "noticeId";
                        var allowDel = true;
                        var disDelStr = "";
                        for(var i=0;i<checkedNodes.length;i++){
                            var noticeIds = checkedNodes[i].data.noticeId;
                            var creator = checkedNodes[i].data.creator;
                            var title = checkedNodes[i].data.noticeTitle;
                            if(creator!=__userId){
                                allowDel=false;
                                disDelStr += "【"+title+"】";
                            }
                            batString +=":"+noticeIds;
                        }
                        if(!allowDel){
                            Ext.Msg.alert('提示', '记录：'+disDelStr+"不是由您创建，您无权删除。");
                            return ;
                        }
                        Ext.Ajax.request({
                            url: basepath+'/workplatnotice.json',                                
                            method : 'POST',
                            params : {
                                methodNs : 'delete',
                                isBat : true ,
                                batString : batString
                            },
                            success : function(){
                                Ext.Msg.alert('提示', '删除成功');
                                restfulStore.load({
                                    params : {
                                        start : 0,
                                        limit : parseInt(pagesize_combo.getValue())
                                    }
                                });
                            },
                            failure : function(){
                                Ext.Msg.alert('提示', '删除失败');
                                restfulStore.load({
                                    params : {
                                        start : 0,
                                        limit : parseInt(pagesize_combo.getValue())
                                    }
                                });
                            }
                        });
                    //}
                
                	}
                    }
            }
        },'-',{
            id:'pubNot',
            text:'发布',
            iconCls:'publishIconCss',
            handler:function()
            {
                var checkedNodes = grid.getSelectionModel().selections.items;
                if(checkedNodes.length==0){
                    Ext.MessageBox.alert('发布操作', '未选择记录！');
                }else{
                    
                    var allowPub = true;
                    var disPubStr = "";
                    for(var i=0;i<checkedNodes.length;i++){
                        var creator = checkedNodes[i].data.creator;
                        var title = checkedNodes[i].data.noticeTitle;
                        if(creator!=__userId){
                            allowPub=false;
                            disPubStr += "【"+title+"】";
                        }
                    }
                    if(!allowPub){
                        Ext.Msg.alert('提示', '记录：'+disPubStr+"不是由您创建，您无权发布。");
                        return ;
                    }
                    for(var i = 0;i<checkedNodes.length;i++){
                        if(checkedNodes[i].data.published=="pub001"){
                            var title = checkedNodes[i].data.noticeTitle;
                            Ext.MessageBox.alert('发布操作', '公告【'+title+'】已发布，请取消选择！');
                            return false;
                        }
                    }
                    var noticeIds = "noticeId";
                    for(var i = 0;i<checkedNodes.length;i++){
                        noticeIds +=":"+checkedNodes[i].data.noticeId;
                    }
                    var parStr = noticeIds;
                    Ext.Ajax.request({
                        url:basepath+'/workplatnotice.json',
                        method:'POST',
                        params:{
                            isBat:true,
                            batString:parStr,
                            methodNs:'publish'
                        },
                        success : function(){
                            Ext.MessageBox.alert('发布操作', '发布成功');
                            restfulStore.load({
                                params : {
                                    start : 0,
                                    limit : parseInt(pagesize_combo.getValue())
                                }
                            });
                        },
                        failure : function(){
                            Ext.MessageBox.alert('发布操作', '发布失败');
                            restfulStore.load({
                                params : {
                                    start : 0,
                                    limit : parseInt(pagesize_combo.getValue())
                                }
                            });
                        }
                    });
                }
            }
        },'-',{
            id:'redNot',
            text:'已 阅',
            iconCls:'ReadIconCss',
            handler:function()
            {	debugger;
                var checkedNodes = grid.getSelectionModel().selections.items;
                if(checkedNodes.length==0){
                    Ext.MessageBox.alert('已阅操作', '未选择记录！');
                }else{
                    /**
                     * check if the record has been read. Remind for the bizlogic.
                     */
                    var noticeIds = "noticeId";
                    for(var i = 0; i<checkedNodes.length; i++){
                        noticeIds += ":"+checkedNodes[i].data.noticeId;
                    }
                    Ext.Ajax.request({
                        url : basepath+'/workplatnoticeread.json',
                        method : 'POST',
                        params : {
                            isBat:true,
                            batString:noticeIds,
                            methodNs:'create'
                        },
                        success : function(){
                            Ext.MessageBox.alert('已阅操作', '设置成功！');
                            restfulStore.load({
                                params : {
                                    start : 0,
                                    limit : parseInt(pagesize_combo.getValue())
                                }
                            });
                        },
                        failure : function(){
                            Ext.MessageBox.alert('已阅操作', '设置失败！');
                            restfulStore.load({
                                params : {
                                    start : 0,
                                    limit : parseInt(pagesize_combo.getValue())
                                }
                            });
                        }
                    });
                }
            }
        },'-',{
            id:'annNot',
            text:'附件信息',
            iconCls:'youJiIconCss',
            handler:function()
            {
                var record = grid.getSelectionModel().getSelected(); 
                if (!record) {
                    Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
                    return false;
                }
                var checkedNodes = grid.getSelectionModel().selections.items;
                if(checkedNodes.length>1){
                    Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
                    return false;
                }
                var noticeIdStr = record.get('noticeId');
                
                uploadForm.relaId = noticeIdStr;
                uploadForm.modinfo = 'notice';
                var condi = {};
                condi['relationInfo'] = noticeIdStr;
                condi['relationMod'] = 'notice';
                Ext.Ajax.request({
                    url:basepath+'/queryanna.json',
                    method : 'GET',
                    params : {
                        "condition":Ext.encode(condi)
                    },
                    failure : function(a,b,c){
                        Ext.MessageBox.alert('查询异常', '查询失败！');
                    },
                    success : function(response){
                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
                        appendixStore.loadData(anaExeArray.json.data);
                        appendixGridPanel.getView().refresh();
                    }
                });
                appendixWindow.show();
            }
        },'-',new Com.yucheng.bob.ExpButton({
            formPanel : 'searchCondition',
            iconCls:'exportIconCss',
            url : basepath+'/noticequery.json'
        })]
    });
    /**公告信息表格**/
    var grid = new Ext.grid.GridPanel({
        title : '公告信息',
        frame : true,
        store : restfulStore, 
        region : 'center',
        stripeRows : true, 
        tbar : tbar,
        cm : cm1,
        sm : sm,
        bbar : bbar,
        viewConfig : {
        },
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
    });
    grid.on('click', function(grid, rowIndex, event) {
//      setGrant();
  }); 
    function setGrant(){
        var record = grid.getSelectionModel().getSelected(); 
        if(record==undefined){
            return;
        }
        var creator = record.get('creator');
        if(creator==__userId){
            Ext.getCmp('__upNot').setDisabled(false);
            Ext.getCmp('delNot').setDisabled(false);
            Ext.getCmp('pubNot').setDisabled(false);
            Ext.getCmp('__upload').setDisabled(false);
        }else {
            Ext.getCmp('__upNot').setDisabled(true);
            Ext.getCmp('delNot').setDisabled(true);
            Ext.getCmp('pubNot').setDisabled(true);
            Ext.getCmp('__upload').setDisabled(true);
        }
    }
    grid.on('rowdblclick', function() {
    	var win=new Ext.Window({
			layout : 'fit',
			width : 900,
			height :350,
			closable : true,
			resizable : false,
			draggable : true,
			closeAction : 'hide',
			title : '查看公告',
			collapsible : false,
			modal : true, 
			animCollapse : false,
			maximizable : true,
			border : false,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [showform],
			buttonAlign:'center',
			buttons:[{
			    text: '返回',
			    handler:function(){
				debugger;
			        win.hide();
			    }
			}]
    	});

		var record = grid.getSelectionModel().getSelected(); 
        if (!record) {
            Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
            return false;
        }
        var checkedNodes = grid.getSelectionModel().selections.items;
        if(checkedNodes.length>1){
            Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
            return false;
        }
        showform.getForm().loadRecord(record);			
        win.show();
    }); 
         
    if(__roles.indexOf('1')>=0||__roles.indexOf('3')>=0||__roles.indexOf('4')>=0){
        Ext.getCmp('_addNot').disabled=false;
    }
    
    var viewport = new Ext.Viewport({
        layout : 'fit',
        items : [ {
            layout : 'border',
            items : [centerApply,grid]
        } ]
    });
});