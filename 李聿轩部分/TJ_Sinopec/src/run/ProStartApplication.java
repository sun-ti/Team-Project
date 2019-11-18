package run;

import java.util.ArrayList;

import compute.TJ_InStation_CarFlow_HumanFlow_LaoXiao_Others;
import model.DBaseUtil;
import model.SetParameterUtil;


public class ProStartApplication {
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
		boolean 		 flag			=	baseUtil.setParameters(configpath);
		
		baseUtil.setTag("统计功能");
		baseUtil.tagStartModule();
		//	入站率车流与人;
		TJ_InStation_CarFlow_HumanFlow_LaoXiao_Others   oper1	=	new TJ_InStation_CarFlow_HumanFlow_LaoXiao_Others(baseUtil);

		//	当数据库配置成功的情况下;
		if(flag) {
			//	进行数据库的开启操作;
			baseUtil.open();
			//	自己去寻找表进行统计;
			String 		 		 sql		=	"select tablename from tongji_table_list where type='jiankong'";
			//	查询的数据集合;
			ArrayList<String[]>  stations	=	baseUtil.select(sql);
			
			if(stations.size()>0) {
				//	获取时间戳;
				String[] 		 times		=	baseUtil.getTimeSpans();
				
				//	根据站点对列表进行遍历;
				for(String[] tablenames:stations) {
					String 		 tablename	=	tablenames[0];
				
					//	进行车辆进站率和加油量的统计;
					oper1.compute(tablename, times);	
				}				
			}
			// 数据库的关闭;
			baseUtil.close();
			baseUtil.tagEndModule();
			baseUtil.tagComputeProcessingTime();
			System.out.println("3秒后自动关闭!");
			
			try {
				Thread.sleep(3000);
				System.exit(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("数据库连接失败!请重新操作");
		}
	}
}
