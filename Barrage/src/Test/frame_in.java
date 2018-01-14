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
	 public static int text=1;//id编号
	 
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
	
	//设置面板
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
	p.add(test);
	p.setOpaque(false);
	
	//设置JFrame
	this.add(p);
	//this.setSize(1200, 675);
	 //this.setLocation(450, 200);
	
	this.setExtendedState(Frame.MAXIMIZED_BOTH);
	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 this.setAlwaysOnTop(true);
	 this.setVisible(true);
	
	 Timer timer=new Timer();
	 
	 timer.schedule(new TextTimer()	 , 2000);

 }
 
public class TextTimer extends TimerTask{
	public void run()
	 {
		 try
			{
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("jdbc:sqlite:test.sqlite3");
				Statement stat=conn.createStatement();
				//stat.executeUpdate("insert into table1 values('aa',12);");
			
				ResultSet rs=stat.executeQuery("select * from content;");
				String page="";
				while(rs.next())
				{
					String x=rs.getString("id");
					int temp=Integer.parseInt(x);
					
					if(temp==text)
					{
						page+=rs.getString("text");
						page+="\n";
						test.setText(page);
						text++;
					}
					Thread.sleep(1000);
				}
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
