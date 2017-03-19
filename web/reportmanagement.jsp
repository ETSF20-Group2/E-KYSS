<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="signReports">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getAllUnsignedReports()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det finns inga rapporter att visa.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                <tr>
                    <th>Signera</th>
                    <th>Vecka</th>
                    <th>Användare</th>
                    <th>D</th>
                    <th>I</th>
                    <th>F</th>
                    <th>R</th>
                    <th>Övrigt</th>
                    <th>Total tid</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bean.getAllUnsignedReports()}" var="unsignedReport">
                    <tr>
                        <td><input name="signReport" type="checkbox" value="${unsignedReport[0].concat(" ".concat(unsignedReport[1]))}"></td>
                        <td>${unsignedReport[0]}</td>
                        <td>${unsignedReport[1]}</td>
                        <td>${unsignedReport[2]}</td>
                        <td>${unsignedReport[3]}</td>
                        <td>${unsignedReport[4]}</td>
                        <td>${unsignedReport[5]}</td>
                        <td>${unsignedReport[6]}</td>
                        <td>${unsignedReport[7]}</td>

                    </tr>
                </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <c:if test="${not empty bean.getAllUnsignedReports()}">
        <button class="btn btn-default" type="submit">Signera</button>
    </c:if>
</c:set>

<c:set var="unsignReports">
    <table class="table table-hover">
        <c:choose>
            <c:when test="${empty bean.getAllSignedReports()}">
                <div class="alert alert-info" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    Det finns inga rapporter att visa.
                </div>
            </c:when>
            <c:otherwise>
                <thead>
                <tr>

                    <th>Annullera</th>
                    <th>Vecka</th>
                    <th>Användare</th>
                    <th>D</th>
                    <th>I</th>
                    <th>F</th>
                    <th>R</th>
                    <th>Övrigt</th>
                    <th>Total tid</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bean.getAllSignedReports()}" var="signedReport">
                    <tr>
                        <td><input name="unsignReport" type="checkbox" value="${signedReport[0].concat(" ".concat(signedReport[1]))}"></td>
                        <td>${signedReport[0]}</td>
                        <td>${signedReport[1]}</td>
                        <td>${signedReport[2]}</td>
                        <td>${signedReport[3]}</td>
                        <td>${signedReport[4]}</td>
                        <td>${signedReport[5]}</td>
                        <td>${signedReport[6]}</td>
                        <td>${signedReport[7]}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </c:otherwise>
        </c:choose>
    </table>
    <c:if test="${not empty bean.getAllSignedReports()}">
        <button class="btn btn-default" type="submit">Annullera</button>
    </c:if>
</c:set>

<c:set var="signInfo">
    <c:if test="${bean.getErr_code() eq 1}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Rapporterna har signerats.
        </div>
    </c:if>
</c:set>

<c:set var="unsignInfo">
    <c:if test="${bean.getErr_code() eq 2}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Rapporterna har annullerats.
        </div>
    </c:if>
</c:set>

<t:block pageTitle="Tidrapporteringshanterare">
    <jsp:attribute name="stylesheets" />
    <jsp:attribute name="navigation" />
    <jsp:attribute name="javascript" >
    <script>
        $(document).ready(function(){
            activaTab('${bean.getTab()}');
        });

        function activaTab(tab){
            $('.nav-tabs a[href="#' + tab + '"]').tab('show');
        };
    </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">

            <h2 class="form-signin-heading">Hantering av veckorapporter</h2>
            <p class="form-signin-heading">Signera eller annullera rapporter</p>

            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#sign" aria-controls="sign" role="tab" data-toggle="tab">Signera rapporter</a></li>
                <li role="presentation"><a href="#unsign" aria-controls="unsign" role="tab" data-toggle="tab">Annullera rapporter</a></li>
            </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="sign">
                        <h3>Signera tidrapporter</h3>
                        ${signInfo}
                        <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/reports">
                            <input type="hidden" name="type" value="sign">
                                ${signReports}
                        </form>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="unsign">
                        <h3>Annullera tidrapporter</h3>
                        ${unsignInfo}
                        <form class="form-signin" name="input" method="POST" action="${pageContext.request.contextPath}/management/reports">
                            <input type="hidden" name="type" value="unsign">
                                ${unsignReports}
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:block>
