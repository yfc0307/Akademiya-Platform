<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${pageTitle}" langUrl="/admin/users/edit/${user.id}">
    <div class="form-container">
        <h1><spring:message code="admin.user.edit"/></h1>

        <form action="<c:url value='/admin/users/edit/${user.id}'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label><spring:message code="admin.users.username"/></label>
                <input type="text" value="${user.username}" disabled/>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.users.fullname"/></label>
                <input type="text" name="fullName" value="<c:out value='${user.fullName}'/>" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.users.email"/></label>
                <input type="email" name="email" value="<c:out value='${user.email}'/>" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.users.phone"/></label>
                <input type="text" name="phone" value="<c:out value='${user.phone}'/>"/>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.users.role"/></label>
                <select name="role">
                    <option value="STUDENT" ${user.role.name() == 'STUDENT' ? 'selected' : ''}><spring:message code="register.role.student"/></option>
                    <option value="TEACHER" ${user.role.name() == 'TEACHER' ? 'selected' : ''}><spring:message code="register.role.teacher"/></option>
                </select>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.user.password"/></label>
                <input type="password" name="password"/>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="admin.user.save"/></button>
            <a href="<c:url value='/admin/users'/>" class="btn"><spring:message code="common.cancel"/></a>
        </form>
    </div>
</t:layout>

