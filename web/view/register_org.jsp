<%--suppress XmlDuplicatedId --%>
<%--suppress CheckTagEmptyBody --%>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="main.java.Dict" %><%--
  Created by IntelliJ IDEA.
  User: mrlz
  Date: 02.04.2018
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="<c:url value="/js/datepicker.min.js"/>"></script>
    <script src="<c:url value="/js/i18n/datepicker.en.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <link href="<c:url value="/css/profile_edit.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/datepicker.min.css"/>" rel="stylesheet" type="text/css">

    <script>
        $(document).ready(function(){
            var photo = $('#photo');
            if($('#profileImg').val() === ""){
                photo.attr("src", '<a:url value="/img/not_defined.png"/>');
            } else {
                photo.attr("src", '<a:url value="/img/${userInfo.photoUrl}"/>');
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
    <div class="text_box_right"><a class="black_link" href="/login">Войти</a></div>
    <div class="text_box_right"><a class="black_link" href="/registration">Регистрация</a></div>
</div>
<div class="main_box">
    <div class="main_box_center" style="margin-top: 0px">
    <form:form method="POST" modelAttribute="orgInfo" enctype="multipart/form-data">
        <div class="table-mid edit-mid table-big-org">
        <p class="label-main">Информация об организации</p>
        <form:label path="orgName" cssclass="edit-text label">Название организации</form:label>
        <spring:bind path="orgName">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="orgName" maxlength="50" placeholder="Название организации" class="edit-input"></form:input>
                <form:errors path="orgName"></form:errors>
            </div>
        </spring:bind>
<br>
        <form:label path="orgType" cssClass="edit-text label">Тип организации</form:label>
        <spring:bind path="orgType">
            <div class="${status.error ? 'has-error' : ''}">
                <form:select path="orgType" items="${orgType}" class="edit-input"></form:select>
                <form:errors path="orgType"></form:errors>
            </div>
        </spring:bind>
<br>
        <form:label path="countMem" class="edit-text label">Количество работников</form:label>
        <spring:bind path="countMem">
            <div class="${status.error ? 'has-error' : ''}">
                <form:select path="countMem" items="${countMem}" class="edit-input"></form:select>
                <form:errors path="countMem"></form:errors>
            </div>
        </spring:bind>
<br>

        <form:label path="adress" class="edit-text label">Адрес</form:label>
        <spring:bind path="adress">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="adress" maxlength="50" placeholder="Адрес" class="edit-input"></form:input>
                <form:errors path="adress"></form:errors>
            </div>
        </spring:bind>
<br>
        <form:label path="link" class="edit-text label">Ссылка</form:label>
        <spring:bind path="link">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="link" maxlength="50" placeholder="Ссылка" class="edit-input"></form:input>
                <form:errors path="link"></form:errors>
            </div>
        </spring:bind>

        <p  class="edit-text label">Город:</p><br>
        <select id="cityName" name="cityName"  class="edit-input">
            <a:forEach var="city" items="${cities}" varStatus="it">
                <option <a:if test="${city.cityId == selectedCity}">selected </a:if> value="${city.cityId}">${city.cityName}</option>
            </a:forEach>
        </select><br>


        <form:label path="aboutOrg" class="edit-text label">Об Организации</form:label>
        <spring:bind path="aboutOrg">
            <div class="${status.error ? 'has-error' : ''}">
                <form:textarea path="aboutOrg" maxlength="500" rows="5" cols="30" style="width: 270px;"></form:textarea>
                <form:errors path="aboutOrg"></form:errors>
            </div>
        </spring:bind>

        <h5>Контактное лицо</h5>

        <form:label path="contactName" class="edit-text label">Имя</form:label>
        <spring:bind path="contactName">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="contactName" maxlength="50" placeholder="Имя" class="edit-input"></form:input>
                <form:errors path="contactName"></form:errors>
            </div>
        </spring:bind>

        <form:label path="contactSecName" class="edit-text label">Фамилия</form:label>
        <spring:bind path="contactSecName">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="contactSecName" maxlength="50" placeholder="Фамилия" class="edit-input"></form:input>
                <form:errors path="contactSecName"></form:errors>
            </div>
        </spring:bind>

        <form:label path="contactEmail" class="edit-text label">Почта</form:label>
        <spring:bind path="contactEmail">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="contactEmail" maxlength="50" placeholder="E-mail" class="edit-input"></form:input>
                <form:errors path="contactEmail"></form:errors>
            </div>
        </spring:bind>

        <form:label path="contactTeleph" class="edit-text label">Телефон</form:label>
        <spring:bind path="contactTeleph">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="contactTeleph" maxlength="50" placeholder="Телефон" class="edit-input"></form:input>
                <form:errors path="contactTeleph"></form:errors>
            </div>
        </spring:bind>

        <form:label path="contactDopTeleph" class="edit-text label">Дополнительный телефон</form:label>
        <spring:bind path="contactDopTeleph">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="contactDopTeleph" maxlength="50" placeholder="Дополнительный телефон" class="edit-input"></form:input>
                <form:errors path="contactDopTeleph"></form:errors>
            </div>
        </spring:bind>

        <img src="" height="200" width="200" id="photo"/> <br>
        Новое изображение: <input type="file" name="file" onchange="setImage(this);"><br />
        <h5>Желательно использовать изображения <br> с размером 200х200</h5>

        <input type="submit" name="btnSend" class="common-button" value="Отправить"/>
        <input type="submit" name="btnCancel" class="common-button" value="Отменить"/>
        <input type="hidden" id="profileImg" name="profileImg" value=""/>
    </form:form>
        </div>
    </div>
</div>

</body>
</html>
