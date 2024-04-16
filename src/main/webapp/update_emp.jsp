<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="emp.*"%>
<%@ page import="java.util.List"%>
<!-- 確保加入了這一行 -->
<%@ page import="java.time.format.DateTimeFormatter"%>


<%
List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");
%>

<%-- 保留用戶輸入的資料 --%>
<%
EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">


<title>修改員工資料</title>
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
	padding: 20px 10px; /* 增加了上下的 padding 來提供更多空間 */
	text-align: center;
	font-size: 24px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	margin-bottom: 30px; /* 新增底部間距，使標題與下方內容有更多空間 */
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
	margin-bottom: 20px; /* 增加了間距使得表單元素之間不會太擠 */
}

.input-group label, .form-group label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
}

.input-group input[type="text"], .input-group input[type="date"],
	.input-group select, .input-group input[type="password"], .input-group input[type="file"],
	.form-group span {
	width: 100%; /* 保持寬度不變 */
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
	padding-right: 30px; /* 保留眼睛圖標的空間 */
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

/* 針對員工編號的行直接展示 */
.form-group {
	display: flex;
	justify-content: flex-start;
	align-items: center;
	gap: 10px; /* 增加了標籤和值之間的間隙 */
}

.empno-value {
	padding-left: 10px;
	background-color: #e9ecef;
	border-radius: 4px;
	display: inline-block;
	max-width: calc(100% - 120px);
}
</style>
</head>
</head>
<body>

	<% if (request.getAttribute("errorMsgs") != null) { %>
    <div style="color: red;">
        <ul>
            <% for(String errorMsg : (List<String>)request.getAttribute("errorMsgs")) { %>
                <li><%= errorMsg %></li>
            <% } %>
        </ul>
    </div>
<% } %>


	<!-- 在這裡添加新增員工的表頭 -->



	<form action="EmpServlet1" method="post">
		<div class="form-header">修改員工資料</div>

		<div class="form-group">
			<label for="empno">* 員工編號:</label> <span class="empno-value"><%=empVO != null ? empVO.getEmpno() : "N/A"%></span>
			<input type="hidden" name="empno"
				value="<%=empVO != null ? empVO.getEmpno() : ""%>">
		</div>



		<div class="input-group">
			<label for="positionId">* 職位:</label> <select id="positionId"
				name="positionId">
				<%
				int selectedPositionId = empVO != null ? empVO.getPositionId() : 4;
				%>
				<option value="1" <%=selectedPositionId == 1 ? " selected" : ""%>>經理</option>
				<option value="2" <%=selectedPositionId == 2 ? " selected" : ""%>>客服人員</option>
				<option value="3" <%=selectedPositionId == 3 ? " selected" : ""%>>行銷人員</option>
				<option value="4" <%=selectedPositionId == 4 ? " selected" : ""%>>房務員</option>
				<option value="5" <%=selectedPositionId == 5 ? " selected" : ""%>>櫃台接待人員</option>
				<option value="6" <%=selectedPositionId == 6 ? " selected" : ""%>>活動人員</option>
				<option value="7" <%=selectedPositionId == 7 ? " selected" : ""%>>會議廳人員</option>
				<option value="8" <%=selectedPositionId == 8 ? " selected" : ""%>>餐廳人員</option>
			</select>
		</div>

		<div class="input-group">
			<label for="empName">* 員工姓名:</label> <input type="text" id="empName"
				name="empName" value="<%=empVO != null ? empVO.getEmpName() : ""%>">
		</div>

		<div class="input-group">
			<label for="hiredate">* 入職日期:</label> <input type="date"
				id="hiredate" name="hiredate"
				value="<%=empVO != null ? empVO.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : ""%>">
		</div>

		<div class="input-group">
			<label for="empState">* 員工狀態:</label> <select id="empState"
				name="empState">
				<option value="true"
					<%=empVO != null && empVO.getEmpState() ? " selected" : ""%>>在職</option>
				<option value="false"
					<%=empVO != null && !empVO.getEmpState() ? " selected" : ""%>>離職</option>
			</select>
		</div>

		<div class="input-group">
			<input type="text" id="empAccount" name="empAccount" value="<%=empVO != null && empVO.getEmpAccount() != null ? empVO.getEmpAccount() : ""%>">
		</div>

		<div class="input-group">
			<label for="empPassword">* 員工密碼:</label>
			<div class="password-container">
				<input type="password" id="empPassword" name="empPassword"
					value="<%=empVO != null ? empVO.getEmpPassword() : ""%>"> <i
					class="fa-solid fa-eye toggle-password"
					onclick="togglePasswordVisibility()"></i>
			</div>
		</div>


		<div class="input-group">
			<label for="image">* 員工照片:</label> <input type="file" id="image"
				name="image">
		</div>

		<input type="hidden" name="action" value="update"> <input
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


</body>
</html>