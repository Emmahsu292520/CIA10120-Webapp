<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="emp.*"%>
<%@ page import="java.util.List"%> <!-- 確保加入了這一行 -->
<%@ page import="java.time.format.DateTimeFormatter"%>

<% 
   List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");
%>

<%-- 保留用戶輸入的資料 --%>
<% EmpVO empVO = (EmpVO) request.getAttribute("empVO"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增員工</title>
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
    }
    form {
        max-width: 400px;
        margin: 20px auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    .form-header + .input-group {
        margin-top: 20px; /* 增加標題和第一個輸入組之間的距離 */
    }
    .input-group {
        margin-bottom: 10px;
    }
    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold; /* 設定標籤文字為粗體 */
    }
    input[type="text"],
    input[type="date"],
    select,
    input[type="password"],
    input[type="file"] {
        width: calc(100% - 10px);
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
</style>
</head>
<body>

<% if (errorMsgs != null && !errorMsgs.isEmpty()) { %>
    <div style="color: red;">
        <ul>
            <% for (String errorMsg : errorMsgs) { %>
                <li><%= errorMsg %></li>
            <% } %>
        </ul>
    </div>
<% } %>

<form action="EmpServlet1" method="post">
    <div class="form-header">新增員工</div>
    <div class="input-group">
        <label for="positionid">* 職位:</label>
        <select id="positionid" name="positionid">
            <% int selectedPositionId = empVO != null ? empVO.getPositionid() : 4; %>
            <option value="1"<%= selectedPositionId == 1 ? " selected" : "" %>>經理</option>
            <option value="2"<%= selectedPositionId == 2 ? " selected" : "" %>>客服人員</option>
            <option value="3"<%= selectedPositionId == 3 ? " selected" : "" %>>行銷人員</option>
            <option value="4"<%= selectedPositionId == 4 ? " selected" : "" %>>房務員</option>
            <option value="5"<%= selectedPositionId == 5 ? " selected" : "" %>>櫃台接待人員</option>
            <option value="6"<%= selectedPositionId == 6 ? " selected" : "" %>>活動人員</option>
            <option value="7"<%= selectedPositionId == 7 ? " selected" : "" %>>會議廳人員</option>
            <option value="8"<%= selectedPositionId == 8 ? " selected" : "" %>>餐廳人員</option>
        </select>
    </div>
    
    <div class="input-group">
        <label for="empname">* 員工姓名:</label>
        <input type="text" id="empname" name="empname" value="<%= empVO != null ? empVO.getEmpname() : "" %>">
    </div>
    
    <div class="input-group">
        <label for="hiredate">* 入職日期:</label>
        <input type="date" id="hiredate" name="hiredate" value="<%= empVO != null ? empVO.getHiredate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "" %>">
    </div>
    
    <div class="input-group">
        <label for="empstate">* 員工狀態:</label>
        <select id="empstate" name="empstate">
            <option value="true"<%= empVO != null && empVO.getEmpstate() ? " selected" : "" %>>在職</option>
            <option value="false"<%= empVO != null && !empVO.getEmpstate() ? " selected" : "" %>>離職</option>
        </select>
    </div>
    
    <div class="input-group">
        <label for="emppassword">* 員工密碼:</label>
        <input type="password" id="emppassword" name="emppassword" value="<%= empVO != null ? empVO.getEmppassword() : "" %>">
    </div>
    
    <div class="input-group">
        <label for="image">* 員工照片:</label>
        <input type="file" id="image" name="image">
    </div>
    
    <input type="hidden" name="action" value="insert">
    <input type="submit" value="新增">
</form>
</body>
</html>
