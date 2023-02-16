<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${pageContext.request.servletPath}" scope="session"/>
<html>
<head>
    <c:choose>
        <c:when test="${empty authorizedUser}">
            <title><fmt:message key="login_jsp.title"/></title>
        </c:when>
        <c:otherwise>
            <title><fmt:message key="login_jsp.title_p"/></title>
        </c:otherwise>
    </c:choose>
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
					<c:choose>
                    	<c:when test="${not empty authorizedUser}" >
                    	    <h3><fmt:message key="login_jsp.h3_profile"/></h3>
                    		<p><fmt:message key="login_jsp.logged" /> ${authorizedUser.email}</p><br/>
                    		<p><fmt:message key="register_jsp.firstname" /> ${authorizedUser.firstName}</p><br/>
                    		<p><fmt:message key="register_jsp.lastname" /> ${authorizedUser.lastName}</p><br/>
                    		<p><fmt:message key="login_jsp.role" /> ${role.getName()}</p><br/>
                    		<a class="btn btn-dark" href="front?command=edituser&id=${authorizedUser.id}" role="button"><fmt:message key="login_jsp.editprofile" /></a>
                    	</c:when>
                    	<c:otherwise>
                    	    <h3><fmt:message key="login_jsp.h3_login"/></h3>
                    		<form action="front" method="post">
                    			<input type="hidden" name="command" value="login" />
                    			<div class="form-group">
                    				<label for="input-login"><fmt:message key="login_jsp.email" /></label>
                    				<input type="email" name="login" class="form-control" id="input-login" required="required">
                    			</div>
                    			<div class="form-group">
                    				<label for="input-password"><fmt:message key="login_jsp.password" /></label>
                    				<input type="password" name="password" pattern="[a-zA-Z0-9]{5,32}" class="form-control" id="input-password" required="required">
                    			</div>
                    			<input type="reset" class="btn btn-dark" value="<fmt:message key="login_jsp.cancel" />" />
                    			<input type="submit" class="btn btn-dark" value="<fmt:message key="login_jsp.login" />" />
                    		</form>
                    		<p>${errorLogin}</p>
                    		<c:remove var="errorLogin" />
                    	</c:otherwise>
                    </c:choose>
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