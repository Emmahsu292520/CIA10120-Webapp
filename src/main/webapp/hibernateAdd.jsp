<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="hibernate.entity.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="hibernate.service.EmpServiceImpl" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>

<%
EmpServiceImpl empService = new EmpServiceImpl();
List<Position> positions = empService.getAllPositions();
List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");
Employee employee = (Employee) request.getAttribute("newEmp");
Integer selectedPositionId = employee != null && employee.getPosition() != null ? employee.getPosition().getPositionId() : null;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增員工</title>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

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
    padding: 10px;
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

.input-group {
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

input[type="text"], input[type="date"], select, input[type="password"],
input[type="file"] {
    width: calc(100% - 18px);
    padding: 8px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
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

.password-container {
    position: relative;
}

.toggle-password {
    position: absolute;
    right: 10px;
    top: 8px;
    cursor: pointer;
}

/* 下拉選單寬度調整 */
select#positionId, select#empState {
    width: calc(100% - 1px); /* 計算實際寬度，包括邊框和內邊距 */
}
</style>

</head>
<body>
<%-- 	<%@ include file="/backend/htmlfile/content1.html" %> --%>
	
<!-- 	<div class="container-fluid pt-4 px-4"> -->
<!--     <div class="bg-light rounded "> -->
<!--     <div class="G3_content"> -->


<!-- 顯示錯誤訊息 -->
<% if (errorMsgs != null && !errorMsgs.isEmpty()) { %>
<div style="color: red;">
    <ul>
        <% for (String errorMsg : errorMsgs) { %>
        <li><%= errorMsg %></li>
        <% } %>
    </ul>
</div>
<% } %>


	

<form action="${pageContext.request.contextPath}/HibernateServlet" method="post" enctype="multipart/form-data">
    <div class="form-header">新增員工</div>
    <div class="input-group">
        <label for="positionId">* 職位:</label>
        <select id="positionId" name="positionId">
            <% for (Position position : positions) {
                boolean isSelected = (selectedPositionId != null && position.getPositionId().equals(selectedPositionId));
            %>
            <option value="<%= position.getPositionId() %>" <%= isSelected ? "selected" : "" %>>
                <%= position.getPositionName() %>
            </option>
            <% } %>
        </select>
    </div>

    <div class="input-group">
        <label for="empName">* 員工姓名:</label>
        <input type="text" id="empName" name="empName" value="<%=employee != null && employee.getEmpName() != null ? employee.getEmpName() : ""%>">
    </div>

    <div class="input-group">
        <label for="hiredate">* 入職日期:</label>
        <input type="date" id="hiredate" name="hiredate" value="<%=employee != null && employee.getHiredate() != null ? employee.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : ""%>">
    </div>

    <div class="input-group">
        <label for="empState">* 員工狀態:</label>
        <select id="empState" name="empState">
           <option value="true" <%=employee != null && employee.getEmpState() != null && employee.getEmpState() ? "selected" : ""%>>在職</option>
		   <option value="false" <%=employee != null && employee.getEmpState() != null && !employee.getEmpState() ? "selected" : ""%>>離職</option>
        </select>
    </div>

    <div class="input-group">
        <label for="empPassword">* 員工密碼:</label>
        <div class="password-container">
            <input type="password" id="empPassword" name="empPassword" value="<%=employee != null && employee.getEmpPassword() != null ? employee.getEmpPassword() : ""%>">
            <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility();"></i>
        </div>
    </div>

    <div class="input-group">
        <label for="image">* 員工照片:</label>
        <input type="file" id="image" name="image">
    </div>

    <input type="hidden" name="action" value="insert">
    <input type="submit" value="新增">
</form>

<script>
function togglePasswordVisibility() {
    var passwordInput = document.getElementById("empPassword");
    var toggleIcon = document.querySelector('.toggle-password');
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        toggleIcon.classList.remove("fa-eye");
        toggleIcon.classList.add("fa-eye-slash");
    } else {
        passwordInput.type = "password";
        toggleIcon.classList.remove("fa-eye-slash");
        toggleIcon.classList.add("fa-eye");
    }
}
</script>


<!-- 	 </div> -->
<!--     </div> -->
<!--     </div> -->


<%-- <%@ include file="/backend/htmlfile/content2.html" %> --%>
<%-- <%@ include file="/backend/htmlfile/script.html" %> --%>

</body>
</html>
