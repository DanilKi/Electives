<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${pageContext.request.servletPath}" scope="session"/>
<html>
<head>
	<title><fmt:message key="register_jsp.title"/></title>
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
					<h3><fmt:message key="register_jsp.h3"/></h3>
					<form action="front" method="post">
						<input type="hidden" name="command" value="register"/>
						<div class="form-group">
                        	<label for="input-email"><fmt:message key="register_jsp.email"/></label>
                        	<input type="email" name="email" class="form-control" id="input-email" required="required" />
                        </div>
						<div class="form-group">
							<label for="input-password"><fmt:message key="register_jsp.password"/></label>
							<input type="password" name="password" pattern="[a-zA-Z0-9]{5,32}" class="form-control" id="input-password" required="required" />
						</div>
						<div class="form-group">
							<label for="input-firstname"><fmt:message key="register_jsp.firstname"/></label>
							<input type="text" name="firstname" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ'\-]{2,20}" class="form-control" id="input-firstname" required="required" />
						</div>
						<div class="form-group">
							<label for="input-lastname"><fmt:message key="register_jsp.lastname" /></label>
							<input type="text" name="lastname" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ'\-]{2,20}" class="form-control" id="input-lastname" required="required" />
						</div>
						<c:if test="${role.getName() == 'admin'}">
							<div class="form-group">
								<label for="input-role"><fmt:message key="register_jsp.role" /></label>
								<select name="roleid" class="form-control" id="input-role" required="required">
									<option value="1" selected="selected"><fmt:message key="register_jsp.teacher"/></option>
									<option value="2"><fmt:message key="register_jsp.student"/></option>
								</select>
							</div>
						</c:if>
						<input type="reset" class="btn btn-dark" value="<fmt:message key="register_jsp.cancel"/>"/>
						<input type="submit" class="btn btn-dark" value="<fmt:message key="register_jsp.submit"/>"/>
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