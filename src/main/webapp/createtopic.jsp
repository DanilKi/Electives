<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${pageContext.request.servletPath}" scope="session"/>
<html>
<head>
	<title><fmt:message key="createtopic_jsp.title"/></title>
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
	</header>
	<main class="main">
		<div class="container-fluid">
			<div class="row">
			    <div class="col-md-4 col-sm-0"></div>
				<div class="col-md-4 mainframe">
					<h3><fmt:message key="createtopic_jsp.h3"/></h3>
					<form action="front" method="post">
						<input type="hidden" name="command" value="createtopic"/>
						<div class="form-group">
							<label for="input-topic"><fmt:message key="createtopic_jsp.topic"/></label>
							<input type="text" name="topic-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9 '.)(+#\-]{2,45}" class="form-control" id="input-topic" required="required" />
						</div>
						<input type="reset" class="btn btn-dark" value="<fmt:message key="createtopic_jsp.cancel"/>"/>
						<input type="submit" class="btn btn-dark" value="<fmt:message key="createtopic_jsp.submit"/>"/>
					</form>
				</div>
				<div class="col-md-4 col-sm-0"></div>
			</div>
		</div>
	</main>
	<footer class="fixed-bottom bg-dark footer">
		<%@ include file="/_footer.jsp" %>
	</footer>
</body>
</html>