
Ext.ns('Ext.ux.form');
Ext.ux.form.TeamManagerField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    singleSelected:false,
    callback:false,
    unitId:'',
    unitName:'',
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',
    
    onTrigger2Click : function(){
    	var oThisSearchField=this;
		
		var record = Ext.data.Record.create([
		         {name: 'id', mapping: 'ID'},
		         {name: 'userId', mapping: 'USERID'},   
		         {name: 'userName', mapping: 'USERNAME'},
		         {name: 'unitid', mapping: 'UNITID'},
		         {name: 'UNITNAME', mapping: 'UNITNAME'}
			     ]);
			
		 var Sstore = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/addTeamleaderQuery.json'
		        }),
		       reader: new Ext.data.JsonReader({
		       totalProperty : 'json.count',
		        root:'json.data'
		        }, record)
			});
			 // 复选框
			var mysm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
						header : 'No.',
						width : 28
					});
				// 定义列模型
				var mycm = new Ext.grid.ColumnModel([rownum,
				        {header : 'ID',dataIndex : 'id',width : 250,sortable : true,hidden :true},
				        {header : '用户工号',dataIndex : 'userId',sortable : true,width : 200},
				        {header : '用户姓名',dataIndex : 'userName',sortable : true,width : 200},
				        {header : '归属机构编号',dataIndex : 'unitid',sortable : true,width : 200,hidden :true},
				        {header : '归属机构名称',dataIndex : 'UNITNAME',sortable : true,width : 200}
//				        {header : 'id', dataIndex : 'id',sortable : true,width : 150,sortable : true,hidden :true}
						]);
				
				
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
			         forceSelection : true,
			         width : 85
			     });
			    var number = parseInt(pagesize_combo.getValue());
			    pagesize_combo.on("select", function(comboBox) {
			    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
					Sstore.load({
								params : {
									start : 0,
									limit : parseInt(pagesize_combo.getValue())
								}
							});
				});
				var mybbar = new Ext.PagingToolbar({
			        pageSize : number,
			        store : Sstore,
			        displayInfo : true,
			        displayMsg : '显示{0}条到{1}条,共{2}条',
			        // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			        emptyMsg : "没有符合条件的记录",
			        items : ['-', '&nbsp;&nbsp;', pagesize_combo
			                 ]
			    });
				
			// 表格实例
			var myTestGrid = new Ext.grid.GridPanel({
						height :300,
						width : 780,
						frame : true,
						autoScroll : true,
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						store : Sstore, // 数据存储
						stripeRows : true, // 斑马线
						cm : mycm, // 列模型
						sm : mysm, // 复选框
						bbar:mybbar,
						viewConfig:{
							   forceFit:false,
							   autoScroll:true
							},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			
			var qForm = new Ext.form.FormPanel({
				labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'right',
				region:'north',
				// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
				buttonAlign : 'center',
				height : 100,
				width: 780,
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '用户工号',
							name : 'USERID',
							anchor : '95%'
						} ]
					},
					{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '用户姓名',
							name : 'USERNAME',
							anchor : '95%'
					}]
				}],
			buttons : [{
						text : '查询',
						handler : function() {
							var conditionStr = qForm.getForm().getFieldValues();
							Sstore.baseParams = {
									"condition" : Ext.encode(conditionStr)
								};
							Sstore.reload({
								  params : {
	                                   start : 0,
	                                   limit : mybbar.pageSize }} );
					
					   }},{
						text : '重置',
							handler : function() {
								qForm.getForm().reset();
							}
						}]
			});
			
	var oCustomerQueryWindow=new Ext.Window({
		title : '团队负责人列表',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height:400,
		width:800,
		draggable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口 
		//animCollapse : false,
		border : false,
		//maximized:true,
		//maximizable: true,
		autoScroll : true,
		autoHeight : true,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [{
			layout:'form',
			items:[qForm,myTestGrid]
		}],
		buttonAlign:'center',
		buttons:[{
			text:'确定',
				handler:function()
				{
			debugger;
			var json={'aId':[]};
			var json1={'unitName':[]};
			var json2 = {'userId' : []};
			var sName='';
			var checkedNodes = myTestGrid.getSelectionModel().selections.items;
			if(oThisSearchField.singleSelected&&checkedNodes.length>1)
			{
				Ext.Msg.alert('提示', '您只能选择一个团队负责人');
				return ;
			}
			
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.aId.push(checkedNodes[i].data.unitid);
				json1.unitName.push(checkedNodes[i].data.UNITNAME);
				json2.userId.push(checkedNodes[i].data.userId);
				if(i==0){
					sName=sName+checkedNodes[i].data.userName;
				}
				else{
					sName=sName+','+checkedNodes[i].data.userName;
				}
			}
				oThisSearchField.unitId=json;
				oThisSearchField.unitName=json1;
				oThisSearchField.userId=json2;
				oThisSearchField.setValue(sName);
				oCustomerQueryWindow.hide();
			if (typeof oThisSearchField.callback == 'function') {
					 oThisSearchField.callback();
				   
				      }
				}
			},{
			text: '取消',
				handler:function(){
				oCustomerQueryWindow.hide();
					}
					}]	
		});
	Sstore.load();
	oCustomerQueryWindow.show();
    return;
    }
});