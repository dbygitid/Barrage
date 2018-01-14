package Test;


import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.sql.*;

import org.sqlite.JDBC;

public  class frame_in extends JFrame{
	
	 private JTextPane test=new JTextPane();
	 private JPanel p=new JPanel();
	 public static int id=1;//id编号
	 
	 Font font=new Font("宋体",Font.PLAIN,40);

 public frame_in()
 {
	 
	 //设置frame透明	 
	 this.setUndecorated(true);	
	 this.setBackground(new Color(0, 0, 0, 0));
	 
	 //设置JTextPane透明
	test.setOpaque(false);
	test.setEditable(false);
	//设置字体大小、颜色等
	test.setFont(font);
	test.setForeground(Color.RED);
	
	//设置面板，面板只添加一个test的JTextPane即可，test用于内容显示
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
	p.add(test);
	p.setOpaque(false);
	
	//设置JFrame，添加p面板
	this.add(p);
	this.setExtendedState(Frame.MAXIMIZED_BOTH);
	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 this.setAlwaysOnTop(true);
	 this.setVisible(true);
	
	 Timer timer=new Timer();
	 //每2000毫秒运行TextTimer程序，检测数据库内容是否更新，更新即输出
	 timer.schedule(new TextTimer()	 , 2000);

 }
 
public class TextTimer extends TimerTask{
	public void run()
	 {
		 try
			{
			    //链接SQLite数据库
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("jdbc:sqlite:test.sqlite3");
				Statement stat=conn.createStatement();
				//搜寻content表格
				ResultSet rs=stat.executeQuery("select * from content;");
				
				//JTextPane显示的内容,格式为String
				String page="";
				
				//如果有id更新，就将text的内容添加进入page，从而在JTextPane中显示出来
				while(rs.next())
				{
					String x=rs.getString("id");
					int temp=Integer.parseInt(x);
					
					if(temp==id)
					{
						page+=rs.getString("text");
						page+="\n";
						test.setText(page);
						id++;
					}
					//在此停顿1500毫秒
					Thread.sleep(1500);
				}
				//最后内容显示完以后，将内容情况，为下一次做准备
				test.setText(" ");
				rs.close();
				conn.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	 }
}
 
}
