<%@ page import="main.java.service.OrgRegistrationService" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ page import="main.java.dao.UserDao" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: mrlz
  Date: 08.04.2018
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="orgRegistrationService" class="main.java.service.OrgRegistrationService"/>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/about.css"/>"/>
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
            <div class="text_box_left">
                <a class="white_link" href="/responses?id=<%=UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId().toString()%>">Мои отклики</a>
            </div>
        </div>
    </a:if>
    <a:if test="${rdbo.accType == organType}">
        <div class="sub_header">
            <div class="text_box_left">
                <a class="white_link" href="/vacansy?id=<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString()%>">Вакансии</a>
            </div>
            <div class="text_box_left">
                <a class="white_link" href="/orgResponses?id=<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString()%>">Мои отклики</a>
            </div>
        </div>
    </a:if>
</sec:authorize>
<div class="main_box">
    <form:form modelAttribute="orgInfo">
    <a href="javascript:history.back()" class="black_link">Назад</a>
    <div class="about_content_box">
        <div class="about_header">
            <div class="about_left_header">
                <div class="about_content_title">
                    <a:if test="${orgInfo.getorgName() == ''}">
                        <div class="has-error">Необходимо заполнить это поле в личном кабинете!</div>
                    </a:if>
                    <a:if test="${orgInfo.getorgName() != ''}">
                        <a href="/profileorg?id=${orgInfo.profileorgId}" class="black_link">${orgInfo.getorgName()}</a> <br>
                    </a:if>
                </div>
            </div>
            <a:if test="${orgInfo.imageUrl == null}">
                <img src="<a:url value="/img/not_defined.png"/>" class="about_img"/>
            </a:if>
            <a:if test="${orgInfo.imageUrl != null}">
                <img src="<a:url value="/img/${orgInfo.imageUrl}"/>" class="about_img"/>
            </a:if>
        </div>
        <div class="about_content_main_info">
            <div class="about_content_main_info_title">
                Тип Организации:
            </div>
            <form:label path="orgType">${orgRegistrationService.getOrgType(orgInfo.orgType)}</form:label>
            <div class="about_content_main_info_title">
                Количество работников:
            </div>
            <form:label path="countMem">${orgRegistrationService.getcountMem(orgInfo.getcountMem())}</form:label><br>
            <div class="about_content_main_info_title">
                Адрес:
            </div>
            г. ${cityName} &nbsp;
            <form:label path="adress">${orgInfo.adress} </form:label> <br>
            <div class="about_content_main_info_title">
                Сайт:
            </div>
            <form:label path="link">${orgInfo.link}</form:label><br>
            <div class="about_content_main_info_title">
                Об организации:
            </div>
            <form:label path="aboutOrg">${orgInfo.aboutOrg}</form:label><br>
            <div class="about_content_main_info_title">
                Плюсы работы в компании
            </div>
            <sec:authorize access="isAuthenticated()">
                <a:if test="${rdbo.accType == userType}">
                    Транспортная доступность <form:label path="transport">${orgInfo.transport}</form:label> <a href="/profileorg/add?orgId=${orgInfo.profileorgId}&type=1" class="black_link">+</a> <br>
                    Карьерный рост <form:label path="career">${orgInfo.career}</form:label> <a href="/profileorg/add?orgId=${orgInfo.profileorgId}&type=2" class="black_link">+</a> <br>
                    Корпоративный дух <form:label path="spirit">${orgInfo.spirit}</form:label> <a href="/profileorg/add?orgId=${orgInfo.profileorgId}&type=3" class="black_link">+</a> <br>
                    Своевременная з/п <form:label path="salary">${orgInfo.salary}</form:label> <a href="/profileorg/add?orgId=${orgInfo.profileorgId}&type=4" class="black_link">+</a> <br>
                    Интересные проекты <form:label path="projects">${orgInfo.projects}</form:label> <a href="/profileorg/add?orgId=${orgInfo.profileorgId}&type=5" class="black_link">+</a> <br>
                </a:if>
                <a:if test="${rdbo.accType == organType}">
                    Транспортная доступность <form:label path="transport">${orgInfo.transport}</form:label> <br>
                    Карьерный рост <form:label path="career">${orgInfo.career}</form:label> <br>
                    Корпоративный дух <form:label path="spirit">${orgInfo.spirit}</form:label> <br>
                    Своевременная з/п <form:label path="salary">${orgInfo.salary}</form:label> <br>
                    Интересные проекты <form:label path="projects">${orgInfo.projects}</form:label> <br>
                </a:if>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                Транспортная доступность <form:label path="transport">${orgInfo.transport}</form:label> <br>
                Карьерный рост <form:label path="career">${orgInfo.career}</form:label> <br>
                Корпоративный дух <form:label path="spirit">${orgInfo.spirit}</form:label> <br>
                Своевременная з/п <form:label path="salary">${orgInfo.salary}</form:label> <br>
                Интересные проекты <form:label path="projects">${orgInfo.projects}</form:label> <br>
            </sec:authorize>
            <a:if test="${orgInfo.profileorgId == authenticatedUser.profileorgId}">
                <br>
                <br>
                <br>
                <a href="profileorg/edit?id=${authenticatedUser.profileorgId}" class="black_link">Редактировать</a>
            </a:if>
        </div>
    </div>
    </form:form>
    <div class="bottom_info_block">
        <div class="bottom_info_text">
            Сервис для поиска работы JobToad<br><br>
            Разработано командой JobToad © 2018<br>
            Менеджер проекта: Надымов Никита
        </div>
    </div>
</div>
</body>
</html>
