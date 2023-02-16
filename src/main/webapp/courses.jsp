<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="backAddress" value="${requestScope['javax.servlet.forward.servlet_path']}?${pageContext.request.queryString}" scope="session"/>
<html>
<head>
    <title><fmt:message key="courses_jsp.title"/></title>
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
			    <div class="col-md-2 col-sm0"></div>
				<div class="col-md-8 mainframe">
					<h3><fmt:message key="courses_jsp.h3" /></h3>
					<form action="front" method="get" class="form-inline">
						<input type="hidden" name="command" value="getallcourses" />
						<div class="form-group">
							<fmt:message key="courses_jsp.teacher" /> <select name="teacher" class="form-control">
								<option value="0"><fmt:message key="courses_jsp.all_teachers" /></option>
								<c:forEach items="${teachers}" var="teacher">
									<option value="${teacher.id}" ${param.teacher == teacher.id ? "selected='selected'" : ""}>${teacher.firstName} ${teacher.lastName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<fmt:message key="courses_jsp.topic" /> <select name="topic" class="form-control">
								<option value="0"><fmt:message key="courses_jsp.all_topics" /></option>
								<c:forEach items="${topics}" var="topic">
									<option value="${topic.id}" ${param.topic == topic.id ? "selected='selected'" : ""}>${topic.name}</option>
								</c:forEach>
							</select>
						</div>
						<input type="hidden" name="sort" value="${param.sort}" />
						<input type="hidden" name="page" value="1" />
						<input type="submit" class="btn btn-dark" value="<fmt:message key="courses_jsp.select" />" />
					</form>
					<div>
                    	<fmt:message key="sort_courses.sortby" /> <a class="btn btn-dark" href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=az&page=${param.page}" role="button">A-Z</a> |
                    	<a class="btn btn-dark" href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=za&page=${param.page}" role="button">Z-A</a> |
                    	<a class="btn btn-dark" href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=duration&page=${param.page}" role="button"><fmt:message key="sort_courses.duration" /></a> |
                    	<a class="btn btn-dark" href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=students&page=${param.page}" role="button"><fmt:message key="sort_courses.students" /></a>
                    </div>
                    <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col"><fmt:message key="show_courses.title" /></th> <th scope="col"><fmt:message key="show_courses.duration" /></th> <th scope="col"><fmt:message key="show_courses.students" /></th> <th scope="col"> </th>
                            </tr>
                        </thead>
                        <tbody>
                    	    <c:forEach items="${list}" var="course">
                    		    <tr>
                    		        <td>${course.title}</td>
                    		        <td>${course.duration} <fmt:message key="show_courses.days" /></td>
                    		        <td>${course.students} <fmt:message key="show_courses.persons" /></td>
                    			    <td><a href="front?command=courseinfo&course-id=${course.id}" class="mainlink" title="View information about course"><fmt:message key="show_courses.view" /></a></td>
                    		    </tr>
                    	    </c:forEach>
                        </tbody>
                    </table></div>
					<nav aria-label="Pagination of data">
					    <ul class="pagination">
                    	    <c:if test="${page != 1}">
                    			<li class="page-item"><a href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=${param.sort}&page=${page - 1}" class="page-link mainlink"><fmt:message key="pagination.prev" /></a></li>
                      		</c:if>
                    		<c:forEach var="i" begin="1" end="${numOfPages}">
                    		    <c:choose>
                                    <c:when test="${page == i}">
                                        <li class="page-item active" aria-current="page"><a class="page-link">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=${param.sort}&page=${i}" class="page-link mainlink">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                    		</c:forEach>
                    		<c:if test="${page < numOfPages}">
                    		    <li class="page-item"><a href="front?command=getallcourses&teacher=${param.teacher}&topic=${param.topic}&sort=${param.sort}&page=${page + 1}" class="page-link mainlink"><fmt:message key="pagination.next" /></a></li>
                    		</c:if>
                        </ul>
                        <div>${nothingFound}</div>
                    </nav>
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