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
    </jsp:attribute>
    <jsp:body>
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
                            <td><input type="text" name="d_${activity.key}" class="form-control input-sm" size="1" values="" placeholder="0" onChange="checkValidTI(this)"></td>
                            <td><input type="text" name="i_${activity.key}" class="form-control input-sm" size="1" values="" placeholder="0" onChange="checkValidTI(this)"></td>
                            <td><input type="text" name="f_${activity.key}" class="form-control input-sm" size="1" values="" placeholder="0" onChange="checkValidTI(this)"></td>
                            <td><input type="text" name="r_${activity.key}" class="form-control input-sm" size="1" values="" placeholder="0" onChange="checkValidTI(this)"></td>
                            <td><input type="text" name="t_${activity.key}" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                        </c:if>
                        <c:if test="${activity.key gt 20}">
                            <td colspan="5">${activity.value}</td>
                            <td><input type="text" name="t_${activity.key}" class="form-control input-sm" size="1" values="" placeholder="0" onChange="checkValidTI(this)"></td>

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
                    <td>n/a</td>
                    <td><input type="text" name="t_d" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                </tr>
                <tr>
                    <td>n/a</td>
                    <td>I</td>
                    <td>n/a</td>
                    <td><input type="text" name="t_i" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                </tr>
                <tr>
                    <td>n/a</td>
                    <td>F</td>
                    <td>n/a</td>
                    <td><input type="text" name="t_f" values="" placeholder="0" class="form-control input-sm" size="1" disabled></td>
                </tr>
                <tr>
                    <td>n/a</td>
                    <td>R</td>
                    <td>n/a</td>
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
    </jsp:body>
</t:block>
