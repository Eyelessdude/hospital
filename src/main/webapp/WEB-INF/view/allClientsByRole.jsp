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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>All ${requestScope.clientGetRole}s</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminListStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/baseStyle.css">
</head>
<body>
<h3>List of all ${requestScope.clientGetRole}s:</h3>

<form class="clientForm">
    <c:forEach var="client" items="${requestScope.clientsList}">
        <div class="clientsDiv">
            <p>Name: ${client.getName()}</p>
            <p>Surname: ${client.getSurname()}</p>
            <c:choose>
                <c:when test="${requestScope.clientGetRole=='doctor'}">
                    <p>Category: ${client.getAdditionalInfo()}</p>
                </c:when>
                <c:when test="${requestScope.clientGetRole=='patient'}">
                    <p>Date of birth: ${client.getAdditionalInfo()}</p>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
</form>
</body>
</html>