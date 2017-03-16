<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="groups">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getAllGroups()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det finns inga projektgrupper att visa. Skapa en ny projektgrupp för att kunna se den här.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                    <tr>
                        <th>Projektgrupp</th>
                        <th>Ta bort</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${bean.getAllGroups()}" var="group">
                        <tr>
                            <td>${group}</td>
                            <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <button class="btn btn-default" type="submit">Ta bort</button>
</c:set>
<t:block pageTitle="Projektgrupphantering">
    <jsp:attribute name="stylesheets" />
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript" />
    <jsp:body>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h2 class="form-signin-heading">Hantering av projektgrupper</h2>
                <p class="form-signin-heading">Lägg till en ny projektgrupp.</p>
                <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                    <meta type="hidden" name="type" value="add">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Ange önskat projektgruppsnamn">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="submit">Skapa</button>
                        </span>
                    </div>
                </form>
                <br>
                <p class="form-signin-heading">Ta bort existerande projektgrupp(er) genom att markera den/dem och klicka sedan på <em>ta bort</em>-knappen.</p>
                <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                    <meta type="hidden" name="type" value="delete">
                    ${groups}
                </form>
            </div>
            <div class="col-md-3"></div>
        </div>
    </jsp:body>
</t:block>
