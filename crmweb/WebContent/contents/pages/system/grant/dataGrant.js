/*
 * 数据权限管理
 * 2012-11-16
 * GUOCHI
 * */

Ext.onReady(function() {
    var roleId='';
    //数据权限
    var describetionStore = new Ext.data.Store({
        restful: true,
        proxy : new Ext.data.HttpProxy({
            url : basepath + '/datagrantstorequery!getFilters.json',
            method : 'GET'
        }),
        reader : new Ext.data.JsonReader({
            root : 'json.data'
        },['ID','DESCRIBETION']),
        sortInfo:{field: "ID", direction: "ASC"}
    });
  
//    var pagesize_combo = new Ext.form.ComboBox({//每页显示条数下拉选择框
//        name : 'pagesize',
//        triggerAction : 'all',
//        mode : 'local',
//        store : new Ext.data.ArrayStore({
//            fields : ['value', 'text'],
//            data : [[10, '10条/页'], [20, '20条/页'],[50, '50条/页'], [100, '100条/页'],[250, '250条/页'], [500, '500条/页']]
//        }),
//        valueField : 'value',
//        displayField : 'text',
//        value : '20',
//        forceSelection : true,
//        width : 85
//    });

//    pagesize_combo.on("select", function(comboBox) {//改变每页显示条数reload数据
//        pageBar.pageSize = parseInt(pagesize_combo.getValue()), store.reload({
//            params : {
//                start : 0,
//                limit : parseInt(pagesize_combo.getValue())
//            }
//        });
//    });
    var multiSm = new Ext.grid.CheckboxSelectionModel();//复选框
    var rownum = new Ext.grid.RowNumberer({//定义自动当前页行号
        header : 'No.',
        width : 28
    });
    var numberFieldEditor = new Ext.form.NumberField();
    var comboBoxEditor = new Ext.form.ComboBox({
        id : 'filterId0',
        fieldLabel : '数据权限',
        name : 'filterId',
        hiddenName:'filterId',
        xtype : 'combo',
        triggerAction : 'all',
        resizable :true,
        handleHeight :1,
        mode : 'local',
        store : describetionStore,
        valueField : 'ID',
        displayField : 'DESCRIBETION',
        editable : false,
        emptyText : '请选择',
        anchor : '90%'
    });

    var lm = new Ext.LoadMask(document.body, {// 定义遮屏到body节点上
                msg : '正在保存数据,请稍等...',
                removeMask : true
            });
    
    var mouduleColumns = new Ext.grid.ColumnModel([rownum, {//列模型 
            header : 'ID',
            dataIndex : 'Aid',
            sortable : true,
            hideable:false,
            hidden : true
        },{
            header : '查询名称',
            dataIndex : 'classDesc',
            sortable : true,
            width : 150
        }, {
            header : '查询Action',
            dataIndex : 'className',
            sortable : true,
            width : 250
        }, {
            header : 'FID',
            dataIndex : 'Fid',
            sortable : true,
            hideable:false,
            hidden : true,
            editor:numberFieldEditor
        }, {
            header : '数据权限',
            dataIndex : 'describetion',
            sortable : true,
            width : 380,
            editor: comboBoxEditor
        }]);
    var mouduleQueryRecord = Ext.data.Record.create([
        {name : 'Aid',       mapping : 'AID'},
        {name : 'Fid',       mapping : 'FID'},
        {name : 'classDesc', mapping : 'CLASS_DESC'},
        {name : 'className', mapping : 'CLASS_NAME'},
        {name : 'describetion',  mapping : 'DESCRIBETION'}
       ]);
    var store = new Ext.data.Store({//查询数据源
        restful : true,
        proxy : new Ext.data.HttpProxy({
            url : basepath + '/datagrantquery.json',
            method : 'GET'
        }),
        reader : new Ext.data.JsonReader({
            successProperty : 'success',
            idProperty : 'ID',
            messageProperty : 'message',
            root : 'json.data',
            totalProperty : 'json.count'
        }, mouduleQueryRecord)
    });
//    var pageBar = new Ext.PagingToolbar({//分页工具栏
//        pageSize : parseInt(pagesize_combo.getValue()),
//        store : store,
//        displayInfo : true,
//        displayMsg : '显示{0}条到{1}条,共{2}条',
//        emptyMsg : "没有符合条件的记录",
//        items : ['-', '&nbsp;&nbsp;', pagesize_combo]
//    });
    //数据权限授权
    var listPanel = new Ext.grid.EditorGridPanel({
        store : store,
        frame : true,
        cm : mouduleColumns,
        clicksToEdit:1,
        stripeRows : true,
        frame : true,
        autoScroll : true,  //自动出现滚动条
        viewConfig : {},    //不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        },
        buttonAlign : 'center',
        buttons:[{
            text:'保存',handler:function(){
                var json0 = {'FILTER_ID':[]};
                for(var i=0;i<store.getCount();i++){
                    var temp=store.getAt(i);
                    if(temp.data.Fid!=''){
                        json0.FILTER_ID.push(temp.data.Fid);
                    }
                }
                var temp0 = Ext.encode(json0).toString();
                lm.show();
                Ext.Ajax.request({
                    url : basepath + '/DataGrantManager-action!saveAllot.json',
                    method : 'POST',
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    params:{
                        'roleId':roleId,
                        'filterId':temp0
                    },
                    success : function() {
                        Ext.Msg.alert('提示', '操作成功');
                        store.reload({
                            params:{
                                'roleId' : roleId
                            }
                        });
                            lm.hide();
                    },
                    failure : function(response) {
                        Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                        store.reload({
                            params:{
                                'roleId' : roleId
                            }
                        });
                        lm.hide();
                    }
                });
            }
        }, {
             text:'重置',handler:function(){
                 if(roleId!=''){
                     store.reload({
                         params:{
                             'roleId' : roleId
                         }
                     });
                 }
             }
         }]
    });
    var treeContainer = new Ext.Panel( {
        height : document.body.scrollHeight - 59,
        region : 'center',
        layout : 'fit',
        autoScroll : true,
        items : [listPanel]
    });

    var leftTreeForShow = new Ext.tree.TreePanel( {// 左边的产品树
        title : '银行角色树',
        rootVisible : false,
        width : 200,
        autoScroll : true,
        animate : true,
        root : new Ext.tree.AsyncTreeNode( {
            id : "null",
            text : '银行角色树',
            expanded : false
        }),
        loader : new Ext.tree.TreeLoader( {
            baseAttrs : {},
            dataUrl : basepath + '/sysRole-kind-tree.json',
            requestMethod : 'GET'
        }),
        listeners : {
            'click' : function(node) {
                roleId=  node.id;
                store.load({
                    params : {
                        'roleId' : roleId
//                        start : 0,
//                        limit : parseInt(pagesize_combo.getValue())
                    }
                });
            }
        },
        region : 'west',
        split : true
    });
    var view = new Ext.Viewport( {// 页面展示
                layout : 'border',
                items : [ leftTreeForShow, treeContainer ]
    });
/**################################     页面监听事件    ####################################################*/
    listPanel.on('rowclick',function(){//点击某一行，加载该行下拉框内待选项的数据
        var _record = listPanel.getSelectionModel().selection.record;
        if(_record){
            describetionStore.load({
                params : {
                    'className' :_record.data.className 
                }
            });
        }
    });
    comboBoxEditor.on('select',function(){
        var _id = comboBoxEditor.value;
        var _value = comboBoxEditor.lastSelectionText;
        listPanel.getSelectionModel().selection.record.data.Fid=comboBoxEditor.value;
        comboBoxEditor.value=comboBoxEditor.lastSelectionText;
    });
});