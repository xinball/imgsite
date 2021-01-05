<%--
  Created by IntelliJ IDEA.
  User: XinBall
  Date: 2020/12/26
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>message</title>
</head>
<body>
    <c:if test="${not empty requestScope.returnvalue && not empty requestScope.msg}">
        <c:if test="${not empty requestScope.obj}">
            {"return":${requestScope.returnvalue},"returnmsg":${requestScope.msg}}
        </c:if>
        <c:if test="${empty requestScope.obj}">
            {"return":${requestScope.returnvalue},"returnmsg":"${requestScope.msg}"}
        </c:if>
    </c:if>

</body>
</html>
