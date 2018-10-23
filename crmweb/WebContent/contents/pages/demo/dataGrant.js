/*
 * 数据权限管理
 * 2012-03-01
 * 苏建明
 * */

Ext.onReady(function() {

//    var filterStore = new Ext.data.JsonStore( {
//        restful : true,
//        proxy : new Ext.data.HttpProxy( {
//            url : basepath + '/datagrantselectquery!findFilter.json'// DataGrantSelectQueryAction
//            }),
//        fields : [ {
//            name : 'id',
//            mapping : 'id'
//        }, {
//            name : 'describetion',
//            mapping : 'describetion'
//        } ],
//        reader : new Ext.data.JsonReader( {
//            root : 'list'
//        }, [ {
//            name : 'id',
//            mapping : 'id'
//        }, {
//            name : 'describetion',
//            mapping : 'describetion'
//        } ])
//    });

//    var record = Ext.data.Record.create( [ {
//        name : 'id',
//        mapping : 'ID'
//    }, {
//        name : 'filter_id',
//        mapping : 'FILTER_ID'
//    } ]);

    // 新增窗口展示的from
//        var addPlanForm = new Ext.form.FormPanel(
//                {
//                    id : 'add',
//                    // labelWidth : 100,
//                    title : '授权信息',
//                    // height : 470,
//                    width : 600,
//                    // frame : true,
//                    region : 'center',
//                    region : 'east',
//                    split : true,
//                    autoScroll : true,
//                    buttonAlign : "center",
//                    reader : new Ext.data.JsonReader( {
//                        root : 'json.data'
//                    }, record),
//                    items : [ {
//                        layout : 'column',
//                        items : [ {
//                            columnWidth : .9,
//                            layout : 'form',
//                            items : [
//                                    {
//                                        xtype : 'textfield',
//                                        width : 200,
//                                        hidden : true,
//                                        fieldLabel : '隐藏ID',
//                                        labelStyle : 'text-align:right;',
//                                        name : 'id',
//                                        id : 'id',
//                                        anchor : '90%'
//                                    },
//                                    {
//                                        xtype : 'textfield',
//                                        width : 200,
//                                        fieldLabel : '<span style="color:red">*</span>功能点名称',
//                                        readOnly : true,
//                                        labelStyle : 'text-align:right;',
//                                        allowBlank : false,
//                                        name : 'funcName',
//                                        id : 'funcName',
//                                        anchor : '90%'
//                                    },
//                                    {
//                                        xtype : 'textfield',
//                                        width : 200,
//                                        fieldLabel : '<span style="color:red">*</span>授权角色',
//                                        readOnly : true,
//                                        labelStyle : 'text-align:right;',
//                                        name : 'telephone',
//                                        allowBlank : false,
//                                        id : 'roll',
//                                        name : 'roll',
//                                        anchor : '90%'
//                                    },
//                                    {
//                                        xtype : 'textfield',
//                                        width : 200,
//                                        fieldLabel : '授权角色id',
//                                        readOnly : true,
//                                        hidden : true,
//                                        labelStyle : 'text-align:right;',
//                                        id : 'roleId',
//                                        name : 'roleId',
//                                        anchor : '90%'
//                                    },
//                                    {
//                                        store : filterStore,
//                                        xtype : 'combo',
//                                        resizable : true,
//                                        fieldLabel : '<span style="color:red">*</span>数据权限过滤器',
//                                        labelStyle : 'text-align:right;',
//                                        id : 'dataAuthFilter',
//                                        name : 'filter_id',
//                                        hiddenName : 'filter_id',
//                                        valueField : 'id',
//                                        displayField : 'describetion',
//                                        mode : 'local',
//                                        allowBlank : false,
//                                        typeAhead : true,
//                                        forceSelection : true,
//                                        triggerAction : 'all',
//                                        emptyText : '请选择',
//                                        selectOnFocus : true,
//                                        width : '100',
//                                        anchor : '90%'
//                                    } ]
//                        } ]
//                    } ],
//                    buttons : [
//                            {
//                                text : '保存',
//                                handler : function() {
//                                    if (!addPlanForm.getForm().isValid()) {
//                                        Ext.Msg.alert("系统提醒", "请选择数据权限过滤器!");
//                                        return false;
//                                    }
//                                    Ext.Ajax
//                                            .request( {
//                                                url : basepath + '/auth-sys-filter-auth.json?a=1',
//                                                method : 'POST',
//                                                params : {
//                                                    'id' : Ext.getCmp("id")
//                                                            .getValue(),
//                                                    'filterID' : Ext.getCmp(
//                                                            "dataAuthFilter")
//                                                            .getValue(),
//                                                    'roleID' : Ext.getCmp(
//                                                            'roleId')
//                                                            .getValue(),
//                                                    'operate' : 'ADD'
//                                                },
//                                                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//                                                success : checkResult
//                                            });
//                                    function checkResult(response) {
//                                        Ext.Msg.alert('提示', '操作成功');
//                                        // addPlanForm.getForm().reset();
//                                    }
//                                    ;
//                                }
//                            },
//                            {
//                                text : '重置',
//                                handler : function() {
//                                    addPlanForm.getForm().reset();
//                                }
//                            },
//                            {
//                                text : '删除',
//                                handler : function() {
//                                    if (Ext.getCmp("id").getValue() != null
//                                            && Ext.getCmp("id").getValue() != "") {
//                                        Ext.Ajax
//                                                .request( {
//                                                    url : basepath + '/auth-sys-filter-auth.json?a=1',
//                                                    method : 'POST',
//                                                    params : {
//                                                        'id' : Ext.getCmp("id")
//                                                                .getValue(),
//                                                        'filterID' : Ext
//                                                                .getCmp(
//                                                                        "dataAuthFilter")
//                                                                .getValue(),
//                                                        'roleID' : Ext.getCmp(
//                                                                'roleId')
//                                                                .getValue(),
//                                                        'operate' : 'DELETE'
//                                                    },
//                                                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//                                                    success : checkResult,
//                                                    failure : checkResult
//                                                });
//                                        function checkResult(response) {
//                                            Ext.Msg.alert('提示', '操作成功');
//                                            Ext.getCmp('dataAuthFilter')
//                                                    .reset();
//                                        }
//                                        ;
//
//                                    } else {
//                                        Ext.Msg.alert('提示', '无可删除的数据！');
//                                    }
//                                    // addPlanForm.getForm().reset();
//                                }
//                            } ]
//                });

//        var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {
//            // /**节点数组，可以改为从后台读取*/
//            // nodeArray :nodeArra,
//            // **指向父节点的属性列*//*
//            parentAttr : 'PARENT_ID',
//            // **节点定位属性列，也是父属性所指向的列*//*
//            locateAttr : 'ID',
//            // **虚拟根节点id*//*
//            rootValue : '0',
//            // **用于展示节点名称的属性列*//*
//            textField : 'NAME',
//            // **指定节点ID的属性列*//*
//            idProperties : 'ID',
//            // **节点点击事件句柄*//*
//            clickFn : function(node) {
//                // Ext.getCmp('parentSection').setValue(node.id);
//            }
//        });
//        Ext.Ajax.request( {
//            url : basepath + '/datagrantquery.json',
//            method : 'GET',
//            success : function(response) {
//                var nodeArra = Ext.util.JSON.decode(response.responseText);
//                loader.nodeArray = nodeArra.JSON.data;
//                var children = loader.loadAll();
//                queryFunctionTreePanel.appendChild(children);
//                queryFunctionTreePanel.expandAll();
//                /*
//                 * fnRecursionFristTreeNode(loader.nodeArray[0]);
//                 * fnToDecideType(oNodeLeaf);
//                 */
//            }
//        });
    var record = Ext.data.Record.create([
                                         {name: 'fname',mapping:'NAME'},
                                         {name: 'className',mapping:'CLASS_NAME'},
                                         {name: '_id', type: 'int'},
                                         {name: '_parent',type: 'int'},
                                         {name: '_level', type: 'int'},
                                         {name: '_lft', type: 'int'},
                                         {name: '_rgt', type: 'int'},
                                         {name: '_is_leaf', type: 'bool'}
                                     ]);
        var store = new Ext.ux.maximgb.tg.NestedSetStore( {
            autoLoad : true,
            restful : true,
            proxy : new Ext.data.HttpProxy({
                url : basepath + '/datagrantdemoquery.json',
                success: function(response){
                alert(response.responseText);
                debugger;
                },
                method : 'GET'
            }),
            reader : new Ext.data.JsonReader({
                successProperty : 'success',
                idProperty : 'ID',
                messageProperty : 'message',
                root : 'JSON.data',
                totalProperty : 'JSON.count'
            }, record)
        });
    

        var queryFunctionTreePanel = new Ext.ux.maximgb.tg.GridPanel({
            title: '业统汇总计',
            stripeRows: true,
            autoExpandColumn: 'fname',
            
            store: store,
            master_column_id : 'fname',
            viewConfig : {
                enableRowBody : true
              },
            columns: [
              {id:'fname',header: "菜单", width: 100, sortable: true, dataIndex: 'fname'},
//              
//              {
//                  header:'分成占比<span style="color:red">*</span>',
//                  dataIndex:'price',
//                  xtype:'numbercolumn',
//                  sortable:true,
//                  width:120,
//                  editor:numberFieldEditor}
//              ,
              {header: "Action名称", 
                  type: 'textField',
                  width: 100,
                  sortable: true,
                  dataIndex: 'className',
                  align:'right'}
            ]
           
            
          });
//       new Com.yucheng.bcrm.TreePanel({ title:'系统数据操作功能树', width:200,
//          autoScroll:true, //rootVisible:false, //tbar:tbar, //**虚拟树形根节点
//         
//                                 root: new Ext.tree.AsyncTreeNode({ id:'0',
//                                  expanded:true, text:'数据权限树', autoScroll:true,
//                                  children:[] }), resloader:loader, listeners : {
//                                  'click' : function(node) { if
//                                  (node.leaf==true) {
//                                  addPlanForm.getForm().findField("funcName").setValue(node.text);
//                                  filterStore.baseParams = { "tempId" : node.id };
//                                  filterStore.load( {});
//                                  Ext.getCmp('id').reset();
//                                  Ext.getCmp('dataAuthFilter').reset(); var
//                                  temRoleId = Ext.getCmp('roleId').getValue();
//                                  if(temRoleId==""){ Ext.Msg.alert('系统提示',
//                                  '请先选择角色！'); return; }
//                                  addPlanForm.getForm().load( { restful : true,
//                                  url : basepath+
//                                  '/datagrantfilterquery.json?tempId='+
//                                  temRoleId+ '&tempFilterId='+ node.id, method :
//                                  'GET' });
//                                   } else { Ext.Msg.alert('系统提示', '请选择叶子节点！');
//                                  return; } } }, split:true });
//                                 
        var treeContainer = new Ext.Panel( {
            // frame:true,
            height : document.body.scrollHeight - 59,
            region : 'center',
            layout : 'fit',
            autoScroll : true,
            items : [ queryFunctionTreePanel ]
        });
        // *****************************************
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
            debugger;
//                            addPlanForm.getForm().reset();
//                            Ext.getCmp("roll").setValue(node.text);
//                            Ext.getCmp("roleId").setValue(node.id);
                        }
                    },
                    region : 'west',
                    split : true
                });

        var view = new Ext.Viewport( {// 页面展示
                    layout : 'border',
                    items : [ treeContainer ]
                });
    });