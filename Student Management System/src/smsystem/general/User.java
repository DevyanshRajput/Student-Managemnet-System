package smsystem.general;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import smsystem.course.InfoCourse;
import smsystem.faculty.DataFaculty;
import smsystem.student.DataStudent;


/*
 * Title : User.java
 * Purpose : Binding data related to login activity of students and faculties
 */


class User extends InfoCourse
{
	
	
	private String logindate;
	private String logintime;
	private String userid;
	private String userprofile;
	
	public void setLoginTime(String logintime)
	{
		SimpleDateFormat timeformatter=new SimpleDateFormat("hh:mm aaa");
		SimpleDateFormat dateformatter=new SimpleDateFormat("dd-MMM,yyyy");
		SimpleDateFormat formater=new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa");
		Date date;
		try {
			date = formater.parse(logintime);
			this.logintime=timeformatter.format(date);
			this.logindate=dateformatter.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setUserId(String userid)
	{
		this.userid=userid;
	}
	public void  setUserProfile(String userprofile)
	{
		this.userprofile=userprofile;
	}
	public String getLoginTime()
	{
		return logintime;
	}
	public String getLoginDate()
	{
		if(getCurrentDate().equals(logindate))
		{
			return "today";
		}
		
		return logindate;
	}
	public String getCurrentDate()
	{
		Date date=new Date();
		SimpleDateFormat dateformatter=new SimpleDateFormat("dd-MMM,yyyy");
		String time=dateformatter.format(date);
		return time;
	}
	public String getUserid()
	{
		return userid;
	}
	public String getUserProfile() 
	{
		return userprofile;
	}
	
	public String getName() 
	{
		if(getUserProfile().equals("Student"))
		{
			return new DataStudent().getStudentName(userid);
		}
		else if(getUserProfile().equals("Faculty"))
		{
				return new DataFaculty().getFacultyName(userid);
		}
		return "-";
	}

}