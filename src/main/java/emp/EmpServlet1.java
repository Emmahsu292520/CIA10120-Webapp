package emp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import emp.*;

@WebServlet("/EmpServlet1")
public class EmpServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) { // 來自add.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/******** 1.接收請求參數-輸入格式錯誤處理 *********/
			String empName = req.getParameter("empName");
			String empNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (empName == null || empName.trim().length() == 0) {
				errorMsgs.add("員工姓名:請勿空白");
			} else if (!empName.trim().matches(empNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			LocalDate hiredate = null;
			try {
				hiredate = LocalDate.parse(req.getParameter("hiredate").trim());
			} catch (DateTimeParseException e) {
				hiredate = LocalDate.now(); // 如果日期格式錯誤，默認為當前日期
				errorMsgs.add("請選擇日期!"); // 添加錯誤信息
			}

//			String empAccountString = req.getParameter("empaccount");
//            if (empAccountString == null || empAccountString.trim().isEmpty()) {
//                errorMsgs.add("員工帳號:請勿空白");
//            } else {
//                if (!empAccountString.trim().matches("^[0-9]+$")) {
//                    errorMsgs.add("員工帳號:只能輸入數字");
//                }
//            }

			String empPassword = req.getParameter("empPassword");
			String passwordReg = "^[a-zA-Z0-9_]{6,20}$"; // 密码只包含字母、数字和下划线，长度为6到20位
			if (empPassword == null || empPassword.trim().isEmpty()) {
				errorMsgs.add("員工密碼:請勿空白");
			} else if (!empPassword.trim().matches(passwordReg)) {
				errorMsgs.add("員工密碼: 只能是字母、數字和_ , 且長度必需在6到20之間");
			}

			Integer positionId = Integer.parseInt(req.getParameter("positionId"));
			Boolean empState = Boolean.parseBoolean(req.getParameter("empState"));
			Integer empAccount = null;
			byte[] image = null;
			// 新增

			EmpVO empVO = new EmpVO();
			empVO.setPositionId(positionId);
			empVO.setEmpName(empName);
			empVO.setHiredate(hiredate);
			empVO.setEmpState(empState);
			empVO.setEmpPassword(empPassword);
			empVO.setImage(image);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("empVO", empVO);// 含有輸入格是錯誤的empVO物件，也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/add.jsp");
				failureView.forward(req, res);
				return;

			}

			/*************************** 2.開始新增資料 ***************************************/
			EmpService empSvc = new EmpService();
			empVO = empSvc.addEmp(positionId, empName, hiredate, empState, empAccount, empPassword, image);

			System.out.println("新增成功");

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);

		}

		// 修改
		if ("getOne_For_Update".equals(action)) {// 來自listAllEmp.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer empno = Integer.valueOf(req.getParameter("selectedEmp"));

			/*************************** 2.開始查詢資料 ****************************************/

			EmpService empSvc = new EmpService();
			EmpVO empVO = empSvc.getOneEmp(empno);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("empVO", empVO);
			String url = "update_emp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("update".equals(action)) {// 來自update_emp.jsp
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer empno = Integer.valueOf(req.getParameter("empno").trim());
			
			String empName = req.getParameter("empName");
			String empNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (empName == null || empName.trim().length() == 0) {
				errorMsgs.add("員工姓名:請勿空白");
			} else if (!empName.trim().matches(empNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			LocalDate hiredate = null;
			try {
				hiredate = LocalDate.parse(req.getParameter("hiredate").trim());
			} catch (DateTimeParseException e) {
				hiredate = LocalDate.now(); // 如果日期格式錯誤，默認為當前日期
				errorMsgs.add("請選擇日期!"); // 添加錯誤信息
			}

			String empPassword = req.getParameter("empPassword");
			String passwordReg = "^[a-zA-Z0-9_]{6,20}$"; // 密码只包含字母、数字和下划线，长度为6到20位
			if (empPassword == null || empPassword.trim().isEmpty()) {
				errorMsgs.add("員工密碼:請勿空白");
			} else if (!empPassword.trim().matches(passwordReg)) {
				errorMsgs.add("員工密碼: 只能是字母、數字和_ , 且長度必需在6到20之間");
			}
			
			
			// 獲取員工賬號為字符串
			String empAccountStr = req.getParameter("empAccount").trim();
			if (empAccountStr == null || empAccountStr.isEmpty()) {
			    errorMsgs.add("員工賬號:請勿空白");
			} else {
			    // 檢查字符串是否只包含數字
			    if (!empAccountStr.matches("^[0-9]+$")) {
			        errorMsgs.add("員工賬號:只能輸入數字");
			    }
			}

			// 然後，如果需要，您可以將字符串轉換為整數
			Integer empAccount = null;
			try {
				empAccount = Integer.valueOf(empAccountStr);
			} catch (NumberFormatException e) {
			    errorMsgs.add("員工賬號:格式錯誤");
			}
			

			Integer positionId = Integer.parseInt(req.getParameter("positionId"));
			Boolean empState = Boolean.parseBoolean(req.getParameter("empState"));
			byte[] image = null;

			EmpVO empVO = new EmpVO();
			empVO.setEmpno(empno);
			empVO.setPositionId(positionId);
			empVO.setEmpName(empName);
			empVO.setHiredate(hiredate);
			empVO.setEmpState(empState);
			empVO.setEmpAccount(empAccount);
			empVO.setEmpPassword(empPassword);
			empVO.setImage(image);
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/update_emp.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			/***************************2.開始修改資料*****************************************/
			EmpService empSvc = new EmpService();
			empSvc.updateEmp( empno,positionId, empName, hiredate, empState, empAccount, empPassword, image);
			System.out.println("修改完成");
			
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
			
		}
		
		

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			System.out.println("測試點1");
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String str = req.getParameter("empno");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入員工編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/selectpage1.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			Integer empno = null;
			try {
				empno = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("員工編號格式不正確");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/selectpage1.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			/***************************2.開始查詢資料*****************************************/
			EmpService empSvc = new EmpService();
			EmpVO empVO = empSvc.getOneEmp(empno);
			if (empVO == null) {
				errorMsgs.add("查無資料");
			}
		
			System.out.println("測試點2");
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/selectpage1.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			System.out.println("測試點3");
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
			String url = "/listOneEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
	
			
		}	
	}

}
