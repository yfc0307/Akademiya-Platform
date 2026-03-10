<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="isEdit" value="${poll != null}"/>

<t:layout title="${pageTitle}">
    <div class="form-container">
        <h1>
            <c:choose>
                <c:when test="${isEdit}"><spring:message code="admin.poll.edit"/></c:when>
                <c:otherwise><spring:message code="admin.poll.add"/></c:otherwise>
            </c:choose>
        </h1>

        <c:choose>
            <c:when test="${isEdit}">
                <c:set var="formAction"><c:url value='/admin/poll/edit/${poll.id}'/></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="formAction"><c:url value='/admin/poll/add'/></c:set>
            </c:otherwise>
        </c:choose>

        <form action="${formAction}" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <c:if test="${!isEdit}">
                <input type="hidden" name="courseId" value="${courseId}"/>
            </c:if>

            <div class="form-group">
                <label><spring:message code="admin.poll.question"/></label>
                <input type="text" name="question" value="<c:out value='${isEdit ? poll.question : ""}'/>" required/>
            </div>
            <c:forEach var="i" begin="1" end="5">
                <div class="form-group">
                    <label><spring:message code="admin.poll.option"/> ${i}</label>
                    <input type="text" name="option${i}" value="<c:out value='${isEdit ? poll.getOptionText(i) : ""}'/>" required/>
                </div>
            </c:forEach>

            <button type="submit" class="btn btn-primary"><spring:message code="admin.poll.save"/></button>
            <a href="<c:url value='/'/>" class="btn"><spring:message code="common.cancel"/></a>
        </form>
    </div>
</t:layout>

