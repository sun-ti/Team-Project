package com.model;

import java.io.File;

import com.models.Utils_Image;

public class Util_Image extends Util implements Utils_Image{
	private String path;

	//	进行图像处理的接口内容;
	public Util_Image() {
		this.path=Util.PATH;
	}
	
	//	获得图像路径的内容接口;
	@Override
	public String getImagePath(String str) {
		//	进行链接的返回;
		return this.path+File.separator+str;
	}

}
