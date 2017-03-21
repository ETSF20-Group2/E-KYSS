<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="infoMsg">
    <c:if test="${bean.getErrorCode() eq 1}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Det projektgruppnamn du har valt existerar redan. Välj ett annat namn.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 2}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har inte anget något namn på den nya projektgrupp som du försöker skapa. Ange ett projekgtuppnamn och klicka sedan på <em>skapa</em>-knappen.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 11}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Gruppen har blivit skapad.
        </div>
    </c:if>
</c:set>
<c:set var="infoMsg_assign">
    <c:if test="${bean.getErrorCode() eq 3}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användaren har kopplats till vald projektgrupp.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 4}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Vald användare är redan kopplad till vald projektgrupp. Välj en annan projektgrupp och försök igen på nytt.
        </div>
    </c:if>
</c:set>

<c:set var="infoMsg_delete">
    <c:if test="${bean.getErrorCode() eq 5}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Projektgruppen har tagits bort.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 6}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Något blev fel. Ingen grupp har tagits bort. Försök igen!
        </div>
    </c:if>
</c:set>

<c:set var="infoMsg_plAssign">
    <c:if test="${bean.getErrorCode() eq 7}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användaren har tilldelats rollen Projektledare.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 8}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Något blev fel. Användaren har inte tilldelats rollen Projektledare. Försök igen!
        </div>
    </c:if>
</c:set>

<c:set var="infoMsg_plDelete">
    <c:if test="${bean.getErrorCode() eq 9}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Användarna har fråntagits rollen Projektledare.
        </div>
    </c:if>
    <c:if test="${bean.getErrorCode() eq 10}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har inte markerat någon användare. Markera en eller flera användare och klicka sedan på <em>Ta bort</em>-knappen.
        </div>
    </c:if>
</c:set>
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
                            <td><input name="deleteGroup" type="checkbox" value="${group}"></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <c:if test="${not empty bean.getAllGroups()}">
        <input type="button" name="btn" value="Ta bort" id="sumbitBtn" data-toggle="modal" data-target="#confirm-delete" class="btn btn-default">
    </c:if>
</c:set>

<c:set var="plGroups">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getAllPl()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det finns inga projektgrupper att visa. Skapa en ny projektgrupp för att kunna se den här.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                <tr>
                    <th>Projektgrupp</th>
                    <th>Användare</th>
                    <th>Ta bort</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bean.getAllPl()}" var="pl">
                    <tr>
                        <td>${pl[0]}</td>
                        <td>${pl[1]}</td>
                        <td><input name="removePl" type="checkbox" value="${pl[0].concat(" ".concat(pl[1]))}"></td>
                    </tr>
                </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <c:if test="${not empty bean.getAllPl()}">
        <button class="btn btn-default" type="submit">Ta bort</button>
    </c:if>
</c:set>

<c:set var="select_groups">
    <c:forEach items="${bean.getAllGroups()}" var="group">
        <option value="${group}">${group}</option>
    </c:forEach>
</c:set>
<c:set var="select_users">
    <c:forEach items="${bean.getAllUsers()}" var="user">
        <option value="${user}">${user}</option>
    </c:forEach>
</c:set>
<t:block pageTitle="Projektgrupphantering">
    <jsp:attribute name="stylesheets" />
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript">
        <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="delete-group" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Bekräfta borttagande</h4>
                    </div>
                    <div class="modal-body">
                        <p>Du håller på och tar bort en eller flera projektgrupper som leder till oåterkallelig förlust som inte kan göras ogjort.</p>
                        <p>Är du verkligen säker på att du vill fortsätta?</p>
                        <p class="debug-url"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Avbryt</button>
                        <a  href="#" id="submit" class="btn btn-danger btn-ok">Ta bort</a>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function(){
                activaTab('${bean.getTab()}');
            });

            function activaTab(tab){
                $('.nav-tabs a[href="#' + tab + '"]').tab('show');
            };

            $('#submitBtn').click(function() {
                $('#group').text($('#deleteGroup').val());
            });
            $('#submit').click(function(){
                $('#formfield').submit();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">

                <h2 class="form-signin-heading">Hantering av projektgrupper</h2>
                <p class="form-signin-heading">Lägg till nya projektgrupper, ta bort existerande projektgrupper eller koppla användare till projektgrupp</p>

                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#add" aria-controls="add" role="tab" data-toggle="tab">Lägg till projektgrupp</a></li>
                    <li role="presentation"><a href="#delete" aria-controls="delete" role="tab" data-toggle="tab">Ta bort projektgrupp</a></li>
                    <li role="presentation"><a href="#assign" aria-controls="assign" role="tab" data-toggle="tab">Tilldela projektgrupp</a></li>
                    <li role="presentation"><a href="#assignPl" aria-controls="assignPl" role="tab" data-toggle="tab">Tilldela projektledare</a></li>
                </ul>

                <div class="tab-content">

                    <div role="tabpanel" class="tab-pane active" id="add">
                        <p>Lägg till en ny projektgrupp.</p>
                            ${infoMsg}
                        <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                            <input type="hidden" name="type" value="add">
                            <div class="input-group">
                                <input type="text" name="groupName" class="form-control" placeholder="Ange önskat projektgruppsnamn">
                                <span class="input-group-btn">
                            <button class="btn btn-default" type="submit">Skapa</button>
                        </span>
                            </div>
                        </form>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="delete">
                        <p>Ta bort existerande projektgrupp(er) genom att markera den/dem och klicka sedan på <em>ta bort</em>-knappen.</p>
                        ${infoMsg_delete}
                        <form role="form" id="formfield" class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                            <input type="hidden" name="type" value="delete">
                                ${groups}
                        </form>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="assign">
                        <p>Koppla en användare till given projektgrupp.</p>
                            ${infoMsg_assign}
                        <form class="form-inline" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                            <input type="hidden" name="type" value="assign">
                            <div class="form-group">
                                <label for="inputUsername">Användarnamn</label>
                                <select name="assignUser" type="text" class="form-control" id="inputUsername">
                                    ${select_users}
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="inputGroup">Projektgrupp</label>
                                <select name="assignGroup" type="text" class="form-control" id="inputGroup">
                                    ${select_groups}
                                </select>
                            </div>
                            <button type="submit" class="btn btn-default">Tilldela</button>

                        </form>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="assignPl">
                        <p>Koppla en användare till given projektgrupp. Användaren tilldelas även gruppen och den inte redan är medlem</p>
                        ${infoMsg_plAssign}
                        <form class="form-inline" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                            <input type="hidden" name="type" value="assignPl">
                            <div class="form-group">
                                <label for="inputUsernamePl">Användarnamn</label>
                                <select name="assignUserPl" type="text" class="form-control" id="inputUsernamePl">
                                        ${select_users}
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="inputGroupPl">Projektgrupp</label>
                                <select name="assignGroupPl" type="text" class="form-control" id="inputGroupPl">
                                        ${select_groups}
                                </select>
                            </div>
                            <button type="submit" class="btn btn-default">Tilldela</button>

                        </form>
                        <h4>Nuvarande projektledare.</h4>
                        <p><em>Ta bort rollen PL genom att markera användaren och klicka på ta bort-knappen.</em></p>
                            ${infoMsg_plDelete}
                        <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/groups">
                            <input type="hidden" name="type" value="deletePl">
                                ${plGroups}
                        </form>

                    </div>

                </div>

            </div>
            <div class="col-md-2"></div>
        </div>
    </jsp:body>
</t:block>
