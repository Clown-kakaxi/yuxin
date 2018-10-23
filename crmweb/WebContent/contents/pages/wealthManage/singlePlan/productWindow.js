var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
			checkField : 'ASTRUE',//选择字段
			parentAttr : 'CATL_PARENT',
			locateAttr : 'root',//UNITID
			rootValue :'0',
			textField : 'CATL_NAME',
			idProperties : 'CATL_CODE'//节点点击事件句柄
		});
		var condition = '';
		var filter = false;
		Ext.Ajax.request({//产品树数据加载
			url : basepath + '/productCatlTreeAction.json',
			method:'GET',
			success:function(response){
				var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
				loader.nodeArray = nodeArra;
				var children = loader.loadAll();
				Ext.getCmp('productLeftTreeForShow').appendChild(children);
			}
		});
			
		var productLeftTreeForShow = new Com.yucheng.bcrm.TreePanel({//左侧显示的树
			id:'productLeftTreeForShow',
			width : 200,
			autoScroll:true,
			checkBox : true, //是否现实复选框：
			_hiddens : [],
			resloader:loader,//加载产品树
			region:'west',//布局位置设置
			split:true,
			root: new Ext.tree.AsyncTreeNode({//设置根节点
				id:'root',
				expanded:true,
				text:'银行产品树',
				autoScroll:true,
				children:[]
			}),
			listeners:{
				'checkchange' : function(node, checked) {
					if (checked) {
						var childNodes = node.childNodes;
						for ( var i = 0; i < childNodes.length; i++) {
							childNodes[i].getUI().toggleCheck(true);
						}
					} else {
						var childNodes = node.childNodes;
						for ( var i = 0; i < childNodes.length; i++) {
							childNodes[i].getUI().toggleCheck(false);
						}
					}
				}
			},
			clickFn:function(node){//单击事件，当单击树节点时触发并且获得这个节点的CATL_CODE
				if(node.attributes.CATL_CODE == undefined){
					Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
					return;
				}
				if(node.attributes.id!= ''){
					productManageInfoStore.baseParams = {
							'condition':'{"CATL_CODE":"'+node.attributes.CATL_CODE+'"}'
					};
					idStr = "";
					productManageInfoStore.load({//重新加载产品列表
						params:{
						   'idStr':idStr,
							limit:parseInt(spagesize_combo.getValue()),
							start:0
						}
					});		
				}
			}
		}); 

		var productSearchPanel = new Ext.form.FormPanel({//查询panel
			title:'产品查询',
			height:100,
			labelWidth:100,//label的宽度
			labelAlign:'right',
			frame:true,
			autoScroll : true,
			region:'north',
			split:true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.4,
					layout:'form',
					items:[{
						xtype:'textfield',
						name:'PRODUCT_ID',
						fieldLabel:'产品编号',
						anchor:'100%'
					}]
				},{
					columnWidth:.4,
					layout:'form',
					items:[{
						xtype:'textfield',
						name:'PROD_NAME',
						fieldLabel:'产品名称',
						anchor:'100%'
					}]
				}]
			}],
			buttonAlign:'center',
			buttons:[{
				text:'查询',
				handler: function(){
		 }
			},{
				text:'重置',
				handler: function(){
				 }
			}]
		});
			    	
		var tbar = new Ext.Toolbar({				
			items : [{ 
				text : '选定',
				handler : function() {
					var records = productManageGrid.selModel.getSelections();// 得到被选择的行的数组
	            	var selectLength = records.length;
	                 if (selectLength <= 0 ) {
	                 	alert("请选择产品");
	                     return false;
	                 }
	                 var tempName = '';
	                 for(var i = 0; i<selectLength;i++)
						{
							selectRe = productManageGrid.getSelectionModel().getSelections()[i];
							tempName = selectRe.data.productName;
							tempID = selectRe.data.a0;
							 var u = new store1.recordType({
							    	"a0" :tempID,             
									"a1" :tempName,
									"a2" :"10,000"
							    });
							 productGrid.stopEditing();
							 store1.insert(0, u);
							 productGrid.startEditing(0, 0);
						}
					productManageWindow.hide();
				}
			},'-',{
				text : '取消',
				handler : function() {
				}
			}]
		});
		var sm = new Ext.grid.CheckboxSelectionModel();    		
		var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		var productInfoColumns = new Ext.grid.ColumnModel([rownum,sm,
	        {header :'产品编号',dataIndex:'productId',id:"productId",sortable : true,width:160},
	        {header:'产品名称',dataIndex:'productName',id:'productName',sortable : true,width:200}
	        ]);
	
		var productInfoRecord = new Ext.data.Record.create([	
		    {name:'productId',mapping:'PRODUCT_ID'},
		    {name:'productName',mapping:'PROD_NAME'}
		    ]);
	
		var productInfoReader = new Ext.data.JsonReader({//读取json数据的panel
			totalProperty:'json.count',
			root:'json.data'
		},productInfoRecord);
	
		var productManageInfoStore = new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({
				url:basepath+'/comProductTree-action.json',
				method:'GET'
			}),
			reader:productInfoReader
		});

		var spagesize_combo = new Ext.form.ComboBox({	// 每页显示条数下拉选择框
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

		spagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
			bbar.pageSize = parseInt(spagesize_combo.getValue()),
			searchFunction();
		});
	
		var bbar = new Ext.PagingToolbar({	// 分页工具栏
			pageSize : parseInt(spagesize_combo.getValue()),
			store : productManageInfoStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
		});
	
		var productManageGrid =  new Ext.grid.GridPanel({//产品列表数据grid
			id:'productManageGrid',
			frame:true,
			autoScroll : true,
			tbar:tbar,
			bbar:bbar,
			stripeRows : true, // 斑马线
			store:productManageInfoStore,
			loadMask:true,
			cm :productInfoColumns,
			sm :sm,
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	
		var productManageWindow=new Ext.Window({
			title : '产品管理',
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
			items:[productLeftTreeForShow,{
				region:'center',
				layout:'border',
				items:[productSearchPanel,{
					region:'center',
					layout:'fit',
					items:[productManageGrid]
				}]				
			}]
		}); 
		
		