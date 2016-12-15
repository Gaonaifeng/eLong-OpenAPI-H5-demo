<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="../css/common/error.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/common/error.js"></script>
<!-- 错误信息 -->
<div class="txt-content">
	<div class="aui-content">
	   	<div class="aui-content aui-ellipsis-1 error-text aui-font-size-20">
			${errorMsg}
		</div>
	</div>
</div>

<!-- 选项按钮 -->
<div class=" btn-content">
	<div class="aui-content">
		<div class="aui-col-xs-6"><div class="aui-btn aui-btn-info" onclick="goBack()">返回上页</div></div>
	</div>
	<div class="aui-content">
		<div class="aui-col-xs-6"><div class="aui-btn aui-btn-info" onclick="refurbish()">刷新此页</div></div>
	</div>
</div>