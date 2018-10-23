/**
 * 构建首页工作提醒模块
 * @param styleNo 
 *   value:1->两行,一列基本信息,无日期
 *         2->十一行,两列,一列基本信息,一列日期
 *         3->四行,两列,一列基本信息,一列日期
 * @return
 */
var WorkNotice = function(styleNo){
//	//列模式
//	var workGridCm = new Ext.grid.ColumnModel({
//		header:'名称',
//		dataIndex:'name',
//		width:500
//	});
	//目前暂时用静态store
	var workGridStore = new Ext.data.ArrayStore({
		fields:[{name:'name'}
			   ,{name:'date'}]
	});
	
	//静态数据
	var staticData = [
	  ['离XXXX还有XX1天','2011-08-05'],
	  ['离XXXX还有XX2天','2011-08-05'],
	  ['离XXXX还有XX3天','2011-08-05'],
	  ['离XXXX还有XX4天','2011-08-05'],
	  ['离XXXX还有XX5天','2011-08-05'],
	  ['离XXXX还有XX6天','2011-08-05']
	];
	
	workGridStore.loadData(staticData);
	
	var workGrid = new Ext.grid.GridPanel({
		id:'mainPage_workGrid',
		store:workGridStore,
		columns:[
		  {header:'名称',dataIndex:'name',width:298}
		],
		titleCls:'tit_gztx',
		hideHeaders:true,
		bbar:['->',
			  {
			  text:"<font color='#c20000'>更多>></font>",
			  handler:function(){
			  	return false;
			  }
		  }]
	});
	//1->两行,一列基本信息,无日期
	//2->十一行,两列,一列基本信息,一列日期
	//3->四行,两列,一列基本信息,一列日期
	var workGridColumns = workGrid.getColumnModel();
	var workGridStore = workGrid.getStore();
	if(styleNo == 1){
		workGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:298}
		]);
		if(workGridStore.getCount() > 2){
			//移除store中多余的数据
			var flag_1 = 0;
			workGridStore.each(function(r){
				if(flag_1 >= 2){
					workGridStore.remove(r);
				}else{
					flag_1++;
				}
			});
		}
	}else if(styleNo == 2){
		workGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:223},
		    {header:'日期',dataIndex:'date',width:75}
		]);
		if(workGridStore.getCount() > 11){
			//移除store中多余的数据
			var flag_2 = 0;
			workGridStore.each(function(r){
				if(flag_2 >= 11){
					workGridStore.remove(r);
				}else{
					flag_2++;
				}
			});
		}
	}else if(styleNo == 3){
		workGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:223},
		    {header:'日期',dataIndex:'date',width:75}
		]);
		if(workGridStore.getCount() > 4){
			//移除store中多余的数据
			var flag_3 = 0;
			workGridStore.each(function(r){
				if(flag_3 >= 4){
					workGridStore.remove(r);
				}else{
					flag_3++;
				}
			});
		}
	}
	workGrid.reconfigure(workGridStore,workGridColumns);
	return workGrid;
};