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
<html>
    <head>
        <title>${title}</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
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
                $('#addResumeButton').on('click', function(){
                    query = "/login";
                    window.open(query, "_self");
                });
                $('#addVacancyButton').on('click', function () {
                    query = "/login";
                    window.open(query, "_self");
                });
                $('#addLoginedResumeButton').on('click', function(){
                    query = "/createResume?id=" + $('#profileIdLogined').val();
                    window.open(query, "_self");
                });
                $('#addLoginedVacancyButton').on('click', function () {
                    query = "/createVacansy?id=" + $('#profileorgIdLogined').val();
                    window.open(query, "_self");
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
            <div class="text_box_right"><a class="black_link" href="/logout">??????????</a></div>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <div class="text_box_right"><a class="black_link" href="/login">??????????</a></div>
            <div class="text_box_right"><a class="black_link" href="/registration">??????????????????????</a></div>
        </sec:authorize>
    </div>
    <sec:authorize access="isAuthenticated()">
        <a:if test="${rdbo.accType == userType}">
            <div class="sub_header">
                <div class="text_box_left">
                    <a class="white_link" href="/resume?id=<%=UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId().toString()%>">?????? ????????????</a>
                </div>
                <div class="text_box_left">
                    <a class="white_link" href="/responses?id=<%=UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId().toString()%>">?????? ??????????????</a>
                </div>
            </div>
        </a:if>
        <a:if test="${rdbo.accType == organType}">
            <div class="sub_header">
                <div class="text_box_left">
                    <a class="white_link" href="/vacansy?id=<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString()%>">????????????????</a>
                </div>
                <div class="text_box_left">
                    <a class="white_link" href="/orgResponses?id=<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId().toString()%>">?????? ??????????????</a>
                </div>
            </div>
        </a:if>
    </sec:authorize>
    <div class="main_box">
        <div class="image_box">
                <input type="text" id="searchName" name="searchName" placeholder="?????????????? ????????????????" class="search_input_field margin-top200">
                <select id="searchType" class="search-select-field margin-top200">
                    <option value="resume">????????????</option>
                    <option value="vacancy">????????????????</option>
                </select>
                <button id="searchButton" class="common-button margin-top200">??????????</button><br>
            <sec:authorize access="!isAuthenticated()">
                <button id="addVacancyButton" class="big-button margin-top20">???????????????? ????????????????</button>
                <button id="addResumeButton" class="big-button margin-top20">???????????????? ????????????</button>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <a:if test="${rdbo.accType == organType}">
                    <button id="addLoginedVacancyButton" class="big-button margin-top20">???????????????? ????????????????</button>
                    <input type="hidden" id="profileorgIdLogined" value="<%=OrgDao.findOrgByAccId(SecurityService.getLoggedInUser()).getProfileorgId()%>">
                </a:if>
                <a:if test="${rdbo.accType == userType}">
                    <button id="addLoginedResumeButton" class="big-button margin-top20">???????????????? ????????????</button>
                    <input type="hidden" id="profileIdLogined" value="<%=UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId()%>">
                </a:if>
            </sec:authorize>
        </div>
        <div class="last_vacancies_main_title">
            ???????????????????? ????????????????
        </div>
        <div class="last_vacancies">
            <div class="last_vacancies_left_box">
                <a:forEach var="vcInfo" items="${lastVacansyList}" varStatus="it">
                    <a:if test="${it.index < 5}">
                        <div class="last_vacancies_box">
                            <a href="/vacansy/view?vcId=${vcInfo.vcId}" class="last_vacancies_title black_link">${vcInfo.titlevc}</a>
                            <div class="last_vacancies_text">
                                <a:if test="${vcInfo.stype == 301}">
                                    ???? ${vcInfo.sfrom} ??????.
                                </a:if>
                                <a:if test="${vcInfo.stype == 331}">
                                    ???? ${vcInfo.sto} ??????.
                                </a:if>
                                <a:if test="${vcInfo.stype == 361}">
                                    ???? ${vcInfo.sfrom} ??????. ???? ${vcInfo.sto} ??????.
                                </a:if>
                                <a:if test="${vcInfo.stype == 391}">
                                    ???? ??????????????
                                </a:if>
                            </div>
                        </div>
                    </a:if>
                </a:forEach>
            </div>
            <div class="last_vacancies_right_box">
                <a:forEach var="vcInfo" items="${lastVacansyList}" varStatus="it">
                    <a:if test="${it.index > 4}">
                        <div class="last_vacancies_box">
                            <a href="/vacansy/view?vcId=${vcInfo.vcId}" class="last_vacancies_title black_link">${vcInfo.titlevc}</a>
                            <div class="last_vacancies_text">
                                <a:if test="${vcInfo.stype == 301}">
                                    ???? ${vcInfo.sfrom} ??????.
                                </a:if>
                                <a:if test="${vcInfo.stype == 331}">
                                    ???? ${vcInfo.sto} ??????.
                                </a:if>
                                <a:if test="${vcInfo.stype == 361}">
                                    ???? ${vcInfo.sfrom} ??????. ???? ${vcInfo.sto} ??????.
                                </a:if>
                                <a:if test="${vcInfo.stype == 391}">
                                    ???? ??????????????
                                </a:if>
                            </div>
                        </div>
                    </a:if>
                </a:forEach>
            </div>
        </div>
        <div class="bottom_info_block">
            <div class="bottom_info_text">
                ???????????? ?????? ???????????? ???????????? JobToad<br><br>
                ?????????????????????? ???????????????? JobToad ?? 2018<br>
                ???????????????? ??????????????: ?????????????? ????????????
            </div>
        </div>
    </div>
    </body>
</html>
