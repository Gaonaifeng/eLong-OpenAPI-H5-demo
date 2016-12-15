package com.elong.nb.model.h5.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.elong.nb.model.elong.CancelOrderResult;


public class HotelOrderCancelResult extends BaseResult {
	 	@JSONField(name="Result")
		private CancelOrderResult result;
		
		public CancelOrderResult getResult() {
			return result;
		}
		public void setResult(CancelOrderResult result) {
			this.result = result;
		}
}
