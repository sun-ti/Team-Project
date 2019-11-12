package model;

import java.io.File;
import java.io.IOException;
import com.google.gson.JsonObject;

//	设置参数的工具;
public class SetParameterUtil extends Util{
	private final String TAG	 = "\\";
	private final String proj	 = "TJ_Sinopec";
	private final String CHARSET = "Utf-8";
	private String config_path	 = null;
	
	//	构造函数;
	public SetParameterUtil() {
		super();
	}

	public String getConfig_path() {
		return config_path;
	}

	//	进行参数配置的功能模块;
	public void setParameters() {
		//	获得本工程的父亲节点;
		String[] basedParameters	=	getTheProjectPath();
		
		//  维护配置文件;
		maintainParameterFile(basedParameters);
	}

	//	获得本工程的路径;
	private String[] getTheProjectPath() {
		//	结果集合;
		String[] results=	new String[3];
		//	项目路径;
		String project	=	System.getProperty("user.dir");
		
		//	程序参数;
		String   exe_name	=	null;
		String   root_path	=	null;
		String 	 pro_name	=	null;
		
		//	当目前的路径中含有"\\"时-当前project就是根目录;
		if(project.contains(TAG)) {
			//	获取最后一个文件路径名称;
			String lastFolder = project.substring(project.lastIndexOf(TAG)+1, project.length());
			
			root_path		=	project;
			
			//	当最后一个文件路径名称=项目名称;
			if (lastFolder.equals(proj)) {
				exe_name	=	lastFolder;
				pro_name	=	lastFolder;
			}
			//	当最后一个文件路径名称!=项目名称;
			else {
				exe_name	=	proj;
				pro_name	=	proj;
				
			}
			
		}
		
		//	将参数放置到数组当中;
		results[0]		= exe_name;
		results[1]		= root_path;
		results[2]		= pro_name;

		return results;
	}
	
	//	维护配置文件;
	private void maintainParameterFile(String[] strs) {
		/* 获得应用名称;
		 * 获得根路径;
		 * 获得项目名称;
		 * */
//		String   exe_name 	=	strs[0];
		String   root_path	=	strs[1];
		String   pro_name	=	proj;
		
		//	获取最后一个节点的文件名称;
		String lasterFolder = 	root_path.substring(root_path.lastIndexOf(TAG)+1, root_path.length());

		String 	 folder_path=	"";
		//	在相同命名的文件夹的目录下;
		if(lasterFolder.equals(proj)) {
			folder_path+=root_path+File.separator+"Program Files";
		}
		//	在不同命名的文件夹的目录下;
		else {
			folder_path+=root_path+File.separator+pro_name+" Program Files";
		}

		super.checkFile(folder_path);
		
		//	对其相关的配置文件夹进行生成;
		String[] items		=   {"config","lib","tables"};
		
		//	生成相应的参数配置文件夹;
		for(String item:items) {
			String path		=  folder_path+File.separator+item;
			
			//	检测父节点文件夹;
			super.checkFile(path);
			
			//	检测配置文件是否生成;
			if(item.equals(items[0])) {
				String temp =  path+File.separator+item+".txt";
				
				File   file =  new File(temp);
				if(!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
			super.checkFile(path);
		}
		
		//	检测数据配置参数是否生成;
		String   config_path =  folder_path+File.separator+items[0]+File.separator+items[0]+".txt";
		//	读取配置文件里的内容,判断是否有数据内容;
		//	创建临时文件;
		File	 temp_file   =  new File(config_path);
		//	读取文件的结果;
		String 	 content_file=  super.readFile(temp_file, CHARSET);
		
		//	当读取的文件为空或者文件异常时进行数据的维护;
		if(content_file==null||content_file.trim().equals("")) {
			super.setTag("初始化参数");
			//	通过JSON的方式将初始化参数放入到容器中;
			JsonObject obj	=	new JsonObject();
			
			//	初始化的参数设置;
			obj.addProperty("mysql_version", "5");
			obj.addProperty("server_ip", "localhost");
			obj.addProperty("server_port", "8080");
			obj.addProperty("db_port", "3306");
			obj.addProperty("db_name", "sinopec");
			obj.addProperty("db_charset", "utf8");
			obj.addProperty("db_user_name", "root");
			obj.addProperty("db_user_password", "root");
			
			//	将初始化的内容写入到文件中;
			super.writeFile(temp_file, obj.toString(), CHARSET);
			super.tagEndModule();
		}
		
		//	将参数的路径进行赋值即可;
		this.config_path=config_path;
	}
}
