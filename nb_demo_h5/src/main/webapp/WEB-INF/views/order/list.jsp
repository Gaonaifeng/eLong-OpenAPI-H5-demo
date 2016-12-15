<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单列表</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
	<link href="../plugins/aui/css/aui-slide.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../plugins/aui/script/aui-slide.js"></script>
	<link href="../css/order/list.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/order/list.js"></script>
</head>
<body>
	<!-- 标题栏 -->
	<header class="aui-bar aui-bar-nav">
    	<a class="aui-pull-left aui-btn" href="../hotel/list">
        	<span class="aui-iconfont aui-icon-left"></span>
    	</a>
    	<div class="aui-title">酒店订单</div>
	</header>
	<div class="aui-content aui-margin-b-15">
    	<ul class="aui-list aui-list-in" id="ulOrderList"></div></div>
    		<!-- <div class="aui-card-list-content-padded aui-border-b aui-border-t">
        		<li id="12345" class="aui-list-item" onclick="alert(&quot;you see see &quot;);">
            		<div class="aui-list-item-inner">
                		<section class="aui-content-padded">
    						<div class="aui-card-list">
        						<div class="aui-card-list-header">
            						<p class="aui-font-size-14 aui-text-success">等待确认</p>
        						</div>
        						<div class="aui-card-list-content-padded aui-border-b aui-border-t">
            							<p class="aui-font-size-14">
            								艺龙酒店
            							</p>
            							<p class="aui-font-size-12">
            								豪华双床房	
            								<span class="aui-font-size-14 aui-text-danger total-price">¥208</span>
         	 	  						</p>
            							<p class="aui-font-size-12">
            								11月16日-11月17日
            							</p>
        						</div>
    						</div>	
						</section>
            		</div>
        		</li>
        	</div> -->
    	</ul>
	</div>
</body>
</html>