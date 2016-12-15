var dialog = new auiDialog({});
var toast = new auiToast({});
function cancelOrder(orderId){
	dialog.alert({
        title:"提示",
        msg:'是否取消订单',
        buttons:['取消','确定']
    },function(ret){
        if(ret.buttonIndex == 2){
        	//当点击了确定 生成取消订单的url
        	var oldUrl = window.location.href;
        	var url = "./cancel?";
        	url = url + "orderId=" + orderId;
        	
        	toast.loading({
    				title:"处理中",
                	duration:2000
            	},function(ret){
            		console.log(ret);
            		setTimeout(function(){
                	var result = ajaxCommon(url,"GET","");
                    toast.hide();
                    if(result.state){
                    	toastSuccess(result.msg);
                    }else{
    	                toastError(result.msg);
                    }
                    window.location = oldUrl;
            	}, 3000)
            });
        }
    })
    
}

function goPay(orderId){
	//window.open("../hotel/list");
	var url = "../pay/index?orderId=" + orderId;
	window.location.href=url;
}