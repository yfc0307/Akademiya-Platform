<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="isEdit" value="${lecture != null}"/>

<t:layout title="${pageTitle}">
    <div class="form-container">
        <h1>
            <c:choose>
                <c:when test="${isEdit}"><spring:message code="admin.lecture.edit"/></c:when>
                <c:otherwise><spring:message code="admin.lecture.add"/></c:otherwise>
            </c:choose>
        </h1>

        <c:choose>
            <c:when test="${isEdit}">
                <c:set var="formAction"><c:url value='/admin/lecture/edit/${lecture.id}'/></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="formAction"><c:url value='/admin/lecture/add'/></c:set>
            </c:otherwise>
        </c:choose>

        <form action="${formAction}" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <c:if test="${!isEdit}">
                <input type="hidden" name="courseId" value="${courseId}"/>
            </c:if>

            <div class="form-group">
                <label><spring:message code="admin.lecture.title"/></label>
                <input type="text" name="title" value="<c:out value='${isEdit ? lecture.title : ""}'/>" required/>
            </div>
            <div class="form-group">
                <label><spring:message code="admin.lecture.summary"/></label>
                <textarea name="summary" rows="5" required><c:out value="${isEdit ? lecture.summary : ''}"/></textarea>
            </div>

            <!-- Existing files (edit mode) -->
            <c:if test="${isEdit && not empty lecture.attachments}">
                <div class="form-group">
                    <label><spring:message code="admin.lecture.existing.files"/></label>
                    <ul class="file-list">
                        <c:forEach var="att" items="${lecture.attachments}">
                            <li class="file-item">
                                <span><c:out value="${att.fileName}"/></span>
                                <form action="<c:url value='/admin/lecture/attachment/delete/${att.id}'/>" method="post" style="display:inline;">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="lectureId" value="${lecture.id}"/>
                                    <button type="submit" class="btn btn-xs btn-danger"><spring:message code="admin.lecture.delete.attachment"/></button>
                                </form>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <div class="form-group">
                <label><spring:message code="admin.lecture.files"/></label>
                <input type="file" name="files" multiple/>
            </div>

            <button type="submit" class="btn btn-primary"><spring:message code="admin.lecture.save"/></button>
            <a href="<c:url value='/'/>" class="btn"><spring:message code="common.cancel"/></a>
        </form>
    </div>
</t:layout>

