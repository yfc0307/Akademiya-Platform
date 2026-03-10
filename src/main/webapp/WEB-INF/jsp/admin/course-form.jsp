<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="isEdit" value="${course != null}"/>

<t:layout title="${pageTitle}">
    <div class="form-container">
        <h1>
            <c:choose>
                <c:when test="${isEdit}"><spring:message code="admin.course.edit"/></c:when>
                <c:otherwise><spring:message code="admin.course.add"/></c:otherwise>
            </c:choose>
        </h1>

        <c:choose>
            <c:when test="${isEdit}">
                <c:set var="formAction"><c:url value='/admin/course/edit/${course.id}'/></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="formAction"><c:url value='/admin/course/add'/></c:set>
            </c:otherwise>
        </c:choose>

        <form action="${formAction}" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label><spring:message code="admin.course.name"/></label>
                <input type="text" name="name" value="<c:out value='${isEdit ? course.name : ""}'/>" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.course.description"/></label>
                <textarea name="shortDescription" rows="4" required><c:out value="${isEdit ? course.shortDescription : ''}"/></textarea>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="admin.course.save"/></button>
            <a href="<c:url value='/'/>" class="btn"><spring:message code="common.cancel"/></a>
        </form>
    </div>
</t:layout>

