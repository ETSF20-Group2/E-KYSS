<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="infoMsg">
    <c:if test="${bean.getErr_code() eq 1}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Rollerna har tilldelats.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 2}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Något blev fel! Rollerna har inte tilldelats. Försök igen.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 3}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användaren har skapats.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 4}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användarnamnet är för långt. Ett användarnamn måste vara mellan fem och tio tecken långt.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 5}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användarnamnet är för kort. Ett användarnamn måste vara mellan fem och tio tecken långt.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 6}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användarnamnet består av ogiltiga tecken. Ett användarnamn får bara bestå av A-Z, a-z och 0-9.
        </div>
    </c:if>

    <c:if test="${bean.getErr_code() eq 7}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användarna har tagits bort.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 8}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Något blev fel! Ingen användare har tagits bort. Försök igen.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 9}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har inte valt någon användare att ta bort. Markera en eller flera användare och klicka sedan på
            <em>Ta bort</em>-knappen.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 10}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Epost-adressen är inte giltig. Skriv in en giltig e-post och försök igen.
        </div>
    </c:if>

</c:set>

<c:set var="table_row">
    <c:forEach items="${bean.getPlUserList()}" var="user">
        <c:if test="${user[0] ne 'admin'}">
            <tr>
                <c:set var="select_role">
                    <c:forEach items="${bean.getAllRoles()}" var="someRole">
                        <c:choose>
                            <c:when test="${user[2] eq someRole}">
                                <option value="${user[0].concat(" ".concat(someRole))}" selected>${someRole}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${user[0].concat(" ".concat(someRole))}">${someRole}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:set>
                <td>${user[0]}</td>
                <td>${user[1]}</td>
                <td><select name="assignRole" type="text" class="form-control" id="inputRoles">
                        ${select_role}
                </select></td>
            </tr>
        </c:if>
    </c:forEach>
</c:set>
<c:set var="tbl_admin">
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

<c:set var="tbl_pl">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getPlUserList()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det finns inga användare att visa. Kontakta admin för att lägga till användare i din grupp.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                <tr>
                    <th>Användarnamn</th>
                    <th>E-post</th>
                    <th>Roll</th>
                </tr>
                </thead>
                <tbody>
                ${table_row}
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <c:if test="${not empty bean.getPlUserList()}">
        <button class="btn btn-default" type="submit">Tilldela roller</button>
    </c:if>
</c:set>
<c:set var="admin_page">
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
            ${tbl_admin}
    </form>
</c:set>

<c:set var="pl_page">
    <p class="form-signin-heading">Tilldela en roll genom att välja roll i listan och klicka sedan på <em>tilldela</em>-knappen.</p>
    <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/users">
        <input type="hidden" name="type" value="assign">
            ${tbl_pl}
    </form>
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
                    ${infoMsg}
                <c:if test="${sessionScope.name eq 'admin'}">
                    ${admin_page}
                </c:if>
                <c:if test="${sessionScope.name ne 'admin'}">
                    ${pl_page}
                </c:if>
            </div>
            <div class="col-md-2"></div>
        </div>
    </jsp:body>
</t:block>
