<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="infoMsg">
    <c:if test="${bean.getErrorCode() eq 1}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            De nya lösenorden matchar inte.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 2}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Fel format på de nya lösenorden.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 3}">
    <div class="alert alert-warning" role="alert">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Det nya lösenordet är samma som det gamla.
    </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 4}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Ditt gamla lösenord stämmer inte. Försök igen.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 5}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Lösenordet har ändrats.
        </div>
    </c:if>
</c:set>


<c:set var="groups">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getGroupList()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Du är inte medlem i någon grupp.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                <tr>
                    <th>Projektgrupp</th>
                    <th>Roll</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bean.getGroupList()}" var="group">
                    <tr>
                        <td>${group[0]}</td>
                        <td>${group[1]}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
</c:set>

<t:block pageTitle="Användarinställningar">
    <jsp:attribute name="stylesheets" />
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript" />
    <jsp:body>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h2 class="form-signin-heading">Användarinställningar</h2>
                <p class="form-signin-heading">Här kan du ändra dina inställningar för ditt användarkonto.</p>
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#change" aria-controls="change" role="tab" data-toggle="tab">Ändra lösenord</a></li>
                    <li role="presentation"><a href="#groups" aria-controls="groups" role="tab" data-toggle="tab">Aktiva grupper</a></li>
                </ul>
                <div class="tab-content">

                    <div role="tabpanel" class="tab-pane" id="groups">
                        <p>Aktiva grupper</p>
                        <form class="form-signin" name="input" method="GET" action="${pageContext.request.contextPath}/management/groups">
                                ${groups}
                        </form>
                    </div>

                    <div role="tabpanel" class="tab-pane active" id="change">
                        <p>Lägg till en ny projektgrupp.</p>

                        <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/settings/user">
                            <input type="hidden" name="type" value="changePass">
                            <div class="form-group">
                                <label for="inputOldPass">Gammalt lösenord</label>
                                <input name="oldPassword" type="password" style="width: 200px" class="form-control" id="inputOldPass" placeholder="Gammalt lösenord">
                            </div>
                            <div class="form-group">
                                <label for="inputNewPass1">Nytt lösenord</label>
                                <input name="newPassword1" type="password" style="width: 200px" class="form-control" id="inputNewPass1" placeholder="Nytt lösenord">
                            </div>
                            <div class="form-group">
                                <label for="inputNewPass2">Nytt lösenord</label>
                                <input name="newPassword2" type="password" style="width: 200px" class="form-control" id="inputNewPass2" placeholder="Nytt lösenord">
                            </div>
                            <button type="submit" class="btn btn-default">Ändra lösenord</button>
                                ${infoMsg}
                        </form>
                    </div>
                </div>

            </div>
            <div class="col-md-2"></div>
        </div>
    </jsp:body>
</t:block>
