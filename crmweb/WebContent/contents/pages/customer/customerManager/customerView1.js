Ext.onReady(function(){
	var basepath=parent.basepath;
	var parentcustid = _custId;//parent.oCustInfo.cust_id;
	var custType=1;//以后客户类型得自己去查//parent.oCustInfo.cust_type;
	
	var ajaxdiv3 = Ext.get('div3');
	ajaxdiv3.on('mouseenter',function(e,t){
	    if(custType=='2'){
	        Ext.Ajax.request({
	            url : basepath+'/custQueryForPub.json?custId='+parentcustid,
	            method: 'GET',
	            success : function(response) {
	                var json=Ext.util.JSON.decode(response.responseText);
//	    			Ext.Msg.alert('tip',response.responseText);
	                if(json.json.data.length!=0){
	                    if(json.json.data[0].CUST_ID!=""&&json.json.data[0].CUST_ID!=undefined){
	                        Ext.get('FR_NAME').dom.innerHTML=json.json.data[0].CUST_ID;}
	                    if(json.json.data[0].CUST_ZH_NAME!=""&&json.json.data[0].CUST_ZH_NAME!=undefined){
	                        Ext.get('FZ_NAME').dom.innerHTML=json.json.data[0].CUST_ZH_NAME;}
	                    if(json.json.data[0].CERT_TYPE_ORA!=""&&json.json.data[0].CERT_TYPE_ORA!=undefined){
	                        Ext.get('CRET_NAME').dom.innerHTML=json.json.data[0].CERT_TYPE_ORA;}
	                    if(json.json.data[0].CERT_NUM!=""&&json.json.data[0].CERT_NUM!=undefined){
	                        Ext.get('CRET_NO').dom.innerHTML=json.json.data[0].CERT_NUM;}
	                }
	            },
	            failure : function(response) {
	            }
	        }); 
	    }else if(custType=='1'){
	        Ext.Ajax.request({
	            url : basepath+'/perCustQuery.json?custId='+parentcustid,
    				method: 'GET',
    				params:{'condition' : Ext.encode( {custId:parentcustid})},
    				success : function(response) {
        				var json=Ext.util.JSON.decode(response.responseText);
    //	    				Ext.Msg.alert('tip',response.responseText);
        				if(json.json.data.length!=0){
        					if(json.json.data[0].CUST_ID!=""&&json.json.data[0].CUST_ID!=undefined){
        						Ext.get('FR_NAME').dom.innerHTML=json.json.data[0].CUST_ID;}
        					if(json.json.data[0].CUST_ZH_NAME!=""&&json.json.data[0].CUST_ZH_NAME!=undefined){
        						Ext.get('FZ_NAME').dom.innerHTML=json.json.data[0].CUST_ZH_NAME;}
        					if(json.json.data[0].CERT_TYPE_ORA!=""&&json.json.data[0].CERT_TYPE_ORA!=undefined){
        						Ext.get('CRET_NAME').dom.innerHTML=json.json.data[0].CERT_TYPE_ORA;}
        					if(json.json.data[0].CERT_NUM!=""&&json.json.data[0].CERT_NUM!=undefined){
        						Ext.get('CRET_NO').dom.innerHTML=json.json.data[0].CERT_NUM;}
        				}
    				},
	    			failure : function(response) {
	    			}
	        });
	    }
	},this,{single:true});
	
	var ajaxdiv2 = Ext.get('div2');
	ajaxdiv2.on('mouseenter',function(e,t){
	    Ext.Ajax.request({
			url : basepath+'/querycustomerrelation.json?customerId='+parentcustid,
			method: 'GET',
			success : function(response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var n=9;
				if(json.json.data.length<9)
					n=json.json.data.length;
				for(var i=0;i<n;i++){
					var NAMEKYE="NAME"+i;
					var POSITIONKEY="POSITION"+i;
					var TELEPHONEKEY="TELEPHONE"+i;
					Ext.get(NAMEKYE).dom.innerHTML=json.json.data[i].CUST;
					Ext.get(POSITIONKEY).dom.innerHTML=json.json.data[i].RELATION_TYPE_ORA;
					Ext.get(TELEPHONEKEY).dom.innerHTML=json.json.data[i].RELATION_NAME_ORA;
				}
			},
			failure : function(response) {
			}
	    }); 
	},this,{single:true});
	
	var ajaxdiv1 = Ext.get('div1');
	ajaxdiv1.on('mouseenter',function(e,t){
	    Ext.Ajax.request({
			url : basepath+'/queryaffiliationinformation3.json?customerId='+parentcustid,
			method: 'GET',
			success : function(response) {
				debugger;
				var json=Ext.util.JSON.decode(response.responseText);
				var n=7;
				if(json.json.data.length<7)
					n=json.json.data.length;
				for(var i=0;i<n;i++){
					var KNAMEKEY="KNAME"+i;
					var KTELEPHONEKEY="KTELEPHONE"+i;
					var ORG="ORG"+i;
					Ext.get(KNAMEKEY).dom.innerHTML=json.json.data[i].USER_NAME;
					Ext.get(KTELEPHONEKEY).dom.innerHTML=json.json.data[i].CONTACT;
					Ext.get(ORG).dom.innerHTML=json.json.data[i].UNITNAME;
				}
			},
			failure : function(response) {
			}
		}); 
	},this,{single:true});
	
	var ajaxdiv4 = Ext.get('div4');
	ajaxdiv4.on('mouseenter',function(e,t){
		Ext.Ajax.request({
			url : basepath+'/queryaffiliationinformation2.json?customerId='+parentcustid,
			method: 'GET',
			success : function(response) {
			debugger;
				var json=Ext.util.JSON.decode(response.responseText);
				var n=9;
				if(json.json.data.length<9)
					n=json.json.data.length;
				for(var i=0;i<n;i++){
					var UNITNOKYE="UNITNO"+i;
					var UNITNAMEKEY="UNITNAME"+i;
					Ext.get(UNITNOKYE).dom.innerHTML=json.json.data[i].INSTN_NO;
					Ext.get(UNITNAMEKEY).dom.innerHTML=json.json.data[i].UNITNAME;
				}
			},
			failure : function(response) {
			}
		}); 
	},this,{single:true}); 
	
	var ajaxdiv5 = Ext.get('div5');
	ajaxdiv5.on('mouseenter',function(e,t){
   		Ext.Ajax.request({
    			url : basepath+'/contributionInformation-info.json?cust_id='+parentcustid,
    			method: 'GET',
    			success : function(response) {
    				var json=Ext.util.JSON.decode(response.responseText);
//    				Ext.Msg.alert('tip',response.responseText);
    				if(json.json.data.length!=0){
    					if(json.json.data[0].CONTRI_DEPOSIT!=""&&json.json.data[0].CONTRI_DEPOSIT!=undefined){
    						Ext.get('CONTRI_DEPOSIT').dom.innerHTML=json.json.data[0].CONTRI_DEPOSIT;}
    					if(json.json.data[0].CONTRIBUTION_LOAN!=""&&json.json.data[0].CONTRIBUTION_LOAN!=undefined){
    						Ext.get('CONTRIBUTION_LOAN').dom.innerHTML=json.json.data[0].CONTRIBUTION_LOAN;}
    					if(json.json.data[0].CONTRIBUTION_MID!=""&&json.json.data[0].CONTRIBUTION_MID!=undefined){
    						Ext.get('CONTRIBUTION_MID').dom.innerHTML=json.json.data[0].CONTRIBUTION_MID;}
    					if(json.json.data[0].CONTRIBUTION_CUST!=""&&json.json.data[0].CONTRIBUTION_CUST!=undefined){
    						Ext.get('CONTRIBUTION_CUST').dom.innerHTML=json.json.data[0].CONTRIBUTION_CUST;}
    				}
    			},
    			failure : function(response) {
    			}
   		}); 
	},this,{single:true});
    	
	var ajaxdiv6 = Ext.get('div6');
	ajaxdiv6.on('mouseenter',function(e,t){
	Ext.Ajax.request({
	    url : basepath+'/querycustomerproductname.json?customerId='+parentcustid,
			method: 'GET',
			success : function(response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var n=9;
				if(json.json.data.length<9)
					n=json.json.data.length;
				for(var i=0;i<n;i++){
					var NAMEKYE="1NAME"+i;
					var POSITIONKEY="1POSITION"+i;
					Ext.get(NAMEKYE).dom.innerHTML=json.json.data[i].PRODUCT_ID;
					Ext.get(POSITIONKEY).dom.innerHTML=json.json.data[i].PROD_NAME;
				}
			},
			failure : function(response) {
			}
		}); 
	},this,{single:true});
});