/**
*@description 存贷比信息
*/

imports( [
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);

var _custId;

var needCondition=false;
var createView=false;
var editView=false;	

var url=basepath+'/acrmFCiDepandloanNew.json?CustId='+_custId;

var fields=[
            {name:'SYCKNJS',text:'上月存款年积数',resutlFloat:'left',viewFn: money('0,000.00'),resutlWidth:120,hidden:true}, 
            {name:'BNCKNJS',text:'本年存款年积数',resutlFloat:'right',viewFn: money('0,000.00'),resutlWidth:120,hidden:true},
            {name:'BNDKNJS',text:'本年贷款年积数',viewFn: money('0,000.00'),resutlWidth:120,hidden:true},
            {name:'SYNJCDB',text:'上月年均存贷比',viewFn: money('0,000.00'),viewFn:function(data){
					  return  data+"%";
				  },resutlWidth:120},
            {name:'BNNJCDB',text:'本年年均存贷比',xtype:'textfield',viewFn: money('0,000.00'),viewFn:function(data){
					  return  data+"%";
				  },resutlWidth:120},
            {name:'SYMCDB',text:'上月末存贷比',xtype:'textfield',viewFn: money('0,000.00'),viewFn:function(data){
					  return  data+"%";
				  },resutlWidth:120},
            {name:'BNMCDB',text:'本年末存贷比',xtype:'textfield',viewFn: money('0,000.00'),viewFn:function(data){
					  return  data+"%";
				  },resutlFloat:'right',resutlWidth:80}
            
            ];