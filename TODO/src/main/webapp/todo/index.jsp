<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 16.03.2020
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Notes" />
<tg:base>
    <a href="${pageContext.request.contextPath}/todo/create">Create</a>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">None</th>
            <th scope="col">Comment</th>
            <th scope="col"></th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td scope="row">${item.isDone}</td>
                <td>${item.comment}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/todo/update?id=${item.id}">
                    <c:choose>
                        <c:when test="${item.isDone}">
                            Not done
                        </c:when>
                        <c:when test="${!item.isDone}">
                            Done
                        </c:when>
                    </c:choose>
                    </a>
                </td>
                <td><a href="${pageContext.request.contextPath}/todo/delete?id=${item.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tg:base>
