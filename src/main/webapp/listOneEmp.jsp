<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="emp.*"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map, java.util.HashMap"%>
<%
	Map<Integer, String> positionMap = new HashMap<Integer, String>();
	positionMap.put(1, "經理");
	positionMap.put(2, "客服人員");
	positionMap.put(3, "行銷人員");
	positionMap.put(4, "房務員");
	positionMap.put(5, "櫃台接待人員");
	positionMap.put(6, "活動人員");
	positionMap.put(7, "會議廳人員");
	positionMap.put(8, "餐廳人員");

	EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>員工資料</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	padding: 8px;
	text-align: center;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #5bc0de;
	color: white;
}

tr:hover {
	background-color: #f5f5f5;
}

.header-with-button {
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.title-text {
	font-weight: bold;
	font-size: 24px;
	display: flex;
	align-items: center;
	flex-grow: 1;
	justify-content: center;
}

.title-text i {
	margin-right: 12px; /* Adjusted space between icon and text */
}

.edit-button {
	padding: 10px 20px;
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
	border-radius: 5px;
}

.home-icon {
	color: black;
	text-decoration: none;
	display: flex;
	align-items: center;
	position: relative;
}

.tooltip-text {
	visibility: hidden;
	width: 120px;
	background-color: black;
	color: white;
	text-align: center;
	border-radius: 6px;
	padding: 5px 0;
	position: absolute;
	z-index: 1;
	bottom: 100%;
	left: 50%;
	margin-left: -60px;
	transition: opacity 0.3s;
}

.home-icon:hover .tooltip-text {
	visibility: visible;
	opacity: 1;
}
</style>
</head>
<body>
	<form action="EmpServlet1" method="post"
		onsubmit="return validateSelection();">
		<div class="header-with-button">
			<div style="display: flex; align-items: center;">
				<a href="selectpage1.jsp" class="home-icon"> <i
					class="fas fa-home"></i> <span class="tooltip-text">首頁</span>
				</a> <a href="add.jsp" style="margin-left: 20px;" class="home-icon">
					<i class="fa-solid fa-user-plus"></i> <span class="tooltip-text">新增員工</span>
				</a>
			</div>
			<span class="title-text"> <i class="fas fa-user"></i> 員工資料
			</span> <input type="submit" class="edit-button" value="修改"> <input
				type="hidden" name="action" value="getOne_For_Update">
		</div>
		<table>
			<tr>
				<th>選擇</th>
				<th>員工編號</th>
				<th>員工職位</th>
				<th>員工姓名</th>
				<th>入職日期</th>
				<th>員工狀態</th>
				<th>員工帳號</th>
				<th>員工密碼</th>
				<th>員工照片</th>
			</tr>
			<tr>
				<td><input type="radio" name="selectedEmp"
					value="<%=empVO.getEmpno()%>"></td>
				<td><%=empVO.getEmpno()%></td>
				<td><%= positionMap.get(empVO.getPositionId()) %></td>
				<td><%=empVO.getEmpName()%></td>
				<td><%=empVO.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))%></td>
				<td><%=empVO.getEmpState() ? "在職" : "離職"%></td>
				<td><%=empVO.getEmpAccount()%></td>
				<td><%=empVO.getEmpPassword()%></td>
				<td><img src="Picture?empno=<%=empVO.getEmpno()%>"
					alt="Employee Photo" width="100" height="100"></td>

			</tr>
		</table>
	</form>

	<script>
		function validateSelection() {
			var selectedEmp = document
					.querySelector('input[name="selectedEmp"]:checked');
			if (!selectedEmp) {
				alert("請選擇要修改的員工資料");
				return false;
			}
			return true;
		}
	</script>

</body>
</html>
