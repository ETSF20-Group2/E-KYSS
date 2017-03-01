<%@ tag description="block template" pageEncoding="UTF-8" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="navigation" fragment="true" %>
<%@ attribute name="javascript" fragment="true" %>
<%@ attribute name="stylesheets" fragment="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="sv">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>E-KYSS - ${pageTitle}</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" integrity="sha256-3Jy/GbSLrg0o9y5Z5n1uw0qxZECH7C6OQpVBgNFYa0g=" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js" integrity="sha256-g6iAfvZp+nDQ2TdTR/VVKJf3bGro4ub5fvWSWVRi2NE=" crossorigin="anonymous"></script>
        <![endif]-->
        <jsp:invoke fragment="stylesheets" />
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <jsp:invoke fragment="navigation" />
        </nav>
        <div class="container">
            <jsp:doBody />
        </div>
        <footer>
            <div class="container-fluid">
                <jsp:useBean id="now" class="java.util.Date" />
                <p class="text-muted text-center">Upphovsrätt © <fmt:formatDate value="${now}" pattern="yyyy" /> Gruppen PUSP1702 för ETSF20 vid Campus Hbg.</p>
            </div>
        </footer>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="${pageContext.request.contextPath}/assets/js/ie10-viewport-bug-workaround.js"></script>
        <jsp:invoke fragment="javascript" />
    </body>
</html>
