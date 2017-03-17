<%@ tag description="block template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="container" fragment="true" %>
<div class="container-fluid">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">E-KYSS</a>
    </div>
    <div class="collapse navbar-collapse" id="navbar-collapse">
        <ul class="nav navbar-nav">
            <jsp:invoke fragment="container" />
            <c:choose>
                <c:when test="${sessionScope.state eq 1}">
                    <c:if test="${sessionScope.name eq 'admin'}">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Administration <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/management/groups">Hantering av projektgrupper</a></li>
                                <li><a href="${pageContext.request.contextPath}/management/users">Hantering av användare</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${not sessionScope.ProjectLeader and sessionScope.name ne 'admin'}">
                    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Användare <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/dashboard">Sammanställning</a></li>
                            <li><a href="${pageContext.request.contextPath}/report">Veckorapportering</a></li>
                            <li><a href="${pageContext.request.contextPath}/settings/user">Kontoinställningar</a></li>
                        </ul>
                    </li>
                    </c:if>

                    <c:if test="${sessionScope.ProjectLeader and sessionScope.name ne 'admin'}">
                    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Projektledare <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/dashboard">Sammanställning</a></li>
                            <li><a href="${pageContext.request.contextPath}/report">Veckorapportering</a></li>
                            <li><a href="${pageContext.request.contextPath}/management/users">Hantering av användare</a></li>
                            <li><a href="${pageContext.request.contextPath}/management/reports">Hantering av veckorapporter</a></li>
                        </ul>
                    </li>
                </c:if>



                    <li><a href="${pageContext.request.contextPath}/logout">Logga ut</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/">Logga in</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">Admin</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
        <c:if test="${sessionScope.state eq 1}">
            <c:choose>
                <c:when test="${sessionScope.name eq 'admin'}">
                    <a href="#" type="button" class="btn btn-default navbar-btn navbar-right logedin" aria-label="Left Align">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${sessionScope.name}
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/settings/user" type="button" class="btn btn-default navbar-btn navbar-right logedin" aria-label="Left Align">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${sessionScope.name}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
</div>
