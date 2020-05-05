<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 08.04.2020
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="pageTitle" scope="request" value="Advert - Create" />
<tg:base>
    <form:form action="new-item" method="post" modelAttribute="advertForm">
        <table border="0">
        <tr>
            <td colspan="2" align="center">Create</td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><form:input path="name" /></td>
            <td><form:errors path="name" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><form:textarea path="description" /></td>
            <td><form:errors path="description" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Price:</td>
            <td><form:input path="price" /></td>
            <td><form:errors path="price" cssClass="error"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Create"></td>
        </tr>
    </form:form>
    <a href="${pageContext.request.contextPath}/user/search">Back</a>
</tg:base>
