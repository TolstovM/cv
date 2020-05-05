<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 08.04.2020
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Search" />
<tg:base>
    <form method="get">
        <label>Search: <input type="text" name="text"></label>
        <input type="submit" value="Search">
    </form>
    <a href="${pageContext.request.contextPath}/user/new-item">Create</a>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adverts}" var="advert">
            <tr>
                <td><a href="${pageContext.request.contextPath}/user/items/details/${advert.id}"><c:out value="${advert.name}" /></a></td>
                <td>${advert.price}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tg:base>
