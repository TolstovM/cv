<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 17.03.2020
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; ISO-8859-1" language="java"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Create note" />
<tg:base>
    <h2>Login</h2>
    <form method="post">
        <label name="comment">Comment:</label>
        <input name="comment" type="text">
        <br>
        <input type="submit" value="Create">
    </form>
</tg:base>
