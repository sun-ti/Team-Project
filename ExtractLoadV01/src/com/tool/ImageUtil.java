package com.tool;

import java.io.File;

public class ImageUtil extends Util implements ImageUtils{
	private String path;

	//	进行图像处理的接口内容;
	public ImageUtil() {
		this.path=Util.PATH;
	}
	
	//	获得图像路径的内容接口;
	@Override
	public String getImagePath(String str) {
		//	进行链接的返回;
		return this.path+File.separator+str;
	}

}
