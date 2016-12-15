/**
 * 
 * @param url 访问地址
 * @param type 访问类型
 * @param parameter 参数值
 * @returns
 */
function ajaxCommon(url,type,parameter){
	var dataObj=null;
	 $.ajax({
	        dataType: "json",
	        type: type,
	        data: parameter,
	        cache: false,
	        async: false,  
	        url: url,
	        error: function(){
	        	toastError('请求失败!');
	        },
	        success: function(data){
	        	dataObj=data;
	        }
	    });
	 return dataObj;
}

/**
 * 
 * @param url 访问地址
 * @param type 访问类型
 * @param parameter 参数值
 * @returns
 */
function ajaxCommonForJson(url,type,parameter){
	var dataObj=null;
	 $.ajax({
	        dataType: "json",
	        type: type,
	        contentType:"application/json", 
	        data: JSON.stringify(parameter),
	        cache: false,
	        async: false,  
	        url: url,
	        error: function(){
	        	toastError('请求失败!');
	        },
	        success: function(data){
	        	dataObj=data;
	        }
	    });
	 return dataObj;
}

/**
 * 短提醒：错误提示
 * 注：引用AUI插件
 */
function toastError(msg){
	var toast = new auiToast({}).fail({
        title:msg,
        duration:2000
    });
}

/**
 * 
 * 短提示；成功信息
 * 注：引用AUI插件
 */
function toastSuccess(msg){
	var toast = new auiToast({}).success({
        title:msg,
        duration:2000
    });
}

/**
 * 跳转中
 * @param url
 */
function linking(url){
	var toast = new auiToast({});
	toast.loading({
        title:"跳转中",
        duration:5000
    },function(ret){
    	setTimeout(function(){
    		toast.hide();
    		window.location = url;
    	}, 3000);
    });
}