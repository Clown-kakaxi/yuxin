/**
 * 放大镜查询集团
 * @author:sunjing5
 * @since:2017.06.07
 */
Ext.ns('Com.yucheng.bcrm.common');
Com.yucheng.bcrm.common.Group = Ext.extend(Ext.form.TwinTriggerField, {
	initComponent : function(){
		Com.yucheng.bcrm.common.Group.superclass.initComponent.call(this);
	},

	onRender : function(ct, position){
		Com.yucheng.bcrm.common.Group.superclass.onRender.call(this, ct, position);
		if(this.hiddenName){
			var ownerForm = this;
			while(ownerForm.ownerCt && !Ext.instanceOf(ownerForm.ownerCt,'form')){				//根据条件查询放大镜控件的最外层容器
				ownerForm = ownerForm.ownerCt;
			};
			if(Ext.instanceOf(ownerForm.ownerCt,'form')){										//判断父容器是否为form类型
				ownerForm = ownerForm.ownerCt;
				if(ownerForm.getForm().findField(this.hiddenName)){								//如果已经创建隐藏域
					this.hiddenField = ownerForm.getForm().findField(this.hiddenName);
				}else {																			//如果未创建隐藏域，则根据hiddenName属性创建隐藏域
					this.hiddenField = ownerForm.add({
						xtype : 'hidden',
						id:this.hiddenName,
						name: this.hiddenName
					});
				}
			}
		}
	},
	hiddenName:false, 
	singleSelect:'',
	callback:false,
	groupNo:'',
	groupName:'',
	validationEvent:false,
	validateOnBlur:false,
	trigger1Class:'x-form-clear-trigger',
	trigger2Class:'x-form-search-trigger',
	hideTrigger1:true,
	width:180,
//	searchType:'SUBTREE',//默认查询辖内机构
	searchGroupType:'',//默认查询集团类型
	searchGroupName:'',//默认查询集团名称
	hasSearch : false,
	paramName : 'query',
	listeners:{//增加鼠标点击放大镜输入框触发onTrigger2Click事件
		focus:function(){
			if(!this.disabled){ //禁用的放大镜不允许弹出选择
				this.onTrigger2Click();
			}
		}
	},
	editable:false,
	onTrigger2Click : function(){
		if(this.disabled){ //禁用的放大镜不允许弹出选择
			return;
		}
		var _this= this;
		if(_this.groupWindow){
			_this.groupWindow.show();
			return;
		}
		var searchFunction = function(){
			var parameters = _this.groupSearchPanel.getForm().getFieldValues();
			_this.searchGroupId= parameters.group_no;
			_this.searchGroupName=parameters.group_name;
			_this.GroupInfoStore.removeAll();
			_this.GroupInfoStore.baseParams = {
				'group_no':_this.searchGroupId,
//				'searchType':_this.searchType,
				'group_name':_this.searchGroupName,
				'condition':Ext.util.JSON.encode(parameters)
			};
			_this.GroupInfoStore.load({
				params:{
					start:0,
					limit: parseInt(_this.pagesize_combo.getValue())
				}
			});
	
		};
		var searchField = _this;
		//集团类型
		this.typeStore=new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			sortInfo : {
		            field:'key',
		            direction:'ASC'
		        },
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000106'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});	    		
		_this.groupSearchPanel = new Ext.form.FormPanel({//查询panel
			title:'集团查询',
			height:110,
			labelWidth:100,//label的宽度
			labelAlign:'right',
			frame:true,
			autoScroll : true,
			region:'north',
			split:true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,
					layout:'form',
					items:[{
						xtype:'textfield',
						name:'group_name',
						fieldLabel:'集团名称 ',
						anchor:'90%'
					}]
				},{
					columnWidth:.5,
					layout:'form',
					items:[{
						store : _this.typeStore,
						xtype : 'combo',
						name : 'group_type',
						hiddenName : 'GROUP_TYPE',
						labelStyle: 'text-align:right;',
						fieldLabel : '集团类型',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						editable:false,
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						anchor : '90%',
						hidden:true
					},{
						xtype:'textfield',
						name:'group_no',
						fieldLabel:'集团编号 ',
						anchor:'90%'
					}]
				}]
			}],
			buttonAlign:'center',
			buttons:[{
				text:'查询',
				handler:searchFunction
			},{
				text:'重置',
				handler:function(){
					_this.groupSearchPanel.getForm().reset();
					_this.GroupInfoStore.load({
						params:{
							start:0,
							limit: parseInt(_this.pagesize_combo.getValue())
						}
					});
				}
			}]
		});
		//复选框
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect:_this.singleSelect
		});
		// 定义自动当前页行号
		_this.rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		_this.orgUserInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
		    _this.rownum,sm,
		    {header:'ID',dataIndex:'id',id:'id',width:100,sortable : true,hidden : true},
		    {header:'集团类型',dataIndex:'groupType',id:"groupType",width:100,sortable : true,hidden:true},
		    {header:'集团名称',dataIndex:'groupName',id:'groupName',width:160,sortable : true},
		    {header:'集团状态',dataIndex:'groupStatus',id:'groupStatus',width:100,sortable : true,hidden:true},	
		    {header:'集团母公司ID',dataIndex:'groupRootCustId',id:'groupRootCustId',width:160,sortable : true,hidden:true},
		    {header:'集团编号',dataIndex:'groupNo',id:"groupNo",width:140,sortable : true},
		    {header:'集团所属机构',dataIndex:'gaoOrg',id:'gaoOrg',width:140,sortable : true},
		    {header:'集团所属机构名称',dataIndex:'gaoOrgName',id:'gaoOrgName',width:140,sortable : true}
		]);
		_this.GroupInfoRecord = new Ext.data.Record.create([
		    {name:'id',mapping:'ID'},
		    {name:'groupType',mapping:'GROUP_TYPE'},
		    {name:'groupName',mapping:'GROUP_NAME'},
		    {name:'groupStatus',mapping:'GROUP_STATUS'},
		    {name:'groupRootCustId',mapping:'GROUP_ROOT_CUST_ID'},
		    {name:'groupNo',mapping:'GROUP_NO'},
		    {name:'gaoOrg',mapping:'GAO_ORG'},
		    {name:'gaoOrgName',mapping:'ORG_NAME'}
		]);
		_this.GroupInfoReader = new Ext.data.JsonReader({//读取json数据的panel
			totalProperty:'json.count',
			root:'json.data'
		}, _this.GroupInfoRecord);
		
		_this.groupProxy = new Ext.data.HttpProxy({
			url:basepath+'/AcrmFCiGroup.json'
		});
		_this.GroupInfoStore = new Ext.data.Store({
			restful : true,
			baseParams:{
//				'group_type':this.searchGroupType,
//				'group_name':this.searchGroupName
			},
			proxy : _this.groupProxy,
			reader :_this.GroupInfoReader,
			recordType: _this.GroupInfoRecord
		});
		// 每页显示条数下拉选择框
		_this.pagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				         [ 100, '100条/页' ], [ 250, '250条/页' ],
				         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			forceSelection : true,
			width : 85
		});
			
		var number = parseInt(_this.pagesize_combo.getValue());
		_this.pagesize_combo.on("select", function(comboBox) {
			_this.bbar.pageSize = parseInt(_this.pagesize_combo.getValue()),
			_this.GroupInfoStore.load({
				params : {
					start : 0,
					limit : parseInt(_this.pagesize_combo.getValue())
				}
			});
		});
		_this.bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : _this.GroupInfoStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', _this.pagesize_combo]
		});
		_this.groupInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
			frame:true,
			autoScroll : true,
			bbar:_this.bbar,
			stripeRows : true, // 斑马线
			store:_this.GroupInfoStore,
			loadMask:true,
			cm :_this.orgUserInfoColumns,
			sm :sm,
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
			
		_this.groupWindow=new Ext.Window({
			title : '集团管理',
			closable : true,
			plain : true,
			resizable : false,
			collapsible : false,
			height:400,
			width:800,
			draggable : false,
			closeAction : 'hide',
			modal : true, // 模态窗口 
			border : false,
			autoScroll : true,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			layout:'border',
			buttonAlign:'center',
			items:[{
					region:'center',
					layout:'border',
					items:[_this.groupSearchPanel,
					    {
							region:'center',
							layout:'fit',
							items:[_this.groupInfoGrid]
					    }]				
			    }],
			buttons:[{ 
				text : '选定',
				handler : function() {
					var checkedNodes = _this.groupInfoGrid.getSelectionModel().selections.items;
					if(_this.singleSelect && checkedNodes.length > 0) {
						_this.setValue(checkedNodes[0].data.groupName);
						if(_this.hiddenField){ // 2013-04-18 ZM
							_this.hiddenField.setValue(checkedNodes[0].data.groupNo);
						}
						_this.groupNo=checkedNodes[0].data.groupNo;
					}else{
						var sName='';
						var json = '';
						if(checkedNodes.length > 0){
								json = json + checkedNodes[0].data.groupNo;
								sName = sName + checkedNodes[0].data.groupName;
							}
						for(var i=1;i<checkedNodes.length;i++){
								json = json + ',' + checkedNodes[i].data.groupNo;
								sName = sName + ',' + checkedNodes[i].data.groupName;
						};
						_this.setValue(sName);
						if(_this.hiddenField){
							_this.hiddenField.setValue(json);
						}
					};
					_this.groupWindow.hide();
					if (typeof searchField.callback == 'function') {
						searchField.callback(checkedNodes);
					}
				}
			},{
				text : '取消',
				handler : function() {
					searchField.setRawValue('');
					_this.hiddenField.setValue('');// 之前选中的记录清空
					_this.groupWindow.hide();
				}
			}]
		}); 
	
		_this.groupWindow.on('hide',function(){
			_this.groupSearchPanel.getForm().reset();
			_this.GroupInfoStore.removeAll();
		});
		_this.groupWindow.on('show',function(){
//	---	_this.GroupInfoStore.load();
			searchFunction(); // 2013-04-19 ZM 修复机构用户放大镜默认查询分页问题
		});
		_this.groupWindow.show();
//		searchFunction();
		return;
	}
});
Ext.reg('group',Com.yucheng.bcrm.common.Group);