package run;

import model.DBaseUtil;
import model.SetParameterUtil;
import model.ViewComponent;


public class ProStartApplication{
	
	//	执行的主要程序;
	public static void main(String[] args) {
		
		//	进行参数配置的功能调用;
		SetParameterUtil parameterUtil	=	new SetParameterUtil();
		//	进行相应的参数设置;
		parameterUtil.setParameters();
		//	获得参数的路径信息;
		String configpath				=	parameterUtil.getConfig_path();
		
		//	进行数据库的
		DBaseUtil		 baseUtil		=	new DBaseUtil();
		//	初始化参数配置;
		baseUtil.setParameters(configpath);
		
		//	配置可视化控件;
		ViewComponent 	 component		=	new ViewComponent(baseUtil);
		//	初始化界面;
		component.initView();
		//	添加事件监听;
		component.setListener();
	}
}
