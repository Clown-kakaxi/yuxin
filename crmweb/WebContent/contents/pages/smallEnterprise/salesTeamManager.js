//德阳银行POC 稍作改动 代码很乱

Ext.onReady(function() {
    Ext.QuickTips.init();

    // 展示树形结构

        Ext
                .override(
                        Ext.form.ComboBox,
                        {
                            onViewClick : function(doFocus) {
                                var index = this.view.getSelectedIndexes()[0], s = this.store, r = s
                                        .getAt(index);
                                if (r) {
                                    this.onSelect(r, index);
                                } else if (s.getCount() === 0) {
                                    this.collapse();

                                }
                                if (doFocus !== false) {
                                    this.el.focus();
                                }
                            }
                        });

        // 递归收起子节点
        function childCollapse(node) {
            node.eachChild(function(currNode) {
                if (!currNode.isLeaf()) {
                    currNode.collapse();
                    childCollapse(currNode);
                }
            });
        }
        // 选中节点后判断，所有被选中节点是否有跨行的情况
        function spanBank(orgTreePanel_p) {
            if (orgTreePanel_p.root.getUI().isChecked()) {
                var rootId = orgTreePanel_p.root.id;
                var checkLevel = rootId.substring(rootId.length - 1);
                return checkLevel;
            }
            var level2Nodes = orgTreePanel_p.root.childNodes;// 二级节点数组
            var level3Nodes = new Array();// 三级节点数组
            var level4Nodes = new Array();// 四级节点数组
            // for 循环得到所有3级节点
            for ( var m = 0; m < level2Nodes.length; m++) {
                level2Nodes[m].eachChild(function(level3Node) {
                    level3Nodes[level3Nodes.length] = level3Node;
                });
            }
            // for 循环得到所有4级节点
            for ( var n = 0; n < level3Nodes.length; n++) {
                level3Nodes[n].eachChild(function(level4Node) {
                    level4Nodes[level4Nodes.length] = level4Node;
                });
            }
            var num = 0;
            var level2NodeChecked;
            var level3Checked = new Array();
            for ( var i = 0; i < level2Nodes.length; i++) {
                if (level2Nodes[i].getUI().isChecked()) {
                    level2NodeChecked = level2Nodes[i];// 得到第二层级被选择的节点
                    num++;
                    if (num >= 2) {
                        // alert(" 一级没选,2级选择 有跨行:"+"4");
                        return "4";// 表示跨行选择
                    }
                }
            }

            if (num == 1) {
                for ( var j = 0; j < level3Nodes.length; j++) {
                    if (level3Nodes[j].getUI().isChecked()) {
                        level3Checked[level3Checked.length] = level3Nodes[j];
                        if (level3Nodes[j].parentNode != level2NodeChecked) {// 如果被选择的三级节点的父节点不是同一个被选择二级节点,则表示有跨行选择
                            // alert(" 二级选择了一个,三级选择有跨行:" + "4");
                            return "4";
                        }
                    }
                }
                for ( var k = 0; k < level4Nodes.length; k++) {

                    if (level4Nodes[k].getUI().isChecked()) {
                        var flag = false;
                        for ( var a = 0; a < level3Checked.length; a++) {
                            if (level4Nodes[k].parentNode == level3Checked[a]) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            // alert(level2NodeChecked.text);
                            // alert(" 二级选择了一个,三级选择了一个,四级选择有跨行:" + "4");
                            return "4";
                        }
                    }
                }
                var check2NodeId = level2NodeChecked.id;
                var checkLevel = check2NodeId
                        .substring(check2NodeId.length - 1);
                return checkLevel;

            } else if (num == 0) {

                var nodeNum3 = 0;
                var tempChecked3Node;
                for ( var b = 0; b < level3Nodes.length; b++) {
                    if (level3Nodes[b].getUI().isChecked()) {
                        tempChecked3Node = level3Nodes[b];
                        nodeNum3++;
                        if (nodeNum3 >= 2) {
                            return "4";
                        }
                    }
                }
                if (nodeNum3 == 1) {

                    for ( var c = 0; c < level4Nodes.length; c++) {
                        if ((level4Nodes[c].getUI().isChecked())
                                && (level4Nodes[c].parentNode != tempChecked3Node)) {
                            // alert("二级没选,3级选择了一个,4级选择有跨行.");
                            return "4";
                        }
                    }
                    var check3NodeId = tempChecked3Node.id;
                    var checkLevel = check3NodeId
                            .substring(check3NodeId.length - 1);
                    // alert("选择了三级节点统计:"+checkLevel);
                    return checkLevel;
                } else if (nodeNum3 == 0) {
                    for ( var d = 0; d < level4Nodes.length; d++) {
                        if (level4Nodes[d].getUI().isChecked()) {
                            var check4NodeId = level4Nodes[d].id;
                            var checkLevel = check4NodeId
                                    .substring(check4NodeId.length - 1);
                            // alert("选择了四级节点统计: "+checkLevel);
                            return checkLevel;
                        }
                    }
                }
            }

        }

        function getCheckedStr(checkedStr, node) {
            node.eachChild(function(tempNode) {
                if (tempNode != null) {
                    if (tempNode.getUI().isChecked()) {
                        var idCode = tempNode.id;
                        var id = idCode.substring(0, idCode.length - 1);
                        checkedStr = checkedStr + "'" + id + "',";
                        checkedStr = getCheckedStr(checkedStr, tempNode);
                        return checkedStr;
                    } else {
                        checkedStr = getCheckedStr(checkedStr, tempNode);
                        return checkedStr;
                    }
                }
            });

            return checkedStr;
        }

        function getCheckedNode(orgTreePanel_p) {
            var rootNode = orgTreePanel_p.root;
            var checkedStr = "";
            if (rootNode.getUI().isChecked()) {
                var idCode = rootNode.id;
                var id_2 = idCode.substring(0, idCode.length - 1);
                checkedStr = checkedStr + "'" + id_2 + "'" + ",";
            }
            checkedStr = getCheckedStr(checkedStr, rootNode);

            return checkedStr.substring(0, checkedStr.length - 1);
        }

        var orgTreePanel1 = new Ext.tree.TreePanel( {
            autoScroll : true,
            height : 350,
            width : 200,
            listeners : {
                'click' : function(node) {
                    node.getUI().toggleCheck(true);
                    var level = spanBank(orgTreePanel1);
                    var checkedNodes = getCheckedNode(orgTreePanel1);
                    // tempCombo =
                // addPlanForm.getForm().findField("groupHostOrgNoName");
                // addPlanForm.getForm().findField("organizationId").setValue(node.id);
                tempCombo.setRawValue(node.text);
                tempCombo.collapse();

            },
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
            root : new Ext.tree.AsyncTreeNode( {
                id : orgId,
                text : orgName,
                autoScroll : true,
                expanded : true,
                leaf : false,
                // checked:false,
                loader : new Ext.tree.TreeLoader( {
                    url : basepath + '/system-unit-recursive-plain.json',
                    requestMethod : 'GET',
                    listeners : {
                        'load' : function() {
                            var rootNode = orgTreePanel1.root;
                            rootNode.eachChild(function(node) {
                                if (!node.isLeaf()) {
                                    node.collapse();
                                    childCollapse(node);
                                }
                            });
                        }
                    }
                })
            }),
            animate : false,
            useArrows : false,
            border : false
        });
        // ************************************

        var teamStatusStore = new Ext.data.ArrayStore( {
            fields : [ 'myId', 'displayText' ],
            data : [ [ '1', '正常' ], [ '2', '注销' ] ]
        });

        Ext.QuickTips.init();
        var searchPanel = new Ext.form.FormPanel( {
            labelWidth : 105,
            title : "营销团队信息查询",
            labelAlign : 'right',
            height : 100,
            region : 'north',
            frame : true,
            // region : 'north',
            // autoScroll : true,
                layout : 'column',
                items : [ {
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        fieldLabel : '营销团队编号',
                        id : 'MKT_TEAM_ID',
                        name : 'MKT_TEAM_ID',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        fieldLabel : '营销团队名称',
                        id : 'MKT_TEAM_NAME',
                        name : 'MKT_TEAM_NAME',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        fieldLabel : '团队状态',
                        hiddenName : 'TEAM_STATUS',
                        id : 'TEAMSTATUS',
                        forceSelection : true,
                        resizable : true,
                        xtype : 'combo',
                        labelStyle : 'text-align:right;',
                        triggerAction : 'all',
                        mode : 'local',
                        store : teamStatusStore,
                        valueField : 'myId',
                        displayField : 'displayText',
                        emptyText : '请选择',
                        anchor : '90%'
                } ]
            } ],
            buttonAlign : 'center',
            buttons : [
                    {
                        text : '查询',
                        handler : function() {
                            var conditionStr = searchPanel.getForm()
                                    .getFieldValues(false);
                            store.baseParams = {
                                "condition" : Ext.encode(conditionStr)
                            };
                            store.load( {
                                params : {
                                    start : 0,
                                    limit : parseInt(pagesize_combo.getValue())
                                }
                            });
                        }
                    }, {
                        text : '重置',
                        handler : function() {
                            searchPanel.getForm().reset();
                        }
                    } ]
        });

        // 定义自动当前页行号
        var rownum = new Ext.grid.RowNumberer( {
            header : 'No.',
            width : 28
        });

        // 列模型
        var columns = new Ext.grid.ColumnModel( [ rownum, {
            header : '营销团队编号', // 列标题
            dataIndex : 'marketTeamId', // 数据索引:和Store模型对应
            sortable : true,
            width : 130
        // 是否可排序
                }, {
                    header : '营销团队名称', // 列标题
                    dataIndex : 'marketTeamName', // 数据索引:和Store模型对应
                    sortable : true,
                    width : 150
                // 是否可排序
                }, {
                    header : '团队状态',
                    dataIndex : 'teamstatus',
                    sortable : true,
                    renderer : function(value) {
                        if (value == '1') {
                            return value = '正常';
                        } else if (value == '2') {
                            return value = '注销';
                        }
                    },
                    width : 150
                }, {
                    header : '负责人 ', // 列标题
                    dataIndex : 'teamLeader', // 数据索引:和Store模型对应
                    sortable : true,
                    width : 150
                // 是否可排序
                }, {
                    header : '负责人ID ', // 列标题
                    dataIndex : 'teamLeaderId', // 数据索引:和Store模型对应
                    sortable : true,
                    hidden : true,
                    width : 150
                // 是否可排序
                }, {
                    header : '负责人联系电话 ', // 列标题
                    dataIndex : 'leadTelephone', // 数据索引:和Store模型对应
                    sortable : true,
                    hidden : true,
                    width : 150
                // 是否可排序
                }, {
                    header : '创建人 ', // 列标题
                    dataIndex : 'createUser', // 数据索引:和Store模型对应
                    sortable : true,
                    hidden : true,
                    width : 100
                // 是否可排序
                }, {
                    header : '归属机构 id', // 列标题
                    dataIndex : 'organizationId', // 数据索引:和Store模型对应
                    hidden : true,
                    sortable : true,
                    width : 100
                // 是否可排序
                }, {
                    header : '归属机构 ', // 列标题
                    dataIndex : 'organizationName', // 数据索引:和Store模型对应
                    hidden : true,
                    sortable : true,
                    width : 100
                // 是否可排序
                }, {
                    header : '团队成员数 ', // 列标题
                    dataIndex : 'teamNumber', // 数据索引:和Store模型对应
                    sortable : true,
                    // align:'right',
                    width : 100
                // 是否可排序
                }, {
                    header : '团队客户数 ', // 列标题
                    dataIndex : 'custNumber', // 数据索引:和Store模型对应
                    sortable : true,
                    // align:'right',
                    width : 100
                // 是否可排序
                }, {
                    header : '创建人姓名 ', // 列标题
                    dataIndex : 'createUserName', // 数据索引:和Store模型对应
                    sortable : true,
                    width : 100
                // 是否可排序
                }, {
                    header : '团队创建时间',
                    dataIndex : 'createDate',
                    sortable : true,
                    width : 150
                } ]);

        var record = Ext.data.Record.create( [ {
            name : 'marketTeamId',
            mapping : 'MKT_TEAM_ID'
        }, {
            name : 'createUser',
            mapping : 'CREATE_USER'
        }, {
            name : 'createUserId',
            mapping : 'CREATE_USER_ID'
        }, {
            name : 'createUserName',
            mapping : 'CREATE_USER_NAME'
        }, {
            name : 'createUserOrgId',
            mapping : 'CREATE_USER_ORG_ID'
        }, {
            name : 'createDate',
            mapping : 'CREATE_DATE'
        }, {
            name : 'teamstatus',
            mapping : 'TEAM_STATUS'
        }, {
            name : 'leadTelephone',
            mapping : 'LEAD_TELEPHONE'
        }, {
            name : 'teamNumber',
            mapping : 'TEAM_NO',
            type : 'float'
        }, {
            name : 'custNumber',
            mapping : 'CUST_NO',
            type : 'float'
        }, {
            name : 'teamCustomerNumber',
            mapping : 'TEAM_CUS_NO'
        }, {
            name : 'marketTeamName',
            mapping : 'MKT_TEAM_NAME'
        }, {
            name : 'organizationId',
            mapping : 'ORG_ID'
        }, {
            name : 'organizationName',
            mapping : 'ORG_NAME'
        }, {
            name : 'teamLeader',
            mapping : 'TEAM_LEADER'
        }, {
            name : 'teamLeaderId',
            mapping : 'TEAM_LEADER_ID'
        } ]);

        var store = new Ext.data.Store( {
            restful : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/marketTeamQuery.json',
                failure : function(response) {
                    var resultArray = Ext.util.JSON.decode(response.status);
                    if (resultArray == 403) {
                        Ext.Msg.alert('提示', response.responseText);
                    }
                }
            }),
            reader : new Ext.data.JsonReader( {
                successProperty : 'success',
                idProperty : 'MKT_TEAM_ID',
                messageProperty : 'message',
                root : 'json.data',
                totalProperty : 'json.count'
            }, record)
        });

        // 每页显示条数下拉选择框
        var pagesize_combo = new Ext.form.ComboBox( {
            name : 'pagesize',
            triggerAction : 'all',
            mode : 'local',
            store : new Ext.data.ArrayStore(
                    {
                        fields : [ 'value', 'text' ],
                        data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
                                [ 50, '50条/页' ], [ 100, '100条/页' ],
                                [ 250, '250条/页' ], [ 500, '500条/页' ] ]
                    }),
            valueField : 'value',
            displayField : 'text',
            value : '20',
            // editable : false,
            width : 85
        });

        // 默认加载数据
        store.load( {
            params : {
                start : 0,
                limit : parseInt(pagesize_combo.getValue())
            }
        });

        // 改变每页显示条数reload数据
        pagesize_combo.on("select", function(comboBox) {
            bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
                params : {
                    start : 0,
                    limit : parseInt(pagesize_combo.getValue())
                }
            });
        });
        // 分页工具栏
        var bbar = new Ext.PagingToolbar( {
            pageSize : parseInt(pagesize_combo.getValue()),
            store : store,
            displayInfo : true,
            displayMsg : '显示{0}条到{1}条,共{2}条',
            emptyMsg : "没有符合条件的记录",
            items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
        });

        // ************************************************************

        // ***********************
        var addGroupCustSearchWindow = new Ext.Window( {
            layout : 'border',
            width : 400,
            height : 350,
            // resizable : false,//是否允许缩放
            draggable : true,// 是否可以拖动
            closable : true,// 是否可关闭
            modal : true,
            closeAction : 'hide',
            collapsible : true,// 是否可收缩
            titleCollapse : true,
            buttonAlign : 'center',
            border : false,
            animCollapse : true,
            animateTarget : Ext.getBody(),
            constrain : true
        });

        var listPanel = new Ext.grid.GridPanel(
                {
                    id : 'sujm',
                    store : store,
                    title : "营销团队信息表",
                    frame : true,
                    region : 'center',
                    cm : columns,
                    stripeRows : true,
                    tbar : [
                            {
                                text : '新增',
                                iconCls : 'addIconCss',
                                handler : function() {
                                    addInit();
                                }
                            }/*
                                 * , '-',{ text : '新增集团营销团队', handler :
                                 * function() { addMInit(); } }
                                 */,
                            '-',
                            {
                                text : '修改',
                                iconCls : 'editIconCss',
                                handler : function() {

                                    var selectLength = listPanel
                                            .getSelectionModel()
                                            .getSelections().length;

                                    var selectRe = listPanel
                                            .getSelectionModel()
                                            .getSelections()[0];

                                    if (selectLength != 1) {
                                        alert('请选择一条记录');
                                    } else {
                                        var checkCreater = listPanel
                                                .getSelectionModel().selections.items[0].data.createUser;
                                        if (__userId != checkCreater) {
                                            Ext.Msg.alert("系统提示",
                                                    "您不能修改别人创建的团队！");
                                            return false;
                                        }
                                        editBasePlanForm.getForm().loadRecord(
                                                selectRe);
                                        document
                                                .getElementById('marketTeamIdStr').value = selectRe.data.marketTeamId;
                                        editInit();

                                    }
                                }

                            },
                            '-',
                            {
                                text : '注销',
                                iconCls : 'deleteIconCss',
                                handler : function() {
                                    var selectRe = listPanel
                                            .getSelectionModel()
                                            .getSelections()[0];
                                    if (selectRe == null
                                            || selectRe == 'undefined') {
                                        alert('请选择一条记录');
                                    } else {
                                        var checkCreater = listPanel
                                                .getSelectionModel().selections.items[0].data.createUser;
                                        if (__userId != checkCreater) {
                                            Ext.Msg.alert("系统提示",
                                                    "您不能注销别人创建的团队！");
                                            return false;
                                        }
                                        var kehu_no = listPanel
                                                .getSelectionModel().selections.items[0].data.createUser;
                                        var chengyuan_no = listPanel
                                                .getSelectionModel().selections.items[0].data.teamNumber;
                                        var _status = listPanel
                                                .getSelectionModel().selections.items[0].data.teamstatus;
                                        if (_status == "注销") {
                                            Ext.Msg.alert("系统提示",
                                                    "该用户已经为【注销】状态！！");
                                            return false;
                                        }
                                        // if(kehu_no !=0 ||chengyuan_no !=0 ){
                                        //												
                                        // Ext.Msg.alert("系统提示","该团队下有成员或客户，不能注销！");
                                        // return false;
                                        // }
                                        if (confirm("确定要注销吗?")) {
                                            Ext.Ajax
                                                    .request( {
                                                        url : basepath
                                                                + '/marketTeamAction!destroy.json?id='
                                                                + selectRe
                                                                        .get('marketTeamId'),
                                                        method : 'GET',
                                                        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                                        success : checkResult,
                                                        failure : checkResult
                                                    });
                                            function checkResult(response) {
                                                var resultArray = Ext.util.JSON
                                                        .decode(response.status);
                                                var resultError = response.responseText;
                                                if ((resultArray == 200
                                                        || resultArray == 201 || resultArray == 404)) {
                                                    Ext.Msg.alert('提示', '操作成功');
                                                    addPlanForm.getForm()
                                                            .reset();
                                                    store
                                                            .reload( {
                                                                params : {
                                                                    start : 0,
                                                                    limit : bbar.pageSize
                                                                }
                                                            });
                                                } else {
                                                    Ext.Msg
                                                            .alert(
                                                                    '提示',
                                                                    '操作失败,失败原因:' + resultError);
                                                    store
                                                            .reload( {
                                                                params : {
                                                                    start : 0,
                                                                    limit : bbar.pageSize
                                                                }
                                                            });
                                                }
                                            }
                                            ;
                                        }
                                        ;
                                    }
                                }
                            },
                            '-',
                            {
                                text : '查看详细信息',
                                iconCls : 'detailIconCss',
                                handler : function() {
                                    // 得到选中记录
                                    var selectRe = listPanel
                                            .getSelectionModel()
                                            .getSelections()[0];
                                    if (selectRe == null
                                            || selectRe == "undefined") {
                                        alert('请选择一条记录');
                                    } else {
                                        var checkStatus = listPanel
                                                .getSelectionModel().selections.items[0].data.teamstatus;
                                        if (checkStatus == "注销") {
                                            Ext.Msg.alert("系统提示", "该团队已经被注销！");
                                            return false;
                                        }
                                        editTp.setActiveTab("baseInfo");
                                        showChanceForm.getForm().loadRecord(
                                                selectRe);
                                        showInit();
                                    }
                                }
                            } ],
                    region : 'center',
                    frame : true,
                    bbar : bbar,// 分页工具栏
                    viewConfig : {
                    // 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
                    },
                    loadMask : {
                        msg : '正在加载表格数据,请稍等...'
                    }
                });

        // 新增窗口展示的from
        var addPlanForm = new Ext.form.FormPanel(
                {
                    id : 'add',
                    labelWidth : 100,
                    height : 300,
                    frame : true,
                    region : 'center',
                    autoScroll : true,
                    buttonAlign : "center",
                    items : [ {
                        layout : 'column',
                        items : [ {
                            columnWidth : .9,
                            layout : 'form',
                            items : [
                                    {
                                        id : 'yxtdname',
                                        xtype : 'textfield',
                                        width : 200,
                                        fieldLabel : '<span style="color:red">*</span>营销团队名称',
                                        labelStyle : 'text-align:right;',
                                        allowBlank : false,
                                        name : 'marketTeamName',
                                        id : 'marketTeamName',
                                        anchor : '90%'
                                    },
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        hidden : true,
                                        name : 'organizationId',
                                        anchor : '90%'
                                    },
                                    /*
                                         * { columnWidth : .2, layout : 'form',
                                         * items : [ { xtype:'combo', store :
                                         * new Ext.data.SimpleStore( { fields :
                                         * [], data : [ [] ] }),
                                         * name:'groupHostOrgNoName', //
                                         * id:'GROUP_HOST_ORG_NO_NAME',
                                         * emptyText : '请选择', fieldLabel : '<span
                                         * style="color:red">*</span>归属机构',
                                         * //editable:false, resizable:true,
                                         * labelStyle: 'text-align:right;',
                                         * anchor : '90%', allowBlank : false,
                                         * mode : 'local', triggerAction :
                                         * 'all', maxHeight : 390, //
                                         * 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
                                         * tpl:"<tpl for='.' <div
                                         * style='height:390px'> <div
                                         * id='addOrgTreeDivForAdd1'></div></div></tpl>", //
                                         * tpl:"<div style='height:390px'> <div
                                         * id='addOrgTreeDivForAdd'></div></div>",
                                         * onSelect : Ext.emptyFn, listeners:{
                                         * 'expand':function(combo){ //
                                         * combo.doLayout();
                                         * orgTreePanel1.render('addOrgTreeDivForAdd1'); } } } ] },
                                         */
                                    {
                                        fieldLabel : '<span style="color:red">*</span>团队状态',
                                        hiddenName : 'teamstatus',
                                        forceSelection : true,
                                        resizable : true,
                                        allowBlank : false,
                                        xtype : 'combo',
                                        labelStyle : 'text-align:right;',
                                        triggerAction : 'all',
                                        mode : 'local',
                                        editable : false,
                                        store : teamStatusStore,
                                        valueField : 'myId',
                                        displayField : 'displayText',
                                        emptyText : '请选择',
                                        anchor : '90%'
                                    },
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        hidden : true,
                                        id : 'teamLeaderId',
                                        name : 'teamLeaderId',
                                        anchor : '90%'
                                    },
                                    new Com.yucheng.crm.common.OrgUserManage({ 
                                    	width : 200,
        								xtype:'userchoose',
        								fieldLabel : '负责人', 
        								id:'teamLeaderAdd',
        								labelStyle: 'text-align:right;',
        								name : 'teamLeader',
        								hiddenName:'teamLeaderId',
        								searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
        								singleSelect:true,
        								anchor : '90%',
        								callback : function() {
											var user_name = null;
											var org_id = null;
											user_name = Ext.getCmp('teamLeaderAdd').getValue();
											if (user_name != null
													&& user_name != '') {
												org_id = Ext.getCmp('teamLeaderAdd').orgId;
												addPlanForm.getForm().findField('organizationId').setValue(org_id);
											}
										}
        								}), {
                                        xtype : 'textfield',
                                        width : 200,
                                        fieldLabel : '负责人联系电话',
                                        labelStyle : 'text-align:right;',
                                        name : 'telephone',
                                        vtype : 'mobile',
                                        name : 'leadTelephone',
                                        anchor : '90%'
                                    }
                            ]
                        } ]
                    } ],
                    buttons : [
                            {
                                text : '保  存',
                                handler : function() {
                                    if (!addPlanForm.getForm().isValid()) {
                                        Ext.Msg.alert("系统提醒", "输入有误，请重新输入!");
                                        return false;
                                    }
                                    Ext.Ajax
                                            .request( {
                                                url : basepath + '/marketTeamAction.json?a=1',
                                                method : 'POST',
                                                form : addPlanForm.getForm().id,
                                                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                                success : checkResult,
                                                failure : checkResult
                                            });
                                    addPlanWindow.hide();
                                    function checkResult(response) {
                                        var resultArray = Ext.util.JSON
                                                .decode(response.status);
                                        var resultError = response.responseText;
                                        if ((resultArray == 200 || resultArray == 201)
                                                && resultError == '') {
                                            Ext.Msg.alert('提示', '操作成功');
                                            addPlanForm.getForm().reset();
                                            store.reload( {
                                                params : {
                                                    start : 0,
                                                    limit : bbar.pageSize
                                                }
                                            });
                                        } else {
                                            if (resultArray == 403) {
                                                Ext.Msg.alert('提示',
                                                        response.responseText);
                                            } else {
                                                Ext.Msg
                                                        .alert(
                                                                '提示',
                                                                '操作失败,失败原因:' + resultError);
                                                store.reload( {
                                                    params : {
                                                        start : 0,
                                                        limit : bbar.pageSize
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    ;
                                }
                            }, {
                                text : '取  消',
                                handler : function() {
                                    addPlanWindow.hide();
                                }
                            } ]
                });
        // 修改基本信息展示的form
        var editBasePlanForm = new Ext.form.FormPanel(
                {
                    labelWidth : 100,
                    height : 300,
                    frame : true,
                    region : 'center',
                    autoScroll : true,
                    buttonAlign : "center",
                    items : [ {
                        layout : 'column',
                        items : [ {
                            columnWidth : .9,
                            layout : 'form',
                            items : [
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        fieldLabel : '营销团队编号',
                                        hidden : true,
                                        labelStyle : 'text-align:right;',
                                        name : 'marketTeamId',
                                        anchor : '90%'
                                    },
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        fieldLabel : '营销团队名称',
                                        labelStyle : 'text-align:right;',
                                        name : 'marketTeamName',
                                        anchor : '90%'
                                    },
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        fieldLabel : '归属机构id',
                                        labelStyle : 'text-align:right;',
                                        name : 'organizationId',
                                        editable : false,
                                        hidden : true,
                                        anchor : '90%'
                                    },
                                    {
                                        fieldLabel : '团队状态',
                                        hiddenName : 'teamstatus',
                                        forceSelection : true,
                                        resizable : true,
                                        xtype : 'combo',
                                        labelStyle : 'text-align:right;',
                                        triggerAction : 'all',
                                        mode : 'local',
                                        store : teamStatusStore,
                                        valueField : 'myId',
                                        displayField : 'displayText',
                                        emptyText : '请选择',
                                        anchor : '90%'
                                    },
                                    {
                                        name : 'createUser',
                                        xtype : 'textfield',
                                        readOnly : true,
                                        hidden : true,
                                        fieldLabel : '创建人',
                                        labelStyle : 'text-align:right;',
                                        width : 100,
                                        anchor : '90%'
                                    },
                                    {
                                        name : 'createUserId',
                                        xtype : 'textfield',
                                        readOnly : true,
                                        fieldLabel : '创建人ID',
                                        hidden : true,
                                        labelStyle : 'text-align:right;',
                                        width : 100,
                                        anchor : '90%'
                                    },
                                    {
                                        name : 'createUserName',
                                        xtype : 'textfield',
                                        readOnly : true,
                                        hidden : true,
                                        fieldLabel : '创建人',
                                        labelStyle : 'text-align:right;',
                                        width : 100,
                                        anchor : '90%'
                                    },
                                    {
                                        name : 'createDate',
                                        xtype : 'textfield',
                                        fieldLabel : '团队创建时间',
                                        readOnly : true,
                                        hidden : true,
                                        labelStyle : 'text-align:right;',
                                        width : 100,
                                        anchor : '90%'
                                    },
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        hidden : true,
                                        id : 'createUserOrgId',
                                        name : 'createUserOrgId',
                                        anchor : '90%'
                                    },
                                    {
                                        xtype : 'textfield',
                                        width : 200,
                                        hidden : true,
                                        id : 'teamLeaderId1',
                                        name : 'teamLeaderId',
                                        anchor : '90%'
                                    },  new Com.yucheng.crm.common.OrgUserManage({ 
                                    	width : 200,
        								xtype:'userchoose',
        								fieldLabel : '负责人', 
        								id:'teamLeaderUp',
        								labelStyle: 'text-align:right;',
        								name : 'teamLeader',
        								hiddenName:'teamLeaderId',
        								searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
        								singleSelect:true,
        								anchor : '90%',
        								callback : function() {
											var user_name = null;
											user_name = Ext.getCmp('teamLeaderUp').getValue();
											if (user_name != null
													&& user_name != '') {
												org_id = Ext.getCmp('teamLeaderUp').orgId;
												addPlanForm.getForm().findField('organizationId').setValue(org_id);
											}
										}
        								}),
//                                    new Ext.ux.form.TeamManagerField(
//                                            {
//                                                width : 200,
//                                                fieldLabel : '负责人',
//                                                labelStyle : 'text-align:right;',
//                                                name : 'teamLeader',
//                                                id : 'teamLeader2',
//                                                anchor : '90%',
//                                                callback : function() {
//                                                    var unit_id = null;
//                                                    var unit_name = null;
//                                                    var cust_name = null;
//                                                    cust_name = Ext.getCmp(
//                                                            'teamLeader2')
//                                                            .getValue();
//                                                    if (cust_name != null
//                                                            && cust_name != '') {
//                                                        unit_id = Ext
//                                                                .getCmp('teamLeader2').unitId.aId[0];
//                                                        unit_name = Ext
//                                                                .getCmp('teamLeader2').unitName.unitName[0];
//                                                        editBasePlanForm
//                                                                .getForm()
//                                                                .findField(
//                                                                        'organizationId')
//                                                                .setValue(
//                                                                        unit_id);
//                                                        editBasePlanForm
//                                                                .getForm()
//                                                                .findField(
//                                                                        'organizationName')
//                                                                .setValue(
//                                                                        unit_name);
//                                                    }
//                                                }
//                                            }),
                                            {
                                        xtype : 'textfield',
                                        width : 200,
                                        fieldLabel : '负责人联系电话',
                                        labelStyle : 'text-align:right',
                                        name : 'leadTelephone',
                                        vtype : 'mobile',
                                        anchor : '90%'
                                    } ]
                        } ]
                    } ],
                    buttons : [
                            {
                                text : '保  存',
                                handler : function() {
                                    if (!editBasePlanForm.getForm().isValid()) {
                                        Ext.Msg.alert("系统提醒", "输入有误，请重新输入!");
                                        return false;
                                    }
                                    Ext.Ajax
                                            .request( {
                                                url : basepath + '/marketTeamAction.json?a=2',
                                                method : 'POST',
                                                form : editBasePlanForm
                                                        .getForm().id,
                                                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                                success : checkResult,
                                                failure : checkResult
                                            });
                                    editPlanWindow.hide();
                                    function checkResult(response) {
                                        var resultArray = Ext.util.JSON
                                                .decode(response.status);
                                        var resultError = response.responseText;

                                        if ((resultArray == 200 || resultArray == 201)
                                                && resultError == '') {
                                            Ext.Msg.alert('提示', '操作成功');

                                            store.reload( {
                                                params : {
                                                    start : 0,
                                                    limit : bbar.pageSize
                                                }
                                            });
                                        } else {
                                            if (resultArray == 403) {
                                                Ext.Msg.alert('提示',
                                                        response.responseText);
                                            } else {
                                                Ext.Msg
                                                        .alert(
                                                                '提示',
                                                                '操作失败,失败原因:' + resultError);
                                                store.reload( {
                                                    params : {
                                                        start : 0,
                                                        limit : bbar.pageSize
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    ;
                                }

                            }, {
                                text : '取  消',
                                handler : function() {
                                    editPlanWindow.hide();
                                }
                            } ]
                });

        // 展示详细信息窗口展示的from
        var showChanceForm = new Ext.form.FormPanel( {
            labelWidth : 100,
//            height : 500,
            frame : true,
            region : 'center',
            autoScroll : true,
            buttonAlign : "center",
            items : [ {
                layout : 'column',
                items : [ {
                    columnWidth : .5,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '营销团队编号',
                        labelStyle : 'text-align:right;',
                        name : 'marketTeamId',
                        anchor : '90%'
                    }, {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '营销团队名称',
                        labelStyle : 'text-align:right;',
                        name : 'marketTeamName',
                        anchor : '90%'
                    }, {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '归属机构',
                        labelStyle : 'text-align:right;',
                        name : 'organizationName',
                        anchor : '90%'
                    }, {
                        name : 'createDate',
                        labelStyle : 'text-align:right;',
                        xtype : 'textfield',
                        fieldLabel : '团队创建时间',
                        width : 100,
                        anchor : '90%'
                    }, {
                        name : 'createUserName',
                        xtype : 'textfield',
                        fieldLabel : '创建人',
                        labelStyle : 'text-align:right;',
                        width : 100,
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .5,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '负责人',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'teamLeader',
                        anchor : '90%'
                    }, {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '负责人联系电话',
                        labelStyle : 'text-align:right;',
                        name : 'leadTelephone',
                        anchor : '90%'
                    }, {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '团队成员数',
                        name : 'teamNumber',
                        labelStyle : 'text-align:right;',
                        anchor : '90%'
                    }, {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '团队客户数',
                        labelStyle : 'text-align:right;',
                        name : 'custNumber',
                        anchor : '90%'
                    }, {
                        fieldLabel : '团队状态',
                        hiddenName : 'teamstatus',
                        forceSelection : true,
                        resizable : true,
                        xtype : 'combo',
                        labelStyle : 'text-align:right;',
                        triggerAction : 'all',
                        mode : 'local',
                        store : teamStatusStore,
                        valueField : 'myId',
                        displayField : 'displayText',
                        emptyText : '请选择',
                        anchor : '90%'
                    } ]
                } ]
            } ]

        });

        // 修改窗口展示的from
        var editPlanPanel = new Ext.Panel( {
            labelWidth : 80,
            height : 300,
            layout : 'fit',
            autoScroll : true,
            buttonAlign : "center",
            items : [ editBasePlanForm ]
        });

        // 定义展示窗口的tabPanel
        var tokenDelimiter = ':';
        var editTp = new Ext.TabPanel(
                {
                    id : 'editPlanTabs',
                    activeTab : 0,
                    tabPosition : 'bottom',
                    items : [
                            {
                                title : '基本信息',
                                id : 'baseInfo',
                                selected : true,
                                layout:'fit',
                                items : [ showChanceForm ]
                            },
                            {
                                title : '成员信息',
                                listeners : {
                                    'activate' : function() {
                                        var sujm = listPanel
                                                .getSelectionModel().selections.items[0].data.marketTeamId;
                                        Ext.getCmp('MKT_TEAM_ID1').setValue(
                                                sujm);
                                    }
                                },
                                layout:'fit',
                                items : [{
                                	layout:'border',
                                	items:[ yuangongSearch, SMEOCyPanel]
                                }
                                         ]
                            },
                            {
                                title : '客户信息',
                                listeners : {
                                    'activate' : function() {
                                        var sujm = listPanel
                                                .getSelectionModel().selections.items[0].data.marketTeamId;
                                        Ext.getCmp('MKT_TEAM_ID2').setValue(
                                                sujm);
                                    }
                                },
                                layout:'fit',
                                items : [{
                                	layout:'border',
                                	items:[kehuSearch, SMEOPanel]
                                }  ]
                            } ]

                });

        editTp.setActiveTab("baseInfo");

        // 展示详细信息的from
        var showdetailPlanPanel = new Ext.Panel( {
            labelWidth : 300,
            height : 400,
            layout : 'fit',
            autoScroll : true,
            buttonAlign : "center",
            items : [ editTp ]
        });

        // 定义新增窗口
        var addPlanWindow = new Ext.Window( {
            title : '营销团队信息新增',
            plain : true,
            layout : 'fit',
            width : 500,
            height : 300,
            resizable : true,
            draggable : true,
            closable : true,
            closeAction : 'hide',
            modal : true, // 模态窗口
            loadMask : true,
            maximizable : true,
            collapsible : true,
            titleCollapse : true,
            buttonAlign : 'right',
            border : false,
            items : [ addPlanForm ]
        });

        // 定义修改窗口
        var editPlanWindow = new Ext.Window( {
            title : '营销团队信息修改',
            plain : true,
            layout : 'fit',
            width : 500,
            height : 300,
            resizable : true,
            draggable : true,
            closable : true,
            closeAction : 'hide',
            modal : true, // 模态窗口
            loadMask : true,
            maximizable : true,
            collapsible : true,
            titleCollapse : true,
            border : false,
            items : [ editPlanPanel ]
        });

        // 定义详细信息窗口
        var showChanceWindow = new Ext.Window( {
            title : '营销团队详细信息',
            plain : true,
            layout : 'fit',
            width : 700,
            height : 450,
            resizable : true,
            draggable : true,
            closable : true,
            closeAction : 'hide',
            modal : true, // 模态窗口
            loadMask : true,
            maximizable : true,
            collapsible : true,
            titleCollapse : true,
            border : false,
            constrain : true,
            animCollapse : true,
            buttonAlign : 'center',
            items : [ showdetailPlanPanel ],
            buttons : [ {
                text : '关闭',

                handler : function() {
                    yuangongStore.removeAll();
                    kehuStore.removeAll();
                    showChanceWindow.hide();
                    store.reload( {
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
                }
            } ]
        });

        // 展示新增窗口
        function addInit() {
            addPlanWindow.show();

        }

        // 展示新增窗口
        function addMInit() {
            addMPlanWindow.show();

        }

        // 展示修改窗口
        function editInit() {
            editPlanWindow.show();
        }

        // 展示详细信息窗口
        function showInit() {
            showChanceWindow.show();
        }
        var viewport = new Ext.Viewport({
            layout : 'fit',
            items : [ {
                layout : 'border',
                items : [searchPanel,listPanel]
            } ]
        });
    });