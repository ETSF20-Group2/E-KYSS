<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/>
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
<!--
<c:set var="d_">
    d_
</c:set>
-->

<c:set var="select_weeks">
    <c:forEach items="${bean.getAllWeeks()}" var="week">
        <option value="${week}">${week}</option>
    </c:forEach>
</c:set>

<c:set var="infoMsgCreate">
    <c:if test="${bean.getErr_code() eq 1}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Rapporten har skapats.
        </div>
    </c:if>
    <c:if test="${bean.getErr_code() eq 4}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har redan rapporterat för den valda veckan. Om du vill ändra rapporten gå till "Ändra tidrapport"
             eller kontakta projektledaren för annulering av signerad tidrapport.
        </div>
    </c:if>
</c:set>
<c:set var="infoMsgUpdate">
    <c:if test="${bean.getErr_code() eq 2}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
             Rapporten har uppdaterats.
        </div>
    </c:if>
</c:set>
<c:set var="infoMsgRemove">
    <c:if test="${bean.getErr_code() eq 3}">
        <div class="alert alert-success" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Rapporten har tagits bort.
        </div>
    </c:if>
</c:set>
<c:set var="infoMsgRemove">
    <c:if test="${bean.getErr_code() eq 5}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Du har ännu inte blivit tilldelad någon roll inom din projektgrupp och därför kan du inte tidrapportera.
        </div>
    </c:if>
</c:set>

<t:block pageTitle="Tidrapportering">
    <jsp:attribute name="stylesheets">
        <style>
            .container {
                max-width: 600px;
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
    <jsp:attribute name="javascript">
        <script src="${pageContext.request.contextPath}/assets/js/formvalidation.js"></script>
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
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#create" aria-controls="create" role="tab" data-toggle="tab">Skapa tidrapport</a></li>
            <li role="presentation"><a href="#update" aria-controls="update" role="tab" data-toggle="tab">Ändra tidrapport</a></li>
            <li role="presentation"><a href="#remove" aria-controls="remove" role="tab" data-toggle="tab">Ta bort tidrapport</a></li>
        </ul>

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="create">
                <h3>Skapa en ny tidrapport</h3>
                ${infoMsgCreate}
                <form name="input" method="POST" action="${pageContext.request.contextPath}/report">
                    <input type="hidden" name="type" value="create">
                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <td colspan="2">Namn:</td>
                            <td colspan="2"><input type="text" name="name" value="${sessionScope.name}" class="form-control input-sm" size="1" disabled></td>
                            <td colspan="2">Datum:</td>
                            <td><input type="text" name="date" value="<fmt:formatDate value="${now}" pattern="yyMMdd" />" class="form-control input-sm" size="1" disabled></td>
                        </tr>
                        <tr>
                            <td colspan="2">Grupp:</td>
                            <td colspan="2">${sessionScope.group}</td>
                            <td colspan="2">Vecka:</td>
                            <td>
                                <select class="form-control input-sm" name="week">
                                    <fmt:formatDate value="${now}" pattern="w" var="week" />
                                    <c:forEach var="i" begin="1" end="53" step="1" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${week eq i}">
                                                <option selected>${i}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${i}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="6">Del A: Total tid denna vecka (minuter)</th>
                            <td><input type="text" name="sum" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
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
                                    <td><input type="text" name="d_${activity.key}" class="form-control input-sm" size="1" values="" id="d_${activity.key}0" placeholder="0" onChange="checkValidTI(this, 0)"></td>
                                    <td><input type="text" name="i_${activity.key}" class="form-control input-sm" size="1" values="" id="i_${activity.key}0" placeholder="0" onChange="checkValidTI(this, 0)"></td>
                                    <td><input type="text" name="f_${activity.key}" class="form-control input-sm" size="1" values="" id="f_${activity.key}0"placeholder="0" onChange="checkValidTI(this, 0)"></td>
                                    <td><input type="text" name="r_${activity.key}" class="form-control input-sm" size="1" values="" id="r_${activity.key}0"placeholder="0" onChange="checkValidTI(this, 0)"></td>
                                    <td><input type="text" name="t_${activity.key}" values="" placeholder="0" class="form-control input-sm" id="t_${activity.key}0" size="1" disabled></td>
                                </c:if>
                                <c:if test="${activity.key gt 20}">
                                    <td colspan="5">${activity.value}</td>
                                    <td><input type="text" name="t_${activity.key}" class="form-control input-sm" size="1" values="" id="t_${activity.key}0" placeholder="0" onChange="checkValidTI(this, 0)"></td>

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
                            <td><input type="text" name="t_d" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                        </tr>
                        <tr>
                            <td>Informell granskning</td>
                            <td>I</td>
                            <td>Spenderad tid åt förberedelse och åt informell granskning.</td>
                            <td><input type="text" name="t_i" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                        </tr>
                        <tr>
                            <td>Formell granskning</td>
                            <td>F</td>
                            <td>Spenderad tid åt föreberedelse och åt informell granskning.</td>
                            <td><input type="text" name="t_f" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                        </tr>
                        <tr>
                            <td>Omarbetning, förbättring eller rättning</td>
                            <td>R</td>
                            <td>Spenderad tid för att förbättra, revidera eller rätta dokument och designobjekt.</td>
                            <td><input type="text" name="t_r" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                        </tr>
                        </tbody>
                    </table>

                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <th colspan="2">
                                Del D: Signatur
                            </th>
                        </tr>
                        <tr>
                            <td>Signerad av projektledare:</td>
                            <td><input type="text" name="" value="" class="form-control input-sm" size="1" disabled></td>
                        </tr>
                        </tbody>
                    </table>

                    <input type="submit" value="Skicka in tidrapport" class="btn btn-success center-block">
                </form>
            </div>

            <!-- För uppdatering -->
        <div role="tabpanel" class="tab-pane" id="update">
            <h3>Uppdatera osignerad tidrapport</h3>
            ${infoMsgUpdate}
            <form class="form-inline" name="input" method="GET" action="${pageContext.request.contextPath}/report">
                <input type="hidden" name="type" value="weekSelect">
                <label for="inputWeeks">Vecka</label>
                <select name="week" type="text" class="form-control" id="inputWeeks">
                        ${select_weeks}
                </select>
                <input type="submit" value="Välj" class="btn btn-success">
            </form>

            <form name="input" method="POST" action="${pageContext.request.contextPath}/report">
                <input type="hidden" name="type" value="update">

                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <td colspan="2">Namn:</td>
                        <td colspan="2"><input type="text" name="name" value="${sessionScope.name}" class="form-control input-sm" size="1" disabled></td>
                        <td colspan="2">Datum:</td>
                        <td><input type="text" name="date" value="<fmt:formatDate value="${now}" pattern="yyMMdd" />" class="form-control input-sm" size="1" disabled></td>
                    </tr>
                    <tr>
                        <td colspan="2">Grupp:</td>
                        <td colspan="2">${sessionScope.group}</td>
                        <td colspan="2">Vecka:</td>
                        <td> <!--<input type="text" name="week" value="${bean.getWeek()}" class="from-control input-sm" size="1"></td> -->
                            <select class="form-control input-sm" name="week">
                                <option selected>${bean.getWeek()}</option>
                            </select> </td>
                    </tr>
                    <tr>
                        <th colspan="6">Del A: Total tid denna vecka (minuter)</th>
                        <td><input type="text" name="sum" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
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
                    <script src="${pageContext.request.contextPath}/assets/js/formvalidation.js"></script>
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
                                <td><input type="text" name="d_${activity.key}" class="form-control input-sm" size="1" id="d_${activity.key}1" values="" value="${bean.getReportValues().get("d_".concat(activity.key))}" placeholder="0" onChange="checkValidTI(this, 1)"></td>
                                <td><input type="text" name="i_${activity.key}" class="form-control input-sm" size="1" id="i_${activity.key}1" values="" value="${bean.getReportValues().get("i_".concat(activity.key))}" placeholder="0" onChange="checkValidTI(this, 1)"></td>
                                <td><input type="text" name="f_${activity.key}" class="form-control input-sm" size="1" id="f_${activity.key}1" values="" value="${bean.getReportValues().get("f_".concat(activity.key))}" placeholder="0" onChange="checkValidTI(this, 1)"></td>
                                <td><input type="text" name="r_${activity.key}" class="form-control input-sm" size="1" id="r_${activity.key}1" values="" value="${bean.getReportValues().get("r_".concat(activity.key))}" placeholder="0" onChange="checkValidTI(this, 1)"></td>
                                <td><input type="text" name="t_${activity.key}" values="" placeholder="0" id="t_${activity.key}1" class="form-control input-sm" size="1" disabled></td>
                            </c:if>
                            <c:if test="${activity.key gt 20}">
                                <td colspan="5">${activity.value}</td>
                                <td><input type="text" name="t_${activity.key}" class="form-control input-sm" size="1" values="" value="${bean.getReportValues().get("t_".concat(activity.key))}" id="t_${activity.key}1" placeholder="0" onChange="checkValidTI(this, 1)"></td>
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
                        <td><input type="text" name="t_d" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                    </tr>
                    <tr>
                        <td>Informell granskning</td>
                        <td>I</td>
                        <td>Spenderad tid åt förberedelse och åt informell granskning.</td>
                        <td><input type="text" name="t_i" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                    </tr>
                    <tr>
                        <td>Formell granskning</td>
                        <td>F</td>
                        <td>Spenderad tid åt föreberedelse och åt informell granskning.</td>
                        <td><input type="text" name="t_f" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                    </tr>
                    <tr>
                        <td>Omarbetning, förbättring eller rättning</td>
                        <td>R</td>
                        <td>Spenderad tid för att förbättra, revidera eller rätta dokument och designobjekt.</td>
                        <td><input type="text" name="t_r" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                    </tr>
                    </tbody>
                </table>

                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th colspan="2">
                            Del D: Signatur
                        </th>
                    </tr>
                    <tr>
                        <td>Signerad av projektledare:</td>
                        <td><input type="text" name="" value="" class="form-control input-sm" size="1" disabled></td>
                    </tr>
                    </tbody>
                </table>

                <input type="submit" value="Uppdatera" class="btn btn-success center-block">
            </form>
        </div>

        <div role="tabpanel" class="tab-pane" id="remove">
            <h3>Ta bort tidrapport</h3>
            ${infoMsgRemove}
            <form class="form-inline" name="input" method="POST" action="${pageContext.request.contextPath}/report">
                    <input type="hidden" name="type" value="remove">
                    <label for="inputWeeks2">Vecka</label>
                    <select name="week" type="text" class="form-control" id="inputWeeks2">
                            ${select_weeks}
                    </select>
                    <input type="submit" value="Ta bort" class="btn btn-alert">
            </form>
        </div>



        </div>
    </jsp:body>
</t:block>
