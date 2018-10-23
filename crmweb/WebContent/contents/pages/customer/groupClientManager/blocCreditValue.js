 Ext.onReady(function(){
 	
 var data2 = [ 
 
			{"unditributeValue":"245,000,500.00","inteCredValRest":"7,000,000,000.00","inteCredVal":"14,000,000,000.00","inteCredValUsed":"7,000,000,000.00","company":"中国北京集团公司","lastApplyPerson":"中国北京集团公司","currenttAssureType":"信用","lastCreditValue":"16,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"中国北京集团公司","currentBusKinds":"信用证、押汇","creditValue":"16,000,000,000","usedValue":"16,000,000,000","restValue":"16,000,000,000","restValue2":"2,000,000,000","restValue3":"10,000,000,000","isOverFlow":"是","orgName":"华安支行","_id":1,"_parent":null,"_level":1,"_lft":1,"_rgt":14,"_is_leaf":false},

			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"中国北京集团公司","lastApplyPerson":"中国北京集团公司","currenttAssureType":"信用","lastCreditValue":"1,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"中国北京集团公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"2,000,000,000","restValue3":"10,000,000,000","isOverFlow":"是","orgName":"华安支行","_id":2,"_parent":1,"_level":2,"_lft":2,"_rgt":2,"_is_leaf":true},

			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京银行股份有限公司","lastApplyPerson":"北京银行股份有限公司","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京银行股份有限公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"1,000,000,000","restValue3":"1,000,000,000","isOverFlow":"是","orgName":"华安支行","_id":3,"_parent":1,"_level":2,"_lft":3,"_rgt":3,"_is_leaf":true},

			{"inteCredValRest":"4,000,000,000.00","inteCredVal":"8,000,000,000.00","inteCredValUsed":"4,000,000,000.00","company":"北京国安信息产业股份有限公司","lastApplyPerson":"北京国安信息产业股份有限公司","lastCreditValue":"8,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京国安信息产业股份有限公司","currentBusKinds":"信用证、押汇","creditValue":"8,000,000,000","usedValue":"8,000,000,000","restValue":"8,000,000,000","restValue2":"","restValue3":"8,000,000,000","isOverFlow":"否","orgName":"总行","_id":4,"_parent":1,"_level":2,"_lft":4,"_rgt":9,"_is_leaf":false},

			{"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京国安信息产业股份有限公司","lastApplyPerson":"北京国安信息产业股份有限公司","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京国安信息产业股份有限公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"总行","_id":5,"_parent":4,"_level":3,"_lft":5,"_rgt":5,"_is_leaf":true},
			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京国安信息科技股份有限公司","lastApplyPerson":"北京国安信息科技股份有限公司","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京国安信息科技股份有限公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":6,"_parent":4,"_level":3,"_lft":6,"_rgt":6,"_is_leaf":true},
			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京鸿联九五信息产业有限公司","lastApplyPerson":"北京鸿联九五信息产业有限公司","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京鸿联九五信息产业有限公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":7,"_parent":4,"_level":3,"_lft":7,"_rgt":7,"_is_leaf":true},
			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京国安通信有限公司","lastApplyPerson":"北京国安通信有限公司","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京国安通信有限公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":8,"_parent":4,"_level":3,"_lft":8,"_rgt":8,"_is_leaf":true},
			
			{"inteCredValRest":"3,000,000,000.00","inteCredVal":"6,000,000,000.00","inteCredValUsed":"3,000,000,000.00","company":"北京证劵","lastApplyPerson":"北京证劵","currenttAssureType":"信用","lastCreditValue":"6,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京证劵","currentBusKinds":"信用证、押汇","creditValue":"6,000,000,000","usedValue":"6,000,000,000","restValue":"6,000,000,000","restValue2":"","restValue3":"6,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":9,"_parent":1,"_level":2,"_lft":9,"_rgt":13,"_is_leaf":false},
			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京证劵","lastApplyPerson":"北京证劵","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京证劵","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":10,"_parent":9,"_level":3,"_lft":10,"_rgt":10,"_is_leaf":true},

			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"华夏基金管理有限公司","lastApplyPerson":"华夏基金管理有限公司","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"华夏基金管理有限公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":11,"_parent":9,"_level":3,"_lft":11,"_rgt":11,"_is_leaf":true},
			{"creditStartDate":'2010-08-03',"custOrgCode":'02030202',"inteCredValRest":"1,000,000,000.00","inteCredVal":"2,000,000,000.00","inteCredValUsed":"1,000,000,000.00","company":"北京金通证劵有限责任公司","lastApplyPerson":"北京金通证劵有限责任公司","currenttAssureType":"信用","lastCreditValue":"2,000,000,000","lastBusKinds":"信用证、押汇","lastAssureType":"信用","currentApplyPerson":"北京金通证劵有限责任公司","currentBusKinds":"信用证、押汇","creditValue":"2,000,000,000","usedValue":"2,000,000,000","restValue":"2,000,000,000","restValue2":"","restValue3":"2,000,000,000","isOverFlow":"否","orgName":"华安支行","_id":12,"_parent":9,"_level":3,"_lft":12,"_rgt":12,"_is_leaf":true}	   
		];
   
		for (var i = 0; i < data2.length; i++) {
    	data2[i].desc = 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.';
    	data2[i]._is_loaded = true; 
		}
    var record2 = Ext.data.Record.create([ 

  		{name: 'company'},
		{name:'creditStartDate'},
		{name:'custOrgCode'},
     	{name: 'lastApplyPerson'},
     	{name: 'lastCreditValue'},
     	{name: 'lastBusKinds'},
     	{name: 'lastAssureType'},   
     	
     	{name: 'inteCredVal'},
     	{name: 'inteCredValUsed'},
     	{name: 'inteCredValRest'}, 
     	
     	{name: 'currentApplyPerson'},
     	{name: 'currenttAssureType'},
     	{name: 'currentBusKinds'},
     	{name:'unditributeValue'},
     	{name: 'creditValue'},
     	{name: 'usedValue'},
     	{name: 'restValue'},
     	{name: 'restValue2'},
     	{name: 'restValue3'},
     	{name: 'isOverFlow'},
     	{name: 'orgName'},
     	{name: 'desc'},
     	{name: '_id', type: 'int'},
     	{name: '_level', type: 'int'},
     	{name: '_lft', type: 'int'},
     	{name: '_rgt', type: 'int'},
     	{name: '_is_leaf', type: 'bool'}

   	]);
    var store2 = new Ext.ux.maximgb.tg.NestedSetStore({
    	//	autoLoad : true,
			reader: new Ext.data.JsonReader({id: '_id'}, record2),
			proxy: new Ext.data.MemoryProxy(data2)
    });
    

    var blocCreValAppAudPanel2 = new Ext.ux.maximgb.tg.EditorGridPanel({
      store: store2,
	  region:'center',
	  id:'blocCreValAppAudPanel2',
	  selModel:new Ext.grid.RowSelectionModel({
	  	singleSelection:true,
	  	listeners:{
	  		'rowselect':function(model,rowIndex,record)
	  		{
//	  			var divObj=(Ext.getCmp("blocCreValAppAudPanel2").getView().getRow(rowIndex));
//	  			
//	  			divObj.style.backgroundColor='red';
	  		}
	  	}
	  }),
	  split:true,
	 
      master_column_id : 'company',
      columns: 
      [
      		new Ext.grid.RowNumberer({
      			header:'序号',
      			width:40
      		}),    

      		{
            id:'company',
            header: "客户名称", 
            //columnWidth:.33,
            width: 250, 
            
            dataIndex: 'company'
            
        },
		{
			id:'custOrgCode',
			header:'组织机构代码',
			width:150,
			dataIndex:'custOrgCode'
		},
      	{
            id:'lastCreditValue',
            header: "现有授信额度", 
            width: 160, 
            
            dataIndex: 'lastCreditValue'
            
        },
		{
			id:'creditStartDate',
			header:'授信起始日期',
			width:100,
			dataIndex:'creditStartDate'
		},
      	{
            id:'currentBusKinds',
            header: "本期业务品种及分配情况", 
            width: 160, 
            sortable: true, 
            dataIndex: 'currentBusKinds'
            
        }, 
 		
 		{
            header: "现有业务余额", 
            width: 150, 
            
            dataIndex: 'restValue3'

        }, 			
        {
            header: "本期授信申请额度", 
            width: 150, 
           
            dataIndex: 'creditValue',
            editor: new Ext.form.NumberField({
                allowDecimals: true, 
                allowNegative: false
            })
        }   ,
        {
            header: "本期授信审批调整后额度", 
            width: 150, 
     
    //        renderer: change, 
            dataIndex: 'usedValue'
         /*   
					  editor: new Ext.form.NumberField({
                allowDecimals: true, 
                allowNegative: true
            })
            */
        }, {
            header: "本期授信最终批复额度", 
            width: 150, 
         
     //       renderer: pctChange, 
           
            dataIndex: 'restValue'
		 /*			  editor: new Ext.form.NumberField({
                allowDecimals: true, 
                allowNegative: true
                
            }) 
            */
        },
 		{
            header: "未分配额度", 
            width: 150, 
            align:'right',
            dataIndex: 'unditributeValue'
     	 },        
 		{
            header: "本期已经使用额度", 
            width: 150, 
         
     //       renderer: pctChange, 
           
            dataIndex: 'restValue2'
		 /*			  editor: new Ext.form.NumberField({
                allowDecimals: true, 
                allowNegative: true
                
            }) 
            */
        }, 
      
        {dataIndex: 'inteCredVal',width:150,header:'国际贸易授信总额度'},
     	{dataIndex: 'inteCredValUsed',width:150,header:'国际贸易授信已使用额度'},
     	{dataIndex: 'inteCredValRest',width:150,header:'国际贸易授信可用额度'},
        {
            header: "经办机构", 
            width: 100, 
        
            dataIndex: 'orgName'
        },
        
        
        
      	{
            id:'currentApplyPerson',
            header: "本期申请人", 
            width: 160, 
            sortable: true, 
            dataIndex: 'currentApplyPerson'
            
        },        
      	{
            id:'currenttAssureType',
            header: "本期担保方式", 
            width: 160, 
            sortable: true, 
            dataIndex: 'currenttAssureType'
            
        },
      	{
            id:'lastApplyPerson',
            header: "上期授信申请人", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lastApplyPerson'
            
        },
       
      	{
            id:'lastBusKinds',
            header: "上期业务品种额度分配情况", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lastBusKinds'
            
        },   
      	{
            id:'lastAssureType',
            header: "上期担保方式", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lastAssureType'
            
        }
//        {
//            header: "经办机构", 
//            width: 100, 
//            sortable: true, 
//    //        renderer: Ext.util.Format.dateRenderer('m/d/Y'), 
//            dataIndex: 'orgName'
//     //       editor: new Ext.form.DateField()
//        }
      ],
      stripeRows: true, 
    
      title: '集团客户授信情况',
      viewConfig : {
      	enableRowBody : true,
      	getRowClass:function(record,rowIndex,rp,ds)
      	{
      		if(!record.get("_is_leaf"))
      		{
      			return 'blocLoanAndDepositNodeClass';
      		}
      	}
      }
    
    });
      store2.load();
      store2.expandAll();
      
    var view = new Ext.Viewport({
    	layout : 'fit',
    	constrain:true,
//    	title:'集团授信情况',
    	items : [
    			blocCreValAppAudPanel2
//    			bloCreValAppAudForm
		]
    });  
    
});