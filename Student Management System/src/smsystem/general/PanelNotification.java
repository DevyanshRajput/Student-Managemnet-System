package smsystem.general;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import smsystem.administrator.DataAdmin;
import smsystem.faculty.InfoFaculty;
import smsystem.faculty.DataFaculty;
import smsystem.faculty.MainFaculty;
import smsystem.faculty.PanelViewFaculty;
import smsystem.student.PanelAttandanceReport;
import smsystem.student.PanelMarkSheet;
import smsystem.student.InfoStudent;
import smsystem.student.DataStudent;
import smsystem.student.MainStudent;
import smsystem.student.PanelStudent;
import smsystem.student.PanelViewStudent;
import smsystem.subject.PanelSubject;

@SuppressWarnings("serial")
public class PanelNotification extends JPanel {

	/**
	 * Create the panel.
	 */
	int row=200;
	String date="";
	ArrayList<Integer> readbylist=new ArrayList<Integer>();
	String latest="";
	private JPanel mainpanel;
	private JScrollPane scrollPane;
	private MainStudent sm;
	private MainFaculty fm;
	
	@Override
	public Dimension getPreferredSize()
	{
		return getSize();
	}
	public PanelNotification() 
	{
		setBackground(new Color(255, 255, 255));
		this.setSize(1116, 705);
		setLayout(null);
		setName("Notification Panel");
		JLabel headinglabel = new JLabel(" Notification");
		headinglabel.setIcon(null);
		headinglabel.setBounds(10, 0, 1096, 188);
		add(headinglabel);
		headinglabel.setBackground(new Color(32, 178, 170));
		headinglabel.setHorizontalAlignment(SwingConstants.LEFT);
		headinglabel.setForeground(Color.WHITE);
		headinglabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		headinglabel.setOpaque(true);
		mainpanel=new JPanel()
		{
			public Dimension getPreferredSize()
			{
				return new Dimension(286,row);
			}
		};
		mainpanel.setSize(this.getWidth(), row);
		mainpanel.setLayout(null);
		mainpanel.setBackground(Color.white);
		
		 scrollPane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 188);
		scrollPane.setSize(this.getWidth()-10,this.getHeight()-188);
		scrollPane.setViewportView(mainpanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(80);
		for(Component c:scrollPane.getComponents())
		{
			c.setBackground(Color.white);
		}
		scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		
		add(scrollPane);
		row=0;
	}
	public PanelNotification(MainStudent sm)
	{
		this();
		this.sm=sm;
		addNotification(sm.s.getCourceCode(),sm.s.getSemorYear(),"Student",sm.s.getUserId(),sm.s.getAdmissionDate());
	}
	public PanelNotification(MainFaculty fm)
	{
		this();
		this.fm=fm;
		addNotification(fm.f.getCourceCode(),fm.f.getSemorYear(),"Faculty",fm.f.getFacultyId()+"",fm.f.getJoinedDate());
		
	}
	public void addNotification(String courcecode,int semoryear,String userprofile,String userid,String joinedtime)
	{
		ArrayList<Notification> list=new DataNotification().getNotifications(courcecode, semoryear,userprofile,joinedtime);
		for(Notification n:list)
		{
			
			StringTokenizer readby=new StringTokenizer(n.getReadBy(),"#");
			boolean contain=false;
			if(n.getReadBy().isEmpty())
			{
				readbylist.add(n.getSrNo());
			}
			else
			{
				
				while(readby.hasMoreTokens())
				{
					if(readby.nextToken().equals(userid))
					{
						contain=true;
						break;
					}
				}
				if(!contain)
				{
					readbylist.add(n.getSrNo());
				}
			}
			if(!contain&&!latest.equals("New"))
			{
				addLabel("New");
				latest="New";
			}
			if(contain&&!latest.equals("Earlier"))
			{
				addLabel("Earlier");
				latest="Earlier";
			}
			JPanel panel=createPanel(n);
			mainpanel.add(panel);
			row+=79;
		}
		if(readbylist.size()>0)
		{
		new DataNotification().addReadBy(readbylist, userid);
		}
		
	}
	public void addLabel(String message)
	{
		JLabel label=new JLabel(message);
		label.setBounds(10, row, this.getWidth()-20, 30);
		label.setForeground(new Color(0,129,129));
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setFont(new Font("Segoe UI",Font.PLAIN,20));
		mainpanel.add(label);
		row+=35;
	}
	public JPanel createPanel(Notification n)
	{
		JPanel panel=new JPanel();
		panel.setBorder(new MatteBorder(1,0,1,0,Color.LIGHT_GRAY));
		panel.setLayout(null);
		panel.setBackground(Color.white);
		panel.setBounds(this.getX()+10, row, this.getWidth()-40, 80);
		
		JLabel titlelabel=new JLabel(n.getTitle());
		titlelabel.setBounds(110,5,400,30);
		titlelabel.setForeground(Color.DARK_GRAY);
		titlelabel.setFont(new Font("Segoe UI",Font.BOLD,18));
		panel.add(titlelabel);
		
		JLabel timelabel=new JLabel(n.getTime());
		timelabel.setBounds(410,10,panel.getWidth()-440,30);
		timelabel.setHorizontalAlignment(JLabel.RIGHT);
		timelabel.setForeground(new Color(0,139,139));
		timelabel.setFont(new Font("Segoe UI",Font.PLAIN,15));
		panel.add(timelabel);
	
		JLabel profilepiclabel=new JLabel();
		profilepiclabel.setBounds(10,2,80,75);
		profilepiclabel.setHorizontalAlignment(JLabel.CENTER);
		Image image=null;
		if(n.getTitle().equals("New Student"))
		{
			image=new DataStudent().getProfilePic(n.getUserId()).getScaledInstance(65, 65, Image.SCALE_SMOOTH);
		}
		else if(n.getTitle().equals("Subject Faculty"))
		{
			image=new DataFaculty().getProfilePic(n.getUserId()).getScaledInstance(65, 65, Image.SCALE_SMOOTH);;
		}
		else
		{
			image=new DataAdmin().getProfilePic().getScaledInstance(65, 65, Image.SCALE_SMOOTH);;
		}
		image=ImageFunc.makeRoundedCorner(ImageFunc.toBufferedImage(image), 65);
		profilepiclabel.setIcon(new ImageIcon(image));
		panel.add(profilepiclabel);
	
		
		JLabel messagelabel=new JLabel(n.getMessage());
		messagelabel.setBounds(110,35,panel.getWidth()-10,30);
		messagelabel.setForeground(Color.gray);
		messagelabel.setFont(new Font("Segoe UI",Font.PLAIN,18));
		panel.add(messagelabel);
		panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.setName(n.getTitle()+"#"+n.getUserId());
		panel.addMouseListener(new MouseAdapter()
				{
					public void mouseEntered(MouseEvent e)
					{
						panel.setBackground(new Color(250,250,250));
					}
					public void mouseExited(MouseEvent e)
					{
						panel.setBackground(Color.white);
					}
					public void mousePressed(MouseEvent e)
					{
						if(e.getButton()==MouseEvent.BUTTON1&&e.getClickCount()>0)
						{
							StringTokenizer name=new StringTokenizer(panel.getName(),"#");
							String title=name.nextToken();
							String userid=name.nextToken();
							if(title.equals("New Student"))
							{
								InfoStudent s=new DataStudent().getStudentDetailsByUserId(userid);
								if(sm!=null)
								{
									
									sm.viewstudentpanel=new PanelViewStudent(s,sm,sm.notificationpanel);
									sm.viewstudentpanel.setVisible(true);
									sm.notificationpanel.setVisible(false);
									sm.viewstudentpanel.setLocation(sm.panelx,0);
									sm.viewstudentpanel.setVisible(true);
									sm.viewstudentpanel.setFocusable(true);
									sm.contentPane.add(sm.viewstudentpanel);
								}
								else if(fm!=null)
								{
									fm.viewstudentpanel=new PanelViewStudent(s,fm,fm.notificationpanel);
									fm.viewstudentpanel.setVisible(true);
									fm.notificationpanel.setVisible(false);
									fm.viewstudentpanel.setLocation(fm.panelx,0);
									fm.viewstudentpanel.setVisible(true);
									fm.viewstudentpanel.setFocusable(true);
									fm.contentPane.add(fm.viewstudentpanel);	
								}
							}
							else if(title.equals("Subject Faculty"))
							{
								InfoFaculty f=new DataFaculty().getFacultyInfobyId(Integer.parseInt(userid));
								if(sm!=null)
								{
									sm.viewfacultypanel=new PanelViewFaculty(f,sm,sm.notificationpanel);
									sm.viewfacultypanel.setVisible(true);
									sm.notificationpanel.setVisible(false);
									sm.viewfacultypanel.setLocation(sm.panelx,sm.panely);
									sm.viewfacultypanel.setVisible(true);
									sm.viewfacultypanel.setFocusable(true);
									sm.contentPane.add(sm.viewfacultypanel);
								}
								else if(fm!=null)
								{
									fm.viewfacultypanel=new PanelViewFaculty(f,fm,fm.notificationpanel);
									fm.viewfacultypanel.setVisible(true);
									fm.notificationpanel.setVisible(false);
									fm.viewfacultypanel.setLocation(fm.panelx,fm.panely);
									fm.viewfacultypanel.setVisible(true);
									fm.viewfacultypanel.setFocusable(true);
									fm.contentPane.add(fm.viewfacultypanel);
								}
							}
							else if(title.equals("Result"))
							{
								if(sm!=null)
								{
									sm.notificationpanel.setVisible(false);
									PanelMarkSheet marksheetpanel=new PanelMarkSheet(sm,sm.s,sm.notificationpanel);
									marksheetpanel.setVisible(true);
									sm.marksheetpanelscroll=new JScrollPane(marksheetpanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
									sm.marksheetpanelscroll.getVerticalScrollBar().setUnitIncrement(16);
									sm.marksheetpanelscroll.setBounds(sm.panelx,sm.panely, 1116, 705);
									sm.contentPane.add(sm.marksheetpanelscroll);
									sm.marksheetpanelscroll.setVisible(true);
								}
								else if(fm!=null)
								{
									fm.notificationpanel.setVisible(false);
									fm.studentpanel=new PanelStudent(fm,fm.notificationpanel);
									fm.studentpanel.setLocation(fm.panelx,fm.panely);
									fm.studentpanel.setVisible(true);
									fm.studentpanel.setFocusable(true);
									fm.contentPane.add(fm.studentpanel);
								}
							}
							else if(title.equals("Attandance"))
							{
								if(sm!=null)
								{
									sm.notificationpanel.setVisible(false);
									sm.attandancereportpanel=new PanelAttandanceReport(sm,sm.notificationpanel);
									sm.attandancereportpanel.setVisible(true);
									sm.attandancereportpanelscroll=new JScrollPane(sm.attandancereportpanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
									sm.attandancereportpanelscroll.setBounds(sm.panelx, sm.panely, 1116, 705);
									sm.attandancereportpanelscroll.setVisible(true);
									sm.attandancereportpanelscroll.setName("Attadance Report Panel Scroll");
									sm.attandancereportpanelscroll.getVerticalScrollBar().setUnitIncrement(16);
									sm.contentPane.add(sm.attandancereportpanelscroll);
									for(Component c:sm.attandancereportpanelscroll.getComponents())
									{
										c.setBackground(Color.white);
									}	
								}
							}
							else if(title.equals("New Subject"))
							{
								if(sm!=null)
								{
									sm.notificationpanel.setVisible(false);
									sm.subjectpanel=new PanelSubject(sm,sm.notificationpanel);
									sm.subjectpanel.setLocation(sm.panelx, sm.panely);
									sm.subjectpanel.setFocusable(true);
									sm.contentPane.add(sm.subjectpanel);
									sm.subjectpanel.setVisible(true);
								}
								else if(fm!=null)
								{
									fm.notificationpanel.setVisible(false);
									fm.subjectpanel=new PanelSubject(fm,fm.notificationpanel);
									fm.subjectpanel.setLocation(fm.panelx, fm.panely);
									fm.subjectpanel.setFocusable(true);
									fm.contentPane.add(fm.subjectpanel);
									fm.subjectpanel.setVisible(true);
								}
							}
						}
					}
				}
				);
		return panel;
	}
	
	
}