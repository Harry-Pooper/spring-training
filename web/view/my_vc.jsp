<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 21.05.2018
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="UserDao" class="main.java.dao.UserDao"/>
<jsp:useBean id="PaymentDao" class="main.java.dao.PaymentDao"/>
<jsp:useBean id="OrgDao" class="main.java.dao.OrgDao"/>
<jsp:useBean id="Dict" class="main.java.Dict"/>
<jsp:useBean id="CitiesDao" class="main.java.dao.CitiesDao"/>
<html>
<head>
    <title>JobToad</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
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
    <!--Слева основное дерьмо-->
    <div class="cv_div">
        <a:if test="${isEmpty}">
            <div class="no_cv_div">
                <div class="cv_title_text">
                    На данный момент у вас нет никаких вакансий.
                </div>
            </div>
        </a:if>
        <a:if test="${!isEmpty}">
            <div class="cv_title_text">
                Опубликованные вакансии
            </div>
            <a:if test="${publishedVacansyCount == 0}">
                <div class="cv_text">
                    У вас нет опубликованных вакансий.
                </div>
            </a:if>
            <a:forEach var="vc" items="${vacansyList}" varStatus="it">
                <a:if test="${vc.isPublicvc}">
                    <div class="cv_block">
                        <a:if test="${OrgDao.findOrgByProfileId(vc.profileorgId).imageUrl == null}">
                            <img src="<a:url value="/img/not_defined.png"/>" class="cv_img"/>
                        </a:if>
                        <a:if test="${OrgDao.findOrgByProfileId(vc.profileorgId).imageUrl != null}">
                            <img src="<a:url value="/img/${OrgDao.findOrgByProfileId(vc.profileorgId).imageUrl}"/>" class="cv_img"/>
                        </a:if>
                        <div class="cv_block_info">
                            <div class="cv_block_title">
                                <a:if test="${vc.titlevc == null}">
                                    Без названия
                                </a:if>
                                <a:if test="${vc.titlevc != null}">
                                    ${vc.titlevc}
                                </a:if>
                            </div>
                            <div class="cv_block_text">
                                Наименование организации: ${OrgDao.findOrgByProfileId(vc.profileorgId).orgName}<br>
                                Город: ${CitiesDao.getCityNameById(OrgDao.findOrgByProfileId(vc.profileorgId).cityId)} <br>
                                Зарплата:
                                <a:if test="${vc.stype == 301}">
                                    от ${vc.sfrom} руб.
                                </a:if>
                                <a:if test="${vc.stype == 331}">
                                    до ${vc.sto} руб.
                                </a:if>
                                <a:if test="${vc.stype == 361}">
                                    от ${vc.sfrom} руб. до ${vc.sto} руб.
                                </a:if>
                                <a:if test="${vc.stype == 391}">
                                    не указано
                                </a:if><br>
                            </div>
                        </div>
                        <div class="cv_block_control_panel">
                            <div class="cv_block_text">
                                <a class="black_link" href="/vacansy/view?vcId=${vc.vcId}">Посмотреть</a> <br/>
                                <a class="black_link" href="/vacansy/delete?vcId=${vc.vcId}&profileorgId=${vc.profileorgId}">Удалить</a><br/>
                            </div>
                            <div class="cv_block_control_panel_info">
                                Опубликовано: ${vc.datePublishedvc}<br>
                                Просмотров: ${vc.watches}<br>
                            </div>
                        </div>
                    </div>
                </a:if>
            </a:forEach>
            <div class="cv_title_text">
                Не опубликованные вакансии
            </div>
            <a:if test="${notPublishedVacansyCount == 0}">
                <div class="cv_text">
                    У вас нет не опубликованных вакансий
                </div>
            </a:if>
            <a:forEach var="vc" items="${vacansyList}" varStatus="it">
                <a:if test="${!vc.isPublicvc}">
                    <div class="cv_block">
                        <a:if test="${OrgDao.findOrgByProfileId(vc.profileorgId).imageUrl == null}">
                            <img src="<a:url value="/img/not_defined.png"/>" class="cv_img"/>
                        </a:if>
                        <a:if test="${OrgDao.findOrgByProfileId(vc.profileorgId).imageUrl != null}">
                            <img src="<a:url value="/img/${OrgDao.findOrgByProfileId(vc.profileorgId).imageUrl}"/>" class="cv_img"/>
                        </a:if>
                        <div class="cv_block_info">
                            <div class="cv_block_title">
                                <a:if test="${vc.titlevc == null}">
                                    Без названия
                                </a:if>
                                <a:if test="${vc.titlevc != null}">
                                    ${vc.titlevc}
                                </a:if>
                            </div>
                            <div class="cv_block_text">
                                Наименование организации: ${OrgDao.findOrgByProfileId(vc.profileorgId).orgName}<br>
                                Город: ${CitiesDao.getCityNameById(OrgDao.findOrgByProfileId(vc.profileorgId).cityId)} <br>
                                Зарплата:
                                <a:if test="${vc.stype == 301}">
                                    от ${vc.sfrom} руб.
                                </a:if>
                                <a:if test="${vc.stype == 331}">
                                    до ${vc.sto} руб.
                                </a:if>
                                <a:if test="${vc.stype == 361}">
                                    от ${vc.sfrom} руб. до ${vc.sto} руб.
                                </a:if>
                                <a:if test="${vc.stype == 391}">
                                    не указано
                                </a:if><br>
                            </div>
                        </div>
                        <div class="cv_block_control_panel">
                            <div class="cv_block_text">
                                <a class="black_link" href="/vacansy/view?vcId=${vc.vcId}">Посмотреть</a> <br/>
                                <a class="black_link" href="/vacansy/edit?vcId=${vc.vcId}&profileorgId=${vc.profileorgId}">Редактировать</a><br/>
                                <a class="black_link" href="/vacansy/delete?vcId=${vc.vcId}&profileorgId=${vc.profileorgId}">Удалить</a><br/>
                                <form method="post">
                                    <button type="submit" name="vcId" value="${vc.vcId}" class="cv_submit_link_button">Опубликовать</button>
                                </form>
                            </div>
                            <div class="cv_block_control_panel_info">
                                    Опубликовано: ${vc.datePublishedvc}<br>
                                    Просмотров: ${vc.watches}<br>
                            </div>
                        </div>
                    </div>
                </a:if>
            </a:forEach>
        </a:if>
    </div>
    <!--Правая контрольная панель-->
    <div class="control_panel">
        <a class="black_link margintop10" href="createVacansy?id=<%=request.getParameter("id")%>">Создать вакансию</a>
    </div>
</div>
</body>
</html>
