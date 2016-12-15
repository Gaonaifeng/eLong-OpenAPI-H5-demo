var isNeedCvv = false;

$(function(){
	
	/**
	 * 返回按钮事件
	 */
	$("[flag$='back']").click(function(){
		history.back();
	});
	
	/**
	 * 卡号事件
	 */
	$("#inputCardNo").change(function(){
		if($(this).val().trim() == ""){
			$("#cvvLi").hide();
			isNeedCvv = false;
			return;
		}
		
		//验证卡号是否有效
		var url = "../pay/cardvalidate?cardNo=" + $(this).val().trim();
		var result = ajaxCommon(url, "GET", null);
		if(result.state == false){
			toastError(result.msg);
		}else if(result.data){
			$("#cvvLi").show();
			isNeedCvv = true;
		}
	});
	
	/**
	 * 按钮提交事件
	 */
	$("#submit").click(function(){
		//参数验证
		if(validate() == false){
			return;
		}
		
		//请求
		var url = "../pay/submitpay";
		var reqData = {
			"orderId" : $("#hdOrderId").val(),
			"amount" : $("#hdAmount").val(),
			"cardNo" : $("#inputCardNo").val(),
			"expiration" : $("#inputExpiration").val(),
			"holderName" : $("#inputName").val(),
			"idType" : $("#selectIdType option:selected").val(),
			"idNo" : $("#inputId").val(),
			"cvv" : isNeedCvv ? $("#inputCvv").val() : ""
		};
		
		var toast = new auiToast({});
		toast.loading({
	        title:"提交中",
	        duration:5000
	    },function(ret){
	    	setTimeout(function(){
		    	var result = ajaxCommon(url, "POST", reqData);
		    	toast.hide();
		    	if(result.state){
		    		toastSuccess("支付成功");
		    		window.location = "../order/result?orderId=" + $("#hdOrderId").val();
		    	}else{
		    		toastError(result.msg);
		    	}
	    	}, 3000);
	    });
		
	});
});

/**
 * 
 * 参数验证
 * 注：代理商应该对表单进行详细的参数验证，包括：表单非空性验证、长度、字符类型、特殊字符串验证等。
 * 
 */
function validate(){
	if($("#inputCardNo").val().trim() == ""){
		toastError("卡号不能为空");
		return false;
	}
	if($("#inputExpiration").val().trim() == ""){
		toastError("有效期不能为空");
		return false;
	}
	if($("#inputName").val().trim() == ""){
		toastError("持卡人姓名不能为空");
		return false;
	}
	if($("#inputId").val().trim() == ""){
		toastError("证件号不能为空");
		return false;
	}
	
	return true;
}