<%--
  Created by IntelliJ IDEA.
  User: bohdan
  Date: 3/9/19
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="header.jsp"/>
<html>
<head>
    <title>Hospital cards</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminListStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/baseStyle.css">
</head>
<body>
<h3>All hospital cards:</h3>
<button class="addButton" form="hospitalCardsForm" type="submit">Add new hospital card</button>
<form id="hospitalCardsForm" class="clientForm" action="${contextPath}/admin/createCard">
    <c:forEach var="hospitalCards" items="${sessionScope.hospitalCards}">
        <div class="clientsDiv">
            <p>Doctor:</p>
            <p>Name: ${hospitalCards.getDoctor().getName()}
                Surname: ${hospitalCards.getDoctor().getSurname()}</p>
            <p>Category: ${hospitalCards.getDoctor().getAdditionalInfo()}</p>
            <p>Patient: </p>
            <p>Name: ${hospitalCards.getPatient().getName()}
                Surname: ${hospitalCards.getPatient().getSurname()}</p>
            <p>Date of birth: ${hospitalCards.getPatient().getAdditionalInfo()}</p>
            <p>Status and diagnosis:</p>
            <p>Status: ${hospitalCards.getPatientStatus().toString()}</p>
            <p>Current diagnosis: ${hospitalCards.getPatientDiagnosis().toString()}</p>
            <p>Final diagnosis: ${hospitalCards.getPatientFinalDiagnosis().toString()}</p>
            <p>Medicine:
                <c:forEach var="medicine" items="${hospitalCards.getPatientMedicines()}">
                    ${medicine}
                </c:forEach></p>
            <p>Operations:
                <c:forEach var="medicine" items="${hospitalCards.getPatientOperations()}">
                    ${medicine}
                </c:forEach></p>
            <p>Procedures:
                <c:forEach var="medicine" items="${hospitalCards.getPatientProcedures()}">
                    ${medicine}
                </c:forEach></p>
        </div>
    </c:forEach>
</form>
</body>
</html>
