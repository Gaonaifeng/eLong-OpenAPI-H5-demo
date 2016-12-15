package com.elong.nb.model.h5.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.elong.nb.model.elong.CreateOrderResult;


public class HotelOrderCreateResult extends BaseResult {
	@JSONField(name="Result")
	private CreateOrderResult result;
	
	public CreateOrderResult getResult() {
		return result;
	}
	public void setResult(CreateOrderResult result) {
		this.result = result;
	}
}
