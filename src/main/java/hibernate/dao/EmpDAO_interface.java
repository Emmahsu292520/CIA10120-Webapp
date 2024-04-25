package hibernate.dao;

import java.util.List;
import java.util.Map;

import hibernate.entity.Employee;
import hibernate.entity.Position;

public interface EmpDAO_interface {
	
	
	public Employee findByPrimaryKey(Integer empnoId);
	public void insert(Employee entity);
	public void update(Employee entity);
	public List<Employee> getAll();
	public List<Employee> getAll(int currentPage);
	public List<Employee> getByCompositeQuery(Map<String, String> map);
	public long getTotal();
	public Position findPositionById(Integer positionId);
	public List<Position> findAllPositions();

}
