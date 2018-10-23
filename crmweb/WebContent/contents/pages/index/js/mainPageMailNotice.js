/**
 * 构建首页邮件提醒模块
 * @param styleNo 
 *   value:1->两行,一列基本信息,无日期
 *         2->十一行,两列,一列基本信息,一列日期
 *         3->四行,两列,一列基本信息,一列日期
 * @return
 */
var MailNotice = function(styleNo){
//	//列模式
//	var mailGridCm = new Ext.grid.ColumnModel({
//		header:'名称',
//		dataIndex:'name',
//		width:500
//	});
	//目前暂时用静态store
	var mailGridStore = new Ext.data.ArrayStore({
		fields:[{name:'name'}
			   ,{name:'date'}]
	});
	
	//静态数据
	var staticData = [
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05'],
	  ['离XXXX还有XXX天','2011-08-05']
	];
	
	mailGridStore.loadData(staticData);
	
	var mailGrid = new Ext.grid.GridPanel({
		id:'mainPage_mailGrid',
		store:mailGridStore,
		columns:[
		  {header:'名称',dataIndex:'name',width:223},
		  {header:'日期',dataIndex:'date',width:75}
		],
		titleCls:'tit_yjtx',
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
	var mailGridColumns = mailGrid.getColumnModel();
	var mailGridStore = mailGrid.getStore();
	if(styleNo == 1){
		mailGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:298}
		]);
		if(mailGridStore.getCount() > 2){
			//移除store中多余的数据
			var flag_1 = 0;
			mailGridStore.each(function(r){
				if(flag_1 >= 2){
					mailGridStore.remove(r);
				}else{
					flag_1++;
				}
			});
		}
	}else if(styleNo == 2){
		mailGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:223},
		    {header:'日期',dataIndex:'date',width:75}
		]);
		if(mailGridStore.getCount() > 11){
			//移除store中多余的数据
			var flag_2 = 0;
			mailGridStore.each(function(r){
				if(flag_2 >= 11){
					mailGridStore.remove(r);
				}else{
					flag_2++;
				}
			});
		}
	}else if(styleNo == 3){
		mailGridColumns.setConfig([
		    {header:'名称',dataIndex:'name',width:223},
		    {header:'日期',dataIndex:'date',width:75}
		]);
		if(mailGridStore.getCount() > 4){
			//移除store中多余的数据
			var flag_3 = 0;
			mailGridStore.each(function(r){
				if(flag_3 >= 4){
					mailGridStore.remove(r);
				}else{
					flag_3++;
				}
			});
		}
	}
	mailGrid.reconfigure(mailGridStore,mailGridColumns);
	return mailGrid;
}