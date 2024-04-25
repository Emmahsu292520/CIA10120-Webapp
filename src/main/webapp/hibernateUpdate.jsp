<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="hibernate.entity.*"%>
<%@ page import="hibernate.service.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>

<%
// 透過service去資料庫撈資料創建下拉式選單
EmpServiceImpl empService = new EmpServiceImpl();
List<Position> positions = empService.getAllPositions();

// 保留用戶輸入的資料
List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");
Employee updatedEmp = (Employee) request.getAttribute("updatedEmp");
Integer selectedPositionId = updatedEmp != null && updatedEmp.getPosition() != null
		? updatedEmp.getPosition().getPositionId()
		: null;
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">
<title>修改員工資料</title>

<%-- <%@ include file="/backend/htmlfile/css.html" %> --%>

<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;
}

.form-header {
    background-color: #5bc0de;
    color: white;
    padding: 20px 10px;
    text-align: center;
    font-size: 24px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    margin-bottom: 30px;
}

form {
    max-width: 400px;
    margin: 20px auto;
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.input-group, .form-group {
    margin-bottom: 20px;
}

.input-group label, .form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

.input-group select {
    width: calc(100% - 0px);
    padding: 7px 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.input-group input[type="text"], 
.input-group input[type="date"],
.input-group input[type="password"], 
.input-group input[type="file"],
.form-group span {
    width: calc(100% - 28px);
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.password-container {
    display: flex;
    align-items: center;
    position: relative;
}

input[type="password"] {
    flex-grow: 1;
    padding-right: 30px;
}

.toggle-password {
    cursor: pointer;
    position: absolute;
    right: 10px;
}

input[type="submit"] {
    background-color: #5bc0de;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

input[type="submit"]:hover {
    background-color: #31b0d5;
}

.form-group {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 10px;
}

.empno-value {
    padding: 8px 10px;
    background-color: #e9ecef;
    border-radius: 4px;
    display: flex;
    align-items: center;
    width: calc(100% - 20px);
    height: 22px;
    margin-bottom: 10px;
    text-align: left;
}

.input-group input[type="text"] {
    width: calc(100% - 16px);
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.input-group input[type="date"] {
    width: calc(100% - 16px);
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.input-group input[type="file"] {
    width: calc(100% - 16px); /* 調整這裡的寬度 */
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}




</style>








</head>
<body>

<%-- 	<%@ include file="/backend/htmlfile/content1.html" %> --%>
	
<!-- 	<div class="container-fluid pt-4 px-4"> -->
<!--     <div class="bg-light rounded "> -->
<!--     <div class="G3_content"> -->

	<%
	if (request.getAttribute("errorMsgs") != null) {
	%>
	<div style="color: red;">
		<ul>
			<%for (String errorMsg : (List<String>) request.getAttribute("errorMsgs")) {%>
			<li><%=errorMsg%></li>
			<%}%>
		</ul>
	</div>
	<%}%>

	<form action="${pageContext.request.contextPath}/HibernateServlet"
		method="post" enctype="multipart/form-data">
		<div class="form-header">修改員工資料</div>

		<div class="input-group">
			<label for="empno">* 員工編號:</label>
			<div class="empno-container">
				<span class="empno-value"><%=updatedEmp != null ? updatedEmp.getEmpno() : "N/A"%></span>
			</div>
			<input type="hidden" name="empno"
				value="<%=updatedEmp != null ? updatedEmp.getEmpno() : ""%>"
				style="width: calc(100% - 28px); height: 40px; border: 1px solid #5bc0de; border-radius: 4px;">
		</div>





		<div class="input-group">
			<label for="positionId">* 職位:</label> 
			<select id="positionId" name="positionId">
				<%
				for (Position position : positions) {
				%>

				<%
				boolean isSelected = (selectedPositionId != null && position.getPositionId().equals(selectedPositionId));
				%>

				<option value="<%=position.getPositionId()%>"
					<%=isSelected ? "selected" : ""%>>
					<%=position.getPositionName()%>
				</option>
				<%
				}
				%>
			</select>
		</div>

		<div class="input-group">
			<label for="empName">* 員工姓名:</label> 
			<input type="text" id="empName" name="empName" value="<%=updatedEmp != null ? updatedEmp.getEmpName() : ""%>">
		</div>

		<div class="input-group">
			<label for="hiredate">* 入職日期:</label> 
			<input type="date"	id="hiredate" name="hiredate"	value="<%=updatedEmp != null && updatedEmp.getHiredate() != null
		? updatedEmp.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		: ""%>">
		</div>

		<div class="input-group">
			<label for="empState">* 員工狀態:</label> <select id="empState"
				name="empState">
				<option value="true"
					<%=updatedEmp != null && updatedEmp.getEmpState() != null && updatedEmp.getEmpState() ? "selected" : ""%>>在職</option>
				<option value="false"
					<%=updatedEmp != null && updatedEmp.getEmpState() != null && !updatedEmp.getEmpState() ? "selected" : ""%>>離職</option>
			</select>
		</div>

		<div class="input-group">
			<label for="empAccount">* 員工帳號:</label> <input type="text"
				id="empAccount" name="empAccount"
				value="<%=updatedEmp != null && updatedEmp.getEmpAccount() != null ? updatedEmp.getEmpAccount() : ""%>">
		</div>

		<div class="input-group">
			<label for="empPassword">* 員工密碼:</label>
			<div class="password-container">
				<input type="password" id="empPassword" name="empPassword"
					value="<%=updatedEmp != null ? updatedEmp.getEmpPassword() : ""%>">
				<i class="fa-solid fa-eye toggle-password"
					onclick="togglePasswordVisibility()"></i>
			</div>
		</div>

		<div class="input-group">
			<label for="image">* 員工照片:</label> <input type="file" id="image"
				name="image">
		</div>

		<input type="hidden" name="action" value="updateNow"> <input
			type="submit" value="更新">
	</form>

	<script>
		function togglePasswordVisibility() {
			var passwordInput = document.getElementById("empPassword");
			var icon = document.querySelector('.toggle-password');
			var isPasswordVisible = passwordInput.type === 'password';

			if (isPasswordVisible) {
				passwordInput.type = 'text';
				icon.classList.remove('fa-eye');
				icon.classList.add('fa-eye-low-vision');
			} else {
				passwordInput.type = 'password';
				icon.classList.remove('fa-eye-low-vision');
				icon.classList.add('fa-eye');
			}
		}
	</script>

<!--  	</div> -->
<!--     </div> -->
<!--     </div> -->


<%-- <%@ include file="/backend/htmlfile/content2.html" %> --%>
<%-- <%@ include file="/backend/htmlfile/script.html" %> --%>

</body>
</html>
