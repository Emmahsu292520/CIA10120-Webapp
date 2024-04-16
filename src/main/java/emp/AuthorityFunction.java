package emp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="authorityfunction")
public class AuthorityFunction {

	@Id
	@Column(name="functionid")
	Integer functionId;
	
	@Column(name="functionname")
	String functionName;
	
	
	
	public AuthorityFunction() {
		super();
	}



	public AuthorityFunction(Integer functionId, String functionName) {
		super();
		this.functionId = functionId;
		this.functionName = functionName;
	}



	public Integer getFunctionId() {
		return functionId;
	}



	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}



	public String getFunctionName() {
		return functionName;
	}



	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	
	
}
