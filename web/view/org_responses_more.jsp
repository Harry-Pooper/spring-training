<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: mrlz
  Date: 27.03.2018
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="CommonService" class="main.java.service.CommonService"/>
<jsp:useBean id="VacansyDao" class="main.java.dao.VacansyDao"/>
<jsp:useBean id="ResumeDao" class="main.java.dao.ResumeDao"/>
<html>
<head>
    <title>${title}</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/cv.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/my_responses.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/about.css"/>"/>
    <script>
        $(function() {
            $('#searchButton').on('click', function(){
                var query = "";
                if($('#searchType').val() === "resume"){
                    query = "/search/resume";
                    var titleName = $('#searchName').val()
                    if(titleName !== "" || titleName !== null){
                        query = query + "?title=" + titleName;
                    }
                    window.open(query, "_self");
                }
                if($('#searchType').val() === "vacancy"){
                    query = "/search/vacansy";
                    var titleName = $('#searchName').val()
                    if(titleName !== "" || titleName !== null){
                        query = query + "?title=" + titleName;
                    }
                    window.open(query, "_self");
                }
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
        <div class="about_content_main_info_title">
            Название вакансии:
        </div>
        ${VacansyDao.getOrgVacansyById(responseInfo.vcId).titlevc} &nbsp; &nbsp; &nbsp; &nbsp; <a href="/vacansy/view?vcId=${responseInfo.vcId}" class="black_link">Посмотреть вакансию</a><br>
        <div class="about_content_main_info_title">
            Название резюме:
        </div>
        ${ResumeDao.getUserResumeById(responseInfo.cvId).title} &nbsp; &nbsp; &nbsp; &nbsp; <a href="/resume/view?cvId=${responseInfo.cvId}" class="black_link">Посмотреть резюме</a> <br>
        <div class="about_content_main_info_title">
            Дата отправки резюме:
        </div>
        ${responseInfo.dateSent}<br>
        <div class="about_content_main_info_title">
            Прикреплённое письмо:
        </div>
        ${responseInfo.letter} <br>
        <a:if test="${responseInfo.status == 13262}">
            <form method="post">
                <input type="hidden" name="vrId" value="${responseInfo.vrId}"/>
                <button type="submit" name="accept" value="accept" class="common-button">Пригласить на собеседование</button>
                <button type="submit" name="reject" value="reject" class="common-button">Отклонить</button>
            </form>
        </a:if>
    </div>
</div>
</body>
</html>
