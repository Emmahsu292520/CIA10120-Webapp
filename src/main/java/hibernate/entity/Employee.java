package hibernate.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="emp")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "EMPNO", updatable = false)
	private Integer empno;
	
	
//	@Column(name="positioniid")
//	private Integer positioniId;
	
	@Column(name="emp_name")
	private String empName;
	
	@Column(name="hire_date")
	private LocalDate hiredate;
	
	@Column(name="emp_state")
	private Boolean empState;
	
	@Column(name="emp_account")
	private Integer empAccount;
	
	@Column(name="emp_password")
	private String empPassword;
	
	@Column(name="image",  columnDefinition = "longblob")
	private byte[] image;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", referencedColumnName = "position_id")
	private Position position;

	public Employee() {
		super();
	}

	public Employee(Integer empno, String empName, LocalDate hiredate, Boolean empState, Integer empAccount,
			String empPassword, byte[] image, Position position) {
		super();
		this.empno = empno;
		this.empName = empName;
		this.hiredate = hiredate;
		this.empState = empState;
		this.empAccount = empAccount;
		this.empPassword = empPassword;
		this.image = image;
		this.position = position;
	}

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	
	@Override
	public String toString() {
		return "Employee [empno=" + empno +", positionName=" + position.getPositionName() +  ", empName=" + empName + ", hiredate=" + hiredate + ", empState=" + empState + ", empAccount=" + empAccount
				+ ", empPassword=" + empPassword + "]";
	}

	
	
	
	
}
