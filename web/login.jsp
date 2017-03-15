<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="infoMsg">
    <c:if test="${bean.getErrorCode() eq 1}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har anget felaktigt användarnamn och/eller lösenord.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 2}">
        <div class="alert alert-info" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har nu blivit utloggad eftersom du har varit inaktiv i mer än 30 minuter.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 3}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har nu blivit utloggad.
        </div>
    </c:if>
</c:set>
<c:set var="groups">
    <c:if test="${not empty bean.getAllGroups()}">
        <label for="selectGroup">Projektgrupp:</label>
        <select class="form-control" id="selectGroup" name="selectedGroup">
            <c:forEach items="${bean.getAllGroups()}" var="group">
                <option value="${group}">${group}</option>
            </c:forEach>
        </select>
    </c:if>
</c:set>
<c:set var="login">
    <c:choose>
        <c:when test="${empty bean.getAllGroups() and not bean.getAdminLogin()}">
            <div class="alert alert-info" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Systemet är nere för underhåll. Om du är administratör, gå till <a href="${pageContext.request.contextPath}">inloggningssidan för administratör</a>.
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${bean.getAdminLogin()}">
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det här är inloggning för administratörer. Om du är ordinarie användare, <a href="${pageContext.request.contextPath}/">logga in här</a>.
                </div>
            </c:if>
            <div class="input-group">
                <span class="input-group-addon" id="sizing-addon-username"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
                <label for="username" class="sr-only">Användarnamn</label>
                <input name="username" type="text" id="username" class="form-control" placeholder="Användarnamn" aria-describedby="sizing-addon-username" required autofocus>
            </div>
            <div class="input-group">
                <span class="input-group-addon" id="sizing-addon-password"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
                <label for="password" class="sr-only">Lösenord</label>
                <input name="password" type="password" id="password" class="form-control" placeholder="Lösenord" aria-describedby="sizing-addon-password" required>
            </div>
            <c:if test="${not bean.getAdminLogin()}">
                ${groups}
            </c:if>
            <br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Logga in</button>
        </c:otherwise>
    </c:choose>
</c:set>
<t:block pageTitle="Inloggningssidan">
    <jsp:attribute name="stylesheets">
        <c:if test="${bean.getAdminLogin()}">
            <style>
                html, body {
                    background-color: #ffeee6;
                }
            </style>
        </c:if>
    </jsp:attribute>
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript" />
    <jsp:body>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                ${infoMsg}
                <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/">
                    <h2 class="form-signin-heading">Välkommen till E-KYSS</h2>
                    <p class="form-signin-heading">Logga in genom att ange ditt användarnamn, lösenord samt projektgrupp.</p>
                    ${login}
                </form>
            </div>
            <div class="col-md-4"></div>
        </div>
    </jsp:body>
</t:block>
