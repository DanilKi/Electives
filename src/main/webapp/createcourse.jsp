<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${requestScope['javax.servlet.forward.servlet_path']}?${pageContext.request.queryString}" scope="session"/>
<html>
<head>
    <title><fmt:message key="createcourse_jsp.title"/></title>
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
					<h3><fmt:message key="createcourse_jsp.h3" /></h3>
					<form action="front" method="post">
						<input type="hidden" name="command" value="submitcreatedcourse" />
						<div class="form-group">
							<label for="input-title"><fmt:message key="createcourse_jsp.course" /></label>
							<input type="text" name="title" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9 ',.)(\-]{5,45}" class="form-control" id="input-title" required="required" />
						</div>
						<div class="form-group">
							<label for="input-topic"><fmt:message key="createcourse_jsp.topic" /></label>
							<select name="topic-id" class="form-control" id="input-topic" required="required">
                            	<c:forEach items="${topics}" var="topic">
                            		<option value="${topic.id}">${topic.name}</option>
                            	</c:forEach>
                            </select>
						</div>
						<div class="form-group">
							<label for="input-start-date"><fmt:message key="createcourse_jsp.start" /></label>
							<input type="date" name="start-date" class="form-control" id="input-start-date" placeholder="YYYY-MM-DD" required="required" />
						</div>
						<div class="form-group">
							<label for="input-end-date"><fmt:message key="createcourse_jsp.end" /></label>
							<input type="date" name="end-date" class="form-control" id="input-end-date" placeholder="YYYY-MM-DD" required="required" />
						</div>
						<div class="form-group">
							<label for="input-teacher"><fmt:message key="createcourse_jsp.teacher" /></label>
							<select name="teacher-id" class="form-control" id="input-teacher" required="required">
								<c:forEach items="${teachers}" var="teacher">
									<option value="${teacher.id}">${teacher.lastName} ${teacher.firstName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
                        	<label for="input-description"><fmt:message key="createcourse_jsp.description" /></label>
                        	<input type="textarea" name="description" maxlength="255" rows="3" class="form-control" id="input-description" />
                        </div>
						<input type="reset" class="btn btn-dark" value="<fmt:message key="createcourse_jsp.cancel" />" />
						<input type="submit" class="btn btn-dark" value="<fmt:message key="createcourse_jsp.submit" />" />
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