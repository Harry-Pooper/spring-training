<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: mrlz
  Date: 05.04.2018
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
</head>
<body>
    <!-- copy to all new VIEW-->
    <div class="header">
        <div class="image">
            <a href="/"><img src="<c:url value="/img/logo.png"/>"/></a>
        </div>
        <div class="title">
            <a class="title_link" href="/">JobToad</a>
        </div>
        <sec:authorize access="isAuthenticated()">
            <a:if test="${rdbo.accType == userType}">
                <div class="text_box_right">
                    <a class="black_link" href="/profile?id=<%=UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId().toString()%>">${pageContext.request.userPrincipal.name}</a>
                </div>
            </a:if>
            <a:if test="${rdbo.accType == organType}">
                <div class="text_box_right">
                    <a class="black_link" href="/profileorg?id=<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString()%>">${pageContext.request.userPrincipal.name}</a>
                </div>
            </a:if>
            <div class="text_box_right"><a class="black_link" href="/logout">Выйти</a></div>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <div class="text_box_right"><a class="black_link" href="/login">Войти</a></div>
            <div class="text_box_right"><a class="black_link" href="/registration">Регистрация</a></div>
        </sec:authorize>
    </div>
    <sec:authorize access="isAuthenticated()">
        <a:if test="${rdbo.accType == userType}">
            <div class="sub_header">
                <div class="text_box_left">
                    <a class="white_link" href="/resume?id=<%=UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId().toString()%>">Мои резюме</a>
                </div>
            </div>
        </a:if>
        <a:if test="${rdbo.accType == organType}">
            <div class="sub_header">
                <div class="text_box">
                    <a class="white_link" href="/vacansy?id=<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString()%>">Вакансии</a>
                </div>
            </div>
        </a:if>
    </sec:authorize>
    <div class="main_box text-align-center">
        <form:form method="POST" modelAttribute="loginInfo">
            <div class="small-block">
            <form:label path="login">Логин</form:label>
            <spring:bind path="login">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="login" maxlength="36" placeholder="Login" class="common-input-field" style="width: 190px;"></form:input><br>
                    <form:errors path="login"></form:errors>
                </div>
            </spring:bind>

            <form:label path="password">Пароль</form:label>
            <spring:bind path="password">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password" maxlength="24" placeholder="Password" class="common-input-field" style="width: 190px;"></form:input><br>
                    <form:errors path="password"></form:errors>
                </div>
            </spring:bind>

            <input type="submit" name="btnLogin" value="Войти" class="common-button"/>
            </div>
        </form:form>
    </div>


</body>
</html>
