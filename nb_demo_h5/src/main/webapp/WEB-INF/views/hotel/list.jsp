<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<title>酒店列表</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
	<link href="../plugins/aui/css/aui-slide.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../plugins/aui/script/aui-slide.js"></script>
	
	<link href="../css/hotel/list.css" rel="stylesheet" type="text/css" />  
	<script type="text/javascript" src="../js/hotel/list.js"></script>
</head>
<body>
	<div class="aui-content aui-font-size-14">
	    <header class="aui-bar aui-bar-nav">
	    	<div class="aui-title">北京市</div>
	    	<a class="aui-pull-right aui-btn aui-btn-outlined" href="../order/list">
	        	<span class="aui-iconfont aui-icon-my"></span>
	    	</a>
		</header>
		
		<!-- 搜索模块 -->
		<section class="aui-content-padded">
			<ul class="aui-list aui-form-list">
		      <li class="aui-list-item">
		         <div class="aui-list-item-inner">
		             <div class="aui-list-item-label aui-font-size-12">
		                 <i class="aui-iconfont aui-icon-map aui-margin-r-5"></i>入：
		             </div>
		             <div class="aui-list-item-input">
		                 <input id="indate" type="date" value="${indate}" class="aui-font-size-12">
		             </div>
		         </div>
		       </li>
		      <li class="aui-list-item">
		         <div class="aui-list-item-inner">
		             <div class="aui-list-item-label aui-font-size-12">
		                 <i class="aui-iconfont aui-icon-map aui-margin-r-5"></i> 离：
		             </div>
		             <div class="aui-list-item-input">
		                 <input id="offdate" type="date" value="${offdate}" class="aui-font-size-12">
		             </div>
		         </div>
		       </li>		
		      <li class="aui-list-item">
		         <div class="aui-list-item-inner aui-font-size-12">
		         	<i class="aui-iconfont aui-icon-search"></i>
		             <input id="searchtext" type="text" placeholder="酒店名称/位置不限" class="aui-font-size-12">
		         </div>
		       </li>	
		      <li class="aui-list-item">
		         <div class="aui-list-item-inner aui-list-item-left aui-list-item-btn">
		             <div id="btnsearch" class="aui-btn aui-btn-info aui-btn-block aui-btn-sm" >搜索</div>
		         </div>
		       </li>			       		              
			</ul>
		</section>
		
		<!-- 列表模块 -->
		<section class="aui-content-padded">
		 	<ul id="divhotelsid" class="aui-list aui-media-list">
    		</ul>
		</section>
	</div>
</body>
</html>