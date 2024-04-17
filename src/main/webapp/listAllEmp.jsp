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
<form action="EmpServlet1" method="post" onsubmit="return validateForm();">
    <div class="header-with-button">
        <div style="display: flex; align-items: center;">
            <a href="selectpage1.jsp" class="home-icon">
                <i class="fas fa-home"></i>
                <span class="tooltip-text">首頁</span>
            </a>
            <a href="add.jsp" class="home-icon" style="margin-left: 20px;">
                <i class="fas fa-user-plus"></i>
                <span class="tooltip-text">新增員工</span>
            </a>
        </div>
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
        EmpService empService = new EmpService();
        List<EmpVO> empList = empService.getAll();
        for (EmpVO emp : empList) { %>
        <tr>
            <td><input type="radio" name="selectedEmp" value="<%= emp.getEmpno() %>"></td>
            <td><%= emp.getEmpno() %></td>
            <td><%= emp.getPositionId() %></td>
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

</body>
</html>
