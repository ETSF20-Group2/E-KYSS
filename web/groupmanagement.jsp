<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <table class="table table-hover">
                        <tr>
                            <td>{gruppnamn}</td>
                            <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                        </tr>
                        <tr>
                            <td>{gruppnamn}</td>
                            <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                        </tr>
                        <tr>
                            <td>{gruppnamn}</td>
                            <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                        </tr>
                    </table>
                    <button class="btn btn-default" type="submit">Ta bort</button>
                </form>
            </div>
            <div class="col-md-3"></div>
        </div>
    </jsp:body>
</t:block>
