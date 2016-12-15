<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>支付订单</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
	<link href="../css/pay/index.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/pay/index.js"></script>
</head>
<body>
 <div class="aui-content aui-font-size-14">
	<!-- 标题栏 -->
	<header class="aui-bar aui-bar-nav">
		<a class="aui-pull-left aui-btn" href="javascript:void(0)" flag="back"> <span
			class="aui-iconfont aui-icon-left"></span>
		</a>
		<div class="aui-title">订单支付</div>
		<a class="aui-pull-right aui-btn aui-btn-outlined" href="../order/list">
	        <span class="aui-iconfont aui-icon-my"></span>
	    </a>
	</header>
	
	<c:choose>
		<c:when test="${errorMsg != null}">
			<jsp:include page="/WEB-INF/views/common/error.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<section class="aui-content">
			    <div class="aui-content-padded aui-font-size-16">
			    	订单编号：<span class="aui-text-success">${orderDetail.orderId}</span><br/>
					支付金额：<span class="aui-text-danger">${orderDetail.currencyType}${orderDetail.totalPriceExchanged}</span>
					<input type="hidden" id="hdAmount" value="${orderDetail.totalPriceExchanged}"/>
					<input type="hidden" id="hdOrderId" value="${orderDetail.orderId}"/>
				</div>	
			</section>
			
			<section class="aui-content">
			    <div class="aui-card-list">
			        <div class="aui-card-list-header">
			            ${orderDetail.hotelName}
			        </div>
			        <div class="aui-card-list-content-padded">
			            <div>${orderDetail.roomTypeName} ${orderDetail.numberOfRooms}间</div>
			            <div>${orderDetail.arrivalDate} 到 ${orderDetail.departureDate}</div>
			        </div>
			    </div>
			</section>	
			
			<!-- 信用卡表单 -->
		    <section class="aui-content">
			    <ul class="aui-list aui-form-list">
			      <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 卡号:
			             </div>
			             <div class="aui-list-item-input">
			                 <input id="inputCardNo" type="text" placeholder="请输入13~16位卡号">
			             </div>
			         </div>
			      </li>
			      <li id="cvvLi" class="aui-list-item" style="display:none">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 CVV:
			             </div>
			             <div class="aui-list-item-input">
			                 <input id="inputCvv" type="text" placeholder="信用卡背后三位数字">
			             </div>
			         </div>
			      </li>		      
			      <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 持卡人姓名:
			             </div>
			             <div class="aui-list-item-input">
			                 <input id="inputName" type="text" placeholder="例：张三">
			             </div>
			         </div>
			      </li>	 
			      <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 有效期:
			             </div>
			             <div class="aui-list-item-input">
			                 <input id="inputExpiration" type="text" placeholder="年月，例：201601">
			             </div>
			         </div>
			      </li>	
			      <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                预留证件:
			             </div>
			             <div class="aui-list-item-input">
			                 <select id="selectIdType">
			                 	<option value="IdentityCard" selected>身份证</option>
			                 	<option value="Passport">护照</option>
			                 	<option value="Other">其他</option>
			                 </select>
			             </div>
			         </div>
			      </li>	 	
			      <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 证件号:
			             </div>
			             <div class="aui-list-item-input">
			                 <input id="inputId" type="text" placeholder="身份证号或其他">
			             </div>
			         </div>
			      </li>		          	       	           
			    </ul>
			 </section>
			 
			<section class="aui-content">
			    <div class="aui-content-padded aui-text-warning">
					温馨提醒：请确保提供的信息与发卡行记录的信息一致
				</div>	
			</section>	 
			
			<section class="aui-content-padded">
			    <div id="submit" class="aui-btn aui-btn-info aui-btn-block">确认支付</div>
			</section>		
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>