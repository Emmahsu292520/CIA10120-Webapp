package hibernate.service;

import java.util.List;
import java.util.Map;

import hibernate.entity.Employee;
import hibernate.entity.Position;

public interface EmpService {
	
	Employee addEmp(Employee emp);
	
	Employee updateEmp(Employee emp);
	
	Employee getEmpByEmpno(Integer empno);
	
	List<Employee> getAllEmps(int currentPage);
	
	int getPageTotal();
	
	List<Employee> getEmpsByCompositeQuery(Map<String, String[]> map);
	
	 
    Position findPositionById(Integer positionId);
    
    List<Position> getAllPositions();
    
    public List<Employee> getAllEmps2();


}
