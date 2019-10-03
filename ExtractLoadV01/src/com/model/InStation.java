package com.model;

import java.util.Comparator;

public class InStation implements Comparable<InStation>,Comparator<InStation>{

	public String key;
	public int 	  value;
	
	public InStation() {
		
	}
	
	public InStation(String key, int value) {
		this.key = key;
		this.value = value;
	}

	//	按照名称进行比较;
	@Override
	public int compare(InStation o1, InStation o2) {
		InStation s1=(InStation)o1;
		InStation s2=(InStation)o2;
		
  		return s1.key.compareTo(s2.key);
	}

	//	按照值来进行比较;
	@Override
	public int compareTo(InStation o) {
		if (this.value != o.value)
   			return this.value - o.value;
  		else
  			return 0;
	}

}
