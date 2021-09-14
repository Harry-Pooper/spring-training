<%@ page import="main.java.service.UserRegistrationService" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.dao.OrgDao" %>
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
    <form:form modelAttribute="userInfo">
    <a href="javascript:history.back()" class="black_link">Назад</a>
    <div class="about_content_box">
        <div class="about_header">
            <div class="about_left_header">
                <div class="about_content_title">
                    <form:label path="firstName">${userInfo.firstName} </form:label>
                    <form:label path="secondName">${userInfo.secondName} </form:label>
                    <form:label path="lastName">${userInfo.lastName}</form:label><br>
                </div>
            </div>
            <a:if test="${userInfo.photoUrl == null}">
                <img src="<a:url value="/img/not_defined.png"/>" height="200" width="200" class="about_img"/>
            </a:if>
            <a:if test="${userInfo.photoUrl != null}">
                <img src="<a:url value="/img/${userInfo.photoUrl}"/>" height="200" width="200" class="about_img"/>
            </a:if>
        </div>
        <div class="about_content_main_info">
            <a:if test="${userInfo.hideBirthDate == false}">
                <div class="about_content_main_info_title">
                    Дата рождения:
                </div>
                <form:label path="birthDate">${userInfo.birthDate}</form:label><br>
            </a:if>
            <div class="about_content_main_info_title">
                Город:
            </div>
            ${cityName} <br>
            <a:if test="${positions.size() > 0}">
                <div class="about_content_main_info_title">
                    Должности: <br>
                </div>
                <a:forEach var="pos" items="${positions}" varStatus="it">
                    ${pos.positionName} &nbsp; &nbsp;
                </a:forEach> <br>
            </a:if>
            <a:if test="${tags.size() > 0}">
                <div class="about_content_main_info_title">
                    Ключевые навыки: <br>
                </div>
                <a:forEach var="tag" items="${tags}" varStatus="it">
                    ${tag}&nbsp; &nbsp;
                </a:forEach> <br>
            </a:if>
            <a:if test="${phones.size() > 0}">
                <div class="about_content_main_info_title">
                    <a:if test="${phones.size() == 1}">Контактный телефон:</a:if>
                    <a:if test="${phones.size() > 1}">Контактные телефоны:</a:if>
                </div>
                <div id="phones">
                    <a:forEach var="phone" items="${phones}">
                        ${phone}<br>
                    </a:forEach>
                </div>
            </a:if>
            <a:if test="${emails.size() > 0}">
                <div class="about_content_main_info_title">
                    E-mail:
                </div>
                <div id="emails">
                    <a:forEach var="email" items="${emails}">
                        ${email}<br>
                    </a:forEach>
                </div>
            </a:if>
            <a:if test="${faxes.size() > 0}">
                <div class="about_content_main_info_title">
                    Факс:
                </div>
                <div id="faxes">
                    <a:forEach var="fax" items="${faxes}">
                        ${fax}<br>
                    </a:forEach>
                </div>
            </a:if>
            <a:if test="${userInfo.aboutUser.length() > 0}">
                <div class="about_content_main_info_title">
                    О себе:
                </div>
                <form:label path="aboutUser">${userInfo.aboutUser}</form:label><br>
            </a:if>
            <div class="about_content_main_info_title">
                Образование:
            </div>
            <form:label path="degree">${userRegistrationService.getDegreeType(userInfo.degree)}</form:label><br>
            <div class="about_content_main_info_title">
                Готовность к командировкам:
            </div>
            <form:label path="businessTrips">${userRegistrationService.getBusinessTripType(userInfo.businessTrips)}</form:label><br>

            <a:if test="${userInfo.profileId == authenticatedUser.profileId}">
                <br><a href="profile/edit?id=${authenticatedUser.profileId}">Редактировать</a>
            </a:if>
        </div>
    </form:form>

    <input type="hidden" id="phonesList" value="${phones}"/>
    <input type="hidden" id="emailsList" value="${emails}"/>
    <input type="hidden" id="faxesList" value="${faxes}"/>
</div>
</body>
</html>
