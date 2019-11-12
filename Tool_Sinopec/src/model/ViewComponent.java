package model;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.JsonObject;
//import com.sun.xml.internal.ws.util.StringUtils;

//	进行可视化界面的显示内容;
public class ViewComponent extends JFrame{

	//	序列号的内容；
	private static final long serialVersionUID = 1L;
	//	charset;
	private final String CHARSET			   = "utf-8";
	
	//	定义组件的容器;
	private JPanel 		 jp1, jp2, jp3,jp4,jp5,jp6,jp7,jp8,jp9;
    private JLabel 		 jl_mysql_version,jl_server_ip,jl_server_port,jl_db_port,jl_db_name,jl_db_charset,jl_db_user_name,jl_db_user_pwd;
    private JTextField 	 jtf_mysql_version,jtf_server_ip,jtf_server_port,jtf_db_port,jtf_db_name,jtf_db_charset,jtf_db_user_name,jtf_db_user_pwd;
	
    private JButton		 jb_ok,jb_no,jb_test_db,jb_run,jb_init;
    //	设置控件的标题;
    private final String TAG_TITLE	="中石化-统计-调试工具"; 
    private final String TAG_TIP    ="提示"; 
	private final int    FONT_HEIGHT=20;
	
	private final int	 VIEW_HEIGHT=50;
	private final int    FONT_rows=9;
	private final int    FONT_cols=12;
	
	//	数据库相应配置;
	private DBaseUtil	 baseUtil;
	private Util		 util;
	
	//	界面显示的文件内容;
	private int 		 mysql_version 	= 0;
	private String  	 server_ip	  	= null;
	private String 		 server_port	= null;
	private String  	 port		  	= null;
	private String  	 db_name       	= null;
	private String  	 charset	    = null;
	private String 		 user			= null;
	private String 		 password  		= null;
	private String 		 configpath		= null;
	private String 		 exe_name		= null;
	
	//	配置文件-文件夹;
	private String 		 runpath		= null;
//	private Process 	 process 		= null;
	
	//	进行数据传递的内容;
	public ViewComponent(DBaseUtil baseUtil) throws HeadlessException {
		super();
		//	进行数据库的配置;
		this.baseUtil		= baseUtil;
		//	进行数据库的打开;
		this.baseUtil.open();
		
		//	初始化参数的内容;
		this.mysql_version 	= this.baseUtil.getMysql_version();
		this.server_ip	  	= this.baseUtil.getServer_ip();
		this.server_port	= this.baseUtil.getServer_port();
		this.port		  	= this.baseUtil.getPort();
		this.db_name       	= this.baseUtil.getDb_name();
		this.charset	    = this.baseUtil.getCharset();
		this.user			= this.baseUtil.getUser();
		this.password  		= this.baseUtil.getPassword();
		this.configpath		= this.baseUtil.getConfigPath();
		
		//	获得问价夹的内容;
		String   rootpath	= configpath.substring(0,(configpath.indexOf("config\\")-1)-"Program Files".length());
		//	寻找到根目录;
		String 	 parentpath = rootpath.substring(0,rootpath.lastIndexOf("\\"));
		
		//	判断参数的设置头;
		//	数据的程序内容;	
		exe_name			= "TJ_Sinopec.exe";
		
		runpath				= parentpath+File.separator+exe_name;
		
		//	进行列表的显示;
		Toolkit.getDefaultToolkit().beep();
		
		//	进行工具类的开启;
		this.util			= new Util();

	}

	//	图形界面的初始化设置;
	public void initView() {
		//	控件容器行声明;
		jp1				=	new JPanel();
		jp2				=	new JPanel();
		jp3				=	new JPanel();
		jp4				=	new JPanel();
		jp5				=	new JPanel();
		jp6				=	new JPanel();
		jp7				=	new JPanel();
		jp8				=	new JPanel();
		jp9				=	new JPanel();
		
		//	标签的声明;
		jl_mysql_version= new JLabel("MySQL的版本");
		jl_mysql_version.setFont(new Font (Font.DIALOG, Font.BOLD,FONT_HEIGHT));
		
		jl_server_ip	= new JLabel("服务器 IP地址");
		jl_server_ip.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jl_server_port  = new JLabel("服务器端口号");
		jl_server_port.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jl_db_port		= new JLabel("数据库端口号");
		jl_db_port.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jl_db_name		= new JLabel("数据库的名称");
		jl_db_name.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jl_db_charset	= new JLabel("数据库字符集");
		jl_db_charset.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jl_db_user_name = new JLabel("数据库用户名");
		jl_db_user_name.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jl_db_user_pwd  = new JLabel("数据库的密码");
		jl_db_user_pwd.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		//	填写框的声明;
		jtf_mysql_version=	new JTextField(20);
		jtf_mysql_version.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_mysql_version.setText(this.mysql_version+"");
		
		jtf_server_ip	 =	new JTextField(20);
		jtf_server_ip.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_server_ip.setText(this.server_ip);
		
		jtf_server_port	 =	new JTextField(20);
		jtf_server_port.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_server_port.setText(this.server_port);
		
		jtf_db_port		 =	new JTextField(20);
		jtf_db_port.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_db_port.setText(this.port);
		
		jtf_db_name		 =	new JTextField(20);
		jtf_db_name.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_db_name.setText(this.db_name);
		
		jtf_db_charset	 = new JTextField(20);
		jtf_db_charset.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_db_charset.setText(this.charset);
		
		jtf_db_user_name =	new JTextField(20);
		jtf_db_user_name.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_db_user_name.setText(this.user);
		
		jtf_db_user_pwd	 =	new JTextField(20);
		jtf_db_user_pwd.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		jtf_db_user_pwd.setText(this.password);
		
		//	确认和取消按钮;
		jb_ok			 =	new JButton("设置");
		jb_ok.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jb_no			 =	new JButton("重置");
		jb_no.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jb_test_db		 =	new JButton("测试");
		jb_test_db.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jb_run		 	 =	new JButton("启动");
		jb_run.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		jb_init		 	 =	new JButton("初始化");
		jb_init.setFont(new Font (Font.DIALOG, Font.BOLD, FONT_HEIGHT));
		
		//	设置布局的高度;
		this.setLayout(new GridLayout(FONT_rows, 1));
		//	容器配对;
		jp1.add(jl_mysql_version);
		jp1.add(jtf_mysql_version);
		
		jp2.add(jl_server_ip);
		jp2.add(jtf_server_ip);
		
		jp3.add(jl_server_port);
		jp3.add(jtf_server_port);
		
		jp4.add(jl_db_port);
		jp4.add(jtf_db_port);
		
		jp5.add(jl_db_name);
		jp5.add(jtf_db_name);
		
		jp6.add(jl_db_charset);
		jp6.add(jtf_db_charset);
		
		jp7.add(jl_db_user_name);
		jp7.add(jtf_db_user_name);
		
		jp8.add(jl_db_user_pwd);
		jp8.add(jtf_db_user_pwd);

		jp9.add(jb_ok);
		jp9.add(jb_no);
		jp9.add(jb_test_db);
		jp9.add(jb_run);
		jp9.add(jb_init);

		//	将布局添加到控件中;
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
		this.add(jp6);
		this.add(jp7);
		this.add(jp8);
		this.add(jp9);
		
		int view_width	=	VIEW_HEIGHT*FONT_cols;
		int view_height	=	VIEW_HEIGHT*FONT_rows;
		
		this.setSize(view_width, view_height);
		//	窗体居中显示
		this.setLocationRelativeTo(null);
		//	设置不可改变大小
		this.setResizable(false);
		
		this.setTitle(TAG_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
	}
		
	public ViewComponent(GraphicsConfiguration gc) {
		super(gc);
	}

	public ViewComponent(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public ViewComponent(String title) throws HeadlessException {
		super(title);
	}
	
	//	进行事件监听的添加;
	public void setListener() {
		//	添加事件监听;
		//	确认修改按钮;
		jb_ok.addActionListener(new SubmitAction());
		//	重置输入按钮;
		jb_no.addActionListener(new CancelAction());
		//	测试数据库按钮;
		jb_test_db.addActionListener(new TestAction());
		//	启动程序按钮;
		jb_run.addActionListener(new RunAction());
		//	初始程序按钮;
		jb_init.addActionListener(new StopAction());
	}
	

	//	添加提交触发的监听;
	public class SubmitAction extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;

		@Override
	    public void actionPerformed(ActionEvent e) {
			
			String t1 = jtf_mysql_version.getText().toString();
			String t2 = jtf_server_ip.getText().toString();
			String t3 = jtf_server_port.getText().toString();
			String t4 = jtf_db_port.getText().toString();
			String t5 = jtf_db_name.getText().toString();
			String t6 = jtf_db_charset.getText().toString();
			String t7 = jtf_db_user_name.getText().toString();
			String t8 = jtf_db_user_pwd.getText().toString();
			
			//	临时;
			if((!t1.equals("")&&t1!=null)&&
			   (!t2.equals("")&&t2!=null)&&
			   (!t3.equals("")&&t3!=null)&&
			   (!t4.equals("")&&t4!=null)&&
			   (!t5.equals("")&&t5!=null)&&
			   (!t6.equals("")&&t6!=null)&&
			   (!t7.equals("")&&t7!=null)&&
			   (!t8.equals("")&&t8!=null)) {
				
				//	进行数据的保存;
				//	通过JSON的方式将初始化参数放入到容器中;
				JsonObject obj=new JsonObject();
				
				//	初始化的参数设置;
				obj.addProperty("mysql_version", t1);
				obj.addProperty("server_ip", t2);
				obj.addProperty("server_port", t3);
				obj.addProperty("db_port", t4);
				obj.addProperty("db_name", t5);
				obj.addProperty("db_charset", t6);
				obj.addProperty("db_user_name", t7);
				obj.addProperty("db_user_password", t8);
				
				File file=new File(configpath);
				try {
					if(file.exists()) {
						file.delete();
						file.createNewFile();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//	写入到文件中;
				boolean flag=util.writeFile(file, obj.toString(), CHARSET);
		        
				if(flag)
					JOptionPane.showMessageDialog(null,"修改成功!",TAG_TIP, JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"修改失败!",TAG_TIP, JOptionPane.WARNING_MESSAGE);
				
			}
		}
	}
	//	添加取消触发的监听;
	public class CancelAction extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			jtf_mysql_version.setText(mysql_version+"");
			jtf_server_ip.setText(server_ip);
			jtf_server_port.setText(server_port);
			jtf_db_port.setText(port);
			jtf_db_name.setText(db_name);
			jtf_db_charset.setText(charset);
			jtf_db_user_name.setText(user);
			jtf_db_user_pwd.setText(password);
			
		}
	}
	//	添加测试触发的监听;
	public class TestAction extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String mysql_version=jtf_mysql_version.getText().toString();
			String server_ip	=jtf_server_ip.getText().toString();
			String server_port	=jtf_server_port.getText().toString();
			String db_port		=jtf_db_port.getText().toString();
			String db_name		=jtf_db_name.getText().toString();
			String db_charset	=jtf_db_charset.getText().toString();
			String user			=jtf_db_user_name.getText().toString();
			String pwd			=jtf_db_user_pwd.getText().toString();

			boolean flag=baseUtil.checkOpen(mysql_version, server_ip, server_port, db_port, db_name, db_charset, user, pwd);
			
			if(flag) {
				JOptionPane.showMessageDialog(null, "数据库连接成功!", TAG_TIP, JOptionPane.WARNING_MESSAGE);
			}else
				JOptionPane.showMessageDialog(null, "数据库连接失败!", TAG_TIP, JOptionPane.WARNING_MESSAGE);
		}
	}
	//	添加启动触发的监听;
	public class RunAction extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//	检测文件是否存在;
			File file		=	new File(runpath);

			if(!file.exists())
				JOptionPane.showMessageDialog(null,"程序不存在!无法正常运行!",TAG_TIP, JOptionPane.WARNING_MESSAGE);
			
			else {
	
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					
					JOptionPane.showMessageDialog(null,"程序运行异常!",TAG_TIP, JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
	}
	//	添加停止触发的监听;
	public class StopAction extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//	设置选择器;
			//	选择文件的控件;
			JFileChooser 	chooser	=	new JFileChooser();
			String   		rootpath= configpath.substring(0,configpath.indexOf("config\\")-1)+File.separator+"tables";
			
			//	获得当前父目录;
			//	设定指定目录;
			chooser.setCurrentDirectory(new File(rootpath));
			//	设置为多选;
			chooser.setMultiSelectionEnabled(true);
			
			/* 根据JFileChooser对弹出的文件夹框选择 
			 * 1.只选择目录JFileChooser.DIRECTORIES_ONLY
			 * 2.只选择文件JFileChooser.FILES_ONLY
			 * 3.目录或者文件都可以JFileChooser.FILES_AND_DIRECTORIES
			 */
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			//	是否打开文件选择框;
			int ch=chooser.showSaveDialog(jb_init);
			
			//	获得选中的对象;
			if(ch==JFileChooser.APPROVE_OPTION) {
				
				//	可以进行多文件的选择;
				File[] 			  files = chooser.getSelectedFiles();
				
				for(File file:files) {
					
					//	文件的信息内容;
					String content	=	baseUtil.readFile(file, "GBK").toString();
					//	数据进行操作;
					baseUtil.update(content);					
				}
				//	数据表进行更新的操作;
				JOptionPane.showMessageDialog(null,"表结构创建完毕!",TAG_TIP, JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
