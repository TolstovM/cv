<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 07.04.2020
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; ISO-8859-1" language="java"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="pageTitle" scope="request" value="Sign up" />
<tg:base>
    <div align="center">
        <form:form action="signup" method="post" modelAttribute="signUpForm">
            <table border="0">
                <tr>
                    <td colspan="2" align="center">Sign up</td>
                </tr>
                <tr>
                    <td>Username:</td>
                    <td><form:input path="username" /></td>
                    <td><form:errors path="username" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><form:input path="email" /></td>
                    <td><form:errors path="email" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:password path="password" /></td>
                    <td><form:errors path="password" cssClass="error"/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Sign up"></td>
                </tr>
            </table>
        </form:form>
    </div>
</tg:base>
