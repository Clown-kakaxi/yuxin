Ext.onReady(function() {//Ext入口函数，相当于java中的主函数
	Ext.QuickTips.init();
	/*
	 * 查询条件Form定义
	 */
	var qForm = new Ext.form.FormPanel({
		region: 'north',						//和VIEWPORT布局模型对应，渲染到north区域
	    title: "查询form", 
	    height: 120,							//form面板高度
		labelWidth : 90, 						//标签宽度
		frame : true, 							//*渲染表单面板背景色
		labelAlign : 'middle', 					//*标签对齐方式
		buttonAlign : 'center',					//*按钮位置
		layout : 'column',						//*列布局
		border : false,							//*True表示为显示出面板body元素的边框，false则隐藏（缺省为true），默认下，边框是一套2px宽的内边框，但可在bodyBorder中进一步设置。
		items : [{
			columnWidth : .25,					//列宽为25%
			layout : 'form',					//*form布局
			labelWidth : 70, 					//*标签宽度
			items : [{ //下拉框
				xtype : 'combo',
				hiddenName : 'CUST_TYP',   		//传到action中的key的参数名，若hiddenName=name,则只传key值
				fieldLabel : '客户类型',
				name:'CUST_TYP',      			//传到action中的value的参数名
				store : custTypeStore,			//*加载客户类型Store
				//下列属性在复用时一般不作修改
				editable : false,				//false为不可编辑，页面效果为点击下拉框空白处直接触发下拉菜单
				labelStyle : 'text-align:right;',//fieldLabel右对齐
				triggerAction : 'all',			//*触发器被激活时执行allQuery查询
				displayField : 'value',			//*下列列表选项的中文名，对应Store中的value
				valueField : 'key',				//*下列列表选项对应的key值，对应Store中的key
				mode : 'local',					//*读取本地数据
				emptyText:'请选择',				//*不选择时默认显示的文本
				resizable : false,				//*下拉框下部的缩放柄，可改变下拉框大小
				anchor : '90%'
			}]				
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth: 90, 		
			border : false,
			items : [orgUserManage]				//引用客户经理放大镜
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, 					// 标签宽度
			border : false,	
			items : [orgField]					//引用机构放大镜
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, 					// 标签宽度
			border : false,	
			items : [search_cust]					//引用客户放大镜
		},{
			columnWidth : .25,					//列宽为25%
			layout : 'form',					//*form布局
			labelWidth : 70, 					//*标签宽度
			items : [{
				fieldLabel : '客户号',
				name : 'CUST_ID',				//传到action中的value的参数名
				xtype : 'numberfield', 			// 设置为数字输入框类型
				labelStyle : 'text-align:right;',//右对齐
				anchor : '90%'
			}]
		},{
			columnWidth : .25,					//列宽为25%
			layout : 'form',					//*form布局
			labelWidth : 70, 					//*标签宽度
			items : [{
				fieldLabel : '日期',
				name : 'startDate',				//传到action中的value的参数名
				xtype : 'datefield', 			// 设置为数字输入框类型
				format : 'Y-m-d',
				editable : false,
				labelStyle : 'text-align:right;',//右对齐
				anchor : '90%'
			}]
		}],
		buttons : [{
			text : '查询',
			handler : function() {
				var parameters = qForm.getForm().getValues(false);
				store.baseParams = {
					'condition':Ext.util.JSON.encode(parameters)//将查询条件参数及值转化成JSON格式，并赋给condition
				};
				store.load({      
					params : {
                       start : 0,				//*分页查询的参数
                       limit : bbar.pageSize   	//*分页查询的参数
                    }
				});     
		   }
		},{
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
				Ext.getCmp('CUST_MANAGER').setValue('');  //重置客户经理放大镜
				Ext.getCmp('CUST_ORG').setValue('');	  //重置机构放大镜
			}
		}]
	});
	
	//列模型中dataIndex对应此处的name,mapping对应JDBC查询结果返回的字段名
	var record = Ext.data.Record.create([
	    {name: 'custId',mapping :'CUST_ID'},
		{name: 'custZhName',mapping :'CUST_ZH_NAME'},
		{name: 'CERT_TYPE_ORA'},
		{name: 'CUST_STAT_ORA'},
		{name: 'CUST_TYP_ORA'},
		{name: 'CUST_LEV_ORA'},
		{name: 'certType',mapping: 'CERT_TYPE'},
		{name: 'custStat',mapping: 'CUST_STAT'},
		{name: 'custTyp',mapping: 'CUST_TYP'},
		{name: 'custLev',mapping: 'CUST_LEV'},
		{name: 'INSTITUTION_CODE'},
		{name: 'INSTITUTION_NAME'},
		{name: 'MGR_ID'},
		{name: 'MGR_NAME'},
		{name: 'custEnName',mapping :'CUST_EN_NAME'},//英文名
		{name: 'otherName',mapping :'OTHER_NAME'},//其他名
		{name: 'certNum',mapping :'CERT_NUM'},//证件号码
		{name: 'linkPhone',mapping :'LINK_PHONE'},//联系电话
		{name: 'postNo',mapping :'POST_NO'},//邮编
		{name: 'addr',mapping :'ADDR'},//地址
		{name: 'linkUser',mapping :'LINK_USER'}//联系人
	]);
	/**
	 * 客户查询结果数据存储
	 */
	var store = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({url:basepath+'/customerBaseInformation.json'}),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, record)
	});
	
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
        {header : '客户号',dataIndex : 'custId',sortable : true/*表示为可在该列上进行排列*/,width : 150},
	    {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true},
	    {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',width : 150,sortable : true},
	    {header : '证件号码',dataIndex : 'certNum',width : 150,sortable : true},
	    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 150,sortable : true},
	    {header : '客户类型',dataIndex : 'custTyp',width : 200,sortable : true,hidden:true},
	    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 200,sortable : true},
	    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 200,sortable : true},
	    {header : '主办机构',dataIndex : 'INSTITUTION_CODE',hidden : true,sortable : true},
	    {header : '主办客户经理',dataIndex : 'MGR_ID',width : 150,hidden : true,sortable : true},
	    {header : '主办机构',dataIndex : 'INSTITUTION_NAME',hidden : true,sortable : true},
	    {header : '主办客户经理',dataIndex : 'MGR_NAME',width : 150,hidden : true,sortable : true}
	]);
	
	// 表格工具栏
	var tbar = ['所属机构：',new Com.yucheng.bcrm.common.OrgField({
		id : 'custOrg', 			//放大镜组件ID，用于在重置清空时获取句柄
		searchType:'SUBTREE',		/*指定查询机构范围属性 SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
//		fieldLabel : '所属机构',
		labelStyle : 'text-align:right;',
		name : 'CUST_ORG', 			//后台获取的机构名称对应的参数名
		hiddenName: 'instncode',    //后台获取机构号的参数名称
		checkBox:true ,				//复选标志
		callback : function(){
	    	var custOrg ="";
	        for (i=0 ; i<this.Id.length ; i++){
	        	if (i==0){
	        		custOrg=this.Id[i];
	        	}else{
	        		custOrg = custOrg + ","+this.Id[i];
	        	}
	        }
	        this.Id=custOrg;
		},
		anchor : '90%'
	}), {
        text : '查询',
        handler : function() {
            store.load( {
                params : {
            		'custOrg' : Ext.getCmp('custOrg').Id,
                    start : 0,
                    limit : parseInt(pagesize_combo.getValue())
                }
            });
        }
    },'-',{
		text : '新增',
		iconCls : 'addIconCss',   //定义图标
		handler : function(){	  //调用新增方法
		debugger;
			myForm.getForm().reset();
			myWindow.show();
		}
	}, '-', {
    		text : '修改',
			iconCls : 'editIconCss',
			handler : function() { // 调用修改方法
				if(grid.selModel.hasSelection()){
					var _records = grid.getSelectionModel().selections;// 得到被选择的行的数组
					if (_records.length>1) {
						Ext.MessageBox.alert('系统提示信息', '请选择其中一条记录进行修改！');
						return false;
					} else {
					
					}
				}else{
					Ext.Msg.alert("提示", "请先选择一条要修改的记录!");
				}
			}
		}, '-', {
			text : '删除',
			iconCls : 'deleteIconCss',
			handler : function() { 
				if(grid.selModel.hasSelection()){
					var _records = grid.getSelectionModel().selections;// 得到被选择的行
					if (_records.length>1) {
						Ext.MessageBox.alert('系统提示信息', '请选择其中一条记录进行删除！');
						return false;
					} else {
						if (confirm("确定删除吗?")) {
							var idStr = _records.items[0].data.id;
							Ext.Ajax.request({
								url : basepath	+ '/FwModule-action!destroy.json?idStr=' + idStr,
								waitMsg : '正在删除数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('系统提示信息', '操作成功');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('系统提示信息', '操作失败');
								}
							});
						}
					}
				}else{
					Ext.Msg.alert("提示", "请先选择一条要删除的记录!");
				}
			}
		}, '-', {
			text : '详情', 
			iconCls : 'detailIconCss',
			handler : function() { 
			if(grid.selModel.hasSelection()){
				var _records = grid.getSelectionModel().selections;// 得到被选择的行的数组
				if (_records.length>1) {
					Ext.MessageBox.alert('系统提示信息', '请选择其中一条记录进行查看！');
					return false;
				} else {
					
				}
			}else{
				Ext.Msg.alert("提示", "请先选择一条要查看的记录!");
			}
		}
	}];
	
	//分页工具栏
	var bbar = new Ext.PagingToolbar({
	   pageSize : parseInt(pagesize_combo.getValue()),
	   store : store,
	   displayInfo : true,
	   displayMsg : '显示{0}条到{1}条,共{2}条',
	   emptyMsg : "没有符合条件的记录",
	   items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
		title:'查询结果grid列表(实际开发时一般删除此title属性)',
		frame : true,			//是否渲染表单面板背景色
		autoScroll : true,		//允许滚动条
	    layout:'fit',			//fit布局，使该组件占据父容器剩余全部空间
		region : 'center', 		// 和VIEWPORT布局模型对应，渲染到center区域
		store : store,		 	// 数据存储
		stripeRows : true, 		// 斑马线
		cm : cm, 				// 列模型
		sm : sm, 				// 复选框
		tbar : tbar, 			// 表格工具栏
		bbar : bbar,			// 分页工具栏
		viewConfig:{			//作用在grid's UI试图上的配置项对象， 任何Ext.grid.GridView可用的配置选项都可在这里指定。若view已指定则此项无效。
			forceFit:false     	//True表示为自动展开/缩小列的宽度以适应grid的宽度，这样就不会出现水平的滚动条。
		},
		loadMask : {			//True表示为当grid加载过程中，会有一个Ext.LoadMask的遮罩效果。默认为false。
			msg : '正在加载表格数据,请稍等...'
		},
		listeners : {//监听双击事件
			dblclick : function(){
			}
		}
	});
   
	/*****************************************   布局模型  **************************************************/
	var viewport = new Ext.Viewport({
	    layout:'fit',
	    items:[{
			layout : 'border',
			items: [qForm,grid]
	    }]
	});
}); 

/*********************************  放大镜/下拉列表/Store  ******************************************/

/**
 * 客户类型Store(查询数据字典)
 * 一般复用时只需修改url后的参数name的值
 */
var custTypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,						//页面加载时自动load数据
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000080'//name=字典种类ID
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ]),
	sortInfo: {				//按照key值排序，ASC:升序,DESC:降序
    	field: 'key',
    	direction: 'ASC' 	// or 'DESC' (case sensitive for local sorting)
	}
});

/*
 * 客户经理放大镜
 */
var orgUserManage = new Com.yucheng.crm.common.OrgUserManage({ 
	id:'CUST_MANAGER',            	//定义id，便于重置时做清空处理
	name : 'CUST_MANAGER',		  	//后台获取客户经理姓名对应的参数名
	hiddenName:'custMgrId',		 	 //后台获取的客户经理编号对应的参数名，hiddenName==name时只传编号
	fieldLabel : '所属客户经理', 
	searchRoleType:('127,47'),    	//指定查询角色属性 ,默认全部角色
	searchType:'SUBTREE',	     	/*允许空，默认辖内机构用户，指定查询机构范围属性 SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
	labelStyle: 'text-align:right;',//fieldLabel右对齐
	singleSelect:false,			//true只能单选,false为多选，有复选框
	anchor : '90%'
	});

/*
 * 机构选择放大镜
 */
var orgField = new Com.yucheng.bcrm.common.OrgField({
	id : 'CUST_ORG', 					//放大镜组件ID，用于在重置清空时获取句柄
	searchType:'SUBTREE',				/*指定查询机构范围属性 SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
	fieldLabel : '所属机构',
	labelStyle : 'text-align:right;',	//fieldLabel右对齐
	name : 'CUST_ORG', 					//后台获取的机构名称对应的参数名
	hiddenName: 'instncode',    		//后台获取机构号的参数名称；目前此放大镜name等于hiddenName，后台将获取不到机构号【2013-05-17】
	checkBox:true ,						//复选标志
	anchor : '90%'
});

/*
 * 客户放大镜
 */
var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({
    fieldLabel: '客户姓名',
    labelStyle: 'text-align:right;',
    labelWidth: 100,
    name: 'custName',
    id: 'rel_cust_name',
//	custtype :'1',						//客户类型：  1：对私, 2:对公,  不设默认全部
//	custStat:'1',						//客户状态: 1:正式 2：潜在     , 不设默认全部
    singleSelected: false,				//单选复选标志
    editable: false,					//不允许编辑
    allowBlank: false,  				//不允许为空
    blankText: "不能为空，请填写",
//  callback: function(a,b) { },	//
    anchor: '95%'

});


//定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

//定义复选框
var sm = new Ext.grid.CheckboxSelectionModel();

/***********************************分页工具栏***********************************************/
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
    value : '20',
    editable : false,
    width : 85
});
pagesize_combo.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo.getValue()),
	store.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
});
/***********************************新增/修改/查询Form及Window***********************************************/

var myForm =  new Ext.form.FormPanel({
	frame : true,
	labelAlign : 'right',
	buttonAlign : "center",
//	layout:'fit',
	items : [{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				labelWidth : 60,
				layout : 'form',
				items : [new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '负责人', 
					id:'burdenUser_1',
					labelStyle: 'text-align:right;',
					name : 'burdenUser',
					hiddenName:'PUBLISHER',
					//searchRoleType:('127,47'),  //指定查询角色属性
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:true,
					anchor : '90%'
					})]
			},{
				columnWidth : .5,
				labelWidth : 60,
				layout : 'form',
				items : [ new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '协办人', 
					id:'assistUser_1',
					labelStyle: 'text-align:right;',
					name : 'assistUser',
					hiddenName:'PUBLISHER',
					//searchRoleType:('127,47'),  //指定查询角色属性
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:true,
					anchor : '90%'
					})]
			}]
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				labelWidth : 60,
				layout : 'form',
				items : {
					id : 'start_Date',
					fieldLabel : '开始时间',
					xtype : 'datefield',
					format : 'Y-m-d',
					editable : false,
					name : 'startDate',
					anchor : '90%'
				}
			}, {
				columnWidth : .5,
				labelWidth : 60,
				layout : 'form',
				items : {
					id : 'end_Date',
					xtype : 'datefield',
					resizable : true,
					// allowBlank : false,
					fieldLabel : '结束时间',
					name : 'endDate',
					format : 'Y-m-d',
					editable : false,
					anchor : '90%'
				}
			}, {// 特别注意：须放置隐藏域的主键
						name : 'id',
						xtype : 'hidden'
					} ]
		}, {
			layout : 'form',
			columnWidth : .5,
			labelWidth : 60,
			items : [ {
				id : 'taskContent_1',
				name : 'taskContent',
				fieldLabel : '工作安排',
				xtype : 'textarea',
				width : 200,
				allowBlank : false,
				maxLength : 400,
				anchor : '95%'
			} ]
		}]	 
	
});
var myWindow = new Ext.Window({// 定义修改窗口
    title : '修改窗口',
    width : 600,
    draggable : true,//可拖动，默认为false
    closable : true,
    closeAction : 'hide',
    modal : true, // 对其后面的一切内容进行灰显
    border : false,//隐藏面板body元素的边框
    items : [myForm]
});



















