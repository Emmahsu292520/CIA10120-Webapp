package emp;

import java.time.LocalDate;
import java.util.List;

public class EmpService {
	
	private EmpDAO_interface<EmpVO> dao;
	
	public EmpService() {
		dao = new EmpJDBCDao(); 
	}
	
	//新增
	public EmpVO addEmp(Integer positionid, String empname, LocalDate hiredate, Boolean empstate, Integer empaccount, String emppassword, byte[] image) {
		
		EmpVO empVO = new EmpVO();
		empVO.setPositionid(positionid);
		empVO.setEmpname(empname);
		empVO.setHiredate(hiredate);
		empVO.setEmpstate(empstate);
		empVO.setEmpaccount(empaccount);
		empVO.setEmppassword(emppassword);
		empVO.setImage(image);
		
		dao.insert(empVO);
		return empVO;
	}
	
	
	//修改
	public EmpVO updateEmp(Integer empno, Integer positionid, String empname, LocalDate hiredate, Boolean empstate, Integer empaccount, String emppassword, byte[] image) {
		
		EmpVO empVO = new EmpVO();
		empVO.setEmpno(empno);
		empVO.setPositionid(positionid);
		empVO.setEmpname(empname);
		empVO.setHiredate(hiredate);
		empVO.setEmpstate(empstate);
		empVO.setEmpaccount(empaccount);
		empVO.setEmppassword(emppassword);
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
