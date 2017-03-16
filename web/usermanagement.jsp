<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <meta type="hidden" name="type" value="add">
                    <div class="form-group">
                        <label for="inputUsername">Användarnamn</label>
                        <input type="text" class="form-control" id="inputUsername" placeholder="Användarnamn">
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">E-postadress</label>
                        <input type="text" class="form-control" id="inputEmail" placeholder="E-post">
                    </div>
                    <button type="submit" class="btn btn-default">Skapa</button>
                </form>
                <br>
                <p class="form-signin-heading">Ta bort existerande användare genom att markera den/dem och klicka sedan på <em>ta bort</em>-knappen.</p>
                <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/users">
                    <meta type="hidden" name="type" value="delete">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Anv.namn</th>
                                <th>Lösen.</th>
                                <th>E-post</th>
                                <th>Ta bort</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{username}</td>
                                <td>{password}</td>
                                <td>{email}</td>
                                <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                            </tr>
                            <tr>
                                <td>{username}</td>
                                <td>{password}</td>
                                <td>{email}</td>
                                <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                            </tr>
                            <tr>
                                <td>{username}</td>
                                <td>{password}</td>
                                <td>{email}</td>
                                <td><input name="checkbox[]" type="checkbox" value="{id}"></td>
                            </tr>
                        </tbody>
                    </table>
                    <button class="btn btn-default" type="submit">Ta bort</button>
                </form>
            </div>
            <div class="col-md-2"></div>
        </div>
    </jsp:body>
</t:block>
