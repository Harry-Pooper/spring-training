<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 07.06.2018
  Time: 0:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Отправить отклик</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/send_resume.css"/>"/>
    <script>
        $(function() {
            $('#preview').on('click', function() {
                if ($('#selectedCv').val() !== 0) {
                    var query = "/resume/view?cvId=" + $('#selectedCv').val();
                    window.open(query, 'new', 'width=900, height=600')
                } else {
                    if($('#selectedCv').val() === 0){
                        $('#selectedCv').setAttribute("class", $('#selectedCv').getAttribute("class") + " has-error-field");
                    }
                }
            });
        });
    </script>
</head>
<body>
    <div class="main_box">
        <a:if test="${success == true}">
            <div class="title">
                Отклик отправлен успешно
            </div>
        </a:if>
        <a:if test="${success != true}">
            <div class="title">
                Отправить отклик на вакансию
            </div>
            <div class="sub_title">
                ${vcInfo.titlevc}
            </div>
            <form method="post">
                <div class="text">
                    <select id="selectedCv" name="selectedCv" class="search-select-field">
                        <option value="0" selected>Не выбрано</option>
                        <a:forEach var="cv" items="${cvList}" varStatus="it">
                            <option value="${cv.cvId}">${cv.title}</option>
                        </a:forEach>
                    </select>
                    <a class="black_link" id="preview">Посмотреть резюме</a>
                </div>
                <textarea rows="10" cols="45" id="letter" name="letter" class="text-area-field"></textarea><br>
                <button type="submit" name="vcId" value="${vcInfo.vcId}" class="common-button">Отправить</button>
            </form>
        </a:if>
    </div>

</body>
</html>
