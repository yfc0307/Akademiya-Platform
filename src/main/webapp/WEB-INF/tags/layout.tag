<%@ tag description="Page Layout" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="langUrl" required="false" type="java.lang.String" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>"/>
</head>
<body>
<nav class="navbar">
    <div class="nav-brand">
        <a href="<c:url value='/'/>"><spring:message code="index.title"/></a>
    </div>
    <div class="nav-links">
        <a href="<c:url value='/'/>"><spring:message code="nav.home"/></a>
        <sec:authorize access="isAuthenticated()">
            <a href="<c:url value='/profile/edit'/>"><spring:message code="nav.profile"/></a>
            <a href="<c:url value='/profile/history'/>"><spring:message code="nav.history"/></a>
        </sec:authorize>
        <sec:authorize access="hasRole('TEACHER')">
            <a href="<c:url value='/admin/users'/>"><spring:message code="nav.users"/></a>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <a href="<c:url value='/login'/>"><spring:message code="nav.login"/></a>
            <a href="<c:url value='/register'/>"><spring:message code="nav.register"/></a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <form action="<c:url value='/logout'/>" method="post" style="display:inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="nav-btn"><spring:message code="nav.logout"/></button>
            </form>
        </sec:authorize>
        <span class="lang-switch">
            <c:choose>
                <c:when test="${not empty langUrl}">
                    <a href="${langUrl}${langUrl.contains('?') ? '&' : '?'}lang=en">EN</a> |
                    <a href="${langUrl}${langUrl.contains('?') ? '&' : '?'}lang=zh">中文</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/?lang=en'/>">EN</a> |
                    <a href="<c:url value='/?lang=zh'/>">中文</a>
                </c:otherwise>
            </c:choose>
        </span>
    </div>
</nav>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <jsp:doBody/>
</div>

<footer class="footer">
    <p><spring:message code="footer.copyright"/></p>
</footer>
</body>
</html>

