<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><spring:message code="register.title"/></title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>"/>
</head>
<body>
<nav class="navbar">
    <div class="nav-brand">
        <a href="<c:url value='/'/>"><spring:message code="index.title"/></a>
    </div>
    <div class="nav-links">
        <a href="<c:url value='/'/>"><spring:message code="nav.home"/></a>
        <a href="<c:url value='/login'/>"><spring:message code="nav.login"/></a>
        <span class="lang-switch">
            <a href="<c:url value='/register?lang=en'/>">EN</a> |
            <a href="<c:url value='/register?lang=zh'/>">中文</a>
        </span>
    </div>
</nav>

<div class="container">
    <div class="form-container">
        <h1><spring:message code="register.title"/></h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="<c:url value='/register'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label><spring:message code="register.username"/></label>
                <input type="text" name="username" required autofocus/>
            </div>
            <div class="form-group">
                <label><spring:message code="register.password"/></label>
                <input type="password" name="password" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="register.fullname"/></label>
                <input type="text" name="fullName" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="register.email"/></label>
                <input type="email" name="email" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="register.phone"/></label>
                <input type="text" name="phone"/>
            </div>
            <div class="form-group">
                <label><spring:message code="register.role"/></label>
                <select name="role">
                    <option value="STUDENT"><spring:message code="register.role.student"/></option>
                    <option value="TEACHER"><spring:message code="register.role.teacher"/></option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="register.submit"/></button>
        </form>

        <p class="form-footer">
            <a href="<c:url value='/login'/>"><spring:message code="register.login"/></a>
        </p>
    </div>
</div>
</body>
</html>

