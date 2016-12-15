$(function(){
	/**
	 * 返回按钮事件
	 */
	$("[flag$='back']").click(function(){
		var detailUrl = "../hotel/detail?hotelId="+ $("#hdHotelId").val() + "&arrivalDate=" + $("#hdArrivalDate").val() + "&departureDate=" + $("#hdDepartureDate").val();
		linking(detailUrl);
	});
	
	/**
	 * 提交按钮事件
	 */
	$("[flag$='submit']").click(function(){
		submitOrder();
	});
	
	/**
	 * 房间数变化事件
	 */
	$("#inputRoomAdd").change(function(){
		var val = $(this).val();
		//阻止产生负数
		if(val < 1){
			$(this).val(1);
			val = $(this).val();
		}else if(val > 5){
			$(this).val(5);
			val = $(this).val();
			toastError("房间数不能大于5");
		}
		
		//总价变动
		$("#totalMoney").text(val * $("#price").val());
		
		//宾客信息变动
		var ulNum = $("#guesterContainer ul").length;
		if(ulNum < val){
			for(var i=0; i<val-ulNum; i++){
				$("#guesterContainer").append('<ul class="aui-list aui-form-list aui-margin-t-15">' +
						'<li class="aui-list-item">'+
			            '<div class="aui-list-item-inner">'+
			                '<div class="aui-list-item-label">'+
			                   '<span class="mustStar">*</span>姓名'+
			                '</div>'+
			                '<div class="aui-list-item-input">'+
			                    '<input id="guesterName'+ (i + ulNum + 1) +'" type="text" placeholder="入住人姓名（房间'+ (i + ulNum + 1) +'）">'+
			                '</div>'+
			            '</div>'+
			        '</li>'+
			    '</ul>');
			}
		}else if(ulNum > val){
			for(var i=0; i< ulNum-val; i++){
				$("#guesterContainer ul:last").remove();
			}
		}
	});
});

/**
 * 提交订单
 */
function submitOrder(){
	//参数验证
	if(paramsValidate() == false){
		return;
	}
	
	//准备提交数据
	var guesterList = [];
	for(var i = 1; i < parseInt($("#inputRoomAdd").val()) + 1; i++){
		guesterList.push({
			"name" : $("#guesterName" + i).val()
		});
	}
	var url = "../order/submitorder";
	var reqDate = {
			"hotelId" : $("#hdHotelId").val(),
			"ratePlanId" : $("#hdRatePlanId").val(),
			"roomTypeId" : $("#hdRoomTypeId").val(),
			"arrivalDate" : $("#hdArrivalDate").val(),
			"departureDate" : $("#hdDepartureDate").val(),
			"numberOfRooms" : $("#inputRoomAdd").val(),
			"latestArrivalTimeString" : $("#selectLatestArrivalTime option:selected").val(),
			"customerIPAddress" : "127.0.0.1",
			"contact" : {
				"name" : $("#contactName").val(),
				"mobile" : $("#contactMobile").val()
			},
			"guesterList" : guesterList
	};
	
	var toast1 = new auiToast({});
	toast1.loading({
        title:"提交中",
        duration:5000
    },function(ret){
    	setTimeout(function(){
        	var result = ajaxCommonForJson(url, "POST", reqDate);
        	toast1.hide();
        	if(result.state){
        		toastSuccess("提交成功");
        		if(result.data.isNeedPay){
        			window.location = "../pay/index?orderId=" + result.data.orderId;
        		}else{
        			window.location = "../order/result?orderId=" + result.data.orderId;
        		}
        	}else{
        		toastError(result.msg);
        	}
    	}, 3000);
    });

}

/**
 * 参数验证
 * 注意：代理商在提交前需要校验数据的长度，特殊字符串过滤
 */
function paramsValidate(){
	//联系人信息校验
	if($("#contactName").val().trim() == ""){
		toastError("联系人姓名不能为空");
		return false;
	}
	if($("#contactMobile").val().trim() == ""){
		toastError("联系人手机号不能为空");
		return false;
	}
	
	//宾客信息校验
	for(var i = 1; i < (parseInt($("#inputRoomAdd").val()) + 1); i++){
		if($("#guesterName" + i).val().trim() == ""){
			toastError("宾客姓名不能为空");
			return false;
		}
	}
	
	return true;
}
