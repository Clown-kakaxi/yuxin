/**
 * 构建首页资讯模块
 * @param styleNo 
 *   value:1->两行,一列基本信息,无日期
 *         2->十一行,两列,一列基本信息,一列日期
 *         3->四行,两列,一列基本信息,一列日期
 * @return
 */
var Infos = function(styleNo){
//	//列模式
//	var workGridCm = new Ext.grid.ColumnModel({
//		header:'名称',
//		dataIndex:'name',
//		width:500
//	});
	//目前暂时用静态store
	var infoGridStore = new Ext.data.ArrayStore({
		fields:[{name:'name'}
			   ,{name:'date'}]
	});
	
	//静态数据
	var staticData = [
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05']
	];
	
	infoGridStore.loadData(staticData);
	
	var infoGrid = new Ext.grid.GridPanel({
		id:'mainPage_workGrid',
		store:infoGridStore,
		columns:[
		  {header:'名称',dataIndex:'name',width:223}
		 ,{header:'日期',dataIndex:'date',width:75}
		],
		titleCls:'tit_zx',
		hideHeaders:true,
		bbar:[
		  '->',
		  {
			  text:"<font color='#c20000'>更多>></font>",
			  handler:function(){
			  	return false;
			  }
		  }
		]
	});
	//1->两行,一列基本信息,无日期
	//2->十一行,两列,一列基本信息,一列日期
	//3->四行,两列,一列基本信息,一列日期
	var infoGridColumns = infoGrid.getColumnModel();
	var infoGridStore = infoGrid.getStore();
	if(styleNo == 1){
		infoGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:298}
		]);
		if(infoGridStore.getCount() > 2){
			//移除store中多余的数据
			var flag_1 = 0;
			infoGridStore.each(function(r){
				if(flag_1 >= 2){
					infoGridStore.remove(r);
				}else{
					flag_1++;
				}
			});
		}
	}else if(styleNo == 2){
		infoGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:223},
		    {header:'日期',dataIndex:'date',width:75}
		]);
		if(infoGridStore.getCount() > 11){
			//移除store中多余的数据
			var flag_2 = 0;
			infoGridStore.each(function(r){
				if(flag_2 >= 11){
					infoGridStore.remove(r);
				}else{
					flag_2++;
				}
			});
		}
	}else if(styleNo == 3){
		infoGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:223},
		    {header:'日期',dataIndex:'date',width:75}
		]);
		if(infoGridStore.getCount() > 4){
			//移除store中多余的数据
			var flag_3 = 0;
			infoGridStore.each(function(r){
				if(flag_3 >= 4){
					infoGridStore.remove(r);
				}else{
					flag_3++;
				}
			});
		}
	}
	infoGrid.reconfigure(infoGridStore,infoGridColumns);
	return infoGrid;
}