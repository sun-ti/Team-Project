package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

import models.Utils;

public class Util implements Utils{
	
	//	时间的标签参数;
	public  final String TimeFormat= "yyyy-MM-dd HH:mm:ss";
	//	车牌识别;
	public  final int    TAG_KIND1 = 1;
	
	//	客流统计;
	public  final int    TAG_KIND2 = 2;
	
	//	人脸识别;
	public  final int    TAG_KIND3 = 3;
	
	//	卸油区识别;
	public  final int    TAG_KIND4 = 4;
	//	车流摄像头统计;
	public  final int    TAG_KIND5 = 5;

	//	客户类型;
	public  final int    TAG_KIND1_CLIENT = 1;
	public  final int    TAG_KIND2_CLIENT = 2;
	public  final int    TAG_KIND3_CLIENT = 3;
	public  final int    TAG_KIND4_CLIENT = 4;
	
	//	计时的标签参数;
	public  String tag; 
	//	时间的戳t1;
	private long   t1;
	//	时间的戳t2;
	private long   t2;
	
	//	构造函数的内容;
	public Util() {
		
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	//	获得长整型的时间长度类型;
	public String[] getTimeSpans() {
		//	承装结果的容器;
		String[] results	=	new String[5];
		
		long   	 t2			=	System.currentTimeMillis();
		//	TODO 参数调试的内容部件;
//		String 	 testtime	=	"2019-11-04 02:00:00";
//		long	 t2			=   transDateStr2Long(testtime, TimeFormat);
		
		//	当前的时间内容;
		String 	 nowtime	=	getCurrentDatetime(t2, TimeFormat);
		
		//	一天以前的毫秒;
		long   	 t1			=	t2-(24*60*60*1000L);
		String 	 beforetime	=	getCurrentDatetime(t1, TimeFormat);
		
		//	时间的差值毫秒数;
		long     dis	    =	(t2-t1)/2;
		
		//	获得中间时间点时间;
		long     ltoday 	=	t1+dis;
		
		//	获得当前时间戳;
		String 	  today  	=   getCurrentDatetime(ltoday, "yyyy-MM-dd");
		//	获得时间数组;
		String[]  dates		=   today.split("-");
		String    year  	=	dates[0];
		String    month 	=	dates[1];
		String    day   	=	dates[2];
		System.out.println("获得从 "+beforetime+" 至 "+nowtime+" 的数据 统计的时间为:"+today+"的数据");
		
		//	将相应的数据信息进行转变;
		String	  date1     =   today+" 00:00:00";
		String	  date2     =   today+" 23:59:59";
		
		long	  l1	  	= 	transDateStr2Long(date1, "yyyy-MM-dd HH:mm:ss");
		
		long	  l2	  	= 	transDateStr2Long(date2, "yyyy-MM-dd HH:mm:ss");
		
		//	t1长整型;
		results[0]	  		= 	l1+"";
		//	t2长整型;
		results[1]	  		= 	l2+"";
		
		//	年月日的时间添加;
		results[2]	  		= 	year;
		results[3]	  		= 	month;
		results[4]	  		= 	day;
		
		//	返回结果容器;
		return results;
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

	//	获得随机数;
	public int getRandomNum(int max) {
		Random random=new Random();
		return random.nextInt(max);
	}
	
	//	获得26个字母的随机数;
	public String getRandommEng26() {
	    //	进行26个字母书写的偏移量;
		int c='a'+(int)(Math.random()*26);
		
	    return ((char)c+"").toUpperCase();
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
    //	快速排序的内容;
    public void quickSort(int[] arr,int high,int low){   
        int i,j,temp;  
        i=high;//高端下标  
        j=low;//低端下标  
        temp=arr[i];//取第一个元素为标准元素。  
          
        while(i<j){//递归出口是 low>=high  
              while(i<j&&temp>arr[j])//后端比temp小，符合降序，不管它，low下标前移
           	   j--;//while完后指比temp大的那个
              if(i<j){
           	   arr[i]=arr[j];
           	   i++;
              }
              while(i<j&&temp<arr[i])
           	   i++;
              if(i<j){
           	   arr[j]=arr[i];
           	   j--;
              }
        }//while完，即第一盘排序  
        arr[i]=temp;//把temp值放到它该在的位置。  
     
        if(high<i) //注意，下标值	 
       	 quickSort(arr,high,i-1);//对左端子数组递归  
        if(i<low)  //注意，下标值
            quickSort(arr,i+1,low);//对右端子数组递归  ；对比上面例子，其实此时i和j是同一下标!!!!!!!!!!!!!
 
    } 
    //	按照位保存小数点后第n位;
    public String getDataAccordingToByte(String str,int n) {
		String head="0.";
		String mid="";
	    for(int i=0;i<n;i++) {
	    	mid+="0";
	    }
		
		String result=head+mid;
    	DecimalFormat df = new DecimalFormat(result);
    	return df.format(Double.parseDouble(str));
	}
    //	按照位保存小数点后第n位;
	public String getDataAccordingToByte(Double d,int n) {
		String head="0.";
		String mid="";
	    for(int i=0;i<n;i++) {
	    	mid+="0";
	    }
		
		String result=head+mid;
    	DecimalFormat df = new DecimalFormat(result);
    	return df.format(d);
	}
    //	数据的转化操作;
    public String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(20);
         // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }
}
