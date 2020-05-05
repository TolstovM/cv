<%--
  Created by IntelliJ IDEA.
  User: maksfox
  Date: 17.03.2020
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; ISO-8859-1" language="java"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Error" />
<tg:base>
    <h2>Error</h2>
    <h3>${error.message}</h3>
</tg:base>
