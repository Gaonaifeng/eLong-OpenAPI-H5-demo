package com.elong.nb.model.h5.result;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;


public class BaseResult {

 	@JSONField(name="Code")
	private String code;
 	
// 	@XmlElement(name = "Code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
		
}
