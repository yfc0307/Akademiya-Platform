<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${lecture.title}" langUrl="/lecture/${lecture.id}">
    <a href="<c:url value='/'/>" class="btn btn-sm"><spring:message code="lecture.back"/></a>

    <div class="card">
        <div class="card-header">
            <h1><c:out value="${lecture.title}"/></h1>
            <sec:authorize access="hasRole('TEACHER')">
                <div class="card-actions">
                    <a href="<c:url value='/admin/lecture/edit/${lecture.id}'/>" class="btn btn-sm"><spring:message code="common.edit"/></a>
                    <form action="<c:url value='/admin/lecture/delete/${lecture.id}'/>" method="post" style="display:inline;"
                          onsubmit="return confirm('<spring:message code="common.confirm.delete"/>');">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-sm btn-danger"><spring:message code="admin.lecture.delete"/></button>
                    </form>
                </div>
            </sec:authorize>
        </div>

        <!-- Summary -->
        <div class="section">
            <h3><spring:message code="lecture.summary"/></h3>
            <p><c:out value="${lecture.summary}"/></p>
        </div>

        <!-- Course Materials / Attachments -->
        <div class="section">
            <h3><spring:message code="lecture.materials"/></h3>
            <c:if test="${empty lecture.attachments}">
                <p class="text-muted"><spring:message code="lecture.no.materials"/></p>
            </c:if>
            <c:if test="${not empty lecture.attachments}">
                <ul class="file-list">
                    <c:forEach var="att" items="${lecture.attachments}">
                        <li class="file-item">
                            <a href="<c:url value='/lecture/attachment/${att.id}'/>"><c:out value="${att.fileName}"/></a>
                            <sec:authorize access="hasRole('TEACHER')">
                                <span class="file-action">
                                    <form action="<c:url value='/admin/lecture/attachment/delete/${att.id}'/>" method="post" style="display:inline;">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="lectureId" value="${lecture.id}"/>
                                        <button type="submit" class="btn btn-xs btn-danger"><spring:message code="admin.lecture.delete.attachment"/></button>
                                    </form>
                                </span>
                            </sec:authorize>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>

        <!-- Comments -->
        <div class="section">
            <h3><spring:message code="lecture.comments"/></h3>

            <c:if test="${empty lecture.comments}">
                <p class="text-muted"><spring:message code="lecture.no.comments"/></p>
            </c:if>

            <c:forEach var="comment" items="${lecture.comments}">
                <div class="comment">
                    <div class="comment-header">
                        <strong><c:out value="${comment.user.fullName}"/></strong>
                        <span class="comment-date"><fmt:formatDate value="${comment.timestampAsDate}" pattern="yyyy-MM-dd HH:mm"/></span>
                        <sec:authorize access="hasRole('TEACHER')">
                            <form action="<c:url value='/admin/comment/delete/${comment.id}'/>" method="post" style="display:inline;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="redirectUrl" value="/lecture/${lecture.id}"/>
                                <button type="submit" class="btn btn-xs btn-danger"><spring:message code="admin.delete.comment"/></button>
                            </form>
                        </sec:authorize>
                    </div>
                    <p><c:out value="${comment.content}"/></p>
                </div>
            </c:forEach>

            <!-- Add comment form -->
            <div class="comment-form">
                <h4><spring:message code="lecture.add.comment"/></h4>
                <form action="<c:url value='/lecture/${lecture.id}/comment'/>" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <textarea name="content" rows="3" required placeholder="<spring:message code="lecture.comment.placeholder"/>"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary"><spring:message code="lecture.submit.comment"/></button>
                </form>
            </div>
        </div>
    </div>
</t:layout>

