		imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js'
		        ]);
		var createView = true;
		var editView = false;
		var detailView = false;
		var needCondition = true;
		var lookupTypes = ['IF_FLAG','NOTICE_LEV',{
			TYPE : 'LOOKUP_TYPE',
			url : '/lookupMappingQuery.json',
			key : 'F_NAME',
			value : 'F_COMMENT',
			jsonRoot : 'json.data'
		}];
		var localLookup = {
			'READ' : [
			   {key : '',value : '全部'},
			   {key : 'lev001',value : '重要'},
			   {key : 'lev002',value : '一般'}
			]
		};
		var needGrid = true;
		needTbar = true;
		var url = basepath+'/noticequery.json';
//		var comitUrl = basepath+'/workplatnotice.json';
		var fields = [
		  		    {name: 'NOTICE_ID',hidden : true},
		  		    {name: 'NOTICE_TITLE', text : '公告标题',xtype:'productChoose',hiddenName:'noticeTile', searchField: true, viewFn : function(data){
		  		    	return '<b>'+data+'</b>';
		  		    }},                                   
		  		    {name: 'TOP_ACTIVE_DATE', text:'置顶时间至', xtype:'datefield', dataType : 'date', allowBlank : false},  
		  		    {name: 'NOTICE_CONTENT', text:'公告内容',  resutlWidth:350, xtype : 'htmleditor'},
		  		    {name: 'NOTICE_LEVEL_ORA', text : '重要程度',resutlFloat:'right', translateType : 'LOOKUP_TYPE'},
		  		    {name: 'PUBLISHER'},
		  		    {name: 'PUBLISH_ORG'},
		  		    {name: 'IS_TOP', text : '是否置顶',resutlFloat:'right', searchField: false, translateType : 'READ'},
		  		    {name: 'PUBLISH_TIME'},
		  		    {name: 'MODULE_TYPE'},
		  		    {name: 'PUBLISHED'},
		  		    {name: 'ACTIVE_DATE', text : '有效期至',resutlFloat:'right',searchField: false, xtype : 'wcombotree',innerTree:'KNOWLEDGETREECOMBO',showField:'sectionName',hideField:'id'},
		  		    {name: 'NOTICE_TYPE'},
		  		    {name: 'IS_READ_ORA'},
		  		    {name: 'CREATOR'},
		  		    {name: 'PUBLISHER_NAME',text : '发布人',resutlFloat:'right',searchField: true},
		  		    {name: 'PUB_ORG_NAME'},
		  		    {name: 'CREATOR_NAME'},
		  		    {name: 'ANN_COUNT'}     
		  		];
		/**新增、修改、详情设计**/
		var createFormViewer = [{
			fields : ['TOP_ACTIVE_DATE','NOTICE_TITLE','ERRORFIELD','IS_TOP','NOTICE_LEVEL_ORA','PUBLISHER_NAME','ACTIVE_DATE','sdfsd'],
			fn : function(TOP_ACTIVE_DATE,NOTICE_TITLE,ERRORFIELD,IS_TOP,NOTICE_LEVEL_ORA,PUBLISHER_NAME,ACTIVE_DATE){
				Ext.debug(NOTICE_TITLE);
				NOTICE_TITLE.hidden = true;
				NOTICE_TITLE.value = 1111;
				Ext.debug(NOTICE_TITLE);
				return [NOTICE_TITLE,TOP_ACTIVE_DATE,ERRORFIELD,IS_TOP,NOTICE_LEVEL_ORA,PUBLISHER_NAME,ACTIVE_DATE];
			}
		},{
			columnCount : 1 ,
			fields : ['NOTICE_CONTENT'],
			fn : function(NOTICE_CONTENT){
				return [NOTICE_CONTENT];
			}
		}];
		
		var formViewers = [{
			fields : ['TOP_ACTIVE_DATE','NOTICE_TITLE','IS_TOP','NOTICE_LEVEL_ORA','PUBLISHER_NAME','ACTIVE_DATE'],
			fn : function(TOP_ACTIVE_DATE,NOTICE_TITLE,IS_TOP,NOTICE_LEVEL_ORA,PUBLISHER_NAME,ACTIVE_DATE){
				return [NOTICE_TITLE,TOP_ACTIVE_DATE,IS_TOP,NOTICE_LEVEL_ORA,PUBLISHER_NAME,ACTIVE_DATE];
			}
		},{
			columnCount : 1 ,
			fields : ['NOTICE_CONTENT'],
			fn : function(NOTICE_CONTENT){
				NOTICE_CONTENT.xtype = 'textarea';
				return [NOTICE_CONTENT];
			}
		}];
		
		
		var formCfgs = {
			formButtons : [{
				text : 'aNewCreateButton',
				fn : function(formPanel, basicForm){
					showNextView();
				}
			}]
		};
		
		/**校验对象**/
//		var validates = [{
//			desc : '校验规则1',
//			/**TOP_ACTIVE_DATE 日期大于当前日期**/
////			fn : function(NOTICE_TITLE,TOP_ACTIVE_DATE,PUBLISHER,sdfsdf){
////				if(Date.parseDate(TOP_ACTIVE_DATE,'Y-m-d')<new Date()){
////					return true;
////				}
////			},
//			dataFields : ['NOTICE_TITLE','TOP_ACTIVE_DATE','PUBLISHER','sdfsdf']
//		},{
//			//desc : '校验规则2',
//			/**NOTICE_ID长度大于0**/
//			dataFields : ['NOTICE_TITLE','NOTICE_ID'],
//			fn : function(NOTICE_TITLE,NOTICE_ID){
//				if(NOTICE_TITLE.length > 0){
//					return false;
//				}
//			}
//		},{
//			desc : '校验规则3',
//			dataFields : ['NOTICE_CONTENT'],
//			fn : function(nc){
//				return false;
//			}
//		}];
//		var editValidates = [{
//			/**NOTICE_ID长度大于0**/
//			dataFields : ['NOTICE_TITLE','NOTICE_ID'],
//			fn : function(NOTICE_TITLE,NOTICE_ID){
//				if(NOTICE_TITLE.length == 0){
//					return false;
//				}
//			}
//		}];
		
		
		/**数据联动，当数据发生变化，且失去焦点的时候，会调用该联动逻辑**/
		var linkages = {
			ACTIVE_DATE : {
				fields : ['NOTICE_CONTENT'],
				fn : function(NOTICE_TITLE, NOTICE_CONTENT){
					debugger;
				}
			},
			sdf : {}
		};
		/***
		 * TODO 
		 */
		var customerView = [{
			title : 'hello grid',
			url : basepath + '/noticequery.json',
			type : 'grid',
			pageable : true,
			frame : true,
			buttonAlign: 'center',
			fields : {
				fields : ['NOTICE_TITLE',{
					name : 'F2',
					text : 'F2'
				}],
				fn : function(NOTICE_TITLE, F2){
					return [F2,NOTICE_TITLE];
				}
			},
			gridButtons : [{
				text:'sdfsdf',
				fn : function(grid){
					
				getCustomerViewByTitle('hello form').contentPanel.getForm().setValues({
					ACTIVE_DATE : 'f1value',
					F2 : 'f2value'
				});
					showCustomerViewByTitle('hello form');
				}
			}]
		},{
			title : 'hello on type',
			buttons:[{
				text : 'sfsdf',
				handler : function(){
					showCustomerViewByTitle('hello form');
				}
			}]
		},{
			title : 'hello form',
			type : 'form',
			//hideTitle : true,
			autoLoadSeleted : true,
			groups : [{
				columnCount : 1 ,
				fields : ['NOTICE_TITLE', {
					name : 'F2',
					xtype : 'textfield',
					translateType : 'READ',
					text : 'F2'
				}],
				fn : function(NOTICE_TITLE, F2){
					return [F2,NOTICE_TITLE];
				}
			}],
			formButtons : [{
				text : 'sdf',
				fn : function(formPanel,basicForm){
					showNextView();
				}
			}]
		}];
		
		var tbar = [{
			text : '删除',
			handler : function(){
				//collapseSearchPanel();
//				reloadLookup('LOOKUP_TYPE');
//				reloadLookup('IF_FLAG');
//				reloadLookup('READ');
				//basepath + '/customerBaseInformation!NameFind.json'
			}
		},{
			text : '展开',
			handler : function(){
				expandSearchPanel();
			}
		},{
			text : 'getLeft',
			handler : function(){
//				Ext.Ajax.request({
//					url : url,
//					async : false,//同步加载Ajax
//					method : 'GET',
//					waitMsg : '正在校验数据,请等待...', 
//					success : function(response,a,b,c) {
//						debugger;
//					},
//					failure : function() {
//					}
//				});
				debugger;
			}
		}];
		var treeLoaders = [{
			key : 'KNOWLEDGELOADER',
			url : basepath + '/workplatforminfosection!indexAll.json',
			jsonRoot : 'json.data',
			parentAttr : 'parentSection',
			locateAttr : 'sectionId',
			rootValue : 'root',
			textField : 'sectionName',
			idProperties : 'sectionId'
		},{
			key : 'KNOWLEDGELOADER1',
			url : basepath + '/workplatforminfosection!indexAll.json',
			jsonRoot : 'json.data',
			parentAttr : 'parentSection',
			locateAttr : 'sectionId',
			rootValue : 'root',
			textField : 'sectionName',
			idProperties : 'sectionId'
		}];
		var treeCfgs = [{
			key : 'KNOWLEDGETREE',
			loaderKey : 'KNOWLEDGELOADER',
			//title : '资讯文档树',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				text:'全部目录',
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){
//				/**
//				 * 调用windows域API；
//				 */
//				showNextView();
				getCurrentView().setValues({
					NOTICE_CONTENT : 'lskjdf;laskjfd',
					PUBLISHER_NAME : '123123'
				});
			}
		},{
			key : 'KNOWLEDGETREECOMBO',
			loaderKey : 'KNOWLEDGELOADER1',
			//title : '资讯文档树',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				text:'全部目录',
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){
				/**
				 * 调用windows域API；
				 */
				debugger;
//				setSearchParams({
//					NOTICE_TITLE : '关于'
//				});
			}
		}];
		var edgeVies = {
				left : {
					width : 200,
					layout : 'form',
					items : [TreeManager.createTree('KNOWLEDGETREE')]
//					listeners : {
//						afterrender : function(p){
//							p.add();
//							p.doLayout();
//						}
//					}
				},
				right : {
					items : [TreeManager.createTree('KNOWLEDGETREECOMBO'),TreeManager.createTree('KNOWLEDGETREE')]
				}//,
//				buttom : {
//					items : [{
//						html : '123',
//						title : 'lset'
//					},{
//						html : '123',
//						title : 'sdfs'
//					}]
//				},
//				top : {
//					html : 'ljw'
//				}
		
			};
		/***************************************************************************/
		
//		var treeLoaders = [{
//			key : 'MENUTREELOADER',
////			url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
//			url : basepath + '/indexinit.json',
////			parentAttr : 'SUPERUNITID',
////			locateAttr : 'UNITID',
//			jsonRoot:'json.data',
////			rootValue : JsContext._orgId,
////			textField : 'UNITNAME',
////			idProperties : 'UNITID'
//			parentAttr : 'PARENT_ID',
//			locateAttr : 'ID',
//			rootValue : "0",
//			textField : 'NAME',
//			idProperties : 'ID'
//		}
//		,{
//			key : 'MODULETREELOADER',
////			url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
//			url : basepath + '/fwFunction-Action.json',
////			parentAttr : 'SUPERUNITID',
////			locateAttr : 'UNITID',
//			jsonRoot:'json.data',
////			rootValue : JsContext._orgId,
////			textField : 'UNITNAME',
////			idProperties : 'UNITID'
//			parentAttr : 'PARENT_ID',
//			locateAttr : 'ID',
//			rootValue : '0',
//			textField : 'NAME',
//			idProperties : 'ID'
//		}
//		];
//
//		
//		
//		var treeCfgs = [{
//			key : 'MENUTREE',
//			loaderKey : 'MENUTREELOADER',
//			autoScroll:true,
//			rootCfg : {
//				expanded:true,
//				text:'主菜单',
//				autoScroll:true,
//				children:[]
//			},
//			clickFn : function(node){}
//		}
//		,{
//			key : 'MODULETREE',
//			loaderKey : 'MODULETREELOADER',
//			autoScroll:true,
//			rootCfg : {
//				expanded:true,
//				text:'功能模块菜单',
//				autoScroll:true,
//				children:[]
//			},
//			clickFn : function(node){
//				Ext.debug(node.attributes);
//			}
//		}
//		];
//
//		
//		
//		
//		var edgeVies = {
//				left : {
//					width : 200,
//					layout : 'form',
//					items : [TreeManager.createTree('MENUTREE')]
//				}
//		,
//				right : {
//					width : 200,
//					items : [TreeManager.createTree('MODULETREE')]
//				}//,
//			};
//
//		
//		
//		
//		
		
		
		
		
		/***************************************************************************/
		
		/**APP构建事件**/
//		var beforeinit = function(app){};
//		var afterinit = function(app){};
		
		var tmpfunction = function(){};
		
		/**查询条件域事件**/
		var beforeconditioninit = function(panel, app){
			Ext.debug(panel.id);
		};
		var afterconditioninit = function(panel, app){};
		var beforeconditionrender = function(panel, app){};
		var afterconditionrender = function(panel, app){};
		var beforeconditionadd = function(fCfg, columnIndexT, searchPanel){};
		var conditionadd = function(field, searchPanel){};
		var beforeconditionremove = function(field, searchPanel){};
		var conditionremove = function(searchPanel){};
		function beforedyfieldclear(searchDomain, searchpanel, dyfield){
			Ext.log('asdfasfdsadfasfasdf');
		}
		function afterdyfieldclear(searchDomain, searchpanel, dyfield){
			
		}
		function beforeconditioncollapse(panel){
		}
		function afterconditioncollapse(panel){
		}
		function beforeconditionexpand(panel){
		}
		function afterconditionexpand(panel){
		}
		
		/**查询结果域操作**/
		var turnpage = function(store, pageindex){};
		var recordselect = function(record, store, tile){};
		function rowdblclick(tile, record){
		};
		var load = function(){};
		var beforesetsearchparams = function(params, forceLoad, add, transType){};
		var setsearchparams = function(params, forceLoad, add, transType){};
		var beforeresultinit = function(panel, app){};
		var afterresultinit = function(panel, app){};
		var beforeresultrender = function(panel, app){};
		var afterresultrender = function(panel, app){};

		
		/**查询结果域附加面板事件**/
		var beforecreateviewrender = function(view){};
		var aftercreateviewrender = function(view){};
		var beforeeditviewrender = function(view){};
		var aftereditviewrender = function(view){};
		var beforedetailviewrender = function(view){};
		var afterdetailviewrender = function(view){};
		var beforeviewshow = function(view){
			debugger;
			//return false;
		};
		var viewshow = function(view){
			debugger;
			if(view === getCreateView())
				view.contentPanel.getForm().setValues({
					ACTIVE_DATE : 'f1value',
					F2 : 'f2value'
				});
		};
		var beforeviewhide = function(view){};
		var viewhide = function(view){};
		var beforevalidate =function(view, form){};
		var validate = function(view, form, error){
			Ext.debug(error);
		};
		var beforecommit = function(data, cUrl){
			debugger;
		};
		var afertcommit = function(data, cUrl, result){
			debugger;
		};
//		var beforeeditload = function(view, record){};
//		var aftereditload = function(view, record){};
		function beforedetailload(){
		}
		
		/**边缘面板事件**/
		var beforetophide = function(){};
		var tophide = function(){};
		var beforetopshow = function(){};
		var topshow = function(){};
		var beforelefthide = function(){};
		var lefthide = function(){};
		var beforeleftshow = function(){};
		var leftshow = function(){};
		var beforebuttomhide = function(){};
		var buttomhide = function(){};
		var beforebuttomshow = function(){};
		var buttomshow = function(){};
		var beforerighthide = function(){};
		var righthide = function(){};
		var beforerightshow = function(){};
		var rightshow = function(){};

		
		/**数据字典事件**/
		var lookupinit = function(key, store){};
		var locallookupinit = function(key, store){};
		var alllookupinit = function(lookupManager){};

		
		/**属性面板事件**/
		var beforetreecreate = function(){};
		//var treecreate = function(){};