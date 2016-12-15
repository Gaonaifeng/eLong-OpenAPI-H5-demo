package com.elong.nb.model.h5.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.elong.nb.model.elong.ValidateResult;

public class HotelDataValidateResult  extends BaseResult {

	
	 @JSONField(name="Result")
	private ValidateResult result;
	
	public ValidateResult getResult() {
		return result;
	}
	public void setResult(ValidateResult result) {
		this.result = result;
	}
}