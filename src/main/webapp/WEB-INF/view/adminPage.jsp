<%--
  Created by IntelliJ IDEA.
  User: bohdan
  Date: 3/3/19
  Time: 2:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<html>
<head>
    <title>Admin page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminListStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/baseStyle.css">
</head>
<body>
<h3>Patients with undetermined status</h3>
<form class="clientForm">
    <c:forEach var="client" items="${sessionScope.patients}">
        <div class="clientsDiv">
            <p>Name: ${client.getName()} </p>
            <p>Surname: ${client.getSurname()}</p>
            <p>Date of birth: ${client.getAdditionalInfo()}</p>
        </div>
    </c:forEach>
</form>
</body>
</html>
