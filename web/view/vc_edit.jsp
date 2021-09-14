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
    <link href="<c:url value="/css/profile_edit.css"/>" rel="stylesheet" type="text/css">
    <script>
        $(document).ready(function(){
            var tagId = $('#tagId').val();
            var d = $('#tagsCounter').val();
            for(var i = 0; i < d; i++){
                var s = '#tag' + tagId;
                $(s)
                    .append('<div id=\"tagDel' + tagId + '\" class=\"tagDiv_del\">X</div>')
                    .on('click', function(){
                        $(this).closest().prevObject.remove();
                        d--;
                    });
                tagId++;
                $('#tagsCounter').val(d);
                $('#tagId').val(tagId);
            }
        });

        $(function(){
            var tagDiv = $('#tags');
            var d = $('#tagsCounter').val();
            var tagId = $('#tagId').val();
            $('#addTag').on('click', function(){
                if($('#tagName').val() !== "") {
                    $(tagDiv).append('<div id=\"tag' + tagId + '\" class=\"tagDiv\">' +
                        '<div id=\"tagText' + tagId +'\" class=\"tagDiv_text\">' +
                        $('#tagName').val() +
                        '</div>' +
                        '</div>');
                    var s = '#tag' + tagId;
                    $(s)
                        .append('<div id=\"tagDel' + tagId + '\" class=\"tagDiv_del\">X</div>')
                        .on('click', function(){
                            $(s).remove();
                            d--;
                        });
                    tagId++;
                    d++;
                    $('#tagsCounter').val(d);
                    $('#tagId').val(tagId);
                    $('#tagName').val("");
                    console.log($(this).id);
                }
            });

            $('#btnSend').on('click', function(){
                var elms = $('.tagDiv_text');
                var arr = [];
                for(var i = 0; i < elms.length; i++){
                    arr.push(elms[i].innerText);
                }
                $('#tagList').val(arr);
            });
        })
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
    <div class="content_box">
        <div class="content_title">Редактирование вакансии</div>
        <form:form method="POST" name="vcInfo" id="vcInfo"  modelAttribute="vcInfo" enctype="multipart/form-data">
            <form:label path="titlevc">Название вакансии</form:label>
            <spring:bind path="titlevc">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="titlevc" class="common-input-field" maxlength="50" placeholder="Название"></form:input>
                    <form:errors path="titlevc"></form:errors>
                </div>
            </spring:bind>

            Заработная плата:<br/>
            От
            <input type="number" value="<a:if test="${vcInfo.sfrom != -1}">${vcInfo.sfrom}</a:if>" class="common-input-small-field" id="sFrom" name="sFrom" class="payment" placeholder="От"> руб.
            до
            <input type="number" value="<a:if test="${vcInfo.sto != -1}">${vcInfo.sto}</a:if>" class="common-input-small-field" id="sTo" name="sTo" class="payment" placeholder="До"><br/> руб.


            <form:label path="aboutvc">О вакансии</form:label>
            <spring:bind path="aboutvc">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:textarea path="aboutvc" maxlength="500" class="common-text-area-field" rows="15" cols="50"></form:textarea>
                    <form:errors path="aboutvc"></form:errors>
                </div>
            </spring:bind>


            <form:label path="demands">Требования к кандидату</form:label>
            <spring:bind path="demands">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:textarea path="demands" maxlength="500" class="common-text-area-field" rows="15" cols="50"></form:textarea>
                    <form:errors path="demands"></form:errors>
                </div>
            </spring:bind>

            Ключевые навыки<br>
            <input type="text" id="tagName" maxlength="30" name="tagName" class="common-input-field" class="common-input-field"/><img src="<c:url value="/icons/plus.png"/>" id="addTag">
            <div id="tags">
                <a:forEach var="tag" items="${tags}" varStatus="it">
                    <div id="tag${it.index}" class="tagDiv">
                        <div id="tagText${it.index}" class="tagDiv_text">
                                ${tag}
                        </div>
                    </div>
                </a:forEach>
            </div>

            <br>Контактное лицо:<br><br>

            <form:label path="vcName">Имя</form:label>
            <spring:bind path="vcName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="vcName" class="common-input-field" maxlength="50" placeholder="Имя"></form:input>
                    <form:errors path="vcName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="vcSecName">Фамилия</form:label>
            <spring:bind path="vcSecName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="vcSecName" class="common-input-field" maxlength="50" placeholder="Фамилия"></form:input>
                    <form:errors path="vcSecName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="vcTeleph">Контактный Телефон</form:label>
            <spring:bind path="vcTeleph">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="vcTeleph" class="common-input-field" maxlength="50" placeholder="Телефон"></form:input>
                    <form:errors path="vcTeleph"></form:errors>
                </div>
            </spring:bind>

            <form:label path="vcEmail">Контактный E-mail</form:label>
            <spring:bind path="vcEmail">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="vcEmail" class="common-input-field" maxlength="50" placeholder="E-mail"></form:input>
                    <form:errors path="vcEmail"></form:errors>
                </div>
            </spring:bind>

            <form:label path="adress">Адрес</form:label>
            <spring:bind path="adress">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="adress" class="common-input-field" maxlength="50" placeholder="Адрес"></form:input>
                    <form:errors path="adress"></form:errors>
                </div>
            </spring:bind>

            <br>
            <input type="submit" class="common-button" id="btnSend" name="btnSend" value="Отправить"/>
            <input type="submit" class="common-button" id="btnCancel" name="btnCancel" value="Отменить"/>
            <input type="hidden" id="tagId" name="tagId" value="0"/>
            <input type="hidden" id="tagsCounter" name="tagCounter" value="${tags.size()}"/>
            <input type="hidden" id="tagList" name="tagList" value=""/>
        </form:form>
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
