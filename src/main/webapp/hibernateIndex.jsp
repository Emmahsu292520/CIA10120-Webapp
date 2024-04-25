<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>複合查詢員工資料</title>
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
    padding: 20px 10px;
    text-align: center;
    font-size: 24px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    margin-bottom: 30px;
}

form {
    max-width: 500px; /* 控制表單的最大寬度 */
    margin: 20px auto;
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.input-group {
    margin-bottom: 20px;
    position: relative;
    width: 100%; /* 確保每個 input-group 使用可用的全部寬度 */
}

.input-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

.input-group input[type="text"], 
.input-group input[type="date"], 
.input-group select {
    width: 100%; /* 讓 input 和 select 元素寬度填滿其容器 */
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box; /* 包括邊框和內填充在內的寬度計算 */
}

.error-message {
    color: red;
    font-size: 14px;
    position: absolute;
    top: 0;
    right: 0;
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

<%-- 	<%@ include file="/backend/htmlfile/content1.html" %> --%>
	
<!-- 	<div class="container-fluid pt-4 px-4"> -->
<!--     <div class="bg-light rounded "> -->
<!--     <div class="G3_content"> -->
	
	
	

    <form action="${pageContext.request.contextPath}/HibernateServlet" method="post">
        <div class="form-header">選擇員工</div>
        <div class="input-group">
            <label for="empno">* 輸入員工編號:</label>
            <input type="text" id="empno" name="empno" value="">
            <div class="error-message">
                <% List<String> errors = (List<String>) request.getAttribute("errors");
                   if (errors != null && !errors.isEmpty()) {
                       out.println(errors.get(0));
                   } %>
            </div>
        </div>
        
        <div class="input-group">
            <label for="positionId">* 職位:</label>
            <select id="positionId" name="positionId">
                <option value="">選取職位</option>
                <option value="1">經理</option>
                <option value="2">客服人員</option>
                <option value="3">行銷人員</option>
                <option value="4">房務員</option>
                <option value="5">櫃台接待人員</option>
                <option value="6">活動人員</option>
                <option value="7">會議廳人員</option>
                <option value="8">餐廳人員</option>
            </select>
        </div>
        <div class="input-group">
            <label>* 員工名字：</label>
            <input type="text" name="ename"><br>
        </div>
        
        <div class="input-group">
            <label>* 到職日期間範圍</label>
            <input type="date" name="starthiredate"> ～ <input type="date" name="endhiredate"><br>
        </div>
        
        <div class="input-group">
            <label for="empState">* 員工狀態:</label>
            <select id="empState" name="empState">
                <option value="">選取狀態</option>
                <option value="true">在職</option>
                <option value="false">離職</option>
            </select>
        </div>
        <input type="hidden" name="action" value="compositeQuery">
        <input type="submit" value="送出">
    </form>
    
<!--     </div> -->
<!--     </div> -->
<!--     </div> -->


<%-- <%@ include file="/backend/htmlfile/content2.html" %> --%>
<%-- <%@ include file="/backend/htmlfile/script.html" %> --%>
</body>
</html>