package com.test;

import com.model.Util_DBase;

public class Test {
	public static void main(String[] args) {
		Util_DBase base=new Util_DBase();
		
		boolean flag=base.checkDate("2019-1", "yyyy");
		System.out.println(flag);
	}
}
