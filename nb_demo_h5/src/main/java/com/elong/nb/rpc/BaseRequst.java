package com.elong.nb.rpc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.elong.nb.model.elong.EnumLocal;
import com.elong.nb.model.elong.HotelListCondition;

@XmlRootElement(name = "Condition")
@XmlSeeAlso({HotelListCondition.class})
public class BaseRequst<T> {

	@XmlElement(name = "Version")
	public double Version;
	
	@XmlElement(name = "Local")
    public EnumLocal Local;

	@XmlElement(name = "Request")
    public T Request;
	
}
