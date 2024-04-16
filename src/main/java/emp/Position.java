package emp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="position")
public class Position {
	
	@Column(name="positionid")
	Integer positionId;
	
	@Column(name="positionname")
	Integer positionName;

	
	
	public Position() {
		super();
	}

	public Position(Integer positionId, Integer positionName) {
		super();
		this.positionId = positionId;
		this.positionName = positionName;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getPositionName() {
		return positionName;
	}

	public void setPositionName(Integer positionName) {
		this.positionName = positionName;
	}

	
	
}
