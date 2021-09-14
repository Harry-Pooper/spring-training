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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="<a:url value="/js/datepicker.min.js"/>"></script>
    <script src="<a:url value="/js/i18n/datepicker.en.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/cv.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/search.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <script>
        $(function() {
            $('#searchButton').on('click', function(){
                var query = "";
                query = "/search/vacansy";
                var count = 0;
                var titleName = $('#title').val();
                var orgName = $('#orgName').val();
                var sto = $('#sto').val();
                var city = $('#city').val();
                var sfrom = $('#sfrom').val();
                var sto = $('#sto').val();
                var tagss = $('#tagss').val();
                if(!isNullOrEmpty(titleName)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "title=" + titleName;
                }
                if(!isNullOrEmpty(orgName)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "orgName=" + orgName;
                }
                if(!isNullOrEmpty(city)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "city=" + city;
                }
                if(!isNullOrEmpty(sfrom)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "sfrom=" + sfrom;
                }
                if(!isNullOrEmpty(sto)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "sto=" + sto;
                }
                if(!isNullOrEmpty(tagss)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "tagss=" + tagss;
                }
                window.open(query, "_self");
            });
        });

        function addSeparator(count, str){
            if(count === 0){
                str = str + "?";
            } else {
                str = str + "&";
            }
            return str;
        }

        function isNullOrEmpty(e){
            return e === null || e === "";
        }

        function isNullOrZero(e){
            return e === null || e === "0";
        }
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
    <!--Слева основное дерьмо-->
    <div class="cv_div_search">
        <a:if test="${res.size() == 0}">
            <div class="cv_title_text">По вашему запросу не найдено ни одной записи</div>
        </a:if>
        <a:forEach var="vc" items="${res}" varStatus="it">
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
                    </div>
                    <div class="cv_block_control_panel_info">
                        Опубликовано: ${vc.datePublishedvc}<br>
                        Просмотров: ${vc.watches}<br>
                    </div>
                </div>
            </div>
        </a:forEach>
    </div>
    <!--Правая контрольная панель-->
    <div class="control_panel_search">
        Параметры поиска<br>
        Название вакансии<br>
        <input type="text" maxlength="50" id="title" class="common-input-field" placeholder="Название" value="${title}"><br>
        Название организации<br>
        <input type="text" maxlength="50" id="orgName" class="common-input-field" placeholder="Название организации" value="${orgName}"><br>

        Город<br>
        <select id="city" class="search-select-field width-250">
            <option value="">Не выбрано</option>
            <a:forEach var="cit" items="${citiesList}" varStatus="it">
                <option value="${cit.cityId}" <a:if test="${cit.cityId == city}">selected</a:if>>${cit.cityName}</option>
            </a:forEach>
        </select><br>

        Зарплата<br>
        <input type="number" maxlength="50" id="sfrom" placeholder="от" class="common-input-small-field" value="${sfrom}"> руб.<br>
        <input type="number" maxlength="50" id="sto" placeholder="до" class="common-input-small-field" value="${sto}"> руб.<br>
        Ключевые навыки <br>
        <input type="text" maxlength="50" id="tagss" class="common-input-field" placeholder="Вводите через запятую" value="${tagss}"><br>

        <button id="searchButton" class="common-button">Поиск</button>

       <input type="hidden" id="citiesList" name="citiesList" value="${cities}"/>
    </div>
</div>
</body>
</html>