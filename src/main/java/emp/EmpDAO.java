package emp;

import static common.Common.PASSWORD;
import static common.Common.URL;
import static common.Common.USER;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class EmpDAO implements EmpDAO_interface<EmpVO> {
	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> employees = new ArrayList<>();
		String sql = "SELECT `empno`,`positionid`,  `empname`, `hiredate`,`empstate`, `empaccount`, `emppassword`, `image` FROM `emp`;";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// rs.getString(2) 是根據目前 SELECT 查詢中指定的欄位順序而取得對應的值，而不是直接對應到資料庫的欄位名稱。

				Integer empno = rs.getInt(1);
				Integer positionid = rs.getInt(2);
				String empname = rs.getString(3);
				LocalDate hiredate = rs.getObject(4, LocalDate.class); // LocalDate.class 為了確保從資料庫中擷取的日期值可以被正確轉換為 Java
																		// 中的 LocalDate 物件。如果不指定類型，getObject 方法將返回一個通用的
																		// Object
				Boolean empstate = rs.getBoolean(5);
				Integer empaccount = rs.getInt(6);
				String emppassword = rs.getString(7);
				byte[] image = rs.getBytes(8);
				EmpVO empVO = new EmpVO(empno, positionid, empname, hiredate, empstate, empaccount, emppassword, image);
				employees.add(empVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public EmpVO findByPrimaryKey(Integer empnoId) {
		EmpVO empVO = null;
		String sql = "SELECT empno,positionid,empname,hiredate,empstate  FROM emp WHERE empno= ?;";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, empnoId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Integer empno = rs.getInt(1);
				Integer positionid = rs.getInt(2);
				String empname = rs.getString(3);
				LocalDate hiredate = rs.getObject(4, LocalDate.class);
				Boolean empstate = rs.getBoolean(5);
				empVO = new EmpVO(empno, positionid, empname, hiredate, empstate);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return empVO;
	}

	@Override
	public void insert(EmpVO empVO) {

		String sql = "INSERT INTO `emp` ( `positionid`,  `empname`, `hiredate`,`empstate`,`empaccount`, `emppassword`, `image`)VALUES( ?, ? ,?, ?, ?, ?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, empVO.getPositionid());
			ps.setString(2, empVO.getEmpname());
			ps.setObject(3, empVO.getHiredate());
			ps.setBoolean(4, empVO.getEmpstate());
			ps.	setInt(5, 1);  //先隨便給一個數 在後面修正回來
			ps.setString(6, empVO.getEmppassword());
			ps.setBytes(7, empVO.getImage());
			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					int empno = rs.getInt(1);
					ps.setInt(5, empno); // 设置 empaccount值為自動產生的 empno
					updateEmpAccount(empno);
				} else {
					throw new SQLException("Inserting employee failed, no ID obtained.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新数据库中的 empaccount
	private void updateEmpAccount(int empno) {
	    String sql = "UPDATE `emp` SET `empaccount` = ? WHERE `empno` = ?";
	    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setInt(1, empno);
	        ps.setInt(2, empno);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	@Override
	public void update(EmpVO empVO) {
		String sql = "UPDATE `emp` "
				+ "SET `positionid` = ?,  `empname`= ? , `hiredate`= ?, `empstate` = ?, `empaccount`= ?, `emppassword`= ?, `image` = ? "
				+ "WHERE `empno` = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, empVO.getPositionid());
			ps.setString(2, empVO.getEmpname());
			ps.setObject(3, empVO.getHiredate());
			ps.setBoolean(4, empVO.getEmpstate());
			ps.setInt(5, empVO.getEmpaccount());
			ps.setString(6, empVO.getEmppassword());
			ps.setBytes(7, empVO.getImage());
			ps.setInt(8, empVO.getEmpno());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void delete(Integer empnoId) {
		String sql = "DELETE FROM `emp` WHERE `empno`= ?;";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, empnoId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	// 使用byte[]方法，回傳 byte[]
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];  //這邊我選擇讀取的是我硬碟的圖片，已經檔案大小所以用available
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	
	
	
	
	public static void main(String[] args) {
		EmpDAO dao = new EmpDAO();

		// 新增
		EmpVO empVO1 = new EmpVO();
		empVO1.setPositionid(1);
		empVO1.setEmpname("許芳慈");
		empVO1.setHiredate(LocalDate.of(2024, 4, 6));
		empVO1.setEmpstate(true);
		empVO1.setEmppassword("Password10");
		try {
			empVO1.setImage(getPictureByteArray("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/12.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dao.insert(empVO1);
		System.out.println("新增成功");
		
		//修改
		EmpVO empVO2 = new EmpVO();

		empVO2.setPositionid(2);
		empVO2.setEmpname("許阿胖");
		empVO2.setHiredate(LocalDate.of(2024, 1, 15));
		empVO2.setEmpstate(false);
		empVO2.setEmpaccount(7050);
		empVO2.setEmppassword("Password1234");
		try {
			empVO2.setImage(getPictureByteArray("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/11.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		empVO2.setEmpno(7009);
		dao.update(empVO2);
		System.out.println("修改成功");
		
		
		// 刪除
		dao.delete(7012);
		System.out.println("刪除成功");
		
		// 查詢
		EmpVO empVO3 = dao.findByPrimaryKey(7001);
		System.out.println(empVO3);
		System.out.println("---------------------");
		
		
		// 查詢多筆
		List<EmpVO> employees = dao.getAll();
		for (EmpVO showAllEmployee : employees) {
			System.out.println(showAllEmployee);
		}
	}

}
