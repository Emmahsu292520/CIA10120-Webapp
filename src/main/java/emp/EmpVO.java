package emp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpVO {
	private Integer empno;
	private Integer positionid;
	private String empname;
	private LocalDate hiredate;
	private Boolean empstate;
	private Integer empaccount;
	private String emppassword;
	private byte[] image;
	
	
	
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Integer getPositionid() {
		return positionid;
	}
	public void setPositionid(Integer positionid) {
		this.positionid = positionid;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public LocalDate getHiredate() {
		return hiredate;
	}
	public void setHiredate(LocalDate hiredate) {
		this.hiredate = hiredate;
	}
	public Boolean getEmpstate() {
		return empstate;
	}
	public void setEmpstate(Boolean empstate) {
		this.empstate = empstate;
	}
	public Integer getEmpaccount() {
		return empaccount;
	}
	public void setEmpaccount(Integer empaccount) {
		this.empaccount = empaccount;
	}
	public String getEmppassword() {
		return emppassword;
	}
	public void setEmppassword(String emppassword) {
		this.emppassword = emppassword;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	public EmpVO() {
		super();
	}
	
	public EmpVO(Integer empno, Integer positionid, String empname, LocalDate hiredate, Boolean empstate,
			Integer empaccount, String emppassword, byte[] image) {
		super();
		this.empno = empno;
		this.positionid = positionid;
		this.empname = empname;
		this.hiredate = hiredate;
		this.empstate = empstate;
		this.empaccount = empaccount;
		this.emppassword = emppassword;
		this.image = image;
	}
	
	
	public EmpVO(Integer empno, Integer positionid, String empname, LocalDate hiredate, Boolean empstate) {
		this(empno, positionid, empname, hiredate,empstate, null, null, null);
	}
	
	

	

    
	@Override
	public String toString() {
	    String text = String.format("EmpNo: %d, PositionID: %d, EmpName: %s, HireDate: %s, EmpState: %s",
	            empno, positionid, empname, hiredate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), empstate);
	    return text;
	}
 
   
   
   

 
}
