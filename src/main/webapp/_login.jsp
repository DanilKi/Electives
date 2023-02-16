<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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