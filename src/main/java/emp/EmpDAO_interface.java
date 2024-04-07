package emp;

import java.util.*;



public interface EmpDAO_interface<T> {
		
		public List<T> getAll();
		public T findByPrimaryKey(Integer empnoId);
		public void insert(T obj);
		public void update(T obj);
		public void delete(Integer empnoId);
		
		
}

//public void insert(EmpVO empVO);
//public void update(EmpVO empVO);
//public void delete(Integer empno);
//public EmpVO findByPrimaryKey(Integer empno);
//public List<EmpVO> getAll();