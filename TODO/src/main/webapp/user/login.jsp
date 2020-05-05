<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 16.03.2020
  Time: 2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; ISO-8859-1" language="java"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Login" />
<tg:base>
    <h2>Login</h2>
    <form method="post">
        <label name="username">Username:</label>
        <input name="username" type="text">
        <br>
        <label name="password">Password</label>
        <input name="password" type="password">
        <br>
        <input type="submit" value="Sign up">
    </form>
</tg:base>
