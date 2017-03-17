<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="users">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getAllUsers()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det finns inga användare att ta bort. Skapa en ny användare för att kunna se den här.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                    <tr>
                        <th>Användarnamn</th>
                        <th>Lösenord</th>
                        <th>E-post</th>
                        <th>Ta bort</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${bean.getAllUsers()}" var="user">
                        <c:if test="${user[0] ne 'admin'}">
                            <tr>
                                <td>${user[0]}</td>
                                <td>${user[1]}</td>
                                <td>${user[2]}</td>
                                <td><input name="deleteUserList" type="checkbox" value="${user[0]}"></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <c:if test="${not empty bean.getAllUsers()}">
        <button class="btn btn-default" type="submit">Ta bort</button>
    </c:if>
</c:set>
<t:block pageTitle="Användarhanterare">
    <jsp:attribute name="stylesheets" />
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript" />
    <jsp:body>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h2 class="form-signin-heading">Hantering av användare</h2>
                <p class="form-signin-heading">Lägg till en ny användare.</p>
                <form class="form-inline" name="input" method="POST" action="${pageContext.request.contextPath}/management/users">
                    <input type="hidden" name="type" value="add">
                    <div class="form-group">
                        <label for="inputUsername">Användarnamn</label>
                        <input name="username" type="text" class="form-control" id="inputUsername" placeholder="Användarnamn">
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">E-postadress</label>
                        <input name="email" type="text" class="form-control" id="inputEmail" placeholder="E-post">
                    </div>
                    <button type="submit" class="btn btn-default">Skapa</button>
                </form>
                <br>
                <p class="form-signin-heading">Ta bort existerande användare genom att markera den/dem och klicka sedan på <em>ta bort</em>-knappen.</p>
                <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/users">
                    <input type="hidden" name="type" value="delete">
                    ${users}
                </form>
            </div>
            <div class="col-md-2"></div>
        </div>
    </jsp:body>
</t:block>
