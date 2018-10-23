/**
 * 用户管理模块
 * @author wangwan
 * @since 2012-09-24
 */
Ext.onReady(function() {

    Ext.QuickTips.init();
    userId = __userId; //全局变量：当前登录用户名
    /***************************读取密码校验策略参数********************************/
    Ext.Ajax.request({
        url: basepath + '/userApproveTacticsQuery.json',
        method: 'GET',
        params: {
            'tem1': 6,
            'tem2': 10
        },
        success: function(response) {
            var param = Ext.util.JSON.decode(response.responseText);
            var state6 = param.json.data[0].ENABLE_FLAG; //口令与近期历史密码重复策略启用标志
            var historyPw = param.json.data[0].DETAIL; //口令与近期历史密码重复策略配置参数
            var state7 = param.json.data[1].ENABLE_FLAG; //口令长度策略启用标志
            var pwLength = param.json.data[1].DETAIL; //口令长度策略配置参数
            var state8 = param.json.data[2].ENABLE_FLAG; //口令不重复长度策略启用标志
            var pwComplex = param.json.data[2].DETAIL; //口令不重复长度策略配置参数
            var state9 = param.json.data[3].ENABLE_FLAG; //口令连续长度策略启用标志
            var pwNoRepeat = param.json.data[3].DETAIL; //口令连续长度策略配置参数
            var state10 = param.json.data[4].ENABLE_FLAG; //口令复杂度策略启用标志
            var pwSeries = param.json.data[4].DETAIL; //口令复杂度策略配置参数
            var message1 = ''; //错误提示信息
            var message2 = '';
            var message3 = '';
            var message4 = '';
            var message5 = '';
            var message6 = '';
            var message7 = '';
            var message8 = '';
            var pw;

            /******************口令长度策略************************/
            function passwordLength(v, pwLength) {
                var minLength = parseInt(pwLength);
                if (v.length >= minLength) {
                    return true;

                } else {
                    pw = '';
                    message1 = '您输入的密码长度小于最小口令长度';
                    return false;
                }
            }

            /******************口令不重复长度策略************************/
            function passwordNoRepeatLength(v, pwNoRepeat) {
                var repeatMinLength = parseInt(pwNoRepeat);
                var c = 0;
                var a1 = 0;
                for (var j = 0; j < v.length - 1; j++) {
                    for (var i = 0; i < (repeatMinLength + 1); i++) {
                        var tmp = v.charCodeAt(i + j) - v.charCodeAt(i + j + 1);
                        if (tmp == 0) {
                            c++;
                        }
                    }
                    if (c == (repeatMinLength)) {
                        a1++;
                    }
                    c = 0;
                }
                if (a1 > 0) {
                    pw = '';
                    message2 = '您输入的密码连续重复的字符数超过最大长度';
                    return false;
                } else {
                    return true;

                }

            }

            /******************口令连续长度策略************************/
            function passwordSeries(v, pwSeries) {

                var repeatMinLength = parseInt(pwSeries);
                var a = 0;
                var b = 0;
                var a1 = 0;
                for (var j = 0; j < v.length - 1; j++) {
                    for (var i = 0; i < (repeatMinLength + 1); i++) {
                        var tmp = v.charCodeAt(i + j) - v.charCodeAt(i + j + 1);
                        if (tmp == 1) {
                            a++;
                        } else if (tmp == -1) {
                            b++;
                        }
                    }
                    if (a == (repeatMinLength) || b == (repeatMinLength)) {
                        a1++;
                    }
                    a = 0;
                    b = 0;
                }
                if (a1 > 0) {
                    pw = '';
                    message3 = '您输入的密码连续字符的字符数超过最大长度';
                    return false;
                } else {
                    return true;

                }
            }

            /*******************口令复杂度策略*************************/
            function passwordComplexStrategy(v, pwComplex) {

                var m = 0;
                var n = 0;
                var p = 0;
                var q = 0;
                var tmp = pwComplex.split('/');
                for (var i = 0; i < v.length; i++) {
                    var asc = v.charCodeAt(i);
                    if (asc > 47 && asc < 59) {
                        m = 1; //数字
                    } else if (asc > 64 && asc < 91) {
                        n = 1; //大写字符
                    } else if (asc > 96 && asc < 123) {
                        p = 1; //小写字符
                    } else if ((asc > 37 && asc < 48) || (asc > 57 && asc < 65)) {
                        q = 1; //其他字符
                    }
                }
                for (var i = 0; i < tmp.length; i++) {

                    var m = 0;
                    var n = 0;
                    var p = 0;
                    var q = 0;
                    var tmp = pwComplex.split('/');
                    for (var i = 0; i < v.length; i++) {
                        var asc = v.charCodeAt(i);
                        if (asc > 47 && asc < 59) {
                            m = 1; //数字
                        } else if (asc > 64 && asc < 91) {
                            n = 1; //大写字符
                        } else if (asc > 96 && asc < 123) {
                            p = 1; //小写字符
                        } else if ((asc > 37 && asc < 48) || (asc > 57 && asc < 65)) {
                            q = 1; //其他字符
                        }
                    }

                    for (var i = 0; i < tmp.length; i++) {
                        var a = tmp[i];
                        switch (a) {
                        case '1':
                            message5 = '数字';
                            if (m > 0) {} else {
                                pw = '';
                                message4 = '您输入的密码必须几种组合:';
                            };
                            break;
                        case '2':
                            message6 = '大写字母';
                            if (n > 0) {} else {
                                pw = '';
                                message4 = '您输入的密码必须几种组合:';
                            };
                            break;
                        case '3':
                            message7 = '小写字母';
                            if (p > 0) {} else {
                                pw = '';
                                message4 = '您输入的密码必须几种组合:';
                            };
                            break;
                        case '4':
                            message8 = '其他符号';
                            if (q > 0) {} else {
                                pw = '';
                                message4 = '您输入的密码必须几种组合:';
                            }
                            //默认情况：若指定情况不匹配，则直接保存输入密码，即不做复杂度校验，默认用户没有做复杂度校验的参数配置
                        }

                    }
                }

            }
            /***********************************IP校验方法************************************************/
            var ipAuthSign = 0; //ip校验成功与否标志
            function ipAuth() {
                var ipTmp = Ext.getCmp('offenIP').getValue(); //循环校验IP
                if (! (ipTmp == '')) {
                    var oftenIpTmp = ipTmp.split(',');
                    for (var i = 0; i < oftenIpTmp.length; i++) {
                        if (/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(oftenIpTmp[i])) {} else {
                            ipAuthSign++;
                        }
                    }
                }
            }

            var stateStore = new Ext.data.Store({ //启用禁用状态store
                restful: true,
                sortInfo: {
                    field: 'key',
                    direction: 'ASC'
                },
                autoLoad: true,
                proxy: new Ext.data.HttpProxy({
                    url: basepath + '/lookup.json?name=SYS_USER_STATE'
                }),
                reader: new Ext.data.JsonReader({
                    root: 'JSON',
                    totalProperty: 'list'
                },
                ['key', 'value'])
            });

            var roleLevelRecord = new Ext.data.Record.create([{
                name: 'roleId',
                mapping: 'ID'
            },
            {
                name: 'roleLevel',
                mapping: 'ROLE_LEVEL'
            }]);

            var roleLevelReader = new Ext.data.JsonReader({ //读取json数据的panel
                totalProperty: 'json.count',
                root: 'json.data'
            },
            roleLevelRecord);

            var roleLevelProxy = new Ext.data.HttpProxy({
                url: basepath + '/roleLevelQuery.json',
                params: {
                    'role_Id': JsContext._roleId[0]
                }
            });
            var roleLevelStore = new Ext.data.Store({
                id: 'roleLevelStore',
                restful: true,
                proxy: roleLevelProxy,
                reader: roleLevelReader,
                recordType: roleLevelRecord
            });
            roleLevelStore.load({
                params: {
                    'role_Id': JsContext._roleId[0]
                }
            });

            var searchFunction = function() {
                var parameters = userSearchPanel.getForm().getValues(false);
                userManageInfoStore.removeAll();
                userManageInfoStore.baseParams = {
                    'condition': Ext.util.JSON.encode(parameters)
                };
                userManageInfoStore.load({
                    params: {
                        start: 0,
                        limit: parseInt(pagesize_combo.getValue())
                    }
                });
            };

            var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
                /**选则字段*/
                checkField: 'ASTRUE',
                /**指向父节点的属性列*/
                parentAttr: 'SUPERUNITID',
                /**节点定位属性列，也是父属性所指向的列*/
                locateAttr: 'root',
                /**虚拟根节点id 若果select的值为root则为根节点*/
                rootValue: JsContext._orgId,
                /**用于展示节点名称的属性列*/
                textField: 'UNITNAME',
                /**指定节点ID的属性列*/
                idProperties: 'ID'
                /**节点点击事件句柄*/
            });
            var condition = {
                searchType: 'SUBTREE'
            };
            var filter = false;
            Ext.Ajax.request({
                url: basepath + '/commsearch.json?condition=' + Ext.encode(condition),
                method: 'GET',
                success: function(response) {
                    var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
                    loader.nodeArray = nodeArra;
                    var children = loader.loadAll();
                    Ext.getCmp('orgUserManageTreeForShow').appendChild(children);
                },
                failure: function(a, b, c) {}
            });

            var orgUserManageTreeForShow = new Com.yucheng.bcrm.TreePanel({ //主页面机构树
                id: 'orgUserManageTreeForShow',
                width: '25%',
                heigth: 600,
                autoScroll: true,
                /**虚拟树形根节点*/
                root: new Ext.tree.AsyncTreeNode({
                    id: JsContext._orgId,
                    text: JsContext._unitname,
                    expanded: true,
                    autoScroll: true,
                    children: []
                }),
                resloader: loader,
                region: 'west',
                split: true,
                listeners: {
                    'click': function(node) {
                        userManageInfoStore.removeAll();
                        var id = node.id;
                        var orgid = node.attributes.UNITID;
                        Ext.getCmp('treenode').setValue(orgid);
                        searchFunction();
                    }
                }
            });

            var orgManageTreeForShow = new Com.yucheng.bcrm.TreePanel({ //子页面机构树
                id: 'orgManageTreeForShow',
                width: '25%',
                heigth: 600,
                //			autoScroll:true,
                /**虚拟树形根节点*/
                root: new Ext.tree.AsyncTreeNode({
                    enableDD: false,
                    id: JsContext._orgId,
                    text: JsContext._unitname,
                    expanded: true,
                    autoScroll: true,
                    children: []
                }),
                resloader: loader,
                region: 'west',
                split: true,
                listeners: {
                    'click': function(node) {
                        userManageInfoStore.removeAll();
                        var id = node.id;
                        var orgid = node.attributes.UNITID;
                        Ext.getCmp('treenode').setValue(orgid);
                    }
                }
            });
            var orgPanel = new Ext.form.FormPanel({ //子页面机构树panel
                width: '25%',
                height: 600,
                frame: true,
                autoScroll: true,
                region: 'west',
                split: true,
                items: [orgManageTreeForShow]
            });

            var orgForAuthGrid = new Ext.grid.GridPanel({
                height: 600,
                enableDD: true,
                stripeRows: true,
                title: '授权机构列表'
            });

            var _this = this;

            var userSearchPanel = new Ext.form.FormPanel({ //用户查询panel
                title: '用户查询',
                height: 100,
                width: '75%',
                //		buttonAlign:'center',
                labelWidth: 100,
                //label的宽度
                labelAlign: 'right',
                frame: true,
                autoScroll: true,
                region: 'north',
                split: true,
                items: [{
                    layout: 'column',
                    items: [{
                        columnWidth: .3,
                        layout: 'form',
                        items: [{
                            xtype: 'textfield',
                            name: 'ACCOUNT_NAME',
                            fieldLabel: '登录名/用户名',
                            anchor: '100%',
                            enableKeyEvents: true,
                            listeners: {
                                keypress: function(a, b, c) {
                                    if (b.getKey() == 13) {
                                        searchFunction();
                                    }
                                }
                            }

                        }]
                    },
                    {
                        columnWidth: .3,
                        layout: 'form',
                        items: [{
                            xtype: 'textfield',
                            name: 'TREE_STORE',
                            hiddenName: 'TREE_STORE',
                            id: 'treenode',
                            fieldLabel: '机构节点',
                            anchor: '90%',
                            hidden: true
                        }]
                    }]
                }],
                buttonAlign: 'center',
                buttons: [{
                    text: '查找',
                    id: 'searchbutton',
                    handler: searchFunction
                },
                {
                    text: '重置',
                    id: 'btnReset',
                    handler: function() {
                        userSearchPanel.getForm().reset();
                        //clearForm(addRoleFormPanel.getForm());
                    }
                }]
            });

            var sexStore = new Ext.data.Store({ //性别store
                restful: true,
                autoLoad: true,
                proxy: new Ext.data.HttpProxy({
                    url: basepath + '/lookup.json?name=DEM0100005'
                }),
                reader: new Ext.data.JsonReader({
                    root: 'JSON'
                },
                ['key', 'value'])
            });

            //	var condition_role ={searchForRoleType:this.searchRoleType};
            var dptStore = new Ext.data.Store({
                restful: true,
                //		autoLoad :true,
                proxy: new Ext.data.HttpProxy({
                    url: basepath + '/dptQuery.json'
                }),
                reader: new Ext.data.JsonReader({
                    root: 'json.data'
                },
                ['DPT_ID', 'DPT_NAME'])
            });
            //	typeStore.baseParams = {
            //			'condition':Ext.util.JSON.encode(condition_role)
            //	};
            var userBaseInfoPanel = new Ext.form.FormPanel({ //子页面--用户基本信息页面
                // 			title:'用户基本信息',
                height: 295,
                width: 530,
                buttonAlign: 'center',
                labelWidth: 100,
                //label的宽度
                labelAlign: 'right',
                frame: true,
                autoScroll: true,
                region: 'north',
                split: true,
                items: [{
                    layout: 'column',
                    items: [{
                        columnWidth: .5,
                        layout: 'form',
                        items: [{
                            xtype: 'textfield',
                            id: 'id',
                            name: 'id',
                            fieldLabel: 'ID',
                            anchor: '100%',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            id: 'accountName',
                            name: 'accountName',
                            fieldLabel: '登录名<font color="red">*</font>',
                            anchor: '100%',
                            allowBlank: false
                        },
                        {
                            xtype: 'datefield',
                            fieldLabel: '有效时间<font color="red">*</font>',
                            format: 'Y-m-d',
                            id: 'deadline',
                            name: 'deadline',
                            selectOnFocus: true,
                            anchor: '100%',
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '密码<font color="red">*</font>',
                            id: 'password1',
                            name: 'password1',
                            inputType: "password",
                            anchor: '100%',
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '确认密码<font color="red">*</font>',
                            id: 'password',
                            name: 'password',
                            inputType: "password",
                            anchor: '100%',
                            allowBlank: false
                        },
                        {
                            xtype: 'datefield',
                            fieldLabel: '生日',
                            format: 'Y-m-d',
                            id: 'birthday',
                            name: 'birthday',
                            selectOnFocus: true,
                            anchor: '100%'
                        },
                        {
                            xtype: 'numberfield',
                            vtype: 'mobile',
                            fieldLabel: '手机',
                            id: 'mobilephone',
                            name: 'mobilephone',
                            anchor: '100%'
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '邮箱',
                            vtype: 'email',
                            id: 'email',
                            name: 'email',
                            anchor: '100%'
                        }
                        //	    		
                        ]
                    },
                    {
                        columnWidth: .5,
                        layout: 'form',
                        items: [{
                            xtype: 'textfield',
                            id: 'userName',
                            name: 'userName',
                            fieldLabel: '用户姓名<font color="red">*</font>',
                            anchor: '100%',
                            valueField: 'id',
                            displayField: 'USER_NAME',
                            allowBlank: false
                        },
                        new Com.yucheng.bcrm.common.OrgField({
                            xtype: 'textfield',
                            fieldLabel: '所属机构<font color="red">*</font>',
                            id: 'orgName',
                            name: 'orgName',
                            anchor: '100%',
                            hiddenName: 'orgId',
                            searchType: 'SUBTREE',
                            checkBox: false,
                            allowBlank: false,
                            callback: function() {
                                dptStore.load({
                                    params: {
                                        "belongOrgId": Ext.getCmp('orgId').getValue()
                                    }
                                });
                                Ext.getCmp('dirName').setValue('');

                            }
                        }), {

                            //									hiddenName : 'CUST_LEV',
                            xtype: 'combo',
                            fieldLabel: '部门',
                            id: 'dirName',
                            name: 'dirName',
                            hiddenName: 'dirId',
                            labelStyle: 'text-align:right;',
                            triggerAction: 'all',
                            store: dptStore,
                            displayField: 'DPT_NAME',
                            valueField: 'DPT_ID',
                            mode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择',
                            resizable: true,
                            anchor: '100%'

                        },
                        new Ext.form.ComboBox({
                            //									hiddenName : 'CUST_LEV',
                            fieldLabel: '性别',
                            id: 'sex',
                            name: 'sex',
                            labelStyle: 'text-align:right;',
                            triggerAction: 'all',
                            store: sexStore,
                            displayField: 'value',
                            valueField: 'key',
                            mode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择',
                            resizable: true,
                            anchor: '100%'
                        }), {
                            xtype: 'textfield',
                            vtype: 'telephone',
                            fieldLabel: '办公电话',
                            id: 'officetel',
                            name: 'officetel',
                            anchor: '100%'
                        },
                        {
                            fieldLabel: '用户ID',
                            id: 'userId',
                            name: 'userId',
                            hidden: true,
                            xtype: 'textfield',
                            readOnly: true,
                            labelStyle: 'text-align:right;',
                            anchor: '90%'
                        },
                        {
                            xtype: 'textfield',
                            name: 'appId',
                            id: 'appId',
                            fieldLabel: 'APPID',
                            anchor: '100%',
                            hidden: true,
                            readOnly: true,
                            value: JsContext._appId
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '状态',
                            id: 'userState',
                            name: 'userState',
                            anchor: '100%',
                            hidden: true,
                            value: '1'
                        },
                        { //逗号分隔输入
                            xtype: 'textfield',
                            fieldLabel: '常用IP',
                            id: 'offenIP',
                            name: 'offenIP',
                            anchor: '100%'
                        },
                        { //只读，不可修改
                            xtype: 'textfield',
                            fieldLabel: '最近登录时间',
                            id: 'lastLoginTime',
                            name: 'lastLoginTime',
                            anchor: '100%',
                            disabled: true,
                            readOnly: true
                        }]
                    }]
                }],
                buttonAlign: 'center',
                buttons: [{
                    text: '确定',
                    id: 'comfirm',
                    handler: function() {

                        Ext.getCmp('appId').setValue(JsContext._appId);
                        Ext.getCmp('userState').setValue('1');
                        var array = [];
                        if (! (Ext.getCmp('userId').getValue() == '')) { //修改用户信息
                            listPanel.items.items[1].setDisabled(false);
                            roleGridPanel.buttons[0].setDisabled(false);
                            roleStore.load({

                                params: {
                                    'accountId': Ext.getCmp('id').getValue(),
                                    roleLevel: roleLevelStore.data.items[0].json.ROLE_LEVEL
                                }
                            });
                            //								Ext.getCmp('password1').setValue(Ext.getCmp('password').getValue());
                            ipAuth(); //执行IP校验方法
                            if (!userBaseInfoPanel.getForm().isValid()) {
                                Ext.Msg.alert('提示', '输入信息有误');
                                return false;
                            } else if (ipAuthSign > 0) {
                                Ext.Msg.alert('提示', 'IP地址格式输入有误');
                                ipAuthSign = 0;
                            } else if (

                            Ext.Ajax.request({
                                url: basepath + '/userManager.json',
                                method: 'POST',
                                params: userBaseInfoPanel.getForm().getFieldValues(),
                                success: function(response) {
                                    Ext.Ajax.request({
                                        url: basepath + '/userManager!getPid.json',
                                        method: 'GET',
                                        //    											params : userBaseInfoPanel.getForm().items.items[6].getValue(),
                                        success: function(response) {
                                            var pId = Ext.util.JSON.decode(response.responseText).pid;
                                            Ext.getCmp('userId').setValue(pId);

                                        },
                                        failure: function(response) {
                                            Ext.Msg.alert('提示', '保存出错'
                                            /*response.responseText*/
                                            );
                                        }
                                    });
                                    userManageInfoStore.reload();
                                    Ext.Msg.alert('提示', '保存用户基本信息成功!');

                                },
                                failure: function(response) {
                                    Ext.Msg.alert('提示', '保存出错'
                                    /*response.responseText*/
                                    );
                                }
                            }));
                        } else { //新增用户    
                            roleStore.load({

                                params: {
                                    roleLevel: roleLevelStore.data.items[0].json.ROLE_LEVEL
                                }
                            });

                            Ext.Ajax.request({
                                url: basepath + '/userManager.json',
                                //查询用户表中全部用户名信息
                                method: 'GET',
                                success: function(response) {
                                    var array = [];
                                    var accountName = Ext.util.JSON.decode(response.responseText);
                                    for (var i = 0; i < accountName.json.data.length; i++) {
                                        if (accountName.json.data[i].ACCOUNT_NAME == Ext.getCmp('accountName').getValue()) {
                                            array.push(Ext.getCmp('accountName').getValue());
                                        }
                                    }

                                    ipAuth(); //执行IP校验方法
                                    if (!userBaseInfoPanel.getForm().isValid()) {
                                        Ext.Msg.alert('提示', '输入信息有误!');
                                        return false;
                                    } else if (! (Ext.getCmp('password1').getValue() == Ext.getCmp('password').getValue())) {
                                        Ext.Msg.alert('提示', '输入密码不一致!');
                                        return false;
                                    } else if (ipAuthSign > 0) { //ip校验
                                        Ext.Msg.alert('提示', 'IP地址格式输入有误!');
                                        ipAuthSign = 0;
                                    } else if (! (array.length == 0)) {
                                        Ext.Msg.alert('提示', '您新增的用户已存在!');
                                        return false;
                                    } else {

                                        /************************************密码校验***********************************************/
                                        var newPassword1 = Ext.getCmp('password').getValue();
                                        /****************新增用户时输入密码，进行密码校验***************************/
                                        pw = newPassword1;
                                        if (state7 == '1') {
                                            passwordLength(newPassword1, pwLength);
                                        };
                                        if (state8 == '1') {
                                            passwordNoRepeatLength(newPassword1, pwNoRepeat);
                                        };
                                        if (state9 == '1') {
                                            passwordSeries(newPassword1, pwSeries);
                                        };
                                        if (state10 == '1') {
                                            passwordComplexStrategy(newPassword1, pwComplex);
                                        };
                                        Ext.getCmp('password').setValue(pw);

                                        if (Ext.getCmp('password').getValue() == '') {
                                            Ext.Msg.alert('提示', '密码校验出错' + ' ' + message1 + ' ' + message2 + ' ' + message3 + ' ' + message4 + '' + message5 + ' ' + message6 + ' ' + message7 + ' ' + message8);
                                            message1 = ''; //还原提示信息
                                            message2 = '';
                                            message3 = '';
                                            message4 = '';
                                            message5 = '';
                                            message6 = '';
                                            message7 = '';
                                            message8 = '';
                                        } else {

                                            Ext.Ajax.request({
                                                url: basepath + '/userManager!save.json',
                                                method: 'POST',
                                                params: userBaseInfoPanel.getForm().getFieldValues(),
                                                success: function(response) {
                                                    Ext.Ajax.request({
                                                        url: basepath + '/userManager!getPid.json',
                                                        method: 'GET',
                                                        //				    											params : userBaseInfoPanel.getForm().items.items[6].getValue(),
                                                        success: function(response) {
                                                            var pId = Ext.util.JSON.decode(response.responseText).pid;
                                                            Ext.getCmp('userId').setValue(pId);
                                                        },
                                                        failure: function(response) {
                                                            Ext.Msg.alert('提示', '保存出错'
                                                            /*response.responseText*/
                                                            );
                                                        }
                                                    });
                                                    userManageInfoStore.reload();
                                                    Ext.Msg.alert('提示', '保存用户基本信息成功!');
                                                    listPanel.items.items[1].setDisabled(false);
                                                    roleGridPanel.buttons[0].setDisabled(false);
                                                    Ext.getCmp('comfirm').setDisabled(true);
                                                    Ext.getCmp('btnReset').setDisabled(true);
                                                    var tmp = [];
                                                    var pswd = {};
                                                    pswd.updateUser = JsContext._userId,
                                                    pswd.userId = Ext.getCmp('accountName').getValue;
                                                    pswd.pswdUped = Ext.getCmp('password').getValue;
                                                    tmp.push(pswd);
                                                },
                                                failure: function(response) {
                                                    Ext.Msg.alert('提示', '保存出错'
                                                    /*response.responseText*/
                                                    );
                                                }
                                            });
                                        }

                                    };

                                },
                                failure: function(response) {
                                    Ext.Msg.alert('提示', '查询用户名信息出错'
                                    /*response.responseText*/
                                    );
                                }
                            });

                        }
                    }
                },
                {
                    text: '清空',
                    id: 'btnReset',
                    handler: function() {
                        if (Ext.getCmp('userId').getValue() == '') { //新增用户
                            userBaseInfoPanel.getForm().reset();
                            dptStore.removeAll();
                        } else {
                            Ext.getCmp('accountName').setRawValue('');
                            Ext.getCmp('deadline').setRawValue('');
                            Ext.getCmp('birthday').setRawValue('');
                            Ext.getCmp('mobilephone').setRawValue('');
                            Ext.getCmp('email').setRawValue('');
                            Ext.getCmp('userName').setRawValue('');
                            Ext.getCmp('orgName').setRawValue('');
                            Ext.getCmp('orgId').setRawValue('');
                            Ext.getCmp('dirName').setRawValue('');
                            Ext.getCmp('sex').setRawValue('');
                            Ext.getCmp('officetel').setRawValue('');
                            Ext.getCmp('sex').setRawValue('');
                            Ext.getCmp('userState').setRawValue('');
                            dptStore.removeAll();
                        }

                        //								Ext.getCmp('orgName').setRawValue('');
                        //								var parameters= Ext.getCmp('userId').getValue();
                        //								Ext.Ajax.request({
                        //									url:basepath+'/userManageInfoQuery.json',
                        //									method: 'GET',
                        //									params :{
                        //										'userId':parameters
                        //								} ,
                        //									success : function(response) {
                        //									var record = Ext.util.JSON.decode(response.responseText).json;
                        //									userBaseInfoPanel.getForm().getEl().dom.reset();
                        //							        userBaseInfoPanel.getForm().loadRecord(record);
                        //							        var _record = userManageGrid.getSelectionModel().getSelected();
                        //							        userBaseInfoPanel.getForm().getEl().dom.reset();
                        //							        userBaseInfoPanel.getForm().loadRecord(_record);
                        //								},
                        //									failure : function(response) {
                        //										Ext.Msg.alert('提示','重置出错' /*response.responseText*/);
                        //									}
                        //								});
                        ////								userManageInfoStore.load();
                        ////								   var _record = userManageGrid.getSelectionModel().getSelected();
                        ////								   userBaseInfoPanel.getForm().getEl().dom.reset();
                        ////							        userBaseInfoPanel.getForm().loadRecord(_record);
                        //							}else{
                        //								userBaseInfoPanel.getForm().reset(); 
                        //							}
                    }
                }]
            });
            //	var userAndOrgPanel = new Ext.form.FormPanel({//子页面--机构授权信息页面
            //		
            //		title:'用户与机构管理',
            //		height:300,
            //		width:500,
            //		buttonAlign:'center',
            //		labelWidth:100,//label的宽度
            //		labelAlign:'right',
            //		frame:true,
            //		autoScroll : true,
            //		region:'west',
            //		split:true,
            //		items:[orgTreeForShow],
            //		       buttonAlign:'center',
            //		       buttons:[
            //		                {
            //		                	text:'保存',
            //		                	id:'preservebutton',
            //		                	disabled:true,
            //							handler : function() {
            //								  if(orgTreeForShow.getChecked().length == 0)
            //									{ 
            //										Ext.Msg.alert('提示','请选择授权机构!');
            //										return false;
            //									}
            //								  for(var i=0;i<orgTreeForShow.getChecked().length;i++){
            //									  
            //									  var checkedArray = {'array':[]};
            //									  var orgForAuth = orgTreeForShow.getChecked()[i].text;
            //									  var accountId = userBaseInfoPanel.getForm();
            //									  var tmp={'orgId':orgForAuth,'accountId':[]};
            //								
            //									checkedArray.array.push(tmp);
            //								  }
            //								Ext.Ajax.request({
            //								    url:basepath+'/userManagerForAuth.json',
            //								    method: 'POST',
            //									params : checkedArray,
            //								
            //									success : function(response) {
            //		    							Ext.Msg.alert('提示', '保存成功');
            //					//					store.load();
            //		    						},
            //		    						failure : function(response) {
            //		    							Ext.Msg.alert('提示', response.responseText);
            //		    						}
            //								});
            //								roleGridPanel.buttons[0].setDisabled(false);
            //							}
            //		                }
            //		                ]
            //	});
            //	var sm = new Ext.grid.CheckboxSelectionModel();
            var grids = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer({
                header: 'No.',
                width: 28
            }),//gridtable中的列定义
            {
                header: '角色层级',
                dataIndex: 'roleLevel',
                width: 150,
                sortable: true,
                align: 'center',
                hidden: false
            },
            {
                header: '角色名称',
                dataIndex: 'roleName',
                width: 200,
                sortable: true,
                align: 'center'
            },
            {
                header: '角色ID',
                dataIndex: 'roleId',
                width: 160,
                sortable: true,
                align: 'center',
                hidden: true
            },
            {
                header: '角色授权',
                dataIndex: 'isChecked',
                id: 'isChecked',
                renderer: function(value, record, e) {
                    var checked = (e.json.IS_CHECKED == '1') ? 'checked': '';
                    var checkBox = '<input id=' + e.id + '_check type="checkbox" ' + checked + ' />';
                    return checkBox;
                },
                width: 100,
                sortable: true
            }
            //                                      
            ]);
            var roleRecord = new Ext.data.Record.create([{
                name: 'roleLevel',
                mapping: 'ROLE_LEVEL'
            },
            {
                name: 'roleName',
                mapping: 'ROLE_NAME'
            },
            {
                name: 'roleId',
                mapping: 'ID'
            },
            {
                name: 'isChecked',
                mapping: 'IS_CHECKED'
            },
            ]);

            var roleReader = new Ext.data.JsonReader({ //读取json数据的panel
                totalProperty: 'json.count',
                root: 'json.data'
            },
            roleRecord);

            var roleProxy = new Ext.data.HttpProxy({
                url: basepath + '/roleInfoQuery.json'

            });
            var recordIds = new Array(); // 选中的Record主键列id列表   
            var recordsChecked = new Array(); // 选中的Record列表   
            var roleStore = new Ext.data.Store({
                id: 'roleStore',
                restful: true,
                proxy: roleProxy,
                reader: roleReader,
                baseParams :{
            	roleLevel: __unitlevel
            },
                recordType: roleRecord
            });
            roleStore.load();

//            var spagesize_combo = new Ext.form.ComboBox({
//                name: 'pagesize',
//                triggerAction: 'all',
//                mode: 'local',
//                store: new Ext.data.ArrayStore({
//                    fields: ['value', 'text'],
//                    data: [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
//                }),
//                valueField: 'value',
//                displayField: 'text',
//                value: '20',
//                forceSelection: true,
//                width: 85
//            });

//            var number = parseInt(spagesize_combo.getValue());
//            spagesize_combo.on("select",
//            function(comboBox) {
//                sbbar.pageSize = parseInt(spagesize_combo.getValue()),
//                roleStore.load({
//                    params: {
//                        start: 0,
//                        limit: parseInt(spagesize_combo.getValue()),
//                        roleLevel: roleLevelStore.data.items[0].json.ROLE_LEVEL
//                    }
//                });
//            });
//            var sbbar = new Ext.PagingToolbar({
//                pageSize: number,
//                store: roleStore,
//                displayInfo: true,
//                displayMsg: '显示{0}条到{1}条,共{2}条',
//                emptyMsg: "没有符合条件的记录",
//                items: ['-', '&nbsp;&nbsp;', spagesize_combo]
//            });

            var roleGridPanel = new Ext.grid.GridPanel({ //角色列表数据grid
                frame: true,
                region: "center",
                id: 'roleGridPanel',
                height: 295,
                width: 540,
                store: roleStore,
                loadMask: true,
                cm: grids,
//                bbar: sbbar,
                //		sm:sm,
                buttonAlign: 'center',
                buttons: [{
                    text: '保存',
                    id: 'save',
                    disabled: true,
                    handler: function() {
                        Ext.getCmp('appId').setValue(JsContext._appId);

                        var addArray = [];
                        var deleteArray = [];
                        for (var j = 0; j < roleGridPanel.store.data.items.length; j++) {
                            var firstArray = {};
                            var secondArray = {};
                            var k = roleGridPanel.store.data.keys;
                            var one = document.getElementById(k[j] + "_check");
                            var children = roleGridPanel.store.data.items[j].json;
                            if (one.checked == true) {
                                if (children.IS_CHECKED == '0') {
                                    firstArray.accountId = Ext.getCmp('userId').getValue();
                                    firstArray.roleId = children.ID;
                                    firstArray.appId = Ext.getCmp('appId').getValue();
                                    addArray.push(firstArray);
                                }
                            } else {
                                if (children.IS_CHECKED == '1') {
                                    secondArray.accountId = Ext.getCmp('userId').getValue();
                                    secondArray.roleId = children.ID;
                                    secondArray.id = children.check;
                                    deleteArray.push(secondArray);
                                }
                            }
                        }

                        Ext.Ajax.request({
                            url: basepath + '/userManagerForAuth.json',
                            method: 'POST',
                            params: {
                                'addArray': Ext.encode(addArray),
                                'deleteArray': Ext.encode(deleteArray)
                            },
                            success: function(response) {
                                Ext.getCmp('save').setDisabled(true);
                                userManageInfoStore.reload();
                                Ext.Msg.alert('提示', '保存成功');

                            },
                            failure: function(response) {
                                Ext.Msg.alert('提示', '保存失败'
                                /*response.responseText*/
                                );

                            }
                        });
                    }
                }],

                loadMask: {
                    msg: '正在加载表格数据,请稍等...'
                }
            });

            var listPanel = new Ext.TabPanel({ //页签展示
                id: 'listPanel',
                activeTab: 0,
                tabPosition: 'top',
                height: 355,
                width: 545,
                primary: "id",
                items: [{
                    title: '用户基本信息',
                    items: [userBaseInfoPanel]
                },
                {
                    title: '角色配置',
                    items: [roleGridPanel]
                }]
            });
            listPanel.items.items[1].setDisabled(true);

            var addWindow = new Ext.Window({ //新增和修改时弹出的窗口定义
                title: '新增用户',
                closable: true,
                plain: true,
                resizable: false,
                collapsible: false,
                height: 355,
                width: 545,
                draggable: false,
                closeAction: 'hide',
                modal: true,
                // 模态窗口
                border: false,
                autoScroll: true,
                closable: true,
                animateTarget: Ext.getBody(),
                constrain: true,
                layout: 'fit',
                region: 'north',
                items: [listPanel]
            });

            addWindow.on('hide',
            function() {
                Ext.getCmp('save').setDisabled(false);
                Ext.getCmp('comfirm').setDisabled(false);
                Ext.getCmp('btnReset').setDisabled(false);

            });
            var updatePasswordPanel = new Ext.form.FormPanel({ //重置密码Panel
                height: 225,
                width: 450,
                buttonAlign: 'center',
                labelWidth: 100,
                //label的宽度
                labelAlign: 'right',
                frame: true,
                //			autoScroll : true,
                region: 'north',
                split: true,
                items: [{
                    layout: 'column',
                    items: [{
                        columnWidth: .8,
                        layout: 'form',
                        items: [{
                            xtype: 'textfield',
                            name: 'id',
                            fieldLabel: 'ID',
                            anchor: '100%',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'accountName',
                            fieldLabel: '登录名',
                            readOnly: true,
                            anchor: '100%'
                        },
                        {
                            xtype: 'textfield',
                            name: 'userName',
                            fieldLabel: '用户姓名',
                            readOnly: true,
                            anchor: '100%'
                        },
                        {
                            xtype: 'textfield',
                            name: 'password1',
                            allowBlank: false,
                            inputType: "password",
                            fieldLabel: '重置密码<font color="red">*</font>',
                            anchor: '100%'
                        },
                        {
                            xtype: 'textfield',
                            name: 'password2',
                            allowBlank: false,
                            id: "password2",
                            inputType: "password",
                            fieldLabel: '确认重置密码<font color="red">*</font>',
                            anchor: '100%'
                        }]
                    }]
                }],
                buttonAlign: 'center',
                buttons: [{
                    text: '保存',
                    //重置密码按钮
                    id: 'preservebutton',
                    handler: function() {
                        if (! (updatePasswordPanel.getForm().items.items[3].getValue() == updatePasswordPanel.getForm().items.items[4].getValue())) {
                            Ext.Msg.alert('提示', '输入密码不一致');
                            return false;
                        } else {
                            //如密码输入一致，则进行下一步校验
                            /**********************************密码校验方法************************************/
                            var newPassword2 = updatePasswordPanel.getForm().items.items[4].getValue();
                            pw = newPassword2;
                            if (state7 == '1') {
                                passwordLength(newPassword2, pwLength);
                            };
                            if (state8 == '1') {
                                passwordNoRepeatLength(newPassword2, pwNoRepeat);
                            };
                            if (state9 == '1') {
                                passwordSeries(newPassword2, pwSeries);
                            };
                            if (state10 == '1') {
                                passwordComplexStrategy(newPassword2, pwComplex);
                            };
                            updatePasswordPanel.getForm().items.items[4].setValue(pw);
                            updatePasswordPanel.getForm().items.items[3].setValue(pw);
                        }
                        if (pw == '') {
                            if (!updatePasswordPanel.getForm().isValid()) {
                                Ext.Msg.alert('提示', '输入信息有误');
                                return false;
                            } else {
                                Ext.Msg.alert('提示', message1 + ' ' + message2 + ' ' + message3 + ' ' + message4 + '' + message5 + ' ' + message6 + ' ' + message7 + ' ' + message8);
                            }
                            message1 = ''; //还原提示信息
                            message2 = '';
                            message3 = '';
                            message4 = '';
                            message5 = '';
                            message6 = '';
                            message7 = '';
                            message8 = '';
                            updatePasswordPanel.getForm().items.items[4].setValue(pw);
                            updatePasswordPanel.getForm().items.items[3].setValue(pw);
                        } else {
                            Ext.Ajax.request({
                                url: basepath + '/passwordChangeAction!authPassword.json',
                                method: 'POST',
                                params: {
                                    'userId': updatePasswordPanel.getForm().items.items[1].getValue(),
                                    'password': pw,
                                    'updateUser': userId,
                                    'authEnableFlag': state6,
                                    'historyPw': historyPw,
                                    'id': updatePasswordPanel.getForm().items.items[0].getValue(),
                                    'oldPassword2': ''
                                },
                                success: function(response) {
                                    Ext.Msg.alert('提示', '重置密码成功'
                                    /*response.responseText*/
                                    );

                                }
                            });
                        }
                    }
                }]
            });
            var updatePasswordWindow = new Ext.Window({ //重置密码window
                title: '重置密码',
                closable: true,
                plain: true,
                resizable: false,
                collapsible: false,
                height: 250,
                width: 450,
                draggable: false,
                closeAction: 'hide',
                modal: true,
                // 模态窗口 
                border: false,
                closable: true,
                animateTarget: Ext.getBody(),
                constrain: true,
                items: [updatePasswordPanel]
            });

            var update = function() { //修改方法
                var _record = userManageGrid.getSelectionModel().getSelected();
                var checkedNodes = userManageGrid.getSelectionModel().selections.items;
                if (!_record || checkedNodes.length > 1) {
                    Ext.MessageBox.alert('提示', '请选择要修改的一行！');
                } else {
                    userBaseInfoPanel.getForm().items.items[9].setValue(userManageGrid.getSelectionModel().getSelected().json.ID);
                    addWindow.show();
                    addWindow.setTitle('修改用户信息');
                    listPanel.items.items[1].setDisabled(false);
                    userBaseInfoPanel.getForm().getEl().dom.reset();
                    userBaseInfoPanel.getForm().loadRecord(_record);
                    Ext.getCmp('password1').setValue(Ext.getCmp('password').getValue());
                    Ext.getCmp('userId').setValue(userManageGrid.getSelectionModel().getSelected().json.ID);
                    dptStore.load({
                        params: {
                            "belongOrgId": Ext.getCmp('orgId').getValue()
                        }
                    });
                    roleStore.load({

                        params: {
                            'accountId': userBaseInfoPanel.items.items[0].items.items[0].items.items[0].getValue(),
                            roleLevel: roleLevelStore.data.items[0].json.ROLE_LEVEL
                        }
                    });

                    Ext.getCmp('userId').setValue(userManageGrid.getSelectionModel().getSelected().json.ID); //隐藏域赋值用户ID
                };
            };

            var tbar = new Ext.Toolbar({
                items: [{
                    text: '新建',
                    iconCls: 'addIconCss',
                    handler: function() {
                        Ext.getCmp('listPanel').setActiveTab(0);
                        addWindow.show();
                        addWindow.setTitle('新增用户');
                        listPanel.items.items[1].setDisabled(true);
                        Ext.getCmp('password1').show();
                        Ext.getCmp('password').show();
                        userBaseInfoPanel.getForm().getEl().dom.reset();
                        roleStore.load({
                            params: {
                                roleLevel: roleLevelStore.data.items[0].json.ROLE_LEVEL
                            }
                        });
                        userManageInfoStore.reload();
                        //			      	dptStore.baseParams = {
                        //						'condition':Ext.util.JSON.encode(condition_role)
                        //				};
                        //			          dptStore.load({
                        //			        	  params : {
                        //			        	  'condition':Ext.util.JSON.encode(Ext.getCmp('orgName').getValue())
                        //		       	 }
                        //			          });
                    }
                },
                '-', {
                    text: '修改',
                    iconCls: 'editIconCss',
                    handler: function() {
                        Ext.getCmp('listPanel').setActiveTab(0);
                        roleGridPanel.buttons[0].setDisabled(false);
                        //		    		 Ext.getCmp('password1').setReadOnly(true);
                        //					 Ext.getCmp('password').setReadOnly(true);
                        update();
                        Ext.getCmp('password1').hide();
                        Ext.getCmp('password').hide();
                        //		        	
                        userManageInfoStore.reload();
                    }
                },
                '-', {
                    text: '启用',
                    iconCls: 'publishIconCss',
                    handler: function() {
                        var selectLength = userManageGrid.getSelectionModel().getSelections().length;
                        var selectRe;
                        var tempId;
                        var idStr = '';
                        if (selectLength < 1) {
                            Ext.Msg.alert('提示', '请选择需要启用的用户!');
                        }
                        /*else if(selectLength > 1){
							Ext.Msg.alert('提示','请选择<font color="red">一个用户</font>!');
						}*/
                        else {
                            for (var i = 0; i < selectLength; i++) {
                                selectRe = userManageGrid.getSelectionModel().getSelections()[i];
                                tempId = selectRe.json.ID;
                                idStr += tempId;
                                if (i != selectLength - 1) idStr += ',';
                            }
                            Ext.Ajax.request({
                                url: basepath + '/userManager!updateState.json',
                                method: 'POST',
                                params: {
                                    'idStr': idStr,
                                    'userState': 1

                                },
                                success: function(response) {
                                    userManageInfoStore.reload();
                                    Ext.Msg.alert('提示', '启用用户成功'
                                    /*response.responseText*/
                                    );
                                },
                                failure: function(response) {
                                    Ext.Msg.alert('提示', '启用用户失败'
                                    /*response.responseText*/
                                    );
                                }
                            });

                        }

                    }
                },
                '-', {
                    text: '停用',
                    iconCls: 'endCss',
                    handler: function() {

                        var selectLength = userManageGrid.getSelectionModel().getSelections().length;
                        if (selectLength < 1) {
                            Ext.Msg.alert('提示', '请选择需要停用的用户!');
                            return;
                        } 
                        if (selectLength > 1) {
                            Ext.Msg.alert('提示', '请选择<font color="red">一个用户</font>!');
                            return;
                        }  
                        selectRe = userManageGrid.getSelectionModel().getSelections()[0];
                        if(selectRe.data.accountName=='admin'){
                        	Ext.Msg.alert('提示', 'admin用户不能停用!');
                            return;
                        }
                        
                        
                            userManageGrid.getSelectionModel().getSelected().json.ID;
                            Ext.Ajax.request({
                                url: basepath + '/userManager!updateState.json',
                                method: 'POST',
                                params: {
                                    'idStr': userManageGrid.getSelectionModel().getSelected().json.ID,
                                    'userState': 0

                                },
                                success: function(response) {
                                    userManageInfoStore.load({
                                        params: {
                                            start: 0,
                                            limit: parseInt(pagesize_combo.getValue())
                                        }
                                    });
                                    userManageInfoStore.reload();
                                    Ext.Msg.alert('提示', '停用用户成功'
                                    /*response.responseText*/
                                    );
                                },
                                failure: function(response) {
                                    Ext.Msg.alert('提示', '停用用户失败'
                                    /*response.responseText*/
                                    );
                                }
                            });
                            userManageInfoStore.reload();
                        }

                },
                '-', {
                    text: '删除',
                    iconCls: 'deleteIconCss',
                    handler: function() {

                        var selectLength = userManageGrid.getSelectionModel().getSelections().length;
                        var selectRe;
                        var tempId;
                        var idStr = '';
                        if (selectLength < 1) {
                            Ext.Msg.alert('提示', '请选择需要删除的记录!');
                            return;
                        } 
                        for (var i = 0; i < selectLength; i++) {
                            selectRe = userManageGrid.getSelectionModel().getSelections()[i];
                            if(selectRe.data.accountName=='admin'){
                            	Ext.Msg.alert('提示', 'admin用户不能删除!');
                                return;
                            }
                            tempId = selectRe.data.id;
                            idStr += tempId;
                            if (i != selectLength - 1) idStr += ',';
                        }
                            Ext.MessageBox.confirm('提示', '确定删除吗?',
                            function(buttonId) {
                                if (buttonId.toLowerCase() == "no") {
                                    return;
                                }
                                Ext.Ajax.request({
                                    url: basepath + '/userManager!batchDestroy.json',
                                    method: 'POST',
                                    params: {
                                        'idStr': idStr

                                    },
                                    success: function(response) {
                                        userManageInfoStore.reload();
                                        Ext.Msg.alert('提示', '删除用户成功'
                                        /*response.responseText*/
                                        );
                                    },
                                    failure: function(response) {
                                        Ext.Msg.alert('提示', '操作失败'
                                        /*response.responseText*/
                                        );
                                    }
                                });

                            });
                        }
                },
                '-', {
                    text: '密码重置',
                    iconCls: 'resetIconCss',
                    handler: function() {
                        var selectLength = userManageGrid.getSelectionModel().getSelections().length;
                        if (selectLength < 1) {
                            Ext.Msg.alert('提示', '请选择需要重置密码的记录!');
                        } else if (selectLength > 1) {
                            Ext.Msg.alert('提示', '请选择<font color="red">一个用户</font>!');
                        } else {
                            var record = userManageGrid.getSelectionModel().getSelected();
                            updatePasswordWindow.show();
                            updatePasswordPanel.getForm().getEl().dom.reset();
                            updatePasswordPanel.getForm().loadRecord(record);
                            userManageInfoStore.reload();
                        }
                    }
                }]
            });

            //	// 列选择模型
            var sm = new Ext.grid.CheckboxSelectionModel();
            //	// 定义自动当前页行号
            var rownum = new Ext.grid.RowNumberer({
                header: 'No.',
                width: 28
            });
            var userInfoColumns = new Ext.grid.ColumnModel([ //gridtable中的列定义
            sm, rownum, {
                header: 'ID',
                dataIndex: 'id',
                id: "id",
                width: 100,
                sortable: true,
                hidden: true
            },
            {
                header: '登录名',
                dataIndex: 'accountName',
                id: "accountName",
                width: 100,
                sortable: true
            },
            {
                header: '用户姓名',
                dataIndex: 'userName',
                id: 'userName',
                width: 100,
                sortable: true
            },
            {
                header: '所属机构号',
                dataIndex: 'orgId',
                id: 'orgId',
                width: 100,
                sortable: true,
                hidden: true
            },
            {
                header: '所属机构',
                dataIndex: 'orgName',
                id: 'orgName',
                width: 130,
                sortable: true
            },
            {
                header: '部门号',
                dataIndex: 'dirId',
                id: 'dirId',
                width: 80,
                sortable: true,
                hidden: true
            },
            {
                header: '部门',
                dataIndex: 'dirName',
                id: 'dirName',
                width: 80,
                sortable: true
            },
            {
                header: '生日',
                dataIndex: 'birthday',
                id: 'birthday',
                width: 100,
                sortable: true
            },
            {
                header: '有效日期',
                dataIndex: 'deadline',
                id: 'deadline',
                width: 100,
                sortable: true,
                hidden: true
            },
            {
                header: '手机',
                dataIndex: 'mobilephone',
                id: "mobilephone",
                width: 100,
                sortable: true
            },
            {
                header: '办公电话',
                dataIndex: 'officetel',
                id: 'officetel',
                width: 100,
                sortable: true
            },
            {
                header: '邮箱',
                dataIndex: 'email',
                id: 'email',
                width: 130,
                sortable: true
            },
            {
                header: '性别',
                dataIndex: 'sexOra',
                id: 'sex',
                width: 50,
                sortable: true
            },
            {
                header: '状态',
                dataIndex: 'stateOra',
                id: 'userState',
                width: 110,
                sortable: true
            },
            {
                header: '密码',
                dataIndex: 'password',
                id: 'password',
                width: 130,
                sortable: true,
                hidden: true
            },
            {
                header: 'id2',
                dataIndex: 'id2',
                id: 'id2',
                width: 130,
                sortable: true,
                hidden: true
            },
            {
                header: '常用IP',
                dataIndex: 'offenIP',
                id: 'offenIP',
                width: 130,
                sortable: true,
                hidden: true
            },
            {
                header: '最近登录时间',
                dataIndex: 'lastLoginTime',
                id: 'lastLoginTime',
                width: 130,
                sortable: true,
                hidden: false
            }

            ]);
            var userInfoRecord = new Ext.data.Record.create([{
                name: 'id',
                mapping: 'ID'
            },
            {
                name: 'accountName',
                mapping: 'ACCOUNT_NAME'
            },
            {
                name: 'userName',
                mapping: 'USER_NAME'
            },
            {
                name: 'orgId',
                mapping: 'ORG_ID'
            },
            {
                name: 'orgName',
                mapping: 'ORG_NAME'
            },
            {
                name: 'dirId',
                mapping: 'DIR_ID'
            },
            {
                name: 'dirName',
                mapping: 'DPT_NAME'
            },
            {
                name: 'birthday',
                mapping: 'BIRTHDAY'
            },
            {
                name: 'deadline',
                mapping: 'DEADLINE'
            },
            {
                name: 'mobilephone',
                mapping: 'MOBILEPHONE'
            },
            {
                name: 'officetel',
                mapping: 'OFFICETEL'
            },
            {
                name: 'email',
                mapping: 'EMAIL'
            },
            {
                name: 'sex',
                mapping: 'SEX'
            },
            {
                name: 'sexOra',
                mapping: 'SEX_ORA'
            },
            {
                name: 'userState',
                mapping: 'USER_STATE'
            },
            {
                name: 'stateOra',
                mapping: 'USER_STATE_ORA'
            },
            {
                name: 'password',
                mapping: 'PASSWORD'
            },
            {
                name: 'id2',
                mapping: 'ID2'
            },
            {
                name: 'offenIP',
                mapping: 'OFFENIP'
            },
            {
                name: 'lastLoginTime',
                mapping: 'LASTLOGINTIME'
            }]);
            var userInfoReader = new Ext.data.JsonReader({ //读取json数据的panel
                totalProperty: 'json.count',
                root: 'json.data'
            },
            userInfoRecord);

            var userManageProxy = new Ext.data.HttpProxy({
                url: basepath + '/usermanagequery.json'
            });
            var userManageInfoStore = new Ext.data.Store({
                id: 'userManageInfoStore',
                restful: true,
                proxy: userManageProxy,
                reader: userInfoReader,
                recordType: userInfoRecord
            });

            // 每页显示条数下拉选择框
            var pagesize_combo = new Ext.form.ComboBox({
                name: 'pagesize',
                triggerAction: 'all',
                mode: 'local',
                store: new Ext.data.ArrayStore({
                    fields: ['value', 'text'],
                    data: [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
                }),
                valueField: 'value',
                displayField: 'text',
                value: '20',
                forceSelection: true,
                width: 85
            });

            userManageInfoStore.load({
                params: {
                    start: 0,
                    limit: parseInt(pagesize_combo.getValue())
                }
            });
            var number = parseInt(pagesize_combo.getValue());
            pagesize_combo.on("select",
            function(comboBox) {
                bbar.pageSize = parseInt(pagesize_combo.getValue()),
                userManageInfoStore.load({
                    params: {
                        start: 0,
                        limit: parseInt(pagesize_combo.getValue())
                    }
                });
            });
            var bbar = new Ext.PagingToolbar({
                pageSize: number,
                store: userManageInfoStore,
                displayInfo: true,
                displayMsg: '显示{0}条到{1}条,共{2}条',
                emptyMsg: "没有符合条件的记录",
                items: ['-', '&nbsp;&nbsp;', pagesize_combo]
            });
            var userManageGrid = new Ext.grid.GridPanel({ //用户列表数据grid
                frame: true,
                width: '75%',
                id: 'userManageGrid',
                autoScroll: true,
                tbar: tbar,
                bbar: bbar,
                stripeRows: true,
                // 斑马线
                store: userManageInfoStore,
                loadMask: true,
                cm: userInfoColumns,
                sm: sm,
                viewConfig: {
                    forceFit: false,
                    autoScroll: true
                },
                loadMask: {
                    msg: '正在加载表格数据,请稍等...'
                }
            });

            var view = new Ext.Viewport({
                layout: 'fit',
                items: [{
                    plain: true,
                    resizable: false,
                    collapsible: false,
                    animateTarget: Ext.getBody(),
                    constrain: true,
                    layout: 'border',
                    items: [orgUserManageTreeForShow, {
                        region: 'center',
                        layout: 'border',
                        items: [userSearchPanel, {
                            region: 'center',
                            layout: 'fit',
                            items: [userManageGrid]
                        }]
                    }]
                }]

            });
            searchFunction();
            return;
        },
        failure: function(response) {
            Ext.Msg.alert('提示', '失败');
        }
    });

});