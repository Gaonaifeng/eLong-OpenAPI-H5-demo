var slide3 = new auiSlide({
    container:document.getElementById("aui-slide3"),
    // "width":300,
    "height":240,
    "speed":500,
    "autoPlay": 3000, //自动播放
    "loop":true,
    "pageShow":true,
    "pageStyle":'dot',
    'dotPosition':'center'
})

$(function(){
	$(".plusBtn").click(function(){
		//显示列表项
		var data = $(this).attr("plus-data");
		$("#child-div-" + data).show();
		//隐藏+，显示-
		
		$("a[minus-data='"+ data +"']").show();
		$(this).hide();
	});
	
	$(".minusBtn").click(function(){
		//显示列表项
		var data = $(this).attr("minus-data");
		$("#child-div-" + data).hide();
		//隐藏+，显示-
		$("a[plus-data='"+ data +"']").show();
		$(this).hide();
	});
	
	$("[flag$='book']").click(function(){
		var params = $(this).attr("data");
		var url = "../order/add?" + params;
		linking(url);
	});
});