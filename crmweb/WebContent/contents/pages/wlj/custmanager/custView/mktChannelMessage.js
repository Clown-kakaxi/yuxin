/**
 * 对私客户视图-渠道自动营销推送记录
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var url = basepath+'/ocrmFMmSysMsg.json?custId='+_custId+'&&flag=0';
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = !JsContext.checkGrant('custMKtMsgHis_detail');
    var lookupTypes=[
                     'MODEL_TYPE',
                     'IS_FEEDBACK'
                     ];
    
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'CUST_ID',text:'客户编号',searchField: false,hidden:true},
				  {name: 'PROD_ID',dataType:'string',text:"产品编号",hidden:true},	
				  {name: 'PROD_NAME',hiddenName:'PROD_ID',singleSelect:true,
  		        	  text:"营销产品",searchField : false,prodState:(''),riskLevel:('')},
	              {name:'MODEL_TYPE',text:'发送渠道',searchField:false,viewFn:function(val){
		            	if(val=='1'){
		            		return "短信";
		            	}else if(val=='2'){
		            		return '邮件';
		            	}else if(val=='3'){
		            		return '微信';
		            	}else if(val=='1,2'||val=='2,1'){
		            		return '短信,邮件';
		            	}else if(val=='3,2'||val=='2,3'){
		            		return '邮件,微信';
		            	}else if(val=='1,3'||val=='3,1'){
		            		return '短信,微信';
		            	}else if(val=='1,2,3'||val=='3,2,1'||val=='2,1,3'||
		            			val=='2,3,1'||val=='1,3,2'||val=='3,1,2'){
		            		return '短信,邮件,微信';
		            	}
		            		
		            }},
	              {name:'MODEL_NAME',text:'营销模板',dataType:'string',searchField:false,allowBlank:true},
	              {name:'MESSAGE_REMARK',text:'营销内容',xtype:'textarea',searchField:false,allowBlank:true},
	              {name:'CRT_DATE',text:'发送时间',dataType:'date',searchField:true,allowBlank:true},
	              {name:'USER_NAME',text:'发送人',searchField:true},
	              {name:'IS_FEEDBACK',text:'是否反馈',translateType:'IS_FEEDBACK',searchField:true},
	              {name:'FEEDBACK_CONT',text:'反馈内容',xtype:'textarea',searchField:false}
	              ];
	
	
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
	},new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('custMktMsgHis_export'),
        formPanel : 'searchCondition',
        url :basepath+'/ocrmFMmSysMsg.json?custId='+_custId+'&&flag=0'
    })];
	
	var detailFormViewer = [{
		columnCount : 2,
		fields : ['PROD_NAME','MODEL_TYPE','MODEL_NAME','CRT_DATE','USER_NAME','IS_FEEDBACK'],
		fn : function(PROD_NAME,MODEL_TYPE,MODEL_NAME,CRT_DATE,USER_NAME,IS_FEEDBACK){
			PROD_NAME.readOnly=true;
			PROD_NAME.cls='x-readOnly';
			MODEL_TYPE.readOnly=true;
			MODEL_TYPE.cls='x-readOnly';
			MODEL_NAME.readOnly=true;
			MODEL_NAME.cls='x-readOnly';
			CRT_DATE.readOnly=true;
			CRT_DATE.cls='x-readOnly';
			USER_NAME.readOnly=true;
			USER_NAME.cls='x-readOnly';
			IS_FEEDBACK.readOnly=true;
			IS_FEEDBACK.cls='x-readOnly';
			return [PROD_NAME,MODEL_TYPE,MODEL_NAME,CRT_DATE,USER_NAME,IS_FEEDBACK];
		}
	},{
		columnCount : 0.95 ,
		fields : ['FEEDBACK_CONT'],
		fn : function(FEEDBACK_CONT){
			FEEDBACK_CONT.readOnly=true;
			FEEDBACK_CONT.cls='x-readOnly';
			return [FEEDBACK_CONT];
		}
	},{
		columnCount : 0.95 ,
		fields : ['MESSAGE_REMARK'],
		fn : function(MESSAGE_REMARK){
			MESSAGE_REMARK.readOnly=true;
			MESSAGE_REMARK.cls='x-readOnly';
			return [MESSAGE_REMARK];
		}
	}];
	
	var customerView=[{
		title:'渠道营销信息反馈',
		hideTitle:JsContext.checkGrant('mktMsg_back'),
		type:'form',
		autoLoadSeleted:true,
		groups:[{
			fields:['ID'],
			fn:function(ID){
				return[ID];
			}
		},{
			columnCount:1,
			fields:['FEEDBACK_CONT'],
			fn:function(FEEDBACK_CONT){
				FEEDBACK_CONT.fieldLabel='<font color="red">*</font>反馈内容';
				FEEDBACK_CONT.allowBlank=false;
				return[FEEDBACK_CONT];
			}
		}],
		formButtons:[{
			text:'保存',
			fn:function(contentPanel,baseform){
				var commitData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				if (!baseform.isValid()) {
		    		 Ext.MessageBox.alert('系统提示信息', '请输入反馈内容！');
		    		 return false;
		    	 }
				Ext.Ajax.request({
                    url: basepath + '/ocrmFMmSysMsg!saveFeedBack.json',
                    method: 'POST',
                    params: commitData,
                    success: function(response) {
                    	Ext.Msg.alert('提示', '操作成功');
                        reloadCurrentData();
                    },
                    failure: function(){
                    	Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                    }
                });
			}
		}]
	}];
	
	var beforeviewshow=function(view){
		if(view._defaultTitle=='渠道营销信息反馈'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		}
		if(view==getDetailView()){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
			var MODEL_TYPE='';
			var val=getSelectedData().data.MODEL_TYPE;
			if(val=='1'){
				MODEL_TYPE ="短信";
        	}else if(val=='2'){
        		MODEL_TYPE = '邮件';
        	}else if(val=='3'){
        		MODEL_TYPE = '微信';
        	}else if(val=='1,2'||val=='2,1'){
        		MODEL_TYPE = '短信,邮件';
        	}else if(val=='3,2'||val=='2,3'){
        		MODEL_TYPE = '邮件,微信';
        	}else if(val=='1,3'||val=='3,1'){
        		MODEL_TYPE = '短信,微信';
        	}else if(val=='1,2,3'||val=='3,2,1'||val=='2,1,3'||
        			val=='2,3,1'||val=='1,3,2'||val=='3,1,2'){
        		MODEL_TYPE = '短信,邮件,微信';
        	}
			getSelectedData().data.MODEL_TYPE=MODEL_TYPE;
		
			
		}
	};
	
	//var beforeviewshow=function(view){
	
