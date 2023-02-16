<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="backAddress" value="${pageContext.request.servletPath}" scope="session"/>
<c:if test="${empty currentLocale}">
    <c:set var="currentLocale" value="uk" scope="session"/>
</c:if>
<html>
<head>
    <title><fmt:message key="index_jsp.title"/></title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script defer src="js/jquery.min.js"></script>
    <script defer src="js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
		<%@ include file="/_menu.jsp" %>
	</nav>
	<header class="header">
		<%@ include file="/_header.jsp" %>
	</header>
	<main class="main">
		<div class="container-fluid">
			<div class="row">
                <div class="col-md-4 col-sm-0"></div>
				<div class="col-md-4 mainframe">
					<div><c:out value="${infoMessage}" /></div>
					<c:remove var="infoMessage" />
					<c:remove var="editUser" />
				</div>
				<div class="col-md-4 col-sm-0"></div>
			</div>
			<c:if test="${empty authorizedUser}">
			    <div class="row">
			        <div class="col-md-4 col-sm-0"></div>
			        <div class="col-md-4">
                        <%@ include file="/_login.jsp" %>
                    </div>
                    <div class="col-md-4 col-sm-0"></div>
			    </div>
			</c:if>
		</div>
	</main>
	<footer class="fixed-bottom bg-dark footer">
		<%@ include file="/_footer.jsp" %>
	</footer>
</body>
</html>