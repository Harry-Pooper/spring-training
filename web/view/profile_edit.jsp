<%@ page import="main.java.service.SecurityService" %>
<%@ page import="main.java.dao.OrgDao" %>
<%@ page import="main.java.dao.UserDao" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    <script src="<a:url value="/js/datepicker.min.js"/>"></script>
    <script src="<a:url value="/js/i18n/datepicker.en.js"/>"></script>
    <link href="<a:url value="/css/datepicker.min.css"/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main_page.css"/>"/>
    <link href="<c:url value="/css/profile_edit.css"/>" rel="stylesheet" type="text/css">
    <title>Title</title>
    <script>
        $(document).ready(function(){
            var dp = $('#dp').datepicker({
                maxDate: new Date(),
                dateFormat: 'dd.mm.yyyy'
            }).data('datepicker');
            var photo = $('#photo');
            var countrySelect = $('#country');
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
            if($('#profileImg').val() === ""){
                photo.attr("src", '<a:url value="/img/not_defined.png"/>');
            } else {
                photo.attr("src", '<a:url value="/img/${userInfo.photoUrl}"/>');
            }
        });

        function loadSuccess(){
            console.log("IMG_LOADED SUCCESSFULLY");
        }

        function changeCities(input){
            var dummy = 0;
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
                if(elms.length != 0) {
                    $('#tagList').val(arr);
                }
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
                $(phoneDiv).append('<input id=\"phone' + a + '\" name=\"phone' + a + '\" placeholder=\"Phone Number\" class=\"common-input-field\" type=\"text\" value=\"\"/><br id=\"phone' + a + '\">');
                a++;
                $('#phoneCounter').val(a);
                return false;
            });
            $('#addEmail').on('click', function(){
                $(emailDiv).append('<input id=\"email' + b + '\" name=\"email' + b + '\" placeholder=\"E-mail\" class=\"common-input-field\" type=\"text\" value=\"\"/><br id=\"email' + b + '\">');
                b++;
                $('#emailCounter').val(b);
                return false;
            });
            $('#addFax').on('click', function(){
                $(faxDiv).append('<input id=\"fax' + c + '\" name=\"fax' + c + '\" placeholder=\"Fax Number\" class=\"common-input-field\" type=\"text\" value=\"\"/><br id=\"fax' + c + '\">');
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
                if(a === 1) {
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
                if(b === 1){
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
                if(c === 1){
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
        <form:form method="POST" modelAttribute="userInfo" enctype="multipart/form-data">
            <form:label path="firstName">Фамилия</form:label>
            <spring:bind path="firstName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="firstName" class="common-input-field" maxlength="50" placeholder="First Name"></form:input>
                    <form:errors path="firstName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="secondName">Имя</form:label>
            <spring:bind path="secondName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="secondName" class="common-input-field" maxlength="50" placeholder="Second Name"></form:input>
                    <form:errors path="secondName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="lastName">Отчество</form:label>
            <spring:bind path="lastName">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="lastName" class="common-input-field" maxlength="50" placeholder="Last Name"></form:input>
                    <form:errors path="lastName"></form:errors>
                </div>
            </spring:bind>

            <form:label path="birthDate">Дата рождения</form:label>
            <spring:bind path="birthDate">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" id="dp" path="birthDate" class="common-input-field" placeholder="Date"></form:input>
                    <form:errors path="birthDate"></form:errors>
                </div>
            </spring:bind>

            Город:<br>
            <select id="cityName" class="search-select-field width-250" name="cityName">
                <a:forEach var="city" items="${cities}" varStatus="it">
                    <option <a:if test="${city.cityId == selectedCity}">selected </a:if> value="${city.cityId}">${city.cityName}</option>
                </a:forEach>
            </select><br>

            Контактные телефоны <img src="<a:url value="/icons/plus.png"/>" id="addPhone"> <img src="<a:url value="/icons/minus.png"/>" id="remLastPhone">
            <div id="phones">
                <a:if test="${phones.size() == 0}"><input id="phone0" name="phone0" class="common-input-field" placeholder="Phone Number" type="text" value=""/><br id="phone0"></a:if>
                <a:forEach var="phone" items="${phones}" varStatus="it">
                    <input id="phone${it.index}" name="phone${it.index}" class="common-input-field" placeholder="Phone Number" type="text" value="${phone}"/><br id="phone${it.index}">
                </a:forEach>
            </div>
            E-mail <img src="<a:url value="/icons/plus.png"/>" id="addEmail"> <img src="<a:url value="/icons/minus.png"/>" id="remLastEmail">
            <div id="emails">
                <a:if test="${emails.size() == 0}"><input id="email0" name="email0" class="common-input-field" placeholder="E-mail" type="text" value=""/><br id="email0"></a:if>
                <a:forEach var="email" items="${emails}" varStatus="it">
                    <input id="email${it.index}" name="email${it.index}" class="common-input-field" placeholder="E-mail" type="text" value="${email}"/><br id="email${it.index}">
                </a:forEach>
            </div>
            Факс <img src="<a:url value="/icons/plus.png"/>" id="addFax"> <img src="<a:url value="/icons/minus.png"/>" id="remLastFax">
            <div id="faxes">
                <a:if test="${faxes.size() == 0}"><input id="fax0" name="fax0" class="common-input-field" placeholder="Fax Number" type="text" value=""/><br id="fax0"></a:if>
                <a:forEach var="fax" items="${faxes}" varStatus="it">
                    <input id="fax${it.index}" name="fax${it.index}" class="common-input-field" placeholder="Fax Number" type="text" value="${fax}"/><br id="fax${it.index}">
                </a:forEach>
            </div>

            Ключевые навыки<br>
            <input type="text" maxlength="30" id="tagName" class="common-input-field" name="tagName"/><img src="<a:url value="/icons/plus.png"/>" id="addTag">
            <div id="tags">
                <a:forEach var="tag" items="${tags}" varStatus="it">
                    <div id="tag${it.index}" class="tagDiv">
                        <div id="tagText${it.index}" class="tagDiv_text">
                        ${tag}
                        </div>
                    </div>
                </a:forEach>
            </div>

            <form:label path="aboutUser">О себе</form:label>
            <spring:bind path="aboutUser">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:textarea path="aboutUser" rows="15" cols="50" class="common-text-area-field" maxlength="500"></form:textarea>
                    <form:errors path="aboutUser"></form:errors>
                </div>
            </spring:bind>

            Должность: <br/>
            <select id="position" name="position" class="search-select-field width-250" multiple>
                <a:forEach var="pos" items="${positions}" varStatus="it">
                    <option value="${pos.positionId}" <a:if test="${selectedPositions.contains(pos)}">selected</a:if>>${pos.positionName}</option>
                </a:forEach>
            </select>

            <form:label path="degree">Образование</form:label>
            <spring:bind path="degree">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:select path="degree" class="search-select-field width-250" items="${degrees}"></form:select>
                    <form:errors path="degree"></form:errors>
                </div>
            </spring:bind>

            <form:label path="businessTrips">Командировки</form:label>
            <spring:bind path="businessTrips">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:select path="businessTrips" class="search-select-field width-250" items="${businessTrips}"></form:select>
                    <form:errors path="businessTrips"></form:errors>
                </div>
            </spring:bind>


            <img src="" height="200" width="200" id="photo"/>
            Новое изображение: <input type="file" name="file" onchange="setImage(this);"><br />
            <h5>Желательно использовать изображения с размером 200х200</h5>


            <input type="submit" class="common-button" id="btnSend" name="btnSend" value="Отправить"/>
            <input type="submit" class="common-button" name="btnCancel" value="Отменить"/>
            <input type="hidden" id="citiesList" name="citiesList" value="${cities}"/>
            <input type="hidden" id="profileImg" name="profileImg" value="${userInfo.photoUrl}"/>
            <input type="hidden" id="phoneCounter" name="phoneCounter" value="${phones.size()}"/>
            <input type="hidden" id="emailCounter" name="emailCounter" value="${emails.size()}"/>
            <input type="hidden" id="faxCounter" name="faxCounter" value="${faxes.size()}"/>
            <input type="hidden" id="tagsCounter" name="tagCounter" value="${tags.size()}"/>
            <input type="hidden" id="tagId" name="tagId" value="0"/>
            <input type="hidden" id="tagList" name="tagList" value=""/>
            <input type="hidden" id="positionList" name="positionList" value=""/>
        </form:form>
    </div>
</div>
</body>
</html>
