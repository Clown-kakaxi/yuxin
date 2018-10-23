/**
 * @description 打印预览之 客户信息变更历史
 * @author helin
 * @since 2014-10-28
 */
Ext.onReady(function(){
	var updateHisRecord = new Ext.data.Record.create([
	  {name:'CUST_ID'},
	  {name:'UPDATE_ITEM'},
	  {name:'UPDATE_BE_CONT'},
	  {name:'UPDATE_AF_CONT_VIEW'},
	  {name:'UPDATE_USER'},
	  {name:'USER_NAME'},
	  {name:'APPR_NAME'},
	  {name:'APPR_DATE'}
	]);
	// create the data store
	var updateHisStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithFsx!queryCustUpdateHis.json'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },updateHisRecord)
	});
	var cond = {
		UPDATE_ITEM: UPDATE_ITEM,
		UPDATE_USER: UPDATE_USER,
		START_DATE: START_DATE,
		END_DATE: END_DATE
	};
	updateHisStore.baseParams = {
		condition:Ext.encode(cond),
		custId : custId
	};
	
	var html = '<div class="showPrintContent"><div class="Noprint"><input type="button" class="button" value="打印" onclick="printPageCurr();"><div id="beforeEnd"></div></div>';
	html += '<div id="showPrintView">';
	html +='<div class="single"><div class="titleDiv">客户信息变更单</div><br><table class="print_tab_01">';
	html +='<div class="title2">客户号：'+custId+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户名称：'+custName+' <span class="endDiv">打印时间：'+new Date().format('Y-m-d')+'</span></div><br>';
	html +='<tr><td width="20%">修改项目</td><td width="20%">变更前内容</td><td width="20%">变更后内容</td><td width="10%">变更人</td><td width="10%">复核人</td><td width="15%">复核时间</td></tr>';
	updateHisStore.load({
        callback: function(){
        	var size = updateHisStore.getCount();
        	if(size > 0){
        		for(var i=0;i<size;i++){
        			var data = updateHisStore.getAt(i).data;
        			html +='<tr><td width="20%">'+data.UPDATE_ITEM+'</td><td width="20%">'+data.UPDATE_BE_CONT+'</td><td width="20%">'+data.UPDATE_AF_CONT_VIEW+'</td><td width="10%">'+data.USER_NAME+'</td><td width="10%">'+data.APPR_NAME+'</td><td width="15%">'+data.APPR_DATE+'</td></tr>';
        		}
        	}else{
        		html +='<tr><td colspan=6>无变更内容</td></tr>';
        	}
        	html +='</table>';
			html += '</div></div>';
        	printPanel.removeAll();
        	printPanel.add({
        		autoScroll : true,
        		html:html
        	});
        	printPanel.doLayout();
			
        }
    });
			
    var printPanel = new Ext.Panel({
    	layout:'fit',
		items:[]
	});
    
    var wbPanel = new Ext.Panel({
		id : 'wbPanel',
		hidden: true
	});
	
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [printPanel,wbPanel]
	});
});