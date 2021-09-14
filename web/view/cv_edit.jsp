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
            var photo = $('#photo');
            if($('#cvImg').val() === ""){
                photo.attr("src", '<c:url value="/img/not_defined.png"/>');
            } else {
                photo.attr("src", '<c:url value="/img/${cvInfo.cvImage}"/>');
            }
        });

        function setImage(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onLoad = function (e) {
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
    <form:form method="POST" name="cvInfo" id="cvInfo" modelAttribute="cvInfo" enctype="multipart/form-data">
        <div class="content_box">
        <div class="content_title">Редактирование резюме</div>
            <p class="label-head"><form:label path="title">Название резюме</form:label></p>
        <spring:bind path="title">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="title" maxlength="50" placeholder="Название" class="common-input-field"></form:input>
                <form:errors path="title"></form:errors>
            </div>
        </spring:bind>

            <p class="label-head"><form:label path="phone">Телефон</form:label></p>
        <spring:bind path="phone">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="phone" maxlength="14" placeholder="Телефон" class="common-input-field"></form:input>
                <form:errors path="phone"></form:errors>
            </div>
        </spring:bind>

            <p class="label-head"><form:label path="email">E-mail</form:label></p>
        <spring:bind path="email">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" maxlength="50" placeholder="E-mail" class="common-input-field"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>

            <p class="label-head"><form:label path="experience">Опыт работы</form:label></p>
        <spring:bind path="experience">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="number" path="experience" placeholder="Опыт работы" class="common-input-field"></form:input>
                <form:errors path="experience"></form:errors>
            </div>
        </spring:bind>

            <p class="label-head">Ключевые навыки<br></p>
        <input type="text" id="tagName" maxlength="30" name="tagName" class="common-input-field"/><img src="<c:url value="/icons/plus.png"/>" id="addTag">
        <div id="tags">
            <a:forEach var="tag" items="${tags}" varStatus="it">
                <div id="tag${it.index}" class="tagDiv">
                    <div id="tagText${it.index}" class="tagDiv_text">
                            ${tag}
                    </div>
                </div>
            </a:forEach>
        </div>

            <p class="label-head">Должность <br></p>
        <a:if test="${userPositions.size() > 0}">
            <select id="pos" name="pos">
                <a:forEach var="position" items="${userPositions}" varStatus="it">
                    <option value="${position.positionId}" <a:if test="${selectedPosition.positionId == position.positionId}">selected</a:if> >${position.positionName}</option>
                </a:forEach>
            </select>
        </a:if>
        <a:if test="${userPositions.size() == 0}">
            В личном кабинете вы не выбрали ни одной должности
        </a:if><br>

            <p class="label-head"><form:label path="about">О себе</form:label></p>
        <spring:bind path="about">
            <div class="${status.error ? 'has-error' : ''}">
                <form:textarea path="about" maxlength="500" rows="15" cols="50" class="common-text-area-field"></form:textarea>
                <form:errors path="about"></form:errors>
            </div>
        </spring:bind>
            <p class="label-head">Желаемая зп:<br/></p>
        От
        <input type="number" value="<a:if test="${paymentInfo.paymentFrom != -1}">${paymentInfo.paymentFrom}</a:if>" id="paymentFrom" name="paymentFrom" class="payment" placeholder="От">
        До
        <input type="number" value="<a:if test="${paymentInfo.paymentTo != -1}">${paymentInfo.paymentTo}</a:if>" id="paymentTo" name="paymentTo" class="payment" placeholder="До">руб.<br/>

        <br>
        <img src="" height="200" width="200" id="photo"/><br/>
        Новое изображение: <br> <input type="file" name="file" onchange="setImage(this);"><br />
        <h5>Желательно использовать изображения с размером 200х200</h5><br>

        <br>
        <input type="submit" id="btnSend" name="btnSend" value="Отправить" class="common-button"/>
        <input type="submit" id="btnCancel" name="btnCancel" value="Отменить" class="common-button"/>
        <input type="hidden" id="cvImg" name="cvImg" value="${cvInfo.cvImage}"/>
        <input type="hidden" id="tagId" name="tagId" value="0"/>
        <input type="hidden" id="tagsCounter" name="tagCounter" value="${tags.size()}"/>
        <input type="hidden" id="tagList" name="tagList" value=""/>
        </div>
    </form:form>
</div>
</body>
</html>
