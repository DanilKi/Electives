<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${requestScope['javax.servlet.forward.servlet_path']}?${pageContext.request.queryString}" scope="session"/>
<html>
<head>
	<c:if test="${role.getName() != 'admin' && authorizedUser.id != editUser.id}">
		<fmt:message var="errorMessage" key="edituser_jsp.forbidden" scope="session" />
		<c:redirect url="error.jsp" />
	</c:if>
    <title><fmt:message key="edituser_jsp.title"/></title>
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
					<h3><fmt:message key="edituser_jsp.h3" /></h3>
					<form action="front" method="post">
						<input type="hidden" name="command" value="submitediteduser" />
						<div class="form-group">
                        	<label for="input-email"><fmt:message key="edituser_jsp.email" /></label>
                        	<input type="email" name="email" class="form-control" id="input-email" value="${editUser.email}" required="required" />
                        </div>
						<div class="form-group">
							<label for="input-password"><fmt:message key="edituser_jsp.password" /></label>
							<input type="password" name="password" pattern="[a-zA-Z0-9]{5,32}" class="form-control" id="input-password" />
						    <div><fmt:message key="edituser_jsp.psw_msg" /></div>
						</div>
						<div class="form-group">
							<label for="input-first-name"><fmt:message key="edituser_jsp.firstname" /></label>
							<input type="text" name="first-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9'\-]{2,20}" class="form-control" id="input-first-name" value="${editUser.firstName}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-last-name"><fmt:message key="edituser_jsp.lastname" /></label>
							<input type="text" name="last-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9'\-]{2,20}" class="form-control" id="input-last-name" value="${editUser.lastName}" required="required" />
						</div>
						<c:if test="${role.getName() == 'admin'}">
							<div class="form-group">
								<label for="input-status"><fmt:message key="edituser_jsp.status" /></label>
								<select name="status" class="form-control" id="input-status" required="required">
									<option value="false" ${editUser.isBlocked() == false ? "selected='selected'" : ""} ><fmt:message key="edituser_jsp.active" /></option>
									<option value="true" ${editUser.isBlocked() == true ? "selected='selected'" : ""} ><fmt:message key="edituser_jsp.blocked" /></option>
								</select>
							</div>
						</c:if>
						<input type="reset" class="btn btn-dark" value="<fmt:message key="edituser_jsp.cancel" />" />
						<input type="submit" class="btn btn-dark" value="<fmt:message key="edituser_jsp.submit" />" />
					</form>
				</div>
				<div class="col-md-4 col-sm-0"></div>
			</div>
		</div>
	</main>
	<footer class="fixed-bottom bd-dark footer">
		<%@ include file="/_footer.jsp" %>
	</footer>
</body>
</html>