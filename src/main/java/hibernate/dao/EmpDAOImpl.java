package hibernate.dao;

import static hibernate.util.Constants.PAGE_MAX_RESULT;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hibernate.entity.Employee;
import hibernate.entity.Position;
import hibernate.util.HibernateUtil;

public class EmpDAOImpl implements EmpDAO_interface {

	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;

	public EmpDAOImpl() {
		factory = HibernateUtil.getSessionFactory();
	}

	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public void insert(Employee entity) {
		Integer Id = (Integer) getSession().save(entity);
		entity.setEmpAccount(Id);
	}

	@Override
	public void update(Employee entity) {
		getSession().update(entity);

	}

	@Override
	public Employee findByPrimaryKey(Integer empnoId) {
		return getSession().get(Employee.class, empnoId);
	}

	@Override
	public List<Employee> getAll() {
		return getSession().createQuery("from Employee", Employee.class).list();

	}

	//複合查詢
	@Override
	public List<Employee> getByCompositeQuery(Map<String, String> map) {
		if (map.size() == 0)
			return getAll();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);	
		Root<Employee> root = criteria.from(Employee.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		
		
		// 員工編號查詢
	    if (map.containsKey("empno")) {
	        int empno = Integer.parseInt(map.get("empno"));
	        //root.get("empno") 來自資料庫的欄位   後面的empno是來自jsp裡面name標籤的value值
	        predicates.add(builder.equal(root.get("empno"), empno));
	    }

	    // 職位 ID 查詢
	    if (map.containsKey("positionId")) {
	        int positionId = Integer.parseInt(map.get("positionId"));
	        //root.get("position") 這邊的position是永續類別裡的實體變數名稱，再利用position永續類別取的positionId
	        predicates.add(builder.equal(root.get("position").get("positionId"), positionId));
	    }
		
	    // 員工名字模糊查詢
	    if (map.containsKey("ename")) {
	        String ename = map.get("ename");
	        predicates.add(builder.like(root.get("empName"), "%" + ename + "%"));
	    }
	    
	    
	    // 入職日期範圍查詢
	    if (map.containsKey("starthiredate") && map.containsKey("endhiredate")) {
	        LocalDate start = LocalDate.parse(map.get("starthiredate"));
	        LocalDate end = LocalDate.parse(map.get("endhiredate"));
	        predicates.add(builder.between(root.get("hiredate"), start, end));
	    }
	    
	    
	    // 員工狀態查詢
	    if (map.containsKey("empState")) {
	        Boolean empState = Boolean.parseBoolean(map.get("empState"));
	        predicates.add(builder.equal(root.get("empState"), empState));
	    }
	    
	    /*criteria.where() 方法用於設定 Criteria 查詢的 where 條件。
		builder.and() 方法是用來組合多個查詢條件（Predicate）的邏輯與（AND）操作。
		這允許將多個條件結合成一個條件表達式，只有當所有條件同時滿足時，記錄才會被選取。*/
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		criteria.orderBy(builder.asc(root.get("empno")));
		TypedQuery<Employee> query = getSession().createQuery(criteria);

		//getResultList() 方法執行查詢並返回符合條件的所有實體列表。在這個案例中，返回的是 Employee 類型的對象列表，這些對象代表數據庫中的員工紀錄。
		return query.getResultList();
	    
	}

	@Override
	public long getTotal() {
		return getSession().createQuery("select count(*) from Employee", Long.class).uniqueResult();
	}

	@Override
	public List<Employee> getAll(int currentPage) {
		int first = (currentPage - 1) * PAGE_MAX_RESULT;
		return getSession().createQuery("from Employee", Employee.class).setFirstResult(first)
				.setMaxResults(PAGE_MAX_RESULT).list();
	}

	// 使用byte[]方法，回傳 byte[]
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()]; // 這邊我選擇讀取的是我硬碟的圖片，已經檔案大小所以用available
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	
	public Position findPositionById(Integer positionId) {
        // 使用 Hibernate session 获取 Position 实例
        Session session = getSession();
        return session.get(Position.class, positionId);
    }

	@Override
	public List<Position> findAllPositions() {
		Session session = getSession();
		 // 使用createQuery創建查詢所有Position的HQL查詢
		List<Position> positions = session.createQuery("from Position", Position.class).list();
		return positions;
	}
	
	
	
	
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

//			    //新增
//			    try {
//			        session.beginTransaction();
//
//			        // 假設您已知想要指定的 Position 的 ID
//			        int desiredPositionId = 1;  // 這裡設定為 1，您可以根據實際情況更改
//
//			        Position position = session.get(Position.class, desiredPositionId);
//			        if (position == null) {
//			            System.out.println("No Position found with ID: " + desiredPositionId);
//			            return;
//			        }
//
//			        Employee employee = new Employee();
//			        employee.setEmpName("晚上十二點二十一");
//			        employee.setPosition(position);  // 設置 Employee 的 Position
//			        employee.setHiredate(LocalDate.of(2024, 4, 21));
//			        employee.setEmpState(true);
//			        employee.setEmpAccount(7011);
//			        employee.setEmpPassword("Password12345");
//			        employee.setImage(getPictureByteArray("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/11.jpg"));
//					
//			        
//			        
//			        EmpDAOImpl empdao1 = new EmpDAOImpl();
//			        empdao1.insert(employee);
//			        
//			        
//			        session.getTransaction().commit();
//			    } catch (Exception e) {
//			        if (session.getTransaction() != null) {
//			            session.getTransaction().rollback();
//			        }
//			        e.printStackTrace();
//			    } finally {
//			        session.close();
//			        sessionFactory.close();
//			    }
//
//		
//		//單一查詢
//		try {
//			session.beginTransaction();
//
//
//		        EmpDAOImpl empDao = new EmpDAOImpl();
//		        // 查詢編號為 7001 的員工
//		        Employee emp = empDao.findByPrimaryKey(7001);
//		        if (emp != null) {
//		            System.out.println("員工詳細信息: " + emp); // 打印員工詳細信息
//		            Position position = emp.getPosition();
//		            if (position != null) {
//		                System.out.println("職位名稱: " + position.getPositionName()); // 打印職位名稱
//
//		                // 訪問並打印每個職位權限及對應的功能名稱
//		                for (PositionAuthority authority : position.getAuthorities()) {
//		                    System.out.print("職位權限 - 職位ID: " + authority.getCompositeKey().getPositionId());
//		                    System.out.print(", 功能ID: " + authority.getCompositeKey().getFunctionId());
//		                    if (authority.getAuthorityFunction() != null) {
//		                        System.out.println(", 功能名稱: " + authority.getAuthorityFunction().getFunctionName());
//		                    } else {
//		                        System.out.println(", 功能名稱: 未知");
//		                    }
//		                }
//		            } else {
//		                System.out.println("該員工沒有指定職位");
//		            }
//		        } else {
//		            System.out.println("沒有找到編號為 7001 的員工");
//		        }
//
//		        session.getTransaction().commit();
//		    } catch (Exception e) {
//		        if (session.getTransaction() != null) {
//		            session.getTransaction().rollback();
//		        }
//		        e.printStackTrace();
//		    } finally {
//		        session.close();
//		        sessionFactory.close();
//		    }
//
//		
//		
//		//找尋Employee資料庫有幾筆資料
//		try {
//			session.beginTransaction();
//			EmpDAOImpl empDao = new EmpDAOImpl();
//			long count = empDao.getTotal();
//			System.out.println(count);
//			session.getTransaction().commit();
//			
//			
//			
//		}catch (Exception e) {
//	        if (session.getTransaction() != null) {
//            session.getTransaction().rollback();
//        }
//	        e.printStackTrace();
//		} finally {
//			session.close();
//			sessionFactory.close();
//		
//		}
		
	}




}
