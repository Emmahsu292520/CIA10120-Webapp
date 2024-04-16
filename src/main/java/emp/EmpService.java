package emp;

import java.time.LocalDate;
import java.util.List;

public class EmpService {
	
	private EmpDAO_interface<EmpVO> dao;
	
	public EmpService() {
		dao = new EmpJDBCDao(); 
	}
	
	//新增
	public EmpVO addEmp(Integer positionId, String empName, LocalDate hiredate, Boolean empState, Integer empAccount, String empPassword, byte[] image) {
		
		EmpVO empVO = new EmpVO();
		empVO.setPositionId(positionId);
		empVO.setEmpName(empName);
		empVO.setHiredate(hiredate);
		empVO.setEmpState(empState);
		empVO.setEmpAccount(empAccount);
		empVO.setEmpPassword(empPassword);
		empVO.setImage(image);
		
		dao.insert(empVO);
		return empVO;
	}
	
	
	//修改
	public EmpVO updateEmp(Integer empno, Integer positionId, String empName, LocalDate hiredate, Boolean empState, Integer empAccount, String empPassword, byte[] image) {
		
		EmpVO empVO = new EmpVO();
		empVO.setEmpno(empno);
		empVO.setPositionId(positionId);
		empVO.setEmpName(empName);
		empVO.setHiredate(hiredate);
		empVO.setEmpState(empState);
		empVO.setEmpAccount(empAccount);
		empVO.setEmpPassword(empPassword);
		empVO.setImage(image);
		
		dao.update(empVO);
		return empVO;
			
	}
	
	//刪除
	public void deleteEmp(Integer empnoId) {
		dao.delete(empnoId);
		
	}
	
	//查詢單筆資料
	public EmpVO getOneEmp(Integer empnoId) {
		return dao.findByPrimaryKey(empnoId);
	}
	
	public  List<EmpVO> getAll(){
		return dao.getAll();
	}
	

}
