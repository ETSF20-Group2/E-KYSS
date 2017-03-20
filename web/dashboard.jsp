<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="reportActivityList" class="java.util.LinkedHashMap"/>
<c:set target="${reportActivityList}" property="11" value="SDP" />
<c:set target="${reportActivityList}" property="12" value="SRS" />
<c:set target="${reportActivityList}" property="13" value="SVVS" />
<c:set target="${reportActivityList}" property="14" value="STLDD" />
<c:set target="${reportActivityList}" property="15" value="SVVI" />
<c:set target="${reportActivityList}" property="16" value="SDDD" />
<c:set target="${reportActivityList}" property="17" value="SVVR" />
<c:set target="${reportActivityList}" property="18" value="SSD" />
<c:set target="${reportActivityList}" property="19" value="Slutrapport" />
<c:set target="${reportActivityList}" property="21" value="Funktionstest" />
<c:set target="${reportActivityList}" property="22" value="Systemtest" />
<c:set target="${reportActivityList}" property="23" value="Regresionstest" />
<c:set target="${reportActivityList}" property="30" value="Möten" />
<c:set target="${reportActivityList}" property="41" value="Lektion" />
<c:set target="${reportActivityList}" property="42" value="Övning" />
<c:set target="${reportActivityList}" property="43" value="Datorövning" />
<c:set target="${reportActivityList}" property="44" value="Hemläseri" />
<c:set target="${reportActivityList}" property="100" value="Annat" />

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
                <td>_SUM_</td>
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
                        <c:set var="d" value="${'d_'.concat(activity.key)}" />
                        <c:set var="i" value="${'i_'.concat(activity.key)}" />
                        <c:set var="f" value="${'f_'.concat(activity.key)}" />
                        <c:set var="r" value="${'r_'.concat(activity.key)}" />
                        <c:set var="t" value="${'t_'.concat(activity.key)}" />
                        <td>${activity.value}</td>
                        <td>${bean.getReportValuesSum()[d]}</td>
                        <td>${bean.getReportValuesSum()[i]}</td>
                        <td>${bean.getReportValuesSum()[f]}</td>
                        <td>${bean.getReportValuesSum()[r]}</td>
                        <td>${bean.getReportValuesSum()[t]}</td>
                    </c:if>
                    <c:if test="${activity.key gt 20}">
                        <c:set var="t" value="${'t_'.concat(activity.key)}" />
                        <td colspan="5">${activity.value}</td>
                        <td>${bean.getReportValuesSum()[t]}</td>
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
                <td>Utveckling av ny kod, testfall och dokumentation, inklusive systemdokumentering.</td>
                <td>_SUM_</td>
            </tr>
            <tr>
                <td>Informell granskning</td>
                <td>I</td>
                <td>Spenderad tid åt förberedelse och åt informell granskning.</td>
                <td>_SUM_</td>
            </tr>
            <tr>
                <td>Formell granskning</td>
                <td>F</td>
                <td>Spenderad tid åt föreberedelse och åt informell granskning.</td>
                <td>_SUM_</td>
            </tr>
            <tr>
                <td>Omarbete, förbättring, rättning</td>
                <td>R</td>
                <td>Spenderad tid åt förbättring, omprövning eller dokument- och designobjektsrättning.</td>
                <td>_SUM_</td>
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
    <jsp:attribute name="stylesheets">
        <style>
            table {
                max-width: 600px;
                width: 100%;
            }
            th {
                background-color: #F2F2F2;
            }
            th, td {
                vertical-align: middle;
            }
        </style>
    </jsp:attribute>
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
