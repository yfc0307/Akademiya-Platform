<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Edit Profile" langUrl="/profile/edit">

    <div class="form-container">
        <h1><spring:message code="profile.edit.title"/></h1>

        <div class="form-group" style="margin-bottom:1.5rem;">
            <label><spring:message code="profile.username"/></label>
            <input type="text" value="<c:out value='${user.username}'/>" disabled
                   style="background:#f3f4f6; cursor:not-allowed;"/>
        </div>

        <div class="form-group" style="margin-bottom:1.5rem;">
            <label><spring:message code="profile.role"/></label>
            <input type="text" value="<c:out value='${user.role}'/>" disabled
                   style="background:#f3f4f6; cursor:not-allowed;"/>
        </div>

        <form action="<c:url value='/profile/edit'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="fullName"><spring:message code="profile.fullname"/></label>
                <input type="text" id="fullName" name="fullName"
                       value="<c:out value='${user.fullName}'/>" required/>
            </div>

            <div class="form-group">
                <label for="email"><spring:message code="profile.email"/></label>
                <input type="email" id="email" name="email"
                       value="<c:out value='${user.email}'/>" required/>
            </div>

            <div class="form-group">
                <label for="phone"><spring:message code="profile.phone"/></label>
                <input type="text" id="phone" name="phone"
                       value="<c:out value='${user.phone}'/>"/>
            </div>

            <div class="form-group">
                <label for="password"><spring:message code="profile.new.password"/></label>
                <input type="password" id="password" name="password"
                       placeholder="<spring:message code='profile.password.placeholder'/>"/>
            </div>

            <div style="display:flex; gap:0.5rem; margin-top:1.5rem;">
                <button type="submit" class="btn btn-primary"><spring:message code="common.save"/></button>
                <a href="<c:url value='/profile/history'/>" class="btn btn-secondary"><spring:message code="common.back"/></a>
            </div>
        </form>
    </div>

</t:layout>

