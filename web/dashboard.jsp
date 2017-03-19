<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="container_admin">
    <c:if test="${sessionScope.name eq 'admin'}">
        <h2 class="form-signin-heading">Välkommen, administratör!</h2>
        <p class="form-signin-heading">Välj önskad menyval i menyn för att lägga till eller ta bort användare, skapa projektgrupper samt koppla användare till projektgrupper.</p>
    </c:if>
</c:set>
<c:set var="container_pl">
    <c:if test="${sessionScope.ProjectLeader}">
        <h2 class="form-signin-heading">Översikt</h2>
        <p class="form-signin-heading">Här ser du statistik för tidrapporter över all tid som rapporterats i projektet. Du kan se tidsantgång per användare, roll, aktivitet, vecka, fas och dokument.</p>
    </c:if>
</c:set>
<c:set var="container_others">
    <c:if test="${not sessionScope.ProjectLeader and sessionScope.name ne 'admin'}">
        <h2 class="form-signin-heading">Översikt</h2>
        <p class="form-signin-heading">Här kan du se din sammanfattning av din rapporterade tid.</p>
        <table class="table table-bordered">
            <tbody>
            <tr>
                <td colspan="2">Namn:</td>
                <td colspan="2">${sessionScope.name}</td>
                <td colspan="2">Grupp:</td>
                <td colspan="2">${sessionScope.group}</td>
            </tr>
            <tr>
                <th colspan="6">Del A: Total tid denna vecka (minuter)</th>
                <td>9999</td>
            </tr>
            </tbody>
        </table>
        <table class="table table-bordered">
            <tbody>
            <tr>
                <th colspan="7">
                    Del B: Antal minuter per aktivitet<br>
                    <small>(Den totala summan av samtliga aktiviteter summeras automatiskt och läggs in ovanför.)</small>
                </th>
            </tr>
            <tr>
                <td>Nummer</td>
                <td>Aktivitet</td>
                <td class="text-center">D</td>
                <td class="text-center">I</td>
                <td class="text-center">F</td>
                <td class="text-center">R</td>
                <td>Total tid</td>
            </tr>
            <c:forEach items="${reportActivityList}" var="activity">
                <c:if test="${activity.key eq 21}">
                    <tr>
                        <td>Sum</td>
                        <th></th>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <th></th>
                    </tr>
                </c:if>
                <tr>
                    <td>${activity.key}</td>
                    <c:if test="${activity.key lt 20}">
                        <td>${activity.value}</td>
                        <td>9999</td>
                        <td>9999</td>
                        <td>9999</td>
                        <td>9999</td>
                        <td>9999</td>
                    </c:if>
                    <c:if test="${activity.key gt 20}">
                        <td colspan="5">${activity.value}</td>
                        <td>9999</td>

                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <table class="table table-bordered">
            <tbody>
            <tr>
                <th colspan="7">
                    Del C: Tidsaktivitet för olika underaktiviteter<br>
                    <small>(Värdena summeras ihop automatiskt.)</small>
                </th>
            </tr>
            <tr>
                <td>Aktivitetstyp</td>
                <td>Aktivitetskod</td>
                <td>Beskrivning</td>
                <td>Summa</td>
            </tr>
            <tr>
                <td>Utveckling och dokumentation</td>
                <td>D</td>
                <td>Utveckling av ny kod, testfall och dokumentation, klusive systemdokumentering.</td>
                <td>9999</td>
            </tr>
            <tr>
                <td>Informell granskning</td>
                <td>I</td>
                <td>Spenderad tid åt förberedelse och åt informell granskning.</td>
                <td>9999</td>
            </tr>
            <tr>
                <td>Formell granskning</td>
                <td>F</td>
                <td>Spenderad tid åt föreberedelse och åt informell granskning.</td>
                <td>9999</td>
            </tr>
            <tr>
                <td>Omarbete, förbättring, rättning</td>
                <td>R</td>
                <td>Spenderad tid åt förbättring, omprövning eller dokument- och designobjektsrättning.</td>
                <td>9999</td>
            </tr>
            </tbody>
        </table>
    </c:if>
</c:set>
<c:set var="container">
    ${container_admin}
    ${container_pl}
    ${container_others}
</c:set>
<t:block pageTitle="Översiktsidan">
    <jsp:attribute name="stylesheets" />
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript" />
    <jsp:body>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                ${container}
            </div>
            <div class="col-md-3"></div>
        </div>
    </jsp:body>
</t:block>
