<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${pageContext.request.servletPath}" scope="session"/>
<html>
<head>
	<c:if test="${authorizedUser.id != course.teacherId}">
		<fmt:message var="errorMessage" key="ejournal_jsp.forbidden" scope="session" />
		<c:redirect url="error.jsp" />
	</c:if>
    <title><fmt:message key="ejournal_jsp.title"/></title>
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
					<h3><fmt:message key="ejournal_jsp.h3" /></h3>
						<div class="ejournal">${course.title}</div>
						<form action="front" method="post">
							<input type="hidden" name="command" value="submitsetmarks" />
							<input type="hidden" name="course-id" value="${course.id}" />
							<table class="table table-striped table-hover">
								<thead class="thead-dark"><tr><th scope="col"><fmt:message key="ejournal_jsp.student" /></th><th scope="col"><fmt:message key="ejournal_jsp.mark" /></th></tr></thead>
								<tbody>
									<c:forEach items="${courseStudents}" var="courseStudent">
										<tr>
										    <td><input type="hidden" name="student-id" value="${courseStudent.student.id}">${courseStudent.student.lastName} ${courseStudent.student.firstName}</td>
										    <td><input type="text" name="mark" maxlength="3" size="5" value="${courseStudent.mark}"></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<input type="submit" class="btn btn-dark" value="<fmt:message key="ejournal_jsp.submit" />" />
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