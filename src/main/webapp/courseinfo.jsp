<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${requestScope['javax.servlet.forward.servlet_path']}?${pageContext.request.queryString}" scope="session"/>
<html>
<head>
    <title><fmt:message key="courseinfo_jsp.title"/></title>
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
			    <div class="col-md-2 col-sm-0"></div>
				<div class="col-md-8 mainframe">
					<h3><fmt:message key="courseinfo_jsp.h3" /></h3>
					<div>
						<p><fmt:message key="courseinfo_jsp.course" /> ${course.title}</p>
						<p><fmt:message key="courseinfo_jsp.topic" /> ${topic.name}</p>
						<p><fmt:message key="courseinfo_jsp.start" /> ${course.startDate}</p>
						<p><fmt:message key="courseinfo_jsp.end" /> ${course.endDate}</p>
						<p><fmt:message key="courseinfo_jsp.duration" /> ${course.duration} <fmt:message key="courseinfo_jsp.days" /></p>
						<p><fmt:message key="courseinfo_jsp.students" /> ${course.students}</p>
						<p><fmt:message key="courseinfo_jsp.description" /> ${course.description}</p><br/>
					</div>
					<div class="coursemenu">
						<c:choose>
							<c:when test="${role.getName() == 'admin'}">
								<p>${infoMessage}</p>
								<form action="front" method="get" class="side-by-side">
									<input type="hidden" name="command" value="editcourse" />
									<input type="hidden" name="course-id" value="${course.id}" />
									<input type="submit" class="btn btn-dark" value="<fmt:message key="courseinfo_jsp.edit" />" />
								</form>
								<form action="front" method="post" class="side-by-side">
									<input type="hidden" name="command" value="deletecourse" />
									<input type="hidden" name="course-id" value="${course.id}" />
									<input type="submit" class="btn btn-dark" value="<fmt:message key="courseinfo_jsp.delete" />" />
								</form>
							</c:when>
							<c:when test="${role.getName() == 'teacher'}">
								<p>${infoMessage}</p>
								<c:choose>
									<c:when test="${authorizedUser.id == course.teacherId && course.startDate <= currentDate && course.endDate > currentDate}">
										<fmt:message key="courseinfo_jsp.t_inprogress" />
									</c:when>
									<c:when test="${authorizedUser.id == course.teacherId && course.startDate > currentDate}">
										<fmt:message key="courseinfo_jsp.t_notstarted" />
									</c:when>
									<c:when test="${authorizedUser.id == course.teacherId && course.endDate <= currentDate}">
										<c:set var="course" value="${course}" scope="session" />
										<form action="front" method="post" class="side-by-side">
											<input type="hidden" name="command" value="setmarks" />
											<input type="submit" class="btn btn-dark" value="<fmt:message key="courseinfo_jsp.set_marks" />" />
										</form>
									</c:when>
									<c:otherwise>
										<fmt:message key="courseinfo_jsp.notassigned" />
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${role.getName() == 'student'}">
								<p>${infoMessage}</p>
								<c:set var="found" value="0" />
								<c:forEach items="${courseList}" var="courseStudent">
									<c:if test="${course.id == courseStudent.course.id && course.startDate < currentDate && course.endDate > currentDate}">
										<fmt:message key="courseinfo_jsp.s_inprogress" />
										<c:set var="found" value="1" />
									</c:if>
									<c:if test="${course.id == courseStudent.course.id && course.startDate > currentDate}">
										<fmt:message key="courseinfo_jsp.s_notstarted" />
										<c:set var="found" value="1" />
									</c:if>
									<c:if test="${course.id == courseStudent.course.id && course.endDate < currentDate}">
										<fmt:message key="courseinfo_jsp.mark" /> ${courseStudent.mark}
										<c:set var="found" value="1" />
									</c:if>
								</c:forEach>
								<c:if test="${found == 0}">
									<c:if test="${course.startDate > currentDate}">
										<form action="front" method="post" class="side-by-side">
											<input type="hidden" name="command" value="enroll" />
											<input type="hidden" name="course-id" value="${course.id}" />
											<input type="hidden" name="student-id" value="${authorizedUser.id}" />
											<input type="submit" class="btn btn-dark" value="<fmt:message key="courseinfo_jsp.enroll" />" />
										</form>
									</c:if>
									<c:if test="${course.startDate < currentDate}">
										<fmt:message key="courseinfo_jsp.noenroll" />
									</c:if>
								</c:if>
								<c:remove var="found" />
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${course.startDate > currentDate}">
										<fmt:message key="courseinfo_jsp.guest" /> <a href="login.jsp" class="mainlink"> <fmt:message key="courseinfo_jsp.login" /> </a>
										<fmt:message key="courseinfo_jsp.or" /><a href="register.jsp" class="mainlink"> <fmt:message key="courseinfo_jsp.register" /></a>
									</c:when>
									<c:otherwise>
										<fmt:message key="courseinfo_jsp.noenroll" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
					<c:remove var="infoMessage" />
					<c:remove var="editCourse" />
				</div>
				<div class="col-md-2 col-sm-0"></div>
			</div>
		</div>
	</main>
	<footer class="fixed-bottom bg-dark footer">
		<%@ include file="/_footer.jsp" %>
	</footer>
</body>
</html>