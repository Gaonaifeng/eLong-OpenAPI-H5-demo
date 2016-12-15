package com.elong.nb.model.h5.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.elong.nb.model.elong.HotelList;


@XmlRootElement(name = "ApiResult")
public class HotelListResult extends BaseResult{

	@JSONField(name="Result")
	private HotelList result;

//	@XmlElement(name = "Result")
	public HotelList getResult() {
		return result;
	}
	public void setResult(HotelList result) {
		this.result = result;
	}
	 
	public HotelListResult(){
		super();
	}
}
