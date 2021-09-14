<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 21.05.2018
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JobToad</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/cv.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/about.css"/>"/>
    <script>
        $(function() {
            $('#sendResume').on('click', function(){
                var query = "/response/send?vcId=${vcInfo.vcId}";
                window.open(query, 'new', 'width=600, height=500')
            });
        });
    </script>
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
    <a href="javascript:history.back()" class="black_link">Назад</a>
    <div class="about_content_box">
        <div class="about_header">
            <div class="about_left_header" style="font-size: 32px;">
                <p style="font-size: 32px;">${vcInfo.titlevc}</p>
                <p class="label-mid">
                    <a:if test="${orgInfo.getorgName() == ''}">
                        <div class="has-error">Необходимо заполнить это поле в личном кабинете!</div>
                    </a:if>
                    <a:if test="${orgInfo.getorgName() != ''}">
                        <a href="/profileorg?id=${orgInfo.profileorgId}" class="black_link">${orgInfo.getorgName()}</a> <br>
                    </a:if>
                </p>
                <div class="about_content_sub_title">
                    <a:if test="${paymentInfo.paymentType == 301}">
                        От ${paymentInfo.paymentFrom} руб.
                    </a:if>
                    <a:if test="${paymentInfo.paymentType == 331}">
                        До ${paymentInfo.paymentTo} руб.
                    </a:if>
                    <a:if test="${paymentInfo.paymentType == 361}">
                        От ${paymentInfo.paymentFrom} руб. до ${paymentInfo.paymentTo} руб.
                    </a:if>
                    <a:if test="${paymentInfo.paymentType == 391}">
                        Не указано
                    </a:if>
                </div>
                <sec:authorize access="isAuthenticated()">
                    <a:if test="${rdbo.accType == userType}">
                        <button id="sendResume" class="common-button">Отправить отклик</button>
                    </a:if>
                </sec:authorize>
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
                Город:
            </div>
            ${city} <br>
            <div class="about_content_main_info_title">
                Ключевые навыки:
            </div>
            <a:if test="${tags.size() == 0}">
                <div class="has-error">Необходимо указать хотя бы один ключевой навык в поле "Ключевые навыки" в редактировании!</div>
            </a:if>
            <a:if test="${tags.size() > 0}">
                <a:forEach var="tag" items="${tags}" varStatus="it">
                    ${tag} &nbsp;
                </a:forEach>
            </a:if><br>
            <div class="about_content_main_info_title">
                О компании:
            </div>
            <a:if test="${orgInfo.aboutOrg == ''}">
                <div class="has-error">Необходимо заполнить поле "О компании" в личном кабинете!</div>
            </a:if>
            <a:if test="${orgInfo.aboutOrg != ''}">
                ${orgInfo.aboutOrg}<br>
            </a:if>
            <div class="about_content_main_info_title">
                О вакансии:
            </div>
            <a:if test="${vcInfo.aboutvc == ''}">
                <div class="has-error">Необходимо заполнить поле "О вакансии" в редактировании!</div>
            </a:if>
            <a:if test="${vcInfo.aboutvc != ''}">
                ${vcInfo.aboutvc} <br>
            </a:if>
            <div class="about_content_main_info_title">
                Требования к кандидату:
            </div>
            <a:if test="${vcInfo.demands == ''}">
                <div class="has-error">Необходимо заполнить поле "Требования к кандидату" в редактировании!</div>
            </a:if>
            <a:if test="${vcInfo.demands != ''}">
                ${vcInfo.demands} <br>
            </a:if>
            <div class="about_content_main_info_title">
                Контактное лицо
            </div>
            <div class="about_content_main_info_title">
                ФИО:
            </div>
            <a:if test="${vcInfo.vcSecName == '' && vcInfo.vcName == ''}">
                <div class="has-error">Необходимо указать имя контактного лица по вакансии в редактировании!</div>
            </a:if>
            <a:if test="${vcInfo.vcSecName != '' || vcInfo.vcName != ''}">
                ${vcInfo.vcSecName} ${vcInfo.vcName}<br>
            </a:if>
            <div class="about_content_main_info_title">
                Контактный телефон:
            </div>
            <a:if test="${vcInfo.vcTeleph == ''}">
                <div class="has-error">Необходимо указать телефон контактонго лица в редактировании!</div>
            </a:if>
            <a:if test="${vcInfo.vcTeleph != ''}">
                ${vcInfo.vcTeleph}<br>
            </a:if>
            <div class="about_content_main_info_title">
                E-mail:
            </div>
            <a:if test="${vcInfo.vcEmail == ''}">
                <div class="has-error">Необходимо указать E-mail контактоного лица в редактировании!</div>
            </a:if>
            <a:if test="${vcInfo.vcEmail != ''}">
                ${vcInfo.vcEmail}<br>
            </a:if>
            <div class="about_content_main_info_title">
                Адрес:
            </div>
            ${vcInfo.adress}<br>
        </div>
    </div>
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