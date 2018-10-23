	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = '';
		var nodeid = '';
		if(window.location.href.split("instanceid=")[1]!=undefined){
			instanceid = window.location.href.split("instanceid=")[1];
			nodeid = window.location.href.split("nodeid=")[1];
		}
		/**
		 * 数据存储
		 */
		var xlStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data : [['1','良好'], ['2','一般' ], ['3','较差' ]]
		});
		var rsRecord = Ext.data.Record.create([ 
		                            {name:'id',mapping:'ID'},
{name:'userId',mapping:'USER_ID'},
{name:'custMgrName',mapping:'CUST_MGR_NAME'},
{name:'affiGroup',mapping:'AFFI_GROUP'},
{name:'entrTime',mapping:'ENTR_TIME'},
{name:'assMon',mapping:'ASS_MON'},
{name:'loanCurrRelCnt',mapping:'LOAN_CURR_REL_CNT'},
{name:'loanCurrMissCnt',mapping:'LOAN_CURR_MISS_CNT'},
{name:'loanOveRatio',mapping:'LOAN_OVE_RATIO'},
{name:'savEndmonCnt',mapping:'SAV_ENDMON_CNT'},
{name:'savMondayAve',mapping:'SAV_MONDAY_AVE'},
{name:'upmonWrkSum',mapping:'UPMON_WRK_SUM'},
{name:'proIdeaFb',mapping:'PRO_IDEA_FB'},
{name:'empPlan',mapping:'EMP_PLAN'},
{name:'assTime',mapping:'ASS_TIME'},
{name:'achiEffic',mapping:'ACHI_EFFIC'},
{name:'chkAnalyse',mapping:'CHK_ANALYSE'},
{name:'commuExpand',mapping:'COMMU_EXPAND'},
{name:'reportExp',mapping:'REPORT_EXP'},
{name:'operFlow',mapping:'OPER_FLOW'},
{name:'leanAbility',mapping:'LEAN_ABILITY'},
{name:'timeMgr',mapping:'TIME_MGR'},
{name:'wrkAttitude',mapping:'WRK_ATTITUDE'},
{name:'groupColla',mapping:'GROUP_COLLA'},
{name:'status',mapping:'STATUS'},
{name:'behavRule',mapping:'BEHAV_RULE'},
{name:'sevAttitude',mapping:'SEV_ATTITUDE'},
{name:'subjEvaluate',mapping:'SUBJ_EVALUATE'},
{name:'evaTime',mapping:'EVA_TIME'}
		                                							 
		                                	    ]);
			var rsreader = new Ext.data.JsonReader({
				successProperty : 'success',
				idProperty : 'ID',
				messageProperty : 'message',
				root : 'json.data',
				totalProperty : 'json.count'
			}, rsRecord);
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/querycustmgrpg.json?ID='+instanceid,
				        			method:'get'
				        		}),
				        reader: rsreader
					});
	    var panel2 = new Ext.FormPanel({ 
	        formId:'panel2',
			frame:true,
			autoScroll:true,
			bodyStyle:'padding:5px 5px 0',
			title : '<span style="font-weight:normal">客户经理评估信息</span>',
			width: '100%',
//		    height:380,
			items: [
			        {
		               xtype:'fieldset',
		               id:'fieldset1',
		               title: '客户经理评估信息', 
		               items:[
		                      {
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 120, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [{name:'status',xtype:'textfield',hidden:true},
					         {id:'pg1',fieldLabel : '客户经理编号',name : 'userId', labelStyle: 'text-align:right;',xtype : 'textfield', anchor : '95%'},
					         {id:'pg2',fieldLabel : '所在团队',name : 'affiGroup', labelStyle: 'text-align:right;',xtype : 'textfield', anchor : '95%'   },
					         {id:'pg3',fieldLabel : '评定月份',name : 'assMon', labelStyle: 'text-align:right;', format:'Ym',xtype : 'textfield', anchor : '95%'   },
					         {id:'pg4',fieldLabel : '贷款本月任务笔数',name : 'loanCurrMissCnt', labelStyle: 'text-align:right;',xtype : 'textfield', anchor : '95%'   }
					         ]
				},{
					columnWidth : .3,
					layout : 'form',
					labelWidth : 120, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [
					         
					         {id:'pg5',fieldLabel : '贷款月末时点数',name : 'savEndmonCnt', labelStyle: 'text-align:right;',xtype : 'textfield', anchor : '95%'   },
					         {id:'pg6',xtype:'textfield',fieldLabel : '客户经理名称',  labelStyle: 'text-align:right;',name : 'custMgrName', allowBlank : true,  anchor : '95%' },
					         {id:'pg7',xtype:'datefield',format:'Y-m-d',fieldLabel : '入行时间',  labelStyle: 'text-align:right;',name : 'entrTime', allowBlank : true,  anchor : '95%' },
					         {id:'pg8',xtype:'textfield',fieldLabel : '贷款本月放款笔数',  labelStyle: 'text-align:right;',name : 'loanCurrRelCnt', allowBlank : true,  anchor : '95%' }]
				}, {
					columnWidth : .3,
					layout : 'form',
					labelWidth : 120, // 标签宽度
					//defaultType : 'textfield',
					border : false,
					items : [
					         
					         {id:'pg9',xtype:'textfield',fieldLabel : '贷款完成率',  labelStyle: 'text-align:right;',name : 'loanOveRatio', allowBlank : true,  anchor : '95%' },
					         {id:'pg10',xtype:'textfield',fieldLabel : '贷款月日均',  labelStyle: 'text-align:right;',name : 'savMondayAve', allowBlank : true,  anchor : '95%' },
								{id:'pg11',fieldLabel : '评定时间',name : 'assTime',labelStyle: 'text-align:right;',xtype : 'datefield',format:'Y-m-d', anchor : '95%'}]
				}]
			},{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .95,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							defaultType : 'textarea',
							border : false,
							items : [{id:'pg12',
										fieldLabel : '上月工作总结',
										
										name : 'upmonWrkSum',
										 labelStyle: 'text-align:right;',
										xtype : 'textarea', // 设置为数字输入框类型
										anchor : '95%'
									}]
						}]
			},{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .95,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							defaultType : 'textarea',
							border : false,
							items : [{id:'pg13',
										fieldLabel : '意见问题反馈',
										
										name : 'proIdeaFb',
										 labelStyle: 'text-align:right;',
										xtype : 'textarea', // 设置为数字输入框类型
										anchor : '95%'
									}]
						}]
			},{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .95,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							defaultType : 'textarea',
							border : false,
							items : [{id:'pg14',
										fieldLabel : '职业规划',
										
										name : 'empPlan',
										 labelStyle: 'text-align:right;',
										xtype : 'textarea', // 设置为数字输入框类型
										anchor : '95%'
									}]
						}]
			}
			]},{
	               xtype:'fieldset',
	               id:'fieldset2',
	               title: '主管评价', 
	               items:[
			{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .3,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [
							         {id:'pj1',fieldLabel : '调查分析',name : 'chkAnalyse',resizable : true,hiddenName : 'chkAnalyse',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',labelStyle: 'text-align:right;',store:xlStore,xtype : 'combo', anchor : '95%'   },
							         {id:'pj2',fieldLabel : '汇报表达',name : 'reportExp',resizable : true,hiddenName : 'reportExp',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择', labelStyle: 'text-align:right;', store:xlStore,	xtype : 'combo', anchor : '95%'   },
							         {id:'pj3',fieldLabel : '学习能力',name : 'leanAbility',resizable : true,hiddenName : 'leanAbility',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择', labelStyle: 'text-align:right;', store:xlStore,	xtype : 'combo', anchor : '95%'   },
							         {id:'pj4',fieldLabel : '工作态度',name : 'wrkAttitude',resizable : true,hiddenName : 'wrkAttitude',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择', labelStyle: 'text-align:right;', store:xlStore,	xtype : 'combo', anchor : '95%'   }
							         ]
						}, {
							columnWidth : .3,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							//defaultType : 'textfield',
							border : false,
							items : [{id:'pj5',fieldLabel : '行为准则',name : 'behavRule',resizable : true,hiddenName : 'behavRule',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择', labelStyle: 'text-align:right;', store:xlStore,	xtype : 'combo', anchor : '95%'   	},
							         {id:'pj6',fieldLabel : '业绩效率', labelStyle: 'text-align:right;',name : 'achiEffic', resizable : true,hiddenName : 'achiEffic',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',allowBlank : true, store:xlStore,xtype : 'combo', anchor : '95%'   },
							         {id:'pj7',fieldLabel : '沟通拓展', labelStyle: 'text-align:right;',name : 'commuExpand', resizable : true,hiddenName : 'commuExpand',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',allowBlank : true, store:xlStore,xtype : 'combo', anchor : '95%'   },
							         {id:'pj8',fieldLabel : '操作流程', labelStyle: 'text-align:right;',name : 'operFlow', resizable : true,hiddenName : 'operFlow',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',allowBlank : true, store:xlStore,xtype : 'combo', anchor : '95%'   }]
						}, {
							columnWidth : .3,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							//defaultType : 'textfield',
							border : false,
							items : [
							         {id:'pj9',fieldLabel : '时间管理', labelStyle: 'text-align:right;',name : 'timeMgr', resizable : true,hiddenName : 'timeMgr',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',allowBlank : true, store:xlStore,xtype : 'combo', anchor : '95%'   },
							         {id:'pj10',fieldLabel : '团队协作', labelStyle: 'text-align:right;',name : 'groupColla', resizable : true,hiddenName : 'groupColla',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',allowBlank : true, store:xlStore,xtype : 'combo', anchor : '95%'   },
							         {id:'pj11',fieldLabel : '服务态度', labelStyle: 'text-align:right;',name : 'sevAttitude', resizable : true,hiddenName : 'sevAttitude',valueField : 'myId',displayField : 'displayText',mode : 'local',typeAhead : true,forceSelection : true,editable:false,triggerAction : 'all',emptyText : '请选择',allowBlank : true, store:xlStore,xtype : 'combo', anchor : '95%'   }]
						}]
			},{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .95,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							defaultType : 'textarea',
							border : false,
							items : [{
										id:'pj12',fieldLabel : '主管评价',
										name : 'subjEvaluate',
										 labelStyle: 'text-align:right;',
										xtype : 'textarea', // 设置为数字输入框类型
										anchor : '95%'
									}]
						}]
			},{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .95,
							layout : 'form',
							labelWidth : 120, // 标签宽度
							defaultType : 'datefield',
							border : false,
							items : [{
										id:'pj13',fieldLabel : '评价时间',
										name : 'evaTime',
										 labelStyle: 'text-align:right;',
										xtype : 'datefield',format:'Y-m-d', // 设置为数字输入框类型
										anchor : '95%'
									}]
						}]
			},{
				name:'id',xtype:'hidden'
			}]}],
			buttonAlign:'center',
			buttons:[
			        {
			        	text:'保存',
			        	id:'savbut',
			        	handler:function(){
			        	if(!panel2.getForm().isValid())
                        { 
                            alert('请填写正确信息');
                            return false;
                        }
						Ext.Ajax.request({
						    url:basepath+'/customerManagerEstimate.json',
						    method: 'POST',
						    params:panel2.getForm().getValues(),
						    success : function(){
								Ext.Msg.alert('提示', '操作成功');
								store.reload({params : {
					                id:instanceid
					            },
					            callback:function(){
					            	if(store.getCount()!=0){
					            		loadFormData();
					            	}
								}});
						},
                            failure :  function(){
								Ext.Msg.alert('提示', '操作失败');
								store.reload({params : {
					                id:instanceid
					            },
					            callback:function(){
					            	if(store.getCount()!=0){
					            		loadFormData();
					            	}
								}});
						}
						});
			        }
			        }]
			});
	    
			// 布局模型
			var viewport = new Ext.Viewport( {
				layout : 'fit',
				items : [ panel2 ]
			});
			
			store.load({params : {
                id:instanceid
            },
            callback:function(){
//            	alert(store.getCount());
//            	alert(store.getAt(0).data.ID);
            	if(store.getCount()!=0){
            		loadFormData();
            	}
				viewport.render('info');
//				viewport.doLayout();    //如果render不正常，调用此方法
//				addRoleWindow.show();
			}});
			
			function loadFormData(){
        		panel2.getForm().loadRecord(store.getAt(0));
        		Ext.getCmp('pg7').setValue(store.getAt(0).get('entrTime').substring(0,10));
        		Ext.getCmp('pg11').setValue(store.getAt(0).get('assTime').substring(0,10));
        		Ext.getCmp('pj13').setValue(store.getAt(0).get('evaTime').substring(0,10));
			}
			
			/*********************************判断当前节点******************************/
			function getNode(nodeid){
//				alert(nodeid);
				if(nodeid=='12_a6'){//客户经理提交评估节点
//					Ext.getCmp('fieldset1').enable();
					for(var i=1;i<15;i++){
						var pgid = 'pg'+i;
						Ext.getCmp(pgid).setReadOnly(false);
					}
//					Ext.getCmp('fieldset2').disable();
					for(var i=1;i<14;i++){
						var pjid = 'pj'+i;
						Ext.getCmp(pjid).setReadOnly(true);
					}
					Ext.getCmp('savbut').show();
				}else if(nodeid=='12_a4'){//主管评价节点
//					Ext.getCmp('fieldset1').disable();
					for(var i=1;i<15;i++){
						var pgid = 'pg'+i;
						Ext.getCmp(pgid).setReadOnly(true);
					}
//					Ext.getCmp('fieldset2').enable();
					for(var i=1;i<14;i++){
						var pjid = 'pj'+i;
						Ext.getCmp(pjid).setReadOnly(false);
					}
					Ext.getCmp('savbut').show();
				
				}else if(nodeid=='12_a5'){//督导查看节点
//					Ext.getCmp('fieldset1').disable();
					for(var i=1;i<15;i++){
						var pgid = 'pg'+i;
						Ext.getCmp(pgid).setReadOnly(true);
					}
//					Ext.getCmp('fieldset2').disable();
					for(var i=1;i<14;i++){
						var pjid = 'pj'+i;
						Ext.getCmp(pjid).setReadOnly(true);
					}
					Ext.getCmp('savbut').hide();
					
				}
			}
			
			getNode(nodeid);
		
			/***************************************************************/
		});
			/***************************************************************/