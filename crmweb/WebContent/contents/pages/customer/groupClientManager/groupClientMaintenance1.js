Ext.onReady(function() {
    // *********************

        var blocMemberTree2 = new Ext.tree.TreePanel(
                {
                    autoScroll : true,
                    height : 350,
                    // width:groupCreditAddForm.getForm().findField("groupCreditName").getWidth(),
                    width : 220,
                    listeners : {
                        'click' : function(node) {
                            groupCreditAddForm.getForm().findField(
                                    "groupCreditName").setValue(node.text);
                        }
                    },
                    root : new Ext.tree.AsyncTreeNode(
                            {
                                // autoScroll:true,
                                expanded : true,
                                leaf : false,
                                loader : new Ext.tree.TreeLoader(
                                        {
                                            url : basepath + '/client-group-tree.json',
                                            requestMethod : 'GET',
                                            listeners : {
                                                'beforeload' : function(loader) {
                                                    var data = listPanel
                                                            .getSelectionModel().selections.items[0].data;
                                                    var groupNo = data.groupNo;
                                                    loader.baseParams = {
                                                        'condition' : '{"group_no":"' + groupNo + '"}'
                                                    };
                                                }
                                            }
                                        })
                            }),
                    animate : false,
                    useArrows : false,
                    border : false
                });

        // 集团状态
        var JTKHZTStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=GROUP_STS'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

        // 集团类型
        var JTKHLXStore = new Ext.data.Store( {
            restful : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=GROUP_TYP'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

        Ext.QuickTips.init();
        var searchPanel = new Ext.form.FormPanel( {
            title : "集团客户管理->集团客户查询",
            labelWidth : 100,
            labelAlign : 'right',
            height : 100,
            id : 'searchPanel',
            frame : true,
            region : 'north',
            autoScroll : true,
            layout : 'column',
            items : [ {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    fieldLabel : '集团编号',
                    id : 'GROUP_NO',
                    name : 'GROUP_NO',
                    anchor : '100%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    fieldLabel : '集团名称',
                    id : 'GROUP_NAME',
                    name : 'GROUP_NAME',
                    anchor : '100%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    fieldLabel : '集团母公司名称',
                    id : 'CUST_ZH_NAME',
                    name : 'CUST_ZH_NAME',
                    anchor : '100%'
                } ]
            }, {
                name : "GROUP_HOST_ORG_NO",
                hidden : true,
                xtype : 'textfield'
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    store : JTKHLXStore,
                    xtype : 'combo',
                    name : 'GROUP_TYPE',
                    fieldLabel : '集团类型',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    mode : 'local',
                    typeAhead : true,
                    forceSelection : true,
                    resizable : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    anchor : '100%'
                } ]
            } ],
            buttonAlign : 'center',
            buttons : [ {
                text : '查询',
                handler : function() {
                    var conditionStr = searchPanel.getForm().getFieldValues();
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
                // alert("选中2级节点统计: "+checkLevel );
                return checkLevel;

            } else if (num == 0) {

                var nodeNum3 = 0;
                var tempChecked3Node;
                for ( var b = 0; b < level3Nodes.length; b++) {
                    if (level3Nodes[b].getUI().isChecked()) {
                        tempChecked3Node = level3Nodes[b];
                        nodeNum3++;
                        if (nodeNum3 >= 2) {
                            // alert("2级没选,三级选择有跨行: "+4);
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
        var orgTreePanel2 = new Ext.tree.TreePanel( {
            autoScroll : true,
            height : 350,
            width : 200,
            listeners : {
                'click' : function(node) {
                    node.getUI().toggleCheck(true);
                    var level = spanBank(orgTreePanel2);
                    var checkedNodes = getCheckedNode(orgTreePanel2);
                    tempCombo = editBasePlanForm.getForm().findField(
                            "groupHostOrgNoName");
                    editBasePlanForm.getForm().findField("groupHostOrgNo")
                            .setValue(node.id);
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
                id : JsContext._orgId,
                text : JsContext._unitname,
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

        // *********************************
        var groupCreditAddForm = new Ext.form.FormPanel(
                {
                    title : '授信发起',
                    labelWidth : 120,
                    region : 'north',
                    height : 100,
                    frame : true,
                    split : true,
                    labelAlign : 'right',
                    autoScroll : true,
                    buttonAlign : "center",
                    items : [ {
                        layout : 'column',
                        items : [
                                {
                                    columnWidth : .5,
                                    layout : 'form',
                                    items : [
                                            {
                                                xtype : 'textfield',
                                                editable : true,
                                                readOnly : true,
                                                name : 'groupNo',
                                                triggerAction : 'all',
                                                anchor : '90%',
                                                fieldLabel : '集团客户编号'
                                            },
                                            {
                                                xtype : 'textfield',
                                                triggerAction : 'all',
                                                anchor : '90%',
                                                readOnly : true,
                                                fieldLabel : '集团客户名称',
                                                name : 'groupName'
                                            },
                                            {
                                                xtype : 'textfield',
                                                fieldLabel : '集团母公司名称',
                                                anchor : '90%',
                                                readOnly : true,
                                                name : 'groupRootName'
                                            },
                                            {
                                                xtype : 'combo',
                                                store : new Ext.data.SimpleStore(
                                                        {
                                                            fields : [],
                                                            data : [ [] ]
                                                        }),
                                                name : 'groupCreditName',
                                                emptyText : '请选择',
                                                fieldLabel : '*本批次集团授信主体',
                                                editable : false,
                                                allowBlank : false,
                                                // resizable:true,
                                                anchor : '90%',
                                                mode : 'local',
                                                triggerAction : 'all',
                                                maxHeight : 390,
                                                // 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
                                                tpl : "<tpl for='.' <div style='height:350px;'> <div id='blocMemberTree2'></div></div></tpl>",
                                                // tpl:"<tpl for='.' <div
                                                // style='height:390px'> <div
                                                // id='addOrgTreeDivForAdd'></div></div></tpl>",
                                                onSelect : Ext.emptyFn,
                                                listeners : {
                                                    'expand' : function(combo) {
                                                        blocMemberTree2
                                                                .render('blocMemberTree2');
                                                        blocMemberTree2.root
                                                                .reload();
                                                    },
                                                    'collapse' : function(combo) {
                                                        var rawValue = combo
                                                                .getRawValue();
                                                        if (rawValue
                                                                .indexOf("待审批") >= 0) {
                                                            Ext.MessageBox
                                                                    .alert(
                                                                            '提示',
                                                                            '待审批状态的成员不能做为授信主体.',
                                                                            function() {
                                                                                combo
                                                                                        .setRawValue("");
                                                                            });
                                                            return;
                                                        }
                                                    }
                                                }
                                            }, {
                                                name : 'groupRootId',
                                                hidden : true,
                                                xtype : 'textfield'
                                            }, {
                                                name : 'creditStatus',
                                                hidden : true,
                                                xtype : 'textfield',
                                                value : '01'
                                            }, {
                                                xtype : 'textfield',
                                                fieldLabel : '*授信批次号',
                                                anchor : '90%',
                                                // disabled:true,
                                                readOnly : true,
                                                name : 'batchNo'
                                            } ]
                                }, {
                                    columnWidth : .5,
                                    layout : 'form',
                                    items : [ {
                                        xtype : 'textfield',
                                        fieldLabel : '集团母公司注册地址',
                                        name : 'groupRootAddress',
                                        readOnly : true,
                                        anchor : '90%'

                                    }, new Com.yucheng.bcrm.common.OrgField( {
                                        searchType : 'SUBTREE',/*
                                                                 * 指定查询机构范围属性
                                                                 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
                                                                 * （所有父、祖机构）ALLORG（所有机构）
                                                                 */
                                        fieldLabel : '*授信主办行',
                                        allowBlank : false,
                                        labelStyle : 'text-align:right;',
                                        id : 'CUST_ORG', // 放大镜组件ID，用于在重置清空时获取句柄
                                        name : 'hostOrgName',
                                        hiddenName : 'hostOrgId', // 后台获取的参数名称
                                        anchor : '90%',
                                        checkBox : true
                                    // 复选标志
                                            }), {
                                        xtype : 'datefield',
                                        fieldLabel : '*授信发起日期',
                                        anchor : '90%',
                                        allowBlank : false,
                                        name : 'creditStartDate',
                                        format : 'Y-m-d'
                                    }, {
                                        xtype : 'datefield',
                                        fieldLabel : '*授信终止日期',
                                        format : 'Y-m-d',
                                        allowBlank : false,
                                        anchor : '90%',
                                        name : 'creditEndDate'
                                    } ]
                                }

                        ]
                    } ]

                });
        var groupCreditAddWindow = new Ext.Window(
                {
                    layout : 'fit',
                    width : 800,
                    height : 400,
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
                    constrain : true,
                    listeners : {
                        'beforeshow' : function() {
                            groupCreditAddForm.getForm().findField(
                                    "groupCreditName").setRawValue("");
                            groupCreditAddForm.getForm().findField(
                                    "hostOrgName").setRawValue("");
                            groupCreditAddForm.getForm().findField(
                                    "creditStartDate").setValue("");
                            groupCreditAddForm.getForm().findField(
                                    "creditEndDate").setValue("");
                        }

                    },
                    items : [ groupCreditAddForm ],
                    buttons : [
                            {
                                text : '保存',

                                handler : function(button) {
                                    if (!groupCreditAddForm.getForm().isValid()) {
                                        Ext.MessageBox.alert('提示',
                                                '输入有误,请检查输入项');
                                        return false;
                                    }

                                    var startDate = groupCreditAddForm
                                            .getForm().findField(
                                                    "creditStartDate").value;
                                    var endDate = groupCreditAddForm.getForm()
                                            .findField("creditEndDate").value;
                                    var today = new Date();
                                    var month0=today.getMonth() + 1;
                                    var date0=today.getDate();
                                    if (date0 < 10) {
                                    	date0="0"+date0;
                                    }
                                    if(month0<10){
                                    	month0="0"+month0;
                                    }
                                    var todayDate = today.getYear() + "-"
                                                + month0+ "-"
                                                + date0;
                                    if (startDate < todayDate) {
                                        Ext.MessageBox.alert('提示',
                                                '发起日期不能小于当前日期');
                                        return;
                                    }
                                    if (startDate >= endDate) {
                                        Ext.MessageBox.alert('提示',
                                                '终止日期应该在发起日期之后');
                                        return;
                                    }
                                    button.setDisabled(true);
                                    Ext.Ajax
                                            .request( {
                                                url : basepath + '/group-credit-manage.json?a=1',
                                                form : groupCreditAddForm
                                                        .getForm().id,
                                                method : 'POST',
                                                success : checkResult,
                                                failure : checkResult
                                            });
                                    function checkResult(response) {
                                        button.setDisabled(false);
                                        var resultArray = Ext.util.JSON
                                                .decode(response.status);
                                        var resultError = response.responseText;
                                        if ((resultArray == 200 || resultArray == 201)
                                                && resultError == '') {
                                            Ext.Msg
                                                    .alert(
                                                            '提示',
                                                            '操作成功',
                                                            function() {

                                                                window.location.href = basepath
                                                                        + "/contents/pages/blocCredit/groupCreditStartAndEndList.jsp";
                                                            });
                                            return;
                                        }
                                        if (resultArray == 403) {
                                            Ext.Msg.alert('提示',
                                                    response.responseText,
                                                    function() {
                                                        return;
                                                    });
                                        } else {

                                            var exception = resultError
                                                    .split("#sena#")[1];
                                            if (exception == "already") {
                                                Ext.Msg.alert('提示',
                                                        '操作失败:授信批次重复',
                                                        function() {
                                                            return;
                                                        });
                                            } else {
                                                Ext.Msg.alert('提示',
                                                        '操作失败:' + resultError,
                                                        function() {
                                                            return;
                                                        });
                                            }
                                        }
                                    }
                                    ;
                                }
                            }, {
                                text : '返回',
                                handler : function() {
                                    groupCreditAddWindow.hide();

                                }
                            } ]
                });

        // 定义自动当前页行号
        var rownum1 = new Ext.grid.RowNumberer( {
            header : 'No.',
            width : 28
        });

        // 复选框
        var windowsm1 = new Ext.grid.CheckboxSelectionModel();

        // 列模型
        var columns = new Ext.grid.ColumnModel( [ rownum1, windowsm1, {
            header : '隐藏ID', // 列标题
            hidden : true,
            dataIndex : 'id', // 数据索引:和Store模型对应
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团编号', // 列标题
            dataIndex : 'groupNo', // 数据索引:和Store模型对应
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团名称', // 列标题
            dataIndex : 'groupName', // 数据索引:和Store模型对应
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团母公司名称', // 列标题
            dataIndex : 'groupRootCustName1', // 数据索引:和Store模型对应
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团母公司ID', // 列标题
            dataIndex : 'groupRootCustId', // 数据索引:和Store模型对应
            hidden : true,
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '授信主办行ID', // 列标题
            dataIndex : 'groupHostOrgNo', // 数据索引:和Store模型对应
            hidden : true,
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '授信主办行', // 列标题
            dataIndex : 'groupHostOrgNoName', // 数据索引:和Store模型对应
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团类型', // 列标题
            dataIndex : 'GROUP_TYPE_ORA', // 数据索引:和Store模型对应
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团简介', // 列标题
            dataIndex : 'groupMemo', // 数据索引:和Store模型对应
            hidden : true,
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团母公司注册地址', // 列标题
            dataIndex : 'groupRootAdress', // 数据索引:和Store模型对应
            hidden : true,
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团规模', // 列标题
            dataIndex : 'groupSize', // 数据索引:和Store模型对应
            hidden : true,
            sortable : true,// 是否可排序
            width : 150
        }, {
            header : '集团状态',
            dataIndex : 'GROUP_STATUS_ORA',
            sortable : true,
            hidden : true,
            width : 150
        }, {
            header : '成员总数',
            sortable : true,// 是否可排序
            align : 'right',
            dataIndex : 'groupNumber'
        }, {
            header : '正式成员数',
            align : 'right',
            sortable : true,// 是否可排序
            dataIndex : 'zhengshiNo'
        }, {
            header : '待审成员数',
            align : 'right',
            sortable : true,// 是否可排序
            dataIndex : 'daishenNo'
        }, {
            header : '对外担保成员数',
            align : 'right',
            sortable : true,// 是否可排序
            dataIndex : 'dwdbNo'
        }, {
            header : '创建人工号',
            sortable : true,// 是否可排序
            dataIndex : 'createUserId'
        }, {
            header : '创建人姓名',
            sortable : true,// 是否可排序
            dataIndex : 'createUserName'
        }, {
            header : '创建人姓名所属机构号',
            sortable : true,// 是否可排序
            hidden : true,
            dataIndex : 'createUserOrgId'
        }, {
            header : '修改人',
            hidden : true,
            sortable : true,// 是否可排序
            dataIndex : 'updateUserId'
        }, {
            header : '创建日期',
            sortable : true,// 是否可排序
            // hidden :true,
            dataIndex : 'createDate'
        } ]);

        var record = Ext.data.Record.create( [ {
            name : 'id',
            mapping : 'ID'
        }, {
            name : 'groupNo',
            mapping : 'GROUP_NO'
        }, {
            name : 'groupName',
            mapping : 'GROUP_NAME'
        }, {
            name : 'groupRootCustId',
            mapping : 'GROUP_ROOT_CUST_ID'
        }, {
            name : 'groupHostOrgNo',
            mapping : 'GROUP_HOST_ORG_NO'
        }, {
            name : 'groupRootCustName1',
            mapping : 'CUST_ZH_NAME'
        }, {
            name : 'updateDate',
            mapping : 'UPDATE_DATE'
        }, {
            name : 'groupNumber',
            mapping : 'GROUP_NUMBER',
            type : 'float'
        }, {
            name : 'groupHostOrgNoName',
            mapping : 'GROUP_HOST_ORG_NO_NAME'
        }, {
            name : 'updateUserId',
            mapping : 'UPDATE_USER_ID'
        }, {
            name : 'groupRootAdress',
            mapping : 'GROUP_ROOT_ADDRESS'
        }, {
            name : 'groupMemo',
            mapping : 'GROUP_MEMO'
        }, {
            name : 'groupType',
            mapping : 'GROUP_TYPE'
        }, {
            name : 'groupTypeId',
            mapping : 'GROUP_TYPE'
        }, {
            name : 'dwdbNo',
            mapping : 'DWDB_NO',
            type : 'float'
        }, {
            name : 'zhengshiNo',
            mapping : 'ZHENGSHI_NO',
            type : 'float'
        }, {
            name : 'daishenNo',
            mapping : 'DAISHEN_NO',
            type : 'float'
        }, {
            name : 'groupStatus',
            mapping : 'GROUP_STATUS'
        }, {
            name : 'GROUP_STATUS_ORA'
        }, {
            name : 'GROUP_TYPE_ORA'
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
            mapping : 'CREATA_DATE'
        } ]);

        var store = new Ext.data.Store( {
            id : 'DDD',
            restful : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/ClientGroupInfoQueryAction.json',
                failure : function(response) {
                    var resultArray = Ext.util.JSON.decode(response.status);
                    if (resultArray == 403) {
                        Ext.Msg.alert('提示', response.responseText);
                    }
                }
            }),
            reader : new Ext.data.JsonReader( {
                successProperty : 'success',
                idProperty : 'ID',
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
            editable : false,
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

        var listPanel = new Ext.grid.GridPanel(
                {

                    id : 'listPanel1',
                    title : "集团客户信息表",
                    store : store,
                    frame : true,
                    sm : windowsm1,
                    cm : columns,
                    stripeRows : true,
                    tbar : [
                            {
                                text : '新建集团',
                                iconCls : 'addIconCss',
                                handler : function() {
                                    // if(__units!="00001"){
                                // Ext.Msg.alert("系统提示","对不起，您不是总行人员，没有操作权限！");
                                // return false;
                                // }
                                // addGroupInit();
                                addInit();
                            }
                            },
                            '-',
                            {
                                text : '修改集团',
                                iconCls : 'editIconCss',
                                handler : function() {

                                    var selectLength = listPanel
                                            .getSelectionModel()
                                            .getSelections().length;

                                    var selectRe = listPanel
                                            .getSelectionModel()
                                            .getSelections()[0];

                                    if (selectLength != 1) {
                                        Ext.Msg.alert("系统提醒", "请选择一条记录！");
                                    } else {
                                        // if(__units!="00001"){
                                        // Ext.Msg.alert("系统提示","对不起，您不是总行人员，没有操作权限！");
                                        // return false;
                                        // }
                                        editGroupInfoForm.getForm().loadRecord(
                                                selectRe);
                                        document.getElementById('idStr').value = selectRe.data.id;
                                        editInit();

                                    }
                                }

                            },
                            '-',
                            {
                                text : '集团视图',
                                iconCls : 'custGroupMemIconCss',
                                handler : function() {
                                    var selectLength = listPanel
                                            .getSelectionModel()
                                            .getSelections().length;
                                    if (selectLength != 1) {
                                        Ext.Msg.alert("系统提醒", "请选择一条记录！");
                                    } else {
                                        var groupNo = listPanel
                                                .getSelectionModel().selections.items[0].data.groupNo;// 获得选中当前列的集团客户ID
                                        var hostOrgNo = listPanel
                                                .getSelectionModel().selections.items[0].data.groupHostOrgNo;
                                        window.location.href = 'groupView.jsp?groupNo='
                                                + groupNo
                                                + '&hostOrgNo='
                                                + hostOrgNo;
                                    }
                                }
                            },
                            '-',
                            {
                                text : '集团成员维护',
                                iconCls : 'maintainIconCss',
                                handler : function() {
                                    var selectLength = listPanel.getSelectionModel().getSelections().length;
                                    if (selectLength != 1) {
                                        Ext.Msg.alert("系统提醒", "请选择一条记录！");
                                        return;
                                    }
                                    var groupNo = listPanel.getSelectionModel().selections.items[0].data.groupNo;// 获得选中当前列的集团客户ID
                                    var id = listPanel.getSelectionModel().selections.items[0].data.id;// 获得选中当前列的集团客户ID
                                    var groupName = listPanel
                                            .getSelectionModel().selections.items[0].data.groupName;// 获得选中当前列的集团客户名称
                                    window.location.href = 'memberMaintenance.jsp?groupNo='
                                            + groupNo
                                            + '&groupName='
                                            + groupName + '&id=' + id;
                                }
                            },
                            '-',
                            {
                                text : '集团授信发起',
                                iconCls : 'publishIconCss',
                                handler : function() {
                                    var selectLength = listPanel
                                            .getSelectionModel()
                                            .getSelections().length;
                                    if (selectLength != 1) {
                                        Ext.Msg.alert("系统提醒", "请选择一条记录！");
                                        return;
                                    }
                                    var today = new Date();
                                    if (today.getDate() < 10) {
                                        var batchNo = today.getYear() + ""
                                                + (today.getMonth() + 1) + "0"
                                                + today.getDate();
                                    } else {
                                        var batchNo = today.getYear() + ""
                                                + (today.getMonth() + 1) + ""
                                                + today.getDate();
                                    }
                                    var data = listPanel.getSelectionModel().selections.items[0].data;
                                    var groupNo = data.groupNo;// 获得选中当前列的集团客户ID
                                    var id = data.id;// 获得选中当前列的集团客户ID
                                    var groupName = data.groupName;// 获得选中当前列的集团客户名称
                                    var groupRootName = data.groupRootCustName1;
                                    var groupRootId = data.groupRootCustId;
                                    var groupRootAddress = data.groupRootAdress;
                                    var hostOrgName = data.groupHostOrgNoName;
                                    var hostOrgId = data.groupHostOrgNo;
                                    var groupType = data.GROUP_TYPE_ORA;

                                    blocMemberTree2.root.setId(groupNo);
                                    blocMemberTree2.root.setText(groupName);
                                    groupCreditAddForm.getForm().findField(
                                            "groupNo").setValue(groupNo);
                                    groupCreditAddForm.getForm().findField(
                                            "groupName").setValue(groupName);
                                    groupCreditAddForm.getForm().findField(
                                            "groupRootName").setValue(
                                            groupRootName);
                                    // groupCreditAddForm.getForm().findField("hostOrgId").setValue(hostOrgId);
                                    // groupCreditAddForm.getForm().findField("hostOrgName").setValue(hostOrgName);
                                    // Ext.getCmp("CUST_ORG").hiddenField.setValue(hostOrgId);
                                    Ext.getCmp("CUST_ORG").setValue(hostOrgId);
                                    groupCreditAddForm.getForm().findField(
                                            "batchNo").setValue(batchNo);
                                    groupCreditAddForm.getForm().findField(
                                            "groupRootId")
                                            .setValue(groupRootId);

                                    groupCreditAddWindow.show();
                                }
                            },
                            '-',
                            new Com.yucheng.bob.ExpButton(
                                    {
                                        formPanel : 'searchPanel',
                                        iconCls : 'exportIconCss',
                                        url : basepath + '/ClientGroupInfoQueryAction.json'
                                    }) ],
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

        listPanel
                .on(
                        'rowdblclick',
                        function(listPanel, rowIndex, event) {
                            var checkedNodes = listPanel.getSelectionModel().selections.items;
                            if (checkedNodes.length == 0) {
                                Ext.Msg.alert('提示', '未选择任何客户');
                                return;
                            }

                            var groupNo = listPanel.getSelectionModel().selections.items[0].data.groupNo;// 获得选中当前列的集团客户ID
                            var groupName = listPanel.getSelectionModel().selections.items[0].data.groupName;// 获得选中当前列的集团客户ID

                            window.location.href = 'groupView.jsp?groupNo='
                                    + groupNo + '&groupName=' + groupName;

                        });

        var rollTypeStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=ROLL_TYPE'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        // 修改基本信息展示的form
        var editGroupInfoForm = new Ext.form.FormPanel( {
            labelWidth : 120,
            frame : true,
            region : 'center',
            autoScroll : true,
            labelAlign : 'right',
            buttonAlign : "center",
            layout : 'form',
            items : [ {
                xtype : 'textfield',
                readOnly : true,
                name : 'groupRootCustName1',
                triggerAction : 'all',
                anchor : '95%',
                fieldLabel : '集团母公司名称'
            }, {
                columnWidth : .25,
                xtype : 'textfield',
                editable : true,
                name : 'groupRootCustId',
                triggerAction : 'all',
                hidden : true,
                anchor : '95%',
                fieldLabel : '集团母公司ID'
            }, {
                xtype : 'textfield',
                editable : true,
                name : 'groupName',
                triggerAction : 'all',
                anchor : '95%',
                fieldLabel : '集团客户名称'
            }, {
                xtype : 'textfield',
                fieldLabel : '集团客户编号',
                name : 'groupNo',
                hidden : true,
                anchor : '95%'
            }, {
                xtype : 'textfield',
                fieldLabel : '集团母公司注册地址',
                readOnly : true,
                name : 'groupRootAdress',
                anchor : '95%'
            }, {
                name : "groupHostOrgNo",
                hidden : true,
                xtype : 'textfield'
            }/*
                 * , { columnWidth : .25, layout : 'form', items : [ {
                 * xtype:'combo', store : new Ext.data.SimpleStore( { fields :
                 * [], data : [ [] ] }), name:'groupHostOrgNoName', //
                 * id:'GROUP_HOST_ORG_NO_NAME', emptyText : '请选择', fieldLabel :
                 * '授信主办行', editable:false, resizable:true, anchor : '90%', mode :
                 * 'local', triggerAction : 'all', maxHeight : 390, //
                 * 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器 tpl:"<tpl for='.' <div
                 * style='height:390px'> <div id='addOrgTreeDivForAdd1'></div></div></tpl>", //
                 * tpl:"<div style='height:390px'> <div
                 * id='addOrgTreeDivForAdd'></div></div>", onSelect :
                 * Ext.emptyFn, listeners:{ 'expand':function(combo){ //
                 * combo.doLayout();
                 * orgTreePanel1.render('addOrgTreeDivForAdd1'); } } } ] }
                 */, {
                xtype : 'hidden',
                fieldLabel : '隐藏ID',
                name : 'id',
                hiddenName : 'id',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏的创建者ID',
                name : 'createUserId',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏的创建者姓名',
                name : 'createUserName',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏的创建者机构号',
                name : 'createUserOrgId',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏创建日期',
                name : 'createDate',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏的修改者ID',
                name : 'updateUserId',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏的修改者姓名',
                name : 'updateUserName',
                anchor : '95%'
            }, {
                xtype : 'hidden',
                fieldLabel : '隐藏修改日期',
                name : 'updateDate',
                anchor : '95%'
            }, {
                store : JTKHLXStore,
                xtype : 'combo',
                name : 'groupType',
                hiddenName : 'groupType',
                fieldLabel : '集团类型',
                valueField : 'key',
                displayField : 'value',
                editable : false,
                mode : 'local',
                typeAhead : true,
                forceSelection : true,
                resizable : true,
                triggerAction : 'all',
                emptyText : '请选择',
                selectOnFocus : true,
                width : '100',
                anchor : '95%'
            }, {
                layout : 'form',
                items : [ {
                    store : JTKHZTStore,
                    xtype : 'combo',
                    name : 'groupStatus',
                    hiddenName : 'groupStatus',
                    fieldLabel : '集团客户状态',
                    valueField : 'key',
                    displayField : 'value',
                    mode : 'local',
                    hidden : true,
                    typeAhead : true,
                    forceSelection : true,
                    resizable : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    width : '100',
                    anchor : '95%'
                }, {
                    xtype : 'textarea',
                    fieldLabel : '公司简介',
                    name : 'groupMemo',
                    maxLength : '300',
                    minLength : '0',
                    anchor : '95%'
                } ]
            } ],
            buttons : [
                    {
                        text : '保  存',
                        handler : function() {
//                            if (__units != "00001") {
//                                Ext.Msg.alert("系统提示", "对不起，您不是总行人员，没有操作权限！");
//                                return false;
//                            }
                            Ext.Ajax.request( {
                                url : basepath + '/GroupInfoAction.json?a=2',
                                method : 'POST',
                                // params:editGroupInfoForm.getForm().getFieldValues(),
                                form : editGroupInfoForm.getForm().id,
                                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                success : checkResult,
                                failure : checkResult
                            });
                            editGroupInfoWindow.hide();
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
                                        Ext.Msg.alert('提示',
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
                            editGroupInfoWindow.hide();
                        }
                    } ]

        });
        // 修改窗口展示的from
        var editGroupInfoPanel = new Ext.Panel( {
            labelWidth : 80,
            height : 300,
            layout : 'fit',
            autoScroll : true,
            buttonAlign : "center",
            items : [ editGroupInfoForm ]
        });

        // 定义修改窗口
        var editGroupInfoWindow = new Ext.Window( {
            title : '集团客户信息修改',
            plain : true,
            layout : 'fit',
            width : 750,
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
            items : [ editGroupInfoPanel ]
        });

        // // 展示新增窗口
        // function addGroupInit() {
        // addGroupCustInfoWindow.show();
        // }
        // 展示修改窗口
        function editInit() {
            editGroupInfoWindow.show();
        }

        // ***************************************************************************

        var record = Ext.data.Record.create( [ {
            name : 'id',
            mapping : 'ID'
        }, {
            name : 'custZzdm',
            mapping : 'CUST_ZZDM'
        }, {
            name : 'groupRootAdress',
            mapping : 'ZC_ADDR'
        }, {
            name : 'groupHostOrgNo',
            mapping : 'GROUP_HOST_ORG_NO'
        }, {
            name : 'groupRootCustName1',
            mapping : 'CUST_ZH_NAME'
        }, {
            name : 'groupRootCustId',
            mapping : 'CUST_ID'
        } ]);

        var Sstore = new Ext.data.Store( {
            restful : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/groupmaininfo.json'
            }),
            reader : new Ext.data.JsonReader( {
                totalProperty : 'json.count',
                root : 'json.data'
            }, record)
        });

        // ***************************************

        // 复选框
        var sm = new Ext.grid.CheckboxSelectionModel();

        // 定义自动当前页行号
        var rownum = new Ext.grid.RowNumberer( {
            header : 'No.',
            width : 28
        });
        // 定义列模型
        // var cm = new Ext.grid.ColumnModel([rownum,
        // {header : '集团母公司编号',dataIndex : 'custId',width : 250,sortable :
        // true,hidden :true},
        // {header : '组织机构代码',dataIndex : 'custZzdm',sortable : true,width :
        // 200},
        // {header : '客户名称',dataIndex : 'groupRootCustName1',sortable :
        // true,width : 200},
        // {header : '集团母公司注册地址',dataIndex : 'groupRootAdress',sortable :
        // true,width : 200},
        // {header : 'id', dataIndex : 'id',sortable : true,width : 150,sortable
        // : true,hidden :true}
        // ]);

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
            forceSelection : true,
            width : 85
        });
        var number = parseInt(pagesize_combo.getValue());
        pagesize_combo.on("select", function(comboBox) {
            bbar.pageSize = parseInt(pagesize_combo.getValue()), Sstore.load( {
                params : {
                    start : 0,
                    limit : parseInt(pagesize_combo.getValue())
                }
            });
        });
        var bbar = new Ext.PagingToolbar( {
            pageSize : number,
            store : Sstore,
            displayInfo : true,
            displayMsg : '显示{0}条到{1}条,共{2}条',
            // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
            emptyMsg : "没有符合条件的记录",
            items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
        });
        // 表格工具栏****************************************************************************************

        // var tbar = new Ext.Toolbar({
        // items : [
        // {
        // text : '设置为集团客户母公司',
        // handler : function() {
        // var selectLength = grid.getSelectionModel()
        // .getSelections().length;
        //										
        // if (selectLength < 1) {
        // alert('请选择需要加入名单的客户');
        // } else
        // {
        //
        // var selectLength = grid
        // .getSelectionModel()
        // .getSelections().length;
        //
        // var selectRe = grid
        // .getSelectionModel()
        // .getSelections()[0];
        //
        // if (selectLength != 1) {
        // Ext.Msg.alert("系统提醒","请选择一条记录！");
        // } else {
        //										
        // //获得集团母客户名，并将该名字存入集团客户名中
        //										
        // var ss =
        // grid.getSelectionModel().selections.items[0].data.groupRootCustName1;
        // selectRe.set('groupName',ss);
        //											
        // // debugger;
        // editBasePlanForm.getForm().loadRecord(selectRe);
        // //editBasePlanForm.getForm().
        // document.getElementById('idStr').value = selectRe.data.id;
        // addGroupCustInfoWindow.hide();
        // addInit();
        //										
        // }
        // }
        //									
        // }
        // }
        //								
        // ]
        // });

        // ***************************************
        // 新增基本信息展示的form
        var editBasePlanForm = new Ext.form.FormPanel({
            labelWidth : 120,
            frame : true,
            region : 'center',
            autoScroll : true,
            labelAlign : 'right',
            buttonAlign : "center",
            layout : 'form',
            items : [ {
                xtype : 'hidden',
                editable : true,
                name : 'groupRootCustId',
                id : 'groupRootCustId',
                triggerAction : 'all',
                anchor : '90%',
                fieldLabel : '隐藏ID'
            }, new Ext.ux.form.CustomerQueryField( {
                fieldLabel : '集团母公司名称',
                labelStyle : 'text-align:right;',
                name : 'groupRootCustName1',
                id : 'groupRootCustName1',
                anchor : '90%',
                callback : function() {
                }
            }), {
                xtype : 'textfield',
                editable : true,
                name : 'groupName',
                triggerAction : 'all',
                allowBlank : false,
                anchor : '90%',
                fieldLabel : '<span style="color:red">*</span>集团客户名称'
            }, {
                xtype : 'textfield',
                fieldLabel : '集团母公司注册地址',
                name : 'groupRootAdress',
                readOnly : true,
                anchor : '90%'
            }, {
                name : "GROUP_HOST_ORG_NO",
                hidden : true,
                xtype : 'textfield'
            }/*
                 * ,{ columnWidth : .25, layout : 'form', items : [ {
                 * xtype:'combo', store : new Ext.data.SimpleStore( {
                 * fields : [], data : [ [] ] }),
                 * name:'groupHostOrgNoName', //
                 * id:'GROUP_HOST_ORG_NO_NAME', emptyText : '请选择',
                 * fieldLabel : '授信主办行', editable:false, resizable:true,
                 * anchor : '90%', mode : 'local', triggerAction :
                 * 'all', maxHeight : 390, //
                 * 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器 tpl:"<tpl for='.'
                 * <div style='height:390px'> <div
                 * id='addOrgTreeDivForAdd2'></div></div></tpl>",
                 * onSelect : Ext.emptyFn, listeners:{
                 * 'expand':function(combo){
                 * orgTreePanel2.render('addOrgTreeDivForAdd2'); } } } ] }
                 */, {
                store : JTKHLXStore,
                xtype : 'combo',
                name : 'groupType',
                fieldLabel : '<span style="color:red">*</span>集团类型',
                valueField : 'key',
                displayField : 'value',
                mode : 'local',
                typeAhead : true,
                editable : false,
                allowBlank : false,
                forceSelection : true,
                resizable : true,
                triggerAction : 'all',
                emptyText : '请选择',
                selectOnFocus : true,
                width : '100',
                anchor : '90%'
            }, {
                xtype : 'textarea',
                fieldLabel : '公司简介',
                name : 'groupMemo',
                maxLength : '300',
                minLength : '0',
                anchor : '90%'
            } ],
            buttons : [
                    {
                        text : '保  存',
                        handler : function() {
                            // if(__units!="00001"){
                        // Ext.Msg.alert("系统提示","对不起，您不是总行人员，没有操作权限！");
                        // return false;
                        // }
                        if (!editBasePlanForm.getForm().isValid()) {
                            Ext.Msg.alert("提醒", "请填写必填项");
                            return false;
                        }
                        ;
                        var tempId = Ext.getCmp('groupRootCustName1').customerId.aId[0];
                        Ext.getCmp("groupRootCustId").setValue(tempId);
                        Ext.Ajax
                                .request( {
                                    url : basepath + '/GroupInfoAction.json?a=1',
                                    method : 'POST',
                                    params : editBasePlanForm.getForm()
                                            .getFieldValues(),
                                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                    success : checkResult,
                                    failure : checkResult
                                });
                        function checkResult(response) {
                            var resultArray = Ext.util.JSON
                                    .decode(response.status);
                            var resultError = response.responseText;
                            debugger;
                            if ((resultArray == 200 || resultArray == 201)
                                    && resultError == '') {
                                Ext.Msg.alert('提示', '操作成功');
                                editBasePlanForm.getForm().reset();
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
                                    Ext.Msg.alert('提示',
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
                        addPlanWindow.hide();
                    }
                    }, {
                        text : '取  消',
                        handler : function() {
                            editBasePlanForm.getForm().reset();
                            addPlanWindow.hide();
                        }
                    } ]
                });

        // ****************************************

        // var qForm = new Ext.form.FormPanel({
        // labelWidth : 100, // 标签宽度
        // frame : true, // 是否渲染表单面板背景色
        // labelAlign : 'right',
        // region:'north',
        // // bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
        // buttonAlign : 'center',
        // height : 80,
        // items : [{
        // layout : 'column',
        // border : false,
        // items : [{
        // columnWidth : .5,
        // layout : 'form',
        // items : [ {
        // xtype : 'textfield',
        // fieldLabel : '组织机构代码',
        // id : 'CUST_ZZDM',
        // name : 'CUST_ZZDM',
        // anchor : '90%'
        // } ]
        // },{
        // columnWidth : .5,
        // layout : 'form',
        // items : [ {
        // xtype : 'textfield',
        // fieldLabel : '客户名称',
        // name : 'CUST_ZH_NAME',
        // anchor : '90%'
        // } ]
        // }]
        // }],
        // buttons : [{
        // text : '查询',
        // handler : function() {
        // var conditionStr = qForm.getForm().getFieldValues();
        // Sstore.baseParams = {
        // "condition" : Ext.encode(conditionStr)
        // };
        // Sstore.reload({
        // params : {
        // start : 0,
        // limit : bbar.pageSize }} );
        //							
        // }},{
        // text : '重置',
        // handler : function() {
        // qForm.getForm().reset();
        // }
        // }]
        // });

        // 表格实例
        // var grid = new Ext.grid.GridPanel({
        // height :310,
        // width : 180,
        // frame : true,
        // autoScroll : true,
        // region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
        // store : Sstore, // 数据存储
        // stripeRows : true, // 斑马线
        // cm : cm, // 列模型
        // sm : sm, // 复选框
        // tbar : tbar, // 表格工具栏
        // bbar:bbar,
        // viewConfig:{
        // forceFit:false,
        // autoScroll:true
        // },
        // loadMask : {
        // msg : '正在加载表格数据,请稍等...'
        // }
        // });

        // // 定义展示员工基本信息窗口
        // var addGroupCustInfoWindow = new Ext.Window({
        // title : '集团客户创建',
        // plain : true,
        // layout : 'border',
        // width : 800,
        // height : 400,
        // resizable : true,
        // draggable : true,
        // closable : true,
        // closeAction : 'hide',
        // modal : true, // 模态窗口
        // loadMask : true,
        // maximizable : true,
        // collapsible : true,
        // titleCollapse : true,
        // buttonAlign : 'right',
        // border : false,
        // items: [qForm,grid]
        // });

        // 新增窗口展示的from
        var editPlanPanel = new Ext.Panel( {
            labelWidth : 80,
            layout : 'fit',
            autoScroll : true,
            buttonAlign : "center",
            labelAlign : 'right',
            items : [ editBasePlanForm ]
        });

        // 定义新增窗口
        var addPlanWindow = new Ext.Window( {
            title : '集团客户新增',
            plain : true,
            layout : 'fit',
            width : 750,
            height : 250,
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
            labelAlign : 'right',
            border : false,
            items : [ editPlanPanel ]
        });

        function addInit() {
            addPlanWindow.show();
        }
        ;

        // ***************************************************************************

        var view = new Ext.Viewport( {
            layout : 'fit',
            frame : true,
            items : [ {
                layout : 'border',
                items : [ {
                    region : 'center',
                    id : 'center-panel',
                    layout : 'fit',
                    items : [ listPanel ]
                }, {
                    region : 'north',
                    id : 'north-panel',
                    height : 100,
                    layout : 'fit',
                    items : [ searchPanel ]
                } ]
            } ]
        });
    });

// **********************************
