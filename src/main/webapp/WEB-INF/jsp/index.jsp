<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${pageTitle}" langUrl="/">
    <h1><spring:message code="index.welcome"/></h1>

    <!-- Teacher: Add Course button -->
    <sec:authorize access="hasRole('TEACHER')">
        <div class="action-bar">
            <a href="<c:url value='/admin/course/add'/>" class="btn btn-primary"><spring:message code="admin.course.add"/></a>
        </div>
    </sec:authorize>

    <c:if test="${empty courses}">
        <div class="empty-state">
            <p><spring:message code="index.no.courses"/></p>
        </div>
    </c:if>

    <c:forEach var="course" items="${courses}">
        <div class="card course-card">
            <div class="card-header">
                <h2><c:out value="${course.name}"/></h2>
                <sec:authorize access="hasRole('TEACHER')">
                    <div class="card-actions">
                        <a href="<c:url value='/admin/course/edit/${course.id}'/>" class="btn btn-sm"><spring:message code="common.edit"/></a>
                        <form action="<c:url value='/admin/course/delete/${course.id}'/>" method="post" style="display:inline;"
                              onsubmit="return confirm('<spring:message code="common.confirm.delete"/>');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-sm btn-danger"><spring:message code="common.delete"/></button>
                        </form>
                    </div>
                </sec:authorize>
            </div>
            <p class="course-desc"><c:out value="${course.shortDescription}"/></p>

            <!-- Lectures -->
            <div class="section">
                <h3><spring:message code="index.lectures"/></h3>
                <ul class="item-list">
                    <c:forEach var="lecture" items="${course.lectures}">
                        <li>
                            <sec:authorize access="isAuthenticated()">
                                <a href="<c:url value='/lecture/${lecture.id}'/>"><c:out value="${lecture.title}"/></a>
                            </sec:authorize>
                            <sec:authorize access="isAnonymous()">
                                <span><c:out value="${lecture.title}"/></span>
                            </sec:authorize>
                        </li>
                    </c:forEach>
                </ul>
                <sec:authorize access="hasRole('TEACHER')">
                    <a href="<c:url value='/admin/lecture/add?courseId=${course.id}'/>" class="btn btn-sm btn-secondary">
                        <spring:message code="admin.lecture.add"/>
                    </a>
                </sec:authorize>
            </div>

            <!-- Polls -->
            <div class="section">
                <h3><spring:message code="index.polls"/></h3>
                <ul class="item-list">
                    <c:forEach var="poll" items="${course.polls}">
                        <li>
                            <sec:authorize access="isAuthenticated()">
                                <a href="<c:url value='/poll/${poll.id}'/>"><c:out value="${poll.question}"/></a>
                            </sec:authorize>
                            <sec:authorize access="isAnonymous()">
                                <span><c:out value="${poll.question}"/></span>
                            </sec:authorize>
                        </li>
                    </c:forEach>
                </ul>
                <sec:authorize access="hasRole('TEACHER')">
                    <a href="<c:url value='/admin/poll/add?courseId=${course.id}'/>" class="btn btn-sm btn-secondary">
                        <spring:message code="admin.poll.add"/>
                    </a>
                </sec:authorize>
            </div>
        </div>
    </c:forEach>
</t:layout>

