<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="emp.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>所有員工資料</title>
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
th:not(:first-child) {
    border-left: 5px solid white;
}
tr:hover {background-color: #f5f5f5;}

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
    color: black; /* 修改图标颜色为黑色 */
    text-decoration: none;
    position: relative; /* 为tooltip-text定位 */
}

.home-icon:hover .tooltip-text {
    visibility: visible;
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
    margin-left: -60px; /* 将tooltip-text水平居中 */
    opacity: 0;
    transition: opacity 0.3s;
}

.home-icon:hover .tooltip-text {
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
<form action="EmpServlet1" method="post" onsubmit="return validateForm();">
    <div class="header-with-button">
        <!-- 更新后的房子图标和首頁文字提示 -->
        <a href="selectpage1.jsp" class="home-icon">
            <i class="fas fa-home"></i>
            <span class="tooltip-text">首頁</span>
        </a>
        <span class="title-text"><i class="fas fa-user title-icon"></i>所有員工資料</span>
        <input type="submit" class="edit-button" value="修改">
        <input type="hidden" name="action" value="getOne_For_Update">
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
        // 使用getAll() 方法來獲取所有員工資料
        EmpService empService =  new EmpService();
        List<EmpVO> empList = empService.getAll();
        for (EmpVO emp : empList) { %>
        <tr>
            <td><input type="radio" name="selectedEmp" value="<%= emp.getEmpno() %>"></td>
            <td><%= emp.getEmpno() %></td>
            <td><%= emp.getPositionid() %></td>
            <td><%= emp.getEmpname() %></td>
            <td><%= emp.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) %></td>
            <td><%= emp.getEmpstate() ? "在職" : "離職" %></td>
            <td><%= emp.getEmpaccount() %></td>
            <td><%= emp.getEmppassword() %></td>
            <td><img src="<%= emp.getImage() %>" alt="Employee Photo" width="100" height="100"></td>
        </tr>
        <% } %>
    </table>
</form>

<script>
function validateForm() {
    // 检查是否有员工被选中
    const isSelected = document.querySelector('input[name="selectedEmp"]:checked');
    if (!isSelected) {
        alert("請選擇要修改的員工");
        return false; // 阻止表单提交
    }
    return true; // 允许表单提交
}
</script>


</body>

</html>
