    /**************************************自定义模块和自定义布局接口，改动数据即可改动模块和布局参数**************************************************/
    var userSetting;
    /**
     * @describe modules data array.U can add new module or delete module.
     *           Or U can make it been read from DataBase.
     *           
     *           name: Title of the module. Not null.
     *           modId: Id of the module. Only. Not null.
     *           isJump: If has the text:'详细信息' to jump to the menu. Defualt: false.
     *           jumpUrl: The jump url.
     *           action: used for load data.
     *           dataNames: for view of grid header.
     *           roles: 一票通过制角色权限控制，只要用户角色中的某一个在roles中存在，则可以设置该模块。如未设置该属性，则该模块所有角色可用。
     */    
    var moduleData = [
      {       
//           roles:'41,27,127,120',
            name:'存款余额趋势图',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/fusionChart/content/orgPerformanceCunKuanChart.jsp',
            modId:'orgAchHis',
            isJump:true,
            jumpUrl:basepath+'/contents/pages/fusionChart/content/orgPerformanceCunKuanChart.jsp',
            action:basepath+'/orgPerformanceCunKuan.json',
            hidden:JsContext.checkGrant('orgAchHis')
        },{
//        	roles:'41,27,127,120',
            name:'贷款余额趋势图',
            modId:'orgAchTre',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/fusionChart/content/orgPerformanceDaiKuanChart.jsp',
            isJump:true,
            jumpUrl:basepath+'/contents/pages/fusionChart/content/orgPerformanceDaiKuanChart.jsp',
            action:basepath+'/orgPerformanceDaiKuan.json',
            hidden:JsContext.checkGrant('orgAchTre')
        },{       
//        	roles:'41,27,127,46,126,120',
            name:'机构业绩柱状图',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/customer/customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/c1.html',
            modId:'orgAchChart',
            isJump:false,
            hidden:JsContext.checkGrant('orgAchChart')
        },{       
//        	roles:'41,27,127,46,126,120',
            name:'机构贡献度占比图',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/customer/customerManager/customerBaseInformation/fusionchartsDemo/contribute/c2.html',
            modId:'orgAchPercentChart1',
            isJump:false,
            hidden:JsContext.checkGrant('orgAchPercentChart1')
        },{       
//        	roles:'41,27,127,46,126,120',
            name:'附件空间大小',
            modId:'sysStatusMonitor',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/systemManager/SysstatusMonitor.jsp',
            isJump:true,
            jumpUrl:basepath+'/contents/pages/systemManager/SysstatusMonitor.jsp',
            action:basepath+'/sysStatusMonitoring.json',
            hidden:false
        },{       
//        	roles:'41,47,27,127,46,126,120,47',
            name:'客户分布图',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/customer/customerManager/customerBaseInformation/fusionchartsDemo/contribute/c3.html',
            modId:'orgAchPercentChart2',
            isJump:false,
            hidden:JsContext.checkGrant('orgAchPercentChart2')
        },{       
//        	roles:'41,27,127,46,126,120',
            name:'机构业绩完成仪表盘',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/customer/customerManager/customerBaseInformation/fusionchartsDemo/performance/pa.html',
            modId:'yibiaopanChart',
            isJump:false,
            hidden:JsContext.checkGrant('yibiaopanChart')
        }/*,{       
//            roles:'2',
            name:'机构业绩时点数占比图jpg',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/demo/shidianshu.png',
            modId:'orgAchPercentChart11',
            isJump:false
        },{       
//            roles:'2',
            name:'机构业绩日均值占比图jpg',
            isFrame:true,
            frameSrc:basepath+'/contents/pages/demo/rijunzhi.png',
            modId:'orgAchPercentChart22',
            isJump:false
        }*/,{       
//        	roles:'41,47',
            name:'客户经理业绩表（客户经理）',
            modId:'custMgrAch',
            isJump:false,
            frameSrc:basepath+'/contents/pages/demo/custMgrAch.jsp',
            action:basepath+'/custmanagerachievequery.json',
            hidden:JsContext.checkGrant('custMgrAch'),
            dataNames:[{
                header : '项目名称',
                width : 150,
                align : 'left',
                dataIndex : 'TARGET_NAME',
                sortable : true
            },{
                header : '当前业绩',
                width : 150,
                align : 'right',
                dataIndex : 'CURR_BAL',
                sortable : true,
                renderer: money('0,000.00')
            },{
                header : '上日业绩',
                width : 150,
                align : 'right',
                dataIndex : 'LADY_BAL',
                sortable : true,
                renderer: money('0,000.00')
            },{
                header : '较上日增量',
                width : 170,
                align : 'right',
                dataIndex : 'COMLADY_INCRE',
                sortable : true
            },{
                header : '业绩目标值',
                width : 170,
                align : 'right',
                dataIndex : 'YEAR_TARVA',
                sortable : true,
                renderer: money('0,000.00')
            },{
                header : '指标完成率%',
                width : 170,
                align : 'right',
                dataIndex : 'TAR_COMRATE',
                sortable : true,
                renderer: money('0,000.00')
            }]
        
        
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'待办工作',
            modId:'work',
            isJump:true,
            jumpUrl:basepath+'/contents/pages/mxtTest/testworkflow.jsp',
            action:basepath+'/queryrestapplywait.json',
            hidden:JsContext.checkGrant('work'),
            dataNames:[{
                align:'left',
                dataIndex : 'WFJOBNAME',
                header : '工作名称'
                
            },{
                align:'left',
                dataIndex : 'WFNAME',
                header : '流程名称'
            },{
                align:'left',
                dataIndex : 'AUTHOR',
                header : '发起人'
            },{
                align:'left',
                dataIndex : 'PRENODENAME',
                header : '上一办理人'
            },{
                align:'left',
                dataIndex : 'NODEPLANENDTIME',
                header : '交办时间',
                renderer:function(value, p, record){
                    if(typeof value =='string'){
                        return value.substring(0,10);
                    }else{
                        return value.format('Y-m-d');
                    }
                }
            }]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'关注客户',
            modId:'attCust',
            isJump:true,
            jumpUrl:basepath+'/contents/pages/customer/customerManager/attentionCustomer.jsp',
            action:basepath+'/custConcern.json',
            hidden:JsContext.checkGrant('attCust'),
            dataNames:[{
                align:'left',
                dataIndex : 'CUST_ZH_NAME',
                header : '客户名称'
                
            },{
                align:'left',
                dataIndex : 'CERT_NUM',
                header : '证件号码'
            },{
                align:'right',
                dataIndex : 'TELEPHONE_NUM',
                header : '存款余额',
                renderer:money('0,000.00')
            }]
        }
        ,{	
//        	roles:'127,46,41,120',
            name:'客户经理业绩表（支行行长）',
            modId:'custManAch',
            isJump:false,
            jumpUrl:basepath+'/contents/pages/demo/custMgrAch2.jsp',
            action:basepath + '/custmanagerachievequery2.json',
            hidden:JsContext.checkGrant('custManAch'),
            dataNames:[
                     {
                         dataIndex : 'CUST_MANAGER_NAME',
                         header: '客户经理姓名',
                         align:'left'
                     },{
                         dataIndex: 'DEP_AVG_BAL',
                         header: '存款日均',
                         align: 'right',
                         renderer: money('0,000.00')
                     },{
                         dataIndex:'LOAN_AVG_BAL',
                         header: '贷款日均',
                         align:'right',
                         renderer: money('0,000.00')
                     },{
                         dataIndex:'DISCOUNT_AVG_BAL',
                         header:'贴现日均',
                         align:'right',
                         renderer: money('0,000.00')
                     },{
                         dataIndex : 'COM_CUST_SUM',
                         header: '对公客户数量',
                         align:'right'
                     },{
                         dataIndex : 'MIDBU_INCOME',
                         header: '中间业务收入',
                         align:'right',
                         renderer: money('0,000.00')
                     }
                        
                     ]
        }
        ,{
//        	roles:'47,46,41,127,47',
            name:'我管理的客户',
            modId:'custManager',
            isJump:true,
            jumpUrl:basepath + '/contents/pages/customer/accountManager/performanceQuery.jsp',
            action:basepath + '/performanceQuery.json',
            hidden:JsContext.checkGrant('custManager'),
            dataNames:[
                     {
                         dataIndex : 'user_name',
                         header: '客户名称',
                         align:'left',
                         rendered:function(){
                    	 return "<span style='text-align:right'>mid</span" +
                    	 		">";
                     }
                     },{
                         dataIndex: 'rela_acct',
                         header: '存款时点余额',
                         align:'right',
                         renderer: money('0,000.00')
                     },{
                         dataIndex: 'rela_pct',
                         header: '贷款时点余额',
                         align: 'right',
                         renderer: money('0,000.00')
//                         renderer: percent(false)
                     },{
                         dataIndex: 'bal',
                         width : 130,
                         header: '数据日期',
                         align:'left',
                        	 sortable : true
//                         renderer: money('0,000.00')
                     }
                        
                     ]
        },{
//        	roles:'41,27,127,46,126,120,47',
        	name : 'TopN客户',
            modId : 'topNCust',
            isJump : false,
            jumpUrl : basepath+'/contents/pages/workSpace/functionBrowse/topNCustomer.jsp',
            action : basepath + '/topNQuery.json?condition='+Ext.encode({'target':'dep_bal_sum','n':'5'}),
            hidden:JsContext.checkGrant('topNCust'),
            dataNames:[{
                           header : '统计日期',
                           width : 130,
                           align : 'left',
                           dataIndex : 'crm_dt',
                           sortable : true
                   }, 
                   {
                           header : '客户名称',
                           width : 210,
                           align : 'left',
                           dataIndex : 'cust_name',
                           sortable : true
                   },
                   {
                           header : '组织机构代码',
                           width : 210,
                           align : 'left',
                           dataIndex : 'cust_zzdm',
                           sortable : true
                   },
                   {
                           header : '存款时点余额',
                           width : 210,
                           align : 'right',
                           dataIndex : 'dep_bal_sum',
                           sortable : true,
                           renderer: money('0,000.00')
                   },
                   {
                       header : '贷款时点余额',
                       width : 210,
                       align : 'right',
                       dataIndex : 'lon_bal_sum',
                       sortable : true,
                       renderer: money('0,000.00')
               }]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'公告',
            modId:'notice',
            isJump:true,
            jumpUrl:basepath+'/contents/pages/workSpace/afficheManage/affiche.jsp',
            action:basepath+'/noticequery.json',
            hidden:JsContext.checkGrant('notice'),
            dataNames:[{
                align:'left',
                dataIndex : 'NOTICE_TITLE',
                header : '公告标题'
            },{
                align:'left',
                dataIndex : 'NOTICE_LEVEL_ORA',
                header : '重要程度'
            },{
                align:'left',
                dataIndex : 'PUBLISHER_NAME',
                header: '发布人'
            },{
                align:'left',
                dataIndex : 'PUB_ORG_NAME',
                header : '发布机构'
            }]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'提醒',
            modId:'remind',
            isJump:false,
            jumpUrl:basepath+'/contents/pages/workSpace/remindManage/remindListNew.jsp',
            action:basepath+'/queryremindlist.json',
            hidden:JsContext.checkGrant('remind'),
            dataNames:[
                {header:'提醒类型',dataIndex:'MSG_TYP_ORA',sortable:true,width:130,align:'left'},
                {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:130,align:'left'},
                {header:'组织机构代码',dataIndex:'CUST_ZZDM',sortable:true,width:130,align:'left'},
                {header:'高管姓名',dataIndex:'MANAGER_NAME',sortable:true,width:130,align:'left'},
                {header:'事件名称',dataIndex:'EVENT_NAME',sortable:true,width:130,align:'left'}
            ]
        },{
//        	roles:'41,27,127,46,126,120,47',
            root:'json.data',
            name:'资讯',
            modId:'infomation',
            isJump:true,
            hidden:JsContext.checkGrant('infomation'),
            jumpUrl:basepath+'/contents/pages/repository/informationRetrieval.jsp',
            action:basepath+'/workingplatformInfoQuery.json',
            dataNames:[{
                align:'left',
                dataIndex : 'MESSAGE_TITLE',
                header : '文档标题'
            },{
                align:'left',
                dataIndex : 'MESSAGE_SUMMARY',
                header : '文档摘要'
            },{
                align:'left',
                dataIndex : 'PUBLISH_DATE',
                header : '发布时间',
                renderer:function(value, p, record){
                    if(typeof value =='string'){
                        return value.substring(0,10);
                    }else{
                        return value.format('Y-m-d');
                    }
                }
            },{
                align:'left',
                dataIndex : 'PUBLISH_USER',
                header : '发布人'
            }]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'营销计划',
            modId:'marketPlan',
            isJump:false,
            hidden:JsContext.checkGrant('marketPlan'),
            jumpUrl : basepath+'/contents/pages/mktManage/mktPlanManage/mktPlanList.jsp',
            action : basepath+'/planQuery.json',
            dataNames:[{
                header : '营销计划状态',
                width : 130,
                align : 'left',
                dataIndex : 'MKT_PLAN_STAT_ORA',
                sortable : true
            }, {
                header : '营销计划名称',
                width : 210,
                align : 'left',
                dataIndex : 'PLAN_NAME',
                sortable : true
            }, {
                header : '计划开始日期',
                width : 170,
                align : 'left',
                dataIndex : 'PLAN_START_DATE',
                sortable : true
            }, {
                header : '预计结束日期',
                width : 170,
                align : 'left',
                dataIndex : 'PLAN_END_DATE',
                sortable : true
        }
]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'营销活动',
            modId:'marketAct',
            isJump:false,
            hidden:JsContext.checkGrant('marketAct'),
            jumpUrl:basepath+'/contents/pages/mktManage/mktActivityManage/mktActivityList.jsp',
            action: basepath+'/market-activity.json',
            dataNames:[{
                header : '营销活动名称',
                width : 150,
                align : 'left',
                dataIndex : 'MKT_ACTI_NAME',
                sortable : true
        }, {
                header : '客户名称',
                width : 150,
                align : 'left',
                dataIndex : 'ACTI_CUST_NAME',
                sortable : true
        }, {
                header : '执行团队',
                width : 150,
                align : 'left',
                dataIndex : 'OPER_NAME',
                sortable : true
        }, {
                header : '开始日期',
                width : 150,
                align : 'left',
                dataIndex : 'ACTI_START_DATE',
                sortable : true
        }, {
                header : '结束日期',
                width : 150,
                align : 'left',
                dataIndex : 'ACTI_END_DATE',
                sortable : true
        }]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'商机',
            modId:'opportunity',
            isJump:false,
            hidden:JsContext.checkGrant('opportunity'),
            jumpUrl:basepath+'/contents/pages/mktManage/mktChanceManage/mktChanceList.jsp',
            action:basepath+'/marketOpportunityQuery.json',
            dataNames:[{
                header : '商机状态',
                width : 170,
                align : 'left',
                dataIndex : 'MKT_OPPOR_STAT_ORA',
                sortable : true
            }, {
                header : '商机名称',
                width : 175,
                align : 'left',
                dataIndex : 'MKT_OPPOR_NAME',
                sortable : true
            }, {
                header : '客户名称',
                width : 200,
                align : 'left',
                dataIndex : 'AIM_CUST_NAME',
                sortable : true
            }, {
                header : '执行团队',
                width : 150,
                align : 'left',
                dataIndex : 'OPER_USER_NAME',
                sortable : true
            },{
                header : '计划开始日期',
                width : 150,
                align : 'left',
                dataIndex : 'OPPOR_START_DATE',
                sortable : true
            }]
        },{
//        	roles:'41,27,127,46,126,120,47',
            name:'客户群管理',
            modId:'custGroup',
            isJump:true,
            hidden:JsContext.checkGrant('custGroup'),
            jumpUrl:basepath+'/contents/pages/customer/customerGroup/cusQuery.jsp',
            action:basepath+'/querycustomerbase.json',
            dataNames:[{
                header : '客户群编号',
                width : 150,
                align : 'left',
                dataIndex : 'CUST_BASE_NUMBER',
                sortable : true
            },{
                header : '客户群名称',
                width : 150,
                align : 'left',
                dataIndex : 'CUST_BASE_NAME',
                sortable : true
            },{
                header : '客户群创建时间',
                width : 150,
                align : 'left',
                dataIndex : 'CUST_BASE_CREATE_DATE',
                sortable : true
            },{
                header : '客户群描述',
                width : 170,
                align : 'left',
                dataIndex : 'CUST_BASE_DESC',
                sortable : true
            }]
        }/**,{
            name:'年度指标完成统计',
            modId:'yearTarget',
            isJump:false,
            jumpUrl:''
        },{
            name:'集团客户列表',
            modId:'groupCustList',
            isJump:false,
            jumpUrl:basepath+'/contents/pages/customer/groupClientManager/groupClientMaintenance1.jsp',
            action:basepath+'/ClientGroupInfoQueryAction.json',
            dataNames:[{
                header : '集团编号', // 列标题
                dataIndex : 'GROUP_NO', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            },{
                header : '集团名称', // 列标题
                dataIndex : 'GROUP_NAME', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            },{
                header : '授信主办行', // 列标题
                dataIndex : 'GROUP_HOST_ORG_NO_NAME', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            },{
                header : '集团类型', // 列标题
                dataIndex : 'GROUP_TYPE_ORA', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            }]

      
        },{
            name:'集团客户授信',
            modId:'groupCustCredit',
            isJump:false,
            jumpUrl:basepath+'/contents/pages/blocCredit/blocCreditValueApplyList.jsp',
            action:basepath+'/group-credit-info-query',
            root:'creditInfoMap.data',
            dataNames:[{
                header : '授信申请人', // 列标题
                dataIndex : 'APPLY_COM_NAME', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            },{
                header : '授信类型', // 列标题
                dataIndex : 'CREDIT_TYPE_ORA', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            },{
                header : '申请单状态', // 列标题
                dataIndex : 'APPLY_STATUS_ORA', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            },{
                header : '集团母公司名称', // 列标题
                dataIndex : 'GROUP_ROOT_NAME', // 数据索引:和Store模型对应
                sortable : true,// 是否可排序
                width : 150,
                align : 'left'
            }]
        }*/
    ]; 
    
    var memoryData = {
    		topNCust:[{
    			crm_dt : '2012-06-28',
    			cust_name : '王一平',
    			cust_zzdm : '2342324',
    			dep_bal_sum : 1233423,
    			lon_bal_sum : 425523
    		},{
    			crm_dt : '2012-06-28',
    			cust_name : '王二平',
    			cust_zzdm : '2536324',
    			dep_bal_sum : 1212423,
    			lon_bal_sum : 423523
    		},{
    			crm_dt : '2012-06-28',
    			cust_name : '王三平',
    			cust_zzdm : '2342323',
    			dep_bal_sum : 1033423,
    			lon_bal_sum : 421523
    		}],
    		custManager : [{
    			user_name:	'李敏',
    			rela_acct:  100000,
    			rela_pct:   100000,
    			bal:'2011-12-12'
    		},{
    			user_name:	'张耒',
    			rela_acct:  50000,
    			rela_pct:   30000,
    			bal:'2012-2-12'
    		},{
    			user_name:	'李海涛',
    			rela_acct:  80000,
    			rela_pct:   60000,
    			bal:'2010-12-12'
    		}],
    		groupCustCredit:[{
    			APPLY_COM_NAME:'张锦',
    			CREDIT_TYPE_ORA:'循环额度授信',
    			APPLY_STATUS_ORA:'审批通过',
    			GROUP_ROOT_NAME:'大河集团'
    		},{
    			APPLY_COM_NAME:'李涛',
    			CREDIT_TYPE_ORA:'大额授信',
    			APPLY_STATUS_ORA:'待审批',
    			GROUP_ROOT_NAME:'石油集团'
    		},{
    			APPLY_COM_NAME:'王磊',
    			CREDIT_TYPE_ORA:'小额授信',
    			APPLY_STATUS_ORA:'审批通过',
    			GROUP_ROOT_NAME:'矿产集团'
    		}],
    		custManAch:[{
    			user_name:'李勇',
    			rela_acct:'XX001',
    			rela_pct:'30%',
    			bal:'200000',
    			mon_avg:'150000',
    			quar_avg:'100000',
    			year_avg:'50000'
    		},{
    			user_name:'袁磊磊',
    			rela_acct:'XX002',
    			rela_pct:'45%',
    			bal:'100000',
    			mon_avg:'50000',
    			quar_avg:'30000',
    			year_avg:'20000'
    		},{
    			user_name:'马超',
    			rela_acct:'XX003',
    			rela_pct:'60%',
    			bal:'150000',
    			mon_avg:'100000',
    			quar_avg:'80000',
    			year_avg:'60000'
    		}],
    		attCust:[{
    			CUST_ZH_NAME:'马强',
    			CUST_ZZDM:'中国银行支行',
    			HY_CLASS_GP:'金融',
    			CREATE_DT:'2011-11-20'
    		},{
    			CUST_ZH_NAME:'童真',
    			CUST_ZZDM:'工商银行支行',
    			HY_CLASS_GP:'金融',
    			CREATE_DT:'2011-10-1'
    		},{
    			CUST_ZH_NAME:'李海',
    			CUST_ZZDM:'农业银行支行',
    			HY_CLASS_GP:'金融',
    			CREATE_DT:'2012-1-20'
    		}]
    };
    
    /**
     * @describe layout data array. U can add new layout or delete a layout.
     *           Or U can make it been read from DataBase.
     */
    var layoutData = [
        {
            xtype:'crm.check',
            layoutId:'oneColumn',
            id:'oneColumn',
            imgLabel:'onecolumnLabel',
            boxLabel:'一列布局',
            layoutPanel:'panelSet',
            checkBoxId:'oneColumncheck',
            checkBoxName:'swim',
            columnCount:1
        },{
            xtype:'crm.check',
            id:'twoColumn',
            layoutId:'twoColumn',
            imgLabel:'twocolumnLabel',
            boxLabel:'二列布局',
            layoutPanel:'panelSet',
            checkBoxId:'twoColumncheck',
            checkBoxName:'swims',
            columnCount:2
        },{
            xtype:'crm.check',
            id:'threeColumn',
            layoutId:'threeColumn',
            imgLabel:'threecolumnLabel',
            boxLabel:'三列布局',
            layoutPanel:'panelSet',
            checkBoxId:'threeColumncheck',
            checkBoxName:'swim',
            columnCount:3
        }
    ];