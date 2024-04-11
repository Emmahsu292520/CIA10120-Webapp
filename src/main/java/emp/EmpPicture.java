package emp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/EmpPicture")
public class EmpPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		try {
			Statement stmt = con.createStatement();
			String empno = req.getParameter("empno").trim();
			ResultSet rs = stmt.executeQuery(
					"SELECT  `image` FROM `emp` WHERE `empno` =" + empno);
			
			if(rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("image"));
				byte[] buf = new byte[4*1024];
				int len;
				while((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			}else {
//				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				InputStream in = getServletContext().getResourceAsStream("/NoData/none2.jpg");
				byte[] errorPic = new byte[in.available()];
				in.read(errorPic);
				out.write(errorPic);
				in.close();
			}
			rs.close();
			stmt.close();
		}catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/NoData/null.jpg");
			byte[] errorPic = in.readAllBytes();/* Java 9 的新方法，，InputStream 类在 Java 9 中新增了 readAllBytes() 方法，它用於將輸入流中的所有位元組讀取到位元組陣列中。這個方法會一次性讀取輸入流中的所有位元組，然後返回一個包含所有位元組的位元組陣列。
														在你的程式碼中，使用 in.readAllBytes() 方法讀取輸入流中的所有位元組，並將其存儲在位元組陣列 b 中。這個方法將輸入資料讀取並轉換成 byte[]，並確保 byte[] 的大小足以容納整個輸入資料。這樣，你可以確保讀取到的資料是完整的，並且在性能上也更為高效。*/
			out.write(errorPic);
		
		}
		

	}

	public void init() throws ServletException {

		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CIA10120");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
