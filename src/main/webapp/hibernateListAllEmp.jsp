<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="hibernate.entity.*" %>
<%@ page import="hibernate.service.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Map, java.util.HashMap" %>

<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>

<%!
    public Map<Integer, String> getPositionMap() {
        Map<Integer, String> positionMap = new HashMap<Integer, String>();
        positionMap.put(1, "經理");
        positionMap.put(2, "客服人員");
        positionMap.put(3, "行銷人員");
        positionMap.put(4, "房務員");
        positionMap.put(5, "櫃台接待人員");
        positionMap.put(6, "活動人員");
        positionMap.put(7, "會議廳人員");
        positionMap.put(8, "餐廳人員");
        return positionMap;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>所有員工資料</title>

<%-- <%@ include file="/backend/htmlfile/css.html" %> --%>

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
        border-left: 5px solid white;
    }
    th:first-child {
        border-left: none;
    }
    tr:hover {
        background-color: #f5f5f5;
    }
    .header-with-button {
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
    }
    .title-text {
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
    }
    .title-icon {
        margin-right: 5px;
    }
    .home-icon {
        flex-grow: 0;
        color: black;
        text-decoration: none;
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
        opacity: 0;
        transition: opacity 0.3s, visibility 0.3s;
    }
    .home-icon:hover .tooltip-text {
        visibility: visible;
        opacity: 1;
    }
    .edit-button {
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        flex-grow: 0;
    }
</style>
</head>
<body>

<%-- <%@ include file="/backend/htmlfile/content1.html" %> --%>
	
<!-- 	<div class="container-fluid pt-4 px-4"> -->
<!--     <div class="bg-light rounded "> -->
<!--     <div class="G3_content"> -->

<form action="${pageContext.request.contextPath}/HibernateServlet" method="post" onsubmit="return validateForm();">
    <div class="header-with-button">
        <div style="display: flex; align-items: center;">
            <a href="hibernateIndex.jsp" class="home-icon">
                <i class="fa-solid fa-magnifying-glass"></i>
                <span class="tooltip-text">查詢</span>
            </a>
            <a href="hibernateAdd.jsp" class="home-icon" style="margin-left: 20px;">
                <i class="fas fa-user-plus"></i>
                <span class="tooltip-text">新增員工</span>
            </a>
        </div>
        <span class="title-text"><i class="fas fa-user title-icon"></i>所有員工資料</span>
        <input type="submit" class="edit-button" value="修改">
        <input type="hidden" name="action" value="update">
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
        <% 
        Map<Integer, String> positionMap = getPositionMap();
        EmpServiceImpl empService = new EmpServiceImpl();
        List<Employee> empList = empService.getAllEmps2();
        for (Employee emp : empList) { %>
        <tr>
            <td><input type="radio" name="selectedEmp" value="<%= emp.getEmpno() %>"></td>
            <td><%= emp.getEmpno() %></td>
            <td><%= positionMap.get(emp.getPosition().getPositionId()) %></td>
            <td><%= emp.getEmpName() %></td>
            <td><%= emp.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) %></td>
            <td><%= emp.getEmpState() ? "在職" : "離職" %></td>
            <td><%= emp.getEmpAccount() %></td>
            <td><%= emp.getEmpPassword() %></td>
            <td><img src="Picture?empno=<%= emp.getEmpno() %>" alt="Employee Photo" width="100" height="100"></td>
        </tr>
        <% } %>
    </table>
</form>

<script>
function validateForm() {
    const isSelected = document.querySelector('input[name="selectedEmp"]:checked');
    if (!isSelected) {
        alert("請選擇要修改的員工");
        return false;
    }
    return true;
}
</script>


<!--  	</div> -->
<!--     </div> -->
<!--     </div> -->


<%-- <%@ include file="/backend/htmlfile/content2.html" %> --%>
<%-- <%@ include file="/backend/htmlfile/script.html" %> --%>

</body>
</html>
