package smsystem.faculty;

import smsystem.general.Person;
import smsystem.general.TimeFunc;
import smsystem.course.DataCourse;

/*
 * Title : Faculty.java
 * Purpose : Binding all the of faculty
 */

public class InfoFaculty extends  Person
{
	
	private int facultyid;
	private String facultyname;
	private String qualification;
	private String experience;
	private String subject;
	private String position;
	private String joineddate;
	
	public void setJoinedDate(String joineddate)
	{
		this.joineddate=joineddate;
	}
	public void setFacultyId(int facultyid)
	{
		this.facultyid=facultyid;
	}
	public void setFacultyName(String facultyname)
	{
		this.facultyname=facultyname;
	}
	public void setQualification(String qualification)
	{
		this.qualification=qualification;
	}
	public void setExperience(String experience)
	{
		this.experience=experience;
	}
	public void setSubject(String subject)
	{
		this.subject=subject;
	}
	public void setPosition(String position)
	{
		this.position=position;
	}
	public String getCourceName()
	{
		return new DataCourse().getcourcename(this.getCourceCode());
	}
	public String getFacultyName()
	{
		return facultyname;
	}
	
	public int getFacultyId()
	{
		return facultyid;
	}
	
	public String getQualification()
	{
		return qualification;
	}
	public String getPosition()
	{
		return position;
	}
	public String getExperience()
	{
		return experience;
	}
	public String getSubject()
	{
		return subject;
	}
	public String getJoinedDate()
	{
		return joineddate;
	}
	public String generateJoinedDate()
	{
		joineddate=TimeFunc.getCurrentTime();
		return joineddate;
	}
	
}