<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 07.04.2020
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" scope="request" value="Login" />
<tg:base>
    <form:form method="post" modelAttribute="loginForm">
        <table border="0">
        <tr>
            <td colspan="2" align="center">Login</td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><form:input path="email" /></td>
            <td><form:errors path="email" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="password" /></td>
            <td><form:errors path="password" cssClass="error"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Login"></td>
        </tr>
    </form:form>
</tg:base>
