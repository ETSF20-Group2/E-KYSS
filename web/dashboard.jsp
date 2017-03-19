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
        <table>
            <tr>
                <th>a</th>
                <th>b</th>
                <th>c</th>
            </tr>
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
