<%--suppress XmlDuplicatedId --%>
<%--suppress CheckTagEmptyBody --%>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page import="main.java.Dict" %>
<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.UserDao" %>
<%@ page import="main.java.dao.OrgDao" %><%--
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
    <script src="<a:url value="/js/datepicker.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <link href="<a:url value="/css/profile_edit.css"/>" rel="stylesheet" type="text/css">
    <script src="<a:url value="/js/i18n/datepicker.en.js"/>"></script>
    <link href="<a:url value="/css/datepicker.min.css"/>" rel="stylesheet" type="text/css">

    <script>
        $(document).ready(function(){
            var dp = $('#dp').datepicker().data('datepicker');
            var photo = $('#photo');
            dp.selectDate(new Date());

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

        $(function(){
            var phoneDiv = $('#phones');
            var emailDiv = $('#emails');
            var faxDiv = $('#faxes');
            var tagDiv = $('#tags');
            var a = $('#phoneCounter').val(); //ids for phones
            var b = $('#emailCounter').val(); //ids for emails
            var c = $('#faxCounter').val(); //ids for faxes
            var d = $('#tagsCounter').val();
            var tagId = $('#tagId').val();
            $('option').on('mousedown', function(e){
                e.preventDefault();
                $(this).prop('selected', !$(this).prop('selected'));
                return false;
            });
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
                elms = $('#position');
                arr = [];
                for(i = 0; i < elms[0].length; i++){
                    if(elms[0][i].selected){
                        arr.push(elms[0][i].value);
                    }
                }
                $('#positionList').val(arr);
            });

            $('#addPhone').on('click', function(){
                $(phoneDiv).append('<input id=\"phone' + a + '\" name=\"phone' + a + '\" placeholder=\"Номер телефона\" type=\"text\" value=\"\"/><br id=\"phone' + a + '\">');
                a++;
                $('#phoneCounter').val(a);
                return false;
            });
            $('#addEmail').on('click', function(){
                $(emailDiv).append('<input id=\"email' + b + '\" name=\"email' + b + '\" placeholder=\"E-mail\" type=\"text\" value=\"\"/><br id=\"email' + b + '\">');
                b++;
                $('#emailCounter').val(b);
                return false;
            });
            $('#addFax').on('click', function(){
                $(faxDiv).append('<input id=\"fax' + c + '\" name=\"fax' + c + '\" placeholder=\"Номер факса\" type=\"text\" value=\"\"/><br id=\"fax' + c + '\">');
                c++;
                $('#faxCounter').val(c);
                return false;
            });
            $('#remLastPhone').on('click', function(){
               if(a > 1){
                   a--;
                   $('#phone' + a ).remove();
                   $('#phone' + a ).remove();
                   $('#phoneCounter').val(a);
               }
               if(a == 1) {
                   $('#phone' + a).val("");
               }
            });
            $('#remLastEmail').on('click', function(){
                if(b > 1){
                    b--;
                    $('#email' + b ).remove();
                    $('#email' + b ).remove();
                    $('#emailCounter').val(b);
                }
                if(b == 1){
                    $('#email' + b).val("");
                }
            });
            $('#remLastFax').on('click', function(){
                if(c > 1){
                    c--;
                    $('#fax' + c ).remove();
                    $('#fax' + c ).remove();
                    $('#faxCounter').val(c);
                }
                if(c == 1){
                    $('#fax' + c).val("");
                }
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
    <div class="text_box_right"><a class="black_link" href="/login">Войти</a></div>
    <div class="text_box_right"><a class="black_link" href="/registration">Регистрация</a></div>
</div>
<div class="main_box">
    <div class="main_box_center" style="margin-top: 0px;">
    <form:form method="POST" modelAttribute="userInfo" enctype="multipart/form-data">
        <div class="table-mid edit-mid table-big-user" style="margin-left: 1%;">
        <p class="label-main">Регистрация пользователя</p>

        <form:label path="firstName" cssclass="edit-text label">Фамилия</form:label>
        <spring:bind path="firstName">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="firstName" maxlength="50" placeholder="Фамилия" cssClass="edit-input"></form:input>
                <form:errors path="firstName"></form:errors>
            </div>
        </spring:bind>

        <form:label path="secondName" cssclass="edit-text label">Имя</form:label>
        <spring:bind path="secondName">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="secondName" maxlength="50" placeholder="Имя" cssClass="edit-input"></form:input>
                <form:errors path="secondName"></form:errors>
            </div>
        </spring:bind>

        <form:label path="lastName" cssclass="edit-text label">Отчество</form:label>
        <spring:bind path="lastName">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="lastName" maxlength="50" placeholder="Отчество" cssClass="edit-input"></form:input>
                <form:errors path="lastName"></form:errors>
            </div>
        </spring:bind>

        <form:label path="birthDate" cssclass="edit-text label">Дата рождения</form:label>
        <spring:bind path="birthDate">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" id="dp" path="birthDate" placeholder="Дата рождения" cssClass="edit-input"></form:input>
                <form:errors path="birthDate"></form:errors>
            </div>
        </spring:bind>

            <p class="edit-text label" style="margin-bottom: 2px;">Город:</p><br>
        <select id="cityName" name="cityName"  class="edit-input">
            <a:forEach var="city" items="${cities}" varStatus="it">
                <option <a:if test="${city.cityId == selectedCity}">selected </a:if> value="${city.cityId}">${city.cityName}</option>
            </a:forEach>
        </select><br>

            <p class="edit-text label" style="margin-bottom: 2px;">Ключевые навыки</p><br>
        <input type="text" id="tagName" maxlength="30" name="tagName" class="edit-input"/><img src="<a:url value="/icons/plus.png"/>" id="addTag">
        <div id="tags">
        </div>

            <p class="edit-text label" style="margin-bottom: 2px;">Должность:</p> <br/>
        <select id="position" name="position" multiple  style="width: 270px;">
            <a:forEach var="pos" items="${positions}" varStatus="it">
                <option value="${pos.positionId}" <a:if test="${selectedPositions.contains(pos)}">selected</a:if>>${pos.positionName}</option>
            </a:forEach>
        </select>

            <p class="edit-text label">Контактные телефоны </p>
            <img src="<a:url value="/icons/plus.png"/>" id="addPhone"> <img src="<a:url value="/icons/minus.png"/>" id="remLastPhone">
        <div id="phones">
            <input id="phone0" name="phone0" placeholder="Номер телефона" type="text" value="" class="edit-input"/><br id="phone0">
        </div>

            <p class="edit-text label">E-mail</p>
            <img src="<a:url value="/icons/plus.png"/>" id="addEmail"> <img src="<a:url value="/icons/minus.png"/>" id="remLastEmail">
        <div id="emails">
            <input id="email0" name="email0" placeholder="E-mail" type="text" value="" class="edit-input"/><br id="email0">
        </div>

            <p class="edit-text label">Факс</p>
            <img src="<a:url value="/icons/plus.png"/>" id="addFax"> <img src="<a:url value="/icons/minus.png"/>" id="remLastFax">
        <div id="faxes">
            <input id="fax0" name="fax0" placeholder="Номер факса" type="text" value="" class="edit-input"/><br id="fax0">
        </div>

        <form:label path="aboutUser" cssclass="edit-text label">О себе</form:label>
        <spring:bind path="aboutUser">
            <div class="${status.error ? 'has-error' : ''}">
                <form:textarea path="aboutUser" maxlength="500" rows="5" cols="30" style="width: 270px;"></form:textarea>
                <form:errors path="aboutUser"></form:errors>
            </div>
        </spring:bind>

        <img src="" height="200" width="200" id="photo"/> <br>
        Новое изображение: <br>
        <input type="file" name="file" onchange="setImage(this);"><br />
        <h5>Желательно использовать изображения <br> с размером 200х200</h5>

        <form:label path="degree" cssclass="edit-text label">Образование</form:label>
        <spring:bind path="degree">
            <div class="${status.error ? 'has-error' : ''}">
                <form:select path="degree" items="${degrees}" cssClass="edit-input"></form:select>
                <form:errors path="degree"></form:errors>
            </div>
        </spring:bind>

        <form:label path="businessTrips" cssclass="edit-text label">Командировки</form:label>
        <spring:bind path="businessTrips">
            <div class="${status.error ? 'has-error' : ''}">
                <form:select path="businessTrips" items="${businessTrips}" cssClass="edit-input"></form:select>
                <form:errors path="businessTrips"></form:errors>
            </div>
        </spring:bind>


        <input type="submit" id="btnSend" name="btnSend" class="common-button" value="Отправить"/>
        <input type="submit" name="btnCancel"  class="common-button" value="Отменить"/>
        <input type="hidden" id="phoneCounter" name="phoneCounter" value="1"/>
        <input type="hidden" id="emailCounter" name="emailCounter" value="1"/>
        <input type="hidden" id="profileImg" name="profileImg" value=""/>
        <input type="hidden" id="faxCounter" name="faxCounter" value="1"/>
        <input type="hidden" id="tagList" name="tagList" value=""/>
        <input type="hidden" id="tagsCounter" name="tagCounter" value="0"/>
        <input type="hidden" id="tagId" name="tagId" value="0"/>
        <input type="hidden" id="positionList" name="positionList" value=""/>
        </div>
    </form:form>
    </div>
</div>

</body>
</html>
