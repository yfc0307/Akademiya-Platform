<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${poll.question}" langUrl="/poll/${poll.id}">
    <c:if test="${not empty voteError}">
        <div class="alert alert-error">${voteError}</div>
    </c:if>

    <a href="<c:url value='/'/>" class="btn btn-sm"><spring:message code="poll.back"/></a>

    <div class="card">
        <div class="card-header">
            <h1><spring:message code="poll.title"/></h1>
            <sec:authorize access="hasRole('TEACHER')">
                <div class="card-actions">
                    <a href="<c:url value='/admin/poll/edit/${poll.id}'/>" class="btn btn-sm"><spring:message code="common.edit"/></a>
                    <form action="<c:url value='/admin/poll/delete/${poll.id}'/>" method="post" style="display:inline;"
                          onsubmit="return confirm('<spring:message code="common.confirm.delete"/>');">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-sm btn-danger"><spring:message code="admin.poll.delete"/></button>
                    </form>
                </div>
            </sec:authorize>
        </div>

        <div class="section">
            <h2><c:out value="${poll.question}"/></h2>

            <!-- Voting form (only shown if user hasn't voted) -->
            <c:if test="${!hasVoted}">
                <form action="<c:url value='/poll/${poll.id}/vote'/>" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="poll-options">
                        <c:forEach var="i" begin="1" end="5">
                            <div class="poll-option">
                                <label>
                                    <input type="radio" name="selectedOption" value="${i}" required/>
                                    <span>${poll.getOptionText(i)}</span>
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="submit" class="btn btn-primary"><spring:message code="poll.vote"/></button>
                </form>
            </c:if>

            <!-- Results (always visible) -->
            <div class="poll-results">
                <h3><spring:message code="poll.total.votes"/></h3>
                <c:set var="totalVotes" value="${fn:length(poll.votes)}"/>
                <c:forEach var="i" begin="1" end="5">
                    <c:set var="voteCount" value="${poll.getVoteCount(i)}"/>
                    <div class="poll-result-item">
                        <div class="poll-result-label">
                            <span>${poll.getOptionText(i)}</span>
                            <span class="vote-count">
                                (${voteCount} <spring:message code="poll.votes"/>)
                            </span>
                        </div>
                        <div class="poll-bar-container">
                            <div class="poll-bar" style="width:${totalVotes > 0 ? (voteCount * 100 / totalVotes) : 0}%"></div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${hasVoted}">
                <div class="alert alert-info">
                    <span><spring:message code="poll.already.voted"/></span>
                </div>
            </c:if>
        </div>

        <!-- Comments -->
        <div class="section">
            <h3><spring:message code="poll.comments"/></h3>

            <c:if test="${empty poll.comments}">
                <p class="text-muted"><spring:message code="poll.no.comments"/></p>
            </c:if>

            <c:forEach var="comment" items="${poll.comments}">
                <div class="comment">
                    <div class="comment-header">
                        <strong><c:out value="${comment.user.fullName}"/></strong>
                        <span class="comment-date"><fmt:formatDate value="${comment.timestampAsDate}" pattern="yyyy-MM-dd HH:mm"/></span>
                        <sec:authorize access="hasRole('TEACHER')">
                            <form action="<c:url value='/admin/poll/comment/delete/${comment.id}'/>" method="post" style="display:inline;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="redirectUrl" value="/poll/${poll.id}"/>
                                <button type="submit" class="btn btn-xs btn-danger"><spring:message code="admin.delete.comment"/></button>
                            </form>
                        </sec:authorize>
                    </div>
                    <p><c:out value="${comment.content}"/></p>
                </div>
            </c:forEach>

            <!-- Add comment form -->
            <div class="comment-form">
                <h4><spring:message code="poll.add.comment"/></h4>
                <form action="<c:url value='/poll/${poll.id}/comment'/>" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <textarea name="content" rows="3" required placeholder="<spring:message code="poll.comment.placeholder"/>"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary"><spring:message code="poll.submit.comment"/></button>
                </form>
            </div>
        </div>
    </div>
</t:layout>

