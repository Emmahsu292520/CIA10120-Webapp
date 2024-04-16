package emp;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="emp")
public class Employee {
	@Id
	@Column(name= "EMPNO")
	private Integer empno;
	
	
	@Column(name="positioniid")
	private Integer positioniId;
	
	@Column(name="empname")
	private String empName;
	
	@Column(name="hiredate")
	private LocalDate hiredate;
	
	@Column(name="empstate")
	private Boolean empState;
	
	@Column(name="empaccount")
	private Integer empAccount;
	
	@Column(name="empassword")
	private String empPassword;
	
	@Column(name="image")
	private byte[] image;
	
	
	
	
	
	public Employee() {
		super();
	}
	
	
	public Employee(Integer empno, Integer positioniId, String empName, LocalDate hiredate, Boolean empState,
			Integer empAccount, String empPassword, byte[] image) {
		super();
		this.empno = empno;
		this.positioniId = positioniId;
		this.empName = empName;
		this.hiredate = hiredate;
		this.empState = empState;
		this.empAccount = empAccount;
		this.empPassword = empPassword;
		this.image = image;
	}


	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Integer getPositioniId() {
		return positioniId;
	}
	public void setPositioniId(Integer positioniId) {
		this.positioniId = positioniId;
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

}
