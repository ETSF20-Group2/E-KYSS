<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <form class="form-inline" name="input" method="POST" action="${pageContext.request.contextPath}/settings/user">
                    <meta type="hidden" name="type" value="changeInfo">
                    <div class="form-group">
                        <label for="inputUsername">Användarnamn</label>
                        <input type="text" class="form-control" id="inputUsername" placeholder="Användarnamn">
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">E-postadress</label>
                        <input type="text" class="form-control" id="inputEmail" placeholder="E-post">
                    </div>
                    <button type="submit" class="btn btn-default">Spara</button>
                </form>
                <div>
                    <label><br><br><br></label>
                </div>
                <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/settings/user">
                    <meta type="hidden" name="type" value="changePass">
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
                </form>

            </div>
            <div class="col-md-2"></div>
        </div>
    </jsp:body>
</t:block>
