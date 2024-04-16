package emp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpVO {
	private Integer empno;
	private Integer positionId;
	private String empName;
	private LocalDate hiredate;
	private Boolean empState;
	private Integer empAccount;
	private String empPassword;
	private byte[] image;
	
	
	

	
	
	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public LocalDate getHiredate() {
		return hiredate;
	}

	public void setHiredate(LocalDate hiredate) {
		this.hiredate = hiredate;
	}

	public Boolean getEmpState() {
		return empState;
	}

	public void setEmpState(Boolean empState) {
		this.empState = empState;
	}

	public Integer getEmpAccount() {
		return empAccount;
	}

	public void setEmpAccount(Integer empAccount) {
		this.empAccount = empAccount;
	}

	public String getEmpPassword() {
		return empPassword;
	}

	public void setEmpPassword(String empPassword) {
		this.empPassword = empPassword;
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
	
	
	
	
public EmpVO(Integer empno, Integer positionId, String empName, LocalDate hiredate, Boolean empState,
			Integer empAccount, String empPassword, byte[] image) {
		super();
		this.empno = empno;
		this.positionId = positionId;
		this.empName = empName;
		this.hiredate = hiredate;
		this.empState = empState;
		this.empAccount = empAccount;
		this.empPassword = empPassword;
		this.image = image;
	}

//	public EmpVO(Integer empno, Integer positionid, String empname, LocalDate hiredate, Boolean empstate,
//			Integer empaccount, String emppassword, byte[] image) {
//		super();
//		this.empno = empno;
//		this.positionid = positionid;
//		this.empname = empname;
//		this.hiredate = hiredate;
//		this.empstate = empstate;
//		this.empaccount = empaccount;
//		this.emppassword = emppassword;
//		this.image = image;
//	}
	

	

    
	@Override
	public String toString() {
	    // 注意安全风险，避免在生产环境中打印敏感信息如密码
	    return String.format("EmpNo: %d, PositionID: %d, EmpName: %s, HireDate: %s, EmpState: %s, EmpAccount: %d, EmpPassword: %s",
	        empno, positionId, empName, 
	        (hiredate != null) ? hiredate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A", 
	        empState, empAccount, empPassword);
	}
 
   
 
   

 
}
