<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>MEALS LIST</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <h2 align="center">List of meals<br/></h2>
    <table align="center" border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:set var="excess" value="${meal.excess}"></c:set>
            <tr style="background-color:${excess ? 'red' : 'greenyellow'}">
                <td align="center"><javatime:format pattern="yyyy-MM-dd HH:mm" value="${meal.dateTime}"/></td>
                <td align="center">${meal.description}</td>
                <td align="center">${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</section>
</body>
</html>
