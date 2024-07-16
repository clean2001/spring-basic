<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <p>data(EL 문법) : ${myData}</p> <!-- myData의 값은 자바 코드에서 꽂아준다.-->
    <p>data(jstl문법 - Java 코드): <%
        String getData = (String)request.getAttribute("myData");
        out.print(getData);
    %></p>
</body>
</html>