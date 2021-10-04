package smsystem.student;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

import smsystem.administrator.PanelProfileAdmin;
import smsystem.chat.DataChat;
import smsystem.chat.PanelChatMain;
import smsystem.general.DatabaseConnection;
import smsystem.general.PanelHome;
import smsystem.general.DataNotification;
import smsystem.general.PanelNotification;
import smsystem.general.PanelSearch;
import smsystem.general.TimeFunc;
import smsystem.faculty.PanelFaculty;
import smsystem.faculty.PanelViewFaculty;
import smsystem.application.SMSystem;
import smsystem.subject.PanelAssignSubject;
import smsystem.subject.PanelSubject;

/*
 * Title : StudentMain.java
 * Purpose : Student Main Frame
 */

@SuppressWarnings("serial")
public class MainStudent extends JFrame  implements ActionListener
{

	public JPanel contentPane;
	private JLabel studentnamelabel;
	private JLabel studentprofilepiclabel;
	private JPanel profilepanel;
	private JButton homebutton;
	private JButton studentsbutton;
	private JButton subjectbutton;
	private JButton faculitiesbutton;
	private JButton marksheetbutton;
	private JButton attandancereportbutton;
	private JButton searchbutton;
	private JButton notificationbutton;
	private JButton contactusbutton;
	private JButton logoutbutton;
	private JButton exitbutton;
	private Color buttonbcolor=Color.DARK_GRAY;
	private Color buttonfcolor=Color.LIGHT_GRAY;
	private Font buttonfont=new Font("Tw Cen MT", Font.PLAIN, 20);
	public PanelSubject subjectpanel;
	public PanelHome homepanel;
	public PanelStudent studentpanel;
	public PanelViewStudent viewstudentpanel;
	public PanelMarkSheet marksheetpanel;
	public JScrollPane marksheetpanelscroll;
	public PanelViewFaculty viewfacultypanel;
	public PanelAssignSubject assignsubjectpanel;
	public PanelEnterMarks entermarkspanel;
	public JScrollPane entermarkspanelscroll;
	private JScrollPane markattandancepanelscroll;
	public PanelAttandanceReport attandancereportpanel;
	public JScrollPane attandancereportpanelscroll;
	public PanelFaculty facultypanel;
	public PanelProfileAdmin adminprofilepanel;
	public PanelSearch searchpanel;
	private PanelChatMain chatmainpanel;
	public PanelNotification notificationpanel;
	public int panely=0,panelx=250;
	private JButton btn;
	private JButton myprofilebutton;
	private String lastlogin;
	public InfoStudent s;
	private int row=63;
	private JButton assignedsubjectbutton;
	private JButton chatbutton;
	public Socket socket;
	private Timer timer;
	private BufferedImage messagecount;
	private JLabel totalnewnotification;
	private JLabel totalnewchatmessage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					if(DatabaseConnection.checkconnection())
					{
						
						InfoStudent s=new DataStudent().getStudentDetails("CE",1, 1001);
						MainStudent frame = new MainStudent(s);
					    frame.setVisible(true);
					}
					else
					{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						JOptionPane.showMessageDialog(null, "You Are Not Connected To DataBase","Error",JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainStudent(InfoStudent s) {
		
		
		ActionListener setActive=new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					int result=new DataStudent().setActiveStatus(s.getActiveStatus(), s.getUserId());
					if(result==0)
					{
						timer.stop();
						JOptionPane.showMessageDialog(null,"Your account is deleted by Admin","Account deleted",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					else
					{
						int notification=new DataNotification().getUnreadNotification(s.getUserId(), "Student", s.getCourceCode(), s.getSemorYear(),s.getAdmissionDate());
						if(notification>0)
						{
						totalnewnotification.setVisible(true);
						totalnewnotification.setText(notification>999?"999+":notification+"");
						totalnewnotification.setIcon(new ImageIcon(messagecount.getScaledInstance(24+totalnewnotification.getText().length(), 24, Image.SCALE_SMOOTH)));
						}
						int chat=new DataChat().getUndreadMessageCountStudent(s);
						if(chat>0)
						{
							totalnewchatmessage.setText(chat>999?"999+":chat+"");
							totalnewchatmessage.setVisible(true);
							totalnewchatmessage.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewchatmessage.getText().length(), 26, Image.SCALE_SMOOTH)));
						}
						else if(chat==0) 
						{
							totalnewchatmessage.setVisible(false);
						}
					}
			}
			
		};
		try
		{
			messagecount=ImageIO.read(new File("./assets/messagecount.png"));
		}
		catch(IOException exp)
		{
			exp.printStackTrace();
		}
		timer=new Timer(1000,setActive);
		timer.start();
		this.s=s;
		Color bgColor =new Color(32,178,170);
		Color frColor=Color.white;
		UIManager.put("ComboBoxUI", "com.sun.java.swing.plaf.windows.WindowsComboBoxUI");
		
	    UIManager.put("ComboBox.selectionBackground", new ColorUIResource(bgColor));
	    UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
	    UIManager.put("ComboBox.foreground", new ColorUIResource(Color.DARK_GRAY));
	    UIManager.put("ComboBox.selectionForeground", new ColorUIResource(frColor));
	    UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");
	  
		this.setResizable(false);
		setTitle("Collage Data Management");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setBounds(-2,0,1370,733);
		createpanel();
		JPanel sidebarpanel = new JPanel();
		sidebarpanel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(64, 64, 64)));
//		sidebarpanel.setBackground(new Color(255, 255, 255));
		sidebarpanel.setBackground(Color.DARK_GRAY);
		sidebarpanel.setBounds(5, 11, 240, 706);
		contentPane.add(sidebarpanel);
		sidebarpanel.setLayout(null);
		
		 profilepanel = new JPanel();
		 profilepanel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		profilepanel.setBackground(Color.DARK_GRAY);
		profilepanel.setBounds(0, 0, 240, 61);
		sidebarpanel.add(profilepanel);
		profilepanel.setLayout(null);
		
		studentnamelabel = new JLabel();
		studentnamelabel.setForeground(Color.WHITE);
		studentnamelabel.setHorizontalAlignment(SwingConstants.LEFT);
		studentnamelabel.setFont(new Font("Tw Cen MT", Font.BOLD, 21));
		studentnamelabel.setBackground(Color.DARK_GRAY);
		studentnamelabel.setOpaque(true);
		studentnamelabel.setBounds(65, 5, 171, 36);
		profilepanel.add(studentnamelabel);
		
		studentprofilepiclabel = new JLabel();
		studentprofilepiclabel.setBounds(5,0, 50, 50);
		profilepanel.add(studentprofilepiclabel);
		studentprofilepiclabel.setHorizontalAlignment(SwingConstants.CENTER);
		studentprofilepiclabel.setBackground(Color.DARK_GRAY);
		
		studentprofilepiclabel.setBorder(new LineBorder(Color.black,0));
		studentprofilepiclabel.setOpaque(true);
		
	
		
		
		homebutton = createButton("Home");
		sidebarpanel.add(homebutton);
		btn=homebutton;
		
		studentsbutton = createButton("Classmates","Students");
		sidebarpanel.add(studentsbutton);
		
		subjectbutton =createButton("Subjects");
		sidebarpanel.add(subjectbutton);
		
		faculitiesbutton = createButton("Faculities");
		sidebarpanel.add(faculitiesbutton);

		assignedsubjectbutton = createButton("Assigned Subject","Assign Subject");
		sidebarpanel.add(assignedsubjectbutton);
		
		marksheetbutton = createButton("Mark Sheet","Marksheet Report");
		sidebarpanel.add(marksheetbutton);
		
		attandancereportbutton = createButton("Attandance Report");
		sidebarpanel.add(attandancereportbutton);
		
		chatbutton = createButton("Chat");
		chatbutton.setLayout(new BorderLayout());
		sidebarpanel.add(chatbutton);
		int chat=new DataChat().getUndreadMessageCountStudent(s);
		totalnewchatmessage=new JLabel();
		totalnewchatmessage.setSize(60,30);
		totalnewchatmessage.setFont(new Font("Arial",Font.BOLD,12));
		totalnewchatmessage.setForeground(Color.white);
		totalnewchatmessage.setHorizontalTextPosition(JLabel.CENTER);
		totalnewchatmessage.setVerticalTextPosition(JLabel.CENTER);
		chatbutton.add(totalnewchatmessage,BorderLayout.LINE_END);
		System.out.println(chat);
		if(chat>0)
		{
			totalnewchatmessage.setText(chat>999?"999+":chat+"");
			totalnewchatmessage.setVisible(true);
			totalnewchatmessage.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewchatmessage.getText().length(), 26, Image.SCALE_SMOOTH)));
		}
		
		searchbutton = createButton("Search");
		sidebarpanel.add(searchbutton);
		
		notificationbutton = createButton("Notification");
		notificationbutton.setLayout(new BorderLayout());
		sidebarpanel.add(notificationbutton);
		
		int notification=new DataNotification().getUnreadNotification(s.getUserId(), "Student", s.getCourceCode(), s.getSemorYear(),s.getAdmissionDate());
		totalnewnotification=new JLabel();
		totalnewnotification.setSize(60,30);
		totalnewnotification.setFont(new Font("Arial",Font.BOLD,12));
		totalnewnotification.setForeground(Color.white);
		totalnewnotification.setHorizontalTextPosition(JLabel.CENTER);
		totalnewnotification.setVerticalTextPosition(JLabel.CENTER);
		notificationbutton.add(totalnewnotification,BorderLayout.LINE_END);
		
		if(notification>0)
		{
			totalnewnotification.setText(notification>999?"999+":notification+"");
			totalnewnotification.setVisible(true);
			totalnewnotification.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewnotification.getText().length(), 26, Image.SCALE_SMOOTH)));
		}
		
		
		myprofilebutton = createButton("My Profile","Profile");
		sidebarpanel.add(myprofilebutton);
		
		contactusbutton= createButton("Contact Us");
		sidebarpanel.add(contactusbutton);
		
		logoutbutton = createButton("logout");
		sidebarpanel.add(logoutbutton);

		exitbutton =createButton("Exit");
		sidebarpanel.add(exitbutton);
		
		activeButton(homebutton);
		
		homepanel.setVisible(true);
		
		this.setCollageDetails();
		lastlogin=s.getLastLogin();
		homepanel.setLastLogin(lastlogin);
		
		
		s.setLastLogin(TimeFunc.getCurrentTime());
		s.setActiveStatus(true);
		new DataStudent().updateStudentData(s, s);
	        this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(final WindowEvent windowenent) {
	            	openPanel(exitbutton);
	            }
	        });
	        
	        
		
	}
	public void createpanel()
	{
		
		homepanel=new PanelHome(s);
		homepanel.setLocation(panelx,panely);
		homepanel.setVisible(true);
		homepanel.setFocusable(true);
		contentPane.add(homepanel);
	
		
		
	}
	public void activeButton(JButton button)
	{
		btn.setBackground(buttonbcolor);
		btn.setForeground(buttonfcolor);
		btn.setFont(buttonfont);
		btn.setDisabledIcon(new ImageIcon(""));
		btn.setIcon(new ImageIcon("./assets/"+btn.getName()+"dac.png"));
		btn=button;
		btn.setForeground(Color.white);
		btn.setFont(new Font("Tw Cen MT", Font.BOLD, 23));
		btn.setIcon(new ImageIcon("./assets/"+btn.getName()+"ac.png"));
		disablepanel();
	}
	public JButton createButton(String text,String name)
	{
		JButton button=createButton(text);
		button.setName(name);
		button.setIcon(new ImageIcon("./assets/"+button.getName()+"dac.png"));
		return button;
	}
	public JButton createButton(String text)
	{
		JButton button=new JButton();
		button.setForeground(buttonfcolor);
		button.setFont(buttonfont);
		button.setBackground(buttonbcolor);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFocusable(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new EmptyBorder(0,0,0,0));
		button.setText(text);
		button.setName(text);
		button.setIcon(new ImageIcon("./assets/"+button.getName()+"dac.png"));
		button.addActionListener(this);
		button.setIconTextGap(10);
		button.setLocation(0, row);
		button.setSize(234, 40);
		row+=40;
		return button;
	}
	public void disablepanel()
	{
		if(homepanel!=null && homepanel.isVisible())
		{
			homepanel.setVisible(false);
		}
		else if(subjectpanel!=null&&subjectpanel.isVisible())
		{
			subjectpanel.setVisible(false);
		}
		else if(studentpanel!=null&&studentpanel.isVisible())
		{
			studentpanel.setVisible(false);
		}
		else if(viewstudentpanel!=null && viewstudentpanel.isVisible())
		{
			viewstudentpanel.setVisible(false);
		}
		else if(facultypanel!=null && facultypanel.isVisible())
		{
			facultypanel.setVisible(false);
		}
		else if(viewfacultypanel!=null&&viewfacultypanel.isVisible())
		{
			viewfacultypanel.setVisible(false);
		}
		else if(assignsubjectpanel!=null &&assignsubjectpanel.isVisible())
		{
			assignsubjectpanel.setVisible(false);
		}
		else if(entermarkspanelscroll!=null && entermarkspanelscroll.isVisible())
		{
			entermarkspanelscroll.setVisible(false);
		}
		else if(marksheetpanelscroll!=null&& marksheetpanelscroll.isVisible())
		{
			marksheetpanelscroll.setVisible(false);
		}
		else if(markattandancepanelscroll!=null && markattandancepanelscroll.isVisible())
		{
			markattandancepanelscroll.setVisible(false);
		}		
		else if(attandancereportpanelscroll!=null && attandancereportpanelscroll.isVisible())
		{
			attandancereportpanelscroll.setVisible(false);
		}
		else if(adminprofilepanel!=null && adminprofilepanel.isVisible())
		{
			adminprofilepanel.setVisible(false);
		}
		else if(searchpanel!=null && searchpanel.isVisible())
		{
			searchpanel.setVisible(false);
		}
		else if(notificationpanel!=null && notificationpanel.isVisible())
		{
			notificationpanel.setVisible(false);
		}
		else if(chatmainpanel!=null && chatmainpanel.isVisible())
		{
			try {
				if(chatmainpanel.chatpanel.subchatpanel!=null&&chatmainpanel.chatpanel.subchatpanel.socket!=null&&!chatmainpanel.chatpanel.subchatpanel.socket.isClosed())
				{
					chatmainpanel.chatpanel.subchatpanel.socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			chatmainpanel.setVisible(false);
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.openPanel(e.getSource());
	}
	

	public void setCollageDetails()
	{
		studentprofilepiclabel.setIcon(new ImageIcon(s.getRoundedProfilePic(50, 50, 50)));
		studentnamelabel.setText(s.getFullName());
		
	}
	public void openPanel(Object source) 
	{
		if(source==homebutton)
		{
			activeButton(homebutton);
			homepanel=new PanelHome(s);
			homepanel.setLocation(panelx, panely);
			homepanel.setFocusable(true);
			contentPane.add(homepanel);
			homepanel.setVisible(true);
			homepanel.setLastLogin(lastlogin);
		}
		
		else if(source==subjectbutton)
		{
			activeButton(subjectbutton);
			subjectpanel=new PanelSubject(this);
			subjectpanel.setLocation(panelx, panely);
			subjectpanel.setFocusable(true);
			contentPane.add(subjectpanel);
			subjectpanel.setVisible(true);
		}
		else if(source==studentsbutton)
		{
			activeButton(studentsbutton);
			studentpanel=new PanelStudent(this);
			studentpanel.setLocation(panelx,panely);
			studentpanel.setVisible(true);
			studentpanel.setFocusable(true);
			contentPane.add(studentpanel);
		}
		else if(source==faculitiesbutton)
		{
			
			activeButton(faculitiesbutton);
			facultypanel=new PanelFaculty(this);
			facultypanel.setLocation(panelx,panely);
			facultypanel.setVisible(true);
			facultypanel.setFocusable(true);
			contentPane.add(facultypanel);
			
		}
		else if(source==assignedsubjectbutton)
		{
			activeButton(assignedsubjectbutton);
			assignsubjectpanel=new PanelAssignSubject(this);
			assignsubjectpanel.setLocation(panelx,panely);
			assignsubjectpanel.setVisible(true);
			assignsubjectpanel.setFocusable(true);
			contentPane.add(assignsubjectpanel);
			
		}
		
		else if(source==marksheetbutton)
		{
				activeButton(marksheetbutton);
				marksheetpanel=new PanelMarkSheet(this,s);
				marksheetpanel.setVisible(true);
				marksheetpanelscroll=new JScrollPane(marksheetpanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				marksheetpanelscroll.getVerticalScrollBar().setUnitIncrement(16);
				marksheetpanelscroll.setBounds(panelx,panely, 1116, 705);
				contentPane.add(marksheetpanelscroll);
				marksheetpanelscroll.setVisible(true);
			
		}
		
		else if(source==attandancereportbutton)
		{
			activeButton(attandancereportbutton);
			attandancereportpanel=new PanelAttandanceReport(this);
			attandancereportpanel.setVisible(true);
			attandancereportpanelscroll=new JScrollPane(attandancereportpanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			attandancereportpanelscroll.setBounds(panelx, panely, 1116, 705);
			attandancereportpanelscroll.setVisible(true);
			attandancereportpanelscroll.setName("Attadance Report Panel Scroll");
			attandancereportpanelscroll.getVerticalScrollBar().setUnitIncrement(16);
			contentPane.add(attandancereportpanelscroll);
			for(Component c:attandancereportpanelscroll.getComponents())
			{
				c.setBackground(Color.white);
			}
		}
		else if(source==chatbutton)
		{
			activeButton(chatbutton);
			chatmainpanel=new PanelChatMain(this);
			chatmainpanel.setLocation(this.panelx, this.panely);
			chatmainpanel.setVisible(true);
			contentPane.add(chatmainpanel);
			
		}
		else if(source==searchbutton)
		{
			activeButton(searchbutton);
			searchpanel=new PanelSearch(this);
			searchpanel.setLocation(this.panelx, this.panely);
			searchpanel.setVisible(true);
			contentPane.add(searchpanel);
			
		}
		else if(source==notificationbutton)
		{
			activeButton(notificationbutton);
			if(totalnewnotification!=null && totalnewnotification.isVisible())
			{
				totalnewnotification.setVisible(false);
			}
			notificationpanel=new PanelNotification(this);
			notificationpanel.setLocation(panelx,panely);
			notificationpanel.setVisible(true);
			notificationpanel.setFocusable(true);
			contentPane.add(notificationpanel);
		}
		else if(source==myprofilebutton)
		{
			activeButton(myprofilebutton);
			viewstudentpanel=new PanelViewStudent(s,this);
			viewstudentpanel.setLocation(panelx,0);
			viewstudentpanel.setVisible(true);
			viewstudentpanel.setFocusable(true);
			
			contentPane.add(viewstudentpanel);
		}
		else if(source==contactusbutton)
		{
			activeButton(contactusbutton);
			adminprofilepanel=new PanelProfileAdmin();
			adminprofilepanel.setLocation(panelx,panely);
			adminprofilepanel.setVisible(true);
			adminprofilepanel.setFocusable(true);
			contentPane.add(adminprofilepanel);
		}
		else if(source==exitbutton)
		{
			int result=JOptionPane.showConfirmDialog(null,"Do you want to exit this application ?","Exit",JOptionPane.INFORMATION_MESSAGE);
			if(result==JOptionPane.YES_OPTION)
			{
				try {
					s.setActiveStatus(false);
					timer.stop();
	        		new DataStudent().setActiveStatus(false, s.getUserId());
					DatabaseConnection.closeConnection();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.disablepanel();
				System.exit(0);
			}
		}
		else if(source==logoutbutton)
		{
			int result=JOptionPane.showConfirmDialog(null,"Do you want to logout this application ?","Logout",JOptionPane.INFORMATION_MESSAGE);
			if(result==JOptionPane.YES_OPTION)
			{
        		s.setActiveStatus(false);
        		timer.stop();
        		new DataStudent().setActiveStatus(false, s.getUserId());
				SMSystem loginpageframe=new SMSystem();
				loginpageframe.setVisible(true);
				loginpageframe.setLocationRelativeTo(null);
				this.disablepanel();
				this.dispose();
			}
		}
		
	}
}