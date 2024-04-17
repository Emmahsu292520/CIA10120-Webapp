package emp;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Picture")
public class Picture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
   

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	doPost(req, res);
	}
    
    
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		res.setContentType("image/png");
		ServletOutputStream out = res.getOutputStream();
		
		try {
            Integer empno = Integer.valueOf(req.getParameter("empno"));
            EmpService empSvc = new EmpService();
            byte[] image = empSvc.getOneEmp(empno).getImage();
            
            if (image == null || image.length == 0) {
                throw new Exception("No image found");
            }
            out.write(image);
        } catch (Exception e) {
            InputStream in = getServletContext().getResourceAsStream("/no.png");
            if (in == null) {
                System.out.println("Fallback image not found");
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            byte[] buf = new byte[1024]; // Use a buffer of 1024 bytes for reading
            int bytesRead;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            in.close();
        }
    }

}
