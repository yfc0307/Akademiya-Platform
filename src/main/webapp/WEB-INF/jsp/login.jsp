<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><spring:message code="login.title"/></title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>"/>
</head>
<body>
<nav class="navbar">
    <div class="nav-brand">
        <a href="<c:url value='/'/>"><spring:message code="index.title"/></a>
    </div>
    <div class="nav-links">
        <a href="<c:url value='/'/>"><spring:message code="nav.home"/></a>
        <a href="<c:url value='/register'/>"><spring:message code="nav.register"/></a>
        <span class="lang-switch">
            <a href="<c:url value='/login?lang=en'/>">EN</a> |
            <a href="<c:url value='/login?lang=zh'/>">中文</a>
        </span>
    </div>
</nav>

<div class="container">
    <div class="form-container">
        <h1><spring:message code="login.title"/></h1>

        <c:if test="${param.error != null}">
            <div class="alert alert-error"><spring:message code="login.error"/></div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success"><spring:message code="login.logout.success"/></div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form action="<c:url value='/login'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label><spring:message code="login.username"/></label>
                <input type="text" name="username" required autofocus/>
            </div>
            <div class="form-group">
                <label><spring:message code="login.password"/></label>
                <input type="password" name="password" required/>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="login.submit"/></button>
        </form>

        <p class="form-footer">
            <a href="<c:url value='/register'/>"><spring:message code="login.register"/></a>
        </p>
    </div>
</div>
</body>
</html>

