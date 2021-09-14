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
    <div class="cv_view_img">
        <a:if test="${cvInfo.cvImage == null}">
            <img src="<a:url value="/img/not_defined.png"/>" class="cv_view_img_inside"/>
        </a:if>
        <a:if test="${cvInfo.cvImage != null}">
            <img src="<a:url value="/img/${cvInfo.cvImage}"/>" class="cv_view_img_inside"/>
        </a:if>
    </div>
    <div class="cv_view_text">
        <p style="font-size: 32px;">
        <a:if test="${userInfo.firstName == '' && userInfo.secondName == '' && userInfo.lastName == ''}">
            <div class="has-error">Необходимо заполнить это поле в личном кабинете!</div>
        </a:if>
            <a:if test="${userInfo.firstName != '' || userInfo.secondName != '' || userInfo.lastName != ''}">
                ${userInfo.firstName} ${userInfo.secondName} ${userInfo.lastName} <br>
            </a:if>
        </p>
        <div class="cv_text">

            <p class="label-mid">
            <a:if test="${position.positionName == ''}">
                <div class="has-error">Необходимо выбрать должность в редактировании!</div>
            </a:if>
            <a:if test="${position.positionName != ''}">
                ${position.positionName} <br>
            </a:if>
            </div>
            <br><br>
            <p class="label-small-text">Название резюме:</p>
            ${cvInfo.title}
            <br>

            <p class="label-small-text">Дата рождения:</p>
            ${userInfo.birthDate} <br>

            <p class="label-small-text">Готовность к командировкам:</p>
            ${business_trip} <br>

            <p class="label-small-text">Образование:</p>
            ${degree} <br>

            <p class="label-small-text">Город:</p>
            ${city} <br>

            <p class="label-small-text">Ключевые навыки:</p>
            <a:if test="${tags.size() == 0}">
                <div class="has-error">Необходимо указать хотя бы один ключевой навык в поле "Ключевые навыки" в редактировании!</div>
            </a:if>
            <a:if test="${tags.size() > 0}">
                <a:forEach var="tag" items="${tags}" varStatus="it">
                    ${tag} &nbsp;
                </a:forEach>
            </a:if><br>

            <p class="label-small-text">Контактный телефон:</p>
            <a:if test="${cvInfo.phone == ''}">
                <div class="has-error">Необходимо заполнить поле "Контактный телефон" в редактировании!</div>
            </a:if>
            <a:if test="${cvInfo.phone != ''}">
                ${cvInfo.phone}<br>
            </a:if>

            <p class="label-small-text">E-mail:</p>
            <a:if test="${cvInfo.email == ''}">
                <div class="has-error">Необходимо заполнить поле "E-mail" в редактировании!</div>
            </a:if>
            <a:if test="${cvInfo.email != ''}">
                ${cvInfo.email}<br>
            </a:if>

        <p class="label-small-text">Опыт работы: </p>
            ${cvInfo.experience} <br>
            О себе: <br>
            <a:if test="${cvInfo.about == ''}">
                <div class="has-error">Необходимо заполнить поле "О себе" в редактировании!</div>
            </a:if>
            <a:if test="${cvInfo.about != ''}">
                ${cvInfo.about} <br>
            </a:if>

        <p class="label-small-text">Желаемая з/п: </p>
            <a:if test="${paymentInfo.paymentType == 301}">
                От ${paymentInfo.paymentFrom}
            </a:if>
            <a:if test="${paymentInfo.paymentType == 331}">
                До ${paymentInfo.paymentTo}
            </a:if>
            <a:if test="${paymentInfo.paymentType == 361}">
                От ${paymentInfo.paymentFrom} до ${paymentInfo.paymentTo}
            </a:if>
            <a:if test="${paymentInfo.paymentType == 391}">
                Не указано
            </a:if>
            <br>
        </div>
    </div>
    <a:if test="${userInfo.accountId == loggedInUser.accountId}">
        <div class="cv_view_control_panel">
                <a:if test="${!cvInfo.isPublic}">
                    <a href="/resume/edit?cvId=${cvInfo.cvId}&profileId=${cvInfo.profileId}" class="black_link">Редактировать резюме</a><br>
                </a:if>
                <a href="/resume/delete?cvId=${cvInfo.cvId}&profileId=${cvInfo.profileId}" class="black_link">Удалить резюме</a><br>
                <a:if test="${!cvInfo.isPublic}">
                    <form method="post">
                        <button type="submit" name="cvId" value="${cvInfo.cvId}" class="cv_submit_link_button">Опубликовать</button>
                        <input type="hidden" name="profileId" value="${cvInfo.profileId}"/>
                    </form>
                </a:if>
        </div>
    </a:if>
</div>
</body>
</html>