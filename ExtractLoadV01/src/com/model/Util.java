package com.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.models.Utils;

/**
 * @author Lenovo
 *
 */
/**
 * @author Lenovo
 *
 */
public class Util implements Utils{

	public static final String FOLDER="SYS"+File.separator+"SINOPEC";
	public static final String FOLDER1="configer";
	public static final String PATH = "D:"+File.separator+FOLDER+File.separator+"SYS_image";
	public static final String PATH2= "D:"+File.separator+FOLDER+File.separator+"SYS_download";
	public static final String PATH3= "D:"+File.separator+FOLDER+File.separator+"SYS_info";
	//	进行初始化参数对象;
	public static Util_Init instance;
	
	//	文件存储的标签;
	public String KEY_1="pic";
	public String TAG_1="%";
	public String TAG_2="\\";
	public String TAG_3="-";
	
	//	格式的相应的内容;
	public String UTF8 ="utf-8";
	public String GBK  ="gbk";
	
	//	计时的标签参数;
	public String tag; 
	private long t1;
	private long t2;

	
	//	构造函数的内容;
	public Util() {
		
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	//	重载文件监测的内容;
	public void checkFile(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}
	//	开始进行程序的相应的操作;
	public void tagStartModule() {
		System.out.println("--------------------------------");
		System.out.println("'"+this.tag+"'功能-开始运行!");
		this.t1=System.currentTimeMillis();
	}
	
	//	结束进行程序的相应的操作;
	public void tagEndModule() {
		
		System.out.println("'"+this.tag+"'功能-结束运行!");
		this.t2=System.currentTimeMillis();
	}
	
	//	程序花费时间的相应的操作;
	public void tagComputeProcessingTime() {
		long td=this.t2-this.t1;
		System.out.println("'"+this.tag+"'功能-花费时间:"+td+"毫秒!");
		System.out.println("--------------------------------");
	}

	//	获得系统获得UUID的方法;
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
	//	获得相应的时间内容;
	@Override
	public String getCurrentDatetime(long ltime,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//可以方便地修改日期格式
		String currenttime 			= dateFormat.format(ltime); 
		return currenttime;
	}
	
	//	将时间转换为长整型数;
	@Override
	public long transDateStr2Long(String date, String format) {
		long 		result	 = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//	结果信息内容;
		try {
			result			 = sdf.parse(date).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 判断时间是否符合标准;
	public boolean checkDate(String str,String str2) {

		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(str2);
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	//	获得时间的元素;
	public String[] getDateElements(String date,String tag) {
		String[] dates=null;
		
		//	字符串是否含有标签;
		if(date.contains(tag)) {
			dates=date.split(tag);
		}else
			return null;
		
		//	长度段3为全的;
		if(dates.length==3) {
			return dates;
		}else
			return null;
	}
	//	读取文件;
	public String readFile(File file,String format){
		
        BufferedReader br;
        StringBuffer   buffer = new StringBuffer();
        String 		   result = null;
		try {
			br 			= new BufferedReader(new InputStreamReader(new FileInputStream(file), format));
			String line = null;
	        while ((line= br.readLine()) != null) {
	        	//	进行文件的读取;
	        	buffer.append(line);
	        }
	        result		= buffer.toString();
		} catch (Exception e) {
			//	当异常时,进行参数的赋值;
			result=null;
		}
		//	将结果进行数据的返回;
        return result;
    }
    
    public boolean writeFile(File file, String content,String format){
    	//	输出流的相应内容;
        FileOutputStream   fos;
        //	输出的对象的内容;
        OutputStreamWriter osw;
        boolean flag=false;
		try {
			//	建立输出流;
			fos = new FileOutputStream(file);
			//	将数据进行输出;
			osw = new OutputStreamWriter(fos, format);
	        osw.write(content);
	        osw.flush();
	        flag= true;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag= false;
		}
        return flag;
    }
}
