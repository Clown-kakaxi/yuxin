Ext.onReady(function() {
    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
    /***********************************数据字典store*****************************/
    var zjlxStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=CMRZJLX'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

    var gjStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=GJDQ'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    var zycdStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=IMT_LEV'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    var xlStore = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data:[['研究生','研究生'],['在职本科','在职本科'],['在职研究生','在职研究生'],['大专','大专'],['在职博研','在职博研'],['高中','高中'],
              ['中专','中专'],['职高','职高'],['初中','初中'],['在职大专','在职大专'],['在职中专','在职中专'],['小学','小学'],
              ['本科','本科'],['博士研究生','博士研究生'],['技校','技校'],['高职','高职']]
    });
    /***************************************************************************/
    var mzStore = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data:[['汉 族','汉 族'],['蒙古族','蒙古族'],['回 族','回 族'],['藏 族','藏 族'],['维吾尔族','维吾尔族'],['苗 族','苗 族']
        ,['彝 族','彝 族'],['壮 族','壮 族'],['布依族','布依族'],['朝鲜族','朝鲜族'],['满 族','满 族'],['侗 族','侗 族']
        ,['瑶 族','瑶 族'],['白 族','白 族'],['土家族','土家族'],['哈尼族','哈尼族'],['哈萨克族','哈萨克族'],['傣 族','傣 族']
        ,['黎 族','黎 族'],['傈傈族','傈傈族'],['佤 族','佤 族'],['畲 族','畲 族'],['高山族','高山族'],['拉祜族','拉祜族']
        ,['水 族','水 族'],['东乡族','东乡族'],['纳西族','纳西族'],['景颇族','景颇族'],['柯尔克孜族','柯尔克孜族'],['土 族','土 族']
        ,['达斡尔族','达斡尔族'],['仫佬族','仫佬族'],['羌 族','羌 族'],['布朗族','布朗族'],['撤拉族','撤拉族'],['毛难族','毛难族']
        ,['仡佬族','仡佬族'],['锡伯族','锡伯族'],['阿昌族','阿昌族'],['普米族','普米族'],['塔吉克族','塔吉克族'],['怒 族','怒 族']
        ,['乌孜别克族','乌孜别克族'],['俄罗斯族','俄罗斯族'],['鄂温克族','鄂温克族'],['崩龙族','崩龙族'],['保安族','保安族'],['裕固族','裕固族']
        ,['京 族','京 族'],['塔塔尔族','塔塔尔族'],['独龙族','独龙族'],['鄂伦春族','鄂伦春族'],['赫哲族','赫哲族'],['门巴族','门巴族']
        ,['珞巴族','珞巴族'],['基诺族','基诺族']]
    });
    
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">高管详细信息</span>',
		width: '100%',
	    height:600,
		items: [{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
                             xtype:'textfield',
                             fieldLabel: '*姓名',
                             maxLength:20,
                              labelStyle: 'text-align:right;',
                             name: 'GGNAME_ZH',
                             anchor:'90%'
                         }, /*{
                             xtype:'textfield',
                             fieldLabel: '高管编号',
                              labelStyle: 'text-align:right;',
                             name: 'MANAGER_ID',
                             anchor:'90%'
                         },*/ {
                             xtype:'numberfield',
                             minValue:0,allowNegative:false,
                             allowDecimals:false,
							 fieldLabel: '担任职务时间(年)',
                             maxLength:6,
							 name: 'POST_TM',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
							xtype:'datefield',
							 format:'Y-m-d', //日期格式化
							fieldLabel:'证件有效期起始日',
                            editable:false,
							 labelStyle: 'text-align:right;',
							name:'CRET_BGNDT',
							anchor:'90%'
						 
						 },{
                             fieldLabel : '是否我行个人客户',
                             name: 'PER_FLG' ,
                             //editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['是','是'],['否','否']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         },{
							 xtype:'textfield',
							 fieldLabel: '个人爱好',
							 maxLength:100,
							 name: 'PER_LIKE',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 fieldLabel: '民族',
							 name: 'NATION',
                             //editable:false,
							 forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:mzStore,
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
						 }, {
							 xtype:'textfield',
							 maxLength:40,
							 fieldLabel: '毕业院校',
							 name: 'SCHOOL',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '主要经济来源',
							 maxLength:40,
							 name: 'RCV_FROM',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 maxLength:100,
							 fieldLabel: '工作地址',
							 name: 'OFFICEADDR',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
						     xtype:'numberfield',
						     vtype:'telephone',
							 fieldLabel: '家庭电话',
							 name: 'FAMILY_TEL',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 vtype:'alpha',
                             xtype:'textfield',
							  fieldLabel: '英文名',
							  allowBlank:true,
							   labelStyle: 'text-align:right;',
							 name: 'GGNAME_EN',
//                             vtype:'alphaText',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '负责业务类型',
							 name: 'FZ_TYP',
							 maxLength:40,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
							 xtype:'datefield',
                             editable:false,
							 format:'Y-m-d', //日期格式化
							 fieldLabel: '证件有效期到期日',
							 name: 'CRET_ENDDT',
							 labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '营销主要关键要素',
							 maxLength:100,
							  labelStyle: 'text-align:right;',
							 name: 'WK_IMT',
							 anchor:'90%'
						 }, {
						     fieldLabel: '国籍',
                             name: 'COUNTRY',
                             //editable:false,
                             store: gjStore,
                             xtype : 'combo',
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '宗教信仰',
							 maxLength:40,
							  labelStyle: 'text-align:right;',
							 name: 'RELIGION',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '职称',
							 maxLength:50,
							  labelStyle: 'text-align:right;',
							 name: 'STAFF',
							 anchor:'90%'
						 }, {
							 xtype:'textarea',
							 fieldLabel: '其他经济来源',
							 maxLength:40,
							 name: 'RCV_FROM_OTHER',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '家庭地址',
							 maxLength:100,
							  labelStyle: 'text-align:right;',
							 name: 'FAMILYADDR',
							 anchor:'90%'
						 }, {
						     xtype:'numberfield',
					            minValue:0,allowNegative :false,
					            allowDecimals:false,
							 fieldLabel: '*家庭供养人口',
							 maxLength:6,
							  labelStyle: 'text-align:right;',
                              allowBlank:false,
							 name: 'NURTUREPER',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
                             fieldLabel : '*是否法人',
                             name: 'FR_FLG' ,
                             allowBlank : false,
                             //editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['是','是'],['否','否']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         },{
                             store: zjlxStore,
                             xtype : 'combo',
                             name : 'CRET_TYP',
                             id:'crettype',
                             hiddenName : 'CRET_TYP',
//                             allowBlank : false,
                             fieldLabel : '*证件类型',
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             //editable:false,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '90%'
                         },{
							 xtype:'numberfield',
                             minValue:0,allowNegative:false,
                             allowDecimals:false,
                             maxLength:20,
							 fieldLabel: '现单位工作时间(年)',
							 name: 'WORK_TM',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
						     xtype:'textfield',
							 fieldLabel: '关系背景',
							 maxLength:50,
							 name: 'RELA_BJ',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'datefield',
                             editable:false,
							 format:'Y-m-d', //日期格式化
							 fieldLabel: '出生日期',
							 name: 'BIRTHDAY',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {xtype:'numberfield',
                             minValue:0,allowNegative:false,
                             allowDecimals:false,
                             fieldLabel : '婚姻状况',
                             name: 'MARRY_FLG' ,
                             //editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['未婚','未婚'],['已婚','已婚'],['离异','离异']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         }, {
                             xtype:'numberfield',
                             minValue:0,allowNegative:false,
                             allowDecimals:false,
							 fieldLabel: '相关行业从业年限(年)',
							 name: 'TRADE_YEAR',
							 maxLength:20,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '社会职务',
							 maxLength:20,
							 name: 'SH_POSITION',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
						     xtype:'numberfield',
						     vtype:'mobile',
							 fieldLabel: '移动电话',
							 name: 'MOBILE',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'textarea',
							 fieldLabel: '工作履历',
							 name: 'WORK_H',
							 maxLength:100,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*公司职务',
                             allowBlank : false,
							 maxLength:40,
							  labelStyle: 'text-align:right;',
							 name: 'POSITION',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 id:'cretno',
							 fieldLabel: '*证件号码',
							 name: 'CRET_NO',
							 vtype:'alphanum',
							 maxLength:100,
                             allowBlank : false,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
						     fieldLabel: '重要程度',
                             name: 'IMT_LEV',
                             store: zycdStore,
                             xtype : 'combo',
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             //editable:false,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '持股情况',
							 maxLength:20,
							 name: 'STOCK',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
                             fieldLabel : '性别',
                             name: 'SEX' ,
                             //editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['男','男'],['女','女']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         }, {
                             fieldLabel: '最高学历',
                             labelStyle: 'text-align:right;',
                             name: 'SCHOOL_H',
                             //editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             triggerAction:'all',
                             mode:'local',
                             store:xlStore,
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
						 }, {
						     xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
                             maxLength:8,
							 fieldLabel: '*月收入(元)',
							  labelStyle: 'text-align:right;',
							  allowBlank:false,
							 name: 'RCV_MON',
							 anchor:'90%'
						 }, {
							 vtype:'email',
							 maxLength:40,
                             xtype:'textfield',
							 fieldLabel: '邮箱地址',
							 name: 'EMAIL',
							 allowBlank:true,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
						     xtype:'numberfield',
						     vtype:'telephone',
							 fieldLabel: '办公电话',
							 maxLength:30,
							 name: 'OFFICETEL',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }, {
							 xtype:'textarea',
							 fieldLabel: '备注',
							 name: 'RMAK',
							 maxLength:2000,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
						     name:'ID',
						     id:'id',
						     xtype:'textfield',
						     hidden:true
						 },{
						     name:'CUST_ID',
						     id:'custid',xtype:'textfield',
						     hidden:true
						 }]
					 }
				]}
				]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : document.body.scrollHeight-30,
        width:document.body.scrollWidth-100,
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll:true,
		closeAction : 'hide',
		// iconCls : 'page_addIcon',
		//maximizable: true,
		//maximized:true,
		collapsible : true,// 是否可收缩
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		pageY : 20,
		//pageX : document.body.clientWidth / 2 - 420 / 2,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [panel2],
		buttons : [{ // 窗口底部按钮配置
            text : '提    交', // 按钮文本
            handler : function() { // 按钮响应函数
                if(!panel2.getForm().isValid())
                { 
                    alert('请正确填写信息');
                    return false;
                }
                if(editFlag == 1){
                    var infoRecord = grid.getSelectionModel().getSelected();
                    id=infoRecord.data.ID;
                    custid=oCustInfo.cust_id;
                    Ext.getCmp('id').setValue(id);
                    Ext.getCmp('custid').setValue(custid);
                }
                else{
                	custid=oCustInfo.cust_id;
                    Ext.getCmp('custid').setValue(custid);
                	oCustInfo.cust_id;
                }
                Ext.Ajax.request({
                    url: basepath+'/customerleader.json',
                    method: 'POST',
                    params:panel2.getForm().getFieldValues(),       
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
                addRoleWindow.hide();
            }
        },
        { // 窗口底部按钮配置
            text : '重    置', // 按钮文本
            handler : function() { // 按钮响应函数
                resetForm(panel2);
            }
        },
        { // 窗口底部按钮配置
            text : '关    闭', // 按钮文本
            handler : function() { // 按钮响应函数
                addRoleWindow.hide();
            }
        }]
	});
	var qForm = new Ext.form.FormPanel({
		renderTo:'viewport_center',
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 100,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '姓名',
											name : 'GGNAME_ZH',
											 labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										},{
										    fieldLabel: '重要程度',
				                             name: 'IMT_LEV',
				                             store: zycdStore,
				                             xtype : 'combo',
				                             labelStyle: 'text-align:right;',
				                             valueField:'key',
				                             displayField:'value',
				                             mode : 'local',
				                             typeAhead: true,
				                             forceSelection: true,
				                             triggerAction: 'all',
				                             emptyText:'请选择',
				                             selectOnFocus:true,
				                             width : '100',
				                             anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '公司职务', // 标签
											 labelStyle: 'text-align:right;',
											name : 'POSITION', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '负责业务类型', // 标签
											name : 'FZ_TYP', // name:后台根据此name属性取值
											 labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '证件号码',
									name : 'CRET_NO',
									xtype : 'numberfield', // 设置为数字输入框类型
								    labelStyle: 'text-align:right;',
									anchor : '80%'
								}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
					    var conditionStr =  qForm.getForm().getFieldValues();
                        //var conditionTstr = Ext.encode(conditionStr);
//                      alert(conditionStr);
                        store.on('beforeload', function() {
                            this.baseParams = {
                                    "condition":Ext.encode(conditionStr)
                            };
                            });
                        store.reload({
                            params : {
                                start : 0,
                                limit : bbar.pageSize
                            }
                        });
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
					}
				}]
	});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	           {header : 'id', dataIndex : 'ID',sortable : true,width : 150,hidden :true},
		       {header : '客户编号', dataIndex : 'CUST_ID',sortable : true,width : 150 }, 
		       {header : '是否法人', dataIndex : 'FR_FLG',sortable : true,width : 150 }, 
		       {header : '公司职务', dataIndex : 'POSITION',sortable : true,width : 150 }, 
				{header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 150 }, 
				{header : '证件号码', dataIndex : 'CRET_NO',sortable : true,width : 150 }, 
				{header : '高管中文名', dataIndex : 'GGNAME_ZH',sortable : true,width : 150 }, 
				{header : '高管英文名', dataIndex : 'GGNAME_EN',sortable : true,width : 150 }, 
				{header : '担任职务时间', dataIndex : 'POST_TM',sortable : true,width : 150 }, 
				{header : '负责业务类型', dataIndex : 'FZ_TYP',sortable : true,width : 150 }, 
				{header : '证件类型', dataIndex : 'CRET_TYP_GP',sortable : true,width : 150 }, 
				{header : '证件有效起始日', dataIndex : 'CRET_BGNDT',sortable : true,width : 150 }, 
				{header : '证件有效到期日', dataIndex : 'CRET_ENDDT',sortable : true,width : 150 }, 
				{header : '现单位工作时间', dataIndex : 'WORK_TM',sortable : true,width : 150 }, 
				{header : '重要程度', dataIndex : 'IMT_LEV_ORA',sortable : true,width : 150 }, 
				{header : '是否我行个人客户', dataIndex : 'PER_FLG',sortable : true,width : 150 }, 
				{header : '营销主要关键要素', dataIndex : 'WK_IMT',sortable : true,width : 150 }, 
				{header : '关系背景', dataIndex : 'RELA_BJ',sortable : true,width : 150 }, 
				{header : '持股情况', dataIndex : 'STOCK',sortable : true,width : 150 }, 
				{header : '个人爱好', dataIndex : 'PER_LIKE',sortable : true,width : 150 }, 
				{header : '国籍', dataIndex : 'COUNTRY_GP',sortable : true,width : 150 }, 
				{header : '出生日期', dataIndex : 'BIRTHDAY',sortable : true,width : 150 }, 
				{header : '性别', dataIndex : 'SEX',sortable : true,width : 150 }, 
				{header : '民族', dataIndex : 'NATION',sortable : true,width : 150 }, 
				{header : '宗教信仰', dataIndex : 'RELIGION',sortable : true,width : 150 }, 
				{header : '婚姻状况', dataIndex : 'MARRY_FLG',sortable : true,width : 150 }, 
				{header : '学历', dataIndex : 'SCHOOL_H',sortable : true,width : 150 }, 
				{header : '毕业院校', dataIndex : 'SCHOOL',sortable : true,width : 150 }, 
				{header : '职称', dataIndex : 'STAFF',sortable : true,width : 150 }, 
				{header : '相关行业从业年限', dataIndex : 'TRADE_YEAR',sortable : true,width : 150 }, 
				{header : '月收入（元）', dataIndex : 'RCV_MON',sortable : true,width : 150 ,
                    align : 'right',
                    renderer: money('0,000' )}, 
				{header : '主要经济来源', dataIndex : 'RCV_FROM',sortable : true,width : 150 }, 
				{header : '其他经济来源', dataIndex : 'RCV_FROM_OTHER',sortable : true,width : 150 }, 
				{header : '社会职务', dataIndex : 'SH_POSITION',sortable : true,width : 150 }, 
				{header : '邮箱地址', dataIndex : 'EMAIL',sortable : true,width : 150 }, 
				{header : '工作地址', dataIndex : 'OFFICEADDR',sortable : true,width : 150 }, 
				{header : '移动电话', dataIndex : 'MOBILE',sortable : true,width : 150 }, 
				{header : '办公电话', dataIndex : 'OFFICETEL',sortable : true,width : 150 }, 
				{header : '家庭住址', dataIndex : 'FAMILYADDR',sortable : true,width : 150 }, 
				{header : '家庭供养人员', dataIndex : 'NURTUREPER',sortable : true,width : 150 }, 
				{header : '工作简历', dataIndex : 'WORK_H',sortable : true,width : 150 }, 
				{header : '备注', dataIndex : 'RMAK',sortable : true,width : 150 }
			]);

	/**
	 * 数据存储
	 */
	var tempIdd = oCustInfo.cust_id;
		var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/leaderinformation.json?customerId='+tempIdd}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [{name: 'ID'},
						{name: 'CUST_ID'},
						{name: 'MANAGER_ID'},
						{name: 'FR_FLG'},
						{name: 'POSITION'},
						{name: 'CUST_NAME'},
						{name: 'CRET_NO'},
						{name: 'GGNAME_ZH'},
						{name: 'GGNAME_EN'},
						{name: 'POST_TM'},
						{name: 'FZ_TYP'},
						{name: 'CRET_TYP'},
						{name: 'CRET_TYP_GP'},
						{name: 'CRET_BGNDT'},
						{name: 'CRET_ENDDT'},
						{name: 'WORK_TM'},
						{name: 'IMT_LEV'},
                        {name: 'IMT_LEV_ORA'},
						{name: 'PER_FLG'},
						{name: 'WK_IMT'},
						{name: 'RELA_BJ'},
						{name: 'STOCK'},
						{name: 'PER_LIKE'},
						{name: 'COUNTRY'},
						{name: 'COUNTRY_GP'},
						{name: 'BIRTHDAY'},
						{name: 'SEX'},
						{name: 'NATION'},
						{name: 'RELIGION'},
						{name: 'MARRY_FLG'},
						{name: 'SCHOOL_H'},
						{name: 'SCHOOL'},
						{name: 'STAFF'},
						{name: 'TRADE_YEAR'},
						{name: 'RCV_MON'},
						{name: 'RCV_FROM'},
						{name: 'RCV_FROM_OTHER'},
						{name: 'SH_POSITION'},
						{name: 'EMAIL'},
						{name: 'OFFICEADDR'},
						{name: 'MOBILE'},
						{name: 'OFFICETEL'},
						{name: 'FAMILYADDR'},
						{name: 'NURTUREPER'},
						{name: 'WORK_H'},
						{name: 'RMAK'},
						{name: 'CRM_DT'},
						{name: 'FAMILY_TEL'}
					])
				});
	
	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
                    text : '新增',
                    handler : function() {
                        addInit();
                    }},'-',
                    {
                    text : '修改',
                    handler : function() {
                        editInit();
                    }},'-',
                    {
                    text : '删除',
                    handler : function() {
                        deleteInit();
                    }},'-',{
					text : '高管家庭成员信息',
					
					handler : function() {
						familyInit();
					}
				}]
			});

	// 每页显示条数下拉选择框
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '100',
        editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
    pagesize_combo.on("select", function(comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
        store.reload({
            params : {
                start : 0,
                limit : bbar.pageSize
            }
        });
    });
    // 分页工具栏
    var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });

	// 表格实例
	var grid = new Ext.grid.GridPanel({
		renderTo:'viewport_center',
        height : document.body.scrollHeight-130,
        width:document.body.scrollWidth,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	/*grid.on('rowdblclick', function(grid, rowIndex, event) {
		 window.location.href = 'customerViewIndex.html' ;
	});*/
	//拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        grid.setHeight(document.body.scrollHeight-130);
        grid.setWidth(document.body.scrollWidth);
        grid.getView().refresh();
    });

//	// 布局模型
//	var viewport = new Ext.Viewport({
//	    layout:'fit',
//        items:{
//
//				layout : 'border',
//				items: [{   
//					region: 'north',
//				    id: 'north-panel',
//				    title: "客户统一视图->高管信息", 
//				    height: 128,
//				    hidden:false,
//				    margins: '0 0 0 0',
//					items:[qForm]
//			     },{   
//			    	region:'center',
//				    id: 'center-panel',
//				    margins: '0 0 0 0',
//				    items : [grid]
//			    }] 
//        }
//			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	
	function editInit(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
        editFlag = 1;
        var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            panel2.getForm().loadRecord(infoRecord);
            addRoleWindow.show();
        }}
    }
    function addInit(){
        editFlag = 0;
        resetForm(panel2);
        Ext.getCmp('id').setValue('');
        Ext.getCmp('custid').setValue(parent.location.href.split("customerId=")[1]);
        addRoleWindow.show();  
    }
    function deleteInit(){
        /****************************************************************************************/
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } 
        
        else {
            if(confirm("确定删除吗?"))
            {
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = grid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/customerleader/'
                        +tempId+'.json?idStr='+idStr,
                method : 'DELETE',        
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : checkResult,
                failure : checkResult
            });
            
            };

    }
        /****************************************************************************************/
    }
	
	function familyInit(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
	    var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
	    var managerId=infoRecord.get('ID');//用高管信息的ID与其家庭成员的MANAGER_ID关联
//	    alert(managerId);
		window.location.href = 'familyMemberInformation.jsp?managerId='+managerId ;
        }}
	}
	
	function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            store.load({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            store.load({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
    }
	
	store.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });

    /**********************************************************/
    function resetForm(form){
        var resetObj;
        if(typeof form == 'string'){
            resetObj = Ext.getCmp(form);
        }else resetObj = form;
        
        if(resetObj == undefined){
            alert('debug:the formPanel has not been defined!');
            return false;
        }
        
        
        
        if(resetObj.getXType() != 'form'){
            alert('debug:the Obj is not a FormPanel!');
            return false;
        }
        
        Ext.each(resetObj.getForm().items.items,function(f){
            
            f.setValue('');
           // f.originalValue = '';
        });
    }
    /**********************************************************/
    
    var cb = Ext.getCmp('crettype');
    var cb1 = Ext.getCmp('cretno');
    cb.addListener('select',function(){
        if(cb.getValue()=='CRM_CMRZJLX_001'){
            cb1.vtype='IDCard';
        }else{
            cb1.vtype='alphanum';
        }
    });
}); 