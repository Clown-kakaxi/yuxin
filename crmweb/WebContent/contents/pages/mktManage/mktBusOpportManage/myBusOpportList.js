/**
 * 营销管理->商机管理->我的商机
 * 入口JS文件
 * wzy，2013-02-17
 */

/*****************************全局变量定义*********开始*****************/
//分页工具栏
var bbar = null;
//每页显示条数下拉选择框
var pagesize_combo = null;
//功能按钮
var tbar = null;
//页面布局
var viewport = null;
//查询结果表格
var grid = null;
//查询结果分页条数
var number = null;
/*****************************全局变量定义*********结束*****************/

/*****************************业务逻辑执行*********开始*****************/
Ext.onReady(function() {
	
	//提示信息组件初始化
	Ext.QuickTips.init();

	// 功能按钮定义
	tbar = new Ext.Toolbar({
		items:[{
			text:'商机详情',
		    iconCls:'detailIconCss',
		    handler:function(){
		        viewMyBusOpportInit();
		    }
		},'-',{
	        text:'新增商机',
	        iconCls:'editIconCss',
	        handler:function(){
	        	resetAddForm();
	        	addMyBusOpportInit();
	        }
		},'-',{
	        text:'维护商机',
	        iconCls:'editIconCss',
	        handler:function(){
	        	editMyBusOpportInit();
	        }
		},'-',{
	        text:'删除商机',
	        iconCls:'deleteIconCss',
	        handler:function(){
	        	deleteInit();
	        }
		},'-',{
	        text:'关闭商机',
	        iconCls:'closeIconCss',
	        handler:function(){
	        	busiOpportCloseWindowInit();
	        }
		},'-',{
	        text:'退回商机',
	        iconCls:'closeIconCss',
	        handler:function(){
	        	backInit();
	        }
		},'-',{
	        text:'销售活动',
	        iconCls:'closeIconCss',
	        handler:function(){
	        	salesActivInit();
	        }
		}]
	});

	// 每页显示条数下拉选择框
    pagesize_combo = new Ext.form.ComboBox({
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
        //editable : false,
        width : 85
    });
    
    //获取查询结果分页条数
    number = parseInt(pagesize_combo.getValue());
    
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
    bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo]
    });

	//定义查询结果表格
	grid = new Ext.grid.GridPanel({
        //height : document.body.scrollHeight-107,
        //width : document.body.scrollWidth-10,
		title : '我的商机列表',
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

	//拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        grid.setHeight(document.body.scrollHeight-107);
        grid.setWidth(document.body.scrollWidth-10);
        grid.getView().refresh();
    });
    
    //页面布局
    viewport = new Ext.Viewport({
        layout : 'fit',
        items : [ {
            layout : 'border',
            items : [qForm,grid]
        }]
    });
    
    //页面打开就进行数据查询
    /*store.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });*/
    
    // 删除商机
	function deleteInit() {
		var record = grid.getSelectionModel().getSelected();
		if(record == null){
			Ext.Msg.alert('提示', '请先选择要删除的商机！');
			return;
		}
		Ext.MessageBox.confirm('提示','您确定要删除吗？',function(buttonId){
			if(buttonId.toLowerCase() == "no"){//不删除
				return false;
			}else{//要删除（未写完删除逻辑代码）
				return false;
				Ext.Ajax.request({
					url : basepath + '/market-plan!batchDestroy.json?idStr='+ idStr,
					//method : 'DELETE',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功');
						store.reload();
					},
					failure : function(response){
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							store.reload();
						}
					}
				});
			}
		});
	}
	
	// 关闭商机
	function closeInit() {
		var record = grid.getSelectionModel().getSelected();
		if(record == null){
			Ext.Msg.alert('提示', '请先选择要关闭的商机！');
			return;
		}
		Ext.MessageBox.confirm('提示','您确定要关闭吗？',function(buttonId){
			if(buttonId.toLowerCase() == "no"){//不关闭
				return false;
			}else{//要关闭（未写完关闭逻辑代码）
				return false;
				Ext.Ajax.request({
					url : basepath + '/market-plan!batchDestroy.json?idStr='+ idStr,
					//method : 'DELETE',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功');
						store.reload();
					},
					failure : function(response){
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							store.reload();
						}
					}
				});
			}
		});
	}
	
	// 退回商机
	function backInit() {
		var record = grid.getSelectionModel().getSelected();
		if(record == null){
			Ext.Msg.alert('提示', '请先选择要退回的商机！');
			return;
		}
		busiOpportReturnWindowInit();
//		Ext.MessageBox.confirm('提示','您确定要退回吗？',function(buttonId){
//			if(buttonId.toLowerCase() == "no"){//不退回
//				return false;
//			}else{//要退回（未写完退回逻辑代码）
//				return false;
//				Ext.Ajax.request({
//					url : basepath + '/market-plan!batchDestroy.json?idStr='+ idStr,
//					//method : 'DELETE',
//					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//					success : function() {
//						Ext.Msg.alert('提示', '操作成功');
//						store.reload();
//					},
//					failure : function(response){
//						var resultArray = Ext.util.JSON.decode(response.status);
//						if(resultArray == 403) {
//							Ext.Msg.alert('提示', response.responseText);
//						} else {
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//							store.reload();
//						}
//					}
//				});
//			}
//		});
	}
}); 
/*****************************业务逻辑执行*********结束*****************/