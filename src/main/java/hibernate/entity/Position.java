package hibernate.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="position")
public class Position {
	
	@Id
	@Column(name="position_id")
	private Integer positionId;
	
	@Column(name="position_name")
	private String  positionName;

	
	@OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
	@OrderBy("empno asc")  //資料庫的欄位名稱
	private Set<Employee> emps;


	@OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
	@OrderBy("position_id asc")
	private Set<PositionAuthority> positionAuthority;
	
	
	public Position() {
		super();
	}


	public Position(Integer positionId, String positionName, Set<Employee> emps, Set<PositionAuthority> positionAuthority) {
		super();
		this.positionId = positionId;
		this.positionName = positionName;
		this.emps = emps;
		this.positionAuthority = positionAuthority;
	}


	public Integer getPositionId() {
		return positionId;
	}


	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}


	public String getPositionName() {
		return positionName;
	}


	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}


	public Set<Employee> getEmps() {
		return emps;
	}


	public void setEmps(Set<Employee> emps) {
		this.emps = emps;
	}


	public Set<PositionAuthority> getAuthorities() {
		return positionAuthority;
	}


	public void setAuthorities(Set<PositionAuthority> positionAuthority) {
		this.positionAuthority = positionAuthority;
	}



	
	


	
	
}
