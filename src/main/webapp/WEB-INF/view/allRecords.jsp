
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${userId} operations</title>
</head>
<body>

<table border="2" width="100%" cellpadding="2">
    <tr>
        <th>User ID</th>
        <th>First number</th>
        <th>Second number</th>
        <th>Operation</th>
        <th>Result</th>
    </tr>
    <c:forEach var = "record" items = "${allRecords}">
    <tr>
        <td>${record.userId}</td>
        <td>${record.one}</td>
        <td>${record.two}</td>
        <td>${record.operation}</td>
        <td>${record.result}</td>
    </tr>
    </c:forEach>
</body>
</html>
