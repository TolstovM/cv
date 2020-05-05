<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 15.03.2020
  Time: 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; ISO-8859-1" language="java"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Sing up" />
<tg:base>
    <h2>Sing up</h2>
    <form method="post">
        <label name="email">Email:</label>
        <input name="email" type="text">
        <br>
        <label name="username">Username:</label>
        <input name="username" type="text">
        <br>
        <label name="password">Password</label>
        <input name="password" type="password">
        <br>
        <input type="submit" value="Sign up">
    </form>
</tg:base>
