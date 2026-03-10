<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${historyTitle}" langUrl="/profile/history">
    <h1><spring:message code="history.title"/></h1>
    <p>
        <strong><c:out value="${user.fullName}"/></strong>
        (<c:out value="${user.username}"/>) -
        <c:out value="${user.role}"/>
    </p>

    <!-- Comment History -->
    <div class="card">
        <h2><spring:message code="history.comments"/></h2>

        <c:if test="${empty comments}">
            <p class="text-muted"><spring:message code="history.no.comments"/></p>
        </c:if>

        <c:if test="${not empty comments}">
            <table class="data-table">
                <thead>
                    <tr>
                        <th><spring:message code="history.date"/></th>
                        <th><spring:message code="history.content"/></th>
                        <th><spring:message code="history.type"/></th>
                        <th><spring:message code="history.target"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td><fmt:formatDate value="${comment.timestampAsDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><c:out value="${comment.content}"/></td>
                            <td>
                                <c:if test="${comment.lecture != null}"><spring:message code="history.comment.on.lecture"/></c:if>
                                <c:if test="${comment.poll != null}"><spring:message code="history.comment.on.poll"/></c:if>
                            </td>
                            <td>
                                <c:if test="${comment.lecture != null}">
                                    <a href="<c:url value='/lecture/${comment.lecture.id}'/>"><c:out value="${comment.lecture.title}"/></a>
                                </c:if>
                                <c:if test="${comment.poll != null}">
                                    <a href="<c:url value='/poll/${comment.poll.id}'/>"><c:out value="${comment.poll.question}"/></a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <!-- Poll Vote History -->
    <div class="card">
        <h2><spring:message code="history.polls"/></h2>

        <c:if test="${empty votes}">
            <p class="text-muted"><spring:message code="history.no.votes"/></p>
        </c:if>

        <c:if test="${not empty votes}">
            <table class="data-table">
                <thead>
                    <tr>
                        <th><spring:message code="history.poll.question"/></th>
                        <th><spring:message code="history.selected.option"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="vote" items="${votes}">
                        <tr>
                            <td>
                                <a href="<c:url value='/poll/${vote.poll.id}'/>"><c:out value="${vote.poll.question}"/></a>
                            </td>
                            <td>${vote.poll.getOptionText(vote.selectedOption)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</t:layout>

