<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${pageTitle}" langUrl="/admin/users">
    <h1><spring:message code="admin.users.title"/></h1>

    <table class="data-table">
        <thead>
            <tr>
                <th><spring:message code="admin.users.id"/></th>
                <th><spring:message code="admin.users.username"/></th>
                <th><spring:message code="admin.users.fullname"/></th>
                <th><spring:message code="admin.users.email"/></th>
                <th><spring:message code="admin.users.phone"/></th>
                <th><spring:message code="admin.users.role"/></th>
                <th><spring:message code="admin.users.actions"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.fullName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.phone}"/></td>
                    <td>${user.role}</td>
                    <td>
                        <a href="<c:url value='/admin/users/edit/${user.id}'/>" class="btn btn-xs"><spring:message code="admin.users.edit"/></a>
                        <form action="<c:url value='/admin/users/delete/${user.id}'/>" method="post" style="display:inline;"
                              onsubmit="return confirm('<spring:message code="admin.users.confirm.delete"/>');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-xs btn-danger"><spring:message code="admin.users.delete"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</t:layout>

