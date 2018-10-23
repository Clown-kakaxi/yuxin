/**
*@description 任务管理
*@author:luyy
*@since:2014-04-28
*@checkedby:
*/

		imports([
			        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
			        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'
			        ]);
		var createView = true;
		var editView = true;
		var detailView = true;
		
		var localLookup = {
			'FINISHSTATE' : [  //工作完成情况
			   {key : '1',value : '待办'},
			   {key : '2',value : '延后办理'},
			   {key : '3',value : '正在办理'},
			   {key : '4',value : '已完成'}
			],
			'GROUPID':[       //职能组
				   {key : '1',value : '综合业务组'},
				   {key : '2',value : '管理督导组'},
				   {key : '3',value : '后台业务组'},
				   {key : '4',value : '法律追讨组'},
				   {key : '5',value : '营销策划组'},
				   {key : '6',value : '文化宣传组'},
				   {key : '7',value : '业务培训组'},
				   {key : '8',value : '内部审计组'},
				   {key : '9',value : '绩能考核组'}
			    ]
		};
		
		var url = basepath+'/ocrmFWpSelcetAction.json';
		
		var comitUrl = basepath+'/ocrmFWpWorkTaskAction.json';
		
		var fields = [
		  		    {name: 'ID',hidden : true},
		  		    {name: 'START_DATE', text : '开始日期', xtype:'datefield',format:'Y-m-d',allowBlank:false},                                   
		  		    {name: 'END_DATE',text:'结束日期',xtype:'datefield',format:'Y-m-d',allowBlank:false},  
		  		    {name: 'TASK_CONTENT', text:'工作任务安排',   resutlWidth:350,xtype:'textarea',allowBlank:false},
		  		    {name: 'BURDEN_USER', text : '负责人',xtype:'userchoose',allowBlank:false},
		  		    {name: 'ASSIST_USER', text : '协办人',xtype:'userchoose'},
		  		    {name: 'GROUP_ID', text : '职能组', translateType : 'GROUPID',allowBlank:false},
		  		    {name: 'FINISH_STATE', text : '工作完成情况',searchField: true, translateType : 'FINISHSTATE',allowBlank:false}
		  		];
		
		var createFormViewer = [{
			fields : ['ID','START_DATE','END_DATE','BURDEN_USER','ASSIST_USER','GROUP_ID','FINISH_STATE'],
			fn : function(ID,START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE){
				FINISH_STATE.readOnly = true;
				return [ID,START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE];
			}
		},{
			columnCount : 1 ,
			fields : ['TASK_CONTENT'],
			fn : function(TASK_CONTENT){
				return [TASK_CONTENT];
			}
		}];
		
		var formViewers = [{
			fields : ['ID','START_DATE','END_DATE','BURDEN_USER','ASSIST_USER','GROUP_ID','FINISH_STATE'],
			fn : function(ID,START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE){
				return [ID,START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE];
			}
		},{
			columnCount : 1 ,
			fields : ['TASK_CONTENT'],
			fn : function(TASK_CONTENT){
				return [TASK_CONTENT];
			}
		}];
		
		var createValidates = [{
			desc : '开始日期小于当前日期!',
			dataFields : ['START_DATE'],
			fn : function(START_DATE){
				if(START_DATE.format('Y-m-d')<new Date().format('Y-m-d')){
					return false;
				}
			}
		},{
			desc : '结束日期小于当前日期!',
			dataFields : ['END_DATE'],
			fn : function(END_DATE){
				if(END_DATE.format('Y-m-d')<new Date().format('Y-m-d')){
					return false;
				}
			}
		},{
			desc : '结束日期小于开始日期!',
			dataFields : ['END_DATE','START_DATE'],
			fn : function(END_DATE,START_DATE){
				if(END_DATE<START_DATE){
					return false;
				}
			}
		}];
		
		var editValidates = [{
			desc : '结束日期小于开始日期!',
			dataFields : ['END_DATE','START_DATE'],
			fn : function(END_DATE,START_DATE){
				if(END_DATE<START_DATE){
					return false;
				}
			}
		}];
		
		var tbar = [{
			text : '收起',
			handler : function(){
				collapseSearchPanel();
			}
		},{
			text : '展开',
			handler : function(){
				expandSearchPanel();
			}
		},{
			text : '删除',
			handler : function(){
				if(getSelectedData() == false){
					Ext.Msg.alert('提示','请选择一条数据！');
					return false;
				}else if(getSelectedData().data.FINISH_STATE!='1'){
					Ext.Msg.alert('提示','只能删除[代办]状态的任务！');
					return false;
				}else{
					var ID = getSelectedData().data.ID;
					Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
						if(buttonId.toLowerCase() == "no"){
						return false;
						} 
					    Ext.Ajax.request({
		                    url: basepath+'/ocrmFWpWorkTaskAction!batchDestroy.json?idStr='+ID,                                
		                    success : function(){
		                        Ext.Msg.alert('提示', '删除成功');
		                        reloadCurrentData();
		                    },
		                    failure : function(){
		                        Ext.Msg.alert('提示', '删除失败');
		                        reloadCurrentData();
		                    }
		                });
					});
				}
			}
		}];
		
		var customerView = [{
			title : '任务处理',
			type : 'form',
			autoLoadSeleted : true,
			groups : [{
				fields : ['ID','START_DATE','END_DATE','BURDEN_USER','ASSIST_USER','GROUP_ID','FINISH_STATE'],
				fn : function(ID,START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE){
					START_DATE.readOnly = true;
					END_DATE.readOnly = true;
					BURDEN_USER.readOnly = true;
					ASSIST_USER.readOnly = true;
					GROUP_ID.readOnly = true;
					FINISH_STATE.readOnly = true;
					return [START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE,ID];
				}
			},{
				columnCount : 1 ,
				fields : ['TASK_CONTENT'],
				fn : function(TASK_CONTENT){
					TASK_CONTENT.readOnly = true;
					return [TASK_CONTENT];
				}
			}],
			formButtons : [{
				text:'延后办理',
				fn:function(){
					if(getSelectedData().data.FINISH_STATE=='2'){
						Ext.Msg.alert('提示','当前工作状态已为[延后办理]！');
						return false;
					}
					Ext.Ajax.request({
						url : basepath + '/ocrmFWpSelcetAction!updateStat.json',
						method : 'POST',
						params : {
							'id' :getSelectedData().data.ID,
							'stat' :'2'
						},
						success : function() {
							Ext.Msg.alert('提示','操作成功！');
							reloadCurrentData();
						}
					});
				}},{text:'正在办理',
					fn:function(){
						if(getSelectedData().data.FINISH_STATE=='3'){
							Ext.Msg.alert('提示','当前工作状态已为[正在办理]！');
							return false;
						}
						Ext.Ajax.request({
							url : basepath + '/ocrmFWpSelcetAction!updateStat.json',
							method : 'POST',
							params : {
								'id' :getSelectedData().data.ID,
								'stat' :'3'
							},
							success : function() {
								Ext.Msg.alert('提示','操作成功！');
								reloadCurrentData();
							}
						});
					}},{text:'提交下一节点',
						fn:function(){
							showCustomerViewByTitle('提交节点');
						}}
				]
		},{
			title : '提交节点',
			type : 'form',
			hideTitle : true,
			autoLoadSeleted : true,
			groups : [{
				fields : ['ID','START_DATE','END_DATE','BURDEN_USER','ASSIST_USER','GROUP_ID','FINISH_STATE'],
				fn : function(ID,START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE){
					START_DATE.readOnly = true;
					END_DATE.readOnly = true;
					BURDEN_USER.readOnly = false;
					ASSIST_USER.readOnly = false;
					GROUP_ID.readOnly = true;
					FINISH_STATE.readOnly = true;
					return [START_DATE,END_DATE,BURDEN_USER,ASSIST_USER,GROUP_ID,FINISH_STATE,ID];
				}
			},{
				columnCount : 1 ,
				fields : ['TASK_CONTENT'],
				fn : function(TASK_CONTENT){
					TASK_CONTENT.readOnly = true;
					return [TASK_CONTENT];
				}
			}],
			formButtons : [{text:'确定',
							fn:function(){
								Ext.Ajax.request({
									url : basepath + '/ocrmFWpSelcetAction!updateNode.json',
									method : 'POST',
									params : {
										'id' :getSelectedData().data.ID,
										'durden' :this.contentPanel.getForm().getValues().BURDEN_USER,
										'assist':this.contentPanel.getForm().getValues().ASSIST_USER
									},
									success : function() {
										Ext.Msg.alert('提示','操作成功！');
										reloadCurrentData();
									}
								});
							}
						}]
		}];

		/**修改和详情面板滑入之前判断是否选择了数据 ,任务处理面板，还要判断数据状态**/
		var beforeviewshow = function(view){
			if(view == getEditView()||view == getDetailView()){
					if(getSelectedData() == false){
						Ext.Msg.alert('提示','请选择一条数据');
						return false;
					}	
			}
			if(view._defaultTitle == '任务处理'){
				if(getSelectedData() == false){
					Ext.Msg.alert('提示','请选择一条数据！');
					return false;
				}else if(getSelectedData().data.FINISH_STATE=='4'){
					Ext.Msg.alert('提示','请选择非[已完成]状态的任务！');
					return false;
				}
			}
		};	
		
		/**新增面板滑入之后，设置执行状态**/
		var viewshow = function(view){
			if(view == getCreateView()){
				getCurrentView().setValues({
					FINISH_STATE: '1'
				});
			}
		};
		
		
		