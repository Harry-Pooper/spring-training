<%@ page import="main.java.Dict" %>
<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: mrlz
  Date: 31.03.2018
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>JobToad</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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

<div class="main_box_center">
    <form:form method="POST" modelAttribute="authorization">
        <div class="table-mid">
            <p class="edit-mid">
                <p class="label-main">Регистрация</p>
                <p class="edit-text label">Логин</p>
                <spring:bind path="login">
                    <div class="${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="login" maxlength="36" placeholder="Login" class="edit-input"></form:input>
                        <form:errors path="login"></form:errors>
                    </div>
                </spring:bind>

                <p class="edit-text label">Пароль</p>
                <spring:bind path="password">
                    <div class="${status.error ? 'has-error' : ''}">
                        <form:input type="password" path="password" maxlength="24" placeholder="Password" class="edit-input"></form:input>
                        <form:errors path="password"></form:errors>
                    </div>
                </spring:bind>

                <p class="edit-text label">Email</p>
                <spring:bind path="email">
                    <div class="${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="email" maxlength="60" placeholder="E-mail" class="edit-input"></form:input>
                        <form:errors path="email"></form:errors>
                    </div>
                </spring:bind>

                <p class="edit-text label">Секретный вопрос</p>
                <spring:bind path="secretQuestion">
                    <div class="${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="secretQuestion" maxlength="60" placeholder="Secret Question" class="edit-input"></form:input>
                        <form:errors path="secretQuestion"></form:errors>
                    </div>
                </spring:bind>

                <p class="edit-text label">Ответ</p>
                <spring:bind path="secretAnswer">
                    <div class="${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="secretAnswer" maxlength="60" placeholder="Secret Answer" class="edit-input"></form:input>
                        <form:errors path="secretAnswer"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="accType">
                    <div class="${status.error ? 'has-error' : ''}">
                        <form:radiobutton path="accType" value="<%=Dict.USER%>" checked="checked" class="edit-text label" label="Соискатель"></form:radiobutton>
                        <form:radiobutton path="accType" cssclass="edit-text label" id="org-radio" value="<%=Dict.ORG%>" label="Организация"></form:radiobutton>
                        <form:errors path="accType"></form:errors>
                    </div>
                </spring:bind>

                <p class="label-small" ><input type="checkbox" checked style="margin-top: 10px; margin-bottom: 10px;">Я принимаю условия пользовательского </br> соглашения</p>

            <div class="center-butt">
                <input type="submit" name="btnSend" value="Создать учетную запись" class="acc-button">
            </div>

            <p><span style="text-decoration:underline; padding-left: 25%">Отменить</span></p>
            </div>
        </div>
    </form:form>
</div>

</body>
</html>
