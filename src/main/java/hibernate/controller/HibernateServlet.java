package hibernate.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import emp.EmpVO;
import hibernate.entity.Employee;
import hibernate.entity.Position;
import hibernate.service.EmpService;
import hibernate.service.EmpServiceImpl;

@WebServlet("/HibernateServlet")
@MultipartConfig(fileSizeThreshold = 0 * 1024 * 1024, maxFileSize = 1 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class HibernateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 一個 servlet 實體對應一個 service 實體
	private EmpService empService;

	@Override
	public void init() throws ServletException {
		empService = new EmpServiceImpl();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		List<String> errors = new ArrayList<>(); // 用來收集錯誤信息的列表
		req.setAttribute("errors", errors); // 把錯誤信息列表放到請求屬性中
		
		 //獲取職位
	    List<Position> positions = empService.getAllPositions();
	    req.setAttribute("positions", positions);

		String action = req.getParameter("action");
		String forwardPath = "";
		switch (action) {
//		case "getAll":
//			forwardPath = getAllEmps(req, res);
//			break;
		case "compositeQuery":
			validateCompositeQuery(req, errors);
			if (!errors.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/hibernateIndex.jsp");
				failureView.forward(req, res);
				return;
			}

			forwardPath = getCompositeEmpsQuery(req, res);
			break;
		case "insert":
			insertEmp(req, res); // 處理新增員工
            return; // 因為 insertEmp 方法中已經處理了轉發，所以直接返回
            
		case "update":    
			forwardPath = updateEmp(req, res); // 選擇要修改的員工，傳送到修改員工的畫面
			break; // 因為 updateEmp 方法中已經處理了轉發，所以直接返回
            
		case "updateNow":  	
			forwardPath = updateEmpNow(req, res); 
			break;
			
		default:
			forwardPath = "/hibernateIndex.jsp";
		}

		res.setContentType("text/html; charset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);

	}

	private String getCompositeEmpsQuery(HttpServletRequest req, HttpServletResponse res) {
		Map<String, String[]> map = req.getParameterMap();

		List<Employee> empList = empService.getEmpsByCompositeQuery(map);
		if (empList.isEmpty()) {
			// 如果查詢結果為空，設定錯誤訊息
			req.setAttribute("errors", List.of("查無資料"));
			return "/hibernateIndex.jsp";
		} else {
			// 如果有查詢結果，設定查詢結果並轉發到結果頁面
			req.setAttribute("empList", empList);
			return "/hibernateListCompositeQueryEmps.jsp";
		}
	}

	private void validateCompositeQuery(HttpServletRequest req, List<String> errors) {
		String empno = req.getParameter("empno");
		String ename = req.getParameter("ename");

		// 驗證員工編號：可以是空值或全是數字
		if (empno != null && !empno.isEmpty() && !empno.matches("\\d+")) {
			errors.add("員工編號必須是數字！");
		}

		// 驗證員工名字：可以是空值或英文大小寫和中文
		if (ename != null && !ename.isEmpty() && !ename.matches("^[a-zA-Z\\u4e00-\\u9fa5]+$")) {
			errors.add("員工名字只能包含英文大小寫和中文！");
		}
	}

	private void insertEmp(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    List<String> errorMsgs = new LinkedList<>();
	    req.setAttribute("errorMsgs", errorMsgs);

	    Employee newEmp = new Employee();  // 在這裡創建一個Employee對象，用於收集和驗證數據

	    try {
	        Integer positionId = Integer.parseInt(req.getParameter("positionId"));
	        Position position = empService.findPositionById(positionId);

	        String empName = req.getParameter("empName");
	        String empNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
	        if (empName == null || empName.trim().isEmpty()) {
	            errorMsgs.add("員工姓名: 請勿空白");
	        } else if (!empName.matches(empNameReg)) {
	            errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_，且長度必需在2到10之間");
	        }

	        String dateInput = req.getParameter("hiredate").trim();
	        LocalDate hiredate = null;
	        if (dateInput == null || dateInput.isEmpty()) {
	            errorMsgs.add("入職日期: 請勿空白");
	        } else {
	            try {
	                hiredate = LocalDate.parse(dateInput);
	            } catch (DateTimeParseException e) {
	                errorMsgs.add("入職日期格式錯誤");
	            }
	        }

	        Boolean empState = Boolean.parseBoolean(req.getParameter("empState"));
	        String empPassword = req.getParameter("empPassword");
	        String passwordReg = "^[a-zA-Z0-9_]{6,20}$";
	        if (empPassword == null || empPassword.trim().isEmpty()) {
	            errorMsgs.add("員工密碼: 請勿空白");
	        } else if (!empPassword.matches(passwordReg)) {
	            errorMsgs.add("員工密碼: 只能是字母、數字和_，且長度必需在6到20之間");
	        }

	        InputStream in = req.getPart("image").getInputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
	        int len;
	        while ((len = in.read(buffer)) != -1) {
	            baos.write(buffer, 0, len);
	        }
	        byte[] image = baos.toByteArray();
	        in.close();

	        newEmp.setPosition(position);  // 這些設置應該在檢查完所有錯誤後進行
	        newEmp.setEmpName(empName);
	        newEmp.setHiredate(hiredate);
	        newEmp.setEmpState(empState);
	        newEmp.setEmpPassword(empPassword);
	        newEmp.setImage(image);

	        if (errorMsgs.isEmpty()) {
	            empService.addEmp(newEmp);
	            req.setAttribute("newEmp", newEmp);
	            RequestDispatcher successView = req.getRequestDispatcher("/hibernateListAllEmp.jsp");
	            successView.forward(req, res);
	        } else {
	            req.setAttribute("newEmp", newEmp); // 這裡將含錯誤的對象放回請求屬性中
	            RequestDispatcher failureView = req.getRequestDispatcher("/hibernateAdd.jsp");
	            failureView.forward(req, res);
	        }
	    } catch (Exception e) {
	        errorMsgs.add("處理過程中發生錯誤: " + e.getMessage());
	        req.setAttribute("newEmp", newEmp); // 同上，確保錯誤信息和對象數據都被正確地返回
	        RequestDispatcher failureView = req.getRequestDispatcher("/hibernateAdd.jsp");
	        failureView.forward(req, res);
	    }
	}


	
	
	//傳送想要修改的參數給hibernateUpdate.jsp
	private String updateEmp(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    List<String> errorMsgs = new LinkedList<>();
	    req.setAttribute("errorMsgs", errorMsgs);

//	    Employee updatedEmp = new Employee();  // 創建一個Employee對象，用於收集和驗證數據

	    /*************************** 1.接收請求參數 ****************************************/
		Integer empno = Integer.valueOf(req.getParameter("selectedEmp"));
		
		/*************************** 2.開始查詢資料 ****************************************/

		EmpService empSvc = new EmpServiceImpl();
		Employee updatedEmp = empSvc.getEmpByEmpno(empno);
		/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
		req.setAttribute("updatedEmp", updatedEmp);
//		String url = "update_emp.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url);
//		successView.forward(req, res);
		return "/hibernateUpdate.jsp";
	    
	}
	
	
	private String updateEmpNow(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
		// 從請求中獲取員工編號
	    Integer empno = Integer.valueOf(req.getParameter("empno").trim());
	    
	    // 從數據庫中查詢此員工
	    EmpService empService = new EmpServiceImpl();
	    Employee updatedEmp = empService.getEmpByEmpno(empno);
	    
	    
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

		Integer empAccount = null;
		try {
			empAccount = Integer.valueOf(empAccountStr);
		} catch (NumberFormatException e) {
			errorMsgs.add("員工賬號:格式錯誤");
		}

		// 照片修改
		InputStream in = req.getPart("image").getInputStream();
		byte[] image;
		if (in != null && in.available() > 0) { // 檢查InputStream是否有可用數據
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int len;
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			in.close(); // 關閉 InputStream
			image = baos.toByteArray(); // 將 ByteArrayOutputStream 轉換為 byte 陣列
		} else {
			EmpService empSvc = new EmpServiceImpl();
			image = empSvc.getEmpByEmpno(empno).getImage(); // 如果沒有上傳新圖片，則保留原圖片
		}

		Integer positionId = Integer.parseInt(req.getParameter("positionId"));
		Position position = empService.findPositionById(positionId);
		Boolean empState = Boolean.parseBoolean(req.getParameter("empState"));



		updatedEmp.setEmpno(empno);
		updatedEmp.setPosition(position);
		updatedEmp.setEmpName(empName);
		updatedEmp.setHiredate(hiredate);
		updatedEmp.setEmpState(empState);
		updatedEmp.setEmpAccount(empAccount);
		updatedEmp.setEmpPassword(empPassword);
		updatedEmp.setImage(image);

		if (!errorMsgs.isEmpty()) {
			req.setAttribute("updatedEmp", updatedEmp); // 含有輸入格式錯誤的empVO物件,也存入req
			RequestDispatcher failureView = req.getRequestDispatcher("/hibernateUpdate.jsp");
			failureView.forward(req, res);
			
		}
		/*************************** 2.開始修改資料 *****************************************/
		EmpService empSvc = new EmpServiceImpl();
		empSvc.updateEmp(updatedEmp);
		System.out.println("修改完成");

		/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
		req.setAttribute("updatedEmp", updatedEmp); // 資料庫update成功後,正確的的empVO物件,存入req
		String url = "/hibernateListAllEmp.jsp";
		
		return url;
	}
	
	


	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    req.setCharacterEncoding("UTF-8");

	    doPost(req, res);
	}


}
