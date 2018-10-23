/**
 * 分组统计结果面板
 */

	
	/**
	 * 分组统计结果数据源
	 */
	var groupingStore = new Ext.data.Store({
		restful : true,
		url:basepath+'/queryagileresult.json',
		reader:new Ext.data.JsonReader({
			successProperty: 'success',
			messageProperty: 'message',
			fields : [{
				name:'CUST_ID'
			}]
		})
	});
	//分组统计结果列模型
	var groupingColumnModel = new Ext.grid.ColumnModel(simple2.getResultColumnHeaders);
	
	var selectModel = new Ext.grid.CheckboxSelectionModel();
    var resultNumber = new Ext.grid.RowNumberer({
        header : 'No.',
        width : 40
    });
	var _tbar2 = new Ext.Toolbar({
		items : [new Com.yucheng.bob.ExcelButton({
			  iconCls:'exportIconCss',
			  expGrid:'groupingGrid'
		})]});
	//分组统计列表
	var groupingGrid = new Ext.grid.GridPanel({
		title :'统计结果',
		frame : true,
		region : 'center', // 返回给页面的div
		store : groupingStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : groupingColumnModel, // 列模型
		sm : selectModel,
		tbar:_tbar2,
		viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
//分组统计窗口
var groupingWindow = new Ext.Window({
	layout : 'fit',
	height : '400',
	width : '800',
	draggable : true,//是否可以拖动
	closable : true,// 是否可关闭
    title : '分组汇总统计展示',
	modal : true,
	closeAction : 'hide',
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	animCollapse : true,
	animateTarget : Ext.getBody(),
	constrain : true,
	items : [groupingGrid],
	buttons : [{
		text : '关闭',
		handler : function() {
			groupingWindow.hide();
			groupingStore.removeAll() ;//移除分组统计数据结果
		}
	}]
});
//分组统计逻辑
var groupingPropere = function(){
	
	/**
	 * 准备表格列模型
	 */
	var groupingColumns = [];
	groupingColumns.push(resultNumber);
	groupingColumns.push(selectModel);
	
	var groupParams = "";//分组字段参数
	var sumParams = "";//统计字段参数
	
	if(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue())){
		groupingColumns.push(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue()));
		groupParams += LovCombo1.getValue().substring(1);
	}
	if(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue())){
		groupingColumns.push(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue()));
		if(!groupParams){
			groupParams +=LovCombo2.getValue().substring(1);
		}else {
			groupParams +=','+LovCombo2.getValue().substring(1);
		}
	}
	if(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue())){
		groupingColumns.push(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue()));
		if(!groupParams){
			groupParams +=LovCombo3.getValue().substring(1);
		} else {
			groupParams +=','+LovCombo3.getValue().substring(1);
		}
	}
	var sumColumns = simple2.getSumColumnsByNodeIds(LovCombo4.getValue());
	Ext.each(sumColumns,function(s){
		groupingColumns.push(s);
	});
	sumParams = LovCombo4.getValue().replace(/b/g, "");//剔除字段ID前缀：b
	
	
	groupingColumnModel.setConfig(groupingColumns,false);
	if(groupingGrid.rendered)
		groupingGrid.view.refresh(true);
	
	/**
	 * 准备数据列，store.reader
	 */
	var readerColumns = [];
	
	Ext.each(groupingColumns,function(col){
		var refield = {};
		refield.name = col.dataIndex;
		readerColumns.push(refield);
	});
	groupingStore.reader.onMetaChange({
		successProperty: 'success',
		messageProperty: 'message',
		idProperty: 'CUST_ID',
		root:'json.data',
		totalProperty: 'json.count',
		fields : readerColumns
	});
	
	groupingStore.baseParams = {
		conditionAttrs : Ext.encode(simple.getConditionsAttrs()),
		results : Ext.encode(simple2.getResults()),
		radio : right_panel.conditionJoinType,
		groupParams : groupParams,
		sumParams : sumParams
	};
	
	//分组字段数据，用于添加合计数据
	var groupCallback = [];
	if(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue())!=undefined)
		groupCallback.push(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue()));
	if(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue())!=undefined)
		groupCallback.push(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue()));
	if(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue())!=undefined)
		groupCallback.push(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue()));
	
	
	Ext.each(groupCallback,function(d){
		d.tmpData = false;
	});
	
	/**
	 * 根据指定开始、结束位置和字段名，返回统计值
	 */
	var sumField = function(firstIndex,index,field){
		var result = 0;
		for(var tIndex = firstIndex ; tIndex <= index; tIndex ++){
			var r = groupingStore.getAt(tIndex);
			var v = parseFloat(r.data[field]);
			if(!isNaN(v)){
				result += v;
			}
		}
		
		return result;
	}
	
	/**
	 * 判断数据是否为一个分组结束位置
	 */
	function isGroup(record){
		var changeLevel = -1;
		for(var i=groupCallback.length-2 ; i >= 0 ; i--){
			var value = record.data[groupCallback[i].dataIndex];
			var tValue = groupCallback[i].tmpData;
			if(tValue === false){
				groupCallback[i].tmpData = value;
				continue;
			}
			if(value == tValue){
				continue;
			} else {
				changeLevel = i;
				groupCallback[i].tmpData = value;
				return changeLevel;
			}
		}
		return changeLevel;
	};
	
	//加载数据，回调函数中动态添加合计数据
	groupingStore.load({
		callback:function(a,b,c,d,e){
			var counts = [];//小计数据集合
			var trIndex=0;//数据遍历游标
			var lastChangeLevels = [];//各级分组上以此分组结束位置
			
			var lastAbstractRecordData = {};//虚拟最后一条数据，用于最后一个分组统计，统计结束后被移除
			Ext.each(groupCallback,function(a){
				lastAbstractRecordData[a.dataIndex] = "lastAbstractRecord";
			});
			var lastAbstractRecord = new Ext.data.Record(lastAbstractRecordData);
			groupingStore.add(lastAbstractRecord);
			//遍历数据集，根据本条数据与上一条数据差异，确定该条数据是否一个分组的开始
			this.each(function(record){
				var changelevel = isGroup(record);
				while(changelevel!= -1){
					
					var dataField = {};
					dataField[groupCallback[changelevel].dataIndex] = "&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'>小计</font>";
					Ext.each(sumColumns,function(sc){
						dataField[sc.dataIndex] = sumField(lastChangeLevels[changelevel]?lastChangeLevels[changelevel]:0,trIndex-1,sc.dataIndex);
					});
					dataField.dataLocation = trIndex;
					counts.push(dataField);
					lastChangeLevels[changelevel] = parseInt(trIndex);
					changelevel = parseInt(isGroup(record));
				}
				trIndex++;
			});
			groupingStore.remove(lastAbstractRecord);
			
			var totleRecord = {};
			totleRecord[groupCallback[0].dataIndex] = "<font color='red'>合计</font>";
			Ext.each(sumColumns,function(sc){
				totleRecord[sc.dataIndex] = sumField(0,groupingStore.getCount()-1,sc.dataIndex);
			});
			//加入最终合计项
			for(var reindex=0;reindex<counts.length;reindex++){
				var trecord = new Ext.data.Record(counts[reindex]);
				groupingStore.insert(counts[reindex].dataLocation + reindex,trecord);
			}
			groupingStore.add(new Ext.data.Record(totleRecord));
		}
	});
};