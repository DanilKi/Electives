<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${requestScope['javax.servlet.forward.servlet_path']}?${pageContext.request.queryString}" scope="session"/>
<html>
<head>
	<title><fmt:message key="mymenu_jsp.title"/></title>
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
					<c:choose>
						<c:when test="${role.getName() == 'admin'}">
							<h3><fmt:message key="mymenu_jsp.h3_admin" /></h3>
							<ul class="list-group">
								<li class="list-group-item">
									<a href="register.jsp" class="mainlink"><fmt:message key="mymenu_jsp.register_user" /></a>
								</li>
								<li class="list-group-item">
									<a href="front?command=getallusers&tab=1&sort=az&page=1" class="mainlink"><fmt:message key="mymenu_jsp.view_users" /></a>
								</li>
								<li class="list-group-item">
									<a href="front?command=createcourse" class="mainlink"><fmt:message key="mymenu_jsp.create_course" /></a>
								</li>
								<li class="list-group-item">
									<a href="front?command=getallcourses&teacher=0&topic=0&sort=none&page=1" class="mainlink"><fmt:message key="mymenu_jsp.view_courses" /></a>
								</li>
								<li class="list-group-item">
                                	<a href="createtopic.jsp" class="mainlink"><fmt:message key="mymenu_jsp.create_topic" /></a>
                                </li>
                                <li class="list-group-item">
                                	<a href="front?command=edittopic" class="mainlink"><fmt:message key="mymenu_jsp.edit_topics" /></a>
                                </li>
							</ul>
						</c:when>
						<c:when test="${role.getName() == 'teacher'}">
							<h3><fmt:message key="mymenu_jsp.h3_courses" /></h3>
							<div>
                            	<ul class="filter-list">
                            		<li class=${param.tab == "inprogress" ? "filter-list-active" : "filter-list-item"}>
                            			<a href="front?command=mymenu&tab=inprogress&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="mymenu_jsp.inprogress" /></a>
                            		</li>
                            		<li class=${param.tab == "notstarted" ? "filter-list-active" : "filter-list-item"}>
                            			<a href="front?command=mymenu&tab=notstarted&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="mymenu_jsp.notstarted" /></a>
                            		</li>
                            		<li class=${param.tab == "finished" ? "filter-list-active" : "filter-list-item"}>
                            			<a href="front?command=mymenu&tab=finished&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="mymenu_jsp.finished" /></a>
                            		</li>
                            	</ul>
                            </div>
        					<div>
                    			<fmt:message key="sort_courses.sortby" /> <a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=az&page=${param.page}" role="button">A-Z</a> |
                            	<a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=za&page=${param.page}" role="button">Z-A</a> |
                            	<a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=duration&page=${param.page}" role="button"><fmt:message key="sort_courses.duration" /></a> |
                            	<a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=students&page=${param.page}" role="button"><fmt:message key="sort_courses.students" /></a>
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
                    				    <li class="page-item"><a href="front?command=mymenu&tab=${param.tab}&sort=${param.sort}&page=${page - 1}" class="page-link mainlink"><fmt:message key="pagination.prev" /></a></li>
                    			    </c:if>
                    			    <c:forEach var="i" begin="1" end="${numOfPages}">
                    			        <c:choose>
                                            <c:when test="${page == i}">
                                                <li class="page-item active"><a class="page-link">${i}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="page-item"><a href="front?command=mymenu&tab=${param.tab}&sort=${param.sort}&page=${i}" class="page-link mainlink">${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                    			    </c:forEach>
                    			    <c:if test="${page < numOfPages}">
                    				    <li class="page-item"><a href="front?command=mymenu&tab=${param.tab}&sort=${param.sort}&page=${page + 1}" class="page-link mainlink"><fmt:message key="pagination.next" /></a></li>
                    			    </c:if>
                                </ul>
                                <div>${nothingFound}</div>
                            </nav>
						</c:when>
						<c:when test="${role.getName() == 'student'}">
							<h3><fmt:message key="mymenu_jsp.h3_courses" /></h3>
							<div>
                            	<ul class="filter-list">
                            		<li class=${param.tab == "inprogress" ? "filter-list-active" : "filter-list-item"}>
                            			<a href="front?command=mymenu&tab=inprogress&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="mymenu_jsp.inprogress" /></a>
                            		</li>
                            		<li class=${param.tab == "notstarted" ? "filter-list-active" : "filter-list-item"}>
                            			<a href="front?command=mymenu&tab=notstarted&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="mymenu_jsp.notstarted" /></a>
                            		</li>
                            		<li class=${param.tab == "finished" ? "filter-list-active" : "filter-list-item"}>
                            			<a href="front?command=mymenu&tab=finished&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="mymenu_jsp.finished" /></a>
                            		</li>
                            	</ul>
                            </div>
        					<div>
                    	        <fmt:message key="sort_courses.sortby" /> <a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=az&page=${param.page}" role="button">A-Z</a> |
                            	<a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=za&page=${param.page}" role="button">Z-A</a> |
                            	<a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=duration&page=${param.page}" role="button"><fmt:message key="sort_courses.duration" /></a> |
                            	<a class="btn btn-dark" href="front?command=mymenu&tab=${param.tab}&sort=students&page=${param.page}" role="button"><fmt:message key="sort_courses.students" /></a>
                            </div>
                            <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col"><fmt:message key="show_courses.title" /></th> <th scope="col"><fmt:message key="show_courses.mark" /></th> <th scope="col"> </th>
                                    </tr>
                                </thead>
                                <tbody>
                            	    <c:forEach items="${list}" var="courseStudent">
                            		    <tr>
                            		        <td>${courseStudent.course.title}</td>
                            		        <c:choose>
                            		            <c:when test="${courseStudent.mark > 0}">
                            		                <td>${courseStudent.mark}</td>
                            		            </c:when>
                            		            <c:otherwise>
                            		                <td><fmt:message key="show_courses.nomark" /></td>
                            		            </c:otherwise>
                            		        </c:choose>
                    			            <td><a href="front?command=courseinfo&course-id=${courseStudent.course.id}" class="mainlink" title="View information about course"><fmt:message key="show_courses.view" /></a></td>
                            		    </tr>
                            	    </c:forEach>
                                </tbody>
                            </table></div>
        					<nav aria-label="Pagination of data">
        					    <ul class="pagination">
                    			    <c:if test="${page != 1}">
                    				    <li class="page-item"><a href="front?command=mymenu&tab=${param.tab}&sort=${param.sort}&page=${page - 1}" class="page-link mainlink"><fmt:message key="pagination.prev" /></a></li>
                    			    </c:if>
                    			    <c:forEach var="i" begin="1" end="${numOfPages}">
                    			        <c:choose>
                                            <c:when test="${page == i}">
                                                <li class="page-item active"><a class="page-link">${i}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="page-item"><a href="front?command=mymenu&tab=${param.tab}&sort=${param.sort}&page=${i}" class="page-link mainlink">${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                    			    </c:forEach>
                    			    <c:if test="${page < numOfPages}">
                    				    <li class="page-item"><a href="front?command=mymenu&tab=${param.tab}&sort=${param.sort}&page=${page + 1}" class="page-link mainlink"><fmt:message key="pagination.next" /></a></li>
                    			    </c:if>
                                </ul>
                                <div>${nothingFound}</div>
                            </nav>
						</c:when>
					</c:choose>
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