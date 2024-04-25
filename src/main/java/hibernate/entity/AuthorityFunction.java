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
@Table(name="authority_function")
public class AuthorityFunction {

    @Id
    @Column(name="function_id")
    private Integer functionId;
    
    @Column(name="function_name")
    private String functionName;
    
    @OneToMany(mappedBy = "authorityFunction", cascade = CascadeType.ALL)
    @OrderBy("positionId asc")
    private Set<PositionAuthority> positionAuthorities;

    public AuthorityFunction() {
        super();
    }

    public AuthorityFunction(Integer functionId, String functionName, Set<PositionAuthority> positionAuthorities) {
        super();
        this.functionId = functionId;
        this.functionName = functionName;
        this.positionAuthorities = positionAuthorities;
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

    public Set<PositionAuthority> getPositionAuthorities() {
        return positionAuthorities;
    }

    public void setPositionAuthorities(Set<PositionAuthority> positionAuthorities) {
        this.positionAuthorities = positionAuthorities;
    }
}
