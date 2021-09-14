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
<jsp:useBean id="Dict" class="main.java.Dict"/>
<html>
<head>
    <title>JobToad</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/cv.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/search.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <script>
        $(function() {
            $('#searchButton').on('click', function(){
                var query = "";
                query = "/search/resume";
                var count = 0;
                var titleName = $('#title').val();
                var firstName = $('#firstName').val();
                var secondName = $('#secondName').val();
                var lastName = $('#lastName').val();
                var position = $('#position').val();
                var tags = $('#tags').val();
                if(!isNullOrEmpty(titleName)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "title=" + titleName;
                }
                if(!isNullOrEmpty(firstName)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "firstName=" + firstName;
                }
                if(!isNullOrEmpty(secondName)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "secondName=" + secondName;
                }
                if(!isNullOrEmpty(lastName)) {
                    query = addSeparator(count, query);
                    count++;
                    query = query + "lastName=" + lastName;
                }
                if(!isNullOrZero(position)){
                    query = addSeparator(count, query);
                    count++;
                    query = query + "position=" + position;
                }

                if(!isNullOrEmpty(tags)) {
                    query = addSeparator(count, query);
                    count++;
                    query = query + "tags=" + tags;
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
        <a:forEach var="cv" items="${res}" varStatus="it">
            <div class="cv_block">
                <a:if test="${cv.cvImage == null}">
                    <img src="<a:url value="/img/not_defined.png"/>" class="cv_img"/>
                </a:if>
                <a:if test="${cv.cvImage != null}">
                    <img src="<a:url value="/img/${cv.cvImage}"/>" class="cv_img"/>
                </a:if>
                <div class="cv_block_info">
                    <div class="cv_block_title">
                        <a:if test="${cv.title == null}">
                            Без названия
                        </a:if>
                        <a:if test="${cv.title != null}">
                            ${cv.title}
                        </a:if>
                    </div>
                    <div class="cv_block_text">
                        ФИО: ${UserDao.findUserByProfileId(cv.profileId).getFirstName()} ${UserDao.findUserByProfileId(cv.profileId).getSecondName()} ${UserDao.findUserByProfileId(cv.profileId).getLastName()}
                    </div>
                    <div class="cv_block_text">
                        Опыт работы: ${cv.experience} лет
                    </div>
                    <div class="cv_block_text">
                        Зарплата:
                        <a:if test="${PaymentDao.getPaymentById(cv.paymentId).paymentType == 301}">
                            От ${PaymentDao.getPaymentById(cv.paymentId).paymentFrom} руб.
                        </a:if>
                        <a:if test="${PaymentDao.getPaymentById(cv.paymentId).paymentType == 331}">
                            До ${PaymentDao.getPaymentById(cv.paymentId).paymentTo} руб.
                        </a:if>
                        <a:if test="${PaymentDao.getPaymentById(cv.paymentId).paymentType == 361}">
                            От ${PaymentDao.getPaymentById(cv.paymentId).paymentFrom} руб. до ${PaymentDao.getPaymentById(cv.paymentId).paymentTo} руб.
                        </a:if>
                        <a:if test="${PaymentDao.getPaymentById(cv.paymentId).paymentType == 391}">
                            Не указано
                        </a:if>
                    </div>
                </div>
                <div class="cv_block_control_panel">
                    <div class="cv_block_text">
                        <a class="black_link" href="/resume/view?cvId=${cv.cvId}">Посмотреть</a><br/>
                    </div>
                    <div class="cv_block_control_panel_info">
                            ${cv.datePublished}<br>
                        Просмотры:${cv.watches}
                    </div>
                </div>
            </div>
        </a:forEach>
    </div>
    <!--Правая контрольная панель-->
    <div class="control_panel_search">
        Параметры поиска<br><br>
        Название<br>
        <input type="text" maxlength="50" id="title" class="common-input-field" placeholder="Название" value="${title}"><br>
        Фамилия<br>
        <input type="text" maxlength="50" id="firstName" class="common-input-field" placeholder="Фамилия" value="${firstName}"><br>
        Имя<br>
        <input type="text" maxlength="50" id="secondName" class="common-input-field" placeholder="Имя" value="${secondName}"><br>
        Отчество<br>
        <input type="text" maxlength="50" id="lastName" class="common-input-field" placeholder="Отчество" value="${lastName}"><br>
        Должность<br>
        <select id="position" class="search-select-field width-250">
            <option value="0">Не выбрано</option>
            <a:forEach var="pos" items="${positions}" varStatus="it">
                <option value="${pos.positionId}" <a:if test="${pos.positionId == position}">selected</a:if>>${pos.positionName}</option>
            </a:forEach>
        </select><br>
        Ключевые навыки <br>
        <input type="text" maxlength="50" id="tags" class="common-input-field" placeholder="Вводите через запятую" value="${tags}"><br>

        <button id="searchButton" class="common-button">Поиск</button>
    </div>
</div>
</body>
</html>