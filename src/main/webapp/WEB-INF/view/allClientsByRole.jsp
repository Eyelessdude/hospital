<%--
  Created by IntelliJ IDEA.
  User: bohdan
  Date: 3/3/19
  Time: 11:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<html>
<head>
    <title>All ${sessionScope.clientGetRole}s</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminListStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/baseStyle.css">
</head>
<body>
<h3>List of all ${sessionScope.clientGetRole}s:</h3>
<button class="addButton" type="button">Add new client</button>
<form class="clientForm">
    <c:forEach var="client" items="${sessionScope.clientsList}">
        <div class="clientsDiv">
            <p>Name: ${client.getName()}</p>
            <p>Surname: ${client.getSurname()}</p>
            <c:choose>
                <c:when test="${sessionScope.clientGetRole=='doctor'}">
                    <p>Category: ${client.getAdditionalInfo()}</p>
                </c:when>
                <c:when test="${sessionScope.clientGetRole=='patient'}">
                    <p>Date of birth: ${client.getAdditionalInfo()}</p>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
</form>
</body>
</html>