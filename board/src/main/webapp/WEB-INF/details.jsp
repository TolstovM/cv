<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 08.04.2020
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Advert - Details" />
<tg:base>
    <c:if test="${userId != null && userId.equals(advertForm.userId)}">
        <a href="${pageContext.request.contextPath}/user/items/delete/${advertForm.id}">Delete</a>
    </c:if>
    <h2><c:out value="${advertForm.name}" /></h2>
    <p>Price: ${advertForm.price}</p>
    <p><c:out value="${advertForm.description}" /></p>
    <a href="${pageContext.request.contextPath}/user/search">Back</a>
</tg:base>
