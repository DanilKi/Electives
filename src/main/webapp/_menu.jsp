<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<a class="navbar-brand" ><fmt:message key="menu_jsp.header1" /> - <font size=3><fmt:message key="menu_jsp.header2" /></font></a>
<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="<fmt:message key="menu_jsp.navmenu" />">
    <span class="navbar-toggler-icon"></span>
</button>
<div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
	<ul class="navbar-nav mr-4">
	   	<li class="nav-item"><a class="nav-link" href="index.jsp"><fmt:message key="menu_jsp.home" /></a></li>
		<li class="nav-item"><a class="nav-link" href="front?command=getallcourses&teacher=0&topic=0&sort=none&page=1"><fmt:message key="menu_jsp.courses" /></a></li>
		<c:choose>
			<c:when test="${not empty authorizedUser}" >
				<li class="nav-item"><a class="nav-link" href="login.jsp"><fmt:message key="menu_jsp.profile" /></a></li>
				<li class="nav-item"><a class="nav-link" href="front?command=mymenu&tab=inprogress&sort=az&page=1"><fmt:message key="menu_jsp.menu" /></a></li>
				<li class="nav-item"><a class="nav-link" href="front?command=logout"><fmt:message key="menu_jsp.logout" /></a></li>
			</c:when>
			<c:otherwise>
				<li class="nav-item"><a class="nav-link" href="login.jsp"><fmt:message key="menu_jsp.login" /></a></li>
				<li class="nav-item"><a class="nav-link" href="register.jsp"><fmt:message key="menu_jsp.register" /></a></li>
			</c:otherwise>
		</c:choose>
		<li class="nav-item">
			<form action="changeLocale.jsp" method="post">
				<select name="locale" class="form-select-sm bg-dark text-white" onchange="this.form.submit()">
					<c:forEach items="${applicationScope.locales}" var="locale">
						<c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
						<option value="${locale.key}" ${selected}>${locale.value}</option>
					</c:forEach>
				</select>
				<!--<input type="submit" value="<fmt:message key="menu_jsp.select" />" class="btn btn-dark" />-->
			</form>
		</li>
	</ul>
</div>