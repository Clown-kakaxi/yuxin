 function assistInput(input, url,oldAssistInput) { 
var event = window.event || arguments.callee.caller.arguments[0]; 
var keyAsc = event.keyCode; 
var newAssistInput = input.getValue();
if(newAssistInput==null||newAssistInput==""){
	custNameInputDiv.style.display = "none"; 
}
//if (keyAsc >= 48 && keyAsc <= 97 || keyAsc == 8 || keyAsc == 46) { 
 if(oldAssistInput!=newAssistInput&&newAssistInput!=null&&newAssistInput!=""){
 findUserByAjax(input, url); 
	}
//}
}; 
 function findUserByAjax(input, url) { 

var str = input.getValue(); 
filedName = input.name; 
var json = null; 

Ext.Ajax.request({
	url : url,
	method : 'GET',
	params : {
	'custName':str
},
success : function(response) {
	var text = response.responseText; 
	anaExeArray = Ext.util.JSON.decode(response.responseText); 
	json = eval("(" + text + ")"); 
	if (json != null&&anaExeArray.json.count>0) { 
	var myDiv = document.getElementById("custNameInputDiv"); 
	var ul = setUl(json, input); 
	myDiv.innerHTML = ul; 
	myDiv.style.display = "block"; 
	}else{
		custNameInputDiv.style.display = "none"; 
	}
	},
failure : function(form, action) {}
});
}; 
//设置UL 
function setUl(json, input) { 

if (json === undefined || json === "") { 
	
	return; 
}else{ 
	setDivPosition();// 设置div的位置 
	var ul = "<ul class='ul_background'>"; 
	for (var i=0;i<anaExeArray.json.data.length;i++) { 
		var name = anaExeArray.json.data[i].CUST_ZH_NAME; 
		var filedValue =i; 
	if (name === undefined) { 
	continue; 
	} else { 
	if (i == 0) { 
	ul += "<li class='input_Assist'"; 
	} else { 
	ul += "<li "; 
} 
ul += " id="+filedName+"_"+ filedValue+ " style=list-style-type:none; mce_style=list-style-type:none; onmouseover='changeCss("+filedValue+")' onclick = 'fillAssistInputText("+filedName+"_"+filedValue+")'>" + name + "</li>"; 
	} 
	} 
	ul += "</ul>"; 
	return ul; 
}
	}; 
function setDivPosition() { 
	var assistHiddenDiv = document.getElementById("custNameInputDiv"); 
	var offsetLeft = Ext.getCmp('CUST_ZH_NAME').getPosition()[0]; //左面的距离
	var offsetTop =Ext.getCmp('CUST_ZH_NAME').getPosition()[1]+20; //到顶端的距离
	var offsetWidth = Ext.getCmp('CUST_ZH_NAME').getWidth(); //宽度 
	assistHiddenDiv.style.width = offsetWidth; 
	assistHiddenDiv.style.left = offsetLeft; 
	assistHiddenDiv.style.top = offsetTop;
//	assistHiddenDiv.style.height=anaExeArray.json.data.length*20;
	};
 function fillAssistInputText(assistInputText) { 
	var assistInput = document.getElementById(assistInputText.id); 
	var assistOutput = assistInput.innerText;
	Ext.getCmp('CUST_ZH_NAME').setValue(assistOutput);
	custNameInputDiv.style.display = "none"; 
	}; 
 function changeCss(filedValue) { 
	var assistHiddenDiv = document.getElementById("custNameInputDiv").getElementsByTagName("li"); 
	 var mli = document.getElementById("custNameInputDiv").getElementsByTagName("ul");
	var length = assistHiddenDiv.length; 
	for(var i=0;i<length;i++){
		assistHiddenDiv[i].className="";
	} 
	assistHiddenDiv[filedValue].className="input_Assist";
	} 
	