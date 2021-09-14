<%@ page import="main.java.dao.OrgDao" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.UserDao" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: mrlz
  Date: 09.04.2018
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="<c:url value="/js/datepicker.min.js"/>"></script>
    <script src="<c:url value="/js/i18n/datepicker.en.js"/>"></script>
    <link href="<c:url value="/css/datepicker.min.css"/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <link href="<c:url value="/css/profile_edit.css"/>" rel="stylesheet" type="text/css">
    <title>Title</title>
    <script>
        $(document).ready(function(){
            var photo = $('#photo');
            if($('#profileImg').val() === ""){
                photo.attr("src", '<a:url value="/img/not_defined.png"/>');
            } else {
                photo.attr("src", '<a:url value="/img/${orgInfo.imageUrl}"/>');
            }
        });

        function loadSuccess(){
            console.log("IMG_LOADED SUCCESSFULLY");
        }

        function setImage(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    loadSuccess();
                    $('#photo')
                        .attr('src', e.target.result)
                        .width(200)
                        .height(200);
                };
                reader.readAsDataURL(input.files[0]);
                var dummy = 0;
            }
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
    <div class="content_box">
        <div class="content_title">
            Редактирование личного кабинета
        </div>
        <form:form method="POST" modelAttribute="orgInfo" enctype="multipart/form-data">

            <form:label path="orgName">Название организации</form:label>
            <spring:bind path="orgName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="orgName" maxlength="50" class="common-input-field" placeholder="Org Name"></form:input>
                    <form:errors path="orgName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="orgType">Тип организации</form:label>
            <spring:bind path="orgType">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:select path="orgType" class="search-select-field width-250" items="${orgType}"></form:select>
                    <form:errors path="orgType"></form:errors>
                </div>
            </spring:bind>

            <form:label path="countMem">Количество работников</form:label>
            <spring:bind path="countMem">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:select path="countMem" class="search-select-field width-250" items="${countMemList}"></form:select>
                    <form:errors path="countMem"></form:errors>
                </div>
            </spring:bind>

            Город:<br>
            <select id="cityName" class="search-select-field width-250" name="cityName">
                <a:forEach var="city" items="${cities}" varStatus="it">
                    <option <a:if test="${city.cityId == orgInfo.cityId}">selected </a:if> value="${city.cityId}">${city.cityName}</option>
                </a:forEach>
            </select><br>

            <form:label path="adress">Адрес</form:label>
            <spring:bind path="adress">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="adress" class="common-input-field" maxlength="50" placeholder="adress"></form:input>
                    <form:errors path="adress"></form:errors>
                </div>
            </spring:bind>

            <form:label path="link">Ссылка</form:label>
            <spring:bind path="link">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="link" class="common-input-field" maxlength="50" placeholder="link"></form:input>
                    <form:errors path="link"></form:errors>
                </div>
            </spring:bind>

            <form:label path="aboutOrg">Об Организации</form:label>
            <spring:bind path="aboutOrg">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:textarea path="aboutOrg" maxlength="500" class="common-text-area-field" rows="15" cols="50"></form:textarea>
                    <form:errors path="aboutOrg"></form:errors>
                </div>
            </spring:bind>

            <br><b>Контактное лицо</b><br><br>

            <form:label path="contactName">Имя</form:label>
            <spring:bind path="contactName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="contactName" class="common-input-field" maxlength="50" placeholder="contactName"></form:input>
                    <form:errors path="contactName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="contactSecName">Фамилия</form:label>
            <spring:bind path="contactSecName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="contactSecName" class="common-input-field" maxlength="50" placeholder="contactSecName"></form:input>
                    <form:errors path="contactSecName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="contactEmail">Почта</form:label>
            <spring:bind path="contactEmail">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="contactEmail" class="common-input-field" maxlength="50" placeholder="contactEmail"></form:input>
                    <form:errors path="contactEmail"></form:errors>
                </div>
            </spring:bind>

            <form:label path="contactTeleph">Телефон</form:label>
            <spring:bind path="contactTeleph">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="contactTeleph" class="common-input-field" maxlength="50" placeholder="contactTeleph"></form:input>
                    <form:errors path="contactTeleph"></form:errors>
                </div>
            </spring:bind>

            <form:label path="contactDopTeleph">Доп. Телефон</form:label>
            <spring:bind path="contactDopTeleph">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="contactDopTeleph" class="common-input-field" maxlength="50" placeholder="contactDopTeleph"></form:input>
                    <form:errors path="contactDopTeleph"></form:errors>
                </div>
            </spring:bind>


            <img src="" height="200" width="200" id="photo"/><br><br>
            Новое изображение: <input type="file" name="file" onchange="setImage(this);"><br />
            <h5>Желательно использовать изображения с размером 200х200</h5>

            <input type="submit" name="btnSend" class="common-button" value="Отправить"/>
            <input type="submit" name="btnCancel" class="common-button" value="Отменить"/>
            <input type="hidden" id="profileImg" name="profileImg" value="${orgInfo.imageUrl}"/>
            <input type="hidden" id="citiesList" name="citiesList" value="${cities}"/>
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
