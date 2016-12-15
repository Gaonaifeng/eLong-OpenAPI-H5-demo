package com.elong.nb.model.h5.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.elong.nb.model.elong.OrderDetailResult;


public class HotelOrderDetailResult extends BaseResult {
	@JSONField(name="Result")
	OrderDetailResult result;
	
	public OrderDetailResult getResult() {
		return result;
	}
	public void setResult(OrderDetailResult result) {
		this.result = result;
	}
}
